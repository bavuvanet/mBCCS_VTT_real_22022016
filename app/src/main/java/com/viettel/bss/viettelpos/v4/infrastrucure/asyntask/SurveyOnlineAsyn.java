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
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

public class SurveyOnlineAsyn extends AsyncTaskCommon<String, Void, ResultSurveyOnlineForBccs2Form>{
	public Activity activity;
	public SurveyOnlineAsyn(Activity context, OnPostExecuteListener<ResultSurveyOnlineForBccs2Form> listener,
                            OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		this.activity = context;
	}

	@Override
	protected ResultSurveyOnlineForBccs2Form doInBackground(String... arg0) {
		return getSurveyOnlineByConetorCode(arg0[0]);
	}


	private ResultSurveyOnlineForBccs2Form getSurveyOnlineByConetorCode(String connectorCode) {
		String original = null;
		ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_surveyOnlineByConnectorCodeForBccs2");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:surveyOnlineByConnectorCodeForBccs2>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<form>");

			rawData.append("<connectorCode>" + connectorCode);
			rawData.append("</connectorCode>");

			rawData.append("<serviceType>" + "");
			rawData.append("</serviceType>");

			rawData.append("<surveyType>" + "1");
			rawData.append("</surveyType>");
			rawData.append("</form>");
			rawData.append("</input>");
			rawData.append("</ws:surveyOnlineByConnectorCodeForBccs2>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
					"mbccs_surveyOnlineByConnectorCodeForBccs2");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee", original);

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				resultSurveyOnlineForBccs2Form = parseOuput.getResultSurveyOnlineForBccs2Form();
			}

			return resultSurveyOnlineForBccs2Form;
//			BCCSGateway input = new BCCSGateway();
//			input.addValidateGateway("username", Constant.BCCSGW_USER);
//			input.addValidateGateway("password", Constant.BCCSGW_PASS);
//			input.addValidateGateway("wscode", "mbccs_getSurveyOnlineByConnectorCodeForBccs2");
//			StringBuilder rawData = new StringBuilder();
//			rawData.append("<ws:getSurveyOnlineByConnectorCodeForBccs2>");
//			rawData.append("<input>");
//			HashMap<String, String> paramToken = new HashMap<String, String>();
//			paramToken.put("token", Session.getToken());
//			rawData.append(input.buildXML(paramToken));
//			rawData.append("<surveyOnlineByConnectorCodeForBccs2Form>");
//
//			rawData.append("<connectorCode>" + connectorCode);
//			rawData.append("</connectorCode>");
//			rawData.append("<serviceType>" + "");
//			rawData.append("</serviceType>");
//
//			rawData.append("</surveyOnlineByConnectorCodeForBccs2Form>");
//			rawData.append("</input>");
//			rawData.append("</ws:getSurveyOnlineByConnectorCodeForBccs2>");
//			Log.i("RowData", rawData.toString());
//
//			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
//			Log.d("Send evelop", envelope);
//			Log.i("LOG", Constant.BCCS_GW_URL);
//			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
//					"mbccs_getSurveyOnlineByConnectorCodeForBccs2");
//			Log.i("Responseeeeeeeeee", response);
//			CommonOutput output = input.parseGWResponse(response);
//			original = output.getOriginal();
//			Log.i("Responseeeeeeeeee", original);
//
//			Serializer serializer = new Persister();
//			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
//			if (parseOuput != null) {
//				errorCode = parseOuput.getErrorCode();
//				description = parseOuput.getDescription();
//				resultSurveyOnlineForBccs2Form = parseOuput.getResultSurveyOnlineForBccs2Form();
//			}
//
//			return resultSurveyOnlineForBccs2Form;

		} catch (Exception e) {
			Log.d("getSurveyOnlineByConetorCode", e.toString());
		}
		return resultSurveyOnlineForBccs2Form;
	}

}
