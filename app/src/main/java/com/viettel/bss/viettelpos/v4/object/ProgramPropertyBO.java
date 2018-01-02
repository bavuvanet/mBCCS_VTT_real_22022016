package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Toancx on 4/21/2017.
 */

@Root(name = "return", strict = false)
public class ProgramPropertyBO {
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "programId", required = false)
    private Integer programId;
    @Element(name = "value", required = false)
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
