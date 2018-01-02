package com.viettel.bss.viettelpos.v4.customer.manage;

import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.RegisterSafeNetFragmentStrategy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentChangeOffer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentCheckChanelSmartSim;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentChangePromotion;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentConnectionManager;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentModifyProfile;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentManageBundleGroup;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentSearchComporation;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCareLostSub;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCheckUsuallyCall;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCollectCusInfo;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentLookupDebitInfo;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentViewOcsHlr;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentWarranty;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.profile.FragmentLookupProfile;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeEquimentFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.TickAppointFragmentStrategy;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentHotlineManager;

import java.util.ArrayList;

public class ManagerCustomerFragment extends Fragment implements
		OnClickListener, OnItemClickListener {
	public static final String CHANGE_PREPAID_TO_POSTPAID = "CHANGE_PREPAID_TO_POSTPAID";
	public static final String CHANGE_POSTPAID_TO_PREPAID = "CHANGE_POSTPAID_TO_PREPAID";
	private View mView;
	private GridView grid;
	private ArrayList<Manager> arrayListManager;
    private final String REGISTER = "REGISTER";
	private final String ID_DAUNOI = "ID_DAUNOI";
	private final String ID_DAUNOIMOI = "ID_DAUNOIMOI";
	private final String ID_MOBILE = "ID_MOBILE";
	private final String ID_VAS = "ID_VAS";

	private final String ID_SMART_SIM = "ID_SMART_SIM";
	private final String SEARCH_CUSTOMER = "SEARCH_CUSTOMER";
	private final String REPAIR_ID_NO = "REPAIR_ID_NO";
	private final String MANAGER_CV = "MANAGER_CV";

	private final String CHANGER_OFFER = "CHANGER_OFFER";
	private final String CHANGE_PROMOTION = "CHANGE_PROMOTION";

	private final String CHANGE_TECH = "CHANGE_TECH";
	private final String BUNDLE_GROUP = "BUNDLE_GROUP";

	private final String ID_HOTLINE = "ID_HOTLINE";
	private final String ID_MODIFY_HOSO = "ID_MODIFY_HOSO";
	// Cham soc thue bao roi mang
	private final String CARE_LOST_SUB = "CARE_LOST_SUB";

	// Tra cuu no cuoc
	private final String LOOKUP_DEBIT_INFO = "LOOKUP_DEBIT_INFO";

	// thu thap thong tin khach hang
	private final String COLLECT_CUS_INFO = "COLLECT_CUS_INFO";

	// Tra cuu no cuoc
	private final String CHECK_USUALLY_CALL = "CHECK_USUALLY_CALL";
	private final String VIEW_OCS_HLR = "VIEW_OCS_HLR";

	private final String CHANGE_SIM = "CHANGE_SIM";
	public static final String BLOCK_OPEN_SUB = "BLOCK_OPEN_SUB";
	// Doi thiet bi
	private final String CHANGE_EQUIPMENT = "CHANGE_EQUIPMENT";
	private final String CHANGE_PRE_TO_POS = "CHANGE_PRE_TO_POS";
	private final String CHANGE_POS_TO_PRE = "CHANGE_POS_TO_PRE";
	// private Button btnHome;
	private final String COLLECT_BUSSINESS = "COLLECT_BUSSINESS";
	// Gia han thue bao
	private final String TICK_APPOINT = "TICK_APPOINT";
	private final String CHANGE_DEPOSIT_LIMIT = "CHANGE_DEPOSIT_LIMIT";
	private EditText edt_search;
	// doi khuyen mai tich co dinh tich hop BCCS 2
	private final String CHANGE_PROMOTION_BCCS = "CHANGE_PROMOTION_BCCS";
	private final String CHANGE_PRODUCT_BCCS = "CHANGE_PRODUCT_BCCS";
	private final String REGISTER_VAS_SAFENET_BCCS = "REGISTER_VAS_SAFENET_BCCS";
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		addManagerList();
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_manager, container,
					false);

		}

		grid = (GridView) mView.findViewById(R.id.gridView);

		arrayListManager = new ArrayList<>();

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

		return mView;
	}

	private void onSearch() {
		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(
					arrayListManager, getActivity());
			grid.setAdapter(itemsAdapter);
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
									CommonActivity.convertCharacter1(keySearch))) {
						items.add(hBeans);
					}
				}
			}
			GridMenuAdapter itemsAdapter = new GridMenuAdapter(items,
					getActivity());
			grid.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			getActivity().finish();
		}
		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

        MainActivity.getInstance().setTitleActionBar(R.string.customers);
		super.onActivityCreated(savedInstanceState);
	}

	private void addManagerList() {
		arrayListManager = new ArrayList<>();
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");









		if (name.contains(";care_lost_sub;")) {
			arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(
							R.string.cham_soc_thue_bao_roi_mang), 0,
					CARE_LOST_SUB));
		}


		// Tra cuu so thuong xuyen lien lac





		if (name.contains(Constant.VSAMenu.DO_WARRANTY)) {
			arrayListManager.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.txt_warranty),
					0, Constant.MENU_FUNCTIONS.DO_WARRANTY));
		}






//		arrayListManager.add(new Manager(R.drawable.customers,
//				getResources().getString(R.string.cdttsts), 0,
//				CHANGE_PRE_TO_POS));

		if (name.contains(";menu.update.corporationcus;")
				|| name.contains(";menu.approve.corporationcus;")
				|| name.contains(";menu.delete.corporationcus;")) {
			arrayListManager.add(new Manager(R.drawable.customers,
					getResources().getString(R.string.thuthapttinkhdn), 0,
					COLLECT_BUSSINESS));

		}
		if (name.contains(";m.tick.appoint;") || name.contains(";m.add.quota;")) {
			arrayListManager.add(new Manager(R.drawable.customers,
					getResources().getString(R.string.tick_appoint), 0,
					TICK_APPOINT));

		}
		// if (name.contains(";m.change.deposit.limit;") ||
		if (name.contains(";menu.change.product.bccs;")) {
			arrayListManager.add(new Manager(R.drawable.customers,
					getResources().getString(R.string.Chuyendoigoicuoc), 0,
					CHANGE_PRODUCT_BCCS));
		}
		if (name.contains(";menu.change.promotion.bccs;")) {
			arrayListManager.add(new Manager(R.drawable.customers,
					getResources().getString(R.string.changepromotionew), 0,
					CHANGE_PROMOTION_BCCS));
		}
		if(name.contains(";menu.register.vas2;")){
			arrayListManager.add(new Manager(R.drawable.customers,
					getResources().getString(R.string.registervassafe), 0,
					REGISTER_VAS_SAFENET_BCCS));
		}

			
		
        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		grid.setAdapter(mAdapterManager);
		grid.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
					true);
			break;
		case R.id.relaBackHome:
			// getActivity().onBackPressed();
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			break;
		case R.id.lnRegisterInfo:
			// Dang ky info
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		String menuName = arrayListManager.get(arg2).getKeyMenuName();
		MainActivity.mLevelMenu = 1;
		Manager item = (Manager) arg0.getAdapter().getItem(arg2);

		String menuName = item.getKeyMenuName();
		if (menuName.equals(REGISTER)) {
			RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
			ReplaceFragment.replaceFragment(getActivity(),
					registerInfoFragment, false);
			return;
		}
		if (menuName.equals(ID_DAUNOI)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentConnectionManager(), true);
			return;
		}
		if (menuName.equals(ID_DAUNOIMOI)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentConnectionManager(), true);
		}
		if (menuName.equals(ID_VAS)) {
			Intent intent = new Intent(getActivity(),
					FragmentRegisterServiceVas.class);
			startActivity(intent);
			return;
		}
		if (menuName.equals(ID_MOBILE)) {
			Intent intent = new Intent(getActivity(),
					ActivityCreateNewRequestMobileNew.class);
			startActivity(intent);
			return;
		}

		if (menuName.equals(ID_SMART_SIM)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentCheckChanelSmartSim(), true);
			return;
		}
		if (menuName.equals(SEARCH_CUSTOMER)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentSearchCustomerMustApprove(), true);
			return;
		}
		if (menuName.equals(REPAIR_ID_NO)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentPrepairIdNo(), true);
			return;
		}

		if (menuName.equals(MANAGER_CV)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentSearchCV(), true);
			return;
		}
		if (menuName.equals(CHANGER_OFFER)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentChangeOffer(), true);
			return;
		}
		if (menuName.equals(CHANGE_PROMOTION)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentChangePromotion(), true);
			return;
		}
		if (menuName.equals(CHANGE_TECH)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentManagerChangeTech(), true);
			return;
		}

		if (menuName.equals(BUNDLE_GROUP)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentManageBundleGroup(), true);
			return;
		}
		if (menuName.equals(ID_HOTLINE)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentHotlineManager(), true);
			return;
		}

		if (menuName.equals(ID_MODIFY_HOSO)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentModifyProfile(), true);
			return;
		}

		if (menuName.equals(CARE_LOST_SUB)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentCareLostSub(), true);
			return;
		}

		if (menuName.equals(Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO)) {
			FragmentLookupDebitInfo fragment = new FragmentLookupDebitInfo();

			Bundle bundle = new Bundle();
			bundle.putString(Constant.FUNCTION,
					Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
			fragment.setArguments(bundle);

			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
			return;
		}

		if (menuName.equals(CHECK_USUALLY_CALL)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentCheckUsuallyCall(), true);
			return;
		}
		if (menuName.equals(CHANGE_SIM)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentSearchSubChangeSim(), true);
			return;
		}
		if (menuName.equals(VIEW_OCS_HLR)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentViewOcsHlr(), true);
			return;
		}
		if (menuName.equals(Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL)) {
			FragmentLookupDebitInfo fragment = new FragmentLookupDebitInfo();

			Bundle bundle = new Bundle();
			bundle.putString(Constant.FUNCTION,
					Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL);
			fragment.setArguments(bundle);

			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
			return;
		}

		if (menuName.equals(Constant.MENU_FUNCTIONS.DO_WARRANTY)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentWarranty(), true);
			return;
		}

		if (menuName.equals(Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentLookupProfile(), true);
			return;
		}

		if (menuName.equals(COLLECT_CUS_INFO)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentCollectCusInfo(), true);
			return;
		}
		if (menuName.equals(COLLECT_BUSSINESS)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentSearchComporation(), true);
			return;
		}

		if (menuName.equals(CHANGE_EQUIPMENT)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeEquimentFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
			return;
		}

		if (menuName.equals(TICK_APPOINT)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new TickAppointFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
			return;
		}
		if (menuName.equals(CHANGE_EQUIPMENT)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeEquimentFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
		
//		if(menuName.equals(CHANGE_PRE_TO_POS)){
//			FragmentSearchSubChangeSim fragmentSearchSub = new FragmentSearchSubChangeSim();
//			Bundle bundle = new Bundle();
//			bundle.putString("functionKey", Constant.CHANGE_PRE_TO_POS);
//			fragmentSearchSub.setArguments(bundle);
//			
//			ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSub, true);
//			
//		}
//		if(menuName.equals(CHANGE_POS_TO_PRE)){
//			FragmentSearchSubChangeSim fragmentSearchSub = new FragmentSearchSubChangeSim();
//			Bundle bundle = new Bundle();
//			bundle.putString("functionKey", Constant.CHANGE_POS_TO_PRE);
//			fragmentSearchSub.setArguments(bundle);
//			
//			ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSub, true);
//			
//		}
		}
		if (menuName.equals(CHANGE_PROMOTION_BCCS)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangePromotionFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
		}
		
		if (menuName.equals(CHANGE_PRODUCT_BCCS)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeProductFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
		}
		if (menuName.equals(REGISTER_VAS_SAFENET_BCCS)) {
			ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new RegisterSafeNetFragmentStrategy();
			Bundle bundle = new Bundle();
			bundle.putSerializable("fragmentStrategy",
					manageSubscriberFragmentStrategy);
			FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
			fragmentManageSubscriber.setArguments(bundle);
			fragmentManageSubscriber.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentManageSubscriber, false);
		}
	}

}
