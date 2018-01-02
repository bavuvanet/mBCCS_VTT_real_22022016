package com.viettel.bss.viettelpos.v4.bankplus.asynctask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

public class AsyncCheckDebit extends AsyncTask<Void, Void, BankPlusOutput> {
	private OnPostExecute<BankPlusOutput> onPostExecute;
	private Context context;
	private ProgressDialog progress;
	private String isdn;

	public AsyncCheckDebit(OnPostExecute<BankPlusOutput> onPostExecute,
			Context context, String isdn) {
		super();
		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.checking_debit_isdn,
				isdn));
		progress.setCancelable(false);
		progress.show();
		this.onPostExecute = onPostExecute;
		this.context = context;
		this.isdn = isdn;
	}

	@Override
	protected void onPostExecute(BankPlusOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			onPostExecute.onPostExecute(result);

		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(MainActivity.getInstance(),
						result.getDescription(), ";m.add.quota;");
			} else {
				String description = result.getDescription();
				if (CommonActivity.isNullOrEmpty(description)) {
					description = context.getString(R.string.unkonw_error);
				}
				CommonActivity.createAlertDialog(MainActivity.getInstance(),
						description, context.getString(R.string.app_name))
						.show();

			}
		}

	}

	@Override
	protected BankPlusOutput doInBackground(Void... arg0) {
		return doAction();
	}

	private BankPlusOutput doAction() {

		String original = "";
		BankPlusOutput result;
		String methodName = "getDebitInfoByIsdn";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName); //pos2_
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<isdn>").append(isdn).append("</isdn>");
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
			result = serializer.read(BankPlusOutput.class, original);
			if (result == null) {
				result = new BankPlusOutput();
				result.setDescription(context
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
				return result;
			} else {
				return result;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			result = new BankPlusOutput();
			result.setDescription(e.getMessage());
			result.setErrorCode(Constant.ERROR_CODE);
		}
		return result;
	}
}
