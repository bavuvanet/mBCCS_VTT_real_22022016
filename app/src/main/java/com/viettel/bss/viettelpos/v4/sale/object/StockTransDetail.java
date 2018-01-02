package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;

@Root(name = "return",strict=false)
public class StockTransDetail implements Serializable{
	@Element (name = "actionCode", required = false)
	private String expCode;
	@Element (name = "expDate", required = false)
	private Date expDate;
	@Element (name = "stateId", required = false)
	private Long stateId;
	@Element (name = "stockTransId", required = false)
	private Long stockTransId;
	@Element (name = "prodOfferCode", required = false)
	private String stockModelCode;
	@Element (name = "prodOfferId", required = false)
	private Long stockModelId;
	@Element (name = "prodOfferName", required = false)
	private String stockModelName;
	@Element (name = "unit", required = false)
	private String unitName;
	@Element (name = "quantity", required = false)
	private Long quantity;
	@Element (name = "checkSerial", required = false)
	private Long checkSerial;
	@Element (name = "prodOfferTypeId", required = false)
	private Long prodOfferTypeId;
	
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}


	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getStockTransId() {
		return stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public String getStockModelCode() {
		return stockModelCode;
	}

	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}

	public Long getStockModelId() {
		return stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getStockModelName() {
		return stockModelName;
	}

	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}

	public Long getCheckSerial() {
		return checkSerial;
	}

	public void setCheckSerial(Long checkSerial) {
		this.checkSerial = checkSerial;
	}

	public Long getProdOfferTypeId() {
		return prodOfferTypeId;
	}

	public void setProdOfferTypeId(Long prodOfferTypeId) {
		this.prodOfferTypeId = prodOfferTypeId;
	}
	
	

}
