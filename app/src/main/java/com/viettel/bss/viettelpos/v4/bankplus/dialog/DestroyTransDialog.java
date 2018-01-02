package com.viettel.bss.viettelpos.v4.bankplus.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.SubWarningInfoAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/13/2017.
 */

public class DestroyTransDialog extends Dialog {

    private ImageButton imgbtDestroyTrans;
    private Context context;
    private Activity activity;
    private String transCode;
    private String isdn;

    private LinearLayout linIsdn;
    private Button btnCall;
    private Button btnGetOtp;
    private Button btnDestroyTrans;
    private EditText edtOtpCode;
    private TextView tvTransCode;
    private TextView tvIsdn;
    private ImageButton imgbtCancel;

    public DestroyTransDialog(ImageButton imgbtDestroyTrans, Context context, Activity activity,
                              String transCode, String isdn) {
        super(context);
        this.imgbtDestroyTrans = imgbtDestroyTrans;
        this.context = context;
        this.activity = activity;
        this.transCode = transCode;
        this.isdn = isdn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bankplus_dialog_destroy_trans);
        this.setCanceledOnTouchOutside(false);

        this.linIsdn = (LinearLayout) findViewById(R.id.linIsdn);
        this.edtOtpCode = (EditText) findViewById(R.id.edtOtpCode);
        this.tvTransCode = (TextView) findViewById(R.id.tvTransCode);
        this.tvIsdn = (TextView) findViewById(R.id.tvIsdn);
        this.btnCall = (Button) findViewById(R.id.btnCall);
        this.btnGetOtp = (Button) findViewById(R.id.btnGetOtp);
        this.btnDestroyTrans = (Button) findViewById(R.id.btnDestroyTrans);
        this.imgbtCancel = (ImageButton) findViewById(R.id.imgbtCancel);

        this.linIsdn.setVisibility(View.GONE);
        this.tvIsdn.setText("0" + isdn);
        this.tvTransCode.setText("" + transCode);

        this.imgbtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });

        this.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doGetOtp(transCode);
            }
        });

        this.btnDestroyTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isValidateOpt()) {
                    alertDestroyTrans(transCode, edtOtpCode.getText().toString());
                }
            }
        });

        this.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                callphone(activity, isdn);
            }
        });
    }

    private void callphone(final Activity context, final String phone) {

        View.OnClickListener onclickCall = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String uri = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                try {
                    context.startActivity(intent);
                } catch (SecurityException ex) {
                    Log.e("SecurityException call phone", ex.getMessage());
                }
            }
        };

        CommonActivity.createDialog(context,
                context.getString(R.string.confirm_call_phone, phone),
                context.getString(R.string.app_name),
                context.getString(R.string.cancel),
                context.getString(R.string.ok), null,onclickCall).show();
    }

    private boolean isValidateOpt() {

        if(CommonActivity.isNullOrEmpty(edtOtpCode)){
            edtOtpCode.requestFocus();
            CommonActivity.createAlertDialog(activity,
                    R.string.bankplus_otp_not_null,
                    R.string.app_name).show();
            return false;
        }

        if (edtOtpCode.getText().toString().length() < 6) {
            edtOtpCode.requestFocus();
            CommonActivity.createAlertDialog(activity,
                    R.string.bankplus_otp_have_six_char,
                    R.string.app_name).show();
            return false;
        }

        return true;
    }

    private void doGetOtp(final String requestId) {
        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(BPConstant.COMMAND_CREATE_OTP_DESTROY_TRANS)
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(requestId).append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), activity,
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        // TODO Auto-generated method stub
                        if ("0".equals(errorCode)) {
                            isdn = "0" + result.getIsdn();
                            tvIsdn.setText(isdn);
                            linIsdn.setVisibility(View.VISIBLE);
                            CommonActivity.createAlertDialog(activity,
                                    context.getString(R.string.bankplus_otp_message_success, isdn),
                                    context.getString(R.string.app_name)).show();
                        } else {
                            Toast.makeText(activity, description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, null).execute();
    }

    private void alertDestroyTrans(final String requestId, final String otpString) {

        final View.OnClickListener destroyTransListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doDestroyTrans(requestId, otpString);
            }
        };

        CommonActivity.createDialog(activity,
                activity.getString(R.string.bankplus_confirm_destroy_trans, requestId),
                activity.getString(R.string.app_name),
                activity.getString(R.string.cancel),
                activity.getString(R.string.ok),
                null, destroyTransListener).show();
    }

    private void doDestroyTrans(String requestId, String otpString) {
        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(BPConstant.COMMAND_DESTROY_TRANS)
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(requestId).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(otpString).append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), activity,
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        // TODO Auto-generated method stub
                        if ("0".equals(errorCode)) {
                            imgbtDestroyTrans.setVisibility(View.INVISIBLE);
                            dismiss();
                            CommonActivity.createAlertDialog(activity,
                                    "Đã hủy giao dịch thành công!",
                                    context.getString(R.string.app_name)).show();
                        } else {
                            Toast.makeText(activity, description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, null).execute();
    }
}