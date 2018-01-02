package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.syn.handler.SynchronizeHandler;
import com.viettel.bss.viettelpos.v4.syn.object.SynchronizeDataObject;

class SynchronizationData extends
		AsyncTask<ArrayList<OjectSyn>, Void, String> {
	private final String Tag = "SynchronizationData";
	final BCCSGateway input = new BCCSGateway();
	String response = null;
	String original = null;
	final XmlDomParse parse = new XmlDomParse();

	private final ProgressDialog progress;
	private Context context = null;
	ArrayList<String> listgetdata = null;
	private ArrayList<OjectSyn> lisSynchoronizationObjects = null;
	private GetMaxOraRowScnDal dal = null;
	String errorCode = "";
	private String description = "";
	private Long timeStart = System.currentTimeMillis();

	public SynchronizationData(Context context, ArrayList<OjectSyn> arrObjects) {
		timeStart = System.currentTimeMillis();
		this.context = context;
		this.lisSynchoronizationObjects = arrObjects;
		this.progress = new ProgressDialog(this.context);
		// check font
		// String syndata = waitingsyn
		this.progress.setMessage(this.context.getResources().getString(
				R.string.waitingsyn));
		this.progress.setCancelable(false);
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected void onPreExecute() {
		Long start = System.currentTimeMillis();
		while (Session.isSyncRunning) {
			try {
				Thread.sleep(1000L);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - start > 300000L) {
				break;
			}

		}
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(ArrayList<OjectSyn>... arg0) {
		Session.isSyncRunning = true;
		return sendListRequest(lisSynchoronizationObjects);
	}

	@Override
	protected void onPostExecute(String result) {
		// parse reponse
		progress.dismiss();
		if (result != null) {
			InputStream in = null;
			try {
				Log.d("reponseeeee", result);
				File file = new File(result);
				Log.e(Tag, "file.exists " + file.exists());
				in = new FileInputStream(file);
				SynchronizeHandler handler = (SynchronizeHandler) CommonActivity
						.parseXMLHandler(new SynchronizeHandler(),
								new InputSource(in));
				file.delete();
				if (handler != null) {
					if (handler.getItem() != null
							&& "0".equals(handler.getItem().getErrorCode())) {

						ArrayList<SynchronizeDataObject> lstObject = handler
								.getData();
						if (lstObject != null && !lstObject.isEmpty()) {
							dal = new GetMaxOraRowScnDal(context);
							dal.syncChange(lstObject);
						}
						Dialog dialog = CommonActivity.createAlertDialog(
								(Activity) context,
								context.getResources().getString(
										R.string.syndatasucess),
								context.getResources().getString(
										R.string.syndata));
						dialog.show();
					} else {
						if (handler.getItem() != null
								&& Constant.INVALID_TOKEN2.equals(handler
										.getItem().getErrorCode())) {
							if (handler.getItem().getDescription() != null
									&& !handler.getItem().getDescription()
											.isEmpty()) {
								description = handler.getItem()
										.getDescription();
							} else {
								description = context
										.getString(R.string.token_invalid);
							}
							Dialog dialog = CommonActivity.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.syndata), moveLogInAct);
							dialog.show();
						} else {
							if (handler.getItem().getDescription() != null
									&& !handler.getItem().getDescription()
											.isEmpty()) {
								description = handler.getItem()
										.getDescription();
							} else {
								description = context
										.getString(R.string.syndatafails);
							}
							Dialog dialog = CommonActivity.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.syndata));
							dialog.show();
						}
					}

				} else {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									context.getString(R.string.syndatafails),
									context.getResources().getString(
											R.string.syndata));
					dialog.show();

				}
			} catch (Exception e) {
				e.printStackTrace();
				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context,
						context.getString(R.string.exception) + ": "
								+ e.getMessage(), context.getResources()
								.getString(R.string.syndata));
				dialog.show();
			} finally {
				if (dal != null) {
					dal.close();
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		Session.isSyncRunning = false;
		Long duration = System.currentTimeMillis() - timeStart;
		Log.e("SYNC_DATA_MANUAL", "total time: " + duration);
	}

	// method add send list request to server
    private String sendListRequest(ArrayList<OjectSyn> araObjects) {
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_syncChangeRecordDatabase");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

			rawData.append("<soapenv:Header/>");
			rawData.append("<soapenv:Body>");
			// rawData.append("<ws:syncChangeRecordDatabase>");
			rawData.append("<ws:syncChangeRecordDatabaseNewWay>");
			rawData.append("<syncInput>");
			HashMap<String, String> param = new HashMap<>();
			HashMap<String, String> paramToken = new HashMap<>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			// rawData.append("<lstSyncBase>");
			if (araObjects.size() > 0) {
				for (OjectSyn item : araObjects) {
					HashMap<String, String> rawDataItem = new HashMap<>();
					rawDataItem.put("oraRowscn", item.getMax_ora_rowscn());
					rawDataItem.put("table", item.getTable_name());
					param.put("lstSyncBase", input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));
				}
			}
			// rawData.append("</lstSyncBase>");
			rawData.append("</syncInput>");
			// rawData.append("</ws:syncChangeRecordDatabase>");
			rawData.append("</ws:syncChangeRecordDatabaseNewWay>");
			rawData.append("</soapenv:Body>");
			rawData.append("</soapenv:Envelope>");

			// String envelope = input.buildInputGatewayWithRawData(rawData
			// .toString());
			String envelope = rawData.toString();
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
            // Log.i("Responseeeeeeeeee", response);
			// CommonOutput output = input.parseGWResponse(response);
			// if (!output.getError().equals("0")) {
			// return Constant.ERROR_CODE;
			// }
			// original = output.getOriginal();
			// Log.d("originalllllllll", original);
			// parse xml
			// Document doc = parse.getDomElement(original);
			// NodeList nl = doc.getElementsByTagName("return");
			// for (int i = 0; i < nl.getLength(); i++) {
			// Element e2 = (Element) nl.item(i);
			// errorCode = parse.getValue(e2, "errorCode");
			// Log.d("errorCode", errorCode);
			// description = parse.getValue(e2, "description");
			// Log.d("description", description);
			// }
			// VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
			// .parseXMLHandler(new VSAMenuHandler(), original);
			// output = handler.getItem();
			// if (output.getToken() != null && !output.getToken().isEmpty()) {
			// Session.setToken(output.getToken());
			// }
			// if (!output.getErrorCode().equals("0")) {
			// original = output.getDescription();
			// }
			// if (output.getErrorCode().equals("0")) {
			// original = output.getErrorCode();
			// }
			return input.sendRequestWriteToFile(envelope,
					Constant.WS_SYNCHRONIZE_DATA,
					Constant.SYNCHRONIZE_MANUAL_FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();

		}
	};

	// create file xml for Synchronization
	public String createXMLSyn(String tableName, String max_Row) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:syncChangeRecordDatabase>");
		stringBuilder.append("<syncInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<lstSyncBase>");
		stringBuilder.append("<oraRowscn>").append(max_Row).append("</oraRowscn>");
		stringBuilder.append("<table>").append(tableName).append("</table>");
		stringBuilder.append("</lstSyncBase>");
		stringBuilder.append("</syncInput>");
		stringBuilder.append("</ws:syncChangeRecordDatabase>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}

}