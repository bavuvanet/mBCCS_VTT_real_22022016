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
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;

public class AsyncTaskGetListSubGoods extends
		AsyncTaskCommon<String, Void, List<SubGoods>> {

	public AsyncTaskGetListSubGoods(Activity context,
			OnPostExecuteListener<List<SubGoods>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<SubGoods> doInBackground(String... arg0) {
		return getListSubGoods(arg0[0]);
	}

	private List<SubGoods> getListSubGoods(String isdn) {
		String original = "";
		ArrayList<SubGoods> lstSubGoods = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "sale_getSubGoodsFromIsdn");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getSubGoodFromIsdn>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<isdn>").append(isdn);
			rawData.append("</isdn>");

			rawData.append("</input>");
			rawData.append("</ws:getSubGoodFromIsdn>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "sale_getSubGoodsFromIsdn");
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
				nodeChild = doc.getElementsByTagName("lstSubGoodsDTOs");
				for (int j = 0; j < nodeChild.getLength(); j++) {
					Element e1 = (Element) nodeChild.item(j);
					SubGoods subGoods = new SubGoods();
					subGoods.setSubGoodsId(parse.getValue(e1,
							"subGoodsId"));
					subGoods.setStockModelName(parse.getValue(e1,
							"stockModelName"));
					subGoods.setStockModelCode(parse.getValue(e1,
							"stockModelCode"));
					subGoods.setReclaimPayMethodName(parse.getValue(e1,
							"reclaimCommitmentName"));
					subGoods.setSerial(parse.getValue(e1, "serial"));

					String smartCode1 = parse.getValue(e1, "smartCode1");
					if (subGoods.getSerial() != null && subGoods.getSerial().equals(smartCode1)) {
						subGoods.setVirtualGoods(true);
					} else {
						subGoods.setVirtualGoods(false);
					}
					subGoods.setAllowSerial(true);
					
					lstSubGoods.add(subGoods);
				}
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return lstSubGoods;

	}
}
