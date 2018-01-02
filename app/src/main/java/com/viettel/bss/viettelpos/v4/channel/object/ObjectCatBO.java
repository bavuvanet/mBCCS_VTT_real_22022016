package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.File;
import java.io.Serializable;

import android.graphics.Bitmap;

public class ObjectCatBO implements Serializable{
	private String broadTypeName;
	private String broadTypeValue;
	private String code;
	private String haveImage;
	private String id;
	private String name;
	private String imageLink;
	private String imageName;
	private Bitmap bmpImage;
	
	// yeu cau
	private String requestType;
	private String lenghSign;
	private String widthSign;
	private String price;
	private String dateInstall;
	private File file;
	
	// month
	// trang thai bien hieu
	private String statusObj;
	// ly do
	private String reason;
	
	private String number;
	
	private String manageAssetId;
	
	public String getManageAssetId() {
		return manageAssetId;
	}
	public void setManageAssetId(String manageAssetId) {
		this.manageAssetId = manageAssetId;
	}
	public String getStatusObj() {
		return statusObj;
	}
	public void setStatusObj(String statusObj) {
		this.statusObj = statusObj;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getLenghSign() {
		return lenghSign;
	}
	public void setLenghSign(String lenghSign) {
		this.lenghSign = lenghSign;
	}
	public String getWidthSign() {
		return widthSign;
	}
	public void setWidthSign(String widthSign) {
		this.widthSign = widthSign;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDateInstall() {
		return dateInstall;
	}
	public void setDateInstall(String dateInstall) {
		this.dateInstall = dateInstall;
	}
	public Bitmap getBmpImage() {
		return bmpImage;
	}
	public void setBmpImage(Bitmap bmpImage) {
		this.bmpImage = bmpImage;
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
	public String getBroadTypeName() {
		return broadTypeName;
	}
	public void setBroadTypeName(String broadTypeName) {
		this.broadTypeName = broadTypeName;
	}
	public String getBroadTypeValue() {
		return broadTypeValue;
	}
	public void setBroadTypeValue(String broadTypeValue) {
		this.broadTypeValue = broadTypeValue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHaveImage() {
		return haveImage;
	}
	public void setHaveImage(String haveImage) {
		this.haveImage = haveImage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
