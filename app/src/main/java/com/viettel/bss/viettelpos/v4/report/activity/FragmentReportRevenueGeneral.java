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
import android.widget.AdapterView;
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
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReportStaffRevenueGeneral;
import com.viettel.bss.viettelpos.v4.report.object.SmartPhoneBO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FragmentReportRevenueGeneral extends FragmentCommon {

	private Activity activity;

	private EditText edt_from_date;

	private EditText edt_to_date;

	private ListView listView;

    private String fromDate;
	private String toDate;
    private String dateNowString = "";
	private List<SmartPhoneBO> lstSmartPhoneBO;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.repor_staff_revenue_general);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		idLayout = R.layout.report_layout_staff_revenue_general;
		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		try {
            Date dateNow = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edt_from_date = (EditText) v.findViewById(R.id.edt_from_date);
		edt_to_date = (EditText) v.findViewById(R.id.edt_to_date);

        Button imb_search_contract_payment = (Button) v.findViewById(R.id.imb_search_contract_payment);

		listView = (ListView) v.findViewById(R.id.listView);
		listView.setVisibility(View.GONE);

		listView.setOnItemClickListener(this);

		edt_from_date.setText(dateNowString);
		edt_from_date.setOnClickListener(this);
		fromDate = dateNowString;
		edt_to_date.setText(dateNowString);
		edt_to_date.setOnClickListener(this);
		toDate = dateNowString;
		imb_search_contract_payment.setOnClickListener(this);
		
//		Calendar calendar = Calendar.getInstance();

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {

		case R.id.edt_from_date:

			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int day) {
					fromDate = day + "/" + (month + 1) + "/" + year;
					edt_from_date.setText(fromDate);
				}
			};
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,onDateSetListener, year, month, day);
			pic.setTitle(activity.getString(R.string.chon_ngay));
			pic.show();

			break;

		case R.id.edt_to_date:

			OnDateSetListener onToDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int day) {
					toDate = day + "/" + (month + 1) + "/" + year;
					edt_to_date.setText(toDate);
				}
			};
			Calendar calendarTo = Calendar.getInstance();
			int yearTo = calendarTo.get(Calendar.YEAR);
			int monthTo = calendarTo.get(Calendar.MONTH);
			int dayTo = calendarTo.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog picTo = new FixedHoloDatePickerDialog(activity,AlertDialog.THEME_HOLO_LIGHT, onToDateSetListener, yearTo, monthTo, dayTo);
			picTo.setTitle(activity.getString(R.string.chon_ngay));
			picTo.show();

			break;

		case R.id.imb_search_contract_payment:
			if (CommonActivity.isNetworkConnected(activity)) {
				if (fromDate == null || fromDate.isEmpty()) {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.notstartdate), getString(R.string.app_name))
							.show();
					break;
				}
				if (toDate == null || toDate.isEmpty()) {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.notendate), getString(R.string.app_name))
							.show();
					break;
				}

				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date from = sdf.parse(edt_from_date.getText().toString());
					Date to = sdf.parse(edt_to_date.getText().toString());
					if (from.after(to)) {
						CommonActivity.createAlertDialog(activity, getString(R.string.checktimeupdatejob),
								getString(R.string.app_name)).show();
						break;
					}

					long diff = to.getTime() - from.getTime();
					long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					if (dayDiff > 30) {
						CommonActivity.createAlertDialog(activity, getString(R.string.khongqua30),
								getString(R.string.app_name)).show();
						break;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				AsyncContractReport asynctaskSearchContract = new AsyncContractReport(fromDate, toDate, activity);
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		super.onItemClick(adapterView, view, position, id);
		Log.i(Constant.TAG, "onItemClick position: " + position);
		if (position != lstSmartPhoneBO.size() -1) {
			SmartPhoneBO smartPhoneBO = lstSmartPhoneBO.get(position);
			if (smartPhoneBO.getListSmartPhoneBO().size() == 0) {
				loadDetailAndShowDetailSmartPhoneBO(smartPhoneBO);
			} else {
				showSmartPhoneBODetail(smartPhoneBO);
			}
		} 
	}

	private void loadDetailAndShowDetailSmartPhoneBO(SmartPhoneBO smartPhoneBO) {
		if (CommonActivity.isNetworkConnected(activity)) {
			if (fromDate == null || fromDate.isEmpty()) {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.notstartdate), getString(R.string.app_name))
						.show();
			} else if (toDate == null || toDate.isEmpty()) {
				CommonActivity.createAlertDialog(activity, getString(R.string.notendate), getString(R.string.app_name))
						.show();
			} else {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date from = sdf.parse(edt_from_date.getText().toString());
					Date to = sdf.parse(edt_to_date.getText().toString());
					if (from.after(to)) {
						CommonActivity.createAlertDialog(activity, getString(R.string.checktimeupdatejob),
								getString(R.string.app_name)).show();
					}

					long diff = to.getTime() - from.getTime();
					long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					if (dayDiff > 30) {
						CommonActivity.createAlertDialog(activity, getString(R.string.khongqua30),
								getString(R.string.app_name)).show();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				AsyncContractDetailReport asyncContractDetailReport = new AsyncContractDetailReport(fromDate, toDate,
						smartPhoneBO);
				asyncContractDetailReport.execute();
			}
		} else {
			CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
					.show();
		}
	}

	private void showSmartPhoneBODetail(SmartPhoneBO smartPhoneBO) {

		Log.d(Constant.TAG, "FragmentReportRevenueGeneral showSmartPhoneBODetail smartPhoneBO.getListSmartPhoneBO(): "
				+ smartPhoneBO.getListSmartPhoneBO().size());

		Bundle bundle = new Bundle();
		bundle.putSerializable("kListSmartPhoneBO", smartPhoneBO.getListSmartPhoneBO());

		FragmentReportRevenueDetail fragment = new FragmentReportRevenueDetail();
		fragment.setArguments(bundle);
		fragment.setTargetFragment(FragmentReportRevenueGeneral.this, 100);
		ReplaceFragment.replaceFragment(activity, fragment, true);
	}

	private class AsyncContractReport extends AsyncTask<String, Void, List<SmartPhoneBO>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		private final String fromDate;
		private final String toDate;

		public AsyncContractReport(String _fromDate, String _toDate, Activity mActivity) {
			fromDate = _fromDate;
			toDate = _toDate;
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);

			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected List<SmartPhoneBO> doInBackground(String... params) {
			return getRevenueGeneralByStaffCode();
		}

		@Override
		protected void onPostExecute(List<SmartPhoneBO> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					lstSmartPhoneBO = result;
					listView.setVisibility(View.VISIBLE);
                    AdapterReportStaffRevenueGeneral adapter = new AdapterReportStaffRevenueGeneral(activity, result);
					listView.setAdapter(adapter);
				} else {
					listView.setVisibility(View.GONE);
					// CommonActivity.createAlertDialog(getActivity(),
					// description, getString(R.string.app_name)).show();
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
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

		private List<SmartPhoneBO> getRevenueGeneralByStaffCode() {
			List<SmartPhoneBO> lstSmartPhoneBO = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getRevenueGeneralByStaffCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getRevenueGeneralByStaffCode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
				rawData.append("<toDate>").append(toDate).append("</toDate>");
				rawData.append("</input>");
				rawData.append("</ws:getRevenueGeneralByStaffCode>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getRevenueGeneralByStaffCode");
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
					nodechild = doc.getElementsByTagName("lstReportSmartPhoneDTOs");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SmartPhoneBO obj = new SmartPhoneBO();
						/**
						 * Â§ stockTypeId Â§ stockTypeName Â§ Sá»‘ lÆ°á»£ng Â§ tiá»�n trÆ°á»›c
						 * thuáº¿ Â§ Tiá»�n thuáº¿ Â§ Tiá»�n sau thuáº¿ Â§ Chiáº¿t kháº¥u
						 * <lstSmartPhoneBO> <amount>200000</amount>
						 * <discountAmount>0</discountAmount>
						 * <quantity>2</quantity> <stockTypeId>6</stockTypeId>
						 * <stockTypeName>Tháº» cÃ o</stockTypeName>
						 * <taxAmount>18182</taxAmount>
						 * <vat>10</vat> </lstSmartPhoneBO>
						 **/

						obj.setStockTypeId(Long.parseLong(parse.getValue(e1, "stockTypeId")));
						obj.setStockTypeName(parse.getValue(e1, "stockTypeName"));
						obj.setQuantity(Long.parseLong(parse.getValue(e1, "quantity")));
						obj.setDiscountAmount(Long.parseLong(parse.getValue(e1, "discountAmount")));
						obj.setVat(Long.parseLong(parse.getValue(e1, "vat")));
						obj.setTaxAmount(Long.parseLong(parse.getValue(e1, "taxAmount")));
						obj.setAmount(Long.parseLong(parse.getValue(e1, "amount")));

						obj.setAmountNotTax(obj.getAmount() - obj.getTaxAmount());

						lstSmartPhoneBO.add(obj);
					}
				}
				Log.d(Constant.TAG, "getRevenueGeneralByStaffCode lstSmartPhoneBO: " + lstSmartPhoneBO.size());
			} catch (Exception e) {
				Log.e(Constant.TAG, e.toString(), e);
			}

			if (lstSmartPhoneBO.size() > 0) {
				SmartPhoneBO obj = new SmartPhoneBO();
				obj.setStockTypeName(mActivity.getResources().getString(R.string.sum_money));
				Long qualityAmount = 0L;
				Long discountAmount = 0L;
				Long taxAmount = 0L;
				Long notTaxAmount = 0L;
				Long amount = 0L;
				
				for (SmartPhoneBO smartPhoneBO : lstSmartPhoneBO) {
					
					qualityAmount += smartPhoneBO.getQuantity();
					discountAmount += smartPhoneBO.getDiscountAmount();
					taxAmount += smartPhoneBO.getTaxAmount();
					notTaxAmount += smartPhoneBO.getAmountNotTax();
					amount += smartPhoneBO.getAmount(); 
				}
				
				obj.setQuantity(qualityAmount);
				obj.setDiscountAmount(discountAmount);
				obj.setTaxAmount(taxAmount);
				obj.setAmount(amount);
				obj.setAmountNotTax(notTaxAmount); 
				lstSmartPhoneBO.add(obj);
			}

			return lstSmartPhoneBO;
		} 
	}

	@SuppressWarnings("unused")
	private class AsyncContractDetailReport extends AsyncTask<String, Void, ArrayList<SmartPhoneBO>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		private final SmartPhoneBO smartPhoneBO;

		private final String fromDate;
		private final String toDate;

		public AsyncContractDetailReport(String _fromDate, String _toDate, SmartPhoneBO smartPhoneBO) {
			fromDate = _fromDate;
			toDate = _toDate;
			this.smartPhoneBO = smartPhoneBO;

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
		protected ArrayList<SmartPhoneBO> doInBackground(String... params) {
			return getRevenueDetailByStaffCode();
		}

		@Override
		protected void onPostExecute(ArrayList<SmartPhoneBO> result) {

			super.onPostExecute(result);
			if(progress != null){
				progress.dismiss();

			}

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					smartPhoneBO.setListSmartPhoneBO(result);
					showSmartPhoneBODetail(smartPhoneBO);
				} else {
					listView.setVisibility(View.GONE);
					// CommonActivity.createAlertDialog(getActivity(),
					// description, getString(R.string.app_name)).show();
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
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

		private ArrayList<SmartPhoneBO> getRevenueDetailByStaffCode() {
			ArrayList<SmartPhoneBO> lstSmartPhoneBO = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getRevenueDetailByStaffCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getRevenueDetailByStaffCode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<stockType>").append(smartPhoneBO.getStockTypeId()).append("</stockType>");
				rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
				rawData.append("<toDate>").append(toDate).append("</toDate>");
				rawData.append("</input>");
				rawData.append("</ws:getRevenueDetailByStaffCode>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getRevenueDetailByStaffCode");
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
					nodechild = doc.getElementsByTagName("lstReportSmartPhoneDTOs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SmartPhoneBO obj = new SmartPhoneBO();
						/**
						 * Â§ stockModelId Â§ stockModelName Â§ stockModelCode Â§ Sá»‘
						 * lÆ°á»£ng Â§ tiá»�n trÆ°á»›c thuáº¿ Â§ Tiá»�n thuáº¿ Â§ Tiá»�n sau thuáº¿ Â§
						 * Chiáº¿t kháº¥u
						 **/
						obj.setStockTypeId(Long.parseLong(parse.getValue(e1, "stockModelId")));
						obj.setStockModelName(parse.getValue(e1, "stockModelName"));
						obj.setStockModelCode(parse.getValue(e1, "stockModelCode"));

						obj.setQuantity(Long.parseLong(parse.getValue(e1, "quantity")));
						obj.setDiscountAmount(Long.parseLong(parse.getValue(e1, "discountAmount")));
						obj.setVat(Long.parseLong(parse.getValue(e1, "vat")));
						obj.setTaxAmount(Long.parseLong(parse.getValue(e1, "taxAmount")));
						obj.setAmount(Long.parseLong(parse.getValue(e1, "amount")));

						obj.setAmountNotTax(obj.getAmount() - obj.getTaxAmount());

						lstSmartPhoneBO.add(obj);
					}
				}
				Log.d(Constant.TAG, "getRevenueDetailByStaffCode lstSmartPhoneBO: " + lstSmartPhoneBO.size());
			} catch (Exception e) {
				Log.e(Constant.TAG, e.toString(), e);
			}
			return lstSmartPhoneBO;
		}

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
