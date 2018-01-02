package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VasInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 18/09/2017.
 */

public class VasInfoAdapter extends
        RecyclerView.Adapter<VasInfoAdapter.ViewHolder> {

    List<VasInfo> vasInfoList;

    public VasInfoAdapter(List<VasInfo> vasInfoList) {
        this.vasInfoList = vasInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.omni_vas_info_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VasInfo vasInfo = vasInfoList.get(position);

        if (!CommonActivity.isNullOrEmpty(vasInfo.getVasName())) {
            String nameCode= vasInfo.getVasName();
            if(!CommonActivity.isNullOrEmpty(vasInfo.getVasCode())) nameCode=nameCode+"\n"+vasInfo.getVasCode();
            holder.tvVasName.setText(nameCode);
        } else {
            holder.tvVasName.setText("Null");
        }

        if (!CommonActivity.isNullOrEmpty(vasInfo.getPrice())) {
            holder.tvVasPrice.setText(StringUtils.formatMoney(vasInfo.getPrice().toString()));
        } else {
            holder.tvVasPrice.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        return vasInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvVasName)
        TextView tvVasName;
        @BindView(R.id.tvVasPrice)
        TextView tvVasPrice;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
