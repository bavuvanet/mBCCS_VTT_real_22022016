package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GetReasondapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<ReasonDTO> arrReason = new ArrayList<>();
    private final ArrayList<ReasonDTO> arraylist = new ArrayList<>();


    public GetReasondapter(ArrayList<ReasonDTO> arr, Context context) {
        if (arr != null) {
            this.arraylist.addAll(arr);
            this.arrReason.addAll(arr);
        }
        mContext = context;
    }

    @Override
    public int getCount() {

        if (arrReason == null || arrReason.size() == 0) {
            return 0;
        } else {
            return arrReason.size();
        }

    }

    @Override
    public Object getItem(int arg0) {
        return arrReason.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    class ViewHolder {
        TextView txtpstn;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder;
        View mView = arg1;

        if (mView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.item_reason, arg2,
                    false);
            holder = new ViewHolder();
            holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }

        final ReasonDTO reasonDTO = arrReason.get(arg0);
        if (!TextUtils.isEmpty(reasonDTO.getReasonCode())) {
            holder.txtpstn.setText(reasonDTO.getReasonCode() + " - " + reasonDTO.getName());
        } else {
            holder.txtpstn.setText("" + reasonDTO.getName());
        }
        return mView;
    }

    public ArrayList<ReasonDTO> SearchInput(String chartext) {
        chartext = chartext.toLowerCase();
        arrReason.clear();
        if (chartext.isEmpty()) {
            arrReason.addAll(arraylist);
        } else {
            for (ReasonDTO hTBeans : arraylist) {
                if (CommonActivity.convertUnicode2Nosign(hTBeans.getReasonCode()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext)) || CommonActivity.convertUnicode2Nosign(hTBeans.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))) {
                    arrReason.add(hTBeans);
                }
            }
        }
        notifyDataSetChanged();
        return arrReason;
    }
}
