package com.viettel.bss.viettelpos.v4.customer.asynctask;

import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

public class CheckCusQuotaAsync extends AsyncTask<String, Void, String> {

	private Context context;
	private String errorCode;
	private String description;
	private ProgressDialog progress;
	private String idNo;

	public CheckCusQuotaAsync(Context context, String idNo) {
		this.context = context;
		this.idNo = idNo;
		this.progress = new ProgressDialog(this.context);
		// check font
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getResources().getString(
				R.string.checking_cus_quota));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		return checkQuotaCus();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (!"0".equals(errorCode)) {
			if (Constant.INVALID_TOKEN2.equals(errorCode)
					&& description != null && !description.isEmpty()) {
				CommonActivity.BackFromLogin((Activity) context, description);
			} else {
				if (description == null || description.isEmpty()) {
					description = context.getString(R.string.checkdes);
				}

				// tra truoc

				// OnClickListener onclick = new OnClickListener() {
				//
				// @Override
				// public void onClick(View arg0) {
				// if (!CommonActivity.isNullOrEmpty(telecomserviceId)) {
				// GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(
				// activity, false);
				// getListPakageAsyn
				// .execute(telecomserviceId, subType);
				// }
				//
				// }
				// };
				Dialog dialog = CommonActivity.createAlertDialog(context,
						description, context.getString(R.string.app_name));
				dialog.show();

			}
		}
	}

	private String checkQuotaCus() {
		String original = "";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_checkQuotaSub");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:checkQuotaSub>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));

			rawData.append("<idNo>" + idNo);
			rawData.append("</idNo>");
			// }
			rawData.append("<telecomServiceId>" + "1");
			rawData.append("</telecomServiceId>");
			rawData.append("<payType>" + "2");
			rawData.append("</payType>");

			rawData.append("</input>");
			rawData.append("</ws:checkQuotaSub>");

			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_checkQuotaSub");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);

			original = output.getOriginal();
			Log.i("original 69696", "original :" + original);

			// === parrse xml =====
			Serializer serializer = new Persister();
			SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

			if (saleOutput != null) {
				errorCode = saleOutput.getErrorCode();
				description = saleOutput.getDescription();
			} else {
				errorCode = Constant.ERROR_CODE;
				description = context.getString(R.string.no_data_return);
			}
		} catch (Exception e) {
			Log.d("checkResource", e.toString());
			errorCode = Constant.ERROR_CODE;
			description = context.getString(R.string.no_data_return);
		}
		return null;
	}
}
