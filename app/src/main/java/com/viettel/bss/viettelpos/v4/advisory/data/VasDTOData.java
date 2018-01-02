package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 7/5/2017.
 */
@Root(name = "return", strict = false)
public class VasDTOData {
    @Element(name = "vasName", required = false)
    private String vasName;
    @Element(name = "staDatetime", required = false)
    private String staDatetime;

    public String getVasName() {
        return vasName;
    }

    public void setVasName(String vasName) {
        this.vasName = vasName;
    }

    public String getStaDatetime() {
        return staDatetime;
    }

    public void setStaDatetime(String staDatetime) {
        this.staDatetime = staDatetime;
    }
}
