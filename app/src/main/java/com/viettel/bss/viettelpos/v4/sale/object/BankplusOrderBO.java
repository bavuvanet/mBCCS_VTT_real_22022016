package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

public class BankplusOrderBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderCode;
	private String staffCode;
	private String staffName;
	private boolean checked = false;
	private String bankPlusMobile;
	private String description;
	private String saleTransStatus;

	public String getSaleTransStatus() {
		return saleTransStatus;
	}

	public void setSaleTransStatus(String saleTransStatus) {
		this.saleTransStatus = saleTransStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public String getBankPlusMobile() {
		return bankPlusMobile;
	}

	public void setBankPlusMobile(String bankPlusMobile) {
		this.bankPlusMobile = bankPlusMobile;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
