package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.adapter.AdapterManager;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

public class FragmentMenuJob extends Fragment implements OnItemClickListener,
		OnClickListener, Define {
	private ListView lvManager;
	private ArrayList<Manager> arrayListManager;
    private Button btnHome;

	// private Bundle mBundle;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated Fragment Menu Job");
        MainActivity.getInstance().setTitleActionBar(R.string.menu_job);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach Fragment Menu Job");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView Fragment Menu Job");
		View mView = inflater.inflate(R.layout.layout_assign_job_menu,
				container, false);
		unit(mView);
		addChanelManagerList();
		return mView;
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FFragment Menu Job");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView Fragment Menu Job");

		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause Fragment Menu Job");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume Fragment Menu Job");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void unit(View v) {
		lvManager = (ListView) v.findViewById(R.id.lvAssignMenu);
		arrayListManager = new ArrayList<>();

	}

	private void addChanelManagerList() {

		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, getActivity().MODE_PRIVATE);
		String menuNameAll = preferences
				.getString(Define.KEY_MENU_NAME, "null");
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.text_menu_assignment), 0, Define.ASSIGN_JOB));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.viewRouter), 0, Define.VIEW_ROUTER));
        AdapterManager mAdapterManager = new AdapterManager(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
		if (menuName.equals(ASSIGN_JOB)) {
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(VIEW_ROUTER, false);
			FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
			mAssignment.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(), mAssignment, false);
		} else if (menuName.equals(VIEW_ROUTER)) {
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(VIEW_ROUTER, true);
			FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
			mAssignment.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(), mAssignment, false);
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		}

	}

}
