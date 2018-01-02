package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Comparator;

/**
 * Created by hantt47 on 7/6/2017.
 */
@Root(name = "return", strict = false)
public class SubPreChargeData {
    @Element(name = "isdnMonth", required = false)
    private String isdnMonth;
    @Element(name = "month", required = false)
    private String month;
    @Element(name = "vTotCharge", required = false)
    private String vTotCharge;
    @Element(name = "sTotCharge", required = false)
    private String sTotCharge;
    @Element(name = "isdn", required = false)
    private String isdn;
    @Element(name = "vasTotCharge", required = false)
    private String vasTotCharge;
    @Element(name = "sIntnTotCharge", required = false)
    private String sIntnTotCharge;
    @Element(name = "sIntTotCharge", required = false)
    private String sIntTotCharge;
    @Element(name = "finalDatetime", required = false)
    private String finalDatetime;
    @Element(name = "vIntnTotCharge", required = false)
    private String vIntnTotCharge;
    @Element(name = "sExtTotCharge", required = false)
    private String sExtTotCharge;
    @Element(name = "vIntTotCharge", required = false)
    private String vIntTotCharge;
    @Element(name = "tOrgCharge", required = false)
    private String tOrgCharge;
    @Element(name = "tTotCharge", required = false)
    private String tTotCharge;
    @Element(name = "vExtTotCharge", required = false)
    private String vExtTotCharge;
    @Element(name = "gTotCharge", required = false)
    private String gTotCharge;
    @Element(name = "subId", required = false)
    private String subId;
    @Element(name = "vOrgCharge", required = false)
    private String vOrgCharge;
    @Element(name = "version", required = false)
    private String version;

    @Element(name = "vDuration", required = false)
    private String vDuration; //Tổng lưu lượng thoại
    @Element(name = "vFreeDuration", required = false)
    private String vFreeDuration; //Tổng lưu lượng thoại miễn phí
    @Element(name = "vOrgDuration", required = false)
    private String vOrgDuration; //Tổng lưu lượng thoại sử dụng tài khoản gốc
    @Element(name = "vPromDuration", required = false)
    private String vPromDuration; //Tổng lưu lượng thoại sử dụng tài khoản khuyến mại
    @Element(name = "vChargeDuration", required = false)
    private String vChargeDuration; //Tổng lưu lượng thoại sử dụng tài khoản gốc và khuyến mại
    @Element(name = "vIntDuration", required = false)
    private String vIntDuration; //Tổng lưu lượng thoại nội
    @Element(name = "vExtDuration", required = false)
    private String vExtDuration; //Tổng lưu lượng thoại ngoại
    @Element(name = "vIntnDuration", required = false)
    private String vIntnDuration; //Tổng lưu lượng thoại quốc tế
    @Element(name = "sIntTimes", required = false)
    private String sIntTimes; //Lưu lượng sms nội mạng
    @Element(name = "sExtTimes", required = false)
    private String sExtTimes; //Lưu lượng sms ngoại mạng
    @Element(name = "sIntnTimes", required = false)
    private String sIntnTimes; //Lưu lượng sms quốc tế
    @Element(name = "rvDuration", required = false)
    private String rvDuration; //Lưu lượng gọi Roaming
    @Element(name = "gTotVol", required = false)
    private String gTotVol; //Tổng lưu lượng data
    @Element(name = "gFreeVol", required = false)
    private String gFreeVol; //Tổng lưu lượng miễn phí data
    @Element(name = "gOrgVol", required = false)
    private String gOrgVol; //Tổng lưu lượng tiêu dùng gốc data
    @Element(name = "gPromVol", required = false)
    private String gPromVol; //Tổng lưu lượng tiêu dùng khuyến mại data
    @Element(name = "gBothVol", required = false)
    private String gBothVol; //Tổng lưu lượng data gốc và khuyến mại

    @Element(name = "vasFtCharge", required = false)
    private String vasFtCharge; //Tổng cước gốc freetalk
    @Element(name = "vasSfCharge", required = false)
    private String vasSfCharge; //Tổng cước smsfree
    @Element(name = "vasVtfCharge", required = false)
    private String vasVtfCharge; //Tổng cước vtfree
    @Element(name = "vasDtplusCharge", required = false)
    private String vasDtplusCharge; //Tổng cước dataplus

    // Comparator
    public static class CompMonth implements Comparator<SubPreChargeData> {
        @Override
        public int compare(SubPreChargeData arg0, SubPreChargeData arg1) {
            return arg0.getMonth().compareTo(arg1.getMonth());
        }
    }

    public String getIsdnMonth() {
        if (isdnMonth == null) {
            return "0";
        }
        return isdnMonth;
    }

    public void setIsdnMonth(String isdnMonth) {
        this.isdnMonth = isdnMonth;
    }

    public String getMonth() {
        if (month == null) {
            return "201700";
        }
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getvTotCharge() {
        if (vTotCharge == null || vTotCharge.contains("N")) {
            return "0";
        }
        return vTotCharge;
    }

    public void setvTotCharge(String vTotCharge) {
        this.vTotCharge = vTotCharge;
    }

    public String getsTotCharge() {
        if (sTotCharge == null || sTotCharge.contains("N")) {
            return "0";
        }
        return sTotCharge;
    }

    public void setsTotCharge(String sTotCharge) {
        this.sTotCharge = sTotCharge;
    }

    public String getIsdn() {
        if (isdn == null) {
            return "0";
        }
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getVasTotCharge() {
        if (vasTotCharge == null || vasTotCharge.contains("N")) {
            return "0";
        }
        return vasTotCharge;
    }

    public void setVasTotCharge(String vasTotCharge) {
        this.vasTotCharge = vasTotCharge;
    }

    public String getsIntnTotCharge() {
        if (sIntnTotCharge == null || sIntnTotCharge.contains("N")) {
            return "0";
        }
        return sIntnTotCharge;
    }

    public void setsIntnTotCharge(String sIntnTotCharge) {
        this.sIntnTotCharge = sIntnTotCharge;
    }

    public String getsIntTotCharge() {
        if (sIntTotCharge == null || sIntTotCharge.contains("N")) {
            return "0";
        }
        return sIntTotCharge;
    }

    public void setsIntTotCharge(String sIntTotCharge) {
        this.sIntTotCharge = sIntTotCharge;
    }

    public String getFinalDatetime() {
        if (finalDatetime == null) {
            return "0";
        }
        return finalDatetime;
    }

    public void setFinalDatetime(String finalDatetime) {
        this.finalDatetime = finalDatetime;
    }

    public String getvIntnTotCharge() {
        if (vIntnTotCharge == null || vIntnTotCharge.contains("N")) {
            return "0";
        }
        return vIntnTotCharge;
    }

    public void setvIntnTotCharge(String vIntnTotCharge) {
        this.vIntnTotCharge = vIntnTotCharge;
    }

    public String getsExtTotCharge() {
        if (sExtTotCharge == null || sExtTotCharge.contains("N")) {
            return "0";
        }
        return sExtTotCharge;
    }

    public void setsExtTotCharge(String sExtTotCharge) {
        this.sExtTotCharge = sExtTotCharge;
    }

    public String getvIntTotCharge() {
        if (vIntTotCharge == null || vIntTotCharge.contains("N")) {
            return "0";
        }
        return vIntTotCharge;
    }

    public void setvIntTotCharge(String vIntTotCharge) {
        this.vIntTotCharge = vIntTotCharge;
    }

    public String gettOrgCharge() {
        if (tOrgCharge == null || tOrgCharge.contains("N")) {
            return "0";
        }
        return tOrgCharge;
    }

    public void settOrgCharge(String tOrgCharge) {
        this.tOrgCharge = tOrgCharge;
    }

    public String gettTotCharge() {
        if (tTotCharge == null || tTotCharge.contains("N")) {
            return "0";
        }
        return tTotCharge;
    }

    public void settTotCharge(String tTotCharge) {
        this.tTotCharge = tTotCharge;
    }

    public String getvExtTotCharge() {
        if (vExtTotCharge == null || vExtTotCharge.contains("N")) {
            return "0";
        }
        return vExtTotCharge;
    }

    public void setvExtTotCharge(String vExtTotCharge) {
        this.vExtTotCharge = vExtTotCharge;
    }

    public String getgTotCharge() {
        if (gTotCharge == null || gTotCharge.contains("N")) {
            return "0";
        }
        return gTotCharge;
    }

    public void setgTotCharge(String gTotCharge) {
        this.gTotCharge = gTotCharge;
    }

    public String getSubId() {
        if (subId == null || subId.contains("N")) {
            return "0";
        }
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getvOrgCharge() {
        if (vOrgCharge == null || vOrgCharge.contains("N")) {
            return "0";
        }
        return vOrgCharge;
    }

    public void setvOrgCharge(String vOrgCharge) {
        this.vOrgCharge = vOrgCharge;
    }

    public String getVersion() {
        if (version == null || version.contains("N")) {
            return "0";
        }
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getvDuration() {
        if (vDuration == null || vDuration.contains("N")) {
            return "0";
        }
        return vDuration;
    }

    public void setvDuration(String vDuration) {
        this.vDuration = vDuration;
    }

    public String getvFreeDuration() {
        if (vFreeDuration == null || vFreeDuration.contains("N")) {
            return "0";
        }
        return vFreeDuration;
    }

    public void setvFreeDuration(String vFreeDuration) {
        this.vFreeDuration = vFreeDuration;
    }

    public String getvOrgDuration() {
        if (vOrgDuration == null || vOrgDuration.contains("N")) {
            return "0";
        }
        return vOrgDuration;
    }

    public void setvOrgDuration(String vOrgDuration) {
        this.vOrgDuration = vOrgDuration;
    }

    public String getvPromDuration() {
        if (vPromDuration == null || vPromDuration.contains("N")) {
            return "0";
        }
        return vPromDuration;
    }

    public void setvPromDuration(String vPromDuration) {
        this.vPromDuration = vPromDuration;
    }

    public String getvChargeDuration() {
        if (vChargeDuration == null || vChargeDuration.contains("N")) {
            return "0";
        }
        return vChargeDuration;
    }

    public void setvChargeDuration(String vChargeDuration) {
        this.vChargeDuration = vChargeDuration;
    }

    public String getvIntDuration() {
        if (vIntDuration == null || vIntDuration.contains("N")) {
            return "0";
        }
        return vIntDuration;
    }

    public void setvIntDuration(String vIntDuration) {
        this.vIntDuration = vIntDuration;
    }

    public String getvExtDuration() {
        if (vExtDuration == null || vExtDuration.contains("N")) {
            return "0";
        }
        return vExtDuration;
    }

    public void setvExtDuration(String vExtDuration) {
        this.vExtDuration = vExtDuration;
    }

    public String getvIntnDuration() {
        if (vIntnDuration == null || vIntnDuration.contains("N")) {
            return "0";
        }
        return vIntnDuration;
    }

    public void setvIntnDuration(String vIntnDuration) {
        this.vIntnDuration = vIntnDuration;
    }

    public String getsIntTimes() {
        if (sIntTimes == null || sIntTimes.contains("N")) {
            return "0";
        }
        return sIntTimes;
    }

    public void setsIntTimes(String sIntTimes) {
        this.sIntTimes = sIntTimes;
    }

    public String getsExtTimes() {
        if (sExtTimes == null || sExtTimes.contains("N")) {
            return "0";
        }
        return sExtTimes;
    }

    public void setsExtTimes(String sExtTimes) {
        this.sExtTimes = sExtTimes;
    }

    public String getsIntnTimes() {
        if (sIntnTimes == null || sIntnTimes.contains("N")) {
            return "0";
        }
        return sIntnTimes;
    }

    public void setsIntnTimes(String sIntnTimes) {
        this.sIntnTimes = sIntnTimes;
    }

    public String getRvDuration() {
        if (rvDuration == null || rvDuration.contains("N")) {
            return "0";
        }
        return rvDuration;
    }

    public void setRvDuration(String rvDuration) {
        this.rvDuration = rvDuration;
    }

    public String getgTotVol() {
        if (gTotVol == null || gTotVol.contains("N")) {
            return "0";
        }
        return gTotVol;
    }

    public void setgTotVol(String gTotVol) {
        this.gTotVol = gTotVol;
    }

    public String getgFreeVol() {
        if (gFreeVol == null || gFreeVol.contains("N")) {
            return "0";
        }
        return gFreeVol;
    }

    public void setgFreeVol(String gFreeVol) {
        this.gFreeVol = gFreeVol;
    }

    public String getgOrgVol() {
        if (gOrgVol == null || gOrgVol.contains("N")) {
            return "0";
        }
        return gOrgVol;
    }

    public void setgOrgVol(String gOrgVol) {
        this.gOrgVol = gOrgVol;
    }

    public String getgPromVol() {
        if (gPromVol == null || gPromVol.contains("N")) {
            return "0";
        }
        return gPromVol;
    }

    public void setgPromVol(String gPromVol) {
        this.gPromVol = gPromVol;
    }

    public String getgBothVol() {
        if (gBothVol == null || gBothVol.contains("N")) {
            return "0";
        }
        return gBothVol;
    }

    public void setgBothVol(String gBothVol) {
        this.gBothVol = gBothVol;
    }

    public String getVasFtCharge() {
        if (vasFtCharge == null || vasFtCharge.contains("N")) {
            return "0";
        }
        return vasFtCharge;
    }

    public void setVasFtCharge(String vasFtCharge) {
        this.vasFtCharge = vasFtCharge;
    }

    public String getVasSfCharge() {
        if (vasSfCharge == null || vasSfCharge.contains("N")) {
            return "0";
        }
        return vasSfCharge;
    }

    public void setVasSfCharge(String vasSfCharge) {
        this.vasSfCharge = vasSfCharge;
    }

    public String getVasVtfCharge() {
        if (vasVtfCharge == null || vasVtfCharge.contains("N")) {
            return "0";
        }
        return vasVtfCharge;
    }

    public void setVasVtfCharge(String vasVtfCharge) {
        this.vasVtfCharge = vasVtfCharge;
    }

    public String getVasDtplusCharge() {
        if (vasDtplusCharge == null || vasDtplusCharge.contains("N")) {
            return "0";
        }
        return vasDtplusCharge;
    }

    public void setVasDtplusCharge(String vasDtplusCharge) {
        this.vasDtplusCharge = vasDtplusCharge;
    }
}
