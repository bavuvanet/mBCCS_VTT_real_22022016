package com.viettel.bss.viettelpos.v4.infrastrucure.object;

public class AreaObject {
	private final String name;
	private final String code;

	public AreaObject(String name, String code) {
		this.name = name;
		this.code = code;

	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

}
