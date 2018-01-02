package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

 public class AsynGetCustomerByCustId extends AsyncTaskCommon<String, Void, CustomerDTO> {
	private Activity context;
	public AsynGetCustomerByCustId(Activity context, OnPostExecuteListener<CustomerDTO> listener,
								   View.OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		this.context = context;
	}
	@Override
	protected CustomerDTO doInBackground(String... params) {
		return getCustomerByCustId(params[0]);
	}


	private CustomerDTO getCustomerByCustId(String custId) {

		CustomerDTO customerDTO = new CustomerDTO();
		String original = "";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getCustomerByCustId");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getCustomerByCustId>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));

			rawData.append("<custId>").append(custId);
			rawData.append("</custId>");

			rawData.append("</input>");
			rawData.append("</ws:getCustomerByCustId>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
					"mbccs_getCustomerByCustId");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.d("original", original);
			// ====== parse xml ===================

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				customerDTO = parseOuput.getCustomerDTO();
			}

			return customerDTO;

		} catch (Exception e) {
			Log.e("getCustomerByCustId + exception", e.toString(), e);
		}
		return customerDTO;

	}



}
