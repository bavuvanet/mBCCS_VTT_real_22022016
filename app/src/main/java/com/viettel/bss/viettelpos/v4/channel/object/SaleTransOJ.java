package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;
import java.util.ArrayList;

public class SaleTransOJ implements Serializable {
	
	
	private String saleTransDate;
	private long amoutTax;
	private ArrayList<SaleTransDetailOJ> arrSaleTranDetail = new ArrayList<>();
	public SaleTransOJ() {
		super();
	}
	
	public SaleTransOJ( String saleTransDate, long amoutTax) {
		super();
	
		this.saleTransDate = saleTransDate;
		this.amoutTax = amoutTax;
	}
	
	public ArrayList<SaleTransDetailOJ> getArrSaleTranDetail() {
		return arrSaleTranDetail;
	}

	public void setArrSaleTranDetail(ArrayList<SaleTransDetailOJ> arrSaleTranDetail) {
		this.arrSaleTranDetail = arrSaleTranDetail;
	}

	public String getSaleTransDate() {
		return saleTransDate;
	}
	public void setSaleTransDate(String saleTransDate) {
		this.saleTransDate = saleTransDate;
	}
	public long getAmoutTax() {
		return amoutTax;
	}
	public void setAmoutTax(long amoutTax) {
		this.amoutTax = amoutTax;
	}
	


}
