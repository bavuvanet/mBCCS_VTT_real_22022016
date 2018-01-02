package com.viettel.bss.viettelpos.v4.guide.fragment;

import android.app.Activity;
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
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.video.FragmentPlayvideo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 1/12/2017.
 */

public class GuideConnectFragment extends FragmentCommon {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ListViewMenuAdapter menuAdapter;

    private final String VIDEO_1 = "VIDEO_1";
    private final String VIDEO_2 = "VIDEO_2";
    private final String VIDEO_3 = "VIDEO_3";

    public static GuideConnectFragment newInstance() {
        return new GuideConnectFragment();
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
                    bundle.putString("videoType","video3");
                    playvideo3.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), playvideo3, true);
                default:
                    break;
            }
        }
    };

    @Override
    public void setPermission() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("InventoryFragment", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Manager> getManagerList() {
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

        return arrayListManager;
    }
}
