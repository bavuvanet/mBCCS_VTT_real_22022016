package com.viettel.bss.viettelpos.v4.dialog;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.ReportProfileBO;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportRegisterDetailDialogFragment extends DialogFragment {

    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.tvIdNo)
    TextView tvIdNo;
    @BindView(R.id.tvIssueDate)
    TextView tvIssueDate;
    @BindView(R.id.tvIssuePlace)
    TextView tvIssuePlace;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvBirthDay)
    TextView tvBirthDay;
    @BindView(R.id.tvRegisterDate)
    TextView tvRegisterDate;
    @BindView(R.id.tvActiveDate)
    TextView tvActiveDate;
    @BindView(R.id.tvProfileStatus)
    TextView tvProfileStatus;
    @BindView(R.id.tvIsdn)
    TextView tvIsdn;
    @BindView(R.id.tvCustType)
    TextView tvCustType;
    @BindView(R.id.tvSubType)
    TextView tvSubType;
    @BindView(R.id.tvShopName)
    TextView tvShopName;
    @BindView(R.id.tvStaffChecked)
    TextView tvStaffChecked;
    @BindView(R.id.tvCheckedDatetime)
    TextView tvCheckedDatetime;
    @BindView(R.id.tvCountCheckAgain)
    TextView tvCountCheckAgain;

    public static ReportRegisterDetailDialogFragment newInstance(ReportProfileBO reportProfileBO){
        ReportRegisterDetailDialogFragment fragment = new ReportRegisterDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("reportProfileBO", reportProfileBO);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_register_detail_dialog, container, false);
        ButterKnife.bind(this, v);

        ReportProfileBO reportProfileBO = (ReportProfileBO) getArguments().getSerializable("reportProfileBO");
        tvIsdn.setText(getString(R.string.isdn_param, reportProfileBO.getIsdnAccount()));
        tvCustomerName.setText(getString(R.string.cust_name_param, DataUtils.safeToString(reportProfileBO.getCusName())));
        tvIdNo.setText(getString(R.string.ido_lst, DataUtils.safeToString(reportProfileBO.getIdNo())));
        tvIssueDate.setText(getString(R.string.issue_date_param, DateTimeUtils.convertDateSoap(reportProfileBO.getIssueDate())));
        tvIssuePlace.setText(getString(R.string.issue_place_param, DataUtils.safeToString(reportProfileBO.getIssuePlace())));
        tvAddress.setText(getString(R.string.txt_dia_chi, DataUtils.safeToString(reportProfileBO.getCusAddress())));
        tvBirthDay.setText(getString(R.string.txt_ngay_sinh, ""));
        tvRegisterDate.setText(getString(R.string.register_date_param, DateTimeUtils.convertDateSoapToString(reportProfileBO.getActionDate())));
        tvActiveDate.setText(getString(R.string.active_date_param, DateTimeUtils.convertDateSoapToString(reportProfileBO.getStaDatetime())));
        tvProfileStatus.setText(getString(R.string.profile_status_param, DataUtils.safeToString(reportProfileBO.getDescTypeStatus())));
        tvCustType.setText(getString(R.string.cust_type_param, DataUtils.safeToString(reportProfileBO.getCusType())));
        tvSubType.setText(getString(R.string.sub_type_param, DataUtils.safeToString(reportProfileBO.getSubType())));
        tvShopName.setText(getString(R.string.shop_manage_param, DataUtils.safeToString(reportProfileBO.getShopName())));
        tvStaffChecked.setText(getString(R.string.staff_checked_param, DataUtils.safeToString(reportProfileBO.getStaffChecked())));
        tvCheckedDatetime.setText(getString(R.string.checked_datetime_param, DateTimeUtils.convertDateSoapToString(reportProfileBO.getCheckedDatetime())));
        tvCountCheckAgain.setText(getString(R.string.count_check_again_param, DataUtils.safeToLong(reportProfileBO.getCountCheckAgain())));
        // Inflate the layout for this fragment
        return v;
    }

    @OnClick(R.id.btnAccept)
    public void btnAcceptOnClick(){
        getDialog().dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
