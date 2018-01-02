package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hantt47 on 11/20/2017.
 */
@Root(name = "spin", strict = false)
public class SpinV2 {

    @Element(name = "id", required = false)
    private String id;
    @Element(name = "value", required = false)
    private String value;
    @Element(name = "code", required = false)
    private String code;
    @Element(name = "name", required = false)
    private String name;

    private String isdnCalled;

    @Element(name = "nodeCode", required = false)
    private String nodeCode;
    @Element(name = "splitterCode", required = false)
    private String splitterCode;

    public String getSplitterCode() {
        return splitterCode;
    }

    public void setSplitterCode(String splitterCode) {
        this.splitterCode = splitterCode;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getIsdnCalled() {
        return isdnCalled;
    }

    public void setIsdnCalled(String isdnCalled) {
        this.isdnCalled = isdnCalled;
    }

    public SpinV2(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SpinV2() {
    }

    public SpinV2(String id, String value) {
        super();
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {


        if(id != null && value != null){
            return id + "-" + value;
        }


        if (value == null || value.isEmpty()) {
            return name;
        }else{
            return value;
        }
    }

}
