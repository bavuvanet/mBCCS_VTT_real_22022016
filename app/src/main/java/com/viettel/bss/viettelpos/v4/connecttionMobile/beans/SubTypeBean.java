package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

public class SubTypeBean {
	private String name;
	private String value;

	
	
	public SubTypeBean(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	
	public SubTypeBean() {
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

	@Override
	public String toString() {
		return "SubTypeBean{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
