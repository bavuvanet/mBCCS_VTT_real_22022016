package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.savelog.SaveLog;

public class UpdateVersionAsyn extends AsyncTask<String, Void, String> {

	private String urlinstall = "";

	private final ProgressDialog progress;
	private Context context = null;
	private String url;
	public UpdateVersionAsyn(Context context,String url) {
		this.context = context;
		this.url = url;
		this.progress = new ProgressDialog(this.context);
		this.progress.setCancelable(false);
		// check font

		this.progress.setMessage(context.getResources().getString(
				R.string.updatingversion));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected String doInBackground(String... params) {
		return UpdateVersion();
	}

	@Override
	protected void onPostExecute(String result) {
		if (result.equalsIgnoreCase(Constant.SUCCESS_CODE)) {
			progress.dismiss();
			if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(urlinstall)),
						"application/vnd.android.package-archive");
//			intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(urlinstall)),
//					"application/vnd.android.package-archive");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else {
				Log.d(Constant.TAG, "fileApk path = " + urlinstall);
				File fileApk = new File(urlinstall);
				Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", fileApk);
				Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
				intent.setDataAndType(apkUri,
						"application/vnd.android.package-archive");;
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}

		} else {
			progress.dismiss();
			Dialog dialog = CommonActivity.createAlertDialog(
					(Activity) context,
					context.getResources()
							.getString(R.string.updateversionfail), context
							.getResources().getString(R.string.updateversion));
			dialog.show();

		}
	}

	private String UpdateVersion() {
		String result = "";
//		String url = Constant.PATH_UPDATE_VERSION + token;
		try {
			URL urlcontrol = new URL(url);
			Log.e("URL getVersion:",url);

			Log.i("Bo nho con trong", Double.toString(SdCardHelper
					.getAvailableInternalMemorySize()));

			double availAbleMemory = SdCardHelper
					.getAvailableInternalMemorySize();

			if (availAbleMemory > 50) {

				InputStream input = new BufferedInputStream(
						urlcontrol.openStream());
				OutputStream output;

				File file = new File(Constant.MBCCS_TEMP_FOLDER);
				if (!file.exists()) {
					file.mkdirs();
				}

				output = new FileOutputStream(Constant.MBCCS_TEMP_FOLDER
						+ "mBCCS.apk");
				urlinstall = Constant.MBCCS_TEMP_FOLDER + "mBCCS.apk";
				Log.d("urlinstall", urlinstall);
				byte data[] = new byte[1024];
				int count = 0;

				while ((count = input.read(data)) != -1) {
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();

				Log.e("FilePath", urlinstall);

				Log.e("UPDATE VERSION", "End Download >>>>>>>>>>>>>>>> ");
				result = Constant.SUCCESS_CODE;
			} else {
				result = Constant.ERROR_CODE;
			}
		} catch (Exception e) {
			try{
				SaveLog saveLog = new SaveLog(context,
						Constant.SYSTEM_NAME, Session.userName,
						"upgradeversion_exception", CommonActivity
						.findMyLocation(context).getX(),
						CommonActivity.findMyLocation(context)
								.getY());
				saveLog.saveLogException(e , new Date(),new Date(),Session.userName + "_"
						+ CommonActivity.getDeviceId(context) + "_"
						+ System.currentTimeMillis());
			}catch (Exception ex){

			}
		}
		return result;
	}

}
