package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by thinhhq1 on 9/30/2017.
 */
@Root(name = "return", strict = false)
public class NodeCodeDetail implements Serializable{
    @Element(name = "ISDN", required = false)
    private String isdn;
    @Element(name = "TENKH", required = false)
    private String nameCus;
    @Element(name = "SDT_LIENHE", required = false)
    private String tel;
    @Element(name = "DIACHI", required = false)
    private String address;
    @Element(name = "ACT_STATUS", required = false)
    private String actStatus;
    @Element(name = "NGAYNGHIEMTHU", required = false)
    private String dateNthu;

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getNameCus() {
        return nameCus;
    }

    public void setNameCus(String nameCus) {
        this.nameCus = nameCus;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActStatus() {

        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getDateNthu() {
        return dateNthu;
    }

    public void setDateNthu(String dateNthu) {
        this.dateNthu = dateNthu;
    }
}
