package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by leekien on 11/10/2017.
 */
@Root(name = "catagoryInfor", strict = false)
public class SubInvalidDTO {
    @Element(name = "description", required = false)
    protected String description;
    @Element(name = "id", required = false)
    protected Long id;
    @Element(name = "importDateTime", required = false)
    protected String importDateTime;
    @Element(name = "isdn",required = false)
    protected String isdn;
    @Element(name = "status",required = false)
    protected String status;
    @Element(name = "subId",required = false)
    protected Long subId;
    @Element(name = "updateDateTime",required = false)
    protected String updateDateTime;
    @Element(name = "updateReason",required = false)
    protected String updateReason;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImportDateTime() {
        return importDateTime;
    }

    public void setImportDateTime(String importDateTime) {
        this.importDateTime = importDateTime;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }
}
