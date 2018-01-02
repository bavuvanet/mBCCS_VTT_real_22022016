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
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.work.object.RegisterRollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;

public class AsyncRegisterRollUp extends AsyncTask<Void, Void, RollUpOutput> {

	private OnPostExecute<RollUpOutput> onPostExecute;
	private Context context;
	private ProgressDialog progress;
	private RegisterRollUpBO bo;

	public AsyncRegisterRollUp(RegisterRollUpBO bo,
			OnPostExecute<RollUpOutput> onPostExecute, Context context) {
		super();
		this.bo = bo;
		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.registing_roll_up));
		progress.setCancelable(false);
		progress.show();
		this.onPostExecute = onPostExecute;
		this.context = context;
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
		String methodName = "registerRollUp";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "pos2_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<registerBO>");
			rawData.append("<programId>" + bo.getProgramId() + "</programId>");
			rawData.append("<note>"
					+ StringUtils.escapeHtml(bo.getNote().trim()) + "</note>");
			rawData.append("<address>"
					+ StringUtils.escapeHtml(bo.getAddress().trim())
					+ "</address>");
			Date from = DateTimeUtils.convertStringToTime(bo
					.getRegisterFromDate());
			Date to = DateTimeUtils.convertStringToTime(bo.getRegisterToDate());
			rawData.append("<registerFromDate>"
					+ DateTimeUtils.convertDateSendOverSoap(from)
					+ "</registerFromDate>");
			rawData.append("<registerToDate>"
					+ DateTimeUtils.convertDateSendOverSoap(to)
					+ "</registerToDate>");
			rawData.append("</registerBO>");
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