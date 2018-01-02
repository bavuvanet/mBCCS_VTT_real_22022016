package com.viettel.bss.viettelpos.v4.charge.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelBankPlus;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelCTV;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/4/2017.
 */

public class PayChargeFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListViewMenuAdapter menuAdapter;
    private final String ID_DEL_CTV = "ID_DEL_CTV";
    private final String ID_CONTRACT_BANKPLUS = "ID_CONTRACT_BANKPLUS";
    public static PayChargeFragment newInstance() {
        return new PayChargeFragment();
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

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        ArrayList<Manager> arrayListManager = new ArrayList<>();
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";pm_payment_ctv;") || name.contains(";pm_payment_ctv_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_gachno, getResources().getString(R.string.charge_del_ctv),
                    0, ID_DEL_CTV));
        }

        if (name.contains(";contract_bankplus;") || name.contains(";contract_bankplus_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.charge_del_bankplus), 0, ID_CONTRACT_BANKPLUS));
        }



        return arrayListManager;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            try {
                Manager manager = (Manager) item;
                String keyMenuName = manager.getKeyMenuName();
                CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());
                switch (keyMenuName) {
                    case ID_DEL_CTV:
//                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDelCTV(), true);
                        Intent intent = new Intent(getActivity(),FragmentChargeDelCTV.class);
//			intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
                        startActivity(intent);
                        break;
                    case ID_CONTRACT_BANKPLUS:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDelBankPlus(), true);
                        break;
                    default:
                        break;

                }
            } catch (Exception ex){
                Log.d("PayChargeFragment", "Error", ex);
            }
        }
    };
}
