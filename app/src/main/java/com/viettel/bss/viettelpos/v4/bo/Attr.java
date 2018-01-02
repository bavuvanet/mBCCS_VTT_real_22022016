package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Toancx on 5/17/2017.
 */
@Root(name = "return", strict = false)
public class Attr implements Serializable{
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "value", required = false)
    private String value;
    @ElementList(name = "attrExts", entry = "attrExts", required = false, inline = true)
    private List<AttrExt> attrExts;


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

    public List<AttrExt> getAttrExts() {
        return attrExts;
    }

    public void setAttrExts(List<AttrExt> attrExts) {
        this.attrExts = attrExts;
    }
}
