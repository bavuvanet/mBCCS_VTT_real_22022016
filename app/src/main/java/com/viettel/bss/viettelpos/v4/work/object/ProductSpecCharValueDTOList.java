package com.viettel.bss.viettelpos.v4.work.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thientv7 on 5/25/2017.
 */
@Root(name = "ProductSpecCharValueDTOList",strict=false)
public class ProductSpecCharValueDTOList {
    @Element(name = "valueFrom", required = false)
    private String valueFrom;
    @Element(name = "valueTo", required = false)
    private String valueTo;
    @Element(name = "isDefault", required = false)
    private String isDefault;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "value", required = false)
    private String id;
    @Element(name = "valueData", required = false)
    public String valueData;

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueData() {
        return valueData;
    }

    public void setValueData(String valueData) {
        this.valueData = valueData;
    }
}
