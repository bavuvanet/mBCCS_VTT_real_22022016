package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

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
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class FragmentBankplusManager extends FragmentCommon implements OnItemClickListener {

    private ListView listView;
    private ArrayList<Manager> arrayListManager;
    private MenuAdapter mAdapterManager;

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.bankplus_menu);
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

        if (name.contains(";m.pay.telecom;") || name.contains("m.buy.pincode")) {
            // Cuoc vien thong hoac mua the cao
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.menu_telecommunication), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_TELECOMMUNICATION,
                    getString(R.string.bill_telecom_hint)));
        }
        if (name.contains("m.transfer.money")
                || name.contains("m.deliver.money")
                || name.contains("m.charge.to.bank")) {

            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.bankplus_menu_transaction_money), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_TRANSACTION_MONEY));
        }

        if (name.contains("m.pay.invoice")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.bankplus_menu_payment_invoice), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_PAYMENT_INVOICE));

            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.bankplus_menu_buy_game_card), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_BUY_GAME_CARD));

//            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
//                    getResources().getString(R.string.bankplus_menu_buy_kaspersky_card), 0,
//                    Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_BUY_KASPERSKY_CARD));
        }

        arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                getResources().getString(R.string.bankplus_menu_utilities), 0,
                Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_UTILITIES));

        mAdapterManager = new MenuAdapter(arrayListManager, getActivity());
        listView.setAdapter(mAdapterManager);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String menuName = arrayListManager.get(arg2).getKeyMenuName();
        MainActivity.mLevelMenu = 1;

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_TELECOMMUNICATION)) {
            FragmentTelecommunicationManager fragment =
                    new FragmentTelecommunicationManager();
            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_TRANSACTION_MONEY)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new TransactionMoneyManager(), false);
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_PAYMENT_INVOICE)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new PaymentInvoiceManager(), false);
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_BUY_GAME_CARD)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new BuyGameCardFragment(), false);
            return;
        }

//        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_BUY_KASPERSKY_CARD)) {
//            ReplaceFragment.replaceFragment(getActivity(),
//                    new BuyKasperskyCardFragment(), false);
//            return;
//        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_MENU_UTILITIES)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new UtilitiesFragment(), false);
            return;
        }
    }
}
