package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.listener.*;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 6/9/2017.
 */
public class LstBTSAdapter extends RecyclerView.Adapter<LstBTSAdapter.ViewHolder>{
    private Context mContext;
    private List<StationBO> lstData;
    private OnItemClickListener onItemClickListener;

    public LstBTSAdapter(Context mContext, List<StationBO> lstData, OnItemClickListener onItemClickListener){
        this.mContext = mContext;
        this.lstData = lstData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StationBO stationBO = lstData.get(position);
        holder.textView.setText(stationBO.getName());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationBO.setIsSelected(holder.checkBox.isChecked());
                onItemClickListener.onItemClick(stationBO);
            }
        });
        holder.checkBox.setChecked(stationBO.getIsSelected());
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
