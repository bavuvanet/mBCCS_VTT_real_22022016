package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by lamnv5 on 9/6/2017.
 */
public class FeeInfo implements Serializable{
    private static final long serialVersionUID = 0L;

    private String feeCode;
    private Long feeAmount;

    public FeeInfo() {
    }

    public FeeInfo(String feeCode, Long feeAmount) {
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

}
