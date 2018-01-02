package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.EditBundleFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 9/14/2017.
 */

public class ProductBundleAdapter extends
        RecyclerView.Adapter<ProductBundleAdapter.ViewHolder> {

    private Activity activity;
    private String buttonName;
    private EditBundleFragment editBundleFragment;
    private List<PoCatalogOutsideDTO> poCatalogOutsideDTOs;

    public ProductBundleAdapter(
            Activity activity, String buttonName,
            EditBundleFragment editBundleFragment,
            List<PoCatalogOutsideDTO> poCatalogOutsideDTOs) {

        this.activity = activity;
        this.buttonName = buttonName;
        this.editBundleFragment = editBundleFragment;
        this.poCatalogOutsideDTOs = poCatalogOutsideDTOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_bundle_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PoCatalogOutsideDTO poCatalogOutsideDTO = poCatalogOutsideDTOs.get(position);

        holder.tvName.setText(poCatalogOutsideDTO.getName());
        holder.tvPrice.setText(activity.getString(R.string.bundle_price,
                StringUtils.formatAbsMoney(poCatalogOutsideDTO.getPrice())));
        holder.tvDescription.setText(poCatalogOutsideDTO.getDescription());
        holder.btnChange.setText(buttonName);

        if (poCatalogOutsideDTO.isSelected()) {
            holder.btnChange.setEnabled(false);
        } else {
            holder.btnChange.setEnabled(true);
        }

        holder.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBundleFragment.doSaveProductBundle(poCatalogOutsideDTO);
            }
        });

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editBundleFragment.doSaveProductBundle(poCatalogOutsideDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poCatalogOutsideDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llContent)
        LinearLayout llContent;
        @BindView(R.id.btnChange)
        Button btnChange;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvDescription)
        TextView tvDescription;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}