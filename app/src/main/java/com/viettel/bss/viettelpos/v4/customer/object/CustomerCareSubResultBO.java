package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class CustomerCareSubResultBO {
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "id", required = false)
	private Long id;
	@Element (name = "type", required = false)
	private Integer type;
	@Element (name = "assignSubId", required = false)
	private Long assignSubId;
	@Element (name = "propertyCode", required = false)
	private String propertyCode;
	@Element (name = "propertyName", required = false)
	private String propertyName;
	@Element (name = "reasonId", required = false)
	private Long reasonId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getAssignSubId() {
		return assignSubId;
	}
	public void setAssignSubId(Long assignSubId) {
		this.assignSubId = assignSubId;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Long getReasonId() {
		return reasonId;
	}
	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}
	
	
}
