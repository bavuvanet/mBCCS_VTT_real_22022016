package com.viettel.bss.viettelpos.v4.customer.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.OmichanelConnectMobileFragment;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

public class AsyncTaskUpdateImageOfline extends AsyncTask<Void, Void, ParseOuput> {

	private Context mActivity;
	private ArrayList<FileObj> listFileUploadImage;
	private OnClickListener onClick;
	XmlDomParse parse = new XmlDomParse();
	String errorCode = "";
	String description = "";
	ProgressDialog progress;
	String isdn;
	String dbType;
	String noticeSucess;

	public AsyncTaskUpdateImageOfline(Context mActivity,
									  ArrayList<FileObj> listFileUploadImage,
									  OnClickListener onClick,
									  String noticeSucess) {

		this.mActivity = mActivity;

		this.listFileUploadImage = listFileUploadImage;
		this.progress = new ProgressDialog(mActivity);
		this.progress.setCancelable(false);
		this.progress.setMessage(mActivity.getResources().getString(
				R.string.progress_uploadImageOffline));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
		this.description = noticeSucess;
		this.noticeSucess = new String(noticeSucess);
		this.onClick = onClick;
	}

	public AsyncTaskUpdateImageOfline(Context mActivity,
									  ArrayList<FileObj> listFileUploadImage, OnClickListener onClick,
									  String description, String mIsdn, String dbType) {
		this.mActivity = mActivity;
		this.listFileUploadImage = listFileUploadImage;
		this.progress = new ProgressDialog(mActivity);
		this.progress.setCancelable(false);
		if (!"10".equals(dbType)) {
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.progress_uploadImageOffline));
		} else {
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.progress_uploadChungTu));
		}
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
		this.description = description;
		this.noticeSucess = new String(description);
		this.onClick = onClick;
		this.isdn = mIsdn;
		this.dbType = dbType;
	}

	@Override
	protected ParseOuput doInBackground(Void... params) {
		return doUploadLstImage(listFileUploadImage);
	}

	@Override
	protected void onPostExecute(ParseOuput result) {
		super.onPostExecute(result);
		progress.dismiss();
		errorCode = result.getErrorCode();
		description = result.getDescription();
		if ("0".equals(result.getErrorCode())) {
			if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
				description = noticeSucess;
			}
			Log.d("descriptionnnnnnnnnnnnnnnnnnnnnn", description);

			if (listFileUploadImage != null && listFileUploadImage.size() > 0) {
				for (FileObj fileObj : listFileUploadImage) {
					File tmp = new File(fileObj.getPath());
					tmp.delete();
				}
			}

			if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
				String des = mActivity
						.getString(R.string.upload_all_profile_success);
				if(ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
					des = mActivity
							.getString(R.string.upload_all_profile_change_success);
				}
				if(OmichanelConnectMobileFragment.instance != null){
					CommonActivity.createAlertDialog(mActivity,description
							+ des,mActivity.getString(R.string.app_name)).show();

				}else{
					CommonActivity
							.createDialog(
									(Activity) mActivity,
									description
											+ des
											+ "\n"
											+ mActivity
											.getString(R.string.messagetichvas),
									mActivity.getString(R.string.app_name),
									mActivity.getString(R.string.OK),
									mActivity.getString(R.string.cancel),
									onclickIsdn, onClick).show();
				}

			} else if ("10".equals(dbType)) {
				CommonActivity.createAlertDialog((Activity) mActivity,
						mActivity.getString(R.string.modifyProfileSuccess),
						mActivity.getString(R.string.app_name), onClick).show();
			} else {
				String des = mActivity
						.getString(R.string.upload_all_profile_success);
				if(ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
					des = mActivity
							.getString(R.string.upload_all_profile_change_success);
				}
				CommonActivity
						.createAlertDialog(
								(Activity) mActivity,
								description
										+ des,
								mActivity.getString(R.string.app_name), onClick)
						.show();
			}

		} else {

			if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
				description = noticeSucess;
			}
			if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
				String des = mActivity
						.getString(R.string.upload_all_profile_success);
				if(ActivityCreateNewRequestMobileNew.instance != null
						&& !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
					des = mActivity
							.getString(R.string.upload_all_profile_change_success);
				}

				if (!CommonActivity.isNullOrEmpty(listFileUploadImage)) {

					for (FileObj fileObj2 : listFileUploadImage) {
						fileObj2.setStatus(0);
						FileUtils.insertFileBackUpToDataBase(fileObj2,
								mActivity.getApplicationContext());
					}

				}

				if(OmichanelConnectMobileFragment.instance != null){
					CommonActivity.createAlertDialog(mActivity,description
							+ des,mActivity.getString(R.string.app_name)).show();

				}else{
					CommonActivity
							.createDialog(
									(Activity) mActivity,
									description
											+ des
											+ "\n"
											+ mActivity
											.getString(R.string.messagetichvas),
									mActivity.getString(R.string.app_name),
									mActivity.getString(R.string.OK),
									mActivity.getString(R.string.cancel),
									onclickIsdn, onClick).show();
				}
			} else if ("10".equals(dbType)) {

				CommonActivity.createAlertDialog(
						(Activity) mActivity,
						mActivity.getString(
								R.string.modify_profile_fail_try_again, result,
								listFileUploadImage != null ? listFileUploadImage.size() : 0) ,
						mActivity.getString(R.string.app_name), onClick).show();
				if (listFileUploadImage != null
						&& listFileUploadImage.size() > 0) {
					for (FileObj fileObj : listFileUploadImage) {
						File tmp = new File(fileObj.getPath());
						tmp.delete();
					}
				}

			} else {
				String des = mActivity
						.getString(R.string.upload_profile_fail_try_again);
				if(ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
					des = mActivity
							.getString(R.string.upload_profile_change_fail_try_again);
				}
				if (!CommonActivity.isNullOrEmpty(listFileUploadImage)) {

					for (FileObj fileObj2 : listFileUploadImage) {
						fileObj2.setStatus(0);
						FileUtils.insertFileBackUpToDataBase(fileObj2,
								mActivity.getApplicationContext());
					}

				}
				CommonActivity
						.createAlertDialog(
								(Activity) mActivity,
								description + "\n"
										+ des,
								mActivity.getString(R.string.app_name), onClick)
						.show();
			}

		}
	}

	OnClickListener onclickIsdn = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(mActivity.getApplicationContext(),
					FragmentRegisterServiceVas.class);
			intent.putExtra("isdnKey", isdn);
			mActivity.startActivity(intent);

		}
	};

	private ParseOuput doUploadLstImage(List<FileObj> arrayFileObject) {
		ParseOuput parseOuput = null;
		String original = "";
		com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
				.findMyLocationGPS(mActivity, "uploadImageLstOffline");
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_uploadImageLstOffline");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:uploadImageLstOffline>");
			rawData.append("<input>");

			rawData.append("<token>" + Session.token + "</token>");
			if (myLocation != null && !"0".equals(myLocation.getX())
					&& !"0".equals(myLocation.getY())) {
				rawData.append("<xUpdate>" + myLocation.getX());
				rawData.append("</xUpdate>");
				rawData.append("<yUpdate>" + myLocation.getY());
				rawData.append("</yUpdate>");
			}
			if (!CommonActivity.isNullOrEmptyArray(arrayFileObject)) {

				for (FileObj fileObj : arrayFileObject) {

					if(fileObj.isUseOldProfile()){
						rawData.append("<lstUploadImage>");

						rawData.append("<id>" + fileObj.getId()
								+ "</id>");

						if (fileObj.getRecordId() != null
								&& fileObj.getRecordId() > 0) {
							rawData.append("<recordId>" + fileObj.getRecordId()
									+ "</recordId>");
						}
						rawData.append("<isdn>" + fileObj.getIsdn() + "</isdn>");
						rawData.append("<recordCode>" + fileObj.getCodeSpinner()
								+ "</recordCode>");
						rawData.append("<actionCode>" + fileObj.getActionCode()
								+ "</actionCode>");
						rawData.append("<reasonId>" + fileObj.getReasonId()
								+ "</reasonId>");
						rawData.append("<actionAudit>" + fileObj.getActionAudit()
								+ "</actionAudit>");
						rawData.append("<pageIndex>" + fileObj.getPageIndex()
								+ "</pageIndex>");
						rawData.append("</lstUploadImage>");
					} else {
						File isdnZip = new File(fileObj.getPath());
						byte[] buffer = FileUtils.fileToBytes(isdnZip);
						String base64 = Base64.encodeToString(buffer,
								Activity.TRIM_MEMORY_BACKGROUND);

						rawData.append("<lstUploadImage>");

						rawData.append("<id>" + fileObj.getId()
								+ "</id>");

						if (fileObj.getRecordId() != null
								&& fileObj.getRecordId() > 0) {
							rawData.append("<recordId>" + fileObj.getRecordId()
									+ "</recordId>");
						}

						rawData.append("<fileContent>" + base64 + "</fileContent>");
						rawData.append("<isdn>" + fileObj.getIsdn() + "</isdn>");
						rawData.append("<recordCode>" + fileObj.getCodeSpinner()
								+ "</recordCode>");
						rawData.append("<actionCode>" + fileObj.getActionCode()
								+ "</actionCode>");
						rawData.append("<reasonId>" + fileObj.getReasonId()
								+ "</reasonId>");
						rawData.append("<actionAudit>" + fileObj.getActionAudit()
								+ "</actionAudit>");
						rawData.append("<pageIndex>" + fileObj.getPageIndex()
								+ "</pageIndex>");
						rawData.append("</lstUploadImage>");
					}
				}

			}
			rawData.append("<type>" + "online" + "</type>");
			rawData.append("</input>");
			rawData.append("</ws:uploadImageLstOffline>");
			// Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			// Log.d("uploadImageOffline Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_uploadImageLstOffline");
			// Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();

			// parser
			Serializer serializer = new Persister();
			parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput == null
					|| CommonActivity.isNullOrEmpty(parseOuput.getErrorCode())) {
				parseOuput = new ParseOuput();
				parseOuput.setDescription(mActivity
						.getString(R.string.no_return_from_system));
				parseOuput.setErrorCode(Constant.ERROR_CODE);
				return parseOuput;
			} else {
				return parseOuput;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, "AsyncTaskUploadImageOffline doUploadImage "
					+ e.toString(), e);
			parseOuput = new ParseOuput();
			parseOuput.setDescription(e.getMessage());
			parseOuput.setErrorCode(Constant.ERROR_CODE);

		}
		return parseOuput;
	}
}