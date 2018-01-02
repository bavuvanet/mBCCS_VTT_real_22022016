package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReportStaffRevenueDetail;
import com.viettel.bss.viettelpos.v4.report.object.SmartPhoneBO;

import java.util.ArrayList;

public class FragmentReportRevenueDetail extends FragmentCommon {

	private Activity activity;

    private ArrayList<SmartPhoneBO> lstSmartPhoneBO;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.repor_staff_revenue_detail);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		idLayout = R.layout.report_layout_staff_revenue_detail;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBundle = getArguments();
		if (mBundle != null) {
			lstSmartPhoneBO = (ArrayList<SmartPhoneBO>) mBundle.getSerializable("kListSmartPhoneBO");
			Log.d(Constant.TAG, "FragmentReportRevenueGeneral showSmartPhoneBODetail smartPhoneBO.getListSmartPhoneBO(): "
					+ lstSmartPhoneBO.size());
		}
	}

	@Override
	public void unit(View v) {
        ListView listView = (ListView) v.findViewById(R.id.listView);
        AdapterReportStaffRevenueDetail adapter = new AdapterReportStaffRevenueDetail(activity, lstSmartPhoneBO);
		listView.setAdapter(adapter);
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
