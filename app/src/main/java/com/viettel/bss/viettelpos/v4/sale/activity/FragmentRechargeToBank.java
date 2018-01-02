package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentRechargeToBank extends Fragment implements OnClickListener {

	private View mView = null;

	private EditText edt_sotiendu, edtsotiennap;
	private ProgressBar prbsotiendu;
	private Spinner spn_bank;
    private Boolean isFormatted = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater
					.inflate(R.layout.layout_pay_bank, container, false);
			unitView(mView);
		}
		return mView;
	}

	private void unitView(View v) {
		edt_sotiendu = (EditText) v.findViewById(R.id.edt_sotiendu);
		edtsotiennap = (EditText) v.findViewById(R.id.edtsotiennap);
		edtsotiennap.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isFormatted) {
					isFormatted = true;
					edtsotiennap.setText(StringUtils.formatMoney(s.toString()
							.replaceAll("\\.", "")));
					edtsotiennap.setSelection(edtsotiennap.getText().toString()
							.length());
					isFormatted = false;
				}

			}
		});
		prbsotiendu = (ProgressBar) v.findViewById(R.id.prbsotiendu);
		spn_bank = (Spinner) v.findViewById(R.id.spn_bank);
        Button btn_recharge = (Button) v.findViewById(R.id.btn_recharge);
		btn_recharge.setOnClickListener(this);
		initBank();

		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsynGetAccountBalance asynGetAccountBalance = new AsynGetAccountBalance(
					getActivity());
			asynGetAccountBalance.execute();

		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

	}

	private void initBank() {
		try {

			BhldDAL dal = new BhldDAL(getActivity());
			dal.open();
			ArrayList<Spin> arrBank = dal.getListBank("PAYMENT_DEBIT_BANK");
			dal.close();

			Spin spin = new Spin("-1", getString(R.string.txt_select));
			arrBank.add(0, spin);
			Utils.setDataSpinner(getActivity(), arrBank, spn_bank);
		} catch (Exception e) {
			Log.e("initBank", e.toString());
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.rechargetobank);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_recharge:
			OnClickListener chargeOnListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Spin spin = (Spin) spn_bank.getSelectedItem();
					AsynChargeMoneyBankplus chargeMoneyBankplus = new AsynChargeMoneyBankplus(
							getActivity());
					chargeMoneyBankplus.execute(spin.getId(), edtsotiennap
							.getText().toString().trim().replace(".", ""));
				}
			};

			if (validateCharge()) {
				CommonActivity.createDialog(getActivity(),
						getString(R.string.confirmnaptien),
						getString(R.string.app_name), getString(R.string.ok),
						getString(R.string.cancel), chargeOnListener, null)
						.show();
			}

			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}

	}

	private boolean validateCharge() {

		Spin spin = (Spin) spn_bank.getSelectedItem();
		if (spin == null) {
			CommonActivity
					.createAlertDialog(getActivity(),
							getString(R.string.checkBank),
							getString(R.string.app_name)).show();
			return false;
		}
		if (spin.getId().equals("-1")) {
			CommonActivity
					.createAlertDialog(getActivity(),
							getString(R.string.checkBank),
							getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edtsotiennap.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkmoneyempty),
					getString(R.string.app_name)).show();
			return false;
		}
		if (Long.parseLong(edtsotiennap.getText().toString().trim()
				.replace(".", "")) <= 0) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkmoneylonhon),
					getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private class AsynGetAccountBalance extends AsyncTask<Void, Void, String> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynGetAccountBalance(Activity mActivity) {
			this.mActivity = mActivity;
			prbsotiendu.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... params) {
			return getAccountBalance();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			prbsotiendu.setVisibility(View.GONE);
			if (errorCode.equals("0")) {
				edt_sotiendu.setText(description);
				// if (result.size() > 0) {
				//
				// } else {
				// CommonActivity.createAlertDialog(mActivity,
				// getString(R.string.ko_co_dl),
				// getString(R.string.app_name)).show();
				// }
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

		private String getAccountBalance() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getAccountBalance");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAccountBalance>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getAccountBalance>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getAccountBalance");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return errorCode;

		}
	}

	private class AsynChargeMoneyBankplus extends
			AsyncTask<String, Void, String> {

		private final Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynChargeMoneyBankplus(Activity context) {
			this.progress = new ProgressDialog(context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.processing));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return chargeMoneyBankplus(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				edt_sotiendu.setText(StringUtils.formatMoney(description));
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									mActivity.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private String chargeMoneyBankplus(String bankCode, String totalBalance) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_chargeMoneyBankplus");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:chargeMoneyBankplus>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				// rawData.append("<saleTransBankPlus>");

				rawData.append("<bankCode>").append(bankCode);
				rawData.append("</bankCode>");
				rawData.append("<totalBalance>").append(totalBalance);
				rawData.append("</totalBalance>");

				// rawData.append("</saleTransBankPlus>");

				rawData.append("</input>");
				rawData.append("</ws:chargeMoneyBankplus>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_chargeMoneyBankplus");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

				}
			} catch (Exception e) {
				Log.d("mbccs_chargeMoneyBankplus", e.toString()
						+ "description error", e);
			}

			return errorCode;

		}
	}

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();

			LoginDialog dialog = new LoginDialog(getActivity(),
					";rechargeToBank;");
			dialog.show();

		}
	};
}
