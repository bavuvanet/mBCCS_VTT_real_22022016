package com.viettel.bss.viettelpos.v4.sale.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChange2GTo3G;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentConfirmNote;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentListOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSalingReturn;
import com.viettel.bss.viettelpos.v4.sale.business.GetStockOrderManager;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by Toancx on 1/12/2017.
 */

public class InventoryFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    // Ban dut cho kenh db, ctv
    private final String CONFIRM_NOTE = "CONFIRM_NOTE";
    private final String VIEW_INFO_ORDER_ITEM = "VIEW_INFO_ORDER_ITEM";
    private final String APPROVE_ORDER = "APPROVE_ORDER";
    private final String RETURN_THE_GOOD = "RETURN_THE_GOOD";
    private final String SALE_2G_TO_3G = "SALE_2G_TO_3G";

    public static InventoryFragment newInstance() {
        return new InventoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_recycler_view;
    }

    @Override
    public void unit(View v) {
        menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    private final OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if (CommonActivity.isNullOrEmpty(keyMenuName)) {
                return;
            }
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            switch (keyMenuName) {
                case CONFIRM_NOTE:
                    FragmentConfirmNote fragment = new FragmentConfirmNote();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                case VIEW_INFO_ORDER_ITEM:
                    if (CommonActivity.isNetworkConnected(getActivity())) {
                        GetStockOrderManager getStockOrderManager = new GetStockOrderManager(getActivity());
                        getStockOrderManager.execute();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.errorNetwork), getActivity().getString(R.string.app_name));
                        dialog.show();
                    }
                    break;
                case APPROVE_ORDER:
                    FragmentListOrder fragmentListOrder = new FragmentListOrder();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentListOrder, false);
                    break;
                case RETURN_THE_GOOD:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleSalingReturn(), false);
                    break;
                case SALE_2G_TO_3G:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChange2GTo3G(), false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void setPermission() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("InventoryFragment", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(Constant.VSAMenu.SALE_CONFIRM_NOTE) || name.contains(Constant.VSAMenu.SALE_CONFIRM_NOTE_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_xac_nhan_nhap_hang,
                    getResources().getString(R.string.confirmNote), 0, CONFIRM_NOTE));
        }

        if (name.contains(Constant.VSAMenu.SALE_ORDER) || name.contains(Constant.VSAMenu.SALE_ORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_dat_hang_cap_tren,
                    getResources().getString(R.string.viewinfoandorder), 0, VIEW_INFO_ORDER_ITEM));
        }

        if (name.contains(Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER) || name.contains(Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_phe_duyet_don_hang,
                    getResources().getString(R.string.approveOrder), 0, APPROVE_ORDER));
        }

        if (name.contains(Constant.VSAMenu.MENU_RETURN_GOOD) || name.contains(Constant.VSAMenu.MENU_RETURN_GOOD_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_hang,
                    getResources().getString(R.string.menu_item_sale_return_the_good), 0, RETURN_THE_GOOD));
        }

        if (name.contains(";2Gto3G;") || name.contains(";2Gto3G.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_2g_3g,
                    getResources().getString(R.string.change2Gto3G), 0, SALE_2G_TO_3G));
        }


        return arrayListManager;
    }
}
