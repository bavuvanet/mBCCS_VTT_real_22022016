package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by quangkm on 9/21/2017.
 */
public class OmniVasInfo implements Serializable {
    private String vasCode;
    private String vasName;
    private Long price;

    public String getVasCode() {
        return vasCode;
    }

    public void setVasCode(String vasCode) {
        this.vasCode = vasCode;
    }

    public String getVasName() {
        return vasName;
    }

    public void setVasName(String vasName) {
        this.vasName = vasName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
