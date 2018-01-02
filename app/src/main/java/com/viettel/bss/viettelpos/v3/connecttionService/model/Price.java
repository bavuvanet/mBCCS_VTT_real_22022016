package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Price", strict = false)
public class Price implements Serializable {

	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "priceId", required = false)
	private Long priceId;
	@Element(name = "programMonth", required = false)
	private Long programMonth;

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

}
