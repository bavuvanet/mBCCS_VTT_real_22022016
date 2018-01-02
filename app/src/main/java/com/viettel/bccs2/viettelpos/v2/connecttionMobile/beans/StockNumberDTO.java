package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StockNumberDTO", strict = false)
public class StockNumberDTO {

	@Element (name = "price", required = false)
	private String price ="";
	@Element (name = "pricePledgeAmount", required = false)
	private String pricePledgeAmount = "";
	@Element (name = "pledgeTime", required = false)
	private String pledgeTime="";
	@Element (name = "priceVat", required = false)
	private String priceVat = "";

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	@Element (name = "isdn", required = false)
	private String isdn = "";

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPricePledgeAmount() {
		return pricePledgeAmount;
	}
	public void setPricePledgeAmount(String pricePledgeAmount) {
		this.pricePledgeAmount = pricePledgeAmount;
	}
	public String getPledgeTime() {
		return pledgeTime;
	}
	public void setPledgeTime(String pledgeTime) {
		this.pledgeTime = pledgeTime;
	}
	public String getPriceVat() {
		return priceVat;
	}
	public void setPriceVat(String priceVat) {
		this.priceVat = priceVat;
	}

	@Override
	public String toString() {
		return isdn;
	}
}
