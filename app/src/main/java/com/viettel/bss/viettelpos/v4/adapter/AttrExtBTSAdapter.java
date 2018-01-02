package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.AttrExt;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 5/17/2017.
 */
public class AttrExtBTSAdapter extends RecyclerView.Adapter<AttrExtBTSAdapter.ViewHolder> {

    private Activity mActivity;
    private List<AttrExt> lstData;

    public AttrExtBTSAdapter(Activity mActivity, List<AttrExt> lstData) {
        this.mActivity = mActivity;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attrext_bts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttrExt attrExt = lstData.get(position);
        holder.tvName.setText(attrExt.getName() + ":");
        holder.tvValue.setText(StringUtils.formatMoney(attrExt.getValue()));
        holder.tvValueInc.setText(StringUtils.formatMoney("" + (attrExt.getValueInc() >= 0 ? attrExt.getValueInc() : -attrExt.getValueInc())));

        if (attrExt.getValueInc() > 0) {
            holder.imgInc.setVisibility(View.VISIBLE);
            holder.imgDec.setVisibility(View.GONE);
        } else if (attrExt.getValueInc() == 0) {
            holder.imgInc.setVisibility(View.GONE);
            holder.imgDec.setVisibility(View.GONE);
        } else {
            holder.imgInc.setVisibility(View.GONE);
            holder.imgDec.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.tvValueInc)
        TextView tvValueInc;
        @BindView(R.id.imgInc)
        AppCompatImageView imgInc;
        @BindView(R.id.imgDec)
        AppCompatImageView imgDec;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
