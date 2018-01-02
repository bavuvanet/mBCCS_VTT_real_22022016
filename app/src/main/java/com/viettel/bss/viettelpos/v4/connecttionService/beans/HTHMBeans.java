package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class  HTHMBeans implements Serializable {
	private String code = "";
	private String codeName;
	private String description;
	private String name;
	private String reasonId;
	private String reqProfile;
	private String status;
	private String telService;
	private String type;
    private boolean isDeposit = false;
	private boolean isOnt = false;
	private int selectedCount;

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public boolean isOnt() {
		return isOnt;
	}

	public void setOnt(boolean ont) {
		isOnt = ont;
	}

	public String getCode() {
		if(code == null){
			code = "";
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		if (!CommonActivity.isNullOrEmpty(code)
				&& !CommonActivity.isNullOrEmpty(name)) {
			return code + "-" + name;
		} else {
			return codeName;
		}
		
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getDescription() {
		
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getReqProfile() {
		return reqProfile;
	}

	public void setReqProfile(String reqProfile) {
		this.reqProfile = reqProfile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelService() {
		return telService;
	}

	public void setTelService(String telService) {
		this.telService = telService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public boolean isDeposit() {
        return isDeposit;
    }

    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    @Override
	public String toString() {

			return getCodeName();
		

	}
}
