package com.viettel.bss.viettelpos.v4.sale.fragment;

import android.app.Activity;
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
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentActiveAccountPayment;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChangePassAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCheckoutMoney;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseStaff;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateChanel;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateInvoice;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentInfoSearch;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentRechargeToBank;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchISDN;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchKIT;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchSerialCard;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 1/12/2017.
 */

public class UtilsFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    // Ban dut cho kenh db, ctv
    private final String CREATE_INVOICE = "CREATE_INVOICE";
    private final String SALE_CREATE_CHANEL = "SALE_CREATE_CHANEL";
    private final String SALE_ACTIVE_ACCOUNT_PAYMENT = "SALE_ACTIVE_ACCOUNT_PAYMENT";
    private final String SALE_INFO_SEARCH = "SALE_INFO_SEARCH";
    private final String SALE_SEARCH_ISDN = "SALE_SEARCH_ISDN";
    private final String RECHARGE_TO_BANK = "RECHARGE_TO_BANK";
    private final String APPROVE_MONEY = "APPROVE_MONEY";

    private final String CHANGE_PASS_ANYPAY = "CHANGE_PASS_ANYPAY";
    private final String SEARCH_ANYPAY = "SEARCH_ANYPAY";
    private final String SALE_SEARCH_SERIAL_CARD = "SALE_SEARCH_SERIAL_CARD";
    private final String SALE_SEARCH_KIT = "SALE_SEARCH_KIT";
    private final String VIEW_STOCK = "VIEW_STOCK";
    public static UtilsFragment newInstance() {
        return new UtilsFragment();
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
                case CREATE_INVOICE:
                    FragmentCreateInvoice fragment = new FragmentCreateInvoice();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                case SALE_CREATE_CHANEL:
                    FragmentCreateChanel fragmentCreateChanel = new FragmentCreateChanel();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentCreateChanel, false);
                    break;
                case SALE_ACTIVE_ACCOUNT_PAYMENT:
                    FragmentActiveAccountPayment fragmentActiveAccount = new FragmentActiveAccountPayment();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentActiveAccount, false);
                    break;
                case SALE_INFO_SEARCH:
                    FragmentInfoSearch fragmentInfoSearch = new FragmentInfoSearch();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentInfoSearch, false);
                    break;
                case SALE_SEARCH_ISDN:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchISDN(), false);
                    break;
                case RECHARGE_TO_BANK:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentRechargeToBank(), false);
                    break;
                case APPROVE_MONEY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentCheckoutMoney(), false);
                    break;
                case SALE_SEARCH_SERIAL_CARD:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchSerialCard(), false);
                    break;
                case SALE_SEARCH_KIT:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchKIT(), false);
                    break;
                case CHANGE_PASS_ANYPAY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChangePassAnypay(), false);
                    break;
                case VIEW_STOCK:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChooseStaff(), false);
                    break;
                case SEARCH_ANYPAY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchAnypay(), false);
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

        if (name.contains(Constant.VSAMenu.SALE_CREATE_INVOICE) || name.contains(Constant.VSAMenu.SALE_CREATE_INVOICE_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_lap_hoa_don,
                    getResources().getString(R.string.createInvoice), 0, CREATE_INVOICE));
        }

        if (name.contains(Constant.VSAMenu.MENU_CREATE_CHANNEL) || name.contains(Constant.VSAMenu.MENU_CREATE_CHANNEL_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_cap_nhat_ma_trang, getResources().getString(R.string.add_news_chanel),
                    0, SALE_CREATE_CHANEL));
        }

        if (name.contains(Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT) || name.contains(Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_kh_tk_thanh_toan,
                    getResources().getString(R.string.add_active_account_payment), 0, SALE_ACTIVE_ACCOUNT_PAYMENT));
        }

        if (name.contains(Constant.VSAMenu.MENU_REVIEW_PAYMENT_STAFF) || name.contains(Constant.VSAMenu.MENU_REVIEW_PAYMENT_STAFF_MBCCS2)) {

            arrayListManager.add(new Manager(R.drawable.ic_duyet_phieu_nop_tien,
                    getResources().getString(R.string.add_info_search), 0, SALE_INFO_SEARCH));

        }

        if (name.contains(";search_isdn;") || name.contains(";search_isdn_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_isdn,
                    getResources().getString(R.string.sale_for_search_isdn), 0, SALE_SEARCH_ISDN));
        }

        if (name.contains(";rechargeToBank;") || name.contains(";rechargeToBank.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_nap_tien_vao_tk,
                    getResources().getString(R.string.rechargetobank), 0, RECHARGE_TO_BANK));
        }

        if (name.contains(";approveBplus;") || name.contains(";approveBplus.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tich_duyet_nop_tien,
                    getResources().getString(R.string.tichduyetnoptien), 0, APPROVE_MONEY));
        }

        if (name.contains(";sale_search_serial_card;") || name.contains(";sale_search_serial_card_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich,
                    getResources().getString(R.string.sale_search_serial_card), 0, SALE_SEARCH_SERIAL_CARD));
        }

        if (name.contains(";sale_search_kit;") || name.contains(";sale_search_kit_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich,
                    getResources().getString(R.string.sale_search_kit), 0, SALE_SEARCH_KIT));
        }

        if (name.contains(";change.pass.anypay;") || name.contains(";change.pass.anypay.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.change_pass_anypay), 0, CHANGE_PASS_ANYPAY));
        }

        if (name.contains(";view_stock;") || name.contains(";view_stock_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xem_kho, getResources().getString(R.string.view_stock),
                    0, VIEW_STOCK));
        }

        arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich, getResources().getString(R.string.tctienich), 0,
                SEARCH_ANYPAY));
        return arrayListManager;
    }
}
