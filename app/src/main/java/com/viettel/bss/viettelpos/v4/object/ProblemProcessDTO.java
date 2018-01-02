package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by toancx on 2/22/2017.
 */
@Root(name = "ProblemProcessDTO", strict = false)
public class ProblemProcessDTO implements Serializable{
    @Element(name = "actionId", required = false)
    private Long actionId;
    @Element(name = "actionName", required = false)
    private String actionName;
    @Element(name = "cooridnateUnitName", required = false)
    private String cooridnateUnitName;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "memo", required = false)
    private String memo;
    @Element(name = "problemId", required = false)
    private Long problemId;
    @Element(name = "processContent", required = false)
    private String processContent;
    @Element(name = "processDate", required = false)
    private String processDate;
    @Element(name = "processId", required = false)
    private Long processId;
    @Element(name = "shopId", required = false)
    private Long shopId;
    @Element(name = "shopName", required = false)
    private String shopName;
    @Element(name = "staffCode", required = false)
    private String staffCode;
    @Element(name = "status", required = false)
    private Short status;

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCooridnateUnitName() {
        return cooridnateUnitName;
    }

    public void setCooridnateUnitName(String cooridnateUnitName) {
        this.cooridnateUnitName = cooridnateUnitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getProcessContent() {
        return processContent;
    }

    public void setProcessContent(String processContent) {
        this.processContent = processContent;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
