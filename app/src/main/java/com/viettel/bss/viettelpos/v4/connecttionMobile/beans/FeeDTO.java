package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

/**
 * Created by thientv7 on 6/17/2017.
 */

public class FeeDTO {
    private String name;
    private String value;
    private int payStatus;

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
