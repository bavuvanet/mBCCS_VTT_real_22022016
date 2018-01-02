package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.ContractBank;

public class ConTractBean {
	private String contractNo;
	private String contractId;
	private String address;
	private String billCycleFrom;
	private String billCycleFromCharging;
	private String contractType;
	private String contractTypeCode;
	private String custId;
	private String dateCreate;
	private String district;
	private String effectDate;
	private String mainIsdn;
	private String mainSubId;
	private String noticeCharge;
	private String numOfSubscribers;
	private String payAreaCode;
	private String payMethodCode;
	private String payer;
	private String precinct;
	private String printMethodCode;
	private String province;
	private String signDate;
	private String status;
	private String streetBlock;
	private String streetName;
	private String telMobile;
	private String userCreate;
	private String home;
	private String contractOfferId;
	private String telFax;
	private String email;
	private ContractBank contractBank = new ContractBank();
	private String billAddress;
	
	private String contactName;//>hd abc</contactName>
    private String contactTitle;//>chuc t</contactTitle>
    private String idNo;
    private String contractPrint;
    
    
    
	
	public String getContractPrint() {
		return contractPrint;
	}

	public void setContractPrint(String contractPrint) {
		this.contractPrint = contractPrint;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	public ContractBank getContractBank() {
		return contractBank;
	}

	public void setContractBank(ContractBank contractBank) {
		this.contractBank = contractBank;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	private RepresentativeCustObj representativeCustObj;

	public String getHome() {
		return home;
	}

	public String getContractOfferId() {
		return contractOfferId;
	}

	public void setContractOfferId(String contractOfferId) {
		this.contractOfferId = contractOfferId;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public RepresentativeCustObj getRepresentativeCustObj() {
		return representativeCustObj;
	}

	public void setRepresentativeCustObj(RepresentativeCustObj representativeCustObj) {
		this.representativeCustObj = representativeCustObj;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBillCycleFrom() {
		return billCycleFrom;
	}

	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}

	public String getBillCycleFromCharging() {
		return billCycleFromCharging;
	}

	public void setBillCycleFromCharging(String billCycleFromCharging) {
		this.billCycleFromCharging = billCycleFromCharging;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractTypeCode() {
		return contractTypeCode;
	}

	public void setContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getMainIsdn() {
		return mainIsdn;
	}

	public void setMainIsdn(String mainIsdn) {
		this.mainIsdn = mainIsdn;
	}

	public String getMainSubId() {
		return mainSubId;
	}

	public void setMainSubId(String mainSubId) {
		this.mainSubId = mainSubId;
	}

	public String getNoticeCharge() {
		return noticeCharge;
	}

	public void setNoticeCharge(String noticeCharge) {
		this.noticeCharge = noticeCharge;
	}

	public String getNumOfSubscribers() {
		return numOfSubscribers;
	}

	public void setNumOfSubscribers(String numOfSubscribers) {
		this.numOfSubscribers = numOfSubscribers;
	}

	public String getPayAreaCode() {
		return payAreaCode;
	}

	public void setPayAreaCode(String payAreaCode) {
		this.payAreaCode = payAreaCode;
	}

	public String getPayMethodCode() {
		return payMethodCode;
	}

	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getPrintMethodCode() {
		return printMethodCode;
	}

	public void setPrintMethodCode(String printMethodCode) {
		this.printMethodCode = printMethodCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getTelMobile() {
		return telMobile;
	}

	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"ConTractBean\":{\"contractNo\":\"%s\", \"contractId\":\"%s\", \"address\":\"%s\", \"billCycleFrom\":\"%s\", \"billCycleFromCharging\":\"%s\", \"contractType\":\"%s\", \"contractTypeCode\":\"%s\", \"custId\":\"%s\", \"dateCreate\":\"%s\", \"district\":\"%s\", \"effectDate\":\"%s\", \"mainIsdn\":\"%s\", \"mainSubId\":\"%s\", \"noticeCharge\":\"%s\", \"numOfSubscribers\":\"%s\", \"payAreaCode\":\"%s\", \"payMethodCode\":\"%s\", \"payer\":\"%s\", \"precinct\":\"%s\", \"printMethodCode\":\"%s\", \"province\":\"%s\", \"signDate\":\"%s\", \"status\":\"%s\", \"streetBlock\":\"%s\", \"streetName\":\"%s\", \"telMobile\":\"%s\", \"userCreate\":\"%s\", \"home\":\"%s\", \"contractOfferId\":\"%s\", \"telFax\":\"%s\", \"email\":\"%s\", \"contractBank\":%s, \"billAddress\":\"%s\", \"representativeCustObj\":\"%s\"}}",
				contractNo, contractId, address, billCycleFrom, billCycleFromCharging, contractType, contractTypeCode,
				custId, dateCreate, district, effectDate, mainIsdn, mainSubId, noticeCharge, numOfSubscribers,
				payAreaCode, payMethodCode, payer, precinct, printMethodCode, province, signDate, status, streetBlock,
				streetName, telMobile, userCreate, home, contractOfferId, telFax, email, contractBank, billAddress,
				representativeCustObj);
	}

}
