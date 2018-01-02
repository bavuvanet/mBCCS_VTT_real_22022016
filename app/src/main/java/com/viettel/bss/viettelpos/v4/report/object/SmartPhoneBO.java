package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;
import java.util.ArrayList;

public class SmartPhoneBO implements Serializable {
	private String staffCode;
    private String ownerCode;
    private String ownerName;
    private String statusName;
    private String stockTypeName;
    private Long stockTypeId;
    private Long ownerId;
    private Long ownerType;
    private Long status;
    private Long isdn;
    private Long sumAmount;
    private Long amount;
    private Long amountNotTax;
    private Long taxAmount;
    private Long discountAmount;
    private Long quantity;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long vat;
    
    
    private ArrayList<SmartPhoneBO> listSmartPhoneBO = new ArrayList<>();
    
    
	
    
    
	
	public ArrayList<SmartPhoneBO> getListSmartPhoneBO() {
		return listSmartPhoneBO;
	}
	public void setListSmartPhoneBO(ArrayList<SmartPhoneBO> listSmartPhoneBO) {
		this.listSmartPhoneBO = listSmartPhoneBO;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
	public Long getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(Long stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIsdn() {
		return isdn;
	}
	public void setIsdn(Long isdn) {
		this.isdn = isdn;
	}
	public Long getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Long sumAmount) {
		this.sumAmount = sumAmount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getAmountNotTax() {
		return amountNotTax;
	}
	public void setAmountNotTax(Long amountNotTax) {
		this.amountNotTax = amountNotTax;
	}
	public Long getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getStockModelCode() {
		return stockModelCode;
	}
	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public Long getVat() {
		return vat;
	}
	public void setVat(Long vat) {
		this.vat = vat;
	}
}
