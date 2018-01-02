package com.viettel.bss.viettelpos.v4.infrastrucure.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
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

public class FindNodeByStaffIdAndNodeCodeAsyn extends AsyncTaskCommon<String, Void, ArrayList<Spin>>{
	public Activity activity;
	public FindNodeByStaffIdAndNodeCodeAsyn(Activity context, OnPostExecuteListener<ArrayList<Spin>> listener,
                                            OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		this.activity = context;
	}

	@Override
	protected ArrayList<Spin> doInBackground(String... arg0) {
		return getSurveyOnlineByConetorCode();
	}


	private ArrayList<Spin> getSurveyOnlineByConetorCode() {
		String original = null;
		ArrayList<Spin> lstSpins = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_findNodeByStaffIdAndNodeCode");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:findNodeByStaffIdAndNodeCode>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));

			rawData.append("</input>");
			rawData.append("</ws:findNodeByStaffIdAndNodeCode>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
					"mbccs_findNodeByStaffIdAndNodeCode");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee", original);

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				lstSpins = parseOuput.getLstMappingNodeStaffDTOs();
			}

			return lstSpins;

		} catch (Exception e) {
			Log.d("findNodeByStaffIdAndNodeCode", e.toString());
		}
		return lstSpins;
	}

}
