package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thinhhq1 on 6/16/2017.
 */

@Root(name = "return", strict = false)
public class DownloadFile {
    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "fileContent", required = false)
    private String fileContent;

    public DownloadFile() {
    }

    public DownloadFile(String errorCode, String description, String fileContent) {
        this.errorCode = errorCode;
        this.description = description;
        this.fileContent = fileContent;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
