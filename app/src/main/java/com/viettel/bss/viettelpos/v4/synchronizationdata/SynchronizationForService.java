package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.InputSource;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.syn.handler.SynchronizeHandler;
import com.viettel.bss.viettelpos.v4.syn.object.SynchronizeDataObject;

public class SynchronizationForService extends
		AsyncTask<ArrayList<OjectSyn>, Void, String> {
	String Tag = SynchronizationForService.class.getName();
	final BCCSGateway input = new BCCSGateway();
	String response = null;
	String original = null;
	String errorCode = "";
	String description = "";
	// final XmlDomParse parse = new XmlDomParse();

	private Context context = null;
	ArrayList<String> listgetdata = null;
	ArrayList<OjectSyn> lisSynchoronizationObjects = null;
	private GetMaxOraRowScnDal dal = null;
	Long timeStart = System.currentTimeMillis();

	public SynchronizationForService(Context context,
			ArrayList<OjectSyn> arrObjects, String token) {
		this.context = context;
		this.lisSynchoronizationObjects = arrObjects;
		timeStart = System.currentTimeMillis();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(ArrayList<OjectSyn>... arg0) {
		SharedPreferences preferences = context.getSharedPreferences(
				Define.PRE_NAME, Context.MODE_MULTI_PROCESS);
		Log.i("SynchronizationForService","start sync table");
		String invalidToken = preferences.getString(Define.KEY_INVALID_TOKEN,
				"0");
		Log.e("HuyPQ15:dong bo du lieu invalid ", invalidToken);
		if ("1".equals(invalidToken)) {
			Log.e("Da bi invalidToken, return null", invalidToken);
			return null;
		}

		if (Session.isSync && !Session.isSyncRunning) {

			Log.e(SynchronizationForService.class.getCanonicalName(),
					"Run Sync");
			Session.isSyncRunning = true;
			return sendListRequest(lisSynchoronizationObjects);
		} else {
			Log.e(SynchronizationForService.class.getCanonicalName(),
					"Sync not run");
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {

		// parse reponse
		if (result != null) {
			InputStream in = null;
			try {

				Log.d(Tag, result);
				File file = new File(result);
				in = new FileInputStream(file);
				SynchronizeHandler handler = (SynchronizeHandler) CommonActivity
						.parseXMLHandler(new SynchronizeHandler(),
								new InputSource(in));
				file.delete();
				if (handler != null) {
					if (handler.getItem() != null
							&& Constant.INVALID_TOKEN.equals(handler.getItem()
									.getErrorCode())) {
						SharedPreferences preferences = context
								.getSharedPreferences(Define.PRE_NAME,
										Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(Define.KEY_INVALID_TOKEN, "1");
						editor.commit();

						return;
					}
					ArrayList<SynchronizeDataObject> lstObject = handler
							.getData();
					Long timeStart = System.currentTimeMillis();
					if (lstObject != null && !lstObject.isEmpty()) {
						dal = new GetMaxOraRowScnDal(context);
						dal.syncChange(lstObject);
					}
					Long duration = System.currentTimeMillis() - timeStart;
					Log.e(Tag, "time insert old: " + duration);

				}

			} catch (Exception e) {
				e.printStackTrace();
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
		Long duration = System.currentTimeMillis() - timeStart;
		Log.e("SYNC_DATA_AUTO", "total time: " + duration);
		Session.isSyncRunning = false;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	// method add send list request to server
	public String sendListRequest(ArrayList<OjectSyn> araObjects) {
		try {

			StringBuilder rawData = new StringBuilder();
			rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

			rawData.append("<soapenv:Header/>");
			rawData.append("<soapenv:Body>");
			rawData.append("<ws:syncDataChangeBCCS2>");

			rawData.append("<syncInput>");
			HashMap<String, String> param = new HashMap<String, String>();
			// HashMap<String, String> paramToken = new HashMap<String,
			// String>();
			// paramToken.put("token", Session.getToken());
			String token = "";

			SharedPreferences preferences = context.getSharedPreferences(
					Define.PRE_NAME, Context.MODE_PRIVATE);

			String encrypt = preferences.getString(Define.KEY_TEMP, "");
			if (encrypt != null && !encrypt.isEmpty()) {
				token = StringUtils.decryptIt(encrypt,
						context.getApplicationContext());
			}

			rawData.append("<token>").append(token).append("</token>");
			// syncInput

			if (araObjects.size() > 0) {
				for (OjectSyn item : araObjects) {
					HashMap<String, String> rawDataItem = new HashMap<String, String>();
					rawDataItem.put("oraRowscn", item.getMax_ora_rowscn());
					rawDataItem.put("table", item.getTable_name());
					param.put("lstSyncBase", input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));
				}
			}
			rawData.append("</syncInput>");
			rawData.append("</ws:syncDataChangeBCCS2>");
			rawData.append("</soapenv:Body>");
			rawData.append("</soapenv:Envelope>");

			String envelope = rawData.toString();
			Log.e("Send evelop", envelope);
			String fileName = input.sendRequestWriteToFile(envelope,
					Constant.WS_SYNCHRONIZE_DATA,
					Constant.SYNCHRONIZE_AUTO_FILE_NAME);
			return fileName;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
