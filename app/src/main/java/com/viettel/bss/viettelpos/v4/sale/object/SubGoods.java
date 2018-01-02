package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;
import java.util.List;

import com.viettel.bss.viettelpos.v4.customer.object.Spin;

public class SubGoods implements Serializable {

	//Mat hang thu hoi
	private String subGoodsId;
	private String stockModelId;
	private String stockModelCode;
	private String stockModelName;
	private String reclaimPayMethodName;
	private String serial;
	private String serialToRetrieve;
	
	//Cho mat hang moi
	private String supplyMethodCode;
	private String supplyMethodName;
	private String supplyProgramCode;
	private String supplyProgramName;
	private String programMonth;
	private String payAmount;
	private String payMethod;
	private String payMethodName;
	private boolean virtualGoods;
	
	private String saleServiceModelId;
	private String saleServiceModelName;
	
	//Cac list trung gian
	private List<Spin> lstSaleServiceDetail;
	private boolean retrieveGoods;
	private boolean allowSerial;
	
	public String getSubGoodsId() {
		return subGoodsId;
	}
	public void setSubGoodsId(String subGoodsId) {
		this.subGoodsId = subGoodsId;
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
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public String getReclaimPayMethodName() {
		return reclaimPayMethodName;
	}
	public void setReclaimPayMethodName(String reclaimPayMethodName) {
		this.reclaimPayMethodName = reclaimPayMethodName;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getSerialToRetrieve() {
		return serialToRetrieve;
	}
	public void setSerialToRetrieve(String serialToRetrieve) {
		this.serialToRetrieve = serialToRetrieve;
	}
	public String getSupplyMethodCode() {
		return supplyMethodCode;
	}
	public void setSupplyMethodCode(String supplyMethodCode) {
		this.supplyMethodCode = supplyMethodCode;
	}
	public String getSupplyMethodName() {
		return supplyMethodName;
	}
	public void setSupplyMethodName(String supplyMethodName) {
		this.supplyMethodName = supplyMethodName;
	}
	public String getSupplyProgramCode() {
		return supplyProgramCode;
	}
	public void setSupplyProgramCode(String supplyProgramCode) {
		this.supplyProgramCode = supplyProgramCode;
	}
	public String getSupplyProgramName() {
		return supplyProgramName;
	}
	public void setSupplyProgramName(String supplyProgramName) {
		this.supplyProgramName = supplyProgramName;
	}
	public String getProgramMonth() {
		return programMonth;
	}
	public void setProgramMonth(String programMonth) {
		this.programMonth = programMonth;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getSaleServiceModelId() {
		return saleServiceModelId;
	}
	public void setSaleServiceModelId(String saleServiceModelId) {
		this.saleServiceModelId = saleServiceModelId;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getSaleServiceModelName() {
		return saleServiceModelName;
	}
	public void setSaleServiceModelName(String saleServiceModelName) {
		this.saleServiceModelName = saleServiceModelName;
	}
	public List<Spin> getLstSaleServiceDetail() {
		return lstSaleServiceDetail;
	}
	public void setLstSaleServiceDetail(List<Spin> lstSaleServiceDetail) {
		this.lstSaleServiceDetail = lstSaleServiceDetail;
	}
	public boolean isRetrieveGoods() {
		return retrieveGoods;
	}
	public void setRetrieveGoods(boolean retrieveGoods) {
		this.retrieveGoods = retrieveGoods;
	}
	public boolean isVirtualGoods() {
		return virtualGoods;
	}
	public void setVirtualGoods(boolean virtualGoods) {
		this.virtualGoods = virtualGoods;
	}
	public boolean isAllowSerial() {
		return allowSerial;
	}
	public void setAllowSerial(boolean allowSerial) {
		this.allowSerial = allowSerial;
	}
	
}
