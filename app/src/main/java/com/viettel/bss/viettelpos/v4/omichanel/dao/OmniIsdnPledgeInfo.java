package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

public class OmniIsdnPledgeInfo implements Serializable {
    private String isdn;
    private Long pledgeAmount;
    private Long price;
    private Long pledgeTime;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(Long pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPledgeTime() {
        return pledgeTime;
    }

    public void setPledgeTime(Long pledgeTime) {
        this.pledgeTime = pledgeTime;
    }
}
