package com.viettel.bss.viettelpos.v4.sale.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.sale.adapter.StaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.BhldBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.BhldObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

public class FragmentChooseBHLD extends Fragment implements
		OnItemClickListener, OnClickListener {
	private ListView lvItem;
	private StaffAdapter bhldAdapter;
	private ArrayList<Staff> lstStaff;
	private View view;
	private Long recordWorkId = 0L;
	private Button btnHome;
	private TextView txtNameActionBar;
	private final String tag = FragmentChooseBHLD.class.getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater
					.inflate(R.layout.sale_choose_bhld, container, false);
		}
		final ArrayList<BhldObject> lstObject = BhldBusiness
				.getListProgram(getActivity());

		Spinner spiProgram = (Spinner) view.findViewById(R.id.spn_program);
		lvItem = (ListView) view.findViewById(R.id.lvChannel);
		lvItem.setOnItemClickListener(this);
		lstStaff = new ArrayList<>();

		if (lstObject != null && !lstObject.isEmpty()) {
			final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), R.layout.simple_list_item_single_row,
                    R.id.text1);
			for (BhldObject bhldObject : lstObject) {
				adapter.add(bhldObject.getSaleProgramName());
			}
			spiProgram.setAdapter(adapter);
			spiProgram.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					BhldObject bhldObject = lstObject.get(position);
					recordWorkId = bhldObject.getRecordWorkId();
					lstStaff = BhldBusiness.getListObjectByProgram(
							getActivity(), bhldObject.getRecordWorkId());
					bhldAdapter = new StaffAdapter(getActivity(), lstStaff);
					lvItem.setAdapter(bhldAdapter);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.choose_programe);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		Log.e(tag, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// // TODO Auto-generated method stub
		// cbStar.setVisibility(View.VISIBLE);
		// btnHome.setVisibility(View.GONE);
		Log.e(tag, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.e(tag, "onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.e(tag, "onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.e(tag, "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.e(tag, "onStop");
		super.onStop();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Staff staff = lstStaff.get(position);

		Staff staffBO = StaffBusiness.getStaffByStaffCode(getActivity(),
				staff.getStaffCode());
		if (staffBO == null) {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.ctvNotFound),
					Toast.LENGTH_LONG).show();
			return;
		}
		Bundle b = new Bundle();
		b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_BHLD);
		b.putString("staffCode", staffBO.getStaffCode());
		b.putString("staffName", staffBO.getName());
		b.putLong("staffId", staffBO.getStaffId());
		b.putLong("recordWorkId", recordWorkId);
		FragmentSaleSaling fragment = new FragmentSaleSaling();
		fragment.setArguments(b);
		ReplaceFragment.replaceFragment(getActivity(), fragment, false);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}
}
