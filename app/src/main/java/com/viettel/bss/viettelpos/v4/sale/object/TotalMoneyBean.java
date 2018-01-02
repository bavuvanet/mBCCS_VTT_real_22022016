package com.viettel.bss.viettelpos.v4.sale.object;

public class TotalMoneyBean {
	private String moneyPreTax; // Tien truoc thue
	private String moneyAfterTax;// Tong tien phai tra
	private String discount;// Khau tru
	private String amount;// So luong giao dich
	private String tax;// Tien thue
	private String vat;// thue suat

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getMoneyPreTax() {
		return moneyPreTax;
	}

	public void setMoneyPreTax(String moneyPreTax) {
		this.moneyPreTax = moneyPreTax;
	}

	public String getMoneyAfterTax() {
		return moneyAfterTax;
	}

	public void setMoneyAfterTax(String moneyAfterTax) {
		this.moneyAfterTax = moneyAfterTax;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
}
