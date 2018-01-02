package com.viettel.bss.viettelpos.v4.channel.object;

import java.util.ArrayList;

public class PaymentAccOJ {
	private final long moneyRemain, moneyMilestone, moneyTotal;
	private ArrayList<MultipleProduct> arrMProduct;

	public PaymentAccOJ(long a1, long a2, long a3) {
		this.moneyRemain = a1;
		this.moneyMilestone = a2;
		this.moneyTotal = a3;
		initArrMProduct();
	}

	public long getMoneyRemain() {
		return moneyRemain;
	}

	public long getMoneyMilestone() {
		return moneyMilestone;
	}

	public long getMoneyTotal() {
		return moneyTotal;
	}

	public ArrayList<MultipleProduct> getArrMProduct() {
		return arrMProduct;
	}

	private void initArrMProduct() {
		// TODO Auto-generated method stub
		arrMProduct = new ArrayList<>();
		arrMProduct.add(new MultipleProduct(new Product("a1", 100000), 3));
		arrMProduct.add(new MultipleProduct(new Product("b1", 50000), 4));
		arrMProduct.add(new MultipleProduct(new Product("c1", 200000), 9));
		arrMProduct.add(new MultipleProduct(new Product("d1", 1000000), 13));
		arrMProduct.add(new MultipleProduct(new Product("a2", 100000), 3));
		// arrMProduct.add(new MultipleProduct(new Product("b2", 50000), 4));
		// arrMProduct.add(new MultipleProduct(new Product("c2", 200000), 9));
		// arrMProduct.add(new MultipleProduct(new Product("d2", 1000000), 13));
		// arrMProduct.add(new MultipleProduct(new Product("a3", 100000), 3));
		// arrMProduct.add(new MultipleProduct(new Product("b3", 50000), 4));
		// arrMProduct.add(new MultipleProduct(new Product("c3", 200000), 9));
		// arrMProduct.add(new MultipleProduct(new Product("d3", 1000000), 13));
	}

}
