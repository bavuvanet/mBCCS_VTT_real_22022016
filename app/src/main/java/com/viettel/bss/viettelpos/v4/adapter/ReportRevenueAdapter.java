package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.Attr;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 5/17/2017.
 */
public class ReportRevenueAdapter extends RecyclerView.Adapter<ReportRevenueAdapter.ViewHolder>{
    private Activity mActivity;
    private List<List<String>> lstData;

    public ReportRevenueAdapter(Activity mActivity, List<List<String>> lstData) {
        this.mActivity = mActivity;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_report_revenue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> attr = lstData.get(position);

        holder.tvStation.setText(attr.get(0));
        if(CommonActivity.isNullOrEmpty(attr.get(1))){
            holder.view0.setVisibility(View.GONE);
            holder.view1.setVisibility(View.GONE);
            holder.view2.setVisibility(View.GONE);
            holder.view3.setVisibility(View.GONE);
            holder.view4.setVisibility(View.GONE);
        } else {
            holder.view0.setVisibility(View.VISIBLE);
            holder.view1.setVisibility(View.VISIBLE);
            holder.view2.setVisibility(View.VISIBLE);
            holder.view3.setVisibility(View.VISIBLE);
            holder.view4.setVisibility(View.VISIBLE);
        }

        holder.tvCriteria.setText(attr.get(1));
        holder.tvSln.setText(StringUtils.formatMoney(attr.get(2)));

        Long delta = DataUtils.safeToLong(attr.get(3));
        if(delta > 0){
            holder.tvDeltaSln.setTextColor(mActivity.getResources().getColor(R.color.green));
            holder.tvDeltaSln.setText("+" + StringUtils.formatMoney(attr.get(3)));
        } else {
            holder.tvDeltaSln.setTextColor(mActivity.getResources().getColor(R.color.red));
            holder.tvDeltaSln.setText(StringUtils.formatMoney(attr.get(3)));
        }

        holder.tvSlt.setText(StringUtils.formatMoney(attr.get(4)));

        delta = DataUtils.safeToLong(attr.get(5));
        if(delta > 0){
            holder.tvDeltaSlt.setTextColor(mActivity.getResources().getColor(R.color.green));
            holder.tvDeltaSlt.setText("+" + StringUtils.formatMoney(attr.get(5)));
        } else {
            holder.tvDeltaSlt.setTextColor(mActivity.getResources().getColor(R.color.red));
            holder.tvDeltaSlt.setText(StringUtils.formatMoney(attr.get(5)));
        }

    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvStation)
        TextView tvStation;
        @BindView(R.id.tvCriteria)
        TextView tvCriteria;
        @BindView(R.id.tvSln)
        TextView tvSln;
        @BindView(R.id.tvDeltaSln)
        TextView tvDeltaSln;
        @BindView(R.id.tvSlt)
        TextView tvSlt;
        @BindView(R.id.tvDeltaSlt)
        TextView tvDeltaSlt;
        @BindView(R.id.view0)
        View view0;
        @BindView(R.id.view1)
        View view1;
        @BindView(R.id.view2)
        View view2;
        @BindView(R.id.view3)
        View view3;
        @BindView(R.id.view4)
        View view4;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
