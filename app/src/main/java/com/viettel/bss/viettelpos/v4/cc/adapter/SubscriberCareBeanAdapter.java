package com.viettel.bss.viettelpos.v4.cc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.cc.object.SubscriberCareBean;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

import java.util.Date;
import java.util.List;

public class SubscriberCareBeanAdapter extends ArrayAdapter<SubscriberCareBean> {
    private Context context;

    public SubscriberCareBeanAdapter(Context context,
                                     List<SubscriberCareBean> lst) {
        super(context, R.layout.layout_sub_care_bean_item, lst);
        this.context = context;
    }

    private static class ViewHolder {
        TextView tvCusName;
        TextView tvCusPhone;
        TextView tvGiftName;
        TextView tvGiftType;
        TextView tvGiftDate;
        TextView tvGiftAllowType;
        TextView tvCareType;
        TextView tvStatus;
        TextView tvReceiverReal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SubscriberCareBean bean = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder; // view lookup cache stored in tag
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_sub_care_bean_item,
                    parent, false);
            holder.tvCusName = (TextView) convertView
                    .findViewById(R.id.tvCusName);
            holder.tvCusPhone = (TextView) convertView
                    .findViewById(R.id.tvCusPhone);
            holder.tvGiftName = (TextView) convertView
                    .findViewById(R.id.tvGiftName);
            holder.tvGiftType = (TextView) convertView
                    .findViewById(R.id.tvGiftType);
            holder.tvGiftDate = (TextView) convertView
                    .findViewById(R.id.tvGiftDate);
            holder.tvGiftAllowType = (TextView) convertView
                    .findViewById(R.id.tvGiftAllowType);
            holder.tvCareType = (TextView) convertView
                    .findViewById(R.id.tvCareType);
            holder.tvStatus = (TextView) convertView
                    .findViewById(R.id.tvStatus);
            holder.tvReceiverReal = (TextView) convertView
                    .findViewById(R.id.tvReceiverReal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCusName.setText(bean.getCustName());
        holder.tvCusPhone.setText(bean.getIsdn());
        Date giftDate = DateTimeUtils.convertDateFromSoap(bean.getGiftDate());
        holder.tvGiftDate.setText(context.getString(R.string.update_date_plan,
                DateTimeUtils.convertDateTimeToString(giftDate, "dd/MM/yyyy")));
        holder.tvGiftName.setText(context.getString(R.string.gift_name,
                bean.getGiftName()));
        holder.tvGiftType.setText(context.getString(R.string.gift_type,
                bean.getGiftType()));
        holder.tvGiftAllowType.setText(context.getString(
                R.string.gift_allow_type, bean.getGiftAllowType()));
        String careType = bean.getCareType();
        String careTypeName = context.getString(R.string.gift_birthday);
        if ("2".equals(careType)) {
            careTypeName = context.getString(R.string.gift_local);
        }

        holder.tvCareType.setText(context.getString(R.string.care_type,
                careTypeName));
        String status = context.getString(R.string.update_gift_fail);
        if ("1".equals(bean.getGiftStatus())) {
            status = context.getString(R.string.updated_gift);
            holder.tvReceiverReal.setVisibility(View.VISIBLE);
            String cusReal = "";
            if(!CommonActivity.isNullOrEmpty(bean.getReceiverRel())){
                cusReal =bean.getReceiverRel();
            }
            holder.tvReceiverReal.setText(context.getString(R.string.gift_receiver_real,cusReal));
        }else{
            holder.tvReceiverReal.setVisibility(View.GONE);
        }
        holder.tvStatus
                .setText(context.getString(R.string.status_gift, status));

        return convertView;
    }
}
