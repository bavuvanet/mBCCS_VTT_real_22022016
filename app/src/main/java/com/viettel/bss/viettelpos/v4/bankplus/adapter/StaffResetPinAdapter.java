package com.viettel.bss.viettelpos.v4.bankplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by toancx on 8/11/2017.
 */

public class StaffResetPinAdapter extends ArrayAdapter<Staff> {

    private ArrayList<Staff> staffs;
    private ArrayList<Staff> staffsBackup;
    Context context;
    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder {
        TextView tvName;
        TextView tvDescription;
        LinearLayout layoutView;
    }

    public StaffResetPinAdapter(ArrayList<Staff> staffs, Context context) {
        super(context, R.layout.bankplus_staff_reset_pin_row, staffs);
        this.staffs = staffs;
        this.staffsBackup = new ArrayList<>();
        this.staffsBackup.addAll(staffs);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Staff staff = getItem(position);
        final View result;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.bankplus_staff_reset_pin_row, parent, false);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.layoutView = (LinearLayout) convertView.findViewById(R.id.layoutView);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        if (staff != null) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
            result.startAnimation(animation);
            lastPosition = position;
            viewHolder.tvName.setText("" + staff.getName());
            viewHolder.tvDescription.setText("" + staff.getStaffCode());
        } else {
            Log.e("STAFF NULL!!!");
        }
        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        staffs.clear();
        if (charText.length() == 0) {
            staffs.addAll(staffsBackup);
        } else {
            for (Staff staff : staffsBackup) {
                if (staff.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        || staff.getStaffCode().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    staffs.add(staff);
                }
            }
        }
        notifyDataSetChanged();
    }
}