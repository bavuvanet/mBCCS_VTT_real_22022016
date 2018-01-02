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
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.charge.dal.CycleDal;
import com.viettel.bss.viettelpos.v4.charge.dal.GetStaffcodeDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentChargeNew extends FragmentCommon {
	private final String TAG = "FragmentChargeNew";
	private Spinner spinnerArea;
    private Spinner spinnerService;
    private Spinner spinnerPeriod;
	private EditText editISDN;
	private Button btnSearch;
	// list telecom service
    private ArrayList<TelecomServiceObject> lstService = null;
	private ArrayList<AreaObj> lstAreaObjs = new ArrayList<>();
	private ArrayList<CycleObj> lisCycleObjs = new ArrayList<>();

	// define areacode, telecomservice , billcyclefrom
    private String areaCode = "";
	private String telecomService = "";
	private String billCycleFrom = "";
	private String isdn = "";

	@Override
	public void onResume() {
		CacheDataCharge.getInstance().setLisArrayListRe(null);
		CacheDataCharge.getInstance().setLisArrayList(null);

//		btnHome.setVisibility(View.GONE);
        MainActivity.getInstance().setTitleActionBar(act.getString(R.string.chargeable) + " - "
                + act.getString(R.string.charge_new));
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_charge_new;

		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void unit(View v) {
		spinnerArea = (Spinner) v.findViewById(R.id.spinner_area);
		spinnerService = (Spinner) v.findViewById(R.id.spinner_service);
		spinnerPeriod = (Spinner) v.findViewById(R.id.spinner_period);
		editISDN = (EditText) v.findViewById(R.id.edit_isdn);
		btnSearch = (Button) v.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(this);

		// fill data for spiner
		initSpinerArea();
		initSpinnerDichVu();
		initSpinerCycle();

		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				areaCode = lstAreaObjs.get(position).getAreaCode();
				Log.d("areaCode", areaCode);

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

				telecomService = lstService.get(position).getCode();
				Log.d("telecomService", telecomService);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinnerPeriod.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				billCycleFrom = lisCycleObjs.get(position).getBillCycleFrom();
				Log.d("billCycleFrom", billCycleFrom);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
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
			// lstAreaObjs.get(0).getAreaCode();
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
			Log.e(TAG, e.toString(), e);
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
		super.onClick(arg0);
		switch (arg0.getId()) {

		case R.id.btn_search:
			// toast("search");

			isdn = editISDN.getText().toString();
			Log.d("isdn", isdn);
			Log.d("areacode", areaCode);
			Log.d("telecomservice", telecomService);
			Log.d("billCycleFrom", billCycleFrom);
			if (CommonActivity.isNetworkConnected(getActivity())) {
				SearchAsyn searchAsyn = new SearchAsyn(getActivity());
				searchAsyn.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

			break;
		default:
			break;
		}
	}

	// class asyn run search data
	public class SearchAsyn extends AsyncTask<Void, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public SearchAsyn(Context context) {
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

					if (CacheDataCharge.getInstance().getLisArrayList() != null
							&& CacheDataCharge.getInstance().getLisArrayList()
									.size() > 0) {
						FragmentChargeSearch fraChargeSearch = new FragmentChargeSearch();
						ReplaceFragment.replaceFragment((Activity) context,
								fraChargeSearch, false);
					} else {

						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.notsearchcustom),
										getResources().getString(
												R.string.searchcustom));
						dialog.show();
					}
				} else {
					if (errorCode.equals(Constant.INVALID_TOKEN2)
							&& description != null && !description.isEmpty()) {
						Log.d("INVALID_TOKEN", "INVALID_TOKEN");
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								description,
								context.getResources().getString(
										R.string.app_name), moveLogInAct);
						dialog.show();
					}
					// else{
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), description,context.getResources()
					// .getString(R.string.app_name));
					// dialog.show();
					// }
				}
			}
		}

		public String sendRequestSearch() {
			String original = null;

			String errorMessage = null;
			GetStaffcodeDal staffcodeDal = new GetStaffcodeDal(context);
			staffcodeDal.open();
			ArrayList<ChargeCustomerOJ> lisChargeCustomerOJs = new ArrayList<>();
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
				rawDataItem.put("billCycleFrom", billCycleFrom);
				rawDataItem.put("mngtStaffCode", staff.getStaffCode());
				rawDataItem.put("payAreaCode", "" + areaCode);
				rawDataItem.put("serviceCode", "" + telecomService);
				rawDataItem.put("isdn", "" + isdn);
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

				if (!output.getErrorCode().equals("0")) {
					errorMessage = output.getDescription();
					return output.getErrorCode();
				}

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
					if (errorCode.equalsIgnoreCase("0")) {
						original = errorCode;
						Log.d("original", original);
					}
					nodechild = doc
							.getElementsByTagName("lstCollectionManagementBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeCustomerOJ chaCustomerOJ = new ChargeCustomerOJ();
						String cusName = parse.getValue(e1, "custName");
						chaCustomerOJ.setNameCustomer(cusName);
						Log.d("cusname", cusName);
						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						chaCustomerOJ.setAddr(address);
						String isdn = parse.getValue(e1, "isdn");
						chaCustomerOJ.setISDN(isdn);
						Log.d("isdn", isdn);
						String serviceName = parse.getValue(e1, "serviceName");
						chaCustomerOJ.setServiceName(serviceName);
						String appliedCycle = parse
								.getValue(e1, "appliedCycle");
						Log.d("appliedCycle", appliedCycle);
						chaCustomerOJ.setAppliedCycle(appliedCycle);
						String contractFormMngtGroup = parse.getValue(e1,
								"contractFormMngtGroup");
						chaCustomerOJ
								.setContractFormMngtGroup(contractFormMngtGroup);
						String billCycleFrom = parse.getValue(e1,
								"billCycleFrom");
						chaCustomerOJ.setBillCycleFrom(billCycleFrom);
						String serviceCodeString = parse.getValue(e1,
								"serviceCode");
						chaCustomerOJ.setServiceCode(serviceCodeString);
						String contractId = parse.getValue(e1, "contractId");
						Log.d("contractId", contractId);
						chaCustomerOJ.setContractId(contractId);
						String groupId = parse.getValue(e1, "groupId");
						chaCustomerOJ.setGroupId(groupId);
						String isCloseCycle = parse
								.getValue(e1, "isCloseCycle");
						chaCustomerOJ.setIsCloseCycle(isCloseCycle);
						String vmId = parse.getValue(e1, "vmId");
						chaCustomerOJ.setVmId(vmId);
						lisChargeCustomerOJs.add(chaCustomerOJ);

						CacheDataCharge.getInstance().setLisArrayList(
								lisChargeCustomerOJs);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return original;
		}

		// move login
        final OnClickListener moveLogInAct = new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Intent intent = new
				// Intent(getActivity(),LoginActivity.class);
				// startActivity(intent);
				// getActivity().finish();
				LoginDialog dialog = new LoginDialog(getActivity(),
						";pm.assign.new.contract;");
				dialog.show();

			}
		};

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";pm.assign.new.contract;";
	}

}
