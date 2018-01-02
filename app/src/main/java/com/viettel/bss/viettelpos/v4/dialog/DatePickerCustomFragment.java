package com.viettel.bss.viettelpos.v4.dialog;

import java.util.ArrayList;
import java.util.Calendar;

import com.viettel.bss.viettelpos.v4.work.adapter.AdapterUpdateCriterial;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerCustomFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private final TextView txtDate;
	private final AdapterUpdateCriterial mAdapterUpdateCriterial;
	private final int pos;
	private ArrayList<ObjectDetailGroup> lstObjs = new ArrayList<>();

	public DatePickerCustomFragment(TextView txtDate,
			AdapterUpdateCriterial mAdapterUpdateCriterial,
			ArrayList<ObjectDetailGroup> lstObjs, int pos) {
		super();
		this.txtDate = txtDate;
		this.mAdapterUpdateCriterial = mAdapterUpdateCriterial;
		this.pos = pos;
		this.lstObjs = lstObjs;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// txtDate.setText(new
		// StringBuilder().append(day).append("/").append(month + 1)
		// .append("/").append(year)
		// .append(" "));
		String date = String.valueOf(day) + "/" +
                (month + 1) + "/" + year + " ";
		ObjectDetailGroup object = lstObjs.get(pos);
		CollectedObjectInfo item = new CollectedObjectInfo();
		item.setValue(date);
		object.setmCollectedObjectInfo(item);
		object.setValue(date);
		lstObjs.remove(pos);
		mAdapterUpdateCriterial.notifyDataSetChanged();
	}

}
