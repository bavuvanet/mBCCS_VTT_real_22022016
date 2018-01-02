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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.MenuAdapter;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

public class FragmentTelecommunicationManager extends Fragment implements OnItemClickListener {
    private View mView;
    private ListView grid;
    private ArrayList<Manager> arrayListManager;
    private MenuAdapter mAdapterManager;
    private final String PAYMENT_DEBIT = "PAYMENT_DEBIT";
    private final String BUY_PINCODE = "BUY_PINCODE";

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.menu_telecommunication);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_listview,
                    container, false);
            grid = (ListView) mView.findViewById(R.id.listView);
            arrayListManager = new ArrayList<Manager>();
            addManagerList();
        }
        return mView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
        }
        return false;
    }

    private void addManagerList() {
        arrayListManager.clear();

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        if (name.contains(";m.pay.telecom;")) {
            // Thanh toan cuoc
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.menu_payment_debit), 0,
                    PAYMENT_DEBIT, getString(R.string.payemnt_charge_content)));
        }
        if (name.contains(";m.buy.pincode;")) {
            // Mua pincode
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.buy_pincode), 0,
                    BUY_PINCODE, getString(R.string.payemnt_charge_content)));
        }
        mAdapterManager = new MenuAdapter(arrayListManager, getActivity());
        grid.setAdapter(mAdapterManager);
        grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String menuName = arrayListManager.get(arg2).getKeyMenuName();
        MainActivity.mLevelMenu = 1;
        if (menuName.equals(PAYMENT_DEBIT)) {

            ReplaceFragment.replaceFragment(getActivity(),
                    new FragmentPaymentCharge(), false);
            return;
        }
        if (menuName.equals(BUY_PINCODE)) {

            ReplaceFragment.replaceFragment(getActivity(),
                    new FragmentBuyPincode(), false);
            return;
        }

    }

}
