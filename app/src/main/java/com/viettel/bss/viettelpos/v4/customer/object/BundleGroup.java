package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;

public class BundleGroup implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 112542L;
	private String code;
	private String name;
	private String groupId;
	private String isdn;
	private String productBundleGroupsConfigId;
	private String status = "";
	private String createDatetime = "";
	
	
	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProductBundleGroupsConfigId() {
		return productBundleGroupsConfigId;
	}

	public void setProductBundleGroupsConfigId(
			String productBundleGroupsConfigId) {
		this.productBundleGroupsConfigId = productBundleGroupsConfigId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}


}
