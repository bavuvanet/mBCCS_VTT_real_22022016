package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class SaleTransDetailOJ implements Serializable {
	private String stockName;
	private String quantity;
	private String amount;
	
	public SaleTransDetailOJ(String stockName, String quantity, String amount) {
		super();
		this.stockName = stockName;
		this.quantity = quantity;
		this.amount = amount;
	}
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	

}
