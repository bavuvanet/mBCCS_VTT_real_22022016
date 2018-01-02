package com.viettel.bss.viettelpos.v4.report.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 1/6/2017.
 */

@Root(name = "itemBean", strict = false)
public class ItemBean {
    @Element(name = "value", required = false)
    private Float value;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "groupName", required = false)
    private String groupName;

    private List<ItemBean> lstItemBean = new ArrayList<>();

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ItemBean> getLstItemBean() {
        return lstItemBean;
    }

    public void setLstItemBean(List<ItemBean> lstItemBean) {
        this.lstItemBean = lstItemBean;
    }
}
