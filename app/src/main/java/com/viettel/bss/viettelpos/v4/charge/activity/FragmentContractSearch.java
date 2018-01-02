package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.InfoCustomerChargeDelAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentContractSearch extends FragmentCommon {

	private Activity activity;

	private EditText edittext_acc;
	private ListView lvListCustomer;
	private LinearLayout btnSearchAcc;
    private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<>();

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_search);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_contract_verify_serach;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public void unit(View v) {
		edittext_acc = (EditText) v.findViewById(R.id.edittext_acc);
		btnSearchAcc = (LinearLayout) v.findViewById(R.id.btn_search_acc);
		lvListCustomer = (ListView) v.findViewById(R.id.lv_info_acc);
		btnSearchAcc.setOnClickListener(this);
		lvListCustomer.setOnItemClickListener(this);

		tuantd7();
	}

	private void tuantd7() {
		if (Session.loginUser != null
				&& "tuantm".equalsIgnoreCase(Session.loginUser.getStaffCode()
						.toLowerCase())) {
			edittext_acc.setText("0986567862");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		super.onItemClick(adapter, view, position, id);
        ChargeContractItem chargContractItem = arrChargeItemObjectDels.get(position);
		Log.d(Constant.TAG,
				"FragmentContractSearch " + chargContractItem.toString());

		// FragmentContractVerifySearchDetail fragment = new
		// FragmentContractVerifySearchDetail();
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("chargContractItem", chargContractItem);
		// fragment.setArguments(bundle);
		//
		// ReplaceFragment.replaceFragment(activity, fragment, true);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btn_search_acc:
			if (CommonActivity.isNetworkConnected(activity)) {
				searchContract();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	private void searchContract() {
		String isdn = CommonActivity.checkStardardIsdn(edittext_acc.getText()
				.toString().trim());
		if (isdn.length() == 0) {
			CommonActivity
					.createAlertDialog(
							activity,
							getString(R.string.message_please_insert_phonenumber_contractid),
							getString(R.string.app_name)).show();
		} else {
			CommonActivity.hideKeyboard(btnSearchAcc, activity);
			AsynctaskSearchContract asynctaskSearchContract = new AsynctaskSearchContract(
					activity);
			asynctaskSearchContract.execute(isdn);
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

	private class AsynctaskSearchContract extends
			AsyncTask<String, Void, ArrayList<ChargeContractItem>> {

		private final Activity mActivity;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskSearchContract(Activity mActivity) {

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
		protected ArrayList<ChargeContractItem> doInBackground(String... params) {

			return searchContract(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
					return;
				}

				arrChargeItemObjectDels = result;
                InfoCustomerChargeDelAdapter chargeDelAdapter = new InfoCustomerChargeDelAdapter(
                        arrChargeItemObjectDels, activity, true);
				lvListCustomer.setAdapter(chargeDelAdapter);
			} else {
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

		private ArrayList<ChargeContractItem> searchContract(String isdn) {
			ArrayList<ChargeContractItem> listChargeContractItem = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<isdn>").append(isdn).append("</isdn>");

				rawData.append("</input>");
				rawData.append("</ws:searchContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchContract");
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
					nodechild = doc.getElementsByTagName("lstMContractBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem chargeItemObject = new ChargeContractItem();
						chargeItemObject.setAddress(parse.getValue(e1,
								"address"));
						chargeItemObject.setContractFormMngt(parse.getValue(e1,
								"contractFormMngt"));
						chargeItemObject.setContractFormMngtName(parse
								.getValue(e1, "contractFormMngtName"));
						chargeItemObject.setContractId(parse.getValue(e1,
								"contractId"));
						chargeItemObject.setContractNo(parse.getValue(e1,
								"contractNo"));
						chargeItemObject.setDebit(parse.getValue(e1, "debit"));
						chargeItemObject.setHotCharge(parse.getValue(e1,
								"hotCharge"));
						chargeItemObject.setIsdn(parse.getValue(e1, "isdn"));
						chargeItemObject.setPayMethodCode(parse.getValue(e1,
								"payMethodCode"));
						chargeItemObject.setPayMethodName(parse.getValue(e1,
								"payMethodName"));
						chargeItemObject.setPayer(parse.getValue(e1, "payer"));

						chargeItemObject.setVerifyStatus(parse.getValue(e1,
								"verifyStatus"));
						chargeItemObject.setxPos(parse.getValue(e1, "xPos"));
						chargeItemObject.setyPos(parse.getValue(e1, "yPos"));

						String s = parse.getValue(e1, "paymentStatus");
						if (s == null || s.isEmpty()) {
							s = "0";
						}
						chargeItemObject.setPaymentStatus(s);

						chargeItemObject.setPriorDebit(parse.getValue(e1,
								"priorDebit"));
						chargeItemObject
								.setTelFax(parse.getValue(e1, "telFax"));
						chargeItemObject.setTotCharge(parse.getValue(e1,
								"totCharge"));
						listChargeContractItem.add(chargeItemObject);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG,
						"FragmentContractVerifySearch searchContract", e);
			}
			return listChargeContractItem;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = "";
	}
}
