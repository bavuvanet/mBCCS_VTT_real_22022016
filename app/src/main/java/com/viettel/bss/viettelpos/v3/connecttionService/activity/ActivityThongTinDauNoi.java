package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;

/**
 * Created by manhtd-outsource on 7/15/16.
 * Activity hiện thị thông tin đấu nối bao gồm 3 tab thông tin KH, hợp đồng, thuê bao
 */
public class ActivityThongTinDauNoi extends GPSTrackerFragment implements View.OnClickListener {

    private TabHost tHost;

    public static ActivityThongTinDauNoi instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_new_request1);
        addActionBarTitle();
        unitView();
        instance = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // create unitView
    public void unitView() {
        tHost = (TabHost) findViewById(android.R.id.tabhost);
        tHost.setup();

        /* Defining Tab Change Listener event. This is invoked when tab is changed */
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                android.support.v4.app.FragmentManager fm = ActivityThongTinDauNoi.this.getSupportFragmentManager();
                TabThongTinKhachHang infoFragmentInfoCustomer = (TabThongTinKhachHang) fm.findFragmentByTag(getResources().getString(R.string.info_customer));
                FragmentInfoExamine infoFragmentInfoExamine = (FragmentInfoExamine) fm.findFragmentByTag(ActivityThongTinDauNoi.this.getResources().getString(R.string.infokhaosat));
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

            /* Detaches the infoFragmentInfoCustomer if exists */
                if (infoFragmentInfoCustomer != null)
                    ft.detach(infoFragmentInfoCustomer);

                if (infoFragmentInfoExamine != null)
                    ft.detach(infoFragmentInfoExamine);

            /* If current tab is android */
                if (tabId.equalsIgnoreCase(ActivityThongTinDauNoi.this.getResources().getString(R.string.info_customer))) {

                    if (infoFragmentInfoCustomer == null) {
                    /* Create infoFragmentInfoCustomer and adding to fragmenttransaction */
                        ft.add(R.id.realtabcontent, new TabThongTinKhachHang(), getResources().getString(R.string.info_customer));
                    } else {
                    /* Bring to the front, if already exists in the fragmenttransaction */
                        ft.attach(infoFragmentInfoCustomer);
                    }

                } else {
                    if (infoFragmentInfoExamine == null) {
                    /* Create infoFragmentInfoExamine and adding to fragmenttransaction */
                        ft.add(R.id.realtabcontent, new FragmentInfoExamine(), ActivityThongTinDauNoi.this.getResources().getString(R.string.infokhaosat));
                    } else {
                    /* Bring to the front, if already exists in the fragmenttransaction */
                        ft.attach(infoFragmentInfoExamine);
                    }
                }
                ft.commit();
            }
        };


        /* Setting tabchangelistener for the tab */
        tHost.setOnTabChangedListener(tabChangeListener);

        TabHost.TabSpec tSpecInfoCus = tHost.newTabSpec(getResources().getString(R.string.info_customer));
        tSpecInfoCus.setIndicator(getResources().getString(R.string.info_customer), getResources().getDrawable(R.drawable.sale_channel));
        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecInfoCus);

        setTab();

    }

    private void setTab() {
        TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec(getResources().getString(R.string.infokhaosat));
        tSpecInfoExamine.setIndicator(getResources().getString(R.string.infokhaosat), getResources().getDrawable(R.drawable.sale_deposit));
        tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecInfoExamine);
    }

    // add actionbar title
    private void addActionBarTitle() {

        ActionBar mActionBar = ActivityThongTinDauNoi.this.getActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.layout_actionbar_channel);
        LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
                .findViewById(R.id.relaBackHome);
        relaBackHome.setOnClickListener(this);
        Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CommonActivity.callphone(ActivityThongTinDauNoi.this, Constant.phoneNumber);
            }
        });
        TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtNameActionbar);
        TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtAddressActionbar);
        txtAddressActionBar.setVisibility(View.GONE);
        txtNameActionBar.setText(getResources().getString(R.string.create_new_request_title));
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
