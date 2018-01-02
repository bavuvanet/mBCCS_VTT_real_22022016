package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;

@Root(name = "return", strict = false)
public class SubscriberDTO implements Serializable {
	@Element(name = "account", required = false)
	private String account;
	@Element(name = "serviceType", required = false)
	private String serviceType;
	@Element(name = "serviceTypeName", required = false)
	private String serviceTypeName;
	@Element(name = "custType", required = false)
	private String custType;
	@Element(name = "subType", required = false)
	private String subType;
	@Element(name = "offerId", required = false)
	private String offerId;
	@Element(name = "productCode", required = false)
	private String productCode;
	@Element(name = "telecomServiceId", required = false)
	private String telecomServiceId;
	@Element(name = "actStatusText", required = false)
	private String actStatusText;
	@Element(name = "statusView", required = false)
	private String statusView;
	@Element(name = "accountNo", required = false)
	private String accountNo;
	@Element(name = "customerName", required = false)
	private String customerName;
	@Element(name = "idNo", required = false)
	private String idNo;
	@Element(name = "birthDate", required = false)
	private String birthDate;
	@Element(name = "technology", required = false)
	private String technology;
	@Element(name = "technologyText", required = false)
	private String technologyText;
	@Element(name = "telecomServiceName", required = false)
	private String telecomServiceName;
	@Element(name = "telecomServiceAlias", required = false)
	private String telecomServiceAlias;
	@Element(name = "productName", required = false)
	private String productName;
	@ElementList(name = "lstSubPromotionPrepaidDTO", entry = "lstSubPromotionPrepaidDTO", required = false, inline = true)
	private ArrayList<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO;
	@Element(name = "promotionCode", required = false)
	private String promotionCode;
	@Element(name = "subId", required = false)
	private String subId;
	@ElementList(name = "lstSubInfrastructureDTO", entry = "lstSubInfrastructureDTO", required = false, inline = true)
	private ArrayList<SubInfrastructureDTO> lstSubInfrastructureDTO;
	@Element(name = "isdn", required = false)
	@ElementList(name = "lstSubPromotionDTO", entry = "lstSubPromotionDTO", required = false, inline = true)
	private ArrayList<SubPromotionDTO> lstSubPromotionDTO;
	@Element(name = "custId", required = false)
	private String custId;
    @Element(name = "actStatusName", required = false)
    private String actStatusName;
	@Element(name = "actStatusView", required = false)
	private String actStatusView;
	@Element(name = "payType", required = false)
	private String payType;
	@Element(name = "rawData", required = false)
	private String rawData;
    @Element(name = "statusName", required = false)
    private String statusName;
    @Element(name = "subTypeName", required = false)
    private String subTypeName;
    @Element(name = "isdn", required = false)
    private String isdn;
	@Element(name = "newProductCode", required = false)
	private String newProductCode;
	@Element(name = "cdtCode", required = false)
	private String cdtCode;
	
	
	
	@Element(name = "firstConnect", required = false)
	private String firstConnect;

	public String getFirstConnect() {
		if("null".equals(firstConnect)){
			return "";
		}
		return firstConnect;
	}

	public void setFirstConnect(String firstConnect) {
		this.firstConnect = firstConnect;
	}
    @Element(name = "actStatus", required = false)
    private String actStatus;
    @Element(name = "accountId", required = false)
    private Long accountId;
    @Element(name = "accountDTO", required = false)
    private AccountDTO accountDTO;
	private Boolean checker=false;

	public Boolean getChecker() {
		return checker;
	}

	public String getActStatusView() {
		return actStatusView;
	}

	public void setActStatusView(String actStatusView) {
		this.actStatusView = actStatusView;
	}

	public void setChecker(Boolean checker) {
		this.checker = checker;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public ArrayList<SubPromotionDTO> getLstSubPromotionDTO() {
		return lstSubPromotionDTO;
	}

	public void setLstSubPromotionDTO(
			ArrayList<SubPromotionDTO> lstSubPromotionDTO) {
		this.lstSubPromotionDTO = lstSubPromotionDTO;
	}

	public ArrayList<SubInfrastructureDTO> getLstSubInfrastructureDTO() {
		return lstSubInfrastructureDTO;
	}


	public void setLstSubInfrastructureDTO(
			ArrayList<SubInfrastructureDTO> lstSubInfrastructureDTO) {
		this.lstSubInfrastructureDTO = lstSubInfrastructureDTO;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getTechnologyText() {
		return technologyText;
	}

	public void setTechnologyText(String technologyText) {
		this.technologyText = technologyText;
	}

	public String getTelecomServiceName() {
		return telecomServiceName;
	}

	public void setTelecomServiceName(String telecomServiceName) {
		this.telecomServiceName = telecomServiceName;
	}

	public String getTelecomServiceAlias() {
		return telecomServiceAlias;
	}

	public void setTelecomServiceAlias(String telecomServiceAlias) {
		this.telecomServiceAlias = telecomServiceAlias;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ArrayList<SubPromotionPrepaidDTO> getLstSubPromotionPrepaidDTO() {
		return lstSubPromotionPrepaidDTO;
	}

	public void setLstSubPromotionPrepaidDTO(
			ArrayList<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO) {
		this.lstSubPromotionPrepaidDTO = lstSubPromotionPrepaidDTO;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getServiceType() {
		return getTelecomServiceAlias();
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeName() {
		return getTelecomServiceName();
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(String telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getActStatusText() {
		return actStatusText;
	}

	public void setActStatusText(String actStatusText) {
		this.actStatusText = actStatusText;
	}

	public String getStatusView() {
		return statusView;
	}

	public void setStatusView(String statusView) {
		this.statusView = statusView;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

    public String getActStatusName() {
        return actStatusName;
    }

    public void setActStatusName(String actStatusName) {
        this.actStatusName = actStatusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
	
	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getNewProductCode() {
		return newProductCode;
	}

	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}

	public String getCdtCode() {
		return cdtCode;
	}

	public void setCdtCode(String cdtCode) {
		this.cdtCode = cdtCode;
	}
    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
}
