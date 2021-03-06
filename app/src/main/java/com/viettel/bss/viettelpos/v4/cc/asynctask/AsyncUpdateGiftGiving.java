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
import com.viettel.bss.viettelpos.v4.cc.object.SubscriberCareBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

public class AsyncUpdateGiftGiving extends AsyncTask<Void, Void, CCOutput> {

	private OnPostExecute<CCOutput> onPostExecute;
	private Context context;
	private SubscriberCareBean bean;
	private ProgressDialog progress;
	private String note;
	private String reasonNotGift;
	private String custName;

	public AsyncUpdateGiftGiving(SubscriberCareBean bean, String note,
			String reasonNotGift,String custName, OnPostExecute<CCOutput> onPostExecute,
			Context context) {
		super();
		this.custName = custName;
		this.reasonNotGift = reasonNotGift;
		this.bean = bean;
		this.note = note;
		this.onPostExecute = onPostExecute;
		this.context = context;
		progress = new ProgressDialog(context);
		progress.setCancelable(false);
		progress.setMessage(context.getString(R.string.gift_updatting));
		progress.show();
	}

	@Override
	protected void onPostExecute(CCOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		result.setSubscriberCareBean(bean);
		onPostExecute.onPostExecute(result);

	}

	@Override
	protected CCOutput doInBackground(Void... arg0) {
		return updateGiftGiving();
	}

	private CCOutput updateGiftGiving() {

		String original = "";
		CCOutput result;
		String methodName = "updateGiftGiving";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");

			rawData.append("<subCareDetailId>")
					.append(bean.getSubCareDetailId())
					.append("</subCareDetailId>");

			rawData.append("<status>").append(bean.getStatus())
					.append("</status>");

			if (!CommonActivity.isNullOrEmpty(reasonNotGift)) {
				rawData.append("<reasonNoGift>").append(reasonNotGift)
						.append("</reasonNoGift>");
			}

			rawData.append("<careType>").append(bean.getCareType())
					.append("</careType>");
			rawData.append("<custName>").append(custName)
					.append("</custName>");
			rawData.append("<description>")
					.append(StringUtils.escapeHtml(CommonActivity
							.getNormalText(note))).append("</description>");
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
