package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeManager;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.manage.ManagerCustomerFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfrastructureManager;
import com.viettel.bss.viettelpos.v4.login.adapter.AdapterManager;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentManageReport;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentListOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.FragmentSynthrozation;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentJobManager;

import java.util.ArrayList;

public class FragmentMenuHome extends Fragment
		implements
			OnItemClickListener,
			OnClickListener,
			Define {
	private ListView lvManager;
	private ArrayList<Manager> arrayListManager;
    private Button btnHome;

	// private Bundle mBundle;

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

		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, getActivity().MODE_PRIVATE);
		String menuNameAll = preferences
				.getString(Define.KEY_MENU_NAME, "null");
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.sales), 0, Define.MENU_SALE_MANAGEMENT));
		arrayListManager
				.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
						.getString(R.string.customers), 0,
						Define.MENU_CHANNEL_CUSTOMER));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.chargeable), 0,
				Define.MENU_CHANNEL_CHARGEABLE));
		arrayListManager
				.add(new Manager(R.drawable.ic_icon_macdinh, getResources().getString(
						R.string.channel), 0, Define.MENU_CHANNEL_MANAGEMENT));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.customer2), 0,
				Define.MENU_CHANNEL_INFRASTRUCTURE));
		arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh, getResources()
				.getString(R.string.job), 0, Define.MENU_CHANNEL_JOB));
		arrayListManager.add(new Manager(R.drawable.ic_quan_ly_he_thong, getResources()
				.getString(R.string.system), 0, Define.MENU_CHANNEL_SYSTEM));
		arrayListManager.add(new Manager(R.drawable.ic_quan_ly_he_thong, getResources()
				.getString(R.string.approveOrder), 0,
				Define.MENU_CHANNEL_APPROVEORDER));
		arrayListManager.add(new Manager(R.drawable.ic_quan_ly_he_thong, getResources()
				.getString(R.string.report), 0, Define.MENU_CHANNEL_REPORT));
        AdapterManager mAdapterManager = new AdapterManager(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
        switch (menuName) {
            case MENU_SALE_MANAGEMENT:
                FragmentSaleManager fragmentSaleManager = new FragmentSaleManager();
                // fragmentSaleManager.setArguments(mBundle);
                ReplaceFragment.replaceFragment(getActivity(), fragmentSaleManager,
                        true);
                break;
            case MENU_CHANNEL_CUSTOMER:
                // Quan ly khach hang
                ManagerCustomerFragment mListMenuManager = new ManagerCustomerFragment();
                ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                        false);

                break;
            case MENU_CHANNEL_MANAGEMENT:
                // FragmentListChannel mListMenuManager = new FragmentListChannel();
                // ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                // true);
                break;
            case MENU_CHANNEL_CHARGEABLE:
                FragmentChargeManager fragmentChargeManager = new FragmentChargeManager();
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentChargeManager, true);
                break;
            case MENU_CHANNEL_INFRASTRUCTURE:
                FragmentInfrastructureManager infrastructureManager = new FragmentInfrastructureManager();
                ReplaceFragment.replaceFragment(getActivity(),
                        infrastructureManager, true);
                break;
            case MENU_CHANNEL_JOB:
                FragmentJobManager fragmentJobManager = new FragmentJobManager();
                ReplaceFragment.replaceFragment(getActivity(), fragmentJobManager,
                        true);

                break;
            case MENU_CHANNEL_SYSTEM: {
                FragmentSynthrozation synfragment = new FragmentSynthrozation();
                ReplaceFragment.replaceFragment(getActivity(), synfragment, true);
                break;
            }
            case MENU_CHANNEL_APPROVEORDER:
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                ReplaceFragment.replaceFragment(getActivity(), fragmentListOrder,
                        false);

                // //==== phe duyet don hang --- TH--------------
                break;
            case MENU_CHANNEL_REPORT: {
                FragmentManageReport synfragment = new FragmentManageReport();
                ReplaceFragment.replaceFragment(getActivity(), synfragment, true);
                break;
            }
        }

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.relaBackHome :
				getActivity().onBackPressed();
				break;

			default :
				break;
		}

	}

}
