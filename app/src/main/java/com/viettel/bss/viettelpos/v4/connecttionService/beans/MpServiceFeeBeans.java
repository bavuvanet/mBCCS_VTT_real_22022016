package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class MpServiceFeeBeans implements Serializable {
	private String amount;
	private String feeCode;
	private String feeName;
	private String priceId;
	private String saleServiceId;
	private String stockModelId;
	private String vat;
	private String realStep;
	private String revenueObj;
	
	public String getRealStep() {
		return realStep;
	}
	public void setRealStep(String realStep) {
		this.realStep = realStep;
	}
	public String getRevenueObj() {
		return revenueObj;
	}
	public void setRevenueObj(String revenueObj) {
		this.revenueObj = revenueObj;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public String getSaleServiceId() {
		return saleServiceId;
	}
	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	
}
