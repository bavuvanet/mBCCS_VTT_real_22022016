package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class TaskAssignBO implements Serializable {

    private String taskAssignId;
    private String taskCode;
    private String taskName;
    private String account;
    private String serviceType;
    private String serviceName;
    private String mngtCode;
    private String staffCode;
    private String status;
    private String reasonId;
    private String assignDate;
    private String updateDate;
    private String endAssignDate;
    private String description;
    private String reasonName;
    
	public String getTaskAssignId() {
		return taskAssignId;
	}
	public void setTaskAssignId(String taskAssignId) {
		this.taskAssignId = taskAssignId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMngtCode() {
		return mngtCode;
	}
	public void setMngtCode(String mngtCode) {
		this.mngtCode = mngtCode;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
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
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getEndAssignDate() {
		return endAssignDate;
	}
	public void setEndAssignDate(String endAssignDate) {
		this.endAssignDate = endAssignDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	@Override
	public String toString() {
		return String.format(
				"{\"TaskAssignBO\":{\"taskAssignId\":\"%s\", \"taskCode\":\"%s\", \"taskName\":\"%s\", \"account\":\"%s\", \"serviceType\":\"%s\", \"serviceName\":\"%s\", \"mngtCode\":\"%s\", \"staffCode\":\"%s\", \"status\":\"%s\", \"reasonId\":\"%s\", \"assignDate\":\"%s\", \"updateDate\":\"%s\", \"endAssignDate\":\"%s\", \"description\":\"%s\"}}",
				taskAssignId, taskCode, taskName, account, serviceType, serviceName, mngtCode, staffCode, status,
				reasonId, assignDate, updateDate, endAssignDate, description);
	}
}
