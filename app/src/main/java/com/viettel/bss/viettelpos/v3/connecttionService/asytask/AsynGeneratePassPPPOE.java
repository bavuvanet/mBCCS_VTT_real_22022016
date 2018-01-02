package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

public class AsynGeneratePassPPPOE extends AsyncTask<Void, Void, ParseOuput> {
	private Activity mActivity = null;
	private View prbIsdn;
	private SubscriberDTO currentSub;
	private EditText edt;
	private String province;

	public AsynGeneratePassPPPOE(Activity mActivity, View prbIsdn,
			SubscriberDTO currentSub, EditText edt, String province) {
		this.province = province;
		this.mActivity = mActivity;
		this.currentSub = currentSub;
		this.prbIsdn = prbIsdn;
		this.edt = edt;

		prbIsdn.setVisibility(View.VISIBLE);
	}

	@Override
	protected ParseOuput doInBackground(Void... params) {
		return generateGroupCode();
	}

	@Override
	protected void onPostExecute(ParseOuput result) {
		super.onPostExecute(result);
		prbIsdn.setVisibility(View.GONE);
		if (result.getErrorCode().equals("0")) {
			edt.setHint(result.getAccount());
			currentSub.setIsdn(result.getAccount());
		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity
						.BackFromLogin(mActivity, result.getDescription());
			} else {
				if (result.getDescription() == null
						|| result.getDescription().isEmpty()) {
					result.setDescription(mActivity
							.getString(R.string.checkdes));
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						result.getDescription(),
						mActivity.getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	private ParseOuput generateGroupCode() {
		ParseOuput result;
		String original = "";

		try {
			String methodName = "genPasswordAutoPPPOE";
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");

			rawData.append("<custName>"
					+ TabThueBaoHopDongManager.instance.custIdentityDTO
							.getCustomer().getName() + "</custName>");
			rawData.append("<province>" + province + "</province>");
			rawData.append("<telecomServiceId>"
					+ currentSub.getTelecomServiceId() + "</telecomServiceId>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
			// parser
			Serializer serializer = new Persister();
			result = serializer.read(ParseOuput.class, original);
			if (result == null) {
				result = new ParseOuput();
				result.setDescription(mActivity
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
			}
		} catch (Exception e) {
			Log.e("getListIP", e.toString() + "description error", e);
			result = new ParseOuput();
			result.setDescription(mActivity.getString(R.string.exception)
					+ " - " + e);
			result.setErrorCode(Constant.ERROR_CODE);
		}

		return result;

	}
}