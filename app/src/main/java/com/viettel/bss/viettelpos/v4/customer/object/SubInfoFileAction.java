package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
class SubInfoFileAction {
	@Element(name = "fileName", required = false)
	private String fileName;
	@Element(name = "actionCode", required = false)
	private String actionCode;
	@Element(name = "approvedDatetime", required = false)
	private String approvedDatetime;
	@Element(name = "approvedUser", required = false)
	private String approvedUser;
	@Element(name = "branchShopId", required = false)
	private Long branchShopId;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "fileAttach", required = false)
	private String fileAttach;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "subId", required = false)
	private long subId;
	@Element(name = "subInfoId", required = false)
	private Long subInfoId;

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getApprovedDatetime() {
		return approvedDatetime;
	}

	public void setApprovedDatetime(String approvedDatetime) {
		this.approvedDatetime = approvedDatetime;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Long getBranchShopId() {
		return branchShopId;
	}

	public void setBranchShopId(Long branchShopId) {
		this.branchShopId = branchShopId;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(String fileAttach) {
		this.fileAttach = fileAttach;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public Long getSubInfoId() {
		return subInfoId;
	}

	public void setSubInfoId(Long subInfoId) {
		this.subInfoId = subInfoId;
	}
}
