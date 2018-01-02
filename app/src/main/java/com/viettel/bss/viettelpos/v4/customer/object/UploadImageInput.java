package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;

public class UploadImageInput {
	@Element(name = "fileContent", required = false)
    private String fileContent;
	@Element(name = "isdn", required = false)
    private String isdn;
	@Element(name = "recordCode", required = false)
    private String recordCode;
	@Element(name = "actionCode", required = false)
    private String actionCode;
	@Element(name = "reasonId", required = false)
    private String reasonId;
	@Element(name = "actionAudit", required = false)
    private Long actionAudit;
	@Element(name = "pageIndex", required = false)
    private Long pageIndex;
	@Element(name = "pageSize", required = false)
    private Long pageSize;    
	@Element(name = "fileLengthOrigin", required = false)
    private String fileLengthOrigin;
	@Element(name = "recordName", required = false)
    private String recordName;
	@Element(name = "recordId", required = false)
    private Long recordId;
	@Element(name = "fileName", required = false)
    private String fileName;
	@Element(name = "actionProfileId", required = false)
    private String actionProfileId;
	@Element(name = "staffId", required = false)
    private String staffId;
	@Element(name = "xUpdate", required = false)
    private Long xUpdate;
	@Element(name = "yUpdate", required = false)
    private Long yUpdate;
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getRecordCode() {
		return recordCode;
	}
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public Long getActionAudit() {
		return actionAudit;
	}
	public void setActionAudit(Long actionAudit) {
		this.actionAudit = actionAudit;
	}
	public Long getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public String getFileLengthOrigin() {
		return fileLengthOrigin;
	}
	public void setFileLengthOrigin(String fileLengthOrigin) {
		this.fileLengthOrigin = fileLengthOrigin;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public Long getxUpdate() {
		return xUpdate;
	}
	public void setxUpdate(Long xUpdate) {
		this.xUpdate = xUpdate;
	}
	public Long getyUpdate() {
		return yUpdate;
	}
	public void setyUpdate(Long yUpdate) {
		this.yUpdate = yUpdate;
	}
	
	
}
