package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ChangePrepaidDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 9/29/2017.
 */

public class ReponseChangePrepaidPromotionAdapter extends
        RecyclerView.Adapter<ReponseChangePrepaidPromotionAdapter.ViewHolder> {
    List<ChangePrepaidDTO> changePrepaidDTOs;
    Activity activity;

    public ReponseChangePrepaidPromotionAdapter(List<ChangePrepaidDTO> changePrepaidDTOs, Activity activity) {
        this.changePrepaidDTOs = changePrepaidDTOs;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reponse_change_prepaid_promotion_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ChangePrepaidDTO changePrepaidDTO = changePrepaidDTOs.get(position);
        if (!CommonActivity.isNullOrEmpty(changePrepaidDTO)) {
            if (!CommonActivity.isNullOrEmpty(changePrepaidDTO.getIsdn())) {
                holder.tvAccount.setText(changePrepaidDTO.getIsdn());
                holder.tvAccount.setVisibility(View.VISIBLE);
            } else {
                holder.tvAccount.setVisibility(View.GONE);
            }
            if (!"0".equals(changePrepaidDTO.getCode())) {
                holder.tvStatus.setText(activity.getText(R.string.tvTaskUpdateNotSuccess));
                holder.imMessage.setVisibility(View.VISIBLE);
            } else {
                holder.tvStatus.setText(activity.getText(R.string.tvTaskUpdateSuccess));
                holder.imMessage.setVisibility(View.GONE);
            }
            holder.imMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonActivity.showConfirmValidate(activity, changePrepaidDTO.getMessage());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return changePrepaidDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAccount)
        TextView tvAccount;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.imMessage)
        ImageView imMessage;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
