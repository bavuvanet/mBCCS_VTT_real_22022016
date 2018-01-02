package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.TransHistoryAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.TransBank;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class ViewHisTransFragment extends FragmentCommon {

	private Activity activity;
	private ViewHolder holder;

	private boolean flag_loading = false;
	private List<TransBank> lstTransBanks = new ArrayList<TransBank>();
	private TransHistoryAdapter adapter;
	private int pageIndex = 1;
	private String requestId = "null";
	private boolean loadMoreBP = true;
	private boolean loadMoreSale = true;

	// private List
	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(activity
				.getString(R.string.view_tran_bank_history));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		idLayout = R.layout.layout_report_bankplus_transaction;
	}
	
	private void resetLookupTransBank(){
		pageIndex = 1;
		requestId = "null";
		loadMoreBP = true;
		loadMoreSale = true;
		flag_loading = false;
		holder.lnTransBank.setVisibility(View.GONE);
		lstTransBanks.clear();
	}
	
	/**
	 * Ham khoi tao spinner
	 * 
	 * @param spinner
	 */
	private void initSpinner(Spinner spinner, String parName) {
		try {
			ApParamDAL apDal = new ApParamDAL(getActivity());
			apDal.open();
			List<Spin> lstSpins = apDal.getAppParam(parName, true);
			lstSpins.add(0, initSpinAll());
			Utils.setDataSpinner(getActivity(), lstSpins, spinner);
			apDal.close();
		} catch (Exception ex) {
			Log.d(getClass().getSimpleName(), "Error when initSpinner: " + ex);
		}
	}
	
	private Spin initSpinAll(){
		Spin spin = new Spin();
		spin.setId("null");
		spin.setName(getString(R.string.all));
		return spin;
	}

	@Override
	public void unit(View v) {
		holder = new ViewHolder();
		holder.edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
		holder.edtToDate = (EditText) v.findViewById(R.id.edtToDate);
		holder.edtRequestId = (EditText) v.findViewById(R.id.edtRequestId);
		holder.btnLookupTrans = (ImageButton) v.findViewById(R.id.btnLookupTrans);
		holder.lnTransBank = (LinearLayout) v.findViewById(R.id.lnTransBank);
		holder.lvTransBank = (ListView) v.findViewById(R.id.lvTransBank);
		holder.btnSearch = (Button) v.findViewById(R.id.btnSearch);
		holder.spnTransaction = (Spinner) v.findViewById(R.id.spnTransaction);
		
		Calendar cal = Calendar.getInstance();
		holder.edtFromDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy"));
		holder.edtToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy"));
		
		new DateTimeDialogWrapper(holder.edtFromDate, getActivity());
		new DateTimeDialogWrapper(holder.edtToDate, getActivity());
		
		holder.btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				resetLookupTransBank();
				doLookupTransBank();
			}
		});
		
		holder.edtRequestId.addTextChangedListener(new TextWatcher() {
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
				holder.lnTransBank.setVisibility(View.GONE);
			}
		});

		holder.lvTransBank.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0)
	            {
					if(flag_loading == false){
						flag_loading = true;
						doLookupTransBank();
					}
	            }
			}
		});
		initSpinner(holder.spnTransaction, "PROCESS_CODE");
	}
	
	private void doLookupTransBank() {
		
		if(!validateViewHistory()){
			return;
		}
		
		StringBuilder data = new StringBuilder();
		data.append(Session.token).append(Constant.STANDARD_CONNECT_CHAR);
		data.append(BPConstant.COMMAND_LOOKUP_TRANS_HISTORY).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtFromDate.getText().toString()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(holder.edtToDate.getText().toString()).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(requestId).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(pageIndex++).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(loadMoreBP).append(
				Constant.STANDARD_CONNECT_CHAR);
		data.append(loadMoreSale).append(
				Constant.STANDARD_CONNECT_CHAR);
		Spin spin = (Spin) holder.spnTransaction.getSelectedItem();
		data.append(spin.getId()).append(
				Constant.STANDARD_CONNECT_CHAR);
		

		new CreateBankPlusAsyncTask(data.toString(), getActivity(),
				new OnPostExecuteListener<BankPlusOutput>() {

					@Override
					public void onPostExecute(BankPlusOutput result,
							String errorCode, String description) {
						// TODO Auto-generated method stub
						if("0".equals(errorCode)){
							loadMoreBP = result.isLoadMoreBP();
							loadMoreSale = result.isLoadMoreSale();
							requestId = result.getRequestId();
							
							if(loadMoreBP || loadMoreSale){
								flag_loading = false;
							} else {
								flag_loading = true;
							}
							
							if(!CommonActivity.isNullOrEmptyArray(result.getLstTransBanks())){
								if(!CommonActivity.isNullOrEmptyArray(lstTransBanks)){
									lstTransBanks.addAll(result.getLstTransBanks());
									adapter.notifyDataSetChanged();
								} else {
									lstTransBanks = result.getLstTransBanks();
									adapter = new TransHistoryAdapter(getContext(), getActivity(), lstTransBanks);
									holder.lvTransBank.setAdapter(adapter);
								}
								holder.lnTransBank.setVisibility(View.VISIBLE);
							} else {
								Toast.makeText(getActivity(), getString(R.string.no_data), Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
						}

					}
				}, moveLogInAct).execute();
	}
	
	private boolean validateViewHistory() {
		if (CommonActivity.isNullOrEmpty(holder.edtFromDate.getText()
				.toString())) {
			Toast.makeText(getActivity(), getString(R.string.notstartdate), Toast.LENGTH_LONG).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(holder.edtToDate.getText()
				.toString())) {
			Toast.makeText(getActivity(), getString(R.string.notendate), Toast.LENGTH_LONG).show();
			return false;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date from = sdf.parse(holder.edtFromDate.getText().toString());
			Date to = sdf.parse(holder.edtToDate.getText().toString());
			if (from.after(to)) {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.checktimeupdatejob),
						getString(R.string.app_name)).show();
				return false;
			}

			long diff = to.getTime() - from.getTime();
			long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if (dayDiff > 30) {
				Toast.makeText(getActivity(), getString(R.string.modify_profile_search_err01), Toast.LENGTH_LONG).show();
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void setPermission() {
		permission = ";m.pay.telecom;";

	}

	OnClickListener onPaymentClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

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

		String title = getString(R.string.confirm_buy_pincode);
		DialogFragment newFragment = DialogConfirmCreateBankplusTrans
				.newInstance(title, msg.toString(), 0, onPaymentClick);
		newFragment.show(ft, "dialog");
	}
	
	class ViewHolder {
		EditText edtFromDate;
		EditText edtToDate;
		EditText edtRequestId;
		ImageButton btnLookupTrans;
		
		LinearLayout lnTransBank;
		ListView lvTransBank;
		Spinner spnTransaction;
		Button btnSearch;
	}
}
