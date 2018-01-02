package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.NumberToTextUtil;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.object.DataMapping;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class TransferMoneyFragment extends FragmentCommon {

	private static final int MAX_AMOUNT_TRANSFER_AT_SHOP = 50000000;
	private static final int MAX_AMOUNT_TRANSFER_AT_HOME = 20000000;
	private static final int AMOUNT_REQUIRED_SHOP = 5000000;
	private static final int MIN_AMOUNT_TRANSFER = 10000;
	
	private ViewHolder holder;
	private String address;
	private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();

	private String province = "";
	private String provinceName = "";
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();

	private String district = "";
	private String districtName = "";
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();

	private String precinct = "";
	private String precinctName = "";
	private String areaCode = "";
	private String fee;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTitleActionBar(getString(R.string.bankplus_transfer_money_menu));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		idLayout = R.layout.layout_transfer_money;
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		holder.btnTransferMoney = (Button) v.findViewById(R.id.btnTransferMoney);
		holder.btnTransferMoney.setOnClickListener(this);
		holder.edtAddressHomeNumber = (EditText) v.findViewById(R.id.edtAddressHomeNumber);
		holder.edtAmount = (EditText) v.findViewById(R.id.edtAmount);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.spnShop = (Spinner) v.findViewById(R.id.spnShop);
		holder.edtDistrict = (EditText) v.findViewById(R.id.edtDistrict);
		holder.edtHoVaTenSend = (EditText) v.findViewById(R.id.edtHoVaTenSend);
		holder.edtIdNo = (EditText) v.findViewById(R.id.edtIdNo);
		holder.edtIsdnReceiver = (EditText) v.findViewById(R.id.edtIsdnReceiver);
		holder.edtHoVaTenReceiver = (EditText) v.findViewById(R.id.edtHoVaTenReceiver);
		holder.edtIsdnSend = (EditText) v.findViewById(R.id.edtIsdnSend);
		holder.edtPin = (EditText) v.findViewById(R.id.edtPin);
		holder.edtPrecint = (EditText) v.findViewById(R.id.edtPrecint);
		holder.edtProvince = (EditText) v.findViewById(R.id.edtProvince);
		holder.rbAtHome = (RadioButton) v.findViewById(R.id.rbAtHome);
		holder.rbAtShop = (RadioButton) v.findViewById(R.id.rbAtShop);
		holder.rbStandardTime = (RadioButton) v.findViewById(R.id.rbStandardTime);
		holder.rbFastTime = (RadioButton) v.findViewById(R.id.rbFastTime);
		holder.tvGetFee = (TextView) v.findViewById(R.id.tvGetFee);
		holder.tvFeeTransfer = (TextView) v.findViewById(R.id.tvFeeTransfer);
		holder.edtContentTransfer = (EditText) v.findViewById(R.id.edtContentTransfer);
		holder.lnAddressHomeNumber = (LinearLayout) v.findViewById(R.id.lnAddressHomeNumber);
		holder.lnShop = (LinearLayout) v.findViewById(R.id.lnShop);
		holder.tvGetFee.setOnClickListener(this);
		holder.edtIsdnSend.requestFocus();
		holder.lnTimeTransfer = (LinearLayout) v.findViewById(R.id.lnTimeTransfer);
		holder.tvTitleAmountAtHome = (TextView) v.findViewById(R.id.tvTitleAmountAtHome);
		holder.tvTitleAmountAtShop = (TextView) v.findViewById(R.id.tvTitleAmountAtShop);
		holder.pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);

		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
			holder.pinCodeLayout.setVisibility(View.VISIBLE);
		} else {
			holder.pinCodeLayout.setVisibility(View.GONE);
		}

		arrProvince = DataUtils.getProvince(getActivity());

		holder.edtProvince.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
				intent.putExtra("arrProvincesKey", arrProvince);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "1");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 106);
			}
		});

		holder.rbAtHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				holder.edtProvince.setText("");
				holder.lnAddressHomeNumber.setVisibility(View.VISIBLE);
				holder.spnShop.setAdapter(null);
				holder.lnShop.setVisibility(View.GONE);
				holder.lnTimeTransfer.setVisibility(View.VISIBLE);
				holder.tvTitleAmountAtHome.setVisibility(View.VISIBLE);
				holder.tvTitleAmountAtShop.setVisibility(View.GONE);
				
				holder.tvGetFee.setText(getString(R.string.bankplus_check_fee));
				fee = "";
			}
		});

		holder.rbAtShop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				holder.edtProvince.setText("");
				holder.edtAddressHomeNumber.setText("");
				holder.lnAddressHomeNumber.setVisibility(View.GONE);
				holder.lnShop.setVisibility(View.VISIBLE);
				holder.lnTimeTransfer.setVisibility(View.GONE);
				holder.tvTitleAmountAtHome.setVisibility(View.GONE);
				holder.tvTitleAmountAtShop.setVisibility(View.VISIBLE);
				holder.tvGetFee.setText(getString(R.string.bankplus_check_fee));
				fee = "";
			}
		});

		holder.edtAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				String moneyNumber = StringUtils.getTextDefault(holder.edtAmount);
				if (!CommonActivity.isNullOrEmpty(moneyNumber) && StringUtils.isDigit(moneyNumber)) {
					String textNumber = NumberToTextUtil.toword(Long.valueOf(moneyNumber));
					holder.tvAmountWord.setText(textNumber);
					holder.tvAmountWord.setVisibility(View.VISIBLE);
				} else {
					holder.tvAmountWord.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable edt) {
				// TODO Auto-generated method stub
				holder.edtAmount.removeTextChangedListener(this);
				String moneyNumber = StringUtils.getTextDefault(holder.edtAmount);
				holder.edtAmount.setText(StringUtils.formatMoney(moneyNumber));
				holder.edtAmount.setSelection(holder.edtAmount.getText().toString().length());
				holder.edtAmount.addTextChangedListener(this);
				
				fee = "";
				holder.tvFeeTransfer.setText("");
				holder.tvFeeTransfer.setVisibility(View.GONE);
				holder.tvGetFee.setText(getString(R.string.bankplus_check_fee));
			}
		});
		
		holder.rbFastTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fee = "";
				holder.tvFeeTransfer.setText("");
				holder.tvFeeTransfer.setVisibility(View.GONE);
				holder.tvGetFee.setText(getString(R.string.bankplus_check_fee));
			}
		});
		
		holder.rbStandardTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fee = "";
				holder.tvFeeTransfer.setText("");
				holder.tvFeeTransfer.setVisibility(View.GONE);
				holder.tvGetFee.setText(getString(R.string.bankplus_check_fee));
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btnTransferMoney:
			if(validateTransferMoney()){
				if(CommonActivity.isNullOrEmpty(fee)){
					doGetFee(true);
				} else {
					showDialogConfirm();
				}
			}
			break;
		case R.id.tvGetFee:
			if(validateGetFee()){
				doGetFee(false);
			}
			break;
		default:
			break;
		}
	}
	
	private boolean validateTransferMoney(){
		holder.btnTransferMoney.requestFocus();
		
		if(CommonActivity.isNullOrEmpty(holder.edtIsdnSend)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_isdn_sender_required), Toast.LENGTH_LONG).show();
			holder.edtIsdnSend.requestFocus();
			return false;
		}
		
		if (!CommonActivity.validateIsdn(holder.edtIsdnSend.getText()
				.toString())) {
			holder.edtIsdnSend.requestFocus();
			CommonActivity.createAlertDialog(getActivity(),
					R.string.bankplus_err_isdn_sender_invalid,
					R.string.app_name).show();
			return false;
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtHoVaTenSend)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_ho_ten_sender_required), Toast.LENGTH_LONG).show();
			holder.edtHoVaTenSend.requestFocus();
			return false;
		}

		if(CommonActivity.isNullOrEmpty(holder.edtHoVaTenReceiver)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_ho_ten_receiver_required), Toast.LENGTH_LONG).show();
			holder.edtHoVaTenReceiver.requestFocus();
			return false;
		}
		
		if(StringUtils.CheckCharSpecical(holder.edtHoVaTenSend.getText().toString())){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_ho_ten_sender_contain_special_char), Toast.LENGTH_LONG).show();
			holder.edtHoVaTenSend.requestFocus();
			return false;
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtIdNo)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_id_no_sender_required), Toast.LENGTH_LONG).show();
			holder.edtIdNo.requestFocus();
			return false;
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtIsdnReceiver)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_isdn_receiver_required), Toast.LENGTH_LONG).show();
			holder.edtIsdnReceiver.requestFocus();
			return false;
		}
		
		if (!CommonActivity.validateIsdn(holder.edtIsdnReceiver.getText()
				.toString())) {
			holder.edtIsdnReceiver.requestFocus();
			CommonActivity.createAlertDialog(getActivity(),
					R.string.bankplus_err_isdn_receiver_invalid,
					R.string.app_name).show();
			return false;
		}
		
		if(holder.rbAtHome.isChecked()){
			if(CommonActivity.isNullOrEmpty(holder.edtProvince)){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_province_receiver_required), Toast.LENGTH_LONG).show();
				holder.edtProvince.requestFocus();
				return false;
			}
			
			if(CommonActivity.isNullOrEmpty(holder.edtAddressHomeNumber)){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_address_home_receiver_required), Toast.LENGTH_LONG).show();
				holder.edtAddressHomeNumber.requestFocus();
				return false;
			}
			
			if(StringUtils.CheckCharSpecical(holder.edtAddressHomeNumber.getText().toString())){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_address_home_receiver_contain_special_char), Toast.LENGTH_LONG).show();
				holder.edtAddressHomeNumber.requestFocus();
				return false;
			}
		}

		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_required), Toast.LENGTH_LONG).show();
			holder.edtAmount.requestFocus();
			return false;
		}
		
		int amount = Integer.valueOf(StringUtils
				.getTextDefault(holder.edtAmount));
		if(holder.rbAtHome.isChecked()){
			if(amount < MIN_AMOUNT_TRANSFER || amount> MAX_AMOUNT_TRANSFER_AT_HOME){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_at_home_invalid), Toast.LENGTH_LONG).show();
				holder.edtAmount.requestFocus();
				return false;
			}
		} else {
			if(amount < MIN_AMOUNT_TRANSFER || amount> MAX_AMOUNT_TRANSFER_AT_SHOP){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_at_shop_invalid), Toast.LENGTH_LONG).show();
				holder.edtAmount.requestFocus();
				return false;
			}
		}
		
		if(holder.rbAtShop.isChecked()){
			if(amount > AMOUNT_REQUIRED_SHOP){
				if(CommonActivity.isNullOrEmpty(holder.edtProvince)){
					Toast.makeText(getActivity(), getString(R.string.bankplus_err_province_receiver_required), Toast.LENGTH_LONG).show();
					holder.edtProvince.requestFocus();
					return false;
				}
				
				if(holder.spnShop.getSelectedItem() == null){
					Toast.makeText(getActivity(), getString(R.string.bankplus_err_spn_shop_required), Toast.LENGTH_LONG).show();
					holder.spnShop.requestFocus();
					return false;
				}
			}
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtContentTransfer)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_content_transfer_required), Toast.LENGTH_LONG).show();
			holder.edtContentTransfer.requestFocus();
			return false;
		}
		
		if(StringUtils.CheckCharSpecical(holder.edtContentTransfer.getText().toString())){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_content_transfer_contain_special_char), Toast.LENGTH_LONG).show();
			holder.edtContentTransfer.requestFocus();
			return false;
		}

		if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
			if(CommonActivity.isNullOrEmpty(holder.edtPin)){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_pin_required), Toast.LENGTH_LONG).show();
				holder.edtPin.requestFocus();
				return false;
			}
		}

		return true;
	}
	
	private boolean validateGetFee(){
		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_required),
					Toast.LENGTH_LONG).show();
			holder.edtAmount.requestFocus();
			return false;
		}

		if(holder.rbAtHome.isChecked()){
			if(CommonActivity.isNullOrEmpty(holder.edtProvince)){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_province_receiver_required), Toast.LENGTH_LONG).show();
				holder.edtProvince.requestFocus();
				return false;
			}

			if(CommonActivity.isNullOrEmpty(holder.edtAddressHomeNumber)){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_address_home_receiver_required), Toast.LENGTH_LONG).show();
				holder.edtAddressHomeNumber.requestFocus();
				return false;
			}

			if(StringUtils.CheckCharSpecical(holder.edtAddressHomeNumber.getText().toString())){
				Toast.makeText(getActivity(), getString(R.string.bankplus_err_address_home_receiver_contain_special_char), Toast.LENGTH_LONG).show();
				holder.edtAddressHomeNumber.requestFocus();
				return false;
			}
		}

		return true;
	}

	private void doTransferMoney() {
		String token = Session.getToken();

		String pinCode = "123456";
		if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
			pinCode = holder.edtPin.getText().toString();
		}

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.rbAtHome.isChecked()
				? BPConstant.COMMAND_TRANSFER_AT_HOME
				: BPConstant.COMMAND_TRANSFER_AT_SHOP).append(Constant.STANDARD_CONNECT_CHAR);

		data.append(holder.edtAmount.getText().toString().trim().replaceAll("\\.", ""))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(fee).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(holder.edtIsdnReceiver.getText().toString().trim()))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtHoVaTenReceiver.getText().toString().trim())
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.stripAccents(holder.edtHoVaTenSend.getText().toString().trim()))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtIdNo.getText().toString().trim())
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(holder.edtIsdnSend.getText().toString().trim()))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.stripAccents(provinceName))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.rbAtHome.isChecked()
				? StringUtils.stripAccents(districtName + "#" + precinctName + "#" + holder.edtAddressHomeNumber.getText().toString())
				: (holder.spnShop.getSelectedItem() != null ? ((DataMapping)(holder.spnShop.getSelectedItem())).getCode() : "null"))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.rbFastTime.isChecked() ? "fast" : "normal")
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtContentTransfer.getText().toString().trim())
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(pinCode).append(Constant.STANDARD_CONNECT_CHAR);

		//remove ma pin truoc khi goi lenh gui ban tin
		holder.edtPin.setText("");
		
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							resetForm();
							showDialogSuccess();
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();

	}
	
	private void showDialogSuccess(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans.newInstance(
				getString(R.string.bankplus_transfer_money_menu),
				getString(R.string.bankplus_transfer_money_success),
				R.drawable.ic_success, null);
		newFragment.show(ft, "dialog");
	}
	
	private void resetForm(){
		holder.edtIsdnSend.setText("");
		holder.edtHoVaTenSend.setText("");
		holder.edtIdNo.setText("");
		holder.edtAmount.setText("");
		holder.edtIsdnReceiver.setText("");
		holder.edtProvince.setText("");
		holder.edtAddressHomeNumber.setText("");
		holder.tvAmountWord.setText("");
		holder.tvAmountWord.setVisibility(View.GONE);
		holder.edtContentTransfer.setText("");
		holder.edtPin.setText("");
		holder.edtIsdnSend.requestFocus();
		holder.spnShop.setAdapter(null);
		fee = "";
	}

	private void doGetFee(final boolean transferMoney) {
		String token = Session.getToken();

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_GET_FEE).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.rbAtHome.isChecked() ? BPConstant.SERVICE_INDICATOR.RECEIVER_AT_HOME : BPConstant.SERVICE_INDICATOR.RECEIVER_AT_SHOP).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", "")).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.rbFastTime.isChecked() ? "fast" : "normal");

		if(holder.rbAtHome.isChecked()){
			data.append(Constant.STANDARD_CONNECT_CHAR);
			data.append(StringUtils.stripAccents(provinceName)).append(Constant.STANDARD_CONNECT_CHAR);
			data.append(StringUtils.stripAccents(districtName) + "#" + StringUtils.stripAccents(precinctName) + "#" + StringUtils.stripAccents(holder.edtAddressHomeNumber.getText().toString())).append(Constant.STANDARD_CONNECT_CHAR);
		}
		
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							fee = result.getFee();
							
							holder.tvFeeTransfer.setText(getString(R.string.bankplus_fee, StringUtils.formatMoney(fee)));
							holder.tvGetFee.setText(getString(R.string.bankplus_fee, StringUtils.formatMoney(fee)));
//							holder.tvFeeTransfer.setVisibility(View.GONE);
							
							if(transferMoney){
								if(validateTransferMoney()){
									showDialogConfirm();
								}
							}
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
//							holder.tvFeeTransfer.setVisibility(View.GONE);
						}

					}
				}, moveLogInAct).execute();
	}
	
	private void doGetShop() {
		String token = Session.getToken();

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_SHOP_RECEIVER_MONEY).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(areaCode).append(Constant.STANDARD_CONNECT_CHAR);
		
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							Utils.setDataSpinner(getActivity(), result.getLstDataMappings(), holder.spnShop);
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}
	
	private void showDialogConfirm() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		String msg = getString(holder.rbAtHome.isChecked() ? R.string.bankplus_confirm_transfer_money_at_home 
				: R.string.bankplus_confirm_transfer_money_at_shop, 
				holder.edtHoVaTenSend.getText().toString(), 
				holder.edtIsdnSend.getText().toString(), 
				holder.edtIdNo.getText().toString(),
//				holder.edtHoVaTenReceiver.getText().toString(), 
				holder.edtIsdnReceiver.getText().toString(), 
				holder.edtAmount.getText().toString(), 
				holder.tvAmountWord.getText().toString(),
				holder.rbAtHome.isChecked() ? (holder.edtAddressHomeNumber.getText().toString() + ", " + holder.edtProvince.getText().toString()) 
						: holder.spnShop.getSelectedItem() != null ? (((DataMapping) holder.spnShop.getSelectedItem()).getName() + "," + holder.edtProvince.getText().toString()) : holder.edtProvince.getText().toString(), 
				StringUtils.formatMoney(fee), 
				NumberToTextUtil.toword(Long.valueOf(fee)));
		
		String title = getString(R.string.bankplus_title_confirm_transfer_money);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans.newInstance(
				title, msg.toString(), 0, onTransferMoneyClick);
		newFragment.show(ft, "dialog");
	}
	
	private OnClickListener onTransferMoneyClick = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			doTransferMoney();
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 106:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

					province = areaBean.getProvince();
					arrDistrict = DataUtils.initDistrict(getActivity(), province);
					address = areaBean.getNameProvince();
					provinceName = areaBean.getNameProvince();

					Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
					intent.putExtra("arrDistrictKey", arrDistrict);
					Bundle mBundle = new Bundle();
					mBundle.putString("checkKey", "2");
					intent.putExtras(mBundle);
					startActivityForResult(intent, 116);
				}
				break;
			case 108:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
					precinct = areaBean.getPrecinct();
					precinctName = areaBean.getNamePrecint();
					areaCode = areaBean.getAreaCode();

					address = areaBean.getNamePrecint() + "-" + address;
					holder.edtProvince.setText(address);

					if(holder.rbAtShop.isChecked()){
						doGetShop();
					}
				}
				break;
			case 116:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
					district = areaBean.getDistrict();
					districtName = areaBean.getNameDistrict();
					arrPrecinct = DataUtils.initPrecinct(getActivity(), province, district);

					address = areaBean.getNameDistrict() + "-" + address;

					Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
					intent.putExtra("arrPrecinctKey", arrPrecinct);
					Bundle mBundle = new Bundle();
					mBundle.putString("checkKey", "3");
					intent.putExtras(mBundle);
					startActivityForResult(intent, 108);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
	}

	class ViewHolder {
		EditText edtIsdnSend;
		EditText edtHoVaTenSend;
		EditText edtIdNo;
		EditText edtIsdnReceiver;
		EditText edtHoVaTenReceiver;
		EditText edtProvince;
		EditText edtDistrict;
		EditText edtPrecint;
		LinearLayout lnTimeTransfer;
		TextView tvTitleAmountAtHome;
		TextView tvTitleAmountAtShop;

		RadioButton rbAtHome;
		RadioButton rbAtShop;
		EditText edtAddressHomeNumber;
		Spinner spnShop;

		EditText edtAmount;
		TextView tvAmountWord;
		RadioButton rbStandardTime;
		RadioButton rbFastTime;
		TextView tvGetFee;
		TextView tvFeeTransfer;

		EditText edtContentTransfer;
		EditText edtPin;
		Button btnTransferMoney;
		
		LinearLayout lnAddressHomeNumber;
		LinearLayout lnShop;
		LinearLayout pinCodeLayout;
	}
}
