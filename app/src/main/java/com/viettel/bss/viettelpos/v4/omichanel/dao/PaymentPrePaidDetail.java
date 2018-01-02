
package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

public class PaymentPrePaidDetail implements Serializable {

    protected String discountFromTo;
    protected Long endMonth;
    protected Long id;
    protected String itemCode;
    protected String moneyType;
    protected String moneyUnit;
    protected Long monthPrePaid;
    protected String packageCode;
    protected Long prePaidAmount;
    protected Long promAmount;
    protected String promDesc;
    protected Long promId;
    protected String promProgramCode;
    protected String promType;
    protected String provinceCode;
    protected Long startMonth;
    protected String status;
    protected Long subMonth;
    protected Long totalMoney;

    private String discountAmount;

    public String getDiscountFromTo() {
        return discountFromTo;
    }

    public void setDiscountFromTo(String discountFromTo) {
        this.discountFromTo = discountFromTo;
    }

    public Long getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Long endMonth) {
        this.endMonth = endMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public Long getMonthPrePaid() {
        return monthPrePaid;
    }

    public void setMonthPrePaid(Long monthPrePaid) {
        this.monthPrePaid = monthPrePaid;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Long getPrePaidAmount() {
        return prePaidAmount;
    }

    public void setPrePaidAmount(Long prePaidAmount) {
        this.prePaidAmount = prePaidAmount;
    }

    public Long getPromAmount() {
        return promAmount;
    }

    public void setPromAmount(Long promAmount) {
        this.promAmount = promAmount;
    }

    public String getPromDesc() {
        return promDesc;
    }

    public void setPromDesc(String promDesc) {
        this.promDesc = promDesc;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public String getPromProgramCode() {
        return promProgramCode;
    }

    public void setPromProgramCode(String promProgramCode) {
        this.promProgramCode = promProgramCode;
    }

    public String getPromType() {
        return promType;
    }

    public void setPromType(String promType) {
        this.promType = promType;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Long getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Long startMonth) {
        this.startMonth = startMonth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubMonth() {
        return subMonth;
    }

    public void setSubMonth(Long subMonth) {
        this.subMonth = subMonth;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }
}
