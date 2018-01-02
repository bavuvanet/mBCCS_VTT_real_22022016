package com.viettel.bss.viettelpos.v4.sale.asytask;

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

public class AsyncTaskChangeEquipment extends AsyncTaskCommon<Void, Void, String> {
	
	private final List<SubGoods> lstSubGoods;
	private final List<SubGoods> lstSubGoodsNew;
	private final String isdn;
	private final String reasonId;
	private final boolean createTaskForTeam;

	public AsyncTaskChangeEquipment(Activity context,
			OnPostExecuteListener<String> listener,
			OnClickListener moveLogInAct, String isdn, String reasonId,
			boolean createTaskForTeam,
			List<SubGoods> lstSubGoods, List<SubGoods> lstSubGoodsNew) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
		
		this.lstSubGoods = lstSubGoods;
		this.lstSubGoodsNew = lstSubGoodsNew;
		this.isdn = isdn;
		this.reasonId = reasonId;
		this.createTaskForTeam = createTaskForTeam;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		return changeEquipment();
	}

	private String changeEquipment() {
		String original = "";
		String result = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "sale_changeEquipmentForSub");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:changeEquipmentForSub>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<isdn>").append(isdn).append("</isdn>");
			rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
			rawData.append("<createTaskForTeam>").append(createTaskForTeam).append("</createTaskForTeam>");
			
			for (SubGoods subGoods: lstSubGoods) {
				if (subGoods.isRetrieveGoods()) {
					rawData.append("<lstStockModelToRetrieve>");
					rawData.append("<subGoodsId>").append(subGoods.getSubGoodsId()).append("</subGoodsId>");
					if (!subGoods.isVirtualGoods() && subGoods.isAllowSerial()) {
						rawData.append("<serialToRetrieve>").append(subGoods.getSerialToRetrieve()).append("</serialToRetrieve>");
					}
					rawData.append("</lstStockModelToRetrieve>");
				}
			}
			for (SubGoods subGoods: lstSubGoodsNew) {
				rawData.append("<lstStockSelectedToSale>");
				rawData.append("<stockModelId>").append(subGoods.getStockModelId()).append("</stockModelId>");
				rawData.append("<reclaimCommitmentCode>").append(subGoods.getSupplyMethodCode()).append("</reclaimCommitmentCode>");
				if (subGoods.getSupplyProgramCode() != null) {
					rawData.append("<reclaimProCode>").append(subGoods.getSupplyProgramCode()).append("</reclaimProCode>");
				}
				if (subGoods.getProgramMonth() != null) {
					rawData.append("<reclaimCommitmentTime>").append(subGoods.getProgramMonth()).append("</reclaimCommitmentTime>");
				}
				if (subGoods.getPayMethod() != null) {
					rawData.append("<reclaimPayMethod>").append(subGoods.getPayMethod()).append("</reclaimPayMethod>");
				}
				rawData.append("<serial>").append(subGoods.getSerial()).append("</serial>");
				rawData.append("</lstStockSelectedToSale>");
			}

			rawData.append("</input>");
			rawData.append("</ws:changeEquipmentForSub>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL, mActivity,
					"mbccs_getReasonFullPM");
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
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return result;

	}

}
