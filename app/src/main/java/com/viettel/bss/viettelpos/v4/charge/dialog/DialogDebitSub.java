package com.viettel.bss.viettelpos.v4.charge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.adapter.DebitSubAdapter;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetChargeInfo;
import com.viettel.bss.viettelpos.v4.charge.object.DebitSub;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuyPQ15 on 1/4/2017.
 */

public class DialogDebitSub extends Dialog {
    private final int MAX_RECORD_PER_PAGE = 50;
    private Context context;
    private TextView tvHint;
    private String contractId;
    private List<DebitSub> lstData = new ArrayList<DebitSub>();
    private Boolean loadMore = false;
    private DebitSubAdapter adapter;
    private TextView tvIsdnCharge;
    //    private OnScrollListView onScroll;
    public DialogDebitSub(Context context, String contractId,TextView tvIsdnCharge) {
        super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        this.context = context;
        this.tvIsdnCharge = tvIsdnCharge;
        this.contractId = contractId;
//        this.onScroll = onScroll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_single_list_view);
        tvHint = (TextView) findViewById(R.id.tvHint);
        tvHint.setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvTitle.setText(R.string.debit_sub);
        ListView lv = (ListView) findViewById(R.id.lvTransHis);
        adapter = new DebitSubAdapter(context, lstData);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DebitSub item = lstData.get(i);
                Boolean check = !item.getCheck();
                for (DebitSub tmp : lstData) {
                    tmp.setCheck(false);
                }
                item.setCheck(check);
                if(check){
                    tvIsdnCharge.setVisibility(View.VISIBLE);
                    tvIsdnCharge.setText(context.getString(R.string.isdnCharge,item.getIsdn()));
                }else{
                    tvIsdnCharge.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastItemScreen = firstVisibleItem + visibleItemCount;
                if ((lastItemScreen == totalItemCount) && (loadMore)
                        && totalItemCount > 0) {
                    loadMore = false;
                    AsyncGetChargeInfo asy = new AsyncGetChargeInfo(context, onPostSuccessGetDebitSub, contractId,
                            lstData.size() / MAX_RECORD_PER_PAGE, MAX_RECORD_PER_PAGE, true, false);
                    asy.execute();
                }
            }
        });
        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
    }

    public List<DebitSub> getLstData() {
        return lstData;
    }

    public void searchData() {
        AsyncGetChargeInfo asy = new AsyncGetChargeInfo(context, onPostSuccessGetDebitSub, contractId,
                lstData.size() / MAX_RECORD_PER_PAGE, MAX_RECORD_PER_PAGE, true, false);
        asy.execute();
    }

    private OnPostSuccessExecute<PaymentOutput> onPostSuccessGetDebitSub = new OnPostSuccessExecute<PaymentOutput>() {
        @Override
        public void onPostSuccess(PaymentOutput result) {
            if (result.getMsgDebitBean() == null || CommonActivity.isNullOrEmpty(result.getMsgDebitBean().getLstDebitSub())
                    || result.getMsgDebitBean().getLstDebitSub().size() < MAX_RECORD_PER_PAGE) {
                loadMore = false;
            } else {
                loadMore = true;

            }
            List lstTmp = null;
            if (result.getMsgDebitBean() != null) {
                lstTmp = result.getMsgDebitBean().getLstDebitSub();
                if (!CommonActivity.isNullOrEmpty(lstTmp)) {
                    lstData.addAll(lstTmp);
                }
            }

            if (CommonActivity.isNullOrEmpty(lstData)) {
                String des = result.getDescription();
                if (CommonActivity.isNullOrEmpty(des)) {
                    des = context.getString(R.string.no_data);
                }
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        des, context.getString(R.string.app_name));
                dialog.show();
            }

            adapter.notifyDataSetChanged();
        }
    };

    public String getIsdnCharge() {
        if (!CommonActivity.isNullOrEmpty(lstData)) {
            for (DebitSub item : lstData) {
                if (item.getCheck()) {
                    return item.getIsdn();
                }
            }
        }
        return "";
    }
}
