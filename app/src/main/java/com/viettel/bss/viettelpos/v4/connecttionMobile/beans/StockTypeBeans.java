package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

public class StockTypeBeans {
	private String stockId;
	private String stockName;
	
	private String saleServiceCode;
	private String productOfferTypeId;
	
	

	public String getProductOfferTypeId() {
		return productOfferTypeId;
	}

	public void setProductOfferTypeId(String productOfferTypeId) {
		this.productOfferTypeId = productOfferTypeId;
	}

	public String getSaleServiceCode() {
		return saleServiceCode;
	}

	public void setSaleServiceCode(String saleServiceCode) {
		this.saleServiceCode = saleServiceCode;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"StockTypeBeans\":{\"stockId\":\"%s\", \"stockName\":\"%s\", \"saleServiceCode\":\"%s\"}}", stockId,
				stockName, saleServiceCode);
	}
}
