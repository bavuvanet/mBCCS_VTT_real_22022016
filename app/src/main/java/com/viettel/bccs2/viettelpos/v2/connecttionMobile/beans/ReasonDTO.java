package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "custIdentityDTO", strict = false)
public class ReasonDTO implements Serializable {
	@Element(name = "payType", required = false)
	private String payType;
	@Element(name = "reasonCode", required = false)
	private String reasonCode;
	@Element(name = "reasonId", required = false)
	private String reasonId;
	@Element(name = "reasonType", required = false)
	private String reasonType;
	@Element(name = "telService", required = false)
	private String telService;
	@Element(name = "updateUser", required = false)
	private String updateUser;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "type", required = false)
	private String type;
	@Element(name = "description", required = false)
	private String description;

	@Element(name = "code", required = false)
	private String code;
	@Element(name = "codeName", required = false)
	private String codeName;
	@Element(name = "status", required = false)
	private String status;
	@ElementList(name = "lstCharacterCode", entry = "lstCharacterCode", required = false, inline = true)
	private List<String> lstCharacterCode;
	private int selectedCount;

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getTelService() {
		return telService;
	}

	public void setTelService(String telService) {
		this.telService = telService;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}


	public List<String> getLstCharacterCode() {
		return lstCharacterCode;
	}

	public void setLstCharacterCode(List<String> lstCharacterCode) {
		this.lstCharacterCode = lstCharacterCode;
	}

	@Override
	public String toString() {


		if(CommonActivity.isNullOrEmpty(reasonCode)){
			reasonCode = code;
		}
		if(!CommonActivity.isNullOrEmpty(reasonCode) && !CommonActivity.isNullOrEmpty(name)){
			return reasonCode + " - " + name;
		}else{
			return name;
		
		}

	}
}
