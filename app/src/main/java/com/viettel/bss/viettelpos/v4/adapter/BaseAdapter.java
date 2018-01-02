package com.viettel.bss.viettelpos.v4.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is base for Adapter of RecyclerView.
 * Created by NHAT on 11/06/2015.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    protected AdapterView.OnItemClickListener mOnItemClickListener;
    protected List<T> mDatas;
    protected int mItemId;

    public BaseAdapter(@LayoutRes final int resID) {
        mDatas = new ArrayList<>();
        mItemId = resID;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View root = inflater.inflate(mItemId, viewGroup, false);
        return getViewHolder(root);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        T data = mDatas.get(position);
        viewHolder.setData(data);
    }

    /**
     * Sub class implement this method.
     *
     * @return ViewHolder of RecyclerView.ViewHolder.
     */
    public abstract BaseViewHolder<T> getViewHolder(View view);

    @Override
    public int getItemCount() {
        int count = mDatas != null ? mDatas.size() : 0;
        return count;
    }

    public T getItem(int position) {
        if (mDatas != null && mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }

    /**
     * set list data for Adapter.
     *
     * @param objs list of T.
     */
    public void setDatas(final List<T> objs) {
        mDatas.clear();
        if (objs != null) {
            mDatas.addAll(objs);
        }
        setDataChanged();
    }

    /**
     * set list data for Adapter.
     *
     * @param objs list of T.
     */
    public void setDatas(final T[] objs) {
        mDatas.clear();
        if (objs != null) {
            for (T obj : objs) {
                if (obj != null) {
                    mDatas.add(obj);
                }
            }
        }
        setDataChanged();
    }

    //    @UiThread
    public void setDataChanged() {
        notifyDataSetChanged();
    }


    /**
     * append item to list
     *
     * @param obj the object
     */
    public void appendItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
            setDataChanged();
        }
    }

    public void removeItem(int position) {
        if (mDatas != null && mDatas.size() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void changeItem(T obj, int position) {
        if (mDatas != null && mDatas.size() > position) {
            mDatas.set(position, obj);
            notifyItemChanged(position);
        }
    }

    public void setSelectPosition(int position) {
        if (position < getItemCount()) {
            notifyItemChanged(position);
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * Listener when click a item of list. This is call back to parent.
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * call back from ViewHolder.
     */
    public void onItemHolderClick(BaseViewHolder itemHolder) {
        if (itemHolder != null) {
            onItemHolderClick(itemHolder, itemHolder.getLayoutPosition());
        }
    }

    /**
     * call back from ViewHolder.
     */
    public void onItemHolderClick(BaseViewHolder itemHolder, int position) {
        if (mOnItemClickListener != null && itemHolder != null) {
            itemHolder.itemView.setTag(mDatas.get(position));
            mOnItemClickListener.onItemClick(null, itemHolder.itemView, position, itemHolder.getItemId());
        }
    }
}
