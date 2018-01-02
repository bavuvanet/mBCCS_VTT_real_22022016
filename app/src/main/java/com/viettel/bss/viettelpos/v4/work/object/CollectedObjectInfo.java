package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class CollectedObjectInfo implements Serializable{
	
	private String id;
	private String parentId;
	private String groupId;
	private String createTime;
	private String updateTime;
	private String createUser;
	private	String value;
	private String reportCircle;
	private String areaCode;
	private String status;
	private String yearCircle;
	
	
	public CollectedObjectInfo() {
		super();
	}
	public CollectedObjectInfo(String parentId, String groupId,
			String createTime, String updateTime, String createUser,
			String value, String reportCircle, String areaCode, String status,
			String yearCircle) {
		super();
		this.parentId = parentId;
		this.groupId = groupId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUser = createUser;
		this.value = value;
		this.reportCircle = reportCircle;
		this.areaCode = areaCode;
		this.status = status;
		this.yearCircle = yearCircle;
	}
	
	public CollectedObjectInfo(String id, String parentId, String groupId,
			String createTime, String updateTime, String createUser,
			String value, String reportCircle, String areaCode, String status,
			String yearCircle) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.groupId = groupId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUser = createUser;
		this.value = value;
		this.reportCircle = reportCircle;
		this.areaCode = areaCode;
		this.status = status;
		this.yearCircle = yearCircle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getReportCircle() {
		return reportCircle;
	}
	public void setReportCircle(String reportCircle) {
		this.reportCircle = reportCircle;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYearCircle() {
		return yearCircle;
	}
	public void setYearCircle(String yearCircle) {
		this.yearCircle = yearCircle;
	}
	
	
	
	
}
