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
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class AsyncBlockOpenSub extends AsyncTask<Void, Void, SaleOutput> {

	private String blockMode;
	private ProgressDialog progress;
	private Context context;
	private OnPostExecute<SaleOutput> onPostExecute;
	private SubscriberDTO sub;
    private String reasonId;

	public AsyncBlockOpenSub(Context context, String blockMode,
			SubscriberDTO sub, OnPostExecute<SaleOutput> onPostExecute) {
		this.context = context;
		this.blockMode = blockMode;
		this.sub = sub;
		this.onPostExecute = onPostExecute;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		String msg = context.getString(R.string.dang_chan_thue_bao, sub.getIsdn());
		this.progress.setMessage(msg);
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

    public AsyncBlockOpenSub(Context context, String blockMode,
                             SubscriberDTO sub, OnPostExecute<SaleOutput> onPostExecute, String reasonId) {
        this.context = context;
        this.blockMode = blockMode;
        this.sub = sub;
        this.onPostExecute = onPostExecute;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.reasonId = reasonId;
        String msg = context.getString(R.string.dang_chan_thue_bao, sub.getIsdn());
        this.progress.setMessage(msg);
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

	@Override
	protected SaleOutput doInBackground(Void... params) {
		return blockOpenSub();

	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		onPostExecute.onPostExecute(result);
	}

	private SaleOutput blockOpenSub() {
		String original = "";
		SaleOutput out;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			String wsCode = "blockOpenSubscriber";
			input.addValidateGateway("wscode", "mbccs_" + wsCode);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + wsCode + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<subId>" + sub.getSubId() + "</subId>");
            rawData.append("<telecomServiceAlias>" + sub.getTelecomServiceAlias() + "</telecomServiceAlias>");
			rawData.append("<blockMode>" + blockMode + "</blockMode>");
            if(!CommonActivity.isNullOrEmpty(reasonId)){
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
            }

            if(!CommonActivity.isNullOrEmpty(sub.getActionCode())){
                rawData.append("<actionCode>" + sub.getActionCode() + "</actionCode>");
            }
			rawData.append("</input>");
			rawData.append("</ws:" + wsCode + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_" + wsCode);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
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
			Log.e("getListIP", e.toString() + "description error", e);
			Log.e(Constant.TAG, "blockOpenSub", e);
			out = new SaleOutput();
			out.setDescription(e.getMessage());
			out.setErrorCode(Constant.ERROR_CODE);
		}
		return out;
	}

}
