package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by huypq15 on 4/25/2017.
 */
@Root(strict = false)
public class ConfirmDebitTransDTO implements Serializable{

    @Element(name = "id", required = false)
    private Long id;
    @Element (name = "approveDate", required = false)
    private String approveDate;
    @Element (name = "approveUserId", required = false)
    private Long approveUserId;
    @Element (name = "approveUserName", required = false)
    private String approveUserName;
    @Element (name = "balanceDifferent", required = false)
    private Long balanceDifferent;
    @Element (name = "balanceDifferentReason", required = false)
    private String balanceDifferentReason;
    @Element (name = "billCycle", required = false)
    private String billCycle;
    @Element (name = "billCycleAmount", required = false)
    private Long billCycleAmount;
    @Element (name = "billCycleString", required = false)
    private String billCycleString;
    @Element (name = "confirmBalance", required = false)
    private Long confirmBalance;
    @Element (name = "confirmDate", required = false)
    private String confirmDate;
    @Element (name = "confirmPay", required = false)
    private Long confirmPay;
    @Element (name = "confirmUserId", required = false)
    private Long confirmUserId;
    @Element (name = "confirmUserName", required = false)
    private String confirmUserName;
    @Element (name = "createDate", required = false)
    private String createDate;
    @Element (name = "debitType", required = false)
    private Long debitType;
    @Element (name = "debitTypeDetail", required = false)
    private Long debitTypeDetail;
    @Element (name = "debitTypeDetailName", required = false)
    private String debitTypeDetailName;
    @Element (name = "endCycleAmount", required = false)
    private Long endCycleAmount;
    @Element (name = "endCycleAmountConfirm", required = false)
    private Long endCycleAmountConfirm;
    @Element (name = "endCycleBalance", required = false)
    private Long endCycleBalance;
    @Element (name = "endCycleBalanceConfirm", required = false)
    private Long endCycleBalanceConfirm;
    @Element (name = "getCycleBalance", required = false)
    private Long getCycleBalance;
    @Element (name = "lastUpdate", required = false)
    private String lastUpdate;
    @Element (name = "ownerId", required = false)
    private Long ownerId;
    @Element (name = "ownerName", required = false)
    private String ownerName;
    @Element (name = "ownerType", required = false)
    private Long ownerType;
    @Element (name = "payCycleAmount", required = false)
    private Long payCycleAmount;
    @Element (name = "payDifferent", required = false)
    private Long payDifferent;
    @Element (name = "payDifferentReason", required = false)
    private String payDifferentReason;
    @Element (name = "shopName", required = false)
    private String shopName;
    @Element (name = "staOfCycleAmount", required = false)
    private Long staOfCycleAmount;
    @Element (name = "staOfCycleBalance", required = false)
    private Long staOfCycleBalance;
    @Element (name = "status", required = false)
    private Long status;
    @Element (name = "statusName", required = false)
    private String statusName;
    @Element (name = "addCycleBalance", required = false)
    private Long addCycleBalance;

    public Long getAddCycleBalance() {
        return addCycleBalance;
    }

    public void setAddCycleBalance(Long addCycleBalance) {
        this.addCycleBalance = addCycleBalance;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public Long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public Long getBalanceDifferent() {
        return balanceDifferent;
    }

    public void setBalanceDifferent(Long balanceDifferent) {
        this.balanceDifferent = balanceDifferent;
    }

    public String getBalanceDifferentReason() {


        return balanceDifferentReason == null ? "":balanceDifferentReason;
    }

    public void setBalanceDifferentReason(String balanceDifferentReason) {
        this.balanceDifferentReason = balanceDifferentReason;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }

    public Long getBillCycleAmount() {
        return billCycleAmount;
    }

    public void setBillCycleAmount(Long billCycleAmount) {
        this.billCycleAmount = billCycleAmount;
    }

    public String getBillCycleString() {
        return billCycleString;
    }

    public void setBillCycleString(String billCycleString) {
        this.billCycleString = billCycleString;
    }

    public Long getConfirmBalance() {
        return confirmBalance;
    }

    public void setConfirmBalance(Long confirmBalance) {
        this.confirmBalance = confirmBalance;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getConfirmPay() {
        return confirmPay;
    }

    public void setConfirmPay(Long confirmPay) {
        this.confirmPay = confirmPay;
    }

    public Long getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Long confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public String getConfirmUserName() {
        return confirmUserName;
    }

    public void setConfirmUserName(String confirmUserName) {
        this.confirmUserName = confirmUserName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getDebitType() {
        return debitType;
    }

    public void setDebitType(Long debitType) {
        this.debitType = debitType;
    }

    public Long getDebitTypeDetail() {
        return debitTypeDetail;
    }

    public void setDebitTypeDetail(Long debitTypeDetail) {
        this.debitTypeDetail = debitTypeDetail;
    }

    public String getDebitTypeDetailName() {
        return debitTypeDetailName;
    }

    public void setDebitTypeDetailName(String debitTypeDetailName) {
        this.debitTypeDetailName = debitTypeDetailName;
    }

    public Long getEndCycleAmount() {
        return endCycleAmount;
    }

    public void setEndCycleAmount(Long endCycleAmount) {
        this.endCycleAmount = endCycleAmount;
    }

    public Long getEndCycleAmountConfirm() {
        return endCycleAmountConfirm;
    }

    public void setEndCycleAmountConfirm(Long endCycleAmountConfirm) {
        this.endCycleAmountConfirm = endCycleAmountConfirm;
    }

    public Long getEndCycleBalance() {
        if(endCycleBalance == null){
            return 0L;
        }
        return endCycleBalance;
    }

    public void setEndCycleBalance(Long endCycleBalance) {
        this.endCycleBalance = endCycleBalance;
    }

    public Long getEndCycleBalanceConfirm() {
        return endCycleBalanceConfirm;
    }

    public void setEndCycleBalanceConfirm(Long endCycleBalanceConfirm) {
        this.endCycleBalanceConfirm = endCycleBalanceConfirm;
    }

    public Long getGetCycleBalance() {
        return getCycleBalance;
    }

    public void setGetCycleBalance(Long getCycleBalance) {
        this.getCycleBalance = getCycleBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getPayCycleAmount() {
        return payCycleAmount;
    }

    public void setPayCycleAmount(Long payCycleAmount) {
        this.payCycleAmount = payCycleAmount;
    }

    public Long getPayDifferent() {
        return payDifferent;
    }

    public void setPayDifferent(Long payDifferent) {
        this.payDifferent = payDifferent;
    }

    public String getPayDifferentReason() {
        return payDifferentReason == null ? "":payDifferentReason;
    }

    public void setPayDifferentReason(String payDifferentReason) {
        this.payDifferentReason = payDifferentReason;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStaOfCycleAmount() {
        return staOfCycleAmount;
    }

    public void setStaOfCycleAmount(Long staOfCycleAmount) {
        this.staOfCycleAmount = staOfCycleAmount;
    }

    public Long getStaOfCycleBalance() {
        if(staOfCycleBalance == null){
            return 0l;
        }
        return staOfCycleBalance;
    }

    public void setStaOfCycleBalance(Long staOfCycleBalance) {
        this.staOfCycleBalance = staOfCycleBalance;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
