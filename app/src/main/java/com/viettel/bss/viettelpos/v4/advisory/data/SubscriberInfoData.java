package com.viettel.bss.viettelpos.v4.advisory.data;

import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhhq1 on 7/5/2017.
 */
@Root(name = "return", strict = false)
public class SubscriberInfoData {
    @Element(name = "promotionBalance", required = false)
    private String promotionBalance;
    @Element(name = "subId", required = false)
    private String subId;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "staDatetime", required = false)
    private String staDatetime;
    @Element(name = "device", required = false)
    private String device;
    @Element(name = "dataBalance", required = false)
    private String dataBalance;
    @Element(name = "myViettelInstalled", required = false)
    private String myViettelInstalled;
    @Element(name = "accountBalance", required = false)
    private String accountBalance;
    @Element(name = "simType", required = false)
    private String simType;
    @Element(name = "productCode", required = false)
    private String productCode;
    @Element(name = "version", required = false)
    private String version;
    @Element(name = "expiredDateTime", required = false)
    private String expiredDateTime;
    @Element(name = "multiSim", required = false)
    private String multiSim;

    @ElementList(name = "lstWarningConsum", entry = "lstWarningConsum", required = false, inline = true)
    private List<String> lstWarningConsum;

    public List<String> getLstWarningConsum() {
        if (lstWarningConsum == null) {
            return new ArrayList<>();
        }
        return lstWarningConsum;
    }

    public void setLstWarningConsum(List<String> lstWarningConsum) {
        this.lstWarningConsum = lstWarningConsum;
    }

    public String getPromotionBalance() {
        if (promotionBalance == null) {
            return "";
        }
        return promotionBalance;
    }

    public void setPromotionBalance(String promotionBalance) {
        this.promotionBalance = promotionBalance;
    }

    public String getSubId() {
        if (subId == null) {
            return "";
        }
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getIsdn() {
        if (isdn == null) {
            return "841678608355";
        }
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getStaDatetime() {
        if (staDatetime == null) {
            return "";
        }
        return staDatetime;
    }

    public void setStaDatetime(String staDatetime) {
        this.staDatetime = staDatetime;
    }

    public String getDevice() {
        if (device == null) {
            return "";
        }
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDataBalance() {
        if (dataBalance == null) {
            return "";
        }
        return dataBalance;
    }

    public void setDataBalance(String dataBalance) {
        this.dataBalance = dataBalance;
    }

    public String getMyViettelInstalled() {
        if (myViettelInstalled == null) {
            return "false";
        }
        return myViettelInstalled;
    }

    public void setMyViettelInstalled(String myViettelInstalled) {
        this.myViettelInstalled = myViettelInstalled;
    }

    public String getAccountBalance() {
        if (accountBalance == null) {
            return "";
        }
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getSimType() {
        if (simType == null) {
            return "";
        }
        if (simType.contains("N")) {
            return "3G";
        }
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public String getProductCode() {
        if (productCode == null) {
            return "";
        }
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVersion() {
        if (version == null) {
            return "";
        }
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExpiredDateTime() {
        if (expiredDateTime == null) {
            return "";
        }
        return expiredDateTime;
    }

    public void setExpiredDateTime(String expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public String getMultiSim() {
        if (multiSim == null) {
            return "";
        }
        return multiSim;
    }

    public void setMultiSim(String multiSim) {
        this.multiSim = multiSim;
    }

    public String getPrepaid() {
        if (accountBalance == null) {
            return "false";
        } else {
            return "true";
        }
    }
}
