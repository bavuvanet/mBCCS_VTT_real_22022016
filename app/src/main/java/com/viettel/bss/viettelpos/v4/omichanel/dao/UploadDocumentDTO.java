package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ToanLD2 on 5/26/2015.
 */
public class UploadDocumentDTO implements Serializable{

    private ArrayList<RecordTypeScanDTO> listRecord; // moi list la mot spin
    private String documentType;
    private String recordNameScan;
    private UploadedFileDTO uploadedFileDTO;
    private String isRecordRequired;//phai chuyen sang string vi eo hieu tai sao k dung dc boolen tren giao dien :(
    private RecordTypeScanDTO recordTypeScanSelected;
    private String fileExisted = "0";
    private boolean fakeImage;

    private Long actionProfileId;
    private Long recordId;
    private Long receptionId;

    private ArrayList<ProfileRecord> lstProfileRecordInit;
    private String urlImage;
    private String processInstanceId; //Id Process

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    // xu ly profile da chon
    public void proccessProfileRecordSelect() {
        if (CommonActivity.isNullOrEmpty(listRecord)) {
            return;
        }
        if (recordTypeScanSelected == null) {
            listRecord.get(0).setSelected(true);
            return;
        }
        for (RecordTypeScanDTO recordTypeScanDTO : listRecord) {
            if (recordTypeScanDTO.getRecordCode().equals(recordTypeScanSelected.getRecordCode())) {
                recordTypeScanDTO.setSelected(true);
            }
        }
    }

    public ArrayList<ProfileRecord> getLstProfileRecordInit() {
        if (lstProfileRecordInit == null) {
            lstProfileRecordInit = new ArrayList<>();
        }
        return lstProfileRecordInit;
    }

    public void setLstProfileRecordInit(ArrayList<ProfileRecord> lstProfileRecordInit) {
        this.lstProfileRecordInit = lstProfileRecordInit;
    }

    public UploadedFileDTO getUploadedFileDTO() {
        return uploadedFileDTO;
    }

    public void setUploadedFileDTO(UploadedFileDTO uploadedFileDTO) {
        this.uploadedFileDTO = uploadedFileDTO;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public ArrayList<RecordTypeScanDTO> getListRecord() {
        return listRecord;
    }

    public void setListRecord(ArrayList<RecordTypeScanDTO> listRecord) {
        this.listRecord = listRecord;
    }

    public String getIsRecordRequired() {
        return isRecordRequired;
    }

    public void setIsRecordRequired(String isRecordRequired) {
        this.isRecordRequired = isRecordRequired;
    }

    public RecordTypeScanDTO getRecordTypeScanSelected() {
        return recordTypeScanSelected;
    }

    public void setRecordTypeScanSelected(RecordTypeScanDTO recordTypeScanSelected) {
        this.recordTypeScanSelected = recordTypeScanSelected;
    }

    @Override
    public String toString() {
        return "UploadDocumentDTO{" +
                "listRecord=" + listRecord +
                ", documentType='" + documentType + '\'' +
                '}';
    }

    public Long getActionProfileId() {
        return actionProfileId;
    }

    public void setActionProfileId(Long actionProfileId) {
        this.actionProfileId = actionProfileId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getRecordNameScan() {
        return recordNameScan;
    }

    public void setRecordNameScan(String recordNameScan) {
        this.recordNameScan = recordNameScan;
    }

    public Long getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Long receptionId) {
        this.receptionId = receptionId;
    }

    public String getFileExisted() {
        return fileExisted;
    }

    public void setFileExisted(String fileExisted) {
        this.fileExisted = fileExisted;
    }

    public boolean isFakeImage() {
        return fakeImage;
    }

    public void setFakeImage(boolean fakeImage) {
        this.fakeImage = fakeImage;
    }

    public Long getSourceId() {
        if (CommonActivity.isNullOrEmpty(listRecord)) {
            return 0l;
        }
        return listRecord.get(0).getSourceId();
    }
}
