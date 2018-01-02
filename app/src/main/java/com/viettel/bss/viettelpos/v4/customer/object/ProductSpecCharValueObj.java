package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class ProductSpecCharValueObj {
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "isDefault", required = false)
	private short isDefault;
	@Element (name = "valueFrom", required = false)
	private String valueFrom;
	@Element (name = "valueTo", required = false)
	private String valueTo;
	@Element (name = "valueType", required = false)
	private String valueType;
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
	public short getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(short isDefault) {
		this.isDefault = isDefault;
	}
	public String getValueFrom() {
		return valueFrom;
	}
	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}
	public String getValueTo() {
		return valueTo;
	}
	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	
}
