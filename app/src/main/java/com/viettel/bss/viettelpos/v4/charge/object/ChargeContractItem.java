package com.viettel.bss.viettelpos.v4.charge.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ChargeContractItem", strict = false)
public class ChargeContractItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4654346L;
	@Element(name = "isdnContract", required = false)
	private String isdnContract;
	@Element(name = "customerName", required = false)
	private String customerName;
	@Element(name = "verifyStatus", required = false)
	private String verifyStatus;
	@Element(name = "xPos", required = false)
	private String xPos;
	@Element(name = "yPos", required = false)
	private String yPos;
	@Element(name = "aMountCharge", required = false)
	private String aMountCharge;
	@Element(name = "contractFormMngt", required = false)
	private String contractFormMngt;
	@Element(name = "contractFormMngtName", required = false)
	private String contractFormMngtName;
	@Element(name = "contractId", required = false)
	private String contractId;
	@Element(name = "contractNo", required = false)
	private String contractNo;
	@Element(name = "debit", required = false)
	private String debit;
	@Element(name = "hotCharge", required = false)
	private String hotCharge;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "payMethodCode", required = false)
	private String payMethodCode;
	@Element(name = "payMethodName", required = false)
	private String payMethodName;
	@Element(name = "payer", required = false)
	private String payer;
	@Element(name = "paymentStatus", required = false)
	private String paymentStatus;
	@Element(name = "priorDebit", required = false)
	private String priorDebit;
	@Element(name = "telFax", required = false)
	private String telFax;
	@Element(name = "totCharge", required = false)
	private String totCharge;
	@Element(name = "verifyDate", required = false)
	private String verifyDate;
	@Element(name = "reasonNotCollect", required = false)
	private String reasonNotCollect;
	@Element(name = "staffCode", required = false)
	private String staffCode;
	@Element(name = "address", required = false)
	private String address;
	@Element(name = "status", required = false)
	private String status;
	
	@Element(name = "idNo", required = false)
	private String idNo;
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsdnContract() {
		return isdnContract;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setIsdnContract(String isdnContract) {
		this.isdnContract = isdnContract;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getxPos() {
		return xPos;
	}

	public void setxPos(String xPos) {
		this.xPos = xPos;
	}

	public String getyPos() {
		return yPos;
	}

	public void setyPos(String yPos) {
		this.yPos = yPos;
	}

	public String getaMountCharge() {
		return aMountCharge;
	}

	public void setaMountCharge(String aMountCharge) {
		this.aMountCharge = aMountCharge;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContractFormMngt() {
		return contractFormMngt;
	}

	public void setContractFormMngt(String contractFormMngt) {
		this.contractFormMngt = contractFormMngt;
	}

	public String getContractFormMngtName() {
		return contractFormMngtName;
	}

	public void setContractFormMngtName(String contractFormMngtName) {
		this.contractFormMngtName = contractFormMngtName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getHotCharge() {
		return hotCharge;
	}

	public void setHotCharge(String hotCharge) {
		this.hotCharge = hotCharge;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getPayMethodCode() {
		return payMethodCode;
	}

	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}

	public String getPayMethodName() {
		return payMethodName;
	}

	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPriorDebit() {
		return priorDebit;
	}

	public void setPriorDebit(String priorDebit) {
		this.priorDebit = priorDebit;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	public String getTotCharge() {
		return totCharge;
	}

	public void setTotCharge(String totCharge) {
		this.totCharge = totCharge;
	}

	public String getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getReasonNotCollect() {
		return reasonNotCollect;
	}

	public void setReasonNotCollect(String reasonNotCollect) {
		this.reasonNotCollect = reasonNotCollect;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	@Override
	public String toString() {
		return String
				.format("{\"ChargeContractItem\":{\"address\":\"%s\", \"contractFormMngt\":\"%s\", \"contractFormMngtName\":\"%s\", \"contractId\":\"%s\", \"contractNo\":\"%s\", \"debit\":\"%s\", \"hotCharge\":\"%s\", \"isdn\":\"%s\", \"payMethodCode\":\"%s\", \"payMethodName\":\"%s\", \"payer\":\"%s\", \"paymentStatus\":\"%s\", \"priorDebit\":\"%s\", \"telFax\":\"%s\", \"totCharge\":\"%s\", \"aMountCharge\":\"%s\"}}",
						address, contractFormMngt, contractFormMngtName,
						contractId, contractNo, debit, hotCharge, isdn,
						payMethodCode, payMethodName, payer, paymentStatus,
						priorDebit, telFax, totCharge, aMountCharge);
	}

}
