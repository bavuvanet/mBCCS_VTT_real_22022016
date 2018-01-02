package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

@Root(name = "SubInfrastructureDTO", strict = false)
public class SubInfrastructureDTO implements Serializable {
	@Element(name = "accountGline", required = false)
	private String accountGline;
	@Element(name = "accountToWriteFile", required = false)
	private String accountToWriteFile;
	@Element(name = "actionId", required = false)
	private String actionId;
	@Element(name = "boardId", required = false)
	private Long boardId;
	@Element(name = "cabLen", required = false)
	private Long cabLen;
	@Element(name = "cableBoxCode", required = false)
	private String cableBoxCode;
	@Element(name = "cableBoxId", required = false)
	private Long cableBoxId;
	@Element(name = "cableBoxType", required = false)
	private String cableBoxType;
	@Element(name = "channelSpeed", required = false)
	private String channelSpeed;
	@Element(name = "chooseSubExit", required = false)
	private boolean chooseSubExit;
	@Element(name = "createDate", required = false)
	private String createDate;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "email", required = false)
	private String email;
	@Element(name = "error", required = false)
	private String error;
	@Element(name = "feeAmount", required = false)
	private Long feeAmount;
	@Element(name = "indexList", required = false)
	private String indexList;
	@Element(name = "inforUpdateFile", required = false)
	private String inforUpdateFile;
	@Element(name = "interfaceDevice", required = false)
	private String interfaceDevice;
	@Element(name = "invalidSourceId", required = false)
	private String invalidSourceId;
	@Element(name = "ip", required = false)
	private String ip;
	@Element(name = "issueDate", required = false)
	private String issueDate;
	@Element(name = "lineNo", required = false)
	private Long lineNo;
	@Element(name = "odfIndor", required = false)
	private String odfIndor;
	@Element(name = "odfOutdor", required = false)
	private String odfOutdor;
	@Element(name = "portNo", required = false)
	private Long portNo;
	@Element(name = "portSpeed", required = false)
	private String portSpeed;
	@Element(name = "posId", required = false)
	private Long posId;
	@Element(name = "productId", required = false)
	private Long productId;
	@Element(name = "progress", required = false)
	private String progress;
	@Element(name = "radiusCust", required = false)
	private Long radiusCust;
	@Element(name = "reqDatetime", required = false)
	private String reqDatetime;
	@Element(name = "reqId", required = false)
	private Long reqId;
	@Element(name = "sourceId", required = false)
	private Long sourceId;
	@Element(name = "sourceType", required = false)
	private Long sourceType;
	@Element(name = "staffId", required = false)
	private Long staffId;
	@Element(name = "stageId", required = false)
	private Long stageId;
	@Element(name = "stationCode", required = false)
	private String stationCode;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "subId", required = false)
	private Long subId;
	@Element(name = "subInfrastructureId", required = false)
	private Long subInfrastructureId;
	@Element(name = "subReqId", required = false)
	private Long subReqId;
	@Element(name = "subnetMask", required = false)
	private String subnetMask;
	@Element(name = "subscriberDTO", required = false)
	private SubscriberDTO subscriberDTO;
	@Element(name = "taskId", required = false)
	private Long taskId;
	@Element(name = "teamCode", required = false)
	private String teamCode;
	@Element(name = "techCode", required = false)
	private String techCode;
	@Element(name = "technology", required = false)
	private String technology;
	@Element(name = "technologyName", required = false)
	private String technologyName;
	@Element(name = "telFax", required = false)
	private String telFax;
	@Element(name = "telMobile", required = false)
	private String telMobile;
	@Element(name = "trunkId", required = false)
	private String trunkId;
	@Element(name = "typeCrc", required = false)
	private String typeCrc;
	@Element(name = "typeSignal", required = false)
	private String typeSignal;
	@Element(name = "updateDatetime", required = false)
	private String updateDatetime;
	@Element(name = "updaterPosId", required = false)
	private Long updaterPosId;
	@Element(name = "updaterUserId", required = false)
	private Long updaterUserId;
	@Element(name = "userUsing", required = false)
	private String userUsing;
	@Element(name = "validateImport", required = false)
	private boolean validateImport;
	@Element(name = "vendor", required = false)
	private String vendor;
	@Element(name = "address", required = false)
	private String address;
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
	public String getAccountGline() {
		return accountGline;
	}
	public void setAccountGline(String accountGline) {
		this.accountGline = accountGline;
	}
	public String getAccountToWriteFile() {
		return accountToWriteFile;
	}
	public void setAccountToWriteFile(String accountToWriteFile) {
		this.accountToWriteFile = accountToWriteFile;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public Long getBoardId() {
		return boardId;
	}
	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}
	public Long getCabLen() {
		return cabLen;
	}
	public void setCabLen(Long cabLen) {
		this.cabLen = cabLen;
	}
	public String getCableBoxCode() {
		return cableBoxCode;
	}
	public void setCableBoxCode(String cableBoxCode) {
		this.cableBoxCode = cableBoxCode;
	}
	public Long getCableBoxId() {
		return cableBoxId;
	}
	public void setCableBoxId(Long cableBoxId) {
		this.cableBoxId = cableBoxId;
	}
	public String getCableBoxType() {
		return cableBoxType;
	}
	public void setCableBoxType(String cableBoxType) {
		this.cableBoxType = cableBoxType;
	}
	public String getChannelSpeed() {
		return channelSpeed;
	}
	public void setChannelSpeed(String channelSpeed) {
		this.channelSpeed = channelSpeed;
	}
	public boolean isChooseSubExit() {
		return chooseSubExit;
	}
	public void setChooseSubExit(boolean chooseSubExit) {
		this.chooseSubExit = chooseSubExit;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Long getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getIndexList() {
		return indexList;
	}
	public void setIndexList(String indexList) {
		this.indexList = indexList;
	}
	public String getInforUpdateFile() {
		return inforUpdateFile;
	}
	public void setInforUpdateFile(String inforUpdateFile) {
		this.inforUpdateFile = inforUpdateFile;
	}
	public String getInterfaceDevice() {
		return interfaceDevice;
	}
	public void setInterfaceDevice(String interfaceDevice) {
		this.interfaceDevice = interfaceDevice;
	}
	public String getInvalidSourceId() {
		return invalidSourceId;
	}
	public void setInvalidSourceId(String invalidSourceId) {
		this.invalidSourceId = invalidSourceId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public Long getLineNo() {
		return lineNo;
	}
	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}
	public String getOdfIndor() {
		return odfIndor;
	}
	public void setOdfIndor(String odfIndor) {
		this.odfIndor = odfIndor;
	}
	public String getOdfOutdor() {
		return odfOutdor;
	}
	public void setOdfOutdor(String odfOutdor) {
		this.odfOutdor = odfOutdor;
	}
	public Long getPortNo() {
		return portNo;
	}
	public void setPortNo(Long portNo) {
		this.portNo = portNo;
	}
	public String getPortSpeed() {
		return portSpeed;
	}
	public void setPortSpeed(String portSpeed) {
		this.portSpeed = portSpeed;
	}
	public Long getPosId() {
		return posId;
	}
	public void setPosId(Long posId) {
		this.posId = posId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public Long getRadiusCust() {
		return radiusCust;
	}
	public void setRadiusCust(Long radiusCust) {
		this.radiusCust = radiusCust;
	}
	public String getReqDatetime() {
		return reqDatetime;
	}
	public void setReqDatetime(String reqDatetime) {
		this.reqDatetime = reqDatetime;
	}
	public Long getReqId() {
		return reqId;
	}
	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Long getSourceType() {
		return sourceType;
	}
	public void setSourceType(Long sourceType) {
		this.sourceType = sourceType;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public Long getStageId() {
		return stageId;
	}
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getSubId() {
		return subId;
	}
	public void setSubId(Long subId) {
		this.subId = subId;
	}
	public Long getSubInfrastructureId() {
		return subInfrastructureId;
	}
	public void setSubInfrastructureId(Long subInfrastructureId) {
		this.subInfrastructureId = subInfrastructureId;
	}
	public Long getSubReqId() {
		return subReqId;
	}
	public void setSubReqId(Long subReqId) {
		this.subReqId = subReqId;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	public SubscriberDTO getSubscriberDTO() {
		return subscriberDTO;
	}
	public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
		this.subscriberDTO = subscriberDTO;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTechCode() {
		return techCode;
	}
	public void setTechCode(String techCode) {
		this.techCode = techCode;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getTechnologyName() {
		return technologyName;
	}
	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
	public String getTelFax() {
		return telFax;
	}
	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}
	public String getTelMobile() {
		return telMobile;
	}
	public void setTelMobile(String telMobile) {
		this.telMobile = telMobile;
	}
	public String getTrunkId() {
		return trunkId;
	}
	public void setTrunkId(String trunkId) {
		this.trunkId = trunkId;
	}
	public String getTypeCrc() {
		return typeCrc;
	}
	public void setTypeCrc(String typeCrc) {
		this.typeCrc = typeCrc;
	}
	public String getTypeSignal() {
		return typeSignal;
	}
	public void setTypeSignal(String typeSignal) {
		this.typeSignal = typeSignal;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public Long getUpdaterPosId() {
		return updaterPosId;
	}
	public void setUpdaterPosId(Long updaterPosId) {
		this.updaterPosId = updaterPosId;
	}
	public Long getUpdaterUserId() {
		return updaterUserId;
	}
	public void setUpdaterUserId(Long updaterUserId) {
		this.updaterUserId = updaterUserId;
	}
	public String getUserUsing() {
		return userUsing;
	}
	public void setUserUsing(String userUsing) {
		this.userUsing = userUsing;
	}
	public boolean isValidateImport() {
		return validateImport;
	}
	public void setValidateImport(boolean validateImport) {
		this.validateImport = validateImport;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

}
