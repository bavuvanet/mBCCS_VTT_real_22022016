package com.viettel.bss.viettelpos.v4.channel.object;

import java.util.ArrayList;

public class ArraySaleChannelOJ {
	private ArrayList<SaleTransDetailOJ> saleList = new ArrayList<>();

	public ArrayList<SaleTransDetailOJ> getSaleList() {
		return saleList;
	}

	public void setSaleList(ArrayList<SaleTransDetailOJ> saleList) {
		this.saleList = saleList;
	}

}
