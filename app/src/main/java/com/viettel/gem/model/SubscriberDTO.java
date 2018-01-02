package com.viettel.gem.model;

import android.content.Context;
import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StockNumberDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.PrepaidMonthBO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Root(name = "SubscriberDTO", strict = false)
public class SubscriberDTO implements Serializable {

    @Element(name = "isCusExisted", required = false)
    private boolean isCusExisted;

    public boolean isCusExisted() {
        return isCusExisted;
    }

    public void setCusExisted(boolean cusExisted) {
        isCusExisted = cusExisted;
    }

    @Element(name = "checkDebit", required = false)
    private boolean checkDebit;
    @Element(name = "existIdNo", required = false)
    private boolean existIdNo;
    @Element(name = "accountDTO", required = false)
    private transient AccountDTO accountDTO;
    @Element(name = "addDeployInfo", required = false)
    private boolean addDeployInfo;
    @Element(name = "autoActive900", required = false)
    private boolean autoActive900;
    @Element(name = "blockSuccess", required = false)
    private boolean blockSuccess;
    @Element(name = "businessAccepted", required = false)
    private boolean businessAccepted;
    @Element(name = "changeAddress", required = false)
    private boolean changeAddress;
    @Element(name = "chargesAvg", required = false)
    private String chargesAvg;
    @Element(name = "chargesMonthly", required = false)
    private String chargesMonthly;
    @Element(name = "connectedExtraService", required = false)
    private boolean connectedExtraService;
    @Element(name = "contractDTOInput", required = false)
    private String contractDTOInput;
    @Element(name = "contractId", required = false)
    private String contractId;
    @Element(name = "currentPrepaidMonthBO", required = false)
    private String currentPrepaidMonthBO;
    @Element(name = "customLabel", required = false)
    private String customLabel;

    @Element(name = "account", required = false)
    private String account;
    @Element(name = "accountId", required = false)
    private Long accountId;
    @Element(name = "actStatus", required = false)
    private String actStatus;
    @Element(name = "bundleConfigID", required = false)
    private Long bundleConfigID;
    @Element(name = "custId", required = false)
    private Long custId;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "main", required = false)
    private String main;
    @Element(name = "offerId", required = false)
    private Long offerId;
    @Element(name = "payType", required = false)
    private String payType;
    @Element(name = "productCode", required = false)
    private String productCode;
    @Element(name = "shortNumber", required = false)
    private String shortNumber;
    @Element(name = "subId", required = false)
    private Long subId;
    @Element(name = "telecomServiceId", required = false)
    private Long telecomServiceId;
    @Element(name = "type", required = false)
    private Long type;
    @Element(name = "activeDatetime", required = false)
    private String activeDatetime;
    @Element(name = "imsi", required = false)
    private String imsi;
    @Element(name = "isInfoComplete", required = false)
    private Long isInfoComplete;
    @Element(name = "orgProductCode", required = false)
    private String orgProductCode;
    @Element(name = "regTypeId", required = false)
    private Long regTypeId;
    @Element(name = "shopCode", required = false)
    private String shopCode;
    @Element(name = "startMoney", required = false)
    private Long startMoney;
    @Element(name = "status", required = false)
    private String status;
    @Element(name = "updateUser", required = false)
    private String updateUser;
    @Element(name = "createDatetime", required = false)
    private String createDatetime;
    @Element(name = "createUser", required = false)
    private String createUser;
    @Element(name = "accountDTOForInput", required = false)
    private transient AccountDTO accountDTOForInput;
    @Element(name = "customerDTOInput", required = false)
    private CustomerDTO customerDTOInput;
    @Element(name = "customerDTO", required = false)
    private CustomerDTO customerDTO;
    @Element(name = "subInfrastructureDTO", required = false)
    private transient SubInfrastructureDTO subInfrastructureDTO;
    @Element(name = "technology", required = false)
    private String technology;
    @Element(name = "technologyOld", required = false)
    private String technologyOld;
    @Element(name = "technologyText", required = false)
    private String technologyText;
    @Element(name = "prepaidMonthBO", required = false)
    private transient PrepaidMonthBO prepaidMonthBO;
    @Element(name = "actStatusText", required = false)
    private String actStatusText;
    @Element(name = "actStatusView", required = false)
    private String actStatusView;
    @Element(name = "promotionCode", required = false)
    private String promotionCode;
    @Element(name = "promotionName", required = false)
    private String promotionName;
    @Element(name = "normalIsdn", required = false)
    private boolean normalIsdn;
    @ElementList(name = "lstProductSpecCharDTO", entry = "lstProductSpecCharDTO", required = false, inline = true)
    private List<ProductSpecCharDTO> lstProductSpecCharDTO;
    @Element(name = "productName", required = false)
    private String productName;
    private String subTypeName;
    @Element(name = "subType", required = false)
    private String subType;
    private String accountPPPOE;
    private String passwordPPPOE;
    @Element(name = "rawData", required = false)
    private String rawData;
    @Element(name = "cdtCode", required = false)
    private String cdtCode;
    @Element(name = "serviceType", required = false)
    private String serviceType;
    @Element(name = "serviceTypeName", required = false)
    private String serviceTypeName;
    @Element(name = "serial", required = false)
    private String serial;
    @Element(name = "hasVerifiedOwner", required = false)
    private boolean hasVerifiedOwner;
    @Element(name = "markNotOwner", required = false)
    private boolean markNotOwner;
    @Element(name = "actionAuditId", required = false)
    private String actionAuditId;
    @Element(name = "telecomServiceName", required = false)
    private String telecomServiceName;
    @Element(name = "staDatetime", required = false)
    private String staDatetime;
    @Element(name = "sim4G", required = false)
    private boolean sim4G;

    @Element(name = "requestType", required = false)
    private String requestType;
    @Element(name = "newSerialChangeSim", required = false)
    private String newSerialChangeSim;
    @Element(name = "oldSerialChangeSim", required = false)
    private String oldSerialChangeSim;

    private transient PromotionTypeBeans promotion;
    private transient ArrayList<ProductOfferTypeDTO> lstProductType;
    private boolean checkStock = false;
    private boolean isGetCDT = false;
    private transient PaymentPrePaidPromotionBeans cuocDongTruoc;
    private String customerOrderDetailId;
    private transient List<StockNumberDTO> lstIsdn;
    private transient ArrayList<PromotionTypeBeans> lstPromotion;
    private String errorCode = "";
    private String description = "";
    private String ip = "";
    private transient HTHMBeans hthm;
    private transient ReasonPledgeDTO reasonPledgeDTO;
    private List<String> lstFilePath;

    public boolean isCheck() {
        return false;
    }

    private transient List<SubPromotionDTO> lstSubPromotionDTO = new ArrayList<SubPromotionDTO>();
    private transient List<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO = new ArrayList<SubPromotionPrepaidDTO>();
    private List<OptionSetValueDTO> lstQuota = new ArrayList<OptionSetValueDTO>();
    private ReasonPledgeDTO deposit;
    private String deployModel;
    private String quota;
    private List<ProfileUpload> lstProfile;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getNewSerialChangeSim() {
        return newSerialChangeSim;
    }

    public void setNewSerialChangeSim(String newSerialChangeSim) {
        this.newSerialChangeSim = newSerialChangeSim;
    }

    public String getOldSerialChangeSim() {
        return oldSerialChangeSim;
    }

    public void setOldSerialChangeSim(String oldSerialChangeSim) {
        this.oldSerialChangeSim = oldSerialChangeSim;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getCdtCode() {
        return cdtCode;
    }

    public void setCdtCode(String cdtCode) {
        this.cdtCode = cdtCode;
    }

    private HashMap<String, ArrayList<ProductPackageFeeDTO>> hashmapProductPakage = new HashMap<>();

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public HashMap<String, ArrayList<ProductPackageFeeDTO>> getHashmapProductPakage() {
        if (hashmapProductPakage == null) {
            hashmapProductPakage = new HashMap<>();
        }
        return hashmapProductPakage;
    }

    public void setHashmapProductPakage(HashMap<String, ArrayList<ProductPackageFeeDTO>> hashmapProductPakage) {
        this.hashmapProductPakage = hashmapProductPakage;
    }

    @Element(name = "telecomServiceAlias", required = false)
    private String telecomServiceAlias;

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    @Element(name = "custType", required = false)
    private String custType;

    public List<SubscriberDTO> getListExtraServices() {
        if (listExtraServices == null) {
            listExtraServices = new ArrayList<SubscriberDTO>();
        }
        return listExtraServices;
    }

    public void setListExtraServices(List<SubscriberDTO> listExtraServices) {
        this.listExtraServices = listExtraServices;
    }

    private List<SubscriberDTO> listExtraServices;
    @Element(name = "actionCode", required = false)
    private String actionCode;
    @Element(name = "blockMode", required = false)
    private String blockMode;

    public String getTelecomServiceAlias() {
        return telecomServiceAlias;
    }

    @ElementList(name = "lstSubInfrastructureDTO", entry = "lstSubInfrastructureDTO", required = false, inline = true)
    private ArrayList<SubInfrastructureDTO> lstSubInfrastructureDTO;

    public ArrayList<SubInfrastructureDTO> getLstSubInfrastructureDTO() {
        return lstSubInfrastructureDTO;
    }

    public void setLstSubInfrastructureDTO(ArrayList<SubInfrastructureDTO> lstSubInfrastructureDTO) {
        this.lstSubInfrastructureDTO = lstSubInfrastructureDTO;
    }

    public void setTelecomServiceAlias(String telecomServiceAlias) {
        this.telecomServiceAlias = telecomServiceAlias;
    }

    public List<ProfileUpload> getLstProfile() {
        return lstProfile;
    }

    public void setLstProfile(List<ProfileUpload> lstProfile) {
        this.lstProfile = lstProfile;
    }

    public List<OptionSetValueDTO> getLstQuota() {
        return lstQuota;
    }

    public void setLstQuota(List<OptionSetValueDTO> lstQuota) {
        this.lstQuota = lstQuota;
    }

    public ReasonPledgeDTO getDeposit() {
        return deposit;
    }

    public void setDeposit(ReasonPledgeDTO deposit) {
        this.deposit = deposit;
    }

    public boolean isSim4G() {
        return sim4G;
    }

    public void setSim4G(boolean sim4g) {
        sim4G = sim4g;
    }

    public String getStaDatetime() {
        return staDatetime;
    }

    public void setStaDatetime(String staDatetime) {
        this.staDatetime = staDatetime;
    }

    public boolean isMarkNotOwner() {
        return markNotOwner;
    }

    public void setMarkNotOwner(boolean markNotOwner) {
        this.markNotOwner = markNotOwner;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public List<ProductSpecCharDTO> getLstProductSpecCharDTO() {
        return lstProductSpecCharDTO;
    }

    public void setLstProductSpecCharDTO(List<ProductSpecCharDTO> lstProductSpecCharDTO) {
        this.lstProductSpecCharDTO = lstProductSpecCharDTO;
    }

    public boolean isNormalIsdn() {
        return normalIsdn;
    }

    public void setNormalIsdn(boolean normalIsdn) {
        this.normalIsdn = normalIsdn;
    }

    public List<SubPromotionDTO> getLstSubPromotionDTO() {
        return lstSubPromotionDTO;
    }

    public void setLstSubPromotionDTO(List<SubPromotionDTO> lstSubPromotionDTO) {
        this.lstSubPromotionDTO = lstSubPromotionDTO;
    }

    public List<SubPromotionPrepaidDTO> getLstSubPromotionPrepaidDTO() {
        return lstSubPromotionPrepaidDTO;
    }

    public void setLstSubPromotionPrepaidDTO(List<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO) {
        this.lstSubPromotionPrepaidDTO = lstSubPromotionPrepaidDTO;
    }

    public boolean isHasVerifiedOwner() {
        return hasVerifiedOwner;
    }

    public void setHasVerifiedOwner(boolean hasVerifiedOwner) {
        this.hasVerifiedOwner = hasVerifiedOwner;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }


    public List<String> getLstFilePath() {
        return lstFilePath;
    }

    public void setLstFilePath(List<String> lstFilePath) {
        this.lstFilePath = lstFilePath;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getActStatusView() {
        return actStatusView;
    }

    public void setActStatusView(String actStatusView) {
        this.actStatusView = actStatusView;
    }

    public String getActStatusText() {
        return actStatusText;
    }

    public void setActStatusText(String actStatusText) {
        this.actStatusText = actStatusText;
    }

    public PrepaidMonthBO getPrepaidMonthBO() {
        return prepaidMonthBO;
    }

    public void setPrepaidMonthBO(PrepaidMonthBO prepaidMonthBO) {
        this.prepaidMonthBO = prepaidMonthBO;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnologyOld() {
        return technologyOld;
    }

    public void setTechnologyOld(String technologyOld) {
        this.technologyOld = technologyOld;
    }

    public String getTechnologyText() {
        return technologyText;
    }

    public void setTechnologyText(String technologyText) {
        this.technologyText = technologyText;
    }

    public SubInfrastructureDTO getSubInfrastructureDTO() {
        return subInfrastructureDTO;
    }

    public void setSubInfrastructureDTO(
            SubInfrastructureDTO subInfrastructureDTO) {
        this.subInfrastructureDTO = subInfrastructureDTO;
    }

    public String getAccountPPPOE() {
        return accountPPPOE;
    }

    public void setAccountPPPOE(String accountPPPOE) {
        this.accountPPPOE = accountPPPOE;
    }

    public String getPasswordPPPOE() {
        return passwordPPPOE;
    }

    public void setPasswordPPPOE(String passwordPPPOE) {
        this.passwordPPPOE = passwordPPPOE;
    }

    public ReasonPledgeDTO getReasonPledgeDTO() {
        return reasonPledgeDTO;
    }

    public void setReasonPledgeDTO(ReasonPledgeDTO reasonPledgeDTO) {
        this.reasonPledgeDTO = reasonPledgeDTO;
    }

    public HTHMBeans getHthm() {
        return hthm;
    }

    public void setHthm(HTHMBeans hthm) {
        this.hthm = hthm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<PromotionTypeBeans> getLstPromotion() {
        return lstPromotion;
    }

    public void setLstPromotion(ArrayList<PromotionTypeBeans> lstPromotion) {
        this.lstPromotion = lstPromotion;
    }

    public List<StockNumberDTO> getLstIsdn() {
        return lstIsdn;
    }

    public void setLstIsdn(List<StockNumberDTO> lstIsdn) {
        this.lstIsdn = lstIsdn;
    }

    public String getCustomerOrderDetailId() {
        return customerOrderDetailId;
    }

    public void setCustomerOrderDetailId(String customerOrderDetailId) {
        this.customerOrderDetailId = customerOrderDetailId;
    }

    // public String getRegTypeName() {
    // return regTypeName;
    // }
    //
    // public void setRegTypeName(String regTypeName) {
    // this.regTypeName = regTypeName;
    // }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public PaymentPrePaidPromotionBeans getCuocDongTruoc() {
        return cuocDongTruoc;
    }

    public void setCuocDongTruoc(PaymentPrePaidPromotionBeans cuocDongTruoc) {
        this.cuocDongTruoc = cuocDongTruoc;
    }

    public boolean isGetCDT() {
        return isGetCDT;
    }

    public void setGetCDT(boolean isGetCDT) {
        this.isGetCDT = isGetCDT;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public boolean isCheckStock() {
        return checkStock;
    }

    public void setCheckStock(boolean checkStock) {
        this.checkStock = checkStock;
    }

    public ArrayList<ProductOfferTypeDTO> getLstProductType() {
        return lstProductType;
    }

    public void setLstProductType(ArrayList<ProductOfferTypeDTO> lstProductType) {
        this.lstProductType = lstProductType;
    }

    // public String getRegTypeCode() {
    // return regTypeCode;
    // }
    //
    // public void setRegTypeCode(String regTypeCode) {
    // this.regTypeCode = regTypeCode;
    // }

    public PromotionTypeBeans getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionTypeBeans promotion) {
        this.promotion = promotion;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public CustomerDTO getCustomerDTOInput() {
        return customerDTOInput;
    }

    public void setCustomerDTOInput(CustomerDTO customerDTOInput) {
        this.customerDTOInput = customerDTOInput;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public AccountDTO getAccountDTOForInput() {
        return accountDTOForInput;
    }

    public void setAccountDTOForInput(AccountDTO accountDTOForInput) {
        this.accountDTOForInput = accountDTOForInput;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getActiveDatetime() {
        return activeDatetime;
    }

    public void setActiveDatetime(String activeDatetime) {
        this.activeDatetime = activeDatetime;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Long getIsInfoComplete() {
        return isInfoComplete;
    }

    public void setIsInfoComplete(Long isInfoComplete) {
        this.isInfoComplete = isInfoComplete;
    }

    public String getOrgProductCode() {
        return orgProductCode;
    }

    public void setOrgProductCode(String orgProductCode) {
        this.orgProductCode = orgProductCode;
    }

    public Long getRegTypeId() {
        return regTypeId;
    }

    public void setRegTypeId(Long regTypeId) {
        this.regTypeId = regTypeId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Long startMoney) {
        this.startMoney = startMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public Long getBundleConfigID() {
        return bundleConfigID;
    }

    public void setBundleConfigID(Long bundleConfigID) {
        this.bundleConfigID = bundleConfigID;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getDeployModel() {
        return deployModel;
    }

    public void setDeployModel(String deployModel) {
        this.deployModel = deployModel;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        if (!CommonActivity.isNullOrEmpty(account)) {
            return account;
        } else {
            return isdn;
        }

    }

    public String getCuocDongTruocKey() {
        return promotion.getPromProgramCode() + "-" + getProductCode() + "-";
    }

    public String buildSubXml(Context context, String root,
                              ResultSurveyOnlineForBccs2Form survey, AccountDTO account,
                              CustIdentityDTO cus, AreaObj area, String oldSubId, String actionCode) throws Exception {
        StringBuilder xml = new StringBuilder();
        xml.append("<").append(root).append(">");

        // them luong dat coc
        if (!CommonActivity.isNullOrEmpty(getDeposit()) && !"null".equals(getDeposit())) {
            xml.append("<deposit>")
                    .append(getDeposit().getNumMoney())
                    .append("</deposit>");
            xml.append("<roleSaveDeposit>")
                    .append(true)
                    .append("</roleSaveDeposit>");
            xml.append("<subDepositDTO>");
            xml.append("<deposit>")
                    .append(getDeposit().getNumMoney())
                    .append("</deposit>");
            xml.append("<reasonType>")
                    .append("3")
                    .append("</reasonType>");
            xml.append("<numOfMonth>")
                    .append(getDeposit().getNumMonth())
                    .append("</numOfMonth>");
//			<reasonType>3</reasonType>
            xml.append("</subDepositDTO>");
        }


        if (!CommonActivity.isNullOrEmpty(getDeployModel())) {
            xml.append("<gponInfoDTO>");

//			groupCode, groupId
            if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.groupsDTO)) {
                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.groupsDTO.getCode())) {
                    xml.append("<groupCode>")
                            .append(TabThueBaoHopDongManager.instance.groupsDTO.getCode())
                            .append("</groupCode>");
                    xml.append("<groupId>")
                            .append(TabThueBaoHopDongManager.instance.groupsDTO.getGroupId())
                            .append("</groupId>");
                }
            }


            xml.append("<deployModel>")
                    .append(getDeployModel())
                    .append("</deployModel>");
            xml.append("</gponInfoDTO>");
        }
        if (!CommonActivity.isNullOrEmpty(getQuota())) {

            xml.append("<subExtDTO>")
                    .append("<extValue>").append(getQuota()).append("</extValue>")
                    .append("</subExtDTO>");
        }


//		xml.append("<activeDatetime>" + "").append(StringUtils.convertDateToString(cus
//				.get())).append("T00:00:00Z");
//		xml.append("</activeDatetime>");
        xml.append("<subId>")
                .append(getSubId())
                .append("</subId>");

        xml.append("<isSelected>")
                .append(true)
                .append("</isSelected>");


        if (promotion != null) {
            xml.append("<promotionCode>")
                    .append(promotion.getPromProgramCode())
                    .append("</promotionCode>");
        }
        xml.append("<ipStatic>")
                .append(getIp())
                .append("</ipStatic>");
        xml.append("<contractId>").append(account.getAccountId())
                .append("</contractId>");
        xml.append("<regType>").append(hthm.getCode()).append("</regType>");
        xml.append("<subType>").append(subType).append("</subType>");
        xml.append("<serviceType>").append(serviceType)
                .append("</serviceType>");
        xml.append("<technology>").append(survey.getTechNology(context))
                .append("</technology>");
        xml.append("<reasonId>").append(hthm.getReasonId())
                .append("</reasonId>");
        xml.append("<regTypeId>").append(hthm.getReasonId())
                .append("</regTypeId>");
        // Thong tin hop dong
        xml.append("<accountDTOForInput>");
        xml.append(account.toXml());
        if ("02".equals(account.getPayMethod())
                || "03".equals(account.getPayMethod())) {
            // xml.append("<accountBankDTO>");
            xml.append(account.getAccountBank().toXmlAcc());
            // xml.append("</accountBankDTO>");
        }
        xml.append("</accountDTOForInput>");
        if ("02".equals(account.getPayMethod())
                || "03".equals(account.getPayMethod())) {
            // xml.append("<accountBankDTO>");
            xml.append(account.getAccountBank().toXml());
            // xml.append("</accountBankDTO>");
        }
        xml.append("<customerDTOInput>");
        // them dia chi thong bao cuoc cho phan hop dong

        xml.append("<status>").append("1");
        xml.append("</status>");

        xml.append("<custContactDTO>");
        xml.append("<billAddress>").append(account.getAddressPrint());
        xml.append("</billAddress>");
        xml.append("<email>").append(account.geteMail());
        xml.append("</email>");
        xml.append("<telephone>").append(account.getTelMobile());
        xml.append("</telephone>");
        xml.append("</custContactDTO>");
        if (cus.getCustId() != null) {
            xml.append("<custId>" + "").append(cus.getCustId());
            xml.append("</custId>");
            xml.append("<isNewCustomer>" + false);
            xml.append("</isNewCustomer>");
        } else {
            if (cus.getCustomer().getCustId() != null) {
                xml.append("<custId>" + "").append(cus.getCustomer().getCustId());
                xml.append("</custId>");
                xml.append("<isNewCustomer>" + false);
                xml.append("</isNewCustomer>");
            } else {
                xml.append("<custId>").append("null");
                xml.append("</custId>");
                xml.append("<updateCustIdentity>" + true);
                xml.append("</updateCustIdentity>");
                xml.append("<isNewCustomer>" + true);
                xml.append("</isNewCustomer>");
                xml.append("<name>" + "").append(StringUtils.escapeHtml(cus.getCustomer().getName()));
                xml.append("</name>");
                xml.append("<custType>" + "").append(cus.getCustomer().getCustType());
                xml.append("</custType>");
                xml.append("<custTypeDTO>");
                xml.append("<custType>" + "").append(cus.getCustomer().getCustType());
                xml.append("</custType>");
                xml.append("<groupType>" + "").append(cus.getGroupType());
                xml.append("</groupType>");
                xml.append("</custTypeDTO>");
                // truong hop co nhieu loai giay to
                if (cus.getCustomer().getListCustIdentity() != null
                        && cus.getCustomer().getListCustIdentity().size() > 1) {
                    for (CustIdentityDTO item : cus.getCustomer()
                            .getListCustIdentity()) {
                        xml.append("<listCustIdentity>");
                        xml.append("<idNo>" + "").append(item.getIdNo());
                        xml.append("</idNo>");
                        xml.append("<idType>" + "").append(item.getIdType());
                        xml.append("</idType>");
                        if (cus.getCustomer().getCustId() != null) {
                            xml.append("<idIssueDate>" + "").append(cus.getIdIssueDate());
                            xml.append("</idIssueDate>");
                        } else {
                            xml.append("<idIssueDate>" + "").append(StringUtils.convertDateToString(cus
                                    .getIdIssueDate())).append("T00:00:00Z");
                            xml.append("</idIssueDate>");
                        }

                        xml.append("<idIssuePlace>" + "").append(StringUtils.escapeHtml(cus.getIdIssuePlace()));
                        xml.append("</idIssuePlace>");
                        // }

                        xml.append("<required>" + true);
                        xml.append("</required>");

                        xml.append("</listCustIdentity>");
                    }
                } else {
                    xml.append("<listCustIdentity>");
                    xml.append("<idNo>" + "").append(cus.getIdNo());
                    xml.append("</idNo>");
                    xml.append("<idType>" + "").append(cus.getIdType());
                    xml.append("</idType>");

                    if (cus.getCustomer().getCustId() != null) {
                        xml.append("<idIssueDate>" + "").append(StringUtils.escapeHtml(cus.getIdIssueDate()));
                        xml.append("</idIssueDate>");
                    } else {
                        xml.append("<idIssueDate>" + "").append(StringUtils.convertDateToString(cus
                                .getIdIssueDate())).append("T00:00:00Z");
                        xml.append("</idIssueDate>");
                    }

                    xml.append("<idIssuePlace>" + "").append(StringUtils.escapeHtml(cus.getIdIssuePlace()));
                    xml.append("</idIssuePlace>");
                    // }
                    xml.append("<required>" + true);
                    xml.append("</required>");

                    xml.append("</listCustIdentity>");

                    xml.append("<sex>" + "").append(cus.getCustomer().getSex());
                    xml.append("</sex>");
                }

                // ngay sinh/ngay thanh lap

                if (cus.getCustId() != null
                        || cus.getCustomer().getCustId() != null) {
                    xml.append("<birthDate>").append(cus.getCustomer().getBirthDate());
                    xml.append("</birthDate>");
                } else {
                    xml.append("<birthDate>").append(StringUtils.convertDateToString(cus.getCustomer()
                            .getBirthDate())).append("T00:00:00Z");
                    xml.append("</birthDate>");
                }

                xml.append("<custAdd>");
                xml.append("<province>");
                xml.append("<code>" + "").append(CommonActivity.replaceNull(cus.getCustomer().getProvince()));
                xml.append("</code>");
                xml.append("</province>");

                xml.append("<district>");
                xml.append("<code>" + "").append(CommonActivity.replaceNull(cus.getCustomer().getDistrict()));
                xml.append("</code>");
                xml.append("</district>");

                xml.append("<precinct>");
                xml.append("<code>" + "").append(cus.getCustomer().getPrecinct());
                xml.append("</code>");
                xml.append("</precinct>");

                xml.append("<streetBlock>");
                xml.append("<code>" + "").append(CommonActivity.replaceNull(cus.getCustomer().getStreetBlock()));
                xml.append("</code>");
                xml.append("</streetBlock>");

                xml.append("<areaCode>" + "").append(CommonActivity.replaceNull(cus.getCustomer().getAreaCode()));
                xml.append("</areaCode>");
                xml.append("<fullAddress>" + "").append(StringUtils
                        .escapeHtml(cus.getCustomer().getAddress()));
                xml.append("</fullAddress>");

                xml.append("</custAdd>");

                xml.append("<province>" + "").append(cus.getCustomer().getProvince());
                xml.append("</province>");
                xml.append("<district>" + "").append(cus.getCustomer().getDistrict());
                xml.append("</district>");
                xml.append("<precinct>" + "").append(cus.getCustomer().getPrecinct());
                xml.append("</precinct>");
                xml.append("<streetBlock>" + "").append(CommonActivity.replaceNull(StringUtils.escapeHtml(cus.getCustomer()
                        .getStreetBlock())));
                xml.append("</streetBlock>");

                if (!CommonActivity.isNullOrEmpty(cus.getCustomer().getHome())) {
                    xml.append("<home>" + "").append(CommonActivity.replaceNull(StringUtils.escapeHtml(cus.getCustomer()
                            .getHome())));
                    xml.append("</home>");
                }

                if (!CommonActivity.isNullOrEmpty(StringUtils.escapeHtml(CommonActivity.replaceNull(cus
                        .getCustomer().getStreetName())))) {
                    xml.append("<streetName>" + "").append(cus.getCustomer().getStreet());
                    xml.append("</streetName>");
                }

                xml.append("<areaCode>" + "").append(CommonActivity.replaceNull(cus.getCustomer().getAreaCode()));
                xml.append("</areaCode>");
                xml.append("<address>" + "").append(StringUtils
                        .escapeHtml(cus.getCustomer().getAddress()));
                xml.append("</address>");

                if (!CommonActivity.isNullOrEmpty(cus.getCustomer()
                        .getNationality())) {
                    xml.append("<nationality>" + "").append(StringUtils.escapeHtml(cus.getCustomer()
                            .getNationality()));
                    xml.append("</nationality>");
                }

            }
        }
        // thong tin kh dai dien ca nhan
        if (account.getRefCustomer() != null) {
            xml.append("<representativeCustContract>");
            if (account.getRefCustomer().getCustId() != null) {
                xml.append("<custId>" + "").append(account.getRefCustomer().getCustId());
                xml.append("</custId>");
                xml.append("<isNewCustomer>" + false);
                xml.append("</isNewCustomer>");
            } else {
                xml.append("<updateCustIdentity>" + true);
                xml.append("</updateCustIdentity>");
                xml.append("<isNewCustomer>" + true);
                xml.append("</isNewCustomer>");
                xml.append("<newRepCustomer>" + true);
                xml.append("</newRepCustomer>");

            }

            xml.append("<name>" + "").append(StringUtils
                    .escapeHtml(account.getRefCustomer().getName()));
            xml.append("</name>");

            xml.append("<custType>" + "").append(account.getRefCustomer().getCustType());
            xml.append("</custType>");

            xml.append("<custTypeDTO>");
            xml.append("<custType>" + "").append(account.getRefCustomer().getCustType());
            xml.append("</custType>");
            xml.append("<groupType>" + "" + 1);
            xml.append("</groupType>");
            xml.append("</custTypeDTO>");

            if (account.getRefCustomer().getListCustIdentity() != null
                    && account.getRefCustomer().getListCustIdentity().size() > 0) {
                xml.append("<listCustIdentity>");
                xml.append("<idNo>" + "").append(account.getRefCustomer().getListCustIdentity().get(0)
                        .getIdNo());
                xml.append("</idNo>");
                xml.append("<idType>" + "").append(account.getRefCustomer().getListCustIdentity().get(0)
                        .getIdType());
                xml.append("</idType>");

                if (account.getRefCustomer().getCustId() != null) {
                    xml.append("<idIssueDate>" + "").append(account.getRefCustomer().getListCustIdentity()
                            .get(0).getIdIssueDate());
                    xml.append("</idIssueDate>");
                } else {
                    xml.append("<idIssueDate>" + "").append(StringUtils.convertDateToString(account
                            .getRefCustomer().getListCustIdentity()
                            .get(0).getIdIssueDate())).append("T00:00:00Z");
                    xml.append("</idIssueDate>");
                }

                xml.append("<idIssuePlace>" + "").append(StringUtils
                        .escapeHtml(account.getRefCustomer()
                                .getListCustIdentity().get(0)
                                .getIdIssuePlace()));
                xml.append("</idIssuePlace>");
                xml.append("<required>" + true);
                xml.append("</required>");

                xml.append("</listCustIdentity>");
            }

            xml.append("<sex>" + "").append(account.getRefCustomer().getSex());
            xml.append("</sex>");

            // ngay sinh/ngay thanh lap
            if (account.getRefCustomer().getCustId() != null) {
                xml.append("<birthDate>" + "").append(account.getRefCustomer().getBirthDate());
                xml.append("</birthDate>");
            } else {
                xml.append("<birthDate>" + "").append(StringUtils.convertDateToString(account
                        .getRefCustomer().getBirthDate())).append("T00:00:00Z");
                xml.append("</birthDate>");
            }

            xml.append("<custAdd>");
            xml.append("<province>");
            xml.append("<code>" + "").append(account.getRefCustomer().getProvince());
            xml.append("</code>");
            xml.append("</province>");

            xml.append("<district>");
            xml.append("<code>" + "").append(account.getRefCustomer().getDistrict());
            xml.append("</code>");
            xml.append("</district>");

            xml.append("<precinct>");
            xml.append("<code>" + "").append(account.getRefCustomer().getPrecinct());
            xml.append("</code>");
            xml.append("</precinct>");

            xml.append("<streetBlock>");
            xml.append("<code>" + "").append(CommonActivity.replaceNull(account.getRefCustomer().getStreetBlock()));
            xml.append("</code>");
            xml.append("</streetBlock>");

            xml.append("<areaCode>" + "").append(StringUtils.escapeHtml(account.getRefCustomer()
                    .getAreaCode()));
            xml.append("</areaCode>");
            xml.append("<fullAddress>" + "").append(account.getRefCustomer().getAddress());
            xml.append("</fullAddress>");

            xml.append("</custAdd>");

            xml.append("<province>" + "").append(account.getRefCustomer().getProvince());
            xml.append("</province>");
            xml.append("<district>" + "").append(account.getRefCustomer().getDistrict());
            xml.append("</district>");
            xml.append("<precinct>" + "").append(account.getRefCustomer().getPrecinct());
            xml.append("</precinct>");
            xml.append("<streetBlock>" + "").append(CommonActivity.replaceNull(account.getRefCustomer().getStreetBlock()));
            xml.append("</streetBlock>");
            if (!CommonActivity.isNullOrEmpty(account.getRefCustomer()
                    .getHome())) {
                xml.append("<home>" + "").append(StringUtils.escapeHtml(account.getRefCustomer()
                        .getHome()));
                xml.append("</home>");
            }

            if (!CommonActivity.isNullOrEmpty(account.getRefCustomer()
                    .getStreetName())) {
                xml.append("<streetName>" + "").append(StringUtils.escapeHtml(account.getRefCustomer()
                        .getStreetName()));
                xml.append("</streetName>");
            }

            xml.append("<areaCode>" + "").append(account.getRefCustomer().getAreaCode());
            xml.append("</areaCode>");
            xml.append("<address>" + "").append(StringUtils.escapeHtml(account.getRefCustomer()
                    .getAddress()));
            xml.append("</address>");
            xml.append("<nationality>" + "").append(StringUtils.escapeHtml(account.getRefCustomer()
                    .getNationality()));
            xml.append("</nationality>");
            xml.append("</representativeCustContract>");
        }
        xml.append("</customerDTOInput>");
        if (cuocDongTruoc != null) {
            xml.append("<prepaidMonthBO>");
            xml.append("<prepaidValue>").append(cuocDongTruoc.getPrePaidCode());
            xml.append("</prepaidValue>");
            xml.append("<prepaidId>").append(cuocDongTruoc.getId());
            xml.append("</prepaidId>");
            xml.append("</prepaidMonthBO>");
        }
        xml.append("<vasList>");
        xml.append("</vasList>");

        xml.append("<vasListConnect>");
        xml.append("</vasListConnect>");
        xml.append("<isdn>").append(isdn);
        xml.append("</isdn>");


        xml.append("<productCode>").append(productCode);
        xml.append("</productCode>");
        xml.append("<offerId>").append(offerId);
        xml.append("</offerId>");
        xml.append("<payType>" + "1");
        xml.append("</payType>");
        xml.append("<telecomServiceId>").append(telecomServiceId);
        xml.append("</telecomServiceId>");
        xml.append("<customerOrderDetailDTO>");

        // BO SUNG LUONG HOTLINE
        if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.requestExtId)) {
            xml.append("<requestExtId>" + "" + TabThueBaoHopDongManager.instance.requestExtId);
            xml.append("</requestExtId>");
        }


        if (!CommonActivity.isNullOrEmpty(area) && !CommonActivity.isNullOrEmpty(area.getNote())) {
            xml.append("<description>" + area.getNote());
            xml.append("</description>");
        }

        if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.accountGline)) {
            xml.append("<gponGroupAccount>" + "" + TabThueBaoHopDongManager.instance.accountGline);
            xml.append("</gponGroupAccount>");
        }


        xml.append("<custOrderDetailId>");
        xml.append(customerOrderDetailId);
        xml.append("</custOrderDetailId>");

        if (!CommonActivity.isNullOrEmpty(oldSubId) && !CommonActivity.isNullOrEmpty(actionCode)) {
            xml.append("<oldSubId>");
            xml.append(oldSubId);
            xml.append("</oldSubId>");
            xml.append("<actionCode>");
            xml.append(actionCode);
            xml.append("</actionCode>");
        }

        xml.append("</customerOrderDetailDTO>");
        xml.append("<subInfrastructureDTO>");

        xml.append("<accountGline>");
//		xml.append(TabThueBaoHopDongManager.instance.accountGline);
        xml.append("</accountGline>");

        xml.append("<cableBoxCode>");
        xml.append(survey.getConnectorCode());
        xml.append("</cableBoxCode>");

        xml.append("<cableBoxId>");
        xml.append(survey.getConnectorId());
        xml.append("</cableBoxId>");

        xml.append("<actionId>");
        xml.append("00");
        xml.append("</actionId>");

        xml.append("<stationCode>");
        xml.append(survey.getStationCode());
        xml.append("</stationCode>");

        xml.append("<technology>").append(survey.getTechNology(context))
                .append("</technology>");

        xml.append("<address>");
        xml.append(area.getFullNamel());
        xml.append("</address>");

        xml.append("<areaCode>");
        xml.append(area.getAreaCode().replace("null", ""));
        xml.append("</areaCode>");

        xml.append("<province>");
        xml.append(area.getProvince());
        xml.append("</province>");

        xml.append("<district>");
        xml.append(area.getDistrict());
        xml.append("</district>");

        xml.append("<precinct>");
        xml.append(area.getPrecinct());
        xml.append("</precinct>");

        if (!CommonActivity.isNullOrEmpty(area.getStreet())) {
            xml.append("<streetName>");
            xml.append(StringUtils.escapeHtml(CommonActivity.replaceNull(area.getStreet())));
            xml.append("</streetName>");
        }


        xml.append("<precinct>");
        xml.append(area.getPrecinct());
        xml.append("</precinct>");

        xml.append("</subInfrastructureDTO>");

        xml.append("<deployAreaCode>");
        xml.append(CommonActivity.replaceNull(area.getAreaCode()));
        xml.append("</deployAreaCode>");
        xml.append("<areaCode>");
        xml.append(area.getProvince()).append(area.getDistrict()).append(area.getPrecinct());
        xml.append("</areaCode>");
        xml.append("<deployAddress>");
        xml.append(area.getFullNamel());
        xml.append("</deployAddress>");

        if (!CommonActivity.isNullOrEmpty(lstProductType)) {

            xml.append("<saleServicesCode>").append(lstProductType.get(0).getSaleServiceCode());
            xml.append("</saleServicesCode>");
            for (ProductOfferTypeDTO item : lstProductType) {
                xml.append("<listSubGoodsDTOs>");
                xml.append("<reclaimAmount>").append(item.getStockModel().getPrice());
                xml.append("</reclaimAmount>");

                xml.append("<reclaimPayMethod>").append(item.getStockModel().getHttt());
                xml.append("</reclaimPayMethod>");

                xml.append("<reclaimProCode>").append(item.getStockModel().getProgramMonth());
                xml.append("</reclaimProCode>");

                xml.append("<reclaimCommitmentCode>").append(item.getStockModel().getSupplyMethod());
                xml.append("</reclaimCommitmentCode>");

                if (!CommonActivity.isNullOrEmpty(item.getStockModel().getNumberMonth())) {
                    xml.append("<reclaimDatetime>").append(item.getStockModel().getNumberMonth());
                    xml.append("</reclaimDatetime>");
                }

                xml.append("<subGoodsKeyset>");
                xml.append(item.getProductOfferTypeId());

                xml.append("</subGoodsKeyset>");

                xml.append("<subGoodsDTOSelectd>");
                xml.append("<stockModelId>").append(item.getStockModel().getStockModelId());
                xml.append("</stockModelId>");
                xml.append("<stockTypeId>" + "").append(item.getProductOfferTypeId());
                xml.append("</stockTypeId>");
                xml.append("<stockModelName>").append(StringUtils.escapeHtml(item.getStockModel()
                        .getStockModelName()));
                xml.append("</stockModelName>");


                xml.append("<reclaimAmount>").append(item.getStockModel().getPrice());
                xml.append("</reclaimAmount>");

                xml.append("<reclaimPayMethod>").append(item.getStockModel().getHttt());
                xml.append("</reclaimPayMethod>");

                xml.append("<reclaimProCode>").append(item.getStockModel().getProgramMonth());
                xml.append("</reclaimProCode>");

                xml.append("<reclaimCommitmentCode>").append(item.getStockModel().getSupplyMethod());
                xml.append("</reclaimCommitmentCode>");
                if (!CommonActivity.isNullOrEmpty(item.getStockModel().getNumberMonth())) {
                    xml.append("<reclaimDatetime>").append(item.getStockModel().getNumberMonth());
                    xml.append("</reclaimDatetime>");
                }


                // dfdf
                xml.append("</subGoodsDTOSelectd>");
                xml.append("</listSubGoodsDTOs>");
            }

        }

        xml.append("</").append(root).append(">");
        return xml.toString();
    }

    public SubscriberDTO cloneSub() {
        SubscriberDTO sub = new SubscriberDTO();
        try {

            sub.setAccount(this.getAccount());
            sub.setAccountId(this.getAccountId());
            sub.setActStatus(this.getActStatus());
            sub.setBundleConfigID(this.getBundleConfigID());
            sub.setCustId(this.getCustId());
            sub.setIsdn(this.getIsdn());
            sub.setMain(this.getMain());
            sub.setOfferId(this.getOfferId());
            sub.setPayType(this.getPayType());
            sub.setProductCode(this.getProductCode());
            sub.setShortNumber(this.getShortNumber());
            sub.setSubId(this.getSubId());
            sub.setTelecomServiceId(this.getTelecomServiceId());
            sub.setActiveDatetime(this.getActiveDatetime());
            sub.setImsi(this.getImsi());
            sub.setIsInfoComplete(this.getIsInfoComplete());
            sub.setOrgProductCode(this.getOrgProductCode());
            sub.setRegTypeId(this.getRegTypeId());
            sub.setShopCode(this.getShopCode());
            sub.setStartMoney(this.getStartMoney());
            sub.setUpdateUser(this.getUpdateUser());
            sub.setCreateDatetime(this.getCreateDatetime());
            sub.setCreateUser(this.getCreateUser());
            sub.setAccountDTOForInput(this.getAccountDTOForInput());
            sub.setCustomerDTOInput(this.getCustomerDTOInput());
            sub.setSubInfrastructureDTO(this.getSubInfrastructureDTO());
            sub.setTechnology(this.getTechnology());
            sub.setTechnologyOld(this.getTechnologyOld());
            sub.setTechnologyText(this.getTechnologyText());
            sub.setPrepaidMonthBO(this.getPrepaidMonthBO());
            sub.setActStatusText(this.getActStatusText());
            sub.setActStatusView(this.getActStatusView());
            sub.setPromotionCode(this.getPromotionCode());
            sub.setPromotionName(this.getPromotionName());
            sub.setLstProductSpecCharDTO(this.getLstProductSpecCharDTO());
            sub.setProductName(this.getProductName());
            sub.setSubTypeName(this.getSubTypeName());
            sub.setAccountPPPOE(this.getAccountPPPOE());
            sub.setPasswordPPPOE(this.getPasswordPPPOE());
            sub.setServiceType(this.getServiceType());
            sub.setServiceTypeName(this.getServiceTypeName());
            sub.setPromotion(this.getPromotion());
            sub.setLstProductType(this.getLstProductType());
            sub.setCuocDongTruoc(this.getCuocDongTruoc());
            sub.setCustomerOrderDetailId(this.getCustomerOrderDetailId());
            sub.setLstIsdn(this.getLstIsdn());
            sub.setLstPromotion(this.getLstPromotion());
            sub.setErrorCode(this.getErrorCode());
            sub.setIp(this.getIp());
            sub.setHthm(this.getHthm());
            sub.setReasonPledgeDTO(this.getReasonPledgeDTO());
            sub.setLstFilePath(this.getLstFilePath());
            sub.setSerial(this.getSerial());
            sub.setActionAuditId(this.getActionAuditId());
            sub.setType(this.getType());
            sub.setDescription(this.getDescription());
            sub.setStatus(this.getStatus());
            sub.setSubType(this.getSubType());
            sub.setGetCDT(this.isGetCDT());
            sub.setLstProfile(this.getLstProfile());
            sub.setHashmapProductPakage(this.getHashmapProductPakage());
            sub.setDeposit(this.getDeposit());
            sub.setDeployModel(this.getDeployModel());
            sub.setQuota(this.getQuota());
            sub.setLstQuota(this.getLstQuota());
        } catch (Exception e) {
            Log.d(Constant.TAG, "Error", e);
            return new SubscriberDTO();
        }
        return sub;

    }

    public void copyFormAnotherSub(SubscriberDTO sub) {
        this.setAccount(sub.getAccount());
        this.setAccountId(sub.getAccountId());
        this.setActStatus(sub.getActStatus());
        this.setBundleConfigID(sub.getBundleConfigID());
        this.setCustId(sub.getCustId());
        this.setIsdn(sub.getIsdn());
        this.setMain(sub.getMain());
        this.setOfferId(sub.getOfferId());
        this.setPayType(sub.getPayType());
        this.setProductCode(sub.getProductCode());
        this.setShortNumber(sub.getShortNumber());
        this.setSubId(sub.getSubId());
        this.setTelecomServiceId(sub.getTelecomServiceId());
        this.setActiveDatetime(sub.getActiveDatetime());
        this.setImsi(sub.getImsi());
        this.setIsInfoComplete(sub.getIsInfoComplete());
        this.setOrgProductCode(sub.getOrgProductCode());
        this.setRegTypeId(sub.getRegTypeId());
        this.setShopCode(sub.getShopCode());
        this.setStartMoney(sub.getStartMoney());
        this.setUpdateUser(sub.getUpdateUser());
        this.setCreateDatetime(sub.getCreateDatetime());
        this.setCreateUser(sub.getCreateUser());
        this.setAccountDTOForInput(sub.getAccountDTOForInput());
        this.setCustomerDTOInput(sub.getCustomerDTOInput());
        this.setSubInfrastructureDTO(sub.getSubInfrastructureDTO());
        this.setTechnology(sub.getTechnology());
        this.setTechnologyOld(sub.getTechnologyOld());
        this.setTechnologyText(sub.getTechnologyText());
        this.setPrepaidMonthBO(sub.getPrepaidMonthBO());
        this.setActStatusText(sub.getActStatusText());
        this.setActStatusView(sub.getActStatusView());
        this.setPromotionCode(sub.getPromotionCode());
        this.setPromotionName(sub.getPromotionName());
        this.setLstProductSpecCharDTO(sub.getLstProductSpecCharDTO());
        this.setProductName(sub.getProductName());
        this.setSubTypeName(sub.getSubTypeName());
        this.setAccountPPPOE(sub.getAccountPPPOE());
        this.setPasswordPPPOE(sub.getPasswordPPPOE());
        this.setServiceType(sub.getServiceType());
        this.setServiceTypeName(sub.getServiceTypeName());
        this.setPromotion(sub.getPromotion());
        this.setLstProductType(sub.getLstProductType());
        this.setCuocDongTruoc(sub.getCuocDongTruoc());
        this.setCustomerOrderDetailId(sub.getCustomerOrderDetailId());
        this.setLstIsdn(sub.getLstIsdn());
        this.setLstPromotion(sub.getLstPromotion());
        this.setErrorCode(sub.getErrorCode());
        this.setIp(sub.getIp());
        this.setHthm(sub.getHthm());
        this.setReasonPledgeDTO(sub.getReasonPledgeDTO());
        this.setLstFilePath(sub.getLstFilePath());
        this.setSerial(sub.getSerial());
        this.setActionAuditId(sub.getActionAuditId());
        this.setType(sub.getType());
        this.setDescription(sub.getDescription());
        this.setStatus(sub.getStatus());
        this.setSubType(sub.getSubType());
        this.setGetCDT(sub.isGetCDT());
        this.setLstProfile(sub.getLstProfile());
        this.setHashmapProductPakage(sub.getHashmapProductPakage());
        this.setDeposit(sub.getDeposit());
        this.setDeployModel(sub.getDeployModel());
        this.setQuota(sub.getQuota());
        this.setLstQuota(sub.getLstQuota());
    }

    public boolean isPNService() {
        return "N".equals(this.getServiceType())
                || "P".equals(this.getServiceType());
    }

    public String getActionAuditId() {
        return actionAuditId;
    }

    public void setActionAuditId(String actionAuditId) {
        this.actionAuditId = actionAuditId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getBlockMode() {
        return blockMode;
    }

    public void setBlockMode(String blockMode) {
        this.blockMode = blockMode;
    }

    private boolean ignoreOTPCTV = false;

    public boolean getIgnoreOTPCTV() {
        return ignoreOTPCTV;
    }

    public void setIgnoreOTPCTV(boolean ignoreOTPCTV) {
        this.ignoreOTPCTV = ignoreOTPCTV;
    }
}
