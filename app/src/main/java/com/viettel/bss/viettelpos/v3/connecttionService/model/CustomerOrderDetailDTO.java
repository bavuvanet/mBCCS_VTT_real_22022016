package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.FullAddressDTO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

@Root(name = "CustomerOrderDetailDTO", strict = false)
public class CustomerOrderDetailDTO implements Serializable {
	@Element(name = "lineType", required = false)
	private String lineType;
	@Element(name = "actionCode", required = false)
	private String actionCode;
	@Element(name = "contactName", required = false)
	private String contactName;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "custOrderDetailId", required = false)
	private Long custOrderDetailId;
	@Element(name = "custOrderId", required = false)
	private Long custOrderId;
	@Element(name = "telecomServiceId", required = false)
	private Long telecomServiceId;
	@Element(name = "telecomServiceIdOld", required = false)
	private Long telecomServiceIdOld;
	@Element(name = "oldTelecomServiceName", required = false)
	private String oldTelecomServiceName;
	@Element(name = "oldTechnology", required = false)
	private String oldTechnology;
	@Element(name = "telecomServiceName", required = false)
	private String telecomServiceName;
	@ElementList(name = "moreServicesAlias", entry = "moreServicesAlias", required = false, inline = true)
	private List<String> moreServicesAlias;
	@Element(name = "oldSubId", required = false)
	private Long oldSubId;
	@Element(name = "oldSubDTO", required = false)
	private SubscriberDTO oldSubDTO;
	@Element(name = "oldGroupId", required = false)
	private Long oldGroupId;
	@Element(name = "requestExtId", required = false)
	private Long requestExtId;
	@Element(name = "sourceType", required = false)
	private String sourceType;
	@Element(name = "subInfrastructureDTO", required = false)
	private SubInfrastructureDTO subInfrastructureDTO;
	@Element(name = "resultSurveyOnlineFormDTO", required = false)
	private ResultSurveyOnlineFormDTO resultSurveyOnlineFormDTO;
	@Element(name = "deployAddress", required = false)
	private String deployAddress;
	@Element(name = "deployAreaCode", required = false)
	private String deployAreaCode;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "devStaffCode", required = false)
	private String devStaffCode;
	@Element(name = "effectDate", required = false)
	private String effectDate;
	@Element(name = "requestDate", required = false)
	private String requestDate;
	@Element(name = "idNo", required = false)
	private String idNo;
	@Element(name = "ipStatic", required = false)
	private String ipStatic;
	@Element(name = "isdnAccount", required = false)
	private String isdnAccount;
	@ElementList(name = "listIsdnAccount", entry = "listIsdnAccount", required = false, inline = true)
	private List<String> listIsdnAccount;
	@Element(name = "limitDate", required = false)
	private String limitDate;
	@Element(name = "notificationLevel", required = false)
	private Short notificationLevel;
	@Element(name = "offerId", required = false)
	private Long offerId;
	@Element(name = "productCode", required = false)
	private String productCode;
	@Element(name = "promotionCode", required = false)
	private String promotionCode;
	@Element(name = "shopCode", required = false)
	private String shopCode;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "statusTmp", required = false)
	private String statusTmp;
	@Element(name = "subId", required = false)
	private Long subId;
	@Element(name = "imRequest", required = false)
	private Long imRequest;
	@Element(name = "numberCoupler", required = false)
	private Long numberCoupler;
	@Element(name = "reasonId", required = false)
	private Long reasonId;
	@Element(name = "actionAuditId", required = false)
	private Long actionAuditId;
	@Element(name = "telMobile", required = false)
	private String telMobile;
	@Element(name = "statusCode", required = false)
	private String statusCode;
	@Element(name = "resultTest", required = false)
	private String resultTest;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "taskName", required = false)
	private String taskName;
	@Element(name = "enableAsign", required = false)
	private boolean enableAsign;
	@Element(name = "disableCancel", required = false)
	private boolean disableCancel;
	@Element(name = "serviceKt", required = false)
	private String serviceKt;
	@Element(name = "chooseSubExit", required = false)
	private boolean chooseSubExit;
	@Element(name = "isdnAccountOld", required = false)
	private String isdnAccountOld;
	@Element(name = "custNameOld", required = false)
	private String custNameOld;
	@Element(name = "needRetake", required = false)
	private boolean needRetake;
	@Element(name = "retakeAccount", required = false)
	private String retakeAccount;
	@Element(name = "userSelectedISDNBefore", required = false)
	private boolean userSelectedISDNBefore;
	@Element(name = "accountNo", required = false)
	private String accountNo;
	@Element(name = "shortNumber", required = false)
	private String shortNumber;
	@Element(name = "main", required = false)
	private String main;
	@Element(name = "newIsdn", required = false)
	private String newIsdn;
	@Element(name = "vasName", required = false)
	private String vasName;
	@Element(name = "infoTask", required = false)
	private String infoTask;
	@Element(name = "strCreateDate", required = false)
	private String strCreateDate;
	@Element(name = "stationCode", required = false)
	private String stationCode;
	@Element(name = "stationId", required = false)
	private Long stationId;
	@Element(name = "cableBoxCode", required = false)
	private String cableBoxCode;
	@Element(name = "isMoveAPAttach", required = false)
	private boolean isMoveAPAttach;
	@Element(name = "gponGroupAccount", required = false)
	private String gponGroupAccount;
	@Element(name = "moveAllLine", required = false)
	private boolean moveAllLine;
	@Element(name = "technology", required = false)
	private String technology;
	@Element(name = "selectedRow", required = false)
	private boolean selectedRow;
	@Element(name = "sourceId", required = false)
	private Long sourceId;
	@Element(name = "type", required = false)
	private String type;
	@ElementList(name = "lstMoreServiceSubId", entry = "lstMoreServiceSubId", required = false, inline = true)
	private List<Long> lstMoreServiceSubId;
	@Element(name = "subscriberDTO", required = false)
	private SubscriberDTO subscriberDTO;
	@Element(name = "groupsDTO", required = false)
	private GroupsDTO groupsDTO;
	@Element(name = "technologyDTO", required = false)
	private OptionSetValueDTO technologyDTO;
	@Element(name = "ndCustomerOrderDetailDTO", required = false)
	private CustomerOrderDetailDTO ndCustomerOrderDetailDTO;
	@Element(name = "teamCode", required = false)
	private String teamCode;
	@Element(name = "deviceType", required = false)
	private Long deviceType;
	
	@ElementList(name = "listCustIdentity", entry = "listCustIdentity", required = false, inline = true)
	private List<CustIdentityDTO> listCustIdentity;
	@ElementList(name = "lstChildSubId", entry = "lstChildSubId", required = false, inline = true)
	private List<CustomerOrderDetailDTO> lstChildSubId;
	@Element(name = "fullDeploymentAddress", required = false)
	private FullAddressDTO fullDeploymentAddress;
	@Element(name = "pstn", required = false)
	private String pstn;
	@Element(name = "ngn", required = false)
	private String ngn;
	@Element(name = "selectedInfoDeploy", required = false)
	private boolean selectedInfoDeploy;
	@Element(name = "glineExist", required = false)
	private Long glineExist;
	@Element(name = "tooltip", required = false)
	private String tooltip;
	@Element(name = "renderCommandLink", required = false)
	private boolean renderCommandLink;
	@Element(name = "trunkDeptutizeNumber", required = false)
	private String trunkDeptutizeNumber;
	@Element(name = "isConnectNA", required = false)
	private boolean isConnectNA;
	@Element(name = "retakeOnuOtherVendor", required = false)
	private String retakeOnuOtherVendor;
	@Element(name = "vendor", required = false)
	private String vendor;
	@Element(name = "retake", required = false)
	private Long retake;
	@Element(name = "staffIdNo", required = false)
	private String staffIdNo;
	@Element(name = "staffIdNoEnd", required = false)
	private String staffIdNoEnd;
	@Element(name = "sourceName", required = false)
	private String sourceName;
	@Element(name = "vendorOld", required = false)
	private String vendorOld;
	
	@ElementList(name = "listStatus", entry = "listStatus", required = false, inline = true)
	private List<String> listStatus;

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCustOrderDetailId() {
		return custOrderDetailId;
	}

	public void setCustOrderDetailId(Long custOrderDetailId) {
		this.custOrderDetailId = custOrderDetailId;
	}

	public Long getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(Long custOrderId) {
		this.custOrderId = custOrderId;
	}

	public Long getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public Long getTelecomServiceIdOld() {
		return telecomServiceIdOld;
	}

	public void setTelecomServiceIdOld(Long telecomServiceIdOld) {
		this.telecomServiceIdOld = telecomServiceIdOld;
	}

	public String getOldTelecomServiceName() {
		return oldTelecomServiceName;
	}

	public void setOldTelecomServiceName(String oldTelecomServiceName) {
		this.oldTelecomServiceName = oldTelecomServiceName;
	}

	public String getOldTechnology() {
		return oldTechnology;
	}

	public void setOldTechnology(String oldTechnology) {
		this.oldTechnology = oldTechnology;
	}

	public String getTelecomServiceName() {
		return telecomServiceName;
	}

	public void setTelecomServiceName(String telecomServiceName) {
		this.telecomServiceName = telecomServiceName;
	}

	public List<String> getMoreServicesAlias() {
		return moreServicesAlias;
	}

	public void setMoreServicesAlias(List<String> moreServicesAlias) {
		this.moreServicesAlias = moreServicesAlias;
	}

	public Long getOldSubId() {
		return oldSubId;
	}

	public void setOldSubId(Long oldSubId) {
		this.oldSubId = oldSubId;
	}

	public SubscriberDTO getOldSubDTO() {
		return oldSubDTO;
	}

	public void setOldSubDTO(SubscriberDTO oldSubDTO) {
		this.oldSubDTO = oldSubDTO;
	}

	public Long getOldGroupId() {
		return oldGroupId;
	}

	public void setOldGroupId(Long oldGroupId) {
		this.oldGroupId = oldGroupId;
	}

	public Long getRequestExtId() {
		return requestExtId;
	}

	public void setRequestExtId(Long requestExtId) {
		this.requestExtId = requestExtId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public SubInfrastructureDTO getSubInfrastructureDTO() {
		return subInfrastructureDTO;
	}

	public void setSubInfrastructureDTO(SubInfrastructureDTO subInfrastructureDTO) {
		this.subInfrastructureDTO = subInfrastructureDTO;
	}

	public ResultSurveyOnlineFormDTO getResultSurveyOnlineFormDTO() {
		return resultSurveyOnlineFormDTO;
	}

	public void setResultSurveyOnlineFormDTO(ResultSurveyOnlineFormDTO resultSurveyOnlineFormDTO) {
		this.resultSurveyOnlineFormDTO = resultSurveyOnlineFormDTO;
	}

	public String getDeployAddress() {
		return deployAddress;
	}

	public void setDeployAddress(String deployAddress) {
		this.deployAddress = deployAddress;
	}

	public String getDeployAreaCode() {
		return deployAreaCode;
	}

	public void setDeployAreaCode(String deployAreaCode) {
		this.deployAreaCode = deployAreaCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDevStaffCode() {
		return devStaffCode;
	}

	public void setDevStaffCode(String devStaffCode) {
		this.devStaffCode = devStaffCode;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIpStatic() {
		return ipStatic;
	}

	public void setIpStatic(String ipStatic) {
		this.ipStatic = ipStatic;
	}

	public String getIsdnAccount() {
		return isdnAccount;
	}

	public void setIsdnAccount(String isdnAccount) {
		this.isdnAccount = isdnAccount;
	}

	public List<String> getListIsdnAccount() {
		return listIsdnAccount;
	}

	public void setListIsdnAccount(List<String> listIsdnAccount) {
		this.listIsdnAccount = listIsdnAccount;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public Short getNotificationLevel() {
		return notificationLevel;
	}

	public void setNotificationLevel(Short notificationLevel) {
		this.notificationLevel = notificationLevel;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusTmp() {
		return statusTmp;
	}

	public void setStatusTmp(String statusTmp) {
		this.statusTmp = statusTmp;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public Long getImRequest() {
		return imRequest;
	}

	public void setImRequest(Long imRequest) {
		this.imRequest = imRequest;
	}

	public Long getNumberCoupler() {
		return numberCoupler;
	}

	public void setNumberCoupler(Long numberCoupler) {
		this.numberCoupler = numberCoupler;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getActionAuditId() {
		return actionAuditId;
	}

	public void setActionAuditId(Long actionAuditId) {
		this.actionAuditId = actionAuditId;
	}

	public String getTelMobile() {
		return telMobile;
	}

	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getResultTest() {
		return resultTest;
	}

	public void setResultTest(String resultTest) {
		this.resultTest = resultTest;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isEnableAsign() {
		return enableAsign;
	}

	public void setEnableAsign(boolean enableAsign) {
		this.enableAsign = enableAsign;
	}

	public boolean isDisableCancel() {
		return disableCancel;
	}

	public void setDisableCancel(boolean disableCancel) {
		this.disableCancel = disableCancel;
	}

	public String getServiceKt() {
		return serviceKt;
	}

	public void setServiceKt(String serviceKt) {
		this.serviceKt = serviceKt;
	}

	public boolean isChooseSubExit() {
		return chooseSubExit;
	}

	public void setChooseSubExit(boolean chooseSubExit) {
		this.chooseSubExit = chooseSubExit;
	}

	public String getIsdnAccountOld() {
		return isdnAccountOld;
	}

	public void setIsdnAccountOld(String isdnAccountOld) {
		this.isdnAccountOld = isdnAccountOld;
	}

	public String getCustNameOld() {
		return custNameOld;
	}

	public void setCustNameOld(String custNameOld) {
		this.custNameOld = custNameOld;
	}

	public boolean isNeedRetake() {
		return needRetake;
	}

	public void setNeedRetake(boolean needRetake) {
		this.needRetake = needRetake;
	}

	public String getRetakeAccount() {
		return retakeAccount;
	}

	public void setRetakeAccount(String retakeAccount) {
		this.retakeAccount = retakeAccount;
	}

	public boolean isUserSelectedISDNBefore() {
		return userSelectedISDNBefore;
	}

	public void setUserSelectedISDNBefore(boolean userSelectedISDNBefore) {
		this.userSelectedISDNBefore = userSelectedISDNBefore;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getShortNumber() {
		return shortNumber;
	}

	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getNewIsdn() {
		return newIsdn;
	}

	public void setNewIsdn(String newIsdn) {
		this.newIsdn = newIsdn;
	}

	public String getVasName() {
		return vasName;
	}

	public void setVasName(String vasName) {
		this.vasName = vasName;
	}

	public String getInfoTask() {
		return infoTask;
	}

	public void setInfoTask(String infoTask) {
		this.infoTask = infoTask;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getCableBoxCode() {
		return cableBoxCode;
	}

	public void setCableBoxCode(String cableBoxCode) {
		this.cableBoxCode = cableBoxCode;
	}

	public boolean isMoveAPAttach() {
		return isMoveAPAttach;
	}

	public void setMoveAPAttach(boolean isMoveAPAttach) {
		this.isMoveAPAttach = isMoveAPAttach;
	}

	public String getGponGroupAccount() {
		return gponGroupAccount;
	}

	public void setGponGroupAccount(String gponGroupAccount) {
		this.gponGroupAccount = gponGroupAccount;
	}

	public boolean isMoveAllLine() {
		return moveAllLine;
	}

	public void setMoveAllLine(boolean moveAllLine) {
		this.moveAllLine = moveAllLine;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public boolean isSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getLstMoreServiceSubId() {
		return lstMoreServiceSubId;
	}

	public void setLstMoreServiceSubId(List<Long> lstMoreServiceSubId) {
		this.lstMoreServiceSubId = lstMoreServiceSubId;
	}

	public SubscriberDTO getSubscriberDTO() {
		return subscriberDTO;
	}

	public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
		this.subscriberDTO = subscriberDTO;
	}

	public GroupsDTO getGroupsDTO() {
		return groupsDTO;
	}

	public void setGroupsDTO(GroupsDTO groupsDTO) {
		this.groupsDTO = groupsDTO;
	}

	public OptionSetValueDTO getTechnologyDTO() {
		return technologyDTO;
	}

	public void setTechnologyDTO(OptionSetValueDTO technologyDTO) {
		this.technologyDTO = technologyDTO;
	}

	public CustomerOrderDetailDTO getNdCustomerOrderDetailDTO() {
		return ndCustomerOrderDetailDTO;
	}

	public void setNdCustomerOrderDetailDTO(CustomerOrderDetailDTO ndCustomerOrderDetailDTO) {
		this.ndCustomerOrderDetailDTO = ndCustomerOrderDetailDTO;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public Long getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}

	public List<CustIdentityDTO> getListCustIdentity() {
		return listCustIdentity;
	}

	public void setListCustIdentity(List<CustIdentityDTO> listCustIdentity) {
		this.listCustIdentity = listCustIdentity;
	}

	public List<CustomerOrderDetailDTO> getLstChildSubId() {
		return lstChildSubId;
	}

	public void setLstChildSubId(List<CustomerOrderDetailDTO> lstChildSubId) {
		this.lstChildSubId = lstChildSubId;
	}

	public FullAddressDTO getFullDeploymentAddress() {
		return fullDeploymentAddress;
	}

	public void setFullDeploymentAddress(FullAddressDTO fullDeploymentAddress) {
		this.fullDeploymentAddress = fullDeploymentAddress;
	}

	public String getPstn() {
		return pstn;
	}

	public void setPstn(String pstn) {
		this.pstn = pstn;
	}

	public String getNgn() {
		return ngn;
	}

	public void setNgn(String ngn) {
		this.ngn = ngn;
	}

	public boolean isSelectedInfoDeploy() {
		return selectedInfoDeploy;
	}

	public void setSelectedInfoDeploy(boolean selectedInfoDeploy) {
		this.selectedInfoDeploy = selectedInfoDeploy;
	}

	public Long getGlineExist() {
		return glineExist;
	}

	public void setGlineExist(Long glineExist) {
		this.glineExist = glineExist;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isRenderCommandLink() {
		return renderCommandLink;
	}

	public void setRenderCommandLink(boolean renderCommandLink) {
		this.renderCommandLink = renderCommandLink;
	}

	public String getTrunkDeptutizeNumber() {
		return trunkDeptutizeNumber;
	}

	public void setTrunkDeptutizeNumber(String trunkDeptutizeNumber) {
		this.trunkDeptutizeNumber = trunkDeptutizeNumber;
	}

	public boolean isConnectNA() {
		return isConnectNA;
	}

	public void setConnectNA(boolean isConnectNA) {
		this.isConnectNA = isConnectNA;
	}

	public String getRetakeOnuOtherVendor() {
		return retakeOnuOtherVendor;
	}

	public void setRetakeOnuOtherVendor(String retakeOnuOtherVendor) {
		this.retakeOnuOtherVendor = retakeOnuOtherVendor;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Long getRetake() {
		return retake;
	}

	public void setRetake(Long retake) {
		this.retake = retake;
	}

	public String getStaffIdNo() {
		return staffIdNo;
	}

	public void setStaffIdNo(String staffIdNo) {
		this.staffIdNo = staffIdNo;
	}

	public String getStaffIdNoEnd() {
		return staffIdNoEnd;
	}

	public void setStaffIdNoEnd(String staffIdNoEnd) {
		this.staffIdNoEnd = staffIdNoEnd;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getVendorOld() {
		return vendorOld;
	}

	public void setVendorOld(String vendorOld) {
		this.vendorOld = vendorOld;
	}

	public List<String> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<String> listStatus) {
		this.listStatus = listStatus;
	}

	
	
}
