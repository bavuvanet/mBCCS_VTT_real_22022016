package com.viettel.bss.viettelpos.v4.report.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.listener.OnLoadMoreListener;
import com.viettel.bss.viettelpos.v4.report.object.LogMethodBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 1/7/2017.
 */

public class LogMBCCSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "LogMBCCSAdapter";
    private List<LogMethodBean> lstData = new ArrayList<>();
    private Context mContext;
    private final int VIEW_TYPE_ITEM = 0;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private final int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private int page = 0;
    private int count = 40;

    public LogMBCCSAdapter(){

    }

    public LogMBCCSAdapter(Context mContext, List<LogMethodBean> lstData, RecyclerView recyclerView){
        this.mContext = mContext;
        this.lstData = lstData;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                Log.d(TAG, "onScrolled with totalItemCount = " + totalItemCount + ", lastVisibleItem = " + lastVisibleItem
                        + ", isLoading = " + isLoading + ", page = " + page + ", count = " + count);
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && (totalItemCount % count == 0)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return this.lstData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_method, parent, false);
            return new LogMBCCSAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            LogMethodBean logMethodBean = lstData.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvUserCall.setText(logMethodBean.getUserCall());
            viewHolder.tvExecuteDate.setText(mContext.getString(R.string.executeDate, logMethodBean.getStartTime()));
            viewHolder.tvIpServer.setText(mContext.getString(R.string.ipServer, logMethodBean.getServerId()));
            viewHolder.tvLogMethodClassName.setText(mContext.getString(R.string.logMethodClassName, logMethodBean.getClassName()));
            viewHolder.tvLogMethodDuration.setText(mContext.getString(R.string.logMethodDuration, logMethodBean.getDuration()));
            viewHolder.tvLogMethodResult.setText(mContext.getString(R.string.logMethodResult, logMethodBean.getResultCode()));
            viewHolder.tvOrder.setText("" + (position + 1));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    public List<LogMethodBean> getLstData() {
        return lstData;
    }

    public void setLstData(List<LogMethodBean> lstData) {
        this.lstData = lstData;
    }

    @Override
    public int getItemCount() {
        return this.lstData == null ? 0 : this.lstData.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvUserCall)
        TextView tvUserCall;
        @BindView(R.id.tvExecuteDate)
        TextView tvExecuteDate;
        @BindView(R.id.tvIpServer)
        TextView tvIpServer;
        @BindView(R.id.tvLogMethodResult)
        TextView tvLogMethodResult;
        @BindView(R.id.tvLogMethodDuration)
        TextView tvLogMethodDuration;
        @BindView(R.id.tvLogMethodClassName)
        TextView tvLogMethodClassName;
        @BindView(R.id.tvOrder)
        TextView tvOrder;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public LoadingViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
