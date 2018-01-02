package com.viettel.bss.viettelpos.v4.report.object;

public class DataStaffResult {

    private String areaCode;
    private String dataDate;
    private Long rptIndexId;
    private Double targetValue;
    private Double valueDay;
    private Double valueMonth;

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the dataDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDate() {
        return dataDate;
    }

    /**
     * Sets the value of the dataDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDate(String value) {
        this.dataDate = value;
    }

    /**
     * Gets the value of the rptIndexId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRptIndexId() {
        return rptIndexId;
    }

    /**
     * Sets the value of the rptIndexId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRptIndexId(Long value) {
        this.rptIndexId = value;
    }

    /**
     * Gets the value of the targetValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTargetValue() {
        return targetValue;
    }

    /**
     * Sets the value of the targetValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTargetValue(Double value) {
        this.targetValue = value;
    }

    /**
     * Gets the value of the valueDay property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValueDay() {
        return valueDay;
    }

    /**
     * Sets the value of the valueDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValueDay(Double value) {
        this.valueDay = value;
    }

    /**
     * Gets the value of the valueMonth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValueMonth() {
        return valueMonth;
    }

    /**
     * Sets the value of the valueMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValueMonth(Double value) {
        this.valueMonth = value;
    }

	@Override
	public String toString() {
		return String.format(
				"{\"DataStaffResult\":{\"areaCode\":\"%s\", \"dataDate\":\"%s\", \"rptIndexId\":\"%s\", \"valueDay\":\"%s\", \"valueMonth\":\"%s\"}}",
				areaCode, dataDate, rptIndexId, valueDay, valueMonth);
	}
}

