package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thientv7 on 6/6/2017.
 */
@Root(name = "ProductSpecCharDTO", strict = false)
public class ProductSpecCharValueDTO implements Serializable {

    @Element(name = "isDefault", required = false)
    private int isDefault;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
