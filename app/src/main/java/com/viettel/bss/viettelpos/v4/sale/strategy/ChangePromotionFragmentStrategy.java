package com.viettel.bss.viettelpos.v4.sale.strategy;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChangePromotionBCCS;
import com.viettel.bss.viettelpos.v4.R;

import android.support.v4.app.Fragment;

public class ChangePromotionFragmentStrategy implements ManageSubscriberFragmentStrategy {

	@Override
	public int getLabel() {
		return R.string.chuyendoikm;
	}

	@Override
	public Fragment getReplaceFragment() {
		return new FragmentChangePromotionBCCS();
	}

}
