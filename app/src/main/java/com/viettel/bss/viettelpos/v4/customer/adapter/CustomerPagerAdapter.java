package com.viettel.bss.viettelpos.v4.customer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.viettel.bss.viettelpos.v4.customer.manage.FragmentCustomerInfoContract;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentCustomersInfoCus;

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {

	private static final int TAB_COUNT = 1;
	private static final int TAB_INFO_CONTRACT = 0;
	private static final int TAB_INFO_CUSTOMER = 1;

	public CustomerPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case TAB_INFO_CONTRACT:
            return new FragmentCustomerInfoContract();
		case TAB_INFO_CUSTOMER:
            return new FragmentCustomersInfoCus();

		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_UNCHANGED;
	}

}
