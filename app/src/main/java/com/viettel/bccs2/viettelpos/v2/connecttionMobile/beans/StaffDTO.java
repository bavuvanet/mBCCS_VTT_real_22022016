package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;



import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "StaffDTO", strict = false)
public class StaffDTO implements Serializable{
	@Element (name = "areaCode", required = false)
	private String areaCode;
	@Element (name = "bankplusMobile", required = false)
	private String bankplusMobile;
	@Element (name = "businessLicence", required = false)
	private String businessLicence;
	@Element (name = "businessMethod", required = false)
	private Long businessMethod;
	@Element (name = "channelTypeId", required = false)
	private Long channelTypeId;
	@Element (name = "contractFromDate", required = false)
	private String contractFromDate;
	@Element (name = "contractMethod", required = false)
	private Long contractMethod;
	@Element (name = "contractNo", required = false)
	private String contractNo;
	@Element (name = "contractToDate", required = false)
	private String contractToDate;
	@Element (name = "depositValue", required = false)
	private Long depositValue;
	@Element (name = "discountPolicy", required = false)
	private String discountPolicy;
	@Element (name = "district", required = false)
	private String district;
	@Element (name = "email", required = false)
	private String email;
	@Element (name = "hasEquipment", required = false)
	private Long hasEquipment;
	@Element (name = "hasTin", required = false)
	private Long hasTin;
	@Element (name = "home", required = false)
	private String home;
	@Element (name = "idIssueDate", required = false)
	private String idIssueDate;
	@Element (name = "idIssuePlace", required = false)
	private String idIssuePlace;
	@Element (name = "idNo", required = false)
	private String idNo;
	@Element (name = "isdn", required = false)
	private String isdn;
	@Element (name = "lastLockTime", required = false)
	private String lastLockTime;
	@Element (name = "lockStatus", required = false)
	private Long lockStatus;
	@Element (name = "note", required = false)
	private String note;
	@Element (name = "pin", required = false)
	private String pin;
	@Element (name = "pointOfSale", required = false)
	private String pointOfSale;
	@Element (name = "pointOfSaleType", required = false)
	private Long pointOfSaleType;
	@Element (name = "precinct", required = false)
	private String precinct;
	@Element (name = "pricePolicy", required = false)
	private String pricePolicy;
	@Element (name = "province", required = false)
	private String province;
	@Element (name = "serial", required = false)
	private String serial;
	@Element (name = "shopId", required = false)
	private Long shopId;
	@Element (name = "shopOwnerId", required = false)
	private Long shopOwnerId;
	@Element (name = "staffCode", required = false)
	private String staffCode;
	@Element (name = "staffId", required = false)
	private Long staffId;
	@Element (name = "staffOwnType", required = false)
	private String staffOwnType;
	@Element (name = "staffOwnerId", required = false)
	private Long staffOwnerId;
	@Element (name = "stockNum", required = false)
	private Long stockNum;
	@Element (name = "stockNumImp", required = false)
	private Long stockNumImp;
	@Element (name = "street", required = false)
	private String street;
	@Element (name = "streetBlock", required = false)
	private String streetBlock;
	@Element (name = "subOwnerId", required = false)
	private Long subOwnerId;
	@Element (name = "subOwnerType", required = false)
	private Long subOwnerType;
	@Element (name = "tel", required = false)
	private String tel;
	@Element (name = "tin", required = false)
	private String tin;
	@Element (name = "ttnsCode", required = false)
	private String ttnsCode;
	@Element (name = "userId", required = false)
	private Long userId;
	@Element (name = "shopCode", required = false)
	private String shopCode;
	@Element (name = "shopDTO", required = false)
	private ShopDTO shopDTO;
	@Element (name = "shopName", required = false)
	private String shopName;
	@Element (name = "shopChanelTypeId", required = false)
	private Long shopChanelTypeId;
	@Element (name = "shopProvince", required = false)
	private String shopProvince;
	@Element (name = "shopPrecinct", required = false)
	private String shopPrecinct;
	@Element (name = "shopPath", required = false)
	private String shopPath;
	@Element (name = "saleTransStaffId", required = false)
	private Long saleTransStaffId;
	@Element (name = "ipAddress", required = false)
	private String ipAddress;
	@Element (name = "ipPortWeb", required = false)
	private String ipPortWeb;
	@Element (name = "tablePk", required = false)
	private String tablePk;
	@Element (name = "createStaffId", required = false)
	private Long createStaffId;
	@Element (name = "systemType", required = false)
	private String systemType;
	@Element (name = "errorCode", required = false)
	private String errorCode;
	@Element (name = "address", required = false)
	private String address;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "type", required = false)
	private Long type;
	@Element (name = "fileName", required = false)
	private String fileName;
	@Element (name = "lastModified", required = false)
	private String lastModified;
	@Element (name = "status", required = false)
	private String status;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBankplusMobile() {
		return bankplusMobile;
	}
	public void setBankplusMobile(String bankplusMobile) {
		this.bankplusMobile = bankplusMobile;
	}
	public String getBusinessLicence() {
		return businessLicence;
	}
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	public Long getBusinessMethod() {
		return businessMethod;
	}
	public void setBusinessMethod(Long businessMethod) {
		this.businessMethod = businessMethod;
	}
	public Long getChannelTypeId() {
		return channelTypeId;
	}
	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}
	public String getContractFromDate() {
		return contractFromDate;
	}
	public void setContractFromDate(String contractFromDate) {
		this.contractFromDate = contractFromDate;
	}
	public Long getContractMethod() {
		return contractMethod;
	}
	public void setContractMethod(Long contractMethod) {
		this.contractMethod = contractMethod;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractToDate() {
		return contractToDate;
	}
	public void setContractToDate(String contractToDate) {
		this.contractToDate = contractToDate;
	}
	public Long getDepositValue() {
		return depositValue;
	}
	public void setDepositValue(Long depositValue) {
		this.depositValue = depositValue;
	}
	public String getDiscountPolicy() {
		return discountPolicy;
	}
	public void setDiscountPolicy(String discountPolicy) {
		this.discountPolicy = discountPolicy;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getHasEquipment() {
		return hasEquipment;
	}
	public void setHasEquipment(Long hasEquipment) {
		this.hasEquipment = hasEquipment;
	}
	public Long getHasTin() {
		return hasTin;
	}
	public void setHasTin(Long hasTin) {
		this.hasTin = hasTin;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getIdIssueDate() {
		return idIssueDate;
	}
	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}
	public String getIdIssuePlace() {
		return idIssuePlace;
	}
	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getLastLockTime() {
		return lastLockTime;
	}
	public void setLastLockTime(String lastLockTime) {
		this.lastLockTime = lastLockTime;
	}
	public Long getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPointOfSale() {
		return pointOfSale;
	}
	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}
	public Long getPointOfSaleType() {
		return pointOfSaleType;
	}
	public void setPointOfSaleType(Long pointOfSaleType) {
		this.pointOfSaleType = pointOfSaleType;
	}
	public String getPrecinct() {
		return precinct;
	}
	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}
	public String getPricePolicy() {
		return pricePolicy;
	}
	public void setPricePolicy(String pricePolicy) {
		this.pricePolicy = pricePolicy;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getShopOwnerId() {
		return shopOwnerId;
	}
	public void setShopOwnerId(Long shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getStaffOwnType() {
		return staffOwnType;
	}
	public void setStaffOwnType(String staffOwnType) {
		this.staffOwnType = staffOwnType;
	}
	public Long getStaffOwnerId() {
		return staffOwnerId;
	}
	public void setStaffOwnerId(Long staffOwnerId) {
		this.staffOwnerId = staffOwnerId;
	}
	public Long getStockNum() {
		return stockNum;
	}
	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}
	public Long getStockNumImp() {
		return stockNumImp;
	}
	public void setStockNumImp(Long stockNumImp) {
		this.stockNumImp = stockNumImp;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetBlock() {
		return streetBlock;
	}
	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}
	public Long getSubOwnerId() {
		return subOwnerId;
	}
	public void setSubOwnerId(Long subOwnerId) {
		this.subOwnerId = subOwnerId;
	}
	public Long getSubOwnerType() {
		return subOwnerType;
	}
	public void setSubOwnerType(Long subOwnerType) {
		this.subOwnerType = subOwnerType;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getTtnsCode() {
		return ttnsCode;
	}
	public void setTtnsCode(String ttnsCode) {
		this.ttnsCode = ttnsCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public ShopDTO getShopDTO() {
		return shopDTO;
	}
	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getShopChanelTypeId() {
		return shopChanelTypeId;
	}
	public void setShopChanelTypeId(Long shopChanelTypeId) {
		this.shopChanelTypeId = shopChanelTypeId;
	}
	public String getShopProvince() {
		return shopProvince;
	}
	public void setShopProvince(String shopProvince) {
		this.shopProvince = shopProvince;
	}
	public String getShopPrecinct() {
		return shopPrecinct;
	}
	public void setShopPrecinct(String shopPrecinct) {
		this.shopPrecinct = shopPrecinct;
	}
	public String getShopPath() {
		return shopPath;
	}
	public void setShopPath(String shopPath) {
		this.shopPath = shopPath;
	}
	public Long getSaleTransStaffId() {
		return saleTransStaffId;
	}
	public void setSaleTransStaffId(Long saleTransStaffId) {
		this.saleTransStaffId = saleTransStaffId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpPortWeb() {
		return ipPortWeb;
	}
	public void setIpPortWeb(String ipPortWeb) {
		this.ipPortWeb = ipPortWeb;
	}
	public String getTablePk() {
		return tablePk;
	}
	public void setTablePk(String tablePk) {
		this.tablePk = tablePk;
	}
	public Long getCreateStaffId() {
		return createStaffId;
	}
	public void setCreateStaffId(Long createStaffId) {
		this.createStaffId = createStaffId;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
