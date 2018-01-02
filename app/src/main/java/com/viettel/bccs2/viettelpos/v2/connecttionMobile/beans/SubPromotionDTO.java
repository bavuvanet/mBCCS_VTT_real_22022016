package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "SubPromotionDTO", strict = false)
public class SubPromotionDTO implements Serializable{
	@Element(name = "promotionCode", required = false)
	  private String promotionCode;
	@Element(name = "promotionName", required = false)
	  private String promotionName;
	
	@Element(name = "staDatetime", required = false)
	private String staDatetime;
	@Element(name = "expireDatetime", required = false)
	private String expireDatetime;
	
	
	
	
	
	public String getStaDatetime() {
		return staDatetime;
	}
	public void setStaDatetime(String staDatetime) {
		this.staDatetime = staDatetime;
	}
	public String getExpireDatetime() {
		return expireDatetime;
	}
	public void setExpireDatetime(String expireDatetime) {
		this.expireDatetime = expireDatetime;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	
	
	
}
