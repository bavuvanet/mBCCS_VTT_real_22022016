package com.viettel.bss.viettelpos.v4.report.object;

public class InfoSearchInvoicePaymentStaff {

	private String staffCode;
	private String fromDate;
	private String toDate;
	private String telecomeServiceId;
	private String saleTransType;
	private String inspectStatus;

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTelecomeServiceId() {
		return telecomeServiceId;
	}

	public void setTelecomeServiceId(String telecomeServiceId) {
		this.telecomeServiceId = telecomeServiceId;
	}

	public String getSaleTransType() {
		return saleTransType;
	}

	public void setSaleTransType(String saleTransType) {
		this.saleTransType = saleTransType;
	}

	public String getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(String inspectStatus) {
		this.inspectStatus = inspectStatus;
	}

}
