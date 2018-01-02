package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class Contract implements Serializable {

	public Contract() {
		super();
	}

	public Contract(String province) {
		super();
		this.province = province;
	}

	private String accountNumber;
    private String address;
    private String billAddress;
    private Long billCycleFrom;
    private String billCycleFromCharging;
    private String contactName;
    private String contactTitle;
    private ContractBank contractBank;
    private String contractExts;
    private String contractGroupType;
    private Long contractId;
    private String contractNo;
    private String contractNoList;
    private ContractOffer contractOffer;
    private String contractOffers;
    private String contractPrint;
    private String contractType;
    private String contractTypeCode;
    private Long custId;
    private String customer;
    private String dateCreate;
    private Long deporsit;
    private String district;
    private String effectDate;
    private String email;
    private String endDatetime;
    private String endEffectDate;
    private Long fromPrice;
    private String home;
    private String idNo;
    private String isdnAccountList;
    private String lastUpdateTime;
    private String lastUpdateUser;
    private String listContractOffer;
    private String mainIsdn;
    private Long mainSubId;
    private String mainSubOfContractList;
    private String nameCust;
    private String nickDomain;
    private String nickName;
    private String noticeCharge;
    private Long numOfSubscribers;
    private Long oldCustId;
    private String payAreaCode;
    private String payMethodCode;
    private String payer;
    private String precinct;
    private String printContractNo;
    private String printMethodCode;
    private Long project;
    private String province;
    private String reason;
    private String receiveInvoice;
    private Long regType;
    private String representativeCust;
    private String serviceTypes;
    private String signDate;
    private Long status;
    private String strEffectDate;
    private String strEndEffectDate;
    private String strSignDate;
    private String streetBlock;
    private String streetBlockName;
    private String streetName;
    private String telFax;
    private String telMobile;
    private String telecomServiceId;
    private Long toPrice;
    private String userCreate;
    
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	public Long getBillCycleFrom() {
		return billCycleFrom;
	}

	public void setBillCycleFrom(Long billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}

	public String getBillCycleFromCharging() {
		return billCycleFromCharging;
	}

	public void setBillCycleFromCharging(String billCycleFromCharging) {
		this.billCycleFromCharging = billCycleFromCharging;
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

	public ContractBank getContractBank() {
		return contractBank;
	}

	public void setContractBank(ContractBank contractBank) {
		this.contractBank = contractBank;
	}

	public String getContractExts() {
		return contractExts;
	}

	public void setContractExts(String contractExts) {
		this.contractExts = contractExts;
	}

	public String getContractGroupType() {
		return contractGroupType;
	}

	public void setContractGroupType(String contractGroupType) {
		this.contractGroupType = contractGroupType;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractNoList() {
		return contractNoList;
	}

	public void setContractNoList(String contractNoList) {
		this.contractNoList = contractNoList;
	}

	public ContractOffer getContractOffer() {
		return contractOffer;
	}

	public void setContractOffer(ContractOffer contractOffer) {
		this.contractOffer = contractOffer;
	}

	public String getContractOffers() {
		return contractOffers;
	}

	public void setContractOffers(String contractOffers) {
		this.contractOffers = contractOffers;
	}

	public String getContractPrint() {
		return contractPrint;
	}

	public void setContractPrint(String contractPrint) {
		this.contractPrint = contractPrint;
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

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Long getDeporsit() {
		return deporsit;
	}

	public void setDeporsit(Long deporsit) {
		this.deporsit = deporsit;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}

	public String getEndEffectDate() {
		return endEffectDate;
	}

	public void setEndEffectDate(String endEffectDate) {
		this.endEffectDate = endEffectDate;
	}

	public Long getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(Long fromPrice) {
		this.fromPrice = fromPrice;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIsdnAccountList() {
		return isdnAccountList;
	}

	public void setIsdnAccountList(String isdnAccountList) {
		this.isdnAccountList = isdnAccountList;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getListContractOffer() {
		return listContractOffer;
	}

	public void setListContractOffer(String listContractOffer) {
		this.listContractOffer = listContractOffer;
	}

	public String getMainIsdn() {
		return mainIsdn;
	}

	public void setMainIsdn(String mainIsdn) {
		this.mainIsdn = mainIsdn;
	}

	public Long getMainSubId() {
		return mainSubId;
	}

	public void setMainSubId(Long mainSubId) {
		this.mainSubId = mainSubId;
	}

	public String getMainSubOfContractList() {
		return mainSubOfContractList;
	}

	public void setMainSubOfContractList(String mainSubOfContractList) {
		this.mainSubOfContractList = mainSubOfContractList;
	}

	public String getNameCust() {
		return nameCust;
	}

	public void setNameCust(String nameCust) {
		this.nameCust = nameCust;
	}

	public String getNickDomain() {
		return nickDomain;
	}

	public void setNickDomain(String nickDomain) {
		this.nickDomain = nickDomain;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNoticeCharge() {
		return noticeCharge;
	}

	public void setNoticeCharge(String noticeCharge) {
		this.noticeCharge = noticeCharge;
	}

	public Long getNumOfSubscribers() {
		return numOfSubscribers;
	}

	public void setNumOfSubscribers(Long numOfSubscribers) {
		this.numOfSubscribers = numOfSubscribers;
	}

	public Long getOldCustId() {
		return oldCustId;
	}

	public void setOldCustId(Long oldCustId) {
		this.oldCustId = oldCustId;
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

	public String getPrintContractNo() {
		return printContractNo;
	}

	public void setPrintContractNo(String printContractNo) {
		this.printContractNo = printContractNo;
	}

	public String getPrintMethodCode() {
		return printMethodCode;
	}

	public void setPrintMethodCode(String printMethodCode) {
		this.printMethodCode = printMethodCode;
	}

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReceiveInvoice() {
		return receiveInvoice;
	}

	public void setReceiveInvoice(String receiveInvoice) {
		this.receiveInvoice = receiveInvoice;
	}

	public Long getRegType() {
		return regType;
	}

	public void setRegType(Long regType) {
		this.regType = regType;
	}

	public String getRepresentativeCust() {
		return representativeCust;
	}

	public void setRepresentativeCust(String representativeCust) {
		this.representativeCust = representativeCust;
	}

	public String getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(String serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStrEffectDate() {
		return strEffectDate;
	}

	public void setStrEffectDate(String strEffectDate) {
		this.strEffectDate = strEffectDate;
	}

	public String getStrEndEffectDate() {
		return strEndEffectDate;
	}

	public void setStrEndEffectDate(String strEndEffectDate) {
		this.strEndEffectDate = strEndEffectDate;
	}

	public String getStrSignDate() {
		return strSignDate;
	}

	public void setStrSignDate(String strSignDate) {
		this.strSignDate = strSignDate;
	}

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

	public String getStreetBlockName() {
		return streetBlockName;
	}

	public void setStreetBlockName(String streetBlockName) {
		this.streetBlockName = streetBlockName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	public String getTelMobile() {
		return telMobile;
	}

	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	public String getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(String telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public Long getToPrice() {
		return toPrice;
	}

	public void setToPrice(Long toPrice) {
		this.toPrice = toPrice;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"Contract\":{\"accountNumber\":\"%s\", \"address\":\"%s\", \"billAddress\":\"%s\", \"billCycleFrom\":\"%s\", \"billCycleFromCharging\":\"%s\", \"contactName\":\"%s\", \"contactTitle\":\"%s\", \"contractBank\":%s, \"contractExts\":\"%s\", \"contractGroupType\":\"%s\", \"contractId\":\"%s\", \"contractNo\":\"%s\", \"contractNoList\":\"%s\", \"contractOffer\":%s, \"contractOffers\":\"%s\", \"contractPrint\":\"%s\", \"contractType\":\"%s\", \"contractTypeCode\":\"%s\", \"custId\":\"%s\", \"customer\":\"%s\", \"dateCreate\":\"%s\", \"deporsit\":\"%s\", \"district\":\"%s\", \"effectDate\":\"%s\", \"email\":\"%s\", \"endDatetime\":\"%s\", \"endEffectDate\":\"%s\", \"fromPrice\":\"%s\", \"home\":\"%s\", \"idNo\":\"%s\", \"isdnAccountList\":\"%s\", \"lastUpdateTime\":\"%s\", \"lastUpdateUser\":\"%s\", \"listContractOffer\":\"%s\", \"mainIsdn\":\"%s\", \"mainSubId\":\"%s\", \"mainSubOfContractList\":\"%s\", \"nameCust\":\"%s\", \"nickDomain\":\"%s\", \"nickName\":\"%s\", \"noticeCharge\":\"%s\", \"numOfSubscribers\":\"%s\", \"oldCustId\":\"%s\", \"payAreaCode\":\"%s\", \"payMethodCode\":\"%s\", \"payer\":\"%s\", \"precinct\":\"%s\", \"printContractNo\":\"%s\", \"printMethodCode\":\"%s\", \"project\":\"%s\", \"province\":\"%s\", \"reason\":\"%s\", \"receiveInvoice\":\"%s\", \"regType\":\"%s\", \"representativeCust\":\"%s\", \"serviceTypes\":\"%s\", \"signDate\":\"%s\", \"status\":\"%s\", \"strEffectDate\":\"%s\", \"strEndEffectDate\":\"%s\", \"strSignDate\":\"%s\", \"streetBlock\":\"%s\", \"streetBlockName\":\"%s\", \"streetName\":\"%s\", \"telFax\":\"%s\", \"telMobile\":\"%s\", \"telecomServiceId\":\"%s\", \"toPrice\":\"%s\", \"userCreate\":\"%s\"}}",
				accountNumber, address, billAddress, billCycleFrom, billCycleFromCharging, contactName, contactTitle,
				contractBank, contractExts, contractGroupType, contractId, contractNo, contractNoList, contractOffer,
				contractOffers, contractPrint, contractType, contractTypeCode, custId, customer, dateCreate, deporsit,
				district, effectDate, email, endDatetime, endEffectDate, fromPrice, home, idNo, isdnAccountList,
				lastUpdateTime, lastUpdateUser, listContractOffer, mainIsdn, mainSubId, mainSubOfContractList, nameCust,
				nickDomain, nickName, noticeCharge, numOfSubscribers, oldCustId, payAreaCode, payMethodCode, payer,
				precinct, printContractNo, printMethodCode, project, province, reason, receiveInvoice, regType,
				representativeCust, serviceTypes, signDate, status, strEffectDate, strEndEffectDate, strSignDate,
				streetBlock, streetBlockName, streetName, telFax, telMobile, telecomServiceId, toPrice, userCreate);
	}

	
}
