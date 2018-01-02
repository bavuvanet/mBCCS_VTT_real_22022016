package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChangeDepositLimit;

public class ChangeDepositLimitFragmentStrategy implements ManageSubscriberFragmentStrategy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465545445L;

	@Override
	public int getLabel() {
		return R.string.change_limit;
	}

	@Override
	public Fragment getReplaceFragment() {
		return new FragmentChangeDepositLimit();
	}

}
