
package com.viettel.bss.viettelpos.v4.omichanel.dao;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.xml.datatype.XMLGregorianCalendar;

@Root(name = "MapStaffArea", strict = false)
public class MapStaffArea {
    @Element(name = "contactIsdn", required = false)
    protected String contactIsdn;
    @Element(name = "createUser", required = false)
    protected String createUser;
    @Element(name = "districtCode", required = false)
    protected String districtCode;
    @Element(name = "id", required = false)
    protected Long id;
    @Element(name = "precinctCode", required = false)
    protected String precinctCode;
    @Element(name = "provinceCode", required = false)
    protected String provinceCode;
    @Element(name = "staffId", required = false)
    protected Long staffId;
    @Element(name = "status", required = false)
    protected Long status;
    @Element(name = "updateUser", required = false)
    protected String updateUser;

    /**
     * Gets the value of the contactIsdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactIsdn() {
        return contactIsdn;
    }

    /**
     * Sets the value of the contactIsdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactIsdn(String value) {
        this.contactIsdn = value;
    }

    /**
     * Gets the value of the createUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Sets the value of the createUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    /**
     * Gets the value of the districtCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * Sets the value of the districtCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictCode(String value) {
        this.districtCode = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the precinctCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecinctCode() {
        return precinctCode;
    }

    /**
     * Sets the value of the precinctCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecinctCode(String value) {
        this.precinctCode = value;
    }

    /**
     * Gets the value of the provinceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * Sets the value of the provinceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceCode(String value) {
        this.provinceCode = value;
    }

    /**
     * Gets the value of the staffId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStaffId() {
        return staffId;
    }

    /**
     * Sets the value of the staffId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStaffId(Long value) {
        this.staffId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStatus(Long value) {
        this.status = value;
    }

    /**
     * Gets the value of the updateUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * Sets the value of the updateUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

}
