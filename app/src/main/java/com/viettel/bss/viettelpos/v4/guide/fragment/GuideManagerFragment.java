package com.viettel.bss.viettelpos.v4.guide.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel_fromNoti;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCollectCusInfo;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.staff.work.WorkListActivity;
import com.viettel.bss.viettelpos.v4.video.FragmentPlayvideo;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentCriteria;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentListStaffJobAssignment;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignManager;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignStaff;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateArea;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateWork;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocation;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocationMobileSaling;
import com.viettel.bss.viettelpos.v4.work.activity.ManagerRollUpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 7/22/2017.
 */
public class GuideManagerFragment extends Fragment {
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
    private final String VIDEO_1 = "VIDEO_1";
    private final String VIDEO_2 = "VIDEO_2";
    private final String VIDEO_3 = "VIDEO_3";

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
            Bundle bundle;
            switch (keyMenuName) {
                case VIDEO_1:
                    FragmentPlayvideo playvideo1 = new FragmentPlayvideo();
                    bundle = new Bundle();
                    bundle.putString("videoType", "video1");
                    playvideo1.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), playvideo1, true);
                    break;
                case VIDEO_2:
                    FragmentPlayvideo playvideo2 = new FragmentPlayvideo();
                    bundle = new Bundle();
                    bundle.putString("videoType","video2");
                    playvideo2.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(),
                            playvideo2, true);
                case VIDEO_3:
                    FragmentPlayvideo playvideo3 = new FragmentPlayvideo();
                    bundle = new Bundle();
                    bundle.putString("videoType", "video3");
                    playvideo3.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), playvideo3, true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.job);
        MainActivity.getInstance().enableMenuListOrGridView(true);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("JobManagerFragment", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);

    }

    private List<GridMenu> initGridMenuData() {
        lstData = new ArrayList<>();

        GridMenu gridMenu = initGuideConnectFix();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }
        return lstData;
    }

    private GridMenu initGuideConnectFix() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.title_guide_connect_fix));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();

        arrayListManager.add(new Manager(R.drawable.so_lieu_ha_tang_online_03,
                getString(R.string.menu_function_connect_fix1), 0,
                VIDEO_1));
        arrayListManager.add(new Manager(R.drawable.ic_bc_tan_suat_cs_kenh,
                getString(R.string.menu_function_connect_fix2), 0,
                VIDEO_2));
        arrayListManager.add(new Manager(R.drawable.ic_bc_hoa_hong,
                getString(R.string.menu_function_connect_fix3), 0,
                VIDEO_3));

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