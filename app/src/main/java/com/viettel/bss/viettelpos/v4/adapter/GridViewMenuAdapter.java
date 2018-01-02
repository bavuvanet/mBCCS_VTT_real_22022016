package com.viettel.bss.viettelpos.v4.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 1/19/2017.
 */

class GridViewMenuAdapter extends BaseAdapter {
    private static final String TAG = "GridViewMenuAdapter";
    private  List<Manager> lstData = new ArrayList<>();
    private final Context mContext;
    private final com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener;

    public GridViewMenuAdapter(Context mContext, List<Manager> lstData, com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;

        this.lstData = lstData;
        if(!CommonActivity.isNullOrEmptyArray(lstData)){
            int columnRow = 4;
            if(lstData.size() % columnRow != 0){
                this.lstData.add(new Manager()); // Bo sung them mot menu tam
            }
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        @SuppressLint("InflateParams") final View rowView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_grid_view_item_menu, null);
        final ViewHolder holder = new ViewHolder();
        holder.textView = (TextView) rowView.findViewById(R.id.tvTitle);
        holder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
        holder.lnLineRight = (LinearLayout) rowView.findViewById(R.id.lnLineRight);
        holder.lnLineBottom = (LinearLayout) rowView.findViewById(R.id.lnLineBottom);

        final Manager manager = lstData.get(position);
        holder.manager = manager;

        holder.textView.setText(manager.getNameManager());

//        if(manager.getResIcon() != 0) {
//            Picasso.with(mContext).load(manager.getResIcon()).into(holder.imageView);
//        }
//        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), manager.getResIcon());
//        holder.imageView.setImageBitmap(bm);

        holder.imageView.setImageResource(manager.getResIcon());
//        new Thread(new Runnable() {
//            public void run() {
//                InputStream is = mContext.getResources().openRawResource(manager.getResIcon());
//                final Bitmap imageBitmap = BitmapFactory.decodeStream(is);
//                rowView.post( new Runnable() {
//                    public void run() {
//                        holder.imageView.setBackgroundResource(imageBitmap);
//                    }
//                });
//            }
//        }).start();
        int size = lstData.size();
        if (position % 4 == 3 && (position != (size - 1))) {
            holder.lnLineRight.setVisibility(View.VISIBLE);
        }

        if (position == (size - 1) && (position % 4 != 3)) {
            holder.lnLineBottom.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }
        final String keyMenuName = manager.getKeyMenuName();
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holder.manager);
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lstData == null ? 0 : lstData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return lstData == null ? null : (lstData.isEmpty() ? null : lstData.get(position));
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public List<Manager> getLstData() {
        return lstData;
    }

    public void setLstData(List<Manager> lstData) {
        this.lstData = lstData;
        if(!CommonActivity.isNullOrEmptyArray(lstData)){
            int columnRow = 4;
            if(lstData.size() % columnRow != 0){
                this.lstData.add(new Manager()); // Bo sung them mot menu tam
            }
        }
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
        Manager manager;
        LinearLayout lnLineRight;
        LinearLayout lnLineBottom;
    }
}
