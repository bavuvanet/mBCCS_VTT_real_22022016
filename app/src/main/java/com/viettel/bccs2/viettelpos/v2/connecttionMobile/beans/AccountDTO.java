package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "AccountDTO", strict = false)
public class AccountDTO implements Serializable {
	@Element(name = "accountBank", required = false)
	private AccountBankDTO accountBank;
	@Element(name = "accountId", required = false)
	private Long accountId;
	@Element(name = "accountIdText", required = false)
	private String accountIdText;
	@Element(name = "accountNo", required = false)
	private String accountNo;
	@Element(name = "actionReason", required = false)
	private Long actionReason;
	@Element(name = "addressPayment", required = false)
	private String addressPayment;
	@Element(name = "addressPrint", required = false)
	private String addressPrint;
	@Element(name = "billAddress", required = false)
	private String billAddress;
	@Element(name = "billCycleId", required = false)
	private Long billCycleId;
	@Element(name = "billCycleName", required = false)
	private String billCycleName;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "custId", required = false)
	private Long custId;
	@Element(name = "custType", required = false)
	private String custType;
	@Element(name = "debtStatus", required = false)
	private String debtStatus;
	@Element(name = "debtStatusText", required = false)
	private String debtStatusText;
	@Element(name = "effectDate", required = false)
	private String effectDate;
	@Element(name = "endDatetime", required = false)
	private String endDatetime;
	@Element(name = "isdn", required = false)
	private String isdn;
	@Element(name = "isdnSearch", required = false)
	private String isdnSearch;
	@Element(name = "limitUsage", required = false)
	private Long limitUsage;
	@Element(name = "noticeCharge", required = false)
	private String noticeCharge;
	@Element(name = "noticeChargeAddress", required = false)
	private String noticeChargeAddress;
	@Element(name = "noticeChargeName", required = false)
	private String noticeChargeName;
	@Element(name = "numSubscriber", required = false)
	private Long numSubscriber;
	@Element(name = "payMethod", required = false)
	private String payMethod;
	@Element(name = "payMethodName", required = false)
	private String payMethodName;
	@Element(name = "phoneContact", required = false)
	private String phoneContact;
	@Element(name = "printContractNo", required = false)
	private String printContractNo;
	@Element(name = "printMethod", required = false)
	private String printMethod;
	@Element(name = "printMethodName", required = false)
	private String printMethodName;
	@Element(name = "receiveInvoice", required = false)
	private String receiveInvoice;
	@Element(name = "signDate", required = false)
	private String signDate;
	@Element(name = "statusText", required = false)
	private String statusText;
	@Element(name = "street", required = false)
	private String street;
	@Element(name = "subId", required = false)
	private Long subId;
	@Element(name = "telMobile", required = false)
	private String telMobile;
	@Element(name = "totalDepositSub", required = false)
	private Long totalDepositSub;
	@Element(name = "updateUser", required = false)
	private String updateUser;
	@Element(name = "eMail", required = false)
	private String eMail;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "billCycleTypeDTO", required = false)
    private BillCycleTypeDTO billCycleTypeDTO;
	@Element(name = "refCustomer", required = false)
	private CustomerDTO refCustomer;

	@Element(name = "areaCode", required = false)
	private String areaCode;
	@Element(name = "district", required = false)
	private String district;
	@Element(name = "home", required = false)
	private String home;
	@Element(name = "precinct", required = false)
	private String precinct;
	@Element(name = "province", required = false)
	private String province;
	@Element(name = "streetBlock", required = false)
	private String streetBlock;
	@Element(name = "streetBlockName", required = false)
	private String streetBlockName;
	@Element(name = "streetName", required = false)
	private String streetName;
	@Element(name = "address", required = false)
	private String address;
	@Element(name = "oldAccount", required = false)
    private boolean oldAccount;
//	@Element(name = "errorCode", required = false)
	// lay them addInfo
	@Element(name="addInfo", required = false)
	private String addInfo;
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public boolean isOldAccount() {
		return oldAccount;
	}

//	public String getErrorCode() {
//		return errorCode;
//	}
//
//	public void setErrorCode(String errorCode) {
//		this.errorCode = errorCode;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}

	public void setOldAccount(boolean oldAccount) {
		this.oldAccount = oldAccount;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

	public String getStreetBlockName() {
		return streetBlockName;
	}

	public void setStreetBlockName(String streetBlockName) {
		this.streetBlockName = streetBlockName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AccountBankDTO getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBankDTO accountBank) {
		this.accountBank = accountBank;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
//		this.accountId = null;
        this.accountId = accountId;
	}

	public String getAccountIdText() {
		return accountIdText;
	}

	public void setAccountIdText(String accountIdText) {
		this.accountIdText = accountIdText;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getActionReason() {
		return actionReason;
	}

	public void setActionReason(Long actionReason) {
		this.actionReason = actionReason;
	}

	public String getAddressPayment() {
		return addressPayment;
	}

	public void setAddressPayment(String addressPayment) {
		this.addressPayment = addressPayment;
	}

	public String getAddressPrint() {
		return addressPrint;
	}

	public void setAddressPrint(String addressPrint) {
		this.addressPrint = addressPrint;
	}

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	public Long getBillCycleId() {
		return billCycleId;
	}

	public void setBillCycleId(Long billCycleId) {
		this.billCycleId = billCycleId;
	}

	public String getBillCycleName() {
		return billCycleName;
	}

	public void setBillCycleName(String billCycleName) {
		this.billCycleName = billCycleName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDebtStatus() {
		return debtStatus;
	}

	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus;
	}

	public String getDebtStatusText() {
		return debtStatusText;
	}

	public void setDebtStatusText(String debtStatusText) {
		this.debtStatusText = debtStatusText;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getIsdnSearch() {
		return isdnSearch;
	}

	public void setIsdnSearch(String isdnSearch) {
		this.isdnSearch = isdnSearch;
	}

	public Long getLimitUsage() {
		return limitUsage;
	}

	public void setLimitUsage(Long limitUsage) {
		this.limitUsage = limitUsage;
	}

	public String getNoticeCharge() {
		return noticeCharge;
	}

	public void setNoticeCharge(String noticeCharge) {
		this.noticeCharge = noticeCharge;
	}

	public String getNoticeChargeAddress() {
		return noticeChargeAddress;
	}

	public void setNoticeChargeAddress(String noticeChargeAddress) {
		this.noticeChargeAddress = noticeChargeAddress;
	}

	public String getNoticeChargeName() {
		return noticeChargeName;
	}

	public void setNoticeChargeName(String noticeChargeName) {
		this.noticeChargeName = noticeChargeName;
	}

	public Long getNumSubscriber() {
		return numSubscriber;
	}

	public void setNumSubscriber(Long numSubscriber) {
		this.numSubscriber = numSubscriber;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getPayMethodName() {
		return payMethodName;
	}

	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}

	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	public String getPrintContractNo() {
		return printContractNo;
	}

	public void setPrintContractNo(String printContractNo) {
		this.printContractNo = printContractNo;
	}

	public String getPrintMethod() {
		return printMethod;
	}

	public void setPrintMethod(String printMethod) {
		this.printMethod = printMethod;
	}

	public String getPrintMethodName() {
		return printMethodName;
	}

	public void setPrintMethodName(String printMethodName) {
		this.printMethodName = printMethodName;
	}

	public String getReceiveInvoice() {
		return receiveInvoice;
	}

	public void setReceiveInvoice(String receiveInvoice) {
		this.receiveInvoice = receiveInvoice;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getTelMobile() {
		return telMobile;
	}

	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	public Long getTotalDepositSub() {
		return totalDepositSub;
	}

	public void setTotalDepositSub(Long totalDepositSub) {
		this.totalDepositSub = totalDepositSub;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BillCycleTypeDTO getBillCycleTypeDTO() {
		return billCycleTypeDTO;
	}

	public void setBillCycleTypeDTO(BillCycleTypeDTO billCycleTypeDTO) {
		this.billCycleTypeDTO = billCycleTypeDTO;
	}

	public CustomerDTO getRefCustomer() {
		return refCustomer;
	}

	public void setRefCustomer(CustomerDTO refCustomer) {
		this.refCustomer = refCustomer;
	}

	// billCycleId = {Long@41203} "1"
	// noticeCharge = {String@42258} "3"
	// noticeChargeName = {String@42259} "Nhận thông báo cước trực tiếp"
	// payMethod = {String@42260} "00"
	// payMethodName = {String@42261} "Thu tại nhà"
	// printMethod = {String@42262} "1"
	// printMethodName = {String@42263} "In bản kê chi tiết cước kèm TBC"
	// signDate = {Date@42265} "Thu Aug 04 00:00:00 ICT 2016"
	// address = {String@42271} "Tổ 1, Chương Dương Độ Hoàn Kiếm Hà Nội"
	// areaCode = {String@42272} "H004002001001"
	// province = {String@42273} "H004"
	// district = {String@42274} "002"
	// precinct = {String@42275} "001"
	// streetBlock = {String@42276} "001"
	// streetName = {String@42277} ""
	// home = {String@42278} ""
	// streetBlockName = {String@42279} "Tổ 1"

	public String toXml() {
		StringBuilder sb = new StringBuilder();

		if (!CommonActivity.isNullOrEmpty(accountId)
				&& !CommonActivity.isNullOrEmpty(accountNo)) {
			sb.append("<accountNo>").append(accountNo);
			sb.append("</accountNo>");

			if (!CommonActivity.isNullOrEmpty(status)) {
				sb.append("<status>").append(status);
				sb.append("</status>");
			}
			sb.append("<oldAccount>" + true);
			sb.append("</oldAccount>");
		}

		if (accountId != null) {
			sb.append("<accountId>").append(accountId);
			sb.append("</accountId>");
		}
		sb.append("<billCycleId>").append(billCycleId);
		sb.append("</billCycleId>");

		sb.append("<noticeCharge>").append(noticeCharge);
		sb.append("</noticeCharge>");

		if (!CommonActivity.isNullOrEmpty(noticeChargeName)) {
			try {
				sb.append("<noticeChargeName>").append(StringUtils.escapeHtml(noticeChargeName));
				sb.append("</noticeChargeName>");
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		sb.append("<payMethod>").append(payMethod);
		sb.append("</payMethod>");
		if (!CommonActivity.isNullOrEmpty(payMethodName)) {
			try {
				sb.append("<payMethodName>").append(StringUtils.escapeHtml(payMethodName));
				sb.append("</payMethodName>");
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		sb.append("<eMail>").append(eMail);
		sb.append("</eMail>");

		sb.append("<telMobile>").append(telMobile);
		sb.append("</telMobile>");

		sb.append("<phoneContact>").append(phoneContact);
		sb.append("</phoneContact>");

		sb.append("<addressPrint>").append(addressPrint);
		sb.append("</addressPrint>");

		sb.append("<printMethod>").append(printMethod);
		sb.append("</printMethod>");

		if (!CommonActivity.isNullOrEmpty(printMethodName)) {
			sb.append("<printMethodName>").append(printMethodName);
			sb.append("</printMethodName>");
		}
		if (accountId != null) {
			sb.append("<signDate>").append(signDate);
			sb.append("</signDate>");
		} else {
			sb.append("<signDate>").append(signDate).append("T00:00:00+07:00");
			sb.append("</signDate>");
		}

		sb.append("<address>").append(address);
		sb.append("</address>");

		sb.append("<areaCode>").append(areaCode);
		sb.append("</areaCode>");

		sb.append("<province>").append(province);
		sb.append("</province>");

		sb.append("<district>").append(district);
		sb.append("</district>");

		sb.append("<precinct>").append(precinct);
		sb.append("</precinct>");

		sb.append("<streetBlock>").append(streetBlock);
		sb.append("</streetBlock>");

		if (!CommonActivity.isNullOrEmpty(streetName)) {
			sb.append("<streetName>").append(streetName);
			sb.append("</streetName>");
		}

		if (!CommonActivity.isNullOrEmpty(home)) {
			sb.append("<home>").append(home);
			sb.append("</home>");
		}
		// streetBlockName = {String@42279} "Tổ 1"

		return sb.toString();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return accountNo;
	}

}
