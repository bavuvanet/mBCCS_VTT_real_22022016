package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lamnv5 on 8/22/2017.
 */
public class OmniAccountPrepaidInfo implements Serializable {
    private String account;
    private OmniProductInfo productInfo;
    private String productCode;
    private String areaCode;
    private List<PaymentPrePaidDetail> lstPaymentPrePaidDetailNew;
    private PromotionTypeDTO promotionTypeDTONew;
    private List<SubPromotionPrepaidDTO> lstPaymentPrePaidDetailOld;
    private PromotionTypeDTO promotionTypeDTOOld;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public OmniProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(OmniProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<PaymentPrePaidDetail> getLstPaymentPrePaidDetailNew() {
        return lstPaymentPrePaidDetailNew;
    }

    public void setLstPaymentPrePaidDetailNew(List<PaymentPrePaidDetail> lstPaymentPrePaidDetailNew) {
        this.lstPaymentPrePaidDetailNew = lstPaymentPrePaidDetailNew;
    }

    public PromotionTypeDTO getPromotionTypeDTONew() {
        return promotionTypeDTONew;
    }

    public void setPromotionTypeDTONew(PromotionTypeDTO promotionTypeDTONew) {
        this.promotionTypeDTONew = promotionTypeDTONew;
    }

    public List<SubPromotionPrepaidDTO> getLstPaymentPrePaidDetailOld() {
        return lstPaymentPrePaidDetailOld;
    }

    public void setLstPaymentPrePaidDetailOld(List<SubPromotionPrepaidDTO> lstPaymentPrePaidDetailOld) {
        this.lstPaymentPrePaidDetailOld = lstPaymentPrePaidDetailOld;
    }

    public PromotionTypeDTO getPromotionTypeDTOOld() {
        return promotionTypeDTOOld;
    }

    public void setPromotionTypeDTOOld(PromotionTypeDTO promotionTypeDTOOld) {
        this.promotionTypeDTOOld = promotionTypeDTOOld;
    }
}
