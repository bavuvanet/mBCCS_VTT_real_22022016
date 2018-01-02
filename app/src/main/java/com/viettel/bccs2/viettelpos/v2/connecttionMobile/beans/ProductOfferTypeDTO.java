package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v3.connecttionService.model.StockModelConnectSub;

@Root(name = "ProductOfferTypeDTO", strict = false)
public class ProductOfferTypeDTO implements Serializable{

	@Element (name = "saleServiceCode", required = false)
	private String saleServiceCode;
	@Element (name = "productOfferTypeId", required = false)
	private String productOfferTypeId;
	@Element (name = "name", required = false)
	private String name;
	@ElementList(name = "productOfferings", entry = "productOfferings", required = false, inline = true)
	private ArrayList<ProductOfferingDTO> productOfferings;
	private StockModelConnectSub stockModel;
	
	private String serial;
	
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getSaleServiceCode() {
		return saleServiceCode;
	}
	public void setSaleServiceCode(String saleServiceCode) {
		this.saleServiceCode = saleServiceCode;
	}
	public String getProductOfferTypeId() {
		return productOfferTypeId;
	}
	public void setProductOfferTypeId(String productOfferTypeId) {
		this.productOfferTypeId = productOfferTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ProductOfferingDTO> getProductOfferings() {
		if(productOfferings == null){
			productOfferings = new ArrayList<>();
		}
		return productOfferings;
	}
	public void setProductOfferings(ArrayList<ProductOfferingDTO> productOfferings) {
		this.productOfferings = productOfferings;
	}
	public StockModelConnectSub getStockModel() {
		return stockModel;
	}
	public void setStockModel(StockModelConnectSub stockModel) {
		this.stockModel = stockModel;
	}
	
	
	
}
