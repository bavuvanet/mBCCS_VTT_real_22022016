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
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.InfoCustomerChargeDelAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentUpdateCustype extends Fragment implements OnClickListener,
		OnItemClickListener {

	private EditText edittext_acc;
	private LinearLayout btn_search_acc;
	private ListView lvListCustomer;

	private Activity activity;
	private View mView;
    private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<>();

	private final List<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<>();

    private Button btnHome;
	private TextView txtNameActionBar;


    private Spinner spnCustType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {

			mView = inflater.inflate(R.layout.layout_update_custype, container,
					false);
			edittext_acc = (EditText) mView.findViewById(R.id.edittext_acc);
			btn_search_acc = (LinearLayout) mView
					.findViewById(R.id.btn_search_acc);
			lvListCustomer = (ListView) mView.findViewById(R.id.lv_del_search2);
			
			btn_search_acc.setOnClickListener(this);
			lvListCustomer.setOnItemClickListener(this);
		}
		return mView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		ChargeContractItem chargContractItem = arrChargeItemObjectDels
				.get(arg2);
		AsyncCustType cusAsyncCustType = new AsyncCustType(getActivity(), chargContractItem);
		cusAsyncCustType.execute();
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.updateCustype);
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

	
	private class AsyncCustType extends AsyncTask<String, Void, List<ReasonBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		private final ChargeContractItem chargeContractItem;
		public AsyncCustType(Activity mActivity,ChargeContractItem chargeContractItem) {
			this.mActivity = mActivity;
			this.chargeContractItem = chargeContractItem;
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
			return getCustType();
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
                    showDescription(chargeContractItem, result);
					
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

		private List<ReasonBean> getCustType() {
			List<ReasonBean> lstReasonBean = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustType>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX + "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE + "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:getCustType>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_getCustType");
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
					nodechild = doc.getElementsByTagName("lstApDomainBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReasonBean obj = new ReasonBean();
						obj.setId(parse.getValue(e1, "value"));
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));
						lstReasonBean.add(obj);
					}
				}
				Log.i(Constant.TAG, "getCustType lstReasonBean " + lstReasonBean.size());
			} catch (Exception e) {
				Log.e("getCustType", e.toString(), e);
			}
			return lstReasonBean;
		}
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
	private  Dialog dialogDes;
	private ReasonBean reasonBean;
	private void showDescription(final ChargeContractItem chargeContractItem,
		 final List<ReasonBean> result) {

		dialogDes = new Dialog(activity);
		dialogDes.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogDes.setContentView(R.layout.layout_custype_dialog);
		
		final EditText edtmota = (EditText) dialogDes.findViewById(R.id.edtmota);
		spnCustType = (Spinner) dialogDes.findViewById(R.id.spn_reason);
		List<String> list = new ArrayList<>();
		for (ReasonBean reasonBean : result) {
			list.add(reasonBean.getName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCustType.setAdapter(dataAdapter);
		
//		spnCustType.setOnItemSelectedListener(new )
		
		spnCustType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(result != null && !result.isEmpty()){
					reasonBean = result.get(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
//		spnCustType.setOnItemSelectedListener(new )
		
		dialogDes.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (validateSearch(edtmota.getText().toString().trim())) {
							OnClickListener onclickUpdate = new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									AsynUpdateCustype asynUpdateCustype = new AsynUpdateCustype(
											getActivity());
									asynUpdateCustype.execute(
											""+ chargeContractItem
															.getContractId(),
											reasonBean.getCode(),
											edtmota.getText().toString().trim());
								}
							};

							CommonActivity.createDialog(getActivity(),
									getString(R.string.confirmUpdateCus),
									getString(R.string.app_name),
									getString(R.string.alert_dialog_ok),
									getString(R.string.alert_dialog_no),
									onclickUpdate, null).show();

						}
					}
				});

		dialogDes.findViewById(R.id.btncancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogDes.dismiss();
					}
				});

		dialogDes.show();
	}

	private class AsynUpdateCustype extends AsyncTask<String, Void, String> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		@SuppressWarnings("unused")
		private Context mContext = null;

		public AsynUpdateCustype(Context context) {
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
			return updateCustype(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if(dialogDes != null){
					dialogDes.dismiss();
				}
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.updateCustypeSuccess),
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

		// <ws:updateContractCustType>
		// <input>
		// <token>?</token>
		// <contractId>?</contractId>
		//
		// <custType>?</custType>
		// <description>?</description>
		//
		//
		// </input>
		// </ws:updateContractCustType>
		private String updateCustype(String contractId, String cusType,
				String descriptionUpdate) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateContractCustType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateContractCustType>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");
				rawData.append("<description>").append(descriptionUpdate).append("</description>");

				rawData.append("<custType>").append(cusType).append("</custType>");

				rawData.append("</input>");
				rawData.append("</ws:updateContractCustType>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateContractCustType");
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

	private LoadMoreListView lvListObjectCustomer;
    private EditText edtCusName;
	private EditText edtCusCode;

	private ContractFormMngtBean searchBean;

	private void showDialogListObjectCustommer(
			final ChargeContractItem chargeContractItem) {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_customer);
		lvListObjectCustomer = (LoadMoreListView) dialog
				.findViewById(R.id.lvListObjectCustomer);
        Button btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
        Button btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
		edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);
		edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);

		lvListObjectCustomer
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
//						ContractFormMngtBean contractFormMngtBean = lstContractFormMngtBean
//								.get(position);
//						showDescription(chargeContractItem, contractFormMngtBean);
					}
				});

		lvListObjectCustomer
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						Log.i(Constant.TAG, "lvListObjectCustomer onLoadMore");

						AsyncContractFormMngt asynctaskContractFotmMngt = new AsyncContractFormMngt(
								searchBean, lstContractFormMngtBean.size(),
								lstContractFormMngtBean.size()
										+ Constant.PAGE_SIZE);
						asynctaskContractFotmMngt.execute();
					}
				});

		btnSearchCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(v, activity);
				String customerName = edtCusName.getText().toString().trim();
				String customerCode = edtCusCode.getText().toString().trim();

				if (!CommonActivity.isNullOrEmpty(customerName)
						|| !CommonActivity.isNullOrEmpty(customerCode)) {
					if (CommonActivity.isNetworkConnected(activity)) {
						searchBean = new ContractFormMngtBean();
						searchBean.setName(edtCusName.getText().toString()
								.trim());
						searchBean.setCode(edtCusCode.getText().toString()
								.trim());

						lstContractFormMngtBean.clear();
						AsyncContractFormMngt asynctaskContractFotmMngt = new AsyncContractFormMngt(
								searchBean, lstContractFormMngtBean.size(),
								lstContractFormMngtBean.size()
										+ Constant.PAGE_SIZE);
						asynctaskContractFotmMngt.execute();
					} else {
						CommonActivity.createAlertDialog(activity,
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity
							.createAlertDialog(
									activity,
									getString(R.string.message_not_input_customer_name_or_name),
									getString(R.string.app_name)).show();

				}
			}
		});

		btnDeleteCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

        dialog.show();

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

	private class AsyncContractFormMngt extends
			AsyncTask<String, Void, List<ContractFormMngtBean>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		private final ContractFormMngtBean contractBean;

		private final int pageIndex;
		private final int pageSize;

		public AsyncContractFormMngt(ContractFormMngtBean contractBean,
				int _pageIndex, int _pageSize) {
			this.contractBean = contractBean;
			this.pageIndex = _pageIndex;
			this.pageSize = _pageSize;

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
		protected List<ContractFormMngtBean> doInBackground(String... params) {

			return getContractFormMngt();
		}

		@Override
		protected void onPostExecute(List<ContractFormMngtBean> result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					// them danh sach
					lstContractFormMngtBean.addAll(result);
                    CustomerObjectAdapter customerObjectAdapter = new CustomerObjectAdapter(activity,
                            lstContractFormMngtBean);
					lvListObjectCustomer.setAdapter(customerObjectAdapter);
					customerObjectAdapter.notifyDataSetChanged();
				} else {
					if (lstContractFormMngtBean.isEmpty()) {
						CustomerObjectAdapter customerObjectAdapter = new CustomerObjectAdapter(activity,
								lstContractFormMngtBean);
						lvListObjectCustomer.setAdapter(customerObjectAdapter);
						customerObjectAdapter.notifyDataSetChanged();
//						lvListObjectCustomer.setAdapter(null);
						CommonActivity
								.createAlertDialog(
										getActivity(),
										getString(R.string.message_not_result_customer),
										getString(R.string.app_name)).show();
					}
				}
				Log.d(Constant.TAG, "onPostExecute: " + result.size());
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

		private List<ContractFormMngtBean> getContractFormMngt() {
			List<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getContractFormMngt");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getContractFormMngt>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<code>").append(contractBean.getCode()).append("</code>");
				rawData.append("<name>").append(contractBean.getName()).append("</name>");

				rawData.append("<pageIndex>").append(pageIndex).append("</pageIndex>");
				rawData.append("<pageSize>").append(pageSize).append("</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:getContractFormMngt>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getContractFormMngt");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", original);

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
						ContractFormMngtBean obj = new ContractFormMngtBean();
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));

						lstContractFormMngtBean.add(obj);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return lstContractFormMngtBean;
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

}
