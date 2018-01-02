package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.ReportVerifyAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.MVerifyBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentContractReportVerify extends FragmentCommon {

	private Activity activity;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Spinner spnStaff;

	private EditText edtFromDate;
	private EditText edtToDate;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_report_verify);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_contract_report_verify;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		spnStaff = (Spinner) v.findViewById(R.id.spnStaff);
		edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
		edtToDate = (EditText) v.findViewById(R.id.edtToDate);

		String day = sdf.format(new Date());
		edtFromDate.setText(day);
		edtFromDate.setOnClickListener(editTextListener);
		edtToDate.setText(day);
		edtToDate.setOnClickListener(editTextListener);

        Button imb_search_contract_payment = (Button) v
                .findViewById(R.id.imb_search_contract_payment);
		imb_search_contract_payment.setOnClickListener(this);

		AsyncGetStaffByMngt asynctask = new AsyncGetStaffByMngt();
		asynctask.execute();
	}

	private final View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog datePicker = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
					datePickerListener, year, month, day);

			datePicker.getDatePicker().setTag(view);
			datePicker.show();
		}
	};

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
						+ selectedYear);
			}
		}
	};

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.imb_search_contract_payment:
			try {
				if (CommonActivity.isNetworkConnected(activity)) {
					Spin spin = (Spin) spnStaff.getSelectedItem();
					Log.d(this.getClass().getSimpleName(),
							"onClick btn_update spin: " + spin.toString());

					Date fromDate = sdf.parse(edtFromDate.getText().toString());
					Date toDate = sdf.parse(edtToDate.getText().toString());

					Calendar cal = Calendar.getInstance();
					cal.setTime(fromDate);
					cal.add(Calendar.DAY_OF_MONTH, 30);

					if (fromDate.after(toDate)) {
						CommonActivity.createAlertDialog(act,
								getString(R.string.checktimeupdatejob),
								getString(R.string.app_name)).show();
					} else if (toDate.after(cal.getTime())) {
						CommonActivity.createAlertDialog(act,
								getString(R.string.khongqua30),
								getString(R.string.app_name)).show();
					} else {
						String _staffCode = spin.getId();
						Log.d(this.getClass().getSimpleName(),
								"onClick btn_update staffCode: " + _staffCode);
						AsyncReportVerify async = new AsyncReportVerify();
						async.execute(_staffCode, edtFromDate.getText()
								.toString(), edtToDate.getText().toString());
					}
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void showDialogLoadMoreListView(ArrayAdapter adapter) {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_listview);
		dialog.setCancelable(true);
		dialog.setTitle(getString(R.string.contract_payment_report));

		LoadMoreListView loadMoreListView = (LoadMoreListView) dialog
				.findViewById(R.id.loadMoreListView);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		loadMoreListView.setAdapter(adapter);
		loadMoreListView
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						Log.i(this.getClass().getSimpleName(),
								"lv_contract_payment onLoadMore");
					}
				});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private class AsyncReportVerify extends
			AsyncTask<String, Void, ArrayList<MVerifyBean>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);
			this.progress.setMessage(activity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected ArrayList<MVerifyBean> doInBackground(String... params) {
			return reportVerify(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<MVerifyBean> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {

					ReportVerifyAdapter reportVerifyAdapter = new ReportVerifyAdapter(
							activity, result);
					showDialogLoadMoreListView(reportVerifyAdapter);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(activity, description, activity
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<MVerifyBean> reportVerify(String staffCode,
				String fromDate, String toDate) {
			ArrayList<MVerifyBean> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_reportVerify");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportVerify>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<staffCode>").append(staffCode).append("</staffCode>");
				rawData.append("<fromDate>").append(fromDate).append("</fromDate>");
				rawData.append("<toDate>").append(toDate).append("</toDate>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:reportVerify>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_reportVerify");
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
					nodechild = doc.getElementsByTagName("lstMVerifyBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						MVerifyBean obj = new MVerifyBean();
						/**
						 * <rate>NaN</rate> <staffCode>BC_TESTER</staffCode>
						 * <totalAssign>1</totalAssign>
						 * <totalFalse>0</totalFalse>
						 * <totalInTime>1</totalInTime>
						 * <totalNoVerify>1</totalNoVerify>
						 * <totalNotVerify>0</totalNotVerify>
						 * <totalOutTime>0</totalOutTime>
						 * <totalOverTime>0</totalOverTime>
						 * <totalTrue>1</totalTrue>
						 **/
						obj.setStaffCode(parse.getValue(e1, "staffCode"));
						Double dRate = Double.parseDouble(parse.getValue(e1,
								"rate"));
						String sRate = String.format("%.2f", dRate);
						obj.setRate(sRate);

						obj.setTotalAssign(parse.getValue(e1, "totalAssign"));
						obj.setTotalFalse(parse.getValue(e1, "totalFalse"));
						obj.setTotalInTime(parse.getValue(e1, "totalInTime"));
						obj.setTotalNoVerify(parse
								.getValue(e1, "totalNoVerify"));
						obj.setTotalNotAssign(parse.getValue(e1,
								"totalNotAssign"));
						obj.setTotalNotVerify(parse.getValue(e1,
								"totalNotVerify"));
						obj.setTotalOutTime(parse.getValue(e1, "totalOutTime"));
						obj.setTotalOverTime(parse
								.getValue(e1, "totalOverTime"));
						obj.setTotalTrue(parse.getValue(e1, "totalTrue"));

						list.add(obj);
					}
				}

			} catch (Exception e) {
				Log.d(this.getClass().getSimpleName(), e.toString(), e);
			}
			return list;
		}

	}

	private class AsyncGetStaffByMngt extends
			AsyncTask<String, Void, List<Spin>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(act);
			this.progress.setCancelable(false);
			this.progress.setMessage(act.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<Spin> doInBackground(String... params) {

			return getStaffByMngt();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					List<Spin> list = new ArrayList<>();
					Spin spinAll = new Spin("0", getString(R.string.all));
					list.add(spinAll);
					list.addAll(result);

					Utils.setDataSpinner(act, list, spnStaff);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.staff_not_data),
							getString(R.string.app_name)).show();
				}
				Log.d(this.getClass().getSimpleName(),
						"onPostExecute: result.size()" + result.size());
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							act.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = act.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getStaffByMngt() {
			List<Spin> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getStaffByMngt");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffByMngt>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getStaffByMngt>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStaffByMngt");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodeLstStaffBean = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);

					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					String token = parse.getValue(e2, "token");
					Session.setToken(token);
					Log.d(this.getClass().getSimpleName(), "errorCode: "
							+ errorCode + " description: " + description);

					nodeLstStaffBean = doc.getElementsByTagName("lstStaffBean");
					for (int j = 0; j < nodeLstStaffBean.getLength(); j++) {
						Element e1 = (Element) nodeLstStaffBean.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "staffCode"));
						spin.setValue(parse.getValue(e1, "name"));
						list.add(spin);
					}
				}
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(), "getStaffByMngt", e);
			}
			return list;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
}
