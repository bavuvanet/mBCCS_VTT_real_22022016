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

public class FragmentCustomerRegister extends FragmentCommon {
	private EditText editIsdn;
    private EditText editGoicuoc;
    private EditText editSerial;
    private EditText editTinh;
    private EditText editHuyen;
    private EditText editPhuong;
    private EditText editTenkh;
    private EditText editNsinh;
    private EditText editCmt;
    private EditText editNcapCmt;
    private EditText editNdeadHchieu;
    private EditText editNoiCapCmt;
    private EditText editSonha;
	private Button btnRegister;
	private Spinner spinnerGender;
    private Spinner spinnerLoaigt;
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
		idLayout = R.layout.layout_customer_register;
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public void unit(View v) {
		spinnerLoaigt = (Spinner) v.findViewById(R.id.spinner_loaigt);
		spinnerGender = (Spinner) v.findViewById(R.id.spinner_gender);
		editIsdn = (EditText) v.findViewById(R.id.edit_isdn);
		editGoicuoc = (EditText) v.findViewById(R.id.edit_goicuoc);
		editSerial = (EditText) v.findViewById(R.id.edit_serial);
		editTinh = (EditText) v.findViewById(R.id.edit_matinh);
		editHuyen = (EditText) v.findViewById(R.id.edit_mahuyen);
		editPhuong = (EditText) v.findViewById(R.id.edit_maxa);
		editTenkh = (EditText) v.findViewById(R.id.edit_tenkh);
		editNsinh = (EditText) v.findViewById(R.id.edit_ngaysinh);
		editCmt = (EditText) v.findViewById(R.id.edit_cmt);

		editNcapCmt = (EditText) v.findViewById(R.id.edit_ncap_cmt);
		editNdeadHchieu = (EditText) v.findViewById(R.id.edit_ndead_hchieu);
		editNoiCapCmt = (EditText) v.findViewById(R.id.edit_noicapcmt);
		editSonha = (EditText) v.findViewById(R.id.edit_sonha);

		btnRegister = (Button) v.findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);
		setData4Views();
	}
	private void setData4Views() {
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ(act
				.getString(R.string.select_loaigiayto)));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 1"));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 2"));
		arrCustomerLoaiGiaytoOJs.add(new CustomerLoaiGiaytoOJ("giay to 3"));

		ArrayAdapter<String> adapterLoaigt = new ArrayAdapter<>(act,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		// adapterLoaigt.add(act.getString(R.string.select_area));
		for (CustomerLoaiGiaytoOJ obj : arrCustomerLoaiGiaytoOJs) {
			adapterLoaigt.add(obj.getName());
		}
		spinnerLoaigt.setAdapter(adapterLoaigt);
		ArrayAdapter<String> adapterGender = new ArrayAdapter<>(act,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		String arrGender[] = new String[]{
				act.getString(R.string.select_gender), "Nam", "Nữ"};
		for (String obj : arrGender) {
			adapterGender.add(obj);
		}
		spinnerGender.setAdapter(adapterGender);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {

			case R.id.btn_register :
				// toast("register");
				ReplaceFragment.replaceFragment(act,
						new FragmentSelectCustomer(), true);
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
