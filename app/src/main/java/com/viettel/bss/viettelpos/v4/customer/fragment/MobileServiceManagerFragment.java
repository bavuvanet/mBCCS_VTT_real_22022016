package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.advisory.screen.SearchAdvisoryFragment;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentChangeOffer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentManageBundleGroup;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentPrepairIdNo;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCustomerMustApprove;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.BlockOpenSubManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/6/2017.
 */

public class MobileServiceManagerFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String REGISTER = "REGISTER";
    private final String ID_VAS = "ID_VAS";
    private final String SEARCH_CUSTOMER = "SEARCH_CUSTOMER";
    private final String REPAIR_ID_NO = "REPAIR_ID_NO";
    private final String BUNDLE_GROUP = "BUNDLE_GROUP";
    private final String CHECK_USUALLY_CALL = "CHECK_USUALLY_CALL";
    private final String VIEW_OCS_HLR = "VIEW_OCS_HLR";
    private final String CHANGE_SIM = "CHANGE_SIM";
    private final String CHANGER_OFFER = "CHANGER_OFFER";
    private final String ADV_CUSTOMERS = "ADV_CUSTOMERS";
    public static MobileServiceManagerFragment newInstance() {
        return new MobileServiceManagerFragment();
    }

    @Override
    protected void unit(View v) {
        menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_recycler_view;
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
                case REGISTER:
                    RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
                    ReplaceFragment.replaceFragment(getActivity(),
                            registerInfoFragment, false);
                    break;
                case ID_VAS:
                    Intent intent = new Intent(getActivity(),
                            FragmentRegisterServiceVas.class);
                    startActivity(intent);
                    break;
                case SEARCH_CUSTOMER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchCustomerMustApprove(), true);
                    break;
                case REPAIR_ID_NO:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentPrepairIdNo(), true);
                    break;
                case BUNDLE_GROUP:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentManageBundleGroup(), true);
                    break;
                case CHECK_USUALLY_CALL:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentCheckUsuallyCall(), true);
                    break;
                case CHANGE_SIM:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchSubChangeSim(), true);
                    break;
                case VIEW_OCS_HLR:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentViewOcsHlr(), true);
                    break;
                case CHANGER_OFFER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentChangeOffer(), true);
                    break;
                case ADV_CUSTOMERS:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new SearchAdvisoryFragment(), false);
                    break;
                case Constant.BLOCK_OPEN_SUB_MOBILE:
                    FragmentSearchSubChangeSim fragmentSearchSubMobile = new FragmentSearchSubChangeSim();
                    Bundle bundle = new Bundle();
                    bundle.putString("functionKey", Constant.BLOCK_OPEN_SUB_MOBILE);
                    fragmentSearchSubMobile.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubMobile, true);
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
        if (name.contains(";cus.register;") || name.contains(";cus.register.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_dk_thongtin,
                    getResources().getString(R.string.customer_new), 0,
                    REGISTER));
        }

        if (name.contains(";sale.saling;") || name.contains(";sale.saling.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_daunoi_dichvu_vas,
                    getResources().getString(R.string.connectVas), 0, ID_VAS));
        }

        if (name.contains(";menu_approve;") || name.contains(";menu_approve_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_pheduyet_hoso_doisim,
                    getResources().getString(R.string.approve_change_sim), 0,
                    SEARCH_CUSTOMER));
        }

        if (name.contains(";menu_updateidno;") || name.contains(";menu_updateidno_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_suasai_cmt,
                    getResources().getString(R.string.repair_id_no), 0,
                    REPAIR_ID_NO));
        }

        if (name.contains(";bundle.group;") || name.contains(";bundle.group.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_nhombundle,
                    getResources().getString(R.string.manage_bundle_group), 0,
                    BUNDLE_GROUP));
        }

        if (name.contains(";check_usually_call;") || name.contains(";check_usually_call_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_ktra_so_thuongxuyen_lienlac,
                    getResources().getString(R.string.check_usually_call), 0,
                    CHECK_USUALLY_CALL));
        }

        if (name.contains(";menu.changesim;") || name.contains(";menu.changesim.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_doisim,
                    getResources().getString(R.string.txt_change_sim), 0,
                    CHANGE_SIM));
        }

        if (name.contains(";view_ocs_hlr;") || name.contains(";view_ocs_hlr_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xem_thongtin_ocs,
                    getString(R.string.view_ocs_hlr), 0, VIEW_OCS_HLR));
        }

        if (name.contains(";menu.change.product;") || name.contains(";menu.change.product.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_goicuoc,
                    getResources().getString(R.string.Chuyendoigoicuoctoms), 0,
                    CHANGER_OFFER));
        }

        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                .getString(R.string.advisory_customers), 0, ADV_CUSTOMERS));
        if (name.contains(";menu.block.open.sub.mobile;") || name.contains(";menu.block.open.sub.mobile.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.blockOpenSubMobile), 0,
                    Constant.BLOCK_OPEN_SUB_MOBILE));
        }
        return arrayListManager;
    }
}
