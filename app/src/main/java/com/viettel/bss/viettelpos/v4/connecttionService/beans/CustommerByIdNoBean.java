package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class CustommerByIdNoBean implements Serializable, Cloneable {
	private String nameCustomer;
	private String idNo;
	private String custId;
	private String addreseCus;
	private String birthdayCus;
	private boolean ischeckCus = false;
	private String cusGroupId;
	private String cusType;
	private String busPermitNo;
	private String idType;
	private String ngaycap;
	private String province;
	private String district;
	private String precint;
	private String birthdayCusNew;
	private String idIssueDate;
	private String idIssuePlace;
	private String areaCode;
	private String sex;
	private String strIdExpire;
	private String tin;
	private String idExpireDate;
	private String nationality;
	private String cusTypeId;
	private String street_block;
	private String street;
	private String home;

	private String haveSubActive;
	private String requestSendOTP;

	private String notes;
	
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRequestSendOTP() {
		return requestSendOTP;
	}

	public void setRequestSendOTP(String requestSendOTP) {
		this.requestSendOTP = requestSendOTP;
	}

	public String getHaveSubActive() {
		return haveSubActive;
	}

	public void setHaveSubActive(String haveSubActive) {
		this.haveSubActive = haveSubActive;
	}

	private CustomerAttribute customerAttribute = new CustomerAttribute();

	private String custTypeId;

	private Contract contract;

	public String getAddreseCus() {
		return addreseCus;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getBirthdayCus() {
		return birthdayCus;
	}

	public String getBirthdayCusNew() {
		return birthdayCusNew;
	}

	public String getBusPermitNo() {
		return busPermitNo;
	}

	public Contract getContract() {
		return contract;
	}

	public String getCusGroupId() {
		return cusGroupId;
	}

	public String getCustId() {
		return custId;
	}

	public CustomerAttribute getCustomerAttribute() {
		return customerAttribute;
	}

	public String getCustTypeId() {
		return custTypeId;
	}

	public String getCusType() {
		return cusType;
	}

	public String getCusTypeId() {
		return cusTypeId;
	}

	public String getDistrict() {
		return district;
	}

	public String getHome() {
		return home;
	}

	public String getIdExpireDate() {
		return idExpireDate;
	}

	public String getIdIssueDate() {
		return idIssueDate;
	}

	public String getIdIssuePlace() {
		return idIssuePlace;
	}

	public String getIdNo() {
		return idNo;
	}

	public String getIdType() {
		return idType;
	}

	public String getNameCustomer() {
		return nameCustomer;
	}

	public String getNationality() {
		return nationality;
	}

	public String getNgaycap() {
		return ngaycap;
	}

	public String getPrecint() {
		return precint;
	}

	public String getProvince() {
		return province;
	}

	public String getSex() {
		return sex;
	}

	public String getStreet() {
		return street;
	}

	public String getStreet_block() {
		return street_block;
	}

	public String getStrIdExpire() {
		return strIdExpire;
	}

	public String getTin() {
		return tin;
	}

	public boolean isIscheckCus() {
		return ischeckCus;
	}

	public void setAddreseCus(String addreseCus) {
		this.addreseCus = addreseCus;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setBirthdayCus(String birthdayCus) {
		this.birthdayCus = birthdayCus;
	}

	public void setBirthdayCusNew(String birthdayCusNew) {
		this.birthdayCusNew = birthdayCusNew;
	}

	public void setBusPermitNo(String busPermitNo) {
		this.busPermitNo = busPermitNo;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public void setCusGroupId(String cusGroupId) {
		this.cusGroupId = cusGroupId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setCustomerAttribute(CustomerAttribute customerAttribute) {
		this.customerAttribute = customerAttribute;
	}

	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public void setCusTypeId(String cusTypeId) {
		this.cusTypeId = cusTypeId;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public void setIdExpireDate(String idExpireDate) {
		this.idExpireDate = idExpireDate;
	}

	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setIscheckCus(boolean ischeckCus) {
		this.ischeckCus = ischeckCus;
	}

	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setNgaycap(String ngaycap) {
		this.ngaycap = ngaycap;
	}

	public void setPrecint(String precint) {
		this.precint = precint;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStreet_block(String street_block) {
		this.street_block = street_block;
	}

	public void setStrIdExpire(String strIdExpire) {
		this.strIdExpire = strIdExpire;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	@Override
	public CustommerByIdNoBean clone() {
		try {
			return (CustommerByIdNoBean) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format(
				"{\"CustommerByIdNoBean\":{\"nameCustomer\":\"%s\", \"idNo\":\"%s\", \"custId\":\"%s\", \"addreseCus\":\"%s\", \"birthdayCus\":\"%s\", \"ischeckCus\":\"%s\", \"cusGroupId\":\"%s\", \"cusType\":\"%s\", \"busPermitNo\":\"%s\", \"idType\":\"%s\", \"ngaycap\":\"%s\", \"province\":\"%s\", \"district\":\"%s\", \"precint\":\"%s\", \"birthdayCusNew\":\"%s\", \"idIssueDate\":\"%s\", \"idIssuePlace\":\"%s\", \"areaCode\":\"%s\", \"sex\":\"%s\", \"strIdExpire\":\"%s\", \"tin\":\"%s\", \"idExpireDate\":\"%s\", \"nationality\":\"%s\", \"cusTypeId\":\"%s\", \"street_block\":\"%s\", \"street\":\"%s\", \"home\":\"%s\", \"haveSubActive\":\"%s\", \"requestSendOTP\":\"%s\", \"customerAttribute\":%s, \"custTypeId\":\"%s\", \"contract\":\"%s\"}}",
				nameCustomer, idNo, custId, addreseCus, birthdayCus, ischeckCus, cusGroupId, cusType, busPermitNo,
				idType, ngaycap, province, district, precint, birthdayCusNew, idIssueDate, idIssuePlace, areaCode, sex,
				strIdExpire, tin, idExpireDate, nationality, cusTypeId, street_block, street, home, haveSubActive,
				requestSendOTP, customerAttribute, custTypeId, contract);
	}

	public boolean isBusinessCustomer() {
		return getCustomerAttribute() != null && !CommonActivity.isNullOrEmpty(getCustomerAttribute().getIdNo());
	}
}
