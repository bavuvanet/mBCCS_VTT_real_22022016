package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentCheckChanelSmartSim;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentConnectionManager;
import com.viettel.bss.viettelpos.v4.connecttionService.asyn.GetListCustomerOrderDetailNokByStaffCodeAsyn;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentSearchComporation;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.RegisterSafeNetFragmentStrategy;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentHotlineManager;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import java.util.ArrayList;

import butterknife.BindView;

import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_POS_TO_PRE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_PRE_TO_POS;

/**
 * Created by Toancx on 2/6/2017.
 */

public class MobileDevFragment extends FragmentCommon{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String ID_DAUNOI = "ID_DAUNOI";
    private final String ID_DAUNOIMOI = "ID_DAUNOIMOI";
    private final String ID_MOBILE = "ID_MOBILE";
    private final String ID_SMART_SIM = "ID_SMART_SIM";
    private final String ID_HOTLINE = "ID_HOTLINE";
    private final String CARE_LOST_SUB = "CARE_LOST_SUB";
    private final String COLLECT_BUSSINESS = "COLLECT_BUSSINESS";
    private final String REGISTER_VAS_SAFENET_BCCS = "REGISTER_VAS_SAFENET_BCCS";
    public static MobileDevFragment newInstance() {
        return new MobileDevFragment();
    }

    @Override
    protected void unit(View v) {
        menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_recycler_view;
    }

    @Override
    protected void setPermission() {

    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            Bundle bundle;
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            switch (keyMenuName){
                case ID_DAUNOI:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentConnectionManager(), true);
                    break;
                case ID_DAUNOIMOI:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new com.viettel.bss.viettelpos.v3.connecttionService.activity.FragmentConnectionManager(), true);
                    break;
                case ID_MOBILE:
                    Intent intent = new Intent(getActivity(),
                            ActivityCreateNewRequestMobileNew.class);
                    startActivity(intent);
                    break;
                case ID_SMART_SIM:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentCheckChanelSmartSim(), true);
                    break;
                case ID_HOTLINE:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentHotlineManager(), true);
                    break;
                case CHANGE_PRE_TO_POS:
                    FragmentSearchSubChangeSim fragmentSearchSubPos = new FragmentSearchSubChangeSim();
                    bundle = new Bundle();
                    bundle.putString("functionKey", Constant.CHANGE_PRE_TO_POS);
                    fragmentSearchSubPos.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubPos, true);
                    break;

                case CHANGE_POS_TO_PRE:
                    FragmentSearchSubChangeSim fragmentSearchSubPre = new FragmentSearchSubChangeSim();
                    bundle = new Bundle();
                    bundle.putString("functionKey", Constant.CHANGE_POS_TO_PRE);
                    fragmentSearchSubPre.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubPre, true);
                    break;
                case CARE_LOST_SUB:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentCareLostSub(), true);
                    break;
                case COLLECT_BUSSINESS:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchComporation(), true);
                    break;
                case REGISTER_VAS_SAFENET_BCCS:
                    ManageSubscriberFragmentStrategy manageS= new RegisterSafeNetFragmentStrategy();
                    Bundle bundle4 = new Bundle();
                    bundle4.putSerializable("fragmentStrategy",
                            manageS);
                    FragmentManageSubscriber fragmentManageSubscriber12 = new FragmentManageSubscriber();
                    fragmentManageSubscriber12.setArguments(bundle4);
                    fragmentManageSubscriber12.setTargetFragment(MobileDevFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber12, false);
                    break;
                default:
                    break;
            }
        }
    };

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";cm.connect_sub_CD;") || name.contains(";cm.connect_sub_CD_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_coday,
                    getResources().getString(
                            R.string.customer_connection_service_static2), 0,
                    ID_DAUNOI));
        }

        // dau noi dich vu co dinh moi
        if (name.contains(";cm.connect_sub_bccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_daunoi_moi,
                    getResources().getString(
                            R.string.customer_connection_service_static3), 0,
                    ID_DAUNOIMOI));
        }

        if (name.contains(";menu.connect.mobile2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_didong,
                    getResources().getString(R.string.servicemobile), 0,
                    ID_MOBILE));
        }

        // dau noi dich vu hotline
        if (name.contains(";connect_smartsim;") || name.contains(";connect_smartsim_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_daunoi_smartsim,
                    getResources().getString(R.string.daunoismartsim), 0,
                    ID_SMART_SIM));
        }

        if (name.contains(";cm.create_req_cd;") || name.contains(";cm.create_req_cd_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_yeucau_hotline,
                    getResources().getString(R.string.yeucauhotline), 0,
                    ID_HOTLINE));
        }
        if (name.contains(";change.pre.to.pos;") || name.contains(";change.pre.to.pos.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_tratruoc_sang_trasau,
                    getResources().getString(R.string.cdttsts), 0,
                    CHANGE_PRE_TO_POS));
        }
        if (name.contains(";change.pos.to.pre;") || name.contains(";change.pos.to.pre.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_trasau_sang_tratruoc,
                    getResources().getString(R.string.cdttstt), 0,
                    CHANGE_POS_TO_PRE));
        }

        if (name.contains(";care_lost_sub;") || name.contains(";care_lost_sub_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(
                            R.string.cham_soc_thue_bao_roi_mang), 0,
                    CARE_LOST_SUB));
        }
        if (name.contains(";menu.update.corporationcus;")
                || name.contains(";menu.approve.corporationcus;")
                || name.contains(";menu.delete.corporationcus;")) {
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.thuthapttinkhdn), 0,
                    COLLECT_BUSSINESS));

        }
        if(name.contains(";menu.register.vas2;") || name.contains(";menu.register.vas2.mbccs2;")){
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.registervassafe), 0,
                    REGISTER_VAS_SAFENET_BCCS));
        }

        return arrayListManager;
    }

}
