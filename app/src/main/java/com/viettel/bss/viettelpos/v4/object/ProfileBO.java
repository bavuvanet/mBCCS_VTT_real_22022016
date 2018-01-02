package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Toancx on 5/10/2017.
 */
public class ProfileBO implements Serializable {
    private List<Object> lstActionCode;
    private List<Object> lstReasonId;
    private String parValue;// tra truoc la productCode, tra sau la subType
    private String serviceType;
    private String payType;
    private String custType;
    private String idNo;
    private String isdnAccount;
    private String recordCode;
    private String recordId;
    private String type = "0";
    private String fileContent;
    private String fileName;

    private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
    private HashMap<String, ArrayList<FileObj>> hashmapFileObjDuplicate = new HashMap<>();
    private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid = new HashMap<>();
    private Map<String, RecordPrepaid> mapRecordSelected = new HashMap<>();

    private boolean isUseSig;
    private boolean requiredUploadImage = true;
    private String sigImageFullPath;
    private String sigImageFullPathTwo;

    private List<ProfileRecord> profileRecords;
    private Map<String, ArrayList<RecordTypeScanDTO>> mapListRecordTypeScanDTO = new HashMap<>();
    private Map<String, RecordTypeScanDTO> mapRecordTypeScanDTOSelected = new HashMap<>();

    public ProfileBO() {
        super();
        this.isUseSig = true;
    }

    public void clearData() {
        this.sigImageFullPath = null;
        this.sigImageFullPathTwo = null;
        if (hashmapFileObj != null) {
            this.hashmapFileObj.clear();
        }
        CommonActivity.deleteAllFileInDir(Constant.DIR_SAVE_SIG_PATH);
    }

    public String getSigImageFullPathTwo() {
        return sigImageFullPathTwo;
    }

    public void setSigImageFullPathTwo(String sigImageFullPathTwo) {
        this.sigImageFullPathTwo = sigImageFullPathTwo;
    }

    public boolean isUseSig() {
        return isUseSig;
    }

    public void setUseSig(boolean useSig) {
        isUseSig = useSig;
    }

    public void setProfileRecords(List<ProfileRecord> profileRecords) {
        this.profileRecords = profileRecords;
    }

    public Map<String, ArrayList<RecordTypeScanDTO>> getMapListRecordTypeScanDTO() {
        return mapListRecordTypeScanDTO;
    }

    public void setMapListRecordTypeScanDTO(Map<String, ArrayList<RecordTypeScanDTO>> mapListRecordTypeScanDTO) {
        this.mapListRecordTypeScanDTO = mapListRecordTypeScanDTO;
    }

    public Map<String, RecordTypeScanDTO> getMapRecordTypeScanDTOSelected() {
        return mapRecordTypeScanDTOSelected;
    }

    public void setMapRecordTypeScanDTOSelected(Map<String, RecordTypeScanDTO> mapRecordTypeScanDTOSelected) {
        this.mapRecordTypeScanDTOSelected = mapRecordTypeScanDTOSelected;
    }

    public void updateProfileRecords(String fileContentOne, String fileContentTwo) {

        if (profileRecords != null) {
            profileRecords.clear();
        } else {
            profileRecords = new ArrayList<>();
        }

        if (!CommonActivity.isNullOrEmpty(fileContentOne)) {
            ProfileRecord signatureOne = new ProfileRecord();
            signatureOne.setContent(fileContentOne);
            signatureOne.setCode(Constant.PROFILE.CHUKY);
            signatureOne.setType(Constant.PROFILE.CHUKY);
            signatureOne.setFileExtension(Constant.IMG_EXT_PNG);
            profileRecords.add(signatureOne);
        }

        if (!CommonActivity.isNullOrEmpty(fileContentTwo)) {
            ProfileRecord signatureTwo = new ProfileRecord();
            signatureTwo.setContent(fileContentTwo);
            signatureTwo.setCode(Constant.PROFILE.CHUKY2);
            signatureTwo.setType(Constant.PROFILE.CHUKY);
            signatureTwo.setFileExtension(Constant.IMG_EXT_PNG);
            profileRecords.add(signatureTwo);
        }

        ProfileRecord profileRecord;
        for (Map.Entry<String, RecordPrepaid> entry : mapRecordSelected.entrySet()) {
            RecordPrepaid recordPrepaidTemp = entry.getValue();
            if (recordPrepaidTemp.getElectronicSign() == 1
                    && "1".equals(recordPrepaidTemp.getRequire())) {
                profileRecord = new ProfileRecord();
                profileRecord.setCode(recordPrepaidTemp.getCode());
                profileRecords.add(profileRecord);
            }
        }
    }

    public List<ProfileRecord> getProfileRecords() {
        return profileRecords;
    }

    public String getSigImageFullPath() {
        return sigImageFullPath;
    }

    public void setSigImageFullPath(String sigImageFullPath) {
        this.sigImageFullPath = sigImageFullPath;
    }

    public List<Object> getLstActionCode() {
        if(CommonActivity.isNullOrEmpty(lstActionCode)){
            lstActionCode = new ArrayList<>();
        }
        return lstActionCode;
    }

    public void setLstActionCode(List<Object> lstActionCode) {
        this.lstActionCode = lstActionCode;
    }

    public List<Object> getLstReasonId() {
        if(CommonActivity.isNullOrEmpty(lstReasonId)){
            lstReasonId = new ArrayList<>();
        }
        return lstReasonId;
    }

    public void setLstReasonId(List<Object> lstReasonId) {
        this.lstReasonId = lstReasonId;
    }

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIsdnAccount() {
        return isdnAccount;
    }

    public void setIsdnAccount(String isdnAccount) {
        this.isdnAccount = isdnAccount;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HashMap<String, ArrayList<FileObj>> getHashmapFileObj() {
        return hashmapFileObj;
    }

    public void setHashmapFileObj(HashMap<String, ArrayList<FileObj>> hashmapFileObj) {
        this.hashmapFileObj = hashmapFileObj;
    }

    public Map<String, ArrayList<RecordPrepaid>> getMapListRecordPrepaid() {
        return mapListRecordPrepaid;
    }

    public void setMapListRecordPrepaid(Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid) {
        this.mapListRecordPrepaid = mapListRecordPrepaid;
    }

    public Map<String, RecordPrepaid> getMapRecordSelected() {
        return mapRecordSelected;
    }

    public void setMapRecordSelected(Map<String, RecordPrepaid> mapRecordSelected) {
        this.mapRecordSelected = mapRecordSelected;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public HashMap<String, ArrayList<FileObj>> getHashmapFileObjDuplicate() {
        return hashmapFileObjDuplicate;
    }

    public void setHashmapFileObjDuplicate(HashMap<String, ArrayList<FileObj>> hashmapFileObjDuplicate) {
        this.hashmapFileObjDuplicate = hashmapFileObjDuplicate;
    }

    public boolean isRequiredUploadImage() {
        return requiredUploadImage;
    }

    public void setRequiredUploadImage(boolean requiredUploadImage) {
        this.requiredUploadImage = requiredUploadImage;
    }

   public void removeRecordByCode(String code) {
        for (Map.Entry<String, ArrayList<RecordPrepaid>> entry : mapListRecordPrepaid.entrySet()) {
            ArrayList<RecordPrepaid> recordPrepaids = entry.getValue();
            if(!CommonActivity.isNullOrEmpty(recordPrepaids)){
                for (int i= 0; i < recordPrepaids.size() ; i++ ){
                    RecordPrepaid recordPrepaidTemp = recordPrepaids.get(i);
                    if (recordPrepaidTemp.getCode().equals(code) && recordPrepaids.size() > 1) {
                        recordPrepaids.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    public void removeItemFromMapById(String id){
        for (Iterator<Map.Entry<String, ArrayList<RecordPrepaid>>> it = mapListRecordPrepaid.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, ArrayList<RecordPrepaid>> entry = it.next();
            if (entry.getKey().equals(id)) {
                it.remove();
            }
        }
    }

    public void removeItemFromMapByCode(String code){
        for (Iterator<Map.Entry<String, ArrayList<RecordPrepaid>>> it = mapListRecordPrepaid.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, ArrayList<RecordPrepaid>> entry = it.next();
            if(checkRemoveCode(entry.getValue() , code)){
                it.remove();
            }

        }
    }

    public boolean checkRemoveCode(ArrayList<RecordPrepaid> arrayList, String code){

        if(CommonActivity.isNullOrEmpty(arrayList)){
           return false;
        }
        for (RecordPrepaid item: arrayList) {
            if(code.equals(item.getCode()) || code.equals(item.getCode())){
                return  true;
            }
        }
        return false;
    }

}
