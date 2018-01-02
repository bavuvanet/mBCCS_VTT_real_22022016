package com.viettel.bss.viettelpos.v4.bo;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class ApParamBO {
    @Element(name = "parName", required = false)
    private String parName;
    @Element(name = "parType", required = false)
    private String parType;
    @Element(name = "parValue", required = false)
    private String parValue;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "id", required = false)
    private Long id;
    @Element(name = "status", required = false)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getXML() {
        StringBuilder xml = new StringBuilder();
        if (!CommonActivity.isNullOrEmpty(parName))
            xml.append("<parName>").append(parName.trim()).append("</parName>");
        if (!CommonActivity.isNullOrEmpty(parType))
            xml.append("<parType>").append(parType.trim()).append("</parType>");
        if (!CommonActivity.isNullOrEmpty(parValue))
            xml.append("<parValue>").append(parValue.trim()).append("</parValue>");
        if (!CommonActivity.isNullOrEmpty(status))
            xml.append("<status>").append(status).append("</status>");
        if (!CommonActivity.isNullOrEmpty(description))
            xml.append("<description>").append(description.trim()).append("</description>");
        if (!CommonActivity.isNullOrEmpty(id) && id != -1)
            xml.append("<id>").append(id).append("</id>");
        return xml.toString();
    }
}
