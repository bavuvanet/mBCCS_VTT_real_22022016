package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentChargeManager extends Fragment implements OnItemClickListener, OnClickListener {
	private GridView lvManager;
	private ArrayList<Manager> arrayListManager;

    // Ban dut cho kenh db, ctv
	// private final String CHARGE_NEW = "0";
	// // Xuat dat coc
	// private final String CHARGE_RE = "1";
	// //
	// private final String CHARGE_DEL = "2";
	// private final String CHARGE_TKTC = "3";
	// private final String CONFIRM_NOTE = "4";
	private final int ID_NEW = 0;
	private final int ID_RE = 1;
	private final int ID_DEL = 2;
	private final int ID_DEL_CTV = 3;

	private final int ID_CONTRACT_PAYMENT = 4;
	private final int ID_CONTRACT_NOT_PAYMENT = 5;
	private final int ID_CONTRACT_REPORT = 6;
	private final int ID_VERIFY_UPDATE = 7;
	private final int ID_VERIFY_SEARCH = 8;
	private final int ID_UPDATE_PROMOTION = 9;
	private final int ID_CHARGE_NOTIFY = 10;
	private final int ID_SUPPORT_UPDATE = 11;
	private final int ID_UPDATE_CUS = 12;
	private final int ID_UPDATE_COMPLAIN = 13;

	private final int ID_CONTRACT_SEARCH = 14;

	private final int ID_CONTRACT_BANKPLUS = 15;
	private final int ID_UPDATE_DELAY = 16;
	private final int ID_OPEN_SUBSCRIBER = 17;
	private final int ID_CONTRACT_ASSIGN = 18;
	private final int ID_CONTRACT_REPORT_MANAGER = 19;
	private final int ID_CONTRACT_PAYMENT_MANAGER = 20;
	private final int ID_CONTRACT_REPORT_VERIFY = 21;
	private final int ID_CONTRACT_NOT_PAYMENT_MANAGER = 22;
	private final int ID_CONTRACT_DELAY = 23;
	private final int ID_SMS_CTV = 24;

	private EditText edt_search;

	@Override
	public void onResume() {
		MainActivity.mLevelMenu = 0;
		addManagerList();
        MainActivity.getInstance().getSupportActionBar().setTitle(getString(R.string.chargeable));
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.layout_charge_manager, container, false);

		unit(mView);
		return mView;

	}

	private void unit(View v) {
		lvManager = (GridView) v.findViewById(R.id.gridView);
		arrayListManager = new ArrayList<>();


		edt_search = (EditText) v.findViewById(R.id.edtsearch);
		edt_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				onSearch();
			}
		});

	}

	private void onSearch() {
		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(arrayListManager, getActivity());
			lvManager.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<Manager> items = new ArrayList<>();
			for (Manager hBeans : arrayListManager) {
				if (hBeans != null && !CommonActivity.isNullOrEmpty(hBeans.getNameManager())) {
					if (CommonActivity.convertCharacter1(hBeans.getNameManager()).toLowerCase()
							.contains(CommonActivity.convertCharacter1(keySearch))) {
						items.add(hBeans);
					}
				}
			}
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(items, getActivity());
			lvManager.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}

	// int iItem = 0;
	private void addManagerList() {

		SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
		arrayListManager = new ArrayList<>();
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if (name.contains(";pm.assign.new.contract;")) {
			arrayListManager
					.add(new Manager(R.drawable.giaomoiicon, getResources().getString(R.string.charge_new), 0, ID_NEW));
		}
		if (name.contains(";pm.assign_old_contract;")) {
			arrayListManager
					.add(new Manager(R.drawable.giaolaiicon, getResources().getString(R.string.charge_re), 0, ID_RE));
		}
		if (name.contains(";pm_payment;")) {

			arrayListManager
					.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.charge_del), 0, ID_DEL));
		}

		// CTV Gach no
		if (name.contains(";pm_payment_ctv;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.charge_del_ctv),
					0, ID_DEL_CTV));
		}

		// K/H can thu cuoc
		if (name.contains(";cus_need_payment;")) {
			arrayListManager.add(new Manager(R.drawable.bao_cao_07, getResources().getString(R.string.contract_payment),
					0, ID_CONTRACT_PAYMENT));
		}

		// Ton chua thu cuoc
		if (name.contains(";contract_not_payment;")) {
			arrayListManager.add(new Manager(R.drawable.bao_cao_07,
					getResources().getString(R.string.contract_not_payment), 0, ID_CONTRACT_NOT_PAYMENT));
		}

		if (name.contains(";contract_report;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.contract_report),
					0, ID_CONTRACT_REPORT));
		}

		if (name.contains(";verify_update;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.contract_verify_update), 0, ID_VERIFY_UPDATE));
		}

		if (name.contains(";verify_search;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.contract_verify_search), 0, ID_VERIFY_SEARCH));
		}

		if (name.contains(";update_subscriber;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.title_menu_update_promotion), 0, ID_UPDATE_PROMOTION));
		}

		if (name.contains(";charge_notify;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.charge_notify), 0,
					ID_CHARGE_NOTIFY));
		}

		if (name.contains(";support_update;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.support_update),
					0, ID_SUPPORT_UPDATE));
		}

		if (name.contains(";update_custype;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.updateCustype), 0,
					ID_UPDATE_CUS));
		}

		if (name.contains(";update_complain;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.updateComplain),
					0, ID_UPDATE_COMPLAIN));
		}

		if (name.contains(";contract_search;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon, getResources().getString(R.string.contract_search),
					0, ID_CONTRACT_SEARCH));
		}

		if (name.contains(";contract_bankplus;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.charge_del_bankplus), 0, ID_CONTRACT_BANKPLUS));
		}

		if (name.contains(";contract_report_verify;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.contract_report_verify), 0,
					ID_CONTRACT_REPORT_VERIFY));
		}

		if (name.contains(";contract_delay;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.contract_delay), 0,
					ID_CONTRACT_DELAY));
		}
		if (name.contains(";contract_send_sms;")) {
			arrayListManager.add(new Manager(R.drawable.gachnoicon,
					getResources().getString(R.string.sendSms), 0, ID_SMS_CTV));
		}

        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// String menuName = arrayListManager.get(arg2).getKeyMenuName();
		// toast(menuName);
		MainActivity.mLevelMenu = 1;

		Manager item = (Manager) arg0.getAdapter().getItem(arg2);

		int id = item.getId();
		switch (id) {
		case ID_NEW:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNew(), true);
			break;

		case ID_RE:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeRe(), true);
			break;
		case ID_DEL:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDel(), true);
			break;
		case ID_DEL_CTV:
			Intent intent = new Intent(getActivity(),FragmentChargeDelCTV.class);
//			intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
			startActivity(intent);
            break;

		case ID_CONTRACT_PAYMENT:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractPayment(), true);
			break;

		case ID_CONTRACT_NOT_PAYMENT:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractNotPayment(), true);
			break;

		case ID_CONTRACT_REPORT:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractReport(), true);
			break;

		case ID_VERIFY_UPDATE:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractVerifyUpdate(), true);
			break;

		case ID_VERIFY_SEARCH:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractVerifySearch(), true);
			break;

		case ID_UPDATE_PROMOTION:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdatePromotion(), true);
			break;

		case ID_CHARGE_NOTIFY:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeNotify(), true);
			break;

		case ID_SUPPORT_UPDATE:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentSuportUpdate(), true);
			break;
		case ID_UPDATE_CUS:

			ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateCustype(), true);
			break;

		case ID_UPDATE_COMPLAIN:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateComplain(), true);
			break;

		case ID_CONTRACT_SEARCH:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractSearch(), true);
			break;

		case ID_CONTRACT_BANKPLUS:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentChargeDelBankPlus(), true);
			break;

		case ID_CONTRACT_REPORT_VERIFY:
			ReplaceFragment.replaceFragment(getActivity(), new FragmentContractReportVerify(), true);
			break;
		case ID_CONTRACT_DELAY:
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentContractDelay(), true);
			break;
		case ID_SMS_CTV:
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentSendSmsCtv(), true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.relaBackHome:
			// getActivity().onBackPressed();
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			CacheDataCharge.getInstance().setLisArrayListRe(null);
			CacheDataCharge.getInstance().setLisArrayList(null);
			break;
		default:
			break;
		}
	}

}
