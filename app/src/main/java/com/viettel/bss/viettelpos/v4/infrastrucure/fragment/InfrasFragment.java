package com.viettel.bss.viettelpos.v4.infrastrucure.fragment;

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
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfoIDNumberManager;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfrastructureOnline;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/7/2017.
 */

public class InfrasFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;
    private final String ID_KS_HT_ONLINE = "ID_KS_HT_ONLINE";
    private final String ID_SL_KDT = "ID_SL_KDT";

    public static InfrasFragment newInstance() {
        return new InfrasFragment();
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
                case ID_KS_HT_ONLINE:
                    FragmentInfoIDNumberManager idNumberManager = new FragmentInfoIDNumberManager();
                    ReplaceFragment.replaceFragment(getActivity(),
                            idNumberManager, false);
                    break;
                case ID_SL_KDT:
                    FragmentInfrastructureOnline mInfrastructureOnline = new FragmentInfrastructureOnline();
                    ReplaceFragment.replaceFragment(getActivity(),
                            mInfrastructureOnline, false);
                    break;
//                case 2:
//                    FragmentShowDataInfrastructure mDataInfrastructure = new FragmentShowDataInfrastructure();
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            mDataInfrastructure, false);
//                    break;
                default:
                    break;
            }
        }
    };

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";infractrue_survey_online")) {
            arrayListManager.add(new Manager(R.drawable.ic_khao_sat_ha_tang,
                    getResources().getString(R.string.infrastructure_1), 0, ID_KS_HT_ONLINE));
        }

        if (name.contains(";infractrue_business_data")) {
            arrayListManager.add(new Manager(R.drawable.ic_so_lieu_kd_tram,
                    getResources().getString(R.string.infrastructure_3), 0, ID_SL_KDT));
        }
        return arrayListManager;
    }
}
