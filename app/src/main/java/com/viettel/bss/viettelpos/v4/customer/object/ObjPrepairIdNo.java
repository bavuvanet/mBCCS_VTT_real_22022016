package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;

public class ObjPrepairIdNo {
	private String  accountName;
	private String cusTomerTypeId;
	private String customerType;
	private String documentTypeId;
	private String documentType;
	private String idNoError;
	private String idNo;
	private String endDateDocument;
	private String houseHold;
	private String dateSupply;
	private String locationSupply;
	private String businessNumberError;
	private String businessNumber;
	private long reasonId;
	private String reasonName;
	private String subId;
	
	
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	private ArrayList<Spin> lstBusType = new ArrayList<>();

	public ArrayList<Spin> getLstBusType() {
		return lstBusType;
	}
	public void setLstBusType(ArrayList<Spin> lstBusType) {
		this.lstBusType = lstBusType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCusTomerTypeId() {
		return cusTomerTypeId;
	}
	public void setCusTomerTypeId(String cusTomerTypeId) {
		this.cusTomerTypeId = cusTomerTypeId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(String documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getIdNoError() {
		return idNoError;
	}
	public void setIdNoError(String idNoError) {
		this.idNoError = idNoError;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getEndDateDocument() {
		return endDateDocument;
	}
	public void setEndDateDocument(String endDateDocument) {
		this.endDateDocument = endDateDocument;
	}
	public String getHouseHold() {
		return houseHold;
	}
	public void setHouseHold(String houseHold) {
		this.houseHold = houseHold;
	}
	public String getDateSupply() {
		return dateSupply;
	}
	public void setDateSupply(String dateSupply) {
		this.dateSupply = dateSupply;
	}
	public String getLocationSupply() {
		return locationSupply;
	}
	public void setLocationSupply(String locationSupply) {
		this.locationSupply = locationSupply;
	}
	public String getBusinessNumberError() {
		return businessNumberError;
	}
	public void setBusinessNumberError(String businessNumberError) {
		this.businessNumberError = businessNumberError;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public long getReasonId() {
		return reasonId;
	}
	public void setReasonId(long reasonId) {
		this.reasonId = reasonId;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	
	
}
