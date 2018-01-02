package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.FormBean;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.TabThongTinHopDong;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.savelog.SaveLog;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ConnectSubAsyn extends AsyncTask<String, String, Integer> {

	private ProgressDialog progress;
	private Activity context = null;
	private ResultSurveyOnlineForBccs2Form surveyOnline;
	private List<SubscriberDTO> lstSub;
	private AreaObj area;
	private Date timeEnd = null;
	private int index = 0;
	private View btnConnectSub;
	private String oldSubId = "";
	private String actionCode = "";
	public ConnectSubAsyn(Activity context, List<SubscriberDTO> lstSub,
			ResultSurveyOnlineForBccs2Form surveyOnline, AreaObj area , View btnConnectSub) {
		this.lstSub = lstSub;
		this.area = area;
		this.context = context;
		this.surveyOnline = surveyOnline;
		this.progress = new ProgressDialog(this.context);
		// check font
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getString(R.string.connecting_sub,
				lstSub.get(index).getIsdn()));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
		this.btnConnectSub = btnConnectSub;
	}



	@Override
	protected void onProgressUpdate(String... values) {
		this.progress.setMessage(context.getString(R.string.connecting_sub,
				values[0]));
	}

	@Override
	protected Integer doInBackground(String... params) {

		for (SubscriberDTO sub : lstSub) {
			publishProgress(sub.getIsdn());
			// Tao ten file

			File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
			if (!folder.exists()) {
				folder.mkdir();
			}
			Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());

			// Neu co chon ho so thi se dinh kem
			for (ProfileUpload profile : sub.getLstProfile()) {
				if (CommonActivity.isNullOrEmpty(profile.getParentId())) {
					String ext = "";
					if (profile.getLstFile().size() == 1) {
						ext = ".jpg";
					} else {
						ext = ".zip";
					}
					String zipFilePath = folder.getPath() + File.separator
							+ new Date().getTime() + "_"
							+ profile.getSelectProfile().getCode() + ext;
					profile.setZipFilePath(zipFilePath);
				}
			}
			SaleOutput out = doConnectSub(sub);
			sub.setErrorCode(out.getErrorCode());
			sub.setDescription(out.getDescription());

			if (!"0".equals(out.getErrorCode()) && index == 0) {
				return 0;
			}
			if ("0".equals(out.getErrorCode())) {
				sub.setActionAuditId(out.getDescription());
			}
			index++;
		}
		return 1;
	}

	@Override
	protected void onPostExecute(Integer result) {
		progress.dismiss();
		if (result == 0) {
			CommonActivity.createAlertDialog(
					context,
					context.getString(R.string.connect_sub_fail, lstSub.get(0)
							.getIsdn(), lstSub.get(0).getDescription()),
					context.getString(R.string.app_name)).show();
			return;
		}

		Long subIdEnd = lstSub.get(0).getSubId();
		for (SubscriberDTO sub : lstSub) {
			if ("0".equals(sub.getErrorCode())) {
				subIdEnd = sub.getSubId();
			}
		}

		for (SubscriberDTO sub : lstSub) {
			if ("0".equals(sub.getErrorCode())) {
				btnConnectSub.setVisibility(View.GONE);
				boolean isEnd = sub.getSubId() == subIdEnd;
				if (!CommonActivity.isNullOrEmpty(sub.getLstProfile())) {
					AsynZipFile zip = new AsynZipFile(context, sub, isEnd);
					zip.execute();
				}

			}
		}
	}

	// =====method get list service ========================
	public SaleOutput doConnectSub(SubscriberDTO sub) {
		// SaleOutput out = new SaleOutput();
		// out.setErrorCode("0");
		// sub.setActionAuditId(System.currentTimeMillis() + "");
		// if ("0".equals(out.getErrorCode())) {
		// return out;
		// }
		String original = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_doConnectSub");
			StringBuilder rawData = new StringBuilder();

			rawData.append("<ws:doConnectSub>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));

			rawData.append(lstSub.get(index).buildSubXml(context,
					"subscriberDTO", surveyOnline,
					TabThongTinHopDong.accountDTOMain,
					TabThueBaoHopDongManager.instance.custIdentityDTO, area, oldSubId, actionCode).replace(">null<","><"));
			for (ProfileUpload item : sub.getLstProfile()) {
				FormBean bean = item.getSelectProfile();
				if (!CommonActivity.isNullOrEmpty(item.getParentId())) {
					for (ProfileUpload main : lstSub.get(0).getLstProfile()) {
						if (main.getId().equals(item.getParentId())) {
							bean = main.getSelectProfile();
							break;
						}
					}
				}
				rawData.append("<lstFormBean>");
				rawData.append("<code>");
				rawData.append(bean.getCode());
				rawData.append("</code>");
				rawData.append("<name>");
				rawData.append(bean.getName());
				rawData.append("</name>");
				rawData.append("<typeId>");
				rawData.append(bean.getTypeId());
				rawData.append("</typeId>");
				rawData.append("<filePath>");
				rawData.append(item.getZipFilePath());
				rawData.append("</filePath>");
				rawData.append("</lstFormBean>");
			}
			rawData.append("<actionAuditIdRef>");
			rawData.append(lstSub.get(0).getActionAuditId());
			rawData.append("</actionAuditIdRef>");
			rawData.append("</input>");
			rawData.append("</ws:doConnectSub>");

			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_doConnectSub");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee", original);

			Serializer serializer = new Persister();
			SaleOutput parseOuput = serializer.read(SaleOutput.class, original);
			if (parseOuput == null) {
				parseOuput = new SaleOutput();
				parseOuput.setDescription(context
						.getString(R.string.no_return_from_system));
				parseOuput.setErrorCode(Constant.ERROR_CODE);
				return parseOuput;
			} else {
				return parseOuput;
			}

		} catch (Exception e) {
			Log.e("exception", "Exception", e);
			// errorMessage = getResources().getString(R.string.exception)
			// + e.toString();
			SaleOutput parseOuput = new SaleOutput();
			parseOuput.setErrorCode(Constant.ERROR_CODE);
			parseOuput.setDescription(context.getString(R.string.exception));
			return parseOuput;
		}
	}

	private class AsynZipFile extends AsyncTask<String, Void, Void> {

		private Context mContext;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";
		private SubscriberDTO sub;
		Date timeStartZipFile = new Date();
		private boolean isEndSub = false;

		public AsynZipFile(Context context, SubscriberDTO sub, boolean isEndSub) {
			this.isEndSub = isEndSub;
			this.sub = sub;
			this.mContext = context;
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setMessage(mContext.getString(R.string.zip_file,
					sub.getIsdn()));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... arg0) {
			FileOutputStream out = null;
			try {
				File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
				if (!folder.exists()) {
					folder.mkdir();
				}
				for (ProfileUpload profile : sub.getLstProfile()) {
					if (CommonActivity.isNullOrEmpty(profile.getLstFile())) {
						continue;
					}

					List<File> lstFileSource = profile.getLstFile();
					if (lstFileSource.size() > 1) {
						List<File> lstFileDes = new ArrayList<File>();
						for (File file : lstFileSource) {

							Bitmap bitmap = BitmapFactory.decodeFile(file
									.getPath());

							Bitmap bitmapImage = CommonActivity
									.getResizedBitmap(bitmap,
											Constant.SIZE_IMAGE_SCALE);
							File fileDes = new File(folder.getPath()
									+ File.separator + "_"
									+ new Date().getTime()
									+ profile.getSelectProfile().getCode()
									+ ".jpg");

							out = new FileOutputStream(fileDes);
							bitmapImage.compress(Bitmap.CompressFormat.PNG,
									100, out);
							out.close();
							lstFileDes.add(fileDes);
						}
						String zipFilePath = profile.getZipFilePath();
						FileUtils.zip(lstFileDes, zipFilePath);
						for (File file : lstFileDes) {
							file.delete();
						}
					} else {
						Bitmap bitmap = BitmapFactory.decodeFile(lstFileSource
								.get(0).getPath());

						Bitmap bitmapImage = CommonActivity.getResizedBitmap(
								bitmap, Constant.SIZE_IMAGE_SCALE);
						File fileDes = new File(profile.getZipFilePath());
						out = new FileOutputStream(fileDes);
						bitmapImage.compress(Bitmap.CompressFormat.PNG, 100,
								out);
						out.close();
					}

				}
				errorCode = "0";
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				try {
					SaveLog saveLog = new SaveLog(mContext,
							Constant.SYSTEM_NAME, Session.userName,
							"mbccs_connectSub_zipFile", CommonActivity
									.findMyLocation(mContext).getX(),
							CommonActivity.findMyLocation(mContext).getY());
					timeEnd = new Date();
					saveLog.saveLogRequest(
							errorCode,
							timeStartZipFile,
							timeEnd,
							Session.userName + "_"
									+ CommonActivity.getDeviceId(mContext)
									+ "_" + System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}

				UploadFile up = new UploadFile(context, sub, isEndSub);
				up.execute();

			} else {
				CommonActivity.createAlertDialog(context, description,
						context.getString(R.string.app_name)).show();
			}

		}
	}

	private class UploadFile extends AsyncTask<String, Void, Void> {

		private Context mContext;
		private ProgressDialog progress;
		private String errorCode = "";
		private SubscriberDTO sub;
		private Boolean isEnd = false;
		Date timeStartZipFile = new Date();
		XmlDomParse parse = new XmlDomParse();

		public UploadFile(Context context, SubscriberDTO sub, Boolean isEnd) {
			this.isEnd = isEnd;
			this.sub = sub;
			this.mContext = context;
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setMessage(mContext.getString(
					R.string.uploading_profile, sub.getIsdn()));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... arg0) {
			FileOutputStream out = null;

			try {
				for (ProfileUpload profile : sub.getLstProfile()) {
					if (CommonActivity.isNullOrEmpty(profile.getLstFile())) {
						break;
					}
					FileObj file = new FileObj();
					file.setIsdn(sub.getIsdn());
					file.setActionAudit(sub.getActionAuditId());
					file.setActionCode("00");
					file.setCodeSpinner(profile.getSelectProfile().getCode());
					file.setFile(new File(profile.getZipFilePath()));
					String pageIndex = "0";

					if (profile.getZipFilePath().endsWith(".zip")) {
						pageIndex = "1";
					}
					file.setPageIndex(pageIndex);
					file.setName(profile.getSelectProfile().getName());
					file.setPath(profile.getZipFilePath());
					file.setReasonId(sub.getHthm().getReasonId());
					file.setRecodeName(profile.getSelectProfile().getName());
					file.setRecordTypeId(profile.getSelectProfile().getTypeId()
							+ "");
					byte[] buffer = FileUtils.fileToBytes(file.getFile());
					if (buffer == null || buffer.length == 0) {
						continue;
					}
					String base64 = Base64.encodeToString(buffer,
							Activity.TRIM_MEMORY_BACKGROUND);
					String error = doUploadImage(file, base64);
					if (error.equals("0")) {
						file.getFile().delete();
					} else {
						file.setStatus(0);
						FileUtils.insertFileBackUpToDataBase(file, mContext);
					}
				}

			} catch (Exception e) {
				errorCode = "1";
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				try {
					SaveLog saveLog = new SaveLog(mContext,
							Constant.SYSTEM_NAME, Session.userName,
							"mbccs_connectSub_zipFile", CommonActivity
									.findMyLocation(mContext).getX(),
							CommonActivity.findMyLocation(mContext).getY());

					timeEnd = new Date();
					saveLog.saveLogRequest(
							errorCode,
							timeStartZipFile,
							timeEnd,
							Session.userName + "_"
									+ CommonActivity.getDeviceId(mContext)
									+ "_" + System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (isEnd) {
				String success = "";
				String fail = "";
				for (SubscriberDTO sub : lstSub) {
					if ("0".equals(sub.getErrorCode())) {
						success = success + sub.getIsdn() + ";";
					} else {
						fail = fail
								+ context.getString(R.string.connect_sub_fail,
										sub.getIsdn(), sub.getDescription())
								+ "\n";
					}
				}
				View.OnClickListener onclickBack = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((Activity) context).setResult(Activity.RESULT_OK);
						((Activity) context).finish();
					}
				};

				CommonActivity
						.createAlertDialog(
								context,
								context.getString(R.string.connect_sub_success,
										success)
										+ fail
										+ "\n"
										+ context
												.getString(R.string.profile_is_processing),
								"1",onclickBack).show();
			}
		}

		private String doUploadImage(FileObj fileObject, String fileContent) {
			Log.d("Log",
					"doUploadImage upload image to server "
							+ fileObject.getPath() + " filecontent:  "
							+ fileContent.length() + " "
							+ fileObject.toString());

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_uploadImageOffline");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:uploadImageOffline>");
				rawData.append("<input>");
				rawData.append("<token>"
						+ com.viettel.bss.viettelpos.v4.commons.Session
								.getToken() + "</token>");
				rawData.append("<isdn>" + fileObject.getIsdn() + "</isdn>");
				rawData.append("<recordCode>" + fileObject.getCodeSpinner()
						+ "</recordCode>");
				rawData.append("<actionCode>" + fileObject.getActionCode()
						+ "</actionCode>");
				rawData.append("<reasonId>" + fileObject.getReasonId()
						+ "</reasonId>");
				rawData.append("<actionAudit>" + fileObject.getActionAudit()
						+ "</actionAudit>");
				rawData.append("<pageIndex>" + fileObject.getPageIndex()
						+ "</pageIndex>");
				rawData.append("<status>" + fileObject.getStatus()
						+ "</status>");
				rawData.append("<filePath>" + fileObject.getPath()
						+ "</filePath>");
				rawData.append("<fileLength>" + fileObject.getFileLength()
						+ "</fileLength>");
				rawData.append("<recordTypeId>" + fileObject.getRecordTypeId()
						+ "</recordTypeId>");
				rawData.append("<fileLengthOrigin>"
						+ fileObject.getFileLengthOrigin()
						+ "</fileLengthOrigin>");
				rawData.append("<fileContent>" + fileContent + "</fileContent>");
				rawData.append("</input>");
				rawData.append("</ws:uploadImageOffline>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("uploadImageOffline Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, mContext,
						"mbccs_uploadImageOffline", 10000, 30000);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("uploadImageOffline Responseeeeeeeeee Original ",
						response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d(Constant.TAG, errorCode);
				}
				return errorCode;
			} catch (Exception e) {
				Log.e("Log", "Upload fail ", e);
			}
			return "1";
		}
	}
}
