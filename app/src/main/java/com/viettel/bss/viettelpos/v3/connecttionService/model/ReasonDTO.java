package com.viettel.bss.viettelpos.v3.connecttionService.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thinhhq1 on 6/12/2017.
 */
@Root(name = "ReasonDTO", strict = false)
public class ReasonDTO implements Serializable{
    @Element(name = "reasonCode", required = false)
    private String reasonCode;
    @Element(name = "reasonId", required = false)
    private String reasonId;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "code", required = false)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
