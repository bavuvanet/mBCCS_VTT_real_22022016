package com.viettel.bss.viettelpos.v4.charge.object;

public class TelecomServiceObj {
	private int id;
	private int telecomServiceId;
	private String telServiceName;
	private String des;
	private String createDate;
	private int satatus;
	private String roleName;
	private String code;
	private String idPrinted;

	public TelecomServiceObj() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTelecomServiceId() {
		return telecomServiceId;
	}

	public void setTelecomServiceId(int telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getTelServiceName() {
		return telServiceName;
	}

	public void setTelServiceName(String telServiceName) {
		this.telServiceName = telServiceName;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getSatatus() {
		return satatus;
	}

	public void setSatatus(int satatus) {
		this.satatus = satatus;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdPrinted() {
		return idPrinted;
	}

	public void setIdPrinted(String idPrinted) {
		this.idPrinted = idPrinted;
	}

}
