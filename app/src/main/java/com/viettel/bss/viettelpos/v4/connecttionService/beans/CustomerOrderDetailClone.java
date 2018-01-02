package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thinhhq1 on 5/10/2017.
 */
@Root(name = "CustomerOrderDetailClone", strict = false)
public class CustomerOrderDetailClone implements Serializable{
    @Element(name = "custOrderDetailId", required = false)
    private Long custOrderDetailId;
    @Element(name = "telecomServiceId", required = false)
    private Long telecomServiceId;
    @Element(name = "subId", required = false)
    private Long subId;


    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Long getCustOrderDetailId() {
        return custOrderDetailId;
    }

    public void setCustOrderDetailId(Long custOrderDetailId) {
        this.custOrderDetailId = custOrderDetailId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }
}
