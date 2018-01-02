package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;
import java.util.Date;

public class RecordDTO implements Serializable {

    protected String directory;
    protected String fileContent; //encode base64
    protected Long incorectScan;
    protected String message;
    protected String password;
    protected String recordStatusStr;
    protected String serverIp;
    protected String userName;

    private Long actionProfileId;
    private Long recordId;
    private String recordPath;
    private Long recordStatus;
    private Date dateReceive;
    private Long staffId;
    private Date lastModify;
    private Long recordTypeId;
    private String recordNameScan;
    private Long reqScan;
    private Long fakeRecord;
    private Long serverId;
    private String recordCode;
    private String recordName;

    private boolean edit;//thienlt

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getRecordCode()
    {
        return this.recordCode;
    }

    public void setRecordCode(String recordCode)
    {
        this.recordCode = recordCode;
    }

    public String getRecordName()
    {
        return this.recordName;
    }

    public void setRecordName(String recordName)
    {
        this.recordName = recordName;
    }

    public Long getServerId()
    {
        return this.serverId;
    }

    public void setServerId(Long serverId)
    {
        this.serverId = serverId;
    }

    public Long getFakeRecord()
    {
        return this.fakeRecord;
    }

    public void setFakeRecord(Long fakeRecord)
    {
        this.fakeRecord = fakeRecord;
    }

    public Long getReqScan()
    {
        return this.reqScan;
    }

    public void setReqScan(Long reqScan)
    {
        this.reqScan = reqScan;
    }

    public RecordDTO() {}

    public Long getRecordId()
    {
        return this.recordId;
    }

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public String getRecordPath()
    {
        return this.recordPath;
    }

    public void setRecordPath(String recordPath)
    {
        this.recordPath = recordPath;
    }

    public Long getRecordStatus()
    {
        return this.recordStatus;
    }

    public void setRecordStatus(Long recordStatus)
    {
        this.recordStatus = recordStatus;
    }

    public Date getDateReceive()
    {
        return this.dateReceive;
    }

    public void setDateReceive(Date dateReceive)
    {
        this.dateReceive = dateReceive;
    }

    public Long getStaffId()
    {
        return this.staffId;
    }

    public void setStaffId(Long staffId)
    {
        this.staffId = staffId;
    }

    public Date getLastModify()
    {
        return this.lastModify;
    }

    public void setLastModify(Date lastModify)
    {
        this.lastModify = lastModify;
    }

    public Long getRecordTypeId()
    {
        return this.recordTypeId;
    }

    public void setRecordTypeId(Long recordTypeId)
    {
        this.recordTypeId = recordTypeId;
    }

    public String getRecordNameScan()
    {
        return this.recordNameScan;
    }

    public void setRecordNameScan(String recordNameScan)
    {
        this.recordNameScan = recordNameScan;
    }

    public Long getActionProfileId() {
        return actionProfileId;
    }

    public void setActionProfileId(Long actionProfileId) {
        this.actionProfileId = actionProfileId;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

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

    public Long getIncorectScan() {
        return incorectScan;
    }

    public void setIncorectScan(Long incorectScan) {
        this.incorectScan = incorectScan;
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

    public String getRecordStatusStr() {
        return recordStatusStr;
    }

    public void setRecordStatusStr(String recordStatusStr) {
        this.recordStatusStr = recordStatusStr;
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
}
