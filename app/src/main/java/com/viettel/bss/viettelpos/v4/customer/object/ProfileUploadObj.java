package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProfileUploadObj implements Serializable {

	private String profileUploadId;
	private String staffCode;
	private String account;
	private String actionAuditId;
	private String actionCode;
	private String status;
	private String reasonId;
	private String recordCode;
	private String recordName;
	private String fileLength;
	private String filePath;

	private String createTime;
	private String updateTime;
	private String description;

	

	public String getProfileUploadId() {
		return profileUploadId;
	}

	public void setProfileUploadId(String profileUploadId) {
		this.profileUploadId = profileUploadId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getActionAuditId() {
		return actionAuditId;
	}

	public void setActionAuditId(String actionAuditId) {
		this.actionAuditId = actionAuditId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
