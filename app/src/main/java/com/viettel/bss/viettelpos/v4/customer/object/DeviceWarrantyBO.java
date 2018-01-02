package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "DeviceWarrantyBO", strict = false)
public class DeviceWarrantyBO {
	@Element(name = "imei", required = false)
	private String imei;
	@Element(name = "stockModelCode", required = false)
    private String stockModelCode;
	@Element(name = "stockModelName", required = false)
    private String stockModelName;
	@Element(name = "stockModelId", required = false)
    private Long stockModelId;
	@Element(name = "saleDate", required = false)
    private String saleDate;
	@Element(name = "warrantyRegistedDate", required = false)
    private String warrantyRegistedDate;
	@Element(name = "warrantyRegistedDateStr", required = false)
	private String warrantyRegistedDateStr;
	@Element(name = "warrantyExpriedDate", required = false)
    private String warrantyExpriedDate;
	@Element(name = "warrantyExpriedDateStr", required = false)
	private String warrantyExpriedDateStr;
	@Element(name = "saleStatus", required = false)
    private Long saleStatus;
	@Element(name = "statusReceive", required = false)
    private Long statusReceive;
	@Element(name = "description", required = false)
    private String description;
	@Element(name = "warExpriedStatus", required = false)
    private String warExpriedStatus;
	@Element(name = "statusReject", required = false)
    private String statusReject;
	@Element(name = "checkSCDV", required = false)
    private String checkSCDV;
	@Element(name = "receiceBHVT", required = false)
    private String receiceBHVT;
    @Element(name = "receiceSCDV", required = false)
    private String receiceSCDV;
    @Element(name = "actionType", required = false)
    private String actionType;
    @Element(name = "actionTypeName", required = false)
    private String actionTypeName;
    @Element(name = "createDatemBccsStr", required = false)
    private String createDatemBccsStr;
    
    private boolean select = false;
    
    @Element(name = "cName", required = false)
    private String cName;
    @Element(name = "cIsdn", required = false)
    private String cIsdn;
    @Element(name = "cAddress", required = false)
    private String cAddress;
    @Element(name = "id", required = false)
    private Long id;
    @Element(name = "warrantyRequestDetailId", required = false)
    private Long warrantyRequestDetailId;
    @Element(name = "receiptNomBccs", required = false)
    private String receiptNomBccs;
    @Element(name = "errorDesc", required = false)
    private String errorDesc;
	@Element(name = "codeAgency", required = false)
    private String codeAgency;

	public String getCodeAgency() {
		return codeAgency;
	}

	public void setCodeAgency(String codeAgency) {
		this.codeAgency = codeAgency;
	}

	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getStockModelCode() {
		return stockModelCode;
	}
	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}
	public String getStockModelName() {
		return stockModelName;
	}
	public void setStockModelName(String stockModelName) {
		this.stockModelName = stockModelName;
	}
	public Long getStockModelId() {
		return stockModelId;
	}
	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getWarrantyRegistedDate() {
		return warrantyRegistedDate;
	}
	public void setWarrantyRegistedDate(String warrantyRegistedDate) {
		this.warrantyRegistedDate = warrantyRegistedDate;
	}
	public String getWarrantyExpriedDate() {
		return warrantyExpriedDate;
	}
	public void setWarrantyExpriedDate(String warrantyExpriedDate) {
		this.warrantyExpriedDate = warrantyExpriedDate;
	}
	public Long getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Long saleStatus) {
		this.saleStatus = saleStatus;
	}
	public Long getStatusReceive() {
		return statusReceive;
	}
	public void setStatusReceive(Long statusReceive) {
		this.statusReceive = statusReceive;
	}
	public String getDescription() {
		if(CommonActivity.isNullOrEmpty(description)){
			return "";
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWarExpriedStatus() {
		return warExpriedStatus;
	}
	public void setWarExpriedStatus(String warExpriedStatus) {
		this.warExpriedStatus = warExpriedStatus;
	}
	public String getStatusReject() {
		return statusReject;
	}
	public void setStatusReject(String statusReject) {
		this.statusReject = statusReject;
	}
	public String getCheckSCDV() {
		return checkSCDV;
	}
	public void setCheckSCDV(String checkSCDV) {
		this.checkSCDV = checkSCDV;
	}
	public String getReceiceBHVT() {
		return receiceBHVT;
	}
	public void setReceiceBHVT(String receiceBHVT) {
		this.receiceBHVT = receiceBHVT;
	}
	public String getReceiceSCDV() {
		return receiceSCDV;
	}
	public void setReceiceSCDV(String receiceSCDV) {
		this.receiceSCDV = receiceSCDV;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public String getWarrantyRegistedDateStr() {
		return warrantyRegistedDateStr;
	}
	public void setWarrantyRegistedDateStr(String warrantyRegistedDateStr) {
		this.warrantyRegistedDateStr = warrantyRegistedDateStr;
	}
	public String getWarrantyExpriedDateStr() {
		return warrantyExpriedDateStr;
	}
	public void setWarrantyExpriedDateStr(String warrantyExpriedDateStr) {
		this.warrantyExpriedDateStr = warrantyExpriedDateStr;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcIsdn() {
		return cIsdn;
	}
	public void setcIsdn(String cIsdn) {
		this.cIsdn = cIsdn;
	}
	public String getcAddress() {
		return cAddress;
	}
	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWarrantyRequestDetailId() {
		return warrantyRequestDetailId;
	}
	public void setWarrantyRequestDetailId(Long warrantyRequestDetailId) {
		this.warrantyRequestDetailId = warrantyRequestDetailId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionTypeName() {
		if(CommonActivity.isNullOrEmpty(actionTypeName)){
			return "";
		}
		return actionTypeName;
	}
	public void setActionTypeName(String actionTypeName) {
		this.actionTypeName = actionTypeName;
	}
	public String getCreateDatemBccsStr() {
		return createDatemBccsStr;
	}
	public void setCreateDatemBccsStr(String createDatemBccsStr) {
		this.createDatemBccsStr = createDatemBccsStr;
	}
	public String getReceiptNomBccs() {
		return receiptNomBccs;
	}
	public void setReceiptNomBccs(String receiptNomBccs) {
		this.receiptNomBccs = receiptNomBccs;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
    
	
    
    
}
