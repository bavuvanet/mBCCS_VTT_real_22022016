package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thinhhq1 on 8/12/2017.
 */
@Root(name = "productOfferCharacterClone", strict = false)
public class ProductOfferCharacterClone implements Serializable{
    @Element(name = "listingPrice", required = false)
    private String listingPrice;
    @Element(name = "upLoadSpeed", required = false)
    private String upLoadSpeed;
    @Element(name = "downLoadSpeed", required = false)
    private String downLoadSpeed;


    public ProductOfferCharacterClone() {
    }

    public String getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(String listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getUpLoadSpeed() {
        return upLoadSpeed;
    }

    public ProductOfferCharacterClone(String listingPrice, String upLoadSpeed, String downLoadSpeed) {
        this.listingPrice = listingPrice;
        this.upLoadSpeed = upLoadSpeed;
        this.downLoadSpeed = downLoadSpeed;
    }

    public void setUpLoadSpeed(String upLoadSpeed) {
        this.upLoadSpeed = upLoadSpeed;
    }

    public String getDownLoadSpeed() {
        return downLoadSpeed;
    }

    public void setDownLoadSpeed(String downLoadSpeed) {
        this.downLoadSpeed = downLoadSpeed;
    }
}
