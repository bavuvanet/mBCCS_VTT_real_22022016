package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;
import java.util.ArrayList;


public class StockModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private String stateName;
    private Long telecomServiceId;
    private Long stateId;
    private Long price = 0L;

    // So hang con trong kho
    private Long quantity;
    private Long quantityIssue;
    private ArrayList<String> lstSerial = new ArrayList<String>();
    private Long stockTypeId;
    private ArrayList<String> selectedSerial = new ArrayList<String>();
    private Long quantitySaling = 0L;
    private Long checkSerial;
    private Long transOrderDetailId;
    private ArrayList<Serial> mListSerial = new ArrayList<Serial>();
    private ArrayList<Serial> mListSerialSelection = new ArrayList<Serial>();
    private Long quantityOrder = 0L;
    private String tableName;

    public String getTableName() {
        if(!CommonActivity.isNullOrEmpty(tableName)){
            return tableName;
        }
        if (stockTypeId == null) {
            return "";
        }

        if (stockTypeId.equals(1L)) {
            return "STOCK_ISDN_MOBILE";
        }
        if (stockTypeId.equals(2L)) {
            return "STOCK_ISDN_HOMEPHONE";
        }
        if (stockTypeId.equals(3L)) {
            return "STOCK_ISDN_PSTN";
        }


        if (stockTypeId.equals(4L)) {
            return "STOCK_SIM";
        }
        if (stockTypeId.equals(5L)) {
            return "STOCK_SIM";
        }
        if (stockTypeId.equals(6L)) {
            return "STOCK_CARD";
        }
        if (stockTypeId.equals(7L)) {
            return "STOCK_HANDSET";
        }if (stockTypeId.equals(10L)) {
            return "STOCK_HANDSET";
        }
        if (stockTypeId.equals(8L)) {
            return "STOCK_KIT";
        }

        return "";

    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Long quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    private String serial;

    private String stockTypeName;

    private String stockTypeCode;

    public String getStockTypeCode() {
        return stockTypeCode;
    }

    public void setStockTypeCode(String stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public ArrayList<Serial> getmListSerialSelection() {
        return mListSerialSelection;
    }

    public void setmListSerialSelection(ArrayList<Serial> mListSerialSelection) {
        this.mListSerialSelection = mListSerialSelection;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public ArrayList<Serial> getmListSerial() {
        return mListSerial;
    }

    public void setmListSerial(ArrayList<Serial> mListSerial) {
        this.mListSerial = mListSerial;
    }

    public Long getTransOrderDetailId() {
        return transOrderDetailId;
    }

    public void setTransOrderDetailId(Long transOrderDetailId) {
        this.transOrderDetailId = transOrderDetailId;
    }

    public Long getCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public ArrayList<String> getSelectedSerial() {
        if (selectedSerial == null) {
            return new ArrayList<String>();
        }
        return selectedSerial;
    }

    public void setSelectedSerial(ArrayList<String> selectedSerial) {
        this.selectedSerial = selectedSerial;
    }

    public Long getQuantitySaling() {
        if (quantitySaling == null) {
            return 0L;
        }
        return quantitySaling;
    }

    public void setQuantitySaling(Long quantitySaling) {
        this.quantitySaling = quantitySaling;
    }

//	private String tableName;
//
//	public String getTableName() {
//		return tableName;
//	}
//
//	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
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

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityIssue() {
        if (quantityIssue == null) {
            return 0L;
        }
        return quantityIssue;
    }

    public void setQuantityIssue(Long quantityIssue) {
        this.quantityIssue = quantityIssue;
    }

    public ArrayList<String> getLstSerial() {
        return lstSerial;
    }

    public void setLstSerial(ArrayList<String> lstSerial) {
        this.lstSerial = lstSerial;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public boolean isCheckSerial() {
        return checkSerial != null && checkSerial.compareTo(1L) == 0;
    }

    /**
     * Kiem tra mat hang da duoc nhap day du serial chua
     *
     * @return
     */
    public Boolean isFullySerial() {
        return !(getQuantitySaling() > 0
                && getSelectedSerial().size() < getQuantitySaling()
                && isCheckSerial());
    }

    public Boolean isStockHandset() {
        return stockTypeId.equals(7L);

    }

    public Boolean isStockCard() {
        return stockTypeId.equals(6L);
    }

    public String toJson() {
        return String
                .format("{\"StockModel\":{\"stockModelId\":\"%s\", \"stockModelCode\":\"%s\", \"stockModelName\":\"%s\", \"stateName\":\"%s\", \"telecomServiceId\":\"%s\", \"stateId\":\"%s\", \"price\":\"%s\", \"quantity\":\"%s\", \"quantityIssue\":\"%s\", \"lstSerial\":\"%s\", \"stockTypeId\":\"%s\", \"selectedSerial\":\"%s\", \"quantitySaling\":\"%s\", \"checkSerial\":\"%s\", \"transOrderDetailId\":\"%s\", \"mListSerial\":\"%s\", \"mListSerialSelection\":\"%s\", \"serial\":\"%s\", \"stockTypeName\":\"%s\", \"stockTypeCode\":\"%s\", \"tableName\":\"%s\"}}",
                        stockModelId, stockModelCode, stockModelName,
                        stateName, telecomServiceId, stateId, price, quantity,
                        quantityIssue, lstSerial, stockTypeId, selectedSerial,
                        quantitySaling, checkSerial, transOrderDetailId,
                        mListSerial, mListSerialSelection, serial,
                        stockTypeName, stockTypeCode, "");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return stockModelName;
    }

}
