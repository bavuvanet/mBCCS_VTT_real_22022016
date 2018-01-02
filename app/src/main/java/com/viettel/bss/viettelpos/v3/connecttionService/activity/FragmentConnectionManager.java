package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManageRequestBCCS;
import com.viettel.bss.viettelpos.v4.connecttionService.asyn.GetListCustomerOrderDetailNokByStaffCodeAsyn;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentConnectionManager extends Fragment implements
		OnClickListener, OnItemClickListener {
	// grid manager connection service static
	private GridView grid;

	private ArrayList<Manager> arrayListManager;
	private GridMenuAdapter mAdapterManager;

	// define id for row grid connection manager
	// create new request
	private final String CONNECTION_CREATE_NEW_REQUEST = "0";
	// manager request
	private final String CONNECTION_MANAGER_REQUEST = "1";

	private final String ID_HOTLINE = "2";

	// private TextView txtNameActionBar;s
	private final String tag = FragmentConnectionManager.class.getName();

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
//		addActionBar();
		super.onResume();

	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.manager_customer_connecttion));
	}

	// unit view
	private void unit(View v) {

		grid = (GridView) v.findViewById(R.id.listconnection);
		arrayListManager = new ArrayList<Manager>();

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
			arrayListManager.add(new Manager(R.drawable.sale_channel,
					getResources().getString(R.string.create_new_request), 0,
					CONNECTION_CREATE_NEW_REQUEST));
//		}
//		if (name.contains(";cm.req.management;")) {
			arrayListManager.add(new Manager(R.drawable.sale_deposit,
					getResources().getString(R.string.manager_request), 0,
					CONNECTION_MANAGER_REQUEST));
//		}
//		if (name.contains(";cm.create_req_cd;")) {
//			arrayListManager.add(new Manager(R.drawable.customers, getResources()
//					.getString(R.string.yeucauhotline), 0, ID_HOTLINE));
//		}

		// set Adapter
		mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		grid.setAdapter(mAdapterManager);
		grid.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
		if (menuName.equals(CONNECTION_CREATE_NEW_REQUEST)) {
			// ==== TAO MOI YEU CAU
//			Intent intent = new Intent(getActivity(),
//					ActivityCreateNewRequest.class);
//			startActivity(intent);


			/////////////////
			//dang comment doan nay vi service bi loi
			//31/10/2017
//			FragmentSubscriberInfo fragmentSubscriberInfo = new FragmentSubscriberInfo();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentSubscriberInfo, true);

			GetListCustomerOrderDetailNokByStaffCodeAsyn getListCustomerOrderDetailNokByStaffCodeAsyn = new GetListCustomerOrderDetailNokByStaffCodeAsyn(getActivity(), onPostGetStaffNot, moveLogInAct);
			getListCustomerOrderDetailNokByStaffCodeAsyn.execute();
		}
		if (menuName.equals(CONNECTION_MANAGER_REQUEST)) {
			// === QUAN LY YEU CAU
			FragmentManageRequestBCCS fragManageRequest = new FragmentManageRequestBCCS();
			ReplaceFragment.replaceFragment(getActivity(), fragManageRequest, true);
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

	OnPostExecuteListener<ParseOuput> onPostGetStaffNot = new  OnPostExecuteListener<ParseOuput>(){

		@Override
		public void onPostExecute(ParseOuput result, String errorCode, String description) {


			if("0".equals(result.getErrorCode())){
				if(result.getLimitPobas() > 0){
					CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.qoutarequets,result.getLimitPobas()),getActivity().getString(R.string.app_name)).show();
				}else{
					FragmentSubscriberInfo fragmentSubscriberInfo = new FragmentSubscriberInfo();
					ReplaceFragment.replaceFragment(getActivity(), fragmentSubscriberInfo, true);
				}
			}else{
				String des = result.getDescription();
				if(CommonActivity.isNullOrEmpty(des)){
					des = getActivity().getString(R.string.no_data);
				}
				CommonActivity.createAlertDialog(getActivity(),des,getActivity().getString(R.string.app_name)).show();
			}
		}
	};



	View.OnClickListener moveLogInAct = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

}
