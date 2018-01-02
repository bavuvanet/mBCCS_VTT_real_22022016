package com.viettel.bss.viettelpos.v4.report.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "BonusVasObject", strict = false)
public class BonusVasObject {
	@Element (name = "cycleName", required = false)
	private String cycleName;
	@Element (name = "vasType", required = false)
	private String vasType;
	@Element (name = "serviceCode", required = false)
	private String serviceCode;
	@Element (name = "vasCode", required = false)
	private String vasCode;
	@Element (name = "vasPrice", required = false)
	private String vasPrice;
	@Element (name = "bonusRate", required = false)
	private String bonusRate;
	@Element (name = "bonus", required = false)
	private String bonus;
	@Element (name = "cycleDays", required = false)
	private String cycleDays;
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
	public String getVasType() {
		return vasType;
	}
	public void setVasType(String vasType) {
		this.vasType = vasType;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getVasCode() {
		return vasCode;
	}
	public void setVasCode(String vasCode) {
		this.vasCode = vasCode;
	}
	public String getVasPrice() {
		return vasPrice;
	}
	public void setVasPrice(String vasPrice) {
		this.vasPrice = vasPrice;
	}
	public String getBonusRate() {
		return bonusRate;
	}
	public void setBonusRate(String bonusRate) {
		this.bonusRate = bonusRate;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getCycleDays() {
		return cycleDays;
	}
	public void setCycleDays(String cycleDays) {
		this.cycleDays = cycleDays;
	}
	
	
	
	
}
