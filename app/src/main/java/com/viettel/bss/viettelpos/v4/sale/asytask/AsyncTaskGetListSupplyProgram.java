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
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyBaseBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyProgramBean;

public class AsyncTaskGetListSupplyProgram extends AsyncTaskCommon<String, Void, List<SupplyProgramBean>> {

	public AsyncTaskGetListSupplyProgram(Activity context,
			OnPostExecuteListener<List<SupplyProgramBean>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<SupplyProgramBean> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return getListSupplyProgram(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4]);
	}

	private List<SupplyProgramBean> getListSupplyProgram(String reasonId, String telecomServiceId, String productCode, 
			String proOfferId, String supplyMethodCode) {
		String original = "";
		List<SupplyProgramBean> result = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getSupplyInfoV1");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getSupplyInfoV1>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
			rawData.append("<telecomServiceId>").append(telecomServiceId).append("</telecomServiceId>");
			rawData.append("<prodOfferId>").append(proOfferId).append("</prodOfferId>");
			rawData.append("<productCode>").append(productCode).append("</productCode>");
			rawData.append("<supplyMethodCode>").append(supplyMethodCode).append("</supplyMethodCode>");
			rawData.append("<isSupplyMethod>false</isSupplyMethod>");

			rawData.append("</input>");
			rawData.append("</ws:getSupplyInfoV1>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL, mActivity,
					"mbccs_getSupplyInfoV1");
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
				nodeChild = doc.getElementsByTagName("lstSupplyProgram");
				for (int j = 0; j < nodeChild.getLength(); j++) {
					Element e1 = (Element) nodeChild.item(j);
					
					SupplyProgramBean supplyProgramBean = new SupplyProgramBean();
					supplyProgramBean.setProgramCode(parse.getValue(e1, "code"));
					supplyProgramBean.setProgramName(parse.getValue(e1, "name"));

					List<SupplyBaseBean> lstSupplyBaseBean = supplyProgramBean.getLstSupplyBaseBean();
					
					NodeList supplyMonthLstNodes = e1.getElementsByTagName("supplyMonthLst");
					for (int k = 0; k < supplyMonthLstNodes.getLength(); k++) {
						SupplyBaseBean supplyBaseBean = new SupplyBaseBean();
						supplyBaseBean.setPrice(parse.getValue(e1, "price"));
						supplyBaseBean.setProgramMonth(parse.getValue(e1, "programMonth"));
						lstSupplyBaseBean.add(supplyBaseBean);
					}
					
					result.add(supplyProgramBean);
				}
			}
		} catch (Exception e) {
			Log.d("mbccs_getListApproveFinance", e.toString()
					+ "description error", e);
		}

		return result;

	}

}
