package com.viettel.bss.viettelpos.v4.staff.kpi;

import com.viettel.bss.viettelpos.v4.commons.connection.CommonObj;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ICHI on 6/2/2017.
 */
@Root(name = "return", strict = false)
public class KPIObj extends CommonObj {
    @ElementList(required = false, inline = true)
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Root(name = "kpiForStaffDTO", strict = false)
    public static class Data implements Serializable {
        @Element(name = "channelCode")
        private String channelCode;
        @Element(name = "channelName")
        private String channelName;
        @Element(name = "districtCode")
        private String districtCode;
        @Element(name = "districtName")
        private String districtName;
        @Element(name = "kpiCode")
        private String kpiCode;
        @Element(name = "kpiName")
        private String kpiName;
        @Element(name = "kpiTarget")
        private String kpiTarget;
        @Element(name = "month")
        private String month;
        @Element(name = "provinceCode")
        private String provinceCode;
        @Element(name = "provinceName")
        private String provinceName;
        @Element(name = "staffCode")
        private String staffCode;
        @Element(name = "staffName")
        private String staffName;
        @Element(name = "totalTarget")
        private String totalTarget;

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getKpiCode() {
            return kpiCode;
        }

        public void setKpiCode(String kpiCode) {
            this.kpiCode = kpiCode;
        }

        public String getKpiName() {
            return kpiName;
        }

        public void setKpiName(String kpiName) {
            this.kpiName = kpiName;
        }

        public String getKpiTarget() {
            return kpiTarget;
        }

        public void setKpiTarget(String kpiTarget) {
            this.kpiTarget = kpiTarget;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getStaffCode() {
            return staffCode;
        }

        public void setStaffCode(String staffCode) {
            this.staffCode = staffCode;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getTotalTarget() {
            return totalTarget;
        }

        public void setTotalTarget(String totalTarget) {
            this.totalTarget = totalTarget;
        }
    }
}