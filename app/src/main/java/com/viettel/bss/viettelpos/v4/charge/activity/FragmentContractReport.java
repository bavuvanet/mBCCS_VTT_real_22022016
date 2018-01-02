package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.Date;
import java.util.HashMap;
import com.viettel.bss.viettelpos.v4.charge.fragment.FragmentChooseContractFormMngt;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.channel.object.ReportProgressBean;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectNewAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.ReportProgessAdapter;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentContractReport extends FragmentCommon {

	private Activity activity;

	private Spinner spn_report_data;
	private EditText edt_report_date;
	private EditText edt_bill_cycle;
	private EditText edtObjectCus;

    private final List<Spin> lstSpin = new ArrayList<>();
	private CustomerObjectNewAdapter customerObjectAdapter;

    private String contractFormMngt;

	private String contractName;

	private ScrollView svInfoPayment;

	
	private final int REQUEST_N = 1999;
	
	private ArrayList<ContractFormMngtBean> lstContractForm = new ArrayList<ContractFormMngtBean>();
	
	private Spinner spn_selectgroup;
	private List<Spin> arrSelectGroup = new ArrayList<Spin>();
    @Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_report);
		if(!CommonActivity.isNullOrEmptyArray(lstContractForm)){
			edtObjectCus.setText(getListCodeContract());
		}else{
			edtObjectCus.setText("");
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_N:
				lstContractForm = (ArrayList<ContractFormMngtBean>) data.getSerializableExtra("lstContract");
				if(!CommonActivity.isNullOrEmptyArray(lstContractForm)){
					edtObjectCus.setText(getListCodeContract().toString());
				}else{
					edtObjectCus.setText("");
				}
				break;
			default :
				break;
			}
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_contract_report;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		spn_selectgroup = (Spinner) v.findViewById(R.id.spn_selectgroup);
		initSpinGroup();
		spn_report_data = (Spinner) v.findViewById(R.id.spn_report_data);
		edt_report_date = (EditText) v.findViewById(R.id.edt_report_date);
		edt_bill_cycle = (EditText) v.findViewById(R.id.edt_bill_cycle);
		edtObjectCus = (EditText) v.findViewById(R.id.edtObjectCus);

        TextView tvCusName = (TextView) v.findViewById(R.id.tvCusName);
        TextView tvBeginCus = (TextView) v.findViewById(R.id.tvBeginCus);
        TextView tvBeginMoney = (TextView) v.findViewById(R.id.tvBeginMoney);
        TextView tvBeginCusCharged = (TextView) v.findViewById(R.id.tvBeginCusCharged);
        TextView tvBeginMoneyCharged = (TextView) v
                .findViewById(R.id.tvBeginMoneyCharged);
        TextView tvCusCancel = (TextView) v.findViewById(R.id.tvCusCancel);
        TextView tvMoneyCancel = (TextView) v.findViewById(R.id.tvMoneyCancel);
        TextView tvPercentCustommer = (TextView) v.findViewById(R.id.tvPercentCustommer);
        TextView tvPercentMoney = (TextView) v.findViewById(R.id.tvPercentMoney);
        TextView tvContractFormMngt = (TextView) v.findViewById(R.id.tvContractFormMngt);
        TextView tvContractFormMngtName = (TextView) v
                .findViewById(R.id.tvContractFormMngtName);

        Button imb_search_contract_payment = (Button) v
                .findViewById(R.id.imb_search_contract_payment);

		svInfoPayment = (ScrollView) v.findViewById(R.id.svInfoPayment);
		svInfoPayment.setVisibility(View.GONE);

		edtObjectCus.setOnClickListener(this);
		edt_report_date.setOnClickListener(editTextListener);
		edt_bill_cycle.setOnClickListener(this);
		imb_search_contract_payment.setOnClickListener(this);

		// AsyncGetContractFormMngtN1N6 asynctaskContractFotmMngt = new
		// AsyncGetContractFormMngtN1N6(pbCustomerObject, lstSpin.size(),
		// lstSpin.size() + Constant.PAGE_SIZE);
		// asynctaskContractFotmMngt.execute();
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {

//		case R.id.edt_report_date:
//
//			OnDateSetListener onDateSetListener2 = new OnDateSetListener() {
//				public void onDateSet(DatePicker view, int year, int month,
//						int day) {
//					String reportDate = day + "/" + (month + 1) + "/" + year;
//					edt_report_date.setText(reportDate);
//				}
//			};
//			Calendar calendar2 = Calendar.getInstance();
//			int year2 = calendar2.get(Calendar.YEAR);
//			int month2 = calendar2.get(Calendar.MONTH);
//			int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
//
//			DatePickerDialog pic2 = new DatePickerDialog(activity,
//					onDateSetListener2, year2, month2, day2);
//			pic2.setTitle(activity.getString(R.string.chon_ngay));
//			pic2.show();
//
//			break;
		case R.id.edt_bill_cycle:

			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					day = 1;
					String billCycle = day + "/" + (month + 1) + "/" + year;
					edt_bill_cycle.setText(billCycle);
				}
			};
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity,
					AlertDialog.THEME_HOLO_LIGHT,onDateSetListener, year, month, day);

			pic.getDatePicker()
					.findViewById(
							Resources.getSystem().getIdentifier("day", "id",
									"android")).setVisibility(View.GONE);

			pic.setTitle(activity.getString(R.string.chon_ngay));
			pic.show();

			break;

		case R.id.imb_search_contract_payment:
			if (CommonActivity.isNetworkConnected(activity)) {

				contractFormMngt = getListCodeContract();

				contractName = getListContractName();

				int pos = spn_report_data.getSelectedItemPosition();

				int reportData = getResources().getIntArray(
						R.array.payment_report_type_value)[pos];

				String reportDate = edt_report_date.getText().toString();

				String billCycle = edt_bill_cycle.getText().toString();

				if (reportDate.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.edt_report_date_select),
							getString(R.string.app_name)).show();
					break;
				}

				if (billCycle.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.edt_bill_cycle_select),
							getString(R.string.app_name)).show();
					break;
				}

				if (contractFormMngt == null || contractFormMngt.isEmpty()) {
					CommonActivity
							.createAlertDialog(
									activity,
									getString(R.string.message_not_input_customer_name_or_name),
									getString(R.string.app_name)).show();
					break;
				}

				AsyncContractReport asynctaskSearchContract = new AsyncContractReport(
						reportData, reportDate, billCycle);
				asynctaskSearchContract.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		case R.id.edtObjectCus:
//			showDialogListObjectCustommer();

			if(FragmentChooseContractFormMngt.hashMapContract != null){
				FragmentChooseContractFormMngt.hashMapContract = new HashMap<String, ContractFormMngtBean>();
			}
			FragmentChooseContractFormMngt fragment = new FragmentChooseContractFormMngt();
			fragment.setTargetFragment(this, REQUEST_N);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			
			
			break;

		default:
			break;
		}
	}

	private class AsyncContractReport extends
			AsyncTask<String, Void, List<ReportProgressBean>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		private final int reportData;
		private final String reportDate;
		private final String billCycle;

		public AsyncContractReport(int _reportData, String _reportDate,
				String _billCycle) {
			reportData = _reportData;
			reportDate = _reportDate;
			billCycle = _billCycle;

			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);
			this.progress.setMessage(activity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected List<ReportProgressBean> doInBackground(String... params) {
			return getReportProgress();
		}

		@Override
		protected void onPostExecute(List<ReportProgressBean> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {

				if (result != null && result.size() > 0) {

					ReportProgessAdapter adapter = new ReportProgessAdapter(
							activity, result);
					showDialogLoadMoreListView(adapter);

                    ReportProgressBean reportProgressBean = result.get(0);
					Log.d(Constant.TAG, "reportProgressBean : "
							+ reportProgressBean.toString());
					// update Ui

					// svInfoPayment.setVisibility(View.VISIBLE);
					// tvPercentCustommer.setText(reportProgressBean.getCustRate());
					// Double d = 0.00D;
					// try {
					// d =
					// Double.parseDouble(reportProgressBean.getChargeRate());
					// d = Math.round(d * 100) / 100.00D;
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// tvPercentMoney.setText(d.toString());
					// tvBeginMoney.setText(StringUtils.formatMoney(reportProgressBean.getChargeSta()));
					// tvBeginCusCharged.setText(StringUtils.formatMoney(reportProgressBean.getCustPayment()));
					// tvCusCancel.setText(StringUtils.formatMoney(reportProgressBean.getCustDebit()));
					// tvBeginCus.setText(reportProgressBean.getCustSta());
					// tvMoneyCancel.setText(StringUtils.formatMoney(reportProgressBean.getDebit()));
					// tvBeginMoneyCharged.setText(StringUtils.formatMoney(reportProgressBean.getPayment()));
					// tvCusName.setText(contractName);
					// tvContractFormMngt.setText(reportProgressBean.getContractFormMngt());
					// tvContractFormMngtName.setText(reportProgressBean.getContractFormMngtName());

				} else {
					svInfoPayment.setVisibility(View.GONE);
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.ko_co_dl_contract),
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

		private List<ReportProgressBean> getReportProgress() {
			List<ReportProgressBean> reportProgressBeans = new ArrayList<>();
			String original = "";
			try {

				/**
				 * @WebParam(name = "staffCode", targetNamespace = "")
				 * @WebParam(name = "reportData", targetNamespace = "")
				 * @WebParam(name = "contractFormMngt", targetNamespace = "")
				 * @WebParam(name = "reportDate", targetNamespace = "")
				 * @WebParam(name = "billCycle", targetNamespace = "")
				 * @WebParam(name = "pageIndex", targetNamespace = "")
				 * @WebParam(name = "pageSize", targetNamespace = "")
				 */

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getReportProgress");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReportProgress>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<staffCode>TT_TESTER</staffCode>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");

				rawData.append("<reportData>").append(reportData).append("</reportData>");
				rawData.append("<contractFormMngt>").append(contractFormMngt).append("</contractFormMngt>");
				rawData.append("<reportDate>").append(reportDate).append("</reportDate>");
				rawData.append("<billCycle>").append(billCycle).append("</billCycle>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");

				Spin spin = (Spin)spn_selectgroup.getSelectedItem();
				rawData.append("<isGroup>" + spin.getId()
						+ "</isGroup>");
				
				rawData.append("</input>");
				rawData.append("</ws:getReportProgress>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getReportProgress");
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
					nodechild = doc
							.getElementsByTagName("lstReportProgressBeanServ");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReportProgressBean obj = new ReportProgressBean();

						/**
						 * <chargeRate>0.0</chargeRate>
						 * <chargeSta>319129</chargeSta>
						 * <custDebit>1.0</custDebit>
						 * <custPayment>0.0</custPayment>
						 * <custRate>0.0</custRate> <custSta>1</custSta>
						 * <debit>319129.0</debit> <payment>0.0</payment>
						 * <contractFormMngt>T0</contractFormMngt>
						 * <contractFormMngtName>N1 TT</contractFormMngtName>
						 * <staffCode>T054010100070021</staffCode>
						 **/

						obj.setChargeRate(parse.getValue(e1, "chargeRate"));
						obj.setChargeSta(parse.getValue(e1, "chargeSta"));
						obj.setCustDebit((parse.getValue(e1, "custDebit")));
						obj.setCustPayment(parse.getValue(e1, "custPayment"));
						obj.setCustRate(parse.getValue(e1, "custRate"));
						obj.setCustSta(parse.getValue(e1, "custSta"));
						obj.setDebit(parse.getValue(e1, "debit"));
						obj.setPayment(parse.getValue(e1, "payment"));

						obj.setContractFormMngt(parse.getValue(e1,
								"contractFormMngt"));
						obj.setContractFormMngtName(parse.getValue(e1,
								"contractFormMngtName"));

						obj.setContractName(contractName);

						reportProgressBeans.add(obj);
					}
				}

			} catch (Exception e) {
				Log.d(Constant.TAG, e.toString(), e);
			}
			return reportProgressBeans;
		}

	}

	private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = Integer.valueOf(buttonView.getTag().toString());
			Log.d(this.getClass().getSimpleName(),
					"onCheckedChanged position " + position + " isChecked "
							+ isChecked + " ==> " + lstSpin.toString());

			int numOfSelected = 0;

			for (Spin spin : lstSpin) {
				Log.d(this.getClass().getSimpleName(), "::::" + position
						+ " name " + spin.getName() + " id " + spin.getId());
				if (spin.getId() != null && spin.getId().equalsIgnoreCase("1")) {
					numOfSelected++;
				}
			}

            int MAX_SELECT_CUSTOMER_OBJECT = 3;
            if (isChecked && numOfSelected >= MAX_SELECT_CUSTOMER_OBJECT) {
				buttonView.setChecked(!isChecked);

				String message = String.format(
						getString(R.string.max_select_customer_object),
                        MAX_SELECT_CUSTOMER_OBJECT);
				CommonActivity.toast(activity, message);
			} else {
				Log.d("position :::>", position + "");
				lstSpin.get(position).setId(isChecked ? "1" : "0");
			}

			Log.d(this.getClass().getSimpleName(), "onCheckedChanged position "
					+ position + " AFTER ==> " + lstSpin.toString());
		}
	};

	private String getListName() {
		StringBuilder sb = new StringBuilder();

		for (Spin spin : lstSpin) {
			if (spin.getId() != null && spin.getId().equalsIgnoreCase("1")) {
				sb.append(spin.getName());
				sb.append(",");
			}
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	private String getListContractName() {
		StringBuilder sb = new StringBuilder();

		for (ContractFormMngtBean spin : lstContractForm) {
			if (spin.isCheck()) {
				sb.append(spin.getName());
				sb.append(",");
			}
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	private String getListCode() {
		StringBuilder sb = new StringBuilder();

		for (Spin spin : lstSpin) {
			if (spin.getId() != null && spin.getId().equalsIgnoreCase("1")) {
				sb.append(spin.getCode());
				sb.append(",");
			}
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private ListView lvObjectCustomer;

    private void showDialogListObjectCustommer() {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_customer_new);
		dialog.setCancelable(false);
		lvObjectCustomer = (ListView) dialog
				.findViewById(R.id.lvObjectCustomer);

		lvObjectCustomer
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Spin spin = (Spin) parent.getSelectedItem();
					}
				});

        ProgressBar pbCustomerObject = (ProgressBar) dialog
                .findViewById(R.id.pbCustomerObject);

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				edtObjectCus.setText(getListName());
			}
		});

		dialog.show();

		if (lstSpin == null || lstSpin.isEmpty()) {
			pbCustomerObject.setVisibility(View.VISIBLE);
			AsyncGetContractFormMngtN1N6 asynctaskContractFotmMngt = new AsyncGetContractFormMngtN1N6(
                    pbCustomerObject, lstSpin.size(), lstSpin.size()
							+ Constant.PAGE_SIZE);
			asynctaskContractFotmMngt.execute();
		} else {
			pbCustomerObject.setVisibility(View.GONE);

			// if(customerObjectAdapter != null){
			// customerObjectAdapter.notifyDataSetChanged();
			// }else{
			customerObjectAdapter = new CustomerObjectNewAdapter(activity,
					lstSpin, onCheckedChangeListener);
			lvObjectCustomer.setAdapter(customerObjectAdapter);
			// }

		}
	}

	private Dialog dialogLoadMore;

    private void showDialogLoadMoreListView(
			ArrayAdapter<ReportProgressBean> adapter) {
		dialogLoadMore = new Dialog(activity);
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.dialog_listview);
		dialogLoadMore.setCancelable(true);
		dialogLoadMore.setTitle(getString(R.string.contract_report));

        LoadMoreListView loadMoreListView = (LoadMoreListView) dialogLoadMore
                .findViewById(R.id.loadMoreListView);
		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnCancel);

		loadMoreListView.setAdapter(adapter);

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogLoadMore.dismiss();
			}
		});

		dialogLoadMore.show();
	}

	private class AsyncGetContractFormMngtN1N6 extends
			AsyncTask<String, Void, List<Spin>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressBar pbCustomerObject;

		private final int pageIndex;
		private final int pageSize;

		public AsyncGetContractFormMngtN1N6(ProgressBar pbCustomerObject,
				int _pageIndex, int _pageSize) {
			this.pageIndex = _pageIndex;
			this.pageSize = _pageSize;

			this.pbCustomerObject = pbCustomerObject;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected List<Spin> doInBackground(String... params) {

			return getContractFormMngtN1N6();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			if (pbCustomerObject != null) {
				pbCustomerObject.setVisibility(View.GONE);
			}
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					// them danh sach
					lstSpin.addAll(result);
					customerObjectAdapter = new CustomerObjectNewAdapter(
							activity, lstSpin, onCheckedChangeListener);
					lvObjectCustomer.setAdapter(customerObjectAdapter);
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
							getActivity(), description, activity.getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private List<Spin> getContractFormMngtN1N6Fix() {
			List<Spin> list = new ArrayList<>();

			String[] object_customer_code = getResources().getStringArray(
					R.array.object_customer_code);
			String[] object_customer_name = getResources().getStringArray(
					R.array.object_customer_name);

			for (int i = 0; i < object_customer_code.length; i++) {
				Spin obj = new Spin();
				obj.setCode(object_customer_code[i]);
				obj.setName(object_customer_name[i]);

				list.add(obj);
			}

			errorCode = "0";
			description = "";

			return list;
		}

		private List<Spin> getContractFormMngtN1N6() {
			List<Spin> list = new ArrayList<>();
			String original = "";
			try {

				list = new CacheDatabaseManager(getActivity())
						.getListCustTypePaymentInCache("N1N6");
				if (list != null && !list.isEmpty()) {
					errorCode = "0";
					return list;
				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getContractFormMngtN1N6");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getContractFormMngtN1N6>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<pageIndex>").append(pageIndex).append("</pageIndex>");
				rawData.append("<pageSize>").append(pageSize).append("</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:getContractFormMngtN1N6>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getContractFormMngtN1N6");
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
					nodechild = doc
							.getElementsByTagName("lstContractFormMngtBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin obj = new Spin();
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));

						list.add(obj);
					}
				}
			} catch (Exception e) {
				Log.d(this.getClass().getSimpleName(), e.toString());
			}
			new CacheDatabaseManager(getActivity()).insertCusTypePaymentCache(
					"N1N6", list);
			return list;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
	private String getListCodeContract() {
		StringBuilder sb = new StringBuilder();

		for (ContractFormMngtBean spin : lstContractForm) {
			if (spin.isCheck()) {
				sb.append(spin.getCode());
				sb.append(",");
			}
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	private void initSpinGroup(){
		arrSelectGroup = new ArrayList<Spin>();
		arrSelectGroup.add(new Spin("true", getActivity().getString(R.string.nhomquanly)));
		arrSelectGroup.add(new Spin("false", getActivity().getString(R.string.nhomtong)));
		Utils.setDataSpinner(getActivity(), arrSelectGroup, spn_selectgroup);
		
		
	}
	private View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText()
						.toString(), "dd/MM/yyyy");

				cal.setTime(date);

			}
			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity,AlertDialog.THEME_HOLO_LIGHT,
					datePickerListener, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			pic.getDatePicker().setTag(view);
			pic.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
						+ selectedYear);

				int id = editText.getId();

				Calendar cal = Calendar.getInstance();
				cal.set(selectedYear, selectedMonth, selectedDay);
				Date date = cal.getTime();

				// if(edit_ngaycap != null && id == edit_ngaycap.getId()) {
				// issueDate = date;
				// } else if(edit_ngaycapdd != null && editText.getId() ==
				// edit_ngaycapdd.getId()) {
				// issueDateATT = date;
				// } else if(edit_ngaysinhCus != null && editText.getId() ==
				// edit_ngaysinhCus.getId()) {
				// birthDateATT = date;
				// } else if(edit_ngaysinh != null && editText.getId() ==
				// edit_ngaysinh.getId()) {
				// birthDateCus = date;
				// } else if(editngayhethan != null && editText.getId() ==
				// editngayhethan.getId()) {
				// // ngay het han
				// }

			}
		}
	};
	
}
