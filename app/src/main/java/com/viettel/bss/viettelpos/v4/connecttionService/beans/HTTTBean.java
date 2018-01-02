package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class HTTTBean {
	private String name;
	private String value;
	public HTTTBean(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public HTTTBean() {
		super();
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
	
}
