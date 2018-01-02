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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
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
import java.util.List;

public class FragmentChargeNotifyUpdate extends FragmentCommon {

	private Activity activity;

    private Spinner spnVerifyNotify, spnReasonNotify;

    private ChargeContractItem chargContractItem;

	private String types[];
	private List<ReasonBean> lstReasonBean = new ArrayList<>();
	private List<ReasonBean> lstReasonBeanNo = new ArrayList<>();
	private ArrayAdapter<String> spinnerArrayAdapter;
	private ArrayAdapter<String> spinnerArrayAdapterNo;
	
	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.charge_notify_update);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		idLayout = R.layout.layout_charge_notify_update;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle bundle = getArguments();
		if (bundle != null) {
			chargContractItem = (ChargeContractItem) bundle.getSerializable("chargContractItem");
			Log.d(Constant.TAG, "FragmentChargeNotifyUpdate " + chargContractItem.toString());
		} else {
			Log.d(Constant.TAG, "FragmentChargeNotifyUpdate bundle NULL");
		}
	}

	@Override
	public void unit(View v) {
        TextView tvContractNo = (TextView) v.findViewById(R.id.tvContractNo);
		tvContractNo.setText(chargContractItem.getContractNo());
		
		spnVerifyNotify = (Spinner) v.findViewById(R.id.spnVerifyNotify);
		spnReasonNotify = (Spinner) v.findViewById(R.id.spnReasonNotify);

        Button btn_update = (Button) v.findViewById(R.id.btn_update);

		spnVerifyNotify.setSelected(false);
		
		spnVerifyNotify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d(Constant.TAG, "spnVerifyType onItemClick position:" + position);
				if (position == 1) {
					spnReasonNotify.setAdapter(spinnerArrayAdapter);
				} else if (position == 2) {
					spnReasonNotify.setAdapter(spinnerArrayAdapterNo);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.d(Constant.TAG, "spnVerifyType onNothingSelected");
			}
		});

		btn_update.setOnClickListener(this);

		types = getResources().getStringArray(R.array.verify_notify_value);
		
		AsyncReasonNotify asyncReasonVerify0 = new AsyncReasonNotify(activity, types[1]);
		
		asyncReasonVerify0.execute();
		
		AsyncReasonNotify asyncReasonVerify1 = new AsyncReasonNotify(activity, types[2]);
		
		asyncReasonVerify1.execute();

	}
	
	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyncUpdateDeliverContract asyncUpdateVerify = new AsyncUpdateDeliverContract(activity, contractId, reasonId, type,
						descript);
				Log.d(Constant.TAG, "FragmentChargeNotifyUpdate onClick btn_update " + asyncUpdateVerify.toString());
				asyncUpdateVerify.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
		}
	};
	
	private String contractId = "";
	private String reasonId = "" ;
	private String type = "";
	private String descript = "";

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btn_update:
			if (CommonActivity.isNetworkConnected(activity)) {
				if(checkUpdate()){
					int posVerifyNotify = spnVerifyNotify.getSelectedItemPosition();
					if(posVerifyNotify > 0) {
						contractId = chargContractItem.getContractId();
						reasonId = "" ;
						type = types[posVerifyNotify];
						descript = "";
						
						if(posVerifyNotify == 1) {
							int posReasonVerify = spnReasonNotify.getSelectedItemPosition();
							ReasonBean reasonBean =  lstReasonBean.get(posReasonVerify);
							reasonId = reasonBean.getId();
						} else if(posVerifyNotify == 2) {
							int posReasonVerify = spnReasonNotify.getSelectedItemPosition();
							ReasonBean reasonBean =  lstReasonBeanNo.get(posReasonVerify);
							reasonId = reasonBean.getId();
						}
						
						CommonActivity.createDialog(activity, getString(R.string.message_confirm_update),
								getString(R.string.app_name), getString(R.string.say_ko), getString(R.string.say_co), null,
								confirmChargeAct).show();
					} else {
						Log.d(Constant.TAG, "FragmentChargeNotifyUpdate onClick btn_update posVerifyNotify: " + posVerifyNotify);
						CommonActivity
						.createAlertDialog(activity, getString(R.string.message_not_sellect_reasoon), getString(R.string.app_name))
						.show();
					}
				}
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
		default:
			break;
		}
	}
	
	private boolean checkUpdate() {
		
		int posVerifyNotify = spnVerifyNotify.getSelectedItemPosition();
		if(posVerifyNotify == 1){
			if (lstReasonBean == null || lstReasonBean.isEmpty()) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.lydotbc),
						getString(R.string.app_name)).show();
				return false;
			}
		}
		if(posVerifyNotify == 2){
			if (lstReasonBeanNo == null || lstReasonBeanNo.isEmpty()) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.lydotbc),
						getString(R.string.app_name)).show();
				return false;
			}
		}
		

		return true;
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

	private class AsyncReasonNotify extends AsyncTask<String, Void, List<ReasonBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		
		private final String type;

		public AsyncReasonNotify(Activity mActivity, String type) {
			this.mActivity = mActivity;
			this.type = type;
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
					CommonActivity.createAlertDialog(mActivity, getString(R.string.lydotbc),
							getString(R.string.app_name)).show();
				} else {
					if(type.equalsIgnoreCase(types[1])) {
						lstReasonBean = result;
						List<String> spinnerArray = new ArrayList<>();
						for (ReasonBean reasonBean : lstReasonBean) {
							spinnerArray.add(reasonBean.getName());
						}
						spinnerArrayAdapter = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, spinnerArray);
						spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					} else if(type.equalsIgnoreCase(types[2])) {
						lstReasonBeanNo = result;
						List<String> spinnerArray = new ArrayList<>();
						for (ReasonBean reasonBean : lstReasonBeanNo) {
							spinnerArray.add(reasonBean.getName());
						}
					    spinnerArrayAdapterNo = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, spinnerArray);
					    spinnerArrayAdapterNo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					}
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
				rawData.append("<type>").append(type).append("</type>");
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

	private class AsyncUpdateDeliverContract extends AsyncTask<String, Void, Boolean> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String errorDescription = "";
		private ProgressDialog progress;

		private final String contractId;
		private final String reasonId;
		private final String type;
		private final String descript;

		public AsyncUpdateDeliverContract(Activity mActivity, String contractId, String reasonId, String type, String descript) {
			super();
			this.mActivity = mActivity;
			this.contractId = contractId;
			this.reasonId = reasonId;
			this.type = type;
			this.descript = descript;
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
		protected Boolean doInBackground(String... params) {
			return updateDeliverContract();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.updatesucess),
							getString(R.string.app_name)).show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, getString(R.string.end_token),
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (errorDescription == null || errorDescription.isEmpty()) {
						errorDescription = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), errorDescription,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Boolean updateDeliverContract() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateDeliverContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateDeliverContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");
				rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
				rawData.append("<type>").append(type).append("</type>");
				rawData.append("<description>").append(descript).append("</description>");

				rawData.append("</input>");
				rawData.append("</ws:updateDeliverContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_updateDeliverContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					errorDescription = parse.getValue(e2, "description");
					Log.d(Constant.TAG,  "FragmentChargeNotifyUpdate updateDeliverContract errorCode: " + errorCode + " errorDescription: " + errorDescription) ;
					if("0".equalsIgnoreCase(errorCode)) {
						
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeNotifyUpdate updateDeliverContract", e);
			}
			return false;
		}

		@Override
		public String toString() {
            return "{\"AsyncUpdateVerify\":{\"contractId\":\"" +
                    contractId + "\", \"reasonId\":\"" +
                    reasonId + "\", \"type\":\"" + type +
                    "\", \"descript\":\"" + descript + "\"}}";
		}
	}
	
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";charge_notify;";
	}
}
