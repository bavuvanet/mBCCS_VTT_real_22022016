package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class ProductSpecCharObj {
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	@Element (name = "charType", required = false)
	private String charType;
	@Element (name = "code", required = false)
	private String code;
	@Element (name = "valueType", required = false)
	private String valueType;
	@Element (name = "valueName", required = false)
	private String valueName;
	
	@ElementList(name = "productSpecCharValueDTOList", entry = "productSpecCharValueDTOList", required = false, inline = true)
	private ArrayList<ProductSpecCharValueObj> lstProductSpecCharValueObjs;
	
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
	public String getCharType() {
		return charType;
	}
	public void setCharType(String charType) {
		this.charType = charType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public ArrayList<ProductSpecCharValueObj> getLstProductSpecCharValueObjs() {
		return lstProductSpecCharValueObjs;
	}
	
	public void setLstProductSpecCharValueObjs(
			ArrayList<ProductSpecCharValueObj> lstProductSpecCharValueObjs) {
		this.lstProductSpecCharValueObjs = lstProductSpecCharValueObjs;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
}
