package com.viettel.bss.viettelpos.v4.omichanel.dao;

/**
 * Created by TienPH2 on 3/10/2016.
 * class chua request bankplus moi
 */
public class BankPlusBeanDTO {
    private static final long serialVersionUID = 0L;

    private String name;
    private String code;
    // view account bank plus
    private Long requestId;
    private String custName;
    private String status;
    private String listBank;
    private String errorCode;
    private String errorMsg;
    private String request;
    private String response;

    //Danh sác các đối tượng truyền sang bên Bankplus
    private String listTransBankPlus;
    private String isdn;
    private Long totalBalance;
    private String bankCode;
    private String requestBankplusId;
    private String staffCode;
    private String channelCode;
    private String ownerCode;
    private String shopCode;
    private Long channelTypeId;
    private String paymentType;
    private String recevedMoneyType;
    private String transactionType;
    private String district;
    private String serviceCode;
    private String processCode;
    private String contentDescriptionService;
    private String provinceCode;
    private Long transactionAmount;
    private Long originalRequestId;
    private String serviceIndicator;
    private String actionCode;
    private String collectionGroupId;
    private String pkType;

    public Long getOriginalRequestId() {
        return originalRequestId;
    }

    public void setOriginalRequestId(Long originalRequestId) {
        this.originalRequestId = originalRequestId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
//        System.out.println("request: ");
//        System.out.println(request);
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    // gạch nợ, hủy giao dịch

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getListBank() {
        return listBank;
    }

    public void setListBank(String listBank) {
        this.listBank = listBank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getListTransBankPlus() {
        return listTransBankPlus;
    }

    public void setListTransBankPlus(String listTransBankPlus) {
        this.listTransBankPlus = listTransBankPlus;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRequestBankplusId() {
        return requestBankplusId;
    }

    public void setRequestBankplusId(String requestBankplusId) {
        this.requestBankplusId = requestBankplusId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRecevedMoneyType() {
        return recevedMoneyType;
    }

    public void setRecevedMoneyType(String recevedMoneyType) {
        this.recevedMoneyType = recevedMoneyType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getContentDescriptionService() {
        return contentDescriptionService;
    }

    public void setContentDescriptionService(String contentDescriptionService) {
        this.contentDescriptionService = contentDescriptionService;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getServiceIndicator() {
        return serviceIndicator;
    }

    public void setServiceIndicator(String serviceIndicator) {
        this.serviceIndicator = serviceIndicator;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String getCollectionGroupId() {
        return collectionGroupId;
    }

    public void setCollectionGroupId(String collectionGroupId) {
        this.collectionGroupId = collectionGroupId;
    }
}
