package com.viettel.bss.viettelpos.v4.report.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.report.asyn.AsynGetLstActionMBCCS;
import com.viettel.bss.viettelpos.v4.report.asyn.AsynGetReportMBcss;
import com.viettel.bss.viettelpos.v4.report.asyn.AsynLookupLogMBCCS;
import com.viettel.bss.viettelpos.v4.report.object.InputBean;
import com.viettel.bss.viettelpos.v4.report.object.LogMethodBean;
import com.viettel.bss.viettelpos.v4.ui.DateTime;
import com.viettel.bss.viettelpos.v4.ui.DateTimePicker;
import com.viettel.bss.viettelpos.v4.ui.SimpleDateTimePicker;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLookupLogMBCCS extends FragmentCommon {

    private static final String TAG = "FragmentLookupLogMBCCS";
    private static final int count = 40;

    @BindView(R.id.edtFromDate)
    EditText edtFromDate;
    @BindView(R.id.edtToDate)
    EditText edtToDate;
//    @BindView(R.id.spinnerAction)
//    Spinner spinnerAction;
    @BindView(R.id.edtUserCall)
    EditText edtUserCall;
    @BindView(R.id.edtInputValue)
    EditText edtInputValue;
    @BindView(R.id.edtResultValue)
    EditText edtResultValue;
    @BindView(R.id.recyclerViewTrans)
    RecyclerView recyclerView;
    @BindView(R.id.tvTotalRecord)
    TextView tvTotalRecord;
    @BindView(R.id.actxtAction)
    AutoCompleteTextView actxtAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_lookup_log_mbccs;
    }


    @Override
    public void unit(View v) {
        new AsynGetLstActionMBCCS(getActivity(), actxtAction).execute();
        actxtAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actxtAction.showDropDown();
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -30);

        edtFromDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy HH:mm"));
        edtToDate.setText(DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy HH:mm"));
        edtResultValue.setText("exception");

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void setPermission() {

    }


    @OnClick(R.id.btnSearch)
    public void search() {
        if(!DateTimeUtils.validateData(getContext(), edtFromDate, edtToDate, 30)){
            return;
        }

        this.recyclerView.setVisibility(View.GONE);
        this.tvTotalRecord.setVisibility(View.GONE);

        new AsynLookupLogMBCCS(this, recyclerView, tvTotalRecord, initInputBean(), onItemClickListener).execute();
    }

    private final com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener = new com.viettel.bss.viettelpos.v4.listener.OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            FragmentShowDetailLog fragment = new FragmentShowDetailLog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("logMethodBean", (LogMethodBean) item);
            fragment.setArguments(bundle);

            ReplaceFragment.replaceFragment(getActivity(), fragment,
                    true);
        }
    };

    @OnClick(R.id.btnReport)
    public void report() {
        new AsynGetReportMBcss(getActivity(), initInputBean()).execute();
    }

    private InputBean initInputBean() {
        InputBean inputBean = new InputBean();
        inputBean.setFromDate(edtFromDate.getText().toString());
        inputBean.setToDate(edtToDate.getText().toString());
        inputBean.setUserCall(edtUserCall.getText().toString().trim());
        inputBean.setInputValue(edtInputValue.getText().toString().trim());
        inputBean.setResultValue(edtResultValue.getText().toString().trim());
        String methodName = actxtAction.getText().toString();
        if(!CommonActivity.isNullOrEmpty(methodName)) {
            if(methodName.contains("-")) {
                inputBean.setMethodName(methodName.split("-")[0]);
            } else {
                inputBean.setMethodName(methodName);
            }
        }
        inputBean.setPage(0);
        inputBean.setCount(count);
        return inputBean;
    }

    @OnClick(R.id.edtFromDate)
    public void onClickFromDate() {
        SimpleDateTimePicker
                .make(getString(R.string.fromDate), DateTimeUtils.convertStringToTime(edtFromDate.getText().toString(), "dd/MM/yyyy HH:mm"), new DateTimePicker.OnDateTimeSetListener() {

                    @Override
                    public void DateTimeSet(Date date) {
                        // TODO Auto-generated method stub
                        DateTime mDateTime = new DateTime(date);
                        edtFromDate.setText(mDateTime.getDateString("dd/MM/yyyy") + " " + mDateTime.getDateString("HH:mm"));
                    }
                }, getFragmentManager()).show();
    }

    @OnClick(R.id.edtToDate)
    public void onClickToDate() {
        SimpleDateTimePicker
                .make(getString(R.string.toDate), DateTimeUtils.convertStringToTime(edtToDate.getText().toString(), "dd/MM/yyyy HH:mm"), new DateTimePicker.OnDateTimeSetListener() {

                    @Override
                    public void DateTimeSet(Date date) {
                        // TODO Auto-generated method stub
                        DateTime mDateTime = new DateTime(date);
                        edtToDate.setText(mDateTime.getDateString("dd/MM/yyyy") + " " + mDateTime.getDateString("HH:mm"));
                    }
                }, getFragmentManager()).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.lookup_log_mbccs);
    }
}
