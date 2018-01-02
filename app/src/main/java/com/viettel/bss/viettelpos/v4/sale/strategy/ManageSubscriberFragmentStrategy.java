package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.support.v4.app.Fragment;

import java.io.Serializable;



public interface ManageSubscriberFragmentStrategy extends Serializable {

	int getLabel();
	Fragment getReplaceFragment();
}
