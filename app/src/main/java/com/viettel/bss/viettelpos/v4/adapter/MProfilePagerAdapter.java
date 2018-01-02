package com.viettel.bss.viettelpos.v4.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentEditCustomerBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.fragment.UpdateProfileFragment;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetCustomerByCustId;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toancx on 6/12/2017.
 */
public class MProfilePagerAdapter extends FragmentStatePagerAdapter {
    ActionProfileBean actionProfileBean;
    List<RecordBean> lstRecordBeans;
    Map<Integer, Fragment> mapFragment = new HashMap<>();

    public MProfilePagerAdapter(FragmentManager fm, List<RecordBean> lstRecordBeans, ActionProfileBean actionProfileBean, Map<Integer, Fragment> mapFragment) {
        super(fm);
        this.lstRecordBeans = lstRecordBeans;
        this.mapFragment = mapFragment;
        this.actionProfileBean = actionProfileBean;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment tab = mapFragment.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lstRecordBeans", (Serializable)lstRecordBeans);
        bundle.putSerializable("actionProfileBean", actionProfileBean);
        bundle.putString("type", "FMP_EDT_CUST");
        if(actionProfileBean.getCustId() != null && !actionProfileBean.getCustId().equals(0L)) {
            bundle.putString("custId", String.valueOf(actionProfileBean.getCustId()));
        }

        tab.setArguments(bundle);
        return tab;
    }

    @Override
    public int getCount() {
        return mapFragment.size();
    }

    public Fragment getCurrentFragment(int position){
        return mapFragment.get(position);
    }
}
