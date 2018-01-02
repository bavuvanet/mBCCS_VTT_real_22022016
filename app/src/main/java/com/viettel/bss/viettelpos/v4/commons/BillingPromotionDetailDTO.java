package com.viettel.bss.viettelpos.v4.commons;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 8/26/2017.
 */
@Root(name = "return", strict = false)
public class BillingPromotionDetailDTO  {

    @Element(name = "numMonth", required = false)
    private Long numMonth;
    @Element(name = "promValueName", required = false)
    private String promValueName;
    @Element(name = "startMonth", required = false)
    private Long startMonth;
    @Element(name = "unit", required = false)
    private String unit;
    @Element(name = "billValueFrom", required = false)
    protected Long billValueFrom;
    @Element(name = "billValueTo", required = false)
    protected Long billValueTo;
    @Element(name = "billingPromotionDetailId", required = false)
    protected Long billingPromotionDetailId;
    @Element(name = "billingPromotionId", required = false)
    protected Long billingPromotionId;
    @Element(name = "createDatetime", required = false)
    protected String createDatetime;
    @Element(name = "createUser", required = false)
    protected String createUser;
    @Element(name = "description", required = false)
    protected String description;
    @Element(name = "moneyType", required = false)
    protected String moneyType;
    @Element(name = "moneyTypeName", required = false)
    protected String moneyTypeName;
    @Element(name = "promType", required = false)
    protected String promType;
    @Element(name = "promTypeName", required = false)
    protected String promTypeName;
    @Element(name = "promUsage", required = false)
    protected String promUsage;
    @Element(name = "promValue", required = false)
    protected Long promValue;
    @Element(name = "promValueMax", required = false)
    protected Long promValueMax;
    @Element(name = "status", required = false)
    protected String status;

    @Element(name = "updateDatetime", required = false)
    protected String updateDatetime;
    @Element(name = "updateUser", required = false)
    protected String updateUser;
    @Element(name = "waitMonth", required = false)
    protected String waitMonth;

    public BillingPromotionDetailDTO() {
    }

    public Long getBillValueFrom() {
        return billValueFrom;
    }

    public void setBillValueFrom(Long billValueFrom) {
        this.billValueFrom = billValueFrom;
    }

    public Long getBillValueTo() {
        return billValueTo;
    }

    public void setBillValueTo(Long billValueTo) {
        this.billValueTo = billValueTo;
    }

    public Long getBillingPromotionDetailId() {
        return billingPromotionDetailId;
    }

    public void setBillingPromotionDetailId(Long billingPromotionDetailId) {
        this.billingPromotionDetailId = billingPromotionDetailId;
    }

    public Long getBillingPromotionId() {
        return billingPromotionId;
    }

    public void setBillingPromotionId(Long billingPromotionId) {
        this.billingPromotionId = billingPromotionId;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyTypeName() {
        return moneyTypeName;
    }

    public void setMoneyTypeName(String moneyTypeName) {
        this.moneyTypeName = moneyTypeName;
    }

    public String getPromType() {
        return promType;
    }

    public void setPromType(String promType) {
        this.promType = promType;
    }

    public String getPromTypeName() {
        return promTypeName;
    }

    public void setPromTypeName(String promTypeName) {
        this.promTypeName = promTypeName;
    }

    public String getPromUsage() {
        return promUsage;
    }

    public void setPromUsage(String promUsage) {
        this.promUsage = promUsage;
    }

    public Long getPromValue() {
        return promValue;
    }

    public void setPromValue(Long promValue) {
        this.promValue = promValue;
    }

    public Long getPromValueMax() {
        return promValueMax;
    }

    public void setPromValueMax(Long promValueMax) {
        this.promValueMax = promValueMax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getWaitMonth() {
        return waitMonth;
    }

    public void setWaitMonth(String waitMonth) {
        this.waitMonth = waitMonth;
    }

    public Long getNumMonth() {
        return numMonth;
    }

    public void setNumMonth(Long numMonth) {
        this.numMonth = numMonth;
    }

    public String getPromValueName() {
        return promValueName;
    }

    public void setPromValueName(String promValueName) {
        this.promValueName = promValueName;
    }

    public Long getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Long startMonth) {
        this.startMonth = startMonth;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
