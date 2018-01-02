package com.viettel.bss.viettelpos.v4.cc.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.PagerAdapter;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerGridMenuAdapter;
import com.viettel.bss.viettelpos.v4.advisory.screen.MainAdvisoryFragment;
import com.viettel.bss.viettelpos.v4.advisory.screen.SearchAdvisoryFragment;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.ComplainFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.object.GridMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCCManager extends Fragment {
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

    private final String UPDATE_GIFT = "UPDATE_GIFT";
    private final String RECEIVE_COMPLAIN_CUSTOMNER = "RECEIVE_COMPLAIN_CUSTOMNER";
    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.cc_mamagement);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_menu, container, false);
            ButterKnife.bind(this, mView);

            lstData = initGridMenuData();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            salePagerAdapter = new PagerAdapter(manager, getContext(), lstData);
            if (CommonActivity.isNullOrEmptyArray(lstData) || lstData.size() == 1) {
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

            RecyclerGridMenuAdapter menuAdapter = new RecyclerGridMenuAdapter(
                    getActivity(), lstData, onItemClickListener);
            recyclerView.setAdapter(menuAdapter);

            MainActivity.getInstance().setIconListOrGridView(R.drawable.ic_list);
        } else {
            onCreateViewFirst = false;
        }
        return mView;
    }

    com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener = new com.viettel.bss.viettelpos.v4.listener.OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {

            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if (CommonActivity.isNullOrEmpty(keyMenuName)) {
                return;
            }

            CommonActivity.addMenuActionToDatabase(getContext(),
                    manager.getNameManager(), keyMenuName, keyMenuName, manager.getResIcon());

            switch (keyMenuName) {
                case UPDATE_GIFT:
                    ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateGift(), false);
                    break;
                case RECEIVE_COMPLAIN_CUSTOMNER:
                    ReplaceFragment.replaceFragment(getActivity(), new ComplainFragment(), false);
                    break;
                default:
                    break;
            }
        }
    };

    private List<GridMenu> initGridMenuData() {
        lstData = new ArrayList<>();

        GridMenu gridMenu = initMenuCC();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }
        return lstData;
    }

    private GridMenu initMenuCC() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.cc_mamagement));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();

        if (name.contains(";update.gift;")) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                    .getString(R.string.update_gift), 0, UPDATE_GIFT));
        }

        if(name.contains(Constant.VSAMenu.COMPLAIN_RECEIVER)) {
            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
                    .getString(R.string.txt_create_complain), 0, RECEIVE_COMPLAIN_CUSTOMNER));
        }

        gridMenu.setLstData(arrayListManager);
        return gridMenu;
    }

}
