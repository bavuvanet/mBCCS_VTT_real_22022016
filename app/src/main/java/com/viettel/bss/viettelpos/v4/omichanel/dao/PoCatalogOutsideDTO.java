package com.viettel.bss.viettelpos.v4.omichanel.dao;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 9/14/2017.
 */
@Root(name = "return", strict = false)
public class PoCatalogOutsideDTO implements Serializable {

    @Element(name = "code", required = false)
    protected String code;
    @Element(name = "description", required = false)
    protected String description;
    @ElementList(name = "lstPoCatalogDTO", entry = "lstPoCatalogDTO", required = false, inline = true)
    protected List<PoCatalogDTO> lstPoCatalogDTO;
    @Element(name = "name", required = false)
    protected String name;
    @Element(name = "poPolicyTypeCode", required = false)
    protected String poPolicyTypeCode;
    @Element(name = "poPolicyTypeId", required = false)
    protected Long poPolicyTypeId;
    @Element(name = "poPolicyTypeName", required = false)
    protected String poPolicyTypeName;
    @Element(name = "status", required = false)
    protected String status;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Gets the value of the code property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescription() {
        String descString = getValueOfName("desc");
        if (!CommonActivity.isNullOrEmpty(descString)) {
            return descString;
        } else if (!CommonActivity.isNullOrEmpty(description)) {
            return description;
        } else {
            return "";
        }
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the lstPoCatalogDTO property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstPoCatalogDTO property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstPoCatalogDTO().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PoCatalogDTO }
     *
     *
     */
    public List<PoCatalogDTO> getLstPoCatalogDTO() {
        if (lstPoCatalogDTO == null) {
            lstPoCatalogDTO = new ArrayList<PoCatalogDTO>();
        }
        return this.lstPoCatalogDTO;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the poPolicyTypeCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPoPolicyTypeCode() {
        return poPolicyTypeCode;
    }

    /**
     * Sets the value of the poPolicyTypeCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPoPolicyTypeCode(String value) {
        this.poPolicyTypeCode = value;
    }

    /**
     * Gets the value of the poPolicyTypeId property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getPoPolicyTypeId() {
        return poPolicyTypeId;
    }

    /**
     * Sets the value of the poPolicyTypeId property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setPoPolicyTypeId(Long value) {
        this.poPolicyTypeId = value;
    }

    /**
     * Gets the value of the poPolicyTypeName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPoPolicyTypeName() {
        return poPolicyTypeName;
    }

    /**
     * Sets the value of the poPolicyTypeName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPoPolicyTypeName(String value) {
        this.poPolicyTypeName = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatus(String value) {
        this.status = value;
    }

    public String getDetail() {
        return getValueOfName("desc");
    }

    public String getPrice() {
        return getValueOfName("fee");
    }

    public String getVasName() {
        return getValueOfName("vasName");
    }
    public String getGroup() {
        return getValueOfName("serviceType");
    }

    public String getVasCode() {
        return getValueOfName("vasCode");
    }

    public String getBundleCode() {
        return getValueOfName("bundleCode");
    }

    public String getBundleName() {
        return getValueOfName("bundleName");
    }

    public String getBundleType() {
        return getValueOfName("bundleType");
    }

    public String getProductCode() {
        return getValueOfName("productMain");
    }

    public String getPromotionCode() {
        return getValueOfName("promotionCode");
    }

    public Long getRegReasonId() {
        return Long.parseLong(getValueOfName("REASON_ID"));
    }

    public String getRegReasonCode() {
        return getValueOfName("REASON_CODE");
    }

    public List<String> getLstMiCode() {
        List<String> miCodes = new ArrayList<>();
        if (!CommonActivity.isNullOrEmpty(lstPoCatalogDTO)) {
            for (PoCatalogDTO poCatalogDTO : lstPoCatalogDTO) {
                if (poCatalogDTO.getCode().equals("productData")
                        && !CommonActivity.isNullOrEmpty(poCatalogDTO.getValue())) {
                    miCodes.add(poCatalogDTO.getValue());
                }
            }
        }
        return miCodes;
    }

    public List<String> getLstVtFree() {
        List<String> vtFrees = new ArrayList<>();
        if (!CommonActivity.isNullOrEmpty(lstPoCatalogDTO)) {
            for (PoCatalogDTO poCatalogDTO : lstPoCatalogDTO) {
                if (poCatalogDTO.getCode().equals("productVTFree")
                        && !CommonActivity.isNullOrEmpty(poCatalogDTO.getValue())) {
                    vtFrees.add(poCatalogDTO.getValue());
                }
            }
        }
        return vtFrees;
    }

    private String getValueOfName(String name) {
        if (!CommonActivity.isNullOrEmpty(lstPoCatalogDTO)) {
            for (PoCatalogDTO poCatalogDTO : lstPoCatalogDTO) {
                if (poCatalogDTO.getCode().equals(name)) {
                    return poCatalogDTO.getValue();
                }
            }
        }
        return "0";
    }
}
