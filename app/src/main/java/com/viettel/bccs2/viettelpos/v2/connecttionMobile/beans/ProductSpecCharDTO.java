package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.object.ProductSpecCharValueDTO;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;
import java.util.List;

@Root(name = "ProductSpecCharDTO", strict = false)
public class ProductSpecCharDTO implements Serializable {
    @Element(name = "name", required = false)
    public String name;
    @Element(name = "code", required = false)
    public String code;
    @Element(name = "maxCardinality", required = false)
    public int maxCardinality;
    @Element(name = "minCardinality", required = false)
    public int minCardinality;
    @Element(name = "valueType", required = false)
    public int valueType;
    @Element(name = "valueData", required = false)
    public String valueData;
    @Element(name = "productSpecCharValueDTO", required = false)
    public ProductSpecCharValueDTO productSpecCharValueDTO;

////	@Element (name = "productSpecCharValueDTOList", required = false)
//    @Path("productSpecCharValueDTOList")
//	@Element(required=false)
//	public ProductSpecCharValueDTOList productSpecCharValueDTOList;
//
//	public ProductSpecCharValueDTOList getProductSpecCharValueDTOList() {
//		return productSpecCharValueDTOList;
//	}
//
//	public void setProductSpecCharValueDTOList(ProductSpecCharValueDTOList productSpecCharValueDTOList) {
//		this.productSpecCharValueDTOList = productSpecCharValueDTOList;
//	}


    @ElementList(name = "productSpecCharValueDTOList", entry = "productSpecCharValueDTOList", required = false, inline = true)
    private List<ProductSpecCharValueDTOList> productSpecCharValueDTOList;


    public ProductSpecCharValueDTO getProductSpecCharValueDTO() {
        return productSpecCharValueDTO;
    }

    public void setProductSpecCharValueDTO(ProductSpecCharValueDTO productSpecCharValueDTO) {
        this.productSpecCharValueDTO = productSpecCharValueDTO;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getValueData() {
        return valueData;
    }

    public void setValueData(String valueData) {
        this.valueData = valueData;
    }

    public int getMaxCardinality() {
        return maxCardinality;
    }

    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public int getMinCardinality() {
        return minCardinality;
    }

    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSpecCharValueDTOList> getProductSpecCharValueDTOList() {
        return productSpecCharValueDTOList;
    }

    public void setProductSpecCharValueDTOList(List<ProductSpecCharValueDTOList> productSpecCharValueDTOList) {
        this.productSpecCharValueDTOList = productSpecCharValueDTOList;
    }
}
