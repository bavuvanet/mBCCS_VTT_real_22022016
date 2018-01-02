package com.viettel.bss.viettelpos.v4.video;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReport;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportBonusVAS;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportCarePopup;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportForEmployee;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportGetInveneStaff;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportImagePayment;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportInventory;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportPromotion;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportRevenueGeneral;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportStaffIncome;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportTarget;
import com.viettel.bss.viettelpos.v4.report.fragment.FragmentLookupLogMBCCS;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentManageVideo extends Fragment implements OnClickListener,
		OnItemClickListener {
	private GridView gridView;
	private ArrayList<Manager> arrayListManager;
    private final String VIDEO_1 = "0";
	private final String VIDEO_2 = "1";
	private final String VIDEO_3 = "2";

	private EditText edt_search;
	@Override
	public void onResume() {
		MainActivity.mLevelMenu = 0;
        MainActivity.getInstance().getSupportActionBar().setTitle("Play video");

        ((MainActivity)getActivity()).enableViews(true);
		addManagerList();
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.layout_manage_report, container,
				false);
		unit(mView);
		return mView;

	}

	private void unit(View v) {
		gridView = (GridView) v.findViewById(R.id.grid_report);
		arrayListManager = new ArrayList<>();

		edt_search = (EditText) v.findViewById(R.id.edtsearch);
		edt_search.setVisibility(View.GONE);

	}
	private void onSearch() {
		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					arrayListManager, getActivity());
			gridView.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<Manager> items = new ArrayList<>();
			for (Manager hBeans : arrayListManager) {
				if (hBeans != null
						&& !CommonActivity.isNullOrEmpty(hBeans
								.getNameManager())) {
					if (CommonActivity
							.convertCharacter1(hBeans.getNameManager())
							.toLowerCase()
							.contains(
									CommonActivity
											.convertCharacter1(keySearch))) {
						items.add(hBeans);
					}
				}
			}
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					items, getActivity());
			gridView.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}
	private void addManagerList() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		arrayListManager = new ArrayList<>();
		String name = preferences.getString(Define.KEY_MENU_NAME, "");

		arrayListManager.add(new Manager(R.drawable.so_lieu_ha_tang_online_03,
				"VIDEO 1", 0,
				VIDEO_1));
		arrayListManager.add(new Manager(R.drawable.ic_bc_tan_suat_cs_kenh,
				"VIDEO 2", 0,
				VIDEO_2));
		arrayListManager.add(new Manager(R.drawable.ic_bc_hoa_hong,
					"VIDEO 3", 0,
					VIDEO_3));



        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		gridView.setAdapter(mAdapterManager);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MainActivity.mLevelMenu = 1;
		Manager item = (Manager) arg0.getAdapter().getItem(arg2);

		String menuName = item.getKeyMenuName();
		if (menuName.equals(VIDEO_1)) {
			FragmentPlayvideo synfragment = new FragmentPlayvideo();
			Bundle bundle = new Bundle();
			bundle.putString("videoType","video1");
			synfragment.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(), synfragment, true);
		}
		if (menuName.equals(VIDEO_2)) {
			FragmentPlayvideo fragmentReportCarePopup = new FragmentPlayvideo();
			Bundle bundle = new Bundle();
			bundle.putString("videoType","video2");
			fragmentReportCarePopup.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentReportCarePopup, true);
		}
		if (menuName.equals(VIDEO_3)) {
			FragmentPlayvideo fragEmployee = new FragmentPlayvideo();
			Bundle bundle = new Bundle();
			bundle.putString("videoType","video3");
			fragEmployee.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(), fragEmployee, true);
		}


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.relaBackHome:
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			CacheDataCharge.getInstance().setLisArrayListRe(null);
			CacheDataCharge.getInstance().setLisArrayList(null);
			break;
		default:
			break;
		}

	}
}
