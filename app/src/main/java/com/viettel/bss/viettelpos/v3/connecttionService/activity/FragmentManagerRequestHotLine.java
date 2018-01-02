package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListReceiverequestAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentManagerRequestHotLine extends Fragment implements
		OnClickListener,OnItemClickListener{

	private EditText editusernameortel;
	private LinearLayout btn_search_acc;
	private ListView lvrequest;

	private ArrayList<ReceiveRequestBean> arrReceiveRequestBeans = new ArrayList<ReceiveRequestBean>();
	
	private GetListReceiverequestAdapter adapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(
				R.layout.connection_layout_manager_request_hotline, container,
				false);
		unitView(mView);
		return mView;

	}

	private void unitView(View mView) {
		editusernameortel = (EditText) mView
				.findViewById(R.id.editusernameortel);
		btn_search_acc = (LinearLayout) mView.findViewById(R.id.btn_search_acc);
		btn_search_acc.setOnClickListener(this);
		lvrequest = (ListView) mView.findViewById(R.id.lvrequest);
		lvrequest.setOnItemClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBar();
	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		txtNameActionBar.setText(getResources().getString(
				R.string.searchychotline));
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.searchychotline));
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_search_acc:
			if(CommonActivity.isNetworkConnected(getActivity())){
				if(arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()){
					arrReceiveRequestBeans.clear();
				}
				
				GetListRequestHotlineAsyn getListRequestHotlineAsyn = new GetListRequestHotlineAsyn(getActivity());
				getListRequestHotlineAsyn.execute(editusernameortel.getText().toString().trim());
				
			}else{
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork), getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}
	
	
	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();
			if(ActivityCreateNewRequestHotLine.instance != null){
				ActivityCreateNewRequestHotLine.instance.finish();
			}

		}
	};

	// ws get list request
	private class GetListRequestHotlineAsyn extends
			AsyncTask<String, Void, ArrayList<ReceiveRequestBean>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListRequestHotlineAsyn(Context context) {
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
		protected ArrayList<ReceiveRequestBean> doInBackground(String... arg0) {
			return getListRequest(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ReceiveRequestBean> result) {
			CommonActivity.hideKeyboard(editusernameortel, context);
			progress.dismiss();
			if("0".equals(errorCode)){
				if(result != null && !result.isEmpty()){
					
					arrReceiveRequestBeans = result;
					adapter = new GetListReceiverequestAdapter(result, getActivity());
					lvrequest.setAdapter(adapter);
				}else{
					if(arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()){
						arrReceiveRequestBeans.clear();
					}
					if(adapter != null){
						adapter.notifyDataSetChanged();
					}
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data), getString(R.string.app_name)).show();
				}
			}else{
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
									
									Dialog dialog = CommonActivity
											.createAlertDialog(
													getActivity(),
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
		
		private ArrayList<ReceiveRequestBean> getListRequest(String telorCusname){
			String original = "";
			ArrayList<ReceiveRequestBean> arrReceiveRequestBeans = new ArrayList<ReceiveRequestBean>();
			try{
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListReceiveRequest");
				StringBuilder rawData = new StringBuilder();
				
				rawData.append("<ws:getListReceiveRequest>");
				rawData.append("<hotLineInput>");
				
				rawData.append("<token>" + Session.getToken());
				rawData.append("</token>");
				
				rawData.append("<telOrCusName>" + telorCusname);
				
				rawData.append("</telOrCusName>");
				rawData.append("</hotLineInput>");
				rawData.append("</ws:getListReceiveRequest>");
				
				
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListReceiveRequest");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstReceiveRequest");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
						
						String reciveRequestId = parse.getValue(e1, "reciveRequestId");
						Log.d("reciveRequestId", reciveRequestId);
						receiveRequestBean.setReciveRequestId(reciveRequestId);
						
						String customerName = parse.getValue(e1, "customerName");
						receiveRequestBean.setCustomerName(customerName);
						
						String desciptionSurvey = parse.getValue(e1, "desciptionSurvey");
						receiveRequestBean.setDesciptionSurvey(desciptionSurvey);
						
						String address = parse.getValue(e1, "address");
						receiveRequestBean.setAddress(address);
						
						String tel = parse.getValue(e1, "tel");
						receiveRequestBean.setTel(tel);
						
						String dslam = parse.getValue(e1, "dslam");
						receiveRequestBean.setDslam(dslam);
						
						String contentRequest = parse.getValue(e1, "contentRequest");
						receiveRequestBean.setContentRequest(contentRequest);
						
						String userUpdateSurvey = parse.getValue(e1, "userUpdateSurvey");
						receiveRequestBean.setUserUpdateSurvey(userUpdateSurvey);
						
						String telecomserviceid = parse.getValue(e1, "serviceTypeId");
						receiveRequestBean.setTelecomServiceId(telecomserviceid);
						
						String telecomservicename = parse.getValue(e1, "serviceTypeName");
						receiveRequestBean.setTelecomServiceName(telecomservicename);
						
						String userConnection = parse.getValue(e1, "userConnection");
						receiveRequestBean.setUserConnection(userConnection);
						
						String serviceType = parse.getValue(e1, "serviceTypeCode");
						receiveRequestBean.setServiceType(serviceType);
						
						String dateRequest = parse.getValue(e1, "reciveDate");
						if (dateRequest != null && dateRequest.length() >= 10) {
						String dateconvert = dateRequest.subSequence(0, 10)
									.toString();
							Log.d("dateconvert", dateconvert);
							String[] splitDate = dateconvert.split("-", 3);
							if (splitDate.length == 3) {
								String convertdate = splitDate[2] + "/"
										+ splitDate[1] + "/" + splitDate[0];
								Log.d("convertdate", convertdate);
								receiveRequestBean.setReciveDate(convertdate);
							}
						}
						arrReceiveRequestBeans.add(receiveRequestBean);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return arrReceiveRequestBeans;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		ReceiveRequestBean receiveRequestBean = arrReceiveRequestBeans.get(arg2);
		showDialogViewDetailReceive(receiveRequestBean);
		
		
	}

	private void showDialogViewDetailReceive(final 
			ReceiveRequestBean receiveRequestBean) {
		
		
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_dialog_detail_hotline);
		TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);
		txttitle.setText(getString(R.string.chitietyeucau) + receiveRequestBean.getReciveRequestId());
		
		EditText edtidrequesthotline = (EditText) dialog.findViewById(R.id.edtidrequesthotline);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getReciveRequestId())){
			edtidrequesthotline.setText(receiveRequestBean.getReciveRequestId());
		}
		EditText edttenkhachhang = (EditText) dialog.findViewById(R.id.edttenkhachhang);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getCustomerName())){
			edttenkhachhang.setText(receiveRequestBean.getCustomerName());
		}
		
		EditText edtsodthotline = (EditText) dialog.findViewById(R.id.edtsodthotline);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getTel())){
			edtsodthotline.setText(receiveRequestBean.getTel());
		}
		
		EditText edtdiachihotline = (EditText) dialog.findViewById(R.id.edtdiachihotline);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getAddress())){
			edtdiachihotline.setText(receiveRequestBean.getAddress());
		}
		
		EditText edtnoidunghotline = (EditText) dialog.findViewById(R.id.edtnoidunghotline);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getContentRequest())){
			edtnoidunghotline.setText(receiveRequestBean.getContentRequest());
		}
		
		EditText edtnguoikhaosat = (EditText) dialog.findViewById(R.id.edtnguoikhaosat);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getUserUpdateSurvey())){
			edtnguoikhaosat.setText(receiveRequestBean.getUserUpdateSurvey());
		}
		
		EditText edtndkhaosat = (EditText) dialog.findViewById(R.id.edtndkhaosat);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getDesciptionSurvey())){
			edtndkhaosat.setText(receiveRequestBean.getDesciptionSurvey());
		}
		
		EditText edtdichvuhotline = (EditText) dialog.findViewById(R.id.edtdichvuhotline);
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getTelecomServiceName())){
			edtdichvuhotline.setText(receiveRequestBean.getTelecomServiceName());
		}
		
		Button btnok = (Button) dialog.findViewById(R.id.btnok);
		btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getReciveRequestId()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceType())){
					Bundle bundle = new Bundle();
					Intent intent = new Intent(getActivity() , ActivityCreateNewRequestHotLine.class);
					bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
					intent.putExtras(bundle);
					startActivity(intent);
					
					dialog.dismiss();
					
				}else{
					
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.thieuthongtin), getString(R.string.app_name)).show();
					
				}
			}
		});
		
		
		Button btncanel = (Button) dialog.findViewById(R.id.btncanel);
		btncanel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	


}
