package com.viettel.bss.viettelpos.v4.work.object;

public class ObjectCount {
	private int total;
    private int sub;
    private int subToiHan;
	public ObjectCount(int total, int sub, int subToiHan) {
		setTotal(total);
		setSub(sub);
		setSubToiHan(subToiHan);
	}
	private void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}
	private void setSub(int sub) {
		this.sub = sub;
	}
	public int getSub() {
		return sub;
	}
	private void setSubToiHan(int subToiHan) {
		this.subToiHan = subToiHan;
	}
	public int getSubToiHan() {
		return subToiHan;
	}
}
