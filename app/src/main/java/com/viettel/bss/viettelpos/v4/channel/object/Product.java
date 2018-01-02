package com.viettel.bss.viettelpos.v4.channel.object;

public class Product {
	private final String name;
	private final long price;

	public Product(String name, long price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}
}