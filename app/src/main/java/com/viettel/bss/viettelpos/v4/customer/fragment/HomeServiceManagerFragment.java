package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentChangeOffer;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentChangePromotion;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentModifyProfile;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCV;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.profile.FragmentLookupProfile;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.BlockOpenSubManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeEquimentFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.TickAppointFragmentStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 2/6/2017.
 */

public class HomeServiceManagerFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String MANAGER_CV = "MANAGER_CV";
    private final String CHANGER_OFFER = "CHANGER_OFFER";
    private final String CHANGE_PROMOTION = "CHANGE_PROMOTION";
    private final String CHANGE_TECH = "CHANGE_TECH";
    private final String ID_MODIFY_HOSO = "ID_MODIFY_HOSO";
    private final String CHANGE_EQUIPMENT = "CHANGE_EQUIPMENT";
    private final String CHANGE_PROMOTION_BCCS = "CHANGE_PROMOTION_BCCS";
    private final String TICK_APPOINT = "TICK_APPOINT";
    private final String CHANGE_PRODUCT_BCCS = "CHANGE_PRODUCT_BCCS";
    private final String LIQUIDATION_RECOVERY = "LIQUIDATION_RECOVERY";
    private View mView;
    public static HomeServiceManagerFragment newInstance() {
        return new HomeServiceManagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_recycler_view, container, false);
            ButterKnife.bind(this, mView);

            menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(menuAdapter);
        }
        return mView;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            Fragment fragment;
            Bundle bundle;

            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());
            switch (keyMenuName){
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
                case Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO:
                    fragment = new FragmentLookupDebitInfo();

                    bundle = new Bundle();
                    bundle.putString(Constant.FUNCTION,
                            Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
                    fragment.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), fragment, true);
                    break;
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
                    fragmentManageSubscriber.setTargetFragment(HomeServiceManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber, false);
                    break;
                case Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new FragmentLookupProfile(), true);
                    break;
                case CHANGE_PROMOTION_BCCS:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy1 = new ChangePromotionFragmentStrategy();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy1);
                    FragmentManageSubscriber fragmentManageSubscriber1 = new FragmentManageSubscriber();
                    fragmentManageSubscriber1.setArguments(bundle1);
                    fragmentManageSubscriber1.setTargetFragment(HomeServiceManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber1, false);
                    break;
                case TICK_APPOINT:
                    ManageSubscriberFragmentStrategy manageSubscriberFragment = new TickAppointFragmentStrategy();
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("fragmentStrategy",
                            manageSubscriberFragment);
                    FragmentManageSubscriber fragmentManage = new FragmentManageSubscriber();
                    fragmentManage.setArguments(bundle3);
                    fragmentManage.setTargetFragment(HomeServiceManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManage, false);
                    break;
                case CHANGE_PRODUCT_BCCS:
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy2 = new ChangeProductFragmentStrategy();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy2);
                    FragmentManageSubscriber fragmentManageSubscriber2 = new FragmentManageSubscriber();
                    fragmentManageSubscriber2.setArguments(bundle2);
                    fragmentManageSubscriber2.setTargetFragment(HomeServiceManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageSubscriber2, false);
                    break;
                case Constant.BLOCK_OPEN_SUB_HOMEPHONE:
                    ManageSubscriberFragmentStrategy blockOpenSubHome = new BlockOpenSubManagerFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("fragmentStrategy",
                            blockOpenSubHome);
                    FragmentManageSubscriber fragmentManageHome = new FragmentManageSubscriber();
                    fragmentManageHome.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManageHome, false);
                    break;
                case LIQUIDATION_RECOVERY:
                    ManageSubscriberFragmentStrategy blockOpenSubHomess = new BlockOpenSubManagerFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("fragmentStrategy",
                            blockOpenSubHomess);
                    bundle.putString("funtionTypeKey", Constant.LIQUIDATION_RECOVERY);
                    fragmentManage = new FragmentManageSubscriber();
                    fragmentManage.setArguments(bundle);
                    fragmentManage.setTargetFragment(HomeServiceManagerFragment.this, 100);
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentManage, false);
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

        if (name.contains(";upload_profile;") || name.contains(";upload_profile_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_quanly_hoso_ton,
                    getResources().getString(R.string.manager_cv), 0,
                    MANAGER_CV));
        }

        if (name.contains(";menu.change.product;") || name.contains(";menu.change.product.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_goicuoc,
                    getResources().getString(R.string.Chuyendoigoicuoc), 0,
                    CHANGER_OFFER));
        }


        if (name.contains(";menu.change.promotion;") || name.contains(";menu.change.promotion.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_khuyenmai,
                    getResources().getString(R.string.chuyendoikm), 0,
                    CHANGE_PROMOTION));
        }

        if (name.contains(";menu.change.technology;") || name.contains(";menu.change.technology.mbccs2;") ) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_congnghe,
                    getResources().getString(R.string.changeTechnology), 0,
                    CHANGE_TECH));
        }

        if (name.contains(";cm.modify.hoso;") || name.contains(";cm.modify.hoso.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_suasai_hoso,
                    getResources().getString(R.string.menu_sua_sai_hoso), 0,
                    ID_MODIFY_HOSO));
        }

        if (name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO) || name.contains(Constant.VSAMenu.LOOKUP_DEBIT_INFO_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_nocuoc,
                    getResources().getString(R.string.txt_lookup_debit_info),
                    0, Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO));
        }

        if (name.contains(Constant.VSAMenu.UPDATE_ISDN_EMAIL) || name.contains(Constant.VSAMenu.UPDATE_ISDN_EMAIL_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_cap_nhat_sdt_email,
                    getResources().getString(R.string.txt_update_isdn_email),
                    0, Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL));
        }

        if (name.contains(CHANGE_EQUIPMENT)) {
            arrayListManager.add(new Manager(R.drawable.ic_doi_thietbi,
                    getResources().getString(R.string.changeEquipment), 0,
                    CHANGE_EQUIPMENT));
        }

        if (name.contains(Constant.VSAMenu.LOOKUP_PROFILE_TRANSACTION) || name.contains(Constant.VSAMenu.LOOKUP_PROFILE_TRANSACTION_MBCCS2)) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_hoso,
                    getResources().getString(R.string.title_lookup_profile_transaction),
                    0, Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION));
        }

        if (name.contains(";menu.change.product.bccs;") || name.contains(";menu.change.product.bccs.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_goicuoc,
                    getResources().getString(R.string.Chuyendoigoicuoc), 0,
                    CHANGE_PRODUCT_BCCS));
        }

        if (name.contains(";menu.change.promotion.bccs;") || name.contains(";menu.change.promotion.bccs.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_chuyendoi_khuyenmai,
                    getResources().getString(R.string.changepromotionew), 0,
                    CHANGE_PROMOTION_BCCS));
        }
        if (name.contains(";m.tick.appoint;") || name.contains(";m.add.quota;")) {
            arrayListManager.add(new Manager(R.drawable.customers,
                    getResources().getString(R.string.tick_appoint), 0,
                    TICK_APPOINT));

        }
        if (name.contains(";menu.block.open.sub.homephone;") || name.contains(";menu.block.open.sub.homephone.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.blockOpenSubHomephone), 0,
                    Constant.BLOCK_OPEN_SUB_HOMEPHONE));
        }
        if (name.contains(";menu.block.liquid.sub;") || name.contains(";menu.block.liquid.sub.mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.liquidation_and_recovery), 0,
                    LIQUIDATION_RECOVERY));
        }
        return arrayListManager;
    }
}
