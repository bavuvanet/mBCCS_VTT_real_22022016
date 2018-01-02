package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by hantt47 on 7/18/2017.
 */

@Root(name = "return", strict = false)
public class ProductGroupBean {

    @Element(name = "type", required = false)
    protected String type;
    @ElementList(name = "lstProductBeans", entry = "lstProductBeans", required = false, inline = true)
    private List<ProductBean> lstProductBeans;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProductBean> getLstProductBeans() {
        return lstProductBeans;
    }

    public void setLstProductBeans(List<ProductBean> lstProductBeans) {
        this.lstProductBeans = lstProductBeans;
    }
}
