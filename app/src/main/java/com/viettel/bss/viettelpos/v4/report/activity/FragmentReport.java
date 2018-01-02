package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReport;
import com.viettel.bss.viettelpos.v4.report.object.ObjectReport;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FragmentReport extends FragmentCommon {
	private class AsyncTaskLoadStaff extends AsyncTask<Integer, Void, String> {
		private final Context context;
		final ProgressDialog progress;
		final String name;
		public AsyncTaskLoadStaff(Context context, String name) {
			this.context = context;
			this.name = name;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected String doInBackground(Integer... params) {
			// Log.i("TAG", " Kich thuoc " + arrChannelContent.size());
			// return startDownloadFile(params[0]);
			int l = 5;
			long time = 200;
			for (int i = 0; i < l; i++) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// Log.e("TAG", " Ket qua cap nhat cham soc " + result);
			Log.e(tag, name + " ... on post");
			this.progress.dismiss();
			processStaffSelected();
			submit();
		}
	}
	private Button btnLuyKeThang;
    private Button btnTrongNgay/* , btn30ngay, btnReport */;
	private final Calendar cal = Calendar.getInstance();
	private ChannelTypeObject channelType;
	boolean clickDate1, clickedDate2;

	// private int date1 = -1;

	// private int date2 = -1;
    private Date date1;
    private Date date2;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
	// "dd/MM/yyyy"
			LoginActivity.getInstance().getString(R.string.format_date));

	private int count = 0;
	AlertDialog.Builder dialogWarn;
	private void warnDateInvalid(int id) {
		// TODO Auto-generated method stub
		// dialogWarn = new AlertDialog.Builder(act).setTitle(R.string.app_name)
		// .setMessage(id).setCancelable(false)
		// .setNegativeButton(R.string.buttonOk, null);
		// dialogWarn.show();
		CommonActivity.createAlertDialog(getActivity(), id, R.string.app_name)
				.show();
	}

	private boolean checkDate(Date date1, Date date2) {
		Log.e(tag, count + ".....check date");
		count++;
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

		if (date2.compareTo(datex) > 0) {
			// #denNgay khong duoc lon hon ngay hien tai
			warnDateInvalid(R.string.report_warn_todate_now);
		} else if (date1.compareTo(date2) > 0) {
			// #tuNgay khong duoc lon hon #denNgay
			// txtNameActionBar.setText(R.string.report_date_invalid);
			warnDateInvalid(R.string.report_warn_todate_fromdate);

		} else {
			// ok
			return true;
		}

		return false;

	}

	// private boolean checkDate_() {
	// // TODO Auto-generated method stub
	// Date datex = new Date(System.currentTimeMillis() + 10000);
	//
	// // if (date2.compareTo(datex) > 0) {
	// // // #denNgay khong duoc lon hon ngay hien tai
	// // xxxDate(R.string.report_warn_todate_now);
	// // } else
	// if (date1.compareTo(date2) > 0) {
	// // #tuNgay khong duoc lon hon #denNgay
	// // txtNameActionBar.setText(R.string.report_date_invalid);
	// warnDateInvalid(R.string.report_warn_todate_fromdate);
	//
	// } else {
	// // ok
	// return true;
	// }
	// return false;
	// }

	private final DatePickerDialog.OnDateSetListener datePicker1 = new DatePickerDialog.OnDateSetListener() {
		long timeSet = 0;
		int countSetDate1 = 0;
		@Override
		public void onDateSet(DatePicker view, int year, int month, int date) {
			// date1 = date;
			// month1 = month;
			// year1 = year;
			Log.e(tag, "on date set....." + countSetDate1++);
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

			Date tmpDate1 = new Date(year, month, date);
			if (tmpDate1.compareTo(date1) == 0) {
				return;
			}

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, date);
			// String tmpText = dateFormat.format(cal.getTime()).toString();
			// if(tmpText.equals(textDate1)){
			// return;
			// }
			String tmpTextDate1 = dateFormat.format(cal.getTime()).toString();
			Log.e(tag, clickedLuyKeThang + "---" + clickedTrongNgay + "----"
					+ clicked30ngay);

			if (!(dateOK = checkDate(tmpDate1, date2))) {
				return;
			}
			if (clickedLuyKeThang) {
				Log.e(tag, month + "--" + date2.getMonth());
				Log.e(tag, year + "--" + date2.getYear());
				if (date != 1 || month != date2.getMonth()
						|| year != date2.getYear()) {
					btnLuyKeThang.setBackgroundResource(idGreen);
					clickedLuyKeThang = false;
					clickedTopButton = false;
					pivot = -1;
				} else {
					// btnLuyKeThang.setBackgroundResource(idYealow);
					// clickedLuyKeThang = true;
					// pivot = 0;
				}
			} else if (clickedTrongNgay) {
				if (tmpDate1.compareTo(date2) != 0) {
					// if (date != date2.getDate()) {
					btnTrongNgay.setBackgroundResource(idGreen);
					clickedTrongNgay = false;
					clickedTopButton = false;
					pivot = -1;
				} else {
					// btnTrongNgay.setBackgroundResource(idYealow);
					// clickedTrongNgay = true;
					// pivot = 1;
				}
			} else if (!clickedTopButton) {
				if ((month == date2.getMonth() && year == date2.getYear())) {
					clickedTopButton = true;
					if (date == date2.getDate()) {
						btnTrongNgay.setBackgroundResource(idYealow);
						clickedTrongNgay = true;
						pivot = 1;
					} else {
						btnLuyKeThang.setBackgroundResource(idYealow);
						clickedLuyKeThang = true;
						pivot = 0;
					}

				} else {
					// if (tmpDate2.compareTo(date1) == 0) {
					// // if (date != date2.getDate()) {
					//
					// } else {
					//
					// }
				}
			}
			textDate1 = tmpTextDate1;
			date1 = tmpDate1;
			txtFromDate.setText(textDate1);
			submit();
		}
	};
	private int countSetDate2 = 0;
	private boolean clickedTopButton = true;
	private final DatePickerDialog.OnDateSetListener datePicker2 = new DatePickerDialog.OnDateSetListener() {
		long timeSet = 0;
		@Override
		public void onDateSet(DatePicker view, int year, int month, int date) {
			// date2 = date;
			// month2 = month;
			// year2 = year;

			Log.e(tag, "on date set....." + countSetDate2++);
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
			Date tmpDate2 = new Date(year, month, date);
			if (tmpDate2.compareTo(date2) == 0) {
				return;
			}

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, date);

			String tmpTextDate2 = dateFormat.format(cal.getTime()).toString();
			String tmpTextDate1 = textDate1;
			if (clicked30ngay) {
				Log.e(tag, "chon 30 ngay ");
				cal.add(Calendar.DATE, -30);

			} else if (clickedLuyKeThang) {
				Log.e(tag, "chon luy ke thang ");
				cal.set(Calendar.DAY_OF_MONTH, 1);

			} else if (clickedTrongNgay) {
				// ttt
				Log.e(tag, "chon trong ngay ");

			} else {
				clickedTopButton = false;
			}
			Log.e(tag, clickedTopButton + "==============clicked top button");
			Date tmpDate1 = date1;
			if (clickedTopButton) {
				tmpDate1 = new Date(cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
				// date2 = new Date(cal.get(Calendar.YEAR),
				// cal.get(Calendar.MONTH),
				// cal.get(Calendar.DATE));
				tmpTextDate1 = dateFormat.format(cal.getTime()).toString();
			} else {

			}
			if (!(dateOK = checkDate(tmpDate1, tmpDate2))) {
				return;
			}
			if (!clickedTopButton) {
				if ((month == date1.getMonth() && year == date1.getYear())) {
					clickedTopButton = true;
					if (date == date1.getDate()) {
						btnTrongNgay.setBackgroundResource(idYealow);
						clickedTrongNgay = true;
						pivot = 1;
					} else {
						btnLuyKeThang.setBackgroundResource(idYealow);
						clickedLuyKeThang = true;
						pivot = 0;
					}

				} else {
					// if (tmpDate2.compareTo(date1) == 0) {
					// // if (date != date2.getDate()) {
					//
					// } else {
					//
					// }
				}
			}
			textDate2 = tmpTextDate2;
			textDate1 = tmpTextDate1;
			date2 = tmpDate2;
			date1 = tmpDate1;
			txtToDate.setText(textDate2);
			Log.e(tag, date1.getYear() + "==============date1");
			// textDate1 =
			// date1.getDate()+"/"+date1.getMonth()+"/"+date1.getYear();
			// textDate1 = dateFormat.format(date1.toString()).toString();
			txtFromDate.setText(textDate1);
			submit();
		}
	};
	// private DatePickerDialog.OnDateSetListener datePickerListener = new
	// DatePickerDialog.OnDateSetListener() {
	//
	// public void onDateSet(DatePicker view, int selectedYear,
	// int selectedMonth, int selectedDay) {
	// // year = selectedYear;
	// // month = selectedMonth;
	// // day = selectedDay;
	// // StringBuilder strDate = new StringBuilder();
	// // if (day < 10) {
	// // strDate.append("0");
	// // }
	// // strDate.append(day).append("/");
	// // if (month < 10) {
	// // strDate.append("0");
	// // }
	// // strDate.append(month).append("/");
	// // strDate.append(year);
	// // edtSaleTransDate.setText(strDate);
	// }
	// };
	// private int month1 = -1;
	// private int month2 = -1;
	private Staff selectedStaff;
	Spinner spnChannelType, spnChannelCode;
	private final String tag = "frag report";

	String textChooseChannel;
	private String textDate1;
    private String textDate2;
	private TextView txtChooseChannel;
	private EditText txtFromDate;
    private EditText txtToDate;

	// TextView txtSumRevenue, txtSumKitOut, txtSumKitActived,
	// txtPercentKitActived, txtSumKitReg, txtPercentKitReg,
	// txtSumKitReal, txtPercentKitReal, txtSumKitReal_district,
	// txtPercentKitReal_disttrict;

	// private int year1 = -1;
	// private int year2 = -1;
	// private void foo(final Staff mStaff) {
	// // TODO Auto-generated method stub
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// getActivity());
	//
	// // set title
	// alertDialogBuilder.setTitle(R.string.app_name);
	//
	// // set dialog message
	// alertDialogBuilder
	// .setMessage(mStaff.getName())
	// // .setCancelable(false)
	// .setPositiveButton(R.string.buttonOk,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// txtChooseChannel.setText(mStaff.getName());
	// }
	// })
	// .setNegativeButton(R.string.buttonCancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	//
	// }
	// });
	//
	// // show it
	// if (alertDialogBuilder != null)
	// alertDialogBuilder.show();
	// }

	private void initTime() {
		cal.setTimeInMillis(System.currentTimeMillis());
		// TODO Auto-generated method stub
		// date1 = date2 = cal.get(Calendar.DATE);
		// month1 = month2 = cal.get(Calendar.MONTH);
		// year1 = year2 = cal.get(Calendar.YEAR);
		date1 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		date2 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate1 = dateFormat.format(cal.getTime()).toString();
		txtFromDate.setText(textDate1);
		textDate2 = dateFormat.format(cal.getTime()).toString();
		txtToDate.setText(textDate2);
	}

	private void setTextDate() {
		if (textDate1 == null || txtFromDate == null) {
			return;
		}
		// if (!dateOK) {
		// initTime();
		// return;
		// }
		// TODO Auto-generated method stub
		// textDate1 = dateFormat.format(cal.getTime()).toString();
		txtFromDate.setText(textDate1);
		// textDate2 = dateFormat.format(cal.getTime()).toString();
		txtToDate.setText(textDate2);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.e(tag, "act create++++++++++++++++++++++++");
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.report_kit);
	}

	private AsyncTaskLoadStaff mAsyncTaskLoadStaff;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Log.e(tag, "act res ok");
			selectedStaff = (Staff) data.getExtras().getSerializable("staff");
			channelType = (ChannelTypeObject) data.getExtras().getSerializable(
					"channelType");

		} else {
			channelType = null;
			selectedStaff = null;
			// imgDeleteChannel.setVisibility(View.GONE);
		}
		mAsyncTaskLoadStaff = new AsyncTaskLoadStaff(act, "load staff "
				+ countStaff++);
		mAsyncTaskLoadStaff.execute(0);
	}

	private int countStaff = 0;
	// public void onActivityResult_(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == Activity.RESULT_OK) {
	// Log.e(tag, "act res ok");
	// selectedStaff = (Staff) data.getExtras().getSerializable("staff");
	// channelType = (ChannelTypeObject) data.getExtras().getSerializable(
	// "channelType");
	//
	// if (selectedStaff != null) {
	// String text = selectedStaff.getName() + " - "
	// + selectedStaff.getStaffCode();
	// Log.e(tag, "staff ok -- " + selectedStaff.getName() + " - "
	// + selectedStaff.getStaffCode());
	// // txtChooseChannel.setHint("xxx");
	// // btnLuyKeThang.setText(text);
	// toast(text);
	// foo(selectedStaff);
	// // txtNameActionBar.setText(text);
	//
	// // txtChooseChannel.postInvalidate();
	// txtChooseChannel.invalidate();
	// // txtChooseChannel.requestLayout();
	// // txtChooseChannel.setHint("xxx");
	// txtChooseChannel.setText("yyy");
	// // txtChooseChannel.setText(selectedStaff.getName() + " - "
	// // + selectedStaff.getStaffCode());
	// // imgDeleteChannel.setVisibility(View.VISIBLE);
	// } else if (channelType != null
	// && channelType.getChannelTypeId().intValue() != -1) {
	// txtChooseChannel.setHint(channelType.getName());
	// // imgDeleteChannel.setVisibility(View.VISIBLE);
	// } else {
	// txtChooseChannel.setHint(getResources().getString(
	// R.string.chooseChannelOrStaff));
	// // imgDeleteChannel.setVisibility(View.GONE);
	// }
	// } else {
	// channelType = null;
	// selectedStaff = null;
	// // imgDeleteChannel.setVisibility(View.GONE);
	// }
	// }

	private final int idGreen = R.drawable.round_button;
	private final int idYealow = R.drawable.round_button_focus;

	private void setBtnBgr(int pivot, boolean resetTime) {
		if (pivot >= 0) {
			this.pivot = pivot;
			// TODO Auto-generated method stub
			Button[] arrButtons = new Button[]{btnLuyKeThang, btnTrongNgay,
			// btn30ngay
			};
			// for(Button button:arrButtons){
			for (int i = 0; i < arrButtons.length; i++) {
				Button button = arrButtons[i];
				if (i == pivot) {
					button.setBackgroundResource(idYealow);// yealow
				} else {
					button.setBackgroundResource(idGreen);
				}
			}
		}

		// if(selectStaff){
		if (!resetTime) {
			return;
		}
		switch (pivot) {
			case 0 :
				setDataDate_LuyKeThang();
				break;
			case 1 :
				setDataDate_TrongNgay();
				break;
			case 2 :
				btn30NgayGanNhat();
				break;
			default :
				break;
		}
		Log.e(tag, clickedLuyKeThang + "---" + clickedTrongNgay + "----"
				+ clicked30ngay);
	}
	private boolean clicked30ngay;
    private boolean clickedLuyKeThang;
    private boolean clickedTrongNgay;

	private void btn30NgayGanNhat() {
		clickedLuyKeThang = false;
		clickedTrongNgay = false;
		clicked30ngay = true;
		// TODO Auto-generated method stub
		cal.setTimeInMillis(System.currentTimeMillis());
		// date2 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
		// cal.get(Calendar.DATE));
		// date2 = cal.getTime();
		date2 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate2 = dateFormat.format(cal.getTime()).toString();
		txtToDate.setText(textDate2);

		cal.add(Calendar.DATE, -30);
		// date1 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
		// cal.get(Calendar.DATE));
		// date1 = cal.getTime();
		date1 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate1 = dateFormat.format(cal.getTime()).toString();
		txtFromDate.setText(textDate1);

	}

	private void setDataDate_LuyKeThang() {
		clickedLuyKeThang = true;
		clickedTrongNgay = false;
		clicked30ngay = false;
		// TODO Auto-generated method stub
		cal.setTimeInMillis(System.currentTimeMillis());
		date2 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate2 = dateFormat.format(cal.getTime()).toString();
		txtToDate.setText(textDate2);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		date1 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate1 = dateFormat.format(cal.getTime()).toString();
		txtFromDate.setText(textDate1);

	}
	private void setDataDate_TrongNgay() {
		clickedLuyKeThang = false;
		clickedTrongNgay = true;
		clicked30ngay = false;
		// TODO Auto-generated method stub
		cal.setTimeInMillis(System.currentTimeMillis());
		// date1 = cal.getTime();
		date1 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate1 = dateFormat.format(cal.getTime()).toString();
		txtFromDate.setText(textDate1);

		// date2 = cal.getTime();
		date2 = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE));
		textDate2 = dateFormat.format(cal.getTime()).toString();
		txtToDate.setText(textDate2);

	}

	private int pivot = 1;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		int id = arg0.getId();
		switch (id) {
			case R.id.btn_report_luykethang :
				// khi che do chon da la LUY KE THANG
				//
				// khi reportAsync chua bi huy bao gio
				// tuc la reportAsync da load ket qua tra ve tu ws
				clickedTopButton = true;
				if (clickedLuyKeThang
				// && reportAsyn != null
				// && !reportAsyn.getCancel()
				) {
					return;
				}
				setBtnBgr(0, true);
				submit();
				break;
			case R.id.btn_report_trongngay :
				clickedTopButton = true;
				if (clickedTrongNgay
				// && reportAsyn != null
				// && !reportAsyn.getCancel()
				) {
					return;
				}
				setBtnBgr(1, true);
				submit();
				break;
			// case R.id.btn_report_30ngay :
			// setBtnBgr(2);
			// submit();
			// break;
			case R.id.txt_date_from :
				DatePickerDialog dpd = new FixedHoloDatePickerDialog(act,AlertDialog.THEME_HOLO_LIGHT, datePicker1,
						date2.getYear(), date2.getMonth(), date2.getDate());
				if (date2.compareTo(date1) >= 0) {
					// theo le thong thuong
					dpd = new FixedHoloDatePickerDialog(act,AlertDialog.THEME_HOLO_LIGHT, datePicker1,
							date1.getYear(), date1.getMonth(), date1.getDate());
					dpd.show();

				} else {
					// new DatePickerDialog(act, datePicker1, date2.getYear(),
					// date2.getMonth(), date2.getDate()).show();
				}
				dpd.show();
				if (Build.VERSION.SDK_INT >= 18
				// Build.VERSION_CODES.JELLY_BEAN
				) {
					dpd.setCancelable(false);
				}

				break;
			case R.id.txt_date_to :
				DatePickerDialog dpd1;
				if (dateOK) {
					if (date2.compareTo(date1) >= 0) {
						dpd1 = new FixedHoloDatePickerDialog(act,AlertDialog.THEME_HOLO_LIGHT, datePicker2,
								date2.getYear(), date2.getMonth(),
								date2.getDate());
					} else {
						dpd1 = new FixedHoloDatePickerDialog(act,AlertDialog.THEME_HOLO_LIGHT, datePicker2,
								date1.getYear(), date1.getMonth(),
								date1.getDate());
					}
				} else {
					cal.setTimeInMillis(System.currentTimeMillis());
					dpd1 = new FixedHoloDatePickerDialog(act, AlertDialog.THEME_HOLO_LIGHT,datePicker2,
							cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
							cal.get(Calendar.DATE));
					// date1 = new Date(cal.get(Calendar.YEAR),
					// cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

				}
				dpd1.show();
				if (Build.VERSION.SDK_INT >= 18
				// Build.VERSION_CODES.JELLY_BEAN
				) {
					dpd1.setCancelable(false);
				}
				break;

			case R.id.txtChooseChannel :
				// txtChooseChannel.setText("xxx");

				// FragmentChooseChannel_ fragment = new
				// FragmentChooseChannel_();
				FragmentChooseChannel fragment = new FragmentChooseChannel();
				fragment.setTargetFragment(this, 9);
				ReplaceFragment.replaceFragment(getActivity(), fragment, false);
				selectStaff = true;
				break;
			// case R.id.btnreport :
			// submit();
			// break;
			default :
				break;
		}
	}

	private AsyncReport reportAsyn;
	private void submit() {
		Log.e(tag, "-----------------------submit ");
		if (!CommonActivity.isNetworkConnected(act)) {
			CommonActivity.createAlertDialog(act, R.string.errorNetwork,
					R.string.app_name).show();
			return;
		}
		processStaffSelected_submit();
		// if (!(dateOK = checkDate())) {
		// return;
		// }
		reportAsyn = new AsyncReport(getActivity(), "async report..."
				+ countReport++);
		reportAsyn.execute();

	}
	private int countReport = 0;
	private String channelCode = "";
	private boolean dateOK = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getTask();
		Log.e(tag, "on create view-------------------");
		idLayout = R.layout.layout_report;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(tag, "on destroy");
		if (reportAsyn != null) {
			reportAsyn.cancel(false);
		}
		if (mAsyncTaskLoadStaff != null) {
			mAsyncTaskLoadStaff.cancel(false);
		}
	}

	private void processStaffSelected() {
		// TODO Auto-generated method stub
		String text = null;
		if (selectedStaff != null) {
			text = selectedStaff.getName() + " - "
					+ selectedStaff.getStaffCode();
			Log.e(tag, "staff ok -- " + text);
            setTitleActionBar(selectedStaff.getName());
			txtChooseChannel.setText(text);
		} else if (channelType != null
				&& channelType.getChannelTypeId().intValue() != -1) {
			text = channelType.getName();
			txtChooseChannel.setText(text);
            setTitleActionBar(text);
		} else {
            setTitleActionBar(R.string.selectAllChannel);
			txtChooseChannel.setHint(R.string.chooseChannelOrStaff);
		}

	}

	private void processStaffSelected_submit() {
		// TODO Auto-generated method stub
		String text = null;
		// String tmptext = null;
		if (selectedStaff != null) {
			channelCode = selectedStaff.getStaffCode();
		} else if (channelType != null
				&& channelType.getChannelTypeId().intValue() != -1) {
			channelCode = channelType.getChannelTypeId() + "";

		} else {

		}

	}

	private ListView lvListReport;
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		btnLuyKeThang = (Button) v.findViewById(R.id.btn_report_luykethang);
		btnTrongNgay = (Button) v.findViewById(R.id.btn_report_trongngay);
		// btn30ngay = (Button) v.findViewById(R.id.btn_report_30ngay);
		// btnReport = (Button) v.findViewById(R.id.btnreport);
		txtFromDate = (EditText) v.findViewById(R.id.txt_date_from);
		txtToDate = (EditText) v.findViewById(R.id.txt_date_to);
		txtChooseChannel = (TextView) v.findViewById(R.id.txtChooseChannel);
		lvListReport = (ListView) v.findViewById(R.id.lv_list_report);

		btnLuyKeThang.setOnClickListener(this);
		btnTrongNgay.setOnClickListener(this);
		// btn30ngay.setOnClickListener(this);
		// btnReport.setOnClickListener(this);
		txtFromDate.setOnClickListener(this);
		txtToDate.setOnClickListener(this);
		txtChooseChannel.setOnClickListener(this);

		Log.e(tag, "select staff == " + selectStaff);
		if (!selectStaff) {
			// pivot = 1;
			initTime();
		} else {
			setTextDate();
		}

		setBtnBgr(pivot, !selectStaff);
		if (count == 0) {
			submit();
		}

	}

	private boolean selectStaff = false;
	// webservice Report
	// webservice Report
	public class AsyncReport
			extends
				AsyncTask<Void, Void, ArrayList<ObjectReport>> {
		boolean cancel = false;
		final String tag = "report async";
		private Context context = null;
		final String name;
		String errorCode = "";
		String description = "";
		final XmlDomParse parse = new XmlDomParse();
		final ProgressDialog progress;
		// khai bao contructor
		public AsyncReport(Context context, String name) {
			this.context = context;
			this.name = name;
			this.progress = new ProgressDialog(this.context);
			progress.setCancelable(false);
			// check font
			progress.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					Log.e(tag, "on cancel");
					// cancel = true;
					// if (clickedLuyKeThang) {
					// clickedLuyKeThang = false;
					// } else if (clickedTrongNgay) {
					// clickedTrongNgay = false;
					// }

					// clickedLuyKeThang = false;
					// clickedTrongNgay = false;
				}
			});
			progress.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface arg0) {
					// TODO Auto-generated method stub
					Log.e(tag, "on dismiss");
					// ReportAsyn.this.cancel(false);
					// clickedLuyKeThang = false;
					// clickedTrongNgay = false;
				}
			});
			this.progress.setMessage(context.getResources().getString(
					R.string.searching));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ObjectReport> doInBackground(Void... params) {
			return sendRequestReport();
		}
		@Override
		protected void onPostExecute(ArrayList<ObjectReport> result) {
			Log.e(tag, "on post...." + name);

			cancel = false;
			// === check result
			progress.dismiss();
			if (result.size() > 0) {
				// === fill data for list report
				// khai bao adapter va set adapter vao cai list
				// result.get(0)
				AdapterReport adapterReport = new AdapterReport(result, act);
				lvListReport.setAdapter(adapterReport);
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getResources().getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					// countOrderItem = 0;
					// addManagerList();
					CommonActivity.createAlertDialog(act, R.string.no_data,
							R.string.app_name).show();
				}

			}
		}

		private boolean getCancel() {
			// TODO Auto-generated method stub
			return cancel;
		}

		public ArrayList<ObjectReport> sendRequestReport() {
			Log.e(tag, "send request");
			String original = null;
			ArrayList<ObjectReport> lisObjectReports = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_reportKITBusiness");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportKITBusiness>");
				rawData.append("<reportInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				// === truyen chanelcode get Fragmwntchoicechanel
				rawData.append("<channelCode>").append(channelCode);
				rawData.append("</channelCode>");
				// ======= chon tu ngay
				rawData.append("<fromDate>" + "").append(textDate1);
				rawData.append("</fromDate>");
				// === chon den ngay
				rawData.append("<toDate>" + "").append(textDate2);
				rawData.append("</toDate>");
				rawData.append("</reportInput>");
				rawData.append("</ws:reportKITBusiness>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_reportKITBusiness");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// =====parse xml ==========
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstKITReportBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ObjectReport objectReport = new ObjectReport();
						String criteria = parse.getValue(e1, "criteria");
						Log.d("criteria", criteria);
						objectReport.setCriteria(criteria);
						String percent = parse.getValue(e1, "percent");
						Log.d("percent", percent);
						objectReport.setPercent(percent);
						String total = StringUtils.formatMoney(parse.getValue(
								e1, "total"));
						Log.d("total", total);
						objectReport.setTotal(total);
						lisObjectReports.add(objectReport);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lisObjectReports;
		}

	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
