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
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VStockNumberOmniDTO;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.EditNumberFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 07/09/2017.
 */

public class VStockNumberOmniDTOAdapter extends
        RecyclerView.Adapter<VStockNumberOmniDTOAdapter.ViewHolder> {

    private Activity activity;
    private EditNumberFragment editNumberFragment;
    private List<VStockNumberOmniDTO> vStockNumberOmniDTOs;
    private boolean isPreNumber;

    public VStockNumberOmniDTOAdapter(
            boolean isPreNumber,
            Activity activity,
            EditNumberFragment editNumberFragment,
            List<VStockNumberOmniDTO> vStockNumberOmniDTOs) {

        this.isPreNumber = isPreNumber;
        this.activity = activity;
        this.editNumberFragment = editNumberFragment;
        this.vStockNumberOmniDTOs = vStockNumberOmniDTOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_omichanel_search_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final VStockNumberOmniDTO vStockNumberOmniDTO = vStockNumberOmniDTOs.get(position);

        if (!CommonActivity.isNullOrEmpty(vStockNumberOmniDTO.getIsdn())) {
            holder.tvNumber.setText(vStockNumberOmniDTO.getIsdn());
        } else {
            holder.tvNumber.setText(activity.getString(R.string.not_have_text));
        }

        if (!CommonActivity.isNullOrEmpty(vStockNumberOmniDTO.getPledgeAmount())) {
            holder.tvPledgeAmount.setText(activity.getString(R.string.fee_contract,
                    StringUtils.formatMoney(vStockNumberOmniDTO.getPledgeAmount())));
        } else {
            holder.tvPledgeAmount.setText(activity.getString(R.string.fee_contract, "0"));
        }
        if (!CommonActivity.isNullOrEmpty(vStockNumberOmniDTO.getPledgeAmount())) {
            holder.tvPledgeTime.setText(activity.getString(R.string.time_contract,
                    vStockNumberOmniDTO.getPledgeTime()));
        } else {
            holder.tvPledgeTime.setText(activity.getString(R.string.time_contract, "0"));
        }

        if (isPreNumber) {
            holder.linPosInfo.setVisibility(View.GONE);
            if (!CommonActivity.isNullOrEmpty(vStockNumberOmniDTO.getPrePrice())) {
                holder.tvPrice.setText(activity.getString(R.string.number_price,
                        StringUtils.formatMoney(vStockNumberOmniDTO.getPrePrice())));
            } else {
                holder.tvPrice.setText(activity.getString(R.string.number_price, "0"));
            }
        } else {
            holder.linPosInfo.setVisibility(View.VISIBLE);
            if (!CommonActivity.isNullOrEmpty(vStockNumberOmniDTO.getPosPrice())) {
                holder.tvPrice.setText(activity.getString(R.string.number_price,
                        StringUtils.formatMoney(vStockNumberOmniDTO.getPosPrice())));
            } else {
                holder.tvPrice.setText(activity.getString(R.string.number_price, "0"));
            }
        }

        holder.btnChoiseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNumberFragment.doSaveNumber(vStockNumberOmniDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vStockNumberOmniDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnChoiseNumber)
        Button btnChoiseNumber;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvPledgeAmount)
        TextView tvPledgeAmount;
        @BindView(R.id.tvPledgeTime)
        TextView tvPledgeTime;
        @BindView(R.id.linPosInfo)
        LinearLayout linPosInfo;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
