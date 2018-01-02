package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v3.connecttionService.model.ApStockSupplyInfo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root(name = "custIdentityDTO", strict = false)
public class ProductOfferingDTO implements Serializable {
	@Element(name = "accountingCode", required = false)
	private String accountingCode;
	@Element(name = "accountingModelCode", required = false)
	private String accountingModelCode;
	@Element(name = "accountingModelName", required = false)
	private String accountingModelName;
	@Element(name = "accountingName", required = false)
	private String accountingName;
	@Element(name = "checkDeposit", required = false)
	private Short checkDeposit;
	@Element(name = "checkSerial", required = false)
	private Short checkSerial;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "demoDuration", required = false)
	private Long demoDuration;
	@Element(name = "deviceType", required = false)
	private String deviceType;
	@Element(name = "isDemo", required = false)
	private Short isDemo;
	@Element(name = "mstNoRf", required = false)
	private String mstNoRf;
	@Element(name = "mstType", required = false)
	private String mstType;
	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "prodPackTypeId", required = false)
	private Long prodPackTypeId;
	@Element(name = "productOfferPriceId", required = false)
	private Long productOfferPriceId;
	@Element(name = "productOfferTypeId", required = false)
	private Long productOfferTypeId;
	@Element(name = "productOfferingId", required = false)
	private Long productOfferingId;
	@Element(name = "productSpecId", required = false)
	private Long productSpecId;
	@Element(name = "productSpecification", required = false)
	private String productSpecification;
	@Element(name = "productTypeName", required = false)
	private String productTypeName;
	@Element(name = "rvnQualityId", required = false)
	private Long rvnQualityId;
	@Element(name = "rvnServiceId", required = false)
	private Long rvnServiceId;
	@Element(name = "stockModelType", required = false)
	private Short stockModelType;
	@Element(name = "telecomServiceId", required = false)
	private Long telecomServiceId;
	@Element(name = "telecomServiceName", required = false)
	private String telecomServiceName;
	@Element(name = "transceiver", required = false)
	private Short transceiver;
	@Element(name = "typeIndex", required = false)
	private int typeIndex;
	@Element(name = "unit", required = false)
	private String unit;
	@Element(name = "updateUser", required = false)
	private String updateUser;
	@Element(name = "vat", required = false)
	private Long vat;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "version", required = false)
	private String version;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "subType", required = false)
	private String subType;
	@Element(name = "code", required = false)
	private String code;
    @Element(name = "requestQuantity", required = false)
    private Long requestQuantity;
    @Element(name = "availableQuantity", required = false)
    private Long availableQuantity;
    @Element(name = "receiveQuantity", required = false)
    private Long receiveQuantity;
	private String serial;
	@ElementList(name = "lstProductSpecCharDTOs", entry = "lstProductSpecCharDTOs", required = false, inline = true)
	private ArrayList<ProductSpecCharDTO> lstProductSpecCharDTOs;
    @ElementList(name = "lstAppApStockSupplyInfo", entry = "lstAppApStockSupplyInfo", required = false, inline = true)
    private List<ApStockSupplyInfo> lstAppApStockSupplyInfo;
	private int selectedCount;

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public ArrayList<ProductSpecCharDTO> getLstProductSpecCharDTOs() {
		return lstProductSpecCharDTOs;
	}

	public void setLstProductSpecCharDTOs(ArrayList<ProductSpecCharDTO> lstProductSpecCharDTOs) {
		this.lstProductSpecCharDTOs = lstProductSpecCharDTOs;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public String getAccountingModelCode() {
		return accountingModelCode;
	}

	public void setAccountingModelCode(String accountingModelCode) {
		this.accountingModelCode = accountingModelCode;
	}

	public String getAccountingModelName() {
		return accountingModelName;
	}

	public void setAccountingModelName(String accountingModelName) {
		this.accountingModelName = accountingModelName;
	}

	public String getAccountingName() {
		return accountingName;
	}

	public void setAccountingName(String accountingName) {
		this.accountingName = accountingName;
	}

	public Short getCheckDeposit() {
		return checkDeposit;
	}

	public void setCheckDeposit(Short checkDeposit) {
		this.checkDeposit = checkDeposit;
	}

	public Short getCheckSerial() {
		return checkSerial;
	}

	public void setCheckSerial(Short checkSerial) {
		this.checkSerial = checkSerial;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getDemoDuration() {
		return demoDuration;
	}

	public void setDemoDuration(Long demoDuration) {
		this.demoDuration = demoDuration;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Short getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(Short isDemo) {
		this.isDemo = isDemo;
	}

	public String getMstNoRf() {
		return mstNoRf;
	}

	public void setMstNoRf(String mstNoRf) {
		this.mstNoRf = mstNoRf;
	}

	public String getMstType() {
		return mstType;
	}

	public void setMstType(String mstType) {
		this.mstType = mstType;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getProdPackTypeId() {
		return prodPackTypeId;
	}

	public void setProdPackTypeId(Long prodPackTypeId) {
		this.prodPackTypeId = prodPackTypeId;
	}

	public Long getProductOfferPriceId() {
		return productOfferPriceId;
	}

	public void setProductOfferPriceId(Long productOfferPriceId) {
		this.productOfferPriceId = productOfferPriceId;
	}

	public Long getProductOfferTypeId() {
		return productOfferTypeId;
	}

	public void setProductOfferTypeId(Long productOfferTypeId) {
		this.productOfferTypeId = productOfferTypeId;
	}

	public Long getProductOfferingId() {
		return productOfferingId;
	}

	public void setProductOfferingId(Long productOfferingId) {
		this.productOfferingId = productOfferingId;
	}

	public Long getProductSpecId() {
		return productSpecId;
	}

	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Long getRvnQualityId() {
		return rvnQualityId;
	}

	public void setRvnQualityId(Long rvnQualityId) {
		this.rvnQualityId = rvnQualityId;
	}

	public Long getRvnServiceId() {
		return rvnServiceId;
	}

	public void setRvnServiceId(Long rvnServiceId) {
		this.rvnServiceId = rvnServiceId;
	}

	public Short getStockModelType() {
		return stockModelType;
	}

	public void setStockModelType(Short stockModelType) {
		this.stockModelType = stockModelType;
	}

	public Long getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getTelecomServiceName() {
		return telecomServiceName;
	}

	public void setTelecomServiceName(String telecomServiceName) {
		this.telecomServiceName = telecomServiceName;
	}

	public Short getTransceiver() {
		return transceiver;
	}

	public void setTransceiver(Short transceiver) {
		this.transceiver = transceiver;
	}

	public int getTypeIndex() {
		return typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Long getVat() {
		return vat;
	}

	public void setVat(Long vat) {
		this.vat = vat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public List<ApStockSupplyInfo> getLstAppApStockSupplyInfo() {
        return lstAppApStockSupplyInfo;
    }

    public void setLstAppApStockSupplyInfo(List<ApStockSupplyInfo> lstAppApStockSupplyInfo) {
        this.lstAppApStockSupplyInfo = lstAppApStockSupplyInfo;
    }

    public Long getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(Long requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Long getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Long receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }


	@Override
	public String toString() {
		return name;
	}
}
