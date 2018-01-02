package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class CustomerGroupBeans implements Serializable{
	private String code;
	private String custTypeId;
	private String depsciption;
	private String idCusGroup;
	private String nameCusGroup;
	private String status;
	private String updateDate;
	private String updateUser;
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
	public String getIdCusGroup() {
		return idCusGroup;
	}
	public void setIdCusGroup(String idCusGroup) {
		this.idCusGroup = idCusGroup;
	}
	public String getNameCusGroup() {
		return nameCusGroup;
	}
	public void setNameCusGroup(String nameCusGroup) {
		this.nameCusGroup = nameCusGroup;
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
