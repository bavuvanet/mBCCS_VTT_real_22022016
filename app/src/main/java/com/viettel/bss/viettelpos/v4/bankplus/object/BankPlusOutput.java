package com.viettel.bss.viettelpos.v4.bankplus.object;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.object.DataMapping;

@Root(name = "return", strict = false)
public class BankPlusOutput {

	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstMerchantBeans", entry = "lstMerchantBeans", required = false, inline = true)
	private List<MerchantBean> lstMerchantBeans = new ArrayList<MerchantBean>();
	@ElementList(name = "lstShopReceiverMoney", entry = "lstShopReceiverMoney", required = false, inline = true)
	private List<DataMapping> lstDataMappings = new ArrayList<DataMapping>();
	@ElementList(name = "lstBankBeans", entry = "lstBankBeans", required = false, inline = true)
	private List<BankBean> lstBanks = new ArrayList<BankBean>();
	@ElementList(name = "lstTransBankBOs", entry = "lstTransBankBOs", required = false, inline = true)
	private List<TransBank> lstTransBanks = new ArrayList<TransBank>();
	@Element(name = "fee", required = false)
	private String fee;
	@Element(name = "discount", required = false)
	private String discount;
	@Element(name = "balance", required = false)
	private String balance;
	@Element(name = "datetime", required = false)
	private String datetime;
	@Element(name = "amount", required = false)
	private String amount;
	@Element(name = "tidNumber", required = false)
	private String tidNumber;
	@Element(name = "customerName", required = false)
	private String customerName;
	@Element(name = "miniStatementData", required = false)
	private String miniStatementData;
	@Element(name = "cycleBill", required = false)
	private String cycleBill;
	@Element(name = "oringinalRequestId", required = false)
	private String oringinalRequestId;
	@Element(name = "billCode", required = false)
	private String billCode;
	@Element(name = "serviceCode", required = false)
	private String serviceCode;
	@Element(name = "serviceName", required = false)
	private String serviceName;
	@Element(name = "requestId", required = false)
	private String requestId;
	@Element(name = "loadMoreBP", required = false)
	private boolean loadMoreBP;
	@Element(name = "loadMoreSale", required = false)
	private boolean loadMoreSale;
	@Element(name = "isdn", required = false)
	private String isdn;

	@ElementList(name = "lstTppTransDTOs", entry = "lstTppTransDTOs", required = false, inline = true)
	private List<TppTransDTO> lstTppTransDTOs = new ArrayList<TppTransDTO>();

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public List<TppTransDTO> getLstTppTransDTOs() {
		return lstTppTransDTOs;
	}

	public void setLstTppTransDTOs(List<TppTransDTO> lstTppTransDTOs) {
		this.lstTppTransDTOs = lstTppTransDTOs;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MerchantBean> getLstMerchantBeans() {
		return lstMerchantBeans;
	}

	public void setLstMerchantBeans(List<MerchantBean> lstMerchantBeans) {
		this.lstMerchantBeans = lstMerchantBeans;
	}

	public List<DataMapping> getLstDataMappings() {
		return lstDataMappings;
	}

	public void setLstDataMappings(List<DataMapping> lstDataMappings) {
		this.lstDataMappings = lstDataMappings;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public List<BankBean> getLstBanks() {
		return lstBanks;
	}

	public void setLstBanks(List<BankBean> lstBanks) {
		this.lstBanks = lstBanks;
	}

	public String getTidNumber() {
		return tidNumber;
	}

	public void setTidNumber(String tidNumber) {
		this.tidNumber = tidNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMiniStatementData() {
		return miniStatementData;
	}

	public void setMiniStatementData(String miniStatementData) {
		this.miniStatementData = miniStatementData;
	}

	public String getCycleBill() {
		return cycleBill;
	}

	public void setCycleBill(String cycleBill) {
		this.cycleBill = cycleBill;
	}

	public String getOringinalRequestId() {
		return oringinalRequestId;
	}

	public void setOringinalRequestId(String oringinalRequestId) {
		this.oringinalRequestId = oringinalRequestId;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<TransBank> getLstTransBanks() {
		return lstTransBanks;
	}

	public void setLstTransBanks(List<TransBank> lstTransBanks) {
		this.lstTransBanks = lstTransBanks;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isLoadMoreBP() {
		return loadMoreBP;
	}

	public void setLoadMoreBP(boolean loadMoreBP) {
		this.loadMoreBP = loadMoreBP;
	}

	public boolean isLoadMoreSale() {
		return loadMoreSale;
	}

	public void setLoadMoreSale(boolean loadMoreSale) {
		this.loadMoreSale = loadMoreSale;
	}

	
}
