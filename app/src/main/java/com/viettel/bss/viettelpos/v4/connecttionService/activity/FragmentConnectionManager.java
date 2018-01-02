package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentConnectionManager extends Fragment implements
		OnClickListener, OnItemClickListener {
	// grid manager connection service static
	private GridView grid;

	private ArrayList<Manager> arrayListManager;

    // define id for row grid connection manager
	// create new request
	private final String CONNECTION_CREATE_NEW_REQUEST = "0";
	// manager request
	private final String CONNECTION_MANAGER_REQUEST = "1";
	
	private final String ID_HOTLINE = "2";
	
	// private TextView txtNameActionBar;s
	private final String tag = FragmentConnectionManager.class.getName();
	public static FragmentConnectionManager instance = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(
				R.layout.connection_layout_static_manager, container, false);
		unit(mView);
		addManagerList();
		return mView;
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
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.e(tag, "onResume");
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.manager_customer_connecttion);

	}

	// unit view
	private void unit(View v) {

		grid = (GridView) v.findViewById(R.id.listconnection);
		arrayListManager = new ArrayList<>();

	}

	private void addManagerList() {
		// //Tao moi y/c dau noi co dinh
		// public static final String PERMISSION_CM_CREATE_REQ_CD =
		// "cm.create_req_cd";
		// //Quan ly y/c dau noi co dinh
		// public static final String PERMISSION_CM_CANCEL_REQ_CD =
		// "cm.req.management";
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
//		if (name.contains(";cm.create_req_cd;")) {
//			arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
//					getResources().getString(R.string.create_new_request), 0,
//					CONNECTION_CREATE_NEW_REQUEST));
//		}
		if (name.contains(";cm.connect_sub_bccs2;")) {
			arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.manager_request), 0,
					CONNECTION_MANAGER_REQUEST));
		}
//		if (name.contains(";cm.create_req_cd;")) {
//			arrayListManager.add(new Manager(R.drawable.customers, getResources()
//					.getString(R.string.yeucauhotline), 0, ID_HOTLINE));
//		}
		
		// set Adapter
        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		grid.setAdapter(mAdapterManager);
		grid.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
		if (menuName.equals(CONNECTION_CREATE_NEW_REQUEST)) {
			// ==== TAO MOI YEU CAU
			Intent intent = new Intent(getActivity(),
					ActivityCreateNewRequest.class);
			startActivity(intent);
		}
		if (menuName.equals(CONNECTION_MANAGER_REQUEST)) {
			// === QUAN LY YEU CAU
			FragmentManageRequest fragManageRequest = new FragmentManageRequest();
			ReplaceFragment.replaceFragment(getActivity(), fragManageRequest,
					true);
		}
//		if (menuName.equals(ID_HOTLINE)) {
//			 ReplaceFragment.replaceFragment(getActivity(),
//			 new FragmentManagerRequestHotLine(), true);
//		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}

	}

}
