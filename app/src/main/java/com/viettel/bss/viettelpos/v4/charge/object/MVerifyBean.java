package com.viettel.bss.viettelpos.v4.charge.object;

public class MVerifyBean {
	private String rate;
	private String staffCode;
	private String totalAssign;
	private String totalFalse;
	private String totalInTime;
	private String totalNoVerify;
	private String totalNotAssign;
	private String totalNotVerify;
	private String totalOutTime;
	private String totalOverTime;
	private String totalTrue;
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getTotalAssign() {
		return totalAssign;
	}
	public void setTotalAssign(String totalAssign) {
		this.totalAssign = totalAssign;
	}
	public String getTotalFalse() {
		return totalFalse;
	}
	public void setTotalFalse(String totalFalse) {
		this.totalFalse = totalFalse;
	}
	public String getTotalInTime() {
		return totalInTime;
	}
	public void setTotalInTime(String totalInTime) {
		this.totalInTime = totalInTime;
	}
	public String getTotalNoVerify() {
		return totalNoVerify;
	}
	public void setTotalNoVerify(String totalNoVerify) {
		this.totalNoVerify = totalNoVerify;
	}
	public String getTotalNotAssign() {
		return totalNotAssign;
	}
	public void setTotalNotAssign(String totalNotAssign) {
		this.totalNotAssign = totalNotAssign;
	}
	public String getTotalNotVerify() {
		return totalNotVerify;
	}
	public void setTotalNotVerify(String totalNotVerify) {
		this.totalNotVerify = totalNotVerify;
	}
	public String getTotalOutTime() {
		return totalOutTime;
	}
	public void setTotalOutTime(String totalOutTime) {
		this.totalOutTime = totalOutTime;
	}
	public String getTotalOverTime() {
		return totalOverTime;
	}
	public void setTotalOverTime(String totalOverTime) {
		this.totalOverTime = totalOverTime;
	}
	public String getTotalTrue() {
		return totalTrue;
	}
	public void setTotalTrue(String totalTrue) {
		this.totalTrue = totalTrue;
	}
	
	@Override
	public String toString() {
		return String.format(
				"{\"MVerifyBean\":{\"rate\":\"%s\", \"staffCode\":\"%s\", \"totalAssign\":\"%s\", \"totalFalse\":\"%s\", \"totalInTime\":\"%s\", \"totalNoVerify\":\"%s\", \"totalNotAssign\":\"%s\", \"totalNotVerify\":\"%s\", \"totalOutTime\":\"%s\", \"totalOverTime\":\"%s\", \"totalTrue\":\"%s\"}}",
				rate, staffCode, totalAssign, totalFalse, totalInTime, totalNoVerify, totalNotAssign, totalNotVerify,
				totalOutTime, totalOverTime, totalTrue);
	}
}
