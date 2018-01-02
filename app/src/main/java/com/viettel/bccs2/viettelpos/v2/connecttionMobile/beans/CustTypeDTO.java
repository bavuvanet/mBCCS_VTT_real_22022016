package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;


import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "CustTypeDTO", strict = false)
public class CustTypeDTO implements Serializable{
	@Element (name = "createDatetime", required = false)
	private String createDatetime;
	@Element (name = "createUser", required = false)
	private String createUser;
	@Element (name = "custType", required = false)
	private String custType;
	@Element (name = "groupType", required = false)
	private String groupType;
	@Element (name = "tax", required = false)
	private Long tax;
	@Element (name = "updateDatetime", required = false)
	private String updateDatetime;
	@Element (name = "updateUser", required = false)
	private String updateUser;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "description", required = false)
	private String description;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "plan", required = false)
	private String plan;

	private String cusGroup;

	public String getCusGroup() {
		return cusGroup;
	}

	public void setCusGroup(String cusGroup) {
		this.cusGroup = cusGroup;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
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
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public Long getTax() {
		return tax;
	}
	public void setTax(Long tax) {
		this.tax = tax;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
