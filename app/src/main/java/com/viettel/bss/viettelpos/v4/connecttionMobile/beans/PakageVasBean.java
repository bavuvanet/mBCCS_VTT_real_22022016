package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

public class PakageVasBean {
	
	private boolean isCheck = false;
	private String attributeList;
	private String cmConnected;
	private String constraintId;
	private String isConnected;
	private String relProductCode;
	private String relProductName;
	private String sizeParam;
	private String vasDefault;
	private String status = "";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}
	public String getCmConnected() {
		return cmConnected;
	}
	public void setCmConnected(String cmConnected) {
		this.cmConnected = cmConnected;
	}
	public String getConstraintId() {
		return constraintId;
	}
	public void setConstraintId(String constraintId) {
		this.constraintId = constraintId;
	}
	public String getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}
	public String getRelProductCode() {
		return relProductCode;
	}
	public void setRelProductCode(String relProductCode) {
		this.relProductCode = relProductCode;
	}
	public String getRelProductName() {
		return relProductName;
	}
	public void setRelProductName(String relProductName) {
		this.relProductName = relProductName;
	}
	public String getSizeParam() {
		return sizeParam;
	}
	public void setSizeParam(String sizeParam) {
		this.sizeParam = sizeParam;
	}
	public String getVasDefault() {
		return vasDefault;
	}
	public void setVasDefault(String vasDefault) {
		this.vasDefault = vasDefault;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
}
