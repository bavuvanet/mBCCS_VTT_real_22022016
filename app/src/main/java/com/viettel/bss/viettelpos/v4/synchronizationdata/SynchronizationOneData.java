package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.work.activity.OnTaskCompleted;


public class SynchronizationOneData extends AsyncTask<OjectSyn, Void, String> {
	String Tag = "SynchronizationData";
	final BCCSGateway input = new BCCSGateway();
	String response = null;
	String original = null;
	private final XmlDomParse parse = new XmlDomParse();

	private Context context = null;
	private Activity mActivity = null;
	ArrayList<String> listgetdata = null;
	private Boolean onBackPress = true;
	private GetMaxOraRowScnDal dal = null;
	private OjectSyn ojectSyn = null;
	private OnTaskCompleted listenerTaskCompleted;

	public SynchronizationOneData(Context context, OjectSyn arrObjects, Boolean onBackPress) {
		this.context = context;
		this.mActivity = (Activity) context;
		this.ojectSyn = arrObjects;
		this.onBackPress = onBackPress;
	}
	
	public OnTaskCompleted getListenerTaskCompleted() {
		return listenerTaskCompleted;
	}

	public void setListenerTaskCompleted(OnTaskCompleted listenerTaskCompleted) {
		this.listenerTaskCompleted = listenerTaskCompleted;
	}

	@Override
	protected String doInBackground(OjectSyn... arg0) {
		return sendListRequest(ojectSyn);
	}

	@Override
	protected void onPostExecute(String result) {
		// parse reponse
		if (result != null) {
			
			try {
				dal = new GetMaxOraRowScnDal(context);
				dal.open();
				Log.d("reponseeeee", result);

				Document doc = parse.getDomElement(result);
				NodeList nl = doc.getElementsByTagName("return");

				NodeList nodelistchild = null;
				for (int k = 0; k < nl.getLength(); k++) {
					nodelistchild = doc.getElementsByTagName("lstSyncResult");
					for (int j = 0; j < nodelistchild.getLength(); j++) {
						Element e1 = (Element) nodelistchild.item(j);
						NodeList nl2 = e1.getElementsByTagName("lstData");
						for (int i = 0; i < nl2.getLength(); i++) {
							System.out.println(nl2.item(i).getTextContent());
							// getLstData.add();
							try {
								System.out
										.println(nl2.item(i).getTextContent());
								dal.runquery(nl2.item(i).getTextContent());
							} catch (Exception e) {
								e.printStackTrace();
							}
							// get maxOraRowScn and get tableName
						}
						long ora_Rowscn = Long.parseLong(parse.getValue(e1,
								"oraRowscn"));
						Log.d("ora_Rowscn", "" + ora_Rowscn);
						String tableName = parse.getValue(e1, "table");
						Log.d("tableName", tableName);
						if (tableName != "" && ora_Rowscn != 0) {
							dal.update2(tableName, "" + ora_Rowscn, "1");
						}
						if (tableName != null && ora_Rowscn == 0) {
							dal.update3(tableName, "1");
						}
					}
				}
//				listenerTaskCompleted.onTaskCompleted();
				
				if(onBackPress){
					mActivity.onBackPressed();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(dal!=null){
					dal.close();
				}
			}
		}
	}

	// method add send list request to server
    private String sendListRequest(OjectSyn araObjects) {
		String original = null;
		String errorMessage = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_syncChangeRecordDatabase");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:syncChangeRecordDatabase>");
			rawData.append("<syncInput>");
			HashMap<String, String> param = new HashMap<>();
			HashMap<String, String> paramToken = new HashMap<>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			// rawData.append("<lstSyncBase>");

			HashMap<String, String> rawDataItem = new HashMap<>();
			rawDataItem.put("oraRowscn", araObjects.getMax_ora_rowscn());
			rawDataItem.put("table", araObjects.getTable_name());
			param.put("lstSyncBase", input.buildXML(rawDataItem));
			rawData.append(input.buildXML(param));

			// rawData.append("</lstSyncBase>");
			rawData.append("</syncInput>");
			rawData.append("</ws:syncChangeRecordDatabase>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL,context,"mbccs_syncChangeRecordDatabase");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}
			original = output.getOriginal();
			Log.d("originalllllllll", original);
			VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
					.parseXMLHandler(new VSAMenuHandler(), original);
			output = handler.getItem();
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			if (!output.getErrorCode().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}

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