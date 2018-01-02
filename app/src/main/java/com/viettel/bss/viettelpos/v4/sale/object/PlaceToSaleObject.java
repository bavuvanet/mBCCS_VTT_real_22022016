package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlaceToSaleObject implements Serializable {  
	private String isdnAgent;
	private String staffCode;
	private String staffId;
	private String staffName;
	private String tel;
	
	
	
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getIsdnAgent() {
		return isdnAgent;
	}
	public void setIsdnAgent(String isdnAgent) {
		this.isdnAgent = isdnAgent;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
	
	
	
	
	
	
	
	

}
