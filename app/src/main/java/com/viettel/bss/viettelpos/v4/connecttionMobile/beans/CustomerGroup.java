package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerGroup implements Serializable {
	
	private String  code;
	private String  custTypeId;
	private String  depsciption;
	private String  id;
	private String  name;
	private String  status;
	
	private String  updateDate;
	private String  updateUser;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public String getDepsciption() {
		return depsciption;
	}
	public void setDepsciption(String depsciption) {
		this.depsciption = depsciption;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	} 
	
	
	

	

}
