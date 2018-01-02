package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

public class OmniFeeInfo implements Serializable {
    private String feeCode;
    private Long feeAmount;
    private String type;

    public OmniFeeInfo() {
    }

    public OmniFeeInfo(String feeCode, Long feeAmount) {
        this.feeCode = feeCode;
        this.feeAmount = feeAmount;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
