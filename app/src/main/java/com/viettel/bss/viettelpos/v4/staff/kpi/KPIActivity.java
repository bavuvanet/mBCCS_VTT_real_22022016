package com.viettel.bss.viettelpos.v4.staff.kpi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseActivity;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.staff.warning.AlarmActivity;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NHAT on 30/05/2017.
 */
public class KPIActivity extends BaseActivity {
    public static final String TAG = KPIActivity.class.getSimpleName();
    private final int SIZE = 12;
    @BindView(R.id.sp_month)
    protected AppCompatSpinner mSpMonth;
    @BindView(R.id.rv_list)
    protected RecyclerView mRVList;
    @BindView(R.id.swiperefresh)
    protected SwipeRefreshLayout mRefresh;

    private LinearLayoutManager mManager;
    private KPIAdapter mAdapter;
    private KPIPresenter mPresenter;
    private LinkedHashMap<String, KPIObj.Data> mDatas;
    private int mIndex;
    private String mMonth;
    private boolean mIsRefresh;
    private boolean mIsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.kpi_title));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mRefresh.setOnRefreshListener(mRefreshListener);
        mManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, mManager.getOrientation());
        mAdapter = new KPIAdapter(R.layout.item_staff_kpi);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mRVList.setLayoutManager(mManager);
        mRVList.addItemDecoration(divider);
        mRVList.setAdapter(mAdapter);
        mRVList.addOnScrollListener(mScrollListener);

        mSpMonth.setOnItemSelectedListener(mSpListener);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_center_item, getListMonth(SIZE));
        dataAdapter.setDropDownViewResource(R.layout.spinner_center_dropdown_item);
        mSpMonth.setAdapter(dataAdapter);
        mPresenter = new KPIPresenterImpl(this, this);
        mIndex = 0;
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        mMonth = String.format("%02d/%d", month, year);
        mDatas = new LinkedHashMap<>();
        sendRequest(false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_staff_kpi;
    }

    //TODO : === Request ===
    private void sendRequest(boolean isRefresh) {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken())) {
//            String staffCode = null;
            String staffCode = "NVA1";
            if (Session.loginUser != null && !TextUtils.isEmpty(Session.loginUser.getStaffCode())) {
                staffCode = Session.loginUser.getStaffCode();
            }
            if (!TextUtils.isEmpty(mMonth) && !TextUtils.isEmpty(staffCode)) {
                mIsRequest = true;
                mIsRefresh = isRefresh;
                mPresenter.requestKpiForStaff(staffCode, mMonth, null, mIndex);
            }
        }
    }

    @Override
    public void onSuccess(Object o) {
        if (mIndex == 0) {
            mDatas.clear();
        }
        if (o != null) {
            try {
                List<KPIObj.Data> list = (List<KPIObj.Data>) o;
                if (list != null) {
                    for (KPIObj.Data data : list) {
                        if (data != null && !TextUtils.isEmpty(data.getKpiCode())) {
                            mDatas.put(data.getKpiCode(), data);
                        }
                    }
                    mAdapter.setDatas(new ArrayList<>(mDatas.values()));
                    mIndex++;
                }
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
        if (!mIsRefresh)
            super.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        if (!mIsRefresh)
            super.dismissProgressDialog();
        else
            mRefresh.setRefreshing(false);
    }

    //TODO : === Listener ===
    private AdapterView.OnItemSelectedListener mSpListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mMonth = (String) adapterView.getItemAtPosition(i);
            mDatas.clear();
            mAdapter.setDatas(new ArrayList<>(mDatas.values()));
            sendRequest(false);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mIndex = 0;
            sendRequest(true);
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
                    Log.out(TAG, "scroll : " + totalItemCount);
                    sendRequest(false);
                }
            }
        }
    };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
//                KPIObj.Data data = (KPIObj.Data) view.getTag();
//                Intent intent = new Intent(KPIActivity.this, WorkListActivity.class);
//                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private List<String> getListMonth(int size) {
        List<String> list = new ArrayList<String>();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        for (int i = 0; i < size; i++) {
            list.add(String.format("%02d/%d", month, year));
            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
        }
        return list;
    }
}