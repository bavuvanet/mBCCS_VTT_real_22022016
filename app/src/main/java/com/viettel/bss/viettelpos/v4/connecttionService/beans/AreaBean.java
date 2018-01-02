package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class AreaBean implements Serializable{
	private String province;
	private String district;
	private String precinct;
	private String fullAddress;
	private String nameProvince;
	private String nameDistrict;
	private String namePrecint;
	private String nameStreetBlock;
	private String streetBlock;
	private String street;
	private String homeNumber;

	private String areaCode;
	
	
	
	
	public AreaBean(String province, String district, String precinct,
			String fullAddress) {
		super();
		this.province = province;
		this.district = district;
		this.precinct = precinct;
		this.fullAddress = fullAddress;
	}

	public AreaBean() {
		super();
	}

	public AreaBean(String province, String district, String precinct,
			String nameProvince, String nameDistrict, String namePrecint) {
		super();
		this.province = province;
		this.district = district;
		this.precinct = precinct;
		this.nameProvince = nameProvince;
		this.nameDistrict = nameDistrict;
		this.namePrecint = namePrecint;
	}

	public String getNameProvince() {
		return nameProvince;
	}

	public void setNameProvince(String nameProvince) {
		this.nameProvince = nameProvince;
	}

	public String getNameDistrict() {
		return nameDistrict;
	}

	public void setNameDistrict(String nameDistrict) {
		this.nameDistrict = nameDistrict;
	}

	public String getNamePrecint() {
		return namePrecint;
	}

	public void setNamePrecint(String namePrecint) {
		this.namePrecint = namePrecint;
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

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

	public String getNameStreetBlock() {
		return nameStreetBlock;
	}

	public void setNameStreetBlock(String nameStreetBlock) {
		this.nameStreetBlock = nameStreetBlock;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	

}
