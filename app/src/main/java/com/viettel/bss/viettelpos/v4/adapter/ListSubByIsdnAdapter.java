package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toancx on 2/22/2017.
 */

public class ListSubByIsdnAdapter extends RecyclerView.Adapter<ListSubByIsdnAdapter.ViewHolder>{

    private List<SubscriberDTO> lstData= new ArrayList<>();
    private Context mContext;

    public ListSubByIsdnAdapter(Context mContext, List<SubscriberDTO> lstData){
        this.mContext = mContext;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list_sub_by_isdn, parent, false);
        return new ListSubByIsdnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubscriberDTO subscriberDTO = lstData.get(position);
        holder.tvIsdn.setText(subscriberDTO.getIsdn());
        holder.tvService.setText(subscriberDTO.getTelecomServiceName());
        holder.tvIsdnType.setText(subscriberDTO.getSubTypeName());
        holder.tvStatus.setText(subscriberDTO.getStatusName());
    }

    @Override
    public int getItemCount() {
        return CommonActivity.isNullOrEmptyArray(lstData) ? 0 : lstData.size();
    }

    public List<SubscriberDTO> getLstData(){
        return lstData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvIsdn)
        TextView tvIsdn;
        @BindView(R.id.tvService)
        TextView tvService;
        @BindView(R.id.tvIsdnType)
        TextView tvIsdnType;
        @BindView(R.id.tvStatus)
        TextView tvStatus;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
