package com.viettel.bss.viettelpos.v4.bankplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bankplus.object.TransactionDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 8/3/2017.
 */

public class RecyclerViewTransactionAdapter
        extends RecyclerView.Adapter<RecyclerViewTransactionAdapter.ViewHolder> {
    private List<TransactionDTO> testDTOs;
    private Context context;

    public RecyclerViewTransactionAdapter(Context context,
                                          List<TransactionDTO> testDTO) {
        this.context = context;
        this.testDTOs = testDTO;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionDTO dto = testDTOs.get(position);
        holder.txtIdTrans.setText("Mã yêu cầu: " + dto.getIdTransaction());
        holder.txtMoney.setText(dto.getNumberMoney());
        holder.txtPhone.setText(dto.getPhoneNumberCus());
        holder.txtAddress.setText(dto.getAddressCus());
        holder.txtExpire.setText("còn " + dto.timeToString());
        holder.txtStatus.setText(dto.getStatus());
    }

    @Override
    public int getItemCount() {
        return testDTOs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtIdTrans)
        TextView txtIdTrans;
        @BindView(R.id.txtMoney)
        TextView txtMoney;
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        @BindView(R.id.txtAddress)
        TextView txtAddress;
        @BindView(R.id.txtExpire)
        TextView txtExpire;
        @BindView(R.id.txStatus)
        TextView txtStatus;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
