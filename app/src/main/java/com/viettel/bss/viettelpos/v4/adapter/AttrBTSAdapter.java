package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.Attr;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 5/17/2017.
 */
public class AttrBTSAdapter extends RecyclerView.Adapter<AttrBTSAdapter.ViewHolder>{
    private Activity mActivity;
    private List<Attr> lstData;

    public AttrBTSAdapter(Activity mActivity, List<Attr> lstData) {
        this.mActivity = mActivity;
        this.lstData = lstData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attr_bts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attr attr = lstData.get(position);

        holder.tvName.setText(attr.getName());
        holder.tvValue.setText(StringUtils.formatMoney(attr.getValue()));

        AttrExtBTSAdapter adapter = new AttrExtBTSAdapter(mActivity, attr.getAttrExts());
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
