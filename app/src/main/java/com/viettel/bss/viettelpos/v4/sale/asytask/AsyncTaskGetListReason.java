package com.viettel.bss.viettelpos.v4.sale.asytask;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class AsyncTaskGetListReason extends
		AsyncTaskCommon<SubscriberDTO, Void, ArrayList<Spin>> {

	private String actionCode;

	public AsyncTaskGetListReason(Activity context,
			OnPostExecuteListener<ArrayList<Spin>> listener,
			OnClickListener moveLogInAct, String actionCode, View prb) {

		super(context, listener, moveLogInAct, prb);
		this.actionCode = actionCode;

	}

	public AsyncTaskGetListReason(Activity context,
			OnPostExecuteListener<ArrayList<Spin>> listener,
			OnClickListener moveLogInAct, String actionCode) {

		super(context, listener, moveLogInAct);
		this.actionCode = actionCode;
	}

	@Override
	protected ArrayList<Spin> doInBackground(SubscriberDTO... arg0) {
		return getListReasons(arg0[0]);
	}

	private ArrayList<Spin> getListReasons(SubscriberDTO sub) {
		String original = "";
		ArrayList<Spin> lstReasons = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getReasonFullPM");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getReasonFullPM>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

            if(CommonActivity.isNullOrEmpty(sub.getPayType())) {
                rawData.append("<payType>1</payType>");
            } else {
                rawData.append("<payType>").append(sub.getPayType()).append("</payType>");
            }
			rawData.append("<offerId>").append(sub.getOfferId())
					.append("</offerId>");
			rawData.append("<actionCode>").append(actionCode)
					.append("</actionCode>");
			rawData.append("<serviceType>").append(sub.getServiceType())
					.append("</serviceType>");
			rawData.append("<custType>").append(sub.getCustType())
					.append("</custType>");
			rawData.append("<subType>").append(sub.getSubType())
					.append("</subType>");

			rawData.append("</input>");
			rawData.append("</ws:getReasonFullPM>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_getReasonFullPM");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);

			// parser
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodeChild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);

				// lstSaleTransBankPluses
				nodeChild = doc.getElementsByTagName("lstReasonDTO");
				for (int j = 0; j < nodeChild.getLength(); j++) {
					Element e1 = (Element) nodeChild.item(j);

					Spin spin = new Spin();
					spin.setId(parse.getValue(e1, "reasonId"));
					spin.setName(parse.getValue(e1, "reasonCode") + " - "
							+ parse.getValue(e1, "name"));

					lstReasons.add(spin);
				}
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return lstReasons;

	}

}
