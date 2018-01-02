package com.viettel.bss.viettelpos.v4.customview.obj;

public class LoaiGiayToObj {
	private String parType;
	private String parValue;

	public LoaiGiayToObj(String parType, String parValue) {
		super();
		this.parType = parType;
		this.parValue = parValue;
	}

	public String getParType() {
		return parType;
	}

	public void setParType(String parType) {
		this.parType = parType;
	}

	public String getParValue() {
		return parValue;
	}

	public void setParValue(String parValue) {
		this.parValue = parValue;
	}

}
