package com.viettel.bss.viettelpos.v4.customer.object;

import java.math.BigDecimal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

@Root(name = "SubBeanBO", strict = false)
public class SubBeanBO implements Parcelable{
	@Element (name = "actStatus", required = false)
	private String actStatus;
	@Element (name = "amountDebit", required = false)
	private BigDecimal amountDebit;
	@Element (name = "billCycleFrom", required = false)
	private String billCycleFrom;
	@Element (name = "isdn", required = false)
	private String isdn;
	@Element (name = "payNew", required = false)
	private BigDecimal payNew;
	@Element (name = "payment", required = false)
	private BigDecimal payment;
	@Element (name = "priorDebit", required = false)
	private BigDecimal priorDebit;
	@Element (name = "staOfCycle", required = false)
	private BigDecimal staOfCycle;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "subId", required = false)
	private String subId;
	@Element (name = "telServiceName", required = false)
	private String telServiceName;
	@Element (name = "totCharge", required = false)
	private BigDecimal totCharge;
	@Element (name = "deployArea", required = false)
	private String deployArea;

	public String getDeployArea() {
		return deployArea;
	}

	public void setDeployArea(String deployArea) {
		this.deployArea = deployArea;
	}

	public String getActStatus() {
		return actStatus;
	}
	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}
	public BigDecimal getAmountDebit() {
		return amountDebit;
	}
	public void setAmountDebit(BigDecimal amountDebit) {
		this.amountDebit = amountDebit;
	}
	public String getBillCycleFrom() {
		return billCycleFrom;
	}
	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public BigDecimal getPayNew() {
		return payNew;
	}
	public void setPayNew(BigDecimal payNew) {
		this.payNew = payNew;
	}
	public BigDecimal getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	public BigDecimal getPriorDebit() {
		return priorDebit;
	}
	public void setPriorDebit(BigDecimal priorDebit) {
		this.priorDebit = priorDebit;
	}
	public BigDecimal getStaOfCycle() {
		return staOfCycle;
	}
	public void setStaOfCycle(BigDecimal staOfCycle) {
		this.staOfCycle = staOfCycle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getTelServiceName() {
		return telServiceName;
	}
	public void setTelServiceName(String telServiceName) {
		this.telServiceName = telServiceName;
	}
	public BigDecimal getTotCharge() {
		return totCharge;
	}
	public void setTotCharge(BigDecimal totCharge) {
		this.totCharge = totCharge;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
