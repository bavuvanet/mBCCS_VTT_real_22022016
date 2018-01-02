package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "stockCardFullDTO", strict = false)
public class StockCardFullDTO implements Serializable{
	@Element(name = "districtCode", required = false)
	private String districtCode;
	@Element(name = "districtName", required = false)
	private String districtName;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "ownerCode", required = false)
	private String ownerCode;
	@Element(name = "ownerId", required = false)
	private String ownerId;
	@Element(name = "ownerName", required = false)
	private String ownerName;
	@Element(name = "ownerType", required = false)
	private String ownerType;
	@Element(name = "provinceCode", required = false)
	private String provinceCode;
	@Element(name = "provinceName", required = false)
	private String provinceName;
	@Element(name = "result", required = false)
	private boolean result;
	@Element(name = "serial", required = false)
	private String serial;
	@Element(name = "shopId", required = false)
	private String shopId;
	@Element(name = "stateId", required = false)
	private String stateId;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "stockModelCode", required = false)
	private String stockModelCode;
	@Element(name = "stockModelName", required = false)
	private String stockModelName;
	@Element(name = "tel", required = false)
	private String tel;

	
	
	
	public StockCardFullDTO() {
		super();
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
