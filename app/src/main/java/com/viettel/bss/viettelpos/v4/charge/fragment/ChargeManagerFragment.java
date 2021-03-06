package com.viettel.bss.viettelpos.v4.charge.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDel;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelBankPlus;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelCTV;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNew;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNotify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeRe;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractDelay;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractNotPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReport;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReportVerify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractSearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifySearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifyUpdate;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentNotifySearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSendSmsCtv;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSuportUpdate;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdateComplain;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdateCustype;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdatePromotion;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentLookupDebitInfo;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 2/6/2017.
 */

public class ChargeManagerFragment extends Fragment {
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private PagerAdapter chargePagerAdapter;
    private View mView;
    private boolean isGridView = true;
    private boolean onCreateViewFirst = true;
    private List<GridMenu> lstData;

    private final String ID_NEW = "ID_NEW";
    private final String ID_RE = "ID_RE";
    private final String ID_DEL = "ID_DEL";
    private final String ID_CONTRACT_PAYMENT = "ID_CONTRACT_PAYMENT";
    private final String ID_CONTRACT_NOT_PAYMENT = "ID_CONTRACT_NOT_PAYMENT";
    private final String ID_CONTRACT_REPORT = "ID_CONTRACT_REPORT";
    private final String ID_UPDATE_PROMOTION = "ID_UPDATE_PROMOTION";
    private final String ID_CHARGE_NOTIFY = "ID_CHARGE_NOTIFY";
    private final String ID_SUPPORT_UPDATE = "ID_SUPPORT_UPDATE";
    private final String ID_UPDATE_CUS = "ID_UPDATE_CUS";
    private final String ID_UPDATE_COMPLAIN = "ID_UPDATE_COMPLAIN";
    private final String ID_CONTRACT_SEARCH = "ID_CONTRACT_SEARCH";
    private final String ID_SMS_CTV = "ID_SMS_CTV";
    private final String ID_DEL_CTV = "ID_DEL_CTV";
    private final String ID_CONTRACT_BANKPLUS = "ID_CONTRACT_BANKPLUS";
    private final String ID_VERIFY_UPDATE = "ID_VERIFY_UPDATE";
    private final String ID_VERIFY_SEARCH = "ID_VERIFY_SEARCH";
    private final String ID_CONTRACT_REPORT_VERIFY = "ID_CONTRACT_REPORT_VERIFY";
    private final String ID_CONTRACT_DELAY = "ID_CONTRACT_DELAY";
    private final String ID_CHARGE_NOTIFY_SEARCH = "ID_CHARGE_NOTIFY_SEARCH";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);

            lstData = initGridMenuData();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            chargePagerAdapter = new PagerAdapter(manager, getContext(), lstData);
            if(CommonActivity.isNullOrEmptyArray(lstData) || lstData.size() == 1){
                tabLayout.setVisibility(View.GONE);
            }

            //set Adapter to view pager
            viewPager.setAdapter(chargePagerAdapter);

            //set tablayout with viewpager
            tabLayout.setupWithViewPager(viewPager);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            //Setting tabs from adpater
            tabLayout.setTabsFromPagerAdapter(chargePagerAdapter);

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

    private List<GridMenu> initGridMenuData() {
        lstData = new ArrayList<>();

        GridMenu gridMenu = initMenuChargeManager();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }

        gridMenu = initMenuPayCharge();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }

        gridMenu = initMenuVerifyCus();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }

        return lstData;
    }

    private GridMenu initMenuChargeManager() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.chargeable));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();

        if (name.contains(";pm.assign.new.contract;") || name.contains(";pm.assign.new.contract.mbccs2;")) {
            arrayListManager
                    .add(new Manager(R.drawable.giaomoiicon, getResources().getString(R.string.charge_new), 0, ID_NEW));
        }

        if (name.contains(";pm.assign_old_contract;") || name.contains(";pm.assign_old_contract_mbccs2;")) {
            arrayListManager
                    .add(new Manager(R.drawable.giaolaiicon, getResources().getString(R.string.charge_re), 0, ID_RE));
        }

        if (name.contains(";pm_payment;") || name.contains(";pm_payment_mbccs2;")) {

            arrayListManager
                    .add(new Manager(R.drawable.ic_gachno, getResources().getString(R.string.charge_del), 0, ID_DEL));
        }

        if (name.contains(";cus_need_payment;") || name.contains(";cus_need_payment_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_khachhang_can_thucuoc, getResources().getString(R.string.contract_payment),
                    0, ID_CONTRACT_PAYMENT));
        }

        if (name.contains(";contract_not_payment;") || name.contains(";contract_not_payment_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_khachhang_ton_chua_thucuoc,
                    getResources().getString(R.string.contract_not_payment), 0, ID_CONTRACT_NOT_PAYMENT));
        }

        if (name.contains(";contract_report;") || name.contains(";contract_report_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tyle_thucuoc, getResources().getString(R.string.contract_report),
                    0, ID_CONTRACT_REPORT));
        }

        if (name.contains(";update_subscriber;") || name.contains(";update_subscriber_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_khuyenmai,
                    getResources().getString(R.string.title_menu_update_promotion), 0, ID_UPDATE_PROMOTION));
        }

        if (name.contains(";charge_notify;") || name.contains(";charge_notify_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_thongbao_cuoc, getResources().getString(R.string.charge_notify), 0,
                    ID_CHARGE_NOTIFY));
        }
        if (name.contains(";print.charge.notify;") || name.contains(";print.charge.notify.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_thongbao_cuoc, getString(R.string.search_charge_notify), 0,
                    ID_CHARGE_NOTIFY_SEARCH));
        }
        if (name.contains(";support_update;") || name.contains(";support_update_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_quanly_hotro, getResources().getString(R.string.support_update),
                    0, ID_SUPPORT_UPDATE));
        }

        if (name.contains(";update_custype;") || name.contains(";update_custype_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_doituong_khachhang, getResources().getString(R.string.updateCustype), 0,
                    ID_UPDATE_CUS));
        }

        if (name.contains(";update_complain;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_khieunai_khachhang, getResources().getString(R.string.updateComplain),
                    0, ID_UPDATE_COMPLAIN));
        }

        if (name.contains(";contract_search;") || name.contains(";contract_search_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_hopdong, getResources().getString(R.string.contract_search),
                    0, ID_CONTRACT_SEARCH));
        }

        if (name.contains(";contract_send_sms;") || name.contains(";contract_send_sms_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_nhantin_cho_ctv,
                    getResources().getString(R.string.sendSms), 0, ID_SMS_CTV));
        }
        if (name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO) || name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_nocuoc,
                    getResources().getString(R.string.txt_lookup_debit_info),
                    0, Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO));
        }
        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

    private GridMenu initMenuPayCharge() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.txt_thanh_toan_cuoc));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();

        if (name.contains(";pm_payment_ctv;") || name.contains(";pm_payment_ctv_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_gachno, getResources().getString(R.string.charge_del_ctv),
                    0, ID_DEL_CTV));
        }

        if (name.contains(";contract_bankplus;") || name.contains(";contract_bankplus_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.charge_del_bankplus), 0, ID_CONTRACT_BANKPLUS));
        }

        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

    private GridMenu initMenuVerifyCus() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.txt_ver_cus_info));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();

        if (name.contains(";verify_update;") || name.contains(";verify_update_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_xacminh,
                    getResources().getString(R.string.contract_verify_update), 0, ID_VERIFY_UPDATE));
        }

        if (name.contains(";verify_search;") || name.contains(";verify_search_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_xacminh,
                    getResources().getString(R.string.contract_verify_search), 0, ID_VERIFY_SEARCH));
        }

        if (name.contains(";contract_report_verify;") || name.contains(";contract_report_verify_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_tyle_xacminh,
                    getResources().getString(R.string.contract_report_verify), 0,
                    ID_CONTRACT_REPORT_VERIFY));
        }

        if (name.contains(";contract_delay;") || name.contains(";contract_delay_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_hoanchan,
                    getResources().getString(R.string.contract_delay), 0,
                    ID_CONTRACT_DELAY));
        }
        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.chargeable);
        MainActivity.getInstance().enableMenuListOrGridView(true);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("SaleManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            try {
                Manager manager = (Manager) item;
                String keyMenuName = manager.getKeyMenuName();
                if(CommonActivity.isNullOrEmpty(keyMenuName)){
                    return;
                }
                CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());
                switch (keyMenuName) {
                    case ID_NEW:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNew(), true);
                        break;
                    case ID_RE:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeRe(), true);
                        break;
                    case ID_DEL:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDel(), true);
                        break;
                    case ID_CONTRACT_PAYMENT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractPayment(), true);
                        break;
                    case ID_CONTRACT_NOT_PAYMENT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractNotPayment(), true);
                        break;
                    case ID_CONTRACT_REPORT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractReport(), true);
                        break;
                    case ID_UPDATE_PROMOTION:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdatePromotion(), true);
                        break;
                    case ID_CHARGE_NOTIFY:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNotify(), true);
                        break;
                    case ID_SUPPORT_UPDATE:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentSuportUpdate(), true);
                        break;
                    case ID_UPDATE_COMPLAIN:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateComplain(), true);
                        break;
                    case ID_CONTRACT_SEARCH:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractSearch(), true);
                        break;
                    case ID_SMS_CTV:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentSendSmsCtv(), true);
                        break;
                    case ID_VERIFY_UPDATE:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractVerifyUpdate(), true);
                        break;
                    case ID_VERIFY_SEARCH:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractVerifySearch(), true);
                        break;
                    case ID_CONTRACT_REPORT_VERIFY:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractReportVerify(), true);
                        break;
                    case ID_CONTRACT_DELAY:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractDelay(), true);
                        break;
                    case ID_DEL_CTV:
//                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDelCTV(), true);
                        Intent intent = new Intent(getActivity(),FragmentChargeDelCTV.class);
//			intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
                        startActivity(intent);
                        break;
                    case ID_CONTRACT_BANKPLUS:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDelBankPlus(), true);
                        break;
                    case ID_UPDATE_CUS:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateCustype(), true);
                        break;
                    case ID_CHARGE_NOTIFY_SEARCH:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentNotifySearch(), true);
                        break;

                    case Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO:
                        FragmentLookupDebitInfo fragment = new FragmentLookupDebitInfo();

                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.FUNCTION,
                                Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
                        fragment.setArguments(bundle);

                        ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex){
                Log.d("ChargeManagerFragment", "Error", ex);
            }
        }
    };

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