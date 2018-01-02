package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "FullAddressDTO", strict = false)
public class FullAddressDTO implements Serializable{
	@Element (name = "address2FirstLevel", required = false)
	private String address2FirstLevel;
	@Element (name = "areaCode", required = false)
	private String areaCode;
	@Element (name = "district", required = false)
	private LocationDTO district;
	@Element (name = "fullAddress", required = false)
	private String fullAddress;
	@Element (name = "precinct", required = false)
	private LocationDTO precinct;
	@Element (name = "province", required = false)
	private LocationDTO province;
	@Element (name = "street", required = false)
	private String street;
	@Element (name = "streetBlock", required = false)
	private LocationDTO streetBlock;
	@Element (name = "number", required = false)
	private String number;
	public String getAddress2FirstLevel() {
		return address2FirstLevel;
	}
	public void setAddress2FirstLevel(String address2FirstLevel) {
		this.address2FirstLevel = address2FirstLevel;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public LocationDTO getDistrict() {
		return district;
	}
	public void setDistrict(LocationDTO district) {
		this.district = district;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public LocationDTO getPrecinct() {
		return precinct;
	}
	public void setPrecinct(LocationDTO precinct) {
		this.precinct = precinct;
	}
	public LocationDTO getProvince() {
		return province;
	}
	public void setProvince(LocationDTO province) {
		this.province = province;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public LocationDTO getStreetBlock() {
		return streetBlock;
	}
	public void setStreetBlock(LocationDTO streetBlock) {
		this.streetBlock = streetBlock;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
