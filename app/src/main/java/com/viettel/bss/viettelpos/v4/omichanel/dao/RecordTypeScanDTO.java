package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by namdx on 5/22/2015.
 */
public class RecordTypeScanDTO implements Serializable {

    private String recordCode;
    private String recordName;
    private Long reqScan;
    private Long recordType;
    private Long sourceId;
    private String recordCodeFile;
    private String electronicSign; // 0 khong thay the, 1 thay the

    //tao them doi tuong cho dong bo RecordPrepaid
    private boolean isSelected = false;
    private String content;
    private String fullPath;
    private String id;
    private String actions ;
    private HashMap<String, ProfileRecord> ProfileRecordMap = new HashMap<>();
    private ArrayList<OrderInfo> arrOrderInfo = new ArrayList<>();

    public ArrayList<OrderInfo> getArrOrderInfo() {
        return arrOrderInfo;
    }

    public void setArrOrderInfo(ArrayList<OrderInfo> arrOrderInfo) {
        this.arrOrderInfo = arrOrderInfo;
    }

    public HashMap<String, ProfileRecord> getProfileRecordMap() {
        return ProfileRecordMap;
    }

    public void setProfileRecordMap(HashMap<String, ProfileRecord> profileRecordMap) {
        ProfileRecordMap = profileRecordMap;
    }

    public String getElectronicSign() {
        return electronicSign;
    }

    public void setElectronicSign(String electronicSign) {
        this.electronicSign = electronicSign;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public Long getReqScan() {
        if (CommonActivity.isNullOrEmpty(reqScan)) {
            return new Long(0);
        }
        return reqScan;
    }

    public void setReqScan(Long reqScan) {
        this.reqScan = reqScan;
    }

    public Long getRecordType() {
        return recordType;
    }

    public void setRecordType(Long recordType) {
        this.recordType = recordType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getRecordCodeFile() {
        return recordCodeFile;
    }

    public void setRecordCodeFile(String recordCodeFile) {
        this.recordCodeFile = recordCodeFile;
    }

    @Override
    public String toString() {
        return recordName;
    }
}
