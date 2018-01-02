package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "stockSim", strict = false)
public class StockSim {
	@Element(name = "isdn", required = false)
	 private String isdn;
	@Element(name = "pin", required = false)
	private String pin;
	@Element(name = "puk", required = false)
	private String puk;
	@Element(name = "pin2", required = false)
	private String pin2;
	@Element(name = "puk2", required = false)
	private String puk2;
	 private String serial;
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getPuk() {
		return puk;
	}
	public void setPuk(String puk) {
		this.puk = puk;
	}
	public String getSerial() {
		return serial;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPin2() {
		return pin2;
	}

	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}

	public String getPuk2() {
		return puk2;
	}

	public void setPuk2(String puk2) {
		this.puk2 = puk2;
	}

	public void setSerial(String serial) {
		this.serial = serial;

	}
	 
	 
	 
	 
}
