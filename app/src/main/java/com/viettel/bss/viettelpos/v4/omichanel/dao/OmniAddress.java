package com.viettel.bss.viettelpos.v4.omichanel.dao;


import java.io.Serializable;

/**
 * Created by Lamnv5 on 8/22/2017.
 */
public class OmniAddress implements Serializable {
    private String province;
    private String district;
    private String precinct;
    private String address;
    private String fullAddress;

    public OmniAddress() {
    }

    public OmniAddress(String province, String district, String precinct, String address, String fullAddress) {
        this.province = province;
        this.district = district;
        this.precinct = precinct;
        this.address = address;
        this.fullAddress = fullAddress;
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
