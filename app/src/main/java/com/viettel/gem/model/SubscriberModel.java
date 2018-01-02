package com.viettel.gem.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return", strict = false)
public class SubscriberModel {

    @Element(name = "errorCode", required = false)
    private String errorCode;

    @ElementList(name = "lstSubscriberDTOExt", entry = "lstSubscriberDTOExt", required = false, inline = true)
    private List<SubscriberDTO> lstSubscriberDTOExt = new ArrayList<>();

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<SubscriberDTO> getLstSubscriberDTOExt() {
        return lstSubscriberDTOExt;
    }

    public void setLstSubscriberDTOExt(List<SubscriberDTO> lstSubscriberDTOExt) {
        this.lstSubscriberDTOExt = lstSubscriberDTOExt;
    }
}
