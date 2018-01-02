package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class ReportProgressBean implements Serializable {

	private String chargeRate;
	private String chargeSta;
	private String custPayment;
	private String custDebit;
	private String custRate;
	private String custSta;
	private String debit;
	private String payment;

	private String contractFormMngt;
	private String contractFormMngtName;
	
	private String contractName;
	
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getChargeSta() {
		return chargeSta;
	}

	public void setChargeSta(String chargeSta) {
		this.chargeSta = chargeSta;
	}

	public String getCustPayment() {
		return custPayment;
	}

	public void setCustPayment(String custPayment) {
		this.custPayment = custPayment;
	}

	public String getCustDebit() {
		return custDebit;
	}

	public void setCustDebit(String custDebit) {
		this.custDebit = custDebit;
	}

	public String getCustRate() {
		return custRate;
	}

	public void setCustRate(String custRate) {
		this.custRate = custRate;
	}

	public String getCustSta() {
		return custSta;
	}

	public void setCustSta(String custSta) {
		this.custSta = custSta;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getContractFormMngt() {
		return contractFormMngt;
	}

	public void setContractFormMngt(String contractFormMngt) {
		this.contractFormMngt = contractFormMngt;
	}

	public String getContractFormMngtName() {
		return contractFormMngtName;
	}

	public void setContractFormMngtName(String contractFormMngtName) {
		this.contractFormMngtName = contractFormMngtName;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"ReportProgressBean\":{\"chargeRate\":\"%s\", \"chargeSta\":\"%s\", \"custPayment\":\"%s\", \"custDebit\":\"%s\", \"custRate\":\"%s\", \"custSta\":\"%s\", \"debit\":\"%s\", \"payment\":\"%s\", \"contractFormMngt\":\"%s\", \"contractFormMngtName\":\"%s\"}}",
				chargeRate, chargeSta, custPayment, custDebit, custRate, custSta, debit, payment, contractFormMngt, contractFormMngtName);
	}

}
