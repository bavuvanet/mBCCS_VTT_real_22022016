package com.viettel.bss.viettelpos.v3.connecttionService.model;

public enum SupplyMethod {

	CT, DC, BD;

	public String value() {
		return name();
	}

	public static SupplyMethod fromValue(String v) {
		return valueOf(v);
	}

}
