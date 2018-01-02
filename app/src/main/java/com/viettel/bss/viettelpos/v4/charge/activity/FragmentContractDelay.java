package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractDelayAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentContractDelay extends Fragment implements OnClickListener {

	private Activity mActivity;
	private View mView;
	private Button btnHome;
	private TextView txtNameActionBar;

    private final List<ChargeContractItem> chargeContractItems = new ArrayList<>();

    private Button btn_searchContract;
	private Button btn_addDelay;
	private EditText edt_isdn_account;
	private EditText edt_fromDate;
	private EditText edt_toDate;
	private EditText edt_description;
	private ListView lv_contractList;
	private LinearLayout ll_contractInfo;
	private Spinner spn_reason;

	private final SimpleDateFormat dbUpdateDateTime = new SimpleDateFormat(
			"dd/MM/yyyy");

    private String searchText;

	private String dateNowString = "";
	
	private static final String TYPE = "DELAY_BARRING";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		try {
            Date dateNow = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_contract_delay, container,
					false);

			edt_isdn_account = (EditText) mView
					.findViewById(R.id.edt_isdn_account);
			btn_searchContract = (Button) mView
					.findViewById(R.id.btn_searchContract);
			btn_addDelay = (Button) mView
					.findViewById(R.id.btn_addDelay);
			lv_contractList = (ListView) mView
					.findViewById(R.id.lv_contractList);
			ll_contractInfo = (LinearLayout) mView
					.findViewById(R.id.ll_contractInfo);
			ll_contractInfo.setVisibility(View.GONE);

			edt_fromDate = (EditText) mView.findViewById(R.id.edt_fromDate);
			edt_fromDate.setText(dateNowString);
			edt_toDate = (EditText) mView.findViewById(R.id.edt_toDate);
			edt_toDate.setText(dateNowString);
			
			edt_description = (EditText) mView.findViewById(R.id.edt_description);
			
			spn_reason = (Spinner) mView.findViewById(R.id.spn_reason);

			new DateTimeDialogWrapper(edt_fromDate, mActivity);
			new DateTimeDialogWrapper(edt_toDate, mActivity);

			if (CommonActivity.isNetworkConnected(getActivity())) {
				AsynctaskGetReason asynGetListApproveFinance = new AsynctaskGetReason(getActivity());
				asynGetListApproveFinance.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}

		btn_searchContract.setOnClickListener(this);
		btn_addDelay.setOnClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {

		addActionbar();
		super.onResume();
	}

	private void addActionbar() {
		MainActivity.getInstance().setTitleActionBar(R.string.contract_delay);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_searchContract:
			onSearchForDelay();
			break;
		case R.id.btn_addDelay:
			onAddDelay();
			break;
		default:
			break;
		}
	}

	private void onSearchForDelay() {
		String isdnAccount = CommonActivity.checkStardardIsdn(edt_isdn_account.getText().toString().trim());

		Dialog dialogError = null;
		if (CommonActivity.isNullOrEmpty(isdnAccount)) {
			dialogError = CommonActivity.createAlertDialog(mActivity,
					getResources().getString(R.string.please_input_isdn),
					getString(R.string.app_name));
		}
		if (dialogError != null) {
			dialogError.show();
			return;
		}

		AsynctaskSearchForDelay asynctaskSearchForDelay = new AsynctaskSearchForDelay(
				mActivity);
		asynctaskSearchForDelay.execute(isdnAccount);

	}

	private final OnClickListener onClickAddDelayConfirm = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			AsynctaskAddDelay asynctaskAddDelay = new AsynctaskAddDelay(
					mActivity);
			asynctaskAddDelay.execute();
		}
	};

	private void onAddDelay() {
//		Log.i("addDelay", "start");
		String isdnAccount = searchText;

		if (CommonActivity.isNullOrEmpty(isdnAccount)
				|| chargeContractItems == null) {
			CommonActivity.createAlertDialog(mActivity,
					getResources().getString(R.string.please_input_isdn),
					getString(R.string.app_name)).show();
//			Log.i("addDelay", "please_input_isdn");
			return;
		}
		
		try {
			Date fromDate = dbUpdateDateTime.parse(edt_fromDate.getText().toString());
			Date toDate = dbUpdateDateTime.parse(edt_toDate.getText().toString());
			if (fromDate.after(toDate)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checktimeupdatejob),
						getString(R.string.app_name)).show();
//				Log.i("addDelay", "checktimeupdatejob");
				return;
			}

		} catch (Exception ex) {
			Log.e("onAddDelay", "parseError: ", ex);
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.exception),
					getString(R.string.app_name)).show();
//			Log.i("addDelay", "start");
			return;
		}
		
		Spin reason = (Spin) spn_reason.getSelectedItem();
		if (CommonActivity.isNullOrEmpty(reason.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.choose_reason_not),
					getString(R.string.app_name)).show();
//			Log.i("addDelay", "choose_reason_not");
			return;
		}
		
		
		CommonActivity.createDialog(getActivity(),
				getActivity().getString(R.string.addDelayConfirm),
				getActivity().getString(R.string.app_name),
				getActivity().getString(R.string.cancel),
				getActivity().getString(R.string.ok ),
				null,onClickAddDelayConfirm ).show();

//		Log.i("addDelay", "call");

	}

	@SuppressWarnings("unused")
	private class AsynctaskSearchForDelay extends
			AsyncTask<String, Void, ChargeContractItem> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskSearchForDelay(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ChargeContractItem doInBackground(String... params) {
			return searchForDelay(params[0]);
		}

		@Override
		protected void onPostExecute(ChargeContractItem result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				chargeContractItems.clear();
				if (result != null && !CommonActivity.isNullOrEmpty(result.getContractNo())) {
					chargeContractItems.add(result);
                    ContractDelayAdapter chargeDelAdapter = new ContractDelayAdapter(
                            chargeContractItems, mActivity, true);
					lv_contractList.setAdapter(chargeDelAdapter);
					ll_contractInfo.setVisibility(View.VISIBLE);

					spn_reason.setSelection(0);
					edt_description.setText("");
					edt_fromDate.setText(dateNowString);
					edt_toDate.setText(dateNowString);

				} else {
					ll_contractInfo.setVisibility(View.GONE);
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ChargeContractItem searchForDelay(String isdn) {
			ChargeContractItem result = null;
			String original = "";
			searchText = isdn;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "payment_searchForDelay");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchForDelay>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				// rawData.append("<staffCode>TT_TESTER</staffCode>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");

				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("</input>");
				rawData.append("</ws:searchForDelay>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"payment_searchForDelay");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("mContractBean2");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem obj = new ChargeContractItem();
						obj.setContractNo(parse.getValue(e1, "contractNo"));
						obj.setAddress(parse.getValue(e1, "address"));
						obj.setCustomerName(parse.getValue(e1, "customerName"));
						obj.setDebit(parse.getValue(e1, "debit"));
						result = obj;
						
						break;
					}
					break;
				}

			} catch (Exception e) {
				Log.d("searchForDelay", "error: ", e);
			}
			return result;
		}

	}

	@SuppressWarnings("unused")
	private class AsynctaskAddDelay extends
			AsyncTask<Void, Void, String> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskAddDelay(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			return addDelay();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.addDelaySuccessful),
						getActivity().getString(R.string.app_name),
						addDelaySuccessfulClick).show();
				
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private String addDelay() {
			ChargeContractItem result = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "payment_addDelay");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:addDelay>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<fromDate>");
				rawData.append(edt_fromDate.getText().toString().trim());
				rawData.append("</fromDate>");
				rawData.append("<toDate>");
				rawData.append(edt_toDate.getText().toString().trim());
				rawData.append("</toDate>");
				rawData.append("<isdn>").append(searchText).append("</isdn>");
				
				String textDescription = edt_description.getText().toString().trim();
				rawData.append("<description>").append(textDescription).append("</description>");

				Spin reason = (Spin) spn_reason.getSelectedItem();
				rawData.append("<reasonId>").append(reason.getId()).append("</reasonId>");
				rawData.append("</input>");
				rawData.append("</ws:addDelay>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"payment_searchForDelay");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}

			} catch (Exception e) {
				Log.d("searchForDelay", "error: ", e);
			}
			return errorCode;
		}

	}

	private final OnClickListener addDelaySuccessfulClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			chargeContractItems.clear();
			ll_contractInfo.setVisibility(View.GONE);
			edt_isdn_account.setText("");
			searchText = null;
			
		}
	};

	private class AsynctaskGetReason extends AsyncTask<String, Void, List<Spin>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskGetReason(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<Spin> doInBackground(String... params) {
			return getReason();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.lydotbc),
							getString(R.string.app_name)).show();
				} else {
                    Utils.setDataSpinner(getActivity(), result, spn_reason);
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private List<Spin> getReason() {
			List<Spin> lstReasonBean = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "payment_getReasonByType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonByType>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX + "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE + "</pageSize>");
				rawData.append("<type>" + TYPE + "</type>");
				rawData.append("</input>");
				rawData.append("</ws:getReasonByType>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"payment_getReasonByType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstReasonBean2");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "id"));
						spin.setName(parse.getValue(e1, "code") + " - " + parse.getValue(e1, "name"));
						lstReasonBean.add(spin);
					}
				}
				Log.i(Constant.TAG, "getReason lstReasonBean " + lstReasonBean.size());
			} catch (Exception e) {
				Log.e("getReason", e.toString(), e);
			}
			return lstReasonBean;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

}
