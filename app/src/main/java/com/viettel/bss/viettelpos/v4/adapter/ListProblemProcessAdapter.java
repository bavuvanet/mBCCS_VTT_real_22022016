package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.object.ProblemProcessDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toancx on 2/22/2017.
 */

public class ListProblemProcessAdapter extends RecyclerView.Adapter<ListProblemProcessAdapter.ViewHolder>{

    private List<ProblemProcessDTO> lstData= new ArrayList<>();
    private Context mContext;

    public ListProblemProcessAdapter(Context mContext, List<ProblemProcessDTO> lstData){
        this.mContext = mContext;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list_problem_process, parent, false);
        return new ListProblemProcessAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProblemProcessDTO problemProcessDTO = lstData.get(position);
        holder.tvActionName.setText(problemProcessDTO.getActionName());
        holder.tvProcessStaff.setText(problemProcessDTO.getStaffCode());
        holder.tvProcessShop.setText(problemProcessDTO.getShopName());
        holder.tvProcessDate.setText(DateTimeUtils.convertDateSoapToString(
                problemProcessDTO.getProcessDate()));
        holder.tvProcessContent.setText(problemProcessDTO.getProcessContent());
    }

    @Override
    public int getItemCount() {
        return CommonActivity.isNullOrEmptyArray(lstData) ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvActionName)
        TextView tvActionName;
        @BindView(R.id.tvProcessStaff)
        TextView tvProcessStaff;
        @BindView(R.id.tvProcessDate)
        TextView tvProcessDate;
        @BindView(R.id.tvProcessShop)
        TextView tvProcessShop;
        @BindView(R.id.tvProcessContent)
        TextView tvProcessContent;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
