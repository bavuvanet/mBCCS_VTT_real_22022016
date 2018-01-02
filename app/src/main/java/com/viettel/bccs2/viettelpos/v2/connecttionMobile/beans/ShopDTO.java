package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "shopDTO", strict = false)
public class ShopDTO {
	@Element (name = "account", required = false)
	private String account;
	@Element (name = "areaCode", required = false)
	private String areaCode;
	@Element (name = "bankName", required = false)
	private String bankName;
	@Element (name = "bankplusMobile", required = false)
	private String bankplusMobile;
	@Element (name = "businessLicence", required = false)
	private String businessLicence;
	@Element (name = "centerCode", required = false)
	private String centerCode;
	@Element (name = "channelTypeId", required = false)
	private Long channelTypeId;
	@Element (name = "company", required = false)
	private String company;
	@Element (name = "contactName", required = false)
	private String contactName;
	@Element (name = "contactTitle", required = false)
	private String contactTitle;
	@Element (name = "contractFromDate", required = false)
	private String contractFromDate;
	@Element (name = "contractNo", required = false)
	private String contractNo;
	@Element (name = "contractToDate", required = false)
	private String contractToDate;
	@Element (name = "createDate", required = false)
	private String createDate;
	@Element (name = "depositValue", required = false)
	private Long depositValue;
	@Element (name = "discountPolicy", required = false)
	private String discountPolicy;
	@Element (name = "district", required = false)
	private String district;
	@Element (name = "email", required = false)
	private String email;
	@Element (name = "fax", required = false)
	private String fax;
	@Element (name = "home", required = false)
	private String home;
	@Element (name = "idIssueDate", required = false)
	private String idIssueDate;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public String getCenterCode() {
		return centerCode;
	}
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	public Long getChannelTypeId() {
		return channelTypeId;
	}
	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactTitle() {
		return contactTitle;
	}
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	public String getContractFromDate() {
		return contractFromDate;
	}
	public void setContractFromDate(String contractFromDate) {
		this.contractFromDate = contractFromDate;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public Short getIdType() {
		return idType;
	}
	public void setIdType(Short idType) {
		this.idType = idType;
	}
	public String getOldShopCode() {
		return oldShopCode;
	}
	public void setOldShopCode(String oldShopCode) {
		this.oldShopCode = oldShopCode;
	}
	public String getParShopCode() {
		return parShopCode;
	}
	public void setParShopCode(String parShopCode) {
		this.parShopCode = parShopCode;
	}
	public Long getParentShopId() {
		return parentShopId;
	}
	public void setParentShopId(Long parentShopId) {
		this.parentShopId = parentShopId;
	}
	public String getPayComm() {
		return payComm;
	}
	public void setPayComm(String payComm) {
		this.payComm = payComm;
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
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopPath() {
		return shopPath;
	}
	public void setShopPath(String shopPath) {
		this.shopPath = shopPath;
	}
	public String getShopPathName() {
		return shopPathName;
	}
	public void setShopPathName(String shopPathName) {
		this.shopPathName = shopPathName;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Element (name = "idIssuePlace", required = false)
	private String idIssuePlace;
	@Element (name = "idNo", required = false)
	private String idNo;
	@Element (name = "idType", required = false)
	private Short idType;
	@Element (name = "oldShopCode", required = false)
	private String oldShopCode;
	@Element (name = "parShopCode", required = false)
	private String parShopCode;
	@Element (name = "parentShopId", required = false)
	private Long parentShopId;
	@Element (name = "payComm", required = false)
	private String payComm;
	@Element (name = "precinct", required = false)
	private String precinct;
	@Element (name = "pricePolicy", required = false)
	private String pricePolicy;
	@Element (name = "province", required = false)
	private String province;
	@Element (name = "provinceCode", required = false)
	private String provinceCode;
	@Element (name = "shop", required = false)
	private String shop;
	@Element (name = "shopCode", required = false)
	private String shopCode;
	@Element (name = "shopId", required = false)
	private Long shopId;
	@Element (name = "shopPath", required = false)
	private String shopPath;
	@Element (name = "shopPathName", required = false)
	private String shopPathName;
	@Element (name = "shopType", required = false)
	private String shopType;
	@Element (name = "stockNum", required = false)
	private Long stockNum;
	@Element (name = "stockNumImp", required = false)
	private Long stockNumImp;
	@Element (name = "street", required = false)
	private String street;
	@Element (name = "streetBlock", required = false)
	private String streetBlock;
	@Element (name = "tel", required = false)
	private String tel;
	@Element (name = "telNumber", required = false)
	private String telNumber;
	@Element (name = "tin", required = false)
	private String tin;
	@Element (name = "address", required = false)
	private String address;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "fileName", required = false)
	private String fileName;
	@Element (name = "description", required = false)
	private String description;
	@Element (name = "status", required = false)
	private String status;
}
