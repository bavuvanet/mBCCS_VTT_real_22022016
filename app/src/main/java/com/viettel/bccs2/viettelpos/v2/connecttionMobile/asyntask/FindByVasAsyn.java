package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

public class FindByVasAsyn extends AsyncTaskCommonSupper<String, Void, ArrayList<PromotionUnitVas>>{

	public FindByVasAsyn(Activity context, OnPostExecuteListener<ArrayList<PromotionUnitVas>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
	}

	@Override
	protected ArrayList<PromotionUnitVas> doInBackground(String... arg0) {
		return findByVas(arg0[0]);
	}
	
	private ArrayList<PromotionUnitVas> findByVas(String productCode){
		ArrayList<PromotionUnitVas> lstPromotionUnitVas = new ArrayList<PromotionUnitVas>();
		String original = "";
		ParseOuput result = null;
		try {
			String methodName = "findByVas";
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<productCode>" + productCode + "</productCode>");

			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.i("envelope", envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_findByVas");
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
				lstPromotionUnitVas = result.getLstMapVasPromotionUnitDTO();
			}else{
				description = mActivity.getString(R.string.no_return_from_system);
				errorCode = Constant.ERROR_CODE;
			}
		} catch (Exception e) {
			Log.e("getListIP", e.toString() + "description error", e);
			description = mActivity.getString(R.string.exception)+ e.toString();
			errorCode = Constant.ERROR_CODE;
		}
		return lstPromotionUnitVas;
	}

}
