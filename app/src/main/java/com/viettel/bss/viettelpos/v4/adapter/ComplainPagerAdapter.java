package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.customer.fragment.ComplainReceiveFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.LookupComplainFragment;

/**
 * Created by Toancx on 2/25/2017.
 */

public class ComplainPagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    public ComplainPagerAdapter(FragmentManager fragmentManager, Context mContext) {
        super(fragmentManager);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = ComplainReceiveFragment.newInstance();
                break;
            case 1:
                fragment = LookupComplainFragment.newInstance();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = mContext.getString(R.string.txt_create_complain);
                break;
            case 1:
                title = mContext.getString(R.string.txt_lookup_complain);
                break;
            default:
                break;
        }

        MainActivity.getInstance().setTitleActionBar(title);
        return title;
    }
}
