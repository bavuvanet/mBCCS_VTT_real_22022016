package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
@Root(name = "AccountBankDTO", strict = false)
public class AccountBankDTO implements Serializable{
	@Element (name = "account", required = false)
	private String account;
	@Element (name = "accountBankId", required = false)
	private Long accountBankId;
	@Element (name = "accountId", required = false)
	private Long accountId;
	@Element (name = "accountName", required = false)
	private String accountName;
	@Element (name = "bankAccountNo", required = false)
	private String bankAccountNo;
	@Element (name = "bankCode", required = false)
	private String bankCode;
	@Element (name = "bankName", required = false)
	private String bankName;
	@Element (name = "createUser", required = false)
	private String createUser;
	@Element (name = "updateUser", required = false)
	private String updateUser;
	@Element (name = "address", required = false)
	private String address;
	@Element (name = "description", required = false)
	private String description;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "bankAccountDate", required = false)
	private String bankAccountDate;
	
	
	
	public String getBankAccountDate() {
		return bankAccountDate;
	}
	public void setBankAccountDate(String bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getAccountBankId() {
		return accountBankId;
	}
	public void setAccountBankId(Long accountBankId) {
		this.accountBankId = accountBankId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toXml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<accountBankDTO>");
		
		if(accountId != null){
			sb.append("<accountId>").append(accountId);
			sb.append("</accountId>");
		}
		if(!CommonActivity.isNullOrEmpty(account)){
			sb.append("<account>").append(account);
			sb.append("</account>");
		}
		sb.append("<accountName>").append(accountName);
		sb.append("</accountName>");
		if(!CommonActivity.isNullOrEmpty(bankCode)){
			sb.append("<bankCode>").append(bankCode);
			sb.append("</bankCode>");
		}
		if(!CommonActivity.isNullOrEmpty(bankName)){
			sb.append("<bankName>").append(bankName);
			sb.append("</bankName>");
		}
		if(!CommonActivity.isNullOrEmpty(bankAccountNo)){
			sb.append("<bankAccountNo>").append(bankAccountNo);
			sb.append("</bankAccountNo>");
		}
		if(accountId != null){
			sb.append("<bankAccountDate>").append(bankAccountDate);
			sb.append("</bankAccountDate>");
		}else{
			if(!CommonActivity.isNullOrEmpty(bankAccountDate)){
				sb.append("<bankAccountDate>").append(bankAccountDate).append("T00:00:00+07:00");
				sb.append("</bankAccountDate>");
			}
		}
		
		sb.append("</accountBankDTO>");
		return sb.toString();
	}

	public String toXmlAccountBank(){
		StringBuilder sb = new StringBuilder();
		sb.append("<accountBank>");

		if(accountId != null){
			sb.append("<accountId>").append(accountId);
			sb.append("</accountId>");
		}
		if(!CommonActivity.isNullOrEmpty(account)){
			sb.append("<account>").append(account);
			sb.append("</account>");
		}
		sb.append("<accountName>").append(accountName);
		sb.append("</accountName>");
		if(!CommonActivity.isNullOrEmpty(bankCode)){
			sb.append("<bankCode>").append(bankCode);
			sb.append("</bankCode>");
		}
		if(!CommonActivity.isNullOrEmpty(bankName)){
			sb.append("<bankName>").append(bankName);
			sb.append("</bankName>");
		}
		if(!CommonActivity.isNullOrEmpty(bankAccountNo)){
			sb.append("<bankAccountNo>").append(bankAccountNo);
			sb.append("</bankAccountNo>");
		}
		if(accountId != null){
			sb.append("<bankAccountDate>").append(bankAccountDate);
			sb.append("</bankAccountDate>");
		}else{
			if(!CommonActivity.isNullOrEmpty(bankAccountDate)){
				sb.append("<bankAccountDate>").append(bankAccountDate).append("T00:00:00+07:00");
				sb.append("</bankAccountDate>");
			}
		}

		sb.append("</accountBank>");
		return sb.toString();
	}

	public String toXmlAcc(){
		StringBuilder sb = new StringBuilder();
		sb.append("<accountBank>");

		if(accountId != null){
			sb.append("<accountId>").append(accountId);
			sb.append("</accountId>");
		}
		if(!CommonActivity.isNullOrEmpty(account)){
			sb.append("<account>").append(account);
			sb.append("</account>");
		}
		sb.append("<accountName>").append(accountName);
		sb.append("</accountName>");
		if(!CommonActivity.isNullOrEmpty(bankCode)){
			sb.append("<bankCode>").append(bankCode);
			sb.append("</bankCode>");
		}
		if(!CommonActivity.isNullOrEmpty(bankName)){
			sb.append("<bankName>").append(bankName);
			sb.append("</bankName>");
		}
		if(!CommonActivity.isNullOrEmpty(bankAccountNo)){
			sb.append("<bankAccountNo>").append(bankAccountNo);
			sb.append("</bankAccountNo>");
		}
		if(accountId != null){
			sb.append("<bankAccountDate>").append(bankAccountDate);
			sb.append("</bankAccountDate>");
		}else{
			if(!CommonActivity.isNullOrEmpty(bankAccountDate)){
				sb.append("<bankAccountDate>").append(bankAccountDate).append("T00:00:00+07:00");
				sb.append("</bankAccountDate>");
			}
		}

		sb.append("</accountBank>");
		return sb.toString();
	}
}
