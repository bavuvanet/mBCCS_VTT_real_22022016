package com.viettel.bss.viettelpos.v4.channel.object;

public class MultipleProduct {
	private final Product product;
	private final int num;

	public MultipleProduct(Product p, int n) {
		this.product = p;
		this.num = n;
	}

	public Product getProduct() {
		return product;
	}

	public int getNum() {
		return num;
	}
}