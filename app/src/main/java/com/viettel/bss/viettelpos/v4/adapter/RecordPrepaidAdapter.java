package com.viettel.bss.viettelpos.v4.adapter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.listener.OnImageClickListener;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordPrepaidAdapter extends ArrayAdapter<ArrayList<RecordPrepaid>> {
    private final Activity context;

    private final OnImageClickListener imageListenner;

    public class ViewHolder {
        public Spinner spUploadImage;
        public TextView tvRequire;
        public ImageButton ibUploadImage;
    }

    public RecordPrepaidAdapter(Activity context, List<ArrayList<RecordPrepaid>> _arrayOfArrayList, OnImageClickListener imageListenner) {
        super(context, R.layout.item_spinner_image, _arrayOfArrayList);
        this.context = context;
        this.imageListenner = imageListenner;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_spinner_image, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.spUploadImage = (Spinner) rowView
                    .findViewById(R.id.spUploadImage);
            viewHolder.ibUploadImage = (ImageButton) rowView
                    .findViewById(R.id.ibUploadImage);
            viewHolder.tvRequire = (TextView) rowView
                    .findViewById(R.id.tvRequire);
            rowView.setTag(viewHolder);
        }

        final ArrayList<RecordPrepaid> recordPrepaids = getItem(position);
        final int imageId = Integer.parseInt(recordPrepaids.get(0).getId());
//        List<String> strings = new ArrayList<>();
//        for (RecordPrepaid recordPrepaid : recordPrepaids) {
//            strings.add(recordPrepaid.getName());
//        }

//		Log.d("getView", "position: " + position + " " + recordPrepaids + " " + recordPrepaids.size());

        // fill data
        final ViewHolder holder = (ViewHolder) rowView.getTag();

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
//                android.R.layout.simple_spinner_item, strings);
//        holder.spUploadImage.setAdapter(adapter);

        Utils.setDataSpinner(context, recordPrepaids, holder.spUploadImage);
        final int spnPosition = getPositionSelected(recordPrepaids);
        Log.d("holder.spUploadImage.setSelection", " spnPosition = " + spnPosition + ", recordPrepaids size = " + recordPrepaids.size());
        if(spnPosition > 0) {
            holder.spUploadImage.setSelection(getPositionSelected(recordPrepaids));
        }

        holder.ibUploadImage.setBackgroundResource(R.drawable.logo_vt);
        holder.spUploadImage.setId(imageId);
        holder.ibUploadImage.setId(imageId);

        holder.ibUploadImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordPrepaid record = (RecordPrepaid) holder.spUploadImage.getSelectedItem();
                imageListenner.onClick(record, imageId);


            }
        });

        holder.spUploadImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RecordPrepaid record = (RecordPrepaid) holder.spUploadImage.getSelectedItem();
                if ("1".equals(record.getRequire())) {
                    holder.tvRequire.setVisibility(View.VISIBLE);
                } else {
                    holder.tvRequire.setVisibility(View.GONE);
                }

                for (RecordPrepaid recordPrepaid : recordPrepaids) {
                    if (record.getCode().equals(recordPrepaid.getCode())) {
                        Log.d("spUploadImage.setOnItemSelectedListener", "record.getId().equals = " + record.getCode());
                        recordPrepaid.setIsSelected(true);
                    } else {
                        recordPrepaid.setIsSelected(false);
                    }
                }

                if(record.isUseOldProfile()){
                    holder.ibUploadImage.setBackgroundResource(R.drawable.library_books);
                } else {
                    holder.ibUploadImage.setBackgroundResource(R.drawable.logo_vt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecordPrepaid recordPrepaid = recordPrepaids.get(0);
        if(!CommonActivity.isNullOrEmpty(recordPrepaid.getFullPath())){
            if(holder.ibUploadImage.getWidth() == 0 && holder.ibUploadImage.getHeight() == 0){
                int width = View.MeasureSpec.getSize((int)getContext().getResources().getDimension(R.dimen.icon_height_layout_channel_manager));
                Picasso.with(getContext()).load(new File(recordPrepaid.getFullPath())).centerCrop().resize(width, width)
                        .into(holder.ibUploadImage);
            } else {
                Picasso.with(getContext()).load(new File(recordPrepaid.getFullPath())).centerCrop().resize(holder.ibUploadImage.getWidth(), holder.ibUploadImage.getHeight())
                        .into(holder.ibUploadImage);
            }
        }
        return rowView;
    }

    private int getPositionSelected(final ArrayList<RecordPrepaid> recordPrepaids){
        for(int i =0; i< recordPrepaids.size(); i++){
            if(recordPrepaids.get(i).isSelected()){
                return i;
            }
        }
        return 0;
    }
}