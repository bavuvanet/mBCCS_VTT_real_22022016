package com.viettel.bss.viettelpos.v4.work.fragment;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncRegisterRollUp;
import com.viettel.bss.viettelpos.v4.work.object.RegisterRollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;
import com.viettel.bss.viettelpos.v4.work.object.SaleProgramDTO;

public class FragmentDialogRegisterRollUp extends DialogFragment implements
		OnClickListener {
	/**
	 * 
	 */
	private RegisterRollUpBO bo;
	private SaleProgramDTO program;
	private EditText edtFromDate, edtToDate, edtAddress, edtNote;
	private OnPostExecute<RollUpOutput> onPostExecute;
	private View btnRegisterRollUp, view;
	private AreaObj areaDistrist;
	private AreaObj areaPrecint;
	private AreaObj areaGroup;
	private String areaFlow;
	private String areaHomeNumber;
	private AreaObj areaProvicial;

	public FragmentDialogRegisterRollUp(RegisterRollUpBO bo,
			SaleProgramDTO program, OnPostExecute<RollUpOutput> onPostExecute) {
		this.program = program;
		this.onPostExecute = onPostExecute;
		this.bo = bo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.dialog_register_roll_up,
					container, false);
		}
		edtFromDate = (EditText) view.findViewById(R.id.edtFromDate);
		edtToDate = (EditText) view.findViewById(R.id.edtToDate);
		edtFromDate.setOnClickListener(this);
		edtToDate.setOnClickListener(this);
		edtNote = (EditText) view.findViewById(R.id.edtNote);
		edtAddress = (EditText) view.findViewById(R.id.edtAddress);
		edtAddress.setOnClickListener(this);
		getDialog().setTitle(R.string.register_roll_up);

		Date fromDate = DateTimeUtils.convertDateFromSoap(program
				.getEffectDatetime());
		if (fromDate != null) {
			fromDate = DateTimeUtils.truncDate(fromDate);
			Date now = new Date();
			if (now.before(fromDate)) {
				edtFromDate.setText(DateTimeUtils
						.convertDateTimeToString(fromDate));
				edtToDate.setText(DateTimeUtils
						.convertDateTimeToString(fromDate));
			} else {
				Date next = DateTimeUtils.addDays(new Date(), 1);
				edtFromDate
						.setText(DateTimeUtils.convertDateTimeToString(next));
				edtToDate.setText(DateTimeUtils.convertDateTimeToString(next));
			}
		} else {
			Date next = DateTimeUtils.addDays(new Date(), 1);
			edtFromDate.setText(DateTimeUtils.convertDateTimeToString(next));
			edtToDate.setText(DateTimeUtils.convertDateTimeToString(next));
		}
		btnRegisterRollUp = view.findViewById(R.id.btnRegisterRollUp);

		btnRegisterRollUp.setOnClickListener(this);
		return view;
	}

	private OnClickListener confirm = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			AsyncRegisterRollUp asy = new AsyncRegisterRollUp(bo,
					onPostExecute, getActivity());
			asy.execute();
		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnRegisterRollUp:
			if (!validate()) {
				return;
			}
			bo.setAddress(edtAddress.getText().toString());
			bo.setNote(edtNote.getText().toString());
			bo.setRegisterFromDate(edtFromDate.getText().toString());
			bo.setRegisterToDate(edtToDate.getText().toString());
			String duration = edtFromDate.getText().toString() + " - "
					+ edtToDate.getText().toString();
			CommonActivity.createDialog(getActivity(),
					getString(R.string.confirm_register_roll_up, duration),
					getString(R.string.app_name), getString(R.string.cancel),
					getString(R.string.ok), null, confirm).show();
			break;
		case R.id.edtFromDate:
			CommonActivity.showDatePickerDialog(getActivity(), edtFromDate);
			break;
		case R.id.edtToDate:
			CommonActivity.showDatePickerDialog(getActivity(), edtToDate);
			break;
		case R.id.edtAddress:
			String strProvince = Session.province;
			String strDistris = Session.district;

			if (areaDistrist != null
					&& !CommonActivity
							.isNullOrEmpty(areaDistrist.getDistrict())) {

				strDistris = areaDistrist.getDistrict();
			}
			if (areaProvicial != null
					&& !CommonActivity.isNullOrEmpty(areaProvicial
							.getProvince())) {
				strProvince = areaProvicial.getProvince();
			}

			Bundle mBundle = new Bundle();
			mBundle.putString("strProvince", strProvince);
			mBundle.putString("strDistris", strDistris);
			mBundle.putString("areaFlow", areaFlow);
			mBundle.putString("areaHomeNumber", areaHomeNumber);
			mBundle.putSerializable("areaPrecint", areaPrecint);
			mBundle.putSerializable("areaGroup", areaGroup);
			mBundle.putString("NotChangeLevel", "province");
			Intent i = new Intent(getActivity(), CreateAddressCommon.class);
			i.putExtras(mBundle);
			startActivityForResult(i, 100);
			break;
		default:
			break;
		}

	}

	private boolean validate() {
		Date fromDate = DateTimeUtils.truncDate(DateTimeUtils
				.convertStringToTime(edtFromDate.getText().toString()));
		Date toDate = DateTimeUtils.convertStringToTime(edtToDate.getText()
				.toString());
		Date now = DateTimeUtils.addDay(DateTimeUtils.truncDate(new Date()), 1);
		// Date fromDate = DateUtil.truncDate(bo.getRegisterFromDate());
		if (fromDate.before(now)) {
			CommonActivity.createAlertDialog(
					getActivity(),
					getString(R.string.fromDateBeforeNow,
							DateTimeUtils.convertDateTimeToString(now)),
					getString(R.string.app_name)).show();
			return false;
		}
		if (toDate.before(fromDate)) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.checktimeupdatejob, R.string.app_name)
					.show();
			return false;
		}
		if (CommonActivity.isNullOrEmptyArray(areaProvicial, areaDistrist,
				areaPrecint, areaGroup)) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.roll_up_address_missing, R.string.app_name).show();
			return false;
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 100:
			areaProvicial = (AreaObj) data.getExtras().getSerializable(
					"areaProvicial");
			areaDistrist = (AreaObj) data.getExtras().getSerializable(
					"areaDistrist");
			areaPrecint = (AreaObj) data.getExtras().getSerializable(
					"areaPrecint");
			areaGroup = (AreaObj) data.getExtras().getSerializable("areaGroup");

			areaFlow = data.getExtras().getString("areaFlow");
			areaHomeNumber = data.getExtras().getString("areaHomeNumber");
			StringBuilder address = new StringBuilder();
			if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
				address.append(areaHomeNumber + " ");
			}
			if (areaFlow != null && areaFlow.length() > 0) {
				address.append(areaFlow + " ");
			}
			if (areaGroup != null && areaGroup.getName() != null
					&& areaGroup.getName().length() > 0) {
				address.append(areaGroup.getName() + " ");
			}
			if (areaPrecint != null && areaPrecint.getName() != null
					&& areaPrecint.getName().length() > 0) {

				address.append(areaPrecint.getName() + " ");
			}
			if (areaDistrist != null && areaDistrist.getName() != null
					&& areaDistrist.getName().length() > 0) {

				address.append(areaDistrist.getName() + " ");
			}
			if (areaProvicial != null && areaProvicial.getName() != null
					&& areaProvicial.getName().length() > 0) {

				address.append(areaProvicial.getName());
			}
			edtAddress.setText(address);
			break;

		default:
			break;
		}
	}

	/**
		 * 
		 */

	// @Override
	// public void doSomething(AreaObj province, AreaObj district,
	// AreaObj precinct, AreaObj streetBlock, String street, String home) {
	// edtAddress.setText(province.getName());
	// }
}
