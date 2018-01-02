
package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.MerchantBean;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.NumberToTextUtil;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.bankplus.view.ExpandableHeightGridView;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class BuyGameCardFragment extends FragmentCommon {

	private ViewHolder holder;
	private ArrayList<FormBean> lstPrice = new ArrayList<FormBean>();
	private ListPriceAdapter adapter;
	private MerchantBean merchantBeanSelected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		idLayout = R.layout.fragment_buy_card_game;
	}
	
	@Override
	public void unit(View v) {

		holder = new ViewHolder();
		holder.btnPayment = (Button) v.findViewById(R.id.btnPayment);
		holder.edtAmount = (EditText) v.findViewById(R.id.edtAmount);
		holder.edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		holder.edtPin = (EditText) v.findViewById(R.id.edtPin);
		holder.edtContractCode = (EditText) v.findViewById(R.id.edtContractCode);
		holder.spnMerchant = (Spinner) v.findViewById(R.id.spnMerchant);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.gvPrice = (ExpandableHeightGridView) v.findViewById(R.id.gvPrice);
		holder.linVerify = (LinearLayout) v.findViewById(R.id.linVerify);
		holder.gvPrice.setOnItemClickListener(onPriceClick);
		holder.lnPayInfo = (LinearLayout) v.findViewById(R.id.lnPayInfo);
		holder.lnMoneyInfo = (LinearLayout) v.findViewById(R.id.lnMoneyInfo);
		holder.lnContractCode = (LinearLayout) v.findViewById(R.id.lnContractCode);
		holder.pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);

		holder.lnMoneyInfo.setVisibility(View.GONE);
		holder.lnContractCode.setVisibility(View.GONE);
		holder.lnPayInfo.setVisibility(View.GONE);
		holder.tvAmountWord.setVisibility(View.GONE);
		holder.linVerify.setVisibility(View.GONE);

		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
//		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){ //mac dinh ko check pin
//			holder.pinCodeLayout.setVisibility(View.VISIBLE);
//		} else {
			holder.pinCodeLayout.setVisibility(View.GONE);
//		}

		initMerchant();
		
		holder.btnPayment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialogConfirm();
			}
		});
		
		holder.spnMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				lstPrice.clear();
				holder.edtContractCode.setText("");
				merchantBeanSelected = (MerchantBean)holder.spnMerchant.getSelectedItem();

				holder.linVerify.setVisibility(View.GONE);
				if (merchantBeanSelected.isPayAccount()) {
					holder.lnContractCode.setVisibility(View.VISIBLE);
					holder.lnMoneyInfo.setVisibility(View.VISIBLE);
					holder.lnPayInfo.setVisibility(View.GONE);
				} else {
					holder.lnContractCode.setVisibility(View.GONE);
					holder.lnMoneyInfo.setVisibility(View.VISIBLE);
					holder.lnPayInfo.setVisibility(View.GONE);
				}
				
				if(!CommonActivity.isNullOrEmpty(merchantBeanSelected.getAmount())){
					String[] lstPrices = merchantBeanSelected.getAmount().split(",");
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

		holder.linVerify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				checkDebitInfor();
			}
		});
	}
	
	private void checkDebitInfor() {

		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(),
					getString(R.string.bankplus_err_trans_amount_card_game_required),
					Toast.LENGTH_LONG).show();
			return;
		}

		String billCode = "";
		if (merchantBeanSelected.isPayAccount()) {
			if(CommonActivity.isNullOrEmpty(holder.edtContractCode)){
				Toast.makeText(getActivity(),
						getString(R.string.bankplus_err_contract_code_required),
						Toast.LENGTH_LONG).show();
				return;
			}
			billCode = holder.edtContractCode.getText().toString();
		} else {
			billCode = holder.edtAmount.getText().toString().replaceAll("\\.", "");
		}
		
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_CHECK_DEBIT_INFOR)
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceCode())
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(billCode)
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", ""))
				.append(Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							holder.oringinalRequestId = result.getOringinalRequestId();
							holder.tidNumber = result.getTidNumber();
							holder.serviceCode = result.getServiceCode();
							holder.lnPayInfo.setVisibility(View.VISIBLE);
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
		setTitleActionBar(getString(R.string.bankplus_menu_buy_game_card));
	}
	
	OnItemClickListener onPriceClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			for (FormBean item : lstPrice) {
				item.setChecked(false);
			}
			holder.lnPayInfo.setVisibility(View.GONE);
			lstPrice.get(position).setChecked(true);
			holder.tvAmountWord.setText(NumberToTextUtil.toword(Long.valueOf(lstPrice.get(position).getCode())));
			holder.tvAmountWord.setVisibility(View.VISIBLE);
			holder.edtAmount.setText(StringUtils.formatMoney(lstPrice.get(position)
					.getCode()));
			holder.linVerify.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();
		}
	};
	
	private boolean validateBuyCardGame(){
		if(CommonActivity.isNullOrEmpty(holder.edtAmount)){
			Toast.makeText(getActivity(), getString(R.string.bankplus_err_trans_amount_card_game_required), Toast.LENGTH_LONG).show();
			holder.edtIsdn.requestFocus();
			return false;
		}

		if (!CommonActivity.validateIsdn(holder.edtIsdn.getText().toString())) {
			holder.edtIsdn.requestFocus();
			CommonActivity.createAlertDialog(getActivity(), R.string.isdn_not_valid,
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
		if(!validateBuyCardGame()){
			return;
		}
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		String contCode = "";
		if (holder.edtContractCode.getText().toString().length() > 0) {
			contCode = holder.edtContractCode.getText().toString();
			contCode = "<li>" + getString(R.string.contract_code_2)
					+ " <strong>" + contCode +"</strong></li>";
		} else {
			contCode = "";
		}

		String msg = getString(R.string.bankplus_confirm_buy_card_game,
				contCode,
				((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceName(), 
				holder.edtAmount.getText().toString(), 
				holder.tvAmountWord.getText().toString());
		
		String title = getString(R.string.bankplus_title_confirm_buy_card_game);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0, new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						doBuyCardGame();
					}
				});
		newFragment.show(ft, "dialog");
	}
	
	private void doBuyCardGame() {
		String billCode = "";
		if (merchantBeanSelected.isPayAccount()) {
			billCode = holder.edtContractCode.getText().toString();
		} else {
			billCode = holder.edtAmount.getText().toString().replaceAll("\\.", "");
		}

		String pinCode = "123456";
		if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
			pinCode = holder.edtPin.getText().toString();
		}

		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_BUY_CARD_GAME).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.serviceCode).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(billCode).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", "")).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(holder.edtIsdn.getText().toString())).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.tidNumber).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.oringinalRequestId).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(pinCode).append(
				Constant.STANDARD_CONNECT_CHAR);
		
		holder.edtPin.setText("");
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							showDialogSuccess();
							resetForm();
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}

					}
				}, moveLogInAct).execute();
	}
	
	private void resetForm(){
		holder.tvAmountWord.setVisibility(View.GONE);
		holder.edtAmount.setText("");
		holder.tvAmountWord.setText("");
		holder.edtIsdn.setText("");
		holder.edtPin.setText("");
	}
	
	private void showDialogSuccess(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		String contCode = "";
		if (holder.edtContractCode.getText().toString().length() > 0) {
			contCode = holder.edtContractCode.getText().toString();
			contCode = "<li>" + getString(R.string.contract_code_2) + " <strong>" + contCode +"</strong></li>";
		} else {
			contCode = "";
		}
		
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans.newInstance(
				getString(R.string.bankplus_payment_buy_card_game),
				getString(R.string.bankplus_payment_buy_card_game_success,
						((MerchantBean)holder.spnMerchant.getSelectedItem()).getServiceName(),
						contCode,
						holder.edtAmount.getText().toString()),
				R.drawable.ic_success,
				null);
		newFragment.show(ft, "dialog");
	}
	
	private void initMerchant(){
		StringBuilder data = new StringBuilder();
		data.append(Session.token)
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_GET_MERCHANT)
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.MERCHANT.GAME)
				.append(Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(), new OnPostExecuteListener<BankPlusOutput>() {
			
			@Override
			public void onPostExecute(BankPlusOutput result, String errorCode,
					String description) {
				if("0".equals(errorCode)){
					Collections.sort(result.getLstMerchantBeans(), new Comparator<MerchantBean>() {
						@Override
						public int compare(MerchantBean o1, MerchantBean o2) {
							return o1.getServiceCode().compareTo(o2.getServiceCode());
						}
					});
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
		LinearLayout linVerify;
		String oringinalRequestId;
		String tidNumber;
		String serviceCode;

		EditText edtContractCode;
		LinearLayout lnMoneyInfo;
		LinearLayout lnContractCode;
		LinearLayout lnPayInfo;
		LinearLayout pinCodeLayout;
	}
}
