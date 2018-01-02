package com.viettel.bss.viettelpos.v4.customer.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.dal.DatabaseManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.savelog.SaveLog;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

public class UploadImageIntentService extends IntentService {
	
	private final XmlDomParse parse = new XmlDomParse();
	private String errorCode = "";
	private String description = "";
	private DatabaseManager databaseManager = null;

	private static final String DATABASE_NAME = "mbccs.db";
	private com.viettel.bss.viettelpos.v4.object.Location myLocation;

	public UploadImageIntentService() {
		super(
				"com.viettel.bss.viettelpos.v4.customer.manage.UploadImageIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		myLocation = CommonActivity.findMyLocationGPS(
				UploadImageIntentService.this, "insertImageTools");
		String numberFile = "";
		try {
			ApParamDAL dal = new ApParamDAL(UploadImageIntentService.this);
			dal.open();
			numberFile = dal.getValue("UPLOAD_IMAGE_OFFLINE",
					"UPLOAD_IMAGE_OFFLINE");
			dal.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			numberFile = "4";
		}
		if (CommonActivity.isNullOrEmpty(numberFile)) {
			numberFile = "4";
		}

		Log.e("LOG intent", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

		if (intent != null) {
			String userName = "";
			SharedPreferences preferences = getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);

			if (preferences != null) {
				userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
			}
			try {
				File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
				if (!folder.exists()) {
					folder.mkdir();
				}

				String tracking = "2";
				SaveLog saveLog;
				saveLog = new SaveLog(this, Constant.SYSTEM_NAME, userName,
						"uploadImageLstOffline", CommonActivity
								.findMyLocation(this).getX(), CommonActivity
								.findMyLocation(this).getY());
				Boolean KPI_DETAIL = "2".equalsIgnoreCase(tracking);
				File database = getApplicationContext().getDatabasePath(
						DATABASE_NAME);
				if (database.exists()) {
					// Log.i("Database", "Found");
					if (databaseManager == null) {
						databaseManager = new DatabaseManager(
								getApplicationContext());
					}
					int countUpload = 0;

					List<FileObj> arrFileBackUp = databaseManager
							.getAllListFileObjectWithFileState(Constant.NUMBER_UPLOAD_FAIL);

					Log.d("Log",
							"UploadImageIntentService Count file size DATABASE listFiles: "
									+ arrFileBackUp.size());
					// khai bao danh sach file upload ==> toi da 4 file upload
					List<FileObj> lstFileUpload = new ArrayList<>();

					if (arrFileBackUp.size() > 0) {
						Log.d(Constant.TAG,
								"UploadImageIntentService onHandleIntent service listFileDB.size() "
										+ arrFileBackUp.size());

						for (FileObj fileObj : arrFileBackUp) {
							Log.d(Constant.TAG,
									"UploadImageIntentService onHandleIntent service running with file zip"
											+ fileObj.getCodeSpinner());

							File isdnZip = new File(fileObj.getPath());
							if (!isdnZip.exists()) {
								databaseManager.deleteFileBackup(fileObj);
								continue;
							}
							byte[] buffer = FileUtils.fileToBytes(isdnZip);
							// xoa file zip khi chuyen qua buffer
							// isdnZip.delete();
							if (buffer != null && buffer.length > 0) {
								String base64 = Base64.encodeToString(buffer,
										Activity.TRIM_MEMORY_BACKGROUND);
								Log.d(Constant.TAG,
										"UploadImageIntentService onHandleIntent service running encodeToString"
												+ buffer.length);
								fileObj.setFileContent(base64);
								// add them o doan nay
								lstFileUpload.add(fileObj);
								if (lstFileUpload.size() == Integer
										.parseInt(numberFile)) {
									updateLoadProfile(lstFileUpload);
									lstFileUpload = new ArrayList<FileObj>(); 
									
								}
							}
						}
//						try {
//							if (KPI_DETAIL) {
//								saveLog.saveLogRequest("Buoc 1: so luong ban ghi: " + lstFileUpload.size(), new Date(),
						// lstFileUpload.size(), new Date(),
						// new Date(), userName + "_" +
//										new Date(), userName + "_" + CommonActivity.getDeviceId(this) + "_"
//												+ System.currentTimeMillis());
//							}
//						} catch (Exception e) {
						// Log.e("SaveLogLibException",
//							Log.e("SaveLogLibException", "Exception when save log ", e);
//						}
						if (!CommonActivity.isNullOrEmptyArray(lstFileUpload)) {
							updateLoadProfile(lstFileUpload);
							lstFileUpload = new ArrayList<FileObj>(); 
						}
					} else {
						Log.d(Constant.TAG,
								"UploadImageIntentService Co loi xay ra trong qua trinh su ly zip file hoac resize file");

					}
					Log.d("Log", "UploadImageIntentService countUpload "
							+ countUpload);
				} else {
					Log.d("Log",
							"UploadImageIntentService  database NOT exists()");
				}
			} catch (Exception e) {
				Log.e("Log",
						"UploadImageIntentService exception upload image ofline: ",
						e);

			}
		} else {

		}
	}

	private void updateLoadProfile(List<FileObj> lstFileUpload){
		

//		Date startDateSend = new Date();
//							try {
//								if (KPI_DETAIL) {
//									saveLog.saveLogRequest("Buoc 2: bat dau lay day du lieu du lieu  ", startDateSend,
//											new Date(), userName + "_" + CommonActivity.getDeviceId(this) + "_"
//													+ System.currentTimeMillis());
//								}
//							} catch (Exception e) {
//								Log.e("SaveLogLibException", "Exception when save log ", e);
//							}

		ParseOuput parseOuput = doUploadLstImage(lstFileUpload);
		try {
			String errorCode = "error";
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
			}

		} catch (Exception e) {
			Log.e("SaveLogLibException",
					"Exception when save log ", e);
		}

		if (CommonActivity.isNullOrEmpty(parseOuput)) {
			// update lai file trong truong hop exception
			for (FileObj fileObj : lstFileUpload) {
									Log.d(Constant.TAG,
											"UploadImageIntentService Upload khong thanh cong");
				fileObj.setStatus(1);
				databaseManager.updateFileState(fileObj);
									if (!CommonActivity.isNullOrEmpty(fileObj
											.getFile())) {
					File fileDest = fileObj.getFile();
					fileDest.delete();
				}

			}
			// reload lai list
								lstFileUpload = new ArrayList<>();
		} else {
								if (!CommonActivity
										.isNullOrEmptyArray(parseOuput
												.getLstImageInputFail())) {

									Log.d("size lst fail : ", parseOuput
											.getLstImageInputFail().size() + "");
									Log.d("size lstFileUpload : ",
											lstFileUpload.size() + "");
									for (int i = 0; i < parseOuput
											.getLstImageInputFail().size(); i++) {

					Log.d("MBCCS" ,"parseOuput.getLstImageInputFail().get(i).getRecordId() :::>>>>>>> : "
							+ parseOuput.getLstImageInputFail().get(i).getRecordId()+ "");

										for (int j = 0; j < lstFileUpload
												.size(); j++) {

											Log.d("MBCCS", "lstFileUpload.get(j).getRecordId() :::>>>>>>> : "
													+ lstFileUpload.get(j).getRecordId() + "");

											if (parseOuput
													.getLstImageInputFail()
													.get(i)
													.getRecordId()
													.equals(lstFileUpload
															.get(j)
															.getRecordId())) {

												lstFileUpload.get(j).setStatus(
														1);
												databaseManager
														.updateFileState(lstFileUpload
																.get(j));
												if (!CommonActivity
														.isNullOrEmpty(lstFileUpload
																.get(j)
																.getFile())) {
													File fileDest = lstFileUpload
															.get(j).getFile();
								fileDest.delete();
							}
							lstFileUpload.remove(j);
												Log.d("MBCCS", "size lstFileUpload.remove after remove : " +
														j + "");
							break;
						}
					}
				}

									Log.d("MBCCS","size lstFileUpload after remove : " + lstFileUpload.size() + "");
				// lay ra danh sach con lai la thanh cong
				for (FileObj fileObj : lstFileUpload) {
										Log.d("Log",
												"UploadImageIntentService Upload thanh cong");
					fileObj.setStatus(Constant.NUMBER_UPLOAD_FAIL);

										databaseManager
												.deleteFileBackup(fileObj);
					File file = new File(fileObj.getPath());
					file.delete();
										Log.d("Log",
												"UploadImageIntentService delete file success: ");
				}
				// khoi tao lai lstFileUpload
									lstFileUpload = new ArrayList<>();
			} else {
				// truong hop thanh cong het
				for (FileObj fileObj : lstFileUpload) {
										Log.d("Log",
												"UploadImageIntentService Upload thanh cong");
					fileObj.setStatus(Constant.NUMBER_UPLOAD_FAIL);

										databaseManager
												.deleteFileBackup(fileObj);
					File file = new File(fileObj.getPath());
					file.delete();
										Log.d("Log",
												"UploadImageIntentService delete file success: ");
				}
									lstFileUpload = new ArrayList<>();
			}
		}

	
		
	}
	
	
	private int doUploadImage(FileObj fileObject, String fileContent) {
		Log.d("Log",
				"UploadImageIntentService doUploadImage upload image to server "
						+ fileObject.getPath() + " filecontent:  "
						+ fileContent.length() + " " + fileObject.toString());
		SharedPreferences preferences = getSharedPreferences(Define.PRE_NAME,
				Context.MODE_PRIVATE);
		String userName = "";
		if (preferences != null) {
			userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
		}
		String tracking = preferences.getString(Define.KEY_TRACKING, "0");

		String original = "";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_uploadImageOffline");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:uploadImageOffline>");
			rawData.append("<input>");
			String token = "";

			String encrypt = preferences.getString(Define.KEY_TEMP, "");
			Log.i("MBCCS", "UploadImageIntentService encrypt " + encrypt + "");

			if (encrypt != null && !encrypt.isEmpty()) {
				token = StringUtils.decryptIt(encrypt, getApplicationContext());
			}
			rawData.append("<token>").append(token).append("</token>");
			rawData.append("<fileContent>").append(fileContent).append("</fileContent>");
			rawData.append("<isdn>").append(fileObject.getIsdn()).append("</isdn>");
			rawData.append("<recordCode>").append(fileObject.getCodeSpinner()).append("</recordCode>");
			rawData.append("<actionCode>").append(fileObject.getActionCode()).append("</actionCode>");
			rawData.append("<reasonId>").append(fileObject.getReasonId()).append("</reasonId>");
			rawData.append("<actionAudit>").append(fileObject.getActionAudit()).append("</actionAudit>");
			rawData.append("<pageIndex>").append(fileObject.getPageIndex()).append("</pageIndex>");
			rawData.append("</input>");
			rawData.append("</ws:uploadImageOffline>");
			// Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			// Log.d("uploadImageOffline Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					getApplicationContext(), "mbccs_uploadImageOffline");
			// Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();

			// parser
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				String token1 = parse.getValue(e2, "token");
				Log.d(Constant.TAG,
						"UploadImageIntentService doUploadImage errorCode: "
								+ errorCode + " description: " + description
								+ " token: " + token1);
				if (errorCode.equals("0")) {
					return 0;
				} else {
					if (errorCode.equals(Constant.INVALID_TOKEN2)) {
						return -1;
					}
				}
			}
		} catch (Exception e) {
			try {
				Log.e(Constant.TAG, "UploadImageIntentService doUploadImage "
						+ e.toString(), e);

			} catch (Exception e2) {
				Log.e("SaveLogLibException", "Exception when save log ", e2);
			}

		}
		return 1;
	}

	private ParseOuput doUploadLstImage(List<FileObj> arrayFileObject) {
		SharedPreferences preferences = getSharedPreferences(Define.PRE_NAME,
				Context.MODE_PRIVATE);
		ParseOuput parseOuput = null;
		String original = "";
		Date startTime = new Date();
		String envelope = "";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_uploadImageLstOffline");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:uploadImageLstOffline>");
			rawData.append("<input>");
			String token = "";

			String encrypt = preferences.getString(Define.KEY_TEMP, "");
			Log.i("MBCCS", "UploadImageIntentService encrypt  " + encrypt + "");

			if (encrypt != null && !encrypt.isEmpty()) {
				token = StringUtils.decryptIt(encrypt, getApplicationContext());
			}
			rawData.append("<token>").append(token).append("</token>");
			if (myLocation != null && !myLocation.getX().equals("0")
					&& !myLocation.getY().equals("0")) {
				rawData.append("<xUpdate>").append(myLocation.getX());
				rawData.append("</xUpdate>");
				rawData.append("<yUpdate>").append(myLocation.getY());
				rawData.append("</yUpdate>");
			}
			if (!CommonActivity.isNullOrEmptyArray(arrayFileObject)) {

				for (FileObj fileObj : arrayFileObject) {

					rawData.append("<lstUploadImage>");

					rawData.append("<recordId>").append(fileObj.getId()).append("</recordId>");
					rawData.append("<fileContent>").append(fileObj.getFileContent()).append("</fileContent>");
					rawData.append("<isdn>").append(fileObj.getIsdn()).append("</isdn>");
					rawData.append("<recordCode>").append(fileObj.getCodeSpinner()).append("</recordCode>");
					rawData.append("<actionCode>").append(fileObj.getActionCode()).append("</actionCode>");
					rawData.append("<reasonId>").append(fileObj.getReasonId()).append("</reasonId>");
					rawData.append("<actionAudit>").append(fileObj.getActionAudit()).append("</actionAudit>");
					rawData.append("<pageIndex>").append(fileObj.getPageIndex()).append("</pageIndex>");
					rawData.append("</lstUploadImage>");
				}

			}

			rawData.append("</input>");
			rawData.append("</ws:uploadImageLstOffline>");
			// Log.i("RowData", rawData.toString());
			envelope = input.buildInputGatewayWithRawData(rawData.toString());
			// Log.d("uploadImageOffline Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					getApplicationContext(), "mbccs_uploadImageLstOffline");
			// Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			 Log.d("MBCCS", "Send evelop original upload image off " + original);
			// parser
			Serializer serializer = new Persister();
			parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
			}
			return parseOuput;
		} catch (Exception e) {
			
			try {
				Log.e(Constant.TAG, "UploadImageIntentService doUploadImage "
						+ e.toString(), e);
				SaveLog saveLog;
				saveLog = new SaveLog(this, Constant.SYSTEM_NAME,
						Session.userName, "mbccs_uploadImageLstOffline",
						CommonActivity.findMyLocation(this).getX(),
						CommonActivity.findMyLocation(this).getY());
				String requestId = Session.userName + "_"
						+ CommonActivity.getDeviceId(this) + "_"
						+ System.currentTimeMillis();
				int begin = envelope.indexOf("<requestId>");
				int end = envelope.indexOf("</requestId>");

				if (begin >= 0 && end >= 0 && end - begin > 11) {
					requestId = envelope.substring(begin + 11, end);
				}
				if (requestId != null && requestId.length() >= 60) {
					requestId = requestId.substring(0, 57);
				}
				saveLog.saveLogException(e, startTime, new Date(), requestId);
			} catch (Exception e2) {
				Log.e("SaveLogLibException", "Exception when save log ", e2);
			}

		}
		return parseOuput;
	}

}
