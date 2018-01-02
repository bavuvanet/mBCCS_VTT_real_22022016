package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "VirtualInvoice", strict = false)
public class VirtualInvoice implements Serializable{
	@Element (name = "adjustmentNegative", required = false)
	private Double adjustmentNegative;
	@Element (name = "adjustmentPositive", required = false)
	private Double adjustmentPositive;
	@Element (name = "contractRemain", required = false)
	private Double contractRemain;
	@Element (name = "adjustPromotion", required = false)
	private Long adjustPromotion;
	@Element (name = "adjustment", required = false)
	private Double adjustment;
	@Element (name = "amount", required = false)
	private Double amount;
	@Element (name = "amountDebit", required = false)
	private Double amountDebit;
	@Element (name = "amountNotTax", required = false)
	private Double amountNotTax;
	@Element (name = "amountPreTax", required = false)
	private Double amountPreTax;
	@Element (name = "amountTax", required = false)
	private Double amountTax;
	@Element (name = "contractId", required = false)
	private Long contractId;
	@Element (name = "contractNo", required = false)
	private String contractNo;
	@Element (name = "discount", required = false)
	private Long discount;
	@Element (name = "hotCharge", required = false)
	private Double hotCharge;
	@Element (name = "needAmount", required = false)
	private Double needAmount;
	@Element (name = "paymentAmount", required = false)
	private Double paymentAmount;
	@Element (name = "payAmount", required = false)
	private Double payAmount;
	@Element (name = "priorDebit", required = false)
	private Double priorDebit;
	@Element (name = "promotion", required = false)
	private Long promotion;
	@Element (name = "tax", required = false)
	private Double tax;

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public Double getAdjustmentNegative() {
		return adjustmentNegative;
	}

	public void setAdjustmentNegative(Double adjustmentNegative) {
		this.adjustmentNegative = adjustmentNegative;
	}

	public Double getAdjustmentPositive() {
		return adjustmentPositive;
	}

	public void setAdjustmentPositive(Double adjustmentPositive) {
		this.adjustmentPositive = adjustmentPositive;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmountDebit() {
		return amountDebit;
	}

	public void setAmountDebit(Double amountDebit) {
		this.amountDebit = amountDebit;
	}

	public Double getAmountNotTax() {
		return amountNotTax;
	}

	public void setAmountNotTax(Double amountNotTax) {
		this.amountNotTax = amountNotTax;
	}

	public Double getAmountPreTax() {
		return amountPreTax;
	}

	public void setAmountPreTax(Double amountPreTax) {
		this.amountPreTax = amountPreTax;
	}

	public Double getAmountTax() {
		return amountTax;
	}

	public void setAmountTax(Double amountTax) {
		this.amountTax = amountTax;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getContractRemain() {
		return contractRemain;
	}

	public void setContractRemain(Double contractRemain) {
		this.contractRemain = contractRemain;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Double getHotCharge() {
		return hotCharge;
	}

	public void setHotCharge(Double hotCharge) {
		this.hotCharge = hotCharge;
	}

	public Double getNeedAmount() {
		return needAmount;
	}

	public void setNeedAmount(Double needAmount) {
		this.needAmount = needAmount;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getPriorDebit() {
		return priorDebit;
	}

	public void setPriorDebit(Double priorDebit) {
		this.priorDebit = priorDebit;
	}

	public Long getPromotion() {
		return promotion;
	}

	public void setPromotion(Long promotion) {
		this.promotion = promotion;
	}

	public Long getAdjustPromotion() {
		return adjustPromotion;
	}

	public void setAdjustPromotion(Long adjustPromotion) {
		this.adjustPromotion = adjustPromotion;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

}
