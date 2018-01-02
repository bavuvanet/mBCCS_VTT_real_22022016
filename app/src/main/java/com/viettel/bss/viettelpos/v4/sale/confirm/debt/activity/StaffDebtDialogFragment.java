package com.viettel.bss.viettelpos.v4.sale.confirm.debt.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.ConfirmDebitTransDTO;

import butterknife.ButterKnife;

/**
 * Created by huypq15 on 4/26/2017.
 */

public class StaffDebtDialogFragment extends DialogFragment {
    private ConfirmDebitTransDTO mItem;
    static StaffDebtDialogFragment newInstance(ConfirmDebitTransDTO item) {
        StaffDebtDialogFragment f = new StaffDebtDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = (ConfirmDebitTransDTO)getArguments().getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_debt_staff_detail_dialog, container, false);
        ButterKnife.bind(this,v);

        return v;
    }

}

