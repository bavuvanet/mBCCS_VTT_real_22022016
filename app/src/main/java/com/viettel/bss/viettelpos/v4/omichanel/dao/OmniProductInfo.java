package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;
import java.util.List;

public class OmniProductInfo implements Serializable {
    private String bundleCode;
    private String bundleName;
    private String productCode;
    private String regReasonCode;
    private Long regReasonId;
    private String promotionCode;
    private String promotionName;
    private String prepaidCode;
    private Long prepaidId;//Id cuoc dong truoc
    private Long prepaidMonth;//So thang cuoc dong truoc
    private Long totalPrepaidAmount; //Tong tien cuoc dong truoc (da chiet khau)
    private String bundleDesc;
    private List<String> miCodes;
    private List<String> vtFreeCodes;
    private List<String> vasCodes;
    private List<OmniVasInfo> vasInfos;
    private Long price;

    // cuoc dong truoc
    private String productName;
    private String oldPrepaidCode;// ma cuoc dong tr hien tai
    private Long oldPrepaidMonth;//So thang cuoc dong truoc hien tai
    private Long monthAmount; // so thang KM

    public String getBundleCode() {
        return bundleCode;
    }

    public void setBundleCode(String bundleCode) {
        this.bundleCode = bundleCode;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getRegReasonCode() {
        return regReasonCode;
    }

    public void setRegReasonCode(String regReasonCode) {
        this.regReasonCode = regReasonCode;
    }

    public Long getRegReasonId() {
        return regReasonId;
    }

    public void setRegReasonId(Long regReasonId) {
        this.regReasonId = regReasonId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public List<String> getMiCodes() {
        return miCodes;
    }

    public void setMiCodes(List<String> miCodes) {
        this.miCodes = miCodes;
    }

    public void setVasCodes(List<String> vasCodes) {
        this.vasCodes = vasCodes;
    }

    public List<String> getVtFreeCodes() {
        return vtFreeCodes;
    }

    public void setVtFreeCodes(List<String> vtFreeCodes) {
        this.vtFreeCodes = vtFreeCodes;
    }

    public String getBundleDesc() {
        return bundleDesc;
    }

    public void setBundleDesc(String bundleDesc) {
        this.bundleDesc = bundleDesc;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPrepaidCode() {
        return prepaidCode;
    }

    public void setPrepaidCode(String prepaidCode) {
        this.prepaidCode = prepaidCode;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Long getTotalPrepaidAmount() {
        return totalPrepaidAmount;
    }

    public void setTotalPrepaidAmount(Long totalPrepaidAmount) {
        this.totalPrepaidAmount = totalPrepaidAmount;
    }

    public Long getPrepaidId() {
        return prepaidId;
    }

    public void setPrepaidId(Long prepaidId) {
        this.prepaidId = prepaidId;
    }

    public Long getPrepaidMonth() {
        return prepaidMonth;
    }

    public void setPrepaidMonth(Long prepaidMonth) {
        this.prepaidMonth = prepaidMonth;
    }

    public List<OmniVasInfo> getVasInfos() {
        return vasInfos;
    }

    public void setVasInfos(List<OmniVasInfo> vasInfos) {
        this.vasInfos = vasInfos;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOldPrepaidCode() {
        return oldPrepaidCode;
    }

    public void setOldPrepaidCode(String oldPrepaidCode) {
        this.oldPrepaidCode = oldPrepaidCode;
    }

    public Long getOldPrepaidMonth() {
        return oldPrepaidMonth;
    }

    public void setOldPrepaidMonth(Long oldPrepaidMonth) {
        this.oldPrepaidMonth = oldPrepaidMonth;
    }

    public Long getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Long monthAmount) {
        this.monthAmount = monthAmount;
    }

    public List<String> getVasCodes() {
        return vasCodes;
    }
}
