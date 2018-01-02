package com.viettel.bss.viettelpos.v4.customer.asynctask;

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
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class AsyncCollectHotCharge extends AsyncTask<Void, Void, SaleOutput> {
	private Long contractId;
	private Long subId;
	private OnPostExecute<SaleOutput> onPost;
	private Context context;
	private ProgressDialog progress;
	private String message;

	public AsyncCollectHotCharge(Long contractId, Long subId, String message,
			OnPostExecute<SaleOutput> onPost, Context context) {
		super();
		this.contractId = contractId;

		this.subId = subId;
		this.onPost = onPost;
		this.context = context;
		progress = new ProgressDialog(context);
		progress.setCancelable(false);
		if (CommonActivity.isNullOrEmpty(message)) {
			message = context.getString(R.string.dang_tong_hop_cuoc_nong);
		}
		progress.setMessage(message);
		progress.show();
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		this.progress.dismiss();
		onPost.onPostExecute(result);
	}

	@Override
	protected SaleOutput doInBackground(Void... arg0) {
		return biGetChargeReport();
	}

	private SaleOutput biGetChargeReport() {
		String original = "";
		SaleOutput out;
		String methodName = "biGetChargeReport";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");

			rawData.append("<contractId>").append(contractId)
					.append("</contractId>");
			rawData.append("<subId>").append(subId).append("</subId>");
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
}
