package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class SubReqDeployment {
	private String radiusCust;
	private String cableBoxId;
	private String cableBoxCode;
	private String stationCode;
	private String teamCode;
	private String vendor;
	private String conectorType;
	
	
	
	public SubReqDeployment(String radiusCust, String cableBoxId,
			String cableBoxCode, String stationCode, String teamCode) {
		super();
		this.radiusCust = radiusCust;
		this.cableBoxId = cableBoxId;
		this.cableBoxCode = cableBoxCode;
		this.stationCode = stationCode;
		this.teamCode = teamCode;
	}


	public SubReqDeployment(String radiusCust, String cableBoxId,
			String cableBoxCode, String stationCode, String teamCode,
			String vendor) {
		super();
		this.radiusCust = radiusCust;
		this.cableBoxId = cableBoxId;
		this.cableBoxCode = cableBoxCode;
		this.stationCode = stationCode;
		this.teamCode = teamCode;
		this.vendor = vendor;
	}

	
	

	public SubReqDeployment(String radiusCust, String cableBoxId,
			String cableBoxCode, String stationCode, String teamCode,
			String vendor, String conectorType) {
		super();
		this.radiusCust = radiusCust;
		this.cableBoxId = cableBoxId;
		this.cableBoxCode = cableBoxCode;
		this.stationCode = stationCode;
		this.teamCode = teamCode;
		this.vendor = vendor;
		this.conectorType = conectorType;
	}


	public String getConectorType() {
		return conectorType;
	}


	public void setConectorType(String conectorType) {
		this.conectorType = conectorType;
	}


	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
	}


	public SubReqDeployment() {
		super();
	}

	public String getRadiusCust() {
		return radiusCust;
	}

	public void setRadiusCust(String radiusCust) {
		this.radiusCust = radiusCust;
	}

	public String getCableBoxId() {
		return cableBoxId;
	}

	public void setCableBoxId(String cableBoxId) {
		this.cableBoxId = cableBoxId;
	}

	public String getCableBoxCode() {
		return cableBoxCode;
	}

	public void setCableBoxCode(String cableBoxCode) {
		this.cableBoxCode = cableBoxCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

}
