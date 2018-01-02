package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "subRelPro", strict = false)
public class SubRelPro {
	@Element(name = "actionLogPrId", required = false)
	private Long actionLogPrId;
	@Element(name = "attributeList", required = false)
	private String attributeList;
	@Element(name = "cmConnected", required = false)
	private Long cmConnected;
	@Element(name = "ghostContractError", required = false)
	private String ghostContractError;
	@Element(name = "id", required = false)
	private Long id;
	@Element(name = "isConnected", required = false)
	private Long isConnected;
	@Element(name = "mainProductCode", required = false)
	private String mainProductCode;
	@Element(name = "relProductCode", required = false)
	private String relProductCode;
	@Element(name = "relProductName", required = false)
	private String relProductName;
	@Element(name = "relProductValue", required = false)
	private String relProductValue;
	@Element(name = "serviceType", required = false)
	private String serviceType;
	@Element(name = "sizeParam", required = false)
	private Integer sizeParam;
	@Element(name = "status", required = false)
	private Long status;
	@Element(name = "subId", required = false)
	private Long subId;
	@Element(name = "vasDefault", required = false)
	private String vasDefault;
	
	private boolean checked = false;
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Long getActionLogPrId() {
		return actionLogPrId;
	}
	public void setActionLogPrId(Long actionLogPrId) {
		this.actionLogPrId = actionLogPrId;
	}
	public String getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}
	public Long getCmConnected() {
		return cmConnected;
	}
	public void setCmConnected(Long cmConnected) {
		this.cmConnected = cmConnected;
	}
	public String getGhostContractError() {
		return ghostContractError;
	}
	public void setGhostContractError(String ghostContractError) {
		this.ghostContractError = ghostContractError;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(Long isConnected) {
		this.isConnected = isConnected;
	}
	public String getMainProductCode() {
		return mainProductCode;
	}
	public void setMainProductCode(String mainProductCode) {
		this.mainProductCode = mainProductCode;
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
	public String getRelProductValue() {
		return relProductValue;
	}
	public void setRelProductValue(String relProductValue) {
		this.relProductValue = relProductValue;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getSizeParam() {
		return sizeParam;
	}
	public void setSizeParam(Integer sizeParam) {
		this.sizeParam = sizeParam;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getSubId() {
		return subId;
	}
	public void setSubId(Long subId) {
		this.subId = subId;
	}
	public String getVasDefault() {
	
		return vasDefault;
	}
	public void setVasDefault(String vasDefault) {
		this.vasDefault = vasDefault;
	}
	
	
	

}
