package com.viettel.bss.viettelpos.v4.report.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.report.object.LogMethodBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toancx on 1/8/2017.
 */

public class FragmentShowDetailLog extends FragmentCommon{

    @BindView(R.id.tvUserCall)
    TextView tvUserCall;
    @BindView(R.id.tvExecuteDate)
    TextView tvExecuteDate;
    @BindView(R.id.tvIpServer)
    TextView tvIpServer;
    @BindView(R.id.tvLogMethodResult)
    TextView tvLogMethodResult;
    @BindView(R.id.tvLogMethodDuration)
    TextView tvLogMethodDuration;
    @BindView(R.id.tvLogMethodClassName)
    TextView tvLogMethodClassName;
    @BindView(R.id.tvInputValue)
    TextView tvInputValue;
    @BindView(R.id.tvResultValue)
    TextView tvResultValue;
    @BindView(R.id.btnInfoGeneral)
    Button btnInfoGeneral;
    @BindView(R.id.btnInputValue)
    Button btnInputValue;
    @BindView(R.id.btnResultValue)
    Button btnResultValue;
    @BindView(R.id.expanInfoGeneral)
    ExpandableLinearLayout expanInfoGeneral;
    @BindView(R.id.expanInputValue)
    ExpandableLinearLayout expanInputValue;
    @BindView(R.id.expanResultValue)
    ExpandableLinearLayout expanResultValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_detail_log_mbccs;
    }

    @Override
    public void unit(View v) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            LogMethodBean logMethodBean = (LogMethodBean) bundle.get("logMethodBean");

            tvUserCall.setText(logMethodBean.getUserCall());
            tvExecuteDate.setText(getString(R.string.executeDate, logMethodBean.getStartTime()));
            tvIpServer.setText(getString(R.string.ipServer, logMethodBean.getServerId()));
            tvLogMethodClassName.setText(getString(R.string.logMethodClassName, logMethodBean.getClassName()));
            tvLogMethodDuration.setText(getString(R.string.logMethodDuration, logMethodBean.getDuration()));
            tvLogMethodResult.setText(getString(R.string.logMethodResult, logMethodBean.getResultCode()));

            tvInputValue.setTextIsSelectable(true);
            tvInputValue.setText(CommonActivity.formatJsonString(logMethodBean.getInputValue()));

            tvResultValue.setTextIsSelectable(true);
            tvResultValue.setText(CommonActivity.formatJsonString(logMethodBean.getResultValue()));
        }
    }

    @Override
    public void setPermission() {

    }

    @OnClick(R.id.btnInfoGeneral)
    public void onBtnInfoGeneralClick(){
        expanInfoGeneral.toggle();
        if(expanInfoGeneral.isExpanded()){
            btnInfoGeneral.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float,0,0,0);
        } else {
            btnInfoGeneral.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float,0,0,0);
        }
    }

    @OnClick(R.id.btnInputValue)
    public void onBtnInputValueClick(){
        expanInputValue.toggle();
        if(expanInputValue.isExpanded()){
            btnInputValue.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float,0,0,0);
        } else {
            btnInputValue.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float,0,0,0);
        }
    }

    @OnClick(R.id.btnResultValue)
    public void onBtnResultValueClick(){
        expanResultValue.toggle();
        if(expanResultValue.isExpanded()){
            btnResultValue.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float,0,0,0);
        } else {
            btnResultValue.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float,0,0,0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.logMethodInfoDetail);
    }
}
