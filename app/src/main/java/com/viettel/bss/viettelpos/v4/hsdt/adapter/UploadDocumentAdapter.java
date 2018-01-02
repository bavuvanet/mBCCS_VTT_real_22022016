package com.viettel.bss.viettelpos.v4.hsdt.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.omichanel.dao.UploadDocumentDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 11/21/2017.
 */

public class UploadDocumentAdapter extends RecyclerView.Adapter<UploadDocumentAdapter.ViewHolder> {

    private Activity activity;
    private List<UploadDocumentDTO> uploadFilesSign;
    private Map<String, List<UploadDocumentDTO>> mapUploadFilesSign = new HashMap<>();
    private List<String> listIsdnOrDerDesc = new ArrayList<>();
    private List<String> listKey = new ArrayList<>();
    private View.OnClickListener moveLogInAct;

    public UploadDocumentAdapter(Activity activity, List<UploadDocumentDTO> uploadFilesSign, View.OnClickListener moveLogInAct) {

        this.activity = activity;
        this.uploadFilesSign = uploadFilesSign;
        this.moveLogInAct = moveLogInAct;

        for(UploadDocumentDTO uploadDocumentDTO:uploadFilesSign){
            String isdnOrderDesc = uploadDocumentDTO.getListRecord().get(0).getArrOrderInfo().get(0).getIsdn()
                    + " - " + uploadDocumentDTO.getListRecord().get(0).getArrOrderInfo().get(0).getOrderTypeDesc();
            String OrderId = uploadDocumentDTO.getListRecord().get(0).getArrOrderInfo().get(0).getOrderId();
            List<UploadDocumentDTO> tmp = this.mapUploadFilesSign.get(OrderId);
            if(CommonActivity.isNullOrEmpty(tmp)) {
                tmp=new ArrayList<>();
                listIsdnOrDerDesc.add(isdnOrderDesc);
                listKey.add(OrderId);
            }
            tmp.add(uploadDocumentDTO);
            this.mapUploadFilesSign.put(OrderId,tmp);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upload_document, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String OrderId = listKey.get(position);
        String isdnOrderDesc = listIsdnOrDerDesc.get(position);
        List<UploadDocumentDTO> uploadDocumentDTOs = mapUploadFilesSign.get(OrderId);
        holder.tvIsdn.setText(isdnOrderDesc + " (" + uploadDocumentDTOs.size() + ") ");
        holder.imShow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_add));
        holder.imShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.recyclerView.getVisibility() == View.GONE) {
                    holder.imShow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_remove));
                    holder.recyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.imShow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_add));
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });



        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        holder.recyclerView.setVisibility(View.GONE);
        DocumentAdapter documentAdapter = new DocumentAdapter(activity, uploadDocumentDTOs, moveLogInAct);
        holder.recyclerView.setAdapter(documentAdapter);

        if(getItemCount() == 1 ){
            holder.imShow.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_remove));
            holder.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listKey.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIsdn)
        public TextView tvIsdn;
        @BindView(R.id.imShow)
        public ImageView imShow;
        @BindView(R.id.recyclerView)
        public RecyclerView recyclerView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setTag(this);
        }
    }
}
