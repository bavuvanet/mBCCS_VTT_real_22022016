package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ObjectProperty", strict = false)
public class ObjectProperty {
	@ElementList(name = "lstDataProperty", entry = "lstDataProperty", required = false, inline = true)
	private List<DataProperty> lstDataProperty;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "dataType", required = false)
	private Integer dataType;
	@Element (name = "objOrder", required = false)
	private Integer objOrder;
	@Element (name = "optionType", required = false)
	private Integer optionType;
	@Element (name = "propertyCode", required = false)
	private String propertyCode;
	@Element (name = "strQuery", required = false)
	private String strQuery;
	@Element (name = "isEnable", required = false)
	private boolean isEnable = true;
	
	public List<DataProperty> getLstDataProperty() {
		return lstDataProperty;
	}
	public void setLstDataProperty(List<DataProperty> lstDataProperty) {
		this.lstDataProperty = lstDataProperty;
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
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Integer getObjOrder() {
		return objOrder;
	}
	public void setObjOrder(Integer objOrder) {
		this.objOrder = objOrder;
	}
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public String getStrQuery() {
		return strQuery;
	}
	public void setStrQuery(String strQuery) {
		this.strQuery = strQuery;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
