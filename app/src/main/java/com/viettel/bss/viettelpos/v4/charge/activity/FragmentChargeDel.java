package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.ChargeDelAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeItemGetDebit;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeItemObjectDel;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeViewInfoContract;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentChargeDel extends FragmentCommon {
	private Spinner spinnerParter;
	private EditText editAcc;
    private EditText editMoney;
	private LinearLayout btnSearchAcc;
	private Button btnViewPayment;
	private ListView lvListCustomer;
	private LinearLayout linearPayMent;
	private ArrayList<ChargeEmployeeOJ> arrChargeEmployeeOJs = new ArrayList<>();
	private ArrayList<ChargeItemObjectDel> arrChargeItemObjectDels = new ArrayList<>();
	private ChargeDelAdapter chargeDelAdapter = null;
	private String staffid = "";
	private String staffName = "";
	private String staffCode = "";
	private String value = "";
	private String paymentAmount = "";
	private int positionlv = -1;
	private Long paymentCount = 0L;
	private Dialog dialogViewInfoCongno;
	// ===========define object ChargeItemGetDebit =============
    private ChargeItemGetDebit chargeItemGetDebit = new ChargeItemGetDebit();
	private ChargeViewInfoContract chargeViewInfoContract = new ChargeViewInfoContract();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("onCreate", "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(act.getString(R.string.chargeable) + " - "
                + act.getString(R.string.charge_del));
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("onActivityCreated", "onActivityCreated");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_charge_del;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void unit(View v) {
		spinnerParter = (Spinner) v.findViewById(R.id.spinner_partner);
		editAcc = (EditText) v.findViewById(R.id.edittext_acc);
		editMoney = (EditText) v.findViewById(R.id.edittext_input_money);
		btnSearchAcc = (LinearLayout) v.findViewById(R.id.btn_search_acc);
		btnViewPayment = (Button) v.findViewById(R.id.btn_view_payment);
		btnSearchAcc.setOnClickListener(this);
		btnViewPayment.setOnClickListener(this);

		linearPayMent = (LinearLayout) v.findViewById(R.id.layout_view_payment);
		lvListCustomer = (ListView) v.findViewById(R.id.lv_del_search2);
		lvListCustomer.setOnItemClickListener(this);
		// lvListCustomer.setonI

		// ====== hide linear Payment and listcustomer=============
		linearPayMent.setVisibility(View.GONE);

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

		spinnerParter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				staffid = arrChargeEmployeeOJs.get(position).getEmployeeId();
				staffName = arrChargeEmployeeOJs.get(position).getNameEmpoyee();
				staffCode = arrChargeEmployeeOJs.get(position).getStaffCode();
				Log.d("staffid", staffid);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
			long arg3) {

		final int p = postion;

		for (ChargeItemObjectDel item : arrChargeItemObjectDels) {
			item.setCheck(false);
		}
		arrChargeItemObjectDels.get(postion).setCheck(true);
		chargeDelAdapter.notifyDataSetChanged();
		if (arrChargeItemObjectDels.get(postion).getDebitContract() != null
				&& arrChargeItemObjectDels.get(postion).getDebitContract()
						.getContractId() != null) {
			editMoney.setText("");
			// ===========show dialog =============
			final Dialog dialogViewInfo = new Dialog(getActivity());
			dialogViewInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogViewInfo.setContentView(R.layout.chagre_dialog_infopayment);
			dialogViewInfo.setTitle(getActivity().getResources().getString(
					R.string.infocongno));
			final TextView txtcongno = (TextView) dialogViewInfo
					.findViewById(R.id.txt_customer_info);
			final Button btnok = (Button) dialogViewInfo
					.findViewById(R.id.btnok);
			txtcongno.setText(getActivity().getResources().getString(
					R.string.tientruocthue)
					+ " "
					+ arrChargeItemObjectDels.get(postion).getDebitContract()
							.getAmountNotTax()
					+ "\n\n"
					+ getActivity().getResources().getString(R.string.tienthue)
					+ " "
					+ arrChargeItemObjectDels.get(postion).getDebitContract()
							.getAmountTax()
					+ "\n\n"
					+ getActivity().getResources()
							.getString(R.string.dieuchinh)
					+ " "
					+ arrChargeItemObjectDels.get(postion).getDebitContract()
							.getAdjustment()
					+ "\n\n"
					+ getActivity().getResources().getString(
							R.string.tongtienthanhtoan)
					+ " "
					+ arrChargeItemObjectDels.get(postion).getDebitContract()
							.getTotCharge());
			btnok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialogViewInfo.dismiss();
					editMoney.setText(""
							+ arrChargeItemObjectDels.get(p).getDebitContract()
									.getTotCharge());
					paymentCount = Long.parseLong(arrChargeItemObjectDels
							.get(p).getDebitContract().getTotCharge());
				}
			});
			dialogViewInfo.show();

		} else {
			editMoney.setText("");
			positionlv = postion;
			// ===== show Asyntask ===============
			if (CommonActivity.isNetworkConnected(getActivity())) {
				GetDeBitContract getBitContractSyn = new GetDeBitContract(
						arrChargeItemObjectDels.get(positionlv), getActivity());
				getBitContractSyn.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

		}

	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_search_acc:
			if (editAcc.getText().toString().isEmpty()) {
				Toast.makeText(
						getActivity(),
						getResources()
								.getString(R.string.checkinfosearchcharge),
						Toast.LENGTH_SHORT).show();
			} else {
				value = editAcc.getText().toString();
				Log.d("value", value);
				Log.d("staffid", staffid);
				if (CommonActivity.isNetworkConnected(getActivity())) {
					SearchContractForPaymentAsyn seForPaymentAsyn = new SearchContractForPaymentAsyn(
							getActivity());
					seForPaymentAsyn.execute();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			}

			break;
		case R.id.btn_view_payment:
			paymentAmount = editMoney.getText().toString();

			if (paymentAmount.isEmpty()) {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.notsotien), Toast.LENGTH_LONG).show();
			} else if (Long.parseLong(paymentAmount) == 0) {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.notsotien), Toast.LENGTH_LONG).show();
			} else if (chargeItemGetDebit.getBillCycleFrom() == null
					&& chargeItemGetDebit.getContractId() == null) {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.notchoiceContract), Toast.LENGTH_LONG)
						.show();
			} else if (Integer.parseInt(paymentAmount) < 0) {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.nhapsoam),
						Toast.LENGTH_LONG).show();
			} else if (Long.parseLong(paymentAmount) > paymentCount) {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.checksotien),
						Toast.LENGTH_SHORT).show();
				editMoney.setText("");
			} else {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					ViewInfoPayMent asynInfoPayMent = new ViewInfoPayMent(
							chargeItemGetDebit, getActivity());
					asynInfoPayMent.execute();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			}
			break;
		default:
			break;
		}
	}

	// =====init spiner partner
    private void initSpinerPartner() {
		if (arrChargeEmployeeOJs != null && arrChargeEmployeeOJs.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ChargeEmployeeOJ employeeOJ : arrChargeEmployeeOJs) {
				adapter.add(employeeOJ.getNameEmpoyee());
			}
			spinnerParter.setAdapter(adapter);
		}
	}

	// =============== asyn get list partner ===============
	public class GetEmployeAsyn extends
			AsyncTask<Void, Void, ArrayList<ChargeEmployeeOJ>> {
		final ProgressDialog progress;
		private Context context = null;
		private String resultemployee = "";
		final String errorCode = "";
		final String description = "";
		XmlDomParse parse = new XmlDomParse();

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
			if (result != null && !result.isEmpty()) {
				arrChargeEmployeeOJs = result;
				initSpinerPartner();
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

				// AlertDialog.Builder alertDialogBuilder = new
				// AlertDialog.Builder(
				// context);
				// alertDialogBuilder.setTitle(context.getResources().getString(
				// R.string.searchemployee));
				// // set dialog message
				// alertDialogBuilder
				// .setMessage(context.getResources().getString(R.string.notemployee))
				// .setCancelable(false)
				// .setPositiveButton(
				// context.getResources().getString(R.string.ok),
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int id) {
				// dialog.dismiss();
				// }
				// });
				// // create alert dialog
				// AlertDialog alertDialog = alertDialogBuilder.create();
				// alertDialog.show();
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
				//
				// String staffCode = parse.getValue(e1, "staffCode");
				// employeeOJ.setStaffCode(staffCode);
				//
				// String staffid = parse.getValue(e1, "staffId");
				// employeeOJ.setEmployeeId(staffid);
				//
				// String staffName = parse.getValue(e1, "staffName");
				// employeeOJ.setNameEmpoyee(staffName);
				//
				// lisChargeEmployeeOJsAsyn.add(employeeOJ);
				// }
				// }
				// Log.d("originalllllllll", original);
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (!output.getErrorCode().equals("0")) {
					resultemployee = output.getErrorCode();
				}
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisChargeEmployeeOJsAsyn;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};

	// ===========Asyntask searchContractforpayment ======================
	private class SearchContractForPaymentAsyn extends
			AsyncTask<Void, Void, ArrayList<ChargeItemObjectDel>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String resultoriginal = "";
		final String errorCode = "";
		final String description = "";

		public SearchContractForPaymentAsyn(Context context) {
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
		protected ArrayList<ChargeItemObjectDel> doInBackground(Void... params) {
			return sendRequestSearchPayment();
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeItemObjectDel> result) {
			progress.dismiss();
			if (result != null && result.size() > 0 && !result.isEmpty()) {

				linearPayMent.setVisibility(View.VISIBLE);
				arrChargeItemObjectDels = result;
				// ======fill data for list customer
				chargeDelAdapter = new ChargeDelAdapter(
						arrChargeItemObjectDels, context);
				lvListCustomer.setAdapter(chargeDelAdapter);
				editAcc.setText("");
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notsearchcustom),
							getResources().getString(R.string.searchcustom));
					dialog.show();

				}
			}
		}

		// =========== method searchContractForPayment
		public ArrayList<ChargeItemObjectDel> sendRequestSearchPayment() {
			String original = null;
			// String errorMessage = null;
			ArrayList<ChargeItemObjectDel> lisChargeItemObjectDels = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_searchContractForPayment");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchContractForPayment>");
				rawData.append("<paymentInput>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("staffId", staffid);
				param.put("value", "" + value);
				rawData.append(input.buildXML(param));
				rawData.append("</paymentInput>");
				rawData.append("</ws:searchContractForPayment>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchContractForPayment");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ======= parse data xml ===================

				ListPaymentContractHandler contractHandler = (ListPaymentContractHandler) CommonActivity
						.parseXMLHandler(new ListPaymentContractHandler(0),
								original);
				lisChargeItemObjectDels = contractHandler
						.getmListItemObjectDels();
				output = contractHandler.getItem();
				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("errorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// nodechild = doc
				// .getElementsByTagName("lstPaymentContractBean");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// ChargeItemObjectDel chaItemObjectDel = new
				// ChargeItemObjectDel();
				//
				// String billCycleFrom = parse.getValue(e1,
				// "billCycleFrom");
				// chaItemObjectDel.setBillCycleFrom(billCycleFrom);
				//
				// String contractId = parse.getValue(e1, "contractId");
				// chaItemObjectDel.setContractId(contractId);
				//
				// String custName = parse.getValue(e1, "custName");
				// chaItemObjectDel.setNameCustomer(custName);
				//
				// String status = parse.getValue(e1, "status");
				// chaItemObjectDel.setStatus(status);
				//
				// String address = parse.getValue(e1, "address");
				// chaItemObjectDel.setAddress(address);
				//
				// String contractNo = parse.getValue(e1, "contractNo");
				// chaItemObjectDel.setContractNo(contractNo);
				//
				// String groupId = parse.getValue(e1, "groupId");
				// chaItemObjectDel.setGroupId(groupId);
				//
				// String isdn = parse.getValue(e1, "isdn");
				// chaItemObjectDel.setIsdn(isdn);
				//
				// lisChargeItemObjectDels.add(chaItemObjectDel);
				// }
				// }
				// VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
				// .parseXMLHandler(new VSAMenuHandler(), original);
				// output = handler.getItem();

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					resultoriginal = output.getDescription();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lisChargeItemObjectDels;

		}
	}

	// ==================== WS get debit contract================
	public class GetDeBitContract extends
			AsyncTask<Void, Void, ChargeItemGetDebit> {
		private Context context = null;
		private final ChargeItemObjectDel chaItemObjectDel;
		String resultoriginal = "";
		final ProgressDialog progress;
		final String errorCode = "";
		final String description = "";

		private GetDeBitContract(ChargeItemObjectDel chaObjectDel,
				Context context) {
			this.context = context;
			this.chaItemObjectDel = chaObjectDel;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ChargeItemGetDebit doInBackground(Void... params) {
			return sendRequestGetdebitContract(positionlv);
		}

		@Override
		protected void onPostExecute(final ChargeItemGetDebit result) {
			progress.dismiss();
			// TODO show ra popup view infomation payment
			if (result.getAdjustment() != null
					&& result.getAmountNotTax() != null
					&& result.getAmountTax() != null) {

				final Dialog dialogViewInfo = new Dialog(context);
				dialogViewInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialogViewInfo
						.setContentView(R.layout.chagre_dialog_infopayment);
				// dialogViewInfo.setTitle(context.getResources().getString(R.string.infocongno));
				final TextView txtcongno = (TextView) dialogViewInfo
						.findViewById(R.id.txt_customer_info);
				final Button btnok = (Button) dialogViewInfo
						.findViewById(R.id.btnok);
				txtcongno.setText(context.getResources().getString(
						R.string.tientruocthue)
						+ " "
						+ result.getAmountNotTax()
						+ "\n\n"
						+ context.getResources().getString(R.string.tienthue)
						+ " "
						+ result.getAmountTax()
						+ "\n\n"
						+ context.getResources().getString(R.string.dieuchinh)
						+ " "
						+ result.getAdjustment()
						+ "\n\n"
						+ context.getResources().getString(
								R.string.tongtienthanhtoan)
						+ " "
						+ result.getTotCharge());
				btnok.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogViewInfo.dismiss();
						editMoney.setText("" + result.getTotCharge());
						paymentCount = Long.parseLong(result.getTotCharge());
						// ====== init ChargeItemGetDebit =====>>> truyen vao de
						// xem thong tin thanh toan
						chargeItemGetDebit = result;
					}
				});
				dialogViewInfo.show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.infocongno),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context,
							context.getResources().getString(
									R.string.notthongtincongno),
							context.getResources().getString(
									R.string.infocongno));
					dialog.show();
				}

			}

		}

		// ======== send get debitcontract===============
		public ChargeItemGetDebit sendRequestGetdebitContract(int position) {
			String original = null;
			String errorMessage = null;
			XmlDomParse parse = new XmlDomParse();
			ChargeItemGetDebit chargeItemGetDebit = new ChargeItemGetDebit();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getDebitContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getDebitContract>");
				rawData.append("<paymentInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				HashMap<String, String> rawDataItem = new HashMap<>();
				rawDataItem.put("billCycleFrom",
						arrChargeItemObjectDels.get(position)
								.getBillCycleFrom());
				rawDataItem.put("contractId",
						""
								+ arrChargeItemObjectDels.get(position)
										.getContractId());
				param.put("paymentContractBean", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));

				rawData.append("</paymentInput>");
				rawData.append("</ws:getDebitContract>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getDebitContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ========parse xml =====================

				ListPaymentContractHandler handlerPaymentContract = (ListPaymentContractHandler) CommonActivity
						.parseXMLHandler(new ListPaymentContractHandler(
								position), original);
				// output = handlerPaymentContract.getItem();
				chargeItemGetDebit = handlerPaymentContract.getmObj();

				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// description = parse.getValue(e2, "description");
				//
				// nodechild = doc.getElementsByTagName("paymentContractBean");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				//
				// Element e1 = (Element) nodechild.item(j);
				//
				// String adjustment = parse.getValue(e1, "adjustment");
				// chargeItemGetDebit.setAdjustment(adjustment);
				//
				// String amountNotTax = parse.getValue(e1, "amountNotTax");
				// chargeItemGetDebit.setAmountNotTax(amountNotTax);
				//
				// String amountTaxString= parse.getValue(e1, "amountTax");
				// chargeItemGetDebit.setAmountTax(amountTaxString);
				//
				// String totCharge = parse.getValue(e1, "totCharge");
				// chargeItemGetDebit.setTotCharge(totCharge);
				//
				// String billCycleFrom = parse.getValue(e1, "billCycleFrom");
				// chargeItemGetDebit.setBillCycleFrom(billCycleFrom);
				//
				// String contractId = parse.getValue(e1, "contractId");
				// chargeItemGetDebit.setContractId(contractId);
				//
				// }
				// }

				arrChargeItemObjectDels.get(position).setDebitContract(
						chargeItemGetDebit);
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					resultoriginal = output.getDescription();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return chargeItemGetDebit;
		}
	}

	// ====================WS
	// ViewInfoPayment=========================================
	private class ViewInfoPayMent extends
			AsyncTask<Void, Void, ChargeViewInfoContract> {
		private final Context context;
		final ChargeItemGetDebit chaGetDebit;
		final ProgressDialog progressviewinfo;
		String resultoriginal = "";
		String errorCode = "";
		String description = "";

		private ViewInfoPayMent(ChargeItemGetDebit chargeItemGetDebit,
				Context context) {
			this.context = context;
			this.chaGetDebit = chargeItemGetDebit;
			this.progressviewinfo = new ProgressDialog(this.context);
			// check font

			this.progressviewinfo.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progressviewinfo.isShowing()) {
				this.progressviewinfo.show();
			}
		}

		@Override
		protected ChargeViewInfoContract doInBackground(Void... params) {
			return sendRequestInforPayMent(chaGetDebit);
		}

		@Override
		protected void onPostExecute(final ChargeViewInfoContract result) {
			// TODO show dialog payment
			progressviewinfo.dismiss();
			if (result.getInvoiceTypeName() != null
					|| result.getPaymentAmount() != null
					|| result.getPaymentCurrency() != null) {
				// ///==============process continue===========

				chargeViewInfoContract = result;
				String mes = context.getResources().getString(
						R.string.loaihoadon)
						+ " "
						+ result.getInvoiceTypeName()
						+ "\n\n"
						+ context.getResources().getString(
								R.string.sotienhientai)
						+ " "
						+ result.getPaymentCurrency()
						+ "\n\n"
						+ context.getResources().getString(R.string.sohoadon)
						+ " "
						+ result.getInvoiceNo()
						+ "\n\n"
						+ context.getResources().getString(R.string.tongsotien)
						+ " " + result.getPaymentAmount();
				dialogViewInfoCongno = CommonActivity.createDialog(
						getActivity(), mes,
						getResources().getString(R.string.infopayment),

						getResources().getString(R.string.cancel),
						getResources().getString(R.string.charge_del ),
						null,viewPaymentClick );
				dialogViewInfoCongno.show();
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.infopayment),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context,
							context.getResources().getString(
									R.string.notinfopayment),
							context.getResources().getString(
									R.string.infopayment));
					dialog.show();
				}

			}

		}

		// ======== send get debitcontract===============
		public ChargeViewInfoContract sendRequestInforPayMent(
				ChargeItemGetDebit chargeItemGetDebit) {
			String original = null;
			String errorMessage = null;
			XmlDomParse parse = new XmlDomParse();
			ChargeViewInfoContract chargeViewInfoContract = new ChargeViewInfoContract();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_analyzePaymentInvoice");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:analyzePaymentInvoice>");
				rawData.append("<paymentInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				HashMap<String, String> rawDataItem = new HashMap<>();
				rawDataItem.put("billCycleFrom",
						chargeItemGetDebit.getBillCycleFrom());
				rawDataItem.put("contractId",
						"" + chargeItemGetDebit.getContractId());
				rawDataItem.put("paymentAmount", paymentAmount);
				rawDataItem.put("totCharge",
						"" + chargeItemGetDebit.getTotCharge());
				param.put("paymentContractBean", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));

				rawData.append("</paymentInput>");
				rawData.append("</ws:analyzePaymentInvoice>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_analyzePaymentInvoice");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ========parse xml =====================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodeChildrent = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e3 = (Element) nl.item(i);
					errorCode = parse.getValue(e3, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e3, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("paymentContractBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String adjustment = parse.getValue(e1, "adjustment");
						chargeViewInfoContract.setAdjustment(adjustment);
						String billCycleFrom = parse.getValue(e1,
								"billCycleFrom");
						Log.d("billCycleFrom", billCycleFrom);
						chargeViewInfoContract.setBillCycleFrom(billCycleFrom);
						String contractId = parse.getValue(e1, "contractId");
						Log.d("contractId", contractId);
						chargeViewInfoContract.setContractId(contractId);
						String contractNo = parse.getValue(e1, "contractNo");
						Log.d("contractNo", contractNo);
						chargeViewInfoContract.setContractNo(contractNo);
						String custName = parse.getValue(e1, "custName");
						Log.d("custName", custName);
						chargeViewInfoContract.setCustName(custName);
						String groupId = parse.getValue(e1, "groupId");
						Log.d("groupId", groupId);
						chargeViewInfoContract.setGroupId(groupId);
						String isdn = parse.getValue(e1, "isdn");
						chargeViewInfoContract.setIsdn(isdn);
						String paymentAmountString = parse.getValue(e1,
								"paymentAmount");
						Log.d("paymentAmountString", paymentAmountString);
						chargeViewInfoContract
								.setPaymentAmount(paymentAmountString);
						nodeChildrent = doc.getElementsByTagName("lstInvoice");
						for (int k = 0; k < nodeChildrent.getLength(); k++) {
							Element e2 = (Element) nodeChildrent.item(k);
							String amountm = parse.getValue(e2, "amount");
							Log.d("amountm", amountm);
							chargeViewInfoContract.setAmount(amountm);
							String amountNotTax = parse.getValue(e2,
									"amountNotTax");
							Log.d("amountNotTax", amountNotTax);
							chargeViewInfoContract
									.setAmountNotTax(amountNotTax);
							String amountTax = parse.getValue(e2, "amountTax");
							Log.d("amountTax", amountTax);
							chargeViewInfoContract.setAmountTax(amountTax);
							String invoiceNo = parse.getValue(e2, "invoiceNo");
							Log.d("invoiceNo", invoiceNo);
							chargeViewInfoContract.setInvoiceNo(invoiceNo);
							String invoiceType = parse.getValue(e2,
									"invoiceType");
							Log.d("invoiceType", invoiceType);
							chargeViewInfoContract.setInvoiceType(invoiceType);
							String invoiceTypeCore = parse.getValue(e2,
									"invoiceTypeCore");
							Log.d("invoiceTypeCore", invoiceTypeCore);
							String invoiceTypeName = parse.getValue(e2,
									"invoiceTypeName");
							Log.d("invoiceTypeName", invoiceTypeName);
							chargeViewInfoContract
									.setInvoiceTypeName(invoiceTypeName);
							String originalInvoiceType = parse.getValue(e2,
									"originalInvoiceType");
							Log.d("originalInvoiceType", originalInvoiceType);
							chargeViewInfoContract
									.setOriginalInvoiceType(originalInvoiceType);
							String paymentCurrencyString = parse.getValue(e2,
									"paymentCurrency");
							Log.d("paymentCurrency", paymentCurrencyString);
							chargeViewInfoContract
									.setPaymentCurrency(paymentCurrencyString);
							String paymentDebit = parse.getValue(e2,
									"paymentDebit");
							Log.d("paymentDebit", paymentDebit);
							chargeViewInfoContract
									.setPaymentDebit(paymentDebit);
							String tax = parse.getValue(e2, "tax");
							chargeViewInfoContract.setTax(tax);
							Log.d("tax", tax);
						}
					}
				}
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					resultoriginal = output.getDescription();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return chargeViewInfoContract;
		}

	}

	// click view payment
    private final android.view.View.OnClickListener viewPaymentClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Dialog dialog = CommonActivity.createDialog(getActivity(),
					getResources().getString(R.string.ischargedel),
					getResources().getString(R.string.charge_del),
					getResources().getString(R.string.cancel), getResources()
							.getString(R.string.ok ),null , paymentclick );
			dialog.show();

		}
	};

	// method click
    private final OnClickListener paymentclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				PaymentAsyn paAsyn = new PaymentAsyn(chargeViewInfoContract,
						getActivity());
				paAsyn.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

		}
	};

	// ============ websevice payment
	// ======================\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////
	public class PaymentAsyn extends
			AsyncTask<ChargeViewInfoContract, Void, String> {
		private final Context context;
		private final ChargeViewInfoContract chargeViewInfoContract;
		final ProgressDialog progressviewinfo;
		final String errorCode = "";
		final String description = "";

		private PaymentAsyn(ChargeViewInfoContract chargeViewInfoContract,
				Context context) {
			this.context = context;
			this.chargeViewInfoContract = chargeViewInfoContract;
			this.progressviewinfo = new ProgressDialog(this.context);
			// check font
			this.progressviewinfo.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progressviewinfo.isShowing()) {
				this.progressviewinfo.show();
			}
		}

		@Override
		protected String doInBackground(ChargeViewInfoContract... params) {
			return sendRequestPayment(chargeViewInfoContract);
		}

		@Override
		protected void onPostExecute(String result) {
			progressviewinfo.dismiss();
			if (result.equals("0")) {
				Log.d("result", result);

				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						context.getResources().getString(
								R.string.chargedelsucess), getResources()
								.getString(R.string.charge_del),
						checkchargeDelClick);
				dialog.show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getResources().getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.chargedelfails),
							getResources().getString(R.string.charge_del),
							checkNOchargeDelClick);
					dialog.show();
				}
			}
		}

		// event check chargedel click
        final OnClickListener checkNOchargeDelClick = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogViewInfoCongno.dismiss();
			}
		};

		// event check chargedel click
        final OnClickListener checkchargeDelClick = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogViewInfoCongno.dismiss();
				arrChargeItemObjectDels.remove(arrChargeItemObjectDels
						.get(positionlv));
				chargeDelAdapter.notifyDataSetChanged();
			}
		};

		public String sendRequestPayment(
				ChargeViewInfoContract chargeViewInfoContract) {
			String original = null;
			String errorMessage = null;
			XmlDomParse parse = new XmlDomParse();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_payment");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:payment>");
				rawData.append("<paymentInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				HashMap<String, String> rawDataItem = new HashMap<>();
				rawDataItem.put("totCharge", chargeItemGetDebit.getTotCharge());
				rawDataItem.put("paymentAmount", paymentAmount);
				rawDataItem.put("contractId",
						chargeItemGetDebit.getContractId());
				rawDataItem.put("staffId", staffid);
				rawDataItem.put("custName",
						arrChargeItemObjectDels.get(positionlv)
								.getNameCustomer());
				rawDataItem.put("address",
						arrChargeItemObjectDels.get(positionlv).getAddress());
				rawDataItem.put("collectionStaffBean", Session.userName);
				rawDataItem
						.put("contractNo",
								arrChargeItemObjectDels.get(positionlv)
										.getContractNo());
				rawDataItem.put("groupId",
						arrChargeItemObjectDels.get(positionlv).getGroupId());
				rawDataItem.put("isdn", arrChargeItemObjectDels.get(positionlv)
						.getIsdn());
				// rawDataItem.put("adjustment",
				// arrChargeItemObjectDels.get(positionlv).getAdjustment());
				// adjustment

				HashMap<String, String> rawDataItemlvchoice = new HashMap<>();
				rawDataItemlvchoice.put("amount",
						chargeViewInfoContract.getAmount());
				rawDataItemlvchoice.put("amountNotTax",
						chargeViewInfoContract.getAmountNotTax());
				rawDataItemlvchoice.put("amountTax",
						chargeViewInfoContract.getAmountTax());
				rawDataItemlvchoice.put("invoiceNo",
						chargeViewInfoContract.getInvoiceNo());
				rawDataItemlvchoice.put("invoiceType",
						chargeViewInfoContract.getInvoiceType());
				// S rawDataItemlvchoice.put("invoiceTypeCore",
				// chargeViewInfoContract.getInvoiceTypeCore());
				rawDataItemlvchoice.put("invoiceTypeName",
						chargeViewInfoContract.getInvoiceTypeName());
				rawDataItemlvchoice.put("originalInvoiceType",
						chargeViewInfoContract.getOriginalInvoiceType());
				rawDataItemlvchoice.put("paymentCurrency",
						chargeViewInfoContract.getPaymentCurrency());
				rawDataItemlvchoice.put("paymentDebit",
						chargeViewInfoContract.getPaymentDebit());
				rawDataItemlvchoice.put("tax", chargeViewInfoContract.getTax());
				rawDataItem.put("lstInvoice",
						input.buildXML(rawDataItemlvchoice));
				HashMap<String, String> rawDataItemmngtStaff = new HashMap<>();
				rawDataItemmngtStaff.put("groupId", arrChargeItemObjectDels
						.get(positionlv).getGroupId());
				rawDataItemmngtStaff.put("staffCode", staffCode);
				rawDataItemmngtStaff.put("staffId", staffid);
				rawDataItemmngtStaff.put("staffName", staffName);
				rawDataItem.put("mngtStaff",
						input.buildXML(rawDataItemmngtStaff));

				param.put("paymentContractBean", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));
				rawData.append("</paymentInput>");
				rawData.append("</ws:payment>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_payment");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
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

	}

	@Override
	public void setPermission() {
		permission = ";pm_payment;";

	}

}
