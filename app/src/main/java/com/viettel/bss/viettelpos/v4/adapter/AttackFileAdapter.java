package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toancx on 2/21/2017.
 */

public class AttackFileAdapter extends BaseAdapter {
    private final Context mContext;
    private List<FileObj> lstFileObjs = new ArrayList<>();
    private View.OnClickListener onClickSelectImage;
    private View.OnClickListener onClickDelete;


    public AttackFileAdapter(Context context, List<FileObj> lstRecordBeans, View.OnClickListener onClickSelectImage, View.OnClickListener onClickDelete) {
        this.mContext = context;
        this.lstFileObjs = lstRecordBeans;
        this.onClickSelectImage = onClickSelectImage;
        this.onClickDelete = onClickDelete;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return CommonActivity.isNullOrEmptyArray(lstFileObjs) ? 0 : lstFileObjs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return CommonActivity.isNullOrEmptyArray(lstFileObjs) ? null : lstFileObjs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        FileObj fileObj = lstFileObjs.get(position);
        if (v == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            v = inflater.inflate(R.layout.layout_attack_file, parent,
                    false);

            holder = new ViewHolder();
            holder.tvItemName = (TextView) v.findViewById(R.id.tvItemName);
            holder.imgUploadImage = (ImageButton) v
                    .findViewById(R.id.imgUploadImage);

            holder.imgUploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickSelectImage != null) {
                        onClickSelectImage.onClick(view);
                    }
                }
            });

            holder.lnDelete = (LinearLayout) v.findViewById(R.id.lnDelete);
            holder.lnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickDelete != null){
                        onClickDelete.onClick(view);
                    }
                }
            });

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (fileObj != null) {
            holder.tvItemName.setText(fileObj.getName());
            if(!CommonActivity.isNullOrEmpty(fileObj.getPath())){
                Picasso.with(mContext)
                        .load(new File(fileObj.getPath()))
                        .centerCrop().resize(100, 100)
                        .into(holder.imgUploadImage);
            } else {
                holder.imgUploadImage.setImageResource(R.drawable.logo_vt);
            }
        }

        holder.imgUploadImage.setId(fileObj.getId());
        holder.imgUploadImage.setTag(position);

        holder.lnDelete.setId(fileObj.getId());
        holder.lnDelete.setTag(position);
        return v;
    }

    public class ViewHolder {
        public TextView tvItemName;
        public ImageButton imgUploadImage;
        public LinearLayout lnDelete;
    }

}
