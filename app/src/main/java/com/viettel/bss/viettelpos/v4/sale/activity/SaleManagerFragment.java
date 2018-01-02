package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.PagerAdapter;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerGridMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentWarranty;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.sale.business.GetStockOrderManager;
import com.viettel.bss.viettelpos.v4.sale.confirm.debt.activity.ConfirmDebitFragment;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrder;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.APPROVE_MONEY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.APPROVE_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CHANGE_PASS_ANYPAY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CHANNEL_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CONFIRM_NOTE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CREATE_INVOICE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.RECHARGE_TO_BANK;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.RETURN_THE_GOOD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_2G_TO_3G;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ACTIVE_ACCOUNT_PAYMENT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ANYPAY_EXCHANGE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ANYPAY_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_BHLD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_BY_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_CREATE_CHANEL;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_DEPOSIT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_INFO_SEARCH;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_PROMOTION;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_RETAIL;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SALING;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_KIT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_SERIAL_CARD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_TO_CUSTOMER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SEARCH_ANYPAY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.STAFF_HANDLE_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.VIEW_INFO_ORDER_ITEM;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.VIEW_STOCK;

/**
 * Created by Toancx on 1/12/2017.
 */

public class SaleManagerFragment extends Fragment {
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private PagerAdapter salePagerAdapter;
    private View mView;
    private boolean isGridView = true;
    private List<GridMenu> lstDataSale;
    private RecyclerGridMenuAdapter menuAdapter;

    //Xac nhan cong no
    private static final String CONRIRM_DEBIT = "CONRIRM_DEBIT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            long start = System.currentTimeMillis();
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (CommonActivity.isNullOrEmptyArray(lstDataSale)) {
                        lstDataSale = initGridMenuData();
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstDataSale, onItemClickListener);
                    recyclerView.setAdapter(menuAdapter);
                }
            }, 0);


            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        }
        return mView;
    }

    OnItemClickListener onItemLoaderListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            lstDataSale = initGridMenuData();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            salePagerAdapter = new PagerAdapter(manager, getContext(), lstDataSale);
            if (CommonActivity.isNullOrEmptyArray(lstDataSale) || lstDataSale.size() == 1) {
                tabLayout.setVisibility(View.GONE);
            }

            //set Adapter to view pager
            viewPager.setAdapter(salePagerAdapter);

            //set tablayout with viewpager
            tabLayout.setupWithViewPager(viewPager);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            //Setting tabs from adpater
            tabLayout.setTabsFromPagerAdapter(salePagerAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstDataSale, onItemClickListener);
            recyclerView.setAdapter(menuAdapter);

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        }
    };

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.sales);
        MainActivity.getInstance().enableMenuListOrGridView(true);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("SaleManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

    }

    public List<GridMenu> initGridMenuData() {
        lstDataSale = new ArrayList<>();

        GridMenu gridMenu = initMenuSale();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataSale.add(gridMenu);
        }

        gridMenu = initMenuInventory();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataSale.add(gridMenu);
        }

        gridMenu = initMenuUtils();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataSale.add(gridMenu);
        }
        return lstDataSale;
    }

    private final OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if (CommonActivity.isNullOrEmpty(keyMenuName)) {
                return;
            }
            CommonActivity.addMenuActionToDatabase(getActivity(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            switch (keyMenuName) {
                case Constant.MENU_FUNCTIONS.SALE_SALING: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
                    FragmentSaleSaling fragment = new FragmentSaleSaling();
                    fragment.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case SALE_DEPOSIT: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_DEPOSIT);
                    FragmentSaleSaling fragment = new FragmentSaleSaling();
                    fragment.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case SALE_BHLD: {
                    FragmentChooseBHLD fragment = new FragmentChooseBHLD();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case SALE_RETAIL: {
                    Bundle b = new Bundle();
                    b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_RETAIL);
                    FragmentSaleSaling fragment = new FragmentSaleSaling();
                    fragment.setArguments(b);
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case SALE_PROMOTION: {
                    Bundle b = new Bundle();
                    b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_PROMOTION);
                    FragmentSaleSaling fragment = new FragmentSaleSaling();
                    fragment.setArguments(b);
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case CONFIRM_NOTE: {
                    FragmentConfirmNote fragment = new FragmentConfirmNote();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
                case CREATE_INVOICE: {
                    FragmentCreateInvoice fragment = new FragmentCreateInvoice();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                    break;
                }
                case SALE_BY_ORDER: {
                    FragmentSaleByOrder fragment = new FragmentSaleByOrder();
                    ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                    break;
                }
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
                case RETURN_THE_GOOD:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleSalingReturn(), false);
                    break;
                case SALE_TO_CUSTOMER:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleToCustomers(), false);
                    break;
                case SALE_SEARCH_ISDN:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchISDN(), false);
                    break;
                case SALE_2G_TO_3G:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChange2GTo3G(), false);
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
                case SALE_ANYPAY_ISDN:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayISDN(), false);
                    break;
                case SALE_ANYPAY_EXCHANGE:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayExchange(), false);
                    break;
                case VIEW_STOCK:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChooseStaff(), false);
                    break;
                case CHANGE_PASS_ANYPAY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChangePassAnypay(), false);
                    break;
                case CHANNEL_ORDER:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrder(), false);
                    break;
                case SEARCH_ANYPAY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchAnypay(), false);
                    break;
                case STAFF_HANDLE_ORDER:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrderManager(), false);
                    break;
                case Constant.MENU_FUNCTIONS.DO_WARRANTY:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentWarranty(), false);
                    break;
                case CONRIRM_DEBIT:
                    ReplaceFragment.replaceFragment(getActivity(), new ConfirmDebitFragment(), false);
                    break;
            }
        }
    };

    private static GridMenu initMenuSale() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(MainActivity.getInstance().getResources().getString(R.string.sale));

        SharedPreferences preferences = MainActivity.getInstance() .getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(Constant.VSAMenu.SALE_SALING) || name.contains(Constant.VSAMenu.SALE_SALING_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_dut, MainActivity.getInstance().getResources().getString(R.string.sale_saling), 0,
                    SALE_SALING));
        }


        if (name.contains(Constant.VSAMenu.SALE_DEPOSIT) || name.contains(Constant.VSAMenu.SALE_DEPOSIT_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_dat_coc, MainActivity.getInstance().getResources().getString(R.string.sale_deposit),
                    0, SALE_DEPOSIT));
        }
        if (name.contains(Constant.VSAMenu.SALE_PROGRAM) || name.contains(Constant.VSAMenu.SALE_PROGRAM_MBCCS2)) {
            arrayListManager
                    .add(new Manager(R.drawable.ic_ban_cho_ct_bhld, MainActivity.getInstance().getResources().getString(R.string.sale_bhld), 0, SALE_BHLD));
        }
        if (name.contains(Constant.VSAMenu.SALE_RETAIL) || name.contains(Constant.VSAMenu.SALE_RETAIL_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_hang, MainActivity.getInstance().getResources().getString(R.string.sale_retail), 0,
                    SALE_RETAIL));
        }

        if (name.contains(Constant.VSAMenu.SALE_BY_ORDER) || name.contains(Constant.VSAMenu.SALE_BY_ORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_hang_theo_don, MainActivity.getInstance().getResources().getString(R.string.sale_by_order),
                    0, SALE_BY_ORDER));
        }

        if (name.contains(";outlet_sale_mbccs2;") || name.contains(";outlet_sale;")) {
            arrayListManager.add(new Manager(R.drawable.ic_phe_duyet_don_hang,
                    MainActivity.getInstance().getResources().getString(R.string.sale_for_customer), 0, SALE_TO_CUSTOMER));
        }

        if (name.contains(";sale_anypay_isdn_mbccs2;") || name.contains(";sale_anypay_isdn;")) {
            arrayListManager.add(new Manager(R.drawable.ic_anypay,
                    MainActivity.getInstance().getResources().getString(R.string.sale_anypay_isdn), 0, SALE_ANYPAY_ISDN));
        }

        if (name.contains(";sale_anypay_exchange_mbccs2;") || name.contains(";sale_anypay_exchange;")) {
            arrayListManager.add(new Manager(R.drawable.ic_anypay,
                    MainActivity.getInstance().getResources().getString(R.string.sale_anypay_exchange), 0, SALE_ANYPAY_EXCHANGE));
        }

        if (name.contains(";channel.order.mbccs2;") || name.contains(";channel.order;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_diem_ban_dat_hang,
                    MainActivity.getInstance().getResources().getString(R.string.channel_order), 0, CHANNEL_ORDER));
        }
        // Nhan vien xu ly don hang
        if (name.contains(";staff_manage_order_mbccs2;") || name.contains(";staff_manage_order;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xu_ly_don_hang,
                    MainActivity.getInstance().getResources().getString(R.string.xy_ly_don_hang_diem_ban), 0, STAFF_HANDLE_ORDER));
        }
        if (name.contains(";sale_promotion_mbccs2;") || name.contains(";sale_promotion;")) {
            arrayListManager.add(new Manager(R.drawable.ic_ban_khuyen_mai,
                    MainActivity.getInstance().getResources().getString(R.string.sale_promotion), 0, SALE_PROMOTION));
        }

        if (name.contains(Constant.VSAMenu.DO_WARRANTY) || name.contains(Constant.VSAMenu.DO_WARRANTY_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_bao_hanh,
                    MainActivity.getInstance().getResources().getString(R.string.txt_warranty),
                    0, Constant.MENU_FUNCTIONS.DO_WARRANTY));
        }

        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

    private static GridMenu initMenuInventory() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(MainActivity.getInstance().getString(R.string.stock));

        SharedPreferences preferences = MainActivity.getInstance().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(Constant.VSAMenu.SALE_CONFIRM_NOTE) || name.contains(Constant.VSAMenu.SALE_CONFIRM_NOTE_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_xac_nhan_nhap_hang,
                    MainActivity.getInstance().getResources().getString(R.string.confirmNote), 0, CONFIRM_NOTE));
        }

        if (name.contains(Constant.VSAMenu.SALE_ORDER) || name.contains(Constant.VSAMenu.SALE_ORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_dat_hang_cap_tren,
                    MainActivity.getInstance().getResources().getString(R.string.viewinfoandorder), 0, VIEW_INFO_ORDER_ITEM));
        }

        if (name.contains(Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER) || name.contains(Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_phe_duyet_don_hang,
                    MainActivity.getInstance().getResources().getString(R.string.approveOrder), 0, APPROVE_ORDER));
        }

        if (name.contains(Constant.VSAMenu.MENU_RETURN_GOOD) || name.contains(Constant.VSAMenu.MENU_RETURN_GOOD_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_hang,
                    MainActivity.getInstance().getResources().getString(R.string.menu_item_sale_return_the_good), 0, RETURN_THE_GOOD));
        }

        if (name.contains(";2Gto3G.mbccs2;") || name.contains(";2Gto3G;")) {
            arrayListManager.add(new Manager(R.drawable.ic_2g_3g,
                    MainActivity.getInstance().getResources().getString(R.string.change2Gto3G), 0, SALE_2G_TO_3G));
        }

        gridMenu.setLstData(arrayListManager);

        return gridMenu;
    }

    private static GridMenu initMenuUtils() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(MainActivity.getInstance().getString(R.string.txt_tien_ich));

        SharedPreferences preferences = MainActivity.getInstance().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();

        if (name.contains(Constant.VSAMenu.SALE_CREATE_INVOICE) || name.contains(Constant.VSAMenu.SALE_CREATE_INVOICE_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_lap_hoa_don,
                    MainActivity.getInstance().getResources().getString(R.string.createInvoice), 0, CREATE_INVOICE));
        }

        if (name.contains(Constant.VSAMenu.MENU_CREATE_CHANNEL) || name.contains(Constant.VSAMenu.MENU_CREATE_CHANNEL_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_cap_nhat_ma_trang, MainActivity.getInstance().getResources().getString(R.string.add_news_chanel),
                    0, SALE_CREATE_CHANEL));
        }

        if (name.contains(Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT) || name.contains(Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_kh_tk_thanh_toan,
                    MainActivity.getInstance().getResources().getString(R.string.add_active_account_payment), 0, SALE_ACTIVE_ACCOUNT_PAYMENT));
        }

        if (name.contains(Constant.VSAMenu.MENU_REVIEW_PAYMENT_STAFF) || name.contains(Constant.VSAMenu.MENU_REVIEW_PAYMENT_STAFF_MBCCS2)) {

            arrayListManager.add(new Manager(R.drawable.ic_duyet_phieu_nop_tien,
                    MainActivity.getInstance().getResources().getString(R.string.add_info_search), 0, SALE_INFO_SEARCH));

        }

        if (name.contains(";search_isdn_mbccs2;") || name.contains(";search_isdn;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_isdn,
                    MainActivity.getInstance().getResources().getString(R.string.sale_for_search_isdn), 0, SALE_SEARCH_ISDN));
        }

        if (name.contains(";rechargeToBank_mbccs2;") || name.contains(";rechargeToBank;")) {
            arrayListManager.add(new Manager(R.drawable.ic_bh_nap_tien_vao_tk,
                    MainActivity.getInstance().getResources().getString(R.string.rechargetobank), 0, RECHARGE_TO_BANK));
        }

        if (name.contains(";approveBplus_mbccs2;") || name.contains(";approveBplus;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tich_duyet_nop_tien,
                    MainActivity.getInstance().getResources().getString(R.string.tichduyetnoptien), 0, APPROVE_MONEY));
        }

//        if (name.contains(";sale_search_serial_card_mbccs2;")) {
//            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich,
//                    MainActivity.getInstance().getResources().getString(R.string.sale_search_serial_card), 0, SALE_SEARCH_SERIAL_CARD));
//        }
//
//        if (name.contains(";sale_search_kit_mbccs2;")) {
//            arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich,
//                    MainActivity.getInstance().getResources().getString(R.string.sale_search_kit), 0, SALE_SEARCH_KIT));
//        }

        if (name.contains(";change.pass.anypay.mbccs2;") || name.contains(";change.pass.anypay;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    MainActivity.getInstance().getResources().getString(R.string.change_pass_anypay), 0, CHANGE_PASS_ANYPAY));
        }

        if (name.contains(";view_stock_mbccs2;") || name.contains(";view_stock;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xem_kho, MainActivity.getInstance().getResources().getString(R.string.view_stock),
                    0, VIEW_STOCK));
        }

//        if (name.contains(";confirm.debit;")) {
        arrayListManager.add(new Manager(R.drawable.ic_xem_kho, MainActivity.getInstance().getResources().getString(R.string.confirm_debit),
                0, CONRIRM_DEBIT));
//        }
        arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_tien_ich, MainActivity.getInstance().getResources().getString(R.string.tctienich), 0,
                SEARCH_ANYPAY));
        gridMenu.setLstData(arrayListManager);

        return gridMenu;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOptionMenuSelect(BaseMsg msg) {
        Log.d("TAG", "onOptionMenuSelect");
        if (isGridView) {
            if (salePagerAdapter == null) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                salePagerAdapter = new PagerAdapter(manager, getContext(), lstDataSale);
                if (CommonActivity.isNullOrEmptyArray(lstDataSale) || lstDataSale.size() == 1) {
                    tabLayout.setVisibility(View.GONE);
                }

                //set Adapter to view pager
                viewPager.setAdapter(salePagerAdapter);

                //set tablayout with viewpager
                tabLayout.setupWithViewPager(viewPager);

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                //Setting tabs from adpater
                tabLayout.setTabsFromPagerAdapter(salePagerAdapter);
            }


            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_luoi);
            viewFlipper.showNext();
        } else {
            if (menuAdapter == null) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstDataSale, onItemClickListener);
                recyclerView.setAdapter(menuAdapter);
            }

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
            viewFlipper.showPrevious();
        }
        isGridView = !isGridView;
    }
}
