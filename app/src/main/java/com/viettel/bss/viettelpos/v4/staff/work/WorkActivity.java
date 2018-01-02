package com.viettel.bss.viettelpos.v4.staff.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ICHI on 6/3/2017.
 */

public class WorkActivity extends BaseActivity {
    public static final String TAG = WorkActivity.class.getSimpleName();
    @BindView(R.id.tv_name)
    protected TextView mTVName;
    @BindView(R.id.tv_assign)
    protected TextView mTVAssign;
    @BindView(R.id.tv_time_from_date)
    protected TextView mTVTimeStart;
    @BindView(R.id.tv_time_to_date)
    protected TextView mTVTimeEnd;
    @BindView(R.id.tv_content)
    protected TextView mTVContent;
    @BindView(R.id.edt_reason)
    protected EditText mEdtReason;
    @BindView(R.id.edt_solution)
    protected EditText mEdtSolution;
    @BindView(R.id.sp_status)
    protected AppCompatSpinner mSpStatus;
    @BindView(R.id.btn_update)
    Button btn_update;

    private WorkListObj.Data mWork;
    private WorkPresenter mPresenter;
    private boolean mIsRequest;
    private List<Integer> mStatus;
    private int mStatusUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        if (intent != null) {
            mWork = (WorkListObj.Data) intent.getSerializableExtra(TAG);
        }
        if (mWork != null) {
            setData();
        }
        mPresenter = new WorkPresenterImpl(this, this);
//        if (mWork != null) {
//            switch (mWork.getStatus()) {
//                case 0:
//                    mSpStatus.setSelection(0);
//                    break;
//                case 1:
//                    mSpStatus.setSelection(2);
//                    break;
//                case 5:
//                    mSpStatus.setSelection(4);
//                    break;
//            }
//        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_staff_work_detail;
    }

    //TODO : === Request ===
    private void requestUpdateWork() {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken()) && mWork != null) {
            final String workID = mWork.getWorkId() + "";
            final String reason = mEdtReason.getText().toString();
            final String solution = mEdtSolution.getText().toString();
            if (!TextUtils.isEmpty(workID) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(solution)) {
                CommonActivity.createDialog(this, getString(R.string.confirm_update_job),
                        getString(R.string.app_name),
                        getString(R.string.cancel),
                        getString(R.string.ok)
                        , null,
                         new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mIsRequest = true;
                                mPresenter.requestUpdateWork(workID, mStatusUpdate + "", reason, solution, "");
                            }
                        }).show();

            } else {
                CommonActivity.toast(this, getString(R.string.data_required));
            }
        }
    }

    @Override
    public void onSuccess(Object o) {
        try {
            showToat(getString(R.string.msg_cause_update_success));
            returnIntent();
        } catch (Exception e) {
            onError(null);
            e.printStackTrace();
        }
        mIsRequest = false;
    }

    private void returnIntent(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(WorkListActivity.class.getSimpleName(), mWork);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
            Toast.makeText(WorkActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemSelectedListener mSpListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mStatusUpdate = mStatus.get(mSpStatus.getSelectedItemPosition());
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
        requestUpdateWork();
    }

    private void setData() {
        try {
            if (mWork != null) {
                mTVName.setText(mWork.getJobCode());
                mTVAssign.setText(getString(R.string.staff_work_assign, mWork.getWorkAssign()));
                mTVTimeStart.setText(getString(R.string.staff_work_from_date, mWork.getStartTime()));
                mTVTimeEnd.setText(getString(R.string.staff_work_to_date, mWork.getEndTime()));
                mTVContent.setText(getString(R.string.staff_work_content, mWork.getAlarm().getAlarmMsg()));
                mStatus = mWork.getTransStatus();
                if(!CommonActivity.isNullOrEmpty(mStatus)) {
                    String[] arr = getResources().getStringArray(R.array.staff_work_status_name);
                    List<String> spData = new ArrayList<>();
                    for (int status : mStatus) {
                        spData.add(arr[status - 1]);
                    }
                    mSpStatus.setOnItemSelectedListener(mSpListener);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spData);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpStatus.setAdapter(dataAdapter);
                    if (mStatus.size() > 0) {
                        mStatusUpdate = mStatus.get(0);
                    } else {
                        mStatusUpdate = mWork.getStatus();
                    }
                } else {
                    btn_update.setVisibility(View.GONE);
                    mSpStatus.setEnabled(false);
                }
                mEdtReason.setText(mWork.getReason());
                mEdtSolution.setText(mWork.getSolution());
            }

            mEdtReason.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mEdtReason.setRawInputType(InputType.TYPE_CLASS_TEXT);

            mEdtSolution.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mEdtSolution.setRawInputType(InputType.TYPE_CLASS_TEXT);
        } catch (Exception e) {
        }
    }
}