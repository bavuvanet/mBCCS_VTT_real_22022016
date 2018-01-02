package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentTickAppoint;

public class TickAppointFragmentStrategy implements ManageSubscriberFragmentStrategy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465454545445L;

	@Override
	public int getLabel() {
		// TODO Auto-generated method stub
		return R.string.tick_appoint;
	}

	@Override
	public Fragment getReplaceFragment() {
		// TODO Auto-generated method stub
		return new FragmentTickAppoint();
	}

}
