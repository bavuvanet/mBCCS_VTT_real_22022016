package com.viettel.bss.viettelpos.v4.sale.object;

public class ViewInfoOject {
	private String stockModelId;
	private String name;
	private String quantityIssue;
	private String _sumremane;
	private Long _price = 0L;
	public Long get_price() {
		return _price;
	}
	public void set_price(Long _price) {
		this._price = _price;
	}
	public String get_sumremane() {
		return _sumremane;
	}
	public void set_sumremane(String _sumremane) {
		this._sumremane = _sumremane;
	}
	public String get_stock_model_id() {
		return stockModelId;
	}
	public void set_stock_model_id(String _stock_model_id) {
		this.stockModelId = _stock_model_id;
	}
	public String get_name() {
		return name;
	}
	public void set_name(String _name) {
		this.name = _name;
	}
	public String get_quantity_issue() {
		return quantityIssue;
	}
	public void set_quantity_issue(String _quantity_issue) {
		this.quantityIssue = _quantity_issue;
	}
}
