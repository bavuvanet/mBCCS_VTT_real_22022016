package com.viettel.bss.viettelpos.v4.sale.object;

public class SaleTransBankplus {
	private String saleTransBankPlusID;
	private String isdnBankPlus;
	private String saleServiceName;
	private String saleTransType;
	private String balance;
	private String createDate;
	private String dateApprove;
	private String maxDebitDay;
	private String requestIdBankPlus;
	private String statusName;
	private String feeAmount;
	private boolean checked = false;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getSaleTransBankPlusID() {
		return saleTransBankPlusID;
	}
	public void setSaleTransBankPlusID(String saleTransBankPlusID) {
		this.saleTransBankPlusID = saleTransBankPlusID;
	}
	public String getIsdnBankPlus() {
		return isdnBankPlus;
	}
	public void setIsdnBankPlus(String isdnBankPlus) {
		this.isdnBankPlus = isdnBankPlus;
	}
	public String getSaleServiceName() {
		return saleServiceName;
	}
	public void setSaleServiceName(String saleServiceName) {
		this.saleServiceName = saleServiceName;
	}
	public String getSaleTransType() {
		return saleTransType;
	}
	public void setSaleTransType(String saleTransType) {
		this.saleTransType = saleTransType;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDateApprove() {
		return dateApprove;
	}
	public void setDateApprove(String dateApprove) {
		this.dateApprove = dateApprove;
	}
	public String getMaxDebitDay() {
		return maxDebitDay;
	}
	public void setMaxDebitDay(String maxDebitDay) {
		this.maxDebitDay = maxDebitDay;
	}
	public String getRequestIdBankPlus() {
		return requestIdBankPlus;
	}
	public void setRequestIdBankPlus(String requestIdBankPlus) {
		this.requestIdBankPlus = requestIdBankPlus;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
}
