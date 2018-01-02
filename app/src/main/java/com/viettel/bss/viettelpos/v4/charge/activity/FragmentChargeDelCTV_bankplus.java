package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.InfoCustomerChargeDelAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.BankBean;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Network;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentChargeDelCTV_bankplus extends FragmentCommon {

	private Activity activity;

	private EditText edittext_acc;
	private ListView lvListCustomer;
	private LinearLayout btnSearchAcc;
    private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<>();
	private ChargeContractItem chargContractItem;

	private List<Spin> lstBankBean;

    private Integer isBankPlus = -1;

	private String bankCode = "";

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.charge_del_ctv);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_charge_del_ctv;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		edittext_acc = (EditText) v.findViewById(R.id.edittext_acc);
		btnSearchAcc = (LinearLayout) v.findViewById(R.id.btn_search_acc);
		lvListCustomer = (ListView) v.findViewById(R.id.lv_info_acc);
		btnSearchAcc.setOnClickListener(this);
		lvListCustomer.setOnItemClickListener(this);

		AsynctaskCheckDeployBankPlus asynctaskCheckDeployBankPlus = new AsynctaskCheckDeployBankPlus(
				activity);
		asynctaskCheckDeployBankPlus.execute();

		// tuantd7();
	}

	private void tuantd7() {
		edittext_acc.setText("985357385");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
		chargContractItem = arrChargeItemObjectDels.get(arg2);
		showDialogChargDetail(chargContractItem);
	}

	private Button btnChargeDel;
    private EditText edtAmountPayment;

	private Spinner spnBankCode;

	private Dialog dialog;

	private LinearLayout lnBankCode;

	private void showDialogChargDetail(ChargeContractItem chargeContract) {

		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_charge_customer_detail);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);

        TextView tvCodeContract = (TextView) dialog.findViewById(R.id.tvCodeContract);
        TextView tvPhoneNumber = (TextView) dialog.findViewById(R.id.tvPhoneNumber);
        TextView tvStatePayment = (TextView) dialog.findViewById(R.id.tvStatePayment);
        TextView tvChargeBefor = (TextView) dialog.findViewById(R.id.tvChargeBefor);
        TextView tvChargeOrther = (TextView) dialog.findViewById(R.id.tvChargOrther);
        TextView tvTotalCharge = (TextView) dialog.findViewById(R.id.tvTotalPayment);
		edtAmountPayment = (EditText) dialog
				.findViewById(R.id.edtAmountPayment);

		spnBankCode = (Spinner) dialog.findViewById(R.id.spnBankCode);
		tvCodeContract.setText(chargeContract.getContractNo());
		tvPhoneNumber.setText(chargeContract.getIsdn());
		tvStatePayment.setText(chargeContract.getPayMethodName());
		tvTotalCharge.setText(StringUtils.formatMoney(chargeContract
				.getTotCharge()));
		tvChargeBefor.setText(StringUtils.formatMoney(chargeContract
				.getPriorDebit()));
		tvChargeOrther.setText(StringUtils.formatMoney(chargeContract
				.getDebit()));
		Double totCharge = 0.0D;

		try {
			totCharge = Double.parseDouble(chargeContract.getTotCharge());
		} catch (Exception e) {
			Log.e(Constant.TAG, "du lieu tra ve nhu shit ", e);
		}
		edtAmountPayment.setText(totCharge.intValue() + "");

        Button btnOut = (Button) dialog.findViewById(R.id.btnOut);
		btnChargeDel = (Button) dialog.findViewById(R.id.btnChargeDel);

		CommonActivity.showKeyBoard(edtAmountPayment, activity);

		btnOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(edtAmountPayment, activity);
				dialog.dismiss();
			}
		});

		btnChargeDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(btnChargeDel, activity);
				String amountPayment = edtAmountPayment.getText().toString()
						.trim();
				Log.d(Constant.TAG, "btnChargeDel onClick amountPayment:"
						+ amountPayment);

				int posBankCode = spnBankCode.getSelectedItemPosition();
				Spin spin = (Spin) spnBankCode.getSelectedItem();

				if (amountPayment.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.message_input_amount_payment),
							getString(R.string.app_name)).show();
				} else if (isBankPlus == 1 && posBankCode == 0) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.require_bankplus),
							getString(R.string.app_name)).show();
				} else {
					bankCode = spin.getId();
					String amount = StringUtils.formatMoney(amountPayment);
					CommonActivity.createDialog(
							activity,
							getString(R.string.message_confirm_payment) + " "
									+ amount + getString(R.string.vnd),
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmChargeAct)
							.show();
				}

			}
		});

		if (lstBankBean == null || lstBankBean.isEmpty()) {
			AsynctaskGetListBank asynctaskGetListBank = new AsynctaskGetListBank(
					activity);
			asynctaskGetListBank.execute();
		} else {
			Utils.setDataSpinner(activity, lstBankBean, spnBankCode); // danh
																		// sach
																		// ngan
																		// hang
		}

		dialog.show();

	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btn_search_acc:
			if (CommonActivity.isNetworkConnected(activity)) {
				searchContract();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	private void searchContract() {
		Log.d(Constant.TAG, "FragmentChargeDelCTV searchContract");

		String phoneNumber = CommonActivity.checkStardardIsdn(edittext_acc
				.getText().toString().trim());
		if (phoneNumber.length() == 0) {
			CommonActivity
					.createAlertDialog(
							activity,
							getString(R.string.message_please_insert_phonenumber_contractid),
							getString(R.string.app_name)).show();
		} else {
			CommonActivity.hideKeyboard(btnSearchAcc, activity);
			AsynctaskSearchContract asynctaskSearchContract = new AsynctaskSearchContract(
					activity);
			asynctaskSearchContract.execute(phoneNumber);
		}
	}

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				String amountPayment = edtAmountPayment.getText().toString()
						.trim();
				chargContractItem.setaMountCharge(amountPayment);
				AsynctaskPaymentContract asynctaskPaymentContract = new AsynctaskPaymentContract(
						activity, chargContractItem);
				asynctaskPaymentContract.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	private class AsynctaskPaymentContract extends
			AsyncTask<String, Void, Void> {

		private final Activity mActivity;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		final ChargeContractItem mChargeContractItem;

		public AsynctaskPaymentContract(Activity mActivity,
				ChargeContractItem mChargeContractItem) {
			this.mActivity = mActivity;
			this.mChargeContractItem = mChargeContractItem;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			paymentContractRequest(mChargeContractItem);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			Log.d(Constant.TAG, "onPostExecute " + errorCode);
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (errorCode.equals("0")) {
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						description, getString(R.string.app_name));
				dialog.show();
				searchContract();

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

		private void paymentContractRequest(ChargeContractItem mContractItem) {
			Log.d("Log", "begin");

			String original = "";

			try {
				Log.d("Log", "begin try payment");
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_paymentContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:paymentContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<contractId>").append(mContractItem.getContractId()).append("</contractId>");
				rawData.append("<amount>").append(mContractItem.getaMountCharge()).append("</amount>");
				rawData.append("<clientIp>").append(Network.getLocalIpAddress()).append("</clientIp>");
				rawData.append("<bankCode>").append(bankCode).append("</bankCode>");
				rawData.append("<isdnContract>").append(mContractItem.getIsdnContract()).append("</isdnContract>");
				rawData.append("<customerName>").append(mContractItem.getCustomerName()).append("</customerName>");
				rawData.append("</input>");
				rawData.append("</ws:paymentContract>");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Log", "Send evelop " + envelope);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_paymentContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);
				Log.d("Log", "Responseeeeeeeeee Original group " + response);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
				}
			} catch (Exception e) {
				Log.e("Log", e.toString(), e);
				description = CommonActivity.getDescription(getActivity(), e);
			}
			Log.d("Log", "end");

		}
	}

	private class AsynctaskSearchContract extends
			AsyncTask<String, Void, ArrayList<ChargeContractItem>> {

		private final Activity mActivity;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskSearchContract(Activity mActivity) {

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
		protected ArrayList<ChargeContractItem> doInBackground(String... params) {

			return searchContract(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
				}

				arrChargeItemObjectDels = result;
                InfoCustomerChargeDelAdapter chargeDelAdapter = new InfoCustomerChargeDelAdapter(
                        arrChargeItemObjectDels, activity, false);
				lvListCustomer.setAdapter(chargeDelAdapter);

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

		private ArrayList<ChargeContractItem> searchContract(String phoneNumber) {
			ArrayList<ChargeContractItem> listChargeContractItem = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(phoneNumber).append("</isdn>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:searchContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstMContractBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem chargeItemObject = new ChargeContractItem();
						chargeItemObject.setAddress(parse.getValue(e1,
								"address"));
						chargeItemObject.setContractFormMngt(parse.getValue(e1,
								"contractFormMngt"));
						chargeItemObject.setContractFormMngtName(parse
								.getValue(e1, "contractFormMngtName"));
						chargeItemObject.setContractId(parse.getValue(e1,
								"contractId"));
						chargeItemObject.setContractNo(parse.getValue(e1,
								"contractNo"));
						chargeItemObject.setDebit(parse.getValue(e1, "debit"));
						chargeItemObject.setHotCharge(parse.getValue(e1,
								"hotCharge"));
						chargeItemObject.setIsdn(parse.getValue(e1, "isdn"));
						chargeItemObject.setPayMethodCode(parse.getValue(e1,
								"payMethodCode"));
						chargeItemObject.setPayMethodName(parse.getValue(e1,
								"payMethodName"));
						chargeItemObject.setPayer(parse.getValue(e1, "payer"));

						chargeItemObject.setIsdnContract(parse.getValue(e1,
								"isdnContract"));
						chargeItemObject.setCustomerName(parse.getValue(e1,
								"customerName"));

						String s = parse.getValue(e1, "paymentStatus");
						if (s == null || s.isEmpty()) {
							s = "0";
						}
						chargeItemObject.setPaymentStatus(s);

						chargeItemObject.setPriorDebit(parse.getValue(e1,
								"priorDebit"));
						chargeItemObject
								.setTelFax(parse.getValue(e1, "telFax"));
						chargeItemObject.setTotCharge(parse.getValue(e1,
								"totCharge"));
						listChargeContractItem.add(chargeItemObject);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV searchContract", e);
			}

			return listChargeContractItem;

		}

	}

	private class AsynctaskCheckDeployBankPlus extends
			AsyncTask<String, Void, Void> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskCheckDeployBankPlus(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// this.progress = new ProgressDialog(mActivity);
			// this.progress.setCancelable(false);
			// this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected Void doInBackground(String... params) {
			return checkDeployBankPlus();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
			}
			if (errorCode.equals("0")) {
				switch (isBankPlus) {
				case -1:
					// TODO XU LY CHO NAY
					// CommonActivity.createAlertDialog(mActivity,
					// getString(R.string.nodata_bankplus),
					// getString(R.string.app_name)).show();

					break;
				// case 0:
				// CommonActivity.createAlertDialog(mActivity,
				// getString(R.string.ko_co_dl),
				// getString(R.string.app_name)).show();
				// break;
				// case 1:
				// CommonActivity.createAlertDialog(mActivity,
				// getString(R.string.ko_co_dl),
				// getString(R.string.app_name)).show();
				// break;
				default:
					break;
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

		private Void checkDeployBankPlus() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkDeployBankPlus");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkDeployBankPlus>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<clientIp>").append(Network.getLocalIpAddress()).append("</clientIp>");
				rawData.append("</input>");
				rawData.append("</ws:checkDeployBankPlus>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_checkDeployBankPlus");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d(this.getClass().getSimpleName(), "errorCode: "
							+ errorCode + " description: " + description);
				}
				if ("1".equalsIgnoreCase(description)) {
					isBankPlus = 1;
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus + " phai su dung BankPlus: ");
				} else if ("0".equalsIgnoreCase(description)) {
					isBankPlus = 0;
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus + " khong can BankPlus: ");
				} else {
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus
							+ " kqt qua tra ve khong thay BankPlus: ");
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV checkDeployBankPlus",
						e);
			}
			return null;
		}
	}

	private class AsynctaskGetListBank extends
			AsyncTask<String, Void, List<Spin>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskGetListBank(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<Spin> doInBackground(String... params) {
			return getListBanks();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.notnganhang),
							getString(R.string.app_name)).show();
				} else {
					Spin spin = new Spin("", getString(R.string.txt_select));
					lstBankBean = new ArrayList<>();
					lstBankBean.add(spin);
					lstBankBean.addAll(result);

					Utils.setDataSpinner(activity, lstBankBean, spnBankCode); // danh
																				// sach
																				// ngan
																				// hang
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

		private List<Spin> getListBanks() {
			List<Spin> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListBanks");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListBanks>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<clientIp>").append(Network.getLocalIpAddress()).append("</clientIp>");
				rawData.append("</input>");
				rawData.append("</ws:getListBanks>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListBanks");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

					nodechild = doc.getElementsByTagName("lstBankBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "code"));
						spin.setValue(parse.getValue(e1, "name"));

						list.add(spin);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV getListBanks", e);
			}
			return list;
		}
	}

	private class AsynctaskGetListRequestBankPlusMbccs extends
			AsyncTask<String, Void, List<BankBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		private final String isdn;
		private final String status;

		public AsynctaskGetListRequestBankPlusMbccs(Activity mActivity,
				String isdn, String status) {
			this.mActivity = mActivity;
			this.isdn = isdn;
			this.status = status;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<BankBean> doInBackground(String... params) {
			return getListRequestBankPlusMbccs();
		}

		@Override
		protected void onPostExecute(List<BankBean> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.notnganhang),
							getString(R.string.app_name)).show();
				} else {
                    List<BankBean> lstBankBeanStatus = new ArrayList<>();
					lstBankBeanStatus.addAll(result);
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

		private List<BankBean> getListRequestBankPlusMbccs() {
			List<BankBean> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListRequestBankPlusMbccs");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRequestBankPlusMbccs>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<status>").append(status).append("</status>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:getListRequestBankPlusMbccs>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListRequestBankPlusMbccs");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

					nodechild = doc.getElementsByTagName("lstBankBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						BankBean bankBean = new BankBean();
						bankBean.setCode(parse.getValue(e1, "code"));
						bankBean.setName(parse.getValue(e1, "name"));
						bankBean.setStatus(parse.getValue(e1, "status"));
						bankBean.setErrorMsg(parse.getValue(e1, "errorMsg"));
						bankBean.setErrorCode(parse.getValue(e1, "errorCode"));
						bankBean.setCustName(parse.getValue(e1, "custName"));
						bankBean.setIsdnBankPlus(parse.getValue(e1,
								"isdnBankPlus"));
						bankBean.setDescription(parse.getValue(e1,
								"description"));

						list.add(bankBean);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG,
						"FragmentChargeDelCTV getListRequestBankPlusMbccs", e);
			}
			return list;
		}
	}

	@Override
	public void setPermission() {
		permission = ";pm_payment_ctv;";

	}
}
