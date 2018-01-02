package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class DataCell {
    @Element(name = "tbRegister", required = false)
    private String tbRegister;
    @Element(name = "tbPSLL", required = false)
    private String tbPSLL;
    @Element(name = "llErlang", required = false)
    private String llErlang;
    @Element(name = "llData", required = false)
    private String llData;
    @Element(name = "dtTT", required = false)
    private String dtTT;
    @Element(name = "dtData", required = false)
    private String dtData;
    @Element(name = "tuPeak", required = false)
    private String tuPeak;
    @Element(name = "tuTb", required = false)
    private String tuTb;
    @Element(name = "listCell", required = false)
    private String listCell;
    public String getTbRegister() {
        return tbRegister;
    }
    public void setTbRegister(String tbRegister) {
        this.tbRegister = tbRegister;
    }
    public String getTbPSLL() {
        return tbPSLL;
    }
    public void setTbPSLL(String tbPSLL) {
        this.tbPSLL = tbPSLL;
    }
    public String getLlErlang() {
        return llErlang;
    }
    public void setLlErlang(String llErlang) {
        this.llErlang = llErlang;
    }
    public String getLlData() {
        return llData;
    }
    public void setLlData(String llData) {
        this.llData = llData;
    }
    public String getDtTT() {
        return dtTT;
    }
    public void setDtTT(String dtTT) {
        this.dtTT = dtTT;
    }
    public String getDtData() {
        return dtData;
    }
    public void setDtData(String dtData) {
        this.dtData = dtData;
    }
    public String getTuPeak() {
        return tuPeak;
    }
    public void setTuPeak(String tuPeak) {
        this.tuPeak = tuPeak;
    }
    public String getTuTb() {
        return tuTb;
    }
    public void setTuTb(String tuTb) {
        this.tuTb = tuTb;
    }
    public String getListCell() {
        return listCell;
    }
    public void setListCell(String listCell) {
        this.listCell = listCell;
    }


}