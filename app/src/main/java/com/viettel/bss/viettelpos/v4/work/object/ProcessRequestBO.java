package com.viettel.bss.viettelpos.v4.work.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thientv7 on 5/19/2017.
 */
@Root(name = "return",strict=false)
public class ProcessRequestBO {

    @Element(name = "reciveDate", required = false)
    String reciveDate;
    @Element(name = "processDate", required = false)
    String processDate;
    @Element(name = "userCreate", required = false)
    String userCreate;
    @Element(name = "status", required = false)
    String status;
    @Element(name = "contentProcess", required = false)
    String contentProcess;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentProcess() {
        return contentProcess;
    }

    public void setContentProcess(String contentProcess) {
        this.contentProcess = contentProcess;
    }

    public String getReciveDate() {
        return reciveDate;
    }

    public void setReciveDate(String reciveDate) {
        this.reciveDate = reciveDate;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }
}
