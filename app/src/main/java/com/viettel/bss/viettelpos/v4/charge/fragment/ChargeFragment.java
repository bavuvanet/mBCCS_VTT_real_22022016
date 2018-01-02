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
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDel;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNew;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNotify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeRe;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractNotPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReport;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractSearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSendSmsCtv;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSuportUpdate;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdateComplain;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdateCustype;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdatePromotion;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentLookupDebitInfo;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 2/4/2017.
 */

public class ChargeFragment extends FragmentCommon{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListViewMenuAdapter menuAdapter;
    private final String ID_NEW = "ID_NEW";
    private final String ID_RE = "ID_RE";
    private final String ID_DEL = "ID_DEL";
    private final String ID_CONTRACT_PAYMENT = "ID_CONTRACT_PAYMENT";
    private final String ID_CONTRACT_NOT_PAYMENT = "ID_CONTRACT_NOT_PAYMENT";
    private final String ID_CONTRACT_REPORT = "ID_CONTRACT_REPORT";
    private final String ID_UPDATE_PROMOTION = "ID_UPDATE_PROMOTION";
    private final String ID_CHARGE_NOTIFY = "ID_CHARGE_NOTIFY";
    private final String ID_SUPPORT_UPDATE = "ID_SUPPORT_UPDATE";
    private final String ID_UPDATE_CUS = "ID_UPDATE_CUS";
    private final String ID_UPDATE_COMPLAIN = "ID_UPDATE_COMPLAIN";
    private final String ID_CONTRACT_SEARCH = "ID_CONTRACT_SEARCH";
    private final String ID_SMS_CTV = "ID_SMS_CTV";
    private final String ID_CHARGE_NOTIFY_SEARCH = "ID_CHARGE_NOTIFY_SEARCH";
    public static ChargeFragment newInstance() {
        return new ChargeFragment();
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
        if (name.contains(";pm.assign.new.contract;") || name.contains(";pm.assign.new.contract.mbccs2;")) {
            arrayListManager
                    .add(new Manager(R.drawable.giaomoiicon, getResources().getString(R.string.charge_new), 0, ID_NEW));
        }

        if (name.contains(";pm.assign_old_contract;") || name.contains(";pm.assign_old_contract_mbccs2;")) {
            arrayListManager
                    .add(new Manager(R.drawable.giaolaiicon, getResources().getString(R.string.charge_re), 0, ID_RE));
        }

        if (name.contains(";pm_payment;") || name.contains(";pm_payment_mbccs2;")) {

            arrayListManager
                    .add(new Manager(R.drawable.ic_gachno, getResources().getString(R.string.charge_del), 0, ID_DEL));
        }

        if (name.contains(";cus_need_payment;") || name.contains(";cus_need_payment_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_khachhang_can_thucuoc, getResources().getString(R.string.contract_payment),
                    0, ID_CONTRACT_PAYMENT));
        }

        if (name.contains(";contract_not_payment;") || name.contains(";contract_not_payment_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_khachhang_ton_chua_thucuoc,
                    getResources().getString(R.string.contract_not_payment), 0, ID_CONTRACT_NOT_PAYMENT));
        }

        if (name.contains(";contract_report;") || name.contains(";contract_report_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tyle_thucuoc, getResources().getString(R.string.contract_report),
                    0, ID_CONTRACT_REPORT));
        }

        if (name.contains(";update_subscriber;") || name.contains(";update_subscriber_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_khuyenmai,
                    getResources().getString(R.string.title_menu_update_promotion), 0, ID_UPDATE_PROMOTION));
        }

        if (name.contains(";charge_notify;") || name.contains(";charge_notify_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_thongbao_cuoc, getResources().getString(R.string.charge_notify), 0,
                    ID_CHARGE_NOTIFY));
        }
        arrayListManager.add(new Manager(R.drawable.ic_thongbao_cuoc, getString(R.string.search_charge_notify), 0,
                ID_CHARGE_NOTIFY_SEARCH));
        if (name.contains(";support_update;") || name.contains(";support_update_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_quanly_hotro, getResources().getString(R.string.support_update),
                    0, ID_SUPPORT_UPDATE));
        }

        if (name.contains(";update_custype;") || name.contains(";update_custype_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_doituong_khachhang, getResources().getString(R.string.updateCustype), 0,
                    ID_UPDATE_CUS));
        }

        if (name.contains(";update_complain;") || name.contains(";update_complain_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_capnhat_khieunai_khachhang, getResources().getString(R.string.updateComplain),
                    0, ID_UPDATE_COMPLAIN));
        }

        if (name.contains(";contract_search;") || name.contains(";contract_search_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_hopdong, getResources().getString(R.string.contract_search),
                    0, ID_CONTRACT_SEARCH));
        }

        if (name.contains(";contract_send_sms;") || name.contains(";contract_send_sms_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_nhantin_cho_ctv,
                    getResources().getString(R.string.sendSms), 0, ID_SMS_CTV));
        }
        if (name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO) || name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_nocuoc,
                    getResources().getString(R.string.txt_lookup_debit_info),
                    0, Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO));
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
                    case ID_NEW:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNew(), true);
                        break;
                    case ID_RE:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeRe(), true);
                        break;
                    case ID_DEL:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDel(), true);
                        break;
                    case ID_CONTRACT_PAYMENT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractPayment(), true);
                        break;
                    case ID_CONTRACT_NOT_PAYMENT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractNotPayment(), true);
                        break;
                    case ID_CONTRACT_REPORT:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractReport(), true);
                        break;
                    case ID_UPDATE_PROMOTION:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdatePromotion(), true);
                        break;
                    case ID_CHARGE_NOTIFY:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNotify(), true);
                        break;
                    case ID_SUPPORT_UPDATE:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentSuportUpdate(), true);
                        break;
                    case ID_UPDATE_COMPLAIN:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateComplain(), true);
                        break;
                    case ID_CONTRACT_SEARCH:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentContractSearch(), true);
                        break;
                    case ID_SMS_CTV:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentSendSmsCtv(), true);
                        break;
                    case ID_UPDATE_CUS:
                        ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateCustype(), true);
                        break;
                    case Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO:
                        FragmentLookupDebitInfo fragment = new FragmentLookupDebitInfo();

                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.FUNCTION,
                                Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
                        fragment.setArguments(bundle);

                        ReplaceFragment.replaceFragment(getActivity(), fragment, true);
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
