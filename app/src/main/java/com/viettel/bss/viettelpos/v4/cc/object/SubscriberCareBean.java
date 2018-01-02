package com.viettel.bss.viettelpos.v4.cc.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "SubscriberCareBean", strict = false)
public class SubscriberCareBean {

	@Element(name = "receiverMobile", required = false)
	private String receiverMobile;
	@Element(name = "custName", required = false)
	private String custName;
	@Element(name = "empPhone", required = false)
	private String empPhone;
	@Element(name = "giftAllowType", required = false)
	private String giftAllowType;
	@Element(name = "giftCode", required = false)
	private String giftCode;
	@Element(name = "giftDate", required = false)
	private String giftDate;
	@Element(name = "giftId", required = false)
	private String giftId;
	@Element(name = "giftImCode", required = false)
	private String giftImCode;
	@Element(name = "giftName", required = false)
	private String giftName;
	@Element(name = "giftType", required = false)
	private String giftType;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "provinceCode", required = false)
	private String provinceCode;
	@Element(name = "subCareId", required = false)
	private String subCareId;
	@Element(name = "careType", required = false)
	private String careType;
	@Element(name = "giftTypeName", required = false)
	private String giftTypeName;
	@Element(name = "giftAllowTypeName", required = false)
	private String giftAllowTypeName;
	@Element(name = "subCareDetailId", required = false)
	private String subCareDetailId;
	@Element(name = "receiverRel", required = false)
	private String receiverRel;
	@Element(name = "giftStatus", required = false)
	private String giftStatus;

	private String status;


	public String getReceiverRel() {
		return receiverRel;
	}

	public void setReceiverRel(String receiverRel) {
		this.receiverRel = receiverRel;
	}

	public String getGiftStatus() {
		return giftStatus;
	}

	public void setGiftStatus(String giftStatus) {
		this.giftStatus = giftStatus;
	}

	public String getSubCareDetailId() {
		return subCareDetailId;
	}

	public void setSubCareDetailId(String subCareDetailId) {
		this.subCareDetailId = subCareDetailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGiftAllowTypeName() {
		return giftAllowTypeName;
	}

	public void setGiftAllowTypeName(String giftAllowTypeName) {
		this.giftAllowTypeName = giftAllowTypeName;
	}

	public String getGiftTypeName() {
		return giftTypeName;
	}

	public void setGiftTypeName(String giftTypeName) {
		this.giftTypeName = giftTypeName;
	}

	public String getCareType() {
		return careType;
	}

	public void setCareType(String careType) {
		this.careType = careType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getGiftAllowType() {
		return giftAllowType;
	}

	public void setGiftAllowType(String giftAllowType) {
		this.giftAllowType = giftAllowType;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	public String getGiftDate() {
		return giftDate;
	}

	public void setGiftDate(String giftDate) {
		this.giftDate = giftDate;
	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getGiftImCode() {
		return giftImCode;
	}

	public void setGiftImCode(String giftImCode) {
		this.giftImCode = giftImCode;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getSubCareId() {
		return subCareId;
	}

	public void setSubCareId(String subCareId) {
		this.subCareId = subCareId;
	}

}
