package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;

import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thientv7 on 5/25/2017.
 */
@Root(name = "lstProductSpecCharUseDTO",strict=false)
public class ProductSpecCharMappingDTO {

    private List<ProductSpecCharDTO> productSpecCharDTO;


}
