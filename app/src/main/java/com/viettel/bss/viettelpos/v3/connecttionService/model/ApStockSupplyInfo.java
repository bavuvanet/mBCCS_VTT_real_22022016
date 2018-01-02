package com.viettel.bss.viettelpos.v3.connecttionService.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;
@Root(name = "ApStockSupplyInfo", strict = false)
public class ApStockSupplyInfo implements Serializable{
	@ElementList(name = "lstMapSupplyMethodSupplyPrograms", entry = "lstMapSupplyMethodSupplyPrograms", required = false, inline = true)
    private List<MapSupplyMethodSupplyProgram> lstMapSupplyMethodSupplyPrograms;
	@Element (name = "price", required = false)
	private Long price;
	@Element (name = "priceId", required = false)
	private Long priceId;
	@Element (name = "stockModelId", required = false)
	private Long stockModelId;
	@Element (name = "saleServicesDetailId", required = false)
	private Long saleServicesDetailId;
	public List<MapSupplyMethodSupplyProgram> getLstMapSupplyMethodSupplyPrograms() {
		return lstMapSupplyMethodSupplyPrograms;
	}
	public void setLstMapSupplyMethodSupplyPrograms(
			List<MapSupplyMethodSupplyProgram> lstMapSupplyMethodSupplyPrograms) {
		this.lstMapSupplyMethodSupplyPrograms = lstMapSupplyMethodSupplyPrograms;
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
	public Long getSaleServicesDetailId() {
		return saleServicesDetailId;
	}
	public void setSaleServicesDetailId(Long saleServicesDetailId) {
		this.saleServicesDetailId = saleServicesDetailId;
	}
	public Long getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}
    
}
