package com.viettel.bss.viettelpos.v4.sale.asytask;

import java.util.ArrayList;
import java.util.List;

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
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;

public class AsyncTaskGetListGoodsByReason extends AsyncTaskCommon<String, Void, List<SubGoods>> {

	public AsyncTaskGetListGoodsByReason(Activity context,
			OnPostExecuteListener<List<SubGoods>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<SubGoods> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return getListSubGoods(arg0[0], arg0[1], arg0[2]);
	}

	private List<SubGoods> getListSubGoods(String reasonId, String telecomServiceId, String productCode) {
		String original = "";
		List<SubGoods> lstSaleServiceModels = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getSaleServicesAdvBOBySSCode");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getSaleServicesAdvBOBySSCode>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
			rawData.append("<telecomServiceId>").append(telecomServiceId).append("</telecomServiceId>");
			rawData.append("<productCode>").append(productCode).append("</productCode>");

			rawData.append("</input>");
			rawData.append("</ws:getSaleServicesAdvBOBySSCode>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL, mActivity,
					"mbccs_getSaleServicesAdvBOBySSCode");
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
				nodeChild = doc.getElementsByTagName("lstSaleServiceModelAdvanceDTO");
				for (int j = 0; j < nodeChild.getLength(); j++) {
					Element e1 = (Element) nodeChild.item(j);
					
					SubGoods subGoods = new SubGoods();
					subGoods.setSaleServiceModelId(parse.getValue(e1, "prodPackTypeId"));
					subGoods.setSaleServiceModelName(parse.getValue(e1, "productOfferTypeName"));
					
					String isVirtual = parse.getValue(e1, "isVirtual");
					if ("true".equals(isVirtual)) {
						subGoods.setVirtualGoods(true);
					} else {
						subGoods.setVirtualGoods(false);
					}
					
					List<Spin> lstSaleServiceDetails = new ArrayList<>();
					
					NodeList saleServiceDetailNodes = e1.getElementsByTagName("listSaleServiceDetail");
					for (int k = 0; k < saleServiceDetailNodes.getLength(); k++) {
						Element saleServiceDetailNode = (Element) saleServiceDetailNodes.item(k);
						
						Spin saleServiceDetail = new Spin();
						saleServiceDetail.setId(parse.getValue(saleServiceDetailNode, "productOfferingId"));
						saleServiceDetail.setName(parse.getValue(saleServiceDetailNode, "offerName"));
						lstSaleServiceDetails.add(saleServiceDetail);
					}
					subGoods.setLstSaleServiceDetail(lstSaleServiceDetails);

					lstSaleServiceModels.add(subGoods);
				}
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return lstSaleServiceModels;

	}

}
