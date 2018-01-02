package com.viettel.bss.viettelpos.v4.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.object.WarningStaff;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 4/19/2017.
 */

public class WarningAttributeAdapter extends RecyclerView.Adapter<WarningAttributeAdapter.ViewHolder> {
    private List<WarningStaff> lstData;

    public WarningAttributeAdapter(List<WarningStaff> lstData){
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warning_attribute, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WarningStaff warningStaff = lstData.get(position);
        if(CommonActivity.isNullOrEmpty(warningStaff.getColumnName())){
            holder.tvCode.setVisibility(View.GONE);
        } else {
            holder.tvCode.setText(warningStaff.getColumnName());
        }

        if(!CommonActivity.isNullOrEmpty(warningStaff.getColumnValue())) {
            holder.tvValue.setText(warningStaff.getColumnValue());
        } else {
            holder.tvValue.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return CommonActivity.isNullOrEmptyArray(lstData) ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.tvValue)
        TextView tvValue;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
