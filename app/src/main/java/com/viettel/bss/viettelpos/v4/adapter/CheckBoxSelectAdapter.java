package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.Attr;
import com.viettel.bss.viettelpos.v4.bo.Criteria;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.listener.*;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 5/17/2017.
 */
public class CheckBoxSelectAdapter extends RecyclerView.Adapter<CheckBoxSelectAdapter.ViewHolder>{
    private Activity mActivity;
    private List<List<Criteria>> lstData;
    private com.viettel.bss.viettelpos.v4.listener.OnItemClickListener onItemClickListener;

    public CheckBoxSelectAdapter(Activity mActivity, List<List<Criteria>> lstData, OnItemClickListener onItemClickListener) {
        this.mActivity = mActivity;
        this.lstData = lstData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkbox_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<Criteria> attr = lstData.get(position);
        if(attr.size() == 1){
            holder.checkbox1.setText(attr.get(0).getName());
            holder.checkbox1.setChecked(attr.get(0).getChecked());

            holder.checkbox2.setVisibility(View.GONE);
            holder.checkbox3.setVisibility(View.GONE);
            holder.checkbox4.setVisibility(View.GONE);
        } else if(attr.size() == 2){
            holder.checkbox1.setText(attr.get(0).getName());
            holder.checkbox1.setChecked(attr.get(0).getChecked());

            holder.checkbox2.setText(attr.get(1).getName());
            holder.checkbox2.setChecked(attr.get(1).getChecked());

            holder.checkbox3.setVisibility(View.GONE);
            holder.checkbox4.setVisibility(View.GONE);
        } else if (attr.size() == 3){
            holder.checkbox1.setText(attr.get(0).getName());
            holder.checkbox1.setChecked(attr.get(0).getChecked());

            holder.checkbox2.setText(attr.get(1).getName());
            holder.checkbox2.setChecked(attr.get(1).getChecked());

            holder.checkbox3.setText(attr.get(2).getName());
            holder.checkbox3.setChecked(attr.get(2).getChecked());

            holder.checkbox4.setVisibility(View.GONE);
        } else if(attr.size() == 4){
            holder.checkbox1.setText(attr.get(0).getName());
            holder.checkbox1.setChecked(attr.get(0).getChecked());

            holder.checkbox2.setText(attr.get(1).getName());
            holder.checkbox2.setChecked(attr.get(1).getChecked());

            holder.checkbox3.setText(attr.get(2).getName());
            holder.checkbox3.setChecked(attr.get(2).getChecked());

            holder.checkbox4.setText(attr.get(3).getName());
            holder.checkbox4.setChecked(attr.get(3).getChecked());
        }

        holder.checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attr.get(0).setChecked(holder.checkbox1.isChecked());
                onItemClickListener.onItemClick(attr);
            }
        });

        holder.checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attr.get(1).setChecked(holder.checkbox2.isChecked());
                onItemClickListener.onItemClick(attr);
            }
        });

        holder.checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attr.get(2).setChecked(holder.checkbox3.isChecked());
                onItemClickListener.onItemClick(attr);
            }
        });

        holder.checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attr.get(3).setChecked(holder.checkbox4.isChecked());
                onItemClickListener.onItemClick(attr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.checkbox1)
        CheckBox checkbox1;
        @BindView(R.id.checkbox2)
        CheckBox checkbox2;
        @BindView(R.id.checkbox3)
        CheckBox checkbox3;
        @BindView(R.id.checkbox4)
        CheckBox checkbox4;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
