package com.viettel.bss.viettelpos.v4.customer.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.customer.adapter.CustomerAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;

import java.util.ArrayList;

public class FragmentSelectCustomer extends FragmentCommon {
	private ListView lvListCustomer;
	private final ArrayList<CustomerOJ> arrCustomerOJs = new ArrayList<>();
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(act.getString(R.string.customers) + " - "
                + act.getString(R.string.customer_new));

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_select_customer;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		lvListCustomer = (ListView) v.findViewById(R.id.lv_list_customer);
		arrCustomerOJs.add(new CustomerOJ("khach 1"));
		arrCustomerOJs.add(new CustomerOJ("khach 2"));
		arrCustomerOJs.add(new CustomerOJ("khach 3"));
		CustomerAdapter cusAdapter = new CustomerAdapter(arrCustomerOJs, act);
		lvListCustomer.setAdapter(cusAdapter);
		lvListCustomer.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// String menuName = arrayListManager.get(arg2).getKeyMenuName();

		toast(arrCustomerOJs.get(arg2).getName());

	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
