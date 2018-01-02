package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class RequestBeans  implements Serializable{
	private String idRequest;
	private String serviceRequest;
	private String dateRequest;
	private String stausRequest;
	private String account;
	

	private String actionProfileId;
	private String actionProfileStatus;
	
    private String regReasonId;
    private String serviceType;
    private String telecomServiceId;
    
	public String getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(String telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRegReasonId() {
		return regReasonId;
	}

	public void setRegReasonId(String regReasonId) {
		this.regReasonId = regReasonId;
	}

	public String getActionProfileId() {
		return actionProfileId;
	}

	public void setActionProfileId(String actionProfileId) {
		this.actionProfileId = actionProfileId;
	}

	public String getActionProfileStatus() {
		return actionProfileStatus;
	}

	public void setActionProfileStatus(String actionProfileStatus) {
		this.actionProfileStatus = actionProfileStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}
	public String getServiceRequest() {
		return serviceRequest;
	}
	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
	public String getDateRequest() {
		return dateRequest;
	}
	public void setDateRequest(String dateRequest) {
		this.dateRequest = dateRequest;
	}
	public String getStausRequest() {
		return stausRequest;
	}
	public void setStausRequest(String stausRequest) {
		this.stausRequest = stausRequest;
	}
	
}
