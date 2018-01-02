package com.viettel.bss.viettelpos.v4.customview.obj;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "CustomerObj", strict = false)
public class CustomerObj {
	
	
	@Element (name = "address", required = false)
	private String address;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "birthDate", required = false)
	private String birthDate;
	@Element (name = "busType", required = false)
	private String busType;
	@Element (name = "custId", required = false)
	private String custId;
	@Element (name = "idIssueDate", required = false)
	private String idIssueDate;
	@Element (name = "idIssuePlace", required = false)
	private String idIssuePlace;
	@Element (name = "idNo", required = false)
	private String idNo;
	@Element (name = "idType", required = false)
	private String idType;
	@Element (name = "sex", required = false)
	private String sex;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "updatedTime", required = false)
	private String updatedTime;
	@Element (name = "updatedUser", required = false)
	private String updatedUser;
	@Element (name = "precinct", required = false)
	private String precinct;
	@Element (name = "province", required = false)
	private String province;
	@Element (name = "home", required = false)
	private String home;
	@Element (name = "district", required = false)
	private String district;

	
	
	public CustomerObj() {
		super();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getIdIssueDate() {
		return idIssueDate;
	}

	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	public String getIdIssuePlace() {
		return idIssuePlace;
	}

	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

}
