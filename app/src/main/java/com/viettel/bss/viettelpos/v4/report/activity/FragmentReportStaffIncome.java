package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReportStaffIncome;
import com.viettel.bss.viettelpos.v4.report.object.DataStaffResult;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentReportStaffIncome extends FragmentCommon {

	private Activity activity;

	private EditText edt_report_date;

	private ListView listView;

    private Date fromDate;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.repor_staff_income);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		idLayout = R.layout.report_layout_staff_income;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edt_report_date = (EditText) v.findViewById(R.id.edt_report_date);


        Button imb_search_contract_payment = (Button) v.findViewById(R.id.imb_search_contract_payment);

		listView = (ListView) v.findViewById(R.id.listView);
		listView.setVisibility(View.GONE);
		
		edt_report_date.setOnClickListener(this);
		imb_search_contract_payment.setOnClickListener(this); 
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {

		case R.id.edt_report_date:

			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int day) {
					String reportDate = day + "/" + (month + 1) + "/" + year;
					edt_report_date.setText(reportDate);
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, day, 0 , 0 , 0);
					fromDate = calendar.getTime();
				}
			};
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, month, day);
			pic.setTitle(activity.getString(R.string.chon_ngay));
			pic.show();

			break;

		case R.id.imb_search_contract_payment:
			if (CommonActivity.isNetworkConnected(activity)) {
				if (fromDate == null) {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.edt_report_date_select), getString(R.string.app_name))
							.show();
					break;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				AsyncContractReport asynctaskSearchContract = new AsyncContractReport(sdf.format(fromDate));
				asynctaskSearchContract.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
		default:
			break;
		}
	}
	
	private class AsyncContractReport extends AsyncTask<String, Void, List<DataStaffResult>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		private final String fromDate;

		public AsyncContractReport(String _fromDate) {
			fromDate = _fromDate;

			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);
			this.progress.setMessage(activity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected List<DataStaffResult> doInBackground(String... params) {
			return getDataStaff();
		}

		@Override
		protected void onPostExecute(List<DataStaffResult> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				listView.setVisibility(View.VISIBLE);
				if (result != null && result.size() > 0) {
                    AdapterReportStaffIncome adapter = new AdapterReportStaffIncome(activity, result);
					listView.setAdapter(adapter);
				} else {
					listView.setVisibility(View.GONE);
					CommonActivity.createAlertDialog(getActivity(), description,
							getString(R.string.app_name)).show();
		//			CommonActivity.createAlertDialog(getActivity(), getString(R.string.ko_co_dl_contract),
		//					getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							activity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private List<DataStaffResult> getDataStaff() {
			List<DataStaffResult> lstData  = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getDataStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getDataStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
				rawData.append("</input>");
				rawData.append("</ws:getDataStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getDataStaff");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstData");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						DataStaffResult obj = new DataStaffResult();
						/**
						 <lstData>
			               <areaCode>HUONGKV_TT1</areaCode>
			               <dataDate>16/11/2015</dataDate>
			               <rptIndexId>3</rptIndexId>
			               <valueDay>0.0</valueDay>
			               <valueMonth>3.56</valueMonth>
			            </lstData>
						 **/
						obj.setTargetValue(Double.parseDouble(parse.getValue(e1, "targetValue")));
						obj.setAreaCode(parse.getValue(e1, "areaCode"));
						obj.setDataDate(parse.getValue(e1, "dataDate"));
						obj.setRptIndexId(Long.parseLong(parse.getValue(e1, "rptIndexId")));
						obj.setValueDay(Double.parseDouble(parse.getValue(e1, "valueDay")));
						obj.setValueMonth(Double.parseDouble(parse.getValue(e1, "valueMonth")));

						lstData.add(obj);
					}
				}
				Log.d(Constant.TAG, "getDataStaff lstData: " + lstData.size());
			} catch (Exception e) {
				Log.e(Constant.TAG, e.toString(), e);
			}
			return lstData;
		}

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
