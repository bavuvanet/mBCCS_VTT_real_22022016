package com.viettel.bss.viettelpos.v4.bankplus.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

public class CreateBankPlusAsyncTask extends
		AsyncTaskCommon<String, String, BankPlusOutput> {

	private String data;

	public CreateBankPlusAsyncTask(String data, Activity context,
			OnPostExecuteListener<BankPlusOutput> listener,
			OnClickListener moveLogInAct) {

		super(context, listener, moveLogInAct);

		Log.i("createBankplusTrans", data);
		this.data = data;
	}

	public CreateBankPlusAsyncTask(String data, Activity context,
			OnPostExecuteListener<BankPlusOutput> listener,
			OnClickListener moveLogInAct, String message) {

		super(context, listener, moveLogInAct, message);

		Log.i("createBankplusTrans", data);
		this.data = data;
	}

	@Override
	protected BankPlusOutput doInBackground(String... arg) {
		// TODO Auto-generated method stub
		return createTransBankPlus();
	}

	private BankPlusOutput createTransBankPlus() {
		BankPlusOutput result = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_createTransBankPlus"); //pos2_createTransBankPlus
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:createTransBankPlus>");

			rawData.append("<input>");
			rawData.append(input.buildBankPlusXml(
					mActivity, Session.token, data));
			rawData.append("</input>");

			rawData.append("</ws:createTransBankPlus>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.i("envelope", envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_createTransBankPlus");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			String original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);

			// parser
			Serializer serializer = new Persister();
			result = serializer.read(BankPlusOutput.class, original);
			if (result != null) {
				errorCode = result.getErrorCode();
				description = result.getDescription();
			} else {
				description = mActivity
						.getString(R.string.no_return_from_system);
				errorCode = Constant.ERROR_CODE;
			}

		} catch (Exception e) {
			Log.e("exception ", "Exception ", e);
		}
		return result;
	}
}
