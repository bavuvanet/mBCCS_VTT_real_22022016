package com.viettel.bss.viettelpos.v4.infrastrucure.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfoIDNumberManager;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfrastructureOnline;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.SeachNodeOnlineFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 2/7/2017.
 */

public class InfrastructureManagerFragment extends Fragment {
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
    private boolean onCreateViewFirst = true;
    private List<GridMenu> lstData;
    private final String ID_KS_HT_ONLINE = "ID_KS_HT_ONLINE";
    private final String ID_SL_KDT = "ID_SL_KDT";


    private final String ID_SEARCH = "ID_SEARCH";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);

            lstData = initGridMenuData();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            salePagerAdapter = new PagerAdapter(manager, getContext(), lstData);
            if(CommonActivity.isNullOrEmptyArray(lstData) || lstData.size() == 1){
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

            RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(getActivity(), lstData, onItemClickListener);
            recyclerView.setAdapter(menuAdapter);

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        } else {
            onCreateViewFirst = false;
        }
        return mView;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if(CommonActivity.isNullOrEmpty(keyMenuName)){
                return;
            }
            CommonActivity.addMenuActionToDatabase(getContext(), manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());
            switch (keyMenuName) {
                case ID_KS_HT_ONLINE:
                    FragmentInfoIDNumberManager idNumberManager = new FragmentInfoIDNumberManager();
                    ReplaceFragment.replaceFragment(getActivity(),
                            idNumberManager, false);
                    break;
                case ID_SL_KDT:
                    FragmentInfrastructureOnline mInfrastructureOnline = new FragmentInfrastructureOnline();
                    ReplaceFragment.replaceFragment(getActivity(),
                            mInfrastructureOnline, false);
                    break;
                case ID_SEARCH:
                    SeachNodeOnlineFragment seachNodeOnlineFragment = new SeachNodeOnlineFragment();
                    ReplaceFragment.replaceFragment(getActivity(),
                            seachNodeOnlineFragment, false);
                    break;
//                case 2:
//                    FragmentShowDataInfrastructure mDataInfrastructure = new FragmentShowDataInfrastructure();
//                    ReplaceFragment.replaceFragment(getActivity(),
//                            mDataInfrastructure, false);
//                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.customer2);
        MainActivity.getInstance().enableMenuListOrGridView(true);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("SaleManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

    }

    private List<GridMenu> initGridMenuData() {
        lstData = new ArrayList<>();

        GridMenu gridMenu = initMenuInfras();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }
        return lstData;
    }

    private GridMenu initMenuInfras() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.customer2));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();
        if (name.contains(";infractrue_survey_online") || name.contains(";infractrue_survey_online_mbccs2")) {
            arrayListManager.add(new Manager(R.drawable.ic_khao_sat_ha_tang,
                    getResources().getString(R.string.infrastructure_1), 0, ID_KS_HT_ONLINE));
        }

        if (name.contains(";infractrue_survey_online_mbccs2;")) {
            arrayListManager.add(new Manager(R.drawable.ic_khao_sat_ha_tang,
                    getResources().getString(R.string.searchNode), 0, ID_SEARCH));
        }
//        if (name.contains(";infractrue_business_data")) {
//            arrayListManager.add(new Manager(R.drawable.ic_so_lieu_kd_tram,
//                    getResources().getString(R.string.infrastructure_3), 0, ID_SL_KDT));
//        }
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
            viewFlipper.showNext();
        } else {
            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
            viewFlipper.showPrevious();
        }
        isGridView = !isGridView;
    }
}
