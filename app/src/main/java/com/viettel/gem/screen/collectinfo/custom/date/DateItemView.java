package com.viettel.gem.screen.collectinfo.custom.date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.common.Constants;
import com.viettel.gem.utils.DialogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by BaVV on 5/31/16.
 */
public class DateItemView
        extends CustomView {

    //CUST_EXP_CDT

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.imvClear)
    ImageView imvClear;

    String mCode;

    String mDateValue;

    int mValueType;

    int mYear = -1;
    int mMonth = -1;
    int mDay = -1;

    Calendar maxCal;

    private Callback mCallback;

    public DateItemView(Context context) {
        super(context);
    }

    public DateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_date_box_item_view;
    }

    @Override
    public boolean validateView() {
        return !tvDate.getText().toString().trim().isEmpty();
    }

    public void build(String code, int valueType, String date, Callback callback) {
        mCode = code;
        mDateValue = date;
        mValueType = valueType;
        mCallback = callback;

        tvDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handleClearUI();
            }
        });

        tvDate.setText(date);

        initDate();
    }

    void initDate() {
        Calendar cal = Calendar.getInstance();
        maxCal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 14;
        if (null != mDateValue) {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.formatDate, Locale.getDefault());
            try {
                cal.setTime(sdf.parse(mDateValue));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        if (mValueType == Constants.BIRTHDAY)
            if (mYear > year) mYear = year;

        maxCal.set(Calendar.YEAR, mYear);
    }

    void handleClearUI() {
        if("CUST_EXP_CDT".equals(mCode)) {
            imvClear.setVisibility(tvDate.getText().toString().trim().isEmpty() ? GONE : VISIBLE);
        } else {
            imvClear.setVisibility(GONE);
        }
    }

    @OnClick(R.id.imvClear)
    void clearDate() {
        DialogUtils.showAlertAction(getContext(), R.string.msg_clear_date, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvDate.setText("");
                if (null != mCallback) {
                    mCallback.onDateChanged(null);
                }
            }
        });
    }

    @OnClick(R.id.layoutDate)
    void showDate() {
        if (mDay == -1 || mMonth == -1 || mYear == -1) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePicker = new FixedHoloDatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat(Constants.formatDate, Locale.getDefault());
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String date = format.format(calendar.getTime());
                tvDate.setText(date);
                if (null != mCallback) {
                    mCallback.onDateChanged(date);
                }
            }
        }, mYear, mMonth, mDay);

        if (mValueType == Constants.BIRTHDAY)
            datePicker.getDatePicker().setMaxDate(maxCal.getTime().getTime());

        datePicker.show();

        /*DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat(Constants.formatDate, Locale.getDefault());
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = format.format(calendar.getTime());
                        tvDate.setText(date);
                        if (null != mCallback) {
                            mCallback.onDateChanged(date);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();*/
    }

    public String getInput() {
        return tvDate.getText().toString().trim();
    }

    public interface Callback {
        void onDateChanged(String text);
    }
}
