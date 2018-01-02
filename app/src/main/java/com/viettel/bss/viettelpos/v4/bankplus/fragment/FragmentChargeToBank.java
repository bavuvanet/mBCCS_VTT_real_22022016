package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.business.ApParamBusiness;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.NumberToTextUtil;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentChargeToBank extends FragmentCommon {

	private static final String SERVICE_INDICATOR_ACCOUNT = "000027";
	private static final String SERVICE_INDICATOR_CARD = "000028";
	private static final String SERVICE_INDICATOR_PHONE = "000029";

	private Activity activity;
	private TextView tvAmountWord, tvViewFee, tvHintIsdnAccount;
	private RadioButton rbPhone, rbAccount, rbCard;
	private EditText edtIsdnSend, edtMoneyCharge, edtPinCode, edtSender, edtAccount;
	private View btnTransferMoney;
	private RadioGroup radioGroup;
	private ArrayList<ApParamBO> lstBank;
	private Spinner spnBank;
	private String fee = "";
	private String serviceIndicator = "";
	private String serviceIndicatorName = "";

	private LinearLayout pinCodeLayout;

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(activity
				.getString(R.string.bankplus_menu_deposit_to_bank));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_charge_to_bank;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	private Boolean isFormatted = false;

	@Override
	public void unit(View v) {
		tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		tvHintIsdnAccount = (TextView) v.findViewById(R.id.tvHintIsdnAccount);
		tvViewFee = (TextView) v.findViewById(R.id.tvViewFee);
		tvViewFee.setOnClickListener(onClickView);
		rbPhone = (RadioButton) v.findViewById(R.id.rbPhone);
		rbAccount = (RadioButton) v.findViewById(R.id.rbAccount);
		rbCard = (RadioButton) v.findViewById(R.id.rbCard);
		rbCard.setVisibility(View.GONE);
		edtIsdnSend = (EditText) v.findViewById(R.id.edtIsdnSend);
		edtAccount = (EditText) v.findViewById(R.id.edtAccount);
		edtAccount.addTextChangedListener(edtAccountTextChange);
		edtSender = (EditText) v.findViewById(R.id.edtSender);
		edtMoneyCharge = (EditText) v.findViewById(R.id.edtMoneyCharge);
		edtPinCode = (EditText) v.findViewById(R.id.edtPinCode);
		spnBank = (Spinner) v.findViewById(R.id.spnBank);
		btnTransferMoney = v.findViewById(R.id.btnTransferMoney);
		btnTransferMoney.setOnClickListener(onClickView);
		radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(onCheckChange);
		rbPhone.setChecked(true);

		rbAccount.setVisibility(View.GONE);

		pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);
		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
			pinCodeLayout.setVisibility(View.VISIBLE);
		} else {
			pinCodeLayout.setVisibility(View.GONE);
		}
//		if(staff.getChannelTypeId() != 14 || (staff.getType() != null && staff.getType().equals(9L))) {
//			pinCodeLayout.setVisibility(View.VISIBLE);
//		} else {
//			pinCodeLayout.setVisibility(View.GONE);
//		}

		serviceIndicator = SERVICE_INDICATOR_PHONE;
		serviceIndicatorName = rbPhone.getText().toString();
		updateSpinBank(serviceIndicator);
		spnBank.setOnItemSelectedListener(onBankSelected);

		edtMoneyCharge.addTextChangedListener(edtMoneyChargeChange);
		edtIsdnSend.addTextChangedListener(edtIsdnSendListener);
		edtMoneyCharge.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_GO) {
					if (!CommonActivity.isNullOrEmpty(fee)) {
						return false;
					}
					if (validateViewFee()) {
						doGetFee(false);
						CommonActivity.hideSoftKeyboard(activity);
					}
					handled = true;
				}
				return handled;

			}
		});
		edtPinCode.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_GO) {
					if (validateConfirm()) {
						if (CommonActivity.isNullOrEmpty(fee)) {
							doGetFee(true);
						} else {
							showDialogConfirm();
						}
					}
					handled = true;
				}
				return handled;
			}
		});
	}



	private void updateSpinBank(String type) {

		ArrayList<String> lstTmp = new ArrayList<String>();
		lstBank = ApParamBusiness.getLstApParamByName(activity,
				"RECHARGE_BANKPLUS_LIST_BANK");

		if (CommonActivity.isNullOrEmpty(lstBank)) {
			lstBank = new ArrayList<ApParamBO>();
			ApParamBO item = new ApParamBO();
			item.setParType("VTT");
			item.setParValue("VTBank");
			item.setDescription("000029|So dien thoai");
			lstBank.add(item);
			rbAccount.setVisibility(View.GONE);
		}

		if (SERVICE_INDICATOR_PHONE.equals(type)) {
			for (ApParamBO item : lstBank) {
				lstTmp.add(item.getParType() + " - " + item.getParValue());
			}
		} else {
			for (ApParamBO item : lstBank) {
				if (item.getParType().equals("MB")) {
					lstTmp.add(item.getParType() + " - " + item.getParValue());
					break;
				}
			}
		}

		Utils.setDataSpinner(getContext(), lstTmp, spnBank);
	}

	@Override
	public void setPermission() {
		permission = ";m.charge.bank;";

	}

	OnCheckedChangeListener onCheckChange = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int checkedId) {
			switch (checkedId) {
			case R.id.rbAccount:
				serviceIndicator = SERVICE_INDICATOR_ACCOUNT;
				serviceIndicatorName = rbAccount.getText().toString();
				updateSpinBank(serviceIndicator);
				tvHintIsdnAccount.setText(rbAccount.getText().toString());
				break;
			case R.id.rbPhone:
				serviceIndicator = SERVICE_INDICATOR_PHONE;
				serviceIndicatorName = rbPhone.getText().toString();
				rbPhone.getText().toString();
				updateSpinBank(serviceIndicator);
				tvHintIsdnAccount.setText(rbPhone.getText().toString());

				if (CommonActivity.validateIsdn(edtAccount.getText().toString().trim())) {
					edtAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0,
							R.drawable.ic_check_black_24dp, 0);
				} else {
					edtAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				}
				break;
			case R.id.rbCard:
				serviceIndicator = SERVICE_INDICATOR_CARD;
				serviceIndicatorName = rbCard.getText().toString();
				updateSpinBank(serviceIndicator);
				tvHintIsdnAccount.setText(rbCard.getText().toString());
				break;
			default:
				break;
			}
		}
	};

	OnClickListener onClickView = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btnTransferMoney:
				if (validateConfirm()) {
					if (CommonActivity.isNullOrEmpty(fee)) {
						doGetFee(true);
					} else {
						showDialogConfirm();
					}
				}
				break;
			case R.id.tvViewFee:
				if (!CommonActivity.isNullOrEmpty(fee)) {
					return;
				}

				if (validateViewFee()) {
					doGetFee(false);
				}

				break;
			default:
				break;
			}

		}
	};

	void showDialogConfirm() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		// Create and show the dialog.
		String bankName = lstBank.get(spnBank.getSelectedItemPosition()).getParValue();
		String account = edtAccount.getText().toString();
		String money = edtMoneyCharge.getText().toString().replace(".", "");
		String moneyText = NumberToTextUtil.toword(Long.parseLong(money));
		String hoTen = edtSender.getText().toString().trim();
		String senderIsdn = edtIsdnSend.getText().toString().trim();
		String feeText = NumberToTextUtil.toword(Long.parseLong(fee));
		String msg = getString(R.string.bankplus_confirm_charge_money_to_bank,
				bankName, serviceIndicatorName, account,
				StringUtils.formatMoney(money), moneyText, hoTen, senderIsdn,
				StringUtils.formatMoney(fee), feeText);
		String title = getString(R.string.confirm_charge_to_bank_title);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg, R.drawable.ic_info, onPaymentConfirmClick);
		newFragment.show(ft, "dialog");
	}

	private OnClickListener onPaymentConfirmClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			StringBuilder data = new StringBuilder();

			data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
			data.append("CMDCB").append(Constant.STANDARD_CONNECT_CHAR);
			data.append(CommonActivity.getStardardIsdn(edtIsdnSend.getText().toString().trim()))
					.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(StringUtils.stripAccents(edtSender.getText().toString().trim()))
					.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(edtMoneyCharge.getText().toString().trim().replace(".", ""))
					.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(serviceIndicator)
					.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(CommonActivity.getStardardIsdn(edtAccount.getText().toString().trim()))
					.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(lstBank.get(spnBank.getSelectedItemPosition()).getParType())
					.append(Constant.STANDARD_CONNECT_CHAR);

			String pinCode = "123456";
			if (pinCodeLayout.getVisibility() == View.VISIBLE) {
				pinCode = edtPinCode.getText().toString().trim();
			}
			data.append(pinCode).append(Constant.STANDARD_CONNECT_CHAR);

			CreateBankPlusAsyncTask asy = new CreateBankPlusAsyncTask(
					data.toString(), activity, onPostPayment, moveLogInAct);
			asy.execute();
			edtPinCode.setText("");
		}
	};

	OnPostExecuteListener<BankPlusOutput> onPostPayment = new OnPostExecuteListener<BankPlusOutput>() {

		@Override
		public void onPostExecute(BankPlusOutput result, String errorCode,
				String description) {

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Fragment prev = getFragmentManager().findFragmentByTag("dialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			StringBuilder msg = new StringBuilder();

			msg.append(getString(R.string.payment_information));
			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			String money = edtMoneyCharge.getText().toString().trim()
					.replace(".", "");
			msg.append(getString(R.string.money_payment, 
					StringUtils.formatMoney(money)));
			
			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			String fee = result.getFee();
			if (CommonActivity.isNullOrEmpty(fee)) {
				fee = "0";
			}
			msg.append("- ");
			msg.append(getString(R.string.fee_param,
					StringUtils.formatMoney(fee)));

			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			String discount = result.getDiscount();
			if (CommonActivity.isNullOrEmpty(discount)) {
				discount = "0";
			}
			String balance = result.getBalance();
			if (CommonActivity.isNullOrEmpty(balance)) {
				balance = "0";
			}
			DialogFragment newFragment = DialogConfirmCreateBankplusTrans
					.newInstance(getString(R.string.charge_to_bank_success),
							msg.toString(), R.drawable.ic_success, null);
			newFragment.show(ft, "dialog");

		}
	};

	private TextWatcher edtMoneyChargeChange = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			tvAmountWord.requestFocus();
			fee = "";
			tvViewFee
					.setText(Html
							.fromHtml(getString(R.string.bankplus_check_fee_underline)));
			if (!isFormatted) {
				String money = s.toString().replaceAll("\\.", "");
				isFormatted = true;
				money = StringUtils.formatMoney(money);
				if (!"0".equals(money)) {
					edtMoneyCharge.setText(money);
					edtMoneyCharge.setSelection(edtMoneyCharge.getText()
							.toString().length());
				} else {
					edtMoneyCharge.setText("");
				}
				isFormatted = false;
			}

			String money = s.toString().replaceAll("\\.", "");
			if (!CommonActivity.isNullOrEmpty(money)) {
				Long moneyNumber = Long.valueOf(money);
				if (moneyNumber.compareTo(0L) == 0) {
					tvAmountWord.setText("");
				} else {
					String moneyText = NumberToTextUtil.toword(moneyNumber);
					tvAmountWord.setText(moneyText);
				}

			} else {
				tvAmountWord.setText("");
			}

		}
	};
	private TextWatcher edtIsdnSendListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (CommonActivity.validateIsdn(s.toString().trim())) {
				edtIsdnSend.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.ic_check_black_24dp, 0);
			} else {
				edtIsdnSend.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			}
		}
	};

	OnItemSelectedListener onBankSelected = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			resetFee();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private void resetFee(){
		fee = "";
//		edtMoneyCharge.setText("");
		tvViewFee.setText(Html.fromHtml(getString(R.string.bankplus_check_fee_underline)));
	}

	private void doGetFee(final Boolean isCharge) {
		String token = Session.getToken();

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append("KPP400").append(Constant.STANDARD_CONNECT_CHAR);
		String money = edtMoneyCharge.getText().toString().replace(".", "");
		data.append(money).append(Constant.STANDARD_CONNECT_CHAR);
		String bankCode = lstBank.get(spnBank.getSelectedItemPosition())
				.getParType();
		data.append(bankCode).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(edtAccount.getText().toString().trim())).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(serviceIndicator);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						if ("0".equals(errorCode)) {
							fee = result.getFee();
							if (CommonActivity.isNullOrEmpty(fee)) {
								fee = "0";
							}
							tvViewFee.setText(getString(R.string.bankplus_fee,
									StringUtils.formatMoney(fee)));
							if (isCharge) {
								showDialogConfirm();
							}
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
							fee = "";
						}
					}
				}, moveLogInAct, getString(R.string.calculating_fee)).execute();
	}

	private boolean validateViewFee() {
		String money = edtMoneyCharge.getText().toString();
		if (CommonActivity.isNullOrEmpty(money)) {
			edtMoneyCharge.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.money_charge_bank_null, R.string.app_name).show();
			return false;
		}

		if(CommonActivity.isNullOrEmpty(edtAccount)){
			CommonActivity.toast(getActivity(), R.string.bankplus_err_isdn_receiver_required);
			return false;
		}

		if(!CommonActivity.validateIsdn(edtAccount.getText().toString().trim())){
			CommonActivity.toast(getActivity(), R.string.bankplus_err_isdn_receiver_invalid);
			return false;
		}

		return true;
	}

	private boolean validateConfirm() {
		String isdnAccount = edtIsdnSend.getText().toString().trim();

		if (!CommonActivity.validateIsdn(isdnAccount)) {
			edtIsdnSend.requestFocus();
			CommonActivity.createAlertDialog(activity, R.string.isdn_not_valid,
					R.string.app_name).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edtSender.getText().toString().trim())) {
			edtSender.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.sender_name_emplty, R.string.app_name).show();
			return false;
		}
		if (!rbAccount.isChecked() && rbCard.isChecked()
				&& !rbPhone.isChecked()) {
			CommonActivity.createAlertDialog(activity,
					R.string.service_indicator_empty, R.string.app_name).show();
			return false;
		}
		if (CommonActivity
				.isNullOrEmpty(edtAccount.getText().toString().trim())) {
			edtAccount.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.service_indicator_empty, R.string.app_name).show();
			return false;
		}
		String money = edtMoneyCharge.getText().toString().trim()
				.replace(".", "");
		if (CommonActivity.isNullOrEmpty(money)) {
			edtMoneyCharge.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.money_charge_to_bank_null, R.string.app_name)
					.show();
			return false;
		}

		if (pinCodeLayout.getVisibility() == View.VISIBLE) {
			if (CommonActivity.isNullOrEmpty(edtPinCode.getText().toString().trim())) {
				edtPinCode.requestFocus();
				CommonActivity.createAlertDialog(activity, R.string.pincode_empty,
						R.string.app_name).show();
				return false;
			}
		}

		return true;
	}

	private TextWatcher edtAccountTextChange = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (rbPhone.isChecked()) {
				if (CommonActivity.validateIsdn(s.toString().trim())) {
					edtAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0,
							R.drawable.ic_check_black_24dp, 0);
				} else {
					edtAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
							0);
				}
			} else {
				edtAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.ic_check_black_24dp, 0);
			}

//			if(!CommonActivity.isNullOrEmpty(s.toString())) {
				resetFee();
//			}
		}
	};
}
