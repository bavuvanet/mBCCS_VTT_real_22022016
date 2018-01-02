package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "SubRelProductBO", strict = false)
public class SubRelProductBO implements Serializable {

	@Element(name = "vasCode", required = false)
	private String vasCode;
	@Element(name = "vasName", required = false)
	private String vasName;
	@Element(name = "registed", required = false)
	private boolean registed;
	@Element(name = "atrrVas", required = false)
	private String atrrVas;

	private String promotionCode;
	private String promotionName;

	private boolean isEnable = true;

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getVasCode() {
		return vasCode;
	}

	public void setVasCode(String vasCode) {
		this.vasCode = vasCode;
	}

	public boolean isRegisted() {
		return registed;
	}

	public void setRegisted(boolean registed) {
		this.registed = registed;
	}

	public String getAtrrVas() {
		return atrrVas;
	}

	public void setAtrrVas(String atrrVas) {
		this.atrrVas = atrrVas;
	}

	public String getVasName() {
		return vasName;
	}

	public void setVasName(String vasName) {
		this.vasName = vasName;
	}

	@Override
	public String toString() {
		if(!CommonActivity.isNullOrEmpty(vasCode)){
			return vasCode;
		}else{
			return vasName;
		}
		
	}

}
