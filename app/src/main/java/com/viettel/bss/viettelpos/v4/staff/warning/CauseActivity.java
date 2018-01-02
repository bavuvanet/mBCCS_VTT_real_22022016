package com.viettel.bss.viettelpos.v4.staff.warning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseActivity;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.connection.CommonObj;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ICHI on 6/3/2017.
 */

public class CauseActivity extends BaseActivity {
    public static final String TAG = CauseActivity.class.getSimpleName();
    @BindView(R.id.tv_name)
    protected TextView mTVName;
    @BindView(R.id.tv_code)
    protected TextView mTVCode;
    @BindView(R.id.tv_time_start)
    protected TextView mTVTimeStart;
    @BindView(R.id.tv_time_accrued)
    protected TextView mTVTimeAccrued;
    @BindView(R.id.tv_time_nok)
    protected TextView mTVTimeNok;
    @BindView(R.id.tv_content)
    protected TextView mTVContent;
    @BindView(R.id.edt_reason)
    protected EditText mEdtReason;
    @BindView(R.id.sp_cause)
    protected AppCompatSpinner mSpCause;

    private List<CauseObj.Data> mCauseList;
    private ArrayAdapter<String> mCauseAdapter;
    private CauseObj.Data mCause;
    private AlarmObj.Data mAlarm;
    private CausePresenter mPresenter;
    private boolean mIsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        if (intent != null) {
            mAlarm = (AlarmObj.Data) intent.getSerializableExtra(TAG);
        }
        if (mAlarm != null) {
            setData();
        }
        mSpCause.setOnItemSelectedListener(mSpListener);
        mPresenter = new CausePresenterImpl(this, this);
        requestCauseForStaff();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_staff_cause;
    }

    //TODO : === Request ===
    private void requestCauseForStaff() {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken())) {
            mIsRequest = true;
            mPresenter.requestCauseForStaff();
        }
    }

    private void requestUpdateAlarmForStaff() {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken()) && mCause != null && mAlarm != null) {
            String alarmID = mAlarm.getAlarmId() + "";
            String causeID = mCause.getCauseId() + "";
            String causeName = mCause.getCauseName();
            String content = mEdtReason.getText().toString();
            if (!TextUtils.isEmpty(causeName) && !TextUtils.isEmpty(content)) {
                mIsRequest = true;
                mPresenter.requestUpdateAlarmForStaff(alarmID, causeID, causeName, content);
            }
        }
    }

    @Override
    public void onSuccess(Object o) {
        try {
            if (((List) o).get(0) instanceof CauseObj.Data) {
                mCauseList = (List<CauseObj.Data>) o;
                if (mCauseList != null && mCauseList.size() > 0) {
                    List<String> causeName = new ArrayList<>();
                    for (CauseObj.Data obj : mCauseList) {
                        causeName.add(obj.getCauseName());
                    }
                    mCause = mCauseList.get(0);
                    mCauseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, causeName);
                    mCauseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpCause.setAdapter(mCauseAdapter);
                } else {
                    onError(R.string.msg_cause_reason_fail);
                }
            } else if (((List) o).get(0) instanceof AlarmObj.Data) {
                AlarmObj.Data data = ((List<AlarmObj.Data>) o).get(0);
                if (data != null) {
                    showToat(getString(R.string.msg_cause_update_success));
                    Intent intent = new Intent();
                    intent.putExtra(AlarmActivity.TAG, data);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    onError(R.string.msg_cause_update_fail);
                }
            } else {
                onError(null);
            }
        } catch (Exception e) {
            onError(null);
            e.printStackTrace();
        }
        mIsRequest = false;
    }

    @Override
    public void onError(Object o) {
        if (o != null) {
            if (o instanceof String) {
                showToat((String) o);
            } else if (o instanceof Integer) {
                showToat(getString((int) o));
            } else {
                showToat(getString(R.string.msg_cause_fail));
            }
        } else {
            showToat(getString(R.string.msg_cause_fail));
        }
        mIsRequest = false;
    }

    private void showToat(String msg) {
        if (!isFinishing() && msg != null) {
            Toast.makeText(CauseActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemSelectedListener mSpListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (mCauseList != null && i < mCauseList.size()) {
                mCause = mCauseList.get(i);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    @OnClick(R.id.btn_close)
    public void onClose() {
        finish();
    }

    @OnClick(R.id.btn_update)
    public void onUpdate(View view) {
        requestUpdateAlarmForStaff();
    }

    private void setData() {
        if (mAlarm != null) {
            mTVName.setText(mAlarm.getAlarmName());
            mTVCode.setText(getString(R.string.alarm_code, mAlarm.getKpiCode()));
            mTVTimeStart.setText(getString(R.string.alarm_time_start, mAlarm.getStartTime()));
            mTVTimeAccrued.setText(getString(R.string.alarm_time_accrued, mAlarm.getAccruedTime()));
            mTVTimeNok.setText(getString(R.string.alarm_time_nok, mAlarm.getCountDayNok()));
            mTVContent.setText(getString(R.string.alarm_content, mAlarm.getAlarmContent()));
            mEdtReason.setText(mAlarm.getContentResponse());
        }
    }
}