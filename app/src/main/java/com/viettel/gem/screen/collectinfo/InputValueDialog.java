package com.viettel.gem.screen.collectinfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BaVV on 11/23/17.
 */

public class InputValueDialog extends Dialog {
    private Callback listener;

    @BindView(R.id.edtValue)
    EditText edtValue;

    private String oldValue = "";

    public InputValueDialog setListener(Callback listener) {
        this.listener = listener;
        return this;
    }

    public InputValueDialog setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public InputValueDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input_dialog);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        edtValue.append(oldValue);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick(R.id.btnOk)
    public void onClickOK() {
        if (listener != null) listener.onValueChanged(edtValue.getText().toString().trim());
        dismiss();
    }

    @OnClick(R.id.btnCancel)
    public void onClickCancel() {
        dismiss();
    }

    public interface Callback {
        void onValueChanged(String value);
    }
}
