package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.AdvisoryPageAdapter;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Admin_pmvt on 7/1/2017.
 */

public class MainAdvisoryFragment extends FragmentCommon {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;

    private SubcriberInfoFragment subcriberInfoFragment;
    private ConsumersLoadCardFragment consumersLoadCardFragment;
    private AdvisoryFragment advisoryFragment;
    private ArrayList<Fragment> fragmentArrayList;

    private CCOutput ccOutput;

    public MainAdvisoryFragment(CCOutput ccOutput) {
        super();
        this.ccOutput = ccOutput;

        this.subcriberInfoFragment = new SubcriberInfoFragment(ccOutput);
        this.consumersLoadCardFragment = new ConsumersLoadCardFragment(ccOutput);
        this.advisoryFragment = new AdvisoryFragment(ccOutput);

        this.fragmentArrayList = new ArrayList<>();
        this.fragmentArrayList.add(this.subcriberInfoFragment);
        this.fragmentArrayList.add(this.consumersLoadCardFragment);
        this.fragmentArrayList.add(this.advisoryFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(ccOutput.getSubscriberInfoData().getIsdn());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.main_advisory_layout;
    }

    public void unit(View v) {
        this.tabLayout.addTab(tabLayout.newTab().setText(R.string.advisory_subscriber_info_text));
        this.tabLayout.addTab(tabLayout.newTab().setText(R.string.advisory_customers_and_load_card_text));
        this.tabLayout.addTab(tabLayout.newTab().setText(R.string.advisory_advisory_pack_text));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final AdvisoryPageAdapter adapter = new AdvisoryPageAdapter
                (getActivity().getSupportFragmentManager(), this.fragmentArrayList);
        viewPager.setAdapter(adapter);

        this.viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        this.viewPager.setCurrentItem(0);

        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 2) {
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    advisoryFragment.requestHistoryData();
                                }
                            }, 300);
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
        permission = ";update.tvkh;";
    }
}
