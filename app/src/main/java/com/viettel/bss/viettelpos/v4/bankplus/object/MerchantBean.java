package com.viettel.bss.viettelpos.v4.bankplus.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class MerchantBean {
	@Element(name = "serviceCode", required = false)
	private String serviceCode;
	@Element(name = "serviceName", required = false)
	private String serviceName;
	@Element(name = "custFeeType", required = false)
	private String custFeeType;
	@Element(name = "custFee", required = false)
	private String custFee;
	@Element(name = "isPayAccount", required = false)
	private String isPayAccount;
	@Element(name = "amount", required = false)
	private String amount;
	@Element(name = "typePayment", required = false)
	private String typePayment;
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCustFeeType() {
		return custFeeType;
	}
	public void setCustFeeType(String custFeeType) {
		this.custFeeType = custFeeType;
	}
	public String getCustFee() {
		return custFee;
	}
	public void setCustFee(String custFee) {
		this.custFee = custFee;
	}
	public String getIsPayAccount() {
		return isPayAccount;
	}
	public void setIsPayAccount(String isPayAccount) {
		this.isPayAccount = isPayAccount;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	public String getTypePayment() {
		return typePayment;
	}
	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	public boolean isPayAccount() {
		if (this.isPayAccount == null) {
			return false;
		} else if (this.isPayAccount.toLowerCase().contains("y")) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return serviceCode + "-" + serviceName;
	}
	
}
