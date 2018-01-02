package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryTBBundeBeanBCCS implements Serializable {
    private ArrayList<SubTypeBeans> arrChargeBeans = new ArrayList<>();

    public ArrayList<SubTypeBeans> getArrBeans() {
        return arrChargeBeans;
    }

    public void setArrBeans(ArrayList<SubTypeBeans> arrChargeBeans) {
        this.arrChargeBeans = arrChargeBeans;
    }
}
