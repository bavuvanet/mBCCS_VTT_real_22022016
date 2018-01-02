package com.viettel.bss.viettelpos.v4.sale.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.adapter.StaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

public class FragmentChooseStaff extends Fragment implements
		OnItemClickListener, OnClickListener {
	private View view;
    private StaffAdapter staffAdapter;
	private ArrayList<Staff> lstStaffs = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.choose_staff_layout, container,
					false);

            ViewHolder viewHolder = new ViewHolder();
			viewHolder.lvStaff = (ListView) view.findViewById(R.id.lvStaff);
			viewHolder.edtSearch = (EditText) view
					.findViewById(R.id.edtSearchStaff);

			if (!CommonActivity.isNullOrEmpty(Session.userName)) {
				lstStaffs = StaffBusiness.getLstStaffByStaffMngt(getActivity(),
						Session.userName);
			}

			staffAdapter = new StaffAdapter(getActivity(), lstStaffs);
			viewHolder.lvStaff.setAdapter(staffAdapter);

			viewHolder.lvStaff.setOnItemClickListener(this);

			viewHolder.edtSearch.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					staffAdapter.getFilter().filter(arg0.toString());
				}
			});
		}
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		FragmentViewStock fragmentViewStock = new FragmentViewStock();
		Bundle bundle = new Bundle();
		String staffCode = ((Staff) arg0.getItemAtPosition(position))
				.getStaffCode();
		String staffName = ((Staff) arg0.getItemAtPosition(position))
				.getName();
		bundle.putString("staffCode", staffCode);
		bundle.putString("staffName", staffName);

		fragmentViewStock.setArguments(bundle);

		ReplaceFragment.replaceFragment(getActivity(),
				fragmentViewStock, false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.view_stock);
	}

	private Button btnHome;
	public class ViewHolder {
		ListView lvStaff;
		EditText edtSearch;
	}

}
