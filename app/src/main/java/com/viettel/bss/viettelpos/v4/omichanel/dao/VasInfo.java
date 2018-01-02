package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by TuLA4 on 9/18/2017.
 */
public class VasInfo implements Serializable {

    private String vasCode;
    private String vasName;
    private Long price;

    public VasInfo() {
        super();
    }

    public VasInfo(PoCatalogOutsideDTO poCatalogOutsideDTO) {
        this.vasCode = poCatalogOutsideDTO.getVasCode();
        this.vasName = poCatalogOutsideDTO.getVasName();
        this.price = Long.parseLong(poCatalogOutsideDTO.getPrice());
    }

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
