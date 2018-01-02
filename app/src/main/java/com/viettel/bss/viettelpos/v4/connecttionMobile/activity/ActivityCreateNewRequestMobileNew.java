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
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.template.ActivityTemplate;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCreateNewRequestMobileNew extends GPSTrackerFragment implements OnClickListener {

    public TabHost tHost;

    public static ActivityCreateNewRequestMobileNew instance = null;

    public String funtionType = "";
    public SubscriberDTO subscriberDTO = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
    public HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();
    public ArrayList<ImageBO> lstImageBOs;

    private FragmentConnectionMobileNew mFragmentConnectionMobileNew;
    // omni
    private String omniProcessId = "";
    private String omniTaskId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_new_request1);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            // omni
            this.omniTaskId = mBundle.getString("omniTaskId", omniTaskId);
            this.omniProcessId = mBundle.getString("omniProcessId", omniProcessId);

            funtionType = mBundle.getString("funtionTypeKey", "");
            subscriberDTO = (SubscriberDTO) mBundle.getSerializable("subscriberDTOKey");
            hotLineReponseDetail = (HotLineReponseDetail) mBundle.getSerializable("hotLineReponseDetail");
            receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("receiveRequestBean");
            lstImageBOs = (ArrayList<ImageBO>) mBundle.getSerializable("lstImageBOs");
            Gson g = new Gson();
            Log.d("hotLineReponseDetail: ", g.toJson(hotLineReponseDetail));
        }

        unitView();
        instance = this;

        findViewById(R.id.btn_loadtemplate).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.servicemobile);

        if (!CommonActivity.isNullOrEmpty(funtionType)) {
            if (Constant.CHANGE_POS_TO_PRE.equals(funtionType)) {
                getSupportActionBar().setTitle(getString(R.string.change_pos_to_pre));
            } else if (Constant.CHANGE_PRE_TO_POS.equals(funtionType)) {
                getSupportActionBar().setTitle(getString(R.string.change_pre_to_pos));
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

        Fragment fragment = ReplaceFragment.getActiveFragmentActivity(ActivityCreateNewRequestMobileNew.this);

        if (fragment != null && fragment instanceof FragmentConnectionMobileNew) {
            fragment.onActivityResult(requestCode, resultCode, data);
        } else {
//			Log.d("TAG 9", "ActivityCreateNewRequestMobile onActivityResult Fragment : " + fragment.getClass().getName());
        }
    }

    @OnClick(R.id.btn_loadtemplate)
    protected void onLoadTemplate(View view) {
        if (toolbar.findViewById(R.id.btn_loadtemplate).getVisibility() == View.VISIBLE && mFragmentConnectionMobileNew != null) {
            Intent intent = new Intent(this, ActivityTemplate.class);
            intent.putExtra(AutoConst.FROM_SCREEN, AutoConst.PREF_MOBILE_NEW_SCREEN + FragmentInfoCustomerMobileNew.subType);
            startActivity(intent);
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
                FragmentInfoCustomerMobileNew infoFragmentInfoCustomer = (FragmentInfoCustomerMobileNew) fm.findFragmentByTag(getResources().getString(R.string.info_customer));

                FragmentConnectionMobileNew subInfoFragment = (FragmentConnectionMobileNew) fm.findFragmentByTag(getResources().getString(R.string.conecmobile));
                FragmentTransaction ft = fm.beginTransaction();

//					Log.i("DATA","ReciveRequestId: "+receiveRequestBean.getReciveRequestId()
//							+" ServiceType: "+receiveRequestBean.getServiceType()
//							+" receiveRequestBean.getRequestType(): "+receiveRequestBean.getRequestType()
//							+"receiveRequestBean.getServiceTypeId(): "+receiveRequestBean.getServiceTypeId());

                /** Detaches the infoFragmentInfoCustomer if exists */
                if (infoFragmentInfoCustomer != null)
                    ft.detach(infoFragmentInfoCustomer);

                /** Detaches the infoFragmentInfoExamine if exists */
                if (subInfoFragment != null)
                    ft.detach(subInfoFragment);

                /** If current tab is android */
                if (tabId.equalsIgnoreCase(getResources().getString(R.string.info_customer))) {
                    toolbar.findViewById(R.id.btn_loadtemplate).setVisibility(View.GONE);
                    if (infoFragmentInfoCustomer == null) {
                        /** Create infoFragmentInfoCustomer and adding to fragmenttransaction */
                            if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                                Bundle arguments = new Bundle();
                            Gson g = new Gson();
                            Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                            arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                            arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                            arguments.putSerializable("lstImageBOs", lstImageBOs);
                            infoFragmentInfoCustomer = new FragmentInfoCustomerMobileNew();

                            infoFragmentInfoCustomer.setArguments(arguments);
                            ft.add(R.id.realtabcontent, infoFragmentInfoCustomer, getResources().getString(R.string.info_customer));
                        } else {
                            ft.add(R.id.realtabcontent, new FragmentInfoCustomerMobileNew(), getResources().getString(R.string.info_customer));
                        }


                    } else {
                        /** Bring to the front, if already exists in the fragmenttransaction */
                        ft.attach(infoFragmentInfoCustomer);
                    }
                } else {
                    toolbar.findViewById(R.id.btn_loadtemplate).setVisibility(View.VISIBLE);
                    if (subInfoFragment == null) {
                        Log.i("DATA", "subInfoFragment: 1111111111");
                        /** Create infoFragmentInfoExamine and adding to fragmenttransaction */
                        mFragmentConnectionMobileNew = new FragmentConnectionMobileNew();
                        if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                            Bundle arguments = new Bundle();
                            Gson g = new Gson();
                            Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                            arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                            arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                            arguments.putSerializable("lstImageBOs", lstImageBOs);
                            //omni
                            arguments.putString("omniProcessId", omniProcessId);
                            arguments.putString("omniTaskId", omniTaskId);



                            subInfoFragment.setArguments(arguments);
                            ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                        } else {
                            ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                        }

                    } else {

                        Log.i("DATA", "subInfoFragment: 2222222");
                        mFragmentConnectionMobileNew = new FragmentConnectionMobileNew();

                        if (!FragmentConnectionMobileNew.subTypeCheck.equalsIgnoreCase(FragmentInfoCustomerMobileNew.subType)) {
                            if (FragmentConnectionMobileNew.custIdentityDTOCheck != null && FragmentInfoCustomerMobileNew.custIdentityDTO != null) {
                                if (FragmentConnectionMobileNew.custIdentityDTOCheck.getIdNo() != null && FragmentInfoCustomerMobileNew.custIdentityDTO.getIdNo() != null) {
                                    ft.remove(subInfoFragment);
                                    if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                                        Bundle arguments = new Bundle();
                                        Gson g = new Gson();
                                        Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                                        arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                                        arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                                        arguments.putSerializable("lstImageBOs", lstImageBOs);

                                        mFragmentConnectionMobileNew.setArguments(arguments);
                                        ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                                    } else {
                                        ft.add(R.id.realtabcontent, new FragmentConnectionMobileNew(), getResources().getString(R.string.conecmobile));
                                    }
                                } else {
                                    ft.remove(subInfoFragment);
                                    if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                                        Bundle arguments = new Bundle();
                                        Gson g = new Gson();
                                        Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                                        arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                                        arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                                        arguments.putSerializable("lstImageBOs", lstImageBOs);

                                        mFragmentConnectionMobileNew.setArguments(arguments);
                                        ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                                    } else {
                                        ft.add(R.id.realtabcontent, new FragmentConnectionMobileNew(), getResources().getString(R.string.conecmobile));
                                    }
                                }
                            } else {
                                ft.remove(subInfoFragment);
                                if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                                    Bundle arguments = new Bundle();
                                    Gson g = new Gson();
                                    Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                                    arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                                    arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                                    arguments.putSerializable("lstImageBOs", lstImageBOs);

                                    mFragmentConnectionMobileNew.setArguments(arguments);
                                    ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                                } else {
                                    ft.add(R.id.realtabcontent, new FragmentConnectionMobileNew(), getResources().getString(R.string.conecmobile));
                                }
                            }


                        } else {
                            if (FragmentConnectionMobileNew.custIdentityDTOCheck != null && FragmentInfoCustomerMobileNew.custIdentityDTO != null) {
                                if (FragmentConnectionMobileNew.custIdentityDTOCheck.getIdNo() != null && FragmentInfoCustomerMobileNew.custIdentityDTO.getIdNo() != null) {
                                    if (!FragmentConnectionMobileNew.custIdentityDTOCheck.getIdNo().equalsIgnoreCase(FragmentInfoCustomerMobileNew.custIdentityDTO.getIdNo())) {

                                        ft.remove(subInfoFragment);
                                        if (receiveRequestBean != null && (!CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceTypeId()) && ("446".equals(receiveRequestBean.getServiceTypeId()) || "445".equals(receiveRequestBean.getServiceTypeId()) || "241".equals(receiveRequestBean.getServiceTypeId()) || "240".equals(receiveRequestBean.getServiceTypeId())))) {
                                            Bundle arguments = new Bundle();
                                            Gson g = new Gson();
                                            Log.d("hotLineReponseDetail_ActivityCreateNewRequestMobileNew: ", g.toJson(hotLineReponseDetail));
                                            arguments.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                                            arguments.putSerializable("receiveRequestBean", receiveRequestBean);
                                            arguments.putSerializable("lstImageBOs", lstImageBOs);
//                                            subInfoFragment = new FragmentConnectionMobileNew();

                                            mFragmentConnectionMobileNew.setArguments(arguments);
                                            ft.add(R.id.realtabcontent, mFragmentConnectionMobileNew, getResources().getString(R.string.conecmobile));
                                        } else {
                                            ft.add(R.id.realtabcontent, new FragmentConnectionMobileNew(), getResources().getString(R.string.conecmobile));
                                        }
                                    } else {
                                        /** Bring to the front, if already exists in the fragmenttransaction */
                                        ft.attach(subInfoFragment);
                                    }

                                } else {
                                    /** Bring to the front, if already exists in the fragmenttransaction */
                                    ft.attach(subInfoFragment);
                                }
                            } else {
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

        TabHost.TabSpec tSpecInfoCus = tHost.newTabSpec(getResources().getString(R.string.info_customer));
        tSpecInfoCus.setIndicator(getResources().getString(R.string.info_customer), getResources().getDrawable(R.drawable.sale_channel));
        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecInfoCus);


        setTab();

    }

    private void setTab() {
        TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec(getResources().getString(R.string.conecmobile));
        tSpecInfoExamine.setIndicator(getResources().getString(R.string.conecmobile), getResources().getDrawable(R.drawable.sale_deposit));
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
