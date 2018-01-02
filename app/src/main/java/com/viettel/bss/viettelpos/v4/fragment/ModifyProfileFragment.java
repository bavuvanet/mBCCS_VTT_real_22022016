package com.viettel.bss.viettelpos.v4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentEditCustomerBCCS;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.MProfilePagerAdapter;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Toancx on 6/12/2017.
 */
public class ModifyProfileFragment extends FragmentCommon {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    ActionProfileBean actionProfileBean;
    List<RecordBean> lstRecordBeans;
    MProfilePagerAdapter adapter;
    Map<Integer, Fragment> mapFragment = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_menu_listview;


    }

    @Override
    protected void unit(View v) {
        Bundle bundle = getArguments();
        actionProfileBean = (ActionProfileBean) bundle.get("actionProfileBean");
        lstRecordBeans = (List<RecordBean>) bundle.getSerializable("lstRecordBeans");

        if (Constant.PROFILE_STATUS.HS_GIA_MAO.equals(actionProfileBean.getTypeStatus())
                || Constant.PROFILE_STATUS.HS_SCAN_TIEPNHAN_CHUAKT.equals(actionProfileBean.getTypeStatus())) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.txt_suasaihs)));
            mapFragment.put(0, new UpdateProfileFragment());

            if(actionProfileBean.getCustId() != null && actionProfileBean.getCustId() > 0) {
                tabLayout.addTab(tabLayout.newTab().setText(R.string.editCustomer));
                mapFragment.put(1, new FragmentEditCustomerBCCS());
            }
        } else if (Constant.PROFILE_STATUS.HS_SCAN_SAI.equals(actionProfileBean.getTypeStatus())) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.txt_suasaihs)));

            mapFragment.put(0, new UpdateProfileFragment());
        } else if (Constant.PROFILE_STATUS.HS_SAI_THONG_TIN.equals(actionProfileBean.getTypeStatus())) {
            tabLayout.addTab(tabLayout.newTab().setText(R.string.editCustomer));
            mapFragment.put(0, new FragmentEditCustomerBCCS());
        }

        adapter = new MProfilePagerAdapter(getFragmentManager(), lstRecordBeans, actionProfileBean, mapFragment);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = adapter.getCurrentFragment(viewPager.getCurrentItem());
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
