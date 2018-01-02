package com.viettel.bss.viettelpos.v4.message;

/**
 * Created by Toancx on 1/19/2017.
 */

public class BaseMsg {
    private String errorCode = "";
    private String description = "";
    private int msgType = 1;

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

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}
