package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v4.R;

import java.util.ArrayList;

/**
 * Created by knight on 24/08/2017.
 */

public class GetServiceTelecomAdapter extends android.widget.BaseAdapter {

    private final Context mContext;
    private ArrayList<ProductCatalogDTO> arrProductCatalogDTOs = new ArrayList<>();

    public GetServiceTelecomAdapter(Context context, ArrayList<ProductCatalogDTO> arr) {
        this.mContext = context;
        this.arrProductCatalogDTOs = arr;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrProductCatalogDTOs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arrProductCatalogDTOs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View rowView;
        rowView = inflater.inflate(R.layout.layout_multi_textview_v1, null);
        TextView tv = (TextView) rowView.findViewById(android.R.id.text1);

        tv.setText(arrProductCatalogDTOs.get(position).getName());

        return rowView;
    }
}
