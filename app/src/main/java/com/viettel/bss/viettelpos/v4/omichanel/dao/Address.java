package com.viettel.bss.viettelpos.v4.omichanel.dao;


import java.io.Serializable;

/**
 * Created by Lamnv5 on 8/22/2017.
 */
public class Address implements Serializable {
    private static final long serialVersionUID = 0L;

    private String province;
    private String district;
    private String precinct;
    private String address;
    private String fullAddress;
    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAreaCode(String areaCode) {

    }
    public String getAreaCode() {
        return province + district + precinct;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
