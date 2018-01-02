package com.viettel.bss.viettelpos.v4.charge.object;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class AreaObj implements Serializable {
	private int id;
	private String areaCode;
	private String parentCode;
	private String cenCode;
	private String province;
	private String district;
	private String precinct;
	private String name;
	private String fullNamel;
	private String street;
	private String streetName;
	private String streetBlock;
	private String fromDate;
	private String toDate;
	private String home;
	private String provinceName;
	private String districtName;
	private String precinctName;

	private String note = "";
	private int selectedCount;

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrecinctName() {
		return precinctName;
	}

	public void setPrecinctName(String precinctName) {
		this.precinctName = precinctName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

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

	public AreaObj() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getCenCode() {
		return cenCode;
	}

	public void setCenCode(String cenCode) {
		this.cenCode = cenCode;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullNamel() {
		return fullNamel;
	}

	public void setFullNamel(String fullNamel) {
		this.fullNamel = fullNamel;
	}

	@Override
	public String toString() {
		return CommonActivity.convertUnicode2Nosign(areaCode + " " + name).toLowerCase().trim();
	}
}
