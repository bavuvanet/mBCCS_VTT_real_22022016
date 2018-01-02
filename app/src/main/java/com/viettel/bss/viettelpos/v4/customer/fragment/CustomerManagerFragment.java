package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentTransferCustomer;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.PagerAdapter;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerGridMenuAdapter;
import com.viettel.bss.viettelpos.v4.advisory.screen.SearchAdvisoryFragment;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentChangeOffer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentCheckChanelSmartSim;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentChangePromotion;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentConnectionManager;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTechNew;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentModifyProfile;
import com.viettel.bss.viettelpos.v4.connecttionService.asyn.GetListCustomerOrderDetailNokByStaffCodeAsyn;
import com.viettel.bss.viettelpos.v4.connecttionService.strategy.ChangeTechFragmentStrategy;
import com.viettel.bss.viettelpos.v4.contract.fragment.FragmentSearchContract;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentManageBundleGroup;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentSearchComporation;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentPrepairIdNo;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCV;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCustomerMustApprove;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.profile.FragmentLookupProfile;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.BlockOpenSubManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeEquimentFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.RegisterSafeNetFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.TickAppointFragmentStrategy;
import com.viettel.bss.viettelpos.v4.video.FragmentManageVideo;
import com.viettel.bss.viettelpos.v4.video.FragmentPlayvideo;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentHotlineManager;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_POS_TO_PRE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_PRE_TO_POS;

/**
 * Created by Toancx on 2/6/2017.
 */

public class CustomerManagerFragment extends Fragment {
    @BindView(R.id.vpPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private PagerAdapter cusPagerAdapter;
    private View mView;
    private boolean isGridView = true;
    private boolean onCreateViewFirst = true;
    private List<GridMenu> lstDataCus;

    private final String ID_DAUNOI = "ID_DAUNOI";
    private final String ID_DAUNOIMOI = "ID_DAUNOIMOI";
    private final String ID_MOBILE = "ID_MOBILE";
    private final String ID_SMART_SIM = "ID_SMART_SIM";
    private final String ID_HOTLINE = "ID_HOTLINE";
    private final String REGISTER = "REGISTER";
    private final String ID_VAS = "ID_VAS";
    private final String SEARCH_CUSTOMER = "SEARCH_CUSTOMER";
    private final String REPAIR_ID_NO = "REPAIR_ID_NO";
    private final String BUNDLE_GROUP = "BUNDLE_GROUP";
    private final String CHECK_USUALLY_CALL = "CHECK_USUALLY_CALL";
    private final String VIEW_OCS_HLR = "VIEW_OCS_HLR";
    private final String CHANGE_SIM = "CHANGE_SIM";
    private final String MANAGER_CV = "MANAGER_CV";
    private final String CHANGER_OFFER = "CHANGER_OFFER";
    private final String CHANGE_PROMOTION = "CHANGE_PROMOTION";
    private final String CHANGE_TECH = "CHANGE_TECH";
    private final String ID_MODIFY_HOSO = "ID_MODIFY_HOSO";
    private final String CHANGE_EQUIPMENT = "CHANGE_EQUIPMENT";
    private final String LIQUIDATION_RECOVERY = "LIQUIDATION_RECOVERY";
    private final String COMPLAIN_RECEIVE = "COMPLAIN_RECEIVE";

    private final String CHANGE_PROMOTION_BCCS = "CHANGE_PROMOTION_BCCS";
    private final String CHANGE_PRODUCT_BCCS = "CHANGE_PRODUCT_BCCS";
    // Cham soc thue bao roi mang
    private final String CARE_LOST_SUB = "CARE_LOST_SUB";
    //    private RecyclerGridMenuAdapter menuAdapter;
    private final String COLLECT_BUSSINESS = "COLLECT_BUSSINESS";
    private final String TICK_APPOINT = "TICK_APPOINT";
    private final String REGISTER_VAS_SAFENET_BCCS = "REGISTER_VAS_SAFENET_BCCS";
    private final String ADV_CUSTOMERS = "ADV_CUSTOMERS";
    private final String CONTRACT_MANAGER = "CONTRACT_MANAGER";
    private final String CHANGE_TECH_NEW = "CHANGE_TECH_NEW";
    private final String INVALID_INFO_SUBS = "INVALID_INFO_SUBS";


    private final String TRANSFER_CUS = "TRANSFER_CUS";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lstDataCus = initGridMenuData();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstDataCus, onItemClickListener);
                    recyclerView.setAdapter(menuAdapter);
                }
            }, 0);


            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        } else {
            onCreateViewFirst = false;
        }
        return mView;
    }

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.customers);
        MainActivity.getInstance().enableMenuListOrGridView(true);

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CustomerManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

    }

    private List<GridMenu> initGridMenuData() {
        lstDataCus = new ArrayList<>();

        GridMenu gridMenu = initMobileDevMenu();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataCus.add(gridMenu);
        }

        gridMenu = initMobileServiceMenu();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataCus.add(gridMenu);
        }

        gridMenu = initHomeServiceMenu();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataCus.add(gridMenu);
        }

        gridMenu = initContractManagement();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstDataCus.add(gridMenu);
        }
        return lstDataCus;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if (CommonActivity.isNullOrEmpty(keyMenuName)) {
                return;
            }
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            Intent intent;
            Fragment fragment;
            Bundle bundle;
            switch (keyMenuName) {
                case ID_DAUNOI:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentConnectionManager(), true);
                    break;
                case ID_DAUNOIMOI:
//                    GetListCustomerOrderDetailNokByStaffCodeAsyn getListCustomerOrderDetailNokByStaffCodeAsyn = new GetListCustomerOrderDetailNokByStaffCodeAsyn(getActivity(),onPostGetStaffNot,moveLogInAct);
//                    getListCustomerOrderDetailNokByStaffCodeAsyn.execute();
                    ReplaceFragment.replaceFragment(getActivity(),
                            new com.viettel.bss.viettelpos.v3.connecttionService.activity.FragmentConnectionManager(), true);
                    break;
                case ID_MOBILE:
                    intent = new Intent(getActivity(),
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
                case REGISTER:
                    RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
                    ReplaceFragment.replaceFragment(getActivity(),
                            registerInfoFragment, false);
                    break;
                case ID_VAS:
                    intent = new Intent(getActivity(),
                            FragmentRegisterServiceVas.class);
                    startActivity(intent);
                    break;
                case SEARCH_CUSTOMER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchCustomerMustApprove(), true);
                    break;
                case REPAIR_ID_NO:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentPrepairIdNo(), true);
                    break;
                case BUNDLE_GROUP:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentManageBundleGroup(), true);
                    break;
//                LIQUIDATION_RECOVERY
//                case LIQUIDATION_RECOVERY:
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            new FragmentManageBundleGroup(), true);
//                    break;
                case CHECK_USUALLY_CALL:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentCheckUsuallyCall(), true);
                    break;
                case CHANGE_SIM:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchSubChangeSim(), true);
                    break;
                case VIEW_OCS_HLR:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentViewOcsHlr(), true);
                    break;
                case MANAGER_CV:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchCV(), true);
                    break;
                case CHANGER_OFFER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentChangeOffer(), true);
                    break;
                case CHANGE_PROMOTION:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentChangePromotion(), true);
                    break;
                case CHANGE_TECH:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentManagerChangeTech(), true);
                    break;
                case ID_MODIFY_HOSO:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentModifyProfile(), true);
                    break;
//                case LIQUIDATION_RECOVERY:
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            new FragmentModifyProfile(), true);
//                    break;

                case Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL:
                    fragment = new FragmentLookupDebitInfo();

                    bundle = new Bundle();
                    bundle.putString(Constant.FUNCTION,
                            Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL);
                    fragment.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                    break;
                case CHANGE_EQUIPMENT:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeEquimentFragmentStrategy();
                    bundle = new Bundle();
                    bundle.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy);

                    FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
                    fragmentManageSubscriber.setArguments(bundle);
                    fragmentManageSubscriber.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber, false);
                    break;
                case Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentLookupProfile(), true);
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
                case CHANGE_PROMOTION_BCCS:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy1 = new ChangePromotionFragmentStrategy();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy1);
                    FragmentManageSubscriber fragmentManageSubscriber1 = new FragmentManageSubscriber();
                    fragmentManageSubscriber1.setArguments(bundle1);
                    fragmentManageSubscriber1.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber1, false);
                    break;
                case CHANGE_PRODUCT_BCCS:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy2 = new ChangeProductFragmentStrategy();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy2);
                    FragmentManageSubscriber fragmentManageSubscriber2 = new FragmentManageSubscriber();
                    fragmentManageSubscriber2.setArguments(bundle2);
                    fragmentManageSubscriber2.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber2, false);
                    break;
                case CARE_LOST_SUB:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentCareLostSub(), true);
                    break;
                case COLLECT_BUSSINESS:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentSearchComporation(), true);
                    break;

                case TICK_APPOINT:
                    ManageSubscriberFragmentStrategy manageSubscriberFragment = new TickAppointFragmentStrategy();
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("fragmentStrategy",
                            manageSubscriberFragment);
                    FragmentManageSubscriber fragmentManage = new FragmentManageSubscriber();
                    fragmentManage.setArguments(bundle3);
                    fragmentManage.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManage, false);
                    break;
                case REGISTER_VAS_SAFENET_BCCS:
                    ManageSubscriberFragmentStrategy manageS = new RegisterSafeNetFragmentStrategy();
                    Bundle bundle4 = new Bundle();
                    bundle4.putSerializable("fragmentStrategy",
                            manageS);
                    FragmentManageSubscriber fragmentManageSubscriber12 = new FragmentManageSubscriber();
                    fragmentManageSubscriber12.setArguments(bundle4);
                    fragmentManageSubscriber12.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber12, false);
                    break;
                case  Define.PLAY_VIDEO:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentManageVideo(), false);
                    break;
                case ADV_CUSTOMERS:
                    ReplaceFragment.replaceFragment(getActivity(), new SearchAdvisoryFragment(), false);
                    break;
                case Constant.BLOCK_OPEN_SUB_MOBILE:
                    FragmentSearchSubChangeSim fragmentSearchSubMobile = new FragmentSearchSubChangeSim();
                    bundle = new Bundle();
                    bundle.putString("functionKey", Constant.BLOCK_OPEN_SUB_MOBILE);
                    fragmentSearchSubMobile.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubMobile, true);
                    break;
                case Constant.BLOCK_OPEN_SUB_HOMEPHONE:
                    ManageSubscriberFragmentStrategy blockOpenSubHome = new BlockOpenSubManagerFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("fragmentStrategy",
                            blockOpenSubHome);
                    fragmentManage = new FragmentManageSubscriber();
                    fragmentManage.setArguments(bundle);
                    fragmentManage.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManage, false);
                    break;
                case Constant.LIQUIDATION_RECOVERY:
                    ManageSubscriberFragmentStrategy blockOpenSubHomess = new BlockOpenSubManagerFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("fragmentStrategy",
                            blockOpenSubHomess);
                    bundle.putString("funtionTypeKey", Constant.LIQUIDATION_RECOVERY);
                    fragmentManage = new FragmentManageSubscriber();
                    fragmentManage.setArguments(bundle);
                    fragmentManage.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManage, false);
                    break;
                case CONTRACT_MANAGER:
                    FragmentSearchContract fragmentSearchContract = new FragmentSearchContract();
                    ReplaceFragment.replaceFragment(getActivity(), fragmentSearchContract, false);
                    break;
                case Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER:
                    fragment = new FragmentSearchSubChangeSim();
                    bundle = new Bundle();
                    bundle.putString("functionKey", Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER);
                    fragment.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                    break;
                case TRANSFER_CUS:
                    fragment = new FragmentSearchSubChangeSim();
                    bundle = new Bundle();
                    bundle.putString("functionKey", "TRANSFER_CUS");
                    fragment.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                    break;

//                case CHANGE_TECH_NEW:
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            new FragmentManagerChangeTechNew(), true);
//                    break;

                case CHANGE_TECH_NEW:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy13 = new ChangeTechFragmentStrategy();
                    Bundle bundle13 = new Bundle();
                    bundle13.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy13);
                    FragmentManageSubscriber fragmentManageSubscriber13 = new FragmentManageSubscriber();
                    fragmentManageSubscriber13.setArguments(bundle13);
                    fragmentManageSubscriber13.setTargetFragment(CustomerManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber13, false);
                    break;
                case INVALID_INFO_SUBS:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchInvalidInfoSubcriber(), false);
                    break;
                default:
                    break;
            }
        }
    };

    private GridMenu initMobileDevMenu() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.txt_dev_mobile));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";cm.connect_sub_bccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_coday,
                    getResources().getString(
                            R.string.customer_connection_service_static2), 0,
                    ID_DAUNOI));
        }

//         dau noi dich vu co dinh moi
        if (name.contains(";cm.connect_sub_bccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_daunoi_moi,
                    getResources().getString(
                            R.string.customer_connection_service_static3), 0,
                    ID_DAUNOIMOI));
        }
        if (name.contains("menu.connect.mobile2")) {
            arrayListManager.add(new Manager(R.drawable.ic_cungcap_dichvu_didong,
                    getResources().getString(R.string.servicemobile), 0,
                    ID_MOBILE));
        }

        // dau noi dich vu hotline
        if (name.contains(";connect_smartsim;")) {
            arrayListManager.add(new Manager(R.drawable.ic_daunoi_smartsim,
                    getResources().getString(R.string.daunoismartsim), 0,
                    ID_SMART_SIM));
        }

        if (name.contains(";cm.create_req_cd;") || name.contains(";cm.create_req_cd_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_yeucau_hotline,
                    getResources().getString(R.string.yeucauhotline), 0,
                    ID_HOTLINE));
        }

        if (name.contains(";change.pre.to.pos;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_tratruoc_sang_trasau,
                    getResources().getString(R.string.cdttsts), 0,
                    CHANGE_PRE_TO_POS));
        }
        if (name.contains(";change.pos.to.pre;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_trasau_sang_tratruoc,
                    getResources().getString(R.string.cdttstt), 0,
                    CHANGE_POS_TO_PRE));
        }
        if (name.contains(";care_lost_sub;")) {
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
        if (name.contains(";menu.register.vas2;")) {
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.registervassafe), 0,
                    REGISTER_VAS_SAFENET_BCCS));
        }



        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }


    private GridMenu initMobileServiceMenu() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.txt_mobile_service_manager));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains("menu.register.mobile2")) {
            arrayListManager.add(new Manager(R.drawable.ic_dk_thongtin,
                    getResources().getString(R.string.customer_new), 0,
                    REGISTER));
        }

        if (name.contains(";menu.changevas.pre;")) {
            arrayListManager.add(new Manager(R.drawable.ic_daunoi_dichvu_vas,
                    getResources().getString(R.string.connectVas), 0, ID_VAS));
        }

        if (name.contains(";menu_approve;")) {
            arrayListManager.add(new Manager(R.drawable.ic_pheduyet_hoso_doisim,
                    getResources().getString(R.string.approve_change_sim), 0,
                    SEARCH_CUSTOMER));
        }

        if (name.contains(";menu_updateidno;")) {
            arrayListManager.add(new Manager(R.drawable.ic_suasai_cmt,
                    getResources().getString(R.string.repair_id_no), 0,
                    REPAIR_ID_NO));
        }

        if (name.contains(";bundle.group;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_nhombundle,
                    getResources().getString(R.string.manage_bundle_group), 0,
                    BUNDLE_GROUP));
        }

        if (name.contains(";check_usually_call;")) {
            arrayListManager.add(new Manager(R.drawable.ic_ktra_so_thuongxuyen_lienlac,
                    getResources().getString(R.string.check_usually_call), 0,
                    CHECK_USUALLY_CALL));
        }

        if (name.contains(";menu.changesim;")) {
            arrayListManager.add(new Manager(R.drawable.ic_doisim,
                    getResources().getString(R.string.txt_change_sim), 0,
                    CHANGE_SIM));
        }

        if (name.contains(";view_ocs_hlr;")) {
            arrayListManager.add(new Manager(R.drawable.ic_xem_thongtin_ocs,
                    getString(R.string.view_ocs_hlr), 0, VIEW_OCS_HLR));
        }
        if (name.contains(";menu.change.product;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_goicuoc,
                    getResources().getString(R.string.Chuyendoigoicuoctoms), 0,
                    CHANGER_OFFER));
        }

        if (name.contains(";update.tvkh;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                .getString(R.string.advisory_customers), 0, ADV_CUSTOMERS));
        }

        if (name.contains(";menu.block.open.sub.mobile;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.blockOpenSubMobile), 0,
                    Constant.BLOCK_OPEN_SUB_MOBILE));
        }

        if (name.contains(Constant.VSAMenu.EDIT_CUSTOMER)) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.cus_management), 0,
                    Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER));
        }

        if (name.contains(Constant.VSAMenu.TRANSFER_CUS)) {
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.transfercus), 0,
                    TRANSFER_CUS));
        }
        if (name.contains("")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                    .getString(R.string.invalid_info_subs), 0, INVALID_INFO_SUBS));
        }
        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }


    private GridMenu initHomeServiceMenu() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.txt_homephone_service_manager));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";upload_profile;")) {
            arrayListManager.add(new Manager(R.drawable.ic_quanly_hoso_ton,
                    getResources().getString(R.string.manager_cv), 0,
                    MANAGER_CV));
        }


        if (name.contains(";menu.change.promotion;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_khuyenmai,
                    getResources().getString(R.string.chuyendoikm), 0,
                    CHANGE_PROMOTION));
        }

        if (name.contains(";menu.change.technology;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_congnghe,
                    getResources().getString(R.string.changeTechnology), 0,
                    CHANGE_TECH));
        }

        if (name.contains(";cm.modify.hoso;")) {
            arrayListManager.add(new Manager(R.drawable.ic_suasai_hoso,
                    getResources().getString(R.string.menu_sua_sai_hoso), 0,
                    ID_MODIFY_HOSO));
        }



        if (name.contains(Constant.VSAMenu.UPDATE_ISDN_EMAIL)) {
            arrayListManager.add(new Manager(R.drawable.ic_cap_nhat_sdt_email,
                    getResources().getString(R.string.txt_update_isdn_email),
                    0, Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL));
        }

        if (name.contains(Constant.VSAMenu.CHANGE_EQUIPMENT)) {
            arrayListManager.add(new Manager(R.drawable.ic_doi_thietbi,
                    getResources().getString(R.string.changeEquipment), 0,
                    CHANGE_EQUIPMENT));
        }

        if (name.contains(Constant.VSAMenu.LOOKUP_PROFILE_TRANSACTION)) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_hoso,
                    getResources().getString(R.string.title_lookup_profile_transaction),
                    0, Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION));
        }
        if (name.contains(";menu.change.product.bccs;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_goicuoc,
                    getResources().getString(R.string.Chuyendoigoicuoc), 0,
                    CHANGE_PRODUCT_BCCS));
        }
        if (name.contains(";menu.change.promotion.bccs;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_khuyenmai,
                    getResources().getString(R.string.changepromotionew), 0,
                    CHANGE_PROMOTION_BCCS));
        }
        if (name.contains(";menu.block.liquid.sub;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.liquidation_and_recovery), 0,
                    LIQUIDATION_RECOVERY));
        }
        if (name.contains(";m.tick.appoint;") || name.contains(";m.add.quota;")) {
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.tick_appoint), 0,
                    TICK_APPOINT));

        }

        if (name.contains(";menu.block.open.sub.homephone;")) {
        arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                getResources().getString(R.string.blockOpenSubHomephone), 0,
                Constant.BLOCK_OPEN_SUB_HOMEPHONE));
        }

        if (name.contains(";menu.change.technology.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_congnghe,
                    getResources().getString(R.string.changeTechnologyNew), 0,
                    CHANGE_TECH_NEW));
        }


        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }



    private GridMenu initContractManagement(){
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.quanlihopdong));

        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";menu.update.contract;")) {
            arrayListManager
                    .add(new Manager(R.drawable.ic_icon_contract_manager, getResources()
                            .getString(R.string.quanlihopdong), 0,
                            CONTRACT_MANAGER, ""));
        }

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


            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_luoi);
            FragmentManager manager = getActivity().getSupportFragmentManager();

            if (cusPagerAdapter == null) {
                cusPagerAdapter = new PagerAdapter(manager, getContext(), lstDataCus);
                if (CommonActivity.isNullOrEmptyArray(lstDataCus) || lstDataCus.size() == 1) {
                    tabLayout.setVisibility(View.GONE);
                }

                //set Adapter to view pager
                viewPager.setAdapter(cusPagerAdapter);

                //set tablayout with viewpager
                tabLayout.setupWithViewPager(viewPager);

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                //Setting tabs from adpater
                tabLayout.setTabsFromPagerAdapter(cusPagerAdapter);
            }

            viewFlipper.showNext();
        } else {

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
            viewFlipper.showPrevious();
        }
        isGridView = !isGridView;
    }


}
