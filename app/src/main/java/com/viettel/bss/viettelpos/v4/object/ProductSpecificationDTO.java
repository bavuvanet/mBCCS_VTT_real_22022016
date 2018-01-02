package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharUseDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "productSpecificationDTO", strict = false)
public class ProductSpecificationDTO {
    @Element(name = "name", required = false)
    private String name;
    @ElementList(name = "lstProductSpecCharUseDTO", entry = "lstProductSpecCharUseDTO", required = false, inline = true)
    public List<ProductSpecCharUseDTO> lstProductSpecCharUseDTO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSpecCharUseDTO> getLstProductSpecCharUseDTO() {
        return lstProductSpecCharUseDTO;
    }

    public void setLstProductSpecCharUseDTO(List<ProductSpecCharUseDTO> lstProductSpecCharUseDTO) {
        this.lstProductSpecCharUseDTO = lstProductSpecCharUseDTO;
    }
}
