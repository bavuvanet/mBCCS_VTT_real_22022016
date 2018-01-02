package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import com.viettel.bss.viettelpos.v4.report.fragment.FragmentLookupLogMBCCS;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentManageReport extends Fragment implements OnClickListener,
		OnItemClickListener {
	private GridView gridView;
	private ArrayList<Manager> arrayListManager;
    private final String REPORT_KIT = "0";
	private final String REPORT_CHANEL_CARE = "1";
	private final String REPORT_CHANEL_CARE_EMPLOYEE = "2";
	private final String REPORT_VER_CUSTOMER = "3";
	private final String REPORT_PROMOTION = "4";
	private final String REPORT_INVENTORY = "5";
	private final String REPORT_IMAGE_PAYMENT = "6";
	private final String REPORT_STAFF_REVENUE_GERENAL = "7";
	private final String REPORT_STAFF_INCOME = "8";
	// Bao cao giao chi tieu
	private final String REPORT_TARGET = "REPORT_TARGET";

	private final String REPORT_GETINVENE_STAFF = "REPORT_GETINVENE_STAFF";
	private final String REPORT_BONUS_VAS = "REPORT_BONUS_VAS";
    private final String REPORT_EXCEPTION_DAILY = "REPORT_EXCEPTION_DAILY";
	private EditText edt_search;
	@Override
	public void onResume() {
		MainActivity.mLevelMenu = 0;
        MainActivity.getInstance().getSupportActionBar().setTitle(getString(R.string.report));
		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
		// arrayListManager.add(new Manager(R.drawable.sale_channel,
		// getResources().getString(R.string.report_kit), 0, REPORT_KIT));
		arrayListManager.add(new Manager(R.drawable.so_lieu_ha_tang_online_03,
				getResources().getString(R.string.REPORT_CHANEL_CARE), 0,
				REPORT_CHANEL_CARE));
		arrayListManager.add(new Manager(R.drawable.ic_bc_tan_suat_cs_kenh,
				getResources().getString(R.string.reporttskenh), 0,
				REPORT_CHANEL_CARE_EMPLOYEE));
		// if
		// (name.contains(Constant.ReportMenu.PERMISSION_REPORT_ASSIGN_CONTRACT))
		// {
		// arrayListManager.add(new Manager(R.drawable.job_manager_plan,
		// getResources().getString(R.string.ver_report_customer), 0,
		// REPORT_VER_CUSTOMER));
		// }

		if (name.contains(";report_revenue;")) {
			arrayListManager.add(new Manager(R.drawable.ic_bc_hoa_hong,
					getResources().getString(R.string.report_promotion), 0,
					REPORT_PROMOTION));
		}
		if (name.contains(";report_inventory;")) {
			arrayListManager.add(new Manager(R.drawable.ic_bc_ton_kho,
					getResources().getString(R.string.report_inventory), 0,
					REPORT_INVENTORY));
		}
		if (name.contains(";report_hadb;")) {
			arrayListManager.add(new Manager(R.drawable.ic_bc_hinh_anh_diem_ban,
					getResources().getString(R.string.report_image_payment), 0,
					REPORT_IMAGE_PAYMENT));
		}
//		if (name.contains(";report_boc;")) {
//			arrayListManager.add(new Manager(R.drawable.ic_bc_hoan_thanh_chi_tieu,
//					getResources().getString(R.string.repor_staff_income), 0,
//					REPORT_STAFF_INCOME));
//		}
		if (name.contains(";rp_sale_revenue;")) {
			arrayListManager.add(new Manager(R.drawable.ic_bc_dt_bh,
					getResources().getString(
							R.string.repor_staff_revenue_general), 0,
					REPORT_STAFF_REVENUE_GERENAL));
		}

		if (name.contains(";REPORT_TARGET;")) {
			arrayListManager.add(new Manager(R.drawable.ic_bc_giao_chi_tieu,
					getResources().getString(R.string.report_target), 0,
					REPORT_TARGET));
		}
//		if (name.contains(";REPORT_TARGET;")) {
//			arrayListManager.add(new Manager(R.drawable.ic_bc_giao_thue_bao,
//					getResources().getString(R.string.baocaogiaochitieu), 0,
//					REPORT_GETINVENE_STAFF));
//		}
		if (name.contains(";rp_bonus_vas;")) {
			arrayListManager.add(new Manager(R.drawable.ic_tra_cuu_phi_hoa_hong,
					getResources().getString(R.string.report_bonus_vas), 0,
					REPORT_BONUS_VAS));
		}

        if("pmvt_huypq15".equalsIgnoreCase(Session.userName)) {

            arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.lookup_log_mbccs), 0,
                    REPORT_EXCEPTION_DAILY));
        }

        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		gridView.setAdapter(mAdapterManager);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MainActivity.mLevelMenu = 1;
		Manager item = (Manager) arg0.getAdapter().getItem(arg2);

		String menuName = item.getKeyMenuName();
		if (menuName.equals(REPORT_KIT)) {
			FragmentReport synfragment = new FragmentReport();
			ReplaceFragment.replaceFragment(getActivity(), synfragment, true);
		}
		if (menuName.equals(REPORT_CHANEL_CARE)) {
			FragmentReportCarePopup fragmentReportCarePopup = new FragmentReportCarePopup();
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentReportCarePopup, true);
		}
		if (menuName.equals(REPORT_CHANEL_CARE_EMPLOYEE)) {
			FragmentReportForEmployee fragEmployee = new FragmentReportForEmployee();
			ReplaceFragment.replaceFragment(getActivity(), fragEmployee, true);
		}

		if (menuName.equals(REPORT_PROMOTION)) { // bao cao hoa hong
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentReportPromotion(), true);
		}

		if (menuName.equals(REPORT_INVENTORY)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentReportInventory(), true);
		}

		if (menuName.equals(REPORT_IMAGE_PAYMENT)) {
			FragmentReportImagePayment fragmentReportImagePayment = new FragmentReportImagePayment();
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentReportImagePayment, true);
		}
		if (menuName.equals(REPORT_STAFF_INCOME)) {
			FragmentReportStaffIncome fragment = new FragmentReportStaffIncome();
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		}
		if (menuName.equals(REPORT_STAFF_REVENUE_GERENAL)) {
			FragmentReportRevenueGeneral fragment = new FragmentReportRevenueGeneral();
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		}
		if (menuName.equals(REPORT_TARGET)) {
			FragmentReportTarget fragment = new FragmentReportTarget();
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		}
		if (menuName.equals(REPORT_GETINVENE_STAFF)) {
			FragmentReportGetInveneStaff fragment = new FragmentReportGetInveneStaff();
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		}
		if (menuName.equals(REPORT_BONUS_VAS)) {
			FragmentReportBonusVAS fragment = new FragmentReportBonusVAS();
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		}

        if (menuName.equals(REPORT_EXCEPTION_DAILY)) {
            FragmentLookupLogMBCCS fragment = new FragmentLookupLogMBCCS();
            ReplaceFragment.replaceFragment(getActivity(), fragment, true);
        }
		// if (menuName.equals(REPORT_VER_CUSTOMER)) {
		// FragmentReportVerifyCustomer fragmentReportVerifyCustomer = new
		// FragmentReportVerifyCustomer();
		// ReplaceFragment.replaceFragment(getActivity(),
		// fragmentReportVerifyCustomer, true);
		// }
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
