package com.viettel.bss.viettelpos.v4.channel.pager;


import android.support.v4.app.Fragment;

import com.viettel.bss.viettelpos.v4.channel.activity.FragmentUpdateImageMonth;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.ModelCallbacks;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.Page;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.ReviewItem;

import java.util.ArrayList;


public class UpdateImageMonthPager extends Page{

	public UpdateImageMonthPager(ModelCallbacks callbacks, String title) {
		super(callbacks, title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stub
		return FragmentUpdateImageMonth.create(getKey());
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		// TODO Auto-generated method stub
		
	}

}
