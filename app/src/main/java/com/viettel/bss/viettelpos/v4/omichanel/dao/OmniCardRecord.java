package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by Lamnv5 on 8/23/2017.
 */
public class OmniCardRecord implements Serializable {
    private String serial;
    private Long amount;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
