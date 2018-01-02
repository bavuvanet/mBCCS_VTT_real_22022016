package com.viettel.bss.viettelpos.v4.charge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.FeeDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.util.List;

/**
 * Created by thinhhq1 on 6/22/2017.
 */
public class FeeAdapter extends BaseAdapter {
    private List<ProductPackageFeeDTO> feeDTOs;
    private Context context;

    public FeeAdapter(Context context, List<ProductPackageFeeDTO> feeDTOs) {
        this.context = context;
        this.feeDTOs = feeDTOs;
    }

    @Override
    public int getCount() {
        return feeDTOs.size();
    }

    @Override
    public ProductPackageFeeDTO getItem(int position) {
        return feeDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        ProductPackageFeeDTO feeDTO=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_free_info, null);
        }
        holder.txtTypeFee = (TextView) convertView.findViewById(R.id.txtTypeFee);
        holder.txtFee = (TextView) convertView.findViewById(R.id.txtFee);
        holder.txtTypeFee.setText(feeDTO.getName());
        holder.txtFee.setText(feeDTO.getPrice());
        return convertView;
    }

    static class ViewHolder {
        TextView txtTypeFee;
        TextView txtFee;
    }
}
