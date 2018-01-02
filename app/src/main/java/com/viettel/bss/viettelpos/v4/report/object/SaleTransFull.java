package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SaleTransFull implements Serializable {

	private String saleTransId;
	private String saleTransCode;
	private String staffCode;
	private String staffName;
	private String saleTransDate;
	private String amountTax;
	private String custName;
	private String saleTransTypeName;
	private String inSpectStatus;
	
	private String saleTransDetailId;
	private String stockModelId;
	private String stockModelCode;
	private String name;
	private String quantity;
	
	private boolean isCheckApproval;
	private ArrayList<SaleTransFull> mlistDetailTranfull;
	
	

	
	
	
	
	public ArrayList<SaleTransFull> getMlistDetailTranfull() {
		return mlistDetailTranfull;
	}
	public void setMlistDetailTranfull(ArrayList<SaleTransFull> mlistDetailTranfull) {
		this.mlistDetailTranfull = mlistDetailTranfull;
	}
	public boolean isCheckApproval() {
		return isCheckApproval;
	}
	public void setCheckApproval(boolean isCheckApproval) {
		this.isCheckApproval = isCheckApproval;
	}
	public String getSaleTransDetailId() {
		return saleTransDetailId;
	}
	public void setSaleTransDetailId(String saleTransDetailId) {
		this.saleTransDetailId = saleTransDetailId;
	}
	public String getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getStockModelCode() {
		return stockModelCode;
	}
	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSaleTransId() {
		return saleTransId;
	}
	public void setSaleTransId(String saleTransId) {
		this.saleTransId = saleTransId;
	}
	public String getSaleTransCode() {
		return saleTransCode;
	}
	public void setSaleTransCode(String saleTransCode) {
		this.saleTransCode = saleTransCode;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getSaleTransDate() {
		return saleTransDate;
	}
	public void setSaleTransDate(String saleTransDate) {
		this.saleTransDate = saleTransDate;
	}
	public String getAmountTax() {
		return amountTax;
	}
	public void setAmountTax(String amountTax) {
		this.amountTax = amountTax;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSaleTransTypeName() {
		return saleTransTypeName;
	}
	public void setSaleTransTypeName(String saleTransTypeName) {
		this.saleTransTypeName = saleTransTypeName;
	}
	public String getInSpectStatus() {
		return inSpectStatus;
	}
	public void setInSpectStatus(String inSpectStatus) {
		this.inSpectStatus = inSpectStatus;
	}
	
	
	
	
	
	
	
	
}
