package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class GetReasonFullPMAsyn extends AsyncTaskCommon<String, Void, List<ReasonDTO>>{
	private String payType;

	public GetReasonFullPMAsyn(Activity context, OnPostExecuteListener<List<ReasonDTO>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected List<ReasonDTO> doInBackground(String... arg0) {
		return getListReasonDTO(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5],arg0[6]);
	}

	
	private List<ReasonDTO> getListReasonDTO(String offerId, String actionCode, String serviceType, String cusType, String subType,String technology, String promotionCode) {
		String original = null;
		List<ReasonDTO> lstReasonDTO = new ArrayList<ReasonDTO>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getReasonFullPM");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getReasonFullPM>");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			if(CommonActivity.isNullOrEmpty(payType)) {
				rawData.append("<payType>" + 1 + "</payType>");
			} else {
				rawData.append("<payType>" + payType + "</payType>");
			}
			rawData.append("<offerId>" + offerId + "</offerId>");
			// 11 doi sim 1015 ||  537 la lay ly do doi khuyen mai
			rawData.append("<actionCode>" + actionCode + "</actionCode>");
			rawData.append("<serviceType>" + serviceType + "</serviceType>");
			if(!CommonActivity.isNullOrEmpty(cusType)) {
				rawData.append("<cusType>" + cusType + "</cusType>");
			}
			if(!CommonActivity.isNullOrEmpty(subType)) {
				rawData.append("<subType>" + subType + "</subType>");
			}

			if(!CommonActivity.isNullOrEmpty(technology)){
				rawData.append("<technology>" + technology + "</technology>");
			}
			if(!CommonActivity.isNullOrEmpty(promotionCode)){
				rawData.append("<promotionCode>" + promotionCode + "</promotionCode>");
			}
			
			rawData.append("</input>");
			rawData.append("</ws:getReasonFullPM>");
			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity,
					"mbccs_getReasonFullPM");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);

			original = output.getOriginal();
			Log.d("originalllllllll", original);

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				lstReasonDTO = parseOuput.getLstReasonDTO();
			}

			return lstReasonDTO;
		} catch (Exception e) {
			Log.d("getReasonFullPM", e.toString());
		}

		return null;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
