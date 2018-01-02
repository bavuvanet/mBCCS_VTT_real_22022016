package com.viettel.bss.viettelpos.v4.work.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel_fromNoti;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCollectCusInfo;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.staff.work.WorkListActivity;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentCriteria;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentListStaffJobAssignment;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignManager;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignStaff;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateArea;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateWork;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocation;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocationMobileSaling;
import com.viettel.bss.viettelpos.v4.work.activity.ManagerRollUpFragment;
import com.viettel.gem.screen.SearchCustomerActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/7/2017.
 */

public class JobFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    public static JobFragment newInstance() {
        return new JobFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_recycler_view;
    }

    @Override
    protected void unit(View v) {
        menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    protected void setPermission() {

    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());
            switch (keyMenuName) {
                case Constant.JobMenu.WORK_COLLECT_INFO:
                    FragmentCriteria mFragmentCriteria = new FragmentCriteria();
                    ReplaceFragment.replaceFragment(getActivity(), mFragmentCriteria,
                            true);
                    break;
                case Constant.JobMenu.WORK_COMMUNICATION:
                    ReplaceFragment.replaceFragmentSlidingMenu(getActivity(),
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
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            new FragmentCollectCusInfo(), true);
                    startActivity(new Intent(getActivity(), SearchCustomerActivity.class));
                    break;
                case Constant.JobMenu.ROLL_UP:
                    Intent i = new Intent(getActivity(), ManagerRollUpFragment.class);
                    startActivity(i);
                    break;
                case Constant.JobMenu.UPDATE_JOB_BOC2:
                    Intent intent = new Intent(getActivity(), WorkListActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(Constant.JobMenu.WORK_COLLECT_INFO) || name.contains(Constant.JobMenu.WORK_COLLECT_INFO_MBCCS2)) {
            arrayListManager.add(new Manager(
                    R.drawable.ic_cv_thuthap_thongtin_thitruong, getResources()
                    .getString(R.string.job_manager_4), 0,
                    Constant.JobMenu.WORK_COLLECT_INFO));
        }
        if (name.contains(Constant.JobMenu.WORK_COMMUNICATION) || name.contains(Constant.JobMenu.WORK_COMMUNICATION_MBCCS2)) {
            arrayListManager.add(new Manager(
                    R.drawable.ic_cv_truyenthong_chinhsach, getResources()
                    .getString(R.string.job_manager_5), 0,
                    Constant.JobMenu.WORK_COMMUNICATION));
        }
//        if (name.contains(Constant.JobMenu.WORK_UPDATE)) {
//            arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_cv,
//                    getResources().getString(R.string.job_manager_2), 0,
//                    Constant.JobMenu.WORK_UPDATE));
//        }
//        if (name.contains(Constant.JobMenu.ASIGN_JOB)) {
//            arrayListManager.add(new Manager(R.drawable.ic_cv_quan_ly_giao_viec,
//                    getResources().getString(R.string.text_menu_assignment), 0,
//                    Constant.JobMenu.ASIGN_JOB));
//        }
        if (name.contains(Constant.JobMenu.WORK_TASK_ROAD) || name.contains(Constant.JobMenu.WORK_TASK_ROAD_MBCCS2)) {
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

        if (name.contains(";menu_task_reassign;") || name.contains(";menu_task_reassign_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cv_quan_ly_giao_viec,
                    getResources().getString(R.string.task_assign_manager), 0,
                    Constant.JobMenu.WORK_TASK_ASSIGN_MANAGER));
        }

        if (name.contains(";menu_update_task_assign;") || name.contains(";menu_update_task_assign_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_cv,
                    getResources().getString(R.string.task_assign_staff), 0,
                    Constant.JobMenu.WORK_TASK_ASSIGN_STAFF));
        }

        if (name.contains(";menu_update_area;") || name.contains(";menu_update_area_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_dia_ban,
                    getResources().getString(R.string.task_update_area), 0,
                    Constant.JobMenu.WORK_TASK_UPDATE_AREA));
        }
        if (name.contains(";menu_update_family;") || name.contains(";menu_update_family_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cv_update_home_info,
                    getResources().getString(R.string.updateHD), 0,
                    Constant.JobMenu.WORK_TASK_UPDATE_FAMILY));
        }

        if (name.contains(Constant.VSAMenu.COLLECT_CUS_INFO) || name.contains(Constant.VSAMenu.COLLECT_CUS_INFO_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_thuthap_thongtin_khachhang,
                    getResources().getString(R.string.title_collect_cus_info),
                    0, Constant.JobMenu.COLLECT_CUS_INFO));
        }

//        if (name.contains(";m.roll.up;")) {
//            arrayListManager.add(new Manager(
//                    R.drawable.thu_thap_tt_thi_truong_03, getResources()
//                    .getString(R.string.roll_up), 0, Constant.JobMenu.ROLL_UP));
//        }

        arrayListManager.add(new Manager(R.drawable.ic_cv_cap_nhat_cv,
                getResources().getString(R.string.task_assign_staff_boc2), 0,
                Constant.JobMenu.UPDATE_JOB_BOC2));
        return arrayListManager;
    }
}