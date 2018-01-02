package com.viettel.bss.viettelpos.v4.omichanel.fragment;

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
import com.viettel.bss.viettelpos.v4.channel.activity.StaffInfoActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetStaffSignatureFile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toancx on 9/25/2017.
 */

public class OmniManagerFragment extends Fragment {

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

    private Staff staff;
    private String staffSigExists;
    private Activity act;

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

            this.staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
            this.act = getActivity();

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

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if(CommonActivity.isNullOrEmpty(keyMenuName)){
                return;
            }

            CommonActivity.addMenuActionToDatabase(getContext(),
                    manager.getNameManager(),
                    keyMenuName, keyMenuName,
                    manager.getResIcon());

            switch (keyMenuName) {
                case Constant.ID_SEARCH_ORDER:
                    if (!CommonActivity.isNullOrEmpty(staff)) {
                        checkSignatureStaff();
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                R.string.data_syn_require, R.string.app_name).show();
                    }
                    break;
                case Constant.ID_CLAIM_ORDER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new SearchOrderToClaimFragment(), true);
                    break;
                default:
                    break;
            }
        }
    };

    private void checkSignatureStaff() {
        if (!CommonActivity.isNullOrEmpty(getSignatureStaff())) {
            this.staffSigExists = "1";
            saveSignatureExists(staffSigExists);
        } else {
            this.staffSigExists = getSignatureExists();
        }
        if (CommonActivity.isNullOrEmpty(this.staffSigExists)) {
            new AsynTaskGetStaffSignatureFile(getActivity(), new OnPostExecuteListener<String>() {
                @Override
                public void onPostExecute(String result, String errorCode, String description) {
                    if("1".equals(result)){
                        staffSigExists = result;
                        saveSignatureExists(result);
                        showSearchOmniScreen();
                    } else {
                        doCreateSigStaff();
                    }
                }
            }, null, AsynTaskGetStaffSignatureFile.TYPE_CHECK_SIG).execute();
        } else {
            showSearchOmniScreen();
        }
    }

    private void showSearchOmniScreen() {
        if ("1".equals(this.staffSigExists)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new SearchOrderOmniFragment(), true);
        }
    }

    private void doCreateSigStaff() {
        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), StaffInfoActivity.class);
                intent.putExtra("type", "order");
                startActivityForResult(intent, 191);
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.staff_sig_not_exits),
                getString(R.string.app_name), "",
                getString(R.string.ok),
                null, okListener).show();
    }

    private String getSignatureStaff() {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SIGNATURE_STAFF_SAVE, "");
    }

    private String getSignatureExists() {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SIGNATURE_STAFF_EXISTS, "");
    }

    private void saveSignatureExists(String existsSig) {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.SIGNATURE_STAFF_EXISTS, existsSig);
        edit.commit();
    }

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.omni_chanel);
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
        GridMenu gridMenu = initOrderMenu();
        if (!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())) {
            lstData.add(gridMenu);
        }
        return lstData;
    }

    private GridMenu initOrderMenu() {
        GridMenu gridMenu = new GridMenu();
        gridMenu.setTitle(getString(R.string.omni_chanel));
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        ArrayList<Manager> arrayListManager = new ArrayList<>();

        //        if (name.contains(";cm.connect_sub_CD;")) {
        arrayListManager.add(new Manager(R.drawable.ic_research_order,
                getResources().getString(R.string.order_search), 0,
                Constant.ID_SEARCH_ORDER));
//        }

//        if (name.contains(";cm.connect_sub_CD;")) {
        arrayListManager.add(new Manager(R.drawable.ic_reclaim_order,
                getResources().getString(R.string.order_receiption_claim), 0,
                Constant.ID_CLAIM_ORDER));
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