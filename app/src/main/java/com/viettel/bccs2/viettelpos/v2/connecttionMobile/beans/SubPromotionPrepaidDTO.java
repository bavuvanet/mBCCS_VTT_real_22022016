package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "SubPromotionPrepaidDTO", strict = false)
public class SubPromotionPrepaidDTO implements Serializable {
	@Element(name = "prepaidCode", required = false)
	private String prepaidCode;
	@Element(name = "prepaidDetailId", required = false)
	private String prepaidDetailId;
	@Element(name = "prepaidName", required = false)
	private String prepaidName;
	@Element(name = "promotionCode", required = false)
	private String promotionCode;
	@Element(name = "effectDate", required = false)
	private String effectDate;
	@Element(name = "createDate", required = false)
	private String createDate;
	@Element(name = "endDate", required = false)
	private String endDate;
	@Element(name = "numMonth", required = false)
	private String areaCode;
	private String err;
	private Long prepaidAmount;
	private Long prepaidBilling;
	private String productCode;
	private String provinceCode;
	private String status;
	private Long subId;
	private Long subPromotionPrepaidId;
	private String numMonth;
	private Long paymentId;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public Long getPrepaidAmount() {
		return prepaidAmount;
	}

	public void setPrepaidAmount(Long prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	public Long getPrepaidBilling() {
		return prepaidBilling;
	}

	public void setPrepaidBilling(Long prepaidBilling) {
		this.prepaidBilling = prepaidBilling;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public Long getSubPromotionPrepaidId() {
		return subPromotionPrepaidId;
	}

	public void setSubPromotionPrepaidId(Long subPromotionPrepaidId) {
		this.subPromotionPrepaidId = subPromotionPrepaidId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getNumMonth() {
		return numMonth;
	}
	public void setNumMonth(String numMonth) {
		this.numMonth = numMonth;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public String getPrepaidCode() {
		return prepaidCode;
	}
	public void setPrepaidCode(String prepaidCode) {
		this.prepaidCode = prepaidCode;
	}
	public String getPrepaidDetailId() {
		return prepaidDetailId;
	}
	public void setPrepaidDetailId(String prepaidDetailId) {
		this.prepaidDetailId = prepaidDetailId;
	}
	public String getPrepaidName() {
		return prepaidName;
	}
	public void setPrepaidName(String prepaidName) {
		this.prepaidName = prepaidName;
	}
	
	
	
}
