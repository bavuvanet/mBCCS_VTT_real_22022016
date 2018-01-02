package com.viettel.bss.viettelpos.v4.charge.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;

public class AsyncSearchVerify extends AsyncTask<String, Void, PaymentOutput> {

	private Context context;
	private ProgressDialog progress;
	private String fromVerifyDate;
	private String toVerifyDate;
	private String fromStartDate;
	private String toStartDate;
	private String verifyType;
	private String isdn;
	private int pageIndex;
	private int pageSize;

	private OnPostSuccessExecute<PaymentOutput> onPost;

	public AsyncSearchVerify(Context context, String fromVerifyDate,
			String toVerifyDate, String fromStartDate, String toStartDate,
			String verifyType, String isdn, int pageIndex, int pageSize,
							 OnPostSuccessExecute<PaymentOutput> onPost) {
		super();
		this.isdn = isdn;
		this.context = context;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getResources().getString(
				R.string.getting_contract_veriry));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}

		this.context = context;
		this.fromStartDate = fromStartDate;
		this.toVerifyDate = toVerifyDate;
		this.fromVerifyDate = fromVerifyDate;
		this.toVerifyDate = toVerifyDate;
		this.toStartDate = toStartDate;
		this.verifyType = verifyType;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.isdn = isdn;
		this.onPost = onPost;
	}

	@Override
	protected PaymentOutput doInBackground(String... params) {

		return searchVerify();
	}

	@Override
	protected void onPostExecute(final PaymentOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			onPost.onPostSuccess(result);
		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context,
						result.getDescription(), ";verify_update;");
			} else {
				String des = result.getDescription();
				if (CommonActivity.isNullOrEmpty(des)) {
					des = context.getString(R.string.no_data);
				}
				Dialog dialog = CommonActivity.createAlertDialog(context, des,
						context.getString(R.string.app_name));
				dialog.show();

			}
		}

	}

	private PaymentOutput searchVerify() {

		String original = "";
		PaymentOutput resultOutput;
		String methodName = "searchVerify";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken())
					.append("</token>");
			if (!CommonActivity.isNullOrEmpty(fromStartDate)) {
				rawData.append("<fromDate>").append(fromStartDate)
						.append("</fromDate>");
			}
			if (!CommonActivity.isNullOrEmpty(toStartDate)) {
				rawData.append("<toDate>").append(toStartDate)
						.append("</toDate>");
			}
			if (!CommonActivity.isNullOrEmpty(fromVerifyDate)) {
				rawData.append("<fromVerifyDate>").append(fromVerifyDate)
						.append("</fromVerifyDate>");
			}

			if (!CommonActivity.isNullOrEmpty(toVerifyDate)) {
				rawData.append("<toVerifyDate>").append(toVerifyDate)
						.append("</toVerifyDate>");
			}
			if (!CommonActivity.isNullOrEmpty(verifyType)) {
				rawData.append("<verifyType>").append(verifyType)
						.append("</verifyType>");
			}
			if (!CommonActivity.isNullOrEmpty(isdn)) {
				rawData.append("<isdn>").append(CommonActivity.checkStardardIsdn(isdn)).append("</isdn>");
			}
			if (!CommonActivity.isNullOrEmpty(pageIndex)) {
				rawData.append("<pageIndex>").append(pageIndex)
						.append("</pageIndex>");
			}
			if (!CommonActivity.isNullOrEmpty(pageSize)) {
				rawData.append("<pageSize>").append(pageSize)
						.append("</pageSize>");
			}
			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_" + methodName);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i(" Original", response);

			// parser
			Serializer serializer = new Persister();
			resultOutput = serializer.read(PaymentOutput.class, original);
			if (resultOutput == null) {
				resultOutput = new PaymentOutput();
				resultOutput.setDescription(context
						.getString(R.string.no_return_from_system));
				resultOutput.setErrorCode(Constant.ERROR_CODE);
				return resultOutput;
			} else {
				return resultOutput;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			resultOutput = new PaymentOutput();
			resultOutput.setDescription(e.getMessage());
			resultOutput.setErrorCode(Constant.ERROR_CODE);
		}

		return resultOutput;
	}

}
