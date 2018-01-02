package com.viettel.bss.viettelpos.v4.charge.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by thinhhq1 on 6/22/2017.
 */
@Root(name = "SubGoodsDTO", strict = false)
public class SubGoodsDTO {

    @Element(name = "serial", required = false)
    protected String serial;
    @Element(name = "serialToRetrieve", required = false)
    protected String serialToRetrieve;
    @Element(name = "stockModelCode", required = false)
    protected String stockModelCode;
    @Element(name = "stockModelName", required = false)
    protected String stockModelName;
    @Element(name = "stockModelId", required = false)
    protected String stockModelId;
    @Element(name = "subGoodsId", required = false)
    protected String subGoodsId;
    @Element(name = "isHiddenCreateSerial", required = false)
    private Boolean isHiddenCreateSerial;
    private Boolean checker=true;

    public Boolean getHiddenCreateSerial() {
        return isHiddenCreateSerial;
    }

    public void setHiddenCreateSerial(Boolean hiddenCreateSerial) {
        isHiddenCreateSerial = hiddenCreateSerial;
    }

    public Boolean getChecker() {
        return checker;
    }
    public void setChecker(Boolean checker) {
        this.checker = checker;
    }

    public String getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(String subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSerialToRetrieve() {
        return serialToRetrieve;
    }

    public void setSerialToRetrieve(String serialToRetrieve) {
        this.serialToRetrieve = serialToRetrieve;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }
}
