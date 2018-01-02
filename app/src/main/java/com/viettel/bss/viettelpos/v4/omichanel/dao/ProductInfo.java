package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TuLA4 on 9/7/2017.
 */
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 0L;

    private String telecomService;
    private String bundleCode;
    private String bundleName;
    private String productCode;
    private String regReasonCode;
    private Long regReasonId;
    private String promotionCode; //Ma chuong trinh KM
    private String promotionName; //Ten chuong trinh KM
    private Long prepaidId;//Id cuoc dong truoc
    private Long prepaidMonth;//So thang cuoc dong truoc
    private String prepaidCode; //Ma cuoc dong truoc
    private Long totalPrepaidAmount; //Tong tien cuoc dong truoc (da chiet khau)
    private String bundleDesc;
    private List<String> miCodes;
    private List<String> vtFreeCodes;
    private List<VasInfo> vasInfos;
    private Long price;

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

    public String getBundleDesc() {
        return bundleDesc;
    }

    public void setBundleDesc(String bundleDesc) {
        this.bundleDesc = bundleDesc;
    }

    public List<String> getMiCodes() {
        return miCodes;
    }

    public void setMiCodes(List<String> miCodes) {
        this.miCodes = miCodes;
    }

    public List<String> getVtFreeCodes() {
        return vtFreeCodes;
    }

    public void setVtFreeCodes(List<String> vtFreeCodes) {
        this.vtFreeCodes = vtFreeCodes;
    }

    public List<VasInfo> getVasInfos() {
        return vasInfos;
    }

    public void setVasInfos(List<VasInfo> vasInfos) {
        this.vasInfos = vasInfos;
    }

    public Long getPrice() {
        if (CommonActivity.isNullOrEmpty(price)) {
            return new Long(0);
        }
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public String getPrepaidCode() {
        return prepaidCode;
    }

    public void setPrepaidCode(String prepaidCode) {
        this.prepaidCode = prepaidCode;
    }

    public Long getTotalPrepaidAmount() {
        return totalPrepaidAmount;
    }

    public void setTotalPrepaidAmount(Long totalPrepaidAmount) {
        this.totalPrepaidAmount = totalPrepaidAmount;
    }
}
