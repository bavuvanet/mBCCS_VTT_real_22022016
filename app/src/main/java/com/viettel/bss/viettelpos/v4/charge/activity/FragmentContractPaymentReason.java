package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractPaymentHistoryAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.MPaymentContractBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentContractPaymentReason extends FragmentCommon {

    private ChargeContractItem chargeContractItem;
	private String appliedCycle;

    private EditText edtReasonCharge;
	private Spinner spReasonCharge;

    private ListView lvPaymentContract;

    private List<AreaObj> lstReasoon = new ArrayList<>();

    @Override
	public void onResume() {
        setTitleActionBar(act.getString(R.string.chargeable) + " - " + act.getString(R.string.payment_contract_detail));
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_contract_payment_reason;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Bundle mBundle = getArguments();
		if (mBundle != null) {
			chargeContractItem = (ChargeContractItem) mBundle
					.getSerializable("chargeContractItem");
			appliedCycle = mBundle.getString("appliedCycle");
		}
	}

	@Override
	public void unit(View v) {
        TextView tvCodeContract = (TextView) v.findViewById(R.id.tvCodeContract);
        TextView tvChargeBefor = (TextView) v.findViewById(R.id.tvChargeBefor);
        TextView tvChargOrther = (TextView) v.findViewById(R.id.tvChargOrther);
        TextView tvTotalPayment = (TextView) v.findViewById(R.id.tvTotalPayment);

        TextView tvCusName = (TextView) v.findViewById(R.id.tvCusName);
        TextView tvCodeChargeDel = (TextView) v.findViewById(R.id.tvCodeChargeDel);
        TextView tvPhoneNumberPresent = (TextView) v
                .findViewById(R.id.tvPhoneNumberPresent);
        TextView tvPhoneNumberContact = (TextView) v
                .findViewById(R.id.tvPhoneNumberContact);
        TextView tvRest = (TextView) v.findViewById(R.id.tvRest);
        TextView tvObjectCus = (TextView) v.findViewById(R.id.tvObjectCus);

        Button btn_payment_history = (Button) v.findViewById(R.id.btn_payment_history);

		edtReasonCharge = (EditText) v.findViewById(R.id.edtReasonCharge);
		spReasonCharge = (Spinner) v.findViewById(R.id.spReasonCharge);

        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        Button btn_update = (Button) v.findViewById(R.id.btn_update);

		if (chargeContractItem != null) {
			// tvCodeContract.setText(chargeContractItem.getContractNo());
			// tvChargeBefor.setText(chargeContractItem.priorDebit);
			// tvChargOrther.setText(chargeContractItem.getHotCharge());
			// tvTotalPayment.setText(chargeContractItem.getTotCharge());
			//
			// tvCusName.setText(chargeContractItem.getPayer());
			// tvCodeChargeDel.setText(chargeContractItem.getContractNo());
			// tvPhoneNumberPresent.setText(chargeContractItem.getIsdn());
			// tvPhoneNumberContact.setText(chargeContractItem.getAddress());
			// tvRest.setText(chargeContractItem.getDebit());
			// tvObjectCus.setText(chargeContractItem.getPriorDebit());

			tvCusName.setText(chargeContractItem.getPayer());
			tvCodeContract.setText(chargeContractItem.getContractId());
			tvCodeChargeDel.setText(chargeContractItem.getAddress());
			tvPhoneNumberPresent.setText(chargeContractItem.getTelFax());
			tvPhoneNumberContact.setText(chargeContractItem.getIsdn());
			tvChargeBefor.setText(chargeContractItem.getPriorDebit());
			tvChargOrther.setText(chargeContractItem.getHotCharge());
			tvRest.setText(chargeContractItem.getDebit());
			tvObjectCus.setText(chargeContractItem.getContractFormMngtName());
			tvTotalPayment.setText(chargeContractItem.getTotCharge());

		}

		btn_cancel.setOnClickListener(this);
		btn_update.setOnClickListener(this);
		btn_payment_history.setOnClickListener(this);
		AsyncContractPaymentReasoon asyntaskContractPaymentReasoon = new AsyncContractPaymentReasoon(
				act);
		asyntaskContractPaymentReasoon.execute();
	}

	private void showListPaymentHistory() {

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_contract_payment_history);
		lvPaymentContract = (ListView) dialog
				.findViewById(R.id.lvPaymentContract);
        Button btnClose = (Button) dialog.findViewById(R.id.btn_close);

		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.getWindow().setAttributes(lp);
		dialog.show();

		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncContractPaymentHistory asyncContractPaymentHistory = new AsyncContractPaymentHistory();
			asyncContractPaymentHistory.execute();
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {

		case R.id.btn_payment_history:
			showListPaymentHistory();
			break;
		case R.id.btn_cancel:
			act.onBackPressed();
			break;
		case R.id.btn_update:
			updateReason();
			break;
		default:
			break;
		}
	}

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(act)) {
				AsyncContractPaymentUpdateReasoon asynTaskUpdateReasoon = new AsyncContractPaymentUpdateReasoon(
						getActivity(), areaObject.getAreaCode());
				asynTaskUpdateReasoon.execute();
			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};
	private AreaObj areaObject;

	private void updateReason() {
		Log.d(Constant.TAG, "updateReason");
		String strReasoon = edtReasonCharge.getText().toString().trim();
		if (strReasoon.equalsIgnoreCase("")) {
			CommonActivity.createAlertDialog(act,
					getString(R.string.message_please_insert_reasoon),
					getString(R.string.app_name)).show();
		} else {
			int pos = spReasonCharge.getSelectedItemPosition();

			areaObject = lstReasoon.get(pos);
			if (areaObject != null) {
				CommonActivity.createDialog(act,
						getString(R.string.message_confirm_update),
						getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co),
						null, confirmChargeAct).show();
			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.message_not_sellect_reasoon),
						getString(R.string.app_name)).show();
			}
		}
	}

	private class AsyncContractPaymentHistory extends
			AsyncTask<String, Void, List<MPaymentContractBean>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsyncContractPaymentHistory() {
			// Log.d(Constant.TAG, AsyncContractPayment.this.toString());
			this.progress = new ProgressDialog(act);
			this.progress.setCancelable(false);
			this.progress.setMessage(act.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected ArrayList<MPaymentContractBean> doInBackground(
				String... params) {

			return getPaymentContract();
		}

		@Override
		protected void onPostExecute(List<MPaymentContractBean> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {

                    ContractPaymentHistoryAdapter adapter = new ContractPaymentHistoryAdapter(
                            result, act);
					lvPaymentContract.setAdapter(adapter);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.ko_co_dl_contract),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							act.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = act.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<MPaymentContractBean> getPaymentContract() {
			ArrayList<MPaymentContractBean> lstMPaymentContractBean = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getPaymentContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getPaymentContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(chargeContractItem.getContractId()).append("</contractId>");
				rawData.append("<appliedCycle>").append(appliedCycle).append("</appliedCycle>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:getPaymentContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getPaymentContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip

				// JSONObject jsonObject = null;
				// try {
				// jsonObject = XML.toJSONObject(original);
				// Log.i(Constant.TAG, jsonObject.toString());
				//
				// if (jsonObject.has("lstMContractBean")) {
				// JSONArray jsonArray =
				// jsonObject.getJSONArray("lstMContractBean");
				// for (int i = 0; i < jsonArray.length(); i++) {
				// JSONObject obj = jsonArray.getJSONObject(i);
				// }
				// } else {
				// Log.i(Constant.TAG, "lstMContractBean Key Not Found");
				// }
				//
				// } catch (JSONException e) {
				// Log.e(Constant.TAG, e.getMessage(), e);
				// }

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc
							.getElementsByTagName("lstMPaymentContractBean");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						MPaymentContractBean obj = new MPaymentContractBean();
						obj.setAmount(parse.getValue(e1, "amount"));
						obj.setCreateDate(parse.getValue(e1, "createDate"));
						obj.setPaymentType(parse.getValue(e1, "paymentType"));
						obj.setPaymentTypeName(parse.getValue(e1,
								"paymentTypeName"));

						lstMPaymentContractBean.add(obj);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return lstMPaymentContractBean;
		}
	}

	private class AsyncContractPaymentReasoon extends
			AsyncTask<String, Void, ArrayList<AreaObj>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		private final Activity mActivity;

		public AsyncContractPaymentReasoon(Activity mActivity) {
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {

			return getReasonNotCollect();
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					lstReasoon = result;
                    AdapterProvinceSpinner adapterContractPaymentReasoon = new AdapterProvinceSpinner(
                            result, mActivity);
					spReasonCharge.setAdapter(adapterContractPaymentReasoon);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.ko_co_dl_contract),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<AreaObj> getReasonNotCollect() {
			ArrayList<AreaObj> listAreaObj = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getReasonNotCollect");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonNotCollect>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:getReasonNotCollect>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getReasonNotCollect");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);
				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstReasonBean");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						AreaObj areaObject = new AreaObj();
						areaObject.setAreaCode(parse.getValue(e1, "id"));
						areaObject.setName(parse.getValue(e1, "name"));
						listAreaObj.add(areaObject);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return listAreaObj;
		}
	}

	@SuppressWarnings("unused")
	private class AsyncContractPaymentUpdateReasoon extends
			AsyncTask<String, Void, Void> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		private final Activity mActivity;
		private final String reasoonId;

		public AsyncContractPaymentUpdateReasoon(Activity mActivity,
				String reasoonId) {
			this.mActivity = mActivity;
			this.reasoonId = reasoonId;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			updateReasonNotCollect();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				OnClickListener onclickback = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						act.onBackPressed();

					}
				};
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.update_reasoon_success),
						getString(R.string.app_name), onclickback).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private void updateReasonNotCollect() {
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateReasonNotCollect");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateReasonNotCollect>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<contractId>").append(chargeContractItem.getContractId()).append("</contractId>");
				rawData.append("<reasonId>").append(reasoonId).append("</reasonId>");
				// rawData.append("<staffCode>" + "TT_TESTER" + "</staffCode>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");
				rawData.append("<appliedCycle>").append(appliedCycle).append("</appliedCycle>");
				rawData.append("<description>").append(edtReasonCharge.getText().toString().trim()).append("</description>");

				rawData.append("</input>");
				rawData.append("</ws:updateReasonNotCollect>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateReasonNotCollect");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);
				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
}
