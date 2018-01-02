package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by thiendn1 on 8/4/2015.
 */
public class UploadedFileDTO implements Serializable {
    private String fileName;
    private long size;
    private byte[] contents;
    private String contentsOfArray;//chuyen mang byte sang dang chuoi theo dinh dạng UTF8. nếu truyền giá trị này thì không cần truyền biên contents nhưng phải chuyển ngược lại
    private String contentType;
    private String pathFile;
    private String fileCode;
    private boolean retrySelectFile;

    //thienlt them de luu url tuyet doi tren thu muc temp cua WEB____sua lai khong dung` cac thuoc tinh ben tren nua
    private String fullPathReal;
    private String fullPathUrl;
    private Long actionProfileId;
    private Long recordId;
    private String fileNameScan;

    private Long receptionId;
    private String recordNameHs;

    public UploadedFileDTO() {

    }

//    public UploadedFileDTO(ObjectFileDTO objectFile) {
//        if (objectFile != null) {
//            this.contents = objectFile.getFileAttach();
//            this.size = objectFile.getFileAttach().length;
//            this.fileName = objectFile.getName();
//
//        }
//    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getFullPathReal() {
        return fullPathReal;
    }

    public void setFullPathReal(String fullPathReal) {
        this.fullPathReal = fullPathReal;
    }

    public String getContentsOfArray() {
        return contentsOfArray;
    }

    public void setContentsOfArray(String contentsOfArray) {
        this.contentsOfArray = contentsOfArray;
    }

    public String getFullPathUrl() {
        return fullPathUrl;
    }

    public void setFullPathUrl(String fullPathUrl) {
        this.fullPathUrl = fullPathUrl;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getActionProfileId() {
        return actionProfileId;
    }

    public void setActionProfileId(Long actionProfileId) {
        this.actionProfileId = actionProfileId;
    }

    public String getFileNameScan() {
        return fileNameScan;
    }

    public void setFileNameScan(String fileNameScan) {
        this.fileNameScan = fileNameScan;
    }

    public Long getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Long receptionId) {
        this.receptionId = receptionId;
    }

    public String getRecordNameHs() {
        return recordNameHs;
    }

    public void setRecordNameHs(String recordNameHs) {
        this.recordNameHs = recordNameHs;
    }

    public boolean isRetrySelectFile() {
        return retrySelectFile;
    }

    public void setRetrySelectFile(boolean retrySelectFile) {
        this.retrySelectFile = retrySelectFile;
    }
}
