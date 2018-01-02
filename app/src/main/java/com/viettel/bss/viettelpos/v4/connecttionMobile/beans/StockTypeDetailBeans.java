package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

public class StockTypeDetailBeans {
  
	private String stockTypeCode;
	private String serial;
	private String partnerName;
	private String stockModelName;
	private String stockModelCode;
	private String price;
	private String stockModelId;
	private String stockTypeId;
	
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
	public String getStockTypeCode() {
		return stockTypeCode;
	}
	public void setStockTypeCode(String stockTypeCode) {
		this.stockTypeCode = stockTypeCode;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return String.format(
				"{\"StockTypeDetailBeans\":{\"stockTypeCode\":\"%s\", \"serial\":\"%s\", \"partnerName\":\"%s\", \"stockModelName\":\"%s\", \"stockModelCode\":\"%s\", \"price\":\"%s\", \"stockModelId\":\"%s\", \"stockTypeId\":\"%s\"}}",
				stockTypeCode, serial, partnerName, stockModelName, stockModelCode, price, stockModelId, stockTypeId);
	}
	
}
