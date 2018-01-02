package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ComplainPagerAdapter;

import butterknife.BindView;

/**
 * Created by Toancx on 2/25/2017.
 */

public class ComplainFragment extends FragmentCommon {

    ComplainPagerAdapter pagerAdapter;
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;


    public static ComplainFragment newInstance() {
        ComplainFragment fragment = new ComplainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_menu_listview;
    }

    @Override
    protected void unit(View v) {
        pagerAdapter = new ComplainPagerAdapter(getFragmentManager(), getActivity());
        //set Adapter to view pager
        viewPager.setAdapter(pagerAdapter);

        //set tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getFragmentManager().getFragments()){
            if(fragment instanceof ComplainReceiveFragment) {
                Log.d("INFO", "fragment instanceof ComplainReceiveFragment");
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
