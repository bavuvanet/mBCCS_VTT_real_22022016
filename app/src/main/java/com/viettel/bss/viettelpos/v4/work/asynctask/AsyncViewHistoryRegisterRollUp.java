package com.viettel.bss.viettelpos.v4.work.asynctask;

import java.util.Date;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.RollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;
import com.viettel.bss.viettelpos.v4.work.object.SaleProgramDTO;

public class AsyncViewHistoryRegisterRollUp extends
		AsyncTask<Void, Void, RollUpOutput> {

	private OnPostExecute<RollUpOutput> onPostExecute;
	private Context context;
	private ProgressDialog progress;
	private String programId;
	private String fromDate;
	private String toDate;

	public AsyncViewHistoryRegisterRollUp(String programId, String fromDate,
			String toDate, OnPostExecute<RollUpOutput> onPostExecute,
			Context context) {
		super();
		this.programId = programId;
		progress = new ProgressDialog(context);
		progress.setMessage(context
				.getString(R.string.getting_register_roll_up_history));
		progress.setCancelable(false);
		progress.show();
		this.onPostExecute = onPostExecute;
		this.context = context;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	protected void onPostExecute(RollUpOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			onPostExecute.onPostExecute(result);
		} else if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
			CommonActivity.BackFromLogin((Activity) context,
					result.getDescription(), "m.roll.up");
		} else {
			CommonActivity.createAlertDialog(context, result.getDescription(),
					context.getString(R.string.app_name)).show();

		}
	}

	@Override
	protected RollUpOutput doInBackground(Void... arg0) {
		return doAction();
	}

	private RollUpOutput doAction() {

		String original = "";
		RollUpOutput result;
		String methodName = "viewHistoryRegisterRollUp";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "pos2_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			if (!CommonActivity.isNullOrEmpty(programId)) {
				rawData.append("<programId>" + programId + "</programId>");
			}
			if (!CommonActivity.isNullOrEmptyArray(fromDate, toDate)) {
				Date from = DateTimeUtils.convertStringToTime(fromDate);
				rawData.append("<fromDate>"
						+ DateTimeUtils.convertDateSendOverSoap(from)
						+ "</fromDate>");
				Date to = DateTimeUtils.convertStringToTime(toDate);
				rawData.append("<toDate>"
						+ DateTimeUtils.convertDateSendOverSoap(to)
						+ "</toDate>");
			}

			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "pos2_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();

			// parser
			Serializer serializer = new Persister();
			result = serializer.read(RollUpOutput.class, original);
			if (result == null || result.getErrorCode() == null) {
				result = new RollUpOutput();
				result.setDescription(context
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
				return result;
			} else {
				return result;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			result = new RollUpOutput();
			result.setDescription(e.getMessage());
			result.setErrorCode(Constant.ERROR_CODE);
		}
		return result;
	}
}