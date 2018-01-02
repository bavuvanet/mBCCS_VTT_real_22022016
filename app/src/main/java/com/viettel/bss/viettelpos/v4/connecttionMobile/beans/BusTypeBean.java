package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;

public class BusTypeBean implements Serializable {
	private String busType;
	private String cusType;
	private String name;
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
