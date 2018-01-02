package com.viettel.bss.viettelpos.v4.sale.object;

public class StockType {
	private Long stockTypeId;
    private String name;
    private Long status;
    private String notes;
    private String tableName;
    private Long checkExp;
	public Long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(Long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Long getCheckExp() {
		return checkExp;
	}
	public void setCheckExp(Long checkExp) {
		this.checkExp = checkExp;
	}
}
