package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 4/19/2017.
 */

@Root(name = "return", strict = false)
public class WarningStaff implements Serializable{
    @Element(name = "columnName", required = false)
    private String columnName;
    @Element(name = "columnOrder", required = false)
    private int columnOrder;
    @Element(name = "columnValue", required = false)
    private String columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(int columnOrder) {
        this.columnOrder = columnOrder;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
