package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 5/17/2017.
 */
@Root(name = "return", strict = false)
public class AttrExt implements Serializable{
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "value", required = false)
    private String value;
    @Element(name = "valueInc", required = false)
    private Long valueInc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getValueInc() {
        return valueInc;
    }

    public void setValueInc(Long valueInc) {
        this.valueInc = valueInc;
    }
}
