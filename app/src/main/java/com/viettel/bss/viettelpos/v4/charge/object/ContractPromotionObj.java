package com.viettel.bss.viettelpos.v4.charge.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ContractPromotionObj implements Serializable {

	private String customerName;
	private String contractCode;
	private String promotionName;
	private String numberMonthPromotion;
	
	private String phoneNumberRepresent;
	private String phoneNumberContact;
	private String addressTBC;
	private String customers;
	private String startTimePromotions;
	private String endTimePromotions;
	private String subId;
	private String serviceType;
	private String isdn;
	private String promotionCode;
	
	
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getNumberMonthPromotion() {
		return numberMonthPromotion;
	}
	public void setNumberMonthPromotion(String numberMonthPromotion) {
		this.numberMonthPromotion = numberMonthPromotion;
	}
	public String getPhoneNumberRepresent() {
		return phoneNumberRepresent;
	}
	public void setPhoneNumberRepresent(String phoneNumberRepresent) {
		this.phoneNumberRepresent = phoneNumberRepresent;
	}
	public String getPhoneNumberContact() {
		return phoneNumberContact;
	}
	public void setPhoneNumberContact(String phoneNumberContact) {
		this.phoneNumberContact = phoneNumberContact;
	}
	public String getAddressTBC() {
		return addressTBC;
	}
	public void setAddressTBC(String addressTBC) {
		this.addressTBC = addressTBC;
	}
	public String getCustomers() {
		return customers;
	}
	public void setCustomers(String customers) {
		this.customers = customers;
	}
	public String getStartTimePromotions() {
		return startTimePromotions;
	}
	public void setStartTimePromotions(String startTimePromotions) {
		this.startTimePromotions = startTimePromotions;
	}
	public String getEndTimePromotions() {
		return endTimePromotions;
	}
	public void setEndTimePromotions(String endTimePromotions) {
		this.endTimePromotions = endTimePromotions;
	}
	
	
	
	
	
	
}
