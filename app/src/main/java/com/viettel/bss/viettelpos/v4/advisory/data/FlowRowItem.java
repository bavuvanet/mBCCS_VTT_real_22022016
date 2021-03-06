package com.viettel.bss.viettelpos.v4.advisory.data;

/**
 * Created by hantt47 on 7/13/2017.
 */

public class FlowRowItem {
    private String name;
    private String value1;
    private String value2;
    private String value3;
    private String unit;

    public FlowRowItem(String name, String value1, String value2, String value3, String unit) {
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
