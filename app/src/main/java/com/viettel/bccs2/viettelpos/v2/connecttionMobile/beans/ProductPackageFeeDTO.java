package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ProductPackageFeeDTO", strict = false)
public class ProductPackageFeeDTO {
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "price", required = false)
	private String price;
	@Element (name = "description", required = false)
	private String description;



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
