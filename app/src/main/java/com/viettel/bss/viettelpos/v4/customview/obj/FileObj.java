package com.viettel.bss.viettelpos.v4.customview.obj;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderInfo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FileObj implements Serializable {
	private String codeSpinner;
	private File file;
	private String path;
	private String fullPath;
	private String name;

	private String actionCode;
	private String reasonId;
	private String isdn;
	private String actionAudit;
	private String pageIndex;
	private String pageSize;
	private int status;
	private int id;
	private String recodeName;
	private int uploadStatus = 0;

	private String fileLength = "";
	private String fileLengthOrigin = "";
	private Long recordId;
	private String fileName;
	private String actionProfileId;

	private String fileContent;
	private String recordTypeId;
	private String actions;
	private boolean isUseOldProfile = false;

	private ImageBO imageBO;
	private String fileExtention;
	private String electronicSign;
	private boolean isZip = false;
	private String url;
	private ArrayList<OrderInfo> arrOrderInfo = new ArrayList<>();

	public ArrayList<OrderInfo> getArrOrderInfo() {
		return arrOrderInfo;
	}

	public void setArrOrderInfo(ArrayList<OrderInfo> arrOrderInfo) {
		this.arrOrderInfo = arrOrderInfo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isZip() {
		return isZip;
	}

	public void setZip(boolean zip) {
		isZip = zip;
	}

	public String getElectronicSign() {
		return electronicSign;
	}

	public void setElectronicSign(String electronicSign) {
		this.electronicSign = electronicSign;
	}

	public String getFileExtention() {
		return fileExtention;
	}

	public void setFileExtention(String fileExtention) {
		this.fileExtention = fileExtention;
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

	public String getRecordTypeId() {
		return recordTypeId;
	}

	public void setRecordTypeId(String recordTypeId) {
		this.recordTypeId = recordTypeId;
	}



	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public int getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getRecodeName() {
		return recodeName;
	}

	public void setRecodeName(String recodeName) {
		this.recodeName = recodeName;
	}

	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileLengthOrigin() {
		return fileLengthOrigin;
	}

	public void setFileLengthOrigin(String fileLengthOrigin) {
		this.fileLengthOrigin = fileLengthOrigin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getActionAudit() {
		return actionAudit;
	}

	public void setActionAudit(String actionAudit) {
		this.actionAudit = actionAudit;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileObj(String codeSpinner, File file, String path, String name) {
		super();
		this.codeSpinner = codeSpinner;
		this.file = file;
		this.path = path;
		this.name = name;
	}

	public FileObj(String codeSpinner, File file, String path) {
		super();
		this.codeSpinner = codeSpinner;
		this.file = file;
		this.path = path;
	}

	public FileObj(String codeSpinner, File file) {
		super();
		this.codeSpinner = codeSpinner;
		this.file = file;
	}

	public FileObj(File file, String path, String name) {
		super();
		this.file = file;
		this.path = path;
		this.name = name;
	}

	public FileObj() {
		super();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCodeSpinner() {
		return codeSpinner;
	}

	public void setCodeSpinner(String codeSpinner) {
		this.codeSpinner = codeSpinner;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getActionProfileId() {
		return actionProfileId;
	}

	public void setActionProfileId(String actionProfileId) {
		this.actionProfileId = actionProfileId;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public boolean isUseOldProfile() {
		return isUseOldProfile;
	}

	public void setIsUseOldProfile(boolean isUseOldProfile) {
		this.isUseOldProfile = isUseOldProfile;
	}



	@Override
	public String toString() {
		return "FileObj [codeSpinner=" + codeSpinner + ", file=" + file + ", path=" +
				path + ", name=" + name + ", actionCode=" + actionCode +
				", reasonId=" + reasonId + ", isdn=" + isdn + ", actionAudit=" +
				actionAudit + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize +
				", status=" + status + ", id=" + id + "]";
	}

	public FileObj clone(){
		FileObj obj = new FileObj();
		obj.setFileContent(this.getFileContent());
		obj.setUploadStatus(this.getUploadStatus());
		obj.setRecodeName(this.getRecodeName());
		obj.setFileLength(this.getFileLength());
		obj.setFileLengthOrigin(this.getFileLengthOrigin());
		obj.setReasonId(this.getReasonId());
		obj.setActionAudit(this.getActionAudit());
		obj.setPageIndex(this.getPageIndex());
		obj.setPageSize(this.getPageSize());
		obj.setIsdn(this.getIsdn());
		obj.setCodeSpinner(this.getCodeSpinner());
		obj.setRecordId(this.getRecordId());
		obj.setActionProfileId(this.getActionProfileId());
		obj.setName(this.getName());
		obj.setId(this.getId());
		obj.setFileName(this.getFileName());
		obj.setPath(this.getPath());
		obj.setFile(this.getFile());
		obj.setStatus(this.getStatus());
		obj.setActionCode(this.getActionCode());
		return obj;
	}
}
