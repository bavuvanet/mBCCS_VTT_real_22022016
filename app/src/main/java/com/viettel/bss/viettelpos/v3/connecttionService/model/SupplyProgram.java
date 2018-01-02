package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "SupplyProgram", strict = false)
public class SupplyProgram implements Serializable{
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "priceId", required = false)
	private Long priceId;
	@Element(name = "programMonth", required = false)
	private Long programMonth;
	@ElementList(name = "supplyMonthLst", entry = "supplyMonthLst", required = false, inline = true)
	private List<Price> supplyMonthLst;

	public SupplyProgram() {

	}

	public List<Price> getSupplyMonthLst() {
		return supplyMonthLst;
	}

	public void setSupplyMonthLst(List<Price> supplyMonthLst) {
		this.supplyMonthLst = supplyMonthLst;
	}

	public SupplyProgram(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public Long getProgramMonth() {
		return programMonth;
	}

	public void setProgramMonth(Long programMonth) {
		this.programMonth = programMonth;
	}

	@Override
	public String toString() {
		if(CommonActivity.isNullOrEmpty(code)){
			return name;
		}
		return code + " - " + name;
	}
}
