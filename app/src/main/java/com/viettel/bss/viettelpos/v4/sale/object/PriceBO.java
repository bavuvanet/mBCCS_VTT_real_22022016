package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "PriceBO", strict = false)
public class PriceBO {
	@Element (name = "amountTax", required = false)
	private Long amountTax;
	@Element (name = "discountAmount", required = false)
	private Long discountAmount;
	@Element (name = "amountNotTax", required = false)
	private Long amountNotTax;
	@Element (name = "tax", required = false)
	private Long tax;
	@Element (name = "errMsg", required = false)
	private String errMsg;
	public Long getAmountTax() {
		return amountTax;
	}
	public void setAmountTax(Long amountTax) {
		this.amountTax = amountTax;
	}
	public Long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Long getAmountNotTax() {
		return amountNotTax;
	}
	public void setAmountNotTax(Long amountNotTax) {
		this.amountNotTax = amountNotTax;
	}
	public Long getTax() {
		return tax;
	}
	public void setTax(Long tax) {
		this.tax = tax;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
    
}
