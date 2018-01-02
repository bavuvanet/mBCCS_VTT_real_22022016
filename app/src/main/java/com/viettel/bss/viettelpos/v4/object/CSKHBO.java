package com.viettel.bss.viettelpos.v4.object;

import java.io.Serializable;

public class CSKHBO implements Serializable{
	private String isdn;
	private String rawData;
	private String packageChange;
	private String cdtCode;
	
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getPackageChange() {
		return packageChange;
	}
	public void setPackageChange(String packageChange) {
		this.packageChange = packageChange;
	}

	public String getCdtCode() {
		return cdtCode;
	}

	public void setCdtCode(String cdtCode) {
		this.cdtCode = cdtCode;
	}
}
