package com.viettel.bss.viettelpos.v4.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class PrcDateSingle {
	private final Activity act;
	private String textDate = "";
	private final EditText edtDate;
	// Button btn;
	public Date mDate;
	private final boolean now;
    private final boolean init;

	private Calendar cal = Calendar.getInstance();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
	// "dd/MM/yyyy"
			LoginActivity.getInstance().getString(R.string.format_date));

	public PrcDateSingle(Activity act, EditText txtDate, String textDate,
			boolean now, boolean init) {
		this.act = act;
		this.edtDate = txtDate;
		this.now = now;
		this.init = init;
		this.textDate = textDate;
		initTime();
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	// public void setBtn(Button btn) {
	// this.btn = btn;
	// }
	// public Button getBtn() {
	// return btn;
	// }
    private void warnDateInvalid(int id) {
		// TODO Auto-generated method stub
		// dialogWarn = new AlertDialog.Builder(act).setTitle(R.string.app_name)
		// .setMessage(id).setCancelable(false)
		// .setNegativeButton(R.string.buttonOk, null);
		// dialogWarn.show();
		CommonActivity.createAlertDialog(act, id, R.string.app_name).show();
	}

	private final String tag = "prc Date single";
	private boolean oldDate = false;
	private int countSetmDate = 0;

	private void initTime() {
		// TODO Auto-generated method stub

		// textDate = null;
		countSetmDate = 0;
		oldDate = false;
		if (textDate == null || textDate.isEmpty()) {
			cal.setTimeInMillis(System.currentTimeMillis());
			mDate = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DATE));
			textDate = dateFormat.format(cal.getTime()).toString();
		} else {
			initMdate();
		}

		if (init) {
			edtDate.setText(textDate);
		} else {
			edtDate.setText(R.string.chon_ngay);
		}
	}

	private void initMdate() {
		// TODO Auto-generated method stub
		String tmp[] = textDate.split("/");
		int date = Integer.parseInt(tmp[0]);
		int month = Integer.parseInt(tmp[1]) - 1;
		int year = Integer.parseInt(tmp[2]);
		mDate = new Date(year, month, date);
	}

	public boolean checkDate(Date mDate) {
		// Log.e(tag, count++ + ".....check date");
		// if (dialogWarn != null) {
		// Log.e(tag, "dialog != null");
		// return false;
		// }
		// TODO Auto-generated method stub
		// Log.e(tag, "check date");
		cal.setTimeInMillis(System.currentTimeMillis());

		// lay ra gio ko chuan hien tai, chenh lech 1900
		Date datex = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE) + 1);
		// Date datex = new Date();//lay ra gio chuan hien tai

		if (mDate.compareTo(datex) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			warnDateInvalid(R.string.report_warn_todate_now);
		}
		// else if (mDate.compareTo(date2) > 0) {
		// // #tuNgay khong duoc lon hon #denNgay
		// // txtNameActionBar.setText(R.string.report_date_invalid);
		// warnDateInvalid(R.string.report_warn_todate_fromdate);
		//
		// }
		else {
			// ok
			return true;
		}

		return false;

	}

	public boolean checkDate() {
		// Log.e(tag, count++ + ".....check date");
		// if (dialogWarn != null) {
		// Log.e(tag, "dialog != null");
		// return false;
		// }
		// TODO Auto-generated method stub
		// Log.e(tag, "check date");
		cal.setTimeInMillis(System.currentTimeMillis());

		// lay ra gio ko chuan hien tai, chenh lech 1900
		Date datex = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE) + 1);
		// Date datex = new Date();//lay ra gio chuan hien tai

		if (mDate.compareTo(datex) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			warnDateInvalid(R.string.report_warn_date_now);
		}
		// else if (mDate.compareTo(date2) > 0) {
		// // #tuNgay khong duoc lon hon #denNgay
		// // txtNameActionBar.setText(R.string.report_date_invalid);
		// warnDateInvalid(R.string.report_warn_todate_fromdate);
		//
		// }
		else {
			// ok
			return true;
		}

		return false;

	}

	public boolean checkDate(int id) {
		// Log.e(tag, count++ + ".....check date");
		// if (dialogWarn != null) {
		// Log.e(tag, "dialog != null");
		// return false;
		// }
		// TODO Auto-generated method stub
		// Log.e(tag, "check date");
		cal.setTimeInMillis(System.currentTimeMillis());

		// lay ra gio ko chuan hien tai, chenh lech 1900
		Date datex = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE) + 1);
		// Date datex = new Date();//lay ra gio chuan hien tai

		if (mDate.compareTo(datex) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			warnDateInvalid(id);
		}
		// else if (mDate.compareTo(date2) > 0) {
		// // #tuNgay khong duoc lon hon #denNgay
		// // txtNameActionBar.setText(R.string.report_date_invalid);
		// warnDateInvalid(R.string.report_warn_todate_fromdate);
		//
		// }
		else {
			// ok
			return true;
		}

		return false;

	}

	public void setTextDate() {
		if (textDate == null || edtDate == null) {
			return;
		}
		// TODO Auto-generated method stub
		// textmDate = dateFormat.format(cal.getTime()).toString();
		edtDate.setText(textDate);
		// textDate2 = dateFormat.format(cal.getTime()).toString();
	}

	public final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		long timeSet = 0;

		@Override
		public void onDateSet(DatePicker view, int year, int month, int date) {
			// mDate = date;
			// month1 = month;
			// year1 = year;
			Log.e(tag, "on date set....." + countSetmDate++);
			// Log.e(tag, Build.VERSION.SDK_INT + "....sdk_int----"
			// + Build.VERSION_CODES.);
			long timeCur = System.currentTimeMillis();
			if (Build.VERSION.SDK_INT >= 18
			// Build.VERSION_CODES.JELLY_BEAN
			) {
				Log.e(tag, ">= jelly bean");
				// if (countSetmDate % 2 != 0) {
				// return;
				// } else {
				//
				// }
				// neu 2 lan lien tiep
				// ko gan nhau thi loai
				if (timeCur - timeSet > Constant.TIME_OUT_SETDATE) {
					timeSet = timeCur;

				} else {
					// gan nhau la ok
					timeSet = timeCur;
					Log.e(tag, "on date set.....return");
					return;
				}
			} else {

			}

            // if (!(dateOK = checkDate(tmpDate))) {
			// return;
			// }
			mDate = new Date(year, month, date);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, date);
			// String tmpText = dateFormat.format(cal.getTime()).toString();
			// if(tmpText.equals(textmDate)){
			// return;
			// }
			String tmpTextDate = dateFormat.format(cal.getTime()).toString();

			edtDate.setText(tmpTextDate);

			// btnDelDate.setVisibility(View.VISIBLE);
			// if (tmpDate.compareTo(mDate) == 0 && countSetmDate >= 1) {
			// oldDate = true;
			// return;
			// } else {
			// oldDate = false;
			// }
			// submit();
			// if (!checkOldDate()) {
			// btn.setEnabled(true);
			// }
		}
	};

	public boolean checkOldDate() {
		// TODO Auto-generated method stub
		// lay text date tmp
		String tmpTextDate = "";
		// if (countSetmDate > 0) {
		// tmpTextDate = txtDate.getText().toString();
		// }
		tmpTextDate = edtDate.getText().toString();
		if (textDate != null) {
			// tmpTextDate = txtDateGift.getText().toString();
            oldDate = tmpTextDate.equals(textDate);

		} else {

		}
		textDate = tmpTextDate;
		return oldDate;
	}
}
