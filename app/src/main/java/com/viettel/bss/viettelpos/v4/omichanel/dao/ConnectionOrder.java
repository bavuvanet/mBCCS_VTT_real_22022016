package com.viettel.bss.viettelpos.v4.omichanel.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lamnv5 on 8/23/2017.
 */
public class ConnectionOrder extends Order implements Serializable {

    private static final long serialVersionUID = 0L;

    private String isdn;//So thue bao dau noi
    private ProductInfo productInfo;//Thong tin san pham
    private IsdnPledgeInfo isdnPledgeInfo;
    private Boolean needChargeCard;//Co nap the ngay khong?
    private Long chargeCardAmound; //Menh gia the nap

    public boolean isCheckIdNo() {
        return isCheckIdNo;
    }

    private boolean isCheckIdNo =false;
    private List<UploadDocumentDTO> uploadFiles;  //ho so hien thi
    private List<RecordDTO> filesOld;  //lst ho so cu
    private int orderActionState;
    public void mergeFileOldToUploadFiles() {
        if (CommonActivity.isNullOrEmpty(filesOld)) {
            return;
        }
        for (RecordDTO recordDTO : filesOld) {
            RecordTypeScanDTO recordTypeScanDTO = new RecordTypeScanDTO();
            recordTypeScanDTO.setElectronicSign("0");
            recordTypeScanDTO.setSourceId(recordDTO.getRecordTypeId());
            recordTypeScanDTO.setRecordCode(recordDTO.getRecordCode());
            recordTypeScanDTO.setRecordName(recordDTO.getRecordName());
            recordTypeScanDTO.setReqScan(recordDTO.getReqScan());
            UploadDocumentDTO uploadDocumentDTO = new UploadDocumentDTO();
            ArrayList<RecordTypeScanDTO> recordTypeScanDTOs = new ArrayList<>();
            recordTypeScanDTOs.add(recordTypeScanDTO);
            uploadDocumentDTO.setListRecord(recordTypeScanDTOs);
            if (!isExitsUploadDocumentDTO(uploadDocumentDTO)) {
                uploadFiles.add(uploadDocumentDTO);
            }
        }
    }
    private boolean isExitsUploadDocumentDTO(UploadDocumentDTO uploadDocumentDTO) {
        if (CommonActivity.isNullOrEmpty(uploadFiles)) {
            return false;
        }
        for (UploadDocumentDTO item : uploadFiles) {
            if (item.getSourceId() == uploadDocumentDTO.getSourceId()) {
                return true;
            }
        }
        return false;
    }

    public void updateTypeForProfileRecords() {
        if (CommonActivity.isNullOrEmpty(getProfileRecords())) {
            return;
        }
        for (ProfileRecord profileRecord : getProfileRecords()) {
            String id = getIdFromProfile(profileRecord);
            profileRecord.setType(id);
        }
    }

    private String getIdFromProfile(ProfileRecord profileRecord) {
        for (UploadDocumentDTO uploadDocumentDTO : uploadFiles) {
            for (RecordTypeScanDTO recordTypeScanDTO : uploadDocumentDTO.getListRecord()) {
                if (recordTypeScanDTO.getRecordCode().equals(profileRecord.getCode())) {
                    // ko cho chon lai chung tu ky
                    if ("1".equals(recordTypeScanDTO.getElectronicSign())) {
                        removeAllSigUtilCode(recordTypeScanDTO.getRecordCode(), uploadDocumentDTO);
                    }
                    return recordTypeScanDTO.getSourceId() + "";
                }
            }
        }
        return "";
    }

    private void removeAllSigUtilCode(String code, UploadDocumentDTO uploadDocumentDTO) {
        RecordTypeScanDTO recordTypeScanDTO;
        for (int index = uploadDocumentDTO.getListRecord().size() - 1; index >= 0; index--) {
            recordTypeScanDTO = uploadDocumentDTO.getListRecord().get(index);
            if (!code.equals(recordTypeScanDTO.getRecordCode())) {
                uploadDocumentDTO.getListRecord().remove(recordTypeScanDTO);
            }
        }
    }

    public List<UploadDocumentDTO> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadDocumentDTO> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public ConnectionOrder() {
        super();
    }

    public ConnectionOrder(String orderType, String nameCus, String processId, Long shopId) {
        super();

        this.setProcessInstanceId(processId);
        this.setOrderType(orderType);
        this.setCustomer(new Customer());
        this.getCustomer().setName(nameCus);
        this.setTarget("SHOP");
        this.setTransactionPlace("SHOP");
        this.setHandleShop(shopId);

        this.setAllowUpdateCustomer(true);
        this.setAllowUpdateProduct(true);
        this.setAllowUpdateHandleShop(true);

        initProfileRecords();
    }

    public void setContentRecordByCode(String code, String content, String ext) {
        for (ProfileRecord profileRecord : getProfileRecords()) {
            if (code.equals(profileRecord.getCode())) {
                profileRecord.setContent(content);
                profileRecord.setFileExtension(ext);
                if (code.equals(Constant.PROFILE.CHUKY)) {
                    profileRecord.setType(Constant.PROFILE.CHUKY);
                }
                return;
            }
        }
        ProfileRecord profileRecordNew = new ProfileRecord();
        profileRecordNew.setCode(code);
        profileRecordNew.setContent(content);
        profileRecordNew.setFileExtension(ext);
        if (code.equals(Constant.PROFILE.CHUKY)) {
            profileRecordNew.setType(Constant.PROFILE.CHUKY);
        }
        getProfileRecords().add(profileRecordNew);
    }

    private void initProfileRecords() {
        ArrayList<ProfileRecord> profileRecordArrayList = new ArrayList<>();

        ProfileRecord cmtBefore = new ProfileRecord();
        cmtBefore.setContent(null);
        cmtBefore.setCode(Constant.PROFILE.CMTNDMT);
        cmtBefore.setFileExtension(Constant.IMG_EXT_JPG);
        cmtBefore.setServer("");

        ProfileRecord cmtAfter = new ProfileRecord();
        cmtAfter.setContent(null);
        cmtAfter.setCode(Constant.PROFILE.CMTNDMS);
        cmtAfter.setFileExtension(Constant.IMG_EXT_JPG);
        cmtAfter.setServer("");

        ProfileRecord signature = new ProfileRecord();
        signature.setContent(null);
        signature.setCode(Constant.PROFILE.CHUKY);
        signature.setType(Constant.PROFILE.CHUKY);
        signature.setFileExtension(Constant.IMG_EXT_PNG);
        signature.setServer("");

        profileRecordArrayList.add(cmtBefore);
        profileRecordArrayList.add(cmtAfter);
        profileRecordArrayList.add(signature);

        this.setProfileRecords(profileRecordArrayList);
    }

    public boolean validateProfileRecordByCode(String code) {
        if ((this.getProfileRecords() == null) || (this.getProfileRecords().size() == 0)) {
            return false;
        }
        for (ProfileRecord profileRecord : this.getProfileRecords()) {
            if (CommonActivity.isNullOrEmpty(profileRecord.getCode())) {
                continue;
            }
            if (profileRecord.getCode().equals(code)) {
                if (profileRecord.getContent() != null || profileRecord.getUrl() != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public void setCheckIdNo(boolean checkIdNo) {
        isCheckIdNo = checkIdNo;
    }

    public int getOrderActionState() {
        return orderActionState;
    }

    public void setOrderActionState(int orderActionState) {
        this.orderActionState = orderActionState;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public IsdnPledgeInfo getIsdnPledgeInfo() {
        return isdnPledgeInfo;
    }

    public void setIsdnPledgeInfo(IsdnPledgeInfo isdnPledgeInfo) {
        this.isdnPledgeInfo = isdnPledgeInfo;
    }

    public List<RecordDTO> getFilesOld() {
        return filesOld;
    }

    public void setFilesOld(List<RecordDTO> filesOld) {
        this.filesOld = filesOld;
    }

    public Boolean getNeedChargeCard() {
        return needChargeCard;
    }

    public Boolean isNeedChargeCard() {
        return needChargeCard;
    }

    public void setNeedChargeCard(Boolean needChargeCard) {
        this.needChargeCard = needChargeCard;
    }

    public Long getChargeCardAmound() {
        if (CommonActivity.isNullOrEmpty(chargeCardAmound)) {
            return new Long(0);
        }
        return chargeCardAmound;
    }

    public void setChargeCardAmound(Long chargeCardAmound) {
        this.chargeCardAmound = chargeCardAmound;
    }
}
