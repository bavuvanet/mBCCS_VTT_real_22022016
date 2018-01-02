package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class PaymentPrePaidDetailBeans implements Serializable{

	@Element(name = "endMonth", required = false)
	private String endMonth;
	@Element(name = "moneyUnit", required = false)
	private String moneyUnit;// don nvi
	@Element(name = "promAmount", required = false)
	private String promAmount;// so tien cuoc dong truoc trong 1 thang
	@Element(name = "startMonth", required = false)
	private String startMonth;
	@Element(name = "subMonth", required = false)
	private String subMonth;// so thang dong truoc
	@Element(name = "totalMoney", required = false)
	private String totalMoney;// tong tien
	@Element(name = "promId", required = false)
	private String promId;
	@Element(name = "discountAmount", required = false)
	protected String discountAmount;


	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getTotalMoney() {

		if (!CommonActivity.isNullOrEmpty(promAmount)
				&& !CommonActivity.isNullOrEmpty(subMonth)) {

			totalMoney = String.valueOf((Long.parseLong(promAmount) * Long
					.parseLong(subMonth)));
			return totalMoney;
		} else {
			return "";
		}
	}
	
	public String getPromId() {
		return promId;
	}

	public void setPromId(String promId) {
		this.promId = promId;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getMoneyUnit() {
		return moneyUnit;
	}

	public void setMoneyUnit(String moneyUnit) {
		this.moneyUnit = moneyUnit;
	}

	public String getPromAmount() {
		return promAmount;
	}

	public void setPromAmount(String promAmount) {
		this.promAmount = promAmount;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getSubMonth() {
		return subMonth;
	}

	public void setSubMonth(String subMonth) {
		this.subMonth = subMonth;
	}

}
