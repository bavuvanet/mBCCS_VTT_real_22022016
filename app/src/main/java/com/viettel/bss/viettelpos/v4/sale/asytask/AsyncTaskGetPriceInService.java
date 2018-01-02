package com.viettel.bss.viettelpos.v4.sale.asytask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;

public class AsyncTaskGetPriceInService extends AsyncTaskCommon<String, Void, String> {
	
	private final String reasonId;
	private final String telecomServiceId;
	private final String productCode;
	private final String proOfferId;

	public AsyncTaskGetPriceInService(Activity context,
			OnPostExecuteListener<String> listener,
			OnClickListener moveLogInAct, String reasonId, String telecomServiceId, String productCode, 
			String proOfferId) {
		super(context, listener, moveLogInAct);
		this.reasonId = reasonId;
		this.telecomServiceId = telecomServiceId;
		this.productCode = productCode;
		this.proOfferId = proOfferId;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return getPriceInService();
	}

	private String getPriceInService() {
		String original = "";
		String result = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getPriceInServices");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getPriceInServices>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
			rawData.append("<telecomServiceId>").append(telecomServiceId).append("</telecomServiceId>");
			rawData.append("<prodOfferId>").append(proOfferId).append("</prodOfferId>");
			rawData.append("<productCode>").append(productCode).append("</productCode>");

			rawData.append("</input>");
			rawData.append("</ws:getPriceInServices>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL, mActivity,
					"mbccs_getPriceInServices");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);

			// parser
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				result = parse.getValue(e2, "price");
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return result;

	}

}
