package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class DetailAccountPaymentOJ  implements Serializable {
	private String name;
	private Long toalDeposit;
	private String stockCode;
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getToalDeposit() {
		return toalDeposit;
	}
	public void setToalDeposit(Long toalDeposit) {
		this.toalDeposit = toalDeposit;
	}
	
}
