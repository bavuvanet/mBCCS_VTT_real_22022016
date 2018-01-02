package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListReceiverequestAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FragmentAsignJob extends FragmentCommon implements
		OnClickListener,OnItemClickListener{

	private EditText editusernameortel;
	private LinearLayout btn_search_acc;
	private ListView lvrequest;
	private ArrayList<ReceiveRequestBean> arrReceiveRequestBeans = new ArrayList<ReceiveRequestBean>();
	private GetListReceiverequestAdapter adapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		idLayout = R.layout.connection_layout_manager_request_hotline;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.searchychotline);
		
		if(arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()){
			arrReceiveRequestBeans= new ArrayList<ReceiveRequestBean>();
		}
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}

		
		
	}
	
	@Override
	public void unit(View v) {
		editusernameortel = (EditText) v
				.findViewById(R.id.editusernameortel);
		btn_search_acc = (LinearLayout) v.findViewById(R.id.btn_search_acc);
		btn_search_acc.setOnClickListener(this);
		lvrequest = (ListView) v.findViewById(R.id.lvrequest);
		lvrequest.setOnItemClickListener(this);	
		
//		tuantd7();
	}
	
	private void tuantd7() {
		editusernameortel.setText("0962379696");
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.relaBackHome:
			act.onBackPressed();
			break;
		case R.id.btn_search_acc:
			if(CommonActivity.isNetworkConnected(act)){
				String keyword = editusernameortel.getText().toString().trim();
				if(arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()){
					arrReceiveRequestBeans= new ArrayList<ReceiveRequestBean>();
				}
				if(adapter != null){
					adapter.notifyDataSetChanged();
				}
//				if(CommonActivity.isNullOrEmpty(keyword)) {
//					Toast.makeText(act, getString(R.string.please_input_isdn), Toast.LENGTH_SHORT).show();
//				} else if(StringUtils.CheckCharSpecical(keyword)) {
//					Toast.makeText(act, getString(R.string.checkcharspecical), Toast.LENGTH_SHORT).show();
//				} else {
					GetListRequestHotlineAsyn getListRequestHotlineAsyn = new GetListRequestHotlineAsyn(act);
					getListRequestHotlineAsyn.execute(keyword);
//				}
			} else{
				CommonActivity.createAlertDialog(act, getString(R.string.errorNetwork), getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}
	
	// move login
	private OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(act, LoginActivity.class);
			startActivity(intent);
			act.finish();
			MainActivity.getInstance().finish();
			if(act != null){
				act.finish();
			}
		}
	};

	// ws get list request
	private class GetListRequestHotlineAsyn extends
			AsyncTask<String, Void, ArrayList<ReceiveRequestBean>> {

		private ProgressDialog progress;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
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
					adapter = new GetListReceiverequestAdapter(arrReceiveRequestBeans, act, true);
					lvrequest.setAdapter(adapter);
				}else{
					arrReceiveRequestBeans= new ArrayList<ReceiveRequestBean>();
					adapter = new GetListReceiverequestAdapter(
							arrReceiveRequestBeans, act, true);
					lvrequest.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					CommonActivity.createAlertDialog(act, getString(R.string.no_data), getString(R.string.app_name)).show();
				}
			} else{
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(act,description,
									context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if(description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(act, description, getResources()
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
						Constant.BCCS_GW_URL,act,"mbccs_getListReceiveRequest");
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
						
						String reasonContent = parse.getValue(e1, "reasonContent");
						receiveRequestBean.setReasonContent(reasonContent);
						
						String statusDislay = parse.getValue(e1, "statusDislay");
						receiveRequestBean.setStatusDislay(statusDislay);
						
						String dateRequest = parse.getValue(e1, "reciveDate");
						Log.d(this.getClass().getSimpleName(), "dateRequest: " + dateRequest);
						if (dateRequest != null && dateRequest.length() >= 10) {
							String dateconvert = dateRequest.subSequence(0, 10).toString();
							Log.d(this.getClass().getSimpleName(), "dateconvert: " + dateconvert);
							String[] splitDate = dateconvert.split("-", 3);
							if (splitDate.length == 3) {
								String convertdate = splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
								Log.d(this.getClass().getSimpleName(), "convertdate: " + convertdate);
								receiveRequestBean.setReciveDate(convertdate);
							}
						}
						
						String dateReciveDate = parse.getValue(e1, "dateReciveDate");
						Log.d(this.getClass().getSimpleName(), "dateReciveDate: " + dateReciveDate);
						if (dateReciveDate != null && !dateReciveDate.isEmpty()) {
							receiveRequestBean.setReciveDate(dateReciveDate);
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
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		ReceiveRequestBean receiveRequestBean = (ReceiveRequestBean) adapterView.getAdapter().getItem(position);
		
		FragmentAsignJobDetail fragment = new FragmentAsignJobDetail();
		Bundle bundle = new Bundle();
		bundle.putSerializable("receiveRequestBean", receiveRequestBean);
		fragment.setArguments(bundle);
		 
		ReplaceFragment.replaceFragment(act, fragment, true);
	}

    @Override
    protected void setPermission() {

    }
}
