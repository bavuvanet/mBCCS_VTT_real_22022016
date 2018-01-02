package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RepresentativeCustObj implements Serializable {
	
	private String custId;
	private String busType;
	private String idType;
	private String idNo;
	private String idIssuePlace;
	private String idIssueDate;
	private String name;
	private String birthDate;
	private String sex;
	
	private String nationality;
	private String address;
	private String areaCode;
	private String province;
	private String district;
	private String precinct;
	private String streetName;
	private String streetBlock;
	private String home;
	private String status;
	
	private String addedUser;
	private String addedDate;
	
	private CustomerType customerType;
	private CustomerGroup customerGroup;
	
    private String popNo;
    private String popIssuePlace;
    private String popIssueDate;
    private String popIssueDateStr;
    
    private String idNoAM;
    private String idNoAMIssuePlace;
    private String idNoAMIssueDate;
    private String idNoAMIssueDateStr;

    
//    "hc",
//    "hcIssueDate",
//    "hcIssueDateStr",
//    "hcExpireDate",
//    "hcExpireDateStr",
    
    private String hc;
    private String hcIssueDateStr;
    private String hcExpireDateStr;
    private String hcIssuePlace;
	
	
	
	public String getPopNo() {
		return popNo;
	}
	public void setPopNo(String popNo) {
		this.popNo = popNo;
	}
	public String getPopIssuePlace() {
		return popIssuePlace;
	}
	public void setPopIssuePlace(String popIssuePlace) {
		this.popIssuePlace = popIssuePlace;
	}
	public String getPopIssueDate() {
		return popIssueDate;
	}
	public void setPopIssueDate(String popIssueDate) {
		this.popIssueDate = popIssueDate;
	}
	public String getPopIssueDateStr() {
		return popIssueDateStr;
	}
	public void setPopIssueDateStr(String popIssueDateStr) {
		this.popIssueDateStr = popIssueDateStr;
	}
	public String getIdNoAM() {
		return idNoAM;
	}
	public void setIdNoAM(String idNoAM) {
		this.idNoAM = idNoAM;
	}
	public String getIdNoAMIssuePlace() {
		return idNoAMIssuePlace;
	}
	public void setIdNoAMIssuePlace(String idNoAMIssuePlace) {
		this.idNoAMIssuePlace = idNoAMIssuePlace;
	}
	public String getIdNoAMIssueDate() {
		return idNoAMIssueDate;
	}
	public void setIdNoAMIssueDate(String idNoAMIssueDate) {
		this.idNoAMIssueDate = idNoAMIssueDate;
	}
	public String getIdNoAMIssueDateStr() {
		return idNoAMIssueDateStr;
	}
	public void setIdNoAMIssueDateStr(String idNoAMIssueDateStr) {
		this.idNoAMIssueDateStr = idNoAMIssueDateStr;
	}
	public String getHc() {
		return hc;
	}
	public void setHc(String hc) {
		this.hc = hc;
	}
	public String getHcIssueDateStr() {
		return hcIssueDateStr;
	}
	public void setHcIssueDateStr(String hcIssueDateStr) {
		this.hcIssueDateStr = hcIssueDateStr;
	}
	public String getHcExpireDateStr() {
		return hcExpireDateStr;
	}
	public void setHcExpireDateStr(String hcExpireDateStr) {
		this.hcExpireDateStr = hcExpireDateStr;
	}
	public String getHcIssuePlace() {
		return hcIssuePlace;
	}
	public void setHcIssuePlace(String hcIssuePlace) {
		this.hcIssuePlace = hcIssuePlace;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdIssuePlace() {
		return idIssuePlace;
	}
	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
	}
	public String getIdIssueDate() {
		return idIssueDate;
	}
	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPrecinct() {
		return precinct;
	}
	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetBlock() {
		return streetBlock;
	}
	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddedUser() {
		return addedUser;
	}
	public void setAddedUser(String addedUser) {
		this.addedUser = addedUser;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public CustomerGroup getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
	} 
	
	

}
