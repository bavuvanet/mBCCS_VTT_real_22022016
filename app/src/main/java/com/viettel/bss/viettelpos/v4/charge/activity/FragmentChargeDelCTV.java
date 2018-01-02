package com.viettel.bss.viettelpos.v4.charge.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.InfoCustomerChargeDelAdapter;
import com.viettel.bss.viettelpos.v4.charge.dialog.DialogDebitSub;
import com.viettel.bss.viettelpos.v4.charge.dialog.DialogUsageCharge;
import com.viettel.bss.viettelpos.v4.charge.object.BankBean;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Network;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customview.InstantAutoComplete;
import com.viettel.bss.viettelpos.v4.sale.adapter.ViewInFoAdapter;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_PHONE_NUMBER;

public class FragmentChargeDelCTV extends GPSTracker{

	private Activity activity;

	private InstantAutoComplete edittext_acc;
//	private EditText edtIsdnCharge;
	private ListView lvListCustomer;
	LinearLayout btnSearchAcc;
	private InfoCustomerChargeDelAdapter chargeDelAdapter = null;
	private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<ChargeContractItem>();
	private ChargeContractItem chargContractItem;

	private List<Spin> lstDelegateStaffCode;

	private List<BankBean> lstBankBeanStatus;

	private Integer isBankPlus = -1;

	private String delegateStaffCode = "";

	private boolean hasPermission;
	private TextView tvIsdnDetail;
	private TextView tvChargeDetail;
	private DialogDebitSub dialogDebitSub;
	private DialogUsageCharge dialogUsageCharge;
	private TextView tvIsdnCharge;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(getString(R.string.charge_del_ctv));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//	
//
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = FragmentChargeDelCTV.this;
		setContentView(R.layout.layout_charge_del_ctv);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		idLayout = R.layout.layout_charge_del_ctv;
		SharedPreferences preferences = activity.getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
        hasPermission = name.contains(Constant.VSAMenu.CHARGE_DEL_CTV_PERMISSION);
		unit();
	}

	public void unit() {
		edittext_acc = (InstantAutoComplete) findViewById(R.id.edittext_acc);

		AutoCompleteUtil.getInstance(this).autoComplete(AUTO_PHONE_NUMBER, edittext_acc);

		btnSearchAcc = (LinearLayout)findViewById(R.id.btn_search_acc);
		lvListCustomer = (ListView) findViewById(R.id.lv_info_acc);
		btnSearchAcc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (CommonActivity.isNetworkConnected(activity)) {
					searchContract();
				} else {
					CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name)).show();
				}
			}
		});
		lvListCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				chargContractItem = arrChargeItemObjectDels.get(i);
				showDialogChargDetail(chargContractItem);
			}
		});

//		AsynctaskCheckDeployBankPlus asynctaskCheckDeployBankPlus = new AsynctaskCheckDeployBankPlus(activity);
//		asynctaskCheckDeployBankPlus.execute();
		Bundle bundle = this.getIntent().getExtras();
		if(!CommonActivity.isNullOrEmpty(bundle)){
			String isdn = bundle.getString("SUB_BEAN_ISDN", "");
			if(!CommonActivity.isNullOrEmpty(isdn)){
				edittext_acc.setText(isdn);

				if (CommonActivity.isNetworkConnected(activity)) {
					searchContract();
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			}
		}



//		 test();
	}

	private void test() {
		edittext_acc.setText("462620740");
	}



	private Button btnChargeDel;
	private Button btnOut;
	private TextView tvCustomerName;
	private TextView tvCodeContract;
	private TextView tvPhoneNumber;
	private TextView tvStatePayment;
	private TextView tvChargeBefor;
	private TextView tvChargeOrther;
	private TextView tvTotalCharge;
	private EditText edtAmountPayment;

	private Spinner spnDelegateStaffCode;
	private LinearLayout lnDelegateStaffCode;

	private Dialog dialog;

	private void showDialogChargDetail(final ChargeContractItem chargeContract) {

		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_charge_customer_detail);
		dialogDebitSub = null;
		dialogUsageCharge = null;
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		tvIsdnDetail = (TextView)dialog.findViewById(R.id.tvIsdnDetail);
		tvChargeDetail = (TextView)dialog.findViewById(R.id.tvChargeDetail);

		tvCustomerName = (TextView) dialog.findViewById(R.id.tvCustomerName);
		tvCodeContract = (TextView) dialog.findViewById(R.id.tvCodeContract);
		tvPhoneNumber = (TextView) dialog.findViewById(R.id.tvPhoneNumber);
		tvStatePayment = (TextView) dialog.findViewById(R.id.tvStatePayment);
		tvChargeBefor = (TextView) dialog.findViewById(R.id.tvChargeBefor);
		tvChargeOrther = (TextView) dialog.findViewById(R.id.tvChargOrther);
		tvTotalCharge = (TextView) dialog.findViewById(R.id.tvTotalPayment);
		edtAmountPayment = (EditText) dialog.findViewById(R.id.edtAmountPayment);
		tvIsdnCharge = (TextView) dialog.findViewById(R.id.tvIsdnCharge);

		spnDelegateStaffCode = (Spinner) dialog.findViewById(R.id.spnDelegateStaffCode);
		lnDelegateStaffCode = (LinearLayout) dialog.findViewById(R.id.lnDelegateStaffCode);

		tvCustomerName.setText(chargeContract.getCustomerName());
		tvCodeContract.setText(chargeContract.getContractNo());
		tvPhoneNumber.setText(chargeContract.getIsdn());
		tvStatePayment.setText(chargeContract.getPayMethodName());
		tvTotalCharge.setText(StringUtils.formatMoney(chargeContract.getTotCharge()));
		tvChargeBefor.setText(StringUtils.formatMoney(chargeContract.getPriorDebit()));
		tvChargeOrther.setText(StringUtils.formatMoney(chargeContract.getDebit()));
		Double totCharge = 0.0D;

		try {
			totCharge = Double.parseDouble(chargeContract.getTotCharge());
		} catch (Exception e) {
			Log.e(Constant.TAG, "du lieu tra ve khong dung dinh dang ", e);
		}
		edtAmountPayment.setText(totCharge.intValue() + "");

		btnOut = (Button) dialog.findViewById(R.id.btnOut);
		btnChargeDel = (Button) dialog.findViewById(R.id.btnChargeDel);

		CommonActivity.showKeyBoard(edtAmountPayment, activity);

		btnOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(edtAmountPayment, activity);
				dialog.dismiss();
			}
		});

		btnChargeDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(btnChargeDel, activity);
				amountPayment = edtAmountPayment.getText().toString().trim();
				String amount = StringUtils.formatMoney(amountPayment);
				Log.d(Constant.TAG, "btnChargeDel onClick amount:" + amount);


				Spin spin = (Spin) spnDelegateStaffCode.getSelectedItem();

				if (amountPayment.isEmpty()) {
					CommonActivity.createAlertDialog(activity, getString(R.string.message_input_amount_payment), getString(R.string.app_name)).show();
				} else {
					if (hasPermission) {
						delegateStaffCode = spin.getId();
					}
					String message = String.format(getString(R.string.message_confirm_payment_param), amount);
					CommonActivity.createDialog(activity, message, getString(R.string.app_name), getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmChargeAct).show();
				}

			}
		});

		tvIsdnDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				if(dialogDebitSub == null){

					dialogDebitSub = new DialogDebitSub(activity,chargeContract.getContractId(),tvIsdnCharge);
				}
				dialogDebitSub.show();
				if(CommonActivity.isNullOrEmpty(dialogDebitSub.getLstData())) {
					dialogDebitSub.searchData();
				}

			}
		});

		tvChargeDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(dialogUsageCharge ==null){
					dialogUsageCharge = new DialogUsageCharge(activity,chargeContract.getContractId());
				}
				dialogUsageCharge.show();
				if(CommonActivity.isNullOrEmpty(dialogUsageCharge.getLstData())){
					dialogUsageCharge.searchData();
				}


			}
		});

		if (hasPermission) {
			if (lstDelegateStaffCode == null || lstDelegateStaffCode.isEmpty()) {
//			AsynctaskGetListBank asynctaskGetListBank = new AsynctaskGetListBank(activity);
//			asynctaskGetListBank.execute();
				AsynctaskGetListStaff asynctaskGetListStaff = new AsynctaskGetListStaff(activity);
				asynctaskGetListStaff.execute();
			} else {
				Utils.setDataSpinner(activity, lstDelegateStaffCode, spnDelegateStaffCode);
			}
		} else {
			lnDelegateStaffCode.setVisibility(View.GONE);
		}

		dialog.show();

	}



	private void searchContract() {
		AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_PHONE_NUMBER, edittext_acc.getText().toString());
		AutoCompleteUtil.getInstance(this).autoComplete(AUTO_PHONE_NUMBER, edittext_acc);

		Log.d(Constant.TAG, "FragmentChargeDelCTV searchContract");

		String phoneNumber = CommonActivity.checkStardardIsdn(edittext_acc.getText().toString().trim());
		if (phoneNumber.length() == 0) {
			CommonActivity.createAlertDialog(activity, getString(R.string.message_please_insert_phonenumber_contractid), getString(R.string.app_name))
					.show();
		} else {
			CommonActivity.hideKeyboard(btnSearchAcc, activity);
			AsynctaskSearchContract asynctaskSearchContract = new AsynctaskSearchContract(activity);
			asynctaskSearchContract.execute(phoneNumber);
		}
	}

	private String amountPayment = "";
	// confirm charge
	private OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				chargContractItem.setaMountCharge(amountPayment);
				AsynctaskPaymentContract asynctaskPaymentContract = new AsynctaskPaymentContract(activity, chargContractItem);
				asynctaskPaymentContract.execute();
			} else {
				CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name)).show();
			}
		}
	};

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentChargeDelCTV.this, LoginActivity.class);
			startActivity(intent);
			FragmentChargeDelCTV.this.finish();
			MainActivity.getInstance().finish();

		}
	};

	@SuppressLint("NewApi")
	private class AsynctaskPaymentContract extends AsyncTask<String, Void, Void> {

		private Activity mActivity;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;
		ChargeContractItem mChargeContractItem;

		public AsynctaskPaymentContract(Activity mActivity, ChargeContractItem mChargeContractItem) {
			this.mActivity = mActivity;
			this.mChargeContractItem = mChargeContractItem;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			paymentContractRequest(mChargeContractItem);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			Log.d(Constant.TAG, "onPostExecute " + errorCode);

			if (errorCode.equals("0")) {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity, description, getString(R.string.app_name));
				dialog.show();
				searchContract();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description, mActivity.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentChargeDelCTV.this, description, getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private void paymentContractRequest(ChargeContractItem mContractItem) {
			Log.d("Log", "begin");

			String original = "";

			try {
				String customerName = mContractItem.getCustomerName();

				customerName = ViewInFoAdapter.convertUnicode2Nosign(customerName);
				if (customerName != null && customerName.length() > 35) {
					customerName = customerName.substring(0, 35);
				}
				customerName = StringUtils.escapeHtml(customerName);


//				String isdnCharge = edtIsdnCharge.getText().toString().trim();

				Log.d("Log", "begin try payment");
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_paymentContractBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:paymentContract>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<contractId>" + mContractItem.getContractId() + "</contractId>");
				rawData.append("<amount>" + mContractItem.getaMountCharge() + "</amount>");
				rawData.append("<clientIp>" + Network.getLocalIpAddress() + "</clientIp>");
				rawData.append("<delegateStaffCode>" + delegateStaffCode + "</delegateStaffCode>");
				String isdnCharge = "";
				if(dialogDebitSub!= null){
					isdnCharge = dialogDebitSub.getIsdnCharge();
				}
				rawData.append("<isdnCharge>" + isdnCharge + "</isdnCharge>");



//				rawData.append("<isdnCharge>" + isdnCharge + "</isdnCharge>");
				rawData.append("<customerName>" + customerName + "</customerName>");
				// rawData.append("<customerName>" +
				// mContractItem.getCustomerName() + "</customerName>");
				rawData.append("</input>");
				rawData.append("</ws:paymentContract>");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Log", "Send evelop " + envelope);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentChargeDelCTV.this, "mbccs_paymentContractBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Log", " Original group " + response);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
				}
			} catch (Exception e) {
				Log.e("Log", e.toString(), e);
			}
			Log.d("Log", "end");

		}
	}

	private class AsynctaskSearchContract extends AsyncTask<String, Void, ArrayList<ChargeContractItem>> {

		private Activity mActivity;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;

		public AsynctaskSearchContract(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected ArrayList<ChargeContractItem> doInBackground(String... params) {

			return searchContract(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_contract), getString(R.string.app_name)).show();
				}

				arrChargeItemObjectDels = result;
				chargeDelAdapter = new InfoCustomerChargeDelAdapter(arrChargeItemObjectDels, activity, false);
				lvListCustomer.setAdapter(chargeDelAdapter);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description, mActivity.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentChargeDelCTV.this, description, getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<ChargeContractItem> searchContract(String phoneNumber) {
			ArrayList<ChargeContractItem> listChargeContractItem = new ArrayList<ChargeContractItem>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchContract>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<isdn>" + phoneNumber + "</isdn>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX + "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE + "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:searchContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentChargeDelCTV.this, "mbccs_searchContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeee", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstMContractBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem chargeItemObject = new ChargeContractItem();
						chargeItemObject.setAddress(parse.getValue(e1, "address"));
						chargeItemObject.setContractFormMngt(parse.getValue(e1, "contractFormMngt"));
						chargeItemObject.setContractFormMngtName(parse.getValue(e1, "contractFormMngtName"));
						chargeItemObject.setContractId(parse.getValue(e1, "contractId"));
						chargeItemObject.setContractNo(parse.getValue(e1, "contractNo"));
						chargeItemObject.setDebit(parse.getValue(e1, "debit"));
						chargeItemObject.setHotCharge(parse.getValue(e1, "hotCharge"));
						chargeItemObject.setIsdn(parse.getValue(e1, "isdn"));
						chargeItemObject.setPayMethodCode(parse.getValue(e1, "payMethodCode"));
						chargeItemObject.setPayMethodName(parse.getValue(e1, "payMethodName"));
						chargeItemObject.setPayer(parse.getValue(e1, "payer"));

						chargeItemObject.setIsdnContract(parse.getValue(e1, "isdnContract"));
						chargeItemObject.setCustomerName(parse.getValue(e1, "customerName"));

						String s = parse.getValue(e1, "paymentStatus");
						if (s == null || s.isEmpty()) {
							s = "0";
						}
						chargeItemObject.setPaymentStatus(s);

						chargeItemObject.setPriorDebit(parse.getValue(e1, "priorDebit"));
						chargeItemObject.setTelFax(parse.getValue(e1, "telFax"));
						chargeItemObject.setTotCharge(parse.getValue(e1, "totCharge"));

						chargeItemObject.setStatus(parse.getValue(e1, "status"));

						listChargeContractItem.add(chargeItemObject);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV searchContract", e);
			}

			return listChargeContractItem;

		}

	}




	private class AsynctaskGetListStaff extends AsyncTask<String, Void, List<Spin>> {

		private Activity mActivity;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskGetListStaff(Activity mActivity) {
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
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<Spin> doInBackground(String... params) {
			return getListStaffs();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.notnganhang), getString(R.string.app_name)).show();
				} else {
					Spin spin = new Spin("", getString(R.string.txt_select));
					lstDelegateStaffCode = new ArrayList<Spin>();
					lstDelegateStaffCode.add(spin);
					lstDelegateStaffCode.addAll(result);

					Utils.setDataSpinner(activity, lstDelegateStaffCode, spnDelegateStaffCode);
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description, mActivity.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentChargeDelCTV.this, description, getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getListStaffs() {
			List<Spin> list = new ArrayList<Spin>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findCtvStaffByShop");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findStaffInShop>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<clientIp>" + Network.getLocalIpAddress() + "</clientIp>");
				rawData.append("</input>");
				rawData.append("</ws:findStaffInShop>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentChargeDelCTV.this, "mbccs_getListBanks");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

					nodechild = doc.getElementsByTagName("lstStaffDTOs2");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "staffCode"));
						spin.setValue(parse.getValue(e1, "staffCode") + " - " + parse.getValue(e1, "name"));

						list.add(spin);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV getListBanks", e);
			}
			return list;
		}
	}


}
