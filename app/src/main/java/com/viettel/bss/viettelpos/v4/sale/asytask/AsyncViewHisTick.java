package com.viettel.bss.viettelpos.v4.sale.asytask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

public class AsyncViewHisTick extends AsyncTask<Void, Void, SaleOutput> {
	private OnPostExecute<SaleOutput> onPostExecute;
	private Context context;
	private ProgressDialog progress;
	private String subId;

	public AsyncViewHisTick(OnPostExecute<SaleOutput> onPostExecute,
			Context context, String subId) {
		super();
		this.subId = subId;
		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.processing));
		progress.setCancelable(false);
		progress.show();
		this.onPostExecute = onPostExecute;
		this.context = context;
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			onPostExecute.onPostExecute(result);

		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(MainActivity.getInstance(),
						result.getDescription(), ";m.tick.appoint;");
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
	protected SaleOutput doInBackground(Void... arg0) {
		return viewHisTick();
	}

	private SaleOutput viewHisTick() {

		String original = "";
		SaleOutput result;
		String methodName = "viewHisTick";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<subId>" + subId + "</subId>");
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
