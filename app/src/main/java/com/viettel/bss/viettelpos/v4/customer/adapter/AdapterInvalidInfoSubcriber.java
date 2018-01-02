package com.viettel.bss.viettelpos.v4.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leekien on 11/6/2017.
 */

public class AdapterInvalidInfoSubcriber extends BaseAdapter {
    private Context context;
    private List<SubInvalidDTO> list = new ArrayList<>();
    private boolean failInfo;

    public AdapterInvalidInfoSubcriber(Context context, List<SubInvalidDTO> list, boolean failInfo) {
        this.context = context;
        this.list = list;
        this.failInfo = failInfo;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_file_info, null);
        TextView tvIsdn = (TextView) view.findViewById(R.id.tvIsdn);
        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        TextView tvReason = (TextView) view.findViewById(R.id.tvReason);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        LinearLayout lnDate = (LinearLayout) view.findViewById(R.id.lnDate);
        LinearLayout lnReason = (LinearLayout) view.findViewById(R.id.lnReason);
        SubInvalidDTO subInvalidDTO = list.get(position);

        if(failInfo){
            tvIsdn.setText(subInvalidDTO.getIsdn());
            tvStatus.setText(R.string.incorrect_info_isdn);
            tvReason.setText(subInvalidDTO.getDescription());
            tvDate.setText("");
            if(!CommonActivity.isNullOrEmpty(subInvalidDTO.getUpdateDateTime())) {
                String date = DateTimeUtils.convertDate(subInvalidDTO.getUpdateDateTime());
                tvDate.setText(date);
            }
        }else{
            lnDate.setVisibility(View.GONE);
            lnReason.setVisibility(View.GONE);
            tvIsdn.setText(subInvalidDTO.getIsdn());
            tvStatus.setText(R.string.correct_info_isdn_show);
        }



        return view;
    }
}
