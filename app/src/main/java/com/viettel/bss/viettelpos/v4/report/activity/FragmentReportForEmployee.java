package com.viettel.bss.viettelpos.v4.report.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReportListChanelForEmployee;
import com.viettel.bss.viettelpos.v4.report.object.ReportObjectChanelCareEmploy;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SuppressLint("SimpleDateFormat")
public class FragmentReportForEmployee extends Fragment implements
		OnClickListener {

	private String fromDate = "";
	private String toDate = "";
	private TextView txtNameActionBar, txtinfocs;
	private View viewhead,viewhead2;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private int toYear;
	private int toMonth;
	private int toDay;
	private Date dateFrom = null;
	private Date dateTo = null;
	private String dateFromString = "";
	private String dateToString = "";
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private EditText tvFromDate;
    private EditText tvToDate;
	private ListView lvExpand;
	private Button btnxuatbaocao;
	private LinearLayout linearmenu;
	
	private AdapterReportListChanelForEmployee aChanelForEmployee;
	private final ArrayList<ReportObjectChanelCareEmploy> lstChanelCareEmploys = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.report_layout_employee,
				container, false);
		unit(view);
		return view;
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.reporttskenh);
		super.onResume();
	}

	private void unit(View view) {
		linearmenu = (LinearLayout) view.findViewById(R.id.lin1);
		linearmenu.setVisibility(View.GONE);
		txtinfocs = (TextView) view.findViewById(R.id.tvsolancs);
		txtinfocs.setVisibility(View.GONE);
		viewhead = view.findViewById(R.id.viewhead);
		viewhead.setVisibility(View.GONE);
		viewhead2 = view.findViewById(R.id.viewhead2);
		viewhead2.setVisibility(View.GONE);
		btnxuatbaocao = (Button) view.findViewById(R.id.btnxuatbaocao);
		btnxuatbaocao.setOnClickListener(this);
		tvFromDate = (EditText) view.findViewById(R.id.edtFromDate);
		tvFromDate.setOnClickListener(this);
		tvToDate = (EditText) view.findViewById(R.id.edtToDate);
		tvToDate.setOnClickListener(this);
		lvExpand = (ListView) view.findViewById(R.id.lvdetail);
		initValue();
	}

	private void initValue() {
		Calendar cal = Calendar.getInstance();
		fromYear = cal.get(Calendar.YEAR);
		fromMonth = cal.get(Calendar.MONTH);
		fromDay = cal.get(Calendar.DAY_OF_MONTH);
		tvFromDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));

		fromDate = tvFromDate.getText().toString();
		Log.d("fromDateInit", fromDate);

		dateFromString = String.valueOf(fromYear) + "-" +
                (fromMonth + 1) + "-" + fromDay;
		Log.d("dateFromString", dateFromString);
		try {
			dateFrom = sdf.parse(dateFromString);
			Log.d("dateFrom init", "" + dateFrom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH);
		toDay = cal.get(Calendar.DAY_OF_MONTH);
		dateToString = String.valueOf(toYear) + "-" +
                (toMonth + 1) + "-" + toDay;
		Log.d("dateToString", dateToString);
		try {
			dateTo = sdf.parse(dateToString);
			Log.d("dateTo init", "" + dateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tvToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		toDate = tvToDate.getText().toString();
		Log.d("toDateInit", toDate);
	}

	private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			fromYear = selectedYear;
			fromMonth = selectedMonth;
			fromDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (fromDay < 10) {
				strDate.append("0");
			}
			strDate.append(fromDay).append("/");
			if (fromMonth < 9) {
				strDate.append("0");
			}
			strDate.append(fromMonth + 1).append("/");
			strDate.append(fromYear);

			fromDate = strDate.toString();

			Log.d("fromDate", fromDate);

			dateFromString = String.valueOf(fromYear) + "-" +
                    (fromMonth + 1) + "-" + fromDay;
			Log.d("dateFromString", dateFromString);
			try {
				dateFrom = sdf.parse(dateFromString);
				Log.d("dateFrom fromDatePickerListener", "" + dateFrom);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvFromDate.setText(strDate);
		}
	};
	private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			toYear = selectedYear;
			toMonth = selectedMonth;
			toDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (toDay < 10) {
				strDate.append("0");
			}
			strDate.append(toDay).append("/");
			if (toMonth < 9) {
				strDate.append("0");
			}
			strDate.append(toMonth + 1).append("/");
			strDate.append(toYear);

			toDate = strDate.toString();

			Log.d("toDate", toDate);

			dateToString = String.valueOf(toYear) + "-" +
                    (toMonth + 1) + "-" + toDay;
			Log.d("dateToString", dateToString);
			try {

				dateTo = sdf.parse(dateToString);
				Log.d("dateTo fromDatePickerListener", "" + dateFrom);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvToDate.setText(strDate);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	case R.id.btnHome :
			
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.edtFromDate:
			DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT,fromDatePickerListener, fromYear, fromMonth,
					fromDay);
			fromDateDialog.show();
			break;
		case R.id.edtToDate:
			DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT,toDatePickerListener, toYear, toMonth, toDay);
			toDateDialog.show();
			break;
		case R.id.btnxuatbaocao:
			// ====== xuat bao cao ===========
			if (dateFrom != null && dateTo != null) {
				if (dateFrom.after(dateTo)) {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.checktimeupdatejob),
							Toast.LENGTH_SHORT).show();
				} else if (dateFrom.compareTo(dateTo) == 0) {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						if (lstChanelCareEmploys.size() > 0 && aChanelForEmployee != null) {
							lstChanelCareEmploys.clear();
							aChanelForEmployee.notifyDataSetChanged();
						}
						
						GetDetailChanelCareEmployee getCareEmployee = new GetDetailChanelCareEmployee(getActivity());
						getCareEmployee.execute();
						
					} else {
						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name));
						dialog.show();
					}
				} else if (dateFrom.before(dateTo)) {
					
					if (CommonActivity.isNetworkConnected(getActivity())) {
						
						long day = getDateDiff(dateFrom, dateTo, TimeUnit.MINUTES);
						Log.d("dayyyyyyyyyyyy", ""+day);
						if(day <= 30){
							if (lstChanelCareEmploys.size() > 0 && aChanelForEmployee != null) {
								lstChanelCareEmploys.clear();
								aChanelForEmployee.notifyDataSetChanged();
							}
							GetDetailChanelCareEmployee getCareEmployee = new GetDetailChanelCareEmployee(getActivity());
							getCareEmployee.execute();
						}else{
							if (lstChanelCareEmploys.size() > 0 && aChanelForEmployee != null) {
								lstChanelCareEmploys.clear();
								aChanelForEmployee.notifyDataSetChanged();
							}
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.checktg), Toast.LENGTH_SHORT).show();
						}
						
					} else {
						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name));
						dialog.show();
					}
				}
			}

			break;	
			
			
		default:
			break;

		}
	}
	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};
	// ws get detail chanel care
	public class GetDetailChanelCareEmployee extends
			AsyncTask<Integer, Void, ArrayList<ReportObjectChanelCareEmploy>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetDetailChanelCareEmployee(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ReportObjectChanelCareEmploy> doInBackground(
				Integer... params) {
			// truyen visited num
			return getListDetailChanelCare();
		}
		
		@Override
		protected void onPostExecute(ArrayList<ReportObjectChanelCareEmploy> result) {

			// check errorcode
			progress.dismiss();

			if (errorCode.equals("0")) {
				
				if(result.size() > 0){
				
					// visibleMenu
					linearmenu.setVisibility(View.VISIBLE);
					txtinfocs.setVisibility(View.VISIBLE);
					viewhead.setVisibility(View.VISIBLE);
					viewhead2.setVisibility(View.VISIBLE);
					// apdater
					aChanelForEmployee = new AdapterReportListChanelForEmployee(getActivity(), result , fromDate,toDate);
					lvExpand.setAdapter(aChanelForEmployee);
					
				}else{
					CommonActivity.createAlertDialog(
					getActivity(),
					getResources().getString(R.string.no_data),
					getResources().getString(
							R.string.reporttskenh)).show();
				}
				
			} 
			else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.reporttskenh));
					dialog.show();

				}
			}

		}

		public ArrayList<ReportObjectChanelCareEmploy> getListDetailChanelCare() {
			ArrayList<ReportObjectChanelCareEmploy> lisChanelCareDetails = new ArrayList<>();
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_reportChannelCareBusinessAllProvince");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportChannelCareBusinessAllProvince>");
				rawData.append("<reportInput>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("fromDate", fromDate);
				param.put("toDate", toDate);
				rawData.append(input.buildXML(param));
				rawData.append("</reportInput>");
				rawData.append("</ws:reportChannelCareBusinessAllProvince>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_reportChannelCareBusinessAllProvince");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ============== parse xml =================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstChannelCareReportBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						
						ReportObjectChanelCareEmploy reChanelCareEmploy = new ReportObjectChanelCareEmploy();

						String userCode = parse.getValue(e1, "userCode");
						Log.d("userCode", userCode);
						reChanelCareEmploy.setUserCode(userCode);
						
						String visit1 = parse.getValue(e1, "visit1");
						Log.d("visit1", visit1);
						if(visit1.isEmpty() || visit1 == null){
							visit1 = "0";
						}
						reChanelCareEmploy.setVisit1(Long.parseLong(visit1));
						
						String visit2 = parse.getValue(e1, "visit2");
						Log.d("visit2", visit2);
						if(visit2.isEmpty() || visit2 == null){
							visit2 = "0";
						}
						reChanelCareEmploy.setVisit2(Long.parseLong(visit2));
						
						String visit3 = parse.getValue(e1, "visit3");
						Log.d("visit3", visit3);
						if(visit3.isEmpty() || visit3 == null){
							visit3 = "0";
						}
						reChanelCareEmploy.setVisit3(Long.parseLong(visit3));
						
						String visit4 = parse.getValue(e1, "visit4");
						Log.d("visit4", visit4);
						if(visit4.isEmpty() || visit4 == null){
							visit4 = "0";
						}
						reChanelCareEmploy.setVisit4(Long.parseLong(visit4));
						
						// add list detail
						lisChanelCareDetails.add(reChanelCareEmploy);

					}
				}

			} catch (Exception e) {
				Log.d("GetDetailChanelCareEmployee", e.toString());
			}

			return lisChanelCareDetails;
		}

	}
	 private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		    long diffInMillies = date2.getTime() - date1.getTime();
		    return diffInMillies/(24*60*60*1000);
	 }
	
}
