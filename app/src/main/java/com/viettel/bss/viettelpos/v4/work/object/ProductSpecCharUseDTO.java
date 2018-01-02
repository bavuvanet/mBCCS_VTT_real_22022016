package com.viettel.bss.viettelpos.v4.work.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thientv7 on 5/26/2017.
 */
@Root(name = "ProductSpecCharUseDTO", strict = false)
public class ProductSpecCharUseDTO {
//    @ElementList(name = "productSpecCharDTO", entry = "productSpecCharDTO", required = false, inline = true)
//    public List<ProductSpecCharDTO> listProductSpecCharDTOs;

    @Element(name = "productSpecCharDTO", required = false)
    public ProductSpecCharDTO productSpecCharDTO;

    public ProductSpecCharDTO getListProductSpecCharDTOs() {
        return productSpecCharDTO;
    }

    public void setListProductSpecCharDTOs(ProductSpecCharDTO listProductSpecCharDTOs) {
        this.productSpecCharDTO = listProductSpecCharDTOs;
    }
}
