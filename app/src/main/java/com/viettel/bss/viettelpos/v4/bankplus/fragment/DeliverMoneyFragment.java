package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.viettel.bss.viettelpos.v4.bankplus.object.BankBean;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.NumberToTextUtil;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class DeliverMoneyFragment extends FragmentCommon {
	private static final String BCCS_BANKPLUS = "BCCSBP";
	private ViewHolder holder;

	private class ViewHolder {
		EditText edtCodeReciver;
		EditText edtAmount;
		EditText edtIsdnReceiver;
		LinearLayout linVerify;
		TextView tvAmountWord;

		TextView tvReciverName;
		EditText edtIdNo;
		Spinner spnAccountBank;
		EditText edtPin;
		Button btnDeliverMoney;

		LinearLayout deliverMoneyLayout;
		LinearLayout pinCodeLayout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		idLayout = R.layout.fragment_deliver_money;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTitleActionBar(getString(R.string.bankplus_deliver_money_menu));
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();

		holder.edtCodeReciver = (EditText) v.findViewById(R.id.edtCodeReciver);
		holder.edtIsdnReceiver = (EditText) v.findViewById(R.id.edtIsdnReceiver);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.edtAmount = (EditText) v.findViewById(R.id.edtAmount);
		holder.linVerify = (LinearLayout) v.findViewById(R.id.linVerify);
		holder.tvAmountWord.setVisibility(View.GONE);

		holder.tvReciverName = (TextView) v.findViewById(R.id.tvReciverName);
		holder.edtIdNo = (EditText) v.findViewById(R.id.edtIdNo);
		holder.spnAccountBank = (Spinner) v.findViewById(R.id.spnAccountBank);
		holder.tvAmountWord = (TextView) v.findViewById(R.id.tvAmountWord);
		holder.btnDeliverMoney = (Button) v.findViewById(R.id.btnDeliverMoney);

		holder.deliverMoneyLayout = (LinearLayout) v.findViewById(R.id.deliverMoneyLayout);
		holder.deliverMoneyLayout.setVisibility(View.GONE);

		holder.pinCodeLayout = (LinearLayout) v.findViewById(R.id.pinCodeLayout);
		Staff staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
		if(CommonActivity.validateInputPin(staff.getChannelTypeId(), staff.getType())){
			holder.pinCodeLayout.setVisibility(View.VISIBLE);
		} else {
			holder.pinCodeLayout.setVisibility(View.GONE);
		}

		holder.linVerify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateDeliverCode()) {
					doQueryDeliver();
				}
			}
		});

		holder.edtAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
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
				String moneyNumber = StringUtils
						.getTextDefault(holder.edtAmount);
				holder.edtAmount.setText(StringUtils.formatMoney(moneyNumber));
				holder.edtAmount.setSelection(holder.edtAmount.getText()
						.toString().length());
				holder.edtAmount.addTextChangedListener(this);
			}
		});

		holder.btnDeliverMoney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialogConfirm();
			}
		});
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub

	}

	private void showDialogConfirm() {
		if (!validateDeliverMoney()) {
			return;
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		String msg = getString(R.string.bankplus_confirm_deliver_money,
				holder.edtIsdnReceiver.getText().toString(), holder.edtAmount
						.getText().toString(), holder.tvAmountWord.getText()
						.toString(), holder.edtIdNo.getText().toString());

		String title = getString(R.string.bankplus_title_confirm_deliver_money);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0, onDeliverMoneyClick);
		newFragment.show(ft, "dialog");
	}

	private OnClickListener onDeliverMoneyClick = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			doDeliverMoney();
		}
	};

	private void initListBank() {
		String token = Session.getToken();

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_BANK_KPP).append(
				Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							BankBean bankBean = new BankBean();
							bankBean.setBankCode(BCCS_BANKPLUS);
							bankBean.setBankName(getString(R.string.bankplus_bccs_bankplus));

							result.getLstBanks().add(0, bankBean);
							Utils.setDataSpinner(getActivity(),
									result.getLstBanks(), holder.spnAccountBank);
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	private void initListBankStaff() {
		BankBean bankBean = new BankBean();
		bankBean.setBankCode(BCCS_BANKPLUS);
		bankBean.setBankName(getString(R.string.bankplus_bccs_bankplus));

		List<BankBean> lstBankBeans = new ArrayList<BankBean>();
		lstBankBeans.add(bankBean);

		Utils.setDataSpinner(getActivity(), lstBankBeans, holder.spnAccountBank);
	}

	private boolean validateDeliverCode() {
		if (CommonActivity.isNullOrEmpty(holder.edtCodeReciver)) {
			Toast.makeText(getActivity(),
					"Mã nhận tiền không được để trống!",
					Toast.LENGTH_LONG).show();
			holder.edtCodeReciver.requestFocus();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(holder.edtIsdnReceiver)) {
			Toast.makeText(getActivity(),
					getString(R.string.bankplus_err_isdn_receiver_required),
					Toast.LENGTH_LONG).show();
			holder.edtIsdnReceiver.requestFocus();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(holder.edtAmount)) {
			Toast.makeText(
					getActivity(),
					getString(R.string.bankplus_err_trans_amount_receiver_required),
					Toast.LENGTH_LONG).show();
			holder.edtAmount.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validateDeliverMoney() {

		holder.btnDeliverMoney.requestFocus();

		if (CommonActivity.isNullOrEmpty(holder.edtIdNo)) {
			Toast.makeText(getActivity(),
					getString(R.string.bankplus_err_id_no_receiver_required),
					Toast.LENGTH_LONG).show();
			holder.edtIdNo.requestFocus();
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

	private void doDeliverMoney() {
		String token = Session.getToken();
        String pinCode = "123456";
        if (holder.pinCodeLayout.getVisibility() == View.VISIBLE) {
            pinCode = holder.edtPin.getText().toString();
        }

		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_DELIVER).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.stripAccents(
				holder.tvReciverName.getText().toString())).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString()
				.replaceAll("\\.", "")).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(
				holder.edtIsdnReceiver.getText().toString().trim())).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtIdNo.getText().toString().trim()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.spnAccountBank.getSelectedItemPosition() == 0
				? "400001" : "400002").append(Constant.STANDARD_CONNECT_CHAR);
		data.append(((BankBean) holder.spnAccountBank.getSelectedItem())
				.getBankCode()).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtCodeReciver.getText().toString().trim()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(pinCode).append(
				Constant.STANDARD_CONNECT_CHAR);

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

	private void doQueryDeliver() {

		String token = Session.getToken();
		StringBuilder data = new StringBuilder();

		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_QUERY_DELIVER_TRANS)
				.append(Constant.STANDARD_CONNECT_CHAR);

		data.append(holder.edtCodeReciver.getText().toString().trim()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(StringUtils.formatMsisdn(
				holder.edtIsdnReceiver.getText().toString().trim())).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtAmount.getText().toString().replaceAll("\\.", ""))
				.append(Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							if (result != null) {
								holder.tvReciverName.setText(result.getCustomerName());
								holder.deliverMoneyLayout.setVisibility(View.VISIBLE);
								if (!isStaff()) {
									initListBank();
								} else {
									initListBankStaff();
								}
							}
							Toast.makeText(getActivity(), getString(R.string.check_info_success),
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getActivity(), description,
									Toast.LENGTH_LONG).show();
						}
					}
				}, moveLogInAct).execute();
	}

	private void resetForm() {
		holder.edtCodeReciver.setText("");
		holder.edtIsdnReceiver.setText("");
		holder.edtAmount.setText("");
		holder.tvAmountWord.setText("");
		holder.tvAmountWord.setVisibility(View.GONE);

		holder.tvReciverName.setText("");
		holder.edtIdNo.setText("");
		holder.tvAmountWord.setText("");
		holder.deliverMoneyLayout.setVisibility(View.GONE);
	}

	private void showDialogSuccess() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(getString(R.string.bankplus_deliver_money_menu),
						getString(R.string.bankplus_deliver_money_success),
						R.drawable.ic_success, null);
		newFragment.show(ft, "dialog");
	}

	private boolean isStaff() {
		DBOpenHelper dbOpenHelper = null;
		SQLiteDatabase database = null;
		try {
			dbOpenHelper = new DBOpenHelper(getActivity());
			database = dbOpenHelper.getReadableDatabase();

			StaffDAL staffDAL = new StaffDAL(database);
			Staff staff = staffDAL.getStaffByStaffCode(Session.userName);
			if (staff != null) {
				if (staff.getChannelTypeId() != null
						&& staff.getChannelTypeId().equals(14L)) {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			return false;
		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}

			if (database != null) {
				database.close();
			}
		}
	}
}
