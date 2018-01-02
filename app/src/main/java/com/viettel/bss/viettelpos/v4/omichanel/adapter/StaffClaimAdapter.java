package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

/**
 * Created by hantt47 on 9/26/2017.
 */

public class StaffClaimAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Staff> arrStaff = new ArrayList<>();

    public StaffClaimAdapter(Context context, ArrayList<Staff> arrStaff) {
        this.arrStaff = arrStaff;
        mContext = context;
    }

    @Override
    public int getCount() {
        return arrStaff.size();
    }

    @Override
    public Staff getItem(int position) {
        return arrStaff.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View arg1, ViewGroup arg2) {

        final ViewHoler holder;
        View mView = arg1;
        if (mView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.simple_list_item_two_row, arg2,
                    false);
            holder = new ViewHoler();
            holder.tvStaffCode = (TextView) mView.findViewById(R.id.tvStaffCode);
            holder.tvStaffName = (TextView) mView.findViewById(R.id.tvStaffName);
            mView.setTag(holder);
        } else {
            holder = (ViewHoler) mView.getTag();
        }
        final Staff staff = this.arrStaff.get(position);
        holder.tvStaffCode.setText(staff.getStaffCode());
        holder.tvStaffName.setText(staff.getName());
        return mView;
    }

    class ViewHoler {
        TextView tvStaffCode;
        TextView tvStaffName;
    }
}