package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractAdapter;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncSearchVerify;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

public class FragmentContractVerifyUpdate extends FragmentCommon {
	private int MAX_RECORD_PER_PAGE = 50;
	private int MAX_DURATION = 60;
	private Activity activity;

	private EditText edittext_acc;
	private ListView lvListCustomer;
	private Button btnSearchAcc;
	private ContractAdapter adapter = null;
	private ArrayList<ChargeContractItem> lstData = new ArrayList<ChargeContractItem>();
	private ChargeContractItem chargContractItem;
	private Spinner spnType;
	private TextView tvFromDateAssign;
	private TextView tvToDateAssign;
	private TextView tvFromDateConnect;
	private TextView tvToDateConnect;
	private boolean loadmore = true;

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(R.string.contract_verify_update);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_contract_verify_update;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public void unit(View v) {
		edittext_acc = (EditText) v.findViewById(R.id.edtIsdn);
		btnSearchAcc = (Button) v.findViewById(R.id.btnSearchContract);
		lvListCustomer = (ListView) v.findViewById(R.id.lv_info_acc);
		spnType = (Spinner) v.findViewById(R.id.spnType);
		tvFromDateAssign = (TextView) v.findViewById(R.id.tvFromDateAssign);
		tvToDateAssign = (TextView) v.findViewById(R.id.tvToDateAssign);
		tvFromDateConnect = (TextView) v.findViewById(R.id.tvFromDateConnect);
		tvToDateConnect = (TextView) v.findViewById(R.id.tvToDateConnect);

		tvFromDateAssign.setOnClickListener(this);
		tvToDateAssign.setOnClickListener(this);
		tvFromDateConnect.setOnClickListener(this);
		tvToDateConnect.setOnClickListener(this);
		btnSearchAcc.setOnClickListener(this);
		lvListCustomer.setOnItemClickListener(this);
		Date now = new Date();
		Date last30 = DateTimeUtils.addDays(now, -30);
		tvFromDateAssign.setText(DateTimeUtils.convertDateTimeToString(last30));
		// tvFromDateConnect.setText(DateTimeUtils.getFirstDateOfMonth());

		String nowDate = DateTimeUtils.convertDateTimeToString(now,
				"dd/MM/yyyy");
		tvToDateAssign.setText(nowDate);
		// tvToDateConnect.setText(nowDate);
		// tvFromDateConnect.setHint(R.string.chon_ngay);
		// tvToDateConnect.setHint(R.string.chon_ngay);
		adapter = new ContractAdapter(lstData, activity);
		lvListCustomer.setAdapter(adapter);
		initVerifyType();
		searchContract(true);
		lvListCustomer.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastitemScreen = firstVisibleItem + visibleItemCount;
				if ((lastitemScreen == totalItemCount) && (!loadmore)
						&& totalItemCount > 0) {
					loadmore = true;
					searchContract(false);
				}
			}
		});
		// test();
	}

	private void test() {
		edittext_acc.setText("988236303");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
		chargContractItem = lstData.get(arg2);

		FragmentContractVerifyUpdateDetail fragment = new FragmentContractVerifyUpdateDetail();
		Bundle bundle = new Bundle();
		bundle.putSerializable("chargContractItem", chargContractItem);
		fragment.setArguments(bundle);

		ReplaceFragment.replaceFragment(activity, fragment, true);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btnSearchContract:
			if (validate()) {
				searchContract(true);
			}
			break;
		case R.id.tvFromDateAssign:
			CommonActivity.showDatePickerDialog(activity, tvFromDateAssign);
			break;
		case R.id.tvToDateAssign:
			CommonActivity.showDatePickerDialog(activity, tvToDateAssign);
			break;
		case R.id.tvFromDateConnect:
			CommonActivity.showDatePickerDialog(activity, tvFromDateConnect);
			break;
		case R.id.tvToDateConnect:
			CommonActivity.showDatePickerDialog(activity, tvToDateConnect);
			break;
		default:
			break;
		}
	}

	private void searchContract(boolean isClear) {
		if (isClear) {
			lstData.clear();
			adapter.notifyDataSetChanged();
		}

		CommonActivity.hideKeyboard(btnSearchAcc, activity);
		String fromVerifyDate = tvFromDateAssign.getText().toString();
		String toVerifyDate = tvToDateAssign.getText().toString();
		String fromDateConnect = tvFromDateConnect.getText().toString();
		String toDateConnect = tvToDateConnect.getText().toString();
		Spin type = (Spin) spnType.getSelectedItem();
		String isdn = edittext_acc.getText().toString();
		AsyncSearchVerify searchVerify = new AsyncSearchVerify(activity,
				fromVerifyDate, toVerifyDate, fromDateConnect, toDateConnect,
				type.getId(), isdn, lstData.size() / MAX_RECORD_PER_PAGE,
				MAX_RECORD_PER_PAGE, onPost);
		searchVerify.execute();
	}

	private boolean validate() {

		String fromVerifyDate = tvFromDateAssign.getText().toString();
		String toVerifyDate = tvToDateAssign.getText().toString();
		String fromDateConnect = tvFromDateConnect.getText().toString();
		String toDateConnect = tvToDateConnect.getText().toString();
		Date datefromVerifyDate = null;
		Date datetoVerifyDate = null;
		Date datefromDateConnect = null;
		Date datetoDateConnect = null;
		datefromVerifyDate = DateTimeUtils.convertStringToTime(fromVerifyDate);
		datetoVerifyDate = DateTimeUtils.convertStringToTime(toVerifyDate);
		Date dateNow = DateTimeUtils.getCurrDate();
		datefromDateConnect = DateTimeUtils
				.convertStringToTime(fromDateConnect);
		datetoDateConnect = DateTimeUtils.convertStringToTime(toDateConnect);

		if (datefromVerifyDate.after(datetoVerifyDate)) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checktimeupdatejob),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (datetoVerifyDate.after(dateNow)) {

			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.denngaynhohonhtai),
					getActivity().getString(R.string.app_name)).show();

			return false;
		}

		if (DateTimeUtils.calculateDays2Date(datefromVerifyDate,
				datetoVerifyDate) > MAX_DURATION) {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.assign_verify_date_over, MAX_DURATION),
					getString(R.string.app_name)).show();
			return false;
		}
		if (!CommonActivity.isNullOrEmpty(datefromDateConnect)) {
			if (CommonActivity.isNullOrEmpty(datetoDateConnect)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validatetodate),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (datefromDateConnect.after(datetoDateConnect)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.checktimeupdatejob),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (datetoDateConnect.after(dateNow)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.denngaynhohonhtai),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
		}
		if (!CommonActivity.isNullOrEmpty(datetoDateConnect)) {
			if (CommonActivity.isNullOrEmpty(datefromDateConnect)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validatefromdate),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (datefromDateConnect.after(datetoDateConnect)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.checktimeupdatejob),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (datetoDateConnect.after(dateNow)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.denngaynhohonhtai),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
		}
		return true;

	}

	private void initVerifyType() {
		List<Spin> lstType = new ArrayList<Spin>();
		Spin first = new Spin();
		first.setValue(getString(R.string.choose_verify));
		lstType.add(first);
		Spin typeNew = new Spin();
		typeNew.setId("1");
		typeNew.setValue(getString(R.string.contract_verify_type_new));
		lstType.add(typeNew);
		Spin typeOld = new Spin();
		typeOld.setValue(getString(R.string.contract_verify_type_old));
		typeOld.setId("0");
		lstType.add(typeOld);
		ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(getActivity(),
				R.layout.spinner_item, lstType);
		spnType.setAdapter(adapter);
	}

	private OnPostSuccessExecute<PaymentOutput> onPost = new OnPostSuccessExecute<PaymentOutput>() {

		@Override
		public void onPostSuccess(PaymentOutput result) {
			if (CommonActivity.isNullOrEmptyArray(result.getLstVerifyBean())
					|| result.getLstVerifyBean().size() < MAX_RECORD_PER_PAGE) {
				loadmore = true;
			} else {
				loadmore = false;

			}
			if (!CommonActivity.isNullOrEmpty(result.getLstVerifyBean())) {
				lstData.addAll(result.getLstVerifyBean());
			}
			if (CommonActivity.isNullOrEmpty(lstData)) {
				String des = result.getDescription();
				if (CommonActivity.isNullOrEmpty(des)) {
					des = getActivity().getString(R.string.no_data);
				}
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						des, getActivity().getString(R.string.app_name));
				dialog.show();
			}

			adapter.notifyDataSetChanged();
		}
	};

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";verify_update;";
	}
}
