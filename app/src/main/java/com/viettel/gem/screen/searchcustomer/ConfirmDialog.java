package com.viettel.gem.screen.searchcustomer;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;


import com.viettel.bss.viettelpos.v4.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 21/11/2017.
 */

public class ConfirmDialog extends Dialog {

    ButtonClickDialog buttonClickDialog;

    public ConfirmDialog(@NonNull Context context, ButtonClickDialog buttonClickDialog) {
        super(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        this.buttonClickDialog = buttonClickDialog;
        setContentView(R.layout.confirm_collect_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.add_btn, R.id.cancel_btn, R.id.close_btn})
    void onClickView(View view){
        switch (view.getId()){
            case R.id.add_btn:
                buttonClickDialog.onAddNew();
                dismiss();
                break;
            case R.id.cancel_btn:
                buttonClickDialog.onCancel();
                dismiss();
                break;
            case R.id.close_btn:
                dismiss();
                break;
        }
    }

    public interface ButtonClickDialog{
        void onAddNew();

        void onCancel();
    }
}
