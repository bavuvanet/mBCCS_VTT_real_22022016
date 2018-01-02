package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BonusCommTransaction implements Serializable {
	
	private String billDate;
	private String groupName;  
	private String quantity;
	private String service;
	private String totalMoneyStr;
	
	private String fromDate;
	private String toDate; 
	
	
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getTotalMoneyStr() {
		return totalMoneyStr;
	}
	public void setTotalMoneyStr(String totalMoneyStr) {
		this.totalMoneyStr = totalMoneyStr;
	}
	
	
	
	
}
