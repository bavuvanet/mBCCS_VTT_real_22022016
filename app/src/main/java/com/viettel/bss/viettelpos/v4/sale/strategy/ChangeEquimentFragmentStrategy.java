package com.viettel.bss.viettelpos.v4.sale.strategy;


import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChangeEquipmentSub;

public class ChangeEquimentFragmentStrategy implements ManageSubscriberFragmentStrategy {
	
	@Override
	public int getLabel() {
		// TODO Auto-generated method stub
		return R.string.changeEquipment;
	}

	@Override
	public Fragment getReplaceFragment() {
		// TODO Auto-generated method stub
		return new FragmentChangeEquipmentSub();
	}

}
