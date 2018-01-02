package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Administrator on 23/06/2017.
 */
@Root(name = "return",strict=false)
public class HotLineReponseDetail implements Serializable{

    @Element(name = "packData", required = false)
    private String packData;

    @Element(name = "regType", required = false)
    private String regType;

    @Element(name = "promotionCode", required = false)
    private String promotionCode;

    @Element(name = "strDateOfBirth", required = false)
    private String strDateOfBirth;

    @Element(name = "idNo", required = false)
    private String idNo;

    @Element(name = "idType", required = false)
    private String idType;

    @Element(name = "telRegister", required = false)
    private String telRegister;

    @Element(name = "reciveRequestId", required = false)
    private String reciveRequestId;

    @Element(name = "address", required = false)
    private String address;

    @Element(name = "customerName", required = false)
    private String customerName;

    @Element(name = "strDateOfCMT", required = false)
    private String strDateOfCMT;

    @Element(name = "placeOfCMT", required = false)
    private String placeOfCMT;

    @Element(name = "catCustomerId", required = false)
    private String catCustomerId;

    public String getCatCustomerId() {
        return catCustomerId;
    }

    public void setCatCustomerId(String catCustomerId) {
        this.catCustomerId = catCustomerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStrDateOfCMT() {
        return strDateOfCMT;
    }

    public void setStrDateOfCMT(String strDateOfCMT) {
        this.strDateOfCMT = strDateOfCMT;
    }

    public String getPlaceOfCMT() {
        return placeOfCMT;
    }

    public void setPlaceOfCMT(String placeOfCMT) {
        this.placeOfCMT = placeOfCMT;
    }

    public String getReciveRequestId() {
        return reciveRequestId;
    }

    public void setReciveRequestId(String reciveRequestId) {
        this.reciveRequestId = reciveRequestId;
    }

    public String getPackData() {
        return packData;
    }

    public void setPackData(String packData) {
        this.packData = packData;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getStrDateOfBirth() {
        return strDateOfBirth;
    }

    public void setStrDateOfBirth(String strDateOfBirth) {
        this.strDateOfBirth = strDateOfBirth;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getTelRegister() {
        return telRegister;
    }

    public void setTelRegister(String telRegister) {
        this.telRegister = telRegister;
    }
}
