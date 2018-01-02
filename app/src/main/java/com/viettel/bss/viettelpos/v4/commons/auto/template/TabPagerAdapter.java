package com.viettel.bss.viettelpos.v4.commons.auto.template;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by diepdc-pc on 8/4/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    String[] titles;
    private String mScreen;
    private String mService;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public TabPagerAdapter(FragmentManager fm, String sc, String service) {
        super(fm);
        mScreen = sc;
        mService = service;
    }

    @Override
    public Fragment getItem(int num) {
        TemplateFragment templateFragment = TemplateFragment.newInstance(num, mScreen, mService);
        return templateFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
