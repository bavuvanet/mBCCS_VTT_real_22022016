package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.object.GridMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 1/19/2017.
 */

public class RecyclerGridMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<GridMenu> lstData;
    private final Context mContext;
    private final com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener;
    GridViewMenuAdapter menuAdapter;
    public RecyclerGridMenuAdapter(Context mContext, List<GridMenu> lstData, com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.lstData = lstData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_view, parent, false);
        return new GridMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridMenu gridMenu = lstData.get(position);
        GridMenuViewHolder gridMenuViewHolder = (GridMenuViewHolder) holder;

        gridMenuViewHolder.tvTitile.setText(gridMenu.getTitle());
        if(!CommonActivity.isNullOrEmptyArray(gridMenu.getLstData())){
            menuAdapter.setLstData(gridMenu.getLstData());
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class GridMenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitile;
        @BindView(R.id.gridView)
        GridView gridView;

        public GridMenuViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            menuAdapter = new GridViewMenuAdapter(mContext, new ArrayList<Manager>(), onItemClickListener);
            gridView.setAdapter(menuAdapter);
            gridView.setVerticalScrollBarEnabled(false);
        }
    }
}
