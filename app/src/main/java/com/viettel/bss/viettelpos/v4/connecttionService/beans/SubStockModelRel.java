package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class SubStockModelRel implements Serializable{

	private String stockModelId;
	private String serial;
	private String newSerial;
	private String stockModelName;
	
	private String subStockModelRelId;
	private boolean isChecked;
	private String reasonId;
	
	
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getSubStockModelRelId() {
		return subStockModelRelId;
	}
	public void setSubStockModelRelId(String subStockModelRelId) {
		this.subStockModelRelId = subStockModelRelId;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getNewSerial() {
		return newSerial;
	}
	public void setNewSerial(String newSerial) {
		this.newSerial = newSerial;
	}
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	
	
	
	
	
}
