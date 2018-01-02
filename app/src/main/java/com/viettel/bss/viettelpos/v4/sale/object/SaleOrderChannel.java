package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "SaleOrderChannel", strict = false)
public class SaleOrderChannel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2446808973034190934L;
	@Element(name = "channel", required = false)
	private String channel;
	@Element(name = "saleOrderId", required = false)
	private Long saleOrderId;
	@Element(name = "saleOrderCode", required = false)
	private String saleOrderCode;
	@Element(name = "shopId", required = false)
	private Long shopId;
	@Element(name = "staffId", required = false)
	private Long staffId;
	@Element(name = "status", required = false)
	private Long status;
	@Element(name = "createDate", required = false)
	private String createDate;
	@Element(name = "cancelDate", required = false)
	private String cancelDate;
	@Element(name = "refuseDate", required = false)
	private String refuseDate;
	@Element(name = "refuseStaffId", required = false)
	private Long refuseStaffId;
	@Element(name = "approveDate", required = false)
	private String approveDate;
	@Element(name = "approveStaffId", required = false)
	private Long approveStaffId;
	@Element(name = "warning", required = false)
	private String warning;
	@Element(name = "paymethod", required = false)
	private Long paymethod;
	@Element(name = "staffCode", required = false)
	private String staffCode;
	@Element(name = "staffName", required = false)
	private String staffName;

	public Long getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(Long saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public String getSaleOrderCode() {
		return saleOrderCode;
	}

	public void setSaleOrderCode(String saleOrderCode) {
		this.saleOrderCode = saleOrderCode;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getRefuseDate() {
		return refuseDate;
	}

	public void setRefuseDate(String refuseDate) {
		this.refuseDate = refuseDate;
	}

	public Long getRefuseStaffId() {
		return refuseStaffId;
	}

	public void setRefuseStaffId(Long refuseStaffId) {
		this.refuseStaffId = refuseStaffId;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public Long getApproveStaffId() {
		return approveStaffId;
	}

	public void setApproveStaffId(Long approveStaffId) {
		this.approveStaffId = approveStaffId;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(Long paymethod) {
		this.paymethod = paymethod;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}
