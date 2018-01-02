package com.viettel.bss.viettelpos.v4.sale.object;

public class TelecomServiceObject {
	private Long telecomServiceId;
	private String name;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getTelecomServiceId() {
		return telecomServiceId;
	}
	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
