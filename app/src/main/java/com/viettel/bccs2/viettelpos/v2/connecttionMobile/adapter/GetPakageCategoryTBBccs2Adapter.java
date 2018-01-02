package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

public class GetPakageCategoryTBBccs2Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SubTypeBeans> arrPakageChargeBeans = new ArrayList<>();
    private ArrayList<SubTypeBeans> arraylist = new ArrayList<>();

    public GetPakageCategoryTBBccs2Adapter(ArrayList<SubTypeBeans> arr, Context context) {
        //this.arraylist = new ArrayList<PakageChargeBeans>();
        if (arr != null) {
            this.arraylist.addAll(arr);
            this.arrPakageChargeBeans.addAll(arr);
        }


        mContext = context;
    }

    @Override
    public int getCount() {
        return arrPakageChargeBeans.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrPakageChargeBeans.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    class ViewHolder {
        TextView txtpstn;
        Button btninfo;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder;
        View mView = arg1;

        if (mView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2,
                    false);
            holder = new ViewHolder();
            holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
            holder.btninfo = (Button) mView.findViewById(R.id.btninfo);
            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }

        final SubTypeBeans subTyBeans = arrPakageChargeBeans.get(arg0);
        //if(!CommonActivity.isNullOrEmpty(pakageChargeBeans.getName())){
        //  holder.btninfo.setVisibility(View.VISIBLE);
        //  }else{
        holder.btninfo.setVisibility(View.GONE);
        //  }

        if (subTyBeans.getName() != null && !subTyBeans.getName().isEmpty()) {
            if (!CommonActivity.isNullOrEmpty(subTyBeans.getSubType())
                    && !CommonActivity.isNullOrEmpty(subTyBeans.getName())) {
                holder.txtpstn.setText(subTyBeans.getSubType() + " - " + subTyBeans.getName());
            } else {
                holder.txtpstn.setText(subTyBeans.getName());
            }

        }
     /* holder.btninfo.setOnClickListener(new OnClickListener() {
         
         @Override
         public void onClick(View arg0) {
            CommonActivity.createAlertDialog(mContext, pakageChargeBeans.getDescription(), mContext.getString(R.string.app_name)).show();
         }
      });*/

        return mView;
    }

    public ArrayList<SubTypeBeans> SearchInput(String chartext) {
        chartext = chartext.toLowerCase();
        arrPakageChargeBeans.clear();
        if (chartext.isEmpty()) {
            Log.d("size arraylist", "" + arraylist.size());
            arrPakageChargeBeans.addAll(arraylist);
        } else {
            for (SubTypeBeans subTyBeans : arraylist) {
                if (CommonActivity.convertUnicode2Nosign(subTyBeans.getSubType()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext)) || CommonActivity.convertUnicode2Nosign(subTyBeans.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))) {
                    arrPakageChargeBeans.add(subTyBeans);
                }
            }
        }

        notifyDataSetChanged();
        return arrPakageChargeBeans;
    }
}
