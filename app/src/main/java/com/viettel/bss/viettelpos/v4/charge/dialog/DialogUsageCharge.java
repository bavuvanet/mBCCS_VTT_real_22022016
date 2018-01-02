package com.viettel.bss.viettelpos.v4.charge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetChargeInfo;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.charge.object.UsageCharge;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuyPQ15 on 1/4/2017.
 */

public class DialogUsageCharge extends Dialog {

    private List<UsageCharge> lstUsageCharge = new ArrayList<UsageCharge>();
    private Context context;
    ListView lv;
    private String contractId;

    public DialogUsageCharge(Context context, String contractId) {
        super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        this.contractId = contractId;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_single_list_view);
        TextView tvTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvTitle.setText(R.string.charge_usage);
        lv = (ListView) findViewById(R.id.lvTransHis);

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
    public void searchData(){
        AsyncGetChargeInfo asy = new AsyncGetChargeInfo(context, onPostSuccessExecute, contractId,
                0, 100, false, true);
        asy.execute();
    }
    public List<UsageCharge> getLstData(){
        return lstUsageCharge;
    }
    OnPostSuccessExecute<PaymentOutput> onPostSuccessExecute = new OnPostSuccessExecute<PaymentOutput>() {
        @Override
        public void onPostSuccess(PaymentOutput result) {
            if (result.getMsgDebitBean() != null) {
                lstUsageCharge = result.getMsgDebitBean().getLstUsageCharge();
            }

            if (CommonActivity.isNullOrEmpty(lstUsageCharge)) {
                String des = result.getDescription();
                if (CommonActivity.isNullOrEmpty(des)) {
                    des = context.getString(R.string.no_data);
                }
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        des, context.getString(R.string.app_name));
                dialog.show();
                return;
            }
            ArrayAdapter<UsageCharge> adapter = new ArrayAdapter<UsageCharge>(context, R.layout.spinner_item, lstUsageCharge);
            lv.setAdapter(adapter);
        }
    };
}
