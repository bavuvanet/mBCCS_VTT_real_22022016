package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "return", strict = false)
public class ActionProfileBean implements Serializable{
	@Element(name = "actionAuditId", required = false)
	private Long actionAuditId;
	@Element(name = "actionCode", required = false)
	private String actionCode;
	@Element(name = "actionDate", required = false)
	private String actionDate;
	@Element(name = "actionDateStr", required = false)
	private String actionDateStr;
	@Element(name = "actionId", required = false)
	private Long actionId;
	@Element(name = "actionProfileId", required = false)
	private Long actionProfileId;
	@Element(name = "chanelTypeId", required = false)
	private Long chanelTypeId;
	@Element(name = "checkStatus", required = false)
	private String checkStatus;
	@Element(name = "checkedDatetime", required = false)
	private String checkedDatetime;
	@Element(name = "contractId", required = false)
	private Long contractId;
	@Element(name = "corectInfo", required = false)
	private String corectInfo;
	@Element(name = "corectScan", required = false)
	private String corectScan;
	@Element(name = "cusProfileId", required = false)
	private Long cusProfileId;
	@Element(name = "cusType", required = false)
	private String cusType;
	@Element(name = "custId", required = false)
	private Long custId;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "endDate", required = false)
	private String endDate;
	@Element(name = "fakeProfile", required = false)
	private Long fakeProfile;
	@Element(name = "incorectInfo", required = false)
	private String incorectInfo;
	@Element(name = "incorectScan", required = false)
	private String incorectScan;
	@Element(name = "isdnAccount", required = false)
	private String isdnAccount;
	@Element(name = "lastUpdate", required = false)
	private String lastUpdate;
	@Element(name = "prepaid", required = false)
	private Long prepaid;
	@Element(name = "profileCode", required = false)
	private String profileCode;
	@Element(name = "promotionName", required = false)
	private String promotionName;
	@Element(name = "reaSon", required = false)
	private String reaSon;
	@Element(name = "reasonId", required = false)
	private Long reasonId;
	@Element(name = "receiveDate", required = false)
	private String receiveDate;
	@Element(name = "receiveStatus", required = false)
	private Long receiveStatus;
	@Element(name = "recordStatus", required = false)
	private Long recordStatus;
	@Element(name = "regType", required = false)
	private String regType;
	@Element(name = "regTypeName", required = false)
	private String regTypeName;
	@Element(name = "reqReceive", required = false)
	private String reqReceive;
	@Element(name = "reqScan", required = false)
	private Long reqScan;
	@Element(name = "scanDatetime", required = false)
	private String scanDatetime;
	@Element(name = "scanStatus", required = false)
	private Long scanStatus;
	@Element(name = "serType", required = false)
	private String serType;
	@Element(name = "serTypeName", required = false)
	private String serTypeName;
	@Element(name = "serviceId", required = false)
	private Long serviceId;
	@Element(name = "shopCode", required = false)
	private String shopCode;
	@Element(name = "shopStore", required = false)
	private String shopStore;
	@Element(name = "staffChecked", required = false)
	private String staffChecked;
	@Element(name = "staffCode", required = false)
	private String staffCode;
	@Element(name = "staffReceive", required = false)
	private String staffReceive;
	@Element(name = "staffScan", required = false)
	private String staffScan;
	@Element(name = "status", required = false)
	private Long status;
	@Element(name = "subType", required = false)
	private String subType;
	@Element(name = "subTypeId", required = false)
	private Long subTypeId;
	@Element(name = "sumStatus", required = false)
	private Long sumStatus;
	@Element(name = "vip", required = false)
	private String vip;
	@Element(name = "strCheckStatus", required = false)
	private String strCheckStatus;
	@Element(name = "strFakeProfile", required = false)
    private String strFakeProfile;
	@Element(name = "typeStatus", required = false)
    private String typeStatus;
    @Element(name = "contractNo", required = false)
    private String contractNo;
    @Element(name = "idNo", required = false)
	private String idNo;
    @Element(name = "subStatus", required = false)
    private String subStatus;
    @Element(name = "profileStatus", required = false)
    private String profileStatus;
    @Element(name = "cusName", required = false)
    private String cusName;
	private Boolean isModify = true;
	@Element(name = "descTypeStatus", required = false)
	private String descTypeStatus;
	@Element(name = "cusTypeCode", required = false)
	private String cusTypeCode;
	@Element(name = "actionName", required = false)
	private String actionName;


    public Long getActionAuditId() {
		return actionAuditId;
	}

	public void setActionAuditId(Long actionAuditId) {
		this.actionAuditId = actionAuditId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Long getActionProfileId() {
		return actionProfileId;
	}

	public void setActionProfileId(Long actionProfileId) {
		this.actionProfileId = actionProfileId;
	}

	public Long getChanelTypeId() {
		return chanelTypeId;
	}

	public void setChanelTypeId(Long chanelTypeId) {
		this.chanelTypeId = chanelTypeId;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckedDatetime() {
		return checkedDatetime;
	}

	public void setCheckedDatetime(String checkedDatetime) {
		this.checkedDatetime = checkedDatetime;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getCorectInfo() {
		return corectInfo;
	}

	public void setCorectInfo(String corectInfo) {
		this.corectInfo = corectInfo;
	}

	public String getCorectScan() {
		return corectScan;
	}

	public void setCorectScan(String corectScan) {
		this.corectScan = corectScan;
	}

	public Long getCusProfileId() {
		return cusProfileId;
	}

	public void setCusProfileId(Long cusProfileId) {
		this.cusProfileId = cusProfileId;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getFakeProfile() {
		return fakeProfile;
	}

	public void setFakeProfile(Long fakeProfile) {
		this.fakeProfile = fakeProfile;
	}

	public String getIncorectInfo() {
		return incorectInfo;
	}

	public void setIncorectInfo(String incorectInfo) {
		this.incorectInfo = incorectInfo;
	}

	public String getIncorectScan() {
		return incorectScan;
	}

	public void setIncorectScan(String incorectScan) {
		this.incorectScan = incorectScan;
	}

	public String getIsdnAccount() {
		return isdnAccount;
	}

	public void setIsdnAccount(String isdnAccount) {
		this.isdnAccount = isdnAccount;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getPrepaid() {
		return prepaid;
	}

	public void setPrepaid(Long prepaid) {
		this.prepaid = prepaid;
	}

	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getReaSon() {
		return reaSon;
	}

	public void setReaSon(String reaSon) {
		this.reaSon = reaSon;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Long getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(Long receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Long getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Long recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getRegTypeName() {
		return regTypeName;
	}

	public void setRegTypeName(String regTypeName) {
		this.regTypeName = regTypeName;
	}

	public String getReqReceive() {
		return reqReceive;
	}

	public void setReqReceive(String reqReceive) {
		this.reqReceive = reqReceive;
	}

	public Long getReqScan() {
		return reqScan;
	}

	public void setReqScan(Long reqScan) {
		this.reqScan = reqScan;
	}

	public String getScanDatetime() {
		return scanDatetime;
	}

	public void setScanDatetime(String scanDatetime) {
		this.scanDatetime = scanDatetime;
	}

	public Long getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(Long scanStatus) {
		this.scanStatus = scanStatus;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getSerTypeName() {
		return serTypeName;
	}

	public void setSerTypeName(String serTypeName) {
		this.serTypeName = serTypeName;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopStore() {
		return shopStore;
	}

	public void setShopStore(String shopStore) {
		this.shopStore = shopStore;
	}

	public String getStaffChecked() {
		return staffChecked;
	}

	public void setStaffChecked(String staffChecked) {
		this.staffChecked = staffChecked;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffReceive() {
		return staffReceive;
	}

	public void setStaffReceive(String staffReceive) {
		this.staffReceive = staffReceive;
	}

	public String getStaffScan() {
		return staffScan;
	}

	public void setStaffScan(String staffScan) {
		this.staffScan = staffScan;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Long getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	public Long getSumStatus() {
		return sumStatus;
	}

	public void setSumStatus(Long sumStatus) {
		this.sumStatus = sumStatus;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getStrCheckStatus() {
		return strCheckStatus;
	}

	public void setStrCheckStatus(String strCheckStatus) {
		this.strCheckStatus = strCheckStatus;
	}

	public String getStrFakeProfile() {
		return strFakeProfile;
	}

	public void setStrFakeProfile(String strFakeProfile) {
		this.strFakeProfile = strFakeProfile;
	}

	public String getActionDateStr() {
		return actionDateStr;
	}

	public void setActionDateStr(String actionDateStr) {
		this.actionDateStr = actionDateStr;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

	public Boolean getIsModify() {
		return isModify;
	}

	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}

	public String getDescTypeStatus() {
		return descTypeStatus;
	}

	public void setDescTypeStatus(String descTypeStatus) {
		this.descTypeStatus = descTypeStatus;
	}

	public String getCusTypeCode() {
		return cusTypeCode;
	}

	public void setCusTypeCode(String cusTypeCode) {
		this.cusTypeCode = cusTypeCode;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
}
