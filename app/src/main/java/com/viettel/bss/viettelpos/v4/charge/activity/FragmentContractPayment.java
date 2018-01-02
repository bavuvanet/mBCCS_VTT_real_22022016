package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.Date;
import java.util.HashMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractPaymentAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectNewAdapter;
import com.viettel.bss.viettelpos.v4.charge.fragment.FragmentChooseContractFormMngt;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView.OnLoadMoreListener;

import android.content.Intent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentContractPayment extends FragmentCommon {

	private Activity activity;

	private EditText edt_applied_cycle;
	private EditText edtFromMoney;
	private EditText edtToMoney;

	// private Spinner spn_bill;
	private Spinner spn_payment_status;
    private EditText edtObjectCus;
    private final List<ChargeContractItem> chargeContractItems = new ArrayList<>();
	private List<Spin> lstSpin = new ArrayList<>();
	private CustomerObjectNewAdapter customerObjectAdapter;

	private String contractFormMngt;

	private String appliedCycle;

	private LinearLayout lnCount;
	private TextView tvCount;

    private int pageIndex = 0;
	
	private final int REQUEST_N_Y = 1998;
	
	private ArrayList<ContractFormMngtBean> lstContractForm = new ArrayList<ContractFormMngtBean>();
	
	
	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_payment);
		if (dialogLoadMore != null && !dialogLoadMore.isShowing()) {
			Log.i(Constant.TAG, "dismissDialog " + dialogLoadMore.isShowing());
			dialogLoadMore.show();
		} else {
			Log.i(Constant.TAG, "dismissDialog ");
		}
		if(!CommonActivity.isNullOrEmptyArray(lstContractForm)){
			edtObjectCus.setText(getListCodeContract());
		}else{
			edtObjectCus.setText("");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = getActivity();
		idLayout = R.layout.layout_contract_payment;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_N_Y:
				lstContractForm = (ArrayList<ContractFormMngtBean>) data.getSerializableExtra("lstContract");
				if(!CommonActivity.isNullOrEmptyArray(lstContractForm)){
					edtObjectCus.setText(getListCodeContract());
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
	public void unit(View v) {
		edt_applied_cycle = (EditText) v.findViewById(R.id.edt_applied_cycle);
		edtFromMoney = (EditText) v.findViewById(R.id.edtFromMoney);
		edtToMoney = (EditText) v.findViewById(R.id.edtToMoney);

		// spn_bill = (Spinner) v.findViewById(R.id.spn_bill);
		spn_payment_status = (Spinner) v.findViewById(R.id.spn_payment_status);
		edtObjectCus = (EditText) v.findViewById(R.id.edtObjectCus);

        Button imb_search_contract_payment = (Button) v
                .findViewById(R.id.imb_search_contract_payment);
		imb_search_contract_payment.setOnClickListener(this);

		edt_applied_cycle.setOnClickListener(this);
		edtObjectCus.setOnClickListener(this);

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = 1;
		edt_applied_cycle.setText(day + "/" + (month + 1) + "/" + year);

		lnCount = (LinearLayout) v.findViewById(R.id.lnCount);
		tvCount = (TextView) v.findViewById(R.id.tvCount);

		// tuantd7();

		// if(pbCustomerObject != null){
		// AsyncGetContractFormMngtN1N6 asynctaskContractFotmMngt = new
		// AsyncGetContractFormMngtN1N6(pbCustomerObject, lstSpin.size(),
		// lstSpin.size() + Constant.PAGE_SIZE);
		// asynctaskContractFotmMngt.execute();
		// }

	}

	private void tuantd7() {
		edt_applied_cycle.setText("01/11/2015");
		edtFromMoney.setText("1");
		edtToMoney.setText("10000000");
		spn_payment_status.setSelection(0);
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
	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.imb_search_contract_payment:
			if (CommonActivity.isNetworkConnected(activity)) {
				appliedCycle = edt_applied_cycle.getText().toString().trim();

				contractFormMngt = getListCodeContract();

				if (appliedCycle.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.chon_ngay),
							getString(R.string.app_name)).show();
					break;
				}

				String fromMoney = edtFromMoney.getText().toString().trim();
				String toMoney = edtToMoney.getText().toString().trim();

				Dialog dialogError = null;
				if (!CommonActivity.isNullOrEmpty(fromMoney)
						&& !CommonActivity.isNullOrEmpty(toMoney)) {
					 if (Long.parseLong(fromMoney) > Long.parseLong(toMoney)) {
						dialogError = CommonActivity
								.createAlertDialog(
										activity,
										getString(R.string.message_input_from_money_big_than_to_money),
										getString(R.string.app_name));
					 }
				}
				
				if (contractFormMngt == null
						|| contractFormMngt.isEmpty()) {
					dialogError = CommonActivity
							.createAlertDialog(
									activity,
									getString(R.string.message_not_input_customer_name_or_name),
									getString(R.string.app_name));
				}

				if (dialogError != null) {
					dialogError.show();
					return;
				}

				if (chargeContractItems != null) {
					chargeContractItems.clear();
				}
				Integer status = getResources().getIntArray(
						R.array.payment_status_value)[spn_payment_status
						.getSelectedItemPosition()];
				dialogLoadMore = null;
				pageIndex = 0;
				AsyncContractPayment asynctaskSearchContract = new AsyncContractPayment(
						appliedCycle, fromMoney, toMoney, status, pageIndex, 50);
				asynctaskSearchContract.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}

			break;

		case R.id.edt_applied_cycle:
			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					day = 1;
					String applied_cycle = day + "/" + (month + 1) + "/" + year;
					edt_applied_cycle.setText(applied_cycle);
				}
			};
			Calendar calendar = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt_applied_cycle.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt_applied_cycle.getText()
						.toString(), "dd/MM/yyyy");

				calendar.setTime(date);

			}
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = 1;// calendar.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity,
					AlertDialog.THEME_HOLO_LIGHT,onDateSetListener, year, month, day);
			
			
			
			
			pic.getDatePicker()
					.findViewById(
							Resources.getSystem().getIdentifier("day", "id",
									"android")).setVisibility(View.GONE);

			pic.setTitle(activity.getString(R.string.chon_ngay));
			pic.show();

			break;

		case R.id.edtObjectCus:
//			showDialogListObjectCustommer();
			if(FragmentChooseContractFormMngt.hashMapContract != null){
				FragmentChooseContractFormMngt.hashMapContract = new HashMap<String, ContractFormMngtBean>();
			}
			FragmentChooseContractFormMngt fragment = new FragmentChooseContractFormMngt();
			fragment.setTargetFragment(this, REQUEST_N_Y);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			break;

		default:
			break;
		}
	}

	private class AsyncContractPayment extends
			AsyncTask<String, Void, ArrayList<ChargeContractItem>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		private final String appliedCycle;
		private final String fromValue;
		private final String toValue;
		private final int paymentStatus;
		private final int pageindex;
		private final int pageSize;

		public AsyncContractPayment(String _appliedCycle, String _fromValue,
				String _toValue, int _paymentStatus, int pageindex, int pageSize) {
			appliedCycle = _appliedCycle;
			fromValue = _fromValue;
			toValue = _toValue;
			paymentStatus = _paymentStatus;
			this.pageindex = pageindex;
			this.pageSize = pageSize;
			Log.d(Constant.TAG, AsyncContractPayment.this.toString());

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
		protected ArrayList<ChargeContractItem> doInBackground(String... params) {

			return searchCollectionManagement();
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					chargeContractItems.addAll(result);
                    ContractPaymentAdapter chargeDelAdapter = new ContractPaymentAdapter(
                            chargeContractItems, activity, true);

					tvCount.setText("" + result.size());
					lnCount.setVisibility(View.VISIBLE);
					if (dialogLoadMore == null) {
						showDialogLoadMoreListView(chargeDelAdapter);
					}

				} else {
					lnCount.setVisibility(View.GONE);

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

		private ArrayList<ChargeContractItem> searchCollectionManagement() {
			ArrayList<ChargeContractItem> listChargeObject = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_searchCollectionManagement");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchCollectionManagement>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				// rawData.append("<staffCode>TT_TESTER</staffCode>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");

				rawData.append("<appliedCycle>").append(appliedCycle).append("</appliedCycle>");
//				rawData.append("<fromValue>" + fromValue + "</fromValue>");
//				rawData.append("<toValue>" + toValue + "</toValue>");
				if(!CommonActivity.isNullOrEmpty(fromValue)){
					rawData.append("<fromValue>").append(fromValue).append("</fromValue>");
				}
				
				if(!CommonActivity.isNullOrEmpty(toValue)){
					rawData.append("<toValue>").append(toValue).append("</toValue>");
				}
				
				rawData.append("<paymentStatus>").append(paymentStatus).append("</paymentStatus>");

				rawData.append("<contractFormMngt>").append(contractFormMngt).append("</contractFormMngt>");

				rawData.append("<pageIndex>").append(pageindex).append("</pageIndex>");
				rawData.append("<pageSize>").append(pageSize).append("</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:searchCollectionManagement>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchCollectionManagement");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip

				// JSONObject jsonObject = null;
				// try {
				// jsonObject = XML.toJSONObject(original);
				// Log.i(Constant.TAG, jsonObject.toString());
				//
				// if(jsonObject.has("lstMContractBean")) {
				// JSONArray jsonArray =
				// jsonObject.getJSONArray("lstMContractBean");
				// for (int i = 0; i < jsonArray.length(); i++) {
				// JSONObject obj = jsonArray.getJSONObject(i);
				// }
				// } else {
				// Log.i(Constant.TAG, "lstMContractBean Key Not Found");
				// }
				//
				// } catch (JSONException e) {
				// Log.e(Constant.TAG, e.getMessage(), e);
				// }

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstMContractBeanPayServ");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem obj = new ChargeContractItem();
						
						obj.setAddress(parse.getValue(e1, "address"));
						obj.setContractFormMngtName(parse.getValue(e1, "contractFormMngtName"));
						obj.setContractId(parse.getValue(e1, "contractId"));
						obj.setContractNo(parse.getValue(e1, "contractNo"));
						obj.setIsdn(parse.getValue(e1, "isdn"));
						obj.setTotCharge(parse.getValue(e1, "totCharge"));
						obj.setTelFax(parse.getValue(e1, "telFax"));
						obj.setPriorDebit(parse.getValue(e1, "priorDebit"));
						obj.setPayMethodCode(parse
								.getValue(e1, "payMethodCode"));
						obj.setContractFormMngt(parse.getValue(e1,
								"contractFormMngt"));
						obj.setContractFormMngtName(parse.getValue(e1,
								"contractFormMngtName"));
						obj.setDebit(parse.getValue(e1, "debit"));
						obj.setHotCharge(parse.getValue(e1, "hotCharge"));
						obj.setPayMethodName(parse
								.getValue(e1, "payMethodName"));
						obj.setPayer(parse.getValue(e1, "payer"));

						String s = "" + paymentStatus;
						if (paymentStatus == 0) {
							s = parse.getValue(e1, "paymentStatus");
							if (s == null || s.isEmpty()) {
								s = "0";
							}
						}
						obj.setPaymentStatus(s);
						listChargeObject.add(obj);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return listChargeObject;
		}

		@Override
		public String toString() {
            return "AsyncContractPayment [appliedCycle=" +
                    appliedCycle + ", fromValue=" +
                    fromValue + ", toValue=" + toValue +
                    ", paymentStatus=" + paymentStatus +
                    "]";
		}
	}

	private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = Integer.valueOf(buttonView.getTag().toString());
			Log.d(this.getClass().getSimpleName(), "buttonView " + position
					+ " " + isChecked);

			int numOfSelected = 0;
			for (Spin spin : lstSpin) {
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
				lstSpin.get(position).setId(isChecked ? "1" : "0");
			}
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
			customerObjectAdapter = new CustomerObjectNewAdapter(activity,
					lstSpin, onCheckedChangeListener);
			lvObjectCustomer.setAdapter(customerObjectAdapter);
		}
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
					lstSpin = result;
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

	private void dismissDialog() {
		if (dialogLoadMore != null) {
			Log.i(Constant.TAG, "dismissDialog " + dialogLoadMore.isShowing());
			dialogLoadMore.dismiss();
		} else {
			Log.i(Constant.TAG, "dismissDialog ");
		}
	}

	private Dialog dialogLoadMore;

    private void showDialogLoadMoreListView(BaseAdapter adapter) {
		dialogLoadMore = new Dialog(activity);
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.dialog_listview);
		dialogLoadMore.setCancelable(true);
		dialogLoadMore.setTitle(getString(R.string.contract_payment));

        LoadMoreListView loadMoreListView = (LoadMoreListView) dialogLoadMore
                .findViewById(R.id.loadMoreListView);
		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnCancel);

		loadMoreListView.setAdapter(adapter);

		loadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				dismissDialog();

				ChargeContractItem chargeContractItem = chargeContractItems
						.get(position);

				Gson gson = new Gson();
				String json = gson.toJson(chargeContractItem);

				Log.i(Constant.TAG, "onItemClick position: " + position + " "
						+ json);

				Bundle bundle = new Bundle();
				bundle.putSerializable("chargeContractItem", chargeContractItem);
				bundle.putString("appliedCycle", appliedCycle);

				FragmentContractPaymentDetail fragmentChargeInfoCustomerDetail = new FragmentContractPaymentDetail();
				fragmentChargeInfoCustomerDetail.setArguments(bundle);
				fragmentChargeInfoCustomerDetail.setTargetFragment(
						FragmentContractPayment.this, 100);
				ReplaceFragment.replaceFragment(activity,
						fragmentChargeInfoCustomerDetail, true);
			}
		});

		loadMoreListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				String fromMoney = edtFromMoney.getText().toString().trim();
				String toMoney = edtToMoney.getText().toString().trim();
				pageIndex = pageIndex + 1;
				Integer status = getResources().getIntArray(
						R.array.payment_status_value)[spn_payment_status
						.getSelectedItemPosition()];
				AsyncContractPayment asynctaskSearchContract = new AsyncContractPayment(
						appliedCycle, fromMoney, toMoney, status, pageIndex, 50);
				asynctaskSearchContract.execute();
			}
		});

		// loadMoreListView.setOnLoadMoreListener(new
		// LoadMoreListView.OnLoadMoreListener() {
		//
		// @Override
		// public void onLoadMore() {
		// Log.i(this.getClass().getSimpleName(), "loadMoreListView
		// onLoadMore");
		// }
		// });

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissDialog();
			}
		});

		dialogLoadMore.show();
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";cus_need_payment;";
	}
}
