package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class SupplyMethodBean {
	private String name;
	private String value;
	
	public SupplyMethodBean() {
		super();
	}
	
	public SupplyMethodBean(String value, String name) {
		super();
		this.name = name;
		this.value = value;
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
		return name;
	}
	
}
