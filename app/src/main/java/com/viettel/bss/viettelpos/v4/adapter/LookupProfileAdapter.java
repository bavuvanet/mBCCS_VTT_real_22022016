package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 12/26/2016.
 */

public class LookupProfileAdapter extends RecyclerView.Adapter<LookupProfileAdapter.ViewHolder>{
    private List<ActionProfileBean> lstData = new ArrayList<>();

    public LookupProfileAdapter(Context mContext, List<ActionProfileBean> lstData){
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lookup_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActionProfileBean profileBean = lstData.get(position);

        holder.tvOrder.setText("" + (position + 1));
        holder.tvCustomerName.setText(profileBean.getCusName());
        holder.tvIsdnAccount.setText(profileBean.getIsdnAccount());
        holder.tvIdNo.setText(profileBean.getIdNo());
        holder.tvService.setText(profileBean.getSerTypeName());
        holder.tvStatusSub.setText(profileBean.getSubStatus());
        holder.tvStatusProfile.setText(profileBean.getProfileStatus());
        holder.tvDateActive.setText(profileBean.getActionDateStr());
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvCustomerName)
        TextView tvCustomerName;
        @BindView(R.id.tvIsdnAccount)
        TextView tvIsdnAccount;
        @BindView(R.id.tvIdNo)
        TextView tvIdNo;
        @BindView(R.id.tvService)
        TextView tvService;
        @BindView(R.id.tvStatusSub)
        TextView tvStatusSub;
        @BindView(R.id.tvStatusProfile)
        TextView tvStatusProfile;
        @BindView(R.id.tvDateActive)
        TextView tvDateActive;
        @BindView(R.id.tvOrder)
        TextView tvOrder;

        public ViewHolder(View v){
            super(v);

            ButterKnife.bind(this, v);
        }
    }

    public List<ActionProfileBean> getLstData() {
        return lstData;
    }

    public void setLstData(List<ActionProfileBean> lstData) {
        this.lstData = lstData;
    }
}
