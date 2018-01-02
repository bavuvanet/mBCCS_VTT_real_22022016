package com.viettel.bss.viettelpos.v4.sale.object;

public class GetOrderObjectDetail {
	private String quantityOrder;
	private String stockModelId;
	private String stockOrderDetailId;
	private String stockOrderId;
	public String getQuantityOrder() {
		return quantityOrder;
	}
	public void setQuantityOrder(String quantityOrder) {
		this.quantityOrder = quantityOrder;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getStockOrderDetailId() {
		return stockOrderDetailId;
	}
	public void setStockOrderDetailId(String stockOrderDetailId) {
		this.stockOrderDetailId = stockOrderDetailId;
	}
	public String getStockOrderId() {
		return stockOrderId;
	}
	public void setStockOrderId(String stockOrderId) {
		this.stockOrderId = stockOrderId;
	}
}
