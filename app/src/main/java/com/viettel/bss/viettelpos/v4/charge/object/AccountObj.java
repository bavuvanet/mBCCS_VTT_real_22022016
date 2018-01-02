package com.viettel.bss.viettelpos.v4.charge.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.util.List;

/**
 * Created by thuandq on 6/22/2017.
 */

public class AccountObj {
    private String accountNo;
    private String signDate;
    private String status;
    private Boolean checker;
    private List<SubscriberDTO> subscriberDTOList;
    public AccountObj(String accountNo, String signDate, String status, Boolean checker,List<SubscriberDTO> subscriberDTOList) {
        this.accountNo = accountNo;
        this.signDate = signDate;
        this.status = status;
        this.checker = checker;
        this.subscriberDTOList=subscriberDTOList;
    }

    public AccountObj(AccountDTO accountDTO) {
        this.accountNo = accountDTO.getAccountNo();
        this.signDate = accountDTO.getSignDate();
        this.status = accountDTO.getStatus();
        this.checker = false;
    }

    public List<SubscriberDTO> getSubscriberDTOList() {
        return subscriberDTOList;
    }

    public void setSubscriberDTOList(List<SubscriberDTO> subscriberDTOList) {
        this.subscriberDTOList = subscriberDTOList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getChecker() {
        return checker;
    }

    public void setChecker(Boolean checker) {
        this.checker = checker;
    }
}

