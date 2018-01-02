package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ContractOffersObj implements Serializable {
	
	
	private String contractOfferId;
	private String contractOfferNo;
	private String strSignDatetime;
	private String strIssueDatetime;
	private String representPosition;
	private String idType;
	private String issuePlace;
	private String idNo;
	
	private String represent;
	
	
	
	
	public String getRepresent() {
		return represent;
	}
	public void setRepresent(String represent) {
		this.represent = represent;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getContractOfferId() {
		return contractOfferId;
	}
	public void setContractOfferId(String contractOfferId) {
		this.contractOfferId = contractOfferId;
	}
	public String getContractOfferNo() {
		return contractOfferNo;
	}
	public void setContractOfferNo(String contractOfferNo) {
		this.contractOfferNo = contractOfferNo;
	}
	public String getStrSignDatetime() {
		return strSignDatetime;
	}
	public void setStrSignDatetime(String strSignDatetime) {
		this.strSignDatetime = strSignDatetime;
	}
	public String getStrIssueDatetime() {
		return strIssueDatetime;
	}
	public void setStrIssueDatetime(String strIssueDatetime) {
		this.strIssueDatetime = strIssueDatetime;
	}
	public String getRepresentPosition() {
		return representPosition;
	}
	public void setRepresentPosition(String representPosition) {
		this.representPosition = representPosition;
	}
	
	public String getIssuePlace() {
		return issuePlace;
	}
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}
	
	@Override
	public String toString() {
		return String.format(
				"{\"ContractOffersObj\":{\"contractOfferId\":\"%s\", \"contractOfferNo\":\"%s\", \"strSignDatetime\":\"%s\", \"strIssueDatetime\":\"%s\", \"representPosition\":\"%s\", \"idType\":\"%s\", \"issuePlace\":\"%s\", \"idNo\":\"%s\", \"represent\":\"%s\"}}",
				contractOfferId, contractOfferNo, strSignDatetime, strIssueDatetime, representPosition, idType,
				issuePlace, idNo, represent);
	}
	
}
