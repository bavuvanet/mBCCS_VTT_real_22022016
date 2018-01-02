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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.ChargeCustomerAdapter;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentChargeSearchRe extends FragmentCommon {

	private ListView lvListCustomer;
	private Spinner spinnerEmployee;
	private Button btnCommit;
	private ArrayList<ChargeCustomerOJ> lisChargeCustomerOJs;
	private ArrayList<ChargeEmployeeOJ> arrChargeEmployeeOJs;
	private ChargeCustomerAdapter chargeCustomerAdapter = null;
	private String staffid = "";

	@Override
	public void onResume() {
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
		idLayout = R.layout.layout_charge_search;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void unit(View v) {

		lisChargeCustomerOJs = new ArrayList<>();
		arrChargeEmployeeOJs = new ArrayList<>();

		spinnerEmployee = (Spinner) v.findViewById(R.id.spinner_employee);
		lvListCustomer = (ListView) v
				.findViewById(R.id.lv_list_charge_customer_search);
		btnCommit = (Button) v.findViewById(R.id.btn_commit);
		btnCommit.setOnClickListener(this);
		if (CacheDataCharge.getInstance().getLisArrayListRe() != null
				&& !CacheDataCharge.getInstance().getLisArrayListRe().isEmpty()) {
			chargeCustomerAdapter = new ChargeCustomerAdapter(CacheDataCharge
					.getInstance().getLisArrayListRe(), getActivity());
			lvListCustomer.setAdapter(chargeCustomerAdapter);
		}

		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetEmployeAsynRe getEmployeAsyn = new GetEmployeAsynRe(
					getActivity());
			getEmployeAsyn.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name));
			dialog.show();
		}

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
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_commit:
			for (ChargeCustomerOJ chaCustomerOJ : CacheDataCharge.getInstance()
					.getLisArrayListRe()) {
				if (chaCustomerOJ.getChecked()) {
					lisChargeCustomerOJs.add(chaCustomerOJ);
				}
			}
			if (lisChargeCustomerOJs != null && !lisChargeCustomerOJs.isEmpty()) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					Dialog dialog = CommonActivity.createDialog(getActivity(),
							getResources().getString(R.string.ischeckchargeRe),
							getResources().getString(R.string.charge_re),
							getResources().getString(R.string.cancel),
							getResources().getString(R.string.ok ),
							null, oncheckAsignClickListener );
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.ischoicecustom), Toast.LENGTH_SHORT)
						.show();
			}

			break;

		default:
			break;
		}
	}

	// check assigncontractre click
    private final OnClickListener oncheckAsignClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				AssignContractSynRe assignContractSyn = new AssignContractSynRe(
						getActivity(), lisChargeCustomerOJs);
				assignContractSyn.execute(lisChargeCustomerOJs);

			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

		}
	};

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

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			LoginDialog dialog = new LoginDialog(getActivity(), "");
			dialog.show();
		}
	};

	// class asyn run get list employ data
	public class GetEmployeAsynRe extends
			AsyncTask<Void, Void, ArrayList<ChargeEmployeeOJ>> {

		final ProgressDialog progress;
		private Context context = null;
		String resultemployee = "";
		final String errorCode = "";
		final String description = "";
		XmlDomParse parse = new XmlDomParse();

		public GetEmployeAsynRe(Context context) {
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
			if (result != null && !result.isEmpty()) {
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
					resultemployee = output.getContent();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisChargeEmployeeOJsAsyn;
		}

	}

	// ============ webservice phan giao viec=============================

	public class AssignContractSynRe extends
			AsyncTask<ArrayList<ChargeCustomerOJ>, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		private final ArrayList<ChargeCustomerOJ> lisChargeCustomerOJs;

		public AssignContractSynRe(Context context,
				ArrayList<ChargeCustomerOJ> arrObjects) {
			this.context = context;
			this.lisChargeCustomerOJs = arrObjects;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.assignJob));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(ArrayList<ChargeCustomerOJ>... params) {
			return sendRequestAssignReContract(lisChargeCustomerOJs);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("0")) {
				Log.d("result", result);
				progress.dismiss();

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.assignResucess),
						getResources().getString(R.string.assignContract),
						checkAsignReContrack);
				dialog.show();

				// AlertDialog.Builder alertDialogBuilder = new
				// AlertDialog.Builder(
				// context);
				// alertDialogBuilder.setTitle(context.getResources().getString(
				// R.string.assignContract));
				// // set dialog message
				// alertDialogBuilder
				// .setMessage(
				// context.getResources().getString(
				// R.string.assignResucess))
				// .setCancelable(false)
				// .setPositiveButton(
				// context.getResources().getString(R.string.ok),
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int id) {
				//
				// FragmentChargeSearchRe.this.lisChargeCustomerOJs.clear();
				// for (ChargeCustomerOJ item :
				// CacheDataCharge.getInstance().getLisArrayListRe()) {
				// item.setChecked(false);
				// }
				// chargeCustomerAdapter = new
				// ChargeCustomerAdapter(CacheDataCharge.getInstance().getLisArrayListRe(),
				// context);
				// lvListCustomer.setAdapter(chargeCustomerAdapter);
				// dialog.dismiss();
				//
				// }
				// });
				// // create alert dialog
				// AlertDialog alertDialog = alertDialogBuilder.create();
				// alertDialog.show();
			} else {
				progress.dismiss();
				FragmentChargeSearchRe.this.lisChargeCustomerOJs.clear();
				chargeCustomerAdapter = new ChargeCustomerAdapter(
						CacheDataCharge.getInstance().getLisArrayListRe(),
						context);
				lvListCustomer.setAdapter(chargeCustomerAdapter);
				chargeCustomerAdapter.notifyDataSetChanged();

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						result,
						getResources().getString(R.string.assignContract),
						moveLogInAct);
				dialog.show();

			}

		}

		// ======== send request assign job===============
		public String sendRequestAssignReContract(
				ArrayList<ChargeCustomerOJ> lisChargeCustomerOJs) {
			String original = null;
			String errorMessage = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_assignContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:assignContract>");
				rawData.append("<assignContract>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (lisChargeCustomerOJs.size() > 0) {
					for (ChargeCustomerOJ item : lisChargeCustomerOJs) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("appliedCycle", item.getAppliedCycle());
						rawDataItem.put("billCycleFrom",
								item.getBillCycleFrom());
						rawDataItem
								.put("custName", "" + item.getNameCustomer());
						rawDataItem.put("serviceCode",
								"" + item.getServiceCode());
						rawDataItem.put("serviceName",
								"" + item.getServiceName());
						rawDataItem.put("address", "" + item.getAddr());
						rawDataItem
								.put("contractId", "" + item.getContractId());
						rawDataItem.put("isdn", "" + item.getISDN());
						rawDataItem.put("staffId", staffid);
						rawDataItem.put("appliedCycle", item.getAppliedCycle());
						rawDataItem.put("contractFormMngtGroup",
								"" + item.getContractFormMngtGroup());
						rawDataItem.put("groupId", "" + item.getGroupId());
						rawDataItem.put("isCloseCycle",
								"" + item.getIsCloseCycle());
						rawDataItem.put("vmId", "" + item.getVmId());

						param.put("lstCollectionManagementBean",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}
				}
				rawData.append("</assignContract>");
				rawData.append("</ws:assignContract>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_assignContract");
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
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();

				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return original;
		}

		// === check asignReContract
        final OnClickListener checkAsignReContrack = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentChargeSearchRe.this.lisChargeCustomerOJs.clear();
				for (ChargeCustomerOJ item : CacheDataCharge.getInstance()
						.getLisArrayListRe()) {
					item.setChecked(false);
				}
				chargeCustomerAdapter = new ChargeCustomerAdapter(
						CacheDataCharge.getInstance().getLisArrayListRe(),
						context);
				lvListCustomer.setAdapter(chargeCustomerAdapter);

			}
		};

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
}
