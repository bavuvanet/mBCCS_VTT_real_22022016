package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.AdvisoryPageAdapter;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;

import java.util.ArrayList;

/**
 * Created by Admin_pmvt on 7/1/2017.
 */

public class ConsumersLoadCardFragment extends Fragment {

    private CCOutput ccOutput;

    private ConsumersInfoDirectFragment consumersInfoDirectFragment;
    private LoadCardInfoFragment loadCardInfoFragment;
    private ArrayList<Fragment> fragments;

    public ConsumersLoadCardFragment(CCOutput ccOutput) {
        super();
        this.ccOutput = ccOutput;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_consumers_fragment, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tlConsumers);
        tabLayout.addTab(tabLayout.newTab().setText(
                getString(R.string.advisory_load_card_text)));
        tabLayout.addTab(tabLayout.newTab().setText(
                getString(R.string.advisory_customers_text)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        this.loadCardInfoFragment = new LoadCardInfoFragment(ccOutput);
        this.consumersInfoDirectFragment = new ConsumersInfoDirectFragment(ccOutput);

        this.fragments = new ArrayList<>();
        this.fragments.add(loadCardInfoFragment);
        this.fragments.add(consumersInfoDirectFragment);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.vpConsumers);
        final AdvisoryPageAdapter adapter = new AdvisoryPageAdapter
                (getActivity().getSupportFragmentManager(), fragments);
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

        return view;
    }
}