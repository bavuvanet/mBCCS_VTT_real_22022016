package com.viettel.bss.viettelpos.v4.connecttionService.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCreateNewRequest extends GPSTrackerFragment implements OnClickListener{

	TabHost tHost;	
	
	public static ActivityCreateNewRequest instance = null;
	@BindView(R.id.toolbar)
    Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_create_new_request1);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		unitView();
		instance = this;
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
					FragmentInfoCustomer infoFragmentInfoCustomer = (FragmentInfoCustomer) fm.findFragmentByTag(getResources().getString(R.string.info_customer));
					FragmentInfoExamine infoFragmentInfoExamine = (FragmentInfoExamine) fm.findFragmentByTag(getResources().getString(R.string.infokhaosat));
					android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
					
					/** Detaches the infoFragmentInfoCustomer if exists */
					if(infoFragmentInfoCustomer!=null)
						ft.detach(infoFragmentInfoCustomer);
					
					/** Detaches the infoFragmentInfoExamine if exists */
					if(infoFragmentInfoExamine!=null)
						ft.detach(infoFragmentInfoExamine);
					
					/** If current tab is android */
					if(tabId.equalsIgnoreCase(getResources().getString(R.string.info_customer))){
						
						if(infoFragmentInfoCustomer==null){		
							/** Create infoFragmentInfoCustomer and adding to fragmenttransaction */
							ft.add(R.id.realtabcontent,new FragmentInfoCustomer(), getResources().getString(R.string.info_customer));						
						}else{
							/** Bring to the front, if already exists in the fragmenttransaction */
							ft.attach(infoFragmentInfoCustomer);						
						}
						
					}else{	
						if(infoFragmentInfoExamine==null){
							/** Create infoFragmentInfoExamine and adding to fragmenttransaction */
							ft.add(R.id.realtabcontent,new FragmentInfoExamine(), getResources().getString(R.string.infokhaosat));						
						}else{
							/** Bring to the front, if already exists in the fragmenttransaction */
							ft.attach(infoFragmentInfoExamine);						
						}
					}
					ft.commit();				
				}
			};
			
			
			/** Setting tabchangelistener for the tab */
			tHost.setOnTabChangedListener(tabChangeListener);
	                
	        TabHost.TabSpec tSpecInfoCus= tHost.newTabSpec(getResources().getString(R.string.info_customer));
	        tSpecInfoCus.setIndicator(getResources().getString(R.string.info_customer), getResources().getDrawable(R.drawable.sale_channel));
	        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));        
	        tHost.addTab(tSpecInfoCus);
	        
	        setTab();
	     
	}
	
	private void setTab() {
		   TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec( getResources().getString(R.string.infokhaosat));
	        tSpecInfoExamine.setIndicator(getResources().getString(R.string.infokhaosat), getResources().getDrawable(R.drawable.sale_deposit));
	        tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
	        tHost.addTab(tSpecInfoExamine);
	}

	@Override
	public void onClick(View v) {
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
