package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by TuLA4 on 9/13/2017.
 */
public class IsdnPledgeInfo implements Serializable {

    private static final long serialVersionUID = 0L;

    private String isdn;
    private Long pledgeAmount;
    private Long pledgeTime;
    private Long price;
    private Long posPrice;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getPledgeAmount() {
        if (pledgeAmount == null) {
            return new Long(0);
        }
        return pledgeAmount;
    }

    public void setPledgeAmount(Long pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public Long getPrice() {
        if (price == null) {
            return new Long(0);
        }
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPledgeTime() {
        if (pledgeTime == null) {
            return new Long(0);
        }
        return pledgeTime;
    }

    public void setPledgeTime(Long pledgeTime) {
        this.pledgeTime = pledgeTime;
    }

    public Long getPosPrice() {
        if (posPrice == null) {
            return new Long(0);
        }

        return posPrice;
    }

    public void setPosPrice(Long posPrice) {
        this.posPrice = posPrice;
    }
}
