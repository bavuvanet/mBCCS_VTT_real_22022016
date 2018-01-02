package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class GetPromotionsAsyn extends AsyncTaskCommon<String, Void, ArrayList<PromotionTypeBeans>> {


	public GetPromotionsAsyn(Activity context, OnPostExecuteListener<ArrayList<PromotionTypeBeans>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
		return getPromotions(arg0[0],arg0[1],arg0[2],arg0[3],arg0[4],arg0[5]);
	}

	private ArrayList<PromotionTypeBeans> getPromotions(String actionCode,String serviceType, String offerId, String province, String district, String precint) {
		ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
		String original = "";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getPromotions");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getPromotions>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<offerId>" + offerId + "</offerId>");
			rawData.append("<serviceType>" + serviceType + "</serviceType>");
			rawData.append("<payType>" + 1 + "</payType>");
			
			rawData.append("<actionCode>" + actionCode + "</actionCode>");
			
			rawData.append("<province>" + province + "</province>");
			rawData.append("<district>" + district + "</district>");
			rawData.append("<precinct>" + precint + "</precinct>");
			rawData.append("</input>");
			rawData.append("</ws:getPromotions>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getPromotions");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();

			// ============parse xml in android=========
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				Log.d("errorCode", errorCode);
				description = parse.getValue(e2, "description");
				Log.d("description", description);
				nodechild = doc.getElementsByTagName("lstPromotionsBO");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
					String codeName = parse.getValue(e1, "codeName");
					Log.d("codeName", codeName);
					promotionTypeBeans.setCodeName(codeName);

					String name = parse.getValue(e1, "name");
					Log.d("name", name);
					promotionTypeBeans.setName(name);

					String promProgramCode = parse.getValue(e1, "promProgramCode");
					Log.d("code", promProgramCode);
					promotionTypeBeans.setPromProgramCode(promProgramCode);

					String descrip = parse.getValue(e1, "description");
					Log.d("descriponnnnn", descrip);
					if (CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())) {
						promotionTypeBeans.setDescription(descrip);
					}
					
					String monthAmount = parse.getValue(e1, "monthAmount");
					promotionTypeBeans.setMonthAmount(monthAmount);

					String monthCommitment = parse.getValue(e1, "monthCommitment");
					promotionTypeBeans.setMonthCommitment(monthCommitment);

					String expireDate = parse.getValue(e1, "expireDate");
					promotionTypeBeans.setExpireDate(expireDate);

					String startDate = parse.getValue(e1, "startDate");
					promotionTypeBeans.setStartDate(startDate);

					String effectDate = parse.getValue(e1, "effectDate");
					promotionTypeBeans.setEffectDate(effectDate);

					lisPromotionTypeBeans.add(promotionTypeBeans);
				}
			}
		} catch (Exception ex) {
			Log.d("getPromotions", ex.toString());
		}

		return lisPromotionTypeBeans;
	}

}
