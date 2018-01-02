package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.ListPriceAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.fragment.PaymentElectricFragment.ViewHolder;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.MerchantBean;
import com.viettel.bss.viettelpos.v4.bankplus.view.ExpandableHeightGridView;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.NumberToTextUtil;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class PaymentTelevisionFragment extends FragmentCommon {
	private ViewHolder holder;
	private ArrayList<FormBean> lstPrice = new ArrayList<FormBean>();
	private ListPriceAdapter adapter;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		idLayout = R.layout.fragment_payment_television;
	}
	
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		holder.btnPayment = (Button) v.findViewById(R.id.btnPayment);
		holder.edtAmount = (EditText) v.findViewById(R.id.edtAmount);
		holder.edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		holder.edtPin = (EditText) v.findViewById(R.id.edtPin);
		holder.edtContractNo = (EditText) v.findViewById(R.id.edtContractNo);
		holder.spnMerchant = (Spinner) v.findViewById(R.id.spnMerchant);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.gvPrice = (ExpandableHeightGridView) v.findViewById(R.id.gvPrice);
		holder.gvPrice.setOnItemClickListener(onPriceClick);
		holder.linVerify = (LinearLayout) v.findViewById(R.id.linVerify);
		holder.lnPayInfor = (LinearLayout) v.findViewById(R.id.lnPayInfor);

		holder.pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);

		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
			holder.pinCodeLayout.setVisibility(View.VISIBLE);
		} else {
			holder.pinCodeLayout.setVisibility(View.GONE);
		}
		
		holder.btnPayment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialogConfirm();
			}
		});
		
		holder.spnMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				MerchantBean merchantBean = (MerchantBean)holder.spnMerchant.getSelectedItem();
				lstPrice.clear();
				holder.lnPayInfor.setVisibility(View.GONE);
				
				if(!CommonActivity.isNullOrEmpty(merchantBean.getAmount())){
					String[] lstPrices = merchantBean.getAmount().split(",");
					for(String price : lstPrices){
						FormBean formBean = new FormBean();
						formBean.setCode(price.trim());
						formBean.setName(price.trim());
						
						lstPrice.add(formBean);
					}
					
					holder.gvPrice.setExpanded(true);
					adapter = new ListPriceAdapter(lstPrice, getActivity());
					holder.gvPrice.setAdapter(adapter);
				} else {
					if(!CommonActivity.isNullOrEmpty(holder.gvPrice)){
						holder.gvPrice.setAdapter(null);
					}
				}
				
				resetForm();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		holder.edtContractNo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				holder.lnPayInfor.setVisibility(View.GONE);
			}
		});
		
		holder.linVerify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				checkDebitInfor();
			}
		});
		
		initMerchant();
	}
	
	private void checkDebitInfor(){
		if(CommonActivity.isNullOrEmpty(holder.edtContractNo)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_contract_no_required), Toast.LENGTH_LONG).show();
			holder.edtIsdn.requestFocus();
			return;
		}

		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_card_game_required), Toast.LENGTH_LONG).show();
			return;
		}
		
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_CHECK_DEBIT_INFOR).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceCode()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtContractNo.getText().toString().replaceAll("\\.", "")).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", "")).append(
				Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							holder.oringinalRequestId = result.getOringinalRequestId();
							holder.tidNumber = result.getTidNumber();
							holder.lnPayInfor.setVisibility(View.VISIBLE);
							holder.serviceCode = result.getServiceCode();
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTitleActionBar(getString(R.string.bankplus_menu_payment_invoice) + " " + getString(R.string.bankplus_payment_television_cab));
	}
	
	OnItemClickListener onPriceClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			for (FormBean item : lstPrice) {
				item.setChecked(false);
			}
			lstPrice.get(position).setChecked(true);
			holder.tvAmountWord.setText(NumberToTextUtil.toword(Long.valueOf(lstPrice.get(position).getCode())));
			holder.tvAmountWord.setVisibility(View.VISIBLE);
			holder.edtAmount.setText(StringUtils.formatMoney(lstPrice.get(position)
					.getCode()));
			adapter.notifyDataSetChanged();

			holder.lnPayInfor.setVisibility(View.GONE);
			holder.edtIsdn.setText("");
			holder.edtPin.setText("");

		}
	};
	
	private boolean validatePayment(){
		if(CommonActivity.isNullOrEmpty(holder.edtContractNo)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_set_top_box_required), Toast.LENGTH_LONG).show();
			holder.edtIsdn.requestFocus();
			return false;
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_card_game_required), Toast.LENGTH_LONG).show();
			holder.edtIsdn.requestFocus();
			return false;
		}
		
		if(CommonActivity.isNullOrEmpty(holder.edtIsdn)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_isdn_receiver_sms_required), Toast.LENGTH_LONG).show();
			holder.edtIsdn.requestFocus();
			return false;
		}
		
		if (!CommonActivity.validateIsdn(holder.edtIsdn.getText()
				.toString())) {
			holder.edtIsdn.requestFocus();
			CommonActivity.createAlertDialog(getActivity(),
					R.string.bankplus_err_isdn_receiver_invalid,
					R.string.app_name).show();
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
	
	private void showDialogConfirm() {
		if(!validatePayment()){
			return;
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		String msg = getString(R.string.bankplus_confirm_payment_television, 
				((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceName(), 
				holder.edtAmount.getText().toString(), 
				holder.tvAmountWord.getText().toString());
		
		String title = getString(R.string.bankplus_title_confirm_payment);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0, new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						doPayment();
					}
				});
		newFragment.show(ft, "dialog");
	}
	
	private void doPayment() {
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_PAYMENT_TELEVISON).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.serviceCode).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", "")).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(holder.edtIsdn.getText().toString())).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtContractNo.getText().toString().trim()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.tidNumber).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.oringinalRequestId).append(
				Constant.STANDARD_CONNECT_CHAR);

		String pinCode = "123456";
		if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
			pinCode = holder.edtPin.getText().toString().trim();
		}
		data.append(pinCode).append(Constant.STANDARD_CONNECT_CHAR);
		
		holder.edtPin.setText("");
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							resetForm();
							showDialogSuccess(result.getBillCode());
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}

					}
				}, moveLogInAct).execute();
	}
	
	private void resetForm(){
		holder.edtAmount.setText("");
		holder.tvAmountWord.setText("");
		holder.tvAmountWord.setVisibility(View.GONE);
		holder.edtIsdn.setText("");
		holder.edtPin.setText("");
	}
	
	private void showDialogSuccess(String billCode){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(getString(R.string.bankplus_menu_payment_invoice),
						getString(R.string.bankplus_payment_television_success,
								((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceName(), billCode),
						R.drawable.ic_success, null);
		newFragment.show(ft, "dialog");
	}
	
	private void initMerchant(){
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_GET_MERCHANT).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.MERCHANT.THS).append(
				Constant.STANDARD_CONNECT_CHAR);

		
		new CreateBankPlusAsyncTask(data.toString(), getActivity(), new OnPostExecuteListener<BankPlusOutput>() {
			
			@Override
			public void onPostExecute(BankPlusOutput result, String errorCode,
					String description) {
				// TODO Auto-generated method stub
				if("0".equals(errorCode)){
					Utils.setDataSpinner(getActivity(), result.getLstMerchantBeans(), holder.spnMerchant);
				} else {
					Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
				}
			}
		}, moveLogInAct).execute();
	}
	
	public class ViewHolder{
		Spinner spnMerchant;
		EditText edtAmount;
		EditText edtIsdn;
		EditText edtPin;
		ExpandableHeightGridView gvPrice;
		Button btnPayment;
		TextView tvAmountWord;
		EditText edtContractNo;
		LinearLayout linVerify;
		String oringinalRequestId;
		String tidNumber;
		LinearLayout lnPayInfor;
		String serviceCode;
		LinearLayout pinCodeLayout;
	}
}
