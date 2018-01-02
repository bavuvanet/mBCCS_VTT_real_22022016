package com.viettel.bss.viettelpos.v4.charge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;

import java.util.List;
import android.view.MotionEvent;
/**
 * Created by thuandq on 7/15/2017.
 */


/**
 * Created by thuandq on 7/15/2017.
 */

public class HintAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;
    private boolean selected = false;
    private String hint;
    private OnClickWhenDropDown click;

    public HintAdapter(Context context, List<String> data, String hint, OnClickWhenDropDown click) {
        this.context = context;
        this.data = data;
        this.hint = hint;
        this.click=click;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text_simple_13, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.spinner_value);
        if (!selected) {
            text.setText(hint);
            text.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        } else {
            text.setText(data.get(position));
            text.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text_simple_13, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.spinner_value);
        text.setText(data.get(position));
        text.setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selected=true;
                notifyDataSetChanged();
                click.onClick(position);
                return false;
            }
        });
        return convertView;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public interface OnClickWhenDropDown{
        void onClick(int position);
    }
}
