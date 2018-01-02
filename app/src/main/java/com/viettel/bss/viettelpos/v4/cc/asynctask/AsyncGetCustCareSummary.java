package com.viettel.bss.viettelpos.v4.cc.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;

public class AsyncGetCustCareSummary extends AsyncTask<Void, Void, CCOutput> {

	private OnPostExecute<CCOutput> onPostExecute;
	private Context context;

	public AsyncGetCustCareSummary(OnPostExecute<CCOutput> onPostExecute,
			Context context) {
		super();
		this.onPostExecute = onPostExecute;
		this.context = context;
	}

	@Override
	protected void onPostExecute(CCOutput result) {
		super.onPostExecute(result);
		onPostExecute.onPostExecute(result);

	}

	@Override
	protected CCOutput doInBackground(Void... arg0) {
		return getCustCareSummary();
	}

	private CCOutput getCustCareSummary() {

		String original = "";
		CCOutput result;
		String methodName = "getCustCareSummary";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");

			// if (CommonActivity.validateIsdn(isdn)) {
			// isdn = CommonActivity.checkStardardIsdn(isdn);
			// }
			// rawData.append("<token>").append(isdn).append("</token>");
			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
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
			result = serializer.read(CCOutput.class, original);
			if (result == null) {
				result = new CCOutput();
				result.setDescription(context
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
				return result;
			} else {
				return result;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			result = new CCOutput();
			result.setDescription(e.getMessage());
			result.setErrorCode(Constant.ERROR_CODE);
		}

		return result;
	}

}
