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
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentWarranty;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentActiveAccountPayment;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentAnypayExchange;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentAnypayISDN;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChange2GTo3G;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChangePassAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCheckoutMoney;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseBHLD;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseStaff;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentConfirmNote;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateChanel;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateInvoice;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentInfoSearch;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentListOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentRechargeToBank;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleByOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSalingReturn;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleToCustomers;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchISDN;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchKIT;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchSerialCard;
import com.viettel.bss.viettelpos.v4.sale.business.GetStockOrderManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 1/12/2017.
 */

public class SaleFragment extends FragmentCommon {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String SALE_SALING = "SALE_SALING";
    private final String SALE_DEPOSIT = "SALE_DEPOSIT";
    private final String SALE_BHLD = "SALE_BHLD";
    private final String SALE_RETAIL = "SALE_RETAIL";
    private final String SALE_BY_ORDER = "SALE_BY_ORDER";
    private final String SALE_TO_CUSTOMER = "SALE_TO_CUSTOMER";

    private final String SALE_ANYPAY_ISDN = "SALE_ANYPAY_ISDN";
    private final String SALE_ANYPAY_EXCHANGE = "SALE_ANYPAY_EXCHANGE";
    private final String CHANNEL_ORDER = "CHANNEL_ORDER";
    private final String STAFF_HANDLE_ORDER = "STAFF_HANDLE_ORDER";
    private final String SALE_PROMOTION = "SALE_PROMOTION";
    private final String VIEW_STOCK = "VIEW_STOCK";
    private final String SALE_SEARCH_KIT = "SALE_SEARCH_KIT";
    private final String SALE_SEARCH_SERIAL_CARD = "SALE_SEARCH_SERIAL_CARD";
    private final String SEARCH_ANYPAY = "SEARCH_ANYPAY";
    private final String CHANGE_PASS_ANYPAY = "CHANGE_PASS_ANYPAY";
    private final String APPROVE_MONEY = "APPROVE_MONEY";
    private final String RECHARGE_TO_BANK = "RECHARGE_TO_BANK";
    private final String SALE_2G_TO_3G = "SALE_2G_TO_3G";
    private final String SALE_SEARCH_ISDN = "SALE_SEARCH_ISDN";
    private final String RETURN_THE_GOOD = "RETURN_THE_GOOD";
    private final String SALE_INFO_SEARCH = "SALE_INFO_SEARCH";
    private final String SALE_ACTIVE_ACCOUNT_PAYMENT = "SALE_ACTIVE_ACCOUNT_PAYMENT";
    private final String SALE_CREATE_CHANEL = "SALE_CREATE_CHANEL";
    private final String APPROVE_ORDER = "APPROVE_ORDER";
    private final String VIEW_INFO_ORDER_ITEM = "VIEW_INFO_ORDER_ITEM";
    private final String CREATE_INVOICE = "CREATE_INVOICE";
    private final String CONFIRM_NOTE = "CONFIRM_NOTE";

    public static SaleFragment newInstance() {
        return new SaleFragment();
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
            Log.d("keyMenuName", keyMenuName);
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            if (keyMenuName.equalsIgnoreCase(Constant.MENU_FUNCTIONS.SALE_SALING)) {
                Bundle bundle = new Bundle();
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(SALE_DEPOSIT)) {
                Bundle bundle = new Bundle();
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_DEPOSIT);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(SALE_BHLD)) {
                FragmentChooseBHLD fragment = new FragmentChooseBHLD();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(SALE_RETAIL)) {
                Bundle b = new Bundle();
                b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_RETAIL);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(b);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(SALE_PROMOTION)) {
                Bundle b = new Bundle();
                b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_PROMOTION);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(b);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(CONFIRM_NOTE)) {
                FragmentConfirmNote fragment = new FragmentConfirmNote();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(CREATE_INVOICE)) {
                FragmentCreateInvoice fragment = new FragmentCreateInvoice();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

            } else if (keyMenuName.equals(SALE_BY_ORDER)) {
                FragmentSaleByOrder fragment = new FragmentSaleByOrder();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            } else if (keyMenuName.equals(VIEW_INFO_ORDER_ITEM)) {
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    GetStockOrderManager getStockOrderManager = new GetStockOrderManager(getActivity());
                    getStockOrderManager.execute();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.errorNetwork), getActivity().getString(R.string.app_name));
                    dialog.show();
                }
            } else if (keyMenuName.equals(APPROVE_ORDER)) {
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                ReplaceFragment.replaceFragment(getActivity(), fragmentListOrder, false);
            } else if (keyMenuName.equals(SALE_CREATE_CHANEL)) {
                FragmentCreateChanel fragmentCreateChanel = new FragmentCreateChanel();
                ReplaceFragment.replaceFragment(getActivity(), fragmentCreateChanel, false);
            } else if (keyMenuName.equals(SALE_ACTIVE_ACCOUNT_PAYMENT)) {
                FragmentActiveAccountPayment fragmentActiveAccount = new FragmentActiveAccountPayment();
                ReplaceFragment.replaceFragment(getActivity(), fragmentActiveAccount, false);
            } else if (keyMenuName.equals(SALE_INFO_SEARCH)) {
                FragmentInfoSearch fragmentInfoSearch = new FragmentInfoSearch();
                ReplaceFragment.replaceFragment(getActivity(), fragmentInfoSearch, false);
            } else if (keyMenuName.equals(RETURN_THE_GOOD)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleSalingReturn(), false);
            } else if (keyMenuName.equals(SALE_TO_CUSTOMER)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleToCustomers(), false);
            } else if (keyMenuName.equals(SALE_SEARCH_ISDN)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchISDN(), false);
            } else if (keyMenuName.equals(SALE_2G_TO_3G)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChange2GTo3G(), false);
            } else if (keyMenuName.equals(RECHARGE_TO_BANK)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentRechargeToBank(), false);
            } else if (keyMenuName.equals(APPROVE_MONEY)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentCheckoutMoney(), false);
            } else if (keyMenuName.equals(SALE_SEARCH_SERIAL_CARD)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchSerialCard(), false);
            } else if (keyMenuName.equals(SALE_SEARCH_KIT)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchKIT(), false);
            } else if (keyMenuName.equals(SALE_ANYPAY_ISDN)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayISDN(), false);
            } else if (keyMenuName.equals(SALE_ANYPAY_EXCHANGE)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayExchange(), false);
            } else if (keyMenuName.equals(VIEW_STOCK)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChooseStaff(), false);
            } else if (keyMenuName.equals(CHANGE_PASS_ANYPAY)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChangePassAnypay(), false);
            } else if (keyMenuName.equals(CHANNEL_ORDER)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrder(), false);
            } else if (keyMenuName.equals(SEARCH_ANYPAY)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchAnypay(), false);
            } else if (keyMenuName.equals(STAFF_HANDLE_ORDER)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrderManager(), false);
            } else if (keyMenuName.equals(Constant.MENU_FUNCTIONS.DO_WARRANTY)) {
                ReplaceFragment.replaceFragment(getActivity(), new FragmentWarranty(), true);
            }
        }
    };

    @Override
    public void setPermission() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("SaleFragment", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(Constant.VSAMenu.SALE_SALING) || name.contains(Constant.VSAMenu.SALE_SALING_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_dut, getResources().getString(R.string.sale_saling), 0,
                    SALE_SALING));
        }

        if (name.contains(Constant.VSAMenu.SALE_DEPOSIT) || name.contains(Constant.VSAMenu.SALE_DEPOSIT_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_dat_coc, getResources().getString(R.string.sale_deposit),
                    0, SALE_DEPOSIT));
        }

        if (name.contains(Constant.VSAMenu.SALE_PROGRAM) || name.contains(Constant.VSAMenu.SALE_PROGRAM_MBCCS2)) {
            arrayListManager
                    .add(new Manager(R.drawable.ic_ban_cho_ct_bhld, getResources().getString(R.string.sale_bhld), 0, SALE_BHLD));
        }
        if (name.contains(Constant.VSAMenu.SALE_RETAIL) || name.contains(Constant.VSAMenu.SALE_RETAIL_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_dut, getResources().getString(R.string.sale_retail), 0,
                    SALE_RETAIL));
        }

        if (name.contains(Constant.VSAMenu.SALE_BY_ORDER) || name.contains(Constant.VSAMenu.SALE_BY_ORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_hang_theo_don, getResources().getString(R.string.sale_by_order),
                    0, SALE_BY_ORDER));
        }

        if (name.contains(";outlet_sale;") || name.contains(";outlet_sale_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_phe_duyet_don_hang,
                    getResources().getString(R.string.sale_for_customer), 0, SALE_TO_CUSTOMER));
        }

        if (name.contains(";sale_anypay_isdn;") || name.contains(";sale_anypay_isdn_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_anypay,
                    getResources().getString(R.string.sale_anypay_isdn), 0, SALE_ANYPAY_ISDN));
        }

        if (name.contains(";sale_anypay_exchange;") || name.contains(";sale_anypay_exchange_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_anypay,
                    getResources().getString(R.string.sale_anypay_exchange), 0, SALE_ANYPAY_EXCHANGE));
        }

        if (name.contains(";channel.order;") || name.contains(";channel.order.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_diem_ban_dat_hang,
                    getResources().getString(R.string.channel_order), 0, CHANNEL_ORDER));
        }

        if (name.contains(";staff_manage_order;") || name.contains(";staff_manage_order_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xu_ly_don_hang,
                    getResources().getString(R.string.xy_ly_don_hang_diem_ban), 0, STAFF_HANDLE_ORDER));
        }

        if (name.contains(";sale_promotion;") || name.contains(";sale_promotion_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_khuyen_mai,
                    getResources().getString(R.string.sale_promotion), 0, SALE_PROMOTION));
        }

        if (name.contains(Constant.VSAMenu.DO_WARRANTY) || name.contains(Constant.VSAMenu.DO_WARRANTY_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_bao_hanh,
                    getResources().getString(R.string.txt_warranty),
                    0, Constant.MENU_FUNCTIONS.DO_WARRANTY));
        }

        return arrayListManager;
    }
}
