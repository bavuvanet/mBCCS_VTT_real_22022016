package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "LocationDTO", strict = false)
public class LocationDTO implements Serializable{
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "label", required = false)
	private String label;
	@Element (name = "code", required = false)
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
