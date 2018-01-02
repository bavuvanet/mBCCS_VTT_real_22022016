package com.viettel.bss.viettelpos.v4.charge.object;

public class ChargeEmployeeOJ {
	private String nameEmpoyee;
	private String employeeId;
	private String staffCode;
	private String isdn;
	private boolean isChecked;
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getNameEmpoyee() {
		return nameEmpoyee;
	}
	public void setNameEmpoyee(String nameEmpoyee) {
		this.nameEmpoyee = nameEmpoyee;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	
}
