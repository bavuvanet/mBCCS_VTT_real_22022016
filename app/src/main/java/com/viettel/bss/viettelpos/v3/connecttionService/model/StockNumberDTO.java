package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StockNumberDTO", strict = false)
public class StockNumberDTO implements Serializable{
	@Element(name = "id", required = false)
	private Long id;
	@Element(name = "serviceType", required = false)
	private String serviceType;
	@Element(name = "imsi", required = false)
	private String imsi;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "ownerId", required = false)
	private Long ownerId;
	@Element(name = "ownerType", required = false)
	private Long ownerType;
	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "priceId", required = false)
	private Long priceId;
	@Element(name = "prodOfferId", required = false)
	private Long prodOfferId;
	@Element(name = "prodOfferName", required = false)
	private String prodOfferName;
	@Element(name = "stationId", required = false)
	private Long stationId;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "stockId", required = false)
	private Long stockId;
	@Element(name = "stockModelName", required = false)
	private String stockModelName;
	@Element(name = "telecomServiceId", required = false)
	private Long telecomServiceId;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
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

	public Long getProdOfferId() {
		return prodOfferId;
	}

	public void setProdOfferId(Long prodOfferId) {
		this.prodOfferId = prodOfferId;
	}

	public String getProdOfferName() {
		return prodOfferName;
	}

	public void setProdOfferName(String prodOfferName) {
		this.prodOfferName = prodOfferName;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getStockModelName() {
		return stockModelName;
	}

	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}

	public Long getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return isdn;
	}

}
