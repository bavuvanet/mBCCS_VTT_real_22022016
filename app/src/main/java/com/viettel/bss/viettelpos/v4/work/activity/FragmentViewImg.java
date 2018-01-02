package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.work.object.OjUri;
import com.viettel.bss.viettelpos.v4.work.object.TouchImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FragmentViewImg extends FragmentCommon {
	private TouchImageView touchImageView;
	private boolean finished = false;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getStaff();
		Log.e(tag, mStaff.getUrl());
        setTitleActionBar(mStaff.getName());
		// txtAddressActionBar.setVisibility(View.VISIBLE);
		// txtAddressActionBar.setText(mStaff.getAddressStaff());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(tag, "on destroy");
		if (asyncLoadImage != null) {
			asyncLoadImage.cancel(true);
		}
	}
	public boolean getFinished() {
		return finished;
	}
	private final String tag = "fragment view img";
	private OjUri mStaff;
	private void getStaff() {
		if (mStaff != null) {
			return;
		}
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			// String timeCreateIdNo, birthday, idno;
			mStaff = (OjUri) mBundle.getSerializable(Define.KEY_OJ_URI);
		}
	}

	// public void decodeUri(Uri uri) {
	// ParcelFileDescriptor parcelFD = null;
	// try {
	// parcelFD = act.getContentResolver().openFileDescriptor(uri, "r");
	// FileDescriptor imageSource = parcelFD.getFileDescriptor();
	//
	// // Decode image size
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inJustDecodeBounds = true;
	// BitmapFactory.decodeFileDescriptor(imageSource, null, o);
	//
	// // the new size we want to scale to
	// final int REQUIRED_SIZE = 1024;
	//
	// // Find the correct scale value. It should be the power of 2.
	// int width_tmp = o.outWidth, height_tmp = o.outHeight;
	// int scale = 1;
	// while (true) {
	// if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
	// break;
	// }
	// width_tmp /= 2;
	// height_tmp /= 2;
	// scale *= 2;
	// }
	//
	// // decode with inSampleSize
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inSampleSize = scale;
	// Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource,
	// null, o2);
	//
	// touchImageView.setImageBitmap(bitmap);
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// // handle errors
	// } catch (IOException e) {
	// e.printStackTrace();
	// // handle errors
	// } finally {
	// if (parcelFD != null)
	// try {
	// parcelFD.close();
	// } catch (IOException e) {
	// // ignored
	// }
	// }
	// }

	// private String readTextFromUri(Uri uri) throws IOException {
	// InputStream inputStream = act.getContentResolver().openInputStream(uri);
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// inputStream));
	// StringBuilder stringBuilder = new StringBuilder();
	// String line;
	// // while ((line = reader.readLine()) != null) {
	// // stringBuilder.append(line);
	// // }
	// int x;
	// while ((x = reader.read()) != -1) {
	// // stringBuilder.append(line);
	//
	// }
	// inputStream.close();
	// // fileInputStream.close();
	// // parcelFileDescriptor.close();
	// return stringBuilder.toString();
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getStaff();
		idLayout = R.layout.layout_policy_view_img;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private AsyncLoadImage asyncLoadImage;
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		touchImageView = (TouchImageView) v.findViewById(R.id.img);
		// touchImageView.setBackgroundColor(act.getResources().getColor(
		// R.color.orange));
		// decodeUri(Uri.parse(mStaff.getUrl()));
		String textUrl = "http://upload.wikimedia.org/wikipedia/commons/b/b3/Viettel_Logo.jpg";
        textUrl = "http://www.i2clipart.com/cliparts/9/c/c/e/clipart-happy-running-dog-9cce.png";
		textUrl = mStaff.getUrl();
		// image.setImageURI(Uri.parse(mStaff.getUrl()));

		asyncLoadImage = new AsyncLoadImage(act);
		asyncLoadImage.execute(textUrl);

		// Uri uri = Uri.parse(textUrl);
		// // image.setImageURI(uri);
		// Bitmap bitmap = null;
		// try {
		// // bitmap = MediaStore.Images.Media.getBitmap(
		// // act.getContentResolver(), uri);
		//
		// URL url = new URL(textUrl);
		// bitmap = BitmapFactory.decodeStream(url.openConnection()
		// .getInputStream());
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// image.setImageBitmap(bitmap);
		// image.invalidate();
	}

	public class AsyncLoadImage extends AsyncTask<String, Void, Bitmap> {
		final ProgressDialog progress;
		private final Context context;
		public AsyncLoadImage(Context context) {
			this.context = context;
			// this.staff = staff;
			// this.arrChannelContent = arrChannelContent;
			// this.myLocation = myLocation;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.loading));
			progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Bitmap doInBackground(String... arg0) {
			Bitmap bitmap = null;
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			try {

				// bitmap = MediaStore.Images.Media.getBitmap(
				// act.getContentResolver(), uri);

				URL url = new URL(arg0[0]);
				InputStream inputStream = url.openConnection().getInputStream();
				// inputStream.reset();
				// bitmap = BitmapFactory.decodeStream(inputStream);
				bitmap = BitmapFactory.decodeStream(new BufferedInputStream(
						inputStream));

				// bitmap = BitmapFactory.decodeStream(new BufferedInputStream(
				// inputStream), null, options);
				// bitmap = BitmapFactory.decodeStream(inputStream, null,
				// options);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			this.progress.dismiss();
			if (result != null) {
				touchImageView.setNormalizedScale(0.99f);
				touchImageView.setImageBitmap(result);
				// touchImageView.setBackgroundResource(R.drawable.account_07);
				// touchImageView.setBackgroundColor(Color.BLUE);
				// touchImageView.setBackgroundColor(act.getResources().getColor(
				// R.color.blue_light));
				// Log.e(tag, result.getByteCount() + "");
				Log.e(tag, result.getWidth() + "---------" + result.getHeight()
						+ "-----------------size");
				// toast("ok");
				finished = true;
			} else {
				CommonActivity.createAlertDialog(act,
						R.string.cannot_display_img, R.string.app_name,
						onClickListener).show();

			}

		}

	}

	private final OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			act.onBackPressed();
		}
	};
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
