package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IsdnOwnerObject implements Serializable {

	private String staffCode;
	private String ownerCode;
	private String ownerName;
	private String statusName;
	private String stockTypeName;
	private String stockTypeId;
	private String ownerId;
	private String ownerType;
	private String status;
	private String isdn;
	
	private String fromIsdn;
	private String toIsdn;
	private String typeIsdn;

	private String stockModelName;

	public String getStockModelName() {
		return stockModelName;
	}

	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}

	public String getFromIsdn() {
		return fromIsdn;
	}
	public void setFromIsdn(String fromIsdn) {
		this.fromIsdn = fromIsdn;
	}
	public String getToIsdn() {
		return toIsdn;
	}
	public void setToIsdn(String toIsdn) {
		this.toIsdn = toIsdn;
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
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getTypeIsdn() {
		return typeIsdn;
	}

	public void setTypeIsdn(String typeIsdn) {
		this.typeIsdn = typeIsdn;
	}
}
