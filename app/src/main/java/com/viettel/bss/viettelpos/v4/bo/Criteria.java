package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by toancx on 6/6/2017.
 */
@Root(name = "return", strict = false)
public class Criteria {
    @Element(name = "code", required = false)
    private String code;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "value", required = false)
    private String value;
    @Element(name = "id", required = false)
    private String id;
    @Element(name = "checked", required = false)
    private Boolean checked = true;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
