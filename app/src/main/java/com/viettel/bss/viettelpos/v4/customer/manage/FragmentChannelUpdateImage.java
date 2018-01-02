package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OkHttpUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentChannelUpdateImage extends Fragment
		implements
			OnItemClickListener,
			OnClickListener,
			Define {

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mView = inflater.inflate(R.layout.layout_update_channel_image,
				container, false);
		Unit(mView);
		return mView;

	}

	private void Unit(View v) {
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		}
        Button btnChooseImage = (Button) v.findViewById(R.id.btnchooseSdcard);
        Button btnSelectCamera = (Button) v.findViewById(R.id.btnChooseCamera);
		btnChooseImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectFromGallery();
				// Log.e(tag + " -- client", mStaff.getStaffId() + "");
			}

		});
		btnSelectCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("TAG", "Choose camera");
				// dispatchTakePictureIntent();
				takeCapture1();
			}
		});

	}

	private Staff mStaff;

	private String uploadFile(File file) {
		// TODO Auto-generated method stub
		try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("token", Session.getToken())
                    .addFormDataPart("file", "image.png", RequestBody.create(MediaType.parse("image/png"), finalFile))
                    .addFormDataPart("staffId", String.valueOf(mStaff.getStaffId()))
                    .build();


            Request request = new Request.Builder()
                    .url(Constant.LINK_WS_UPLOAD_IMAGE)
                    .post(requestBody)
                    .build();

            Response response = OkHttpUtils.getClient().newCall(request).execute();
            String result = response.body().string();
            Log.d("FragmentChannelUpdateImage", "response = " + result);
//
//			HttpClient httpclient = new DefaultHttpClient();
//			httpclient.getParams().setParameter(
//					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//			HttpPost httppost = new HttpPost(Constant.LINK_WS_UPLOAD_IMAGE);
//			// File file = new File("D://test.jpg");
//
//			MultipartEntity mpEntity = new MultipartEntity();
//			ContentBody token = new StringBody(Session.getToken());
//			ContentBody cbFile = new FileBody(file);
//			mpEntity.addPart("token", token);
//			mpEntity.addPart("file", cbFile);
//
//			httppost.setEntity(mpEntity);
//			System.out
//					.println("executing request " + httppost.getRequestLine());
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//
//			System.out.println(response.getStatusLine());
//			if (resEntity != null) {
//				// Toast.makeText(getActivity(), "response entity ok",
//				// 1).show();
//				// System.out.println(EntityUtils.toString(resEntity));
//				Log.e(tag, EntityUtils.toString(resEntity));
//				resEntity.consumeContent();
//			}
//
//			httpclient.getConnectionManager().shutdown();
			String linkImg = Constant.LINK_WS_SYNC + "image/" + Session.getToken()
					+ "/" + finalFile.getName();
			Log.e(tag + " -- client", mStaff.getStaffId() + "");
			mStaff.setLink_photo(linkImg);
			// mStaff.setBitmap(bitmap);
			return Constant.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.ERROR_CODE;
		}
	}

	private final String tag = "channel update image";

	private void askUploadFile() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		alertDialogBuilder.setTitle(R.string.app_name);
		// bitmap.setHeight(10);
		ImageView imgView = new ImageView(getActivity());
		imgView.setImageBitmap(bitmap);
		alertDialogBuilder
				.setMessage(R.string.update_img_staff)
				.setView(imgView)
				// .setCancelable(false)
				.setPositiveButton(R.string.buttonOk,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								new AsyncTaskUploadFile(getActivity())
										.execute();
							}
						})
				.setNegativeButton(R.string.buttonCancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});

		// show it
		if (alertDialogBuilder != null)
			alertDialogBuilder.show();
	}

	private class AsyncTaskUploadFile extends AsyncTask<Void, Void, String> {
		final ProgressDialog progress;
		private final Context context;

		public AsyncTaskUploadFile(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			// Log.i("TAG", " Kich thuoc " + arrChannelContent.size());
			return uploadFile(finalFile);

		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("TAG", " Ket qua cap nhat cham soc " + result);
			this.progress.dismiss();
			String title = "";
			if (Constant.SUCCESS_CODE.equals(result)) {
				String message = context.getString(R.string.updatesucess);
				// String message = "";
				title = context.getString(R.string.app_name);
				new AlertDialog.Builder(context).setTitle(title)
						.setMessage(message).setCancelable(false)
						.setNegativeButton(R.string.buttonOk, null).show();

			} else {
				title = context.getString(R.string.app_name);
				new AlertDialog.Builder(context).setTitle(title)
						.setMessage(R.string.updatefail).setCancelable(false)
						.setNegativeButton(R.string.buttonOk, null).show();
			}
		}

	}

    private Uri mImageCaptureUri;

	private void takeCapture1() {
		// TODO Auto-generated method stub
		finalFile = null;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// tinh toan uri cua anh se chup
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "tmp_avatar_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

		// put vao extras
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		try {
			intent.putExtra("return-data", true);

			startActivityForResult(intent,
					CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void takeCapture() {
		finalFile = null;
		// TODO Auto-generated method stub
		try {
			// use standard intent to capture an image
			Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(captureIntent,
					CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException anfe) {
			Toast.makeText(getActivity(),
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void selectFromGallery() {// ok
		finalFile = null;
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");

		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY);
	}

	private void warnImgLarge() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		alertDialogBuilder.setTitle(R.string.app_name);

		alertDialogBuilder.setMessage(R.string.img_too_large)
		// .setCancelable(false)
				.setPositiveButton(R.string.buttonOk,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

							}
						});

		// show it
		alertDialogBuilder.show();
	}

	private void askUploadOrCrop() {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), finalFile.length() + "...size",
		// 1).show();
		if (finalFile.length() >= Constant.MAX_SIZE_IMG) {

			// warnImgLarge();
			performCrop_(mImageCaptureUri);
		} else {
			askUploadFile();
		}
	}

	private void askUploadOrWarn() {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), finalFile.length() + "...size",
		// 1).show();
		if (finalFile.length() >= Constant.MAX_SIZE_IMG) {
			warnImgLarge();
			// performCrop_(mImageCaptureUri);
		} else {
			askUploadFile();
		}
	}

	private File finalFile;
	private String path;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String res = "act result ... " + requestCode + " ... " + resultCode;
		if (resultCode != getActivity().RESULT_OK) {
			if (requestCode != CHANNEL_UPDATE_IMAGE.CROP_PIC) {
				return;
			}
		}

		if (// resultCode == getActivity().RESULT_CANCELED ||
		data == null && requestCode != CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA) {

			return;
		}
		switch (requestCode) {

			case CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY :
				mImageCaptureUri = data.getData();

				try {
					bitmap = MediaStore.Images.Media.getBitmap(getActivity()
							.getContentResolver(), mImageCaptureUri);
				} catch (IOException e) {
					e.printStackTrace();
				}
				path = getRealPathFromURI(mImageCaptureUri);
				if (path == null) {
					return;
				}
				finalFile = new File(path);
				askUploadOrCrop();
				break;

			case CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA :

				try {
					bitmap = MediaStore.Images.Media.getBitmap(getActivity()
							.getContentResolver(), mImageCaptureUri);
				} catch (IOException e) {
					e.printStackTrace();
				}
				finalFile = new File(mImageCaptureUri.getPath());

				performCrop_(mImageCaptureUri);
				break;
			case CHANNEL_UPDATE_IMAGE.CROP_PIC :
				Bundle extrasCrop = data.getExtras();

				if (extrasCrop != null) {
					bitmap = extrasCrop.getParcelable("data");
					finalFile = bitmap2File(bitmap);
					askUploadOrWarn();
				}
				break;
			default :
				break;
		}
	}

	private File bitmap2File(Bitmap bitmap) {
		// TODO Auto-generated method stub
		File f = new File(getActivity().getCacheDir(), mStaff.getStaffId()
				+ ".jpg");

		// Convert bitmap to byte array
		// Bitmap bitmap = your bitmap;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();

		// write the bytes in file
		FileOutputStream fos;
		try {
			f.createNewFile();
			fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	// @Override
	public void onActivityResult_(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		String res = "act result " + requestCode;
		// Toast.makeText(this, res + "", 1).show();
		if (// resultCode == getActivity().RESULT_CANCELED ||
		data == null) {
			// Toast.makeText(getActivity(), res + " -- data null", 1).show();
			if (requestCode == CHANNEL_UPDATE_IMAGE.CROP_PIC) {
				// chup hinh tu camera va ko crop
				if (finalFile != null) {
					askUploadFile();
				}
			}

			return;
		}
		Log.e(res, res);
		switch (requestCode) {
			case CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA :
				Bundle extras2 = data.getExtras();
				Uri uri = null;
				uri = data.getData();
				path = getRealPathFromURI(uri);
				finalFile = new File(path);
				performCrop(uri);
				break;

			case CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY :

			case CHANNEL_UPDATE_IMAGE.CROP_PIC :
				// Toast.makeText(getActivity(), res + " -- crop", 1).show();
				computeFinalFile(data);
				askUploadFile();
				break;
			default :
				break;
		}
	}

	// String mCurrentPhotoPath;

	private Bitmap bitmap;

	private void computeFinalFile(Intent data) {
		Bundle extras2 = data.getExtras();
		// Uri uri = data.searchData();

		if (extras2 != null) {
			bitmap = extras2.getParcelable("data");
			// extras2.get
			// CALL THIS METHOD TO GET THE URI FROM THE BITMAP
			Uri uri = getImageUri(getActivity(), bitmap);

			// CALL THIS METHOD TO GET THE ACTUAL PATH

			if (uri != null) {
				path = getRealPathFromURI(uri);
				if (path == null) {
					return;
				}
				finalFile = new File(path);
			}

		} else {
			// Toast.makeText(getActivity(), " -- gallery bundle null",
			// 1).show();
		}
	}

	private Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
		// Images.Media.in
		// return Uri.parse("xxx");
	}

	private String getRealPathFromURI(Uri uri) {
		Cursor cursor = getActivity().getContentResolver().query(uri, null,
				null, null, null);
		try {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	private void performCrop(Uri picUri) {
		Intent cropIntent = new Intent("com.android.camera.action.CROP", null)
				.setDataAndType(picUri, "image/*").putExtra("crop", "true")
				.putExtra("aspectX", 1).putExtra("aspectY", 1)
				.putExtra("outputX", 128)
				.putExtra("outputY", 128)
				.putExtra("scale", true)
				// .putExtra("return-data", false)
				// .putExtra("scaleUpIfNeeded", true)
				.putExtra(MediaStore.EXTRA_OUTPUT, picUri)
				.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(cropIntent, CHANNEL_UPDATE_IMAGE.CROP_PIC);

	}

	public static String SaveImage(Bitmap finalBitmap) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Shopic Snaps");

		if (!myDir.exists())
			myDir.mkdirs();

		// Random generator = new Random();
		// int n = 10000;
		// n = generator.nextInt(n);
		String fname = "Image_" + System.currentTimeMillis() + ".jpg";
		File file = new File(myDir, fname);

		if (file.exists())
			file.delete();

		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root + "/App Snaps/" + fname;
	}

	/**
	 * this function does the crop operation.
	 */
	private void performCrop_(Uri picUri) {
		// take care of exceptions
		try {
			// call the standard crop action intent (the user device may not
			// support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", Constant.MAX_SIZE_CROP_IMG);
			cropIntent.putExtra("outputY", Constant.MAX_SIZE_CROP_IMG);
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, CHANNEL_UPDATE_IMAGE.CROP_PIC);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			Toast toast = Toast.makeText(getActivity(),
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
