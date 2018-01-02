package com.viettel.bss.viettelpos.v4.cc.fragment;

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
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.ComplainFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/16/2017.
 */

public class CCFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListViewMenuAdapter menuAdapter;

    private final String UPDATE_GIFT = "UPDATE_GIFT";
    private final String ADV_CUSTOMERS = "ADV_CUSTOMERS";
    private final String RECEIVE_COMPLAIN_CUSTOMNER = "RECEIVE_COMPLAIN_CUSTOMNER";

    public static CCFragment newInstance() {
        return new CCFragment();
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
        if (name.contains(";update.gift;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                    .getString(R.string.update_gift), 0, UPDATE_GIFT));
        }

//        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
//                .getString(R.string.advisory_customers), 0, ADV_CUSTOMERS));

        if(name.contains(Constant.VSAMenu.COMPLAIN_RECEIVER)) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                    .getString(R.string.txt_create_complain), 0, RECEIVE_COMPLAIN_CUSTOMNER));
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
                    case UPDATE_GIFT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateGift(), true);
                        break;
                    case RECEIVE_COMPLAIN_CUSTOMNER:
                        ReplaceFragment.replaceFragment(getActivity(), new ComplainFragment(), true);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                Log.d("CCFragment", "Error", ex);
            }
        }
    };
}
