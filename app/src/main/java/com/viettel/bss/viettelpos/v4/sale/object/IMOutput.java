package com.viettel.bss.viettelpos.v4.sale.object;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class IMOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "listChannelOrder", entry = "listChannelOrder", required = false, inline = true)
	private List<SaleOrderChannel> listChannelOrder;
	@ElementList(name = "lstStockModelByStaff", entry = "lstStockModelByStaff", required = false, inline = true)
	private ArrayList<StockModelByStaff> lstStockModelByStaff;
	@Element(name = "stockSim", required = false)
	private StockSim stockSim;

	@Element(name = "expiredate", required = false)
	private String expiredate;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "serial", required = false)
	private String serial;
	@ElementList(name = "lstSaleOrderChannelDetail", entry = "lstSaleOrderChannelDetail", required = false, inline = true)
	private ArrayList<SaleOrderChannelDetail> lstSaleOrderChannelDetail;

	public ArrayList<SaleOrderChannelDetail> getLstSaleOrderChannelDetail() {
		return lstSaleOrderChannelDetail;
	}

	public void setLstSaleOrderChannelDetail(
			ArrayList<SaleOrderChannelDetail> lstSaleOrderChannelDetail) {
		this.lstSaleOrderChannelDetail = lstSaleOrderChannelDetail;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

	public StockSim getStockSim() {
		return stockSim;
	}

	public void setStockSim(StockSim stockSim) {
		this.stockSim = stockSim;
	}

	@Element(name = "orderId", required = false)
	private Long orderId;
	@Element(name = "orderCode", required = false)
	private String orderCode;
	@Element(name = "price", required = false)
	private PriceBO priceBO;

	public PriceBO getPriceBO() {
		return priceBO;
	}

	public void setPriceBO(PriceBO priceBO) {
		this.priceBO = priceBO;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SaleOrderChannel> getListChannelOrder() {
		return listChannelOrder;
	}

	public void setListChannelOrder(List<SaleOrderChannel> listChannelOrder) {
		this.listChannelOrder = listChannelOrder;
	}

	public ArrayList<StockModelByStaff> getLstStockModelByStaff() {
		return lstStockModelByStaff;
	}

	public void setLstStockModelByStaff(
			ArrayList<StockModelByStaff> lstStockModelByStaff) {
		this.lstStockModelByStaff = lstStockModelByStaff;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	

}
