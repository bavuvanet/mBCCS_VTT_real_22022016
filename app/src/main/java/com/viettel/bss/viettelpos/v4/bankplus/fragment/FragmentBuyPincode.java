package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.ListPriceAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.MerchantBean;
import com.viettel.bss.viettelpos.v4.bankplus.object.TelecomSuppliers;
import com.viettel.bss.viettelpos.v4.bankplus.view.ExpandableHeightGridView;
import com.viettel.bss.viettelpos.v4.business.ApParamBusiness;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentBuyPincode extends FragmentCommon {

	private Activity activity;
	private TextView tvRemainMoney;
	private EditText edtIsdnAccount, edtMoneyCharge, edtPinCode;
	private View btnPayment;
	private ExpandableHeightGridView gvPrice;
	private Spinner spnSupplier;
	private ArrayList<FormBean> lstPrice = new ArrayList<FormBean>();
	private ArrayList<TelecomSuppliers> lstSupplier = new ArrayList<TelecomSuppliers>();
	private ListPriceAdapter priceAdapter;
	private LinearLayout linVerify;
	private LinearLayout lnPayInfo;
	private String oringinalRequestId;
	private String tidNumber;
	private String serviceCode;
	private TelecomSuppliers telecomSuppliers;

	private LinearLayout pinCodeLayout;

	// private List
	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(activity.getString(R.string.buy_pincode));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_buy_pin_code;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		tvRemainMoney = (TextView) v.findViewById(R.id.tvRemainMoney);
		edtIsdnAccount = (EditText) v.findViewById(R.id.edtIsdnAccount);
		edtMoneyCharge = (EditText) v.findViewById(R.id.edtMoneyCharge);
		edtPinCode = (EditText) v.findViewById(R.id.edtPinCode);
		spnSupplier = (Spinner) v.findViewById(R.id.spnSupplier);
		btnPayment = v.findViewById(R.id.btnPayment);
		btnPayment.setOnClickListener(onPaymentClick);
		gvPrice = (ExpandableHeightGridView) v.findViewById(R.id.gvPrice);
		lstSupplier = ApParamBusiness.getLstTelecomSuppliers(activity,
				"TELECOM_SUPPLIERS");
		linVerify = (LinearLayout) v.findViewById(R.id.linVerify);
		lnPayInfo = (LinearLayout) v.findViewById(R.id.lnPayInfo);

		pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);
		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
//		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
//			pinCodeLayout.setVisibility(View.VISIBLE);
//		} else {
			pinCodeLayout.setVisibility(View.GONE);
//		}
		
		if (CommonActivity.isNullOrEmpty(lstSupplier)) {
			lstSupplier = new ArrayList<TelecomSuppliers>();
			TelecomSuppliers spn = new TelecomSuppliers();
			spn.setCode("VT");
			spn.setName("Viettel");
			spn.setValue("10000;20000;30000;50000;100000;200000;300000;500000");
		}

		Utils.setDataSpinner(getContext(), lstSupplier, spnSupplier);
		gvPrice.setOnItemClickListener(onPriceClick);
		spnSupplier.setOnItemSelectedListener(onSupplierSelect);
		edtIsdnAccount.addTextChangedListener(edtPhoneChange);

		edtPinCode.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					handled = true;
					doBuyPinCode();
				}
				return handled;
			}
		});

		linVerify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				checkDebitInfor();
			}
		});
	}
	
	private void checkDebitInfor(){
		if(CommonActivity.isNullOrEmpty(edtMoneyCharge)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_card_game_required), Toast.LENGTH_LONG).show();
			return;
		}
		
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_CHECK_DEBIT_INFOR).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append("TOPUP_" + telecomSuppliers.getCode()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(edtMoneyCharge.getText().toString().trim()
				.replace(".", "")).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(edtMoneyCharge.getText().toString().trim()
				.replace(".", "")).append(
				Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							oringinalRequestId = result.getOringinalRequestId();
							tidNumber = result.getTidNumber();
							serviceCode = result.getServiceCode();
							lnPayInfo.setVisibility(View.VISIBLE);
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	@Override
	public void setPermission() {
		permission = ";m.pay.telecom;";

	}

	OnItemClickListener onPriceClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			for (FormBean item : lstPrice) {
				item.setChecked(false);
			}
			
			if("VT".equals(telecomSuppliers.getCode())){
				lnPayInfo.setVisibility(View.VISIBLE);
			} else {
				lnPayInfo.setVisibility(View.GONE);
			}
			lstPrice.get(arg2).setChecked(true);
			edtMoneyCharge.setText(StringUtils.formatMoney(lstPrice.get(arg2)
					.getCode()));
			priceAdapter.notifyDataSetChanged();
		}
	};

	OnClickListener onPaymentClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			doBuyPinCode();
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
		msg.append(getString(R.string.transaction_info));
		msg.append("<br/>");
		msg.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		msg.append("- ");
		TelecomSuppliers supp = lstSupplier.get(spnSupplier
				.getSelectedItemPosition());
		msg.append(getString(R.string.supplier)).append(" " + supp.getName());
		msg.append("<br/>");
		msg.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		msg.append("- ");
		msg.append(getString(R.string.price_pincode)).append(" ");
		msg.append("<font color=\"blue\">");
		msg.append(edtMoneyCharge.getText().toString().trim());
		msg.append("</font>");
		msg.append("<br/>");
		msg.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		msg.append(getString(R.string.phone_receive_sms_param, edtIsdnAccount
				.getText().toString().trim()));
		msg.append("<br/>");

		String title = getString(R.string.confirm_buy_pincode);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0, onPaymentConfirmClick);
		newFragment.show(ft, "dialog");
	}

	private OnClickListener onPaymentConfirmClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			StringBuilder data = new StringBuilder();
			data.append(Session.token);
			data.append(Constant.STANDARD_CONNECT_CHAR);
			TelecomSuppliers supply = (TelecomSuppliers) spnSupplier
					.getSelectedItem();
			data.append("TOPUP_").append(supply.getCode());
			data.append(Constant.STANDARD_CONNECT_CHAR);
			if(!"VT".equals(telecomSuppliers.getCode())){
				data.append(serviceCode);
				data.append(Constant.STANDARD_CONNECT_CHAR);
			}
			String account = StringUtils.formatIsdn(edtIsdnAccount.getText()
					.toString().trim());
			data.append(account);

			data.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(edtMoneyCharge.getText().toString().trim()
					.replace(".", ""));
			data.append(Constant.STANDARD_CONNECT_CHAR);
			if(!"VT".equals(telecomSuppliers.getCode())){
				data.append(tidNumber).append(
						Constant.STANDARD_CONNECT_CHAR);
				data.append(oringinalRequestId).append(
						Constant.STANDARD_CONNECT_CHAR);
			}

			String pinCode = "123456";
			if (pinCodeLayout.getVisibility() == View.VISIBLE) {
				pinCode = edtPinCode.getText().toString().trim();
			}
			data.append(pinCode);

			String msg = getString(R.string.buying_pincode);
			CreateBankPlusAsyncTask asy = new CreateBankPlusAsyncTask(
					data.toString(), activity, onPostPayment, moveLogInAct, msg);
			asy.execute();
			edtPinCode.setText("");
		}
	};
	private OnItemSelectedListener onSupplierSelect = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			telecomSuppliers = (TelecomSuppliers) spnSupplier.getSelectedItem();
			edtIsdnAccount.setText("");
			edtPinCode.setText("");
			
			if("VT".equals(telecomSuppliers.getCode())){
				lnPayInfo.setVisibility(View.VISIBLE);
				linVerify.setVisibility(View.GONE);
			} else {
				lnPayInfo.setVisibility(View.GONE);
				linVerify.setVisibility(View.VISIBLE);
			}
			
			lstPrice = new ArrayList<FormBean>();
			String str = lstSupplier.get(arg2).getValue();

			if (!CommonActivity.isNullOrEmpty(str)
					&& !CommonActivity.isNullOrEmpty(str.trim())) {
				String[] tmp = str.split(";");
				if (!CommonActivity.isNullOrEmpty(tmp)) {
					boolean isAdd = false;
					String money = edtMoneyCharge.getText().toString()
							.replace(".", "");
					for (String price : tmp) {
						FormBean bean = new FormBean();
						bean.setCode(price);
						if (price.equals(money)) {
							bean.setChecked(true);
							isAdd = true;
						}
						lstPrice.add(bean);

					}
					if (!isAdd) {
						edtMoneyCharge.setText("");
					}
				}
			}
			if (!CommonActivity.isNullOrEmpty(lstPrice)) {
				gvPrice.setExpanded(true);
				priceAdapter = new ListPriceAdapter(lstPrice, activity);
				gvPrice.setAdapter(priceAdapter);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

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

			String balance = result.getBalance();
			if (CommonActivity.isNullOrEmpty(balance)) {
				balance = "0";
			}
			DialogFragment newFragment = DialogConfirmCreateBankplusTrans
					.newInstance(getString(R.string.payment_charge_success),
							msg.toString(), R.drawable.ic_success, null);
			newFragment.show(ft, "dialog");
		}
	};

	private TextWatcher edtPhoneChange = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (CommonActivity.validateIsdn(s.toString().trim())) {
				edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.ic_check_black_24dp, 0);
			} else {
				edtIsdnAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
						0);
			}

		}
	};

	private void doBuyPinCode() {
		String money = edtMoneyCharge.getText().toString().trim()
				.replace(".", "");
		if (CommonActivity.isNullOrEmpty(money)) {
			edtMoneyCharge.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.price_pincode_null, R.string.app_name).show();
			return;
		}
		String isdnAccount = edtIsdnAccount.getText().toString().trim();

		if (!CommonActivity.validateIsdn(isdnAccount)) {
			edtIsdnAccount.requestFocus();
			CommonActivity.createAlertDialog(activity,
					R.string.phone_receive_null_or_invalid, R.string.app_name)
					.show();
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
}
