package com.viettel.bss.viettelpos.v4.customer.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.CMMobileOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class AsyntaskGetListReason extends
		AsyncTask<String, Void, List<ReasonDTO>> {

	private Context context = null;
	private XmlDomParse parse = new XmlDomParse();
	private String errorCode = "";
	private String description = "";

	private ProgressBar progressBar = null;
	private String wsCode = "";
	private final Spinner spnReason;
	private final Button btnRefresh;
	private final String offerId;
	private final String serviceType;

	public AsyntaskGetListReason(Context context, ProgressBar progressBar,
			String wsCode, Spinner spnReason, Button btnRefresh,
			String offerId, String serviceType) {
		this.offerId = offerId;
		this.serviceType = serviceType;
		this.context = context;
		this.progressBar = progressBar;
		this.wsCode = wsCode;
		this.spnReason = spnReason;
		if (this.progressBar != null) {
			this.progressBar.setVisibility(View.VISIBLE);
		}
		this.btnRefresh = btnRefresh;
		if (this.btnRefresh != null) {
			this.btnRefresh.setVisibility(View.GONE);
		}
	}

	@Override
	protected List<ReasonDTO> doInBackground(String... params) {
		return getReasonInfoPos();
	}

	@Override
	protected void onPostExecute(List<ReasonDTO> result) {
		if (this.progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (this.btnRefresh != null) {
			this.btnRefresh.setVisibility(View.VISIBLE);
		}
		if (errorCode.equalsIgnoreCase("0")) {

			if (result == null) {
				result = new ArrayList<>();
			}
			ArrayAdapter<ReasonDTO> adapter = new ArrayAdapter<>(
                    context, R.layout.spinner_item, result);
			adapter.setDropDownViewResource(R.layout.spinner_dropdown);
			spnReason.setAdapter(adapter);

		} else {
			if (errorCode.equals(Constant.INVALID_TOKEN2)) {
				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context, description, context.getResources()
								.getString(R.string.app_name), moveLogInAct);
				dialog.show();
			} else {
				if (description == null || description.isEmpty()) {
					description = context.getString(R.string.checkdes);
				}
				List<ReasonDTO> lstReason = new ArrayList<>();
				ReasonDTO first = new ReasonDTO();
				first.setCode("");
				first.setName(context.getString(R.string.txt_select_reason));
				lstReason.add(first);
				ArrayAdapter<ReasonDTO> adapter = new ArrayAdapter<>(
                        context, R.layout.spinner_item, result);
				adapter.setDropDownViewResource(R.layout.spinner_dropdown);
				spnReason.setAdapter(adapter);
				Dialog dialog = CommonActivity.createAlertDialog(context,
						description,
						context.getResources().getString(R.string.app_name));
				dialog.show();

			}
		}
	}

	private List<ReasonDTO> getReasonInfoPos() {

		List<ReasonDTO> lstReason = null;
		String original = "";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + wsCode);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:").append(wsCode).append(">");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");
			rawData.append("<offerId>").append(offerId).append("</offerId>");
			rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
			rawData.append("</input>");
			rawData.append("</ws:").append(wsCode).append(">");

			Log.i("LOG", "raw data" + rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("LOG", "Send evelop" + envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_" + wsCode);
			Log.i("LOG", "Respone:  " + response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("LOG", "Responseeeeeeeeee Original  " + response);

			// parser

			Serializer serializer = new Persister();
			CMMobileOutput cmMobileOutput = serializer.read(
					CMMobileOutput.class, original);
			errorCode = cmMobileOutput.getErrorCode();
			description = cmMobileOutput.getDescription();
			lstReason = cmMobileOutput.getLstReasonDTO();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstReason;
	}

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();

		}
	};

}
