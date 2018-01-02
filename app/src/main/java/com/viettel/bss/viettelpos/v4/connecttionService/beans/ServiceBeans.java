package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class ServiceBeans {
	private String groupName;
	private String groupProductId;
	private String parentId;
	private String telecomServiceId;
	private String serviceType;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupProductId() {
		return groupProductId;
	}
	public void setGroupProductId(String groupProductId) {
		this.groupProductId = groupProductId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTelecomServiceId() {
		return telecomServiceId;
	}
	public void setTelecomServiceId(String telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}
}
