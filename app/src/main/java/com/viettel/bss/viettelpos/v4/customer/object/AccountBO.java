package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "AccountBO", strict = false)
public class AccountBO implements Serializable{
	@Element(name = "accountId", required = false)
	private Long accountId;
	@Element(name = "accountIdText", required = false)
	private String accountIdText;
	@Element(name = "accountNo", required = false)
	private String accountNo;
	@Element(name = "actionReason", required = false)
	private Long actionReason;
	@Element(name = "addressPayment", required = false)
	private String addressPayment;
	@Element(name = "addressPrint", required = false)
	private String addressPrint;
	@Element(name = "billAddress", required = false)
	private String billAddress;
	@Element(name = "billCycleId", required = false)
	private Long billCycleId;
	@Element(name = "billCycleName", required = false)
	private String billCycleName;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "custId", required = false)
	private Long custId;
	@Element(name = "custType", required = false)
	private String custType;
	@Element(name = "debtStatus", required = false)
	private String debtStatus;
	@Element(name = "debtStatusText", required = false)
	private String debtStatusText;
	@Element(name = "effectDate", required = false)
	private String effectDate;
	@Element(name = "endDatetime", required = false)
	private String endDatetime;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "isdnSearch", required = false)
	private String isdnSearch;
	@Element(name = "limitUsage", required = false)
	private Long limitUsage;
	@Element(name = "noticeCharge", required = false)
	private String noticeCharge;
	@Element(name = "noticeChargeAddress", required = false)
	private String noticeChargeAddress;
	@Element(name = "noticeChargeName", required = false)
	private String noticeChargeName;
	@Element(name = "numSubscriber", required = false)
	private Long numSubscriber;
	@Element(name = "payMethod", required = false)
	private String payMethod;
	@Element(name = "payMethodName", required = false)
	private String payMethodName;
	@Element(name = "phoneContact", required = false)
	private String phoneContact;
	@Element(name = "printContractNo", required = false)
	private String printContractNo;
	@Element(name = "printMethod", required = false)
	private String printMethod;
	@Element(name = "printMethodName", required = false)
	private String printMethodName;
	@Element(name = "receiveInvoice", required = false)
	private String receiveInvoice;
	@Element(name = "signDate", required = false)
	private String signDate;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "statusText", required = false)
	private String statusText;
	@Element(name = "street", required = false)
	private String street;
	@Element(name = "subId", required = false)
	private Long subId;
	@Element(name = "telMobile", required = false)
	private String telMobile;
	@Element(name = "totalDepositSub", required = false)
	private Long totalDepositSub;
	@Element(name = "updateDatetime", required = false)
	private String updateDatetime;
	@Element(name = "updateUser", required = false)
	private String updateUser;
	@Element(name = "eMail", required = false)
	private String eMail;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountIdText() {
		return accountIdText;
	}
	public void setAccountIdText(String accountIdText) {
		this.accountIdText = accountIdText;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Long getActionReason() {
		return actionReason;
	}
	public void setActionReason(Long actionReason) {
		this.actionReason = actionReason;
	}
	public String getAddressPayment() {
		return addressPayment;
	}
	public void setAddressPayment(String addressPayment) {
		this.addressPayment = addressPayment;
	}
	public String getAddressPrint() {
		return addressPrint;
	}
	public void setAddressPrint(String addressPrint) {
		this.addressPrint = addressPrint;
	}
	public String getBillAddress() {
		return billAddress;
	}
	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}
	public Long getBillCycleId() {
		return billCycleId;
	}
	public void setBillCycleId(Long billCycleId) {
		this.billCycleId = billCycleId;
	}
	public String getBillCycleName() {
		return billCycleName;
	}
	public void setBillCycleName(String billCycleName) {
		this.billCycleName = billCycleName;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getDebtStatus() {
		return debtStatus;
	}
	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus;
	}
	public String getDebtStatusText() {
		return debtStatusText;
	}
	public void setDebtStatusText(String debtStatusText) {
		this.debtStatusText = debtStatusText;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getEndDatetime() {
		return endDatetime;
	}
	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getIsdnSearch() {
		return isdnSearch;
	}
	public void setIsdnSearch(String isdnSearch) {
		this.isdnSearch = isdnSearch;
	}
	public Long getLimitUsage() {
		return limitUsage;
	}
	public void setLimitUsage(Long limitUsage) {
		this.limitUsage = limitUsage;
	}
	public String getNoticeCharge() {
		return noticeCharge;
	}
	public void setNoticeCharge(String noticeCharge) {
		this.noticeCharge = noticeCharge;
	}
	public String getNoticeChargeAddress() {
		return noticeChargeAddress;
	}
	public void setNoticeChargeAddress(String noticeChargeAddress) {
		this.noticeChargeAddress = noticeChargeAddress;
	}
	public String getNoticeChargeName() {
		return noticeChargeName;
	}
	public void setNoticeChargeName(String noticeChargeName) {
		this.noticeChargeName = noticeChargeName;
	}
	public Long getNumSubscriber() {
		return numSubscriber;
	}
	public void setNumSubscriber(Long numSubscriber) {
		this.numSubscriber = numSubscriber;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getPhoneContact() {
		return phoneContact;
	}
	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}
	public String getPrintContractNo() {
		return printContractNo;
	}
	public void setPrintContractNo(String printContractNo) {
		this.printContractNo = printContractNo;
	}
	public String getPrintMethod() {
		return printMethod;
	}
	public void setPrintMethod(String printMethod) {
		this.printMethod = printMethod;
	}
	public String getPrintMethodName() {
		return printMethodName;
	}
	public void setPrintMethodName(String printMethodName) {
		this.printMethodName = printMethodName;
	}
	public String getReceiveInvoice() {
		return receiveInvoice;
	}
	public void setReceiveInvoice(String receiveInvoice) {
		this.receiveInvoice = receiveInvoice;
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
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Long getSubId() {
		return subId;
	}
	public void setSubId(Long subId) {
		this.subId = subId;
	}
	public String getTelMobile() {
		return telMobile;
	}
	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}
	public Long getTotalDepositSub() {
		return totalDepositSub;
	}
	public void setTotalDepositSub(Long totalDepositSub) {
		this.totalDepositSub = totalDepositSub;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	
}
