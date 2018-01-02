package com.viettel.bss.viettelpos.v4.contract.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.fragment.ReportRegisterFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
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
import com.viettel.bss.viettelpos.v4.report.fragment.FragmentLookupLogMBCCS;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 8/5/2017.
 */
public class ContractFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String CONTRACT_MANAGER = "CONTRACT_MANAGER";

    public static ContractFragment newInstance() {
        return new ContractFragment();
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
                case CONTRACT_MANAGER:
                    FragmentSearchContract fragmentSearchContract = new FragmentSearchContract();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchContract, false);
                    break;
            }
        }
    };

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";menu.update.contract;")) {
            arrayListManager
                    .add(new Manager(R.drawable.ic_icon_contract_manager, getResources()
                            .getString(R.string.quanlihopdong), 0,
                            CONTRACT_MANAGER, ""));
        }

        return arrayListManager;
    }
}
