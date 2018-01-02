package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
@Root(name = "OptionSetValueDTO", strict = false)
public class OptionSetValueDTO implements Serializable{
	@Element (name = "createdBy", required = false)
	private String createdBy;
	@Element (name = "displayOrder", required = false)
	private Long displayOrder;
	@Element (name = "lastUpdatedBy", required = false)
	private String lastUpdatedBy;
	@Element (name = "optionSetId", required = false)
	private Long optionSetId;
	@Element (name = "optionSetValueId", required = false)
	private Long optionSetValueId;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "id", required = false)
	private Long id;
	@Element (name = "description", required = false)
	private String description;
	@Element (name = "status", required = false)
	private String status;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Long getOptionSetId() {
		return optionSetId;
	}
	public void setOptionSetId(Long optionSetId) {
		this.optionSetId = optionSetId;
	}
	public Long getOptionSetValueId() {
		return optionSetValueId;
	}
	public void setOptionSetValueId(Long optionSetValueId) {
		this.optionSetValueId = optionSetValueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Override
	public String toString() {
		if(!CommonActivity.isNullOrEmpty(name)){
			return  name;
		}else{
			return  description;
		}
		
	}
	
	
	
}
