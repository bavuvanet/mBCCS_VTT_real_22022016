package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 9/13/2017.
 */
@Root(name = "return", strict = false)
public class VStockNumberOmniDTO implements Serializable {

    @Element(name = "id", required = false)
    protected String id;
    @Element(name = "isdn", required = false)
    protected String isdn;
    @Element(name = "ownerId", required = false)
    protected String ownerId;
    @Element(name = "ownerType", required = false)
    protected String ownerType;
    @Element(name = "pledgeAmount", required = false)
    protected String pledgeAmount;
    @Element(name = "pledgeTime", required = false)
    protected String pledgeTime;
    @Element(name = "posPrice", required = false)
    protected String posPrice;
    @Element(name = "prePrice", required = false)
    protected String prePrice;
    @Element(name = "price", required = false)
    protected String price;
    @Element(name = "prodOfferId", required = false)
    protected String prodOfferId;
    @Element(name = "productOfferPriceId", required = false)
    protected String productOfferPriceId;
    @Element(name = "status", required = false)
    protected String status;

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the isdn property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIsdn() {
        return isdn;
    }

    /**
     * Sets the value of the isdn property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIsdn(String value) {
        this.isdn = value;
    }

    /**
     * Gets the value of the ownerId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOwnerId(String value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the ownerType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOwnerType() {
        return ownerType;
    }

    /**
     * Sets the value of the ownerType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOwnerType(String value) {
        this.ownerType = value;
    }

    /**
     * Gets the value of the pledgeAmount property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPledgeAmount() {
        if (pledgeAmount == null) {
            return "0";
        }
        return pledgeAmount;
    }

    /**
     * Sets the value of the pledgeAmount property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPledgeAmount(String value) {
        this.pledgeAmount = value;
    }

    /**
     * Gets the value of the pledgeTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPledgeTime() {
        if (CommonActivity.isNullOrEmpty(pledgeTime)) {
            return "0";
        }
        return pledgeTime;
    }

    /**
     * Sets the value of the pledgeTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPledgeTime(String value) {
        this.pledgeTime = value;
    }

    /**
     * Gets the value of the posPrice property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPosPrice() {
        if (CommonActivity.isNullOrEmpty(posPrice)) {
            return "0";
        }
        return posPrice;
    }

    /**
     * Sets the value of the posPrice property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPosPrice(String value) {
        this.posPrice = value;
    }

    /**
     * Gets the value of the prePrice property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPrePrice() {
        if (CommonActivity.isNullOrEmpty(prePrice)) {
            return "0";
        }
        return prePrice;
    }

    /**
     * Sets the value of the prePrice property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPrePrice(String value) {
        this.prePrice = value;
    }

    /**
     * Gets the value of the price property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPrice() {
        if (CommonActivity.isNullOrEmpty(price)) {
            return "0";
        }
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the prodOfferId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProdOfferId() {
        return prodOfferId;
    }

    /**
     * Sets the value of the prodOfferId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProdOfferId(String value) {
        this.prodOfferId = value;
    }

    /**
     * Gets the value of the productOfferPriceId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProductOfferPriceId() {
        return productOfferPriceId;
    }

    /**
     * Sets the value of the productOfferPriceId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProductOfferPriceId(String value) {
        this.productOfferPriceId = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatus(String value) {
        this.status = value;
    }

}