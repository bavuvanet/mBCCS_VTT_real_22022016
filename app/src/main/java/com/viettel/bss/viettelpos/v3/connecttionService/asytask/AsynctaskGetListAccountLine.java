package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;

@SuppressWarnings("unused")
public class AsynctaskGetListAccountLine extends AsyncTask<Void, Void, ParseOuput> {
	private Activity mActivity = null;
	private View prbCustType;
	private View btnRefreshCustType;
	private Spinner spnCustType;

	public AsynctaskGetListAccountLine(Activity mActivity, View prbCustType,
			View btnRefreshCustType, Spinner spnCustType) {
		this.mActivity = mActivity;
		this.prbCustType = prbCustType;
		this.btnRefreshCustType = btnRefreshCustType;
		this.spnCustType = spnCustType;
		prbCustType.setVisibility(View.VISIBLE);
		btnRefreshCustType.setVisibility(View.GONE);
	}

	@Override
	protected ParseOuput doInBackground(Void... params) {
		return getListCustType();
	}

	@Override
	protected void onPostExecute(ParseOuput result) {
		super.onPostExecute(result);
		prbCustType.setVisibility(View.GONE);
		btnRefreshCustType.setVisibility(View.VISIBLE);
		if (result.getErrorCode().equals("0")) {
			List<OptionSetValueDTO> lst = result.getLstOptionSetValueDTOs();
			boolean getRecord = true;
			if (lst == null || lst.isEmpty()) {
				lst = new ArrayList<OptionSetValueDTO>();
			}
			OptionSetValueDTO reason = new OptionSetValueDTO();
			reason.setName(mActivity.getString(R.string.select_one_value));
			lst.add(0, reason);
			ArrayAdapter<OptionSetValueDTO> adapter = new ArrayAdapter<OptionSetValueDTO>(
					mActivity, R.layout.spinner_item, R.id.spinner_value, lst);
			adapter.setDropDownViewResource(R.layout.spinner_item);
			spnCustType.setAdapter(adapter);
			// new GetLisRecordPrepaidTask(sub.getProductCode(),
			// mActivity).execute();

		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(mActivity, result.getDescription());
			} else {
				if (result.getDescription() == null
						|| result.getDescription().isEmpty()) {
					result.setDescription(mActivity
							.getString(R.string.checkdes));
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						result.getDescription(),
						mActivity.getResources().getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	private ParseOuput getListCustType() {
		ParseOuput result;
		String original = "";
		try {
			String methodName = "getLsOptionSetValueByCode";
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<code>" + "BUS_TYPE_OBJ" + "</code>");
			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_getReasonMobile");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
			// parser
			Serializer serializer = new Persister();
			result = serializer.read(ParseOuput.class, original);
			if (result == null) {
				result = new ParseOuput();
				result.setDescription(mActivity.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
			}
		} catch (Exception e) {
			Log.e("getListIP", e.toString() + "description error", e);
			result = new ParseOuput();
			result.setDescription(mActivity.getString(R.string.exception) + " - " + e);
			result.setErrorCode(Constant.ERROR_CODE);
		}

		return result;

	}
}
