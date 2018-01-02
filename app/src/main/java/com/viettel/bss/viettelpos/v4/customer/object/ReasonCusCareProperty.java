package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ReasonCusCareProperty", strict = false)
public class ReasonCusCareProperty {
	@ElementList(name = "lstDataProperty", entry = "lstDataProperty", required = false, inline = true)
	private List<DataProperty> lstDataProperty;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "dataType", required = false)
	private Integer dataType;
	@Element (name = "optionType", required = false)
	private Integer optionType;
	@Element (name = "code", required = false)
	private String code;
	@Element (name = "checkIsdnViettel", required = false)
	private Integer checkIsdnViettel;
	private String nameValue;
	
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
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNameValue() {
		return nameValue;
	}
	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}
	public Integer getCheckIsdnViettel() {
		return checkIsdnViettel;
	}
	public void setCheckIsdnViettel(Integer checkIsdnViettel) {
		this.checkIsdnViettel = checkIsdnViettel;
	}
	
	
}
