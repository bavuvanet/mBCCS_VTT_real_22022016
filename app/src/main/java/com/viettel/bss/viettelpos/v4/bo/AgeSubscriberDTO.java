package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thuandq on 10/22/2017.
 */

@Root(name = "return", strict = false)
public class AgeSubscriberDTO implements Serializable{
    @Element(name = "age", required = false)
    protected Long age;
    @Element(name = "avgCharge", required = false)
    protected Double avgCharge;
    @ElementList(name = "subPreChargeDTOList", entry = "subPreChargeDTOList", required = false, inline = true)
    protected List<SubPreChargeDTO> subPreChargeDTOList;
    @Element(name = "totalCharge", required = false)
    protected Double totalCharge;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Double getAvgCharge() {
        return avgCharge;
    }

    public void setAvgCharge(Double avgCharge) {
        this.avgCharge = avgCharge;
    }

    public List<SubPreChargeDTO> getSubPreChargeDTOList() {
        return subPreChargeDTOList;
    }

    public void setSubPreChargeDTOList(List<SubPreChargeDTO> subPreChargeDTOList) {
        this.subPreChargeDTOList = subPreChargeDTOList;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }
}
