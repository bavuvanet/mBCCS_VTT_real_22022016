package com.viettel.bss.viettelpos.v4.sale.object;

public class BhldObject {
	private Long recordWorkId;
	private String saleProgramCode;
	private String saleProgramName;
	private String address;
	public Long getRecordWorkId() {
		return recordWorkId;
	}
	public void setRecordWorkId(Long recordWorkId) {
		this.recordWorkId = recordWorkId;
	}

	public String getSaleProgramCode() {
		return saleProgramCode;
	}
	public void setSaleProgramCode(String saleProgramCode) {
		this.saleProgramCode = saleProgramCode;
	}
	public String getSaleProgramName() {
		return saleProgramName;
	}
	public void setSaleProgramName(String saleProgramName) {
		this.saleProgramName = saleProgramName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getSaleProgramName();
	}
}
