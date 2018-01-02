package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.util.ArrayList;

public class ObjThongTinHH {

	private String saleServicesModelId;
	private String stockTypeId;
	private String stockTypeName;
	private String saleServiceCode;
	private boolean ischeck = false;
	
	private ArrayList<ObjAPStockModelBeanBO> listStockModelBeanBO;

	public ObjThongTinHH(String saleServicesModelId, String stockTypeId,
			String stockTypeName,
			ArrayList<ObjAPStockModelBeanBO> listStockModelBeanBO) {
		super();
		this.saleServicesModelId = saleServicesModelId;
		this.stockTypeId = stockTypeId;
		this.stockTypeName = stockTypeName;
		this.listStockModelBeanBO = listStockModelBeanBO;
	}

	public ObjThongTinHH(String saleServicesModelId, String stockTypeId,
			String stockTypeName, String saleServiceCode,
			ArrayList<ObjAPStockModelBeanBO> listStockModelBeanBO) {
		super();
		this.saleServicesModelId = saleServicesModelId;
		this.stockTypeId = stockTypeId;
		this.stockTypeName = stockTypeName;
		this.saleServiceCode = saleServiceCode;
		this.listStockModelBeanBO = listStockModelBeanBO;
	}

	
	
	public ObjThongTinHH(String saleServicesModelId, String stockTypeId,
			String stockTypeName, String saleServiceCode,
			ArrayList<ObjAPStockModelBeanBO> listStockModelBeanBO, boolean ischeck) {
		super();
		this.saleServicesModelId = saleServicesModelId;
		this.stockTypeId = stockTypeId;
		this.stockTypeName = stockTypeName;
		this.saleServiceCode = saleServiceCode;
		this.listStockModelBeanBO = listStockModelBeanBO;
		this.ischeck = ischeck;
	}

	public ObjThongTinHH() {
		super();
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public String getSaleServiceCode() {
		return saleServiceCode;
	}

	public void setSaleServiceCode(String saleServiceCode) {
		this.saleServiceCode = saleServiceCode;
	}

	public ArrayList<ObjAPStockModelBeanBO> getListStockModelBeanBO() {
		return listStockModelBeanBO;
	}

	public void setListStockModelBeanBO(
			ArrayList<ObjAPStockModelBeanBO> listStockModelBeanBO) {
		this.listStockModelBeanBO = listStockModelBeanBO;
	}

	public String getSaleServicesModelId() {
		return saleServicesModelId;
	}

	public void setSaleServicesModelId(String saleServicesModelId) {
		this.saleServicesModelId = saleServicesModelId;
	}

	public String getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getStockTypeName() {
		return stockTypeName;
	}

	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}

	public ArrayList<ObjAPStockModelBeanBO> getList() {
		return listStockModelBeanBO;
	}

	public void setList(ArrayList<ObjAPStockModelBeanBO> list) {
		this.listStockModelBeanBO = list;
	}

}
