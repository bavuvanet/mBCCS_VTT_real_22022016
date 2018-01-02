package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bss.viettelpos.v4.commons.AttributeObject;

public class SaleMoneyCouting extends AttributeObject {
	private String amountNotTax;
	private String amountTax;
	private String tax;
	private String vat;
	private String amount;
	private String discountAmount;
	private String token;
	
	public String getAmountNotTax() {
		if (amountNotTax == null || amountNotTax.isEmpty()) {
			return "0";
		}
		return amountNotTax;
	}

	public void setAmountNotTax(String amountNotTax) {
		this.amountNotTax = amountNotTax;
	}

	public String getAmountTax() {
		if (amountTax == null || amountTax.isEmpty()) {
			return "0";
		}
		return amountTax;
	}

	public void setAmountTax(String amountTax) {
		this.amountTax = amountTax;
	}

	public String getTax() {
		if (tax == null || tax.isEmpty()) {
			return "0";
		}
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDiscountAmount() {
		if (discountAmount == null) {
			return "0";
		}
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
