package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class DataMapping {
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "codeReflect", required = false)
	private String codeReflect;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "value", required = false)
	private String value;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeReflect() {
		return codeReflect;
	}
	public void setCodeReflect(String codeReflect) {
		this.codeReflect = codeReflect;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code + "-" + name;
	}
	
	
}
