package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.AdvisoryPageAdapter;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/18/2017.
 */

public class AdvisoryFragment extends FragmentCommon {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CCOutput ccOutput;
    private AdvisoryHistoryFragment historyFragment;
    private AdvisoryPacketFragment packetFragment;
    private ArrayList<Fragment> fragments;

    public AdvisoryFragment(CCOutput ccOutput) {
        super();
        this.ccOutput = ccOutput;

        this.historyFragment = new AdvisoryHistoryFragment(
                StringUtils.formatMsisdn(ccOutput.getSubscriberInfoData().getIsdn()));
        this.packetFragment = new AdvisoryPacketFragment(
                StringUtils.formatMsisdn(ccOutput.getSubscriberInfoData().getIsdn()),
                ccOutput.getSubscriberInfoData().getPrepaid());
        this.fragments = new ArrayList<>();
        this.fragments.add(historyFragment);
        this.fragments.add(packetFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.advisory_fragment;
    }

    @Override
    protected void unit(View v) {

        tabLayout.addTab(tabLayout.newTab().setText(
                getString(R.string.advisory_history_text)));
        tabLayout.addTab(tabLayout.newTab().setText(
                getString(R.string.advisory_packet_text)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final AdvisoryPageAdapter adapter =
                new AdvisoryPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    requestHistoryData();
                } else {
                    requestProductData();
                }
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

    public void requestHistoryData() {
        historyFragment.requestData();
    }

    public void requestProductData() {
        packetFragment.requestData();
    }
}