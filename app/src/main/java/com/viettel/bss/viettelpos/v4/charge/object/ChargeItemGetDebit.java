package com.viettel.bss.viettelpos.v4.charge.object;

public class ChargeItemGetDebit {
	private String totCharge;
	private String adjustment;
	private String amountNotTax;
	private String amountTax;
	private String billCycleFrom;
	private String contractId;
	private String contractNo;
	private String custName;
	private String groupId;
	private String isdn;
	private String serviceName;
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
	public String getBillCycleFrom() {
		return billCycleFrom;
	}
	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
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
	
}
