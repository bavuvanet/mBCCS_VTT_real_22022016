package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.ListPriceAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CheckDebitAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.view.ExpandableHeightGridView;
import com.viettel.bss.viettelpos.v4.business.ApParamBusiness;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class FragmentPaymentCharge extends FragmentCommon {

	private Activity activity;
	private TextView tvRemainMoney, tvHint, tvIsdnAccount;
	private RadioButton rbMobile, rbFix;
	private EditText edtIsdnAccount, edtMoneyCharge, edtPinCode;
	private View imgCheckIsdn, btnPayment;
	private ExpandableHeightGridView gvPrice;
	private RadioGroup radioGroup;
	private ArrayList<FormBean> lstPrice = new ArrayList<FormBean>();
	private ListPriceAdapter adapter;
	private String debit = "";

	private LinearLayout pinCodeLayout;

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(activity
				.getString(R.string.menu_payment_debit));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_payment_charge_bankplus;
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
		tvRemainMoney = (TextView) v.findViewById(R.id.tvRemainMoney);
		tvHint = (TextView) v.findViewById(R.id.tvHint);
		tvIsdnAccount = (TextView) v.findViewById(R.id.tvIsdnAccount);
		rbMobile = (RadioButton) v.findViewById(R.id.rbMobile);
		rbFix = (RadioButton) v.findViewById(R.id.rbFix);
		edtIsdnAccount = (EditText) v.findViewById(R.id.edtIsdnAccount);
		edtMoneyCharge = (EditText) v.findViewById(R.id.edtMoneyCharge);
		edtPinCode = (EditText) v.findViewById(R.id.edtPinCode);
		imgCheckIsdn = v.findViewById(R.id.imgCheckIsdn);
		imgCheckIsdn.setOnClickListener(onClickView);
		imgCheckIsdn.setVisibility(View.GONE);
		btnPayment = v.findViewById(R.id.btnPayment);
		btnPayment.setOnClickListener(onClickView);
		gvPrice = (ExpandableHeightGridView) v.findViewById(R.id.gvPrice);
		radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
		pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);

		radioGroup.setOnCheckedChangeListener(onCheckChange);
		String strPrice = ApParamBusiness.getValue(activity,
				"PAYMENT_TELECOM_CHARGE", "PAYMENT_TELECOM_CHARGE");
		if (!CommonActivity.isNullOrEmpty(strPrice)
				&& !CommonActivity.isNullOrEmpty(strPrice.trim())) {
			String[] tmp = strPrice.split(";");
			if (!CommonActivity.isNullOrEmpty(tmp)) {
				for (String price : tmp) {
					FormBean bean = new FormBean();
					bean.setCode(price);
					lstPrice.add(bean);
				}
			}
		}
		if (!CommonActivity.isNullOrEmpty(lstPrice)) {
			gvPrice.setExpanded(true);
			adapter = new ListPriceAdapter(lstPrice, activity);
			gvPrice.setAdapter(adapter);
		}
		gvPrice.setOnItemClickListener(onPriceClick);
		edtMoneyCharge.addTextChangedListener(edtMoneyChargeChange);
		edtIsdnAccount.addTextChangedListener(edtPhoneChange);
		edtPinCode.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					handled = true;
					doPayment();
				}
				return handled;
			}
		});

		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
//		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
//			pinCodeLayout.setVisibility(View.VISIBLE);
//		} else {
			pinCodeLayout.setVisibility(View.GONE);
//		}
	}

	@Override
	public void setPermission() {
		permission = ";m.pay.telecom;";

	}

	OnCheckedChangeListener onCheckChange = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.rbMobile:
				InputFilter[] fArray = new InputFilter[1];
				fArray[0] = new InputFilter.LengthFilter(11);
				edtIsdnAccount.setFilters(fArray);
				imgCheckIsdn.setVisibility(View.GONE);
				tvHint.setText(R.string.payment_multi_supplier);
				tvIsdnAccount.setText(R.string.isdnStar);
				edtIsdnAccount.setInputType(InputType.TYPE_CLASS_PHONE);
				if (StringUtils.isViettelMobile(edtIsdnAccount.getText()
						.toString().trim())) {
					imgCheckIsdn.setVisibility(View.VISIBLE);
					edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				}
				edtIsdnAccount.setText("");
				edtMoneyCharge.setText("");
				break;
			case R.id.rbFix:
				InputFilter[] fArrayFix = new InputFilter[1];
				fArrayFix[0] = new InputFilter.LengthFilter(30);
				edtIsdnAccount.setFilters(fArrayFix);
				edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
						0);
				edtIsdnAccount.setInputType(InputType.TYPE_CLASS_TEXT);
				tvIsdnAccount.setText(R.string.account_require);
				if (edtIsdnAccount.getText().toString().trim().isEmpty()) {
					imgCheckIsdn.setVisibility(View.GONE);
				} else {
					imgCheckIsdn.setVisibility(View.VISIBLE);
				}

				tvHint.setText(R.string.payment_only_viettel);
				edtIsdnAccount.setText("");
				edtMoneyCharge.setText("");
				break;
			default:
				break;
			}
		}
	};

	OnItemClickListener onPriceClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			for (FormBean item : lstPrice) {
				item.setChecked(false);
			}
			lstPrice.get(arg2).setChecked(true);
			edtMoneyCharge.setText(StringUtils.formatMoney(lstPrice.get(arg2)
					.getCode()));
			adapter.notifyDataSetChanged();
		}
	};

	OnClickListener onClickView = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btnPayment:
				doPayment();
				break;
			case R.id.imgCheckIsdn:
				if (CommonActivity.isNullOrEmpty(debit)) {
					CheckDebitAsyncTask asy = new CheckDebitAsyncTask(onPostCheckDebit,
							activity, edtIsdnAccount.getText().toString()
									.trim());
					asy.execute();
				} else {
					edtMoneyCharge.setText(debit);
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
		StringBuilder msg = new StringBuilder();
		msg.append(getString(R.string.payment_info));
		msg.append("<br/>");
		msg.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		msg.append(getString(R.string.money_payment_not_replace)).append(" ");
		msg.append("<font color=\"blue\">");
		msg.append(edtMoneyCharge.getText().toString().trim());
		msg.append("</font>");
		msg.append("<br/>");
		msg.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		msg.append(getString(R.string.phone_payment, edtIsdnAccount.getText()
				.toString().trim()));
		String title = getString(R.string.confirm_payment_charge);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), R.drawable.ic_info,
						onPaymentConfirmClick);
		newFragment.show(ft, "dialog");
	}

	private OnClickListener onPaymentConfirmClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			StringBuilder data = new StringBuilder();
			data.append(Session.token);
			data.append(Constant.STANDARD_CONNECT_CHAR);

			data.append("PAYMENT");
			data.append(Constant.STANDARD_CONNECT_CHAR);
			String account = edtIsdnAccount.getText().toString().trim();
			if (rbMobile.isChecked()) {
				data.append("M");
				data.append(Constant.STANDARD_CONNECT_CHAR);
				account = CommonActivity.getStardardIsdnBCCS(account);
			} else {
				data.append("F");
				data.append(Constant.STANDARD_CONNECT_CHAR);
				account = getAccountStringChecked(account);
			}
			data.append(account);
			data.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(edtMoneyCharge.getText().toString().trim()
					.replace(".", ""));
			data.append(Constant.STANDARD_CONNECT_CHAR);

			String pinCode = "123456";
			if (pinCodeLayout.getVisibility() == View.VISIBLE) {
				pinCode = edtPinCode.getText().toString().trim();
			}
			data.append(pinCode);

			CreateBankPlusAsyncTask asy = new CreateBankPlusAsyncTask(
					data.toString(), activity, onPostPayment, moveLogInAct);
			asy.execute();

			edtPinCode.setText("");
			edtPinCode.setOnEditorActionListener(new OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					boolean handled = false;

					if (actionId == EditorInfo.IME_ACTION_DONE) {
						handled = true;
						doPayment();
					}
					return handled;
				}
			});

			resetForm();
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
			msg.append("- ");
			msg.append(getString(R.string.phone_receive_param, edtIsdnAccount
					.getText().toString().trim()));

			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			msg.append("- ");
			String fee = result.getFee();
			if (CommonActivity.isNullOrEmpty(fee)) {
				fee = "0";
			}
			msg.append(getString(R.string.fee_param,
					StringUtils.formatMoney(fee)));
			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			msg.append("- ");
			String discount = result.getDiscount();
			if (CommonActivity.isNullOrEmpty(discount)) {
				discount = "0";
			}
			msg.append(getString(R.string.discount_amount_param,
					StringUtils.formatMoney(discount)));

			msg.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
			DialogFragment newFragment = DialogConfirmCreateBankplusTrans
					.newInstance(getString(R.string.payment_charge_success),
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
			if (!CommonActivity.isNullOrEmpty(lstPrice)) {
				for (FormBean price : lstPrice) {
					if (price.getCode().equals(money)) {
						price.setChecked(true);
					} else {
						price.setChecked(false);
					}
				}
			}
			
			if(adapter != null){
				adapter.notifyDataSetChanged();
			}

		}
	};
	private TextWatcher edtPhoneChange = new TextWatcher() {

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
			debit = "";

			if (rbMobile.isChecked()) {
				// Thanh toan di dong
				if (StringUtils.isViettelMobile(s.toString().trim())) {
					// Neu la so viettel
					imgCheckIsdn.setVisibility(View.VISIBLE);
					edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(0,
							0, R.drawable.ic_check_black_24dp, 0);
					edtMoneyCharge.setEnabled(true);
				} else {
					if (CommonActivity.validateIsdn(s.toString().trim())) {
						edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(
								0, 0, R.drawable.ic_check_black_24dp, 0);
					} else {
						edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(
								0, 0, 0, 0);
					}
					imgCheckIsdn.setVisibility(View.GONE);
					
					if(!validMoney(edtMoneyCharge.getText().toString().trim().replace(".", ""))){
						edtMoneyCharge.setText("");
						edtMoneyCharge.setEnabled(false);
					}
					
				}
			} else {

				if (s.toString().trim().isEmpty()) {
					imgCheckIsdn.setVisibility(View.GONE);
				} else {
					imgCheckIsdn.setVisibility(View.VISIBLE);
				}
			}

			resetForm();

		}
	};
	
	private boolean validMoney(String money){
		if(CommonActivity.isNullOrEmpty(money)){
			return false;
		}
		
		for(FormBean  form : lstPrice){
			if(money.equals(form.getCode())){
				return true;
			}
		}
		return false;
	}

	private OnPostExecute<BankPlusOutput> onPostCheckDebit = new OnPostExecute<BankPlusOutput>() {

		@Override
		public void onPostExecute(BankPlusOutput result) {
			debit = result.getDescription();
			edtMoneyCharge.setText(debit);
			debit = "";
		}
	};

	private void doPayment() {
		String isdnAccount = edtIsdnAccount.getText().toString().trim();
		if (rbMobile.isChecked()) {
			if (!CommonActivity.validateIsdn(isdnAccount)) {
				edtIsdnAccount.requestFocus();
				CommonActivity.createAlertDialog(activity,
						R.string.isdn_not_valid, R.string.app_name).show();
				return;
			}
		} else {
			if (CommonActivity.isNullOrEmpty(isdnAccount)) {
				edtIsdnAccount.requestFocus();
				CommonActivity.createAlertDialog(activity,
						R.string.account_null, R.string.app_name).show();
				return;
			}
		}
		String money = edtMoneyCharge.getText().toString().trim()
				.replace(".", "");
		if (CommonActivity.isNullOrEmpty(money)) {
			edtMoneyCharge.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.money_charge_null, R.string.app_name).show();
			return;
		}

		if (pinCodeLayout.getVisibility() == View.VISIBLE) {
			if (CommonActivity.isNullOrEmpty(edtPinCode.getText().toString().trim())) {
				edtPinCode.requestFocus();
				CommonActivity.createAlertDialog(activity, R.string.pincode_empty,
						R.string.app_name).show();
				return;
			}
		}

		showDialogConfirm();
	}

	private String getAccountStringChecked(String account) {
		if (!account.matches(".*[a-z].*")) {
			if (account.startsWith("0")){
				return account.substring(0, account.length());
			}
		}
		return account;
	}

	private void resetForm(){
		edtMoneyCharge.setText("");
	}
}
