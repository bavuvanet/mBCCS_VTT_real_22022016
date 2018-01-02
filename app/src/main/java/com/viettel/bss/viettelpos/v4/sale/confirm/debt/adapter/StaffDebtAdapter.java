package com.viettel.bss.viettelpos.v4.sale.confirm.debt.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.ConfirmDebitTransDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huypq15 on 4/25/2017.
 */

public class StaffDebtAdapter extends BaseAdapter {
    private List<ConfirmDebitTransDTO> lstData;
    private Activity act;

    public StaffDebtAdapter(Activity act, List<ConfirmDebitTransDTO> lstData) {
        this.lstData = lstData;
        this.act = act;
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return lstData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = act.getLayoutInflater().inflate(R.layout.debt_staff_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ConfirmDebitTransDTO item = lstData.get(position);
        holder.tvDebtType.setText(item.getDebitTypeDetailName());
//        Long startDebt = item.getStaOfCycleAmount() == null ? 0L : item.getStaOfCycleAmount();
//        Long cycleAmount = item.getBillCycleAmount() == null ? 0L : item.getBillCycleAmount();
//        Long confirmed = item.getPayCycleAmount() == null ? 0L : item.getPayCycleAmount();

//        Long needConfirm = startDebt + cycleAmount - confirmed;
        holder.tvNeedConfirm.setText(act.getString(R.string.need_confirm_debt, StringUtils.formatMoney(item.getEndCycleAmount() + "")));
        Long confirmPay = item.getConfirmPay() == null ? 0L : item.getConfirmPay();

        holder.tvConfirmed.setText(act.getString(R.string.confirm, StringUtils.formatMoney(confirmPay + "")));
        long different = item.getPayDifferent();
//        if(different <0){
//            different = 0-different;
//        }
        holder.tvDifferent.setText(act.getString(R.string.different, StringUtils.formatMoney(different + "")));
        if(different >0){
            holder.tvReason.setVisibility(View.VISIBLE);
            holder.tvReason.setText(act.getString(R.string.reason,item.getPayDifferentReason()));
        }else{
            holder.tvReason.setVisibility(View.GONE);
        }
        if(position == lstData.size()-1){

                holder.tvReason.setVisibility(View.GONE);
                holder.img.setVisibility(View.GONE);

        }else{
            holder.img.setVisibility(View.VISIBLE);
        }


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvDebtType)
         TextView tvDebtType;
        @BindView(R.id.tvNeedConfirm)
         TextView tvNeedConfirm;
        @BindView(R.id.tvConfirmed)
         TextView tvConfirmed;
        @BindView(R.id.tvDifferent)
         TextView tvDifferent;
        @BindView(R.id.tvReason)
         TextView tvReason;
        @BindView(R.id.imageView)
        View img;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
