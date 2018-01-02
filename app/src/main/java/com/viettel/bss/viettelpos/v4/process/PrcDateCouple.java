package com.viettel.bss.viettelpos.v4.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class PrcDateCouple {
	private final Activity act;
	public String textDateFrom = "", textDateTo = "";
	private final EditText tvDateFrom;
    private final EditText tvDateTo;
	private Button btn;
	public Date mDateFrom, mDateTo;
	private final Calendar cal = Calendar.getInstance();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
	// "dd/MM/yyyy"
	// LoginActivity.getInstance().getString(R.string.format_date));
			MainActivity.getInstance().getString(R.string.format_date));
	private boolean oldDateFrom = false;
	boolean dateOK = false;
	private boolean setTextTimeTo = false;
	boolean setTextTimeFrom = true;
	public PrcDateCouple(Activity act, EditText tvDateFrom, EditText tvDateTo,
			boolean setTextTimeTo, String textDateFrom, String textDateTo) {
		this.act = act;
		this.tvDateFrom = tvDateFrom;
		this.tvDateTo = tvDateTo;
		this.setTextTimeTo = setTextTimeTo;
		this.textDateFrom = textDateFrom;
		this.textDateTo = textDateTo;
		initTimeFrom();
		initTimeTo();

	}

	// public PrcDateCouple(Activity act, EditText tvDateFrom, EditText
	// tvDateTo,
	// String textDateFrom, String textDateTo) {
	// this.act = act;
	// this.tvDateFrom = tvDateFrom;
	// this.tvDateTo = tvDateTo;
	// this.textDateFrom = textDateFrom;
	// this.textDateTo = textDateTo;
	// initTimeFrom();
	// initTimeTo();
	//
	// }

	public void setBtn(Button btn) {
		this.btn = btn;
	}
	public Button getBtn() {
		return btn;
	}
	// int countSetDateFrom, countSetDateTo;
    private void checkOldDateFrom() {
		// TODO Auto-generated method stub
		String tmpTextDateFrom = "";
		// if (countSetDateFrom >= 0) {
		tmpTextDateFrom = tvDateFrom.getText().toString();
		// }

		if (textDateFrom != null) {
			// tmpTextDate = txtDateGift.getText().toString();
            oldDateFrom = tmpTextDateFrom.equals(textDateFrom);

		} else {

		}
		textDateFrom = tmpTextDateFrom;
	}
	private boolean oldDateTo;
	private final String tag = "prc date";
	private void warnDateInvalid(int id) {
		// TODO Auto-generated method stub
		// dialogWarn = new AlertDialog.Builder(act).setTitle(R.string.app_name)
		// .setMessage(id).setCancelable(false)
		// .setNegativeButton(R.string.buttonOk, null);
		// dialogWarn.show();
		CommonActivity.createAlertDialog(act, id, R.string.app_name).show();
	}

	private void checkOldDateTo() {
		// TODO Auto-generated method stub
		String tmpTextDateTo = "";
		// if (countSetDateTo >= 0) {
		tmpTextDateTo = tvDateTo.getText().toString();
		// }
		Log.e(tag, tmpTextDateTo + "----" + textDateTo);
		if (textDateTo != null) {
			// tmpTextDate = txtDateGift.getText().toString();
            oldDateTo = tmpTextDateTo.equals(textDateTo);

		} else {

		}
		textDateTo = tmpTextDateTo;
	}
	private int count;
	public boolean isFullData() {
		String textDateFrom = tvDateFrom.getText().toString();
		String textDateTo = tvDateTo.getText().toString();
		return textDateFrom != null && !textDateFrom.isEmpty()
				&& textDateTo != null && !textDateTo.isEmpty();
		// return setTextTimeFrom && setTextTimeTo;
	}

	public boolean checkValidDate(/* Date date1, Date date2 */boolean toDateNow,
			int[] arrResId) {
		Log.e(tag, count++ + ".....check date");

		// if (dialogWarn != null) {
		// Log.e(tag, "dialog != null");
		// return false;
		// }
		// TODO Auto-generated method stub
		// Log.e(tag, "check date");
		cal.setTimeInMillis(System.currentTimeMillis());

		// lay ra gio ko chuan hien tai, chenh lech 1900
		Date dateCurrent = new Date(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		// Date datex = new Date();//lay ra gio chuan hien tai
		// if (countSetDateFrom > 0) {
		// int k = 0;
		if (mDateFrom.compareTo(dateCurrent) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			// warnDateInvalid(R.string.report_warn_fromdate_now);
			warnDateInvalid(arrResId[0]);
			return false;

		}
		// }
		// if (countSetDateTo > 0) {
		if (toDateNow && mDateTo.compareTo(dateCurrent) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			// warnDateInvalid(R.string.report_warn_todate_now);
			warnDateInvalid(arrResId[1]);
			return false;
		}
		// if (countSetDateFrom > 0) {
		if (mDateFrom.compareTo(mDateTo) > 0) {
			// #tuNgay khong duoc lon hon #denNgay
			// txtNameActionBar.setText(R.string.report_date_invalid);
			// warnDateInvalid(R.string.report_warn_todate_fromdate);
			warnDateInvalid(arrResId[2]);
			return false;
		}
		// }

		// }
		return true;

	}

	public boolean checkOldDate() {
		checkOldDateFrom();
		checkOldDateTo();
		return oldDateFrom && oldDateTo;
	}
	public final DatePickerDialog.OnDateSetListener onDateSetListenerFrom = new DatePickerDialog.OnDateSetListener() {
		long timeSet;

		@Override
		public void onDateSet(DatePicker view, int year, int month, int date) {

			// Log.e(tag, "on date set....." + countSetDateFrom++);
			// Log.e(tag, Build.VERSION.SDK_INT + "....sdk_int----"
			// + Build.VERSION_CODES.);
			long timeCur = System.currentTimeMillis();
			if (Build.VERSION.SDK_INT >= 18
			// Build.VERSION_CODES.JELLY_BEAN
			) {
				Log.e(tag, ">= jelly bean");

				// neu 2 lan lien tiep
				// ko gan nhau thi loai
				if (timeCur - timeSet > Constant.TIME_OUT_SETDATE) {
					timeSet = timeCur;
					// return;
				} else {
					// gan nhau la ok
					timeSet = timeCur;
					Log.e(tag, "on date set.....return");
					return;
				}
			} else {

			}

			Date tmpDate = new Date(year, month, date);

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, date);

			String tmpTextDateFrom = dateFormat.format(cal.getTime())
					.toString();

			// if (tmpDate.compareTo(mDateFrom) == 0 && countSetDateFrom > 1) {
			// oldDateFrom = true;
			// return;
			// } else {
			// oldDateFrom = false;
			// }
			mDateFrom = tmpDate;
			// textDateFrom = tmpTextDateFrom;
			tvDateFrom.setText(tmpTextDateFrom);

			// btnDelDateFrom.setVisibility(View.VISIBLE);
			// submit();
		}
	};

	public final DatePickerDialog.OnDateSetListener onDateSetListenerTo = new DatePickerDialog.OnDateSetListener() {
		long timeSet;

		@Override
		public void onDateSet(DatePicker view, int year, int month, int date) {

			// Log.e(tag, "on date set....." + countSetDateTo++);
			// /////////////////
			long timeCur = System.currentTimeMillis();
			if (Build.VERSION.SDK_INT >= 18
			// Build.VERSION_CODES.JELLY_BEAN
			) {

				// neu 2 lan lien tiep
				// ko gan nhau thi loai
				if (timeCur - timeSet > Constant.TIME_OUT_SETDATE) {
					timeSet = timeCur;
					// return;
				} else {
					// gan nhau la ok
					timeSet = timeCur;
					Log.e(tag, "on date set.....return");
					return;
				}
			} else {

			}
			// /////////////
			Date tmpDate = new Date(year, month, date);

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, date);
			String tmpTextDateTo = dateFormat.format(cal.getTime()).toString();

			// if (tmpDate.compareTo(mDateTo) == 0 && countSetDateTo > 1) {
			// oldDateTo = true;
			// return;
			// } else {
			// oldDateTo = false;
			// }
			mDateTo = tmpDate;
			// textDateTo = tmpTextDateTo;
			tvDateTo.setText(tmpTextDateTo);
			// btnDelDateTo.setVisibility(View.VISIBLE);
			// submit();
		}
	};
	private void initTimeFrom() {
		// TODO Auto-generated method stub
		// mDate = date2 = cal.get(Calendar.DATE);
		// month1 = month2 = cal.get(Calendar.MONTH);
		// year1 = year2 = cal.get(Calendar.YEAR);
		// textDateFrom = null;
		oldDateFrom = false;
		// countSetDateFrom = 0;
		// mdatefr
		if (textDateFrom != null && !textDateFrom.isEmpty()) {
			String arrDate[] = textDateFrom.split("/");
			mDateFrom = new Date(Integer.parseInt(arrDate[2]),
					Integer.parseInt(arrDate[1]) - 1,
					Integer.parseInt(arrDate[0]));
			tvDateFrom.setText(textDateFrom);
			return;
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		mDateFrom = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
		// cal.get(Calendar.DATE)
				1);

		textDateFrom = dateFormat.format(cal.getTime()).toString();

		tvDateFrom.setText(textDateFrom);
		// tvDateFrom.setText(R.string.chon_ngay);

	}
	private void initTimeTo() {
		// TODO Auto-generated method stub
		// countSetDateTo = 0;
		// textDateTo = null;
		oldDateTo = false;
		if (textDateTo != null && !textDateTo.isEmpty()) {
			String arrDate[] = textDateTo.split("/");
			mDateTo = new Date(Integer.parseInt(arrDate[2]),
					Integer.parseInt(arrDate[1]) - 1,
					Integer.parseInt(arrDate[0]));
			tvDateTo.setText(textDateTo);
			return;
		}
		cal.setTimeInMillis(System.currentTimeMillis());
		mDateTo = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDateTo = dateFormat.format(cal.getTime()).toString();
		if (setTextTimeTo) {
			tvDateTo.setText(textDateTo);
		}

		// tvDateTo.setText(R.string.chon_ngay);
	}

	private final long oneDayInMillSec = 86400 * 1000;
	private int getCountDayBween() {
		// TODO Auto-generated method stub

		return (int) ((mDateTo.getTime() - mDateFrom.getTime()) / oneDayInMillSec);
	}

	public boolean checkDayBetween(int x, int id) {
		boolean res = getCountDayBween() <= x;
		if (!res) {
			warnDateInvalid(id);
		}
		return res;
	}
}
