package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObject;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObjectDetail;
import com.viettel.bss.viettelpos.v4.sale.object.StockOrderDetail;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOjectMerge;

public class CacheData {
	
	private ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges = new ArrayList<>();
	private static CacheData instanse = null;
	private ViewInfoOjectMerge viInfoOjectMerge = null;
	
	private ArrayList<StockOrderDetail> lisStockOrderDetails = new ArrayList<>();
	private String stockOrderCode;
	private ArrayList<GetOrderObjectDetail> lisGetOrderObjectDetails = new ArrayList<>();
	private  GetOrderObject orderObject = new GetOrderObject();
	private String versionclient;
	private String version;
	public String getVersionclient() {
		return versionclient;
	}

	public void setVersionclient(String versionclient) {
		this.versionclient = versionclient;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public GetOrderObject getOrderObject() {
		return orderObject;
	}

	public void setOrderObject(GetOrderObject orderObject) {
		this.orderObject = orderObject;
	}

	public ArrayList<GetOrderObjectDetail> getLisGetOrderObjectDetails() {
		return lisGetOrderObjectDetails;
	}

	public void setLisGetOrderObjectDetails(
			ArrayList<GetOrderObjectDetail> lisGetOrderObjectDetails) {
		this.lisGetOrderObjectDetails = lisGetOrderObjectDetails;
	}

	public String getStockOrderCode() {
		return stockOrderCode;
	}

	public void setStockOrderCode(String stockOrderCode) {
		this.stockOrderCode = stockOrderCode;
	}

	public ArrayList<StockOrderDetail> getLisStockOrderDetails() {
		return lisStockOrderDetails;
	}

	public void setLisStockOrderDetails(
			ArrayList<StockOrderDetail> lisStockOrderDetails) {
		this.lisStockOrderDetails = lisStockOrderDetails;
	}

	public ViewInfoOjectMerge getViInfoOjectMerge() {
		return viInfoOjectMerge;
	}
	
	public void setViInfoOjectMerge(ViewInfoOjectMerge viInfoOjectMerge) {
		this.viInfoOjectMerge = viInfoOjectMerge;
	}

	public static CacheData getInstanse() {
		if(instanse == null){
			instanse = new CacheData();
		}
		return instanse;
	}

	public static void setInstanse(CacheData instanse) {
		CacheData.instanse = instanse;
	}

	public ArrayList<ViewInfoOjectMerge> getLisInfoOjectMerges() {
		return lisInfoOjectMerges;
	}

	public void setLisInfoOjectMerges(
			ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges) {
		this.lisInfoOjectMerges = lisInfoOjectMerges;
	}
}
