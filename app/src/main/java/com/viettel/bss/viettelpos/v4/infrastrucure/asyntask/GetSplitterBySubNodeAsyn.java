package com.viettel.bss.viettelpos.v4.infrastrucure.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;

public class GetSplitterBySubNodeAsyn extends AsyncTaskCommon<String, Void, ArrayList<Spin>>{
	public Activity activity;
	public GetSplitterBySubNodeAsyn(Activity context, OnPostExecuteListener<ArrayList<Spin>> listener,
                                    OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		this.activity = context;
	}

	@Override
	protected ArrayList<Spin> doInBackground(String... arg0) {
		return getSurveyOnlineByConetorCode(arg0[0]);
	}


	private ArrayList<Spin> getSurveyOnlineByConetorCode(String subNodeCode) {
		String original = null;
		ArrayList<Spin> lstSpins = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getSplitterBySubNode");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getSplitterBySubNode>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<inputForm>");
			rawData.append("<subNodeCode>" + subNodeCode);
			rawData.append("</subNodeCode>");
			rawData.append("</inputForm>");
			rawData.append("</input>");
			rawData.append("</ws:getSplitterBySubNode>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
					"mbccs_getSplitterBySubNode");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee", original);

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				lstSpins = parseOuput.getLstForms();
			}

			return lstSpins;

		} catch (Exception e) {
			Log.d("getSplitterBySubNode", e.toString());
		}
		return lstSpins;
	}

}
