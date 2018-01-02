package com.viettel.bss.viettelpos.v4.cc.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

public class AsyncGetCustCareDetail extends AsyncTask<Void, Void, CCOutput> {

	private OnPostExecute<CCOutput> onPostExecute;
	private Context context;
	private String isdn;
	private String careType;
	public static final int MAX_RESULT = 10;
	private int firstResult;
	private ProgressDialog progress;
	private String fromDate;
	private String toDate;
	private String status;
	public AsyncGetCustCareDetail(String isdn, String careType,
			String fromDate, String toDate, String status,int firstResult,
			OnPostExecute<CCOutput> onPostExecute, Context context) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.searching_gift_list));
		progress.setCancelable(false);
		progress.show();
		this.isdn = isdn;
		this.firstResult = firstResult;
		this.onPostExecute = onPostExecute;
		this.context = context;
		this.careType = careType;
	}

	@Override
	protected void onPostExecute(CCOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		onPostExecute.onPostExecute(result);

	}

	@Override
	protected CCOutput doInBackground(Void... arg0) {
		return getCustCareSummary();
	}

	private CCOutput getCustCareSummary() {

		String original = "";
		CCOutput result;
		String methodName = "getCustCareDetail";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			if (CommonActivity.validateIsdn(isdn)) {
				isdn = CommonActivity.checkStardardIsdn(isdn);
			}
			rawData.append("<isdn>")
					.append(StringUtils.escapeHtml(CommonActivity
							.getNormalText(isdn))).append("</isdn>");
			rawData.append("<careType>").append(careType).append("</careType>");
			rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
			rawData.append("<toDate>").append(toDate).append("</toDate>");
			rawData.append("<pageIndex>").append(firstResult)
					.append("</pageIndex>");
			rawData.append("<pageSize>").append(MAX_RESULT)
					.append("</pageSize>");
			 rawData.append("<status>").append(status).append("</status>");
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
