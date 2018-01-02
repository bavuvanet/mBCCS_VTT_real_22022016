package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "assignIsdnStaffBean", strict = false)
public class AssignIsdnStaffBean {
	@Element(name = "id", required = false)
	private Long id;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "districtCode", required = false)
	private String districtCode;
	@Element(name = "districtName", required = false)
	private String districtName;
	@Element(name = "precinctCode", required = false)
	private String precinctCode;
	@Element(name = "precinctName", required = false)
	private String precinctName;
	@Element(name = "provinceCode", required = false)
	private String provinceCode;
	@Element(name = "provinceName", required = false)
	private String provinceName;
	@Element(name = "reStaffCode", required = false)
	private String reStaffCode;
	@Element(name = "reasonCode", required = false)
	private String reasonCode;
	@Element(name = "reasonName", required = false)
	private String reasonName;
	@Element(name = "status", required = false)
	private Integer status;

	@Element(name = "address", required = false)
	private String address;
	@Element(name = "select", required = false)
	private boolean select = false;
	@Element(name = "isdnInfor", required = false)
	private String isdnInfor;
	@Element(name = "isdnViettelSim", required = false)
	private String isdnViettelSim;
	@Element(name = "meetDate", required = false)
    private String meetDate;
	@Element(name = "note", required = false)
    private String note;
	@Element(name = "assignDate", required = false)
	private String assignDate;
	@Element(name = "userAssign", required = false)
	private String userAssign;
	@Element(name = "isDisable", required = false)
	private Integer isDisable;
	
	@Element(name = "isSim4G", required = false)
	private Integer isSim4G;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPrecinctCode() {
		return precinctCode;
	}

	public void setPrecinctCode(String precinctCode) {
		this.precinctCode = precinctCode;
	}

	public String getPrecinctName() {
		return precinctName;
	}

	public void setPrecinctName(String precinctName) {
		this.precinctName = precinctName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getReStaffCode() {
		return reStaffCode;
	}

	public void setReStaffCode(String reStaffCode) {
		this.reStaffCode = reStaffCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getIsdnInfor() {
		return isdnInfor;
	}

	public void setIsdnInfor(String isdnInfor) {
		this.isdnInfor = isdnInfor;
	}

	public String getIsdnViettelSim() {
		return isdnViettelSim;
	}

	public void setIsdnViettelSim(String isdnViettelSim) {
		this.isdnViettelSim = isdnViettelSim;
	}

	public String getMeetDate() {
		return meetDate;
	}

	public void setMeetDate(String meetDate) {
		this.meetDate = meetDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String getUserAssign() {
		return userAssign;
	}

	public void setUserAssign(String userAssign) {
		this.userAssign = userAssign;
	}

	public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getIsSim4G() {
		return isSim4G;
	}

	public void setIsSim4G(Integer isSim4G) {
		this.isSim4G = isSim4G;
	}

	
}
