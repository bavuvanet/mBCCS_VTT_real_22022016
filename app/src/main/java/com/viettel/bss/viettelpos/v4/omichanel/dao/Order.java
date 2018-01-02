package com.viettel.bss.viettelpos.v4.omichanel.dao;


import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lamnv5 on 8/23/2017.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 0L;

    public static final String CHECKIN_ABLE = "receptionistReceiveRequest";
    public static final String RECEIPTION_ABLE = "tellerProcessOrder";
    public static final String TRANSFER_FEE = "TRANSFER_FEE";
    public static final String PAY_FEE = "PAY_FEE";

    public static final String TARGET_SHOP = "SHOP";
    public static final String TARGET_OMNI = "OMNI";
    public static final String PLACE_HOME = "HOME";
    public static final String PLACE_SHOP = "SHOP";
    public static final Long DEADLINE_STATUS_NORMAL = 1L;
    public static final Long DEADLINE_STATUS_ALERT = 2L;
    public static final Long DEADLINE_STATUS_OVER = 3L;

    private Customer customer; //Thong tin khach hang
    private Address address; //Dia chi thuc hien giao dich
    private String transactionPlace; //Phuong thuc giao dich
    private Long handleShop; //Cua hang xu ly
    private PayInfo payInfo; //Thong tin thanh toan
    private Long totalFee; //Tong so tien dich vu
    private List<FeeInfo> feeRecords; //TRANSFER_FEE : phi van chuyen, PAY_FEE : phi thu ho, TOP: tien nap ngay
    private String recipientName; //Ten nguoi nhan hang
    private String recipientPhone; //SDT nguoi nhan hang
    private List<ProfileRecord> profileRecords; //Danh sach ho so
    private String myViettelAccount; //So dien thoai ben MyViettel
    private String processInstanceId; //Id Process
    private String orderCode;
    private Long planFrom; //Tu ngay
    private Long planTo; //Den ngay
    private Long createDate;
    private Long payStatus;
    private Long staffId;
    private Long status;
    private String statusDesc;
    private String orderType;
    private String orderTypeDesc;
    private String taskId;
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private Long deadlineStatus;
    private Boolean allowUpdate;
    private Boolean allowUpdateCustomer;
    private Boolean allowUpdateProduct;
    private Boolean allowUpdateHandleShop;
    private Boolean allowCancel;
    private String bpState;
    private String target;

    public static String getCheckinAble() {
        return CHECKIN_ABLE;
    }

    public static String getReceiptionAble() {
        return RECEIPTION_ABLE;
    }

    public static String getTransferFee() {
        return TRANSFER_FEE;
    }

    public static String getPayFee() {
        return PAY_FEE;
    }

    public static String getTargetShop() {
        return TARGET_SHOP;
    }

    public static String getTargetOmni() {
        return TARGET_OMNI;
    }

    public static String getPlaceHome() {
        return PLACE_HOME;
    }

    public static String getPlaceShop() {
        return PLACE_SHOP;
    }

    public static Long getDeadlineStatusNormal() {
        return DEADLINE_STATUS_NORMAL;
    }

    public static Long getDeadlineStatusAlert() {
        return DEADLINE_STATUS_ALERT;
    }

    public static Long getDeadlineStatusOver() {
        return DEADLINE_STATUS_OVER;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTransactionPlace() {
        return transactionPlace;
    }

    public void setTransactionPlace(String transactionPlace) {
        this.transactionPlace = transactionPlace;
    }

    public Long getHandleShop() {
        return handleShop;
    }

    public void setHandleShop(Long handleShop) {
        this.handleShop = handleShop;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public List<FeeInfo> getFeeRecords() {
        return feeRecords;
    }

    public void setFeeRecords(List<FeeInfo> feeRecords) {
        this.feeRecords = feeRecords;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public List<ProfileRecord> getProfileRecords() {
        if (profileRecords == null) {
            profileRecords = new ArrayList<>();
        }

        return profileRecords;
    }

    public void setProfileRecords(List<ProfileRecord> profileRecords) {
        this.profileRecords = profileRecords;
    }

    public String getMyViettelAccount() {
        return myViettelAccount;
    }

    public void setMyViettelAccount(String myViettelAccount) {
        this.myViettelAccount = myViettelAccount;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getPlanFrom() {
        return planFrom;
    }

    public void setPlanFrom(Long planFrom) {
        this.planFrom = planFrom;
    }

    public Long getPlanTo() {
        return planTo;
    }

    public void setPlanTo(Long planTo) {
        this.planTo = planTo;
    }

    public Date getCreateDate() {
        return new Date(createDate);
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Long getDeadlineStatus() {
        return deadlineStatus;
    }

    public void setDeadlineStatus(Long deadlineStatus) {
        this.deadlineStatus = deadlineStatus;
    }

    public Boolean getAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(Boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public Boolean getAllowUpdateCustomer() {
        return allowUpdateCustomer;
    }

    public void setAllowUpdateCustomer(Boolean allowUpdateCustomer) {
        this.allowUpdateCustomer = allowUpdateCustomer;
    }

    public Boolean getAllowUpdateProduct() {
        return allowUpdateProduct;
    }

    public void setAllowUpdateProduct(Boolean allowUpdateProduct) {
        this.allowUpdateProduct = allowUpdateProduct;
    }

    public Boolean getAllowUpdateHandleShop() {
        return allowUpdateHandleShop;
    }

    public void setAllowUpdateHandleShop(Boolean allowUpdateHandleShop) {
        this.allowUpdateHandleShop = allowUpdateHandleShop;
    }

    public Boolean getAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(Boolean allowCancel) {
        this.allowCancel = allowCancel;
    }

    public String getBpState() {
        return bpState;
    }

    public void setBpState(String bpState) {
        this.bpState = bpState;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getFeeByCode(String code) {
        for (FeeInfo feeInfo : feeRecords) {
            if (code.equals(feeInfo.getFeeCode())) return feeInfo.getFeeAmount();
        }
        return 0l;
    }
}
