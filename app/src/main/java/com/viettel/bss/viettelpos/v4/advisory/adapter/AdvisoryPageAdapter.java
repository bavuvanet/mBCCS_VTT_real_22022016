package com.viettel.bss.viettelpos.v4.advisory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/18/2017.
 */

public class AdvisoryPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    public AdvisoryPageAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}