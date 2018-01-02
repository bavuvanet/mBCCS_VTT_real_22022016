package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

public class StockModelConnectSub implements Serializable{
	private String stockTypeId;
	private String stockModelId;
	private String saleServicesDetailId;
	private Long price;
	private String supplyMethod;
	private String programMonth;
	private String httt;
	private String stockModelCode;
	private String stockModelName;

	private String numberMonth;

	public String getNumberMonth() {
		return numberMonth;
	}

	public void setNumberMonth(String numberMonth) {
		this.numberMonth = numberMonth;
	}

	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public String getStockModelCode() {
		return stockModelCode;
	}
	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getSaleServicesDetailId() {
		return saleServicesDetailId;
	}
	public void setSaleServicesDetailId(String saleServicesDetailId) {
		this.saleServicesDetailId = saleServicesDetailId;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getSupplyMethod() {
		return supplyMethod;
	}
	public void setSupplyMethod(String supplyMethod) {
		this.supplyMethod = supplyMethod;
	}
	public String getProgramMonth() {
		return programMonth;
	}
	public void setProgramMonth(String programMonth) {
		this.programMonth = programMonth;
	}
	public String getHttt() {
		return httt;
	}
	public void setHttt(String httt) {
		this.httt = httt;
	}
	
}
