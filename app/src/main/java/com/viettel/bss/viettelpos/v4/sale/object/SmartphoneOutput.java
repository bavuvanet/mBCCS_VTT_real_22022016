package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huypq15 on 6/27/2017.
 */
@Root(name = "return", strict = false)
public class SmartphoneOutput {
    @Element(name = "token", required = false)
    private String token;
    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "description", required = false)
    private String description;
    @ElementList(name = "lstShop", entry = "lstShop", required = false, inline = true)
    private List<Shop> lstShop;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Shop> getLstShop() {
        return lstShop;
    }

    public void setLstShop(List<Shop> lstShop) {
        this.lstShop = lstShop;
    }
}
