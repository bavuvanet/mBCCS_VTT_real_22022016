package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateConnectMobileOmiActivity extends GPSTrackerFragment implements OnClickListener{

	public TabHost tHost;	
	
	public static CreateConnectMobileOmiActivity instance = null;
	
	public String funtionType = "";
	public SubscriberDTO subscriberDTO = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public ConnectionOrder connectionOrder = new ConnectionOrder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_create_new_request1);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle mBundle = this.getIntent().getExtras();
		if(mBundle  != null){
			connectionOrder = (ConnectionOrder) mBundle.getSerializable("connectionOrder");
			subscriberDTO = (SubscriberDTO) mBundle.getSerializable("subscriberDTOKey");
			Gson g = new Gson();
			Log.d("connectionOrder: ", g.toJson(connectionOrder));
		}

		unitView();
		instance = this;
		
		

	}

	@Override
	protected void onResume() {
		super.onResume();
        getSupportActionBar().setTitle(R.string.servicemobile);

		if (!CommonActivity.isNullOrEmpty(funtionType)){
			if (Constant.CHANGE_POS_TO_PRE.equals(funtionType)) {
				getSupportActionBar().setTitle(getString(R.string.change_pos_to_pre));
			} else if (Constant.CHANGE_PRE_TO_POS.equals(funtionType)) {
				getSupportActionBar().setTitle(getString(R.string.change_pre_to_pos));
			}
		}

	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9", "ActivityCreateNewRequestMobileNew onActivityResult requestCode : " + requestCode);
		
		Fragment fragment = ReplaceFragment.getActiveFragmentActivity(CreateConnectMobileOmiActivity.this);
		
		if(fragment != null && fragment instanceof OmichanelConnectMobileFragment) {
			fragment.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	
	// create unitView
    private void unitView() {
		 tHost = (TabHost) findViewById(android.R.id.tabhost);
	     tHost.setup();
	     
	        /** Defining Tab Change Listener event. This is invoked when tab is changed */
	        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					FragmentManager fm = getSupportFragmentManager();
					OmiChanelCustomerFragment infoFragmentInfoCustomer = (OmiChanelCustomerFragment)  fm.findFragmentByTag(getResources().getString(R.string.info_customer));

					OmichanelConnectMobileFragment subInfoFragment = (OmichanelConnectMobileFragment) fm.findFragmentByTag(getResources().getString(R.string.conecmobile));
					FragmentTransaction ft = fm.beginTransaction();

					/** Detaches the infoFragmentInfoCustomer if exists */
					if(infoFragmentInfoCustomer!=null)
						ft.detach(infoFragmentInfoCustomer);
					
					/** Detaches the infoFragmentInfoExamine if exists */
					if(subInfoFragment!=null)
						ft.detach(subInfoFragment);
					
					/** If current tab is android */
					if(tabId.equalsIgnoreCase(getResources().getString(R.string.info_customer))){
						
						if(infoFragmentInfoCustomer==null){		
							/** Create infoFragmentInfoCustomer and adding to fragmenttransaction */
							if (connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
								Bundle arguments = new Bundle();
								Gson g = new Gson();
								arguments.putSerializable("connectionOrder", connectionOrder);
								infoFragmentInfoCustomer = OmiChanelCustomerFragment.newInstance();

								infoFragmentInfoCustomer.setArguments(arguments);
								ft.add(R.id.realtabcontent,infoFragmentInfoCustomer, getResources().getString(R.string.info_customer));
							} else {
								ft.add(R.id.realtabcontent,OmiChanelCustomerFragment.newInstance(), getResources().getString(R.string.info_customer));
							}


						}else{
							/** Bring to the front, if already exists in the fragmenttransaction */
							ft.attach(infoFragmentInfoCustomer);						
						}



					} else {	
						if(subInfoFragment==null){
							Log.i("DATA","subInfoFragment: 1111111111");
							/** Create infoFragmentInfoExamine and adding to fragmenttransaction */
							subInfoFragment = OmichanelConnectMobileFragment.newInstance(connectionOrder);
							ft.add(R.id.realtabcontent,subInfoFragment, getResources().getString(R.string.conecmobile));

						}else{

							Log.i("DATA","subInfoFragment: 2222222");
							
							if(!OmichanelConnectMobileFragment.subTypeCheck.equalsIgnoreCase(OmiChanelCustomerFragment.subType) ){
								if(OmichanelConnectMobileFragment.custIdentityDTOCheck != null && OmiChanelCustomerFragment.custIdentityDTO != null){
									if(OmichanelConnectMobileFragment.custIdentityDTOCheck.getIdNo() != null && OmiChanelCustomerFragment.custIdentityDTO.getIdNo() != null){
										ft.remove(subInfoFragment);
										if (connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
											Bundle arguments = new Bundle();
											Gson g = new Gson();
											Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(connectionOrder));
											arguments.putSerializable("connectionOrder", connectionOrder);
											subInfoFragment = new OmichanelConnectMobileFragment();

											subInfoFragment.setArguments(arguments);
											ft.add(R.id.realtabcontent,subInfoFragment, getResources().getString(R.string.conecmobile));
										} else {
											ft.add(R.id.realtabcontent,new OmichanelConnectMobileFragment(), getResources().getString(R.string.conecmobile));
										}
								}else{
									ft.remove(subInfoFragment);
										if (connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
											Bundle arguments = new Bundle();
											Gson g = new Gson();
											Log.d("connectionOrder_OmichanelConnectMobileFragment: ", g.toJson(connectionOrder));
											arguments.putSerializable("connectionOrder", connectionOrder);
											subInfoFragment = new OmichanelConnectMobileFragment();

											subInfoFragment.setArguments(arguments);
											ft.add(R.id.realtabcontent,subInfoFragment, getResources().getString(R.string.conecmobile));
										} else {
											ft.add(R.id.realtabcontent,new OmichanelConnectMobileFragment(), getResources().getString(R.string.conecmobile));
										}
									}
								}else{
									ft.remove(subInfoFragment);
									if (connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
										Bundle arguments = new Bundle();
										Gson g = new Gson();
										Log.d("connectionOrder_OmichanelConnectMobileFragment: ", g.toJson(connectionOrder));
										arguments.putSerializable("connectionOrder", connectionOrder);
										subInfoFragment = new OmichanelConnectMobileFragment();

										subInfoFragment.setArguments(arguments);
										ft.add(R.id.realtabcontent,subInfoFragment, getResources().getString(R.string.conecmobile));
									} else {
										ft.add(R.id.realtabcontent,new OmichanelConnectMobileFragment(), getResources().getString(R.string.conecmobile));
									}
								}
								
								
							}else{
								if(OmichanelConnectMobileFragment.custIdentityDTOCheck != null && OmiChanelCustomerFragment.custIdentityDTO != null){
									if(OmichanelConnectMobileFragment.custIdentityDTOCheck.getIdNo() != null && OmiChanelCustomerFragment.custIdentityDTO.getIdNo() != null){
										if(!OmichanelConnectMobileFragment.custIdentityDTOCheck.getIdNo().equalsIgnoreCase(OmiChanelCustomerFragment.custIdentityDTO.getIdNo())){
											
											ft.remove(subInfoFragment);
											if (connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
												Bundle arguments = new Bundle();
												Gson g = new Gson();
												Log.d("connectionOrder_OmichanelConnectMobileFragment: ", g.toJson(connectionOrder));
												arguments.putSerializable("connectionOrder", connectionOrder);
												subInfoFragment = new OmichanelConnectMobileFragment();

												subInfoFragment.setArguments(arguments);
												ft.add(R.id.realtabcontent,subInfoFragment, getResources().getString(R.string.conecmobile));
											} else {
												ft.add(R.id.realtabcontent,new OmichanelConnectMobileFragment(), getResources().getString(R.string.conecmobile));
											}
										}else{
											/** Bring to the front, if already exists in the fragmenttransaction */
											ft.attach(subInfoFragment);
										}
					
									}else{
										/** Bring to the front, if already exists in the fragmenttransaction */
										ft.attach(subInfoFragment);
									}
								}else{
									/** Bring to the front, if already exists in the fragmenttransaction */
									ft.attach(subInfoFragment);
								}
							}
						}
					}
					ft.commit();				
				}
			};
			
			
			/** Setting tabchangelistener for the tab */
			tHost.setOnTabChangedListener(tabChangeListener);
	                
	        TabHost.TabSpec tSpecInfoCus= tHost.newTabSpec(getResources().getString(R.string.info_customer));
	        tSpecInfoCus.setIndicator(getResources().getString(R.string.info_customer),getResources().getDrawable(R.drawable.sale_channel));
	        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
	        tHost.addTab(tSpecInfoCus);


	        
	        setTab();
	     
	}
	
	private void setTab() {
		   TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec( getResources().getString(R.string.conecmobile));
	        tSpecInfoExamine.setIndicator(getResources().getString(R.string.conecmobile),getResources().getDrawable(R.drawable.sale_deposit));
	        tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
	        tHost.addTab(tSpecInfoExamine);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
		onBackPressed();

			break;
		default:
			break;
		}

		
	}
	
}
