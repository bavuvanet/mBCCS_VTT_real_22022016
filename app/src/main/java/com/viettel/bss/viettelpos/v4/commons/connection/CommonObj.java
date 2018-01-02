package com.viettel.bss.viettelpos.v4.commons.connection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ICHI on 6/2/2017.
 */
@Root(name = "return", strict=false)
public class CommonObj {
    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "description", required = false)
    private String description;

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
}
