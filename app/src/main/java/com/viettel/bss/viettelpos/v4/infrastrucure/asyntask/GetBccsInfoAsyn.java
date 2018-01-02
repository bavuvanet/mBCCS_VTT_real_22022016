package com.viettel.bss.viettelpos.v4.infrastrucure.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.NodeCodeDetail;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;

public class GetBccsInfoAsyn extends AsyncTaskCommon<String, Void, ArrayList<NodeCodeDetail>>{
	public Activity activity;
	public GetBccsInfoAsyn(Activity context, OnPostExecuteListener<ArrayList<NodeCodeDetail>> listener,
                           OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		this.activity = context;
	}

	@Override
	protected ArrayList<NodeCodeDetail> doInBackground(String... arg0) {
		return getSurveyOnlineByConetorCode(arg0[0]);
	}


	private ArrayList<NodeCodeDetail> getSurveyOnlineByConetorCode(String account) {
		String original = null;
		ArrayList<NodeCodeDetail> lstSpins = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getBccsInfo");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getBccsInfo>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));

			rawData.append("<infoCode>" + "108_getListSubByGroupAccount");
			rawData.append("</infoCode>");
			rawData.append("<paramList>" + account);
			rawData.append("</paramList>");

			rawData.append("</input>");
			rawData.append("</ws:getBccsInfo>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
					"mbccs_getBccsInfo");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee", original);

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				if("0".equals(errorCode)){
					if(!CommonActivity.isNullOrEmpty(parseOuput.getLstInfo()) && parseOuput.getLstInfo().size() > 0){
						for (String item: parseOuput.getLstInfo()) {
							if(item.startsWith("<![CDATA[")){
								item = item.substring(9,item.length() - 2);
							}
							item = "<return>" + item + "</return>";
							NodeCodeDetail nodeCodeDetail = serializer.read(NodeCodeDetail.class,item);
							if(!CommonActivity.isNullOrEmpty(nodeCodeDetail)){
								lstSpins.add(nodeCodeDetail);
							}
						}
					}
				}
			}
			return lstSpins;
		} catch (Exception e) {
			Log.d("getBccsInfo", e.toString());
		}
		return lstSpins;
	}

}
