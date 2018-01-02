package com.viettel.bss.viettelpos.v4.ui.image.picker;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.DelayAsyncTask;
import com.viettel.bss.viettelpos.v4.ui.image.model.Image;
import com.viettel.bss.viettelpos.v4.ui.image.utils.ImageInternalFetcher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ImagePickerActivity extends AppCompatActivity {

	private static final String KEY_LIST = "com.viettel.bss.viettelpos.v4.savedinstance.key.list";
	public static final String EXTRA_IMAGE_URIS = "com.viettel.bss.viettelpos.v4.extra.selected_image_uris";

	private Set<Image> mSelectedImages;
	private LinearLayout mSelectedImagesContainer;
	private TextView mSelectedImageEmptyMessage;

	private ViewPager mViewPager;
	public ImageInternalFetcher mImageFetcher;
	private static String mCurrentPhotoPath = "";

	// initialize with default config.
	private static Config mConfig = new Config.Builder().build();

	private int imageId = -1;
	private Uri[] uris;
	private static final int SWIPE_MIN_DISTANCE = 20;
	private static final int SWIPE_THRESHOLD_VELOCITY = 50;
	private ImageButton imageCamera;
	private Uri fileUri;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int MEDIA_TYPE_VIDEO = 2;
	private PagerAdapter2Fragments adapter;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.pp__picker_image);
		Intent intent = getIntent();
		if (bundle == null)
			bundle = intent.getExtras();
		if (bundle != null && bundle.containsKey("imageId")) {
			imageId = bundle.getInt("imageId", -1);
		}
		Log.d(Constant.TAG, "onCreate() imageId: " + imageId);

		Parcelable[] parcelableUris = intent
				.getParcelableArrayExtra(EXTRA_IMAGE_URIS);
		if (parcelableUris != null) {
			uris = new Uri[parcelableUris.length];
			System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
			Log.d(Constant.TAG, "onCreate() uris: " + uris.length);
		} else {
			Log.d(Constant.TAG, "onCreate() uris NOTFOUND");
		}
		// Java doesn't allow array casting, this is a little hack
		mSelectedImagesContainer = (LinearLayout) findViewById(R.id.pp__selected_photos_container);
		mSelectedImageEmptyMessage = (TextView) findViewById(R.id.pp__selected_photos_empty);
		mViewPager = (ViewPager) findViewById(R.id.pp__pager);
		Button mCancelButtonView = (Button) findViewById(R.id.pp__btn_cancel);
		Button mDoneButtonView = (Button) findViewById(R.id.pp__btn_done1);
		mSelectedImages = new HashSet<>();
		mImageFetcher = new ImageInternalFetcher(this, 500);
		mCancelButtonView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});
		mDoneButtonView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("Onclickkkkkkkkkkkkkkkkkkkkkk", "Onclickkkkkkkkkkkkkkkkkkkkkk");
				if (mSelectedImages.isEmpty()) {
					Toast.makeText(
							ImagePickerActivity.this,
							ImagePickerActivity.this
									.getString(R.string.no_images_selected),
							Toast.LENGTH_SHORT).show();
				} else {
					Uri[] uris = new Uri[mSelectedImages.size()];
					int i = 0;
					for (Image img : mSelectedImages) {
						uris[i++] = img.mUri;
					}

					Intent intent = new Intent();
					intent.putExtra("imageId", imageId);
					intent.putExtra(EXTRA_IMAGE_URIS, uris);
					setResult(Activity.RESULT_OK, intent);
					Log.i(Constant.TAG, "mOnFinishGettingImages imageId: " + imageId);
					finish();
				}
			}
		});

		// ==thinhhq1 bo sung them camrera chup anh thu he thong =====
		imageCamera = (ImageButton) findViewById(R.id.camera);
		imageCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				} else {
					fileUri = getOutputMediaFileUri(getApplicationContext(), MEDIA_TYPE_IMAGE);
				}
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(intent, 11);
			}
		});

		if (uris != null && uris.length > 0) {
			for (Uri uri : uris) {
				Image image = new Image(uri, 0);
				addImage(image);
			}
		}
		setupActionBar();
		if (bundle != null) {
			populateUi(bundle);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 11) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Log.d("data", "" + data);
				if (fileUri != null) {
					Log.e("fileUri", "fileUri ! nullllllllllllll " + fileUri.getPath());
					Log.e("fileUri", "mCurrentPhotoPath = " + mCurrentPhotoPath);
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
						fileUri = Uri.parse(mCurrentPhotoPath);
					}
					Image image = new Image(fileUri, 0);
					addImage(image);
					notifyGalleryAddPic();

					// wait notify
					final GalleryFragment galleryFragment = (GalleryFragment) adapter.getItem(0);
					new DelayAsyncTask(this, new OnPostExecuteListener<String>() {
						@Override
						public void onPostExecute(String result, String errorCode, String description) {
							galleryFragment.initDataImages();
						}
					}, null).execute(1000);
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}
	}

	private void notifyGalleryAddPic() {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	public static void setConfig(Config config) {
		if (config == null) {
			throw new NullPointerException(
					"Config cannot be passed null. Not setting config will use default values.");
		}
		mConfig = config;
	}

	public static Config getConfig() {
		return mConfig;
	}

	private void populateUi(Bundle bundle) {
		ArrayList<Image> list = bundle.getParcelableArrayList(KEY_LIST);
		if (list != null) {
			for (Image image : list) {
				addImage(image);
			}
		}
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static Uri getOutputMediaFileUri(Context mContext, int type){
		return FileProvider.getUriForFile(mContext,
				mContext.getApplicationContext().getPackageName() + ".fileprovider",
				getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {

		File mediaStorageDir = new File(Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MBBCSCameraApp");
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
			mCurrentPhotoPath = mediaFile.getPath();
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VD_"+ timeStamp + ".mp4");
			mCurrentPhotoPath = mediaFile.getPath();
		} else {
			mCurrentPhotoPath = null;
			return null;
		}

		return mediaFile;
	}

	/**
	 * Sets up the action bar, adding view page indicator.
	 */
	private void setupActionBar() {
		adapter = new PagerAdapter2Fragments(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		SlidingTabText mSlidingTabText = (SlidingTabText) findViewById(R.id.pp__sliding_tabs);
		mSlidingTabText.setSelectedIndicatorColors(getResources().getColor(
				mConfig.getTabSelectionIndicatorColor()));
		mSlidingTabText.setCustomTabView(R.layout.pp__tab_view_text,
				R.id.pp_tab_text);
		mSlidingTabText.setTabStripColor(mConfig.getTabBackgroundColor());
		mSlidingTabText.setTabTitles(getResources().getStringArray(
				R.array.tab_titles));
		mSlidingTabText.setViewPager(mViewPager);
	}

	public boolean addImage(Image image) {
		Log.d(Constant.TAG, "addImage " + image.mUri.getPath());
		if (mSelectedImages == null) {
			mSelectedImages = new HashSet<>();
		}
		String url = image.mUri.getPath();
		File file = new File(url);
		if (file.length() > (8 * 1024 * 1024)) {
			Toast.makeText(
					this,
					getString(R.string.image_overload,
							mConfig.getSelectionLimit()), Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		if (mSelectedImages.size() >= mConfig.getSelectionLimit()) {
			Toast.makeText(
					this,
					getString(R.string.n_images_selected,
							mConfig.getSelectionLimit()), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		Boolean isExtends = false;
		List<String> lstExtends = new ArrayList<>();
		lstExtends.add(".jpg");
		lstExtends.add(".jpeg");
		lstExtends.add(".png");
		for (String string : lstExtends) {
			if (url.toLowerCase().endsWith(string)) {
				isExtends = true;
				break;
			}
		}
		if (!isExtends) {

			CommonActivity.createAlertDialog(this,
					getString(R.string.chon_anh_sai_dinh_dang),
					getString(R.string.app_name)).show();
			return false;
		}
		if (mSelectedImages.add(image)) {
			@SuppressLint("InflateParams") View rootView = LayoutInflater.from(ImagePickerActivity.this)
					.inflate(R.layout.pp__list_item_selected_thumbnail, null);
			ImageView thumbnail = (ImageView) rootView
					.findViewById(R.id.pp__selected_photo);
			rootView.setTag(image.mUri);

			rootView.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(final View view) {

					for (Image image : mSelectedImages) {
						if (view.getTag().equals(image.mUri)) {
							Log.d(Constant.TAG, "onLongClick " + view.getTag());
							ImagePickerActivity.this.removeImage(image);
							break;
						}
					}

					return false;
				}
			});

			mImageFetcher.loadImage(image.mUri, thumbnail);
			mSelectedImagesContainer.addView(rootView,
					mSelectedImagesContainer.getChildCount());

			int px = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 60, getResources()
							.getDisplayMetrics());
			thumbnail.setLayoutParams(new FrameLayout.LayoutParams(px, px));

			if (mSelectedImages.size() >= 1) {
				mSelectedImagesContainer.setVisibility(View.VISIBLE);
				mSelectedImageEmptyMessage.setVisibility(View.GONE);
			}
			return true;
		}
		return false;
	}

	private Animation bottomToTopAnimation(MyAnimationListener animationListener) {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f);
		outtoRight.setDuration(1000);
		outtoRight.setInterpolator(new AccelerateInterpolator());

		outtoRight.setAnimationListener(animationListener);

		return outtoRight;
	}

	private class MyAnimationListener implements Animation.AnimationListener {
		private final View view;

		public MyAnimationListener(View _view) {
			this.view = _view;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			Log.d(Constant.TAG, "onAnimationStart " + new Date());
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			Log.d(Constant.TAG,
					"onAnimationEnd " + new Date() + " tag:" + view.getTag());

			for (Image image : mSelectedImages) {
				if (view.getTag().equals(image.mUri)) {
					Log.d(Constant.TAG, "onAnimationEnd FOUND " + view.getTag());
					ImagePickerActivity.this.removeImage(image);
					break;
				}
			}

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	public boolean removeImage(Image image) {
		Image tmp = null;
		for (Image item : mSelectedImages) {
			tmp = item;
			if (tmp.mUri.toString().equals(image.mUri.toString())) {
				break;
			}
		}
		if (tmp != null && mSelectedImages.remove(tmp)) {
			for (int i = 0; i < mSelectedImagesContainer.getChildCount(); i++) {
				View childView = mSelectedImagesContainer.getChildAt(i);
				if (childView.getTag().equals(image.mUri)) {
					mSelectedImagesContainer.removeViewAt(i);
					break;
				}
			}
			if (mSelectedImages.size() == 0) {
				mSelectedImagesContainer.setVisibility(View.GONE);
				mSelectedImageEmptyMessage.setVisibility(View.VISIBLE);

			}
			if (adapter != null) {
				GalleryFragment gallery = (GalleryFragment) adapter.getItem(0);
				if (gallery != null && gallery.mGalleryAdapter != null) {
					gallery.mGalleryAdapter.notifyDataSetChanged();
				}
			}
			return true;
		}
		return false;
	}

	public boolean containsImage(Image image) {
		if (image == null) {
			return false;
		}
		for (Image item : mSelectedImages) {
			if (item.mUri.getPath().equals(image.mUri.getPath())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// preserve already taken images on configuration changes like
		// screen rotation or activity run out of memory.
		// HashSet cannot be saved, so convert to list and then save.
		ArrayList<Image> list = new ArrayList<>(mSelectedImages);
		outState.putParcelableArrayList(KEY_LIST, list);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		populateUi(savedInstanceState);
	}
}
