package com.viettel.bss.viettelpos.v4.staff.work;

import com.viettel.bss.viettelpos.v4.commons.connection.CommonObj;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NHAT on 30/05/2017.
 */
@Root(name = "return", strict = false)
public class WorkListObj extends CommonObj {
    @ElementList(name = "listJob", entry = "listJob",required = false, inline = true)
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Root(name = "Data", strict = false)
    public static class Data implements Serializable {
        @Element(name = "id", required = false)
        private long workId;
        @Element(name = "jobCode", required = false)
        private String jobCode;
        @Element(name = "jobContent", required = false)
        private String workName;
        @Element(name = "workName", required = false)
        private String workAssign;
        @Element(name = "startTime", required = false)
        private String startTime;
        @Element(name = "endTime", required = false)
        private String endTime;
        @Element(name = "jobContentUpdate", required = false)
        private String content;
        @Element(name = "status", required = false)
        private int status;
        @ElementList(name = "transStatus", entry = "transStatus", required = false, inline = true)
        private List<Integer> transStatus;
        @Element(name = "reason", required = false)
        private String reason;
        @Element(name = "solution", required = false)
        private String solution;
        @Element(name = "alarm", required = false)
        private Alarm alarm;

        public long getWorkId() {
            return workId;
        }

        public void setWorkId(long workId) {
            this.workId = workId;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String getWorkAssign() {
            return workAssign;
        }

        public void setWorkAssign(String workAssign) {
            this.workAssign = workAssign;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getJobCode() {
            return jobCode;
        }

        public void setJobCode(String jobCode) {
            this.jobCode = jobCode;
        }

        public List<Integer> getTransStatus() {
            return transStatus;
        }

        public void setTransStatus(List<Integer> transStatus) {
            this.transStatus = transStatus;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }

        public Alarm getAlarm() {
            return alarm;
        }

        public void setAlarm(Alarm alarm) {
            this.alarm = alarm;
        }
    }

    @Root(name = "Alarm", strict = false)
    public static class Alarm implements Serializable {
        @Element(name = "alarmMsg", required = false)
        private String alarmMsg;

        public String getAlarmMsg() {
            return alarmMsg;
        }

        public void setAlarmMsg(String alarmMsg) {
            this.alarmMsg = alarmMsg;
        }
    }
}