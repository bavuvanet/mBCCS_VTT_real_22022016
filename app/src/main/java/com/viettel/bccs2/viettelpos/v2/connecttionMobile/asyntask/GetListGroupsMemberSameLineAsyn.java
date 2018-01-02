package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import java.util.ArrayList;
import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class GetListGroupsMemberSameLineAsyn extends AsyncTaskCommon<String, Void, ArrayList<SubscriberDTO>>{

	public GetListGroupsMemberSameLineAsyn(Activity context, OnPostExecuteListener<ArrayList<SubscriberDTO>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected ArrayList<SubscriberDTO> doInBackground(String... arg0) {
		return getListGroupsMemberSameLine(arg0[0]);
	}

	private ArrayList<SubscriberDTO> getListGroupsMemberSameLine(String subId){
		ArrayList<SubscriberDTO> arraySubscriberDTO = new ArrayList<SubscriberDTO>();
		String original = "";
		try{
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getListGroupsMemberSameLine");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getListGroupsMemberSameLine>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<subId>" + subId + "</subId>");
			
			rawData.append("</input>");
			rawData.append("</ws:getListGroupsMemberSameLine>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getListGroupsMemberSameLine");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			
			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class,
					original);
			if(parseOuput != null){
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				arraySubscriberDTO = parseOuput.getLstBasicSubscriberInfo();
			}
		}catch (Exception e) {
			Log.d("getListGroupsMemberSameLine", e.toString());
		}
		return arraySubscriberDTO;
	}
	
	
}
