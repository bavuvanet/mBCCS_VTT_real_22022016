package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class SubStockModelRelReq {

	private String subStockModelRelReq;
	private String saleServiceId;
	private String stockModelId;
	private String serial;
	private String stockTypeId;
	private String reclaimCommitmentCode;
	private String reclaimProCode;
	private String reclaimAmount;
	private String reclaimPayMethod;
	private String reclaimDatetime;

	public SubStockModelRelReq(
			String saleServiceId, String stockModelId, String serial,
			String stockTypeId, String reclaimCommitmentCode,
			String reclaimProCode, String reclaimAmount,
			String reclaimPayMethod, String reclaimDatetime) {
		super();
		this.saleServiceId = saleServiceId;
		this.stockModelId = stockModelId;
		this.serial = serial;
		this.stockTypeId = stockTypeId;
		this.reclaimCommitmentCode = reclaimCommitmentCode;
		this.reclaimProCode = reclaimProCode;
		this.reclaimAmount = reclaimAmount;
		this.reclaimPayMethod = reclaimPayMethod;
		this.reclaimDatetime = reclaimDatetime;
	}

	public SubStockModelRelReq() {
		super();
	}

	public String getSubStockModelRelReq() {
		return subStockModelRelReq;
	}

	public void setSubStockModelRelReq(String subStockModelRelReq) {
		this.subStockModelRelReq = subStockModelRelReq;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getStockModelId() {
		return stockModelId;
	}

	public void setStockModelId(String stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getReclaimCommitmentCode() {
		return reclaimCommitmentCode;
	}

	public void setReclaimCommitmentCode(String reclaimCommitmentCode) {
		this.reclaimCommitmentCode = reclaimCommitmentCode;
	}

	public String getReclaimProCode() {
		return reclaimProCode;
	}

	public void setReclaimProCode(String reclaimProCode) {
		this.reclaimProCode = reclaimProCode;
	}

	public String getReclaimAmount() {
		return reclaimAmount;
	}

	public void setReclaimAmount(String reclaimAmount) {
		this.reclaimAmount = reclaimAmount;
	}

	public String getReclaimPayMethod() {
		return reclaimPayMethod;
	}

	public void setReclaimPayMethod(String reclaimPayMethod) {
		this.reclaimPayMethod = reclaimPayMethod;
	}

	public String getReclaimDatetime() {
		return reclaimDatetime;
	}

	public void setReclaimDatetime(String reclaimDatetime) {
		this.reclaimDatetime = reclaimDatetime;
	}

}
