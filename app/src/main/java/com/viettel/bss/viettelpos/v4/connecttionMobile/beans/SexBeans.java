package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

public class SexBeans {

	private String name;
	private String values;
	
	
	
	public SexBeans() {
		super();
	}


	public SexBeans(String name, String values) {
		super();
		this.name = name;
		this.values = values;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
