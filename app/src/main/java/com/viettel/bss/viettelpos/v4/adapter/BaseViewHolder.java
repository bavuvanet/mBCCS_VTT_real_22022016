package com.viettel.bss.viettelpos.v4.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by NHAT on 11/06/2015.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Adapter set data to view.
     *
     * @param obj T
     */
    public abstract void setData(T obj);
}
