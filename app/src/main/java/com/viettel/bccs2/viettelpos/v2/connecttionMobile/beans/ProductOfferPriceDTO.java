package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 5/18/2017.
 */
@Root(name = "ProductOfferPriceDTO", strict = false)
public class ProductOfferPriceDTO {
    @Element(name = "price", required = false)
    private String price ="";
    @Element (name = "pricePledgeAmount", required = false)
    private String pricePledgeAmount = "";
    @Element (name = "pledgeTime", required = false)
    private String pledgeTime="";
    @Element (name = "priceVat", required = false)
    private String priceVat = "";
    @Element (name = "pricePriorPay", required = false)
    private String pricePriorPay;
    @Element (name = "productOfferName", required = false)
    private String productOfferName;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricePledgeAmount() {
        return pricePledgeAmount;
    }

    public void setPricePledgeAmount(String pricePledgeAmount) {
        this.pricePledgeAmount = pricePledgeAmount;
    }

    public String getPledgeTime() {
        return pledgeTime;
    }

    public void setPledgeTime(String pledgeTime) {
        this.pledgeTime = pledgeTime;
    }

    public String getPriceVat() {
        return priceVat;
    }

    public void setPriceVat(String priceVat) {
        this.priceVat = priceVat;
    }

    public String getPricePriorPay() {
        return pricePriorPay;
    }

    public void setPricePriorPay(String pricePriorPay) {
        this.pricePriorPay = pricePriorPay;
    }

    public String getProductOfferName() {
        return productOfferName;
    }

    public void setProductOfferName(String productOfferName) {
        this.productOfferName = productOfferName;
    }
}
