package com.viettel.bss.viettelpos.v4.sale.confirm.debt.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.confirm.debt.asynctask.FindByShopIdStaffIdAndCycleMonthAsync;
import com.viettel.bss.viettelpos.v4.sale.object.ConfirmDebitTransDTO;
import com.viettel.bss.viettelpos.v4.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by huypq15 on 4/21/2017.
 */

public class ConfirmDebitFragment extends Fragment {
     final int REQUEST_SHOW_DETAIL = 12;
     final String permission = ";confirm.debit;";
     View mView;
    @BindView(R.id.spnDebtCycle)
     Spinner spnDebtCycle;
    @BindView(R.id.spnStatus)
     Spinner spnStatus;
    @BindView(R.id.btnSearchDebt)
     Button btnSearchDebt;
    @BindView(R.id.lnDebt)
     View lnDebt;
    @BindView(R.id.tvDebtCycle)
     TextView tvDebtCycle;
    @BindView(R.id.tvApproveTime)
     TextView tvApproveTime;
    @BindView(R.id.tvDebtStatus)
     TextView tvDebtStatus;
    @BindView(R.id.tvConfirmTime)
     TextView tvConfirmTime;
    @BindView(R.id.btnDebtDetail)
     Button btnDebtDetail;
    private String cycleDate;
 ConfirmDebitTransDTO bo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_confirm_debit, container, false);
        ButterKnife.bind(this, mView);
        lnDebt.setVisibility(View.GONE);
        initSpnCycle();
        initSpnStatus();
        return mView;
    }

    @OnItemSelected(R.id.spnDebtCycle)
    void clearData(){
        lnDebt.setVisibility(View.GONE);
    }
    @OnItemSelected(R.id.spnStatus)
    void clearData1(){
        lnDebt.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnSearchDebt)
     void onSearchDebt() {
        try {
             cycleDate = "01/" + spnDebtCycle.getSelectedItem().toString();
            String strStatus = ((Spin) spnStatus.getSelectedItem()).getId();
            Long status = null;
            if (!CommonActivity.isNullOrEmpty(strStatus)) {
                status = Long.parseLong(strStatus);
            }
            lnDebt.setVisibility(View.GONE);
            FindByShopIdStaffIdAndCycleMonthAsync async =
                    new FindByShopIdStaffIdAndCycleMonthAsync(getActivity(), searchDebtListener, moveLogInAct, cycleDate, status);
            async.execute();
        } catch (Exception e) {
            android.util.Log.e(e.getMessage(), e.getMessage(), e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.confirm_debit,false);
    }

    OnPostExecuteListener<List<ConfirmDebitTransDTO>> searchDebtListener = new OnPostExecuteListener<List<ConfirmDebitTransDTO>>() {
        @Override
        public void onPostExecute(List<ConfirmDebitTransDTO> result, String errorCode, String description) {
            if (CommonActivity.isNullOrEmpty(result)) {
                CommonActivity.createErrorDialog(getActivity(), getString(R.string.no_debt_staff_found, Session.userName, spnDebtCycle.getSelectedItem().toString()), "1").show();
                return;
            }
            lnDebt.setVisibility(View.VISIBLE);
            bo = result.get(0);
            tvDebtCycle.setText(getString(R.string.debt_cycle, bo.getBillCycleString()));
//            String dateApprove = DateTimeUtils.convertDateSoap(bo.getApproveDate());
//            tvApproveTime.setText(getString(R.string.time_approve_debt, dateApprove));
            tvDebtStatus.setText(getString(R.string.status_arg, bo.getStatusName()));
            Long status = bo.getStatus();
            tvConfirmTime.setVisibility(View.GONE);
            if (status != null) {
                if (status.compareTo(1L) == 0) {
                    tvConfirmTime.setText(DateTimeUtils.convertDateSoap(bo.getConfirmDate()));
                    tvConfirmTime.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    protected final View.OnClickListener moveLogInAct = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();

        }
    };

     void initSpnCycle() {
        List<String> lstMonth = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i <= 12; i++) {
            cal.add(Calendar.MONTH, -1);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            lstMonth.add(month + "/" + year);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, lstMonth);
        spnDebtCycle.setAdapter(adapter);
    }

     void initSpnStatus() {
        List<Spin> lstStatus = new ArrayList<Spin>();
        Spin first = new Spin();
        first.setName(getString(R.string.all));
        lstStatus.add(first);

        Spin unconfirm = new Spin();
        unconfirm.setName(getString(R.string.debt_unconfirm));
        unconfirm.setId("0");
        lstStatus.add(unconfirm);
        Spin confirmed = new Spin();
        confirmed.setName(getString(R.string.debt_confirmed));
        confirmed.setId("1");
        lstStatus.add(confirmed);

         com.viettel.bss.viettelpos.v4.ui.image.utils.Utils.setDataSpinner(getActivity(),lstStatus,spnStatus);
    }
    @OnClick(R.id.btnDebtDetail)
     void showDetailDebit(){
        Bundle bundle = new Bundle();
        bundle.putString("fromDate", cycleDate);
        bundle.putLong("status", bo.getStatus());
        DebtStaffDetailFragment fragment = new DebtStaffDetailFragment();
        fragment.setArguments(bundle);
        fragment.setTargetFragment(this, REQUEST_SHOW_DETAIL);
        ReplaceFragment.replaceFragment(getActivity(), fragment, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SHOW_DETAIL && resultCode == Activity.RESULT_OK){
           onSearchDebt();
        }
    }
}