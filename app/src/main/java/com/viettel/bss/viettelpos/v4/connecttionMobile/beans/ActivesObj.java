package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActivesObj implements Serializable {

	private String createDate;
	private String fieldActiveCode;
	private String fieldActiveId;
	private String groupCustomerId;
	private String name;
	
	private String status;
	private String userName;
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFieldActiveCode() {
		return fieldActiveCode;
	}
	public void setFieldActiveCode(String fieldActiveCode) {
		this.fieldActiveCode = fieldActiveCode;
	}
	public String getFieldActiveId() {
		return fieldActiveId;
	}
	public void setFieldActiveId(String fieldActiveId) {
		this.fieldActiveId = fieldActiveId;
	}
	public String getGroupCustomerId() {
		return groupCustomerId;
	}
	public void setGroupCustomerId(String groupCustomerId) {
		this.groupCustomerId = groupCustomerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	} 
	
	
}
