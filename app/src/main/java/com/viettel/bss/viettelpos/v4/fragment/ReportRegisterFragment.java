package com.viettel.bss.viettelpos.v4.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ReportRegisterDetailAdapter;
import com.viettel.bss.viettelpos.v4.bo.ReportProfileBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.ReportRegisterDetailDialogFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.task.AsynTaskReportProfileDetail;
import com.viettel.bss.viettelpos.v4.task.AsynTaskReportProfileTotal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportRegisterFragment extends FragmentCommon {

    @BindView(R.id.edtFromDate)
    EditText edtFromDate;
    @BindView(R.id.edtToDate)
    EditText edtToDate;
    @BindView(R.id.spnStatus)
    Spinner spnStatus;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lnStatus)
    LinearLayout lnStatus;
    @BindView(R.id.rbDetail)
    RadioButton rbDetail;
    @BindView(R.id.lnResultDetail)
    LinearLayout lnResultDetail;
    @BindView(R.id.lnResultTotal)
    LinearLayout lnResultTotal;

    @BindView(R.id.tvNumDKTT)
    TextView tvNumDKTT;
    @BindView(R.id.tvNumSubProfileValid)
    TextView tvNumSubProfileValid;
    @BindView(R.id.tvNumProfileNotExpire)
    TextView tvNumProfileNotExpire;
    @BindView(R.id.tvNumProfileExpire)
    TextView tvNumProfileExpire;
    @BindView(R.id.tvNumSubCheckInvalid)
    TextView tvNumSubCheckInvalid;
    @BindView(R.id.tvNumSubNotCheck)
    TextView tvNumSubNotCheck;


    ReportRegisterDetailAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_report_register;
    }

    @Override
    protected void unit(View v) {
        new DateTimeDialogWrapper(edtFromDate, getActivity());
        edtFromDate.setText(DateTimeUtils.getFirstDateOfMonth());

        new DateTimeDialogWrapper(edtToDate, getActivity());

        ArrayAdapter<String> spnArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.report_register_profile_status));
        spnArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(spnArrayAdapter);
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.report_register);
    }

    @OnClick(R.id.btnSearch)
    public void btnSearchOnClick(){
        resetForm();
        if(!DateTimeUtils.validateData(getActivity(), edtFromDate, edtToDate, 31)){
            return;
        }

        if(rbDetail.isChecked()) {
            new AsynTaskReportProfileDetail(getActivity(), new OnPostExecuteListener<List<ReportProfileBO>>() {
                @Override
                public void onPostExecute(List<ReportProfileBO> result, String errorCode, String description) {
                    if(!CommonActivity.isNullOrEmpty(result)) {
                        lnResultDetail.setVisibility(View.VISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        adapter = new ReportRegisterDetailAdapter(getActivity(), result, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object item) {
                                ReportProfileBO reportProfileBO = (ReportProfileBO) item;
                                ReportRegisterDetailDialogFragment fragment = ReportRegisterDetailDialogFragment.newInstance(reportProfileBO);
                                fragment.show(getFragmentManager(), "Dialog");
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        CommonActivity.toast(getActivity(), getString(R.string.no_data));
                    }
                }
            }, moveLogInAct, edtFromDate.getText().toString(), edtToDate.getText().toString(), "04", getLstStatus()).execute();
        } else {
            new AsynTaskReportProfileTotal(getActivity(), new OnPostExecuteListener<List<ReportProfileBO>>() {
                @Override
                public void onPostExecute(List<ReportProfileBO> result, String errorCode, String description) {
                    if(!CommonActivity.isNullOrEmpty(result)){
                        lnResultTotal.setVisibility(View.VISIBLE);

//                        Long total = DataUtils.safeToLong(result.get(0).getNotCheck()) + DataUtils.safeToLong(result.get(0).getCheckValid())
//                                + DataUtils.safeToLong(result.get(0).getCheckInvalid());
                        tvNumDKTT.setText(getString(R.string.number_sub_register_param, StringUtils.formatMoney(String.valueOf(result.get(0).getTotalProfile()))));
                        tvNumSubProfileValid.setText(getString(R.string.number_sub_check_profile_param, StringUtils.formatMoney(String.valueOf(result.get(0).getCheckValid()))));
                        tvNumProfileNotExpire.setText(getString(R.string.number_check_profile_valid_param, StringUtils.formatMoney(String.valueOf(result.get(0).getCheckValidNotExpire()))));
                        tvNumProfileExpire.setText(getString(R.string.number_check_profile_invalid_param, StringUtils.formatMoney(String.valueOf(result.get(0).getCheckValidExpire()))));

                        tvNumSubCheckInvalid.setText(getString(R.string.number_profile_check_invalid_param, StringUtils.formatMoney(String.valueOf(result.get(0).getCheckInvalid()))));
                        tvNumSubNotCheck.setText(getString(R.string.number_profile_not_check_param, StringUtils.formatMoney(String.valueOf(result.get(0).getNotCheck()))));
                    } else {
                        CommonActivity.toast(getActivity(), getString(R.string.no_data));
                    }
                }
            }, moveLogInAct, edtFromDate.getText().toString(), edtToDate.getText().toString(), "04").execute();
        }
    }

    private List<String> getLstStatus(){
        List<String> lstStatus = new ArrayList<>();
        if(spnStatus.getSelectedItemPosition() == 0){ //truong hop chon tat ca
            lstStatus.add("1");
            lstStatus.add("2");
            lstStatus.add("3");
            lstStatus.add("4");
            lstStatus.add("7");
            lstStatus.add("8");
            lstStatus.add("9");
            lstStatus.add("11");
        } else if (spnStatus.getSelectedItemPosition() == 1){//truong hop da tich sai
            lstStatus.add("7");
            lstStatus.add("8");
            lstStatus.add("9");
        } else if (spnStatus.getSelectedItemPosition() == 2){ //truong hop tich dung
            lstStatus.add("4");
        } else if (spnStatus.getSelectedItemPosition() == 3){ //truong hop chua kiem tra
            lstStatus.add("1");
            lstStatus.add("2");
            lstStatus.add("3");
            lstStatus.add("11");
        }
        return lstStatus;
    }

    @OnClick(R.id.rbDetail)
    public void rbDetailOnClick(){
        lnStatus.setVisibility(View.VISIBLE);
        resetForm();
    }

    @OnClick(R.id.rbTotal)
    public void rbTotalOnClick(){
        lnStatus.setVisibility(View.GONE);
        resetForm();
    }

    private void resetForm(){
        lnResultDetail.setVisibility(View.GONE);
        lnResultTotal.setVisibility(View.GONE);
    }


}
