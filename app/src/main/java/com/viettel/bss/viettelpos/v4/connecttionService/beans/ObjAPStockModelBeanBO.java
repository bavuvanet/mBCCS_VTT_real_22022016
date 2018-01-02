package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class ObjAPStockModelBeanBO {

	private String stockTypeId;
	private String stockModelId;
	private String stockModelType;
	private String stockModelName;
	private String quantity;
	private String saleTransDetailId;
	private String saleServicesDetailId;
	private String saleTransId;

	public ObjAPStockModelBeanBO(String stockTypeId, String stockModelId,
			String stockModelType, String stockModelName, String quantity,
			String saleTransDetailId, String saleServicesDetailId,
			String saleTransId) {
		super();
		this.stockTypeId = stockTypeId;
		this.stockModelId = stockModelId;
		this.stockModelType = stockModelType;
		this.stockModelName = stockModelName;
		this.quantity = quantity;
		this.saleTransDetailId = saleTransDetailId;
		this.saleServicesDetailId = saleServicesDetailId;
		this.saleTransId = saleTransId;
	}

	public ObjAPStockModelBeanBO() {
		super();
	}

	public String getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getStockModelId() {
		return stockModelId;
	}

	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getStockModelType() {
		return stockModelType;
	}

	public void setStockModelType(String stockModelType) {
		this.stockModelType = stockModelType;
	}

	public String getStockModelName() {
		return stockModelName;
	}

	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSaleTransDetailId() {
		return saleTransDetailId;
	}

	public void setSaleTransDetailId(String saleTransDetailId) {
		this.saleTransDetailId = saleTransDetailId;
	}

	public String getSaleServicesDetailId() {
		return saleServicesDetailId;
	}

	public void setSaleServicesDetailId(String saleServicesDetailId) {
		this.saleServicesDetailId = saleServicesDetailId;
	}

	public String getSaleTransId() {
		return saleTransId;
	}

	public void setSaleTransId(String saleTransId) {
		this.saleTransId = saleTransId;
	}

}
