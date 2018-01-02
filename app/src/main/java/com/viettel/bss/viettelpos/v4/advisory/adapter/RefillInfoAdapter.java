package com.viettel.bss.viettelpos.v4.advisory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.data.RefillInfoData;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/8/2017.
 */

public class RefillInfoAdapter extends ArrayAdapter<RefillInfoData> {

    private ArrayList<RefillInfoData> refillInfoDatas;
    Context mContext;
    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public RefillInfoAdapter(ArrayList<RefillInfoData> refillInfoDatas, Context context) {
        super(context, R.layout.advisory_vas_dto_item_view, refillInfoDatas);
        this.refillInfoDatas = refillInfoDatas;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefillInfoData refillInfoData = getItem(position);
        RefillInfoAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new RefillInfoAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advisory_vas_dto_item_view, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.tv2);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RefillInfoAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(refillInfoData.getRefillDate());
        viewHolder.txtType.setText(StringUtils.formatMoney(
                refillInfoData.getRefillAmount()));

        if (position % 2 == 0) {
            viewHolder.txtName.setBackgroundResource(R.color.gray_7);
            viewHolder.txtType.setBackgroundResource(R.color.gray_7);
        } else {
            viewHolder.txtName.setBackgroundResource(R.color.gray_8);
            viewHolder.txtType.setBackgroundResource(R.color.gray_8);
        }

        return convertView;
    }
}