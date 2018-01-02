package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListRequestAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListRequestAdapter.OnCancelRequest;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetReasonAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoConnectionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.MpServiceFeeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Reason;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RequestBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.StatusBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubStockModelRelReqBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
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
public class FragmentManageRequest extends Fragment implements OnClickListener,
		OnItemClickListener, OnCancelRequest {
	String dateconvert;
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
	EditText tvFromDate;
	EditText tvToDate;
	private final String tag = FragmentManageRequest.class.getName();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Button btn_search;
	Spinner spinner_service, spinner_status;
	EditText edit_isdnacount;
	EditText edit_mayeucau;
	// init input for webservice
	String isdnOrAccount;
	String serviceType;
	String subRequestId;
	private String fromDate = "";
	private String toDate = "";

	ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
	public ArrayList<RequestBeans> arrRequestBeans = new ArrayList<RequestBeans>();
	public GetListRequestAdapter adaRequestAdapter;
	RequestBeans requestBeans = new RequestBeans();
	public RequestBeans requestBeansItem = new RequestBeans();
	public ListView lvRequest;

	ArrayList<StatusBeans> arrStatusBeans = new ArrayList<StatusBeans>();

	String telecomServiceId = "";
	String typeStatus = "";

	String account = "";
	public static FragmentManageRequest fragInstance = null;

	private Dialog dialogSelectReason;
	private String reasonId = "";

	private GetReasonAdapter adapReasonAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragInstance = this;
		View mView = inflater.inflate(
				R.layout.connection_layout_manager_request, container, false);
		unitView(mView);
		return mView;
	}

	@Override
	public void onResume() {
		Log.e(tag, "onResume");
		if (adaRequestAdapter != null && arrRequestBeans.size() > 0) {
			adaRequestAdapter = new GetListRequestAdapter(arrRequestBeans,
					getActivity(), FragmentManageRequest.this);
			lvRequest.setAdapter(adaRequestAdapter);
			adaRequestAdapter.notifyDataSetChanged();
		}
		addActionBar();
		super.onResume();

	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.manager_customer_connecttion));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edtFromDate:
			DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, fromYear, fromMonth,
					fromDay);
			fromDateDialog.show();
			break;
		case R.id.edtToDate:
			DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
					toDatePickerListener, toYear, toMonth, toDay);
			toDateDialog.show();
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_search:

			// servicetype
			if (serviceType != null && !serviceType.isEmpty()) {
				checkSearch();
			} else {
				if (edit_isdnacount.getText().toString() != null
						&& !edit_isdnacount.getText().toString().isEmpty()) {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkserviceType),
							Toast.LENGTH_SHORT).show();
				} else {
					checkSearch();
				}
			}
			break;
		default:
			break;
		}

	}

	public void checkSearch() {
		if (dateFrom != null && dateTo != null) {
			if (dateFrom.after(dateTo)) {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.checktimeupdatejob),
						Toast.LENGTH_SHORT).show();
			} else if (dateFrom.compareTo(dateTo) == 0) {
				if (CommonActivity.isNetworkConnected(getActivity()) == true) {
					if (arrRequestBeans.size() > 0 && adaRequestAdapter != null) {
						arrRequestBeans.clear();
						adaRequestAdapter.notifyDataSetChanged();
					}

					GetListRequestAsyn getListRequestAsyn = new GetListRequestAsyn(
							getActivity());
					getListRequestAsyn.execute();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else if (dateFrom.before(dateTo)) {

				if (CommonActivity.isNetworkConnected(getActivity()) == true) {

					long day = getDateDiff(dateFrom, dateTo, TimeUnit.MINUTES);
					Log.d("dayyyyyyyyyyyy", "" + day);
					if (day <= 30) {
						if (arrRequestBeans.size() > 0
								&& adaRequestAdapter != null) {
							arrRequestBeans.clear();
							adaRequestAdapter.notifyDataSetChanged();
						}

						GetListRequestAsyn getListRequestAsyn = new GetListRequestAsyn(
								getActivity());
						getListRequestAsyn.execute();
					} else {
						if (arrRequestBeans.size() > 0
								&& adaRequestAdapter != null) {
							arrRequestBeans.clear();
							adaRequestAdapter.notifyDataSetChanged();
						}
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.checkrequest30),
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}
	}

	public void unitView(View view) {
		tvFromDate = (EditText) view.findViewById(R.id.edtFromDate);
		tvFromDate.setOnClickListener(this);
		tvToDate = (EditText) view.findViewById(R.id.edtToDate);
		tvToDate.setOnClickListener(this);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		spinner_service = (Spinner) view.findViewById(R.id.spinner_service);
		edit_isdnacount = (EditText) view.findViewById(R.id.edit_isdnacount);
		edit_mayeucau = (EditText) view.findViewById(R.id.edit_mayeucau);
		spinner_status = (Spinner) view.findViewById(R.id.spinner_status);
		lvRequest = (ListView) view.findViewById(R.id.lisrequest);
		lvRequest.setOnItemClickListener(this);
		isdnOrAccount = edit_isdnacount.getText().toString();
		Log.d("isdnOrAccount", isdnOrAccount);
		subRequestId = edit_mayeucau.getText().toString();
		Log.d("subRequestId", subRequestId);
		initValue();
		initTelecomService();
		if (arrStatusBeans != null && arrRequestBeans.size() > 0) {
			arrStatusBeans.clear();
		}

		initSpinerStatus();
		spinner_service.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 > 0) {
					serviceType = arrTelecomServiceBeans.get(arg2)
							.getServiceAlias();
					Log.d("serviceType", serviceType);

					telecomServiceId = arrTelecomServiceBeans.get(arg2)
							.getTelecomServiceId();
					Log.d("telecomServiceId", telecomServiceId);
				} else {
					serviceType = "";
					telecomServiceId = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinner_status.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 > 0) {
					typeStatus = arrStatusBeans.get(arg2).getTypeStatus();
					Log.d("typeStatus", typeStatus);
				} else {
					typeStatus = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	// ==== add list status =====
	private void addStatus() {
		arrStatusBeans.add(0, new StatusBeans("", getActivity().getResources()
				.getString(R.string.selectstatus)));
		// arrStatusBeans.add(new StatusBeans("1", getActivity().getResources()
		// .getString(R.string.status1)));
		arrStatusBeans.add(new StatusBeans("2", getActivity().getResources()
				.getString(R.string.status2)));
		arrStatusBeans.add(new StatusBeans("3", getActivity().getResources()
				.getString(R.string.status3)));
		arrStatusBeans.add(new StatusBeans("4", getActivity().getResources()
				.getString(R.string.status4)));

	}

	// init spiner status
	private void initSpinerStatus() {
		addStatus();
		if (arrStatusBeans != null && arrStatusBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_dropdown_item_1line,
					android.R.id.text1);
			for (StatusBeans statusBeans : arrStatusBeans) {
				adapter.add(statusBeans.getNameStatus());
			}
			spinner_status.setAdapter(adapter);
		}

	}

	// init typepaper
	private void initTelecomService() {
		try {

			GetServiceDal dal = new GetServiceDal(getActivity());
			dal.open();
			arrTelecomServiceBeans = dal.getlisTelecomServiceBeans();
			dal.close();
			TelecomServiceBeans teleServiceBeans = new TelecomServiceBeans();
			teleServiceBeans.setTele_service_name(getActivity().getResources()
					.getString(R.string.chondichvu));
			arrTelecomServiceBeans.add(0, teleServiceBeans);
			if (arrTelecomServiceBeans != null
					&& arrTelecomServiceBeans.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					adapter.add(telecomServiceBeans.getTele_service_name());
				}
				spinner_service.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		}
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

		dateFromString = new StringBuilder().append(fromYear).append("-")
				.append(fromMonth + 1).append("-").append(fromDay).toString();
		Log.d("dateFromString", dateFromString);
		try {

			dateFrom = sdf.parse(dateFromString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH);
		toDay = cal.get(Calendar.DAY_OF_MONTH);
		dateToString = new StringBuilder().append(toYear).append("-")
				.append(toMonth + 1).append("-").append(toDay).toString();
		Log.d("dateToString", dateToString);
		try {
			dateTo = sdf.parse(dateToString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tvToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		toDate = tvToDate.getText().toString();
		Log.d("toDateInit", toDate);
	}

	private DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

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
			dateFromString = new StringBuilder().append(fromYear).append("-")
					.append(fromMonth + 1).append("-").append(fromDay)
					.toString();
			Log.d("dateFromString", dateFromString);
			try {
				dateFrom = sdf.parse(dateFromString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvFromDate.setText(strDate);
		}
	};
	private DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

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
			dateToString = new StringBuilder().append(toYear).append("-")
					.append(toMonth + 1).append("-").append(toDay).toString();
			Log.d("dateToString", dateToString);
			try {
				dateTo = sdf.parse(dateToString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvToDate.setText(strDate);
		}
	};

	// ===== get distance day < 30 day
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return diffInMillies / (24 * 60 * 60 * 1000);
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	// ==================ws get detail request=========================
	private class GetDetailRequestAsyn extends
			AsyncTask<String, Void, InfoConnectionBeans> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetDetailRequestAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected InfoConnectionBeans doInBackground(String... arg0) {
			return getDetailRequestBean(arg0[0]);
		}

		@Override
		protected void onPostExecute(InfoConnectionBeans result) {
			progress.dismiss();
			// ======== succces =================
			if (errorCode.equals("0")) {
				if (result.getIdRequest() != null
						|| result.getIdContractNo() != null) {

					FragmentManageConnect fragmentManageConnect = new FragmentManageConnect();
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("ConnectKey", result);
					mBundle.putString("statusKey",
							requestBeansItem.getStausRequest());
					fragmentManageConnect.setArguments(mBundle);
					ReplaceFragment.replaceFragment(getActivity(),
							fragmentManageConnect, true);
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
					if (description == null && description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private InfoConnectionBeans getDetailRequestBean(String idRequest) {
			InfoConnectionBeans inConnectionBeans = new InfoConnectionBeans();
			ArrayList<MpServiceFeeBeans> lisMpServiceFeeBeans = new ArrayList<MpServiceFeeBeans>();
			ArrayList<SubStockModelRelReqBeans> lisSubStockModelRelReqBeans = new ArrayList<SubStockModelRelReqBeans>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewDetailRequest");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewDetailRequest>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("token", Session.getToken());
				rawData.append(input.buildXML(param));
				rawData.append("<subReqId>" + idRequest);
				rawData.append("</subReqId>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:viewDetailRequest>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_viewDetailRequest");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// =======parse xml =================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nlsubRequest = null;
				NodeList nlstockModelRelReqs = null;
				NodeList nlmpServiceFeeList = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nlsubRequest = doc.getElementsByTagName("subRequest");
					for (int j = 0; j < nlsubRequest.getLength(); j++) {
						Element e1 = (Element) nlsubRequest.item(j);
						String subReqId = parse.getValue(e1, "subReqId");
						Log.d("subReqId", subReqId);
						inConnectionBeans.setIdRequest(subReqId);
						String isdn = parse.getValue(e1, "isdn");
						Log.d("isdn", isdn);
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						if (account != null && !account.isEmpty()) {
							inConnectionBeans.setIsdnOrAccount(account);
						} else {
							inConnectionBeans.setIsdnOrAccount(isdn);
						}
						String contractNo = parse.getValue(e1, "contractNo");
						Log.d("contractNo", contractNo);
						inConnectionBeans.setIdContractNo(contractNo);
						String cusId = parse.getValue(e1, "custId");
						inConnectionBeans.setCustId(cusId);
						String serviceType = parse.getValue(e1, "serviceType");
						
						inConnectionBeans.setServiceType(serviceType);
						
						if (serviceType != null && !serviceType.isEmpty()) {
							try {
								GetServiceDal getServiceDal = new GetServiceDal(
										getActivity());
								getServiceDal.open();
								String serviceName = getServiceDal
										.getDescription(serviceType);
								Log.d("serviceName", serviceName);
								inConnectionBeans.setServiceName(serviceName);
								getServiceDal.close();
							} catch (Exception e) {
								Log.d("getlistRequest", e.toString());
							}
						}
						String pakageCharge = parse.getValue(e1, "productCode");
						Log.d("pakageCharge", pakageCharge);
						inConnectionBeans.setPakageCharge(pakageCharge);

						String reasonId = parse.getValue(e1, "regReasonId");
						inConnectionBeans.setRegReasonId(reasonId);
						
						// parse mpServiceFeeList
						nlmpServiceFeeList = doc
								.getElementsByTagName("mpServiceFeeList");
						for (int k = 0; k < nlmpServiceFeeList.getLength(); k++) {
							Element e3 = (Element) nlmpServiceFeeList.item(k);
							MpServiceFeeBeans mpServiceFeeBeans = new MpServiceFeeBeans();
							String amount = parse.getValue(e3, "amount");
							Log.d("amount", amount);

							mpServiceFeeBeans.setAmount(amount);
							String feeCode = parse.getValue(e3, "feeCode");
							Log.d("feeCode", feeCode);
							mpServiceFeeBeans.setFeeCode(feeCode);

							String feeName = parse.getValue(e3, "feeName");
							Log.d("feeName", feeName);
							mpServiceFeeBeans.setFeeName(feeName);

							String priceId = parse.getValue(e3, "priceId");
							Log.d("priceId", priceId);
							mpServiceFeeBeans.setPriceId(priceId);

							String realStep = parse.getValue(e3, "realStep");
							Log.d("realStep", realStep);
							mpServiceFeeBeans.setRealStep(realStep);

							String revenueObj = parse
									.getValue(e3, "revenueObj");
							Log.d("revenueObj", revenueObj);
							mpServiceFeeBeans.setRevenueObj(revenueObj);

							String saleServiceId = parse.getValue(e3,
									"saleServiceId");
							Log.d("saleServiceId", saleServiceId);
							mpServiceFeeBeans.setSaleServiceId(saleServiceId);

							String stockModelId = parse.getValue(e3,
									"stockModelId");
							Log.d("stockModelId", stockModelId);
							mpServiceFeeBeans.setStockModelId(stockModelId);

							String vat = parse.getValue(e3, "vat");
							Log.d("vat", vat);
							mpServiceFeeBeans.setVat(vat);

							lisMpServiceFeeBeans.add(mpServiceFeeBeans);
						}
						// ==========add setLisMpServiceFeeBeans ===============
						inConnectionBeans
								.setLisMpServiceFeeBeans(lisMpServiceFeeBeans);

						// parse stockModelRelReqs
						nlstockModelRelReqs = doc
								.getElementsByTagName("stockModelRelReqs");
						for (int m = 0; m < nlstockModelRelReqs.getLength(); m++) {
							Element e4 = (Element) nlstockModelRelReqs.item(m);
							SubStockModelRelReqBeans subModelRelReqBeans = new SubStockModelRelReqBeans();
							String reclaimAmount = parse.getValue(e4,
									"reclaimAmount");
							Log.d("reclaimAmount", reclaimAmount);
							subModelRelReqBeans.setReclaimAmount(reclaimAmount);

							String reclaimCommitmentCode = parse.getValue(e4,
									"reclaimCommitmentCode");
							Log.d("reclaimCommitmentCode",
									reclaimCommitmentCode);
							subModelRelReqBeans
									.setReclaimCommitmentCode(reclaimCommitmentCode);

							String reclaimCommitmentName = parse.getValue(e4,
									"reclaimCommitmentName");
							Log.d("reclaimCommitmentName",
									reclaimCommitmentName);
							subModelRelReqBeans
									.setReclaimCommitmentName(reclaimCommitmentName);

							String serial = parse.getValue(e4, "serial");
							Log.d("serial", serial);
							subModelRelReqBeans.setSerial(serial);

							String status = parse.getValue(e4, "status");
							Log.d("status", status);
							subModelRelReqBeans.setStatus(status);

							String stockModelId = parse.getValue(e4,
									"stockModelId");
							Log.d("stockModelId", stockModelId);
							subModelRelReqBeans.setStockModelId(stockModelId);

							String stockModelName = parse.getValue(e4,
									"stockModelName");
							Log.d("stockModelName", stockModelName);
							subModelRelReqBeans
									.setStockModelName(stockModelName);

							String stockTypeId = parse.getValue(e4,
									"stockTypeId");
							Log.d("stockTypeId", stockTypeId);
							subModelRelReqBeans.setStockTypeId(stockTypeId);

							String stockTypeName = parse.getValue(e4,
									"stockTypeName");
							Log.d("stockTypeName", stockTypeName);
							subModelRelReqBeans.setStockTypeName(stockTypeName);

							String subId = parse.getValue(e4, "subId");
							Log.d("subId", subId);
							subModelRelReqBeans.setSubId(subId);

							String subStockModelRelId = parse.getValue(e4,
									"subStockModelRelId");
							Log.d("subStockModelRelId", subStockModelRelId);
							subModelRelReqBeans
									.setSubStockModelRelId(subStockModelRelId);

							lisSubStockModelRelReqBeans
									.add(subModelRelReqBeans);
						}
						// ===============add list product ==========
						inConnectionBeans
								.setLisSubStockModelRelReqBeans(lisSubStockModelRelReqBeans);
					}
				}

			} catch (Exception e) {
				Log.d("getDetailRequestBean", e.toString());
			}

			return inConnectionBeans;
		}

	}

	private void showDiaLogSelectReason(final ArrayList<Reason> result) {

		dialogSelectReason = new Dialog(getActivity());

		dialogSelectReason.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogSelectReason
				.setContentView(R.layout.connection_layout_select_reason);

		ListView lvReason = (ListView) dialogSelectReason
				.findViewById(R.id.lstreason);

		adapReasonAdapter = new GetReasonAdapter(result, getActivity());
		lvReason.setAdapter(adapReasonAdapter);

		lvReason.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				reasonId = result.get(arg2).getReasonId();
				String name = result.get(arg2).getName();
				// TODO XU LY CHO NGAY GOI WS DELETE
				CommonActivity.createDialog(
						getActivity(),
						getResources().getString(R.string.iscancelReq) + " "
								+ reBeans.getIdRequest() + " "
								+ getResources().getString(R.string.voilydo)
								+ " " + reasonId + "_" + name + "?",
						getResources().getString(R.string.app_name),
						getResources().getString(R.string.xacnhanReq),
						getResources().getString(R.string.boquaReq),
						cancelRequest, null).show();

			}
		});

		Button btncancel = (Button) dialogSelectReason
				.findViewById(R.id.btnhuy);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogSelectReason.dismiss();
			}
		});

		dialogSelectReason.show();
	}

	// ws get list reason
	private class GetListReasonAsyn extends
			AsyncTask<String, Void, ArrayList<Reason>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListReasonAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<Reason> doInBackground(String... arg0) {
			return getListReason(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Reason> result) {
			super.onPostExecute(result);

			progress.dismiss();

			if ("0".equals(errorCode)) {

				if (result.size() > 0) {

					showDiaLogSelectReason(result);

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.no_data),
							getResources().getString(R.string.app_name));
					dialog.show();

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
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}

			}

		}

		private ArrayList<Reason> getListReason(String serviceType) {
			ArrayList<Reason> arrReasons = new ArrayList<Reason>();
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListReason");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReason>");
				rawData.append("<input>");
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("token", Session.getToken());
				rawData.append(input.buildXML(param));
				rawData.append("<serviceType>" + serviceType);
				rawData.append("</serviceType>");
				rawData.append("</input>");
				rawData.append("</ws:getListReason>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReason");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);

					nodechild = doc.getElementsByTagName("lstReason");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Reason reason = new Reason();

						String name = parse.getValue(e1, "name");
						reason.setName(name);
						String reasonId = parse.getValue(e1, "reasonId");
						reason.setReasonId(reasonId);

						arrReasons.add(reason);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrReasons;
		}

	}

	// ws get list request
	private class GetListRequestAsyn extends
			AsyncTask<Void, Void, ArrayList<RequestBeans>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListRequestAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<RequestBeans> doInBackground(Void... arg0) {
			return getlistRequest();
		}

		@Override
		protected void onPostExecute(ArrayList<RequestBeans> result) {
			CommonActivity.hideKeyboard(edit_isdnacount, context);
			CommonActivity.hideKeyboard(edit_mayeucau, context);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					// add list request
					arrRequestBeans = result;
					adaRequestAdapter = new GetListRequestAdapter(
							arrRequestBeans, getActivity(),
							FragmentManageRequest.this);
					lvRequest.setAdapter(adaRequestAdapter);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.no_data),
							getResources().getString(R.string.app_name));
					dialog.show();
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
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<RequestBeans> getlistRequest() {
			ArrayList<RequestBeans> lisRequestBeans = new ArrayList<RequestBeans>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListSubRequest");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListSubRequest>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("token", Session.getToken());
				param.put("fromDate", tvFromDate.getText().toString());
				param.put("toDate", tvToDate.getText().toString());

				rawData.append(input.buildXML(param));

				if (serviceType != null && !serviceType.isEmpty()) {
					rawData.append("<serviceType>" + serviceType);
					rawData.append("</serviceType>");
				}
				if (typeStatus != null && !typeStatus.isEmpty()) {
					rawData.append("<status>" + typeStatus);
					rawData.append("</status>");
				}
				if (edit_isdnacount.getText().toString() != null
						&& !edit_isdnacount.getText().toString().isEmpty()) {

					rawData.append("<account>"
							+ edit_isdnacount.getText().toString());
					rawData.append("</account>");
				}
				if (edit_mayeucau.getText().toString() != null
						&& !edit_mayeucau.getText().toString().isEmpty()) {
					rawData.append("<subReqId>"
							+ edit_mayeucau.getText().toString());
					rawData.append("</subReqId>");
				}
				rawData.append("<sourceCode>" + "3");
				rawData.append("</sourceCode>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListSubRequest>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListSubRequest");
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
					nodechild = doc.getElementsByTagName("lstSubRequest");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						RequestBeans requestBeans = new RequestBeans();
						String requestId = parse.getValue(e1, "subReqId");
						Log.d("requestId", requestId);
						requestBeans.setIdRequest(requestId);
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						requestBeans.setAccount(account);
						String reasonId = parse.getValue(e1, "regReasonId");
						requestBeans.setRegReasonId(reasonId);
						String dateRequest = parse.getValue(e1, "createDate");
						if (dateRequest != null && dateRequest.length() >= 10) {
							dateconvert = dateRequest.subSequence(0, 10)
									.toString();
							Log.d("dateconvert", dateconvert);
							String[] splitDate = dateconvert.split("-", 3);
							if (splitDate.length == 3) {
								String convertdate = splitDate[2] + "/"
										+ splitDate[1] + "/" + splitDate[0];
								Log.d("convertdate", convertdate);
								requestBeans.setDateRequest(convertdate);
							}
						}
						String serviveType = parse.getValue(e1, "serviceType");
						requestBeans.setServiceType(serviveType);
						if (serviveType != null && !serviveType.isEmpty()) {
							try {
								GetServiceDal getServiceDal = new GetServiceDal(
										getActivity());
								getServiceDal.open();
								String serviceName = getServiceDal
										.getDescription(serviveType);
								Log.d("serviceName", serviceName);
								requestBeans.setServiceRequest(serviceName);

								String telecomserviceId = getServiceDal
										.getTelecomserviceId(serviveType);
								requestBeans
										.setTelecomServiceId(telecomserviceId);

								getServiceDal.close();
							} catch (Exception e) {
								Log.d("getlistRequest", e.toString());
							}
						}

						String actionProfileId = parse.getValue(e1,
								"actionProfileId");
						requestBeans.setActionProfileId(actionProfileId);

						String actionProfileStatus = parse.getValue(e1,
								"actionProfileStatus");
						requestBeans
								.setActionProfileStatus(actionProfileStatus);

						String status = parse.getValue(e1, "status");
						switch (Integer.parseInt(status)) {
						case 1:
							requestBeans
									.setStausRequest(getActivity()
											.getResources().getString(
													R.string.status1));
							break;
						case 2:
							requestBeans
									.setStausRequest(getActivity()
											.getResources().getString(
													R.string.status2));
							break;
						case 3:
							requestBeans
									.setStausRequest(getActivity()
											.getResources().getString(
													R.string.status3));
							break;
						case 4:
							requestBeans
									.setStausRequest(getActivity()
											.getResources().getString(
													R.string.status4));
							break;
						default:
							break;
						}

						if (requestBeans.getStausRequest().equals(
								getActivity().getResources().getString(
										R.string.status4))
								|| requestBeans.getStausRequest().equals(
										getActivity().getResources().getString(
												R.string.status2))
								|| requestBeans.getStausRequest().equals(
										getActivity().getResources().getString(
												R.string.status3))) {
							lisRequestBeans.add(requestBeans);
						}

					}
				}

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}

			return lisRequestBeans;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		requestBeansItem = arrRequestBeans.get(arg2);
		Log.d("idrequest", requestBeansItem.getIdRequest());
		if (requestBeansItem.getStausRequest().equals(
				getActivity().getResources().getString(R.string.status4))
		// || requestBeansItem.getStausRequest().equals(
		// getActivity().getResources()
		// .getString(R.string.status3))
		) {
			CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checkrequest),
					getActivity().getResources().getString(R.string.app_name))
					.show();

		} else {
			if (CommonActivity.isNetworkConnected(getActivity()) == true) {
				GetDetailRequestAsyn getDetailRequestAsyn = new GetDetailRequestAsyn(
						getActivity());
				getDetailRequestAsyn.execute(requestBeansItem.getIdRequest());
			} else {
				CommonActivity.createAlertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.errorNetwork),
						getActivity().getResources().getString(
								R.string.app_name)).show();
			}
		}

	}

	private RequestBeans reBeans = new RequestBeans();

	@Override
	public void onCancelRequest(RequestBeans requestBeans) {
		reBeans = requestBeans;
		// TODO GOI WS LAY CHI TIET

		GetListReasonAsyn getListReasonAsyn = new GetListReasonAsyn(
				getActivity());
		getListReasonAsyn.execute(reBeans.getServiceType());

	}

	// ws cancel request
	private class CancelRequestAsyn extends AsyncTask<String, Void, String> {
		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CancelRequestAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.canceling));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return sendCancelRequest(arg0[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (dialogSelectReason != null) {
					dialogSelectReason.cancel();
				}

				// check success remove item old
				if (arrRequestBeans != null
						&& arrRequestBeans.size() > 0
						&& FragmentManageRequest.fragInstance.adaRequestAdapter != null) {
					CommonActivity
							.createAlertDialog(
									getActivity(),
									context.getResources().getString(
											R.string.cancelReSucess),
									context.getResources().getString(
											R.string.app_name),
									cancelRequestClink).show();
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
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private String sendCancelRequest(String requestId) {
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteSubRequest");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteSubRequest>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("token", Session.getToken());
				param.put("subReqId", requestId);
				param.put("deleteReasonId", reasonId);
				rawData.append(input.buildXML(param));
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:deleteSubRequest>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_deleteSubRequest");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}
			} catch (Exception e) {
				Log.d("sendCancelRequest", e.toString());
			}

			return errorCode;
		}

	}

	OnClickListener cancelRequestClink = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			arrRequestBeans.remove(reBeans);
			adaRequestAdapter = new GetListRequestAdapter(arrRequestBeans,
					getActivity(), FragmentManageRequest.this);
			lvRequest
					.setAdapter(FragmentManageRequest.fragInstance.adaRequestAdapter);
			adaRequestAdapter.notifyDataSetChanged();
		}
	};

	OnClickListener cancelRequest = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (CommonActivity.isNetworkConnected(getActivity()) == true) {
				CancelRequestAsyn cancelRequestAsyn = new CancelRequestAsyn(
						getActivity());
				cancelRequestAsyn.execute(reBeans.getIdRequest());
			}

		}
	};

}
