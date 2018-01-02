package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import java.io.Serializable;

public class SurveyOnline implements Serializable{
	
	private String address;
	
	private String connectorCode;
	
	private String connectorId;
	
	private String connectorType;
	
	private String deptCode;
	
	private String deptName;
	
	private String lat;
	
	private String lng;
	
	private String resourceConnector;
	
	private String resourceDevice;

	private String resourceRootCable;

	private String projectCode;
	
	private String projectId;
	
	private String projectName;
	
	private String stationCode;
	
	private String stationId;

	private String distance;

	private String cableLength;

	private String locationName;
	
	private String connectorFreePort;
	
	private String connectorLockPort;
	private String deviceCode;
	private String portCode;
	private String sizePortForCable;
	private String sizeKeepDeployAccount;
	
	private String locationCode;
	private String nodeOpticalCode;
	private String pillarCode;
	private String surfaceRadius;
	private String teamCode;
	
	private String customerPossition;
	private String cableBoxPosition;
	private String polylineData;
	
	private String maxSubNumOnDslam;
	private String totalSubNumber;
	
	private String vendorCode;
	private String vendorName;
	
	// bo sung mo hinh
	private String deployModelOntSfu;
	private String deployModelOntSfuName;
	
	
	
	
	public String getDeployModelOntSfu() {
		return deployModelOntSfu;
	}

	public void setDeployModelOntSfu(String deployModelOntSfu) {
		this.deployModelOntSfu = deployModelOntSfu;
	}

	public String getDeployModelOntSfuName() {
		return deployModelOntSfuName;
	}

	public void setDeployModelOntSfuName(String deployModelOntSfuName) {
		this.deployModelOntSfuName = deployModelOntSfuName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getMaxSubNumOnDslam() {
		return maxSubNumOnDslam;
	}

	public void setMaxSubNumOnDslam(String maxSubNumOnDslam) {
		this.maxSubNumOnDslam = maxSubNumOnDslam;
	}

	public String getTotalSubNumber() {
		return totalSubNumber;
	}

	public void setTotalSubNumber(String totalSubNumber) {
		this.totalSubNumber = totalSubNumber;
	}

	public String getPolylineData() {
		return polylineData;
	}

	public void setPolylineData(String polylineData) {
		this.polylineData = polylineData;
	}

	public String getCustomerPossition() {
		return customerPossition;
	}

	public void setCustomerPossition(String customerPossition) {
		this.customerPossition = customerPossition;
	}

	public String getCableBoxPosition() {
		return cableBoxPosition;
	}

	public void setCableBoxPosition(String cableBoxPosition) {
		this.cableBoxPosition = cableBoxPosition;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getSurfaceRadius() {
		return surfaceRadius;
	}

	public void setSurfaceRadius(String surfaceRadius) {
		this.surfaceRadius = surfaceRadius;
	}

	public String getPillarCode() {
		return pillarCode;
	}

	public void setPillarCode(String pillarCode) {
		this.pillarCode = pillarCode;
	}

	public String getNodeOpticalCode() {
		return nodeOpticalCode;
	}

	public void setNodeOpticalCode(String nodeOpticalCode) {
		this.nodeOpticalCode = nodeOpticalCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getConnectorFreePort() {
		return connectorFreePort;
	}

	public void setConnectorFreePort(String connectorFreePort) {
		this.connectorFreePort = connectorFreePort;
	}

	public String getConnectorLockPort() {
		return connectorLockPort;
	}

	public void setConnectorLockPort(String connectorLockPort) {
		this.connectorLockPort = connectorLockPort;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getSizePortForCable() {
		return sizePortForCable;
	}

	public void setSizePortForCable(String sizePortForCable) {
		this.sizePortForCable = sizePortForCable;
	}

	public String getSizeKeepDeployAccount() {
		return sizeKeepDeployAccount;
	}

	public void setSizeKeepDeployAccount(String sizeKeepDeployAccount) {
		this.sizeKeepDeployAccount = sizeKeepDeployAccount;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public SurveyOnline() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCableLength() {
		return cableLength;
	}

	public void setCableLength(String cableLength) {
		this.cableLength = cableLength;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	
	public String getResourceRootCable() {
		return resourceRootCable;
	}

	public void setResourceRootCable(String resourceRootCable) {
		this.resourceRootCable = resourceRootCable;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}
	public String getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}
	public String getConnectorType() {
		return connectorType;
	}
	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
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
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
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
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
}
