package com.viettel.bss.viettelpos.v4.staff.warning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseActivity;
import com.viettel.bss.viettelpos.v4.commons.Session;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NHAT on 30/05/2017.
 */

public class AlarmActivity extends BaseActivity {
    public static final String TAG = AlarmActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 100;
    @BindView(R.id.rv_list)
    protected RecyclerView mRVList;
    @BindView(R.id.swiperefresh)
    protected SwipeRefreshLayout mRefresh;

    private LinearLayoutManager mManager;
    private AlarmAdapter mAdapter;
    private AlarmPresenter mPresenter;
    private LinkedHashMap<Long, AlarmObj.Data> mDatas;
    private int mIndex;
    private int mPosition;
    private boolean mIsRefresh;
    private boolean mIsRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.alarm_title));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mRefresh.setOnRefreshListener(mRefreshListener);
        mManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, mManager.getOrientation());
        mAdapter = new AlarmAdapter(R.layout.item_staff_alarm);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mRVList.setLayoutManager(mManager);
        mRVList.addItemDecoration(divider);
        mRVList.setAdapter(mAdapter);
        mRVList.addOnScrollListener(mScrollListener);

        mPresenter = new AlarmPresenterImpl(this, this);
        mIndex = 0;
        mDatas = new LinkedHashMap<>();
        sendRequest(false);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_staff_alarm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                AlarmObj.Data obj = (AlarmObj.Data) data.getSerializableExtra(TAG);
                if (obj != null) {
                    mDatas.put(obj.getAlarmId(), obj);
                    mAdapter.changeItem(obj, mPosition);
                }
            }
        }
    }

    //TODO : === Request ===
    private void sendRequest(boolean isRefresh) {
        if (!mIsRequest && !TextUtils.isEmpty(Session.getToken())) {
            String staffCode = null;
            if (Session.loginUser != null && !TextUtils.isEmpty(Session.loginUser.getStaffCode())) {
                staffCode = Session.loginUser.getStaffCode();
            }
            if (!TextUtils.isEmpty(staffCode)) {
                mIsRequest = true;
                mIsRefresh = isRefresh;
                mPresenter.requestAlarmForStaff(staffCode, mIndex);
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
                List<AlarmObj.Data> list = (List<AlarmObj.Data>) o;
                for (AlarmObj.Data data : list) {
                    if (data != null) {
                        mDatas.put(data.getAlarmId(), data);
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
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mIndex = 0;
            sendRequest(true);
        }
    };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                AlarmObj.Data data = (AlarmObj.Data) view.getTag();
                if (data != null) {
                    mPosition = i;
                    Intent intent = new Intent(AlarmActivity.this, CauseActivity.class);
                    intent.putExtra(CauseActivity.TAG, data);
                    startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}