package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Toancx on 4/19/2017.
 */

@Root(name = "return", strict = false)
public class WarningStaffBO implements Serializable{
    @ElementList(name = "lstWarningStaff", entry = "lstWarningStaff", required = false, inline = true)
    private List<WarningStaff> lstWarningStaff;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "level", required = false)
    private Integer level;

    public List<WarningStaff> getLstWarningStaff() {
        return lstWarningStaff;
    }

    public void setLstWarningStaff(List<WarningStaff> lstWarningStaff) {
        this.lstWarningStaff = lstWarningStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
