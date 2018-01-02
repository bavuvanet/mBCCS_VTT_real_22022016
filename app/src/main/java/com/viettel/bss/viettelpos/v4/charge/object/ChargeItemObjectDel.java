package com.viettel.bss.viettelpos.v4.charge.object;

import java.io.Serializable;

public class ChargeItemObjectDel implements Serializable {
	private String contractId;
	private String nameCustomer;
	private String status;
	private boolean isCheck = false;
	private String billCycleFrom;
	private ChargeItemGetDebit debitContract;
	private String address ;
	private String contractNo;
	private String custName;
	private String groupId;
	private String isdn;
	private String serviceName;
	private String totCharge;
	private String adjustment;
	private String amountNotTax;
	private String amountTax;
	private String telFax;
	
	
	// them
	private String priorDebit;
	private String payMethodCode;
	private String contractFormMngt;
	private String contractFormMngtName;
	private String debit;
	private String hotCharge;
	private String payMethodName;
	private String payer;
	private String paymentStatus;
	
	
	
	
	
	
	
	
	
	
	
	public String getPriorDebit() {
		return priorDebit;
	}
	public void setPriorDebit(String priorDebit) {
		this.priorDebit = priorDebit;
	}
	public String getPayMethodCode() {
		return payMethodCode;
	}
	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}
	public String getContractFormMngt() {
		return contractFormMngt;
	}
	public void setContractFormMngt(String contractFormMngt) {
		this.contractFormMngt = contractFormMngt;
	}
	public String getContractFormMngtName() {
		return contractFormMngtName;
	}
	public void setContractFormMngtName(String contractFormMngtName) {
		this.contractFormMngtName = contractFormMngtName;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getHotCharge() {
		return hotCharge;
	}
	public void setHotCharge(String hotCharge) {
		this.hotCharge = hotCharge;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getTelFax() {
		return telFax;
	}
	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}
	public String getTotCharge() {
		return totCharge;
	}
	public void setTotCharge(String totCharge) {
		this.totCharge = totCharge;
	}
	public String getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(String adjustment) {
		this.adjustment = adjustment;
	}
	public String getAmountNotTax() {
		return amountNotTax;
	}
	public void setAmountNotTax(String amountNotTax) {
		this.amountNotTax = amountNotTax;
	}
	public String getAmountTax() {
		return amountTax;
	}
	public void setAmountTax(String amountTax) {
		this.amountTax = amountTax;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ChargeItemGetDebit getDebitContract() {
		return debitContract;
	}
	public void setDebitContract(ChargeItemGetDebit debitContract) {
		this.debitContract = debitContract;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getBillCycleFrom() {
		return billCycleFrom;
	}
	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}
	public String getNameCustomer() {
		return nameCustomer;
	}
	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
}
