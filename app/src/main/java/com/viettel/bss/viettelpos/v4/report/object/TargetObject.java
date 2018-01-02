package com.viettel.bss.viettelpos.v4.report.object;

import java.util.List;

public class TargetObject {
	private String[] lstHeader;
	private List<String[]> lstValue;

	public String[] getLstHeader() {	
		
		return lstHeader;
	}

	public void setLstHeader(String[] lstHeader) {
		this.lstHeader = lstHeader;
	}

	public List<String[]> getLstValue() {
		return lstValue;
	}

	public void setLstValue(List<String[]> lstValue) {
		this.lstValue = lstValue;
	}

}
