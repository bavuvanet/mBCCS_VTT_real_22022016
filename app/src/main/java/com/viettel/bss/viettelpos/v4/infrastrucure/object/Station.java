package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "lstResultSurveyOnlineForm", strict = false)
public class Station implements Serializable {
	@Element
	private String address;
	@Element
	private String cableLength;
	@Element
	private String connectorCode;
	@Element
	private String connectorId;
	@Element
	private String deptCode;
	@Element
	private String deptName;
	@Element
	private String lat;
	@Element
	private String lng;
	// @Element
	// private String resourceRootCable;
	// @Element
	// private String projectId;
	// @Element
	// private String projectName;
	@Element
	private String resourceConnector;
	@Element
	private String resourceDevice;
	@Element
	private String stationCode;
	@Element
	private String stationId;

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

	public String getCableLength() {
		return cableLength;
	}

	public void setCableLength(String cableLength) {
		this.cableLength = cableLength;
	}

	// public String getResourceRootCable() {
	// return resourceRootCable;
	// }
	//
	// public void setResourceRootCable(String resourceRootCable) {
	// this.resourceRootCable = resourceRootCable;
	// }

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
