package com.viettel.bss.viettelpos.v4.omichanel.dao;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by thuandq on 9/28/2017.
 */

public class ChangePrepaidDTO {

    protected String code;
    protected String effectDate;
    protected String isdn;
    protected String message;
    protected String prepaidCode;
    protected Long promId;
    protected String promotionCode;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrepaidCode() {
        return prepaidCode;
    }

    public void setPrepaidCode(String prepaidCode) {
        this.prepaidCode = prepaidCode;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }
}
