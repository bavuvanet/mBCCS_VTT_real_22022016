package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.EditVasPlusFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 9/14/2017.
 */

public class ProductVasPlusAdapter extends
        RecyclerView.Adapter<ProductVasPlusAdapter.ViewHolder> {

    private Context context;
    private EditVasPlusFragment editVasPlusFragment;
    private List<PoCatalogOutsideDTO> poCatalogOutsideDTOs;

    public ProductVasPlusAdapter(
            Context context,
            EditVasPlusFragment editVasPlusFragment,
            List<PoCatalogOutsideDTO> poCatalogOutsideDTOs) {

        this.context = context;
        this.editVasPlusFragment = editVasPlusFragment;
        this.poCatalogOutsideDTOs = poCatalogOutsideDTOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_vas_plus_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final PoCatalogOutsideDTO poCatalogOutsideDTO = poCatalogOutsideDTOs.get(position);

        if (poCatalogOutsideDTO.isSelected()) {
            holder.cbSelectState.setChecked(true);
        } else {
            holder.cbSelectState.setChecked(false);
        }

        holder.tvCode.setText(poCatalogOutsideDTO.getVasCode());
        holder.tvName.setText(poCatalogOutsideDTO.getVasName());
        holder.tvPrice.setText(StringUtils.formatMoney(poCatalogOutsideDTO.getPrice()));

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editVasPlusFragment.showDetailDialog(poCatalogOutsideDTO);
            }
        });

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editVasPlusFragment.updateVas(position);
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
        @BindView(R.id.btnDetail)
        Button btnDetail;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.cbSelectState)
        CheckBox cbSelectState;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}