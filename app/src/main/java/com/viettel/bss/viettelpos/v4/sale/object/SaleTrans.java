package com.viettel.bss.viettelpos.v4.sale.object;

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "SaleTrans", strict = false)
public class SaleTrans {
	private Long saleTransId;
	private Long invoiceUsedId;
	private String saleTransDate;
	private String saleTransType;
	private String status;
	private String checkStock;
	private Date invoiceCreateDate;
	private Long shopId;
	private Long staffId;
	private String payMethod;
	private Long saleServiceId;
	private Long saleServicePriceId;
	private Long amountService;
	private Long amountModel;
	@Element (name = "discount", required = false)
	private String discount;
	private Long promotion;
	@Element (name = "amountTax", required = false)
	private String amountTax;
	private String amountNotTax;
	private String vat;
	private String tax;
	private Long subId;
	private String isdn;
	private String custName;
	private String contractNo;
	private String telNumber;
	private String company;
	private String address;
	private String tin;
	private String note;
	private String destroyUser;
	private Date destroyDate;
	private String approverUser;
	private Date approverDate;
	private Long reasonId;
	private Long telecomServiceId;
	private String transferGoods;
	private String saleTransCode;
	private Long stockTransId;
	private Long createStaffId;
	private Long receiverId;
	private Long receiverType;
	private String inTransId;
	private Long fromSaleTransId;
	private String airtimeId;
	private String transactionId;
	private Long recordWorkId;
	private Long staffOwnerId;
	private Long channelTypeId;
	private Long channelId;

	private String saleTransName;
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public SaleTrans() {

	}

	public SaleTrans(String saleTransType, String saleTransName) {
		this.saleTransType = saleTransType;
		this.saleTransName = saleTransName;
	}

	public String getSaleTransName() {
		return saleTransName;
	}

	public void setSaleTransName(String saleTransName) {
		this.saleTransName = saleTransName;
	}

	public Long getSaleTransId() {
		return saleTransId;
	}

	public void setSaleTransId(Long saleTransId) {
		this.saleTransId = saleTransId;
	}

	public Long getInvoiceUsedId() {
		return invoiceUsedId;
	}

	public void setInvoiceUsedId(Long invoiceUsedId) {
		this.invoiceUsedId = invoiceUsedId;
	}

	public String getSaleTransDate() {
		return saleTransDate;
	}

	public void setSaleTransDate(String saleTransDate) {
		this.saleTransDate = saleTransDate;
	}

	public String getSaleTransType() {
		return saleTransType;
	}

	public void setSaleTransType(String saleTransType) {
		this.saleTransType = saleTransType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckStock() {
		return checkStock;
	}

	public void setCheckStock(String checkStock) {
		this.checkStock = checkStock;
	}

	public Date getInvoiceCreateDate() {
		return invoiceCreateDate;
	}

	public void setInvoiceCreateDate(Date invoiceCreateDate) {
		this.invoiceCreateDate = invoiceCreateDate;
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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Long getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public Long getSaleServicePriceId() {
		return saleServicePriceId;
	}

	public void setSaleServicePriceId(Long saleServicePriceId) {
		this.saleServicePriceId = saleServicePriceId;
	}

	public Long getAmountService() {
		return amountService;
	}

	public void setAmountService(Long amountService) {
		this.amountService = amountService;
	}

	public Long getAmountModel() {
		return amountModel;
	}

	public void setAmountModel(Long amountModel) {
		this.amountModel = amountModel;
	}

	public Long getPromotion() {
		return promotion;
	}

	public void setPromotion(Long promotion) {
		this.promotion = promotion;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAmountTax() {
		return amountTax;
	}

	public void setAmountTax(String amountTax) {
		this.amountTax = amountTax;
	}

	public String getAmountNotTax() {
		return amountNotTax;
	}

	public void setAmountNotTax(String amountNotTax) {
		this.amountNotTax = amountNotTax;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDestroyUser() {
		return destroyUser;
	}

	public void setDestroyUser(String destroyUser) {
		this.destroyUser = destroyUser;
	}

	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	public String getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(String approverUser) {
		this.approverUser = approverUser;
	}

	public Date getApproverDate() {
		return approverDate;
	}

	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getTransferGoods() {
		return transferGoods;
	}

	public void setTransferGoods(String transferGoods) {
		this.transferGoods = transferGoods;
	}

	public String getSaleTransCode() {
		return saleTransCode;
	}

	public void setSaleTransCode(String saleTransCode) {
		this.saleTransCode = saleTransCode;
	}

	public Long getStockTransId() {
		return stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public Long getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(Long createStaffId) {
		this.createStaffId = createStaffId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(Long receiverType) {
		this.receiverType = receiverType;
	}

	public String getInTransId() {
		return inTransId;
	}

	public void setInTransId(String inTransId) {
		this.inTransId = inTransId;
	}

	public Long getFromSaleTransId() {
		return fromSaleTransId;
	}

	public void setFromSaleTransId(Long fromSaleTransId) {
		this.fromSaleTransId = fromSaleTransId;
	}

	public String getAirtimeId() {
		return airtimeId;
	}

	public void setAirtimeId(String airtimeId) {
		this.airtimeId = airtimeId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getRecordWorkId() {
		return recordWorkId;
	}

	public void setRecordWorkId(Long recordWorkId) {
		this.recordWorkId = recordWorkId;
	}

	public Long getStaffOwnerId() {
		return staffOwnerId;
	}

	public void setStaffOwnerId(Long staffOwnerId) {
		this.staffOwnerId = staffOwnerId;
	}

	public Long getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
