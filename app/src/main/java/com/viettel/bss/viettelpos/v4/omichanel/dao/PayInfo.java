package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuLA4 on 8/29/2017.
 */
public class PayInfo implements Serializable {
    private static final long serialVersionUID = 0L;

    public static final String PAY_METHOD_BANKPLUS = "BANKPLUS";
    public static final String PAY_METHOD_PREPAID_CARD = "PREPAID_CARD";

    private boolean immediatePay; //true-thanh toan nga, false - thanh toan sau
    private String payMethod;
    private List<String> cardSerials = new ArrayList<>();
    private List<CardRecord> cardRecords = new ArrayList<>();
    private String isdnBankplus;
    private String bankCode;

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getIsdnBankplus() {
        return isdnBankplus;
    }

    public void setIsdnBankplus(String isdnBankplus) {
        this.isdnBankplus = isdnBankplus;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isImmediatePay() {
        return immediatePay;
    }

    public void setImmediatePay(boolean immediatePay) {
        this.immediatePay = immediatePay;
    }

    public List<CardRecord> getCardRecords() {
        return cardRecords;
    }

    public void setCardRecords(List<CardRecord> cardRecords) {
        this.cardRecords = cardRecords;
    }

    public List<String> getCardSerials() {
        return cardSerials;
    }

    public void setCardSerials(List<String> cardSerials) {
        this.cardSerials = cardSerials;
    }
}
