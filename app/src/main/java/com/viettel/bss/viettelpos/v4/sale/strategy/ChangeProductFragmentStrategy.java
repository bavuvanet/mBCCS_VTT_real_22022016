package com.viettel.bss.viettelpos.v4.sale.strategy;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChangeProductBCCS;
import com.viettel.bss.viettelpos.v4.R;

import android.support.v4.app.Fragment;

public class ChangeProductFragmentStrategy implements ManageSubscriberFragmentStrategy{

	@Override
	public int getLabel() {
		return R.string.Chuyendoigoicuoc;
	}

	@Override
	public Fragment getReplaceFragment() {
		return new FragmentChangeProductBCCS();
	}

}
