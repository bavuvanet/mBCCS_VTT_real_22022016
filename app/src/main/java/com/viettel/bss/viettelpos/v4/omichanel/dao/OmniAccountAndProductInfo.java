package com.viettel.bss.viettelpos.v4.omichanel.dao;


import java.io.Serializable;

/**
 * Created by Lamnv5 on 8/22/2017.
 */
public class OmniAccountAndProductInfo implements Serializable {
    private String account;
    private OmniProductInfo productInfo;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public OmniProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(OmniProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
