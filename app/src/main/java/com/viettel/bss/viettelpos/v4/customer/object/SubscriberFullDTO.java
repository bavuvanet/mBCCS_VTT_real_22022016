package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Toancx on 10/5/2017.
 */
@Root(name = "return", strict = false)
public class SubscriberFullDTO {
    @Element(name = "account", required = false)
    private String account;
    @Element(name = "accountId", required = false)
    private Long accountId;
    @Element(name = "accountNo", required = false)
    private String accountNo;
    @Element(name = "accountPPPOE", required = false)
    private String accountPPPOE;
    @Element(name = "actStatus", required = false)
    private String actStatus;
    @Element(name = "actStatusText", required = false)
    private String actStatusText;
    @Element(name = "actStatusView", required = false)
    private String actStatusView;
    @Element(name = "actionAuditId", required = false)
    private Long actionAuditId;
    @Element(name = "autoActive900", required = false)
    private boolean autoActive900;
    @Element(name = "blockActionCode", required = false)
    private String blockActionCode;
    @Element(name = "blockSuccess", required = false)
    private boolean blockSuccess;
    @Element(name = "businessAccepted", required = false)
    private boolean businessAccepted;
    @Element(name = "channelTypeId", required = false)
    private Long channelTypeId;
    @Element(name = "cmkIsdn", required = false)
    private String cmkIsdn;
    @Element(name = "cmkPromotion", required = false)
    private String cmkPromotion;
    @Element(name = "commitmentDesc", required = false)
    private String commitmentDesc;
    @Element(name = "connectError", required = false)
    private String connectError;
    @Element(name = "connectExtraError", required = false)
    private String connectExtraError;
    @Element(name = "contractId", required = false)
    private Long contractId;
    @Element(name = "createUser", required = false)
    private String createUser;
    @Element(name = "custId", required = false)
    private Long custId;
    @Element(name = "custName", required = false)
    private String custName;
    @Element(name = "custOrderDetailId", required = false)
    private Long custOrderDetailId;
    @Element(name = "deposit", required = false)
    private Long deposit;
    @Element(name = "depositView", required = false)
    private Long depositView;
    @Element(name = "detailCommitDesc", required = false)
    private String detailCommitDesc;
    @Element(name = "devStaffId", required = false)
    private Long devStaffId;
    @Element(name = "directChangePromotion", required = false)
    private boolean directChangePromotion;
    @Element(name = "disableItem", required = false)
    private boolean disableItem;
    @Element(name = "disableOwner", required = false)
    private boolean disableOwner;
    @Element(name = "eligibleToJoinGroup", required = false)
    private boolean eligibleToJoinGroup;
    @Element(name = "errorOCS", required = false)
    private boolean errorOCS;
    @Element(name = "gponGroupAccount", required = false)
    private String gponGroupAccount;
    @Element(name = "groupId", required = false)
    private Long groupId;
    @Element(name = "groupType", required = false)
    private String groupType;
    @Element(name = "imsi", required = false)
    private String imsi;
    @Element(name = "inGroup", required = false)
    private boolean inGroup;
    @Element(name = "infrastructureType", required = false)
    private String infrastructureType;
    @Element(name = "ipGateway", required = false)
    private String ipGateway;
    @Element(name = "ipLan", required = false)
    private String ipLan;
    @Element(name = "ipRouter", required = false)
    private String ipRouter;
    @Element(name = "ipStatic", required = false)
    private String ipStatic;
    @Element(name = "ipView", required = false)
    private String ipView;
    @Element(name = "ipWan", required = false)
    private String ipWan;
    @Element(name = "isChecked", required = false)
    private boolean isChecked;
    @Element(name = "isInfoCompleted", required = false)
    private Long isInfoCompleted;
    @Element(name = "isProvince", required = false)
    private boolean isProvince;
    @Element(name = "isSelected", required = false)
    private boolean isSelected;
    @Element(name = "isSmartSIM", required = false)
    private boolean isSmartSIM;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "keepAlive", required = false)
    private Long keepAlive;
    @Element(name = "limiUsageAfterConfirm", required = false)
    private Long limiUsageAfterConfirm;
    @Element(name = "limitUsage", required = false)
    private Long limitUsage;
    @Element(name = "limitUsageNew", required = false)
    private Long limitUsageNew;
    @Element(name = "limitUsageTmp", required = false)
    private Long limitUsageTmp;
    @Element(name = "localSpeed", required = false)
    private Long localSpeed;
    @Element(name = "mainSubAcc", required = false)
    private Long mainSubAcc;
    @Element(name = "message", required = false)
    private String message;
    @Element(name = "msgSearch", required = false)
    private String msgSearch;
    @Element(name = "networkClass", required = false)
    private String networkClass;
    @Element(name = "newSerialEq", required = false)
    private boolean newSerialEq;
    @Element(name = "noticeChargeName", required = false)
    private String noticeChargeName;
    @Element(name = "numOfComputer", required = false)
    private Long numOfComputer;
    @Element(name = "numResetZone", required = false)
    private Long numResetZone;
    @Element(name = "offerId", required = false)
    private Long offerId;
    @Element(name = "oldImsi", required = false)
    private String oldImsi;
    @Element(name = "oldSerial", required = false)
    private String oldSerial;
    @Element(name = "orgProductCode", required = false)
    private String orgProductCode;
    @Element(name = "ownerId", required = false)
    private Long ownerId;
    @Element(name = "ownerType", required = false)
    private Long ownerType;
    @Element(name = "parentSubId", required = false)
    private Long parentSubId;
    @Element(name = "password", required = false)
    private String password;
    @Element(name = "passwordPPPOE", required = false)
    private String passwordPPPOE;
    @Element(name = "payType", required = false)
    private String payType;
    @Element(name = "payTypeView", required = false)
    private String payTypeView;
    @Element(name = "privateIp", required = false)
    private String privateIp;
    @Element(name = "productCode", required = false)
    private String productCode;
    @Element(name = "productCodeAlias", required = false)
    private String productCodeAlias;
    @Element(name = "productEligible", required = false)
    private boolean productEligible;
    @Element(name = "productId", required = false)
    private Long productId;
    @Element(name = "productName", required = false)
    private String productName;
    @Element(name = "productOfferName", required = false)
    private String productOfferName;
    @Element(name = "project", required = false)
    private Long project;
    @Element(name = "promotionCode", required = false)
    private String promotionCode;
    @Element(name = "promotionName", required = false)
    private String promotionName;
    @Element(name = "reasonId", required = false)
    private Long reasonId;
    @Element(name = "regType", required = false)
    private String regType;
    @Element(name = "regTypeId", required = false)
    private Long regTypeId;
    @Element(name = "renderChild", required = false)
    private boolean renderChild;
    @Element(name = "representSub", required = false)
    private String representSub;
    @Element(name = "requestProvisioning", required = false)
    private String requestProvisioning;
    @Element(name = "responseHLR", required = false)
    private String responseHLR;
    @Element(name = "responseOCS", required = false)
    private String responseOCS;
    @Element(name = "responseProvisioning", required = false)
    private String responseProvisioning;
    @Element(name = "saleServicesCode", required = false)
    private String saleServicesCode;
    @Element(name = "serial", required = false)
    private String serial;
    @Element(name = "serviceTypeId", required = false)
    private String serviceTypeId;
    @Element(name = "shopCode", required = false)
    private String shopCode;
    @Element(name = "smartSIM", required = false)
    private boolean smartSIM;
    @Element(name = "speed", required = false)
    private Long speed;
    @Element(name = "startMoney", required = false)
    private Long startMoney;
    @Element(name = "status", required = false)
    private String status;
    @Element(name = "statusActive900", required = false)
    private String statusActive900;
    @Element(name = "statusView", required = false)
    private String statusView;
    @Element(name = "strDeposit", required = false)
    private String strDeposit;
    @Element(name = "strLstStockName", required = false)
    private String strLstStockName;
    @Element(name = "subId", required = false)
    private Long subId;
    @Element(name = "subType", required = false)
    private String subType;
    @Element(name = "subTypeName", required = false)
    private String subTypeName;
    @Element(name = "supportActive900", required = false)
    private boolean supportActive900;
    @Element(name = "technology", required = false)
    private String technology;
    @Element(name = "technologyOld", required = false)
    private String technologyOld;
    @Element(name = "technologyText", required = false)
    private String technologyText;
    @Element(name = "telServiceAlias", required = false)
    private String telServiceAlias;
    @Element(name = "telecomServiceId", required = false)
    private Long telecomServiceId;
    @Element(name = "telecomServiceName", required = false)
    private String telecomServiceName;
    @Element(name = "type", required = false)
    private String type;
    @Element(name = "upLink", required = false)
    private Long upLink;
    @Element(name = "updateUser", required = false)
    private String updateUser;
    @Element(name = "useDoLockIsdn", required = false)
    private String useDoLockIsdn;
    @Element(name = "validateMsg", required = false)
    private String validateMsg;
    @Element(name = "vip", required = false)
    private String vip;
    @Element(name = "vlan", required = false)
    private String vlan;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountPPPOE() {
        return accountPPPOE;
    }

    public void setAccountPPPOE(String accountPPPOE) {
        this.accountPPPOE = accountPPPOE;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getActStatusText() {
        return actStatusText;
    }

    public void setActStatusText(String actStatusText) {
        this.actStatusText = actStatusText;
    }

    public String getActStatusView() {
        return actStatusView;
    }

    public void setActStatusView(String actStatusView) {
        this.actStatusView = actStatusView;
    }

    public Long getActionAuditId() {
        return actionAuditId;
    }

    public void setActionAuditId(Long actionAuditId) {
        this.actionAuditId = actionAuditId;
    }

    public boolean isAutoActive900() {
        return autoActive900;
    }

    public void setAutoActive900(boolean autoActive900) {
        this.autoActive900 = autoActive900;
    }

    public String getBlockActionCode() {
        return blockActionCode;
    }

    public void setBlockActionCode(String blockActionCode) {
        this.blockActionCode = blockActionCode;
    }

    public boolean isBlockSuccess() {
        return blockSuccess;
    }

    public void setBlockSuccess(boolean blockSuccess) {
        this.blockSuccess = blockSuccess;
    }

    public boolean isBusinessAccepted() {
        return businessAccepted;
    }

    public void setBusinessAccepted(boolean businessAccepted) {
        this.businessAccepted = businessAccepted;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getCmkIsdn() {
        return cmkIsdn;
    }

    public void setCmkIsdn(String cmkIsdn) {
        this.cmkIsdn = cmkIsdn;
    }

    public String getCmkPromotion() {
        return cmkPromotion;
    }

    public void setCmkPromotion(String cmkPromotion) {
        this.cmkPromotion = cmkPromotion;
    }

    public String getCommitmentDesc() {
        return commitmentDesc;
    }

    public void setCommitmentDesc(String commitmentDesc) {
        this.commitmentDesc = commitmentDesc;
    }

    public String getConnectError() {
        return connectError;
    }

    public void setConnectError(String connectError) {
        this.connectError = connectError;
    }

    public String getConnectExtraError() {
        return connectExtraError;
    }

    public void setConnectExtraError(String connectExtraError) {
        this.connectExtraError = connectExtraError;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Long getCustOrderDetailId() {
        return custOrderDetailId;
    }

    public void setCustOrderDetailId(Long custOrderDetailId) {
        this.custOrderDetailId = custOrderDetailId;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Long getDepositView() {
        return depositView;
    }

    public void setDepositView(Long depositView) {
        this.depositView = depositView;
    }

    public String getDetailCommitDesc() {
        return detailCommitDesc;
    }

    public void setDetailCommitDesc(String detailCommitDesc) {
        this.detailCommitDesc = detailCommitDesc;
    }

    public Long getDevStaffId() {
        return devStaffId;
    }

    public void setDevStaffId(Long devStaffId) {
        this.devStaffId = devStaffId;
    }

    public boolean isDirectChangePromotion() {
        return directChangePromotion;
    }

    public void setDirectChangePromotion(boolean directChangePromotion) {
        this.directChangePromotion = directChangePromotion;
    }

    public boolean isDisableItem() {
        return disableItem;
    }

    public void setDisableItem(boolean disableItem) {
        this.disableItem = disableItem;
    }

    public boolean isDisableOwner() {
        return disableOwner;
    }

    public void setDisableOwner(boolean disableOwner) {
        this.disableOwner = disableOwner;
    }

    public boolean isEligibleToJoinGroup() {
        return eligibleToJoinGroup;
    }

    public void setEligibleToJoinGroup(boolean eligibleToJoinGroup) {
        this.eligibleToJoinGroup = eligibleToJoinGroup;
    }

    public boolean isErrorOCS() {
        return errorOCS;
    }

    public void setErrorOCS(boolean errorOCS) {
        this.errorOCS = errorOCS;
    }

    public String getGponGroupAccount() {
        return gponGroupAccount;
    }

    public void setGponGroupAccount(String gponGroupAccount) {
        this.gponGroupAccount = gponGroupAccount;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    public String getInfrastructureType() {
        return infrastructureType;
    }

    public void setInfrastructureType(String infrastructureType) {
        this.infrastructureType = infrastructureType;
    }

    public String getIpGateway() {
        return ipGateway;
    }

    public void setIpGateway(String ipGateway) {
        this.ipGateway = ipGateway;
    }

    public String getIpLan() {
        return ipLan;
    }

    public void setIpLan(String ipLan) {
        this.ipLan = ipLan;
    }

    public String getIpRouter() {
        return ipRouter;
    }

    public void setIpRouter(String ipRouter) {
        this.ipRouter = ipRouter;
    }

    public String getIpStatic() {
        return ipStatic;
    }

    public void setIpStatic(String ipStatic) {
        this.ipStatic = ipStatic;
    }

    public String getIpView() {
        return ipView;
    }

    public void setIpView(String ipView) {
        this.ipView = ipView;
    }

    public String getIpWan() {
        return ipWan;
    }

    public void setIpWan(String ipWan) {
        this.ipWan = ipWan;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Long getIsInfoCompleted() {
        return isInfoCompleted;
    }

    public void setIsInfoCompleted(Long isInfoCompleted) {
        this.isInfoCompleted = isInfoCompleted;
    }

    public boolean isProvince() {
        return isProvince;
    }

    public void setProvince(boolean province) {
        isProvince = province;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSmartSIM() {
        return isSmartSIM;
    }

    public void setSmartSIM(boolean smartSIM) {
        isSmartSIM = smartSIM;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
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

    public String getStatusActive900() {
        return statusActive900;
    }

    public void setStatusActive900(String statusActive900) {
        this.statusActive900 = statusActive900;
    }

    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }

    public String getStrDeposit() {
        return strDeposit;
    }

    public void setStrDeposit(String strDeposit) {
        this.strDeposit = strDeposit;
    }

    public String getStrLstStockName() {
        return strLstStockName;
    }

    public void setStrLstStockName(String strLstStockName) {
        this.strLstStockName = strLstStockName;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public boolean isSupportActive900() {
        return supportActive900;
    }

    public void setSupportActive900(boolean supportActive900) {
        this.supportActive900 = supportActive900;
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

    public String getTelServiceAlias() {
        return telServiceAlias;
    }

    public void setTelServiceAlias(String telServiceAlias) {
        this.telServiceAlias = telServiceAlias;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUpLink() {
        return upLink;
    }

    public void setUpLink(Long upLink) {
        this.upLink = upLink;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUseDoLockIsdn() {
        return useDoLockIsdn;
    }

    public void setUseDoLockIsdn(String useDoLockIsdn) {
        this.useDoLockIsdn = useDoLockIsdn;
    }

    public String getValidateMsg() {
        return validateMsg;
    }

    public void setValidateMsg(String validateMsg) {
        this.validateMsg = validateMsg;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Long keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Long getLimiUsageAfterConfirm() {
        return limiUsageAfterConfirm;
    }

    public void setLimiUsageAfterConfirm(Long limiUsageAfterConfirm) {
        this.limiUsageAfterConfirm = limiUsageAfterConfirm;
    }

    public Long getLimitUsage() {
        return limitUsage;
    }

    public void setLimitUsage(Long limitUsage) {
        this.limitUsage = limitUsage;
    }

    public Long getLimitUsageNew() {
        return limitUsageNew;
    }

    public void setLimitUsageNew(Long limitUsageNew) {
        this.limitUsageNew = limitUsageNew;
    }

    public Long getLimitUsageTmp() {
        return limitUsageTmp;
    }

    public void setLimitUsageTmp(Long limitUsageTmp) {
        this.limitUsageTmp = limitUsageTmp;
    }

    public Long getLocalSpeed() {
        return localSpeed;
    }

    public void setLocalSpeed(Long localSpeed) {
        this.localSpeed = localSpeed;
    }

    public Long getMainSubAcc() {
        return mainSubAcc;
    }

    public void setMainSubAcc(Long mainSubAcc) {
        this.mainSubAcc = mainSubAcc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgSearch() {
        return msgSearch;
    }

    public void setMsgSearch(String msgSearch) {
        this.msgSearch = msgSearch;
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public void setNetworkClass(String networkClass) {
        this.networkClass = networkClass;
    }

    public boolean isNewSerialEq() {
        return newSerialEq;
    }

    public void setNewSerialEq(boolean newSerialEq) {
        this.newSerialEq = newSerialEq;
    }

    public String getNoticeChargeName() {
        return noticeChargeName;
    }

    public void setNoticeChargeName(String noticeChargeName) {
        this.noticeChargeName = noticeChargeName;
    }

    public Long getNumOfComputer() {
        return numOfComputer;
    }

    public void setNumOfComputer(Long numOfComputer) {
        this.numOfComputer = numOfComputer;
    }

    public Long getNumResetZone() {
        return numResetZone;
    }

    public void setNumResetZone(Long numResetZone) {
        this.numResetZone = numResetZone;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getOldImsi() {
        return oldImsi;
    }

    public void setOldImsi(String oldImsi) {
        this.oldImsi = oldImsi;
    }

    public String getOldSerial() {
        return oldSerial;
    }

    public void setOldSerial(String oldSerial) {
        this.oldSerial = oldSerial;
    }

    public String getOrgProductCode() {
        return orgProductCode;
    }

    public void setOrgProductCode(String orgProductCode) {
        this.orgProductCode = orgProductCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getParentSubId() {
        return parentSubId;
    }

    public void setParentSubId(Long parentSubId) {
        this.parentSubId = parentSubId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordPPPOE() {
        return passwordPPPOE;
    }

    public void setPasswordPPPOE(String passwordPPPOE) {
        this.passwordPPPOE = passwordPPPOE;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeView() {
        return payTypeView;
    }

    public void setPayTypeView(String payTypeView) {
        this.payTypeView = payTypeView;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCodeAlias() {
        return productCodeAlias;
    }

    public void setProductCodeAlias(String productCodeAlias) {
        this.productCodeAlias = productCodeAlias;
    }

    public boolean isProductEligible() {
        return productEligible;
    }

    public void setProductEligible(boolean productEligible) {
        this.productEligible = productEligible;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductOfferName() {
        return productOfferName;
    }

    public void setProductOfferName(String productOfferName) {
        this.productOfferName = productOfferName;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
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

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public Long getRegTypeId() {
        return regTypeId;
    }

    public void setRegTypeId(Long regTypeId) {
        this.regTypeId = regTypeId;
    }

    public boolean isRenderChild() {
        return renderChild;
    }

    public void setRenderChild(boolean renderChild) {
        this.renderChild = renderChild;
    }

    public String getRepresentSub() {
        return representSub;
    }

    public void setRepresentSub(String representSub) {
        this.representSub = representSub;
    }

    public String getRequestProvisioning() {
        return requestProvisioning;
    }

    public void setRequestProvisioning(String requestProvisioning) {
        this.requestProvisioning = requestProvisioning;
    }

    public String getResponseHLR() {
        return responseHLR;
    }

    public void setResponseHLR(String responseHLR) {
        this.responseHLR = responseHLR;
    }

    public String getResponseOCS() {
        return responseOCS;
    }

    public void setResponseOCS(String responseOCS) {
        this.responseOCS = responseOCS;
    }

    public String getResponseProvisioning() {
        return responseProvisioning;
    }

    public void setResponseProvisioning(String responseProvisioning) {
        this.responseProvisioning = responseProvisioning;
    }

    public String getSaleServicesCode() {
        return saleServicesCode;
    }

    public void setSaleServicesCode(String saleServicesCode) {
        this.saleServicesCode = saleServicesCode;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
