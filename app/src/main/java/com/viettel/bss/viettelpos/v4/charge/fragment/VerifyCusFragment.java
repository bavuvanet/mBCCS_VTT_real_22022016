package com.viettel.bss.viettelpos.v4.charge.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractDelay;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReportVerify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifySearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifyUpdate;
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

public class VerifyCusFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListViewMenuAdapter menuAdapter;
    private final String ID_VERIFY_UPDATE = "ID_VERIFY_UPDATE";
    private final String ID_VERIFY_SEARCH = "ID_VERIFY_SEARCH";
    private final String ID_CONTRACT_REPORT_VERIFY = "ID_CONTRACT_REPORT_VERIFY";
    private final String ID_CONTRACT_DELAY = "ID_CONTRACT_DELAY";
    public static VerifyCusFragment newInstance() {
        return new VerifyCusFragment();
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
        ArrayList<Manager>  arrayListManager = new ArrayList<>();
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";verify_update;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_xacminh,
                    getResources().getString(R.string.contract_verify_update), 0, ID_VERIFY_UPDATE));
        }

        if (name.contains(";verify_search;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_xacminh,
                    getResources().getString(R.string.contract_verify_search), 0, ID_VERIFY_SEARCH));
        }

        if (name.contains(";contract_report_verify;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bc_tyle_xacminh,
                    getResources().getString(R.string.contract_report_verify), 0,
                    ID_CONTRACT_REPORT_VERIFY));
        }

        if (name.contains(";contract_delay;")) {
            arrayListManager.add(new Manager(R.drawable.ic_hoanchan,
                    getResources().getString(R.string.contract_delay), 0,
                    ID_CONTRACT_DELAY));
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
                    default:
                        break;
                }
            } catch (Exception ex){
                Log.d("PayChargeFragment", "Error", ex);
            }
        }
    };
}
