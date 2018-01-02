package com.viettel.bss.viettelpos.v4.connecttionService.strategy;

import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTechNew;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;

/**
 * Created by knight on 24/08/2017.
 */

public class ChangeTechFragmentStrategy implements ManageSubscriberFragmentStrategy {

    @Override
    public int getLabel() {
        return R.string.changeTechnology;
    }

    @Override
    public Fragment getReplaceFragment() {
        return new FragmentManagerChangeTechNew();
    }


}