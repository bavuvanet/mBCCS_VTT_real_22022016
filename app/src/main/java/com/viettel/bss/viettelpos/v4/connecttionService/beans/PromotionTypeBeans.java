package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;

public class PromotionTypeBeans implements Serializable {
	private String codeName = "";
	private String cycle;
	private String monthAmount;
	private String monthCommitment;
	private String name = "";
	private String promProgramCode;
	private String promType;
	private String staDate;
	private String status;
	private String telService;
	private String type;
	private String description;
	private String chargeMonthly;
	private String chargeMonthlyName;
	private String expireDate;
	private String effectDate;
	private int selectedCount;

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	private String startDate;
	private String endDate;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodeName() {
		
		if(CommonActivity.isNullOrEmpty(codeName)){
			return promProgramCode + "-" + name;
		}
		return codeName;
	}

	public String getChargeMonthlyName() {
		return chargeMonthlyName;
	}

	public void setChargeMonthlyName(String chargeMonthlyName) {
		this.chargeMonthlyName = chargeMonthlyName;
	}

	public String getChargeMonthly() {
		return chargeMonthly;
	}

	public void setChargeMonthly(String chargeMonthly) {
		this.chargeMonthly = chargeMonthly;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(String monthAmount) {
		this.monthAmount = monthAmount;
	}
	public String getMonthCommitment() {
		return monthCommitment;
	}
	public void setMonthCommitment(String monthCommitment) {
		this.monthCommitment = monthCommitment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPromProgramCode() {
		return promProgramCode;
	}
	public void setPromProgramCode(String promProgramCode) {
		this.promProgramCode = promProgramCode;
	}
	public String getPromType() {
		return promType;
	}
	public void setPromType(String promType) {
		this.promType = promType;
	}
	public String getStaDate() {
		return staDate;
	}
	public void setStaDate(String staDate) {
		this.staDate = staDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTelService() {
		return telService;
	}
	public void setTelService(String telService) {
		this.telService = telService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		String result = "";
		if(!CommonActivity.isNullOrEmpty(codeName)){
			result = codeName;
		}else{
			result =  this.promProgramCode + " - " + this.name;
		}
		if(!CommonActivity.isNullOrEmpty(chargeMonthlyName)){
			result = result + "\n" + chargeMonthlyName;
		}
		return  result;
	}
	
	
	
	
}
