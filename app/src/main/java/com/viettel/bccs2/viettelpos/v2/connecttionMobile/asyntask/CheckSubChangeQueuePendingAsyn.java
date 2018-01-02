package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class CheckSubChangeQueuePendingAsyn extends AsyncTaskCommon<String, Void, String>{

	public CheckSubChangeQueuePendingAsyn(Activity context, OnPostExecuteListener<String> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected String doInBackground(String... arg0) {
		return checkSubChangeQueuePending(arg0[0],arg0[1]);
	}

	private String checkSubChangeQueuePending(String subId, String actionCode){
		String original = "";
		try{
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_checkSubChangeQueuePending");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:checkSubChangeQueuePending>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
	
			rawData.append("<subId>" + subId + "</subId>");
			rawData.append("<actionCode>" + actionCode + "</actionCode>");
			rawData.append("</input>");
			rawData.append("</ws:checkSubChangeQueuePending>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_checkSubChangeQueuePending");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();

			// ============parse xml in android=========
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				Log.d("errorCode", errorCode);
				description = parse.getValue(e2, "description");
				Log.d("description", description);
			}
		}catch (Exception e) {
			Log.d("checkSubChangeQueuePending", e.toString());
		}
		return errorCode; 
	}
	
}
