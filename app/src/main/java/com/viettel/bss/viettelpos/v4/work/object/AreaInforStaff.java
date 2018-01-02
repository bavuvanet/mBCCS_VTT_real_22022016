package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class AreaInforStaff implements Serializable {

	private String areaCode;
	private String provinceCode;
	private String districtCode;
	private String precinctCode;
	private String streetBlockCode;
	private String staffCode;
	private Long staffId;
	private String fullName;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getPrecinctCode() {
		return precinctCode;
	}

	public void setPrecinctCode(String precinctCode) {
		this.precinctCode = precinctCode;
	}

	public String getStreetBlockCode() {
		return streetBlockCode;
	}

	public void setStreetBlockCode(String streetBlockCode) {
		this.streetBlockCode = streetBlockCode;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"AreaInforStaff\":{\"areaCode\":\"%s\", \"provinceCode\":\"%s\", \"districtCode\":\"%s\", \"precinctCode\":\"%s\", \"streetBlockCode\":\"%s\", \"staffCode\":\"%s\", \"staffId\":\"%s\", \"fullName\":\"%s\"}}",
				areaCode, provinceCode, districtCode, precinctCode, streetBlockCode, staffCode, staffId, fullName);
	}

}
