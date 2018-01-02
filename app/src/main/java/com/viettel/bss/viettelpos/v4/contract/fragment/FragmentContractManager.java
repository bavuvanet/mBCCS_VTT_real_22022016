package com.viettel.bss.viettelpos.v4.contract.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.MenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

public class FragmentContractManager extends FragmentCommon implements OnItemClickListener {
    private ListView listView;
    private ArrayList<Manager> arrayListManager;
    private MenuAdapter mAdapterManager;
    private final String CONTRACT_MANAGER = "Contract_manager";

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.quanlihopdong);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_listview;
    }

    @Override
    protected void unit(View v) {
        listView = (ListView) v.findViewById(R.id.listView);
        arrayListManager = new ArrayList<Manager>();

        addManagerList();
    }

    @Override
    protected void setPermission() {

    }

    private void addManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        arrayListManager.clear();
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";menu.update.contract;")) {
            arrayListManager
                    .add(new Manager(R.drawable.ic_icon_contract_manager, getResources()
                            .getString(R.string.quanlihopdong), 0,
                            CONTRACT_MANAGER,""));
        }

        mAdapterManager = new MenuAdapter(arrayListManager, getActivity());
        listView.setAdapter(mAdapterManager);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String menuName = arrayListManager.get(arg2).getKeyMenuName();
        MainActivity.mLevelMenu = 1;
        if (menuName.equals(CONTRACT_MANAGER)) {
            FragmentSearchContract fragment = new FragmentSearchContract();
            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            return;
        }

    }

}
