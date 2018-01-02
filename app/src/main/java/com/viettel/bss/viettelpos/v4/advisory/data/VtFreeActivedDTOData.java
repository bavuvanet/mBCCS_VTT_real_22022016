package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thinhhq1 on 7/5/2017.
 */
@Root(name = "return", strict = false)
public class VtFreeActivedDTOData {
    @Element(name = "productCode", required = false)
    private String productCode;
    @Element(name = "productName", required = false)
    private String productName;

    public VtFreeActivedDTOData() {

    }

    public String getProductName() {
        return productName;
    }

    public String getProductCode() {
        return productCode;
    }
}
