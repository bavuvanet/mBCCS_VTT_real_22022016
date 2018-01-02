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

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/13/2017.
 */

public class SubWarningInfoAdapter extends ArrayAdapter<String> {

    private ArrayList<String> mesStrings;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtMessage;
    }

    public SubWarningInfoAdapter(ArrayList<String> mesStrings, Context context) {
        super(context, R.layout.advisory_sub_warning_item_view, mesStrings);
        this.mesStrings = mesStrings;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String stringMes = getItem(position);
        SubWarningInfoAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new SubWarningInfoAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advisory_sub_warning_item_view, parent, false);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.tvMes);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SubWarningInfoAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtMessage.setText(stringMes);

        return convertView;
    }
}