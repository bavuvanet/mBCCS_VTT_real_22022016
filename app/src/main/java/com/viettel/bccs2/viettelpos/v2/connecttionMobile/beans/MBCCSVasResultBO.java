package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "MBCCSVasResultBO", strict = false)
public class MBCCSVasResultBO implements Serializable{
	@Element (name = "vasCode", required = false)
    private String vasCode;
	@Element (name = "description", required = false)
    private String description;
	public String getVasCode() {
		return vasCode;
	}
	public void setVasCode(String vasCode) {
		this.vasCode = vasCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
