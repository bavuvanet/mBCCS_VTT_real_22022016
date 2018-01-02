package com.viettel.bss.viettelpos.v4.bankplus.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hantt47 on 7/27/2017.
 */
@Root(name = "return", strict = false)
public class TppTransDTO {
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "strCreateDate", required = false)
    private String strCreateDate;
    @Element(name = "requestId", required = false)
    private String requestId;
    @Element(name = "appName", required = false)
    private String appName;
    @Element(name = "id", required = false)
    private String id;
    @Element(name = "requestType", required = false)
    private String requestType;
    @Element(name = "amount", required = false)
    private String amount;
    @Element(name = "numSendSms", required = false)
    private String numSendSms;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumSendSms() {
        return numSendSms;
    }

    public void setNumSendSms(String numSendSms) {
        this.numSendSms = numSendSms;
    }
}
