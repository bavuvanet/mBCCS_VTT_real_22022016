package com.viettel.bss.viettelpos.v4.staff.warning;

import com.viettel.bss.viettelpos.v4.commons.connection.CommonObj;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NHAT on 30/05/2017.
 */
@Root(name = "return", strict = false)
public class AlarmObj extends CommonObj {
    @ElementList(required = false, inline = true)
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Root(name = "alarmForStaffDTO", strict = false)
    public static class Data implements Serializable {
        @Element(name = "alarmId", required = false)
        private long alarmId;
        @Element(name = "alarmLevel", required = false)
        private String alarmLevel;
        @Element(name = "alarmName", required = false)
        private String alarmName;
        @Element(name = "startTime", required = false)
        private String startTime;
        @Element(name = "accruedTime", required = false)
        private long accruedTime;
        @Element(name = "countDayNok", required = false)
        private long countDayNok;
        @Element(name = "kpiCode", required = false)
        private String kpiCode;
        @Element(name = "channelCode", required = false)
        private String channelCode;
        @Element(name = "contentResponse", required = false)
        private String contentResponse;
        @Element(name = "reasonName", required = false)
        private String reasonName;
        @Element(name = "alarmContent", required = false)
        private String alarmContent;

        public long getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(long alarmId) {
            this.alarmId = alarmId;
        }

        public String getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(String alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public String getAlarmName() {
            return alarmName;
        }

        public void setAlarmName(String alarmName) {
            this.alarmName = alarmName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public long getAccruedTime() {
            return accruedTime;
        }

        public void setAccruedTime(long accruedTime) {
            this.accruedTime = accruedTime;
        }

        public long getCountDayNok() {
            return countDayNok;
        }

        public void setCountDayNok(long countDayNok) {
            this.countDayNok = countDayNok;
        }

        public String getKpiCode() {
            return kpiCode;
        }

        public void setKpiCode(String kpiCode) {
            this.kpiCode = kpiCode;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getContentResponse() {
            return contentResponse;
        }

        public void setContentResponse(String contentResponse) {
            this.contentResponse = contentResponse;
        }

        public String getReasonName() {
            return reasonName;
        }

        public void setReasonName(String reasonName) {
            this.reasonName = reasonName;
        }

        public String getAlarmContent() {
            return alarmContent;
        }

        public void setAlarmContent(String alarmContent) {
            this.alarmContent = alarmContent;
        }
    }
}