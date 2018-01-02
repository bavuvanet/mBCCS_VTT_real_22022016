package com.viettel.bss.viettelpos.v4.work.asynctask;

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
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;

public class AsyncCancelRegisterRollUp extends
		AsyncTask<Void, Void, RollUpOutput> {

	private OnPostExecute<RollUpOutput> onPostExecute;
	private Context context;
	private ProgressDialog progress;
	private String registerRollUpId;

	public AsyncCancelRegisterRollUp(String registerRollUpId,
			OnPostExecute<RollUpOutput> onPostExecute, Context context) {
		super();
		this.registerRollUpId = registerRollUpId;
		progress = new ProgressDialog(context);
		progress.setMessage(context
				.getString(R.string.canceling_register_roll_up));
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
		String methodName = "cancelRegisterRollUp";
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "pos2_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<registerRollUpId>" + registerRollUpId
					+ "</registerRollUpId>");

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