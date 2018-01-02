package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentUpdateComplain extends Fragment implements
		OnClickListener, OnItemClickListener {

	private EditText edittext_acc;
	private LinearLayout btn_search_acc;
	private ListView lvListCustomer;

	private Activity activity;
	private View mView;
    private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<>();
	private Spinner spinReason;
	private List<ReasonBean> arrReasonBeans = new ArrayList<>();
	private ReasonBean reasonBean = null;
	private Button btnHome;
	private TextView txtNameActionBar;


	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {

			mView = inflater.inflate(R.layout.layout_update_complain, container,
					false);
			edittext_acc = (EditText) mView.findViewById(R.id.edittext_acc);
			btn_search_acc = (LinearLayout) mView
					.findViewById(R.id.btn_search_acc);
			lvListCustomer = (ListView) mView.findViewById(R.id.lv_del_search2);
			spinReason = (Spinner) mView.findViewById(R.id.spn_reason);
			btn_search_acc.setOnClickListener(this);
			lvListCustomer.setOnItemClickListener(this);
			spinReason.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(arrReasonBeans != null && !arrReasonBeans.isEmpty()){
						reasonBean = arrReasonBeans.get(arg2);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			
			if(arrReasonBeans != null && !arrReasonBeans.isEmpty()){
				arrReasonBeans = new ArrayList<>();
			}
			
			if(CommonActivity.isNetworkConnected(getActivity())){
				
				AsyncReasonNotify asynNotify = new AsyncReasonNotify(getActivity());
				asynNotify.execute();
				
			}else{
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork), getString(R.string.app_name)).show();
			}
		}
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.updateComplain);
	}
	private boolean validateSearch(String descripton) {

		if (CommonActivity.isNullOrEmpty(descripton)) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.descriptionUpdate),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		if(!CommonActivity.isNullOrEmpty(descripton)){
			if(!StringUtils.CheckCharSpecical(descripton.trim())){
				return true;
			}else{
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkcharspecical), getString(R.string.app_name)).show();
				return false;
				
			}
		}
		return true;
	}
	private  Dialog dialogdes;
	private void showDescription(final ChargeContractItem chargeContractItem,
			final ReasonBean reasonBean) {

		  dialogdes = new Dialog(activity);
		  dialogdes.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialogdes.setContentView(R.layout.layout_description_dialog);

		final EditText edtmota = (EditText) dialogdes.findViewById(R.id.edtmota);
		dialogdes.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (validateSearch(edtmota.getText().toString().trim())) {
							OnClickListener onclickUpdate = new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									AsynUpdateContractComplain asynUpdateCustype = new AsynUpdateContractComplain(
											getActivity());
									asynUpdateCustype.execute(
											""+ chargeContractItem
															.getContractId(),
											reasonBean.getId(),
											edtmota.getText().toString().trim());
								}
							};

							CommonActivity.createDialog(getActivity(),
									getString(R.string.confirmUpdateComplain),
									getString(R.string.app_name),
									getString(R.string.alert_dialog_no),
									getString(R.string.alert_dialog_ok ),
									null,onclickUpdate ).show();

						}
					}
				});

		dialogdes.findViewById(R.id.btncancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogdes.dismiss();
					}
				});

		dialogdes.show();
	}
	
	
	private class AsyncReasonNotify extends AsyncTask<String, Void, List<ReasonBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		
		public AsyncReasonNotify(Activity mActivity) {
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
		protected List<ReasonBean> doInBackground(String... params) {
			return getReason();
		}

		@Override
		protected void onPostExecute(List<ReasonBean> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
				} else {
					arrReasonBeans = new ArrayList<>();
					arrReasonBeans = result;
					ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
					for (ReasonBean reasonBean : arrReasonBeans) {
						adapter.add(reasonBean.getName());
					}
					spinReason.setAdapter(adapter);
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

		private List<ReasonBean> getReason() {
			List<ReasonBean> lstReasonBean = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getReason");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReason>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX + "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE + "</pageSize>");
				rawData.append("<type>" + 1004 + "</type>");
				rawData.append("</input>");
				rawData.append("</ws:getReason>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getReason");
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
					nodechild = doc.getElementsByTagName("lstReasonBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReasonBean obj = new ReasonBean();
						obj.setId(parse.getValue(e1, "id"));
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));
						lstReasonBean.add(obj);
					}
				}
				Log.i(Constant.TAG, "getReason lstReasonBean " + lstReasonBean.size());
			} catch (Exception e) {
				Log.e("getReason", e.toString(), e);
			}
			return lstReasonBean;
		}
	}
	
	
	// tim kiem hop dong
	private void searchContract() {
		String phoneNumber = CommonActivity.checkStardardIsdn(edittext_acc
				.getText().toString().trim());
		if (phoneNumber.length() == 0) {
			CommonActivity
					.createAlertDialog(
							activity,
							getString(R.string.message_please_insert_phonenumber_contractid),
							getString(R.string.app_name)).show();
		} else {
			CommonActivity.hideKeyboard(btn_search_acc, activity);
			AsynctaskSearchContract asynctaskSearchContract = new AsynctaskSearchContract(
					activity);
			asynctaskSearchContract.execute(phoneNumber);
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

	
	private class AsynUpdateContractComplain extends AsyncTask<String, Void, String> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		@SuppressWarnings("unused")
		private Context mContext = null;

		public AsynUpdateContractComplain(Context context) {
			this.mContext = context;
			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);
			this.progress.setMessage(activity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return updateComplain(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				
				if(dialogdes != null){
					dialogdes.dismiss();
				}
				
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.updateComplainSuccess),
						getString(R.string.app_name)).show();
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

//		 <ws:updateContractComplain>
//         <input>
//            <token>?</token>
//            <contractId>?</contractId>
//            <description>?</description>
//            <reasonId>?</reasonId>
//         </input>
//      </ws:updateContractComplain>
		private String updateComplain(String contractId, String reasonId,
				String descriptionUpdate) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateContractComplain");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateContractComplain>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");
				rawData.append("<description>").append(descriptionUpdate).append("</description>");

				rawData.append("<reasonId>").append(reasonId).append("</reasonId>");

				rawData.append("</input>");
				rawData.append("</ws:updateContractComplain>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateContractComplain");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return errorCode;
		}
	}
	
	
	
	
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

			return getlistChargeDelObject(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
					lvListCustomer.setAdapter(null);
				} else {
					arrChargeItemObjectDels = result;
                    InfoCustomerChargeDelAdapter infoChargeDelAdapter = new InfoCustomerChargeDelAdapter(
                            arrChargeItemObjectDels, activity, true);
					lvListCustomer.setAdapter(infoChargeDelAdapter);
				}
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

		private ArrayList<ChargeContractItem> getlistChargeDelObject(
				String phoneNumber) {
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

				rawData.append("<isdn>").append(phoneNumber).append("</isdn>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");
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
				Log.d("getlistChargeDelObject", e.toString());
			}
			return listChargeContractItem;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ChargeContractItem chargContractItem = arrChargeItemObjectDels
				.get(arg2);
		if( reasonBean != null && !CommonActivity.isNullOrEmpty(reasonBean.getId())){
			showDescription(chargContractItem, reasonBean);
		}else{
			CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkreasonUpdate), getString(R.string.app_name)).show();
		}
		
	}

	@Override
	public void onClick(View arg0) {
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
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

}
