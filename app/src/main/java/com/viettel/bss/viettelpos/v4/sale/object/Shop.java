package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "shop", strict = false)
public class Shop implements Serializable {
	@Element(name = "shop_id", required = false)
	private Long shopId;
	@Element(name = "pricePolicy", required = false)
	private String pricePolicy;
	@Element(name = "province", required = false)
	private String province;
	@Element(name = "district", required = false)
	private String district;
	@Element(name = "precinct", required = false)
	private String precinct;
	@Element(name = "shopPath", required = false)
	private String shopPath;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "shop_code", required = false)
	private String shopCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopPath() {
		return shopPath;
	}

	public void setShopPath(String shopPath) {
		this.shopPath = shopPath;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getPricePolicy() {
		return pricePolicy;
	}

	public void setPricePolicy(String pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	@Override
	public String toString() {
		return shopCode+" - " + name;
	}
}
