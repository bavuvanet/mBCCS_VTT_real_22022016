package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.customer.object.Spin;

public class ChangeZoneBean {
	  private String  numResetZone;
	  private String balance;
	  private String currZoneCode;
	  private String currProduct;
	  private String numberChangeZone;
	  
	  public String getNumberChangeZone() {
		return numberChangeZone;
	}

	public void setNumberChangeZone(String numberChangeZone) {
		this.numberChangeZone = numberChangeZone;
	}

	// lay danh sach ly do
	  private ArrayList<Spin> lstReason = new ArrayList<>();
	  private String feeChangeZone;
	  
	public String getFeeChangeZone() {
		return feeChangeZone;
	}

	public void setFeeChangeZone(String feeChangeZone) {
		this.feeChangeZone = feeChangeZone;
	}

	public String getNumResetZone() {
		return numResetZone;
	}

	public void setNumResetZone(String numResetZone) {
		this.numResetZone = numResetZone;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCurrZoneCode() {
		return currZoneCode;
	}

	public void setCurrZoneCode(String currZoneCode) {
		this.currZoneCode = currZoneCode;
	}

	public String getCurrProduct() {
		return currProduct;
	}

	public void setCurrProduct(String currProduct) {
		this.currProduct = currProduct;
	}

	public ArrayList<Spin> getLstReason() {
		return lstReason;
	}

	public void setLstReason(ArrayList<Spin> lstReason) {
		this.lstReason = lstReason;
	}
	  
	  
}
