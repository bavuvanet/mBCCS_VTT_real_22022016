package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.sale.fragment.SaleFragment;

/**
 * Created by Toancx on 1/12/2017.
 */

public class SalePagerAdapter extends FragmentStatePagerAdapter{
    private static final int NUM_ITEMS = 3;

    public SalePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SaleFragment.newInstance();
            case 1:
                return SaleFragment.newInstance();
            case 2:
                return SaleFragment.newInstance();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = MainActivity.getInstance().getString(R.string.sale);
                break;
            case 1:
                title = MainActivity.getInstance().getString(R.string.title_container);
                break;
            case 2:
                title = MainActivity.getInstance().getString(R.string.txt_tien_ich);
                break;
            default:
                break;
        }
        return title;
    }
}
