package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OkHttpUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.ViewConfig;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.SerialBitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentChannelUpdateImage extends FragmentCommon
// implements
// OnItemClickListener,
// OnClickListener,
// Define
{

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getStaff();

        MainActivity.getInstance().setTitleActionBar(mStaff.getNameStaff());
		if (!CommonActivity.isNullOrEmpty(mStaff.getAddressStaff())) {
            MainActivity.getInstance().getSupportActionBar().setSubtitle(mStaff.getAddressStaff());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// if (!CommonActivity.isNetworkConnected(act)) {// ok
		// Log.e(tag, "net disconn");
		// CommonActivity.createAlertDialog(getActivity(),
		// R.string.errorNetwork, R.string.app_name).show();
		// return null;
		// }
		// TODO Auto-generated method stub
		View mView = inflater.inflate(R.layout.layout_update_channel_image,
				container, false);
		getStaff();
		unit(mView);
		return mView;

	}

	private void getStaff() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			// String timeCreateIdNo, birthday, idno;
			mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		}

	}

	@Override
	public void unit(View v) {

        Button btnChooseImage = (Button) v.findViewById(R.id.btnchooseSdcard);
        Button btnSelectCamera = (Button) v.findViewById(R.id.btnChooseCamera);
		// btnChooseImage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// selectFromGallery();
		// // Log.e(tag + " -- client", mStaff.getStaffId() + "");
		// }
		//
		// });
		// btnSelectCamera.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Log.i("TAG", "Choose camera");
		// // dispatchTakePictureIntent();
		// takeCapture1();
		// // takeCapture_();
		// // Test.foo();
		// // Test.uploadFile(arrStatus);
		// }
		// });
		btnChooseImage.setOnClickListener(this);
		btnSelectCamera.setOnClickListener(this);
	}

	private Staff mStaff;

//	private String uploadFile__(File file) {
//		// TODO Auto-generated method stub
//		try {
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .addFormDataPart("token", Session.getToken())
//                    .addFormDataPart("file")
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
//			System.out.println("executing request " + httppost.toString());
//			System.out
//					.println("executing request " + httppost.getRequestLine());
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//			Log.e("eeeee", "response = + " + response.toString());
//			Log.e("eeeee", "status code  = + "
//					+ response.getStatusLine().getStatusCode());
//			String responseString = new BasicResponseHandler()
//					.handleResponse(response);
//			Log.e("eeeeeeeee", responseString);
//			Log.e("eeeee", "body  = + "
//					+ response.getStatusLine().getStatusCode());
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
//			// mStaff.setLink_photo(getActivity().getString(R.string.link_img_tmp));
//			// link ws:
//			// http://10.58.71.129:8888/SmartphoneV2WS/webresources/syncDatabase/upload/
//			// :
//			// http://10.58.71.129:8888/SmartphoneV2WS/webresources/syncDatabase/image/4bd9488190b74a2384b9ba3c6b8096a81415888854000/temp.folder.image.fileIMG_20141112_222934.jpg
//			String linkImg = Constant.LINK_WS_SYNC + "image/"
//					+ Session.getToken() + "/" + finalFile.getName();
//			Log.e(tag + " -- client", mStaff.getStaffId() + "");
//			mStaff.setLink_photo(linkImg);
//			// mStaff.setBitmap(bitmap);
//			return Constant.SUCCESS_CODE;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Constant.ERROR_CODE;
//		}
//	}

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

//			HttpClient httpclient = new DefaultHttpClient();
//			httpclient.getParams().setParameter(
//					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//			HttpPost httppost = new HttpPost(Constant.LINK_WS_UPLOAD_IMAGE);
//			// File file = new File("D://test.jpg");
//
//			MultipartEntity mpEntity = new MultipartEntity();
//			ContentBody token = new StringBody(Session.getToken());
//			ContentBody staffId = new StringBody(mStaff.getStaffId() + "");
//			FilePrc filePrc = new FilePrc(getActivity());
//			filePrc.foo();
//			ContentBody cbFile = new FileBody(finalFile);
//			mpEntity.addPart("token", token);
//			mpEntity.addPart("file", cbFile);
//			mpEntity.addPart("staffId", staffId);
//			// mpEntity.addPart("file", filePrc.foo());
//			httppost.setEntity(mpEntity);
//			System.out
//					.println("executing request " + httppost.getRequestLine());
//
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//			if (resEntity != null) {
//				// Toast.makeText(getActivity(), "response entity ok",
//				// 1).show();
//				// System.out.println(EntityUtils.toString(resEntity));
//				resEntity.consumeContent();
//			}
//
//			httpclient.getConnectionManager().shutdown();
			// mStaff.setLink_photo(getActivity().getString(R.string.link_img_tmp));
			// link ws:
			// http://10.58.71.129:8888/SmartphoneV2WS/webresources/syncDatabase/upload/
			// :
			// http://10.58.71.129:8888/SmartphoneV2WS/webresources/syncDatabase/image/4bd9488190b74a2384b9ba3c6b8096a81415888854000/temp.folder.image.fileIMG_20141112_222934.jpg
			// String linkImg = Constant.LINK_WS_SYNC + "image/" + Session.token
			// + "/" + finalFile.getName();
			// mStaff.setLink_photo(linkImg);
			// mStaff.setSerialBitmap(new SerialBitmap(bitmap));
//			if (response.getStatusLine().getStatusCode() == 200) {
				return Constant.SUCCESS_CODE;
//			} else {
//				Header[] headers = response.getAllHeaders();
//				if (response.getLastHeader("errorCode") != null) {
//					Log.e("eeee",
//							"errorCode "
//									+ response.getLastHeader("errorCode")
//											.getValue());
//					return response.getLastHeader("errorCode").getValue();
//
//				}
//				/*
//				 * Log.e("eeee", "thong tin tra ve 2 " +
//				 * response.getLastHeader("errorCode").getValue()); for (Header
//				 * header : headers) { if(header.getName() != "errorCode"){
//				 *
//				 * return header.getValue();
//				 *
//				 * }
//				 *
//				 * }
//				 */
//				return Constant.ERROR_CODE;
//			}

		} catch (Exception e) {
			e.printStackTrace();
			return Constant.ERROR_CODE;
		}
	}

	private final String tag = "channel update image";

	// private void resizeFile() {
	// // TODO Auto-generated method stub
	// // if(bitmap.get)
	// Toast.makeText(getActivity(),
	// finalFile.length() + "--" + finalFile.getAbsolutePath(), 1)
	// .show();
	// if (finalFile.length() >= 50000) {
	// // bitmap
	// }
	// }

	// public Bitmap resize(Bitmap bm, float rw, float rh) {
	//
	// int w = bm.getWidth();
	// int h = bm.getHeight();
	// Resources resource = getActivity().getResources();
	// float max = resource.getDimension(R.dimen.MAX_SIZE_CROP_IMG);
	// Bitmap res = bm;
	// Log.e(tag, w + ",," + h + ".....w,h");
	// Log.e(tag, max + ".....max");
	// if (w > max || h > max) {
	// res = ViewConfig.getScaleBitmap(bm, rw);
	// }
	// return res;
	// }

	private static Bitmap resize(Bitmap bm, float rw, float rh) {
		int w = bm.getWidth();
		int h = bm.getHeight();
		Bitmap res = bm;
		if (w > Constant.MAX_SIZE_CROP_IMG || h > Constant.MAX_SIZE_CROP_IMG) {
			res = ViewConfig.getScaleBitmap(bm, rw);
		}
		return res;
	}

	public OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			LoginDialog dialog = new LoginDialog(getActivity(),
					";channel.management;");
			dialog.show();

		}
	};

	private void askUploadFile() {
		bitmap = resize(bitmap, Constant.RATE_RESIZE_IMG,
				Constant.RATE_RESIZE_IMG);

		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					Log.e("e", "Co mang");
					new AsyncTaskUploadFile(getActivity()).execute();

				} else {
					Log.e("e", "Khong co mang");
					CommonActivity.createAlertDialog(getActivity(),
							R.string.errorNetwork, R.string.app_name).show();
				}

			}
		};

		CommonActivity
				.createDialogWithImg(getActivity(), R.string.update_img_staff,
						R.string.app_name, bitmap, R.string.buttonCancel,
						R.string.buttonOk, null, onClickListener).show();
    }

	// private void askUploadFile_() {
	// // TODO Auto-generated method stub
	// // Toast.makeText(getActivity(),
	// // finalFile.length() + "--" + finalFile.getAbsolutePath(), 1)
	// // .show();
	//
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// getActivity());
	//
	// // set title
	// alertDialogBuilder.setTitle(R.string.app_name);
	// // bitmap.setHeight(10);
	// ImageView imgView = new ImageView(getActivity());
	// Log.e(tag, bitmap.getWidth() + ", " + bitmap.getHeight() + ", "
	// + bitmap.getByteCount());
	// bitmap = resize(bitmap, Constant.RATE_RESIZE_IMG,
	// Constant.RATE_RESIZE_IMG);
	// imgView.setImageBitmap(bitmap);
	// // Log.e(tag, imgView.getWidth() + ", " + imgView.getHeight() + ", "
	// // + bitmap.getByteCount()
	// // );
	//
	// // imgView.setMaxWidth(80);
	// // imgView.setMaxHeight(10);
	//
	// // imgView.setBackgroundColor(Color.RED);
	// // photo.getWidth();
	// // set dialog message
	// alertDialogBuilder
	// .setMessage(R.string.update_img_staff)
	// .setView(imgView)
	// // .setCancelable(false)
	// .setPositiveButton(R.string.buttonOk,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // if this button is clicked, close
	// // current activity
	// // Thread threadUploadFile = new Thread() {
	// // public void run() {
	// // uploadFile(finalFile);
	// // }
	// // };
	// // threadUploadFile.start();
	//
	// new AsyncTaskUploadFile(getActivity())
	// .execute();
	// }
	// })
	// .setNegativeButton(R.string.buttonCancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // if this button is clicked, just close
	// // the dialog box and do nothing
	// // dialog.cancel();
	// }
	// });
	//
	// // show it
	// if (alertDialogBuilder != null)
	// alertDialogBuilder.show();
	// }

	private class AsyncTaskUploadFile extends AsyncTask<Void, Void, String> {
		final ProgressDialog progress;
		private final Context context;

		// private Staff staff;
		// private Location myLocation;
		// private ArrayList<ChannelContentCareOJ> arrChannelContent;
		// new VisitChannelAsyncTask(getActivity(), staff, arrChannelContent,
		// myLocation)
		public AsyncTaskUploadFile(Context context) {
			this.context = context;
			// this.staff = staff;
			// this.arrChannelContent = arrChannelContent;
			// this.myLocation = myLocation;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
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
			String title = context.getString(R.string.app_name);
			String message;
            switch (result) {
                case Constant.INVALID_TOKEN2:
                    String errorMessage = getResources().getString(
                            R.string.token_invalid);
                    CommonActivity.BackFromLogin(getActivity(), errorMessage, "");
                    break;
                case Constant.SUCCESS_CODE:
                    String linkImg = Constant.LINK_WS_SYNC + "image/"
                            + Session.getToken() + "/" + finalFile.getName();
                    mStaff.setLink_photo(linkImg);
                    mStaff.setSerialBitmap(new SerialBitmap(bitmap));
                    message = context.getString(R.string.updatesucess);
                    CommonActivity.createAlertDialog(getActivity(), message, title,
                            onClickListenerBack).show();
                    break;
                default:
                    message = context.getString(R.string.updatefail);
                    CommonActivity.createAlertDialog(getActivity(), message, title)
                            .show();
                    break;
            }
			// Log.e(tag, finalFile.delete() + "...del file");
			delFinalFile();
		}

	}

    private void delFinalFile() {
		// TODO Auto-generated method stub
		int count = 0;
		boolean x;
		while (!(x = finalFile.delete())) {
			Log.e(tag, x + ".....del finalFile");
			count++;
			if (count > 10) {
				break;
			}
		}
	}

	private Uri mImageCaptureUri;

	private void takeCapture1() {
		// TODO Auto-generated method stub
		finalFile = null;
		bitmap = null;
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
			// we will handle the returned data in onActivityResult
			// Toast.makeText(
			// getActivity(),
			// MediaStore.EXTRA_OUTPUT
			// + "---"
			// + MediaStore.Images.Media.EXTERNAL_CONTENT_URI
			// .toString(), Toast.LENGTH_SHORT).show();
			startActivityForResult(captureIntent,
					CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException anfe) {
			Toast.makeText(getActivity(),
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT).show();
		}
	}

	// private void takeCapture_() {
	// // TODO Auto-generated method stub
	// // Toast.makeText(getActivity(), "", 1).show();
	// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	//
	// intent.putExtra(MediaStore.EXTRA_OUTPUT,
	// MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
	// // ******** code for crop image
	// // intent.putExtra("crop", "true");
	// intent.putExtra("aspectX", 0);
	// intent.putExtra("aspectY", 0);
	// intent.putExtra("outputX", 200);
	// intent.putExtra("outputY", 150);
	//
	// try {
	//
	// intent.putExtra("return-data", true);
	// startActivityForResult(intent,
	// CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA);
	//
	// } catch (ActivityNotFoundException e) {
	// // Do nothing for now
	// }
	// }

	private void selectFromGallery() {// ok
		finalFile = null;
		bitmap = null;
		String action = Intent.ACTION_GET_CONTENT;
		Intent intent = new Intent(action);
		// Intent intent = new Intent(Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.);
		intent.setType("image/*");

		// startActivityForResult(
		// Intent.createChooser(intent, "Complete action using"),
		// CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY);
		startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY);
	}

	private void warnImgLarge() {

		CommonActivity.createAlertDialog(act, R.string.img_too_large,
				R.string.app_name).show();
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
		// getActivity().RESULT_CANCELED = 0;
		// getActivity().RESULT_FIRST_USER = 1;
		// getActivity().RESULT_OK = -1;
		String res = "act result ... " + requestCode + " ... " + resultCode;
		// Toast.makeText(getActivity(), res + "", 1).show();
		if (resultCode != getActivity().RESULT_OK) {
			if (requestCode != CHANNEL_UPDATE_IMAGE.CROP_PIC) {
				return;
			}
		}

		if (// resultCode == getActivity().RESULT_CANCELED ||
		data == null && requestCode != CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA) {
			// // Toast.makeText(getActivity(), res + " -- data null",
			// 1).show();
			// if (requestCode == CHANNEL_UPDATE_IMAGE.CROP_PIC) {
			// // chup hinh tu camera va ko crop
			// if (finalFile != null) {
			// askUploadOrWarn();
			// }
			// }

			return;
		}
		switch (requestCode) {

		case CHANNEL_UPDATE_IMAGE.PICK_FROM_GALLERY:
			mImageCaptureUri = data.getData();
			Log.e(tag, mImageCaptureUri + "");
			try {
				bitmap = MediaStore.Images.Media.getBitmap(getActivity()
						.getContentResolver(), mImageCaptureUri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// path = getRealPathFromURI(mImageCaptureUri);
			// if (path == null) {
			// return;
			// }
			// finalFile = new File(path);
			finalFile = bitmap2File(bitmap);
			if (finalFile == null) {
				CommonActivity.createAlertDialog(getActivity(),
						R.string.select_img_failed, R.string.app_name).show();
				return;
			}
			// doCrop();
			// performCrop(mImageCaptureUri);
			askUploadOrCrop();
			// performCrop_(mImageCaptureUri);
			// askUploadFile();
			break;

		case CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA:

			try {
				bitmap = MediaStore.Images.Media.getBitmap(getActivity()
						.getContentResolver(), mImageCaptureUri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finalFile = new File(mImageCaptureUri.getPath());

			// askUploadFile();
			performCrop_(mImageCaptureUri);
			break;
		case CHANNEL_UPDATE_IMAGE.CROP_PIC:
			// Toast.makeText(getActivity(), res + " -- crop", 1).show();
			// computeFinalFile(data);
			// askUploadFile();
			Bundle extrasCrop = data.getExtras();

			if (extrasCrop != null) {
				bitmap = extrasCrop.getParcelable("data");

				finalFile = bitmap2File(bitmap);
				if (finalFile == null) {
					return;
				}
				// askUploadFile();
				askUploadOrWarn();
			}
			// finalFile = new File(mImageCaptureUri.getPath());

			// if (f.exists())
			// f.delete();
			break;
		default:
			break;
		}
	}

	private File bitmap2File(Bitmap bitmap) {
		try {
			if (bitmap == null) {
				return null;
			}
			File f = new File(getActivity().getCacheDir(), mStaff.getStaffId()
					+ ".jpg");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */,
					bos);
			byte[] bitmapdata = bos.toByteArray();

			FileOutputStream fos;
			f.createNewFile();
			fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private Bitmap bitmap;

	public void computeFinalFile(Intent data) {
		Bundle extras2 = data.getExtras();
		// Uri uri = data.getData();

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
				// imgview.setImageBitmap(photo);
				// Toast.makeText(getActivity(), " -- gallery--" + path,
				// 1).show();
				// uploadFile(finalFile);
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
			Log.e(tag, idx + "");
			if (idx >= 0) {
				return cursor.getString(idx);
			} else {
				// new AlertDialog.Builder(getActivity())
				// .setTitle(R.string.app_name)
				// .setMessage(R.string.select_img_failed)
				// .setCancelable(false)
				// .setNegativeButton(R.string.buttonOk, null).show();
				CommonActivity.createAlertDialog(getActivity(),
						R.string.select_img_failed, R.string.app_name).show();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return null;
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
			// intent.putExtra("outputX", 200);
			// intent.putExtra("outputY", 200);
			// intent.putExtra("aspectX", 1);
			// intent.putExtra("aspectY", 1);
			// intent.putExtra("scale", true);
			// retrieve data on return
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		// if (actionbarClicked || getActivity() == null || mView == null
		// || arg0 == null) {
		// return;
		// }
		super.onClick(arg0);
		if (actionbarClicked) {
			return;
		}
		if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
			Log.e(tag, "net disconn");
			CommonActivity.createAlertDialog(getActivity(),
					R.string.errorNetwork, R.string.app_name).show();
			return;
		} else {
			Log.e(tag, "net ok");
		}
		int id = arg0.getId();
		switch (id) {
		case R.id.btnchooseSdcard:
			// if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
			// Log.e(tag, "net disconn");
			// CommonActivity.createAlertDialog(getActivity(),
			// R.string.errorNetwork, R.string.app_name).show();
			// return;
			// } else {
			// Log.e(tag, "net ok");
			// }
			selectFromGallery();
			break;

		case R.id.btnChooseCamera:
			// if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
			// Log.e(tag, "net disconn");
			// CommonActivity.createAlertDialog(getActivity(),
			// R.string.errorNetwork, R.string.app_name).show();
			// return;
			// } else {
			// Log.e(tag, "net ok");
			// }
			takeCapture1();
			break;
		default:
			break;
		}
		// try {
		//
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	public class FilePrc {
		final String nameFolderSd;
		public String pathFolderSd;
		File dir;
		final Context context;
		String pathFolderDbx;
		// boolean downloaded = true;
		boolean created = false;

		public boolean createFolder() {
			pathFolderSd = Environment.getExternalStorageDirectory() + "/data/"
					+ nameFolderSd;

			dir = new File(pathFolderSd);
			if (dir.exists()) {
				return false;
			} else {
				try {
					if (dir.mkdirs()) {
						System.out.println("Directory created");
					} else {
						System.out.println("Directory is not created");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		public FilePrc(Context context) {
			this.context = context;
			// nameFolderSd = context.getPackageName();
			nameFolderSd = Constant.nameSdFolderApps;

		}

		// String instrPath1, fileListAppsName1="";
		String instrPath1, fileName = "";

		private File init() {
			// TODO Auto-generated method stub
			created = createFolder();
			fileName = // "." +
			Constant.UPDATE_STAFF + "_" + mStaff.getStaffId()
					+ Constant.extSdcard;
			instrPath1 = pathFolderSd + "/" + fileName;
			Log.e(tag, instrPath1);
            return new File(instrPath1);
		}

		// public void start() {
		// created = createFolder();
		// fileName = mStaff.getStaffId() + Constant.extSdcard;
		// instrPath1 = pathFolderSd + "/" + fileName;
		// File imageFile = finalFile;
		// File compressedImageFile = new File(instrPath1);
		// try {
		// InputStream inputStream = new FileInputStream(imageFile);
		// OutputStream outputStream = new FileOutputStream(
		// compressedImageFile);
		//
		// float imageQuality = 0.3f;
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		//
		// }

		// ContentBody token = new StringBody(Session.getToken());
		// ContentBody cbFile = new FileBody(file);
		// mpEntity.addPart("token", token);
		// mpEntity.addPart("file", cbFile);
		// private ContentBody foo_() {
		// // TODO Auto-generated method stub
		// ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// // Bitmap bm = BitmapFactory.decodeFile(imageUri.getPath());
		// Bitmap bm = bitmap;
		// bm.compress(CompressFormat.JPEG, 60, bos);
		// ContentBody mimePart = new ByteArrayBody(bos.toByteArray(),
		// "filename");
		// return mimePart;
		// }

//		private ContentBody foo() {
//			// TODO Auto-generated method stub
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			// Bitmap bm = BitmapFactory.decodeFile(imageUri.getPath());
//			Bitmap bm = bitmap;
//			bm.compress(CompressFormat.JPEG, 60, bos);
//			finalFile = xx(bos);
//			ContentBody mimePart = new ByteArrayBody(bos.toByteArray(),
//					"filename");
//			return mimePart;
//		}

        private void foo() {
            // TODO Auto-generated method stub
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // Bitmap bm = BitmapFactory.decodeFile(imageUri.getPath());
            Bitmap bm = bitmap;
            bm.compress(CompressFormat.JPEG, 60, bos);
            finalFile = xx(bos);
        }

		private File xx(ByteArrayOutputStream baos) {
			// TODO Auto-generated method stub
			File file = init();

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				// ByteArrayOutputStream baos = new ByteArrayOutputStream();

				// Put data in your baos

				baos.writeTo(fos);

				fos.flush();
				fos.close();
			} catch (IOException ioe) {
				// Handle exception here
				ioe.printStackTrace();
			}
            return file;
		}
	}

	@Override
	public void setPermission() {
		permission = ";channel.management;";

	}

}
