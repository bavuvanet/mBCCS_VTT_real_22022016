package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.SubPreChargeDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 10/22/2017.
 */

public class ChargeAgeSubscriberAdapter extends RecyclerView.Adapter<ChargeAgeSubscriberAdapter.ViewHolder> {

    private List<SubPreChargeDTO> subPreChargeDTOs;
    private Activity context;

    public ChargeAgeSubscriberAdapter(List<SubPreChargeDTO> subPreChargeDTOs, Activity context) {
        this.subPreChargeDTOs = subPreChargeDTOs;
        this.context = context;
    }

    @Override
    public ChargeAgeSubscriberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_charge_month, parent, false);
        return new ChargeAgeSubscriberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChargeAgeSubscriberAdapter.ViewHolder holder, int position) {
        SubPreChargeDTO preChargeDTO = subPreChargeDTOs.get(subPreChargeDTOs.size()-1 - position);


        if (!CommonActivity.isNullOrEmpty(subPreChargeDTOs)) {
            holder.tvNo.setText((position + 1) + "");
            if (!CommonActivity.isNullOrEmpty(preChargeDTO.getMonthId()) && preChargeDTO.getMonthId().length() >= 6) {
                String month = preChargeDTO.getMonthId().substring(4, 6) + "/" + preChargeDTO.getMonthId().substring(0, 4);
                holder.tvMonth.setText(month);
            } else {
                holder.tvMonth.setText("");
            }
            if (!CommonActivity.isNullOrEmpty(preChargeDTO.getTotalCharge())) {
                holder.tvCharge.setText(StringUtils.formatMoney(preChargeDTO.getTotalCharge().longValue() + ""));
            } else {
                holder.tvCharge.setText("");
            }
        }

    }

    @Override
    public int getItemCount() {
        return subPreChargeDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNo)
        TextView tvNo;
        @BindView(R.id.tvMonth)
        TextView tvMonth;
        @BindView(R.id.tvCharge)
        TextView tvCharge;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

