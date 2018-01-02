package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.EditCustomerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 14/09/2017.
 */

public class CustIdentityDTOAdapter extends RecyclerView.Adapter<CustIdentityDTOAdapter.ViewHolder> {
    int FADE_DURATION = 1000;
    private ArrayList<CustIdentityDTO> lstcustIdentityDTOs;
    private Activity context;
    private EditCustomerDialog editCustomerDialog;
    private View.OnClickListener moveLogInAct;
    private ConnectionOrder connectionOrder;
    private FragmentManager ft;

    public CustIdentityDTOAdapter(Activity context, ArrayList<CustIdentityDTO> lstcustIdentityDTOs, View.OnClickListener moveLogInAct, ConnectionOrder connectionOrder) {
        this.lstcustIdentityDTOs = lstcustIdentityDTOs;
        this.context = context;
        this.connectionOrder = connectionOrder;
        this.moveLogInAct = moveLogInAct;
//        this.ft = ft;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_omichanel_customer_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CustIdentityDTO custIdentityDTO = lstcustIdentityDTOs.get(position);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
            holder.txtCustName.setText(custIdentityDTO.getCustomer().getName());
            holder.txtBirthDate.setText(DateTimeUtils.convertDateSoap(custIdentityDTO.getCustomer().getBirthDate()).replaceAll("-", "/"));
            holder.txtAddress.setText(custIdentityDTO.getCustomer().getAddress());
        }
        holder.txtIdNo.setText(custIdentityDTO.getIdNo());
        holder.txtIdIssueDate.setText(DateTimeUtils.convertDateSoap(custIdentityDTO.getIdIssueDate()).replaceAll("-", "/"));
        holder.txtIdIssuePlace.setText(custIdentityDTO.getIdIssuePlace());
        holder.imgEditCusInfo.setVisibility(View.GONE);
        holder.imgEditCusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCustomerDialog = new EditCustomerDialog(context, moveLogInAct, connectionOrder, custIdentityDTO);
                editCustomerDialog.show(context.getFragmentManager(), "dialog fragment EditCus");
            }
        });
        setScaleAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return lstcustIdentityDTOs.size();
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCustName)
        TextView txtCustName;
        @BindView(R.id.txtIdNo)
        TextView txtIdNo;
        @BindView(R.id.txtIdIssueDate)
        TextView txtIdIssueDate;
        @BindView(R.id.txtIdIssuePlace)
        TextView txtIdIssuePlace;
        @BindView(R.id.txtBirthDate)
        TextView txtBirthDate;
        @BindView(R.id.tvAddress)
        TextView txtAddress;
        @BindView(R.id.imgEditCusInfo)
        ImageView imgEditCusInfo;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

