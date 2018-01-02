package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class PakageChargeBeans  implements Serializable{
	
	private String createDate;
	private String groupProductId;
	private String offerCode;
	private String offerId;
	private String offerName;
	private String offerTypeId;
	private String productId;
	private String startDate;
	private String status;
	private String productCode;
	private String msType;
	
	private String isFTTB;
	
	private String description;
	
	private String mstNoRf;
	
	
	public String getMstNoRf() {
		return mstNoRf;
	}
	public void setMstNoRf(String mstNoRf) {
		this.mstNoRf = mstNoRf;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsFTTB() {
		return isFTTB;
	}
	public void setIsFTTB(String isFTTB) {
		this.isFTTB = isFTTB;
	}
	public String getMsType() {
		return msType;
	}
	public void setMsType(String msType) {
		this.msType = msType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getGroupProductId() {
		return groupProductId;
	}
	public void setGroupProductId(String groupProductId) {
		this.groupProductId = groupProductId;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferTypeId() {
		return offerTypeId;
	}
	public void setOfferTypeId(String offerTypeId) {
		this.offerTypeId = offerTypeId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
