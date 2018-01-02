package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;

@Root(name = "return", strict = false)
public class PaymentPrePaidPromotionBeans implements Serializable {
    @Element(name = "prePaidCode", required = false)
    private String prePaidCode;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "id", required = false)
    private String id;
    @Element(name = "prePaidAmount", required = false)
    private Long prePaidAmount;

    // danh sach chi tiet
    @ElementList(name = "paymentPrePaidDetailBeans", entry = "paymentPrePaidDetailBeans", required = false, inline = true)
    private ArrayList<PaymentPrePaidDetailBeans> lstDetailBeans = new ArrayList<>();



    private Long totalMoney = 0L;

    public Long getTotalMoney() {
        Long totalMoney = 0L;
        if (!CommonActivity.isNullOrEmptyArray(getLstDetailBeans())) {
            for (PaymentPrePaidDetailBeans item : getLstDetailBeans()) {
                if (!CommonActivity.isNullOrEmpty(item.getTotalMoney())) {
                    totalMoney = totalMoney + Long.parseLong(item.getTotalMoney());
                }
            }
        }
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrePaidCode() {
        return prePaidCode;
    }

    public void setPrePaidCode(String prePaidCode) {
        this.prePaidCode = prePaidCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PaymentPrePaidDetailBeans> getLstDetailBeans() {
        return lstDetailBeans;
    }

    public void setLstDetailBeans(
            ArrayList<PaymentPrePaidDetailBeans> lstDetailBeans) {
        this.lstDetailBeans = lstDetailBeans;
    }


}
