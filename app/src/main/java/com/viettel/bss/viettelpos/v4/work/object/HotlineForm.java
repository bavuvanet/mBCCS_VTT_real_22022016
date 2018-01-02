package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class HotlineForm implements Serializable{
	private String infraCode;
	private String requestHotlineId;
	public String getInfraCode() {
		return infraCode;
	}
	public void setInfraCode(String infraCode) {
		this.infraCode = infraCode;
	}
	public String getRequestHotlineId() {
		return requestHotlineId;
	}
	public void setRequestHotlineId(String requestHotlineId) {
		this.requestHotlineId = requestHotlineId;
	}
}
