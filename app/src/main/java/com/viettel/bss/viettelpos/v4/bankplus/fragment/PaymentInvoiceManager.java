package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.MenuAdapter;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

public class PaymentInvoiceManager extends Fragment implements OnItemClickListener {
	private View mView;
	private ListView listView;
	private ArrayList<Manager> arrayListManager;
	private MenuAdapter mAdapterManager;
	private final String TELECOMMUNICATION = "TELECOMMUNICATION";

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.bankplus_menu_payment_invoice);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_listview, container,
					false);

			listView = (ListView) mView.findViewById(R.id.listView);
			arrayListManager = new ArrayList<Manager>();
			addManagerList();
		}
		return mView;
	}

	private void addManagerList() {
		arrayListManager.clear();
		
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus, getResources()
				.getString(R.string.bankplus_payment_electric), 0,
				Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_ELECTRIC));

		arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus, getResources()
				.getString(R.string.bankplus_payment_water), 0,
				Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_WATER));
		
		arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus, getResources()
				.getString(R.string.bankplus_payment_television_cab), 0,
				Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_TELEVISION));

//		arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus, getResources()
//				.getString(R.string.bankplus_payment_k_plus), 0,
//				Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_K_PLUS));
		
		arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus, getResources()
				.getString(R.string.bankplus_payment_finance), 0,
				Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_FINANCE));

		mAdapterManager = new MenuAdapter(arrayListManager, getActivity());
		listView.setAdapter(mAdapterManager);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
		MainActivity.mLevelMenu = 1;
		if (menuName.equals(TELECOMMUNICATION)) {
			FragmentTelecommunicationManager fragment = new FragmentTelecommunicationManager();
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			return;
		}

		if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_ELECTRIC)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new PaymentElectricFragment(), false);
			return;
		}

		if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_WATER)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new PaymentWaterFragment(), false);
			return;
		}

		if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_TELEVISION)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new PaymentTelevisionFragment(), false);
			return;
		}

//		if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_K_PLUS)) {
//			ReplaceFragment.replaceFragment(getActivity(),
//					new PaymentKplusFragment(), false);
//			return;
//		}
		
		if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_PAYMENT_FINANCE)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new PaymentFinanceFragment(), false);
			return;
		}
	}
}