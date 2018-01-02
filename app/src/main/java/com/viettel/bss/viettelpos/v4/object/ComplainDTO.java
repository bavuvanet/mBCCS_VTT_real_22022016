package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 2/17/2017.
 */

@Root(name = "ComplainDTO", strict = false)
public class ComplainDTO implements Serializable{
    @Element(name = "code", required = false)
    private String code;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "probPriorityId", required = false)
    private String probPriorityId;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "priority", required = false)
    private Long priority;
    @Element(name = "parentGroupId", required = false)
    private Long parentGroupId;
    @Element(name = "probGroupId", required = false)
    private Long probGroupId;
    @Element(name = "probTypeId", required = false)
    private Long probTypeId;
    @Element(name = "complainTypeId", required = false)
    private Long complainTypeId;
    @Element(name = "problemContent", required = false)
    private String problemContent;
    @Element(name = "complainerAddress", required = false)
    private String complainerAddress;
    @Element(name = "complainerPhone", required = false)
    private String complainerPhone;
    @Element(name = "complainerEmail", required = false)
    private String complainerEmail;
    @Element(name = "complainerName", required = false)
    private String complainerName;
    @Element(name = "complainerIdNo", required = false)
    private String complainerIdNo;
    @Element(name = "areaCode", required = false)
    private String areaCode;
    @Element(name = "province", required = false)
    private String province;
    @Element(name = "district", required = false)
    private String district;
    @Element(name = "precinct", required = false)
    private String precinct;
    @Element(name = "probChannelId", required = false)
    private String probChannelId = "15";
    @Element(name = "probAcceptTypeId", required = false)
    private String probAcceptTypeId;
    @Element(name = "custAppointDate", required = false)
    private String custAppointDate;
    @Element(name = "customerText", required = false)
    private String customerText;
    @Element(name = "actStatus", required = false)
    private String actStatus;
    @Element(name = "actStatusName", required = false)
    private String actStatusName;
    @Element(name = "payType", required = false)
    private String payType;
    @Element(name = "status", required = false)
    private String status;
    @Element(name = "statusName", required = false)
    private String statusName;
    @Element(name = "subTypeName", required = false)
    private String subTypeName;
    @Element(name = "problemLevelId", required = false)
    private String problemLevelId = "2";



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Long parentGroupId) {
        this.parentGroupId = parentGroupId;
    }


    public Long getComplainTypeId() {
        return complainTypeId;
    }

    public void setComplainTypeId(Long complainTypeId) {
        this.complainTypeId = complainTypeId;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getComplainerAddress() {
        return complainerAddress;
    }

    public void setComplainerAddress(String complainerAddress) {
        this.complainerAddress = complainerAddress;
    }

    public String getComplainerPhone() {
        return complainerPhone;
    }

    public void setComplainerPhone(String complainerPhone) {
        this.complainerPhone = complainerPhone;
    }

    public String getComplainerEmail() {
        return complainerEmail;
    }

    public void setComplainerEmail(String complainerEmail) {
        this.complainerEmail = complainerEmail;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getProbChannelId() {
        return probChannelId;
    }

    public void setProbChannelId(String probChannelId) {
        this.probChannelId = probChannelId;
    }

    public String getProbAcceptTypeId() {
        return probAcceptTypeId;
    }

    public void setProbAcceptTypeId(String probAcceptTypeId) {
        this.probAcceptTypeId = probAcceptTypeId;
    }

    public String getCustAppointDate() {
        return custAppointDate;
    }

    public void setCustAppointDate(String custAppointDate) {
        this.custAppointDate = custAppointDate;
    }

    public String getCustomerText() {
        return customerText;
    }

    public void setCustomerText(String customerText) {
        this.customerText = customerText;
    }

    public String getProbPriorityId() {
        return probPriorityId;
    }

    public void setProbPriorityId(String probPriorityId) {
        this.probPriorityId = probPriorityId;
    }

    public Long getProbGroupId() {
        return probGroupId;
    }

    public void setProbGroupId(Long probGroupId) {
        this.probGroupId = probGroupId;
    }

    public String getComplainerName() {
        return complainerName;
    }

    public void setComplainerName(String complainerName) {
        this.complainerName = complainerName;
    }

    public String getComplainerIdNo() {
        return complainerIdNo;
    }

    public void setComplainerIdNo(String complainerIdNo) {
        this.complainerIdNo = complainerIdNo;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getActStatusName() {
        return actStatusName;
    }

    public void setActStatusName(String actStatusName) {
        this.actStatusName = actStatusName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getProblemLevelId() {
        return problemLevelId;
    }

    public void setProblemLevelId(String problemLevelId) {
        this.problemLevelId = problemLevelId;
    }

    public Long getProbTypeId() {
        return probTypeId;
    }

    public void setProbTypeId(Long probTypeId) {
        this.probTypeId = probTypeId;
    }

    @Override
    public String toString() {
        return name;
    }
}
