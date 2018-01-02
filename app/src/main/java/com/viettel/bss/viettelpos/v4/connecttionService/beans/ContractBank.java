package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class ContractBank {
	 private String account;
	 private String accountName;
	 private String bankCode;
	 private String bankContractNo;
	 private String strBankContractDate;
	 private String name;
//	    "contractId",
//	    "description",
//	    "id",
//	    "status",
	
	public String getAccount() {
		return account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankContractNo() {
		return bankContractNo;
	}
	public void setBankContractNo(String bankContractNo) {
		this.bankContractNo = bankContractNo;
	}
	public String getStrBankContractDate() {
		return strBankContractDate;
	}
	public void setStrBankContractDate(String strBankContractDate) {
		this.strBankContractDate = strBankContractDate;
	}
	
	@Override
	public String toString() {
		return String.format(
				"{\"ContractBank\":{\"account\":\"%s\", \"accountName\":\"%s\", \"bankCode\":\"%s\", \"bankContractNo\":\"%s\", \"strBankContractDate\":\"%s\"}}",
				account, accountName, bankCode, bankContractNo, strBankContractDate);
	}
}
