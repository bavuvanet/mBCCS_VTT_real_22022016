package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Administrator on 1/4/2017.
 */
@Root(name = "msgDebitBean", strict = false)
public class DebitBean {
    @Element(name = "contractId", required = false)
    private String contractId;
    @ElementList(name = "lstUsageCharge", entry = "lstUsageCharge", required = false, inline = true)
    private List<UsageCharge> lstUsageCharge;
    @ElementList(name = "lstDebitSub", entry = "lstDebitSub", required = false, inline = true)
    private List<DebitSub> lstDebitSub;


    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<UsageCharge> getLstUsageCharge() {
        return lstUsageCharge;
    }

    public void setLstUsageCharge(List<UsageCharge> lstUsageCharge) {
        this.lstUsageCharge = lstUsageCharge;
    }

    public List<DebitSub> getLstDebitSub() {
        return lstDebitSub;
    }

    public void setLstDebitSub(List<DebitSub> lstDebitSub) {
        this.lstDebitSub = lstDebitSub;
    }
}
