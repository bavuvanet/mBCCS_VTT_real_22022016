package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.InputSource;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v3.connecttionService.handle.PromotionHanlder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class GetPromotionReadFileAsytask extends
		AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

	private Context context = null;
	XmlDomParse parse = new XmlDomParse();
	String errorCode = "";
	String description = "";
	private ArrayList<PromotionTypeBeans> lstData = new ArrayList<PromotionTypeBeans>();
	private SubscriberDTO sub;
	private String custType;
	private String province;
	private EditText edtPromotion;
	private View prbPromotion;

	public GetPromotionReadFileAsytask(Context context, SubscriberDTO sub,
			String custType, String province, EditText edtPromotion,
			View prbPromotion) {
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
						.getString(R.string.not_exists_promotion));
				promotionTypeBeans1.setPromProgramCode("");
				lstData.add(0, promotionTypeBeans1);
				sub.setPromotion(promotionTypeBeans1);
				edtPromotion.setText(promotionTypeBeans1.getCodeName());
			} else {

				PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
				promotionTypeBeans1.setCodeName(context
						.getString(R.string.selectMKM));

				promotionTypeBeans1.setPromProgramCode("-1");
				lstData = new ArrayList<PromotionTypeBeans>();
				lstData.add(0, promotionTypeBeans1);
			}
			sub.setLstPromotion(lstData);
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
		FileInputStream in = null;
		try {
			BCCSGateway input = new BCCSGateway();
//			input.addValidateGateway("username", Constant.BCCSGW_USER);
//			input.addValidateGateway("password", Constant.BCCSGW_PASS);
//			input.addValidateGateway("wscode", "mbccs_getListPromotionByMapSP");
			StringBuilder rawData = new StringBuilder();
			rawData.append(
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

			rawData.append("<soapenv:Header/>");
			rawData.append("<soapenv:Body>");
			rawData.append("<ws:getListPromotionByMapSP>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
			rawData.append("<serviceType>" + sub.getServiceType()
					+ "</serviceType>");
			rawData.append("<custType>" + custType + "</custType>");
			rawData.append("<payType>" + 1 + "</payType>");
			rawData.append("<subType>" + sub.getSubType() + "</subType>");
			rawData.append("<province>" + province + "</province>");
			rawData.append("<district>" + "-1" + "</district>");
			rawData.append("<precinct>" + "-1" + "</precinct>");
			// rawData.append("<actionCode>" + "00" + "</actionCode>");
			rawData.append("</input>");
			rawData.append("</ws:getListPromotionByMapSP>");
			rawData.append("</soapenv:Body>");
			rawData.append("</soapenv:Envelope>");
			Log.i("RowData", rawData.toString());

			Log.i("RowData", rawData.toString());

			String envelope = rawData.toString();
			Log.e("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);

			String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_PROMOTION_DATA,
					Constant.PROMOTION_FILE_NAME);
			if (fileName != null) {

				try {

					File file = new File(fileName);
					if (!file.mkdirs()) {
						file.createNewFile();
					}
					in = new FileInputStream(file);
					PromotionHanlder handler = (PromotionHanlder) CommonActivity.parseXMLHandler(new PromotionHanlder(),
							new InputSource(in));
					 file.delete();
					if (handler != null) {
						if (handler.getItem().getToken() != null && !handler.getItem().getToken().isEmpty()) {
							Session.setToken(handler.getItem().getToken());
						}

						if (handler.getItem().getErrorCode() != null) {
							errorCode = handler.getItem().getErrorCode();
						}
						if (handler.getItem().getDescription() != null) {
							description = handler.getItem().getDescription();
						}
						lisPromotionTypeBeans = handler.getLstData();
					}

				} catch (Exception e) {
					Log.e("Exception", e.toString(), e);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							Log.e("Exception", e.toString(), e);
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.d("getPromotionTypeBeans", ex.toString());
		}

		return lisPromotionTypeBeans;
	}

}
