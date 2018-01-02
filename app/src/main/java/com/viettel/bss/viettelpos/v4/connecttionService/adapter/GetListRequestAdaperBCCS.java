package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v3.connecttionService.model.CustomerOrderDetailDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailMbccsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinhhq1 on 3/23/2017.
 */
public class GetListRequestAdaperBCCS extends RecyclerView.Adapter<GetListRequestAdaperBCCS.ViewHolder> {
    private List<CustomerOrderDetailMbccsDTO> lstData = new ArrayList<>();

    private OnClickRequestBCCS onClickItemRequestBCCS;
    private Activity context;
    private String permistion;
    HashMap<String, String> mapTelecomService;

    public interface OnClickRequestBCCS {
        void onClickRequest(CustomerOrderDetailMbccsDTO customerOrderDetailDTO, int id);
    }

    public GetListRequestAdaperBCCS(Activity mContext, List<CustomerOrderDetailMbccsDTO> lstCustomerOrderDetailDTOs
            , OnClickRequestBCCS onClickItemRequestBCCS, HashMap<String, String> mapTelecomService, String permistion) {
        this.lstData = lstCustomerOrderDetailDTOs;
        this.onClickItemRequestBCCS = onClickItemRequestBCCS;
        this.context = mContext;
        this.mapTelecomService = mapTelecomService;
        this.permistion = permistion;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_request_bccs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CustomerOrderDetailMbccsDTO customerOrderDetailMbccsDTO = this.getLstData().get(position);
        final CustomerOrderDetailDTO customerOrderDetailDTO = customerOrderDetailMbccsDTO.getCustomerOrderDetail();
        holder.tvOrder.setText("" + (position + 1));
        holder.tvidrequest.setText(customerOrderDetailDTO.getCustOrderDetailId() + "");
        if (!CommonActivity.isNullOrEmpty(customerOrderDetailDTO.getIsdnAccount())) {
            holder.tvIsdnAccount.setText(customerOrderDetailDTO.getIsdnAccount());
            holder.viewIsdnAccount.setVisibility(View.VISIBLE);
        } else holder.viewIsdnAccount.setVisibility(View.GONE);
        holder.tvService.setText(mapTelecomService.get(customerOrderDetailDTO.getTelecomServiceId() + ""));
        holder.tvDateRequest.setText(DateTimeUtils.convertDateSoapToNewFormat(customerOrderDetailDTO.getEffectDate(),
                "HH:mm:ss_dd/MM/yyy"));
        holder.tvContactName.setText(customerOrderDetailDTO.getContactName());
        String statusName = "";

        switch (customerOrderDetailDTO.getStatus()) {
            case "02":
                statusName = context.getString(R.string.statusRq02);
                break;
            case "03":
                statusName = context.getString(R.string.statusRq03);
                break;
            case "06":
                statusName = context.getString(R.string.statusRq06);
                break;
            case "12":
                statusName = context.getString(R.string.statusRq12);
                break;
            case "33":
                statusName = context.getString(R.string.statusRq33);
                break;
            case "36":
                statusName = context.getString(R.string.statusRq36);
                break;
            case "48":
                statusName = context.getString(R.string.statusRq48);
                break;
            default:
                break;
        }
        if (!CommonActivity.isNullOrEmpty(customerOrderDetailMbccsDTO.getLstSubInfrastructureS())) {
            holder.tvBoxCode.setText(customerOrderDetailMbccsDTO.getLstSubInfrastructureS().get(0).getCableBoxCode());
        }
        if (!CommonActivity.isNullOrEmpty(customerOrderDetailMbccsDTO.getResultSurveyOnlineForBccs2Form())) {
            String tech = customerOrderDetailMbccsDTO.getResultSurveyOnlineForBccs2Form().getInfratypeName(context);
            holder.tvTech.setText(tech);
        }

        if (!CommonActivity.isNullOrEmpty(customerOrderDetailDTO.getTelMobile())) {
            holder.tvPhone.setText(customerOrderDetailDTO.getTelMobile());
        }
        holder.tvStatusRequest.setText(statusName);
        if (",12,33,36,".contains(customerOrderDetailDTO.getStatus()) && permistion.contains("sale_dev_fix_huyyc")
                && ",00,915,549,".contains("," + customerOrderDetailDTO.getActionCode() + ",")) {
            holder.btndelete.setVisibility(View.VISIBLE);
        } else if (",33,36,".contains(customerOrderDetailDTO.getStatus()) && permistion.contains("sale_cancel_request_connect")
                && ",00,915,549,".contains("," + customerOrderDetailDTO.getActionCode() + ",")) {
             holder.btndelete.setVisibility(View.VISIBLE);
        }else{
              holder.btndelete.setVisibility(View.GONE);
        }


//        if (",12".contains(customerOrderDetailDTO.getStatus()) && permistion.contains("sale_cancel_request_connect")
//                && ",00,915,549,".contains("," + customerOrderDetailDTO.getActionCode() + ",")) {
//            holder.btndelete.setVisibility(View.VISIBLE);
//        } else holder.btndelete.setVisibility(View.GONE);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemRequestBCCS.onClickRequest(customerOrderDetailMbccsDTO, R.id.btnDelete);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemRequestBCCS.onClickRequest(customerOrderDetailMbccsDTO, R.layout.item_manage_request_bccs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvOrder)
        TextView tvOrder;
        @BindView(R.id.tvidrequest)
        TextView tvidrequest;
        @BindView(R.id.tvIsdnAccount)
        TextView tvIsdnAccount;
        @BindView(R.id.tvContactName)
        TextView tvContactName;
        @BindView(R.id.viewIdnAccount)
        LinearLayout viewIsdnAccount;
        @BindView(R.id.tvcreater)
        TextView tvcreater;
        @BindView(R.id.tvService)
        TextView tvService;
        @BindView(R.id.tvDateRequest)
        TextView tvDateRequest;
        @BindView(R.id.tvStatusRequest)
        TextView tvStatusRequest;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvTech)
        TextView tvTech;
        @BindView(R.id.tvBoxCode)
        TextView tvBoxCode;
        @BindView(R.id.btndelete)
        LinearLayout btndelete;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public List<CustomerOrderDetailMbccsDTO> getLstData() {
        return lstData;
    }

    public void setLstData(List<CustomerOrderDetailMbccsDTO> lstData) {
        this.lstData = lstData;
    }
}
