package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.charge.dal.CycleDal;
import com.viettel.bss.viettelpos.v4.charge.dal.GetStaffcodeDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeKythuOJ;
import com.viettel.bss.viettelpos.v4.charge.object.CycleObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.sale.business.TelecomServiceBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FragmentChargeRe extends FragmentCommon {
	private Spinner spinnerKythu;
	private Spinner spinnerArea;
    private Spinner spinnerService;
    private Spinner spinnerPeriod;
	private Button btnSearch;
	private EditText editISDN;
	private Spinner spinnerEmployee;
	private static final String TAG = "FragmentChargeRe";
	private String staffid = "";
	// list telecom service
    private ArrayList<TelecomServiceObject> lstService = null;
	private ArrayList<AreaObj> lstAreaObjs = new ArrayList<>();
	private ArrayList<CycleObj> lisCycleObjs = new ArrayList<>();
	private final ArrayList<ChargeKythuOJ> arrChargeKythuOJs = new ArrayList<>();
	private ArrayList<ChargeEmployeeOJ> arrChargeEmployeeOJs = new ArrayList<>();
	// define areacode, telecomservice , billcyclefrom
    private String areaCodeRe = "";
	private String telecomServiceRe = "";
	private String billCycleFromRe = "";
	private String isdnRe = "";
	private String aplliedCycleRe = "";
	private String kythuRe = "";

	@Override
	public void onResume() {
		CacheDataCharge.getInstance().setLisArrayListRe(null);
		CacheDataCharge.getInstance().setLisArrayList(null);
//		btnHome.setVisibility(View.GONE);
        setTitleActionBar(act.getString(R.string.chargeable) + " - "
                + act.getString(R.string.charge_re));
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_charge_re;
		act = getActivity();
		View mView = inflater.inflate(idLayout, container, false);
		unit(mView);
		return mView;
	}

	@Override
	public void unit(View v) {
		spinnerEmployee = (Spinner) v.findViewById(R.id.spiner_staff);
		spinnerArea = (Spinner) v.findViewById(R.id.spinner_area);
		spinnerService = (Spinner) v.findViewById(R.id.spinner_service);
		spinnerPeriod = (Spinner) v.findViewById(R.id.spinner_period);
		spinnerKythu = (Spinner) v.findViewById(R.id.spinner_kythu);
		editISDN = (EditText) v.findViewById(R.id.edit_isdn);
		btnSearch = (Button) v.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(this);

		initSpinerArea();
		initSpinerCycle();
		initSpinnerDichVu();
		initSpinerKyThu();
		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetEmployeAsyn getEmployeAsyn = new GetEmployeAsyn(getActivity());
			getEmployeAsyn.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork), getActivity()
							.getResources().getString(R.string.app_name));
			dialog.show();
		}

		// initSpinerEmploy();

		spinnerEmployee.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				staffid = arrChargeEmployeeOJs.get(position).getEmployeeId();
				Log.d("staffid", staffid);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				areaCodeRe = lstAreaObjs.get(position).getAreaCode();
				Log.d("areaCode", areaCodeRe);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// spiner telecom service
		spinnerService.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("possition_spiner", "" + position);

				telecomServiceRe = lstService.get(position).getCode();
				Log.d("telecomServiceRe", telecomServiceRe);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinnerPeriod.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				billCycleFromRe = lisCycleObjs.get(position).getBillCycleFrom();
				aplliedCycleRe = lisCycleObjs.get(position).getAppliedCyle();
				Log.d("billCycleFromRe", billCycleFromRe);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerKythu.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				kythuRe = arrChargeKythuOJs.get(position).getKythuhientai();
				Log.d("kythuRe", kythuRe);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	// init spiner employ
    private void initSpinerEmploy() {
		if (arrChargeEmployeeOJs != null && arrChargeEmployeeOJs.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ChargeEmployeeOJ employeeOJ : arrChargeEmployeeOJs) {
				adapter.add(employeeOJ.getNameEmpoyee());
			}
			spinnerEmployee.setAdapter(adapter);
		}
	}

	// init data for ky thu hientai

	private void fillDataKyThuHienTai() {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		Log.d("year", "" + year);
		int moth = cal.get(Calendar.MONTH) + 1;
		Log.d("month", "" + moth);
		// int day = cal.get(Calendar.DAY_OF_MONTH);
		// Log.d("day", ""+day);
		String kythuhientai = moth + "/" + year;
		Log.d("kythuhientai", kythuhientai);
		ChargeKythuOJ kythuOJ = new ChargeKythuOJ();
		kythuOJ.setKythuhientai(kythuhientai);
		arrChargeKythuOJs.add(kythuOJ);
	}

	// init data for ky thu sau
    private void fillDataKyThuSau() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		int year = cal.get(Calendar.YEAR);
		Log.d("year", "" + year);
		int moth = cal.get(Calendar.MONTH) + 1;
		Log.d("month", "" + moth);
		// int day = cal.get(Calendar.DAY_OF_MONTH);
		// Log.d("day", ""+day);
		String kythusau = moth + "/" + year;
		Log.d("kythusau", kythusau);
		ChargeKythuOJ kythuOJ = new ChargeKythuOJ();
		kythuOJ.setKythuhientai(kythusau);
		arrChargeKythuOJs.add(kythuOJ);
	}

	// fill data for spiner ky thu
	private void initSpinerKyThu() {
		fillDataKyThuHienTai();
		fillDataKyThuSau();
		if (arrChargeKythuOJs != null && !arrChargeKythuOJs.isEmpty()) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ChargeKythuOJ obKythuOJ : arrChargeKythuOJs) {
				adapter.add(obKythuOJ.getKythuhientai());
			}
			spinnerKythu.setAdapter(adapter);
		}
	}

	// fill data for spiner service
	private void initSpinnerDichVu() {
		try {
			lstService = TelecomServiceBusiness
					.getAllTelecomService(getActivity());
			if (lstService != null && !lstService.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (TelecomServiceObject telecomServiceObject : lstService) {
					adapter.add(telecomServiceObject.getName());
				}
				spinnerService.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// fill data for spiner Area
	private void initSpinerArea() {
		try {
			AreaDal dal = new AreaDal(getActivity());
			dal.open();
			lstAreaObjs = dal.getAllArea();
			lstAreaObjs.get(0).getAreaCode();
			dal.close();
			if (lstAreaObjs != null && !lstAreaObjs.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaObj areaObj : lstAreaObjs) {
					adapter.add(areaObj.getName());
				}
				spinnerArea.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	// fill data for spiner Cycle
	private void initSpinerCycle() {
		try {
			CycleDal dal = new CycleDal(getActivity());
			dal.open();
			lisCycleObjs = dal.getAllCycle();
			dal.close();
			if (lisCycleObjs != null && !lisCycleObjs.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (CycleObj cycleObj : lisCycleObjs) {
					adapter.add(cycleObj.getBillCycleFrom());
				}
				spinnerPeriod.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {

		case R.id.btn_search:
			isdnRe = editISDN.getText().toString();
			Log.d("aplliedCycleRe", aplliedCycleRe);
			Log.d("isdnRe", isdnRe);
			Log.d("areacodeRe", areaCodeRe);
			Log.d("telecomserviceRe", telecomServiceRe);
			Log.d("billCycleFromRe", billCycleFromRe);
			if (CommonActivity.isNetworkConnected(getActivity())) {
				SearchAsynRe searchAsyn = new SearchAsynRe(getActivity());
				searchAsyn.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

			return;
		default:
			break;
		}
		super.onClick(arg0);
	}

	// class asyn run search data
	public class SearchAsynRe extends AsyncTask<Void, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		final String errorCode = "";
		final String description = "";

		public SearchAsynRe(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.searching));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			return sendRequestSearch();
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result != null) {
				Log.d("result", result);
				if (result.equalsIgnoreCase("0")) {
					if (CacheDataCharge.getInstance().getLisArrayListRe() != null
							&& CacheDataCharge.getInstance()
									.getLisArrayListRe().size() > 0) {
						FragmentChargeSearchRe fraChargeSearch = new FragmentChargeSearchRe();
						ReplaceFragment.replaceFragment((Activity) context,
								fraChargeSearch, false);
					} else {
						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										context.getResources().getString(
												R.string.notsearchcustom),
										getResources().getString(
												R.string.searchcustom));
						dialog.show();
					}
				} else {
					if (errorCode.equals(Constant.INVALID_TOKEN2)) {

						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								description,
								context.getResources().getString(
										R.string.app_name), moveLogInAct);
						dialog.show();
					}
				}
			}
		}

		public String sendRequestSearch() {
			String original = null;

			String errorMessage = null;
			ArrayList<ChargeCustomerOJ> lisChargeCustomerOJsRe = new ArrayList<>();
			GetStaffcodeDal staffcodeDal = new GetStaffcodeDal(context);
			staffcodeDal.open();
			com.viettel.bss.viettelpos.v4.sale.object.Staff staff = new Staff();
			try {
				staff = staffcodeDal.getStaff();
				staffcodeDal.close();
				Log.d("staffcode", staff.getStaffCode());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchPMContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchPMContract>");
				rawData.append("<paymentInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				HashMap<String, String> rawDataItem = new HashMap<>();
				rawDataItem.put("appliedCycle", "01/" + kythuRe);
				rawDataItem.put("billCycleFrom", billCycleFromRe);
				rawDataItem.put("mngtStaffCode", staff.getStaffCode());
				rawDataItem.put("payAreaCode", "" + areaCodeRe);
				rawDataItem.put("serviceCode", "" + telecomServiceRe);
				rawDataItem.put("isdn", "" + isdnRe);
				rawDataItem.put("staffId", "" + staffid);
				param.put("collectionManagementBean",
						input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));

				rawData.append("</paymentInput>");
				rawData.append("</ws:searchPMContract>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchPMContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();

				// parse xml
				ListCollectiontBeanHandler beanHandler = (ListCollectiontBeanHandler) CommonActivity
						.parseXMLHandler(new ListCollectiontBeanHandler(),
								original);
				lisChargeCustomerOJsRe = beanHandler.getListObj();

				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("errorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// if(errorCode.equalsIgnoreCase("0")){
				// original = errorCode;
				// Log.d("original", original);
				// }
				// nodechild =
				// doc.getElementsByTagName("lstCollectionManagementBean");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// ChargeCustomerOJ chaCustomerOJ = new ChargeCustomerOJ();
				// String cusName = parse.getValue(e1, "custName");
				// chaCustomerOJ.setNameCustomer(cusName);
				//
				// String address = parse.getValue(e1, "address");
				// chaCustomerOJ.setAddr(address);
				//
				// String isdn = parse.getValue(e1, "isdn");
				// chaCustomerOJ.setISDN(isdn);
				//
				// String serviceName = parse.getValue(e1, "serviceName");
				// chaCustomerOJ.setServiceName(serviceName);
				//
				// String appliedCycle = parse.getValue(e1, "appliedCycle");
				// chaCustomerOJ.setAppliedCycle(appliedCycle);
				//
				// String contractFormMngtGroup = parse.getValue(e1,
				// "contractFormMngtGroup");
				// chaCustomerOJ.setContractFormMngtGroup(contractFormMngtGroup);
				//
				// String billCycleFrom = parse.getValue(e1, "billCycleFrom");
				// chaCustomerOJ.setBillCycleFrom(billCycleFrom);
				//
				// String serviceCodeString= parse.getValue(e1, "serviceCode");
				// chaCustomerOJ.setServiceCode(serviceCodeString);
				//
				// String contractId = parse.getValue(e1, "contractId");
				// chaCustomerOJ.setContractId(contractId);
				//
				// String groupId = parse.getValue(e1, "groupId");
				// chaCustomerOJ.setGroupId(groupId);
				//
				// String isCloseCycle = parse.getValue(e1, "isCloseCycle");
				// chaCustomerOJ.setIsCloseCycle(isCloseCycle);
				//
				// String vmId = parse.getValue(e1, "vmId");
				// chaCustomerOJ.setVmId(vmId);
				//
				// lisChargeCustomerOJsRe.add(chaCustomerOJ);
				// }
				// }

				CacheDataCharge.getInstance().setLisArrayListRe(
						lisChargeCustomerOJsRe);

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				// original = output.getErrorCode();
				// if (!output.getErrorCode().equals("0")) {
				// errorMessage = output.getDescription();
				// return Constant.ERROR_CODE;
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

			return original;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();

			LoginDialog dialog = new LoginDialog(getActivity(),
					";pm.assign_old_contract;");
			dialog.show();

		}
	};

	// class asyn run get list employ data
	public class GetEmployeAsyn extends
			AsyncTask<Void, Void, ArrayList<ChargeEmployeeOJ>> {

		final ProgressDialog progress;
		private Context context = null;
		String resultemployee = "";
		XmlDomParse parse = new XmlDomParse();
		final String errorCode = "";
		final String description = "";

		public GetEmployeAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ChargeEmployeeOJ> doInBackground(Void... params) {
			return sendRequestGetStaff();
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeEmployeeOJ> result) {
			progress.dismiss();
			if (result.size() > 0 && !result.isEmpty()) {
				arrChargeEmployeeOJs = result;
				initSpinerEmploy();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notemployee),
							getResources().getString(R.string.searchemployee));
					dialog.show();

				}
			}
		}

		// =====method get list staff ========================
		public ArrayList<ChargeEmployeeOJ> sendRequestGetStaff() {
			String original = null;
			String errorMessage = null;
			ArrayList<ChargeEmployeeOJ> lisChargeEmployeeOJsAsyn = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStaffByManager");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStaffByManager>");
				rawData.append("<paymentInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</paymentInput>");
				rawData.append("</ws:getListStaffByManager>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStaffByManager");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				// if (!output.getError().equals("0")) {
				// errorMessage = output.getDescription();
				// return Constant.ERROR_CODE;
				// }
				original = output.getOriginal();

				// ========parser xml get employ from server

				ListStaffBeanHandler handlerListStaffBean = (ListStaffBeanHandler) CommonActivity
						.parseXMLHandler(new ListStaffBeanHandler(), original);
				lisChargeEmployeeOJsAsyn = handlerListStaffBean.getmListObj();

				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("errorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// nodechild =
				// doc.getElementsByTagName("lstCollectionStaffBean");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// ChargeEmployeeOJ employeeOJ = new ChargeEmployeeOJ();
				// String staffCode = parse.getValue(e1, "staffCode");
				// Log.d("staffCode", staffCode);
				// employeeOJ.setStaffCode(staffCode);
				// String staffid = parse.getValue(e1, "staffId");
				// Log.d("staffid", staffid);
				// employeeOJ.setEmployeeId(staffid);
				// String staffName = parse.getValue(e1, "staffName");
				// Log.d("staffName", staffName);
				// employeeOJ.setNameEmpoyee(staffName);
				// lisChargeEmployeeOJsAsyn.add(employeeOJ);
				// }
				// }
				// Log.d("originalllllllll", original);
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					resultemployee = output.getDescription();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisChargeEmployeeOJsAsyn;
		}

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
}
