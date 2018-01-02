package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentInfrastructureManager extends Fragment implements
		OnClickListener {
	private View mView;
	private Button btnHome;
	private GridView lvManager;
	private ArrayList<Manager> arrayListManager;
    private EditText edt_search;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_charge_manager, container,
					false);

			lvManager = (GridView) mView.findViewById(R.id.gridView);
			arrayListManager = new ArrayList<>();
			addManagerList();
			edt_search = (EditText) mView.findViewById(R.id.edtsearch);
			edt_search.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				@Override
				public void afterTextChanged(Editable arg0) {
					onSearch();

				}
			});
		}
		return mView;
	}
	private void onSearch() {
		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					arrayListManager, getActivity());
			lvManager.setAdapter(itemsAdapter);
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
			lvManager.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}
	private void addManagerList() {

		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
//		if (name.contains(";infractrue_info")) {
//			arrayListManager.add(new Manager(R.drawable.qly_ha_tang_03,
//					getResources().getString(R.string.infrastructure_2), 0, 0));
//		}
		if (name.contains(";infractrue_survey_online")) {
			arrayListManager.add(new Manager(R.drawable.ic_khao_sat_ha_tang,
					getResources().getString(R.string.infrastructure_1), 0, 1));
		}
		if (name.contains(";infractrue_business_data")) {

			arrayListManager.add(new Manager(R.drawable.ic_so_lieu_kd_tram,
					getResources().getString(R.string.infrastructure_3), 0, 2));
		}
        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Manager item = (Manager) arg0.getAdapter().getItem(arg2);

				int id = item.getId();
				switch (id) {
				case 0:
					FragmentInfoIDNumberManager idNumberManager = new FragmentInfoIDNumberManager();
					ReplaceFragment.replaceFragment(getActivity(),
							idNumberManager, false);
					break;

				case 1:
					FragmentInfrastructureOnline mInfrastructureOnline = new FragmentInfrastructureOnline();
					ReplaceFragment.replaceFragment(getActivity(),
							mInfrastructureOnline, false);
					break;
				case 2:
					FragmentShowDataInfrastructure mDataInfrastructure = new FragmentShowDataInfrastructure();
					ReplaceFragment.replaceFragment(getActivity(),
							mDataInfrastructure, false);
					break;
				case 3:

					break;

				default:
					break;
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

        MainActivity.getInstance().getSupportActionBar().setTitle(getString(R.string.customer2));
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
//			getActivity().onBackPressed();
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			break;

		default:
			break;
		}

	}

}
