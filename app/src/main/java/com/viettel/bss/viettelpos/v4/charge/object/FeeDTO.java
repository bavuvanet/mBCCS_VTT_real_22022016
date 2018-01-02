package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 6/22/2017.
 */
@Root(name = "return", strict = false)
public class FeeDTO {
    @Element(name = "code", required = false)
    private String typeFee;
    @Element(name = "fee", required = false)
    private String fee;
    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "description", required = false)
    private String description;

    public FeeDTO(String typeFee, String fee) {
        this.typeFee = typeFee;
        this.fee = fee;
    }
    public FeeDTO() {
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

    public String getTypeFee() {
        return typeFee;
    }

    public void setTypeFee(String typeFee) {
        this.typeFee = typeFee;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
