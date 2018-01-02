package com.viettel.bss.viettelpos.v4.work.fragment;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommonV4;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterRegisterRollUpHistory;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterRollUpHistory;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncViewHistoryRegisterRollUp;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncViewHistoryRollUp;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;

public class FragmentRollUpHistory extends FragmentCommonV4 {

	private Activity activity;
	private EditText edtFromDate;
	private EditText edtToDate;
	private ListView lvHistory;
	private View btnViewHisRegister, btnViewHisRollUp;

	@Override
	public void onResume() {
		super.onResume();
		txtNameActionBar.setText(activity.getString(R.string.rolL_up_history));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_roll_up_history;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
		edtToDate = (EditText) v.findViewById(R.id.edtToDate);
		lvHistory = (ListView) v.findViewById(R.id.lvHistory);
		btnViewHisRegister = v.findViewById(R.id.btnViewHisRegister);
		btnViewHisRollUp = v.findViewById(R.id.btnViewHisRollUp);
		edtFromDate.setOnClickListener(this);
		edtToDate.setOnClickListener(this);
		btnViewHisRollUp.setOnClickListener(this);
		btnViewHisRegister.setOnClickListener(this);
		Date now = new Date();
		Date last = DateTimeUtils.addDay(now, -30);
		edtFromDate.setText(DateTimeUtils.convertDateTimeToString(last));
		edtToDate.setText(DateTimeUtils.convertDateTimeToString(now));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
	}

	public void setPermission() {
		permission = ";m.roll.up;";
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.edtFromDate:
			CommonActivity.showDatePickerDialog(activity, edtFromDate);
			break;
		case R.id.edtToDate:
			CommonActivity.showDatePickerDialog(activity, edtToDate);
			break;
		case R.id.btnViewHisRegister:
			if (!validateInput()) {
				return;
			}

			String strFromDate = edtFromDate.getText().toString();
			String strToDate = edtToDate.getText().toString();

			AsyncViewHistoryRegisterRollUp asy = new AsyncViewHistoryRegisterRollUp(
					"", strFromDate, strToDate, onPostHisRegister,
					getActivity());
			asy.execute();
			break;
		case R.id.btnViewHisRollUp:
			if (!validateInput()) {
				return;
			}

			AsyncViewHistoryRollUp rollUpHis = new AsyncViewHistoryRollUp("",
					edtFromDate.getText().toString(), edtToDate.getText()
							.toString(), onPostHisRollUp, getActivity());
			rollUpHis.execute();
			break;

		default:
			break;
		}
	}

	OnPostExecute<RollUpOutput> onPostHisRegister = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			AdapterRegisterRollUpHistory adapter = new AdapterRegisterRollUpHistory(
					getActivity(), result.getLstRegisterRollUpBo());
			lvHistory.setAdapter(adapter);
		}
	};
	OnPostExecute<RollUpOutput> onPostHisRollUp = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			AdapterRollUpHistory adapter = new AdapterRollUpHistory(
					getActivity(), result.getLstRollUpBo());
			lvHistory.setAdapter(adapter);
		}
	};

	private boolean validateInput() {
		String strFromDate = edtFromDate.getText().toString();
		String strToDate = edtToDate.getText().toString();
		Date fromDate = DateTimeUtils.convertStringToTime(strFromDate);
		Date toDate = DateTimeUtils.convertStringToTime(strToDate);
		if (fromDate.after(toDate)) {
			CommonActivity.createAlertDialog(activity,
					R.string.from_date_greate_than_to_date, R.string.app_name)
					.show();
			return false;
		}
		if (toDate.after(new Date())) {
			CommonActivity.createAlertDialog(activity,
					R.string.to_date_greate_than_now, R.string.app_name).show();
			return false;
		}
		int duration = DateTimeUtils.calculateDays2Date(fromDate, toDate);

		if (duration > 30) {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.duration_over_load, 30),
					getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}
}
