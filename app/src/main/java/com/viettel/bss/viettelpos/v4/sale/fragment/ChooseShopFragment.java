package com.viettel.bss.viettelpos.v4.sale.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetLstShopManagedByStaff;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;

import java.util.ArrayList;
import java.util.List;

public class ChooseShopFragment extends Fragment implements OnClickListener {

    private View view;
    private View btnOK = null;
    private View btnCancel;
    private View prb;
    private ListView lvShop;
    private EditText edtFilter;
    private List<Shop> lstShop;
    private List<Shop> lstBackup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.choose_shop_layout, container,
                    false);
            btnOK = view.findViewById(R.id.btnOk);
            btnOK.setOnClickListener(this);

            btnCancel = view.findViewById(R.id.btnViewSaleTrans);
            btnCancel.setOnClickListener(this);
            btnOK.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            prb = view.findViewById(R.id.prbLoadStaff);
            prb.setVisibility(View.GONE);
            lvShop = (ListView) view.findViewById(R.id.lvStaff);
            lvShop.setVisibility(View.VISIBLE);
            edtFilter = (EditText) view.findViewById(R.id.edtSearchStaff);
            Spinner spiChannel = (Spinner) view.findViewById(R.id.spiChannel);
            spiChannel.setVisibility(View.GONE);
            lvShop.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    Intent i = new Intent();
                    Shop shop = lstShop.get(arg2);

                    i.putExtra("shop", shop);
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(), Activity.RESULT_OK, i);
                    getActivity().onBackPressed();
                }
            });
            edtFilter.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    String input = arg0.toString().trim();
                    lstShop = new ArrayList<Shop>();
                    if (CommonActivity.isNullOrEmpty(input)) {
                        if (CommonActivity.isNullOrEmpty(lstBackup)) {
                            return;
                        }
                        lstShop.addAll(lstBackup);
                    } else {
                        for (Shop shop : lstBackup) {
                            String codeName = shop.toString();
                            if (codeName.toLowerCase().contains(input.toLowerCase())) {
                                lstShop.add(shop);
                            }
                        }
                    }
                    ArrayAdapter<Shop> adapter = new ArrayAdapter<Shop>(getActivity(), R.layout.spinner_item, R.id.spinner_value, lstShop);
                    lvShop.setAdapter(adapter);
                }
            });
            AsyncTaskGetLstShopManagedByStaff asy = new AsyncTaskGetLstShopManagedByStaff(getActivity(), onPostGetShop, moveLogInAct);
            asy.execute();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.choose_channel);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnHome:
                CommonActivity.callphone(getActivity(), Constant.phoneNumber);
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            default:
                break;

        }
    }

    private OnPostExecuteListener<List<Shop>> onPostGetShop = new OnPostExecuteListener<List<Shop>>() {
        @Override
        public void onPostExecute(List<Shop> result, String errorCode, String description) {

            lstBackup = result;

            if (CommonActivity.isNullOrEmpty(lstBackup)) {
                CommonActivity.createErrorDialog(getActivity(), getString(R.string.staff_not_manage_shop), "1").show();
                return;
            }
            lstShop = new ArrayList<Shop>();
            lstShop.addAll(lstBackup);
            ArrayAdapter<Shop> adapter = new ArrayAdapter<Shop>(getActivity(), R.layout.spinner_item, R.id.spinner_value, result);
            lvShop.setAdapter(adapter);
        }
    };

    protected final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // startActivity(intent);
            // getActivity().finish();
            LoginDialog dialog = new LoginDialog(getActivity(), "");
            dialog.show();

        }
    };
}
