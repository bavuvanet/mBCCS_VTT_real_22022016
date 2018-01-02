package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StaffDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Lamnv5 on 8/23/2017.
 */
public class OmniOrder implements Serializable {
//    private RequiredRoleMap requiredRoleMap;// doi tuong phan quyen tren sale
//    private ActionUserDTO actionUserDTO;// do1i tuong user tren sale
    private StaffDTO staffDTO;// doi tuong staff tren sale
    private CustomerDTO customerDTO;// doi tuong customer tren sale
    private SubscriberDTO subscriberDTO;// doi tuong thue bao tren sale
//    private ProductSpecInfoDTO productSpecInfoDTO;// doi tuong thong tin dac biet tren sale
//    private ChannelDTO channelDTO;// doi tuong kenh tren sale
    private Long userLoginId;// staffId cua user loggin
    private OmniAddress address; //Dia chi thuc hien giao dich
    private String transactionPlace; //Phuong thuc giao dich
    private Long handleShop; //Cua hang xu ly
    private OmniPayInfo payInfo; //Thong tin thanh toan
    private Long totalFee; //Tong so tien dich vu
    private List<OmniFeeInfo> feeRecords; //TRANSFER_FEE : phi van chuyen, PAY_FEE : phi thu ho, TOP: tien nap ngay
    private String recipientName; //Ten nguoi nhan hang
    private String recipientPhone; //SDT nguoi nhan hang
    private List<OmniProfileRecord> profileRecords; //Danh sach ho so
    private List<OmniProfileRecord> profileRecordsView; //Danh sach ho so de view that tren giao dien
    private String myViettelAccount; //So dien thoai ben MyViettel
    private String processInstanceId; //Id Process
    private String orderCode;
    private Date planFrom; //Tu ngay
    private Date planTo; //Den ngay
    private Date createDate;
    private Long payStatus;
    private Long staffId;
    private Long status;
    private String statusDesc;
    private String orderType;
    private String orderTypeDesc;
    private String orderAceept;// tiep nhan yeu cau tu he thong nao: nhap day du thong tin khach hang, thue bao, hop dong ...
    private String taskId;
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private Long deadlineStatus;
    private boolean allowEdit;
    private boolean allowUpdate;
    private boolean allowUpdateCustomer;
    private boolean allowUpdateProduct;
    private boolean allowUpdateHandleShop;
    private boolean allowCancel;
    private boolean isChangeInfraReq;// check xem co phai yeu cau chuyen doi cong nghe hay khong
    private String roleModifyBlockSpam;// role check chan mo spam
    private String workMode;//
    private String isTransfer;// check chuyen nhuong


    // OMNI CONNECTION ORDER
    private String isdn;//So thue bao dau noi
    private OmniProductInfo productInfo;//Thong tin san pham
    private OmniIsdnPledgeInfo isdnPledgeInfo;
    private boolean needChargeCard;//Co nap the ngay khong?
    private Long chargeCardAmound; //Menh gia the nap
    private OmniCustomer customer; //Thong tin khach hang

    private String staffCode;
//    private List<FeeTranDTO> saleFeeInfos; // convert toan bo cac loai phi cua omni de hien thi cho de
    private String payStatusDesc;
    private String contractId;

    // OMNI CHANGEPREPAID ORDER
    private List<OmniAccountPrepaidInfo> accountPrepaidRecords;
    private String fixRevenueStaffCode;//Staff code dung de goi ham thay doi CDT cua CM (fix tu myViettel)
    private Long fixReasonId; //Ly do dung de goi ham thay doi CDT cua CM (fix tu myViettel)

    //c??c ?�ng tr??c
    private String contractNo;

    private OmniProductInfo bundleProduct; //Thong tin goi bundle
    private List<OmniAccountAndProductInfo> accountRecords; //Danh sach thue bao dau noi


    private String bpState;
    private String target;
    private String orderCmInfo;
    private List<UploadDocumentDTO> uploadFiles;



    public OmniAddress getAddress() {
        return address;
    }

    public void setAddress(OmniAddress address) {
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

    public OmniPayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(OmniPayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public List<OmniFeeInfo> getFeeRecords() {
        return feeRecords;
    }

    public void setFeeRecords(List<OmniFeeInfo> feeRecords) {
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

    public List<OmniProfileRecord> getProfileRecords() {
        return profileRecords;
    }

    public void setProfileRecords(List<OmniProfileRecord> profileRecords) {
        this.profileRecords = profileRecords;
    }

    public List<OmniProfileRecord> getProfileRecordsView() {
        return profileRecordsView;
    }

    public void setProfileRecordsView(List<OmniProfileRecord> profileRecordsView) {
        this.profileRecordsView = profileRecordsView;
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

    public Date getPlanFrom() {
        return planFrom;
    }

    public void setPlanFrom(Date planFrom) {
        this.planFrom = planFrom;
    }

    public Date getPlanTo() {
        return planTo;
    }

    public void setPlanTo(Date planTo) {
        this.planTo = planTo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
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

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public boolean isAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(boolean allowCancel) {
        this.allowCancel = allowCancel;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public OmniProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(OmniProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public OmniIsdnPledgeInfo getIsdnPledgeInfo() {
        return isdnPledgeInfo;
    }

    public void setIsdnPledgeInfo(OmniIsdnPledgeInfo isdnPledgeInfo) {
        this.isdnPledgeInfo = isdnPledgeInfo;
    }

    public boolean isNeedChargeCard() {
        return needChargeCard;
    }

    public void setNeedChargeCard(boolean needChargeCard) {
        this.needChargeCard = needChargeCard;
    }

    public Long getChargeCardAmound() {
        return chargeCardAmound;
    }

    public void setChargeCardAmound(Long chargeCardAmound) {
        this.chargeCardAmound = chargeCardAmound;
    }

    public OmniCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(OmniCustomer customer) {
        this.customer = customer;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

//    public List<FeeTranDTO> getSaleFeeInfos() {
//        return saleFeeInfos;
//    }
//
//    public void setSaleFeeInfos(List<FeeTranDTO> saleFeeInfos) {
//        this.saleFeeInfos = saleFeeInfos;
//    }

    public String getPayStatusDesc() {
        return payStatusDesc;
    }

    public void setPayStatusDesc(String payStatusDesc) {
        this.payStatusDesc = payStatusDesc;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<OmniAccountPrepaidInfo> getAccountPrepaidRecords() {
        return accountPrepaidRecords;
    }

    public void setAccountPrepaidRecords(List<OmniAccountPrepaidInfo> accountPrepaidRecords) {
        this.accountPrepaidRecords = accountPrepaidRecords;
    }

    public String getFixRevenueStaffCode() {
        return fixRevenueStaffCode;
    }

    public void setFixRevenueStaffCode(String fixRevenueStaffCode) {
        this.fixRevenueStaffCode = fixRevenueStaffCode;
    }

    public Long getFixReasonId() {
        return fixReasonId;
    }

    public void setFixReasonId(Long fixReasonId) {
        this.fixReasonId = fixReasonId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public boolean isAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public boolean isAllowUpdateCustomer() {
        return allowUpdateCustomer;
    }

    public void setAllowUpdateCustomer(boolean allowUpdateCustomer) {
        this.allowUpdateCustomer = allowUpdateCustomer;
    }

    public boolean isAllowUpdateProduct() {
        return allowUpdateProduct;
    }

    public void setAllowUpdateProduct(boolean allowUpdateProduct) {
        this.allowUpdateProduct = allowUpdateProduct;
    }

    public boolean isAllowUpdateHandleShop() {
        return allowUpdateHandleShop;
    }

    public void setAllowUpdateHandleShop(boolean allowUpdateHandleShop) {
        this.allowUpdateHandleShop = allowUpdateHandleShop;
    }

    public OmniProductInfo getBundleProduct() {
        return bundleProduct;
    }

    public void setBundleProduct(OmniProductInfo bundleProduct) {
        this.bundleProduct = bundleProduct;
    }

    public List<OmniAccountAndProductInfo> getAccountRecords() {
        return accountRecords;
    }

    public void setAccountRecords(List<OmniAccountAndProductInfo> accountRecords) {
        this.accountRecords = accountRecords;
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
}
