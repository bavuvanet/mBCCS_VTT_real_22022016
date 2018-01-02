package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 7/5/2017.
 */

@Root(name = "return", strict = false)
public class RefillInfoData {

    @Element(name = "refillAmount", required = false)
    protected String refillAmount;
    @Element(name = "refillDate", required = false)
    protected String refillDate;

    public RefillInfoData() {

    }

    public String getRefillAmount() {
        return refillAmount;
    }

    public void setRefillAmount(String refillAmount) {
        this.refillAmount = refillAmount;
    }

    public String getRefillDate() {
        return refillDate;
    }

    public void setRefillDate(String refillDate) {
        this.refillDate = refillDate;
    }
}
