package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "ResultSurveyOnlineForBccs2Form", strict = false)
public class ResultSurveyOnlineForBccs2Form implements Serializable {
	@Element(name = "cableLength", required = false)
	private Long cableLength;
	@Element(name = "connectorCode", required = false)
	private String connectorCode;
	@Element(name = "connectorId", required = false)
	private Long connectorId;
	@Element(name = "connectorType", required = false)
	private String connectorType;
	@Element(name = "deptCode", required = false)
	private String deptCode;
	@Element(name = "deptName", required = false)
	private String deptName;
	@Element(name = "deviceCode", required = false)
	private String deviceCode;
	@Element(name = "distance", required = false)
	private double distance;
	@Element(name = "gponConnectorFreePort", required = false)
	private Long gponConnectorFreePort;
	@Element(name = "gponConnectorLockPort", required = false)
	private Long gponConnectorLockPort;
	@Element(name = "infraType", required = false)
	private String infraType;
	@Element(name = "lat", required = false)
	private Double lat;
	@Element(name = "lng", required = false)
	private Double lng;
	@Element(name = "locationCode", required = false)
	private String locationCode;
	@Element(name = "locationName", required = false)
	private String locationName;
	@Element(name = "nodeOpticalCode", required = false)
	private String nodeOpticalCode;
	@Element(name = "pillarCode", required = false)
	private String pillarCode;
	@Element(name = "portCode", required = false)
	private String portCode;
	@Element(name = "projectCode", required = false)
	private String projectCode;
	@Element(name = "projectId", required = false)
	private Long projectId;
	@Element(name = "projectName", required = false)
	private String projectName;
	@Element(name = "resourceConnector", required = false)
	private String resourceConnector;
	@Element(name = "resourceDevice", required = false)
	private String resourceDevice;
	@Element(name = "resourceDeviceA", required = false)
	private String resourceDeviceA;
	@Element(name = "resourceDeviceF", required = false)
	private String resourceDeviceF;
	@Element(name = "resourceDeviceI", required = false)
	private String resourceDeviceI;
	@Element(name = "resourceDeviceL", required = false)
	private String resourceDeviceL;
	@Element(name = "resourceDeviceP", required = false)
	private String resourceDeviceP;
	@Element(name = "resourceDeviceT", required = false)
	private String resourceDeviceT;
	@Element(name = "resourceRootCable", required = false)
	private String resourceRootCable;
	@Element(name = "result", required = false)
	private String result;
	@Element(name = "resultMessage", required = false)
	private String resultMessage;
	@Element(name = "stationCode", required = false)
	private String stationCode;
	@Element(name = "stationId", required = false)
	private Long stationId;
	@Element(name = "address", required = false)
	private String address;

	private String surveyRadius;
	private ResultGetVendorByConnectorForm resultGetVendorByConnectorForm;

	@ElementList(name = "lstAccountGline", entry = "lstAccountGline", required = false, inline = true)
	private ArrayList<String> lstAccountGline;
	@Element(name = "gponConnectorUsedPort", required = false)
	private Long gponConnectorUsedPort;

	public Long getGponConnectorUsedPort() {
		return gponConnectorUsedPort;
	}

	public void setGponConnectorUsedPort(Long gponConnectorUsedPort) {
		this.gponConnectorUsedPort = gponConnectorUsedPort;
	}

	public ArrayList<String> getLstAccountGline() {
		return lstAccountGline;
	}

	public void setLstAccountGline(ArrayList<String> lstAccountGline) {
		this.lstAccountGline = lstAccountGline;
	}

	public ResultGetVendorByConnectorForm getResultGetVendorByConnectorForm() {
		return resultGetVendorByConnectorForm;
	}

	public void setResultGetVendorByConnectorForm(
			ResultGetVendorByConnectorForm resultGetVendorByConnectorForm) {
		this.resultGetVendorByConnectorForm = resultGetVendorByConnectorForm;
	}

	public String getSurveyRadius() {
		return surveyRadius;
	}

	public void setSurveyRadius(String surveyRadius) {
		this.surveyRadius = surveyRadius;
	}

	public Long getCableLength() {
		return cableLength;
	}

	public void setCableLength(Long cableLength) {
		this.cableLength = cableLength;
	}

	public String getConnectorCode() {
		return connectorCode;
	}

	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}

	public Long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Long connectorId) {
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

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Long getGponConnectorFreePort() {
		return gponConnectorFreePort;
	}

	public void setGponConnectorFreePort(Long gponConnectorFreePort) {
		this.gponConnectorFreePort = gponConnectorFreePort;
	}

	public Long getGponConnectorLockPort() {
		return gponConnectorLockPort;
	}

	public void setGponConnectorLockPort(Long gponConnectorLockPort) {
		this.gponConnectorLockPort = gponConnectorLockPort;
	}

	public String getInfraType() {
		return CommonActivity.convertTechnogoly(infraType);
	}

	public void setInfraType(String infraType) {
		this.infraType = infraType;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfratypeName(Context context) {
		Map<String, String> mapTechId = new HashMap<String, String>();
		mapTechId.put("CCN", "1");
		mapTechId.put("FCN", "3");
		mapTechId.put("CATV", "2");
		mapTechId.put("GPON", "4");

		Map<String, String> mapTechName = new HashMap<String, String>();
		mapTechName.put("1", context.getString(R.string.cap_dong));
		mapTechName.put("2", context.getString(R.string.cap_dong_truc));
		mapTechName.put("3", context.getString(R.string.cap_quang));
		mapTechName.put("4", context.getString(R.string.cap_gpon));

		String techId = mapTechId.get(infraType);
		return mapTechName.get(techId);
	}
	public String getTechNology(Context context){
		Map<String, String> mapTechId = new HashMap<String, String>();
		mapTechId.put("CCN", "1");
		mapTechId.put("FCN", "3");
		mapTechId.put("CATV", "2");
		mapTechId.put("GPON", "4");
		return mapTechId.get(infraType);
	}

}
