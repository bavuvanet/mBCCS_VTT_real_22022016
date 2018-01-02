package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.process.PrcDateSingle;
import com.viettel.bss.viettelpos.v4.report.object.VerifyCustomerOJ;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentReportInfoVerCustomer extends FragmentCommon {
	private EditText editCusIsdn;
    private EditText editCusAddr;
    private EditText editDateVerified;
	private Spinner spinnerSelectEmployee;
	private Button btnUpdate;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.ver_customer);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_info_ver_customer;
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		editCusIsdn = (EditText) v.findViewById(R.id.edit_cus_name);
		editCusAddr = (EditText) v.findViewById(R.id.edit_cus_addr);
		editDateVerified = (EditText) v.findViewById(R.id.edit_cus_care_time);
		editDateVerified.setOnClickListener(this);
		btnUpdate = (Button) v.findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(this);
		spinnerSelectEmployee = (Spinner) v
				.findViewById(R.id.spinner_cus_care_select_employee);
		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncLoadEmploye getEmployeAsyn = new AsyncLoadEmploye(
					getActivity());
			getEmployeAsyn.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork), getActivity()
							.getResources().getString(R.string.app_name));
			dialog.show();
		}
		obtainCusCareObj();
		prcDateSingle = new PrcDateSingle(act, editDateVerified,
				verifyCustomerOJ.getDateVerified(), false,
				verifyCustomerOJ.getVerified());
	}

	private PrcDateSingle prcDateSingle;
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (asyncUpdateCusCareInfo != null) {
			asyncUpdateCusCareInfo.cancel(false);
		}
	}
	private void obtainCusCareObj() {
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			verifyCustomerOJ = (VerifyCustomerOJ) mBundle
					.getSerializable(Define.KEY_VER_CUSTOMER);
			if (verifyCustomerOJ != null) {
				// toast(cusGiftObj.getCustomerOJ().getAllContent());
				CustomerOJ customerOJ = verifyCustomerOJ.getCustomerOJ();
				editCusIsdn.setText(customerOJ.getName());
				editCusAddr.setText(customerOJ.getAddr());
				// editCusTel.setText(customerOJ.getTel());
				editDateVerified.setText(verifyCustomerOJ.getDateVerified());
			}
		}

	}
	private boolean checkOldData() {
		// TODO Auto-generated method stub
		return checkOldDate() && checkOldEmployee();

	}
	private VerifyCustomerOJ verifyCustomerOJ;
	private boolean checkOldDate() {
		// TODO Auto-generated method stub
		boolean res = false;
		String tmpText = editDateVerified.getText().toString();
		if (tmpText.equals(verifyCustomerOJ.getDateVerified())) {
			res = true;
		}
		return res;
	}
	private ArrayList<ChargeEmployeeOJ> arrChargeEmployeeOJs = new ArrayList<>();
	private boolean checkOldEmployee() {
		// TODO Auto-generated method stub
		boolean res = false;

		// if (spinnerSelectEmployee.getVisibility() == View.VISIBLE) {
		int i = spinnerSelectEmployee.getSelectedItemPosition();
		if (i == 0) {

		}
		String idEmployee = arrChargeEmployeeOJs.get(i).getEmployeeId();
		if (idEmployee.equals(verifyCustomerOJ.getIdLocalEmployee())) {
			res = true;
		}

		return res;
	}
	private void submit() {
		// TODO Auto-generated method stub
		if (!CommonActivity.isNetworkConnected(act)) {
			CommonActivity.createAlertDialog(act, R.string.errorNetwork,
					R.string.app_name).show();
			return;
		}

		CommonActivity.createDialog(act, R.string.updateConfirm,
				R.string.app_name, R.string.buttonOk, R.string.buttonCancel,
				onClickListenerUpdate, onClickListenerBack).show();
	}

	private final OnClickListener onClickListenerUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (!prcDateSingle.checkDate()) {
				return;
			}
			if (!checkOldData()) {
				asyncUpdateCusCareInfo = new AsyncUpdateCusCareInfo(act, false);
				asyncUpdateCusCareInfo.execute();
			} else {
				CommonActivity.createAlertDialog(act, R.string.not_change_data,
						R.string.app_name).show();
			}

		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// super.onClick(arg0);
		switch (arg0.getId()) {
			case R.id.relaBackHome :
				if (checkOldData()) {
					CommonActivity.hideKeyboard(editCusIsdn, act);
					getActivity().onBackPressed();
				} else {
					CommonActivity.createDialog(act, R.string.back_confirm,
							R.string.app_name, R.string.buttonOk,
							R.string.buttonCancel, onClickListenerBack, null)
							.show();
				}
				break;
			case R.id.edit_cus_care_time :
				// dpdCancel = false;
				Date mDateFrom = prcDateSingle.mDate;
				DatePickerDialog dpd = new FixedHoloDatePickerDialog(act, AlertDialog.THEME_HOLO_LIGHT,
						prcDateSingle.onDateSetListener, mDateFrom.getYear(),
						mDateFrom.getMonth(), mDateFrom.getDate());
				dpd.show();
				if (Build.VERSION.SDK_INT >= 18
				// Build.VERSION_CODES.JELLY_BEAN
				) {
					dpd.setCancelable(false);
				}

				break;
			// case R.id.edit_cus_care_time_appoint :
			// Date mDateTo = prcDateCouple.mDateTo;
			// DatePickerDialog dpd1 = new DatePickerDialog(act,
			// prcDateCouple.onDateSetListenerTo, mDateTo.getYear(),
			// mDateTo.getMonth(), mDateTo.getDate());
			// dpd1.show();
			// if (Build.VERSION.SDK_INT >= 18
			// // Build.VERSION_CODES.JELLY_BEAN
			// ) {
			// dpd1.setCancelable(false);
			// }
			// break;
			case R.id.btn_update :
				// toast("update");
				submit();

				break;

			default :
				break;
		}
	}
	public class AsyncLoadEmploye
			extends
				AsyncTask<Void, Void, ArrayList<ChargeEmployeeOJ>> {

		final ProgressDialog progress;
		private Context context = null;
		String resultemployee = "";
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		public AsyncLoadEmploye(Context context) {
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

		// Arrays arrEmployeeId = new arrays
		public void initSpinerEmploy() {
			if (arrChargeEmployeeOJs != null && arrChargeEmployeeOJs.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				adapter.add(act.getString(R.string.select_employee));
				for (ChargeEmployeeOJ employeeOJ : arrChargeEmployeeOJs) {
					Log.e(tag, employeeOJ.getEmployeeId() + "...employee id");
					// String staffCode =14;
					arrEmployeeId.add(employeeOJ.getEmployeeId());
					adapter.add(employeeOJ.getNameEmpoyee());
				}
				spinnerSelectEmployee.setAdapter(adapter);
				String id = verifyCustomerOJ.getIdLocalEmployee();
				if (id != null && !id.isEmpty()) {
					setSpinnerEmployeeSelection(id);
				} else {

				}

			}
		}
		final String tag = "info ver customer";
		private void setSpinnerEmployeeSelection(String staffId) {
			// TODO Auto-generated method stub
			int index = -1;

			for (int i = 0; i < arrEmployeeId.size(); i++) {
				if (staffId.equals(arrEmployeeId.get(i))) {
					index = i;

					break;
				}
			}
			if (index != -1) {
				spinnerSelectEmployee.setSelection(index);
			}
		}
		final ArrayList<String> arrEmployeeId = new ArrayList<>();
		public ArrayList<ChargeEmployeeOJ> getArrChargeEmployeeOJs() {
			return arrChargeEmployeeOJs;
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
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListStaffByManager");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				// if (!output.getError().equals("0")) {
				// errorMessage = output.getDescription();
				// return Constant.ERROR_CODE;
				// }
				original = output.getOriginal();

				// ========parser xml get employ from server

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
							.getElementsByTagName("lstCollectionStaffBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeEmployeeOJ employeeOJ = new ChargeEmployeeOJ();
						String staffCode = parse.getValue(e1, "staffCode");
						Log.d("staffCode", staffCode);
						employeeOJ.setStaffCode(staffCode);
						String staffid = parse.getValue(e1, "staffId");
						Log.d("staffid", staffid);
						employeeOJ.setEmployeeId(staffid);
						String staffName = parse.getValue(e1, "staffName");
						Log.d("staffName", staffName);
						employeeOJ.setNameEmpoyee(staffName);
						lisChargeEmployeeOJsAsyn.add(employeeOJ);
					}
				}
				Log.d("originalllllllll", original);
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
	private AsyncUpdateCusCareInfo asyncUpdateCusCareInfo;
	private class AsyncUpdateCusCareInfo
			extends
				AsyncTask<Integer, Void, String> {
		private final Context context;
		final ProgressDialog progress;
		final boolean first;
		// String areaCode, textDate;
		public AsyncUpdateCusCareInfo(Context context, boolean first) {
			this.context = context;
			this.first = first;
			// this.areaCode = areaCode;
			// this.textDate = textDate;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected String doInBackground(Integer... params) {
			// Log.i("TAG", " Kich thuoc " + arrChannelContent.size());
			// return startDownloadFile(params[0]);
			int l = 5;
			long time = 200;
			for (int i = 0; i < l; i++) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// Log.e("TAG", " Ket qua cap nhat cham soc " + result);
			// Log.e(tag, areaCode + " ... on post");
			this.progress.dismiss();
			// processStaffSelected();
			// submit();
			// if (tvTitleList != null) {
			try {
				CommonActivity.createAlertDialog(act, R.string.updatesucess,
						R.string.app_name, onClickListenerBack).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// setData4Test();
		}
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
