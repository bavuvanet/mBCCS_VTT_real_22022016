package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.File;

import android.graphics.Bitmap;

public class ActionRecordBean {
	private String recordCode;
	private String recordName;
	private String recordTypeId;
	private String reqScan;
	private String sourceRecordTypeId;
	private String groupId;
	
	private String imageLink;
	private String imageName;
	private Bitmap bmpImage;
	private File file;
	
	
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Bitmap getBmpImage() {
		return bmpImage;
	}
	public void setBmpImage(Bitmap bmpImage) {
		this.bmpImage = bmpImage;
	}
	public String getRecordCode() {
		return recordCode;
	}
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getRecordTypeId() {
		return recordTypeId;
	}
	public void setRecordTypeId(String recordTypeId) {
		this.recordTypeId = recordTypeId;
	}
	public String getReqScan() {
		return reqScan;
	}
	public void setReqScan(String reqScan) {
		this.reqScan = reqScan;
	}
	public String getSourceRecordTypeId() {
		return sourceRecordTypeId;
	}
	public void setSourceRecordTypeId(String sourceRecordTypeId) {
		this.sourceRecordTypeId = sourceRecordTypeId;
	}

	
}
