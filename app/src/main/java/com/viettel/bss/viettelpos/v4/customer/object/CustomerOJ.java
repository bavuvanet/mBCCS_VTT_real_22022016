package com.viettel.bss.viettelpos.v4.customer.object;

import com.viettel.bss.viettelpos.v4.charge.object._ChargeCommonOJ;

public class CustomerOJ extends _ChargeCommonOJ {
	private String addr;
    private String tel;
	public CustomerOJ(String name) {
		super(name);
	}

	public CustomerOJ(String name, String addr, String tel) {
		super(name);
		setAddr(addr);
		setTel(tel);
		// TODO Auto-generated constructor stub
	}
	private void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr() {
		return addr;
	}
	private void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel() {
		return tel;
	}
	public String getAllContent() {
		// TODO Auto-generated method stub
		return getName() + "---" + addr + "---" + tel;
	}
}
