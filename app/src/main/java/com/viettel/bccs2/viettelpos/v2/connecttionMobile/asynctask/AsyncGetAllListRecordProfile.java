package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asynctask;

import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.SaleOutput;

public class AsyncGetAllListRecordProfile extends
		AsyncTask<Void, Void, SaleOutput> {

	private OnPostExecute<SaleOutput> onPostExecute;
	private Context context;
	private List<String> lstActionCode;
	private List<String> lstReasonId;
	private String custType;
	private String parValue;
	private String serviceType;
	private String payType;
	ProgressDialog progress;

	public AsyncGetAllListRecordProfile(
			OnPostExecute<SaleOutput> onPostExecute, Context context,
			List<String> lstActionCode, List<String> lstReasonId,
			String custType, String parValue, String serviceType, String payType) {
		super();
		this.onPostExecute = onPostExecute;
		this.context = context;
		this.lstActionCode = lstActionCode;
		this.lstReasonId = lstReasonId;
		this.custType = custType;
		this.parValue = parValue;
		this.serviceType = serviceType;
		this.payType = payType;
		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.getting_profile));
		progress.setCancelable(false);
		progress.show();
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		onPostExecute.onPostExecute(result);

	}

	@Override
	protected SaleOutput doInBackground(Void... arg0) {
		return getAllListRecordProfile();
	}

	private SaleOutput getAllListRecordProfile() {

		String original = "";
		SaleOutput result;
		String methodName = "getAllListRecordProfile";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			for (String item : lstActionCode) {
				rawData.append("<lstActionCode>" + item + "</lstActionCode>");
			}
			for (String item : lstReasonId) {
				rawData.append("<lstReasonId>" + item + "</lstReasonId>");
			}

			rawData.append("<custType>" + custType + "</custType>");
			rawData.append("<parValue>" + parValue + "</parValue>");
			rawData.append("<serviceType>" + serviceType + "</serviceType>");
			rawData.append("<payType>" + payType + "</payType>");
			
			
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
			result = serializer.read(SaleOutput.class, original);
			if (result == null) {
				result = new SaleOutput();
				result.setDescription(context
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
				return result;
			} else {
				return result;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			result = new SaleOutput();
			result.setDescription(e.getMessage());
			result.setErrorCode(Constant.ERROR_CODE);
		}
		return result;
	}

}