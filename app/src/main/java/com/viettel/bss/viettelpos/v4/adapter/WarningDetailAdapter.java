package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.object.WarningStaffBO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 4/19/2017.
 */

public class WarningDetailAdapter extends RecyclerView.Adapter<WarningDetailAdapter.ViewHolder>{
    private List<WarningStaffBO> lstData;
    private Context mContext;

    public WarningDetailAdapter(Context mContext, List<WarningStaffBO> lstData){
        this.lstData = lstData;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warning_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WarningStaffBO warningStaffBO = lstData.get(position);

        holder.tvTitle.setText(warningStaffBO.getName());
        holder.tvOrder.setText("" + (position + 1));

        WarningAttributeAdapter warningAttributeAdapter = new WarningAttributeAdapter(warningStaffBO.getLstWarningStaff());
        holder.rvLstAttribute.setHasFixedSize(true);
        holder.rvLstAttribute.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rvLstAttribute.setAdapter(warningAttributeAdapter);
        if(CommonActivity.isNullOrEmpty(warningStaffBO.getLevel())){
            holder.imgWarning.setVisibility(View.GONE);
        } else {
            if(warningStaffBO.getLevel().intValue() == 0){
                holder.imgWarning.setImageResource(R.drawable.ic_warning_red);
            } else if (warningStaffBO.getLevel().intValue() == 1){
                holder.imgWarning.setImageResource(R.drawable.ic_warning_orange);
            } else if (warningStaffBO.getLevel().intValue() == 2){
                holder.imgWarning.setImageResource(R.drawable.ic_warning_yellow);
            } else {
                holder.imgWarning.setImageResource(R.drawable.ic_warning_blue);
            }
        }
    }

    @Override
    public int getItemCount() {
        return CommonActivity.isNullOrEmptyArray(lstData) ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvOrder)
        TextView tvOrder;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.rvLstAttribute)
        RecyclerView rvLstAttribute;
        @BindView(R.id.imgWarning)
        ImageView imgWarning;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
