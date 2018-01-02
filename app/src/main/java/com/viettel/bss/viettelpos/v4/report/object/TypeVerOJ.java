package com.viettel.bss.viettelpos.v4.report.object;

import java.util.ArrayList;

public class TypeVerOJ {
	private Long qty = 0L;
	private String name;
	private boolean done;
	private int colorId;
	private ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs;
	public TypeVerOJ(Long qty, String name,
			ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs, boolean done,
			int colorId) {
		setQty(qty);
		setName(name);
		setArrVerifyCustomerOJs(arrVerifyCustomerOJs);
		// setDone(done);
		setColorId(colorId);
	}
	private void setColorId(int colorId) {
		this.colorId = colorId;
	}
	public int getColorId() {
		return colorId;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public boolean getDone() {
		return done;
	}
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public Long getQty() {
		return qty;
	}
	private void setQty(Long qty) {
		this.qty = qty;
	}
	private void setArrVerifyCustomerOJs(
            ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs) {
		this.arrVerifyCustomerOJs = arrVerifyCustomerOJs;
	}
	public ArrayList<VerifyCustomerOJ> getArrVerifyCustomerOJs() {
		return arrVerifyCustomerOJs;
	}
}
