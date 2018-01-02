package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.VasResponseBO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

public class GetListVasAllAsyn extends AsyncTaskCommon<String, Void, VasResponseBO>{

	public GetListVasAllAsyn(Activity context, OnPostExecuteListener<VasResponseBO> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected VasResponseBO doInBackground(String... arg0) {
		return getListVasAll(arg0[0],arg0[1]);
	}

	private VasResponseBO getListVasAll(String productCode, String subId){
		VasResponseBO vasResponseBO = new VasResponseBO();
		String original = "";
		ParseOuput result = null;
		try{
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getListAllVAS");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getListAllVAS>");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
		
			rawData.append("<productCode>" + productCode
					+ "</productCode>");
			rawData.append("<subId>" + subId
					+ "</subId>");

			rawData.append("</input>");
			rawData.append("</ws:getListAllVAS>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.i("envelope", envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_getListAllVAS");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
			// parser
			Serializer serializer = new Persister();
			result = serializer.read(ParseOuput.class, original);
			
			if(result != null){
				errorCode = result.getErrorCode();
				description = result.getDescription();
				vasResponseBO = result.getVasResponseBO();
			}else{
				description = mActivity.getString(R.string.no_return_from_system);
				errorCode = Constant.ERROR_CODE;
			}
			
			
		}catch (Exception e) {
			Log.d("exception ", e.toString());
		}
		
		return vasResponseBO;
	}
	
	
	
}
