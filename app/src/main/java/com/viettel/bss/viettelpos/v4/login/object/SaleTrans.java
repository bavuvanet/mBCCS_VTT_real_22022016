package com.viettel.bss.viettelpos.v4.login.object;

import java.io.Serializable;

class SaleTrans implements Serializable {
	
	private String idStaff;
	private long totalOfMonth;
	private String lastBuy;
	
	public SaleTrans(long totalOfMonth, String lastBuy) {
		super();
		this.totalOfMonth = totalOfMonth;
		this.lastBuy = lastBuy;
		
	}

	

	public String getIdStaff() {
		return idStaff;
	}



	public void setIdStaff(String idStaff) {
		this.idStaff = idStaff;
	}



	public long getTotalOfMonth() {
		return totalOfMonth;
	}



	public void setTotalOfMonth(long totalOfMonth) {
		this.totalOfMonth = totalOfMonth;
	}



	public String getLastBuy() {
		return lastBuy;
	}



	public void setLastBuy(String lastBuy) {
		this.lastBuy = lastBuy;
	}



}
