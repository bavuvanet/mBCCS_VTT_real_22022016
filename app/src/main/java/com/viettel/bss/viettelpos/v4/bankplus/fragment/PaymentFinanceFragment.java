package com.viettel.bss.viettelpos.v4.bankplus.fragment;

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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
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
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class PaymentFinanceFragment extends FragmentCommon {

	private ViewHolder holder;
	private MerchantBean merchantBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		idLayout = R.layout.fragment_payment_finance;
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		holder.btnPayment = (Button) v.findViewById(R.id.btnPayment);
		holder.edtAmount = (EditText) v.findViewById(R.id.edtAmount);
		holder.edtContractNo = (EditText) v.findViewById(R.id.edtContractNo);
		holder.edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		holder.edtPin = (EditText) v.findViewById(R.id.edtPin);
		holder.linVerify = (LinearLayout) v.findViewById(R.id.linVerify);
		holder.spnMerchant = (Spinner) v.findViewById(R.id.spnMerchant);
		holder.edtCustName = (EditText) v.findViewById(R.id.edtCustName);
		holder.lnLookupInvoice = (LinearLayout) v.findViewById(R.id.lnLookupInvoice);
		holder.lnPayInfor = (LinearLayout) v.findViewById(R.id.lnPayInfor);
		holder.tvBillCycle = (TextView) v.findViewById(R.id.tvBillCycle);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);

		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
			holder.pinCodeLayout.setVisibility(View.VISIBLE);
		} else {
			holder.pinCodeLayout.setVisibility(View.GONE);
		}

		initMerchant();

		holder.spnMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						holder.lnPayInfor.setVisibility(View.GONE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		holder.btnPayment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialogConfirm();
			}
		});

		holder.linVerify.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				checkDebitInfor();
			}
		});

		holder.edtAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				String moneyNumber = StringUtils
						.getTextDefault(holder.edtAmount);
				if (!CommonActivity.isNullOrEmpty(moneyNumber)
						&& StringUtils.isDigit(moneyNumber)) {
					String textNumber = NumberToTextUtil.toword(Long
							.valueOf(moneyNumber));
					holder.tvAmountWord.setText(textNumber);
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
				String moneyNumber = StringUtils
						.getTextDefault(holder.edtAmount);
				if (StringUtils.isDigit(moneyNumber)) {
					holder.edtAmount.removeTextChangedListener(this);
					holder.edtAmount.setText(StringUtils
							.formatMoney(moneyNumber));
					holder.edtAmount.setSelection(holder.edtAmount.getText()
							.toString().length());
					holder.edtAmount.addTextChangedListener(this);
				}
			}
		});

		holder.edtContractNo.addTextChangedListener(new TextWatcher() {

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
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				holder.lnPayInfor.setVisibility(View.GONE);
				resetForm();
			}
		});
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTitleActionBar(getString(R.string.bankplus_payment_finance));
	}

	private void initMerchant() {
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_GET_MERCHANT).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.MERCHANT.TAICHINH).append(
				Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							Utils.setDataSpinner(getActivity(),
									result.getLstMerchantBeans(),
									holder.spnMerchant);
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	private void checkDebitInfor() {
		if (CommonActivity.isNullOrEmpty(holder.edtContractNo)) {
			Toast.makeText(getActivity(),
					getString(R.string.bankplus_contract_no_required),
					Toast.LENGTH_LONG).show();
			return;
		}

		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_CHECK_DEBIT_INFOR)
				.append(Constant.STANDARD_CONNECT_CHAR);

		merchantBean = (MerchantBean) holder.spnMerchant.getSelectedItem();
		data.append(merchantBean.getServiceCode())
				.append(Constant.STANDARD_CONNECT_CHAR);

		data.append(holder.edtContractNo.getText().toString().trim())
				.append(Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							if(CommonActivity.isNullOrEmpty(result.getAmount())
									|| Integer.valueOf(result.getAmount()).intValue() == 0){
								if(!"1".equals(merchantBean.getTypePayment())){
									Toast.makeText(getActivity(), getString(R.string.bp_not_payment),
											Toast.LENGTH_LONG).show();
									return;
								}
							}
							holder.lnPayInfor.setVisibility(View.VISIBLE);
							holder.edtAmount.setText(CommonActivity
									.isNullOrEmpty(result.getAmount()) ? ""
									: result.getAmount());
							holder.edtCustName.setText(CommonActivity
									.isNullOrEmpty(result.getCustomerName()) ? ""
									: result.getCustomerName());
							holder.tvBillCycle.setText(getString(
									R.string.bankplus_bill_cycle,
									CommonActivity.isNullOrEmpty(result
											.getCycleBill()) ? "" : result
											.getCycleBill()));
							holder.oringinalRequestId = result.getOringinalRequestId();
							holder.tidNumber = result.getTidNumber();
							holder.serviceCode = result.getServiceCode();
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	private boolean validatePayment() {
		if (CommonActivity.isNullOrEmpty(holder.edtIsdn)) {
			Toast.makeText(
					getActivity(),
					getString(R.string.bankplus_err_isdn_receiver_sms_required),
					Toast.LENGTH_LONG).show();
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
			if (CommonActivity.isNullOrEmpty(holder.edtPin)) {
				Toast.makeText(getActivity(),
						getString(R.string.bankplus_err_pin_required),
						Toast.LENGTH_LONG).show();
				holder.edtPin.requestFocus();
				return false;
			}
		}

		return true;
	}

	private void doPayment() {
		if (!validatePayment()) {
			return;
		}

		String pinCode = "123456";
		if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
			pinCode = holder.edtPin.getText().toString();
		}

		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_PAYMENT_FINANCE).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.serviceCode).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", ""))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.tidNumber).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(
				StringUtils.formatMsisdn(holder.edtIsdn.getText().toString()))
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.oringinalRequestId).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtContractNo.getText().toString()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtCustName.getText().toString()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(pinCode).append(
				Constant.STANDARD_CONNECT_CHAR);

		holder.edtPin.setText("");
		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							resetForm();
							showDialogSuccess();
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
						}

					}
				}, moveLogInAct).execute();
	}

	private void showDialogSuccess() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(
						getString(R.string.bankplus_menu_payment_finance),
						getString(R.string.bankplus_payment_finance_success,
								((MerchantBean) holder.spnMerchant
										.getSelectedItem()).getServiceName()),
						R.drawable.ic_success, null);
		newFragment.show(ft, "dialog");
	}

	private void resetForm() {
		holder.edtAmount.setText("");
		holder.edtCustName.setText("");
		holder.tvBillCycle.setText("");
		holder.tvAmountWord.setText("");
		holder.edtIsdn.setText("");
		holder.edtPin.setText("");
		holder.lnPayInfor.setVisibility(View.GONE);
	}

	private void showDialogConfirm() {
		if (!validatePayment()) {
			return;
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		String msg = getString(R.string.bankplus_confirm_payment_finance,
				((MerchantBean) holder.spnMerchant.getSelectedItem())
						.getServiceName(), holder.edtCustName.getText()
						.toString(), holder.edtContractNo.getText().toString(),
				holder.edtAmount.getText().toString(), holder.tvAmountWord
						.getText().toString());

		String title = getString(R.string.bankplus_title_confirm_payment_finance);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0,
						new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								doPayment();
							}
						});
		newFragment.show(ft, "dialog");
	}

	public class ViewHolder {
		Spinner spnMerchant;
		EditText edtContractNo;
		EditText edtAmount;
		EditText edtIsdn;
		EditText edtPin;
		EditText edtCustName;
		LinearLayout lnLookupInvoice;
		LinearLayout lnPayInfor;
		LinearLayout linVerify;
		TextView tvBillCycle;
		TextView tvAmountWord;
		String oringinalRequestId;
		String tidNumber;
		String serviceCode;
		Button btnPayment;
		LinearLayout pinCodeLayout;
	}
}
