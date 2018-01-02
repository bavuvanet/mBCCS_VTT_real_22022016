package com.viettel.bss.viettelpos.v4.login.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentChanelFunction;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfrastructureManager;
import com.viettel.bss.viettelpos.v4.login.adapter.AdapterManager;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

public class FragmentChannelManagerNotSynchronization extends Fragment
		implements OnItemClickListener, OnClickListener {
	private ListView lvManager;
	private ArrayList<Manager> arrayListManager;
    private Button btnHome;
	private Bundle mBundle;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentChannelManager");
        MainActivity.getInstance().setTitleActionBar(R.string.menu_main);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentChannelManager");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentChannelManager");
		View mView = inflater.inflate(R.layout.layout_main_menu_home,
				container, false);
		unit(mView);
		addChanelManagerList();
		return mView;
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentChannelManager");

		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void unit(View v) {
		lvManager = (ListView) v.findViewById(R.id.lvChannelManager);
		arrayListManager = new ArrayList<>();

	}

	private void addChanelManagerList() {
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.sales), 0, "0"));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.customers), 0, "1"));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.chargeable), 0, "2"));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.channel), 0, "3"));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.customer2), 0, "4"));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.job), 0, "5"));
		arrayListManager.add(new Manager(R.drawable.ic_quan_ly_he_thong, getResources()
				.getString(R.string.system), 0, "6"));
        AdapterManager mAdapterManager = new AdapterManager(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		String menuName = arrayListManager.get(position).getKeyMenuName();
		Log.d("menuName ", "menuName : " + menuName);
        switch (menuName) {
            case "0":

                break;
            case "1":


                break;
            case "2":

                break;
            case "3":
                FragmentChanelFunction mListMenuManager = new FragmentChanelFunction();
                mListMenuManager.setArguments(mBundle);
                ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                        false);
                break;
            case "4":
                FragmentInfrastructureManager infrastructureManager = new FragmentInfrastructureManager();
                ReplaceFragment.replaceFragment(getActivity(),
                        infrastructureManager, false);
                break;
            case "5":

                break;
            case "6":

                break;
        }

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		// case R.id.btnHome:
		// for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
		// manager.popBackStack();
		// }
		// FragmentLoginNotData mFragmentLoginNotData = new
		// FragmentLoginNotData();
		// transaction.replace(R.id.fragment_main, mFragmentLoginNotData);
		// transaction.commit();
		// break;

		default:
			break;
		}

	}

	// public interface OnBackListener {
	// void onKeyBackListener();
	// }
	// public

}
