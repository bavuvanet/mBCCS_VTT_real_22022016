package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.TppTransDTOAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.TppTransDTO;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/25/2017.
 */

public class ResendCodeCardFragment extends FragmentCommon {

    @BindView(R.id.edtFromDate)
    EditText edtFromDate;
    @BindView(R.id.edtToDate)
    EditText edtToDate;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.lvCardInfo)
    ListView lvCardInfo;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private int fromYear;
    private int fromMonth;
    private int fromDay;
    private int toYear;
    private int toMonth;
    private int toDay;
    private Date dateFrom;
    private Date dateTo;
    private Date currentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.bankplus_resend_code_card_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.bankplus_resend_id_card));
    }

    @Override
    protected void unit(View v) {

        btnSearch.setOnClickListener(this);
        tvNoData.setVisibility(View.GONE);

        Calendar calDateFrom = Calendar.getInstance();
        dateFrom = calDateFrom.getTime();
        fromDay = calDateFrom.get(Calendar.DAY_OF_MONTH);
        fromMonth = calDateFrom.get(Calendar.MONTH);
        fromYear = calDateFrom.get(Calendar.YEAR);

        Calendar caltoDate = Calendar.getInstance();
        dateTo = caltoDate.getTime();
        toDay = caltoDate.get(Calendar.DAY_OF_MONTH);
        toMonth = caltoDate.get(Calendar.MONTH);
        toYear = caltoDate.get(Calendar.YEAR);
        String dateNowString = fromDay + "/" + (fromMonth + 1) + "/" + fromYear;

        new DateTimeDialogWrapper(edtFromDate, getActivity());
        edtFromDate.setText(DateTimeUtils.getFirstDateOfMonth());
        edtToDate.setText(dateNowString);
        new DateTimeDialogWrapper(edtToDate, getActivity());

        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNoData.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void setPermission() {

    }
//    private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            fromYear = selectedYear;
//            fromMonth = selectedMonth;
//            fromDay = selectedDay;
//            StringBuilder strDate = new StringBuilder();
//            if (fromDay < 10) {
//                strDate.append("0");
//            }
//            strDate.append(fromDay).append("/");
//            if (fromMonth < 9) {
//                strDate.append("0");
//            }
//            strDate.append(fromMonth + 1).append("/");
//            strDate.append(fromYear);
//            String fromDate = strDate.toString();
//
//            String dateFromString = String.valueOf(fromYear) + "-" +
//                    (fromMonth + 1) + "-" + fromDay;
//            try {
//                dateFrom = DATE_FORMAT.parse(dateFromString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            edtFromDate.setText(strDate);
//        }
//    };
//
//    private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            toYear = selectedYear;
//            toMonth = selectedMonth;
//            toDay = selectedDay;
//            StringBuilder strDate = new StringBuilder();
//            if (toDay < 10) {
//                strDate.append("0");
//            }
//            strDate.append(toDay).append("/");
//            if (toMonth < 9) {
//                strDate.append("0");
//            }
//            strDate.append(toMonth + 1).append("/");
//            strDate.append(toYear);
//            String toDate = strDate.toString();
//
//            String dateToString = String.valueOf(toYear) + "-" +
//                    (toMonth + 1) + "-" + toDay;
//            try {
//                dateTo = DATE_FORMAT.parse(dateToString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            edtToDate.setText(strDate);
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.edtFromDate:
//                DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
//                        getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, fromYear, fromMonth,
//                        fromDay);
//                fromDateDialog.show();
//
//                break;
//
//            case R.id.edtToDate:
//                DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
//                        AlertDialog.THEME_HOLO_LIGHT, toDatePickerListener, toYear, toMonth, toDay);
//                toDateDialog.show();
//                break;

            case R.id.btnSearch:

                //if (isValidateIsdn()) {
                    doRequestLstPinCodeTrans();
                //}

                break;
            default:
                break;
        }
    }

    private boolean isValidateIsdn() {
        if (CommonActivity.isNullOrEmpty(edtPhoneNumber)
                || !CommonActivity.validateIsdn(edtPhoneNumber.getText().toString())) {
            edtPhoneNumber.requestFocus();
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.bankplus_isdn_invalid,
                    R.string.app_name).show();
            return false;
        }
        return true;
    }

    private void doRequestLstPinCodeTrans() {
        if(!DateTimeUtils.validateData(getActivity(), edtFromDate, edtToDate, 30)){
            return;
        }

        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);

        data.append(BPConstant.COMMAND_GET_LST_PIN_CODE_TRANS)
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(StringUtils.formatIsdn(edtPhoneNumber.getText().toString()))
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(edtFromDate.getText().toString())
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(edtToDate.getText().toString())
                .append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), getActivity(),
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        // TODO Auto-generated method stub
                        if (result != null) {
                            final ArrayList<TppTransDTO> tppTransDTOs =
                                    new ArrayList<>(result.getLstTppTransDTOs());
                            TppTransDTOAdapter historyAdapter =
                                    new TppTransDTOAdapter(tppTransDTOs, getContext(), getActivity());
                            lvCardInfo.setAdapter(historyAdapter);

                            lvCardInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TppTransDTO tppTransDTO = tppTransDTOs.get(position);
                                }
                            });

                            if(tppTransDTOs.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, moveLogInAct).execute();
    }

}