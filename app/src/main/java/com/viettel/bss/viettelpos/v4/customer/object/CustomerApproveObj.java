package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return",strict=false)
public class CustomerApproveObj implements Serializable {
	private static final long serialVersionUID = 1L;
	@Element (name = "fileName", required = false)
	private String fileName;
	@Element (name = "fileAttach", required = false)
	private String fileAttach;
	@Element (name = "country", required = false)
	private String country;
	@Element (name = "isdn", required = false)
	private String isdn;
	@Element (name = "createUser", required = false)
	private String creator;
	@Element (name = "createDatetime", required = false)
	private String dateCreated;
	@Element (name = "fileLink", required = false)
	private String fileLink;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "isChecked", required = false)
	private boolean isChecked;
	@Element (name = "customerName", required = false)
	private String customerName;
	@Element (name = "customerType", required = false)
	private String customerType;
	@Element (name = "customerCode", required = false)
	private String customerCode;
	@Element (name = "birthday", required = false)
	private String birthday;
	@Element (name = "sex", required = false)
	private String sex;
	@Element (name = "documentType", required = false)
	private String documentType;
	@Element (name = "idNo", required = false)
	private String idNo;
	@Element (name = "locationSuppleId", required = false)
	private String locationSuppleId;
	@Element (name = "dateSuppleId", required = false)
	private String dateSuppleId;
	@Element (name = "taxCode", required = false)
	private String taxCode;
	@Element (name = "houseHold", required = false)
	private String houseHold;
	@Element (name = "locationSupplyHouseHold", required = false)
	private String locationSupplyHouseHold;
	@Element (name = "dateSupplyHouseHold", required = false)
	private String dateSupplyHouseHold;
	@Element (name = "fullAddress", required = false)
	private String fullAddress;
	@Element (name = "phoneNumber", required = false)
	private String phoneNumber;
	@Element (name = "note", required = false)
	private String note;
	@Element (name = "businessLicense", required = false)
	private String businessLicense;
	@Element (name = "subId", required = false)
	private String subId;
	@Element (name = "subInfoId", required = false)
	private String subFileId;
	
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getFileLink() {
		return fileLink;
	}
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getLocationSuppleId() {
		return locationSuppleId;
	}
	public void setLocationSuppleId(String locationSuppleId) {
		this.locationSuppleId = locationSuppleId;
	}
	public String getDateSuppleId() {
		return dateSuppleId;
	}
	public void setDateSuppleId(String dateSuppleId) {
		this.dateSuppleId = dateSuppleId;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getHouseHold() {
		return houseHold;
	}
	public void setHouseHold(String houseHold) {
		this.houseHold = houseHold;
	}
	public String getLocationSupplyHouseHold() {
		return locationSupplyHouseHold;
	}
	public void setLocationSupplyHouseHold(String locationSupplyHouseHold) {
		this.locationSupplyHouseHold = locationSupplyHouseHold;
	}
	public String getDateSupplyHouseHold() {
		return dateSupplyHouseHold;
	}
	public void setDateSupplyHouseHold(String dateSupplyHouseHold) {
		this.dateSupplyHouseHold = dateSupplyHouseHold;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	
	
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	public String getSubFileId() {
		return subFileId;
	}
	public void setSubFileId(String subFileId) {
		this.subFileId = subFileId;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getFileAttach() {
		return fileAttach;
	}
	public void setFileAttach(String fileAttach) {
		this.fileAttach = fileAttach;
	}
	public CustomerApproveObj(String isdn, String creator, String dateCreated, String status) {
		super();
		this.isdn = isdn;
		this.creator = creator;
		this.dateCreated = dateCreated;
		this.status = status;
	}
	public CustomerApproveObj() {
		// TODO Auto-generated constructor stub
	}
	
	

}
