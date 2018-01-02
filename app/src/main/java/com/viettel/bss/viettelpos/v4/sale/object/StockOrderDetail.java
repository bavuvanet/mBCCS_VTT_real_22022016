package com.viettel.bss.viettelpos.v4.sale.object;

public class StockOrderDetail {
	private long quantityOder = 0L;
	private long stockModelId = 0L;
	private String stockOderCode ;
	public long getQuantityOder() {
		return quantityOder;
	}
	public void setQuantityOder(long quantityOder) {
		this.quantityOder = quantityOder;
	}
	public long getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(long stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getStockOderCode() {
		return stockOderCode;
	}
	public void setStockOderCode(String stockOderCode) {
		this.stockOderCode = stockOderCode;
	}
}
