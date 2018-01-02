package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.ReportProfileBO;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 6/17/2017.
 */
public class ReportRegisterDetailAdapter extends RecyclerView.Adapter<ReportRegisterDetailAdapter.ViewHolder>{

    private List<ReportProfileBO> lstData;
    private com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener;
    private Context mContext;

    public ReportRegisterDetailAdapter(Context mContext, List<ReportProfileBO> lstData, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.lstData = lstData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_register, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReportProfileBO reportProfileBO = lstData.get(position);

        holder.tvIsdn.setText(DataUtils.safeToString(reportProfileBO.getIsdnAccount()));
        holder.tvIdNo.setText(mContext.getString(R.string.ido_lst, DataUtils.safeToString(reportProfileBO.getIdNo())));
        holder.tvDateRegister.setText(mContext.getString(R.string.date_register_param, DateTimeUtils.convertDateSoapToString(reportProfileBO.getActionDate())));
        holder.tvProfileStatus.setText(mContext.getString(R.string.profile_status_param, DataUtils.safeToString(reportProfileBO.getDescTypeStatus())));

        holder.lnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(reportProfileBO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIsdn)
        TextView tvIsdn;
        @BindView(R.id.tvDateRegister)
        TextView tvDateRegister;
        @BindView(R.id.tvIdNo)
        TextView tvIdNo;
        @BindView(R.id.tvProfileStatus)
        TextView tvProfileStatus;
        @BindView(R.id.lnInfo)
        LinearLayout lnInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
