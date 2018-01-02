package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;


import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;

public class ActivityCreateNewRequestMobile extends GPSTrackerFragment{

//	public TabHost tHost;
//
//	public static ActivityCreateNewRequestMobile instance = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_create_new_request1);
//		unitView();
//		instance = this;
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//        MainActivity.getInstance().setTitleActionBar(R.string.yeucauhotline);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Log.d("TAG 9", "ActivityCreateNewRequestMobile onActivityResult requestCode : " + requestCode);
//
//		Fragment fragment = ReplaceFragment.getActiveFragmentActivity(ActivityCreateNewRequestMobile.this);
//
//		if(fragment != null && fragment instanceof FragmentConnectionMobile) {
//			fragment.onActivityResult(requestCode, resultCode, data);
//		} else {
////			Log.d("TAG 9", "ActivityCreateNewRequestMobile onActivityResult Fragment : " + fragment.getClass().getName());
//		}
//	}
//
//	// create unitView
//    private void unitView() {
//		 tHost = (TabHost) findViewById(android.R.id.tabhost);
//	     tHost.setup();
//
//	        /** Defining Tab Change Listener event. This is invoked when tab is changed */
//	        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
//
//				@Override
//				public void onTabChanged(String tabId) {
//					FragmentManager fm = getSupportFragmentManager();
//					FragmentInfoCustomerMobile infoFragmentInfoCustomer = (FragmentInfoCustomerMobile)  fm.findFragmentByTag(getResources().getString(R.string.info_customer));
//
//					FragmentConnectionMobile subInfoFragment = (FragmentConnectionMobile) fm.findFragmentByTag(getResources().getString(R.string.conecmobile));
//					FragmentTransaction ft = fm.beginTransaction();
//
//					/** Detaches the infoFragmentInfoCustomer if exists */
//					if(infoFragmentInfoCustomer!=null)
//						ft.detach(infoFragmentInfoCustomer);
//
//					/** Detaches the infoFragmentInfoExamine if exists */
//					if(subInfoFragment!=null)
//						ft.detach(subInfoFragment);
//
//					/** If current tab is android */
//					if(tabId.equalsIgnoreCase(getResources().getString(R.string.info_customer))){
//
//						if(infoFragmentInfoCustomer==null){
//							/** Create infoFragmentInfoCustomer and adding to fragmenttransaction */
//							ft.add(R.id.realtabcontent,new FragmentInfoCustomerMobile(), getResources().getString(R.string.info_customer));
//						}else{
//							/** Bring to the front, if already exists in the fragmenttransaction */
//							ft.attach(infoFragmentInfoCustomer);
//						}
//					} else {
//						if(subInfoFragment==null){
//							/** Create infoFragmentInfoExamine and adding to fragmenttransaction */
//							ft.add(R.id.realtabcontent,new FragmentConnectionMobile(), getResources().getString(R.string.conecmobile));
//						}else{
//
//							if(!FragmentConnectionMobile.subTypeCheck.equalsIgnoreCase(FragmentInfoCustomerMobile.subType) ){
//								if(FragmentConnectionMobile.custommerByIdNoBeanCheck != null && FragmentInfoCustomerMobile.custommerByIdNoBean != null){
//									if(FragmentConnectionMobile.custommerByIdNoBeanCheck.getIdNo() != null && FragmentInfoCustomerMobile.custommerByIdNoBean.getIdNo() != null){
//										ft.remove(subInfoFragment);
//										ft.add(R.id.realtabcontent,new FragmentConnectionMobile(), getResources().getString(R.string.conecmobile));
//								}else{
//									ft.remove(subInfoFragment);
//									ft.add(R.id.realtabcontent,new FragmentConnectionMobile(), getResources().getString(R.string.conecmobile));
//									}
//								}else{
//									ft.remove(subInfoFragment);
//									ft.add(R.id.realtabcontent,new FragmentConnectionMobile(), getResources().getString(R.string.conecmobile));
//								}
//
//
//							}else{
//								if(FragmentConnectionMobile.custommerByIdNoBeanCheck != null && FragmentInfoCustomerMobile.custommerByIdNoBean != null){
//									if(FragmentConnectionMobile.custommerByIdNoBeanCheck.getIdNo() != null && FragmentInfoCustomerMobile.custommerByIdNoBean.getIdNo() != null){
//										if(!FragmentConnectionMobile.custommerByIdNoBeanCheck.getIdNo().equalsIgnoreCase(FragmentInfoCustomerMobile.custommerByIdNoBean.getIdNo())){
//
//											ft.remove(subInfoFragment);
//											ft.add(R.id.realtabcontent,new FragmentConnectionMobile(), getResources().getString(R.string.conecmobile));
//										}else{
//											/** Bring to the front, if already exists in the fragmenttransaction */
//											ft.attach(subInfoFragment);
//										}
//
//									}else{
//										/** Bring to the front, if already exists in the fragmenttransaction */
//										ft.attach(subInfoFragment);
//									}
//								}else{
//									/** Bring to the front, if already exists in the fragmenttransaction */
//									ft.attach(subInfoFragment);
//								}
//							}
//						}
//					}
//					ft.commit();
//				}
//			};
//
//
//			/** Setting tabchangelistener for the tab */
//			tHost.setOnTabChangedListener(tabChangeListener);
//
//	        TabHost.TabSpec tSpecInfoCus= tHost.newTabSpec(getResources().getString(R.string.info_customer));
//	        tSpecInfoCus.setIndicator(getResources().getString(R.string.info_customer),getDrawable(R.drawable.sale_channel));
//	        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
//	        tHost.addTab(tSpecInfoCus);
//
//	        setTab();
//
//	}
//
//	private void setTab() {
//		   TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec( getResources().getString(R.string.conecmobile));
//	        tSpecInfoExamine.setIndicator(getResources().getString(R.string.conecmobile),getDrawable(R.drawable.sale_deposit));
//	        tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
//	        tHost.addTab(tSpecInfoExamine);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.relaBackHome:
//		onBackPressed();
//
//			break;
//		default:
//			break;
//		}
//
//
//	}
	
}
