package com.viettel.bss.viettelpos.v3.connecttionService.model;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhhq1 on 6/12/2017.
 */
@Root(name = "ReasonFullDTO", strict = false)
public class ReasonFullDTO implements Serializable{
    @Element(name = "reasonDTO", required = false)
    private ReasonDTO reasonDTO;
    @ElementList(name = "lstPackageOffer", entry = "lstPackageOffer", required = false, inline = true)
    protected ArrayList<ProductOfferingDTO> lstPackageOffer;



    public ReasonDTO getReasonDTO() {
        return reasonDTO;
    }

    public void setReasonDTO(ReasonDTO reasonDTO) {
        this.reasonDTO = reasonDTO;
    }

    public ArrayList<ProductOfferingDTO> getLstPackageOffer() {
        return lstPackageOffer;
    }

    public void setLstPackageOffer(ArrayList<ProductOfferingDTO> lstPackageOffer) {
        this.lstPackageOffer = lstPackageOffer;
    }
}
