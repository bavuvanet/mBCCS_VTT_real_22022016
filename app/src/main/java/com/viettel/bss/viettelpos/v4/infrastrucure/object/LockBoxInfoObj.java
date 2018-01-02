package com.viettel.bss.viettelpos.v4.infrastrucure.object;

public class LockBoxInfoObj {

	private int id;
	private int numberOfPort;
	private String connectorId;
	private String infraType;
	private String rootConnectorId;
	private String serviceType;
	private String stationId;
	private String userLock;

	public LockBoxInfoObj(int id, int numberOfPort, String connectorId,
			String infraType, String rootConnectorId, String serviceType,
			String stationId, String userLock) {
		super();
		this.id = id;
		this.numberOfPort = numberOfPort;
		this.connectorId = connectorId;
		this.infraType = infraType;
		this.rootConnectorId = rootConnectorId;
		this.serviceType = serviceType;
		this.stationId = stationId;
		this.userLock = userLock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfPort() {
		return numberOfPort;
	}

	public void setNumberOfPort(int numberOfPort) {
		this.numberOfPort = numberOfPort;
	}

	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	public String getInfraType() {
		return infraType;
	}

	public void setInfraType(String infraType) {
		this.infraType = infraType;
	}

	public String getRootConnectorId() {
		return rootConnectorId;
	}

	public void setRootConnectorId(String rootConnectorId) {
		this.rootConnectorId = rootConnectorId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getUserLock() {
		return userLock;
	}

	public void setUserLock(String userLock) {
		this.userLock = userLock;
	}

}
