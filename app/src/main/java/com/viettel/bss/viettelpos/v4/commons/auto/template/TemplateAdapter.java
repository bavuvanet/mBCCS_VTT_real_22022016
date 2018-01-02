package com.viettel.bss.viettelpos.v4.commons.auto.template;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by diepdc-pc on 7/27/2017.
 */

public class TemplateAdapter extends BaseAdapter implements AutoConst {
    ArrayList<Map<String, String>> mList;
    Context mContext;

    int TAB_TYPE = 0;

    private View.OnClickListener mDeleteClick;
    private View.OnClickListener mSelectClick;

    public TemplateAdapter(Context context, int pos, ArrayList<Map<String, String>> list, View.OnClickListener delete, View.OnClickListener select) {
        System.out.println("12345 TemplateAdapter list " + list.size());
        mList = list;
        mContext = context;
        TAB_TYPE = pos;
        mDeleteClick = delete;
        mSelectClick = select;
    }

    public void clearAll() {
        if (mList != null) {
            mList.clear();
        }
    }

    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList.size();
    }

    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater.from(mContext));
        View row = inflater.inflate(R.layout.item_list_template, parent, false);

        Map<String, String> map = mList.get(position);
        LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.ll_text);
        initItem(position, map, linearLayout);

        ImageView imgRemove = (ImageView) row.findViewById(R.id.btn_remove);
        LinearLayout llRemove = (LinearLayout) row.findViewById(R.id.ll_remove);
        if (TAB_TYPE == 1) {
            imgRemove.setBackgroundResource(R.drawable.left24);
        } else {
            imgRemove.setBackgroundResource(R.drawable.delete);
        }
        llRemove.setTag(map.get(TEMPLATE_ID));
        linearLayout.setTag(map);

        llRemove.setOnClickListener(mDeleteClick);
        linearLayout.setOnClickListener(mSelectClick);

        return (row);
    }

    private void initItem(int position, Map<String, String> hashmap, LinearLayout linearLayout) {
        String templateKey = hashmap.get(MAP_KEY);
        if (templateKey != null) {
            String time = hashmap.get(TIME_STAMP);
            if (!TextUtils.isEmpty(time)) {
                TextView tvNo = (TextView) linearLayout.findViewById(R.id.tv_no);
                TextView tvTime = (TextView) linearLayout.findViewById(R.id.tv_time);
                tvNo.setText("" + (position + 1));
                tvTime.setText("" + parserDate(time));
            }

            if (templateKey.contains(PREF_MOBILE_NEW_SCREEN)) {
                onConnectNewMobileScreen(linearLayout, hashmap);
            } else if (templateKey.contains(PREF_DAU_NOI_CO_DINH_MOI_SCREEN)) {
                onConnectNewMobileScreen(linearLayout, hashmap);
            }
            /*else if(){ // doi sim
               //TODO
            }*/
        }
    }

    private String parserDate(String strDate) {
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            Date date = originalFormat.parse(strDate);

            String formattedDate = targetFormat.format(date);
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void onConnectNewMobileScreen(LinearLayout linearLayout, Map<String, String> mapTemplate) {
        if (linearLayout == null || mapTemplate == null) return;

        if (!TextUtils.isEmpty(mapTemplate.get(SERVICE))) {
            createTextView(linearLayout, mContext.getString(R.string.t_dv) + " : " + mapTemplate.get(SERVICE));
        }
        if (!TextUtils.isEmpty(mapTemplate.get(PACKAGE))) {
            createTextView(linearLayout, mContext.getString(R.string.t_gc) + " : " + mapTemplate.get(PACKAGE));
        }
        if (!TextUtils.isEmpty(mapTemplate.get(LOAITB))) {
            createTextView(linearLayout, mContext.getString(R.string.t_loadtb) + " : " + mapTemplate.get(LOAITB));
        }
        if (!TextUtils.isEmpty(mapTemplate.get(HTHM))) {
            createTextView(linearLayout, mContext.getString(R.string.t_hthm) + " : " + mapTemplate.get(HTHM));
        }
        if (!TextUtils.isEmpty(mapTemplate.get(KM))) {
            createTextView(linearLayout, mContext.getString(R.string.t_kmai) + " : " + mapTemplate.get(KM));
        }
        if (!TextUtils.isEmpty(mapTemplate.get(CDT))) {
            createTextView(linearLayout, mContext.getString(R.string.t_cdt) + " : " + mapTemplate.get(CDT));
        }
    }

    private void createTextView(LinearLayout linearLayout, String text) {
        if (text.length() > 0) {
            TextView valueTV = new TextView(mContext);
            valueTV.setText(text);
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            linearLayout.addView(valueTV);
        }
    }
}