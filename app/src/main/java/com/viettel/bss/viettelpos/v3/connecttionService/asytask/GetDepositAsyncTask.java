package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class GetDepositAsyncTask extends AsyncTask<Void, Void, ParseOuput> {
	private Activity mActivity = null;
	private Spinner spinner;
	private SubscriberDTO sub;
	private View prb;
	private LinearLayout lndatcoc;
	public GetDepositAsyncTask(Activity mActivity, Spinner spinner,
			SubscriberDTO sub, View prb) {
		this.mActivity = mActivity;
		this.prb = prb;
		this.sub = sub;
		this.spinner = spinner;
		this.prb.setVisibility(View.VISIBLE);
	}
	public GetDepositAsyncTask(Activity mActivity, Spinner spinner,
							   SubscriberDTO sub, View prb , LinearLayout lineadatcoc) {
		this.mActivity = mActivity;
		this.prb = prb;
		this.sub = sub;
		this.spinner = spinner;
		this.prb.setVisibility(View.VISIBLE);
		this.lndatcoc = lineadatcoc;
	}

	@Override
	protected ParseOuput doInBackground(Void... params) {
		return getListDeposit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(ParseOuput result) {
		super.onPostExecute(result);

		this.prb.setVisibility(View.GONE);
		if (result.getErrorCode().equals("0")) {
			List<ReasonPledgeDTO> lst = result.getLstReasonPledgeDTOs();
			if(!CommonActivity.isNullOrEmpty(lndatcoc)){
				lndatcoc.setVisibility(View.GONE);
			}
			if (lst == null || lst.isEmpty()) {
				lst = new ArrayList<ReasonPledgeDTO>();
				ReasonPledgeDTO first = new ReasonPledgeDTO();
				first.setContext(mActivity);
				first.setNumMonth(mActivity.getString(R.string.txt_select));
				lst.add(0, first);
			} else {
				if(!CommonActivity.isNullOrEmpty(lndatcoc)){
					lndatcoc.setVisibility(View.VISIBLE);
				}
				ReasonPledgeDTO first = new ReasonPledgeDTO();
				first.setContext(mActivity);
				first.setNumMonth(mActivity.getString(R.string.txt_select));
				lst.add(0, first);
				for (ReasonPledgeDTO reasonPledgeDTO : lst) {
					reasonPledgeDTO.setContext(mActivity);
				}
			}
			ArrayAdapter<ReasonPledgeDTO> adapter = new ArrayAdapter<ReasonPledgeDTO>(
					mActivity, R.layout.spinner_item, R.id.spinner_value, lst);
			adapter.setDropDownViewResource(R.layout.spinner_item);
			spinner.setAdapter(adapter);

		} else {

			if(!CommonActivity.isNullOrEmpty(lndatcoc)){
				lndatcoc.setVisibility(View.GONE);
			}

			ArrayAdapter<ReasonPledgeDTO> adapter = new ArrayAdapter<ReasonPledgeDTO>(
					mActivity, R.layout.spinner_item, R.id.spinner_value, new ArrayList<ReasonPledgeDTO>());
			adapter.setDropDownViewResource(R.layout.spinner_item);
			spinner.setAdapter(adapter);
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
						result.getDescription(), mActivity.getResources()
								.getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	private ParseOuput getListDeposit() {

		ParseOuput result;
		String original = "";
		try {
			String methodName = "getReasonFullInfo";
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<reasonId>" + sub.getHthm().getReasonId()
					+ "</reasonId>");

			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_getReasonFullInfo");
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
