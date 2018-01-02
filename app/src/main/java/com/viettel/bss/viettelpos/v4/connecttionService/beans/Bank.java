package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bss.viettelpos.v4.object.NonProguard;

public class Bank extends NonProguard {
	
	private String address;
	private String bankCode;
	private String bankType;
	private String name;
	private String status;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
