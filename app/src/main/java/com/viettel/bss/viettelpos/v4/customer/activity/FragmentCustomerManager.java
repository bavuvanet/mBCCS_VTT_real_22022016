package com.viettel.bss.viettelpos.v4.customer.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.adapter.AdapterManager;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

public class FragmentCustomerManager extends FragmentCommon {
	private ListView lvManager;
	private ArrayList<Manager> arrayListManager;

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.customers);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_customer_manager;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void unit(View v) {
		lvManager = (ListView) v.findViewById(R.id.lv_customer_manager);
		arrayListManager = new ArrayList<>();
		addManagerList();
	}

	private void addManagerList() {
        int ID_NEW = 0;
        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.customer_new), 0, ID_NEW));
        int ID_FINISH = 1;
        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.customer_finish), 0, ID_FINISH));

		// arrayListManager.add(new Manager(R.drawable.sales, getResources()
		// .getString(R.string.charge_del), 0, ID_DEL));
		// arrayListManager.add(new Manager(R.drawable.sales, getResources()
		// .getString(R.string.charge_tktc), 0, ID_TKTC));
		// arrayListManager.add(new Manager(R.drawable.sales, getResources()
		// .getString(R.string.confirmNote), 0, CONFIRM_NOTE));

        AdapterManager mAdapterManager = new AdapterManager(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// String menuName = arrayListManager.get(arg2).getKeyMenuName();

		int id = arrayListManager.get(arg2).getId();
		toast(id + "");
		switch (id) {
			case 0 :
				ReplaceFragment.replaceFragment(getActivity(),
						new FragmentCustomerNew(), true);
				break;

			// case 1 :
			// ReplaceFragment.replaceFragment(getActivity(),
			// new FragmentChargeRe(), true);
			// break;
			// case 2 :
			// ReplaceFragment.replaceFragment(getActivity(),
			// new FragmentChargeDel(), true);
			// break;
			case 3 :

				break;

			default :
				break;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
