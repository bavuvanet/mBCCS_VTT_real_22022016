package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.support.v4.app.Fragment;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentRegisterVasSafeNet;
import com.viettel.bss.viettelpos.v4.R;


public class RegisterSafeNetFragmentStrategy implements ManageSubscriberFragmentStrategy{

	@Override
	public int getLabel() {
		return R.string.registervassafe;
	}

	@Override
	public Fragment getReplaceFragment() {
		return new FragmentRegisterVasSafeNet();
	}

}
