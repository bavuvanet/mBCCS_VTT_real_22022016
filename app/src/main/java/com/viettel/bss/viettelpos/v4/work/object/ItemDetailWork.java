package com.viettel.bss.viettelpos.v4.work.object;

public class ItemDetailWork {
	private String staffId;
	private String staffCode;
	private String name;
	private int total;
	private int backLog;
	private int term;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getBackLog() {
		return backLog;
	}
	public void setBackLog(int backLog) {
		this.backLog = backLog;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
