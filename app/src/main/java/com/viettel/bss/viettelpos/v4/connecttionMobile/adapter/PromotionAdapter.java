package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.util.ArrayList;

/**
 * Created by thinhhq1 on 8/11/2017.
 */
public class PromotionAdapter extends BaseAdapter {
    ArrayList<PromotionTypeBeans> result;
    Activity context;
    private String productCode;

    //		int img;
    private LayoutInflater inflater = null;

    public PromotionAdapter(Activity activity, ArrayList<PromotionTypeBeans> listInfo, String productCode) {
        // TODO Auto-generated constructor stub
        this.result = listInfo;
        this.context = activity;
        this.productCode = productCode;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public PromotionTypeBeans getItem(int position) {
        // TODO Auto-generated method stub
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        Button btnInfo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.simple_list_info_layout, null);
        holder.tv = (TextView) rowView.findViewById(R.id.txtItem);
        holder.btnInfo = (Button) rowView.findViewById(R.id.btnInfo);
        holder.tv.setText(result.get(position).getCodeName());
        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogDetailAtribute.showDialogDetailPromotion(context, getItem(position), productCode);
            }
        });
        return rowView;
    }


}
