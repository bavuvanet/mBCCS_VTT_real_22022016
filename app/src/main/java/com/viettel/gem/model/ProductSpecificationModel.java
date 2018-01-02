package com.viettel.gem.model;

import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return", strict = false)
public class ProductSpecificationModel {

    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "isCheckPrduct", required = false)
    private boolean isCheckPrduct;
    @Element(name = "isMoreThan", required = false)
    private boolean isMoreThan;
    @Element(name = "isCompany", required = false)
    private int isCompany;
    @Element(name = "refuseCollect", required = false)
    private boolean refuseCollect;
    @Element(name = "isCVT", required = false)
    private boolean isCVT;
    @Element(name = "isPSenTdO", required = false)
    private boolean isPSenTdO;

    @ElementList(name = "lstProductSpecificationDTOs", entry = "lstProductSpecificationDTOs", required = false, inline = true)
    private List<ProductSpecificationDTO> lstProductSpecificationDTOs = new ArrayList<>();

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isCheckPrduct() {
        return isCheckPrduct;
    }

    public void setCheckPrduct(boolean checkPrduct) {
        isCheckPrduct = checkPrduct;
    }

    public boolean isMoreThan() {
        return isMoreThan;
    }

    public void setMoreThan(boolean moreThan) {
        isMoreThan = moreThan;
    }

    public int getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(int isCompany) {
        this.isCompany = isCompany;
    }

    public boolean isRefuseCollect() {
        return refuseCollect;
    }

    public void setRefuseCollect(boolean refuseCollect) {
        this.refuseCollect = refuseCollect;
    }

    public boolean isCVT() {
        return isCVT;
    }

    public void setCVT(boolean CVT) {
        isCVT = CVT;
    }

    public boolean isPSenTdO() {
        return isPSenTdO;
    }

    public void setPSenTdO(boolean PSenTdO) {
        isPSenTdO = PSenTdO;
    }

    public List<ProductSpecificationDTO> getLstProductSpecificationDTOs() {
        return lstProductSpecificationDTOs;
    }

    public void setLstProductSpecificationDTOs(List<ProductSpecificationDTO> lstProductSpecificationDTOs) {
        this.lstProductSpecificationDTOs = lstProductSpecificationDTOs;
    }
}
