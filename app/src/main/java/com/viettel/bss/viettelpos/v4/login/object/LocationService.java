package com.viettel.bss.viettelpos.v4.login.object;

public class LocationService {
	private String nameLocation;
	private String countFTTH;
	private String countNetTV;
	private String countADSL;
	private String countPSTN;
	private String address;
	private String deptCode;
	private String connectorId;

	public LocationService(String nameLocation, String countFTTH,
			String countNetTV, String countADSL, String countPSTN,
			String address, String deptCode, String connectorId) {
		this.nameLocation = nameLocation;
		this.countFTTH = countFTTH;
		this.countNetTV = countNetTV;
		this.countADSL = countADSL;
		this.countPSTN = countPSTN;
		this.address = address;
		this.deptCode = deptCode;
		this.connectorId = connectorId;
	}

	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getNameLocation() {
		return nameLocation;
	}

	public void setNameLocation(String nameLocation) {
		this.nameLocation = nameLocation;
	}

	public String getCountFTTH() {
		return countFTTH;
	}

	public void setCountFTTH(String countFTTH) {
		this.countFTTH = countFTTH;
	}

	public String getCountNetTV() {
		return countNetTV;
	}

	public void setCountNetTV(String countNetTV) {
		this.countNetTV = countNetTV;
	}

	public String getCountADSL() {
		return countADSL;
	}

	public void setCountADSL(String countADSL) {
		this.countADSL = countADSL;
	}

	public String getCountPSTN() {
		return countPSTN;
	}

	public void setCountPSTN(String countPSTN) {
		this.countPSTN = countPSTN;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
