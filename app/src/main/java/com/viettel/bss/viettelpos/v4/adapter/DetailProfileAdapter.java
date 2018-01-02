package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 12/27/2016.
 */

public class DetailProfileAdapter extends RecyclerView.Adapter<DetailProfileAdapter.ViewHolder>{
    private List<RecordBean> lstData = new ArrayList<>();
    private final String customerName;

    public DetailProfileAdapter(Context mContext, List<RecordBean> lstData, String customerName){
        this.lstData = lstData;
        this.customerName = customerName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecordBean recordBean = lstData.get(position);
        holder.tvProfileName.setText(recordBean.getRecordName());
        holder.tvCustomerName.setText(customerName);
        holder.tvOrder.setText("" + (position + 1));
        holder.tvProfileStatus.setText(recordBean.getRecordStatusStr());

        showImageProfile(holder, recordBean);
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public List<RecordBean> getLstData() {
        return lstData;
    }

    public void setLstData(List<RecordBean> lstData) {
        this.lstData = lstData;
    }

    private void showImageProfile(ViewHolder holder, RecordBean recordBean){
        if(!CommonActivity.isNullOrEmpty(recordBean.getRecordNameScan())){
            if(recordBean.getRecordNameScan().toUpperCase().endsWith(".ZIP")){
                holder.imgZip.setVisibility(View.VISIBLE);
            } else if (recordBean.getRecordNameScan().toUpperCase().endsWith(".PDF")){
                holder.imgPdf.setVisibility(View.VISIBLE);
            } else if (recordBean.getRecordNameScan().toUpperCase().endsWith(".DOC")
                    || recordBean.getRecordNameScan().toUpperCase().endsWith(".DOCX")){
                holder.imgDoc.setVisibility(View.VISIBLE);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvProfileName)
        TextView tvProfileName;
        @BindView(R.id.tvCustomerName)
        TextView tvCustomerName;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvProfileStatus)
        TextView tvProfileStatus;
        @BindView(R.id.tvOrder)
        TextView tvOrder;
        @BindView(R.id.imgPdf)
        ImageView imgPdf;
        @BindView(R.id.imgZip)
        ImageView imgZip;
        @BindView(R.id.imgDoc)
        ImageView imgDoc;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
