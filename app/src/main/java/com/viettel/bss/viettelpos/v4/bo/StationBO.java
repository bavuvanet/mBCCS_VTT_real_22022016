package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Toancx on 5/17/2017.
 */
@Root(name = "return", strict = false)
public class StationBO implements Serializable{
    @Element(name = "station", required = false)
    private String station;
    @ElementList(name = "attrs", entry = "attrs", required = false, inline = true)
    private List<Attr> attrs;
    @Element(name = "longitude", required = false)
    private Double longitude;
    @Element(name = "latitude", required = false)
    private Double latitude;
    @Element(name = "code", required = false)
    private String code;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "children", required = false)
    private String children;
    private Boolean isSelected = false;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<Attr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attr> attrs) {
        this.attrs = attrs;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}
