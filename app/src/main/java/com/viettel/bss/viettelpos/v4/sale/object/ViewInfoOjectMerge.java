package com.viettel.bss.viettelpos.v4.sale.object;

public class ViewInfoOjectMerge {
	
	private String _name;
	private String _quantity_issui_NV;
	private String _quantity_issui_TH;

    private String _stock_model_id;
	private long _soluong = 0L;
	private long _priceMerge = 0L;
	private Long telecomServiceId;
	
	public Long getTelecomServiceId() {
		return telecomServiceId;
	}
	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}
	public long get_priceMerge() {
		return _priceMerge;
	}
	public void set_priceMerge(long _priceMerge) {
		this._priceMerge = _priceMerge;
	}
	public long get_soluong() {
		return _soluong;
	}
	public void set_soluong(long _soluong) {
		this._soluong = _soluong;
	}
	public String get_stock_model_id() {
		return _stock_model_id;
	}
	public void set_stock_model_id(String _stock_model_id) {
		this._stock_model_id = _stock_model_id;
	}
	public String get_sumremane() {
		Long money = Long.parseLong(_quantity_issui_NV) * _priceMerge;
		return money +"";
	}
	public void set_sumremane(String _sumremane) {
    }

	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_quantity_issui_NV() {
		return _quantity_issui_NV;
	}
	public void set_quantity_issui_NV(String _quantity_issui_NV) {
		this._quantity_issui_NV = _quantity_issui_NV;
	}
	public String get_quantity_issui_TH() {
		return _quantity_issui_TH;
	}
	public void set_quantity_issui_TH(String _quantity_issui_TH) {
		this._quantity_issui_TH = _quantity_issui_TH;
	}
}
