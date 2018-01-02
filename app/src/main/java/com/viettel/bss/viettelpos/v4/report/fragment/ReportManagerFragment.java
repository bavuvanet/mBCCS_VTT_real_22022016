package com.viettel.bss.viettelpos.v4.report.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.PagerAdapter;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerGridMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.fragment.LookupRevenueStationFragment;
import com.viettel.bss.viettelpos.v4.fragment.ReportRegisterFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReport;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportBonusVAS;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportCarePopup;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportForEmployee;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportGetInveneStaff;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportImagePayment;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportInventory;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportPromotion;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportRevenueGeneral;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportStaffIncome;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportTarget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 2/7/2017.
 */

public class ReportManagerFragment extends Fragment {
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private PagerAdapter salePagerAdapter;
    private View mView;
    private boolean isGridView = true;
    private boolean onCreateViewFirst = true;
    private List<GridMenu> lstData;

    private final String REPORT_KIT = "REPORT_KIT";
    private final String REPORT_CHANEL_CARE = "REPORT_CHANEL_CARE";
    private final String REPORT_CHANEL_CARE_EMPLOYEE = "REPORT_CHANEL_CARE_EMPLOYEE";
    private final String REPORT_PROMOTION = "REPORT_PROMOTION";
    private final String REPORT_INVENTORY = "REPORT_INVENTORY";
    private final String REPORT_IMAGE_PAYMENT = "REPORT_IMAGE_PAYMENT";
    private final String REPORT_STAFF_REVENUE_GERENAL = "REPORT_STAFF_REVENUE_GERENAL";
    private final String REPORT_STAFF_INCOME = "REPORT_STAFF_INCOME";
    private final String REPORT_TARGET = "REPORT_TARGET";
    private final String REPORT_GETINVENE_STAFF = "REPORT_GETINVENE_STAFF";
    private final String REPORT_BONUS_VAS = "REPORT_BONUS_VAS";
    private final String REPORT_EXCEPTION_DAILY = "REPORT_EXCEPTION_DAILY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);

            lstData = initGridMenuData();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            salePagerAdapter = new PagerAdapter(manager, getContext(), lstData);
            if(CommonActivity.isNullOrEmptyArray(lstData) || lstData.size() == 1){
                tabLayout.setVisibility(View.GONE);
            }

            //set Adapter to view pager
            viewPager.setAdapter(salePagerAdapter);

            //set tablayout with viewpager
            tabLayout.setupWithViewPager(viewPager);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            //Setting tabs from adpater
            tabLayout.setTabsFromPagerAdapter(salePagerAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstData, onItemClickListener);
            recyclerView.setAdapter(menuAdapter);

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        } else {
            onCreateViewFirst = false;
        }

        return mView;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if(CommonActivity.isNullOrEmpty(keyMenuName)){
                return;
            }

            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            switch (keyMenuName) {
                case REPORT_KIT:
                    FragmentReport synfragment = new FragmentReport();
                    ReplaceFragment.replaceFragment(getActivity(), synfragment, true);
                    break;
                case REPORT_CHANEL_CARE:
                    FragmentReportCarePopup fragmentReportCarePopup = new FragmentReportCarePopup();
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentReportCarePopup, true);
                    break;
                case REPORT_CHANEL_CARE_EMPLOYEE:
                    FragmentReportForEmployee fragEmployee = new FragmentReportForEmployee();
                    ReplaceFragment.replaceFragment(getActivity(), fragEmployee, true);
                    break;
                case REPORT_PROMOTION:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentReportPromotion(), true);
                    break;
                case REPORT_INVENTORY:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentReportInventory(), true);
                    break;
                case REPORT_IMAGE_PAYMENT:
                    FragmentReportImagePayment fragmentReportImagePayment = new FragmentReportImagePayment();
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentReportImagePayment, true);
                    break;
                case REPORT_STAFF_INCOME:
                    FragmentReportStaffIncome fragmentReportStaffIncome = new FragmentReportStaffIncome();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentReportStaffIncome, true);
                    break;
                case REPORT_STAFF_REVENUE_GERENAL:
                    FragmentReportRevenueGeneral fragmentReportRevenueGeneral = new FragmentReportRevenueGeneral();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentReportRevenueGeneral, true);
                    break;
                case REPORT_TARGET:
                    FragmentReportTarget fragmentReportTargetr = new FragmentReportTarget();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentReportTargetr, true);
                    break;
                case REPORT_GETINVENE_STAFF:
                    FragmentReportGetInveneStaff fragmentReportGetInveneStaff = new FragmentReportGetInveneStaff();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentReportGetInveneStaff, true);
                    break;
                case REPORT_BONUS_VAS:
                    FragmentReportBonusVAS fragmentReportBonusVAS = new FragmentReportBonusVAS();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentReportBonusVAS, true);
                    break;
                case REPORT_EXCEPTION_DAILY:
                    FragmentLookupLogMBCCS fragmentLookupLogMBCCS = new FragmentLookupLogMBCCS();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentLookupLogMBCCS, true);
                    break;

                case Constant.MENU_FUNCTIONS.REVENUE_STATION:
                    LookupRevenueStationFragment fragment = new LookupRevenueStationFragment();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                    break;

                case Constant.MENU_FUNCTIONS.REPORT_REGISTER:
                    ReportRegisterFragment reportRegisterDetailFragment = new ReportRegisterFragment();
                    ReplaceFragment.replaceFragment(getActivity(), reportRegisterDetailFragment, true);
                    break;
            }
        }
    };

    @Override
    public void onResume() {

        super.onResume();
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MainActivity.getInstance().setTitleActionBar(R.string.report);
        MainActivity.getInstance().enableMenuListOrGridView(true);
        MainActivity.getInstance().getMenuCreateOption().findItem(R.id.btnListOrGridView).setVisible(true);
        MainActivity.getInstance().getMenuCreateOption().findItem(R.id.btnHome).setVisible(false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("ReportManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

    }

    private List<GridMenu> initGridMenuData() {
        lstData = new ArrayList<>();

        GridMenu gridMenu = initReportMenu();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }
        return lstData;
    }

    private GridMenu initReportMenu() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.report));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        arrayListManager.add(new Manager(R.drawable.so_lieu_ha_tang_online_03,
                getResources().getString(R.string.REPORT_CHANEL_CARE), 0,
                REPORT_CHANEL_CARE));

        arrayListManager.add(new Manager(R.drawable.ic_bc_tan_suat_cs_kenh,
                getResources().getString(R.string.reporttskenh), 0,
                REPORT_CHANEL_CARE_EMPLOYEE));

        if (name.contains(";report_revenue;") || name.contains(";report_revenue_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_hoa_hong,
                    getResources().getString(R.string.report_promotion), 0,
                    REPORT_PROMOTION));
        }

        if (name.contains(";report_inventory;") || name.contains(";report_inventory_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_ton_kho,
                    getResources().getString(R.string.report_inventory), 0,
                    REPORT_INVENTORY));
        }

        if (name.contains(";report_hadb;") || name.contains(";report_hadb_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_hinh_anh_diem_ban,
                    getResources().getString(R.string.report_image_payment), 0,
                    REPORT_IMAGE_PAYMENT));
        }

//        if (name.contains(";report_boc;")) {
//            arrayListManager.add(new Manager(R.drawable.ic_bc_hoan_thanh_chi_tieu,
//                    getResources().getString(R.string.repor_staff_income), 0,
//                    REPORT_STAFF_INCOME));
//        }

        if (name.contains(";rp_sale_revenue;") || name.contains(";rp_sale_revenue_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_dt_bh,
                    getResources().getString(
                            R.string.repor_staff_revenue_general), 0,
                    REPORT_STAFF_REVENUE_GERENAL));
        }

        if (name.contains(";REPORT_TARGET;") || name.contains(";REPORT_TARGET_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_giao_chi_tieu,
                    getResources().getString(R.string.report_target), 0,
                    REPORT_TARGET));
        }

        if (name.contains(";REPORT_TARGET;") || name.contains(";REPORT_TARGET_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_giao_thue_bao,
                    getResources().getString(R.string.baocaogiaochitieu), 0,
                    REPORT_GETINVENE_STAFF));
        }

        if (name.contains(";rp_bonus_vas;") || name.contains(";rp_bonus_vas_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_phi_hoa_hong,
                    getResources().getString(R.string.report_bonus_vas), 0,
                    REPORT_BONUS_VAS));
        }

        if ("pmvt_huypq15".equalsIgnoreCase(Session.userName)) {

            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.lookup_log_mbccs), 0,
                    REPORT_EXCEPTION_DAILY));
        }

        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                getResources().getString(R.string.txtReportRevenueBTS), 0,
                Constant.MENU_FUNCTIONS.REVENUE_STATION));

        if (name.contains(Constant.VSAMenu.REPORT_REGISTER) || name.contains(Constant.VSAMenu.REPORT_REGISTER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_phi_hoa_hong,
                    getResources().getString(R.string.report_register), 0,
                    Constant.MENU_FUNCTIONS.REPORT_REGISTER));
        }

        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOptionMenuSelect(BaseMsg msg) {
        Log.d("TAG", "onOptionMenuSelect");
        if (isGridView) {
            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_luoi);
            viewFlipper.showNext();
        } else {
            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
            viewFlipper.showPrevious();
        }
        isGridView = !isGridView;
    }
}

