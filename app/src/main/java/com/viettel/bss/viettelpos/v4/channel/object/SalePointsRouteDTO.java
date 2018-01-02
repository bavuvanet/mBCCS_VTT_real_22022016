package com.viettel.bss.viettelpos.v4.channel.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Toancx on 10/6/2017.
 */

@Root(name = "return", strict = false)
public class SalePointsRouteDTO {
    @Element(name = "careNumber", required = false)
    private Long careNumber;
    @Element(name = "salePointAddress", required = false)
    private String salePointAddress;
    @Element(name = "salePointCode", required = false)
    private String salePointCode;
    @Element(name = "salePointId", required = false)
    private Long salePointId;
    @Element(name = "salePointName", required = false)
    private String salePointName;
    @Element(name = "staffOwnerCode", required = false)
    private String staffOwnerCode;
    @Element(name = "staffOwnerID", required = false)
    private Long staffOwnerID;
    @Element(name = "staffOwnerName", required = false)
    private String staffOwnerName;
    @Element(name = "status", required = false)
    private Long status;
    @Element(name = "userApprove", required = false)
    private String userApprove;
    @Element(name = "sprId", required = false)
    private Long sprId;
    @Element(name = "x", required = false)
    private String x;
    @Element(name = "y", required = false)
    private String y;

    public String getSalePointAddress() {
        return salePointAddress;
    }

    public void setSalePointAddress(String salePointAddress) {
        this.salePointAddress = salePointAddress;
    }

    public String getSalePointCode() {
        return salePointCode;
    }

    public void setSalePointCode(String salePointCode) {
        this.salePointCode = salePointCode;
    }

    public Long getSalePointId() {
        return salePointId;
    }

    public void setSalePointId(Long salePointId) {
        this.salePointId = salePointId;
    }

    public String getSalePointName() {
        return salePointName;
    }

    public void setSalePointName(String salePointName) {
        this.salePointName = salePointName;
    }

    public String getStaffOwnerCode() {
        return staffOwnerCode;
    }

    public void setStaffOwnerCode(String staffOwnerCode) {
        this.staffOwnerCode = staffOwnerCode;
    }

    public Long getStaffOwnerID() {
        return staffOwnerID;
    }

    public void setStaffOwnerID(Long staffOwnerID) {
        this.staffOwnerID = staffOwnerID;
    }

    public String getStaffOwnerName() {
        return staffOwnerName;
    }

    public void setStaffOwnerName(String staffOwnerName) {
        this.staffOwnerName = staffOwnerName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUserApprove() {
        return userApprove;
    }

    public void setUserApprove(String userApprove) {
        this.userApprove = userApprove;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Long getCareNumber() {
        return careNumber;
    }

    public void setCareNumber(Long careNumber) {
        this.careNumber = careNumber;
    }

    public Long getSprId() {
        return sprId;
    }

    public void setSprId(Long sprId) {
        this.sprId = sprId;
    }
}
