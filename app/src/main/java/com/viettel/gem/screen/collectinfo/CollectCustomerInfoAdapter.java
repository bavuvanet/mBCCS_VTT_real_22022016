package com.viettel.gem.screen.collectinfo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.gem.screen.collectinfo.custom.GroupBoxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BaVV on 11/22/17.
 */
public class CollectCustomerInfoAdapter
        extends RecyclerView.Adapter<CollectCustomerInfoAdapter.CollectCustomerInfoViewHolder> {

    private List<ProductSpecificationDTO> mItems;

    private Callback mCallback;

    private Activity activity;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null != mCallback) {
                CollectCustomerInfoViewHolder holder = (CollectCustomerInfoViewHolder) v.getTag();
                mCallback.onGroupSelected(holder.productSpecificationDTO);
            }
        }
    };

    public CollectCustomerInfoAdapter(Callback callback, Activity activity) {
        mCallback = callback;
        this.activity = activity;
    }

    @Override
    public CollectCustomerInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_collect_cus_info_item_view, parent, false);
        return new CollectCustomerInfoViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(CollectCustomerInfoViewHolder holder, int position) {
        ProductSpecificationDTO model = mItems.get(position);
        holder.productSpecificationDTO = model;
        holder.groupBoxView.build(model, activity);
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    public void notifyItemAdded(ProductSpecificationDTO productSpecificationDTO) {
        if (null == mItems) {
            mItems = new ArrayList<>();
        }

        mItems.add(productSpecificationDTO);
        this.notifyItemInserted(0);
    }

    public void notifyDataSetChanged(List<ProductSpecificationDTO> productSpecificationDTOList) {
        if (null == mItems) {
            mItems = new ArrayList<>();
        } else {
            mItems.clear();
        }
        if (null != productSpecificationDTOList && !productSpecificationDTOList.isEmpty()) {
            mItems.addAll(productSpecificationDTOList);
        }

        this.notifyDataSetChanged();
    }

    class CollectCustomerInfoViewHolder extends RecyclerView.ViewHolder {

        public ProductSpecificationDTO productSpecificationDTO;

        @BindView(R.id.groupBoxView)
        GroupBoxView groupBoxView;

        public CollectCustomerInfoViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    public interface Callback {
        void onGroupSelected(ProductSpecificationDTO productSpecificationDTO);
    }
}
