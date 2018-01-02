package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "ResultGetVendorByConnectorForm", strict = false)
public class ResultGetVendorByConnectorForm implements Serializable{

	@Element (name = "connectorCode", required = false)
	private String connectorCode;
	@Element (name = "connectorId", required = false)
	private Long connectorId;
	@Element (name = "deviceCode", required = false)
	private String deviceCode;
	@Element (name = "deviceId", required = false)
	private Long deviceId;
	@Element (name = "haveRF", required = false)
	private Long haveRF;
	@Element (name = "vendorCode", required = false)
	private String vendorCode;
	@Element (name = "vendorName", required = false)
	private String vendorName;
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
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getHaveRF() {
		return haveRF;
	}
	public void setHaveRF(Long haveRF) {
		this.haveRF = haveRF;
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
	
	
	
	
	
}
