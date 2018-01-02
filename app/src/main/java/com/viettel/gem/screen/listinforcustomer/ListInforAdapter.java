package com.viettel.gem.screen.listinforcustomer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.model.SubscriberDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 21/11/2017.
 */

public class ListInforAdapter extends RecyclerView.Adapter<ListInforAdapter.InforHolder> {

    private Context context;
    private List<SubscriberDTO> mListSubcribers;
    private InforClickListener inforClickListener;

    public ListInforAdapter(Context context, List<SubscriberDTO> mListSubcribers, InforClickListener inforClickListener) {
        this.context = context;
        this.mListSubcribers = mListSubcribers;
        this.inforClickListener = inforClickListener;
    }

    @Override
    public InforHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InforHolder(LayoutInflater.from(context).inflate(R.layout.infor_customer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(InforHolder holder, int position) {
        SubscriberDTO subscriberDTO = mListSubcribers.get(position);
        if (null == subscriberDTO) return;

        boolean isCusExisted = subscriberDTO.isCusExisted();

        String name = null == subscriberDTO.getCustomerDTO() ? "" : subscriberDTO.getCustomerDTO().getName();
        String address = null == subscriberDTO.getLstSubInfrastructureDTO() || subscriberDTO.getLstSubInfrastructureDTO().isEmpty()
                ? "" : subscriberDTO.getLstSubInfrastructureDTO().get(0).getAddress();

        holder.mNameTv.setText(name);
        holder.mPhoneTv.setText(subscriberDTO.getIsdn());
        holder.mDescTv.setText(address);

        holder.mInforIv.setImageResource(isCusExisted ? R.drawable.ic_gem_collectinfor_infor_green : R.drawable.ic_gem_collectinfor_infor);
        holder.mInforTv.setText(isCusExisted ? "Đã thu thập thông tin" : "Chưa có thông tin thu thập");
        holder.mInforTv.setTextColor(isCusExisted ? Color.parseColor("#208540") : Color.parseColor("#828282"));
    }

    @Override
    public int getItemCount() {
        return mListSubcribers.size();
    }

    public class InforHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)
        TextView mNameTv;

        @BindView(R.id.phone_tv)
        TextView mPhoneTv;

        @BindView(R.id.desc_tv)
        TextView mDescTv;

        @BindView(R.id.infor_iv)
        ImageView mInforIv;

        @BindView(R.id.infor_tv)
        TextView mInforTv;

        private InforHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inforClickListener.onClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    public interface InforClickListener {
        void onClick(View view, int position);
    }
}
