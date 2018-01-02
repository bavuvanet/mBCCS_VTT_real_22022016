package com.viettel.bss.viettelpos.v4.charge.object;

import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Administrator on 1/4/2017.
 */
@Root(name = "UsageCharge", strict = false)
public class UsageCharge {

    @Element(name = "billItem", required = false)
    private String billItem;
    @Element(name = "itemName", required = false)
    private String itemName;
    @Element(name = "charge", required = false)
    private String charge;

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return itemName + ": " + StringUtils.formatMoney(charge);
    }
}
