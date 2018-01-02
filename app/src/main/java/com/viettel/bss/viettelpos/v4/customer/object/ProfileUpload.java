package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.File;
import java.util.List;

public class ProfileUpload {
	private List<FormBean> lstProfile;
	private List<File> lstFile;
	private String zipFilePath;
	private FormBean selectProfile;
	private String id;
	private String parentId;

	
	public String getZipFilePath() {
		return zipFilePath;
	}

	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<FormBean> getLstProfile() {
		return lstProfile;
	}

	public void setLstProfile(List<FormBean> lstProfile) {
		this.lstProfile = lstProfile;
	}

	public List<File> getLstFile() {
		return lstFile;
	}

	public void setLstFile(List<File> lstFile) {
		this.lstFile = lstFile;
	}

	public FormBean getSelectProfile() {
		return selectProfile;
	}

	public void setSelectProfile(FormBean selectProfile) {
		this.selectProfile = selectProfile;
	}

}
