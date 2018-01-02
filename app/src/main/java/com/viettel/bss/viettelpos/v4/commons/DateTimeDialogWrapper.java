package com.viettel.bss.viettelpos.v4.commons;

import java.util.Calendar;
import java.util.Date;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateTimeDialogWrapper {

	private final EditText localEditText;
	private final Activity activity;
	private final Calendar cal;
	private final OnDateSetListener callback;

	public DateTimeDialogWrapper(final EditText editText, Activity activity) {
		this.localEditText = editText;
		this.localEditText.setText(DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy"));
		this.activity = activity;
		cal = Calendar.getInstance();
		callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				try {

					int month = monthOfYear + 1;
					editText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + (month < 10 ? "0" + month :  "" + month)
							+ "/" + year);

				} catch (Exception ex) {
					Log.e("setText", "error", ex);
				}
			}
		};

		editText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
	}

	private void showDialog() {
		cal.setTime(DateTimeUtils.convertStringToTime(localEditText.getText()
				.toString(), "dd/MM/yyyy"));
		DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,callback,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		pic.setTitle(activity.getString(R.string.chon_ngay));
		pic.show();

	}
}
