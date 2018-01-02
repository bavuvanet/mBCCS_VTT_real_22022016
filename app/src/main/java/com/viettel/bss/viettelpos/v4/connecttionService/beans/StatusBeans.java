package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class StatusBeans {
	private String typeStatus;
	private String nameStatus;

	public StatusBeans() {
	}

	public StatusBeans(String typeStatus, String nameStatus) {
		super();
		this.typeStatus = typeStatus;
		this.nameStatus = nameStatus;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

	public String getNameStatus() {
		return nameStatus;
	}

	public void setNameStatus(String nameStatus) {
		this.nameStatus = nameStatus;
	}

}
