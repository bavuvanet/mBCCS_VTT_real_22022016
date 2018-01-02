package com.viettel.bss.viettelpos.v4.customer.manage;

import com.viettel.bss.viettelpos.v4.R;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCustomerInfoContract extends Fragment {

    private View mView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Activity mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_customer_info_contract,container, false);  
		}
 

		return mView;
	}

}
