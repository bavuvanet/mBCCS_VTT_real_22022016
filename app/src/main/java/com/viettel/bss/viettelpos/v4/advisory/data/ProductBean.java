package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hantt47 on 7/18/2017.
 */
@Root(name = "return", strict = false)
public class ProductBean {

    @Element(name = "code", required = false)
    protected String code;
    @Element(name = "type", required = false)
    protected String type;
    @Element(name = "desc", required = false)
    protected String desc;
    @Element(name = "price", required = false)
    protected String price;
    @Element(name = "bonus", required = false)
    protected String bonus;

    public ProductBean(String code, String type, String desc, String price, String bonus) {
        this.code = code;
        this.type = type;
        this.desc = desc;
        this.price = price;
        this.bonus = bonus;
    }

    public ProductBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
