package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.ProblemHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toancx on 2/22/2017.
 */

public class ProblemHistoryAdapter extends RecyclerView.Adapter<ProblemHistoryAdapter.ViewHolder> {
    private List<ProblemHistory> lstData = new ArrayList<>();
    private Context mContext;

    public ProblemHistoryAdapter(Context mContext, List<ProblemHistory> lstData) {
        this.mContext = mContext;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_problem_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProblemHistory problemHistory = lstData.get(position);
        holder.tvIsdn.setText(problemHistory.getIsdn());
        holder.tvComplainType.setText(problemHistory.getProbTypeName());
        holder.tvStatus.setText(problemHistory.getStatusName());
        holder.tvCreateDate.setText(problemHistory.getCreateDate());
        holder.tvDeadline.setText(problemHistory.getCustLimitDate());
        holder.tvCustNote.setText(problemHistory.getCustomerText());
    }

    @Override
    public int getItemCount() {
        return CommonActivity.isNullOrEmptyArray(lstData) ? 0 : lstData.size();
    }

    public List<ProblemHistory> getLstData(){
        return lstData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIsdn)
        TextView tvIsdn;
        @BindView(R.id.tvComplainType)
        TextView tvComplainType;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvCreateDate)
        TextView tvCreateDate;
        @BindView(R.id.tvCustNote)
        TextView tvCustNote;
        @BindView(R.id.tvDeadline)
        TextView tvDeadline;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
