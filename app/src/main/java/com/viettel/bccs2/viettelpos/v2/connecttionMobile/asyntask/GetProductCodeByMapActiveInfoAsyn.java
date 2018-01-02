package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.InputSource;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.ProductOfferringHanlder;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class GetProductCodeByMapActiveInfoAsyn extends AsyncTaskCommon<String, Void, ArrayList<ProductOfferingDTO>> {
	private boolean isSmartSim = false;

	public GetProductCodeByMapActiveInfoAsyn(Activity context,
			OnPostExecuteListener<ArrayList<ProductOfferingDTO>> listener, OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	public GetProductCodeByMapActiveInfoAsyn(Activity context,
			OnPostExecuteListener<ArrayList<ProductOfferingDTO>> listener, OnClickListener moveLogInAct,
			boolean isSmart) {
		super(context, listener, moveLogInAct);
		this.isSmartSim = isSmart;
	}

	@Override
	protected ArrayList<ProductOfferingDTO> doInBackground(String... arg0) {
		return getListProductCode(arg0[0], arg0[1], arg0[2], arg0[3]);
	}

	private ArrayList<ProductOfferingDTO> getListProductCode(String telecomServiceId, String payType,
			String actionCode, String offerId) {
		String original = null;
		ArrayList<ProductOfferingDTO> lisPakageChargeBeans = new ArrayList<ProductOfferingDTO>();
		FileInputStream in = null;
		try {

			// String key = serviceType + "_" + subType;
			// if (mapPakage != null && mapPakage.containsKey(key)) {
			// if (mapPakage.get(key) != null) {
			// lisPakageChargeBeans = mapPakage.get(key);
			// errorCode = "0";
			// return lisPakageChargeBeans;
			// }
			// }

			BCCSGateway input = new BCCSGateway();
			// input.addValidateGateway("username", Constant.BCCSGW_USER);
			// input.addValidateGateway("password", Constant.BCCSGW_PASS);
			// input.addValidateGateway("wscode",
			// "mbccs_findProductOfferingByTelecomService");
			StringBuilder rawData = new StringBuilder();
			rawData.append(
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

			rawData.append("<soapenv:Header/>");
			rawData.append("<soapenv:Body>");
			rawData.append("<ws:getProductCodeByMapActiveInfo>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("<telecomServiceId>" + telecomServiceId);
			rawData.append("</telecomServiceId>");
			rawData.append("<payType>" + payType);
			rawData.append("</payType>");

			rawData.append("<actionCode>" + "33");
			rawData.append("</actionCode>");

			rawData.append("<isSmart>" + isSmartSim);
			rawData.append("</isSmart>");

			rawData.append("<offerId>" + offerId);
			rawData.append("</offerId>");
			
			
			rawData.append("</input>");
			rawData.append("</ws:getProductCodeByMapActiveInfo>");
			rawData.append("</soapenv:Body>");
			rawData.append("</soapenv:Envelope>");
			Log.i("RowData", rawData.toString());
			String envelope = rawData.toString();
			Log.e("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);

			String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_PAKAGE_DATA_BCCS,
					Constant.PAKAGE_FILE_NAME);
			if (fileName != null) {

				try {

					File file = new File(fileName);
//					if (!file.mkdirs()) {
//						file.createNewFile();
//					}
					in = new FileInputStream(file);
					ProductOfferringHanlder handler = (ProductOfferringHanlder) CommonActivity
							.parseXMLHandler(new ProductOfferringHanlder(), new InputSource(in));
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
						lisPakageChargeBeans = handler.getLstData();

						// mapPakage = new HashMap<String,
						// ArrayList<ProductOfferingDTO>>();
						// mapPakage.put(key, lisPakageChargeBeans);
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

		} catch (Exception e) {
			Log.e("Exception", e.toString(), e);
		}

		return lisPakageChargeBeans;

	}
}
