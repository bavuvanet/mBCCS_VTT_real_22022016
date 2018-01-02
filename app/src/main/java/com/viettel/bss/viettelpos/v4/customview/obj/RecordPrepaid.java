package com.viettel.bss.viettelpos.v4.customview.obj;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "return", strict = false)
public class RecordPrepaid implements Serializable{
	@Element(name = "id", required = false)
	private String id;
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "actions", required = false)
	private String actions;
	@Element(name = "require", required = false)
	private String require;
	@Element(name = "alowReuser", required = false)
	private String alowReuser;
	@Element(name = "electronicSign", required = false)
	private Long electronicSign;

	private boolean isSelected = false;
	private Integer position;
	private String fullPath;
	private String pathOldProfile;
	private boolean isUseOldProfile = false;
	private String recordId;
	private ImageBO imageBO;
	private ProfileRecord profileRecord;

	public RecordPrepaid(String id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public RecordPrepaid(String id, String code, String name, String actions) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.actions = actions;
	}
	public RecordPrepaid(String id, String code, String name, String actions , ImageBO imageBO) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.actions = actions;
		this.imageBO = imageBO;
	}
	public RecordPrepaid(String id, String code, String name, String actions , ProfileRecord imageBO) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.actions = actions;
		this.profileRecord = imageBO;
	}

	public Long getElectronicSign() {
		if (electronicSign == null) {
			electronicSign = new Long(0);
		}
		return electronicSign;
	}

	public void setElectronicSign(Long electronicSign) {
		this.electronicSign = electronicSign;
	}

	public ProfileRecord getProfileRecord() {
		return profileRecord;
	}

	public void setProfileRecord(ProfileRecord profileRecord) {
		this.profileRecord = profileRecord;
	}

	public ImageBO getImageBO() {
		return imageBO;
	}

	public void setImageBO(ImageBO imageBO) {
		this.imageBO = imageBO;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public RecordPrepaid() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}

	public String getAlowReuser() {
		return alowReuser;
	}

	public void setAlowReuser(String alowReuser) {
		this.alowReuser = alowReuser;
	}

	@Override
	public String toString() {
		return code + " - " + name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getPathOldProfile() {
		return pathOldProfile;
	}

	public void setPathOldProfile(String pathOldProfile) {
		this.pathOldProfile = pathOldProfile;
	}

	public boolean isUseOldProfile() {
		return isUseOldProfile;
	}

	public void setIsUseOldProfile(boolean isUseOldProfile) {
		this.isUseOldProfile = isUseOldProfile;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
}
