package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Administrator on 1/4/2017.
 */
@Root(name = "DebitSub",strict = false)
public class DebitSub {
    @Element(name = "subId", required = false)
    private String subId;
    @Element(name = "staOfCycle", required = false)
    private String staOfCycle;
    @Element(name = "isdn", required = false)
    private String isdn;
    private Boolean isCheck = false;

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getStaOfCycle() {
        return staOfCycle;
    }

    public void setStaOfCycle(String staOfCycle) {
        this.staOfCycle = staOfCycle;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
}
