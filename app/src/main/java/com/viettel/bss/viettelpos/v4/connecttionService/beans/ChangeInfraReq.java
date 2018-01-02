package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class ChangeInfraReq implements Serializable{

	
	private String account;
	private String infraType;
	private String oldTechnology;
	private String parentSubId;
	private String serviceType;
	private String subId;
	
	private String newAccount;
	private String newServiceType;
	
	
	public String getNewAccount() {
		return newAccount;
	}
	public void setNewAccount(String newAccount) {
		this.newAccount = newAccount;
	}
	public String getNewServiceType() {
		return newServiceType;
	}
	public void setNewServiceType(String newServiceType) {
		this.newServiceType = newServiceType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getInfraType() {
		return infraType;
	}
	public void setInfraType(String infraType) {
		this.infraType = infraType;
	}
	public String getOldTechnology() {
		return oldTechnology;
	}
	public void setOldTechnology(String oldTechnology) {
		this.oldTechnology = oldTechnology;
	}
	public String getParentSubId() {
		return parentSubId;
	}
	public void setParentSubId(String parentSubId) {
		this.parentSubId = parentSubId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	
	
	
	
}
