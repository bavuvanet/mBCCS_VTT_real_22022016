package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class SubStockModelRelReqBeans implements Serializable {
	private String reclaimAmount;
	private String reclaimCommitmentCode;
	private String reclaimCommitmentName;
	private String serial;
	private String status;
	private String stockModelId;
	private String stockModelName;
	private String stockTypeId;
	private String stockTypeName;
	private String subId;
	private String subStockModelRelId;
	public String getReclaimAmount() {
		return reclaimAmount;
	}
	public void setReclaimAmount(String reclaimAmount) {
		this.reclaimAmount = reclaimAmount;
	}
	public String getReclaimCommitmentCode() {
		return reclaimCommitmentCode;
	}
	public void setReclaimCommitmentCode(String reclaimCommitmentCode) {
		this.reclaimCommitmentCode = reclaimCommitmentCode;
	}
	public String getReclaimCommitmentName() {
		return reclaimCommitmentName;
	}
	public void setReclaimCommitmentName(String reclaimCommitmentName) {
		this.reclaimCommitmentName = reclaimCommitmentName;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getSubStockModelRelId() {
		return subStockModelRelId;
	}
	public void setSubStockModelRelId(String subStockModelRelId) {
		this.subStockModelRelId = subStockModelRelId;
	}
	
}
