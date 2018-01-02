package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OkHttpUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.dal.DalPolicy;
import com.viettel.bss.viettelpos.v4.work.object.OjUri;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject_;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentPolicyDetail extends FragmentCommon {
	private String ARR_EXT[] = new String[] { ".jpg", ".mp3" };
	// ========================================================================================
	// tham so vao cua ham doInBackground
	// kieu cua ham onPostExcute
	// gia tri tra ve cua ham doInBackground
	private class AsyncTaskDownloader extends AsyncTask<Integer, Void, Boolean> {
		private final Context context;
		final ProgressDialog progress;
		int fileType;

		public AsyncTaskDownloader(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {

				this.progress.show();
			}
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			fileType = params[0];
			return checkSession(fileType);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// lay duoc noi dung file ve local
			Log.e("TAG", " Ket qua cap nhat cham soc " + result);
			this.progress.dismiss();
			if (result) {
				processFile_(fileType);
			} else {
				Dialog dialog = CommonActivity
						.createAlertDialog(getActivity(),
								R.string.token_invalid, R.string.app_name,
								moveLogInAct);
				dialog.show();
			}

		}
	}

    // chua dung den===================================================
	Dialog dialog;

	private class AsyncCheckSession extends AsyncTask<Integer, Void, Boolean> {
		private final Context context;
		final ProgressDialog progress;
		int fileType;

		public AsyncCheckSession(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {

				this.progress.show();
			}
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			fileType = params[0];
			return checkSession(fileType);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// lay duoc noi dung file ve local
			Log.e("TAG", " Ket qua cap nhat cham soc " + result);
			this.progress.dismiss();
			if (result) {
				// processFile_(fileType);

			} else {
				Dialog dialog = CommonActivity
						.createAlertDialog(getActivity(),
								R.string.token_invalid, R.string.app_name,
								moveLogInAct);
				dialog.show();
			}

		}
	}

	private class AsyncTaskUpdateSender extends
			AsyncTask<Integer, Void, String> {
		private final Context context;
		final ProgressDialog progress;
		private String original;
		final XmlDomParse parse = new XmlDomParse();
		String description = "";
		String errorCode = "";

		public AsyncTaskUpdateSender(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			return sendRequestUpdate(mStaff.getStaffId() + "",
					mTaskObject.getTaskRoadId());
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			this.progress.dismiss();
			Log.i("TAG", " Ket qua cap nhat cham soc " + result);
			if (errorCode.equals(Constant.INVALID_TOKEN2)
					&& description != null && !description.isEmpty()) {

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						description, getResources()
								.getString(R.string.app_name), moveLogInAct);
				dialog.show();
				return;
			} else {

			}
			if (Constant.SUCCESS_CODE.equals(result)) {
				DalPolicy dalPolicy = new DalPolicy(getActivity());
				dalPolicy.open();

				updateOk = dalPolicy.updateTaskRoadProgress(
						mTaskObject.getTaskRoadId(), mStaff.getStaffId() + "",
						2);
				if (updateOk) {
					mTaskObject.setProgress(2);
					// /////////////
					long tm = System.currentTimeMillis() + 3 * 60 * 1000;
					String newTime = DalPolicy.getDate(tm, LoginActivity
							.getInstance().getString(R.string.format_date_time)

					);
					String tmp[] = newTime.split(" ");
					String myDate = tmp[0];
					mTaskObject.setFinishedDateTask(myDate);
					// /////////////////
					mTaskObject.setPolicyProgress();
					xx();
					CommonActivity.createAlertDialog(getActivity(),
							R.string.policy_succeed, R.string.app_name).show();
				} else {

				}
				// mStaff.get
			}
		}

		private String sendRequestUpdate(String objectId, String taskRoadId) {
			try {
				// TODO Auto-generated method stub
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("wscode",
						"mbccs_updateTaskRoadProgress");
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// <token>?</token>
				input.addParam("token", Session.getToken());
				// <objectId>?</objectId>
				input.addParam("objectId", objectId);
				// <taskRoadId>?</taskRoadId>
				input.addParam("taskRoadId", taskRoadId);
				String envelope = input.buildInputGateway();
				Log.e("envlop policy", envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_updateTaskRoadProgress");
				Log.e("response update policy", response);
				CommonOutput output = input.parseGWResponse(response);
				String errorMessage = null;
				original = output.getOriginal();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				Log.e(tag, nl.getLength() + "...node list size");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.e("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.e("description", description);
					// nodechild = doc.getElementsByTagName("lstStockOrder");

				}
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					Log.e(tag, errorMessage);
					return Constant.ERROR_CODE;
				}
				// description = output.getOriginal();
				// Log.e(tag, original);
				return Constant.SUCCESS_CODE;
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}
	}// asyncUpdateSender

	private boolean updateOk;
	// final String[] ARR_STRING_VIDEO = new String[]{"vid_succeed",
	// "vid_failed"};
	final String[] ARR_STRING_AUDIO = new String[] { "au_succeed", "au_failed" };
	final String[] ARR_STRING_IMG = new String[] { "img_succeed", "img_failed", };
	private Button btnImg;
    private Button btnVideo;
	private boolean clickImg = false;

	private boolean clickVideo = false;
	private boolean created = false;
	private File dir;

	final int DOWNLOAD_FAILED = 1;

	final int DOWNLOAD_SUCCEED = 0;
	private final int FILE_TYPE_AUDIO = 1;

	private final int FILE_TYPE_IMG = 0;
	final int FILE_TYPE_VIDEO = 1;
	// File fileImg, fileAudio, fileVideo;

	private Location locationStaff;
	private Staff mStaff;
	private TaskObject_ mTaskObject;

	private final String nameFolderSd = Constant.nameSdFolderApps;

	private String nameImg;

	private String nameMedia;

	private String pathFolderSd;
	private final String tag = "frag policy detail";
	// TaskObject_ taskObject;
    private TextView txtName;
    private TextView txtContent;
    private TextView txtStatus;

	private String url;

	private float checkLocation() {
		// TODO Auto-generated method stub
		float distance = -1;
		// if (CommonActivity.checkGps(act)) {
		//
		// } else {
		//
		// }

		com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
				.findMyLocation(getActivity());

		if (!myLocation.getX().equals("0") && !myLocation.getY().equals("0")) {
			Location location = new Location("");
			location.setLatitude(Double.parseDouble(myLocation.getX()));
			location.setLongitude(Double.parseDouble(myLocation.getY()));
			if (mStaff.getX() != 0 && mStaff.getY() != 0) {
				Log.i("TAG", "staff location x = " + mStaff.getX() + "y = "
						+ mStaff.getY());
				locationStaff.setLatitude(mStaff.getX());
				locationStaff.setLongitude(mStaff.getY());

				distance = location.distanceTo(locationStaff);
			}
		} else {
			// CommonActivity.DoNotLocation(act);
		}
		Log.e(tag, distance + ".....distance");
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		// distance = 10;
		return distance;
	}

	private float checkLocation_tt() {
        // if (gps.canGetLocation()) {
		// Location myLocation = gps.getLocation();
		// if (x != 0 && y != 0) {
		// Log.i("TAG", "staff location x = " + x + "y = " + y);
		// locationStaff.setLatitude(x);
		// locationStaff.setLongitude(y);
		// if(myLocation != null){
		// distance = myLocation.distanceTo(locationStaff);
		// }
		//
		// }
		// } else {
		//
		// Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
		// getResources().getString(R.string.checkgps),
		// getResources().getString(R.string.updateWork));
		// dialog.show();
		// }
		// Log.e("check location", distance + "");
		return (float) 0;
	}

	private boolean createFolder() {
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

	// private File downloadFile(int fileType) {
	// // String fileName = initFilePath(fileType);
	// // // TODO Auto-generated method stub
	// // HttpClient httpClient = new DefaultHttpClient();
	// // url = Constant.LINK_WS_COM + Session.getToken() + "/"
	// // + mTaskObject.getTastId() + "/" + fileType;
	// // mTaskObject.getImageTask();
	// // mTaskObject.getVideoTask();
	// // Log.e(tag, url);
	// // if(checkUrl(url)){
	// // foo(fileType);
	// // }
	//
	// processFile_(fileType);
	// // processFileViaIntent(fileType);
	// // HttpGet httpGet = new HttpGet(url);
	// // try {
	// // HttpResponse res = httpClient.execute(httpGet);
	// // HttpEntity entity = res.getEntity();
	// // if (entity != null && entity.getContentLength() > 0) {
	// // Log.e(tag, entity.getContentLength() + "");
	// // InputStream is = entity.getContent();
	// // // return saveFile_(is, fileName);
	// // return saveFile(is, fileName);
	// // }
	// //
	// // } catch (ClientProtocolException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // } catch (IOException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// return null;
	// }

	private boolean checkSession(int fileType) {
		// TODO Auto-generated method stub

		url = Constant.LINK_WS_COM + Session.getToken() + "/"
				+ mTaskObject.getTastId() + "/" + fileType;
		// mTaskObject.getImageTask();
		// mTaskObject.getVideoTask();
		Log.e(tag, url);
		boolean ok = true;
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
		try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .build();


            Request request = new Request.Builder()
                    .url(Constant.LINK_WS_UPLOAD_IMAGE)
                    .post(requestBody)
                    .build();

            Response response = OkHttpUtils.getClient().newCall(request).execute();
            String result = response.body().string();
            Log.d("FragmentChannelUpdateImage", "response = " + result);


//			HttpResponse res = httpClient.execute(httpGet);
//			HttpEntity entity = res.getEntity();
//			float l = entity.getContentLength();
//			Log.e(tag, l + "");
//			if (entity != null && l > 0) {
//				org.apache.http.Header[] headers = res.getAllHeaders();
//				if (headers[headers.length - 1].getName().equalsIgnoreCase(
//						"connection")
//						&& headers[headers.length - 1].getValue()
//								.equalsIgnoreCase("close")) {
//					ok = false;
//				}
//			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}

	private void getInfoMedia() {
		// TODO Auto-generated method stub
		nameMedia = mTaskObject.getVideoTask();
		nameImg = mTaskObject.getImageTask();

	}

	private void getTask() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			// String timeCreateIdNo, birthday, idno;
			mTaskObject = (TaskObject_) mBundle
					.getSerializable(Define.KEY_TASK);
			mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
			Log.e(tag, mTaskObject.getAllContent());
			Log.e(tag,
					mTaskObject.getImageTask() + "----"
							+ mTaskObject.getVideoTask());
			getInfoMedia();
		}

	}

	private String initFilePath(int fileType) {
		// TODO Auto-generated method stub
		created = createFolder();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy");
		String date = df.format(Calendar.getInstance().getTime());

		return pathFolderSd + "/" + mStaff.getStaffId() + "_"
				+ mTaskObject.getTastId() + "_" + date
				+ ARR_EXT[fileType];
	}

	private void initLocation() {
		// TODO Auto-generated method stub
		locationStaff = new Location("Location Staff");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(tag, "on act create");
		getTask();
		setTitleActionBar(mStaff.getNameStaff());
		if (!CommonActivity.isNullOrEmpty(mStaff.getAddressStaff())) {
			setSubTitleActionBar(mStaff.getAddressStaff());
		}
		Log.e(tag, (fragmentViewImg == null) + "...fr img-----"
				+ (fragmentViewVideo == null) + "...fr video-----");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(tag, "on destroy");
		if (asyncTaskDownloadFile != null) {
			asyncTaskDownloadFile.cancel(false);

		}
		if (asyncTaskUpdateSender != null) {
			asyncTaskUpdateSender.cancel(false);

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e(tag, requestCode + ", " + (resultCode) + ", " + (data == null));
		if (clickImg || clickVideo) {
			Log.e(tag, " clicked");

		}
	}

	// android.os.Build.VERSION.SDK_INT
	// ham nay chi duoc goi khi anh duoc hien thi hoac video duoc play het
	private void updatePolicy() {
		// TODO Auto-generated method stub\
		int progress = mTaskObject.getProgress();
		Log.e(tag, progress + "=============================progress");
		// if (progress == 2) {
		// return;
		// }
		Log.e(tag, updateOk + "=============================update ok");
		// if (updateOk) {
		// return;
		// }

		String finDAte = mTaskObject.getFinishedDateTask();
		Log.e(tag, finDAte + "=============================finish date");
		if (
		// (finDAte == null || finDAte.isEmpty())||
		// !updateOk||

		progress != 2) {
			float dis = checkLocation();

			if (dis >= Constant.DISTANCE_VALID) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.distance_not_valid),

						getResources().getString(R.string.app_name)).show();
				return;
			} else if (dis < 0) {
				CommonActivity.createAlertDialog(act,
						R.string.cannot_get_location, R.string.app_name).show();
				return;
			}
			Log.e(tag, "start send update................");
			asyncTaskUpdateSender = new AsyncTaskUpdateSender(act);
			asyncTaskUpdateSender.execute(0);
		} else {
			// if (checkSession(FILE_TYPE_IMG)) {
			// CommonActivity
			// .createAlertDialog(getActivity(),
			// R.string.token_invalid, R.string.app_name,
			// moveLogInAct).show();
			// }
		}

	}

	private AsyncTaskUpdateSender asyncTaskUpdateSender;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (!CommonActivity.isNetworkConnected(act)) {// ok
			Log.e(tag, "net disconn");
			CommonActivity.createAlertDialog(act, R.string.errorNetwork,
					R.string.app_name).show();
			return;
		}
		// AsyncCheckSession asyncCheckSession = new AsyncCheckSession(act);
		if (actionbarClicked) {
			return;
		}

		float dis = checkLocation();
		if (dis >= Constant.DISTANCE_VALID) {
			CommonActivity.createAlertDialog(
					getActivity(),
					MessageFormat.format(
							getResources().getString(R.string.checkdistance),
							Constant.DISTANCE_VALID),
					getResources().getString(R.string.app_name)).show();
			return;
		} else if (dis < 0) {
			CommonActivity.createAlertDialog(act, R.string.cannot_get_location,
					R.string.app_name).show();
			return;
		}

		int id = arg0.getId();

		switch (id) {
		case R.id.btn_img:
			if (nameImg != null && nameImg.length() > 0) {
				Log.e(tag, "load img");
				asyncTaskDownloadFile = new AsyncTaskDownloader(act);
				asyncTaskDownloadFile.execute(FILE_TYPE_IMG);

				clickImg = true;
				clickVideo = false;
			} else {
				// no image
				Log.e(tag, "no img");
				warnNoFile(R.string.no_img);
			}

			break;

		case R.id.btn_video:
			if (nameMedia != null && nameMedia.length() > 0) {
				clickVideo = true;
				clickImg = false;
				asyncTaskDownloadFile = new AsyncTaskDownloader(act);
				// request len server chi dung file_type=1

				asyncTaskDownloadFile.execute(FILE_TYPE_AUDIO);

			} else {
				// no media
				Log.e(tag, "no media");
				warnNoFile(R.string.no_media);
			}

			break;
		default:

			break;
		}

	}

	private AsyncTaskDownloader asyncTaskDownloadFile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.e(tag, "on create view");
		Log.e(tag, (fragmentViewImg == null) + "...fr img-----"
				+ (fragmentViewVideo == null) + "...fr video-----");
		boolean ok = false;
		if (clickImg) {
			if (fragmentViewImg != null) {

				if (fragmentViewImg.getFinished()) {
					ok = true;
				}
			}

		}
		if (clickVideo) {
			if (fragmentViewVideo != null) {

				if (fragmentViewVideo.getFinished()) {
					ok = true;
				}
			}
		}
		getTask();
		Log.e(tag, ok + "------------------------------------ok");
		if (ok) {
			updatePolicy();
		}

		idLayout = R.layout.layout_policy_detail;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private void playMp3() {
		// TODO Auto-generated method stub
		MediaPlayer mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(act, Uri.parse(url));
			mPlayer.prepare();
			// mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
			// vol, 0);
			// mPlayer.set
			mPlayer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private FragmentViewImg fragmentViewImg;
	private FragmentViewVideo fragmentViewVideo;

	private void processFile(int fileType) {

		switch (fileType) {
		case FILE_TYPE_IMG:
			Log.e(tag, "img");
			// TODO Auto-generated method stub
			OjUri ojUri = new OjUri(url, nameImg);
			fragmentViewImg = new FragmentViewImg();
			// FragmentViewVideo frag = new FragmentViewVideo();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(Define.KEY_OJ_URI, ojUri);
			fragmentViewImg.setArguments(mBundle);
			ReplaceFragment.replaceFragment(act, fragmentViewImg, true);
			// act.runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			//
			//
			// }
			// });

			break;

		case FILE_TYPE_AUDIO:
			OjUri ojUri1 = new OjUri(url, nameMedia);
			// FragmentViewImg frag = new FragmentViewImg();
			fragmentViewVideo = new FragmentViewVideo();
			Bundle mBundle1 = new Bundle();
			mBundle1.putSerializable(Define.KEY_OJ_URI, ojUri1);
			fragmentViewVideo.setArguments(mBundle1);
			ReplaceFragment.replaceFragment(act, fragmentViewVideo, true);
			// act.runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			//
			// }
			// });
			break;
		default:
			break;
		}

	}

	private void processFile_(int fileType) {

		switch (fileType) {
		case FILE_TYPE_IMG:
			Log.e(tag, "img");
			act.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					OjUri ojUri = new OjUri(url, nameImg);
					fragmentViewImg = new FragmentViewImg();
					// FragmentViewVideo frag = new FragmentViewVideo();
					Bundle mBundle = new Bundle();
					mBundle.putSerializable(Define.KEY_OJ_URI, ojUri);
					fragmentViewImg.setArguments(mBundle);
					ReplaceFragment.replaceFragment(act, fragmentViewImg, true);

				}
			});

			break;

		case FILE_TYPE_AUDIO:
			act.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					OjUri ojUri = new OjUri(url, nameMedia);
					// FragmentViewImg frag = new FragmentViewImg();
					fragmentViewVideo = new FragmentViewVideo();
					Bundle mBundle = new Bundle();
					mBundle.putSerializable(Define.KEY_OJ_URI, ojUri);
					fragmentViewVideo.setArguments(mBundle);
					ReplaceFragment.replaceFragment(act, fragmentViewVideo,
							true);
				}
			});
			break;
		default:
			break;
		}

	}// dang dung

	private void processFileViaIntent(int fileType) {
		// toast("use intent");
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		switch (fileType) {
		case FILE_TYPE_IMG:
			Log.e(tag, "img");

			intent.setDataAndType(Uri.parse(url), "image/jpeg");
			// intent.putExtra(Intent.EXTRA_TITLE, nameImg);
			// startActivityForResult(intent, fileType);
			// intent.setDataAndType(Uri.parse(url), "image/png");

			break;
		// case FILE_TYPE_VIDEO :
		case FILE_TYPE_AUDIO:
			if (nameMedia.endsWith(".mp3")) {
				Log.e(tag, "audio");
				intent.setDataAndType(Uri.parse(url), "audio/mp3");
			} else {
				Log.e(tag, "video");
				intent.setDataAndType(Uri.parse(url), "video/mp4");
			}
			// intent.putExtra(Intent.EXTRA_TITLE, nameMedia);
			// playMp3();
			break;

		// case FILE_TYPE_VIDEO :
		// Log.e(tag, "video");
		// intent.setDataAndType(Uri.parse(url), "video/mp4");
		// break;
		default:
			break;
		}
		// updateDb();
		startActivityForResult(intent, fileType);
		// startActivity(intent);
	}

	private File saveFile(InputStream is, String fileName) {
		// TODO Auto-generated method stub
		try {
			// Log.e(tag, is.available() + "");
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int read;
			while ((read = bis.read()) != -1) {
				// is.read(buffer, 0, byteCount);
				Log.e(tag, read + "");
				bos.write(read);
			}
			bos.flush();
			bos.close();
			bis.close();
			return file;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private File saveFile_(InputStream is, String fileName) {
		// TODO Auto-generated method stub
		try {
			// Log.e(tag, is.available() + "");
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);

			int byteCount = 1024;
			byte buffer[] = new byte[byteCount];
			int read;
			while ((read = is.read()) != -1) {
				// is.read(buffer, 0, byteCount);
				Log.e(tag, read + "");
				fos.write(read);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// private String startDownloadFile(int fileType) {
	// // TODO Auto-generated method stub
	// boolean ok;
	// switch (fileType) {
	// case FILE_TYPE_IMG :
	// ok = checkSession(fileType);
	// if (fileImg != null) {
	// return ARR_STRING_IMG[DOWNLOAD_SUCCEED];
	// } else {
	// return ARR_STRING_IMG[DOWNLOAD_FAILED];
	// }
	// // break;
	//
	// case FILE_TYPE_AUDIO :
	// fileAudio = downloadFile(fileType);
	// if (fileAudio != null) {
	// return ARR_STRING_AUDIO[DOWNLOAD_SUCCEED];
	// } else {
	// return ARR_STRING_AUDIO[DOWNLOAD_FAILED];
	// }
	//
	// // case FILE_TYPE_VIDEO :
	// // fileAudio = downloadFile(fileType);
	// // if (fileAudio != null) {
	// // return ARR_STRING_VIDEO[DOWNLOAD_SUCCEED];
	// // } else {
	// // return ARR_STRING_VIDEO[DOWNLOAD_FAILED];
	// // }
	// default :
	// break;
	// }
	// return null;
	// }

	private void xx() {
		// TODO Auto-generated method stub
		if (mTaskObject.getColorId() != 0) {
			txtStatus.setTextColor(LoginActivity.getInstance().getResources()
					.getColor(mTaskObject.getColorId()));
		}
		txtStatus.setTypeface(null, Typeface.ITALIC);
		txtStatus.setText(mTaskObject.getProgressText());
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		txtName = (TextView) v.findViewById(R.id.txt_policy_name);
		txtStatus = (TextView) v.findViewById(R.id.txt_policy_status);
		txtContent = (TextView) v.findViewById(R.id.txt_policy_content);
		btnImg = (Button) v.findViewById(R.id.btn_img);
		btnVideo = (Button) v.findViewById(R.id.btn_video);
		txtName.setText(mTaskObject.getNameTask());
		xx();
		txtContent.setText(mTaskObject.getContentTask());
		if (mTaskObject != null) {
			if (nameMedia == null || nameMedia.isEmpty()) {
				// btnVideo.setVisibility(View.GONE);
				LinearLayout linearLayout = (LinearLayout) v
						.findViewById(R.id.layout_btn_img);
				linearLayout.setVisibility(View.GONE);
			}
			if (nameImg == null || nameImg.isEmpty()) {
				// btnImg.setVisibility(View.GONE);
				LinearLayout linearLayout = (LinearLayout) v
						.findViewById(R.id.layout_btn_video);
				linearLayout.setVisibility(View.GONE);
			}
		}
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		// btnVideo.setVisibility(View.GONE);
		// LinearLayout linearLayout = (LinearLayout) v
		// .findViewById(R.id.layout_btn_video);
		// linearLayout.setVisibility(View.GONE);

		btnImg.setOnClickListener(this);
		btnVideo.setOnClickListener(this);
		initLocation();
	}

	private void warnNoFile(int msgResId) {
		CommonActivity.createAlertDialog(getActivity(), msgResId,
				R.string.app_name).show();
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

	// private void warnNoFile(String msg) {
	// TODO Auto-generated method stub
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// getActivity());
	//
	// // set title
	// alertDialogBuilder.setTitle(R.string.app_name);
	//
	// alertDialogBuilder.setMessage(msg)
	// // .setCancelable(false)
	// .setPositiveButton(R.string.buttonOk,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // if this button is clicked, close
	// // current activity
	//
	// }
	// });
	//
	// // show it
	// alertDialogBuilder.show();
	// }

	// move login

}
