package com.viettel.bss.viettelpos.v3.connecttionService.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "ResultSurveyOnlineFormDTO", strict = false)
public class ResultSurveyOnlineFormDTO {
	@Element (name = "account", required = false)
	private String account;
	@Element (name = "cableBoxPosition", required = false)
	private String cableBoxPosition;
	@Element (name = "cableLength", required = false)
	private Long cableLength;
	@Element (name = "connectorAddress", required = false)
	private String connectorAddress;
	@Element (name = "connectorCode", required = false)
	private String connectorCode;
	@Element (name = "connectorFreePort", required = false)
	private Long connectorFreePort;
	@Element (name = "connectorId", required = false)
	private Long connectorId;
	@Element (name = "connectorLockPort", required = false)
	private Long connectorLockPort;
	@Element (name = "connectorType", required = false)
	private String connectorType;
	@Element (name = "customerPosition", required = false)
	private String customerPosition;
	@Element (name = "deptCode", required = false)
	private String deptCode;
	@Element (name = "deptName", required = false)
	private String deptName;
	@Element (name = "deviceCode", required = false)
	private String deviceCode;
	@Element (name = "haveRF", required = false)
	private Long haveRF;
	@Element (name = "lat", required = false)
	private Double lat;
	@Element (name = "lng", required = false)
	private Double lng;
	@Element (name = "locationCode", required = false)
	private String locationCode;
	@Element (name = "locationName", required = false)
	private String locationName;
	@Element (name = "nodeOpticalCode", required = false)
	private String nodeOpticalCode;
	@Element (name = "pillarCode", required = false)
	private String pillarCode;
	@Element (name = "polylineData", required = false)
	private String polylineData;
	@Element (name = "portCode", required = false)
	private String portCode;
	@Element (name = "projectCode", required = false)
	private String projectCode;
	@Element (name = "projectId", required = false)
	private Long projectId;
	@Element (name = "projectName", required = false)
	private String projectName;
	@Element (name = "radianCust", required = false)
	private Long radianCust;
	@Element (name = "resourceConnector", required = false)
	private String resourceConnector;
	@Element (name = "resourceDevice", required = false)
	private String resourceDevice;
	@Element (name = "resourceDeviceA", required = false)
	private String resourceDeviceA;
	@Element (name = "resourceDeviceF", required = false)
	private String resourceDeviceF;
	@Element (name = "resourceDeviceI", required = false)
	private String resourceDeviceI;
	@Element (name = "resourceDeviceL", required = false)
	private String resourceDeviceL;
	@Element (name = "resourceDeviceP", required = false)
	private String resourceDeviceP;
	@Element (name = "resourceDeviceT", required = false)
	private String resourceDeviceT;
	@Element (name = "resourceRootCable", required = false)
	private String resourceRootCable;
	@Element (name = "result", required = false)
	private String result;
	@Element (name = "resultMessage", required = false)
	private String resultMessage;
	@Element (name = "rfStatus", required = false)
	private String rfStatus;
	@Element (name = "sizeKeepDeployAccount", required = false)
	private String sizeKeepDeployAccount;
	@Element (name = "sizePortForCable", required = false)
	private String sizePortForCable;
	@Element (name = "stationCode", required = false)
	private String stationCode;
	@Element (name = "stationId", required = false)
	private Long stationId;
	@Element (name = "subRequestId", required = false)
	private Long subRequestId;
	@Element (name = "technogoly", required = false)
	private String technogoly;
	@Element (name = "technogolyName", required = false)
	private String technogolyName;
	@Element (name = "vender", required = false)
	private String vender;
	@Element (name = "address", required = false)
	private String address;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCableBoxPosition() {
		return cableBoxPosition;
	}
	public void setCableBoxPosition(String cableBoxPosition) {
		this.cableBoxPosition = cableBoxPosition;
	}
	public Long getCableLength() {
		return cableLength;
	}
	public void setCableLength(Long cableLength) {
		this.cableLength = cableLength;
	}
	public String getConnectorAddress() {
		return connectorAddress;
	}
	public void setConnectorAddress(String connectorAddress) {
		this.connectorAddress = connectorAddress;
	}
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}
	public Long getConnectorFreePort() {
		return connectorFreePort;
	}
	public void setConnectorFreePort(Long connectorFreePort) {
		this.connectorFreePort = connectorFreePort;
	}
	public Long getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(Long connectorId) {
		this.connectorId = connectorId;
	}
	public Long getConnectorLockPort() {
		return connectorLockPort;
	}
	public void setConnectorLockPort(Long connectorLockPort) {
		this.connectorLockPort = connectorLockPort;
	}
	public String getConnectorType() {
		return connectorType;
	}
	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
	}
	public String getCustomerPosition() {
		return customerPosition;
	}
	public void setCustomerPosition(String customerPosition) {
		this.customerPosition = customerPosition;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public Long getHaveRF() {
		return haveRF;
	}
	public void setHaveRF(Long haveRF) {
		this.haveRF = haveRF;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getNodeOpticalCode() {
		return nodeOpticalCode;
	}
	public void setNodeOpticalCode(String nodeOpticalCode) {
		this.nodeOpticalCode = nodeOpticalCode;
	}
	public String getPillarCode() {
		return pillarCode;
	}
	public void setPillarCode(String pillarCode) {
		this.pillarCode = pillarCode;
	}
	public String getPolylineData() {
		return polylineData;
	}
	public void setPolylineData(String polylineData) {
		this.polylineData = polylineData;
	}
	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getRadianCust() {
		return radianCust;
	}
	public void setRadianCust(Long radianCust) {
		this.radianCust = radianCust;
	}
	public String getResourceConnector() {
		return resourceConnector;
	}
	public void setResourceConnector(String resourceConnector) {
		this.resourceConnector = resourceConnector;
	}
	public String getResourceDevice() {
		return resourceDevice;
	}
	public void setResourceDevice(String resourceDevice) {
		this.resourceDevice = resourceDevice;
	}
	public String getResourceDeviceA() {
		return resourceDeviceA;
	}
	public void setResourceDeviceA(String resourceDeviceA) {
		this.resourceDeviceA = resourceDeviceA;
	}
	public String getResourceDeviceF() {
		return resourceDeviceF;
	}
	public void setResourceDeviceF(String resourceDeviceF) {
		this.resourceDeviceF = resourceDeviceF;
	}
	public String getResourceDeviceI() {
		return resourceDeviceI;
	}
	public void setResourceDeviceI(String resourceDeviceI) {
		this.resourceDeviceI = resourceDeviceI;
	}
	public String getResourceDeviceL() {
		return resourceDeviceL;
	}
	public void setResourceDeviceL(String resourceDeviceL) {
		this.resourceDeviceL = resourceDeviceL;
	}
	public String getResourceDeviceP() {
		return resourceDeviceP;
	}
	public void setResourceDeviceP(String resourceDeviceP) {
		this.resourceDeviceP = resourceDeviceP;
	}
	public String getResourceDeviceT() {
		return resourceDeviceT;
	}
	public void setResourceDeviceT(String resourceDeviceT) {
		this.resourceDeviceT = resourceDeviceT;
	}
	public String getResourceRootCable() {
		return resourceRootCable;
	}
	public void setResourceRootCable(String resourceRootCable) {
		this.resourceRootCable = resourceRootCable;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getRfStatus() {
		return rfStatus;
	}
	public void setRfStatus(String rfStatus) {
		this.rfStatus = rfStatus;
	}
	public String getSizeKeepDeployAccount() {
		return sizeKeepDeployAccount;
	}
	public void setSizeKeepDeployAccount(String sizeKeepDeployAccount) {
		this.sizeKeepDeployAccount = sizeKeepDeployAccount;
	}
	public String getSizePortForCable() {
		return sizePortForCable;
	}
	public void setSizePortForCable(String sizePortForCable) {
		this.sizePortForCable = sizePortForCable;
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
	public Long getSubRequestId() {
		return subRequestId;
	}
	public void setSubRequestId(Long subRequestId) {
		this.subRequestId = subRequestId;
	}
	public String getTechnogoly() {
		return technogoly;
	}
	public void setTechnogoly(String technogoly) {
		this.technogoly = technogoly;
	}
	public String getTechnogolyName() {
		return technogolyName;
	}
	public void setTechnogolyName(String technogolyName) {
		this.technogolyName = technogolyName;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
