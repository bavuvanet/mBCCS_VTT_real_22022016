package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/26/2017.
 */

public class ChangePinCodeBankplusFragment extends FragmentCommon {

    @BindView(R.id.edtPinOld)
    EditText edtPinOld;
    @BindView(R.id.edtNewPin)
    EditText edtNewPin;
    @BindView(R.id.edtConfirmPin)
    EditText edtConfirmPin;
    @BindView(R.id.btnChangePin)
    Button btnChangePin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.bankplus_change_pin_code_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.bankplus_change_pin));
    }

    @Override
    protected void unit(View v) {
        btnChangePin.setOnClickListener(this);
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnChangePin:
                if (isValidateOpt()) {
                    doChangePinCode();
                }
                break;

            default:
                break;
        }
    }

    private void doChangePinCode() {

        final View.OnClickListener resetPinListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doRequestChangePinCode();
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.bankplus_msg_change_pin_confirm),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                null, resetPinListener).show();
    }

    private boolean isValidateOpt() {

        if(CommonActivity.isNullOrEmpty(edtPinOld)){
            edtPinOld.requestFocus();
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.bankplus_pin_not_null,
                    R.string.app_name).show();
            return false;
        }

        if(CommonActivity.isNullOrEmpty(edtNewPin)){
            edtNewPin.requestFocus();
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.bankplus_pin_not_null,
                    R.string.app_name).show();
            return false;
        }

        if(CommonActivity.isNullOrEmpty(edtConfirmPin)){
            edtConfirmPin.requestFocus();
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.bankplus_pin_not_null,
                    R.string.app_name).show();
            return false;
        }

        if (!edtNewPin.getText().toString().equals(edtConfirmPin.getText().toString())) {
            edtNewPin.requestFocus();
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.bankplus_pin_confirm_invalid,
                    R.string.app_name).show();
            return false;
        }

        return true;
    }

    private void doRequestChangePinCode() {
        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(BPConstant.COMMAND_CHANGE_PIN_CODE).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(edtPinOld.getText().toString()).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(edtNewPin.getText().toString()).append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), getActivity(),
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            edtPinOld.setText("");
                            edtNewPin.setText("");
                            edtConfirmPin.setText("");
                            CommonActivity.createAlertDialog(getActivity(),
                                    getString(R.string.bankplus_msg_change_pin_success),
                                    getString(R.string.app_name)).show();
                        } else {
                            Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, moveLogInAct).execute();
    }
}