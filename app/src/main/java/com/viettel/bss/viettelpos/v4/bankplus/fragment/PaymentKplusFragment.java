package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DialogConfirmCreateBankplusTrans;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hantt47 on 8/4/2017.
 */

public class PaymentKplusFragment extends FragmentCommon {

    private Activity activity;
    private ViewHolder holder;
    private List<String> listPackageData = new ArrayList<>(Arrays.asList("Gói 1", "Gói 2", "Gói 3", "Gói 4"));
    private List<String> listTime = new ArrayList<>(Arrays.asList("1 tháng", "2 tháng", "3 tháng"));
    private Map<String, String> mapMoney = new HashMap<>();
    private String time;
    private String money;
    private String phoneReceiveSms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        idLayout = R.layout.bankplus_payment_kplus_fragment;
    }


    @Override
    protected void unit(View v) {

        mapMoney.put("1 tháng", "200.000");
        mapMoney.put("2 tháng", "400.000");
        mapMoney.put("3 tháng", "600.000");

        holder = new ViewHolder();

        holder.edtHeadDecode = (EditText) v.findViewById(R.id.edtHeadDecode);
        holder.rbExtendKplus = (RadioButton) v.findViewById(R.id.rbExtendKplus);
        holder.rbChangeKplus = (RadioButton) v.findViewById(R.id.rbChangeKplus);
        holder.imgVerify = (ImageView) v.findViewById(R.id.imgVerify);
        holder.spnPackageData = (Spinner) v.findViewById(R.id.spnPackageData);
        holder.spnTime = (Spinner) v.findViewById(R.id.spnTime);
        holder.spnTime.setOnItemSelectedListener(onTimeSelect);
        holder.txtMoney = (TextView) v.findViewById(R.id.txtMoney);
        holder.edtPhoneNumber = (EditText) v.findViewById(R.id.edtPhoneNumber);
        holder.btnPayment = (Button) v.findViewById(R.id.btnPayment);
        holder.lnInfor = (LinearLayout) v.findViewById(R.id.lnInfor);

        ArrayAdapter<String> adapterPackageData = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item);

        for (String packageData : listPackageData) {
            adapterPackageData.add(packageData);
        }
        holder.spnPackageData.setAdapter(adapterPackageData);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item);

        for (String time : listTime) {
            adapterTime.add(time);
        }
        holder.spnTime.setAdapter(adapterTime);

        holder.imgVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHeadDecodeInfor();
            }
        });

        holder.btnPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialogConfirm();
            }
        });

    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setTitleActionBar(getString(R.string.bankplus_payment_k_plus));
    }

    private void checkHeadDecodeInfor() {

        if (CommonActivity.isNullOrEmpty(holder.edtHeadDecode)) {
            Toast.makeText(getActivity(),
                    getString(R.string.bankplus_head_decode_required),
                    Toast.LENGTH_LONG).show();
            return;
        }

        //check head_decode
        holder.lnInfor.setVisibility(View.VISIBLE);

    }

    private OnItemSelectedListener onTimeSelect = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            time = (String) holder.spnTime.getSelectedItem();
            money = mapMoney.get(time);
            holder.txtMoney.setText(money);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {


        }
    };


    private boolean validatePaymentKplus() {

        phoneReceiveSms = holder.edtPhoneNumber.getText().toString().trim();

        if (CommonActivity.isNullOrEmpty(phoneReceiveSms)) {
            holder.edtPhoneNumber.requestFocus();
            CommonActivity.createAlertDialog(getActivity(), R.string.bankplus_isdn_invalid,
                    R.string.app_name).show();
            return false;
        }

        return true;
    }


    private void showDialogConfirm() {
        if (!validatePaymentKplus()) {
            return;
        }
        String packageData = (String) holder.spnPackageData.getSelectedItem();
        String headDecode = holder.edtHeadDecode.getText().toString().trim();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        String msg = getString(R.string.bankplus_confirm_payment_k_plus,
                headDecode, packageData, time, money, phoneReceiveSms);

        String title = getString(R.string.bankplus_confirm_payment_k_plus);
        DialogFragment newFragment = DialogConfirmCreateBankplusTrans
                .newInstance(title, msg.toString(), 0, new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
//                        doPaymentKplus();
                        Toast.makeText(getActivity(), "Thanh toán thành công", Toast.LENGTH_LONG).show();
                    }
                });
        newFragment.show(ft, "dialog");
    }


    public class ViewHolder {
        EditText edtHeadDecode;
        RadioButton rbExtendKplus;
        RadioButton rbChangeKplus;
        ImageView imgVerify;
        Spinner spnPackageData;
        Spinner spnTime;
        TextView txtMoney;
        EditText edtPhoneNumber;
        Button btnPayment;
        LinearLayout lnInfor;
    }
}
