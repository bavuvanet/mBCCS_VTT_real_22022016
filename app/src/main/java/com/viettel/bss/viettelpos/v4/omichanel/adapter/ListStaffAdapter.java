package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

/**
 * Created by hantt47 on 9/26/2017.
 */

public class ListStaffAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Staff> arrStaff = new ArrayList<>();

    public ListStaffAdapter(Context context, ArrayList<Staff> arrStaff) {
        this.arrStaff = arrStaff;
        mContext = context;
    }

    @Override
    public int getCount() {
        return arrStaff.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
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
            mView = inflater.inflate(R.layout.list_item_checkbox, arg2,
                    false);
            holder = new ViewHoler();
            holder.tvStaffCode = (TextView) mView.findViewById(R.id.tvStaffCode);
            holder.tvStaffName = (TextView) mView.findViewById(R.id.tvStaffName);
            holder.ckb1 = (CheckBox) mView.findViewById(R.id.ckb1);
            mView.setTag(holder);
        } else {
            holder = (ViewHoler) mView.getTag();
        }
        final Staff staff = this.arrStaff.get(position);
        holder.tvStaffCode.setText(staff.getStaffCode());
        holder.tvStaffName.setText(staff.getName());
        holder.ckb1.setChecked(false);
        if (arrStaff.get(position).isCheck())
            holder.ckb1.setChecked(true);
        else
            holder.ckb1.setChecked(false);

        holder.ckb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.ckb1.isChecked())
                    arrStaff.get(position).setCheck(true);
                else
                    arrStaff.get(position).setCheck(false);
            }
        });

        return mView;
    }

    class ViewHoler {
        TextView tvStaffCode;
        TextView tvStaffName;
        CheckBox ckb1;
    }
}