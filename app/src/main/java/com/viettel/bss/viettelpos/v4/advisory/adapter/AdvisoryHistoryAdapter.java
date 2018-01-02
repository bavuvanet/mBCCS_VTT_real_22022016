package com.viettel.bss.viettelpos.v4.advisory.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.data.HistoryConsultBean;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.chart.MPChartHelper;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/10/2017.
 */

public class AdvisoryHistoryAdapter extends ArrayAdapter<HistoryConsultBean> {

    private ArrayList<HistoryConsultBean> historyItemDatas;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView tvCode;
        TextView tvDate;
        TextView tvStatus;
        View headerView;
        LinearLayout layoutView;
    }

    public AdvisoryHistoryAdapter(ArrayList<HistoryConsultBean> historyItemDatas, Context context) {
        super(context, R.layout.advisory_history_item_view, historyItemDatas);
        this.historyItemDatas = historyItemDatas;
        this.context = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HistoryConsultBean historyItemData = getItem(position);
        AdvisoryHistoryAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new AdvisoryHistoryAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.advisory_history_item_view, parent, false);
            viewHolder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            viewHolder.headerView = (View) convertView.findViewById(R.id.headerView);
            viewHolder.layoutView = (LinearLayout) convertView.findViewById(R.id.layoutView);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdvisoryHistoryAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvCode.setText(""
                + historyItemData.getProductBean().getCode());
        viewHolder.tvDate.setText(""
                + StringUtils.convertDate(historyItemData.getTime())
                + " " + getTimeFromString(historyItemData.getTime()));
        viewHolder.tvStatus.setText(""
                + historyItemData.getStatusName());

        viewHolder.headerView.setBackgroundColor(
                getColorStatus(historyItemData.getStatus()));

        if (position % 2 == 0) {
            viewHolder.layoutView.setBackgroundResource(R.color.gray_6);
        } else {
            viewHolder.layoutView.setBackgroundResource(R.color.gray_7);
        }

        return convertView;
    }

    private int getColorStatus(String status) {
        try {
            int value = Integer.parseInt(status);
            switch(value) {
                case 0:
                    return Color.GREEN;
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.RED;
                case 3:
                    return Color.YELLOW;
                default:
                    return Color.GRAY;
            }
        } catch (Exception e) {
            return Color.GRAY;
        }
    }

    private String getTimeFromString(String timeString) {
        String timeValue = timeString.substring(11, 19);
        return timeValue;
    }
}