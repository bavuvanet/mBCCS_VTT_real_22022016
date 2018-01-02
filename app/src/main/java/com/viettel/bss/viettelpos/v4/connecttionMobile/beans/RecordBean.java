package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "return", strict = false)
public class RecordBean implements Serializable{
	@Element(name = "message", required = false)
	private String message;
	@Element(name = "directory", required = false)
	private String directory;
	@Element(name = "fileContent", required = false)
	private String fileContent;
	@Element(name = "password", required = false)
	private String password;
	@Element(name = "recordCode", required = false)
	private String recordCode;
	@Element(name = "recordId", required = false)
	private Long recordId;
	@Element(name = "recordName", required = false)
	private String recordName;
	@Element(name = "recordNameScan", required = false)
	private String recordNameScan;
	@Element(name = "recordPath", required = false)
	private String recordPath;
	@Element(name = "serverId", required = false)
	private Long serverId;
	@Element(name = "serverIp", required = false)
	private String serverIp;
	@Element(name = "userName", required = false)
	private String userName;
	@Element(name = "fileName", required = false)
	private String fileName;
	@Element(name = "filePath", required = false)
	private String filePath;
    @Element(name = "recordStatus", required = false)
    private String recordStatus;
    @Element(name = "recordStatusStr", required = false)
    private String recordStatusStr;

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordNameScan() {
		return recordNameScan;
	}

	public void setRecordNameScan(String recordNameScan) {
		this.recordNameScan = recordNameScan;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordStatusStr() {
        return recordStatusStr;
    }

    public void setRecordStatusStr(String recordStatusStr) {
        this.recordStatusStr = recordStatusStr;
    }
}
