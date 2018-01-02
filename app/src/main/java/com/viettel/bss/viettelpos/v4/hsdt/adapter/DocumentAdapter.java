package com.viettel.bss.viettelpos.v4.hsdt.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.PrintRequestOrderForSaleAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.UploadDocumentDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 11/21/2017.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    Activity activity;
    List<UploadDocumentDTO> uploadDocumentDTOs;
    private View.OnClickListener moveLogInAct;

    public DocumentAdapter(Activity activity, List<UploadDocumentDTO> uploadDocumentDTOs, View.OnClickListener moveLogInAct) {
        this.activity = activity;
        this.uploadDocumentDTOs = uploadDocumentDTOs;
        this.moveLogInAct = moveLogInAct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.ibUploadImage.setVisibility(View.GONE);
        holder.btnViewPdf.setVisibility(View.VISIBLE);
        final UploadDocumentDTO uploadDocumentDTO = uploadDocumentDTOs.get(position);

        if (!CommonActivity.isNullOrEmpty(uploadDocumentDTO.getListRecord())) {
            Utils.setDataSpinner(activity, uploadDocumentDTO.getListRecord(), holder.spUploadImage);
        }

        holder.btnViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordTypeScanDTO recordTypeScanDTO = (RecordTypeScanDTO) holder.spUploadImage.getSelectedItem();
                PrintRequestOrderForSaleAsyncTask printRequestAsyncTask = new PrintRequestOrderForSaleAsyncTask(
                        activity,
                        recordTypeScanDTO.getRecordCode(),
                        uploadDocumentDTO.getProcessInstanceId(),
                        new OnPostExecuteListener<File>() {
                            @Override
                            public void onPostExecute(File result, String errorCode, String description) {
                                if ("0".equals(errorCode)) {
                                    CommonActivity.openFile(result, activity);
                                } else {
                                    CommonActivity.createAlertDialog(activity,
                                            description, activity.getString(R.string.app_name)).show();
                                }
                            }
                        }, moveLogInAct
                );
                printRequestAsyncTask.execute();
            }
        });

        holder.spUploadImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RecordTypeScanDTO recordTypeScanDTO = (RecordTypeScanDTO) holder.spUploadImage.getSelectedItem();

                if ("1".equals(recordTypeScanDTO.getReqScan() + "")) {
                    holder.tvRequire.setVisibility(View.VISIBLE);
                } else {
                    holder.tvRequire.setVisibility(View.GONE);
                }

                if ("1".equals(recordTypeScanDTO.getElectronicSign())) {
                    holder.btnViewPdf.setVisibility(View.VISIBLE);
                    holder.ibUploadImage.setVisibility(View.GONE);
                } else {
                    holder.btnViewPdf.setVisibility(View.GONE);
                    holder.ibUploadImage.setVisibility(View.VISIBLE);
                }

                for (RecordTypeScanDTO record : uploadDocumentDTO.getListRecord()) {
                    if (record.getRecordCode().equals(recordTypeScanDTO.getRecordCode())) {
                        recordTypeScanDTO.setSelected(true);
                    } else {
                        recordTypeScanDTO.setSelected(false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadDocumentDTOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.spUploadImage)
        public Spinner spUploadImage;
        @BindView(R.id.ibUploadImage)
        public ImageButton ibUploadImage;
        @BindView(R.id.btnViewPdf)
        public Button btnViewPdf;
        @BindView(R.id.tvRequire)
        public TextView tvRequire;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setTag(this);
        }
    }
}
