package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.business.GetStockOrderManager;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrder;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrderManager;

import java.util.ArrayList;

public class FragmentSaleManager extends Fragment implements OnItemClickListener, OnClickListener {
	private GridView grid;
	private ArrayList<Manager> arrayListManager;
    // private Button btnHome;

	// Ban dut cho kenh db, ctv
	private final String SALE_SALING = "0";
	// Xuat dat coc
	private final String SALE_DEPOSIT = "1";
	//
	private final String SALE_BHLD = "2";
	private final String SALE_RETAIL = "3";
	private final String CONFIRM_NOTE = "4";
	private final String CREATE_INVOICE = "5";
	private final String VIEW_INFO_ORDER_ITEM = "6";
	private final String SALE_BY_ORDER = "7";
	private final String APPROVE_ORDER = "8";
	private final String SALE_CREATE_CHANEL = "9";
	private final String SALE_ACTIVE_ACCOUNT_PAYMENT = "10";
	private final String SALE_INFO_SEARCH = "11";
	private final String RETURN_THE_GOOD = "12";
	private final String SALE_TO_CUSTOMER = "13";
	private final String SALE_SEARCH_ISDN = "14";
	private final String SALE_2G_TO_3G = "15";
	private final String RECHARGE_TO_BANK = "16";
	private final String APPROVE_MONEY = "17";

	private final String CHANGE_PASS_ANYPAY = "18";
	private final String SEARCH_ANYPAY = "19";
	private final String SALE_SEARCH_SERIAL_CARD = "SALE_SEARCH_SERIAL_CARD";
	private final String SALE_SEARCH_KIT = "SALE_SEARCH_KIT";
	private final String SALE_ANYPAY_ISDN = "SALE_ANYPAY_ISDN";
	private final String SALE_ANYPAY_EXCHANGE = "SALE_ANYPAY_EXCHANGE";
	private final String VIEW_STOCK = "VIEW_STOCK";
	private final String CHANNEL_ORDER = "CHANNEL_ORDER";
	private final String STAFF_HANDLE_ORDER = "STAFF_HANDLE_ORDER";
	private final String SALE_PROMOTION = "SALE_PROMOTION";
	// private TextView txtNameActionBar;
	private final String tag = FragmentSaleManager.class.getName();

	private EditText edt_search;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.layout_sale_manager, container, false);
		unit(mView);

		return mView;
	}

	@Override
	public void onDestroy() {

		Log.e(tag, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// // TODO Auto-generated method stub
		Log.e(tag, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.e(tag, "onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.e(tag, "onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		addManagerList();
		Log.e(tag, "onResume");
		CacheData.getInstanse().setLisInfoOjectMerges(null);
		CacheData.getInstanse().setStockOrderCode(null);
        MainActivity.getInstance().getSupportActionBar().setTitle(getString(R.string.sales));
		super.onResume();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.e(tag, "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.e(tag, "onStop");
		super.onStop();
	}

	private void unit(View v) {
		grid = (GridView) v.findViewById(R.id.gridView);
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
			grid.setAdapter(itemsAdapter);
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
			grid.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}

	private void addManagerList() {
		arrayListManager = new ArrayList<>();
		SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if (name.contains(Constant.VSAMenu.SALE_SALING)) {
			arrayListManager.add(new Manager(R.drawable.sale_channel, getResources().getString(R.string.sale_saling), 0,
					SALE_SALING));
		}

		if (name.contains(Constant.VSAMenu.SALE_DEPOSIT)) {
			arrayListManager.add(new Manager(R.drawable.sale_deposit, getResources().getString(R.string.sale_deposit),
					0, SALE_DEPOSIT));
		}
		if (name.contains(Constant.VSAMenu.SALE_PROGRAM)) {
			arrayListManager
					.add(new Manager(R.drawable.sale_bhld, getResources().getString(R.string.sale_bhld), 0, SALE_BHLD));
		}
		if (name.contains(Constant.VSAMenu.SALE_RETAIL)) {
			arrayListManager.add(new Manager(R.drawable.sale_retail, getResources().getString(R.string.sale_retail), 0,
					SALE_RETAIL));
		}
		if (name.contains(Constant.VSAMenu.SALE_CONFIRM_NOTE)) {
			arrayListManager.add(new Manager(R.drawable.sale_confirm_note,
					getResources().getString(R.string.confirmNote), 0, CONFIRM_NOTE));
		}
		if (name.contains(Constant.VSAMenu.SALE_CREATE_INVOICE)) {
			arrayListManager.add(new Manager(R.drawable.sale_create_invoice,
					getResources().getString(R.string.createInvoice), 0, CREATE_INVOICE));
		}
		if (name.contains(Constant.VSAMenu.SALE_BY_ORDER)) {
			arrayListManager.add(new Manager(R.drawable.sale_by_order, getResources().getString(R.string.sale_by_order),
					0, SALE_BY_ORDER));
		}
		if (name.contains(Constant.VSAMenu.SALE_ORDER)) {
			arrayListManager.add(new Manager(R.drawable.dathangcaptren,
					getResources().getString(R.string.viewinfoandorder), 0, VIEW_INFO_ORDER_ITEM));
		}
		if (name.contains(Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER)) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.approveOrder), 0, APPROVE_ORDER));
		}

		if (name.contains(Constant.VSAMenu.MENU_CREATE_CHANNEL)) {
			arrayListManager.add(new Manager(R.drawable.qly_kenh_18, getResources().getString(R.string.add_news_chanel),
					0, SALE_CREATE_CHANEL));
		}

		if (name.contains(Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT)) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.add_active_account_payment), 0, SALE_ACTIVE_ACCOUNT_PAYMENT));
		}

//		if (name.contains(Constant.VSAMenu.MENU_REVIEW_PAYMENT_STAFF)) {
//
//			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
//					getResources().getString(R.string.add_info_search), 0, SALE_INFO_SEARCH));
//
//		}

		if (name.contains(Constant.VSAMenu.MENU_RETURN_GOOD)) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.menu_item_sale_return_the_good), 0, RETURN_THE_GOOD));
		}

//		if (name.contains(";outlet_sale;")) {
//			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
//					getResources().getString(R.string.sale_for_customer), 0, SALE_TO_CUSTOMER));
//		}
		if (name.contains(";search_isdn;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_for_search_isdn), 0, SALE_SEARCH_ISDN));
		}
		if (name.contains(";2Gto3G;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.change2Gto3G), 0, SALE_2G_TO_3G));
		}
		if (name.contains(";rechargeToBank;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.rechargetobank), 0, RECHARGE_TO_BANK));
		}
		if (name.contains(";approveBplus;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.tichduyetnoptien), 0, APPROVE_MONEY));
		}
		if (name.contains(";sale_search_serial_card;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_search_serial_card), 0, SALE_SEARCH_SERIAL_CARD));
		}

		if (name.contains(";sale_search_kit;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_search_kit), 0, SALE_SEARCH_KIT));
		}

		if (name.contains(";sale_anypay_isdn;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_anypay_isdn), 0, SALE_ANYPAY_ISDN));
		}

		if (name.contains(";sale_anypay_exchange;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_anypay_exchange), 0, SALE_ANYPAY_EXCHANGE));
		}
		if (name.contains(";change.pass.anypay;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.change_pass_anypay), 0, CHANGE_PASS_ANYPAY));
		}

		if (name.contains(";view_stock;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang, getResources().getString(R.string.view_stock),
					0, VIEW_STOCK));
		}
//		if (name.contains(";channel.order;")) {
//			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
//					getResources().getString(R.string.channel_order), 0, CHANNEL_ORDER));
//		}
		// Nhan vien xu ly don hang
//		if (name.contains(";staff_manage_order;")) {
//			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
//					getResources().getString(R.string.xy_ly_don_hang_diem_ban), 0, STAFF_HANDLE_ORDER));
//		}
		if (name.contains(";sale_promotion;")) {
			arrayListManager.add(new Manager(R.drawable.pheduyetdonhang,
					getResources().getString(R.string.sale_promotion),
					0, SALE_PROMOTION));
		}
		arrayListManager.add(new Manager(R.drawable.pheduyetdonhang, getResources().getString(R.string.tctienich), 0,
				SEARCH_ANYPAY));
        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		grid.setAdapter(mAdapterManager);
		grid.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MainActivity.mLevelMenu = 1;
		Manager item = (Manager) arg0.getAdapter().getItem(arg2);

		String menuName = item.getKeyMenuName();
        switch (menuName) {
            case SALE_SALING: {
                // Xuat ban dut
                // Intent i = new Intent(getActivity(), FragmentSaleSaling.class);
                Bundle bundle = new Bundle();
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                // startActivity(i);
                break;
            }
            case SALE_DEPOSIT: {

                // Ban dat coc

                Bundle bundle = new Bundle();
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_DEPOSIT);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                break;
            }
            case SALE_BHLD: {
                // Ban cho kenh BHLD
                // Intent i = new Intent(getActivity(), FragmentChooseBHLD.class);
                FragmentChooseBHLD fragment = new FragmentChooseBHLD();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                break;
            }
            case SALE_RETAIL: {
                // Ban le
                Bundle b = new Bundle();
                b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_RETAIL);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(b);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case SALE_PROMOTION: {
                // Ban khuyen mai
                Bundle b = new Bundle();
                b.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_PROMOTION);
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(b);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case CONFIRM_NOTE: {
                FragmentConfirmNote fragment = new FragmentConfirmNote();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                // Intent i = new Intent(getActivity(), FragmentConfirmNote.class);
                // startActivity(i);
                break;
            }
            case CREATE_INVOICE: {
                FragmentCreateInvoice fragment = new FragmentCreateInvoice();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case SALE_BY_ORDER: {
                FragmentSaleByOrder fragment = new FragmentSaleByOrder();
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                break;
            }
            case VIEW_INFO_ORDER_ITEM:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    GetStockOrderManager getStockOrderManager = new GetStockOrderManager(getActivity());
                    getStockOrderManager.execute();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), getResources().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case APPROVE_ORDER:
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                ReplaceFragment.replaceFragment(getActivity(), fragmentListOrder, false);
                break;
            case SALE_CREATE_CHANEL:
                FragmentCreateChanel fragmentCreateChanel = new FragmentCreateChanel();
                ReplaceFragment.replaceFragment(getActivity(), fragmentCreateChanel, false);
                break;
            case SALE_ACTIVE_ACCOUNT_PAYMENT:
                FragmentActiveAccountPayment fragmentActiveAccount = new FragmentActiveAccountPayment();
                ReplaceFragment.replaceFragment(getActivity(), fragmentActiveAccount, false);
                break;
            case SALE_INFO_SEARCH:
                FragmentInfoSearch fragmentInfoSearch = new FragmentInfoSearch();
                ReplaceFragment.replaceFragment(getActivity(), fragmentInfoSearch, false);
                break;
            case RETURN_THE_GOOD:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleSalingReturn(), false);
                break;
            case SALE_TO_CUSTOMER:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSaleToCustomers(), false);
                break;
            case SALE_SEARCH_ISDN:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchISDN(), false);

                break;
            case SALE_2G_TO_3G:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChange2GTo3G(), false);
                break;
            case RECHARGE_TO_BANK:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentRechargeToBank(), false);
                break;
            case APPROVE_MONEY:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentCheckoutMoney(), false);
                break;
            case SALE_SEARCH_SERIAL_CARD:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchSerialCard(), false);
                break;
            case SALE_SEARCH_KIT:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchKIT(), false);
                break;
            case SALE_ANYPAY_ISDN:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayISDN(), false);
                break;
            case SALE_ANYPAY_EXCHANGE:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentAnypayExchange(), false);
                break;
            case VIEW_STOCK:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChooseStaff(), false);
                break;
            case CHANGE_PASS_ANYPAY:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChangePassAnypay(), false);
                break;
            case CHANNEL_ORDER:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrder(), false);
                break;
            case SEARCH_ANYPAY:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentSearchAnypay(), false);
                break;
            case STAFF_HANDLE_ORDER:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentChannelOrderManager(), false);
                break;
        }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			// getActivity().onBackPressed();
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			break;
		default:
			break;
		}
	}

}
