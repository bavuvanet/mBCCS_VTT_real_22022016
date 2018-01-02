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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
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
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterExpandReport;
import com.viettel.bss.viettelpos.v4.report.object.ReportChanelCareDetail;
import com.viettel.bss.viettelpos.v4.report.object.ReportHeader;
import com.viettel.bss.viettelpos.v4.report.object.ReportObjectChanellCare;
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
public class FragmentReportChanelCare extends Fragment implements
		OnClickListener {

	private String fromDate = "";
	private String toDate = "";
	private Button btnHome;
	private TextView txtNameActionBar;

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

	private static FragmentReportChanelCare instance = null;
	private int position = 0;
	private ReportObjectChanellCare reportObjectChanellCare = new ReportObjectChanellCare();
	private EditText tvFromDate;
    private EditText tvToDate;
	private ExpandableListView lvExpand;
	private Button btnxuatbaocao;

    private final ArrayList<ReportHeader> lisReportHeaders = new ArrayList<>();

	private AdapterExpandReport adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.report_layout, container, false);
		unit(view);
		return view;
	}

	private void unit(View view) {
		instance = this;
		btnxuatbaocao = (Button) view.findViewById(R.id.btnxuatbaocao);
		btnxuatbaocao.setOnClickListener(this);
		tvFromDate = (EditText) view.findViewById(R.id.edtFromDate);
		tvFromDate.setOnClickListener(this);
		tvToDate = (EditText) view.findViewById(R.id.edtToDate);
		tvToDate.setOnClickListener(this);
		lvExpand = (ExpandableListView) view.findViewById(R.id.lvExpand);
		initValue();

		lvExpand.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				position = groupPosition;

				switch (groupPosition) {
				case 0:
					if (reportObjectChanellCare.getVisit1() > 0) {
						getListChild(groupPosition);
					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.checkcs1),
								getResources().getString(
										R.string.REPORT_CHANEL_CARE)).show();
					}
					break;
				case 1:
					if (reportObjectChanellCare.getVisit2() > 0) {
						getListChild(groupPosition);
					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.checkcs2),
								getResources().getString(
										R.string.REPORT_CHANEL_CARE)).show();
					}
					break;
				case 2:
					if (reportObjectChanellCare.getVisit3() > 0) {
						getListChild(groupPosition);
					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.checkcs3),
								getResources().getString(
										R.string.REPORT_CHANEL_CARE)).show();
					}
					break;
				case 3:
					if (reportObjectChanellCare.getVisit4() > 0) {
						getListChild(groupPosition);
					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.checkcs4),
								getResources().getString(
										R.string.REPORT_CHANEL_CARE)).show();
					}
					break;

				default:
					break;
				}

			}
		});

	}

	private void getListChild(int groupPosition) {
		new GetDetailChanelCare(getActivity()).execute(groupPosition + 1);
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
			Log.d("dateFrom init", ""+dateFrom);
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
			Log.d("dateTo init", ""+dateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tvToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		toDate = tvToDate.getText().toString();
		Log.d("toDateInit", toDate);
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.REPORT_CHANEL_CARE);
		super.onResume();
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
				Log.d("dateFrom fromDatePickerListener", ""+dateFrom);
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
				Log.d("dateTo fromDatePickerListener", ""+dateFrom);
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
					getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, fromYear, fromMonth,
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
						if (lisReportHeaders.size() > 0 && adapter != null) {
							lisReportHeaders.clear();
							adapter.notifyDataSetChanged();
						}
						GetListVisitChanelCare getListVisitChanelCare = new GetListVisitChanelCare(
								getActivity());
						getListVisitChanelCare.execute();
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
							if (lisReportHeaders.size() > 0 && adapter != null) {
								lisReportHeaders.clear();
								adapter.notifyDataSetChanged();
							}
							GetListVisitChanelCare getListVisitChanelCare = new GetListVisitChanelCare(
									getActivity());
							getListVisitChanelCare.execute();
						}else{
							if (lisReportHeaders.size() > 0 && adapter != null) {
								lisReportHeaders.clear();
								adapter.notifyDataSetChanged();
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

	// add list arr
	private void addListArrayReport() {
        String KEY_CS1 = "1";
        lisReportHeaders.add(new ReportHeader(getActivity().getResources()
				.getString(R.string.solancs1lan)
				+ " <font color=\"red\">"
				+ reportObjectChanellCare.getVisit1() + "</font>", KEY_CS1));
        String KEY_CS2 = "2";
        lisReportHeaders.add(new ReportHeader(getActivity().getResources()
				.getString(R.string.solancs2lan)
				+ " <font color=\"red\">"
				+ reportObjectChanellCare.getVisit2() + "</font>", KEY_CS2));
        String KEY_CS3 = "3";
        lisReportHeaders.add(new ReportHeader(getActivity().getResources()
				.getString(R.string.solancs3lan)
				+ " <font color=\"red\">"
				+ reportObjectChanellCare.getVisit3() + "</font>", KEY_CS3));
        String KEY_CS4 = "4";
        lisReportHeaders.add(new ReportHeader(getActivity().getResources()
				.getString(R.string.solancs4lan)
				+ " <font color=\"red\">"
				+ reportObjectChanellCare.getVisit4() + "</font>", KEY_CS4));

		adapter = new AdapterExpandReport(getActivity(), lisReportHeaders);
		lvExpand.setAdapter(adapter);
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

	// Asyn task get list visit chanel report
	public class GetListVisitChanelCare extends
			AsyncTask<Void, Void, ReportObjectChanellCare> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListVisitChanelCare(Context context) {
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
		protected ReportObjectChanellCare doInBackground(Void... params) {
			return getListChanelCare();
		}

		@Override
		protected void onPostExecute(ReportObjectChanellCare result) {

			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.getVisit1() != 0 || result.getVisit2() != 0
						|| result.getVisit3() != 0 || result.getVisit4() != 0) {
					reportObjectChanellCare = result;
					Log.d("result", "visit1 : " + result.getVisit1()
							+ "\n visit2 :" + result.getVisit2()
							+ "\n visit3 :" + result.getVisit3()
							+ "\n visit4 :" + result.getVisit4());

					addListArrayReport();
				} else {
					CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.no_data),
							getResources().getString(
									R.string.REPORT_CHANEL_CARE)).show();
				}
			} else {
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
									.getString(R.string.REPORT_CHANEL_CARE));
					dialog.show();

				}
			}

		}

		private ReportObjectChanellCare getListChanelCare() {

			String original = "";
			ReportObjectChanellCare reportObjectChanellCare = new ReportObjectChanellCare();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_reportChannelCareBusiness");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportChannelCareBusiness>");
				rawData.append("<reportInput>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("fromDate", fromDate);
				param.put("toDate", toDate);
				rawData.append(input.buildXML(param));
				rawData.append("</reportInput>");
				rawData.append("</ws:reportChannelCareBusiness>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_reportChannelCareBusiness");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc
							.getElementsByTagName("channelCareReportBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String visit1 = parse.getValue(e1, "visit1");
						Log.d("visit1", visit1);
						reportObjectChanellCare.setVisit1(Long
								.parseLong(visit1));

						String visit2 = parse.getValue(e1, "visit2");
						Log.d("visit2", visit2);
						reportObjectChanellCare.setVisit2(Long
								.parseLong(visit2));

						String visit3 = parse.getValue(e1, "visit3");
						Log.d("visit3", visit3);
						reportObjectChanellCare.setVisit3(Long
								.parseLong(visit3));

						String visit4 = parse.getValue(e1, "visit4");
						Log.d("visit4", visit4);
						reportObjectChanellCare.setVisit4(Long
								.parseLong(visit4));

					}
				}
			} catch (Exception e) {
				Log.d("ReportObjectChanellCare", e.toString());
			}
			return reportObjectChanellCare;
		}
	}

	// ws get detail chanel care
	public class GetDetailChanelCare extends
			AsyncTask<Integer, Void, ArrayList<ReportChanelCareDetail>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetDetailChanelCare(Context context) {
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
		protected ArrayList<ReportChanelCareDetail> doInBackground(
				Integer... params) {
			// truyen visited num
			return getListDetailChanelCare(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ReportChanelCareDetail> result) {

			// check errorcode
			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					// success get list
					ReportHeader reportHeader = new ReportHeader();
					reportHeader.setLisChanelCareDetails(result);

					ReportHeader reportHeader1 = lisReportHeaders.get(position);
					String name = reportHeader1.getName();

					ReportHeader reportHeader2 = new ReportHeader(name, result);

					lisReportHeaders.remove(position);
					lisReportHeaders.add(position, reportHeader2);
					adapter.notifyDataSetChanged();
				} else {
					CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.no_data),
							getResources().getString(
									R.string.REPORT_CHANEL_CARE)).show();
				}
			} else {
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
									.getString(R.string.REPORT_CHANEL_CARE));
					dialog.show();

				}
			}

		}

		public ArrayList<ReportChanelCareDetail> getListDetailChanelCare(
				int visitNum) {
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails = new ArrayList<>();
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_reportChannelCareDetailBusiness");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportChannelCareDetailBusiness>");
				rawData.append("<reportInput>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("fromDate", fromDate);
				param.put("toDate", toDate);
				param.put("visitedNum", "" + visitNum);
				rawData.append(input.buildXML(param));
				rawData.append("</reportInput>");
				rawData.append("</ws:reportChannelCareDetailBusiness>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_reportChannelCareDetailBusiness");
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
					nodechild = doc.getElementsByTagName("lstStaff");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReportChanelCareDetail reportChanelCareDetail = new ReportChanelCareDetail();

						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						reportChanelCareDetail.setAddress(address);

						String channelType = parse.getValue(e1, "channelType");
						Log.d("channelType", channelType);
						reportChanelCareDetail.setChanelType(channelType);

						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						reportChanelCareDetail.setNameChanel(name);

						// add list detail
						lisChanelCareDetails.add(reportChanelCareDetail);

					}
				}

			} catch (Exception e) {
				Log.d("GetDetailChanelCare", e.toString());
			}

			return lisChanelCareDetails;
		}

	}
	 private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		    long diffInMillies = date2.getTime() - date1.getTime();
		    return diffInMillies/(24*60*60*1000);
	 }
}
