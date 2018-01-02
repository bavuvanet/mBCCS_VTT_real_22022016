package com.viettel.bss.viettelpos.v4.sale.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.dialog.ChooseTransTypeDialog;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

public class GetBankPlusTransType extends AsyncTask<String, Void, SaleOutput> {
	private final Context context;
	private final ProgressDialog progress;
	private final List<FormBean> lstBankTransType;
	private final TextView tvTransSelected;

	public GetBankPlusTransType(Context context,
			List<FormBean> lstBankTransType, TextView tvTransSelected) {
		this.lstBankTransType = lstBankTransType;
		this.tvTransSelected = tvTransSelected;
		this.context = context;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getResources().getString(
				R.string.getting_trans_type));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}

	}

	@Override
	protected SaleOutput doInBackground(String... arg0) {
		return getLstAccountBookBankPlusRequestType();
	}

	private SaleOutput getLstAccountBookBankPlusRequestType() {
		String original = "";
		SaleOutput out;
		String methodName = "getLstAccountBookBankPlusRequestType";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:").append(methodName).append(">");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<").append(methodName).append(">");
			rawData.append("<pageIndex>0");

			rawData.append("</pageIndex>");
			rawData.append("</").append(methodName).append(">");
			rawData.append("</input>");
			rawData.append("</ws:").append(methodName).append(">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", response);

			// parser
			Serializer serializer = new Persister();
			out = serializer.read(SaleOutput.class, original);
			if (out == null) {
				out = new SaleOutput();
				out.setDescription(context
						.getString(R.string.no_return_from_system));
				out.setErrorCode(Constant.ERROR_CODE);
				return out;
			} else {
				return out;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			out = new SaleOutput();
			out.setDescription(e.getMessage());
			out.setErrorCode(Constant.ERROR_CODE);
		}

		return out;
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			if (!CommonActivity.isNullOrEmpty(result.getLstFormBean())) {
				lstBankTransType.clear();
				lstBankTransType.addAll(result.getLstFormBean());
				ChooseTransTypeDialog dialog = new ChooseTransTypeDialog(
						context, lstBankTransType, tvTransSelected);
				dialog.show();
			}
		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context,
						result.getDescription(), "");
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(context,
						result.getDescription(),
						context.getString(R.string.app_name));
				dialog.show();

			}
		}

	}
}
