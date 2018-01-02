package com.viettel.bss.viettelpos.v4.report.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.BonusOutput;

public class GetTotalMoneyBonus extends AsyncTask<String, Void, BonusOutput> {

	private Context context;
	private final ProgressDialog progress;
	private final String billCycle;
	private final String reportType;
	private final String fromDate;
	private final String toDate;
	private final TextView tvAnypay;
	private final TextView tvCash;

	public GetTotalMoneyBonus(Context context, String billCycle,
			String reportType, String fromDate, String toDate,
			TextView tvAnypay, TextView tvCash) {
		super();
		this.context = context;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getResources().getString(
				R.string.getdataing));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}

		this.context = context;
		this.billCycle = billCycle;
		this.reportType = reportType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.tvAnypay = tvAnypay;
		this.tvCash = tvCash;
	}

	@Override
	protected BonusOutput doInBackground(String... params) {

		return getTotalMoneyBonus();
	}

	@Override
	protected void onPostExecute(BonusOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			String strCash = result.getDescription().split(";")[0];
			String strAnypay = result.getDescription().split(";")[1];
			tvCash.setText(strCash);
			tvAnypay.setText(StringUtils.formatMoney(strAnypay));
		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context,
						result.getDescription(),";report_revenue;");
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(context,
						result.getDescription(),
						context.getString(R.string.app_name));
				dialog.show();

			}
		}

	}

	private BonusOutput getTotalMoneyBonus() {

		String original = "";
		BonusOutput bonusOutput;
		String methodName = "getTotalMoneyBonus";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:").append(methodName).append(">");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<token>").append(Session.getToken()).append("</token>");
			rawData.append("<billCycle>").append(billCycle).append("</billCycle>");
			rawData.append("<reportType>").append(reportType).append("</reportType>");
			rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
			rawData.append("<toDate>").append(toDate).append("</toDate>");

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
			bonusOutput = serializer.read(BonusOutput.class, original);
			if (bonusOutput == null) {
				bonusOutput = new BonusOutput();
				bonusOutput.setDescription(context
						.getString(R.string.no_return_from_system));
				bonusOutput.setErrorCode(Constant.ERROR_CODE);
				return bonusOutput;
			} else {
				return bonusOutput;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			bonusOutput = new BonusOutput();
			bonusOutput.setDescription(e.getMessage());
			bonusOutput.setErrorCode(Constant.ERROR_CODE);
		}

		return bonusOutput;
	}

}
