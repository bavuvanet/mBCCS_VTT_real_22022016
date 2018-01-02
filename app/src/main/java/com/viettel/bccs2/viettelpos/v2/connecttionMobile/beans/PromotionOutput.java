package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by thinhhq1 on 4/25/2017.
 */

public class PromotionOutput implements Serializable{
    private String avgSubCharge;
    private ArrayList<PromotionTypeBeans> lstPromotionTypeBeanses = new ArrayList<>();

    public String getAvgSubCharge() {
        return avgSubCharge;
    }

    public void setAvgSubCharge(String avgSubCharge) {
        this.avgSubCharge = avgSubCharge;
    }

    public ArrayList<PromotionTypeBeans> getLstPromotionTypeBeanses() {
        return lstPromotionTypeBeanses;
    }

    public void setLstPromotionTypeBeanses(ArrayList<PromotionTypeBeans> lstPromotionTypeBeanses) {
        this.lstPromotionTypeBeanses = lstPromotionTypeBeanses;
    }
}
