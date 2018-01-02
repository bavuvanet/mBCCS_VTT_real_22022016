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
public class CauseObj extends CommonObj {
    @ElementList(required = false, inline = true)
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Root(name = "causeForStaffDTO", strict = false)
    public static class Data implements Serializable {
        @Element(name = "causeId", required = false)
        private long causeId;
        @Element(name = "causeCode", required = false)
        private String causeCode;
        @Element(name = "causeName", required = false)
        private String causeName;
        @Element(name = "description", required = false)
        private String description;
        @Element(name = "status", required = false)
        private long status;

        public long getCauseId() {
            return causeId;
        }

        public void setCauseId(long causeId) {
            this.causeId = causeId;
        }

        public String getCauseCode() {
            return causeCode;
        }

        public void setCauseCode(String causeCode) {
            this.causeCode = causeCode;
        }

        public String getCauseName() {
            return causeName;
        }

        public void setCauseName(String causeName) {
            this.causeName = causeName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getStatus() {
            return status;
        }

        public void setStatus(long status) {
            this.status = status;
        }
    }
}