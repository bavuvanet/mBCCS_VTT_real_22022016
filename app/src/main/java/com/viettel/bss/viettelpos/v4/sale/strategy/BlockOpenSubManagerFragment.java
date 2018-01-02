package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.R;

/**
 * Created by Toancx on 4/25/2017.
 */

public class BlockOpenSubManagerFragment implements ManageSubscriberFragmentStrategy {

    /**
     *
     */
    private static final long serialVersionUID = 5465454545445L;

    @Override
    public int getLabel() {
        // TODO Auto-generated method stub
        return R.string.blockOpenSubHomephone;
    }

    @Override
    public Fragment getReplaceFragment() {
        // TODO Auto-generated method stub
        return new BlockOpenSubHomeFragment();
    }

}
