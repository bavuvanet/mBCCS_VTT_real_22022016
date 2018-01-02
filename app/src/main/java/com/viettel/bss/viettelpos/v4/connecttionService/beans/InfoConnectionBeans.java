package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoConnectionBeans implements Serializable {
	private String idRequest;
	private String isdnOrAccount;
	private String idContractNo;
	private String serviceName;
	private String pakageCharge;
	private ArrayList<MpServiceFeeBeans> lisMpServiceFeeBeans = new ArrayList<>();
	private ArrayList<SubStockModelRelReqBeans> lisSubStockModelRelReqBeans = new ArrayList<>();
	private String custId;
	 private String regReasonId;
	 private String serviceType;
	 
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getRegReasonId() {
		return regReasonId;
	}
	public void setRegReasonId(String regReasonId) {
		this.regReasonId = regReasonId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}
	public String getIsdnOrAccount() {
		return isdnOrAccount;
	}
	public void setIsdnOrAccount(String isdnOrAccount) {
		this.isdnOrAccount = isdnOrAccount;
	}
	public String getIdContractNo() {
		return idContractNo;
	}
	public void setIdContractNo(String idContractNo) {
		this.idContractNo = idContractNo;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPakageCharge() {
		return pakageCharge;
	}
	public void setPakageCharge(String pakageCharge) {
		this.pakageCharge = pakageCharge;
	}
	public ArrayList<MpServiceFeeBeans> getLisMpServiceFeeBeans() {
		return lisMpServiceFeeBeans;
	}
	public void setLisMpServiceFeeBeans(
			ArrayList<MpServiceFeeBeans> lisMpServiceFeeBeans) {
		this.lisMpServiceFeeBeans = lisMpServiceFeeBeans;
	}
	public ArrayList<SubStockModelRelReqBeans> getLisSubStockModelRelReqBeans() {
		return lisSubStockModelRelReqBeans;
	}
	public void setLisSubStockModelRelReqBeans(
			ArrayList<SubStockModelRelReqBeans> lisSubStockModelRelReqBeans) {
		this.lisSubStockModelRelReqBeans = lisSubStockModelRelReqBeans;
	}
	
}
