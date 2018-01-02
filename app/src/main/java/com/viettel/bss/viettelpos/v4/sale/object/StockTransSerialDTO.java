package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "StockTransSerialDTO", strict = false)
public class StockTransSerialDTO implements Serializable{
	@Element(name = "index", required = false)
	private Long index;
	@Element(name = "errorMessage", required = false)
	private String errorMessage;
	@Element(name = "fromOwnerName", required = false)
	private String fromOwnerName;
	@Element(name = "fromSerial", required = false)
	private String fromSerial;
	@Element(name = "modelName", required = false)
	private String modelName;
	@Element(name = "modelStateName", required = false)
	private String modelStateName;
	@Element(name = "ownerId", required = false)
	private Long ownerId;
	@Element(name = "ownerType", required = false)
	private Long ownerType;
	@Element(name = "prodOfferId", required = false)
	private Long prodOfferId;
	@Element(name = "quantity", required = false)
	private Long quantity;
	@Element(name = "stateId", required = false)
	private Long stateId;
	@Element(name = "status", required = false)
	private Long status;
	@Element(name = "stockTransDetailId", required = false)
	private Long stockTransDetailId;
	@Element(name = "stockTransId", required = false)
	private Long stockTransId;
	@Element(name = "stockTransSerialId", required = false)
	private Long stockTransSerialId;
	@Element(name = "toOwnerName", required = false)
	private String toOwnerName;
	@Element(name = "toSerial", required = false)
	private String toSerial;

	
	public StockTransSerialDTO() {
		super();
	}
	public StockTransSerialDTO(String fromSerial, String toSerial) {
		super();
		this.fromSerial = fromSerial;
		this.toSerial = toSerial;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getFromOwnerName() {
		return fromOwnerName;
	}

	public void setFromOwnerName(String fromOwnerName) {
		this.fromOwnerName = fromOwnerName;
	}

	public String getFromSerial() {
		return fromSerial;
	}

	public void setFromSerial(String fromSerial) {
		this.fromSerial = fromSerial;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelStateName() {
		return modelStateName;
	}

	public void setModelStateName(String modelStateName) {
		this.modelStateName = modelStateName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
	}

	public Long getProdOfferId() {
		return prodOfferId;
	}

	public void setProdOfferId(Long prodOfferId) {
		this.prodOfferId = prodOfferId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getStockTransDetailId() {
		return stockTransDetailId;
	}

	public void setStockTransDetailId(Long stockTransDetailId) {
		this.stockTransDetailId = stockTransDetailId;
	}

	public Long getStockTransId() {
		return stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public Long getStockTransSerialId() {
		return stockTransSerialId;
	}

	public void setStockTransSerialId(Long stockTransSerialId) {
		this.stockTransSerialId = stockTransSerialId;
	}

	public String getToOwnerName() {
		return toOwnerName;
	}

	public void setToOwnerName(String toOwnerName) {
		this.toOwnerName = toOwnerName;
	}

	public String getToSerial() {
		return toSerial;
	}

	public void setToSerial(String toSerial) {
		this.toSerial = toSerial;
	}

	public long getNumber() {
		Long numberSerial = 0L;
		if (toSerial == null || fromSerial == null) {
			numberSerial = 1L;
		} else if (toSerial.equals(fromSerial)) {
			numberSerial = 1L;
		} else {
			numberSerial = Long.parseLong(toSerial) - Long.parseLong(fromSerial) + 1;
		}

		return numberSerial;
	}



	public boolean isContains(StockTransSerialDTO child) {
		if (child == null) {
			return false;
		}

		if (StringUtils.isNumberic(child.getFromSerial())&&StringUtils.isNumberic(child.getToSerial())){
			long parentFrom = Long.parseLong(fromSerial);
			long parentTo = Long.parseLong(toSerial);
			long childFrom = Long.parseLong(child.getFromSerial());

			long childTo = Long.parseLong(child.getToSerial());
			if (childFrom >= parentFrom && childTo <= parentTo) {
				return true;
			}
		}


		return false;
	}
}
