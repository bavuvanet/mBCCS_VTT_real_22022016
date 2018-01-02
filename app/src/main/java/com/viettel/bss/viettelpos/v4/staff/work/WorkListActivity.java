package com.viettel.bss.viettelpos.v4.staff.work;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by NHAT on 30/05/2017.
 */

public class WorkListActivity extends BaseActivity {
    public static final String TAG = WorkListActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 100;
    @BindView(R.id.rv_list)
    protected RecyclerView mRVList;
    @BindView(R.id.tv_from_date)
    protected TextView mTVFromDate;
    @BindView(R.id.tv_to_date)
    protected TextView mTVToDate;
    @BindView(R.id.sp_status)
    protected AppCompatSpinner mSpStatus;
    @BindView(R.id.lnResult)
    protected LinearLayout lnResult;
    @BindView(R.id.edtWoCode)
    protected EditText edtWoCode;

    private LinearLayoutManager mManager;
    private WorkListAdapter mAdapter;
    private WorkListPresenter mPresenter;
    private LinkedHashMap<Long, WorkListObj.Data> mDatas;
    private int mIndex;
    private int mPosition;
    private boolean mIsRequest;
    private int mAssignType = 0;
    private long mFromDate = 0;
    private long mToDate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.staff_work_list_title));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        List<String> spData = Arrays.asList(getResources().getStringArray(R.array.staff_work_status_name));
        mSpStatus.setOnItemSelectedListener(mSpListener);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpStatus.setAdapter(dataAdapter);
        mManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, mManager.getOrientation());
        mAdapter = new WorkListAdapter(R.layout.item_staff_work_list);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mRVList.setLayoutManager(mManager);
        mRVList.addItemDecoration(divider);
        mRVList.setAdapter(mAdapter);
        mRVList.addOnScrollListener(mScrollListener);

        initDate();
        mPresenter = new WorkListPresenterImpl(this, this);
        mIndex = 0;
        mDatas = new LinkedHashMap<>();
        //TEST
        //createTest();
        mAdapter.setDatas(new ArrayList<>(mDatas.values()));
//        sendRequest(false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_staff_work_list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                WorkListObj.Data obj = (WorkListObj.Data) data.getSerializableExtra(TAG);
                if (obj != null) {
                    mDatas.put(obj.getWorkId(), obj);
                    mAdapter.removeItem(mPosition);
                }
            }
        }
    }

    //TODO : === Request ===
    private void sendRequest(boolean isRefresh) {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken())) {
            if (isRefresh) {
                mIndex = 0;
                mDatas.clear();
                mAdapter.notifyDataSetChanged();
            }
            mIsRequest = true;
            mPresenter.requestWorkList(mIndex, mAssignType, mFromDate, mToDate, edtWoCode.getText().toString().trim());
        }
    }

    @Override
    public void onSuccess(Object o) {
        if (mIndex == 0) {
            mDatas.clear();
        }
        if (o != null) {
            try {
                lnResult.setVisibility(View.VISIBLE);
                List<WorkListObj.Data> list = (List<WorkListObj.Data>) o;
                for (WorkListObj.Data data : list) {
                    if (data != null) {
                        mDatas.put(data.getWorkId(), data);
                    }
                }
                mAdapter.setDatas(new ArrayList<>(mDatas.values()));
                mIndex++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mIsRequest = false;
    }

    @Override
    public void onError(Object o) {
        mIsRequest = false;
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    //TODO : === Listener ===
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                WorkListObj.Data data = (WorkListObj.Data) view.getTag();
                if (data != null) {
                    mPosition = i;
                    Intent intent = new Intent(WorkListActivity.this, WorkActivity.class);
                    intent.putExtra(WorkActivity.TAG, data);
                    startActivityForResult(intent, REQUEST_CODE);
//                    startActivityForResult(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                int visibleItemCount = mManager.getChildCount();
                int totalItemCount = mManager.getItemCount();
                int pastVisiblesItems = mManager.findFirstVisibleItemPosition();
                if (!mIsRequest && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    sendRequest(false);
                }
            }
        }
    };

    @OnClick(R.id.ll_from_date)
    public void clickFromDate(View view) {
        String date = mTVFromDate.getText().toString();
        dialogDate(fromDateListener, date);
    }

    @OnClick(R.id.ll_to_date)
    public void clickToDate(View view) {
        String date = mTVToDate.getText().toString();
        dialogDate(toDateListener, date);
    }

    private DatePickerDialog mDialog;
    private DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            String date = String.format("%02d-%02d-%04d 00:00", dayOfMonth, monthOfYear + 1, year);
            mFromDate = setDate(mTVFromDate, date);
        }
    };

    private DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            String date = String.format("%02d-%02d-%04d 23:59", dayOfMonth, monthOfYear + 1, year);
            mToDate = setDate(mTVToDate, date);
        }
    };

    private long setDate(TextView tv, String date) {
        Date d = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            d = sdf.parse(date);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            tv.setText(sdf2.format(d));
            return d.getTime();
        } catch (Exception e) {
        }
        return 0;
    }

    private void dialogDate(DatePickerDialog.OnDateSetListener listener, String date) {
        int year = 0;
        int month = 0;
        int day = 0;
        Calendar c = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date d = sdf.parse(date);
                c.setTime(d);
            } catch (Exception e) {
            }
        }
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        if (mDialog == null || !mDialog.isShowing()) {
            mDialog = new DatePickerDialog(WorkListActivity.this, listener, year, month, day);
            mDialog.show();
        }
    }

    private AdapterView.OnItemSelectedListener mSpListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mAssignType = i + 1;
            lnResult.setVisibility(View.GONE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    @OnClick(R.id.btn_search)
    public void onSearch(View view) {
        lnResult.setVisibility(View.GONE);
        sendRequest(true);
    }

    private void initDate() {
        String date = DateTimeUtils.getFirstDateOfMonth("dd-MM-yyyy") + " 00:00";
        mFromDate = setDate(mTVFromDate, date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy 23:59");
        date = sdf.format(new Date());
        mToDate = setDate(mTVToDate, date);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    void createTest() {
        WorkListObj.Data data = new WorkListObj.Data();
        data.setWorkId(1);
        data.setWorkName("WO điều hành bán sim 3G child 4");
        data.setStartTime("2017-07-06");
        data.setEndTime("2017-07-11");
        data.setWorkAssign("System");
        data.setContent("Phat trien 200 thue bao 3G");
        data.setStatus(1);
        mDatas.put(1l, data);
        data = new WorkListObj.Data();
        data.setWorkId(2);
        data.setWorkName("WO điều hành bán sim 3G child 1");
        data.setStartTime("2017-07-02");
        data.setEndTime("2017-07-14");
        data.setWorkAssign("System");
        data.setContent("Phat trien 200 thue bao 4G");
        data.setStatus(1);
        mDatas.put(2l, data);
        data = new WorkListObj.Data();
        data.setWorkId(3);
        data.setWorkName("WO điều hành bán sim 3G child 2");
        data.setStartTime("2017-07-03");
        data.setEndTime("2017-07-14");
        data.setWorkAssign("System");
        data.setContent("Phat trien 200 thue bao 3G");
        data.setStatus(1);
        mDatas.put(3l, data);
    }
}