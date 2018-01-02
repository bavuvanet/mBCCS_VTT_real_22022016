package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 1/12/2017.
 */

public class ListViewMenuAdapter extends RecyclerView.Adapter<ListViewMenuAdapter.ListViewMenuHolder> {
    private final ArrayList<Manager> lstData;
    private final Activity mContext;
    private final com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener;

    public ListViewMenuAdapter(Activity mContext, ArrayList<Manager> lstData, com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.lstData = lstData;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ListViewMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_menu, parent, false);
        return new ListViewMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewMenuHolder holder, int position) {
        Manager manager = lstData.get(position);
        holder.tvTitle.setText(manager.getNameManager());
        holder.manager = manager;
//        if(manager.getResIcon() != 0) {
//            Picasso.with(mContext).load(manager.getResIcon()).into(holder.imageView);
//        }

        holder.imageView.setImageResource(manager.getResIcon());
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ListViewMenuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        Manager manager;

        public ListViewMenuHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(manager);

                }
            });
            ButterKnife.bind(this, view);
        }


    }

    public Activity getActivity() {
        return mContext;
    }
}
