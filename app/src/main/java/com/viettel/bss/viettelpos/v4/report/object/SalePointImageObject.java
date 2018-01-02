package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SalePointImageObject implements Serializable {
	private String saleName;
	private String quality;
	private String status;
	
	
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
}
