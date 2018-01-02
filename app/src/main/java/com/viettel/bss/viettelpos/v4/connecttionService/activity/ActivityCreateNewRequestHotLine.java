package com.viettel.bss.viettelpos.v4.connecttionService.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCreateNewRequestHotLine extends GPSTrackerFragment implements OnClickListener{

	TabHost tHost;	
	
	public static ActivityCreateNewRequestHotLine instance = null;
	public ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
	public InfoSubChild infoSubChild = new InfoSubChild();
	public String technologyKey = "";
	public String technologyNameKey = "";
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_create_new_request1);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.create_new_request_title));
		unitView();
		instance = this;
		Bundle mBundle = this.getIntent().getExtras();
		// get serriable 
		if(mBundle != null){
			receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("ReceiveRequestBeanKey");
			infoSubChild = (InfoSubChild) mBundle.getSerializable("InfoSubKey");
			technologyKey = mBundle.getString("technologyKey", "");
			technologyNameKey = mBundle.getString("technologyNameKey","");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(getResources().getString(R.string.create_new_request_title));
	}
	// create unitView
    private void unitView(){
		 tHost = (TabHost) findViewById(android.R.id.tabhost);
	     tHost.setup();
	     
	        /** Defining Tab Change Listener event. This is invoked when tab is changed */
	        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
					FragmentInfoCustomerHotLine infoFragmentInfoCustomerHotLine = (FragmentInfoCustomerHotLine) fm.findFragmentByTag(getResources().getString(R.string.info_customer));
					FragmentInfoExamineHotLine infoFragmentInfoExamineHotLine = (FragmentInfoExamineHotLine) fm.findFragmentByTag(getResources().getString(R.string.infokhaosat));
					android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
					
					/** Detaches the infoFragmentInfoCustomerHotLine if exists */
					if(infoFragmentInfoCustomerHotLine!=null)
						ft.detach(infoFragmentInfoCustomerHotLine);
					
					/** Detaches the infoFragmentInfoExamineHotLine if exists */
					if(infoFragmentInfoExamineHotLine!=null)
						ft.detach(infoFragmentInfoExamineHotLine);
					
					/** If current tab is android */
					if(tabId.equalsIgnoreCase(getResources().getString(R.string.info_customer))){
						
						if(infoFragmentInfoCustomerHotLine==null){		
							/** Create infoFragmentInfoCustomerHotLine and adding to fragmenttransaction */
							ft.add(R.id.realtabcontent,new FragmentInfoCustomerHotLine(), getResources().getString(R.string.info_customer));						
						}else{
							/** Bring to the front, if already exists in the fragmenttransaction */
							ft.attach(infoFragmentInfoCustomerHotLine);						
						}
						
					}else{	
						if(infoFragmentInfoExamineHotLine==null){
							/** Create infoFragmentInfoExamineHotLine and adding to fragmenttransaction */
							ft.add(R.id.realtabcontent,new FragmentInfoExamineHotLine(), getResources().getString(R.string.infokhaosat));						
						}else{
							/** Bring to the front, if already exists in the fragmenttransaction */
							ft.attach(infoFragmentInfoExamineHotLine);						
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
		   TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec( getResources().getString(R.string.infokhaosat));
	        tSpecInfoExamine.setIndicator(getResources().getString(R.string.infokhaosat),getResources().getDrawable(R.drawable.sale_deposit));
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
