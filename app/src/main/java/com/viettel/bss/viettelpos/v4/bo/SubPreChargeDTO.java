package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thuandq on 10/22/2017.
 */
@Root(name = "return", strict = false)
public class SubPreChargeDTO implements Serializable {
    @Element(name = "monthId", required = false)
    protected String monthId;
    @Element(name = "totalCharge", required = false)
    protected Double totalCharge;

    public String getMonthId() {
        return monthId;
    }

    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }
}
