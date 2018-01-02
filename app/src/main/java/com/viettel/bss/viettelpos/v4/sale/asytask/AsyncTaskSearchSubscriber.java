package com.viettel.bss.viettelpos.v4.sale.asytask;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class AsyncTaskSearchSubscriber extends
		AsyncTaskCommon<String, Void, List<SubscriberDTO>> {

	public AsyncTaskSearchSubscriber(Activity context,
			OnPostExecuteListener<List<SubscriberDTO>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected List<SubscriberDTO> doInBackground(String... params) {
		String isMobile = "false";
		if(params.length ==3){
			isMobile = params[3];
		}
		return searchForSubscriber(params[0], params[1],isMobile);
	}

	private List<SubscriberDTO> searchForSubscriber(String isdn, String idNo,String isMobile) {
		ChargeContractItem result = null;
		String original = "";
		List<SubscriberDTO> lstSubscribers = new ArrayList();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "sale_searchSubscriber");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:searchSubscriber>");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			if (isdn != null) {
				rawData.append("<isdn>")
						.append(StringUtils.escapeHtml(CommonActivity
								.checkStardardIsdn(isdn))).append("</isdn>");
			}
			if (idNo != null) {
				rawData.append("<idNo>").append(StringUtils.escapeHtml(idNo))
						.append("</idNo>");
			}
			rawData.append("<isMobile>" + isMobile
					+ "</isMobile>");
			
			rawData.append("</input>");
			rawData.append("</ws:searchSubscriber>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "sale_searchSubscriber");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			
			Log.i("Responseeeeeeeeee Original group", response);
			

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class,
					original);
			if(parseOuput != null){
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				lstSubscribers = parseOuput.getBasicSubscriberInfos();
			}
			
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("basicSubscriberInfos");
				
//				Log.i("size result", "" + nodechild.getLength());

//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					SubscriberDTO sub = new SubscriberDTO();
//					sub.setAccount(parse.getValue(e1, "account"));
//					sub.setServiceTypeName(parse.getValue(e1,
//							"telecomServiceName"));
//					sub.setServiceType(parse.getValue(e1,
//							"telecomServiceAlias"));
//					sub.setSubType(parse.getValue(e1, "subType"));
//					sub.setCustType(parse.getValue(e1, "custType"));
//					sub.setOfferId(parse.getValue(e1, "offerId"));
//					sub.setSubId(parse.getValue(e1, "subId"));
//					sub.setProductCode(parse.getValue(e1, "productCode"));
//					sub.setTelecomServiceId(parse.getValue(e1, "telecomServiceId"));
//							"telecomServiceId"));
//					sub.setStatusView(parse.getValue(e1, "statusView"));
//					sub.setActStatusText(parse.getValue(e1, "actStatusText"));
//					sub.setAccountNo(parse.getValue(e1, "accountNo"));
//					sub.setCustomerName(parse.getValue(e1, "customerName"));
//					sub.setIdNo(parse.getValue(e1, "idNo"));
//					sub.setCustId(parse.getValue(e1, "custId"));
					
//					lstSubscribers.add(sub);
//				}
				
//			}

		} catch (Exception e) {
			Log.d("searchForSubscriber", "error: ", e);
		}
		return lstSubscribers;
	}

}
