package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ProductOfferingDTO", strict = false)
public class ProductOfferingDTO implements Serializable {
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "amount", required = false)
	private Long amount;
	@Element(name = "availableQuantity", required = false)
	private Long availableQuantity;
	@Element(name = "checkSerial", required = false)
	private Long checkSerial;
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "nameUpper", required = false)
	private String nameUpper;
	@Element(name = "price", required = false)
	private Long price;
	@Element(name = "productOfferTypeId", required = false)
	private Long productOfferTypeId;
	@Element(name = "productOfferTypeIdName", required = false)
	private String productOfferTypeIdName;
	@Element(name = "productOfferingId", required = false)
	private Long productOfferingId;
	@Element(name = "productSpecId", required = false)
	private Long productSpecId;
	@Element(name = "profile", required = false)
	private String profile;
	@Element(name = "quantity", required = false)
	private Long quantity;
	@Element(name = "quantityKtts", required = false)
	private Long quantityKtts;
	@Element(name = "stateId", required = false)
	private Long stateId;
	@Element(name = "stateIdName", required = false)
	private String stateIdName;
	@Element(name = "status", required = false)
	private String status;
	@ElementList(name = "listStockTransSerialDTOs", entry = "listStockTransSerialDTOs", required = false, inline = true)
	private ArrayList<StockTransSerialDTO> listStockTransSerialDTOs = new ArrayList<>();

	private Long quantitySaling = 0L;

	private List<StockTransSerialDTO> mListSerialSelection = new ArrayList<>();

	
	
	public List<StockTransSerialDTO> getmListSerialSelection() {
		return mListSerialSelection;
	}

	public void setmListSerialSelection(ArrayList<StockTransSerialDTO> mListSerialSelection) {
		this.mListSerialSelection = mListSerialSelection;
	}

	public ArrayList<StockTransSerialDTO> getListStockTransSerialDTOs() {
		return listStockTransSerialDTOs;
	}

	public void setListStockTransSerialDTOs(ArrayList<StockTransSerialDTO> listStockTransSerialDTOs) {
		this.listStockTransSerialDTOs = listStockTransSerialDTOs;
	}

	public Long getQuantitySaling() {
		return quantitySaling;
	}

	public void setQuantitySaling(Long quantitySaling) {
		this.quantitySaling = quantitySaling;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Long availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Long getCheckSerial() {
		return checkSerial;
	}

	public void setCheckSerial(Long checkSerial) {
		this.checkSerial = checkSerial;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameUpper() {
		return nameUpper;
	}

	public void setNameUpper(String nameUpper) {
		this.nameUpper = nameUpper;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getProductOfferTypeId() {
		return productOfferTypeId;
	}

	public void setProductOfferTypeId(Long productOfferTypeId) {
		this.productOfferTypeId = productOfferTypeId;
	}

	public String getProductOfferTypeIdName() {
		return productOfferTypeIdName;
	}

	public void setProductOfferTypeIdName(String productOfferTypeIdName) {
		this.productOfferTypeIdName = productOfferTypeIdName;
	}

	public Long getProductOfferingId() {
		return productOfferingId;
	}

	public void setProductOfferingId(Long productOfferingId) {
		this.productOfferingId = productOfferingId;
	}

	public Long getProductSpecId() {
		return productSpecId;
	}

	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getQuantityKtts() {
		return quantityKtts;
	}

	public void setQuantityKtts(Long quantityKtts) {
		this.quantityKtts = quantityKtts;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateIdName() {
		return stateIdName;
	}

	public void setStateIdName(String stateIdName) {
		this.stateIdName = stateIdName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
