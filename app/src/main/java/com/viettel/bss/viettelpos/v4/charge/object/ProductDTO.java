package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thuandq on 6/21/2017.
 */
@Root(name = "return", strict = false)
public class ProductDTO {
    @Element(name = "code", required = false)
    private String code;
    @Element(name = "serial", required = false)
    private String serial;
    @Element(name = "name", required = false)
    private String name;

    public ProductDTO(String code, String serial, String name) {
        this.code = code;
        this.serial = serial;
        this.name = name;
    }
    public ProductDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
