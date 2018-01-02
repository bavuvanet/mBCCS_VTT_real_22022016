package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.AdvisoryHistoryAdapter;
import com.viettel.bss.viettelpos.v4.advisory.data.HistoryConsultBean;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetHistoryConsultTVBH;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetSubscriberInfoTVBH;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Admin_pmvt on 7/1/2017.
 */

public class AdvisoryHistoryFragment extends FragmentCommon {

    @BindView(R.id.lvHistory)
    ListView lvHistory;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String isdn;
    private CCOutput ccOutput;
    private boolean isRequested;

    public AdvisoryHistoryFragment(String isdn) {
        super();
        this.isdn = isdn;
        this.isRequested = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.advisory_history_fragment;
    }

    @Override
    protected void unit(View v) {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    public void requestData() {
        if (isRequested) {
            return;
        }
        new AsynTaskGetHistoryConsultTVBH(getActivity(), new OnPostExecuteListener<CCOutput>() {
            @Override
            public void onPostExecute(CCOutput result, String errorCode, String description) {
                if(!CommonActivity.isNullOrEmpty(result)){
                    ccOutput = result;
                    refreshUI();
                    isRequested = true;
                } else {
                    CommonActivity.toast(getActivity(), R.string.no_data);
                }
            }
        }, moveLogInAct).execute(StringUtils.formatMsisdn(isdn));

        swipeRefreshLayout.setRefreshing(false);
    }

    public void refreshUI() {
        if (ccOutput.getLstHistoryConsultBeans() != null) {

            tvNoData.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);

            ArrayList<HistoryConsultBean> historyItemDatas =
                    new ArrayList<>(ccOutput.getLstHistoryConsultBeans());
            AdvisoryHistoryAdapter historyAdapter =
                    new AdvisoryHistoryAdapter(historyItemDatas, getActivity());
            lvHistory.setAdapter(historyAdapter);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    private void setDataDemo() {
        ArrayList<HistoryConsultBean> historyItemDatas = new ArrayList<>();
        for (int index = 0; index < 50; index++) {
            historyItemDatas.add(new HistoryConsultBean(index));
        }

        AdvisoryHistoryAdapter historyAdapter =
                new AdvisoryHistoryAdapter(historyItemDatas, getActivity());
        lvHistory.setAdapter(historyAdapter);
    }

    @Override
    protected void setPermission() {

    }
}
