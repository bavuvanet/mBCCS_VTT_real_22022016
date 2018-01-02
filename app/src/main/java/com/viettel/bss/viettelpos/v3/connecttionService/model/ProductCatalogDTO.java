package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.test.ServiceTestCase;

@Root(name = "ProductCatalogDTO", strict = false)
public class ProductCatalogDTO implements Serializable {
	@Element(name = "description", required = false)
    private String description;
	@Element(name = "name", required = false)
    private String name;
	@Element(name = "parentId", required = false)
    private Long parentId;
	@Element(name = "productCatalogId", required = false)
    private Long productCatalogId;
	@Element(name = "status", required = false)
    private String status;
	@Element(name = "telServiceAlias", required = false)
    private String telServiceAlias;
	@Element(name = "telecomServiceId", required = false)
    private int telecomServiceId;
	
	private boolean check = false;
	
	private long quantity = 1L;
	
	private boolean checkEnable = false;


	public ProductCatalogDTO(String name, Long parentId, String telServiceAlias, int telecomServiceId) {
		this.name = name;
		this.parentId = parentId;
		this.telServiceAlias = telServiceAlias;
		this.telecomServiceId = telecomServiceId;
	}

	public ProductCatalogDTO() {

	}



	public ProductCatalogDTO(String name) {
		this.name = name;
	}

	public boolean isCheckEnable() {
		return checkEnable;
	}
	public void setCheckEnable(boolean checkEnable) {
		this.checkEnable = checkEnable;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public String getDescription() {
		return name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getProductCatalogId() {
		return productCatalogId;
	}
	public void setProductCatalogId(Long productCatalogId) {
		this.productCatalogId = productCatalogId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTelServiceAlias() {
		return telServiceAlias;
	}
	public void setTelServiceAlias(String telServiceAlias) {
		this.telServiceAlias = telServiceAlias;
	}
	public int getTelecomServiceId() {
		return telecomServiceId;
	}
	public void setTelecomServiceId(int telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public boolean isPNService() {
		return "N".equals(telServiceAlias) || "P".equals(telServiceAlias);
	}

}
