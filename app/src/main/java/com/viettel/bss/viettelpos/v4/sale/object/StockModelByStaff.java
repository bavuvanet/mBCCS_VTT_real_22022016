package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StockModelByStaff", strict = false)
public class StockModelByStaff implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 15374853L;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "stockModelId", required = false)
	private Long stockModelId;
	@Element(name = "stockModelCode", required = false)
	private String stockModelCode;
	@Element(name = "stockTypeId", required = false)
	private Long stockTypeId;
	@Element(name = "quantity", required = false)
	private Long quantity;
	@Element(name = "quantityIssue", required = false)
	private Long quantityIssue;
	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "priceId", required = false)
	private Long priceId;
	@Element(name = "tableName", required = false)
	private String tableName;
	@Element(name = "checkSerial", required = false)
	private Long checkSerial;

	public Long getStockModelId() {
		return stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getStockModelCode() {
		return stockModelCode;
	}

	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(Long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getQuantityIssue() {
		return quantityIssue;
	}

	public void setQuantityIssue(Long quantityIssue) {
		this.quantityIssue = quantityIssue;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getCheckSerial() {
		return checkSerial;
	}

	public void setCheckSerial(Long checkSerial) {
		this.checkSerial = checkSerial;
	}

}
