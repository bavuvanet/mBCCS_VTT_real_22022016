package com.viettel.bss.viettelpos.v4.customer.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerLoaiGiaytoOJ;

import java.util.ArrayList;

public class FragmentCustomerNew extends FragmentCommon {
	private Spinner spinnerLoaigt;
	private EditText editSogt;
    private EditText editSotb;
	private Button btnCheck;
	private final ArrayList<CustomerLoaiGiaytoOJ> arrCustomerLoaiGiaytoOJs = new ArrayList<>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(act.getString(R.string.customers) + " - "
                + act.getString(R.string.customer_new));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_customer_new;
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public void unit(View v) {
		spinnerLoaigt = (Spinner) v.findViewById(R.id.spinner_loaigt);

		editSogt = (EditText) v.findViewById(R.id.edit_sogt);
		editSotb = (EditText) v.findViewById(R.id.edit_sothuebao);
		btnCheck = (Button) v.findViewById(R.id.btn_check);
		btnCheck.setOnClickListener(this);
		setData4Views();
	}

	private void setData4Views() {
		// arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ(""));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 1"));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 2"));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 3"));
		ArrayAdapter<String> adapterLoaigt = new ArrayAdapter<>(act,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		adapterLoaigt.add(act.getString(R.string.select_loaigiayto));
		for (CustomerLoaiGiaytoOJ obj : arrCustomerLoaiGiaytoOJs) {
			adapterLoaigt.add(obj.getName());
		}
		spinnerLoaigt.setAdapter(adapterLoaigt);

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {

			case R.id.btn_check :
				// toast("search");
				ReplaceFragment.replaceFragment(act,
						new FragmentCustomerRegister(), true);
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
