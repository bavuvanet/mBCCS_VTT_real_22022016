package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.StaffSelectAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnChangeCheckedState;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentSendSmsCtv extends FragmentCommon implements
		OnClickListener, OnChangeCheckedState<ChargeEmployeeOJ> {

	private static final int NUM_OF_CHARS = 320;
	private Activity mActivity;

	private LinearLayout ll_info;
	private ListView lv_ctvList;
	private TextView tv_overall;
	private CheckBox cb_checkAll;
    private TextView tv_sendSmsContentChar;
	private TextView tv_sendSmsContentToBeSent;
	private Button btn_sendSms;
	private int countSelected;
	private int totalSelectable;
	
	private List<ChargeEmployeeOJ> chargeEmployeeOJs;
	
	private StaffSelectAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_sms_ctv;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.sendSms);
		super.onResume();
	}
	
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		ll_info = (LinearLayout) v.findViewById(R.id.ll_info);
		ll_info.setVisibility(View.GONE);
		lv_ctvList = (ListView) v.findViewById(R.id.lv_ctvList);
		tv_overall = (TextView) v.findViewById(R.id.tv_overall);
		cb_checkAll = (CheckBox) v.findViewById(R.id.cb_checkAll);
		cb_checkAll.setOnClickListener(this);
        EditText edt_sendSmsContent = (EditText) v.findViewById(R.id.edt_sendSmsContent);
		tv_sendSmsContentChar = (TextView) v.findViewById(R.id.tv_sendSmsContentChar);
		tv_sendSmsContentToBeSent = (TextView) v.findViewById(R.id.tv_sendSmsContentToBeSent);
		edt_sendSmsContent = (EditText) v.findViewById(R.id.edt_sendSmsContent);
		btn_sendSms = (Button) v.findViewById(R.id.btn_sendSms);

		btn_sendSms.setOnClickListener(this);
		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncTaskGetEmployee asyncTaskGetEmployee = new AsyncTaskGetEmployee(
					getActivity());
			asyncTaskGetEmployee.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name));
			dialog.show();
		}

		tv_sendSmsContentChar.setText(getResources().getString(R.string.sendSmsContentToBeSent) + "0 / " + NUM_OF_CHARS);
		edt_sendSmsContent.addTextChangedListener(new TextWatcher(){
		    public void afterTextChanged(Editable s) {
		    	String content = s.toString().trim();
		    	String nonTonalContent = StringUtils.removeTonal(content);
		    	tv_sendSmsContentToBeSent.setText(nonTonalContent);
		    	if (nonTonalContent.length() > NUM_OF_CHARS) {
		    		tv_sendSmsContentChar.setText(Html.fromHtml(getResources().getString(R.string.sendSmsContentToBeSent) 
		    				+ "<br><font color=\"red\">" + nonTonalContent.length() + "</font> / " + NUM_OF_CHARS));
		    	} else {
		    		tv_sendSmsContentChar.setText(Html.fromHtml(getResources().getString(R.string.sendSmsContentToBeSent) 
		    				+ "<br>" + nonTonalContent.length() + " / " + NUM_OF_CHARS));
		    	}
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		    public void onTextChanged(CharSequence s, int start, int before, int count){}
		}); 	
		
	}

	// class asyn run get list employ data
	public class AsyncTaskGetEmployee extends
			AsyncTask<Void, Void, ArrayList<ChargeEmployeeOJ>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String resultemployee = "";
		final String errorCode = "";
		final String description = "";

		public AsyncTaskGetEmployee(Context context) {
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
			if (result.size() > 0 && !result.isEmpty()) {
				ll_info.setVisibility(View.VISIBLE);
				chargeEmployeeOJs = result;
				totalSelectable = 0;
				for (ChargeEmployeeOJ chargeEmployeeOJ : chargeEmployeeOJs) {
					if (!CommonActivity.isNullOrEmpty(chargeEmployeeOJ.getIsdn())) {
						totalSelectable++;
					}
				}
				adapter = new StaffSelectAdapter(context, chargeEmployeeOJs, FragmentSendSmsCtv.this);
				lv_ctvList.setAdapter(adapter);
				updateTvOverall();
			} else {
//				ll_info.setVisibility(View.GONE);
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
				input.addValidateGateway("wscode", "mbccs_getStaffByMngt");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffByMngt>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</input>");
				rawData.append("</ws:getStaffByMngt>");
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
				StaffBeansHandler handlerListStaffBean = (StaffBeansHandler) CommonActivity
						.parseXMLHandler(new StaffBeansHandler("lstStaffBeanMgnt"), original);
				lisChargeEmployeeOJsAsyn = handlerListStaffBean.getmListObj();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisChargeEmployeeOJsAsyn;
		}

	}

	@Override
	public void onChangeChecked(ChargeEmployeeOJ item, int position) {
		// TODO Auto-generated method stub
		item.setChecked(!item.isChecked());
		if (item.isChecked()) {
			countSelected++;
		} else {
			countSelected--;
		}
		
		cb_checkAll.setChecked(countSelected == chargeEmployeeOJs.size());
		updateTvOverall();
	}
	
	private void updateTvOverall() {
		tv_overall.setText(Html.fromHtml("<b> " + getActivity().getString(R.string.selected) + " " + countSelected 
				+ "/" + totalSelectable + "</b>."));
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.cb_checkAll:
			
			for (ChargeEmployeeOJ item : chargeEmployeeOJs) {
				if (!CommonActivity.isNullOrEmpty(item.getIsdn())) {
					item.setChecked(cb_checkAll.isChecked());
				}
			}
			adapter.notifyDataSetChanged();
			
			if (cb_checkAll.isChecked()) {
				countSelected = totalSelectable;
			} else {
				countSelected = 0;
			}
			updateTvOverall();
			break;
		case R.id.btn_sendSms:
			onSendSms();
			break;
		default:
			break;
		}

	}
	
	private final OnClickListener onClickSendSmsConfirm = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			AsyncTaskSendSms asyncTaskSendSms = new AsyncTaskSendSms(
					mActivity);
			asyncTaskSendSms.execute();

		}
	};

	private void onSendSms() {
		if (countSelected == 0) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.selectedEmpty),
					getString(R.string.app_name)).show();
			return;
		}
		
		String smsContent = tv_sendSmsContentToBeSent.getText().toString().trim();
		if (CommonActivity.isNullOrEmpty(smsContent)) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.smsContentEmpty),
					getString(R.string.app_name)).show();
			return;
		}
		
		if (smsContent.length() > NUM_OF_CHARS) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.smsContentOverflow),
					getString(R.string.app_name)).show();
			return;
		}

		CommonActivity.createDialog(getActivity(),
				getActivity().getString(R.string.sendSmsConfirm),
				getActivity().getString(R.string.app_name),
				getActivity().getString(R.string.cancel),
				getActivity().getString(R.string.ok ),
				null,onClickSendSmsConfirm ).show();

	}

	@SuppressWarnings("unused")
	private class AsyncTaskSendSms extends
			AsyncTask<Void, Void, String> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyncTaskSendSms(Activity mActivity) {
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
			return sendSms();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.sendSmsSuccessful),
						getActivity().getString(R.string.app_name),
						sendSmsSuccessfulClick).show();
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

		private String sendSms() {
			ChargeContractItem result = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkStaffAndSendSms");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkStaffAndSendSms>");
				rawData.append("<paymentInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				
				for (ChargeEmployeeOJ chargeEmployeeOJ : chargeEmployeeOJs) {
					if (chargeEmployeeOJ.isChecked()) {
						rawData.append("<lstStaffId>").append(chargeEmployeeOJ.getEmployeeId()).append("</lstStaffId>");
					}
				}
				String smsContent = tv_sendSmsContentToBeSent.getText().toString().trim();
				rawData.append("<smsContent>").append(smsContent).append("</smsContent>");

				rawData.append("</paymentInput>");
				rawData.append("</ws:checkStaffAndSendSms>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_checkStaffAndSendSms");
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
				Log.d("sendSms", "error: ", e);
			}
			return errorCode;
		}

	}

	private final OnClickListener sendSmsSuccessfulClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			btn_sendSms.setVisibility(View.GONE);
		}
	};

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}


}
