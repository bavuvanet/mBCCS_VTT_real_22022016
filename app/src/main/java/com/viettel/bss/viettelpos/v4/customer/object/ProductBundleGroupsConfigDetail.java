package com.viettel.bss.viettelpos.v4.customer.object;

public class ProductBundleGroupsConfigDetail {
	private String telecomeServiceId;
	private String subType ="1";
	private String fromValue = "0";
	private String toValue = "0";
	private String pkType;
	private String pkId;
	private String shortNumber = "0";
	private String boss = "0";
	private String checkPOP = "0";
	private String isdnAccount;
	private String shortNumberInput = "";
	private Boolean isBoss = false;
	private Boolean isAdd = false;
	private Boolean isDelete = false;
	private Boolean isOld = false;
	private String subId;
	private String groupMemberId;
	
	
	
	public String getGroupMemberId() {
		return groupMemberId;
	}

	public void setGroupMemberId(String groupMemberId) {
		this.groupMemberId = groupMemberId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public Boolean getIsOld() {
		return isOld;
	}

	public void setIsOld(Boolean isOld) {
		this.isOld = isOld;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsBoss() {
		return isBoss;
	}

	public void setIsBoss(Boolean isBoss) {
		this.isBoss = isBoss;
	}

	public String getShortNumberInput() {
		return shortNumberInput;
	}

	public void setShortNumberInput(String shortNumberInput) {
		this.shortNumberInput = shortNumberInput;
	}

	public String getIsdnAccount() {
		return isdnAccount;
	}

	public void setIsdnAccount(String isdnAccount) {
		this.isdnAccount = isdnAccount;
	}

	public String getTelecomeServiceId() {
		return telecomeServiceId;
	}

	public void setTelecomeServiceId(String telecomeServiceId) {
		this.telecomeServiceId = telecomeServiceId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getFromValue() {
		if (fromValue == null || fromValue.isEmpty()) {
			return "0";
		}
		return fromValue;
	}

	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}

	public String getToValue() {
		return toValue;
	}

	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

	public String getPkType() {
		return pkType;
	}

	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getShortNumber() {
		return shortNumber;
	}

	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public String getCheckPOP() {
		return checkPOP;
	}

	public void setCheckPOP(String checkPOP) {
		this.checkPOP = checkPOP;
	}

}
