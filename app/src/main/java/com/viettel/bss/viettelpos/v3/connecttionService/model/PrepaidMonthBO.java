package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "PrepaidMonthBO", strict = false)
public class PrepaidMonthBO implements Serializable {
	@Element(name = "prepaidId", required = false)
	private Long prepaidId;
	@Element(name = "prepaidValue", required = false)
	private String prepaidValue;
	@Element(name = "view", required = false)
	private String view;
	public Long getPrepaidId() {
		return prepaidId;
	}
	public void setPrepaidId(Long prepaidId) {
		this.prepaidId = prepaidId;
	}
	public String getPrepaidValue() {
		return prepaidValue;
	}
	public void setPrepaidValue(String prepaidValue) {
		this.prepaidValue = prepaidValue;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}

	
	
	
}
