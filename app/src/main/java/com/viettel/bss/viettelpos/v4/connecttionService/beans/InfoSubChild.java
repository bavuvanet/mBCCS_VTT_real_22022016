package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.work.object.HotlineForm;

public class InfoSubChild implements Serializable {
	private String precinct;
	private String prepaidCode;
	private String productCode;
	private String promotionCode;
	private String province;
	private String district;
	private String account;

	
	private String subId;
	private String serviceType;
	private String parentSub;
	private String status;
	private boolean isCheck = false;
	
	
	private boolean isCheckAll = false;
	
	
	
	
	
	private CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();

	private ChangeInfraReq changeInfraReq = new ChangeInfraReq();
	
	
	private String requestHotlineId = "";
	
	private HotlineForm hotlineForm = new HotlineForm();
	
	
	
	public HotlineForm getHotlineForm() {
		return hotlineForm;
	}

	public void setHotlineForm(HotlineForm hotlineForm) {
		this.hotlineForm = hotlineForm;
	}

	public String getRequestHotlineId() {
		
		if(requestHotlineId == null){
			requestHotlineId = "";
		}
		
		return requestHotlineId;
	}

	public void setRequestHotlineId(String requestHotlineId) {
		this.requestHotlineId = requestHotlineId;
	}

	public boolean isCheckAll() {
		return isCheckAll;
	}

	public void setCheckAll(boolean isCheckAll) {
		this.isCheckAll = isCheckAll;
	}

	public ChangeInfraReq getChangeInfraReq() {
		return changeInfraReq;
	}

	public void setChangeInfraReq(ChangeInfraReq changeInfraReq) {
		this.changeInfraReq = changeInfraReq;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getParentSub() {
		return parentSub;
	}

	public void setParentSub(String parentSub) {
		this.parentSub = parentSub;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustommerByIdNoBean getCustommerByIdNoBean() {
		return custommerByIdNoBean;
	}

	public void setCustommerByIdNoBean(CustommerByIdNoBean custommerByIdNoBean) {
		this.custommerByIdNoBean = custommerByIdNoBean;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getPrepaidCode() {
		return prepaidCode;
	}

	public void setPrepaidCode(String prepaidCode) {
		this.prepaidCode = prepaidCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
