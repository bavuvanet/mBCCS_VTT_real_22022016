package com.viettel.bss.viettelpos.v4.synchronizationdata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 18/09/2017.
 */

public class ApParamAdapter extends RecyclerView.Adapter<ApParamAdapter.ViewHolder> {

    List<ApParamBO> listApParams;
    Context context;

    public ApParamAdapter(List<ApParamBO> listApParams, Context context) {
        this.listApParams = listApParams;
        this.context = context;
    }

    public List<ApParamBO> getListApParams() {
        return listApParams;
    }

    public void setListApParams(List<ApParamBO> listApParams) {
        this.listApParams = listApParams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ap_param_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApParamBO paramBO = listApParams.get(position);
        if (!CommonActivity.isNullOrEmpty(paramBO.getId())) {
            holder.txtId.setText(paramBO.getId() + "");
        } else {
            holder.llId.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(paramBO.getStatus())) {
            if (paramBO.getStatus() == 1) {
                holder.txtStatus.setText(context.getString(R.string.txt_status_account_active));
            } else if (paramBO.getStatus() == 0) {
                holder.txtStatus.setText(context.getString(R.string.status_0));
            } else holder.txtStatus.setText(context.getString(R.string.status_));
        } else {
            holder.txtStatus.setText(context.getString(R.string.status_));
        }
        if (!CommonActivity.isNullOrEmpty(paramBO.getParName())) {
            holder.txtNameParam.setText(paramBO.getParName());
        }
        if (!CommonActivity.isNullOrEmpty(paramBO.getParType())) {
            holder.txtTypeParam.setText(paramBO.getParType());
        }
        if (!CommonActivity.isNullOrEmpty(paramBO.getParValue())) {
            holder.txtValueParam.setText(paramBO.getParValue());
        }
        if (!CommonActivity.isNullOrEmpty(paramBO.getDescription())) {
            holder.txtDescriptionParam.setText(paramBO.getDescription());
            holder.llDescription.setVisibility(View.VISIBLE);
        } else {
            holder.llDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listApParams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llId)
        LinearLayout llId;
        @BindView(R.id.txtId)
        TextView txtId;
        @BindView(R.id.llStatus)
        LinearLayout llStatus;
        @BindView(R.id.txtStatus)
        TextView txtStatus;
        @BindView(R.id.txtTypeParam)
        TextView txtTypeParam;
        @BindView(R.id.txtNameParam)
        TextView txtNameParam;
        @BindView(R.id.txtValueParam)
        TextView txtValueParam;
        @BindView(R.id.txtDescriptionParam)
        TextView txtDescriptionParam;
        @BindView(R.id.llDescription)
        LinearLayout llDescription;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
