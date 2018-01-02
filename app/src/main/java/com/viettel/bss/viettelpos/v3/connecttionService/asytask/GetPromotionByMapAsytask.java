package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.TabThongTinThueBao;

import static android.content.Context.MODE_PRIVATE;

public class GetPromotionByMapAsytask extends
		AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

	private Activity context = null;
	XmlDomParse parse = new XmlDomParse();
	String errorCode = "";
	String description = "";
	private ArrayList<PromotionTypeBeans> lstData = new ArrayList<PromotionTypeBeans>();
	private SubscriberDTO sub;
	private String custType;
	private String province;
	private EditText edtPromotion;
	private View prbPromotion;
	private TabThongTinThueBao tab;

	public GetPromotionByMapAsytask(Activity context, SubscriberDTO sub,
			String custType, String province, EditText edtPromotion,
			View prbPromotion, TabThongTinThueBao tab) {
		this.tab = tab;
		this.edtPromotion = edtPromotion;
		this.prbPromotion = prbPromotion;
		this.province = province;
		this.custType = custType;
		this.sub = sub;
		this.context = context;
		prbPromotion.setVisibility(View.VISIBLE);
	}

	@Override
	protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
		return getPromotionTypeBeans();
	}

	@Override
	protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
		prbPromotion.setVisibility(View.GONE);
		if (errorCode.equals("0")) {
			if (result != null && result.size() > 0) {
				lstData = result;
				PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
				promotionTypeBeans1.setCodeName(context
						.getString(R.string.selectMKM));
				promotionTypeBeans1.setPromProgramCode("-1");
				lstData.add(0, promotionTypeBeans1);
				edtPromotion.setText(context.getString(R.string.chonctkm1));

			} else {

				PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
				promotionTypeBeans1.setCodeName(context
						.getString(R.string.not_exists_promotion));

				promotionTypeBeans1.setPromProgramCode("");
				lstData = new ArrayList<PromotionTypeBeans>();
				sub.setPromotion(promotionTypeBeans1);
				lstData.add(0, promotionTypeBeans1);
				edtPromotion.setText(promotionTypeBeans1.getCodeName());
				if (tab != null) {
					tab.getCDT();
				}
			}
			sub.setLstPromotion(lstData);

			SharedPreferences mPrefs = context.getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor prefsEditor = mPrefs.edit();
			Gson gson = new Gson();
			String json = gson.toJson(sub);
			prefsEditor.putString("subscriber" + sub.getSubId(), json);
			prefsEditor.commit();

		} else {
			if (errorCode.equals(Constant.INVALID_TOKEN2)) {

				CommonActivity.BackFromLogin((Activity) context, description);
			} else {
				if (description == null || description.isEmpty()) {
					description = context.getString(R.string.checkdes);
				}
				Dialog dialog = CommonActivity.createAlertDialog(context,
						context.getString(R.string.error_get_promotion,
								description), context
								.getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<PromotionTypeBeans> getPromotionTypeBeans() {
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
			rawData.append("<reasonId>" + sub.getHthm().getReasonId() + "</reasonId>");
			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
			rawData.append("<serviceType>" + sub.getServiceType() + "</serviceType>");
			rawData.append("<custType>" + custType + "</custType>");
			rawData.append("<payType>" + 1 + "</payType>");
			rawData.append("<province>" + province + "</province>");

			rawData.append("<actionCode>" + "00" + "</actionCode>");
			rawData.append("</input>");
			rawData.append("</ws:getPromotions>");

//			input.addValidateGateway("wscode", "mbccs_getListPromotionByMapSP");
//			StringBuilder rawData = new StringBuilder();
//			rawData.append("<ws:getListPromotionByMapSP>");
//			rawData.append("<input>");
//			HashMap<String, String> paramToken = new HashMap<String, String>();
//			paramToken.put("token", Session.getToken());
//			rawData.append(input.buildXML(paramToken));
//			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
//			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
//			rawData.append("<serviceType>" + sub.getServiceType()
//					+ "</serviceType>");
//			rawData.append("<custType>" + custType + "</custType>");
//			rawData.append("<payType>" + 1 + "</payType>");
//			rawData.append("<subType>" + sub.getSubType() + "</subType>");
//			rawData.append("<province>" + province + "</province>");
//			rawData.append("<district>" + "-1" + "</district>");
//			rawData.append("<precinct>" + "-1" + "</precinct>");
//			// rawData.append("<actionCode>" + "00" + "</actionCode>");
//			rawData.append("</input>");
//			rawData.append("</ws:getListPromotionByMapSP>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_getPromotions");
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
					lisPromotionTypeBeans.add(promotionTypeBeans);
				}
			}
		} catch (Exception ex) {
			Log.d("getPromotionTypeBeans", ex.toString());
		}

		return lisPromotionTypeBeans;
	}

}
