package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ResultEquipments implements Serializable {

	private String address;
	private String assetId;
	private String assetName;
	private String manageAssetId;
	private String qty;
	private String staffCode;
	private String staffId;
	private String status;
	private String staffName; 
	private String objectType;
	private String isEquipment;
	
	private ArrayList<SalePointImageObject> arrSalePoint = new ArrayList<>();
	
	
	
	
	
	public String getIsEquipment() {
		return isEquipment;
	}
	public void setIsEquipment(String isEquipment) {
		this.isEquipment = isEquipment;
	}
	public ArrayList<SalePointImageObject> getArrSalePoint() {
		return arrSalePoint;
	}
	public void setArrSalePoint(ArrayList<SalePointImageObject> arrSalePoint) {
		this.arrSalePoint = arrSalePoint;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getManageAssetId() {
		return manageAssetId;
	}
	public void setManageAssetId(String manageAssetId) {
		this.manageAssetId = manageAssetId;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
