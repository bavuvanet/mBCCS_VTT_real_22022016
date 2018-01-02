package com.viettel.bss.viettelpos.v4.adapter;

/**
 * Created by diepdc-pc on 8/8/2017.
 */

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReasonAdapter extends BaseAdapter {
    Context context;
    List<ReasonDTO> mReasonDTOList;
    LayoutInflater inflter;

    public ReasonAdapter(Context applicationContext, List<ReasonDTO> list) {
        this.context = applicationContext;
        this.mReasonDTOList = list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void sort(List<ReasonDTO> reasonDTOs) {
        System.out.println("12345 sort");
        //mReasonDTOList = reasonDTOs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mReasonDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return mReasonDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_reason, null);
        TextView names = (TextView) view.findViewById(R.id.text1);

        if (!TextUtils.isEmpty(mReasonDTOList.get(i).getReasonCode())) {
            names.setText(mReasonDTOList.get(i).getReasonCode() + " - " + mReasonDTOList.get(i).getName());
        } else {
            names.setText("" + mReasonDTOList.get(i).getName());
        }
        return view;
    }
}