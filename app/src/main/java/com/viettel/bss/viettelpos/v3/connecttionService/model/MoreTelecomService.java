package com.viettel.bss.viettelpos.v3.connecttionService.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class MoreTelecomService {
	@Element (name = "account", required = false)
	private String account;
	@Element (name = "numOfSub", required = false)
	private int numOfSub;
	@Element (name = "productCode", required = false)
	private String productCode;
	@Element (name = "province", required = false)
	private String province;
	@Element (name = "telecomServiceId", required = false)
	private Long telecomServiceId;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getNumOfSub() {
		return numOfSub;
	}

	public void setNumOfSub(int numOfSub) {
		this.numOfSub = numOfSub;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

}
