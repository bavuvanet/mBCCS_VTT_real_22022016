package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 6/17/2017.
 */
@Root(name = "return", strict = false)
public class ReportProfileBO implements Serializable{
    @Element(name = "actionDate", required = false)
    private String actionDate;
    @Element(name = "checkInvalid", required = false)
    private Long checkInvalid;
    @Element(name = "checkStatus", required = false)
    private String checkStatus;
    @Element(name = "checkValid", required = false)
    private Long checkValid;
    @Element(name = "checkedDatetime", required = false)
    private String checkedDatetime;
    @Element(name = "cusAddress", required = false)
    private String cusAddress;
    @Element(name = "cusName", required = false)
    private String cusName;
    @Element(name = "cusType", required = false)
    private String cusType;
    @Element(name = "idNo", required = false)
    private String idNo;
    @Element(name = "isdnAccount", required = false)
    private String isdnAccount;
    @Element(name = "issueDate", required = false)
    private String issueDate;
    @Element(name = "issuePlace", required = false)
    private String issuePlace;
    @Element(name = "notCheck", required = false)
    private Long notCheck;
    @Element(name = "shopCode", required = false)
    private String shopCode;
    @Element(name = "shopName", required = false)
    private String shopName;
    @Element(name = "staffChecked", required = false)
    private String staffChecked;
    @Element(name = "subType", required = false)
    private String subType;
    @Element(name = "descTypeStatus", required = false)
    private String descTypeStatus;
    @Element(name = "staDatetime", required = false)
    private String staDatetime;
    @Element(name = "totalProfile", required = false)
    private Long totalProfile;
    @Element(name = "checkValidNotExpire", required = false)
    private Long checkValidNotExpire;
    @Element(name = "checkValidExpire", required = false)
    private Long checkValidExpire;
    @Element(name = "countCheckAgain", required = false)
    private Long countCheckAgain;

    public Long getCheckInvalid() {
        return checkInvalid;
    }

    public void setCheckInvalid(Long checkInvalid) {
        this.checkInvalid = checkInvalid;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getCheckValid() {
        return checkValid;
    }

    public void setCheckValid(Long checkValid) {
        this.checkValid = checkValid;
    }

    public String getCheckedDatetime() {
        return checkedDatetime;
    }

    public void setCheckedDatetime(String checkedDatetime) {
        this.checkedDatetime = checkedDatetime;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIsdnAccount() {
        return isdnAccount;
    }

    public void setIsdnAccount(String isdnAccount) {
        this.isdnAccount = isdnAccount;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public Long getNotCheck() {
        return notCheck;
    }

    public void setNotCheck(Long notCheck) {
        this.notCheck = notCheck;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffChecked() {
        return staffChecked;
    }

    public void setStaffChecked(String staffChecked) {
        this.staffChecked = staffChecked;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDescTypeStatus() {
        return descTypeStatus;
    }

    public void setDescTypeStatus(String descTypeStatus) {
        this.descTypeStatus = descTypeStatus;
    }

    public String getStaDatetime() {
        return staDatetime;
    }

    public void setStaDatetime(String staDatetime) {
        this.staDatetime = staDatetime;
    }

    public Long getTotalProfile() {
        return totalProfile;
    }

    public void setTotalProfile(Long totalProfile) {
        this.totalProfile = totalProfile;
    }

    public Long getCheckValidNotExpire() {
        return checkValidNotExpire;
    }

    public void setCheckValidNotExpire(Long checkValidNotExpire) {
        this.checkValidNotExpire = checkValidNotExpire;
    }

    public Long getCheckValidExpire() {
        return checkValidExpire;
    }

    public void setCheckValidExpire(Long checkValidExpire) {
        this.checkValidExpire = checkValidExpire;
    }

    public Long getCountCheckAgain() {
        return countCheckAgain;
    }

    public void setCountCheckAgain(Long countCheckAgain) {
        this.countCheckAgain = countCheckAgain;
    }
}
