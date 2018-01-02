package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "PromotionUnitVas", strict = false)
public class PromotionUnitVas implements Serializable{
	@Element (name = "promotionCodeUnit", required = false)
	private String promotionCodeUnit;
	@Element (name = "promotionCodeUnitName", required = false)
	private String promotionCodeUnitName;
	public String getPromotionCodeUnit() {
		return promotionCodeUnit;
	}
	public void setPromotionCodeUnit(String promotionCodeUnit) {
		this.promotionCodeUnit = promotionCodeUnit;
	}
	public String getPromotionCodeUnitName() {
		return promotionCodeUnitName;
	}
	public void setPromotionCodeUnitName(String promotionCodeUnitName) {
		this.promotionCodeUnitName = promotionCodeUnitName;
	}
	
	
	
}
