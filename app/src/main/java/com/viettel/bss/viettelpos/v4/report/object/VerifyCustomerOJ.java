package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;

public class VerifyCustomerOJ implements Serializable {
	private CustomerOJ customerOJ;
	private boolean verified = false;
	private String idLocalEmployee;
    private String dateVerified;
	public VerifyCustomerOJ(CustomerOJ customerOJ, String idLocalEmployee,
			String dateVerified) {
		setCustomerOJ(customerOJ);
		setIdLocalEmployee(idLocalEmployee);
		setDateVerified(dateVerified);
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public boolean getVerified() {
		return dateVerified != null && !dateVerified.isEmpty();
	}
	private void setIdLocalEmployee(String idLocalEmployee) {
		this.idLocalEmployee = idLocalEmployee;
	}
	public String getIdLocalEmployee() {
		return idLocalEmployee;
	}
	private void setDateVerified(String dateVerified) {
		this.dateVerified = dateVerified;
	}
	public String getDateVerified() {
		return dateVerified;
	}
	private void setCustomerOJ(CustomerOJ customerOJ) {
		this.customerOJ = customerOJ;
	}
	public CustomerOJ getCustomerOJ() {
		return customerOJ;
	}
}
