package com.viettel.bss.viettelpos.v4.charge.asynctask;

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
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

public class AsyncGetDebitInfo extends AsyncTask<Void, Void, SaleOutput> {
	private Context context;
	private ProgressDialog progress;
	private OnPostExecute<SaleOutput> onPostExecute;
	private String contractId;

	public AsyncGetDebitInfo(Context context, String contractId,
			OnPostExecute<SaleOutput> onPostExecute) {
		this.context = context;
		this.contractId = contractId;
		this.onPostExecute = onPostExecute;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		this.progress
				.setMessage(context.getString(R.string.getting_debit_info));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected SaleOutput doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return getDebitInfo();
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		this.progress.dismiss();
		onPostExecute.onPostExecute(result);
	}

	private SaleOutput getDebitInfo() {
		String original = "";
		SaleOutput out;
		String methodName = "getDebitInfo";
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
				if ("0".equals(out.getErrorCode())
						&& out.getVirtualInvoice() == null) {
					out = new SaleOutput();
					out.setDescription(context
							.getString(R.string.no_return_from_system));
					out.setErrorCode(Constant.ERROR_CODE);
				}
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
