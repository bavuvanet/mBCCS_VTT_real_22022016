package com.viettel.bss.viettelpos.v4.cc.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "DetailCalledDTO", strict = false)
public class DetailCalledDTO {

	@Element(name = "typeName", required = false)
	private String typeName;
	@Element(name = "bgDate", required = false)
	private String bgDate;
	@Element(name = "isdnCalled", required = false)
	private String isdnCalled;
	@Element(name = "lsDate", required = false)
	private String lsDate;
	@Element(name = "numCallGt6", required = false)
	private int numCallGt6;
	@Element(name = "numCallLt6", required = false)
	private int numCallLt6;
	@Element(name = "numSms", required = false)
	private int numSms;

	private boolean isCheck = false;

	public void copyFrom(DetailCalledDTO tmp) {
		if (tmp == null) {
			return;
		}
		this.setTypeName(tmp.getTypeName());
		this.setBgDate(tmp.getBgDate());
		this.setIsdnCalled(tmp.getIsdnCalled());
		this.setLsDate(tmp.getLsDate());
		this.setNumCallGt6(tmp.getNumCallGt6());
		this.setNumCallLt6(tmp.getNumCallLt6());
		this.setNumSms(tmp.getNumSms());
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	private String getBgDate() {
		return bgDate;
	}

	public void setBgDate(String bgDate) {
		this.bgDate = bgDate;
	}

	public String getIsdnCalled() {
		return isdnCalled;
	}

	public void setIsdnCalled(String isdnCalled) {
		this.isdnCalled = isdnCalled;
	}

	public String getLsDate() {
		return lsDate;
	}

	public void setLsDate(String lsDate) {
		this.lsDate = lsDate;
	}

	public int getNumCallGt6() {
		return numCallGt6;
	}

	public void setNumCallGt6(int numCallGt6) {
		this.numCallGt6 = numCallGt6;
	}

	public int getNumCallLt6() {
		return numCallLt6;
	}

	public void setNumCallLt6(int numCallLt6) {
		this.numCallLt6 = numCallLt6;
	}

	public int getNumSms() {
		return numSms;
	}

	public void setNumSms(int numSms) {
		this.numSms = numSms;
	}

	private String getTypeName() {
		return typeName;
	}

	private void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
