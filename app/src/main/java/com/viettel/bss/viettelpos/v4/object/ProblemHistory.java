package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by toancx on 2/22/2017.
 */

@Root(name = "ProblemHistory", strict = false)
public class ProblemHistory implements Serializable{
    @Element(name = "causeLevel1", required = false)
    private String causeLevel1;
    @Element(name = "causeLevel2", required = false)
    private String causeLevel2;
    @Element(name = "causeLevel3", required = false)
    private String causeLevel3;
    @Element(name = "createDate", required = false)
    private String createDate;
    @Element(name = "custLimitDate", required = false)
    private String custLimitDate;
    @Element(name = "customerText", required = false)
    private String customerText;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "note", required = false)
    private String note;
    @Element(name = "probGroupName", required = false)
    private String probGroupName;
    @Element(name = "probParentGroupName", required = false)
    private String probParentGroupName;
    @Element(name = "probTypeId", required = false)
    private String probTypeId;
    @Element(name = "probTypeName", required = false)
    private String probTypeName;
    @Element(name = "problemContent", required = false)
    private String problemContent;
    @Element(name = "problemId", required = false)
    private String problemId;
    @Element(name = "reCompNumber", required = false)
    private String reCompNumber;
    @Element(name = "resultContent", required = false)
    private String resultContent;
    @Element(name = "status", required = false)
    private String status;
    @Element(name = "statusName", required = false)
    private String statusName;
    @Element(name = "userAccept", required = false)
    private String userAccept;
    @Element(name = "userProcess", required = false)
    private String userProcess;

    public String getCauseLevel1() {
        return causeLevel1;
    }

    public void setCauseLevel1(String causeLevel1) {
        this.causeLevel1 = causeLevel1;
    }

    public String getCauseLevel2() {
        return causeLevel2;
    }

    public void setCauseLevel2(String causeLevel2) {
        this.causeLevel2 = causeLevel2;
    }

    public String getCauseLevel3() {
        return causeLevel3;
    }

    public void setCauseLevel3(String causeLevel3) {
        this.causeLevel3 = causeLevel3;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCustLimitDate() {
        return custLimitDate;
    }

    public void setCustLimitDate(String custLimitDate) {
        this.custLimitDate = custLimitDate;
    }

    public String getCustomerText() {
        return customerText;
    }

    public void setCustomerText(String customerText) {
        this.customerText = customerText;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProbGroupName() {
        return probGroupName;
    }

    public void setProbGroupName(String probGroupName) {
        this.probGroupName = probGroupName;
    }

    public String getProbParentGroupName() {
        return probParentGroupName;
    }

    public void setProbParentGroupName(String probParentGroupName) {
        this.probParentGroupName = probParentGroupName;
    }

    public String getProbTypeId() {
        return probTypeId;
    }

    public void setProbTypeId(String probTypeId) {
        this.probTypeId = probTypeId;
    }

    public String getProbTypeName() {
        return probTypeName;
    }

    public void setProbTypeName(String probTypeName) {
        this.probTypeName = probTypeName;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getReCompNumber() {
        return reCompNumber;
    }

    public void setReCompNumber(String reCompNumber) {
        this.reCompNumber = reCompNumber;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
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

    public String getUserAccept() {
        return userAccept;
    }

    public void setUserAccept(String userAccept) {
        this.userAccept = userAccept;
    }

    public String getUserProcess() {
        return userProcess;
    }

    public void setUserProcess(String userProcess) {
        this.userProcess = userProcess;
    }
}
