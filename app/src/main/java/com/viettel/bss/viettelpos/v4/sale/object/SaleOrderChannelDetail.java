package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;



@Root(name = "SaleOrderChannelDetail", strict = false)
public class SaleOrderChannelDetail {
	@Element (name = "type", required = false)
	private Long type;
	@Element (name = "saleOrderDetailId", required = false)
	private Long saleOrderDetailId;
	@Element (name = "saleOrderId", required = false)
	private Long saleOrderId;
	@Element (name = "stockModelId", required = false)
	private Long stockModelId;
	@Element (name = "quantityOrder", required = false)
	private Long quantityOrder;
	@Element (name = "quantityReal", required = false)
	private Long quantityReal;
	@Element (name = "stockModelCode", required = false)
	private String stockModelCode;
	@Element (name = "stockModelName", required = false)
	private String stockModelName;
	@Element (name = "stockTypeName", required = false)
	private String stockTypeName;
	public Long getSaleOrderDetailId() {
		return saleOrderDetailId;
	}
	public void setSaleOrderDetailId(Long saleOrderDetailId) {
		this.saleOrderDetailId = saleOrderDetailId;
	}
	public Long getSaleOrderId() {
		return saleOrderId;
	}
	public void setSaleOrderId(Long saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
	public Long getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}
	public Long getQuantityOrder() {
		return quantityOrder;
	}
	public void setQuantityOrder(Long quantityOrder) {
		this.quantityOrder = quantityOrder;
	}
	public Long getQuantityReal() {
		return quantityReal;
	}
	public void setQuantityReal(Long quantityReal) {
		this.quantityReal = quantityReal;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getStockModelCode() {
		return stockModelCode;
	}
	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
    
}
