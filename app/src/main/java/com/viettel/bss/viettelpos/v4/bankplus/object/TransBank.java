package com.viettel.bss.viettelpos.v4.bankplus.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.R;

import android.app.Activity;
import android.content.res.Resources;

@Root(name = "return", strict = false)
public class TransBank {
	@Element(name = "requestDate", required = false)
	private String requestDate;
	@Element(name = "processCode", required = false)
    private String processCode;
	@Element(name = "processName", required = false)
	private String processName;
	@Element(name = "serviceCode", required = false)
    private String serviceCode;
	@Element(name = "isdn", required = false)
    private String isdn;
	@Element(name = "amount", required = false)
    private String amount;
	@Element(name = "errorCode", required = false)
    private String errorCode;
	@Element(name = "serviceType", required = false)
    private String serviceType;
	@Element(name = "fee", required = false)
    private String fee;
	@Element(name = "requestId", required = false)
    private String requestId;
	@Element(name = "errorName", required = false)
	private String errorName;
	
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
