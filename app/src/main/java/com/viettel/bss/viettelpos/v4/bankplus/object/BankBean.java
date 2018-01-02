package com.viettel.bss.viettelpos.v4.bankplus.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "return", strict = false)
public class BankBean {
	@Element(name = "bankCode", required = false)
	private String bankCode;
	@Element(name = "accountNo", required = false)
	private String accountNo;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "bankName", required = false)
	private String bankName;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	
	
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(!CommonActivity.isNullOrEmpty(bankName)){
			return bankCode + "-" + bankName;
		}
		return bankCode;
	}
}
