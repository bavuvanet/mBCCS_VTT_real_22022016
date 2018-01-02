package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentCheckChanelSmartSim  extends Fragment implements OnClickListener{

	private Spinner spinner_typethuebao;
	private TextView tvChooseChannel;

    private String subType = "";
	private static final int REQUEST_CHANGE_CHANNEL = 1;
	private Staff selectedStaff;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_smartsim_checkchanel, container,false);
		unitView(view);
		return view;
	}
	
	// lay thue bao
	private void initSub(){
		final ArrayList<SubTypeBean> arrSubTypeBeans = new ArrayList<>();
		arrSubTypeBeans.add(new SubTypeBean(getString(R.string.subfirst), "1"));
		arrSubTypeBeans.add(new SubTypeBean(getString(R.string.sublast), "2"));
		if(arrSubTypeBeans != null && arrSubTypeBeans.size() > 0){
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (SubTypeBean subTypeBean : arrSubTypeBeans) {
				adapter.add(subTypeBean.getName());
			}
			spinner_typethuebao.setAdapter(adapter);
		}
		
		spinner_typethuebao.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				subType = arrSubTypeBeans.get(arg2).getValue();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
	}
	
	
	private void unitView(View view) {
		
		spinner_typethuebao = (Spinner) view.findViewById(R.id.spinner_typethuebao);
		tvChooseChannel = (TextView) view.findViewById(R.id.tvChooseChannel);
		tvChooseChannel.setOnClickListener(this);


        Button lncheckchanel = (Button) view.findViewById(R.id.lncheckchanel);
		lncheckchanel.setOnClickListener(this);
		
		// khoi tao loai thue bao
		initSub();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.checkchanelsmartsim);
		
		if (selectedStaff != null) {
			tvChooseChannel.setHint(selectedStaff.getName() + " - "
					+ selectedStaff.getStaffCode());
		
		} else {
			tvChooseChannel.setHint(getResources().getString(
					R.string.chooseChannelOrStaff));
		}
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CHANGE_CHANNEL:
				selectedStaff = (Staff) data.getExtras().getSerializable(
						"staff");


				if (selectedStaff != null) {
					tvChooseChannel.setHint(selectedStaff.getName() + " - "
							+ selectedStaff.getStaffCode());
				
				} else {
					tvChooseChannel.setHint(getResources().getString(
							R.string.chooseChannelOrStaff));
				}
				break;
			default:
				break;
			}
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
	
	private class CheckChanelPosAsyn extends AsyncTask<String, Void, String>{
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String cusId = "";
		public CheckChanelPosAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}
		@Override
		protected String doInBackground(String... arg0) {
			return checkChanelPos();
		}
		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if("0".equalsIgnoreCase(errorCode)){
				
				
				// TODO XU LY CHO NAY
				if(cusId != null && !cusId.isEmpty()){
					CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
					custommerByIdNoBean.setCustId(cusId);
					custommerByIdNoBean.setIdNo(selectedStaff.getId_no());
					Bundle bundle = new Bundle();
					bundle.putSerializable("custommerKey", custommerByIdNoBean);
					bundle.putString("subTypeKey", subType);
					bundle.putSerializable("staffKey", selectedStaff);
					bundle.putString("objectCodeKey", selectedStaff.getStaffCode());
					FragmentConnectionMobileSmartSimConnect fragCustomerSmartSimConnect = new FragmentConnectionMobileSmartSimConnect();
					fragCustomerSmartSimConnect.setArguments(bundle);
					ReplaceFragment.replaceFragment(getActivity(), fragCustomerSmartSimConnect, false);
				}else{
					Bundle bundle = new Bundle();
					bundle.putSerializable("staffKey", selectedStaff);
					bundle.putString("subTypeKey", subType);
					
					FragmentInfoCustomerSmartSimConnect fragCustomerSmartSimConnect = new FragmentInfoCustomerSmartSimConnect();
					fragCustomerSmartSimConnect.setArguments(bundle);
					ReplaceFragment.replaceFragment(getActivity(), fragCustomerSmartSimConnect, false);
				}
				
				
				
			}else{
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
					if(description == null || description.isEmpty()){
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}
		
		private String checkChanelPos(){
			String original = null;
			try{
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkChannelPos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkChannelPos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				
				rawData.append("<objectCode>").append(selectedStaff.getStaffCode());
				rawData.append("</objectCode>");
				
				rawData.append("<objectType>" + "2");
				rawData.append("</objectType>");
				
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:checkChannelPos>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_checkChannelPos");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				
				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);	
					cusId = parse.getValue(e2, "cusId");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return errorCode;
		}
		
		
	}
	
	private class CheckChanelPreAsyn extends AsyncTask<String, Void, String>{
		
		
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CheckChanelPreAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			return checkChanelPre();
		}
		
		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if("0".equalsIgnoreCase(errorCode)){
				
				
				// TODO XU LY CHO NAY
				Bundle bundle = new Bundle();
				bundle.putSerializable("staffKey", selectedStaff);
				bundle.putString("subTypeKey", subType);
				
				FragmentInfoCustomerSmartSimConnect fragCustomerSmartSimConnect = new FragmentInfoCustomerSmartSimConnect();
				fragCustomerSmartSimConnect.setArguments(bundle);
				ReplaceFragment.replaceFragment(getActivity(), fragCustomerSmartSimConnect, false);
				
				
			}else{
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
					if(description == null || description.isEmpty()){
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}
		
		
		private String checkChanelPre(){
			String original = null;
			try{
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkChannelPre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkChannelPre>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				
				rawData.append("<objectCode>").append(selectedStaff.getStaffCode());
				rawData.append("</objectCode>");
				
				rawData.append("<objectType>" + "2");
				rawData.append("</objectType>");
				
				
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:checkChannelPre>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_checkChannelPre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				
				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return errorCode;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvChooseChannel:
			FragmentChooseChannel fragment = new FragmentChooseChannel();
			fragment.setTargetFragment(this, REQUEST_CHANGE_CHANNEL);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			break;
		case R.id.lncheckchanel:
			
			if(selectedStaff != null){
				
				
				if(CommonActivity.isNullOrEmpty(selectedStaff.getProvince()) ||
						CommonActivity.isNullOrEmpty(selectedStaff.getDistrict()) ||
						CommonActivity.isNullOrEmpty(selectedStaff.getPrecinct()) ||
						CommonActivity.isNullOrEmpty(selectedStaff.getAddress()) ||
						CommonActivity.isNullOrEmpty(selectedStaff.getHome())||
						CommonActivity.isNullOrEmpty(selectedStaff.getStreet_block()) ||
						CommonActivity.isNullOrEmpty(selectedStaff.getStreet())){
					
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkdiaban), getString(R.string.app_name)).show();
					
					
				}else{
					if(subType.equals("1")){
						CheckChanelPreAsyn checkChanelPreAsyn = new CheckChanelPreAsyn(getActivity());
						checkChanelPreAsyn.execute();
					}else{
						
						CheckChanelPosAsyn checkChanelPosAsyn = new CheckChanelPosAsyn(getActivity());
						checkChanelPosAsyn.execute();
						
					}
				}
			}else{
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkconfirm), getString(R.string.app_name)).show();
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
