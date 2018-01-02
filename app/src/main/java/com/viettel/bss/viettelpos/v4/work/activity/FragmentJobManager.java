package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel_fromNoti;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCollectCusInfo;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentJobManager extends Fragment
		implements
			OnItemClickListener,
			OnClickListener {
	private final String ROLL_UP = "ROLL_UP";

	private GridView lvManager;
	private ArrayList<Manager> arrayListManager;
    private Button btnHome;
	private EditText edt_search;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentChannelManager");
        MainActivity.getInstance().getSupportActionBar().setTitle(getString(R.string.job));
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentChannelManager");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentChannelManager");
		View mView = inflater.inflate(R.layout.job_manager_layout_main,
				container, false);
		unit(mView);
		addChanelManagerList();
		return mView;
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentChannelManager");

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
		Log.d("TAG", "onPause FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentChannelManager");
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
		lvManager = (GridView) v.findViewById(R.id.lvJobManager);
		arrayListManager = new ArrayList<>();
		edt_search = (EditText) v.findViewById(R.id.edtsearch);
		edt_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				onSearch();

			}
		});
	}
	private void onSearch() {
		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					arrayListManager, getActivity());
			lvManager.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<Manager> items = new ArrayList<>();
			for (Manager hBeans : arrayListManager) {
				if (hBeans != null
						&& !CommonActivity.isNullOrEmpty(hBeans
								.getNameManager())) {
					if (CommonActivity
							.convertCharacter1(hBeans.getNameManager())
							.toLowerCase()
							.contains(
									CommonActivity
											.convertCharacter1(keySearch))) {
						items.add(hBeans);
					}
				}
			}
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					items, getActivity());
			lvManager.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}
	private void addChanelManagerList() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");

		if (name.contains(Constant.JobMenu.WORK_COLLECT_INFO)) {
			arrayListManager.add(new Manager(
					R.drawable.ic_cv_thuthap_thongtin_thitruong, getResources()
							.getString(R.string.job_manager_4), 0,
					Constant.JobMenu.WORK_COLLECT_INFO));
		}
		if (name.contains(Constant.JobMenu.WORK_COMMUNICATION)) {
			arrayListManager.add(new Manager(
					R.drawable.ic_cv_truyenthong_chinhsach, getResources()
							.getString(R.string.job_manager_5), 0,
					Constant.JobMenu.WORK_COMMUNICATION));
		}
		if (name.contains(Constant.JobMenu.WORK_UPDATE)) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_cv,
					getResources().getString(R.string.job_manager_2), 0,
					Constant.JobMenu.WORK_UPDATE));
		}
		if (name.contains(Constant.JobMenu.ASIGN_JOB)) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_quan_ly_giao_viec,
					getResources().getString(R.string.text_menu_assignment), 0,
					Constant.JobMenu.ASIGN_JOB));
		}
		if (name.contains(Constant.JobMenu.WORK_TASK_ROAD)) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_xem_lo_trinh,
					getResources().getString(R.string.viewRouter), 0,
					Constant.JobMenu.WORK_TASK_ROAD));
		}
//		if (name.contains(Constant.JobMenu.PERMISSION_WORK_UPDATE_TASK_LOCATION) || true) {
//			arrayListManager.add(new Manager(R.drawable.lo_trinh_03,
//					getResources().getString(R.string.work_update_location), 0,
//					Constant.JobMenu.PERMISSION_WORK_UPDATE_TASK_LOCATION));
//		}
//		if (name.contains(Constant.JobMenu.PERMISSION_WORK_UPDATE_TASK_LOCATION) || true) {
//			arrayListManager.add(new Manager(R.drawable.lo_trinh_03,
//					getResources().getString(
//							R.string.work_update_location_mobile_saling), 0,
//					Constant.JobMenu.PERMISSION_WORK_UPDATE_BHLD_LOCATION));
//		}
		
		if (name.contains(";menu_task_reassign;")) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_quan_ly_giao_viec,
					getResources().getString(R.string.task_assign_manager), 0,
					Constant.JobMenu.WORK_TASK_ASSIGN_MANAGER));
		}
			
		if (name.contains(";menu_update_task_assign;")) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_cv,
					getResources().getString(R.string.task_assign_staff), 0,
					Constant.JobMenu.WORK_TASK_ASSIGN_STAFF));
		}
		
		if (name.contains(";menu_update_area;")) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_dia_ban,
					getResources().getString(R.string.task_update_area), 0,
					Constant.JobMenu.WORK_TASK_UPDATE_AREA));
		}
		if (name.contains(";menu_update_family;")) {
			arrayListManager.add(new Manager(R.drawable.ic_cv_update_home_info,
					getResources().getString(R.string.updateHD), 0,
					Constant.JobMenu.WORK_TASK_UPDATE_FAMILY));
		}

        if (name.contains(Constant.VSAMenu.COLLECT_CUS_INFO)) {
            arrayListManager.add(new Manager(R.drawable.ic_thuthap_thongtin_khachhang,
                    getResources().getString(R.string.title_collect_cus_info),
                    0, Constant.JobMenu.COLLECT_CUS_INFO));
        }
		
		if (name.contains(";m.roll.up;")) {
			arrayListManager.add(new Manager(
					R.drawable.thu_thap_tt_thi_truong_03, getResources()
							.getString(R.string.roll_up), 0, ROLL_UP));
		}

        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	public static boolean policy = false;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
        switch (menuName) {
            case Constant.JobMenu.WORK_COLLECT_INFO:
                FragmentCriteria mFragmentCriteria = new FragmentCriteria();
                ReplaceFragment.replaceFragment(getActivity(), mFragmentCriteria,
                        true);
                break;
            case Constant.JobMenu.WORK_COMMUNICATION:

                // policy = true;
                ReplaceFragment.replaceFragmentSlidingMenu(getActivity(),
                        // new FragmentListChannel_work()
                        new FragmentListChannel_fromNoti(true), true);
                break;
            case Constant.JobMenu.WORK_UPDATE:
                FragmentUpdateWork fragmentUpdateWork = new FragmentUpdateWork();
                ReplaceFragment.replaceFragment(getActivity(), fragmentUpdateWork,
                        false);
                break;
            case Constant.JobMenu.ASIGN_JOB: {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Define.VIEW_ROUTER, false);
                FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
                mAssignment.setArguments(mBundle);
                ReplaceFragment.replaceFragment(getActivity(), mAssignment, false);
                break;
            }
            case Constant.JobMenu.WORK_TASK_ROAD: {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Define.VIEW_ROUTER, true);
                FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
                mAssignment.setArguments(mBundle);
                ReplaceFragment.replaceFragment(getActivity(), mAssignment, false);
                break;
            }
            case Constant.JobMenu.PERMISSION_WORK_UPDATE_TASK_LOCATION: {
                FragmentWorkUpdateLocation frag = new FragmentWorkUpdateLocation();
                ReplaceFragment.replaceFragment(getActivity(), frag, false);
                break;
            }
            case Constant.JobMenu.PERMISSION_WORK_UPDATE_BHLD_LOCATION: {
                FragmentWorkUpdateLocationMobileSaling frag = new FragmentWorkUpdateLocationMobileSaling();
                ReplaceFragment.replaceFragment(getActivity(), frag, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_ASSIGN_MANAGER: {
                FragmentTaskAssignManager fragment = new FragmentTaskAssignManager();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_ASSIGN_STAFF: {
                FragmentTaskAssignStaff fragment = new FragmentTaskAssignStaff();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_UPDATE_AREA: {
                FragmentUpdateArea fragment = new FragmentUpdateArea();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                break;
            }
            case Constant.JobMenu.WORK_TASK_UPDATE_FAMILY: {
                FragmentUpdateArea fragment = new FragmentUpdateArea();
                Bundle bundle = new Bundle();
                bundle.putString("updatefamily", "1");
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                break;
            }
            case Constant.JobMenu.COLLECT_CUS_INFO:
                ReplaceFragment.replaceFragment(getActivity(),
                        new FragmentCollectCusInfo(), true);
                break;
			case ROLL_UP:
               	Intent i = new Intent(getActivity(), ManagerRollUpFragment.class);
				startActivity(i);

                break;
            default:
                break;
        }
		

	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

			case R.id.btnHome :
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
				break;
			case R.id.relaBackHome :
				// getActivity().onBackPressed();
				ReplaceFragment.replaceFragmentToHome(getActivity(), true);
				break;
		}

	}

	// public interface OnBackListener {
	// void onKeyBackListener();
	// }
	// public

}
